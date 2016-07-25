/**
 * 
 */
package com.syml.ws;

import java.io.PrintWriter;
import java.io.StringWriter;

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
import com.syml.domain.Product;
import com.syml.service.CrudServices;
import com.syml.util.HibernateUtil;
import com.syml.util.SynchronizeDBServices;

/**
 * @author Administrator
 *
 */

@Path ("/product")
public class ProductAction {
	
	private CrudServices<Product> crudServices = new CrudServices<Product>(Product.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	/**
	 * This function will accept the product xml and create a new product entity, return the new entity id
	 * @param productXml
	 * @return
	 */
	@Path("{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String create(@PathParam("id") Integer id)
	{
		Session session = HibernateUtil.getSession();
		Transaction transaction = null;
		//String successString = null;
		try {
			transaction = session.beginTransaction();
			crudServices.setHsession(session);
			crudServices.save(id);
			transaction.commit();
			//successString = "Product ID " + id + " created successfully";
		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			e.printStackTrace(new PrintWriter(sw));
//			String exceptionAsString = sw.toString();
			
			//successString = "Product ID " + id + " failed Update - See Error: " + exceptionAsString;
			e.printStackTrace();
			if(transaction != null)
				transaction.rollback();
			return Boolean.FALSE.toString();
		}
		 return Boolean.TRUE.toString();
//		System.out.println(successString);
//		return successString;
	}
	
	/**
	 * This function will get the product object by id
	 * @param id
	 * @return
	 */
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Product get(@PathParam("id") Integer id)
	{
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		crudServices.setHsession(session);
		Product product  = crudServices.get(id);
		
		tx.commit();
		return product;
	}
	
	/**
	 * This function will update the product and return the status
	 * @param jaxbProduct
	 * @return
	 */
	@Path("{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String update(@PathParam("id") Integer id)
	{
		//System.out.println(product.getDescription());
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		//String successString = null;
		try {
			tx = session.beginTransaction();
			
			crudServices.setHsession(session);
			crudServices.update(id);
			
			tx.commit();
			//successString = "Product ID " + id + " updated successfully";
		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			e.printStackTrace(new PrintWriter(sw));
//			String exceptionAsString = sw.toString();
//			
//			successString = "Product ID " + id + " failed Update - See Error: " + exceptionAsString;
			e.printStackTrace();
			if(tx != null)
				tx.rollback();
			return Boolean.FALSE.toString();
		}
		return Boolean.TRUE.toString();
//		System.out.println(successString);
//		return successString;
	}
	
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String del(@PathParam("id") Integer id)
	{
		System.out.println(id);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		//String successString = null;
		try {
			tx = session.beginTransaction();
			
			crudServices.setHsession(session);
			crudServices.delete(id);
			
			tx.commit();
			//successString = "Product ID " + id + " deleted successfully";
		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			e.printStackTrace(new PrintWriter(sw));
//			String exceptionAsString = sw.toString();
//			
//			successString = "Product ID " + id + " failed Update - See Error: " + exceptionAsString;
			e.printStackTrace();
			if(tx != null)
				tx.rollback();
			return Boolean.FALSE.toString();
		}
//		System.out.println(successString);
//		return successString;
		return Boolean.TRUE.toString();
			
		
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
//			
//			BaseDAO<Product, Integer> productDAO = DAOFactory.getInstance().getProductDAO(session);
//			Product product = new Product();
//			product.setId(new Integer(id));
//			productDAO.delete(product);
//			
//			tx.commit();
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
