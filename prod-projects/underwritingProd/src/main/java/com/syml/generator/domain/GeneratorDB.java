package com.syml.generator.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

import com.syml.constant.Constant;
import com.syml.domain.Applicant;
import com.syml.domain.BaseBean;
import com.syml.domain.Opportunity;
import com.syml.util.DataSynHelper;
import com.syml.util.OpenERPSessionUtil;

public class GeneratorDB {
	
	public void run(){
		System.out.println("Inside  RUN");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("underwrittingApp");
		System.out.println("EMF  Value"+emf.getProperties());
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		List<String> errors = new ArrayList<String>();
		
		DataSynHelper<BaseBean> helper = OpenERPSessionUtil.getOpenERPHelper();
		
		Collection<String> classes = Constant.getDomainClasses();
		
		//get data from openerp
		for (String clazzStr : classes) {
			
			System.err.println("start read:"+clazzStr);
			
			Class<? extends BaseBean> clazz = null;
			try {
				clazz = (Class<? extends BaseBean>) Class.forName(clazzStr);
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
			
			// I created the Product if Statement in an effort to only update the products on my local machine.  
			// This method worked well.  There are errors with some of the other classes.  In particular, applicant, dealnotes, etc. 
			// As long as the DB write is limited to Lenders and Products, things are clean.
			if(clazzStr.equals("com.syml.domain.Product") || clazzStr.equals("com.syml.domain.Lender")){
				List<BaseBean> datas;
				try {
					
					datas = helper.getObjectFromERP(clazz, null);
					for (BaseBean bean : datas) {
						try {
							System.out.println(bean);
							em.merge(bean);
						} catch (EntityNotFoundException e1){
							String emsg = e1.getLocalizedMessage().replace("Unable to find ", "");
							errors.add(clazz.getSimpleName()+" --> "+ emsg);
						}
					}
					em.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			// WAS 
//			if(clazzStr.equals("com.syml.domain.Product"))
//				continue;
//			
//			List<BaseBean> datas;
//			try {
//				
//				datas = helper.getObjectFromERP(clazz, null);
//				for (BaseBean bean : datas) {
//					try {
//						System.out.println(bean);
//						em.merge(bean);
//					} catch (EntityNotFoundException e1){
//						String emsg = e1.getLocalizedMessage().replace("Unable to find ", "");
//						errors.add(clazz.getSimpleName()+" --> "+ emsg);
//					}
//				}
//				em.flush();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		
		for (String string : errors) {
			System.out.println(string);
		}
		
		em.getTransaction().commit();
	}
	
	public void debug(){
		DataSynHelper<BaseBean> helper = OpenERPSessionUtil.getOpenERPHelper();
		try {
			List<BaseBean> datas = helper.getObjectFromERP(Opportunity.class, 3);
			System.out.println(datas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		GeneratorDB db = new GeneratorDB();
		db.run();
	}
	
}
