package com.syml.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface BaseDAO<T, PK extends Serializable> {

	void saveOrUpdate(T object);

	void delete(T object);

	T findById(PK id);

	List<T> findAll();
	
    List<T> findByCriteria(List<Criterion> restrictionList);

    List findByHql(String hql,List<String> parameter);
    
    public List<Integer> findAllIds();
    
}
