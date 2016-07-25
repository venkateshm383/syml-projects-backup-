package com.syml.generator.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.constant.Constant;
import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.ERP;
import com.syml.generator.domain.annotation.OpenERPFieldInfo;
import com.syml.generator.domain.annotation.ERP.OpenERPType;
import com.syml.util.DataSynHelper;
import com.syml.util.OpenERPSessionUtil;

public class GeneratorBean {
	
	private Set<String> importList = new HashSet<String>();
	private Map<String, String> javaLang = new HashMap<String, String>();
	
	private Map<String,String> classMap = Constant.getOpenERPAndDomainMappings();
//	private static enum Type{
//		STRING("String"), FLOAT("Double"), DOUBLE("Double"), BOOLEAN("Boolean"), INTEGER("Integer"),
//		CHAR("String"), TEXT("String"), BINARY("byte[]"), DATE("Date"), DATETIME("Date");
//		private String value; 
//		private Type(String value){this.value = value;}
//		public String value(){return value;};
//	}
	private static enum Type{
		STRING, FLOAT, DOUBLE, BOOLEAN, INTEGER,
		CHAR, TEXT, BINARY, DATE, DATETIME;
	}
	
	Map<String, String> spechars = new HashMap<String, String>();
	
	public GeneratorBean(){
		
		List<String> opportunitiesIgnoreFields = new ArrayList<String>();
		this.ignoreFields.put("crm_lead", opportunitiesIgnoreFields);
		opportunitiesIgnoreFields.add("Amortization");
		opportunitiesIgnoreFields.add("down_payment_amount");
//		opportunitiesIgnoreFields.add("Lot Size");
//		opportunitiesIgnoreFields.add("Funding_Date");
//		opportunitiesIgnoreFields.add("Monthly_Payment");
//		opportunitiesIgnoreFields.add("Amount");
//		opportunitiesIgnoreFields.add("Commitment_Fee");
//		opportunitiesIgnoreFields.add("Company Name");
//		opportunitiesIgnoreFields.add("Property Type");
//		opportunitiesIgnoreFields.add("Occupancy Status");
//		opportunitiesIgnoreFields.add("");
		
		spechars.put("Private", "private1");
		spechars.put("primary", "primary1");
		spechars.put("class", "clazz");
		spechars.put("# of units", "numberOfUnits");
		spechars.put("# of units rented", "numberOfUnitsRented");
		spechars.put("0", "zero");
		spechars.put("1", "one");
		spechars.put("2", "two");
		spechars.put("3", "three");
		spechars.put("4", "four");
		spechars.put("5", "five");
		spechars.put("6", "six");
		spechars.put("7", "seven");
		spechars.put("8", "eight");
		spechars.put("9", "night");
		
		javaLang.put("java.lang.Object", "Object");
		javaLang.put("java.lang.String", "String");
		javaLang.put("java.lang.Integer", "Integer");
		javaLang.put("java.lang.Double", "Double");
		javaLang.put("java.lang.Boolean", "Boolean");
		
	}
	
	//run and save to file
	public void run() throws Exception{
		
		DataSynHelper<BaseBean> helper =  OpenERPSessionUtil.getOpenERPHelper();
		
		String path = "D:/work/SymlSystem/codebase/underwriting-app/src/com/syml/domain/";
		
		for (String tablename : classMap.keySet()) {
			
//			if(tablename.equals("res_partner")==false)
//				continue;
			
			Map<String, OpenERPFieldInfo> erpFields = helper.getFields(tablename.replace("_", "."));
			
//			System.out.println(erpFields);
			
			System.err.println("ClassName:"+classMap.get(tablename));
			
			String fieldDeclar = this.genField(tablename, erpFields);
			
			String fullclassName = classMap.get(tablename);
			String packageName = fullclassName.substring(0, fullclassName.lastIndexOf("."));
			String className = fullclassName.substring(fullclassName.lastIndexOf(".")+1);
			
			String classTemplate = this.genClassTemplate(className, packageName, tablename);
			
			String ims = "";
			for (String importRecord : importList) {
				ims += importRecord;
			}
			importList.clear();
			
			classTemplate = classTemplate.replace("#import#", ims);	
			
			String classFile = classTemplate.replace("####", fieldDeclar);
			
			classFile = classFile.replace("#getter#", getterAndSetterCode);
			getterAndSetterCode="";
			
			//write
			File file = new File(path, className+".java");
			if(!file.exists())
				file.createNewFile();

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(classFile.getBytes());
			fos.flush();
			fos.close();
			
		}
		
	}
	
