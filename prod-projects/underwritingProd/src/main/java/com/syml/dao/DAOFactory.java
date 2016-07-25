package com.syml.dao;

import org.hibernate.Session;

import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Lender;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;


public class DAOFactory {
	
	private static DAOFactory instance= null;
	
	private DAOFactory()
	{
		
	}
	
	public static DAOFactory getInstance()
	{
		if(instance == null)
			instance = new DAOFactory();
		return instance;
	}
	
	public BaseDAO<Product, Integer> getProductDAO(Session session)
	{
		return new BaseDAOImpl<Product, Integer>(Product.class, session);
	}
	
	public BaseDAO<Lender, Integer> getLenderDAO(Session session){
		return new BaseDAOImpl<Lender, Integer>(Lender.class, session);
	}

	public BaseDAO<Opportunity, Integer> getOpportunityDAO(Session session){
		return new BaseDAOImpl<Opportunity, Integer>(Opportunity.class, session);
	}
	
}
