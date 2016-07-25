package com.syml.bean.algo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;

public class InsurerProductsAlgo {
	
	public Opportunity clientOpportunity;
    private Session session;
    public List<AlgoNote> dealNotes;
	
	public InsurerProductsAlgo(Opportunity clientOpportunity1, Session session) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException
    {
    	this.session = session;
    	this.clientOpportunity = clientOpportunity1;
        dealNotes = new ArrayList<AlgoNote>();
        
        List<AlgoProduct> productsToConsider = getAllProductsByProvince(clientOpportunity.province);
        
    }

	public List<AlgoProduct> getAllProductsByProvince(String province)
    {
    	// TODO Add Restriction where .active == true ... 
		// TODO Add Restriction for .Like "Insurer" ... i.e. CMHC, GE, AIG  
    	
    	BaseDAO<Product, Integer> dao = new BaseDAOImpl<Product, Integer>(Product.class, session);
    	//Integer[] ids = new Integer[]{3,4,5,6};
    	List<Product> products = session.createCriteria(Product.class)
    			.add(Restrictions.like("allowProvinces", province, MatchMode.ANYWHERE))    		
    			//.add(Restrictions.in("id", ids))
    			.list();
    	
    	//List<Product> products = dao.findAll();
    	System.out.println("getAllProductsByProvince.province:" + province);
    	System.out.println("getAllProductsByProvince:" + products.size());
    	
    	List<AlgoProduct> algoProducts = new ArrayList<AlgoProduct>();
    	for (Product product : products) {
			AlgoProduct algo = new AlgoProduct(product);
			algoProducts.add(algo);
			//product.goodCreditBeaconSplit = 0;
			product.locQualifyingRate = product.locQualifyingRate == null ? "" : product.locQualifyingRate;
			algo.whoseBeaconUsed = AlgoProduct.WhoseIncomeUsed.Highest;
//			product.exclOrInclCity = "Exclude";
//			product.volBonus1Threshold = 1.0;
//			product.applicationMethod = "";
//			product.equifaxScoringUsed = "Beacon5";
			algo.allowableBlackFlagGoodCredit = 10000;
		}
    	
    	return algoProducts;
    }
}