	//generator class declare field
	private String getterAndSetterCode = "";
	private String genField(String tablename, Map<String, OpenERPFieldInfo> erpFields){
		
		String nomalTypeOut = "";
		String selectionTypeOut = "";
		String many2OneTypeOut = "";
		String one2ManyTypeOut = "";
		String many2ManyTypeOut = "";
		
		//selection comment flag
		boolean isSelectTypeComment = true;
		//many2one comment flag
		boolean isMany2OneComment = true;
		//one2many comment flag
		boolean isOne2ManyComment = true;
		//many2many comment flag
		boolean isMany2ManyComment = true;
		
		for(String field : erpFields.keySet()){
			
			OpenERPFieldInfo info = erpFields.get(field);
			
			//TODO
			//ignore : credit debit
			if("credit".equals(field))
				continue;
//				info.type = "integer";
			if("debit".equals(field))
				continue;
//				info.type = "integer";
			
			if(isIgnoreField(tablename, info.name) || "id".equals(info.name))
				continue;
			if("selection".equals(info.type)){ //selection type
				String selectionOut = "";
				if(isSelectTypeComment){
					selectionOut +=  "	/*********************************selection type******************************/\n";
					isSelectTypeComment = false;
				}
				selectionOut += "	//"+info.name+"\n";
				selectionOut += "	@ERP( type = OpenERPType.selection, name = \""+info.name+"\" )\n";
				String fieldName = toFieldName(info.name);
				selectionOut += "	public String "+fieldName+";\n\n";
				
				getterAndSetterCode += generatorGetterAndSetMethod(fieldName, String.class);
				
				selectionTypeOut+=selectionOut;
				
			}else if("binary".equals(info.type)){  //binary type
				String fieldName = toFieldName(info.name);
				nomalTypeOut+="	//"+info.name+"\n";
//				if(info.name.matches("^[a-z][A-Za-z0-1]*")!=true)
				nomalTypeOut += "	@ERP( name = \""+info.name+"\", type=OpenERPType.bytes )\n";
				nomalTypeOut += "	@XmlElement\n";
				addImport(XmlElement.class);
				nomalTypeOut += "	public String "+fieldName+";\n\n";
				
				
				getterAndSetterCode += generatorGetterAndSetMethod(fieldName, String.class);
				
			} else if("one2many".equals(info.type)){  //one to many type
//				@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="lender")
//				private Set<Product> products = new HashSet<Product>();

				String tablename1 = (String) info.relationInfo.get(0);
				String className = Constant.getSimpleClassName(tablename1);
				if(className==null)
					continue;
				
				String o2mout = "";
				if(isOne2ManyComment){
					o2mout +=  "	/*********************************one2many type******************************/\n\n";
					isOne2ManyComment = false;
				}
				
				o2mout += handleO2M(info);
				one2ManyTypeOut += o2mout;
				
			} else if("many2one".equals(info.type)){ //many to one type
				
				if(Constant.getSimpleClassName((String) info.relationInfo.get(0))==null)
					continue;
				
				String m2out = "";
				if(isMany2OneComment){
					m2out +=  "	/*********************************many2one type******************************/\n\n";
					isMany2OneComment = false;
				}
				
				m2out += handleM2O(info);
				
				many2OneTypeOut += m2out;
				
			} else if("many2many".equals(info.type)){ //one to many type
				//TODO
				
				if(Constant.getSimpleClassName((String) info.relationInfo.get(0))==null)
					continue;
				
				String m2out = "";
				if(isMany2ManyComment){
					m2out +=  "	/*********************************many2many type******************************/\n\n";
					isMany2ManyComment = false;
				}
				
				m2out += handleM2O(info);
				
				many2ManyTypeOut += m2out;
				
				
			}else{ //normal type: intger, double, string,  text, date,
				
				String fieldName = toFieldName(info.name);
				nomalTypeOut+="	//"+info.name+"\n";
				
				if(info.type.equalsIgnoreCase("text")){
					nomalTypeOut += "	@Column(length=9999)\n";
					this.addImport(Column.class);
				}else if(info.type.equalsIgnoreCase("date")){
					nomalTypeOut += "	@Temporal(TemporalType.TIMESTAMP)\n";
					this.addImport(Temporal.class);
					this.addImport(TemporalType.class);

				}
				if(info.name.matches("^[a-z][A-Za-z0-1]*")!=true)
					nomalTypeOut += "	@ERP( name = \""+info.name+"\" )\n";
				
				Class<?> fieldType = getFieldType(info.type);
				
				nomalTypeOut += "	public "+fieldType.getSimpleName()+" "+fieldName+";\n\n";
				
				getterAndSetterCode += generatorGetterAndSetMethod(fieldName, fieldType);
				
				this.addImport(fieldType);
				
			}
		}
//		System.out.println(nomalTypeOut+"\n");
//		System.out.println(selectionTypeOut);
		
		//define out order
		
		return nomalTypeOut+selectionTypeOut+one2ManyTypeOut+many2OneTypeOut;
		
	}
	
