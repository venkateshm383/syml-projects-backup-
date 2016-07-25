package com.syml.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.BaseBean;
import com.syml.util.DataSynHelper;
import com.syml.util.OpenERPSessionUtil;

public class CrudServices<E extends BaseBean> {
	
	Session hsession = null;
	DataSynHelper<BaseBean> openerpCollection = null;
	private BaseDAO<E, Serializable> dao = null;
	private  Class<E> entity = null;
	
	public CrudServices(Class<E> entity){
		this.entity = entity;
		openerpCollection = OpenERPSessionUtil.getOpenERPHelper();
	}
	
	public CrudServices(Class<E> entity, boolean isLocal){
		this.entity = entity;
		if(!isLocal)
			openerpCollection = OpenERPSessionUtil.getOpenERPHelper();
	}
	
	public CrudServices(Session hibernateSession, Class<E> entity){
		this(entity);
		this.hsession = hibernateSession;
	}
	
	public E get(Integer id){
		return dao.findById(id);
	}
	
	public E getOpenERPObject(Integer id) throws Exception{
		List<BaseBean> list = openerpCollection.getObjectFromERP(entity, id);
		if(list==null || list.size()==0)
			throw new Exception("open ERP result is null or size is 0 of id="+id);
		
		E entity = (E) list.get(0);
		
		return entity;
	}
	
	public E update(Integer id) throws Exception{
		
		List<BaseBean> list = openerpCollection.getObjectFromERP(entity, id);
		if(list==null || list.size()==0)
			throw new Exception("open ERP result is null or size is 0 of id="+id);
		
		E entity = (E) list.get(0);
		
		hsession.merge(entity);

		return entity;
		
	}

	public E save(Integer id) throws Exception{
		
		
		List<BaseBean> list = openerpCollection.getObjectFromERP(entity, id);
		if(list==null || list.size()==0)
			throw new Exception("open ERP result is null or size is 0 of id="+id);
		
 		E entity = (E) list.get(0);
		
		hsession.merge(entity);

		return entity;
		
	}
	
	public E delete(Integer id) throws Exception{
		
		
		List<BaseBean> list = openerpCollection.getObjectFromERP(entity, id);
		if(list==null || list.size()==0)
			throw new Exception("open ERP result is null or size is 0 of id="+id);
		
 		E entity = (E) list.remove(0);
		
		hsession.delete(entity);

		return entity;
		
	}
	
	public void setHsession(Session hsession) {
		this.hsession = hsession;
		dao = new BaseDAOImpl<E, Serializable>(entity, hsession);
	}
	
}
