package com.syml.ws;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.syml.domain.Lender;
import com.syml.service.CrudServices;
import com.syml.util.HibernateUtil;

@Path("/lender")
public class LenderAction {
	
	private CrudServices<Lender> crudServices = new CrudServices<Lender>(Lender.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Lender get(@PathParam("id") Integer id){
		//System.out.println(id);
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		crudServices.setHsession(session);
		Lender lender  = crudServices.get(id);
		
		tx.commit();
		return lender;
	}
	
	@Path("{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String create( @PathParam("id") Integer id){
		
		Session session = HibernateUtil.getSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			crudServices.setHsession(session);
			crudServices.save(id);
			tx.commit();
			return Boolean.TRUE.toString();
		}catch (Exception e) {
			e.printStackTrace();
			if(tx!=null)
				tx.rollback();
			return Boolean.FALSE.toString();
		}
		
	}
	
	@Path("{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String update( @PathParam("id") Integer id){
		
		//System.out.println(lender.getId());
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			crudServices.setHsession(session);
			crudServices.save(id);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(tx != null)
				tx.rollback();
			return Boolean.FALSE.toString();
		}
		
		return Boolean.TRUE.toString();
	}
	
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String del(@PathParam("id") Integer id){
		
		//System.out.println(id);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			crudServices.setHsession(session);
			crudServices.delete(id);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(tx != null)
				tx.rollback();
			return Boolean.FALSE.toString();
		}
		
		return Boolean.TRUE.toString();
		
		
		// Prior Cantor Code
//		//System.out.println(id);
//		Session session = HibernateUtil.getSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
////			BaseDAO<Lender,Integer> lenderDao = DAOFactory.getInstance().getLenderDAO(session);
////			Lender lender = lenderDao.findById(new Integer(id));
////			lenderDao.delete(lender);
//			
////			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			if(tx != null)
//				tx.rollback();
//			return Boolean.FALSE.toString();
//		}
//		
//		return Boolean.TRUE.toString();
	}
	
}