	//import package
	
	private void addImport(Class<?> fieldType) {
		
//		System.out.println(fieldType.getName());
		if(javaLang.get(fieldType.getName()) != null)
			return ;
		
		String importStr = "import "+fieldType.getName()+";\n";
		importStr = importStr.replace("$", ".");
		importList.add(importStr);
		
		
//		if(fieldType.equals(Date.class))
//			importList.add("import java.util.Date;\n");
//		else if(fieldType.equals(Column.class))
//			importList.add("import javax.persistence.Column;\n");
//		else if(fieldType.equals(ManyToOne.class))
//			importList.add("import javax.persistence.ManyToOne;\n");
//		else if(fieldType.equals(JoinColumn.class))
//			importList.add("import javax.persistence.JoinColumn;\n");
//		else if(fieldType.equals(CascadeType.class))
//			importList.add("import javax.persistence.CascadeType;\n");
//		else if(fieldType.equals(OneToMany.class))
//			importList.add("import javax.persistence.OneToMany;\n");
//		else if(fieldType.equals(Set.class))
//			importList.add("import java.util.Set;\n");
//		else if(fieldType.equals(HashSet.class))
//			importList.add("import java.util.HashSet;\n");
//		else if(fieldType.equals(FetchType.class))
//			importList.add("import javax.persistence.FetchType;\n");
//		else if(fieldType.equals(Temporal.class))
//			importList.add("import javax.persistence.Temporal;\n");
//		else if(fieldType.equals(TemporalType.class))
//			importList.add("import javax.persistence.TemporalType;\n");
//		else if(fieldType.equals(Transient.class))
//			importList.add("import javax.persistence.Transient;\n");
//		else if(fieldType.equals(JsonIgnore.class))
//			importList.add("import org.codehaus.jackson.annotate.JsonIgnore;\n");
	}

	/**
	 * generator class template
	 * @param className 
	 * @param packageName
	 * @param annEntityName eg: crm_select
	 * @return
	 */
	private String genClassTemplate(String className, String packageName, String annEntityName){
		String template = "package "+packageName+";\n\n";
		
		//import mark
		template += "#import#";
		addImport(Entity.class);
		addImport(XmlRootElement.class);
		addImport(BaseBean.class);
		addImport(ERP.class);
		addImport(OpenERPType.class);
		
		template +="/**\n"+
		 "* mapping open erp object : "+annEntityName.replace("_", ".")+"\n"+
		 "* @author .x.m.\n"+
		 "*\n"+
		 "*/\n"+
		 "@XmlRootElement\n" +
		 "@Entity(name=\""+annEntityName+"\")\n"+
		 "public class "+className+" extends BaseBean {\n"+
		 "####\n"+ //TODO
		 "#getter#"+ //getter and setter mark
		 "}";
		
		return template;
		
	}
	
