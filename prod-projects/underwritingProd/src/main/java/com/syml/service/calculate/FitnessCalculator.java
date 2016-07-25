package com.syml.service.calculate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Lender;
import com.syml.domain.Opportunity;
import com.syml.util.HibernateUtil;

public class FitnessCalculator implements ICalculate{
	
	private Integer oppId;
	private Integer top;
	
	public FitnessCalculator(Integer oppId,Integer top){
		this.oppId = oppId;
		this.top = top;
	}
	
	@Override
	public List<LenderFitnessScore> start() {
		List<LenderFitnessScore> returnLenders = new ArrayList<LenderFitnessScore>();
		List<LenderFitnessScore> results = new ArrayList<LenderFitnessScore>();
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			BaseDAO<Opportunity, Integer> oppDao = new BaseDAOImpl<Opportunity, Integer>(Opportunity.class, session);
			Opportunity opp = oppDao.findById(oppId);
			
			//filter some lender by oppId
			List<Lender> lenders = filter(session,opp);
			
			//calculate point by oppId
			//return point and lender
			for (Lender lender : lenders) {
				LenderFitnessScore returnLender = new LenderFitnessScore();
				returnLenders.add(returnLender);
				
				CalculateQueue.getInstance().execute(new FitnessCalculationTask(lender,returnLender,opp));
				
			}
			
			//check working done
			while(true){
				
				boolean isdone = false;
				
				for (LenderFitnessScore lender : returnLenders) {
					isdone = lender.isDone();
					if(!lender.isDone())
						break;
				}
				
				if(!isdone)
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				else
					break;
				
			}
			
			
			//lender order by points
			Collections.sort(returnLenders);
			System.out.println("returnLenders " + returnLenders.size());
			
			if(returnLenders.size()>top){
				for(int i=0;i<top;i++)
					results.add(returnLenders.get(i));
				return results;
			}else 
				return returnLenders;
			
		} catch (Exception e) {
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Lender> filter(Session session, Opportunity opp){
		
		
		//query lenders by condition
		BaseDAO<Lender, Integer> lenderDao = new BaseDAOImpl<Lender, Integer>(Lender.class, session);
		
		
		return lenderDao.findAll();
		
	};
	
	
}
