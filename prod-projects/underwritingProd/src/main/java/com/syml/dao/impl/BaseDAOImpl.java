package com.syml.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.dao.BaseDAO;

public class BaseDAOImpl<T, PK extends Serializable> implements BaseDAO<T, PK>{
	 private static Logger log = LoggerFactory.getLogger(BaseDAOImpl.class);
		
	protected Class<?> type;
	protected Session session = null;

   public BaseDAOImpl(Class<?> class1,Session session) {
       this.type = class1;
       this.session = session;
   }
	
	@Override
	public void saveOrUpdate(T object) {
		if (object == null)
           log.debug("Trying to save or update null object of type " + type);
       log.debug("Saving/Updating to the object '" + object.toString() + "' of type " + type);
       
       session.saveOrUpdate(object);
       log.debug("Completeted successfully!");
       
	}



	@Override
	public void delete(T object) {
		log.debug("Deleting the object '" + object.toString() + "'");
		session.delete(object);
		log.debug("Completed successfully!");
	}

	@Override
	public T findById(PK id) {
		if(id==null)
			return null;
		T obj = (T) session.get(type, id);
		
		return obj;
	}

	@Override
	public List<T> findAll() {
		log.debug("Getting all the " + type.getName() + " objects");
		Query query = session.createQuery("from " + type.getName());
       query.setCacheable(true);
       //TODO
       //query.setMaxResults(2);
       @SuppressWarnings("unchecked")
       List<T> result = (List<T>) query.list();
       log.debug("Completed successfully! result list size: " + result.size());
       return result;
	}


	@Override
	public List<T> findByCriteria(List<Criterion> restrictionList) {

       log.debug("Finding objects by the following criteria: " + restrictionList.toString());
      
       Criteria criteria = session.createCriteria(type.getName());
       for (Criterion criterion : restrictionList) {
           criteria.add(criterion);
       }
       @SuppressWarnings("unchecked")
       List<T> result = (List<T>) criteria.list();
       
       return result;
       
	}
	
	public List findByHql(String hql,List<String> parameters){
		Query query = session.createQuery(hql);
		if(parameters!=null){
			for (int i=0;i<parameters.size();i++) {
				query.setParameter(i, parameters.get(i));
				
			}
		}
		return query.list();
	}

	@Override
	public List<Integer> findAllIds() {
		Query query = session.createQuery("select id from " + type.getName());
		query.setCacheable(true);
		List<Integer> result = (List<Integer>) query.list();
		return result;
	}
	
}