	//format field name
	public String toFieldName(String name1){
		
		String spechar = spechars.get(name1);
		if(spechar!=null)
			return spechar;
		
		String name = name1.replace("(", " ").replace(")", " ").trim();
		
		if(name==null || name.trim().length()==0)
			return name;
		
		//replace first number
		char firstCh = name.charAt(0);
		String firstChS = String.valueOf(firstCh);
		String fir = spechars.get(firstChS);
		if(fir!=null)
			name = fir+name.substring(1);
		
		if(name.indexOf("/")!=-1)
			name = name.substring(0,name.indexOf("/")-1);
		
		String[] subs = name.split("(\\s+)|(_+)|(\\-+)");
		
		String fieldName = "";
		
		if(subs.length>0){ //TDS  ==>  TDS !==> tDS
			String tmp = subs[0];
			
			char[] cs = tmp.toCharArray();
			boolean isAllUpperCase = true;
			for (int i=0; i<cs.length; i++) {
				if(!(cs[i]>=65 && cs[i]<=90)){
					isAllUpperCase = false;
					break;
				}
			}
			
			if(isAllUpperCase)
				fieldName += tmp;
			else
				fieldName += tmp.substring(0,1).toLowerCase()+tmp.substring(1);
		}
		
		if(subs.length>1){
			for (int i = 1; i < subs.length; i++) {
				if(subs[i]!=null && subs[i].trim().length()>0){
					if(subs[i].trim().length()>1)
						fieldName += subs[i].substring(0,1).toUpperCase()+subs[i].substring(1);
					else
						fieldName += subs[i];
				}
					
			}
		}
		
		return fieldName;
		
	}
	
	//get field type 
	private Class<?> getFieldType(String type){
		
		Type enumType = Type.valueOf(type.toUpperCase());
		
		switch (enumType) {
		case STRING: 
		case CHAR:
		case TEXT: return String.class;
		case FLOAT:
		case DOUBLE : return Double.class;
		case DATE :
		case DATETIME : return Date.class;
		case BOOLEAN : return Boolean.class;
		case INTEGER : return Integer.class;
		case BINARY : return Byte[].class;
		default:
			return String.class;
		}
	}
	
	private Map<String,List<String>> ignoreFields = new HashMap<String, List<String>>();
	private boolean isIgnoreField(String tableName, String fieldName){
		List<String> ignoreFieldLists = ignoreFields.get(tableName);
		if(ignoreFieldLists!=null)
			for (String field : ignoreFieldLists) {
				if(field.equals(fieldName))
					return true;
			}
		return false;
	}
	
	
	private String generatorGetterAndSetMethod(String field, Class<?> type){
		String codePiece = "";
		
		String fieldmethod = field.substring(0,1).toUpperCase()+field.substring(1);
		String set = "set"+fieldmethod;
		String get = "get"+fieldmethod;
		
		codePiece +="	public void "+set+"("+type.getSimpleName()+" "+field+"){\n" +
					"		this."+field+" = " +field+";\n"+
					"	}\n\n";
		
		codePiece +="	public "+type.getSimpleName()+" "+get+"(){\n" +
					"		return this."+field+";\n" +
					"	}\n\n";
		
		return codePiece;
	}
	
	private String generatorGetterAndSetMethod(String field, String type){
		String codePiece = "";
		
		String fieldmethod = field.substring(0,1).toUpperCase()+field.substring(1);
		String set = "set"+fieldmethod;
		String get = "get"+fieldmethod;
		
		codePiece +="	public void "+set+"("+type+" "+field+"){\n" +
					"		this."+field+" = " +field+";\n"+
					"	}\n\n";
		
		codePiece +="	public "+type+" "+get+"(){\n" +
					"		return this."+field+";\n" +
					"	}\n\n";
		
		return codePiece;
	}
	
