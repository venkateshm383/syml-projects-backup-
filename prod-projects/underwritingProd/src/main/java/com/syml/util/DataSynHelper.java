package com.syml.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.xmlrpc.XmlRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.FieldCollection;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCProtocol;
import com.debortoliwines.openerp.api.OpeneERPApiException;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.SelectionOption;
import com.syml.constant.Constant;
import com.syml.domain.Address;
import com.syml.domain.Applicant;
import com.syml.domain.Asset;
import com.syml.domain.BaseBean;
import com.syml.domain.Collection;
import com.syml.domain.Income;
import com.syml.domain.LatePayment;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Opportunity;
import com.syml.domain.Property;
import com.syml.generator.domain.annotation.ERP.OpenERPType;
import com.syml.generator.domain.annotation.FieldInfo;
import com.syml.generator.domain.annotation.OpenERPFieldInfo;
import com.syml.generator.domain.annotation.ParseFieldInfo;

public class DataSynHelper<T extends BaseBean> {

	private static final Logger logger = LoggerFactory.getLogger(DataSynHelper.class);

	private static ExtendedSession openERPSession = null;
	public void setOpenERPSession(ExtendedSession openERPSession) {
		this.openERPSession = openERPSession;
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		
		//Session openERPSession = new Session(Constant.OPENERP_URL, Constant.OPENERP_PORT, Constant.OPENERP_DB, Constant.OPENERP_USER, Constant.OPENERP_PWD);
		//openERPSession.startSession();
		ExtendedSession openERPSession = OpenERPSessionUtil.initSession();
		DataSynHelper helper = new DataSynHelper();

		helper.setOpenERPSession(openERPSession);

		helper.setOpenERPSession(openERPSession);

	Opportunity beans = helper.getOpportunityFromERP(586);
//		
		//System.out.println("-------------------------------------------------------------------------BaseLTV:"+ beans.getBaseLtv());
		//System.out.println("-------------------------------------------------------------------------GDS:"+ beans.getGDS());

		/*for(Opportunity opp : beans)
			System.out.println("---------------------"+opp.getBaseltv());*/

//		helper.getFields("ir.model");
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Opportunity getOpportunityFromERP(int id) throws Exception {
		ExtendedSession openERPSession = OpenERPSessionUtil.initSession();
		DataSynHelper helper = new DataSynHelper();
		helper.setOpenERPSession(openERPSession);

		logger.info("Calling getOpportunityFromERP({})", id);
		
		List<Opportunity> beans = helper.getObjectFromERP(Opportunity.class, id);
		logger.info("Calling getOpportunityFromERP({}). Opportunity data size:{}", id, beans.size());
		
		if(beans != null && beans.size() > 0)
		{
			Opportunity opp = beans.get(0);
			if(opp.appRecIds != null && opp.appRecIds.size() > 0) // applicant_record_line  or app_rec_ids
			{
				List<Applicant> applicants = new ArrayList<Applicant>();
				for(Integer applicantId : opp.appRecIds)
				{
					List<Applicant> apps = helper.getObjectFromERP(Applicant.class, applicantId);
					for(Applicant app : apps)
					{
						if(app.beaconScore == null)
							app.beaconScore = 0;
						if(app.liabilityIds != null && app.liabilityIds.size() > 0)
						{
							List<Liability> liabilities = new ArrayList<Liability>();
							for(Integer liabilityId : app.liabilityIds)
							{
								liabilities.addAll(helper.getObjectFromERP(Liability.class, liabilityId));
							}
							app.liabilities = liabilities;
						}
						
						if(app.collectionIds != null && app.collectionIds.size() > 0)
						{
							List<Collection> collections = new ArrayList<Collection>();
							for(Integer collectionId :app.collectionIds)
							{
								collections.addAll(helper.getObjectFromERP(Collection.class, collectionId));
							}
							app.collection = collections;
						}
						
						if(app.assetIds != null && app.assetIds.size() > 0)
						{
							List<Asset> assets = new ArrayList<Asset>();
							for(Integer assetId : app.assetIds)
							{
								assets.addAll(helper.getObjectFromERP(Asset.class, assetId));
							}
							app.assets = assets;
						}
						
						if(app.addressIds != null && app.addressIds.size() > 0)
						{
							List<Address> addresses = new ArrayList<Address>();
							for(Integer addressId : app.addressIds)
							{
								addresses.addAll(helper.getObjectFromERP(Address.class, addressId));
							}
							app.addresses = addresses;
						}
						
						if(app.propertyIds != null && app.propertyIds.size() > 0)
						{
							List<Property> properties = new ArrayList<Property>();
							for(Integer propertyId : app.propertyIds)
							{
								properties.addAll(helper.getObjectFromERP(Property.class, propertyId));
							}
							app.properties = properties;
						}
						
						if(app.incomeIds != null && app.incomeIds.size() > 0)
						{
							List<Income> incomes = new ArrayList<Income>();
							for(Integer incomeId : app.incomeIds)
							{
								List<Income> temps = helper.getObjectFromERP(Income.class, incomeId);
								for(Income income : temps)
								{									
								//	if(!"true".equalsIgnoreCase(income.historical))
										incomes.add(income);
								}
								//incomes.addAll(helper.getObjectFromERP(Income.class, incomeId));
							}
							app.incomes = incomes;
//							for(Income income: app.incomes)
//							{
//								if(income.typeOfIncome != null && Integer.parseInt(income.typeOfIncome) > 0)
//									income.typeOfIncome = (AlgoIncome.IncomeType.values()[Integer.parseInt(income.typeOfIncome) - 1]).name();
//							}
						}
						
						if(app.mortgageIds != null && app.mortgageIds.size() > 0)
						{
							List<Mortgage> mortgages = new ArrayList<Mortgage>();
							for(Integer mortgageId : app.mortgageIds)
							{
								mortgages.addAll(helper.getObjectFromERP(Mortgage.class, mortgageId));
							}
							app.mortgages = mortgages;
						}
						
						if(app.latePaymentIds != null && app.latePaymentIds.size() > 0)
						{
							List<LatePayment> latePayments = new ArrayList<LatePayment>();
							for(Integer latePaymentId : app.latePaymentIds)
							{
								latePayments.addAll(helper.getObjectFromERP(LatePayment.class, latePaymentId));
							}
							app.latePayments = latePayments;
						}
					}
					applicants.addAll(apps);
					
				}
				opp.applicants = applicants;			
			}
			return opp;
		}
		return null;
//		helper.getFields("ir.model");
		
	}
	
	public List<T> getObjectFromERP(Class<? extends BaseBean> clazz, Integer id) throws Exception
	{	
		
		List<com.syml.generator.domain.annotation.FieldInfo> fieldInfos = new ParseFieldInfo<T>().getFieldInfo(clazz);
		
		return this.getDataFromERP(clazz, fieldInfos, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getDataFromERP(Class<?> t, List<FieldInfo> fieldInfos, Integer id ) throws Exception{

		Entity an = t.getAnnotation(Entity.class);
		String name = an.name().replace("_", ".");

		ObjectAdapter objectAdapter = null;
		try {
			objectAdapter = openERPSession.getObjectAdapter(name);
		} catch (XmlRpcException | OpeneERPApiException e) {
			logger.error(">>> Trying to re-connect: ");
			OpenERPSessionUtil.sslInit();
			openERPSession = new ExtendedSession(RPCProtocol.RPC_HTTPS, Constant.OPENERP_URL, Constant.OPENERP_PORT, Constant.OPENERP_DB, Constant.OPENERP_USER, Constant.OPENERP_PWD);
			objectAdapter = openERPSession.getObjectAdapter(name);
			
		}
//		FieldCollection fields = objectAdapter.getFields();
//		for(com.debortoliwines.openerp.api.Field field : fields){
//			System.out.println("private "+field.getType().toString().toLowerCase()+" "+field.getName()+
////					.replace(" ", "$").replace("#", "_0").replace("/", "_1")
////					.replace("(", "_2").replace(")", "_3").replace("-", "_4")+
//					";");
//		}
		
		
		FilterCollection filter = new FilterCollection();
		if(id!=null)
			filter.add("id", "=", id);
//		filter.add("street", "=", "xxx");
		
		//logger.debug("######size  of  Field  Info  is##########"+fieldInfos.size());
		
		String[] fields = new String[fieldInfos.size()];
//		String[] fields = new String[fieldInfos.size()+27];
		for (int i = 0; i < fields.length; i++) {
			fields[i]=fieldInfos.get(i).fieldName;
			
		//logger.debug("******FieldInfo  is*********"+fields[i]);

		
			// System.out.println("Field: " + fields[i]);
		}
		// System.out.println(">> read Object:" + t.getName() + " id:" + id);
		// System.out.println(">> Filter :" + filter + " fields:" + fields.length);
		RowCollection objs = objectAdapter.searchAndReadObject(filter, fields);
		//System.out.println(objs.size());
		Class<?> c = t;
		List<T> result = new ArrayList<T>();
		
		for (Row row : objs) {
		//	logger.info("in datasync helper class{} ");
			//logger.info("in row get-----------------  "+row.getFields().toString());
			
			T entity =  (T) c.newInstance();
			result.add(entity);
			
			for (FieldInfo fieldInfo : fieldInfos) {
			//	logger.info("fieldInfo -------opernerp---------"+fieldInfo.fieldName);
			//	logger.info("fieldInfo -------fieldTypeInERP---------"+fieldInfo.fieldTypeInERP);
				try{

				Object valueObject = row.get(fieldInfo.fieldName);
				
				///logger.debug("fieldInfo.fieldName:" + fieldInfo.fieldName + "||" + valueObject);
				//logger.debug("FieldNAme  is"+fieldInfo.fieldName+"Field  TYPE  is"+fieldInfo.fieldTypeInERP);
				
				
				
				if(valueObject == null)
					continue;

				java.lang.reflect.Method method = null;
				
				
				if(fieldInfo.fieldTypeInERP.equals(OpenERPType.bytes.name())){
					
					//save picture and set value is this picture path
					
					valueObject = "default.jpg";
					
					method = entity.getClass().getMethod(fieldInfo.setFieldMethod, String.class);
					
				}else if(fieldInfo.fieldTypeInERP.equals(OpenERPType.many2one.name())){
					//logger.info("*************Inside  If  Meny  Fields*********");
					
					//Object[] obj = (Object[])valueObject;
					try{
					//	logger.info("-------------setFieldMethod-------------"+fieldInfo.setFieldMethod);
						method = entity.getClass().getMethod(fieldInfo.setFieldMethod, fieldInfo.classType);
						//Object[] obj = (Object[])valueObject;
						//logger.debug("*************Vale  Data  is***********"+valueObject);
					//Integer obj = (Integer) valueObject;
                    Object[] obj = (Object[])valueObject;
                   // logger.info("Iterate over  Object");
                  /*  for (int i = 0; i < obj.length; i++) {
						logger.debug("List  of Object  is"+obj[i]);
					}*/
					valueObject = obj;
					}catch(Exception e){
					//to  be  handled					
						
					}
				}else if(fieldInfo.fieldTypeInERP.equals(OpenERPType.one2many.name())){
					//value = [1,2,3]
					//System.out.println("one to many:" + valueObject.getClass().getName());
					Object[] obj = (Object[])valueObject;
					List<Integer> ids = new ArrayList<Integer>();
					for(Object o : obj)
					{
						ids.add((Integer)o);
						//System.out.println("one to many:" + o);
					}
					method = entity.getClass().getMethod(fieldInfo.setFieldMethod, fieldInfo.classType);
					valueObject = ids;
					//continue;
					
					
				}else if(fieldInfo.fieldTypeInERP.equals(OpenERPType.many2many.name())){
					
					// This field type is not presently being handled ... 
					// TODO Note that this logic is returning the ApplicantIDs of the Opportunity, 
					// It is not cycling through ever object in Object[] and returning lists for each object in Object[] ... 
					// i.e. presently it is no generic for any sort of many2many field.  It is working for the Opportunity/ Applicants 
					// relationship because it is only being called in for the field Opportunity.app_rec_ids where only one opportunity is 
					// is being referenced.  If needed in other places, it will need a rework ... 
					// May need to create an array of objects, then for each object in the array, run the logic in one2many
					//System.out.println("fieldInfo.fieldName:" + fieldInfo.fieldName + "||" + valueObject);
					
					// Presently Copied from 1 to many ... May need to make an array of objects first.
					//System.out.println("many to many:" + valueObject.getClass().getName());
					Object[] obj = (Object[])valueObject;
					List<Integer> ids = new ArrayList<Integer>();
					for(Object o : obj)
					{
						ids.add((Integer)o);
						//System.out.println("many to many:" + o);
					}
					method = entity.getClass().getMethod(fieldInfo.setFieldMethod, fieldInfo.classType);
					valueObject = ids;
					
					//continue;
					
				}else if(fieldInfo.fieldTypeInERP.equals(OpenERPType.selection.name())){
					//System.out.println("fieldInfo.fieldName:" + fieldInfo.fieldName + "||" + valueObject);
					method = entity.getClass().getMethod(fieldInfo.setFieldMethod, String.class);
				}
				else if(fieldInfo.fieldTypeInERP.equals(OpenERPType.selection.name())){
					//System.out.println("fieldInfo.fieldName:" + fieldInfo.fieldName + "||" + valueObject);
					method = entity.getClass().getMethod(fieldInfo.setFieldMethod, Double.class);
				}
//				
				else{
					try{
					method = entity.getClass().getMethod(fieldInfo.setFieldMethod, fieldInfo.classType);
				}catch(Exception e){
					logger.error("method  Class setFieldMethod  not  found "+e.getMessage());
					
				}
				
				}
				
				try{
					if(method!=null){
					if(fieldInfo.classType.equals(String.class) && valueObject!=null){
							String stringValue = (String) valueObject;
							if(stringValue.length()>9999)
								valueObject = stringValue.substring(0, 9998);
						}	
						try {
							method.invoke(entity, new Object[]{valueObject});
						} catch (Exception e) {
							
							//logger.error(entity.getClass().getName()+":" + fieldInfo.classType + " "+fieldInfo.fieldName+":"+valueObject.getClass() + " ValueObject:" + valueObject);
						}
					}
				}catch(Exception e){
					
					//logger.error(fieldInfo.fieldName + ":" + method.getName() + ":" + entity.getClass().getName()+":"+fieldInfo.classType+" "+fieldInfo.fieldName+":"+valueObject.getClass());
				}
				
				}catch(NullPointerException e){
					logger.error("Error  in   Field mappimg"+e);
					
				}
				
			}
		}
		
		return result;
//		return null; 
	}

	
	public Map<String, OpenERPFieldInfo> getFields(String tablename) throws Exception{
		
		String name = tablename;
		
		ObjectAdapter objectAdapter = openERPSession.getObjectAdapter(name);
		FieldCollection fields = objectAdapter.getFields();
		Map<String, OpenERPFieldInfo> fieldMaps = new HashMap<String, OpenERPFieldInfo>();
		for(com.debortoliwines.openerp.api.Field field : fields){
//			System.out.println("private "+field.getType().toString().toLowerCase()+" "+field.getName()+
//					";");
//			System.out.println("field.name:"+field.getName()+", field.getDescription:"+field.getDescription());
			String type = field.getType().name();
//			System.out.println(type);
			
			OpenERPFieldInfo info = new OpenERPFieldInfo();
			info.name = field.getName();
			info.type = type.toLowerCase();
			fieldMaps.put(field.getName(), info);
			
			//System.out.println(field.getType()+" "+field.getName());
			
			if("MANY2ONE".equalsIgnoreCase(type)){
				
//				System.out.println(type);
//				Object obj = field.getFieldProperty("domain");
//				System.out.println(obj);
				String relationTable = (String) field.getFieldProperty("relation");
				info.relationInfo.add(relationTable);
				
//				System.out.println(relationTable);
				
			}else if("ONE2MANY".equalsIgnoreCase(type)){
				String relationField = (String) field.getFieldProperty("relation_field");
				String relationTable = (String) field.getFieldProperty("relation");
				
				info.relationInfo.add(relationTable);
				info.relationInfo.add(relationField);
				
//				System.out.println(relationField);
//				System.out.println(relationTable);
//				System.out.println("one 2 many end");
			}else if("MANY2MANY".equalsIgnoreCase(type)){
				//sometimes is null, if null, coutinue
				Object[] joinColumns = (Object[]) field.getFieldProperty("m2m_join_columns"); 
				String joinTable = (String) field.getFieldProperty("m2m_join_table");
				String relationTable = (String) field.getFieldProperty("relation");
				info.relationInfo.add(relationTable);
				info.relationInfo.add(joinTable);
				info.relationInfo.add(joinColumns);
				
				
//				System.out.println(joinTable);
//				System.out.println(Arrays.toString(joinColumns));
//				System.out.println(relationTable);
//				System.out.println("many 2 many end");
			}else {
				if("SELECTION".equals(field.getType().name())){
					info.typeValues = field.getSelectionOptions();
				}	
			}
		}
		
		return fieldMaps;
		
	}

	public Map<String, Map<String, String>> getSelectionValues(String table) throws XmlRpcException, OpeneERPApiException {

		ObjectAdapter objectAdapter = openERPSession.getObjectAdapter(table.replace("_", "."));
		FieldCollection fields = objectAdapter.getFields();
		Map<String, Map<String, String>> selections = new HashMap<String, Map<String, String>>();
		//System.out.println("get field success!");
		for (Field field : fields) {
			//System.out.println("field: "+field.getType()+" "+field.getName());
			if("selection".equalsIgnoreCase(field.getType().name())){
				Map<String, String> values = new HashMap<String, String>();
				
				ArrayList<SelectionOption> selectionOptions = field.getSelectionOptions();
				for (SelectionOption selectionOption : selectionOptions) {
					values.put(selectionOption.code, selectionOption.value);
				}
				selections.put(field.getName(), values);
			}
		}
		
		return selections;
	}

}
