package com.syml.util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.syml.constant.Constant;
import com.syml.domain.BaseBean;

public class SynchronizeDB {
	public Boolean runSynDb(){
		
		//db list
		List<Class<? extends BaseBean>> classes = Constant.getDomainClassList();
		
		DataSynHelper<BaseBean> helper = OpenERPSessionUtil.getOpenERPHelper();
		
		Session hibSession = HibernateUtil.getSession();
		Transaction tx = hibSession.beginTransaction();
		try {
			
			for (Class<? extends BaseBean> clazz : classes) {
				
				System.err.println("start read:"+clazz.getName());
				
				List<BaseBean> entities = helper.getObjectFromERP( clazz, null);
				for (BaseBean bean : entities) {
					hibSession.merge(bean);
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
	
	public static void main(String[] args)
	{
		SynchronizeDB db = new SynchronizeDB();
		db.runSynDb();
	}
}