	private String handleM2O(OpenERPFieldInfo info){
		
		
		
		//old
		/*
		String m2out = "";
		
		String realtionTableName = (String) info.relationInfo.get(0);
		String className = Constant.getSimpleClassName(realtionTableName);
		
		String dbfieldName = className.substring(0,1).toLowerCase()+className.substring(1);
		String fieldName = toFieldName(info.name);
		fieldName = fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
		
		m2out += "	@ERP(name=\""+info.name+"\", type = OpenERPType.many2one )\n"+ 
			     "	@ManyToOne(optional=false, cascade=CascadeType.ALL)\n" +
			     "	@JoinColumn(name=\""+fieldName+"Id\")\n"+
			     "	private "+className+" "+fieldName+";\n\n";
		
		addImport(ManyToOne.class);
		addImport(JoinColumn.class);
		addImport(CascadeType.class);
		addImport(OpenERPType.class);
		
		getterAndSetterCode += generatorGetterAndSetMethod(fieldName, className);
		*/
		
		//new @Transient
		
		String fieldName = toFieldName(info.name);
		String tablename = info.relationInfo.get(0).toString();
		String m2out =  "	//many to one : mapping table : "+tablename.replace(".", "_")+"("+Constant.getFullClassName(tablename)+")\n"+ 
						"	@ERP(name=\""+info.name+"\", type = OpenERPType.many2one )\n"+ 
						"	@JsonIgnore\n" +
						"	public Integer "+fieldName+";\n\n";
//						"	public "+info.;
		
		addImport(OpenERPType.class);
		addImport(JsonIgnore.class);
		
		getterAndSetterCode += generatorGetterAndSetMethod(fieldName, Integer.class);
		
		return m2out;
		
	}
	
	
	private String handleO2M(OpenERPFieldInfo info){
		
		//old
		/*
		String mappedField = toFieldName((String)(info.relationInfo.get(1)));
		mappedField = mappedField.substring(0,1).toLowerCase()+mappedField.substring(1);
		
		String field1 = toFieldName(info.name);
		
		field1 = field1.substring(0,1).toLowerCase()+field1.substring(1);
		
		o2mout += "	@ERP(name=\""+info.name+"\", type = OpenERPType.one2many )\n" + 
				 "	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy=\""+mappedField+"\")\n"+
				 "	private Set<"+className+"> "+field1+" = new HashSet<"+className+">();\n\n";
		
		addImport(OneToMany.class);
		addImport(CascadeType.class);
		addImport(OpenERPType.class);
		addImport(Set.class);
		addImport(HashSet.class);
		addImport(FetchType.class);				
		one2ManyTypeOut += o2mout;
		getterAndSetterCode += generatorGetterAndSetMethod(field1, "Set<"+className+">");
		 */
		String tablename = (String) info.relationInfo.get(0);
		String mappedField = (String) info.relationInfo.get(1);
		mappedField = mappedField.substring(0,1).toLowerCase()+mappedField.substring(1);
		
		String fieldName = toFieldName(info.name);
		
		String fullClassName = Constant.getFullClassName(tablename);
		String simplaClassName = fullClassName.substring(fullClassName.lastIndexOf(".")+1);
		String out = "	//many to one : mapping table : "+tablename.replace(".", "_")+"("+fullClassName+")-->"+mappedField+"\n"+ 
					 "	@ERP(name=\""+info.name+"\", type = OpenERPType.one2many )\n" + 
					 "	@JsonIgnore\n" +
					 "	@Transient\n" +
					 "	public List<"+simplaClassName+"> "+fieldName+" = new ArrayList<"+simplaClassName+">();\n\n";
		addImport(Transient.class);
		addImport(List.class);
		addImport(ArrayList.class);
		addImport(JsonIgnore.class);
		getterAndSetterCode += generatorGetterAndSetMethod(fieldName, "List<"+simplaClassName+">");
		return out;
	}
	
}
