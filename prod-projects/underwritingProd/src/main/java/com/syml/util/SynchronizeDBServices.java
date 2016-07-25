package com.syml.util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.syml.domain.BaseBean;

public class SynchronizeDBServices {
	@SuppressWarnings("unchecked")
	public Boolean runSynDb(){
		
		//db list
		List<Class<? extends BaseBean>> classes = null;
//		List<Class<? extends BaseBean>> classes = Arrays.asList(
//				AccountFiscalPosition.class,
//				AccountPaymentTerm.class,
//				AccountPaymentTermLine.class,
//				AccountType.class,
//				IrModel.class,
//				LenderBankType.class,
//				LenderBankTypeField.class,
//				LenderCompany.class,
//				LenderCountry.class,
//				//LenderCountryState.class,
//				LenderCurrency.class,
//				LenderCurrencyRate.class,
//				LenderTitle.class,
//				MailAlias.class,
//				OpportunityCaseSection.class,
//				PrePaymentOption.class,
//				ProductCompensation.class,
//				ProductPricelist.class,
//				ProductRateType.class,
//				ProductTemplate.class,
//				ProductTerm.class,
//				ProductUom.class,
//				ProductUomCateg.class,
//				StockJournal.class,
//				StockLocation.class,
//				StockWarehouse.class,
//				LenderUsers.class,
//				Account.class,
//				Lender.class,
//				Product.class
//				);
		
		DataSynHelper<BaseBean> helper = OpenERPSessionUtil.getOpenERPHelper();
		
		Session hibSession = HibernateUtil.getSession();
		Transaction tx = hibSession.beginTransaction();
		try {
			
			for (Class<? extends BaseBean> clazz : classes) {
				
				System.err.println("start read:"+clazz.getName());
				
				List<BaseBean> entities = helper.getObjectFromERP( clazz, null);
				for (BaseBean bean : entities) {
					hibSession.saveOrUpdate(bean);
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public <T extends BaseBean> void synchronizeObject(Class<? extends BaseBean> t, Integer id,
			org.hibernate.Session session) throws Exception{
		
		DataSynHelper<BaseBean> helper = OpenERPSessionUtil.getOpenERPHelper();
		List<BaseBean> beans = helper.getObjectFromERP(t, id);
		if(beans!=null && beans.size()>0){
			session.saveOrUpdate(beans.get(0));
		}
		
	}
	
}
