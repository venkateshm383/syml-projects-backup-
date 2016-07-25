//package com.syml.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.persistence.Entity;
//
//import com.syml.api.openerp.Constant;
//import com.syml.bean.BaseBean;
//import com.syml.domain.Account;
//import com.syml.domain.AccountFiscalPosition;
//import com.syml.domain.AccountPaymentTerm;
//import com.syml.domain.AccountPaymentTermLine;
//import com.syml.domain.AccountType;
//import com.syml.domain.BankType;
//import com.syml.domain.BankTypeField;
//import com.syml.domain.Company;
//import com.syml.domain.Country;
//import com.syml.domain.CountryState;
//import com.syml.domain.Currency;
//import com.syml.domain.CurrencyRate;
//import com.syml.domain.CurrencyRateType;
//import com.syml.domain.IrModel;
//import com.syml.domain.Lender;
//import com.syml.domain.LenderTitle;
//import com.syml.domain.MailAlias;
//import com.syml.domain.OpportunityCaseSection;
//import com.syml.domain.Product;
//import com.syml.domain.ProductPricelist;
//import com.syml.domain.ProductTemplate;
//import com.syml.domain.ProductUom;
//import com.syml.domain.ProductUomCateg;
//import com.syml.domain.StockJournal;
//import com.syml.domain.StockWarehouse;
//import com.syml.domain.Users;
//
//public class DBHelper {
//	
//	public static void main(String[] args) {
//		DBHelper helper = new DBHelper();
//		try {
//			
//			helper.equalsID();
//			
////			List beans = helper.getResults(helper.getConnection());
////			System.out.println(beans);
////			
////			EntityManagerFactory emf =
////				      Persistence.createEntityManagerFactory("underwrittingApp");
////			
////			EntityManager em = emf.createEntityManager();
////			
////			em.getTransaction().begin();
////			
////			for (Object obj : beans) {
////				em.merge(obj);
////			}
////			
////			em.getTransaction().commit();
//			
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}  catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}  catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	private List<Class<? extends BaseBean>> classes = Arrays.asList(
//			Currency.class,
//			BankType.class,
//			BankTypeField.class,
//			CurrencyRateType.class,
//			CurrencyRate.class,
//			Country.class,
//			CountryState.class,
//			ProductUomCateg.class,
//			ProductUom.class
//		);
//	
//	private String url = "jdbc:postgresql://localhost/openerp";
//	private String driver = "org.postgresql.Driver";
//	private String user ="postgres";
//	private String pwd = "123456";
//	
//	public Connection getConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
//		Class.forName(driver).newInstance();
//        Connection con = DriverManager.getConnection(url, user , pwd);
//		return con;
//	}
//	
//	public List<? extends BaseBean> getResults(Connection con) throws SQLException, ParseException{
//		List beans = new ArrayList();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		for (Class c : classes) {
//			if(c.getSimpleName().equals("Currency")){
//				
//				PreparedStatement ps = con.prepareStatement("select * from res_currency");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					Currency lenderCurrency = new Currency();
//					beans.add(lenderCurrency);
//					
//					lenderCurrency.setId(rs.getInt("id"));
//					lenderCurrency.setSymbol(rs.getString("symbol"));
//					lenderCurrency.setBase(rs.getBoolean("base"));
//					lenderCurrency.setActive(rs.getBoolean("active"));
//					lenderCurrency.setRounding(rs.getDouble("rounding"));
//					
//					String date = rs.getString("date");
//					if(date!=null)
//						lenderCurrency.setDate(sdf.parse(date));
//						
//					lenderCurrency.setAccuracy(rs.getInt("accuracy"));
//					lenderCurrency.setPosition(rs.getString("position"));
//					
//				}
//				
//			}else if(c.getSimpleName().equals("BankTypeField")){
//				PreparedStatement ps = con.prepareStatement("select * from res_partner_bank_type_field");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					BankTypeField lbtf = new BankTypeField();
//					beans.add(lbtf);
//					
//					lbtf.setId(rs.getInt("id"));
//					lbtf.setName(rs.getString("name"));
//					lbtf.setReadonly(rs.getBoolean("readonly"));
//					lbtf.setRequired(rs.getBoolean("required"));
//					lbtf.setSize(rs.getInt("size"));
//					
//					int bankTypeId = rs.getInt("bank_type_id");
//					
//					if(bankTypeId > 0){
//						BankType bankType = new BankType();
//						bankType.setId(bankTypeId);
////						lbtf.setBankType(bankType);
//					}
//					
//				}
//			}else if(c.getSimpleName().equals("BankType")){
//				PreparedStatement ps = con.prepareStatement("select * from res_partner_bank_type");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					BankType lbt = new BankType();
//					beans.add(lbt);
//					
//					lbt.setId(rs.getInt("id"));
//					lbt.setName(rs.getString("name"));
//					lbt.setCode(rs.getString("code"));
//					lbt.setFormatLayout(rs.getString("format_layout"));
//					
//				}
//			}else if(c.getSimpleName().equals("CurrencyRateType")){
//				PreparedStatement ps = con.prepareStatement("select * from res_currency_rate_type");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					CurrencyRateType lcrt = new CurrencyRateType();
//					beans.add(lcrt);
//					
//					lcrt.setId(rs.getInt("id"));
//					lcrt.setName(rs.getString("name"));
//					
//				}
//			}else if(c.getSimpleName().equals("CurrencyRate")){
//				PreparedStatement ps = con.prepareStatement("select * from res_currency_rate");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					CurrencyRate lcr = new CurrencyRate();
//					beans.add(lcr);
//					
//					lcr.setId(rs.getInt("id"));
//					
//					String name = rs.getString("name");
//					if(name!=null)
//						lcr.setName(sdf.parse(name));
//					
//					lcr.setRate(rs.getDouble("rate"));
//					
//					int rateTypeId = rs.getInt("currency_rate_type_id");
//					if(rateTypeId>0){
//						CurrencyRateType rateType = new CurrencyRateType();
//						rateType.setId(rateTypeId);
////						lcr.setRateType(rateType);
//					}
//					
//					int currencyId = rs.getInt("currency_id");
//					if(currencyId>0){
//						Currency currency = new Currency();
//						currency.setId(currencyId);
////						lcr.setCurrency(currency);
//					}
//					
//				}
//			}else if(c.getSimpleName().equals("Country")){
//				PreparedStatement ps = con.prepareStatement("select * from res_country");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					Country lc = new Country();
//					beans.add(lc);
//					
//					lc.setId(rs.getInt("id"));
//					lc.setName(rs.getString("name"));
//					lc.setCode(rs.getString("code"));
//					lc.setAddressFormat(rs.getString("address_format"));
//					
//					int currencyId = rs.getInt("currency_id");
//					if(currencyId>0){
//						Currency currency = new Currency();
//						currency.setId(currencyId);
////						lc.setLenderCurrency(currency);
//					}
//					
//				}
//			}else if(c.getSimpleName().equals("CountryState")){
//				PreparedStatement ps = con.prepareStatement("select * from res_country_state");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					CountryState lcs = new CountryState();
//					beans.add(lcs);
//					
//					lcs.setId(rs.getInt("id"));
//					lcs.setName(rs.getString("name"));
//					lcs.setCode(rs.getString("code"));
//					
//					int countryId = rs.getInt("country_id");
//					if(countryId>0){
//						Country country = new Country();
//						country.setId(countryId);
////						lcs.setCountry(country);
//					}
//					
//				}
//			}else if(c.getSimpleName().equals("ProductUomCateg")){
//				PreparedStatement ps = con.prepareStatement("select * from product_uom_categ");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					ProductUomCateg puc = new ProductUomCateg();
//					beans.add(puc);
//					
//					puc.setId(rs.getInt("id"));
//					puc.setName(rs.getString("name"));
//				}
//			}else if(c.getSimpleName().equals("ProductUom")){
//				PreparedStatement ps = con.prepareStatement("select * from product_uom");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					ProductUom pu = new ProductUom();
//					beans.add(pu);
//					
//					pu.setId(rs.getInt("id"));
//					pu.setActive(rs.getBoolean("active"));
//					pu.setFactor(rs.getDouble("factor"));
//					pu.setName(rs.getString("name"));
//					pu.setRounding(rs.getDouble("rounding"));
//					pu.setUomType(rs.getString("uom_type"));
//					
//					int categoryId = rs.getInt("category_id");
//					if(categoryId>0){
//						ProductUomCateg category = new ProductUomCateg();
//						category.setId(categoryId);
////						pu.setCategory(category);
//					}
//				}
//			}
//		}
//		
//		return beans;
//	}
//	
//	public void equalsID() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
//		Class.forName(driver).newInstance();
//        Connection con = DriverManager.getConnection(url, user , pwd);
//		
//        Connection con1 = DriverManager.getConnection("jdbc:postgresql://localhost/underwriting", user , pwd);
//		
//        List<Class<? extends BaseBean>> varClasses = Constant.getDomainClassList();
//		for (Class<? extends BaseBean> clazz : varClasses) {
//			
//			Entity an = clazz.getAnnotation(Entity.class);
//			String tableName = an.name();
//			
//			PreparedStatement ps = con.prepareStatement("select id from " + tableName);
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()){
//				int id = rs.getInt("id");
//				PreparedStatement ps1 = con1.prepareStatement("select id from "+ tableName +" where id="+id);
//				ResultSet rs1 = ps1.executeQuery();
//				if(!rs1.next())
//					System.err.println("table name :"+tableName+"("+clazz.getSimpleName()+"), openERP id = "+id+", can not find in underwritinApp db");
//				
//			}
//		}
//	}
//	
//	
//}
