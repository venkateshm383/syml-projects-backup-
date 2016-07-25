package com.syml.mortgageapplication.impl;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import play.Logger;
import play.data.DynamicForm;
import controllers.Opportunity;

public class MortgageApplicationPageThreeService {
	String formType = "Mortgage Application";
	String subForm = "Mortgage Application 3";
	
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	
	String propertyHeat =null;
	String propertyGetWater=null;
	String buildingType = null;
	String propertyStyle =null;
	String typeGarage = null;
	String propertyDisposeWater = null;
	String squareFootage = null;
	
	/**
	 * To Map page 3 values to opportunity pojo
	 * @param opportunity
	 * @param dynamicForm
	 * @return Opportunity
	 */ 
	public Opportunity loadFormData(Opportunity opportunity,DynamicForm dynamicForm){
		propertyHeat = dynamicForm.get("propertyHeat");
		propertyGetWater = dynamicForm.get("propertyGetWater");
		buildingType = dynamicForm.get("buildingType");
		propertyStyle = dynamicForm.get("propertyStyle");
		typeGarage = dynamicForm.get("typeGarage");
		propertyDisposeWater = dynamicForm.get("propertyDisposeWater");
		squareFootage = dynamicForm.get("squareFt");
		
		opportunity.setHeating(getPropertyHeated(propertyHeat));
		opportunity.setWater(getWater(propertyGetWater));
		opportunity.setProperty_type(getTypeOfBuildingForPG(buildingType));
		opportunity.setProperty_style(getPropertyStyleForPG(propertyStyle));
		opportunity.setGarage_type(getGarageType(typeGarage));
		opportunity.setSewage(getSewage(propertyDisposeWater));
		opportunity.setPogressStatus(30);
		
		opportunity.setPropertyHeated(propertyHeat);
		opportunity.setPropertyWater(propertyGetWater);
		opportunity.setPropertyType(buildingType);
		opportunity.setPropertyStyle(propertyStyle);
		opportunity.setGarageType(typeGarage);
		opportunity.setSquareFootage(squareFootage);
		opportunity.setPropertySewage(propertyDisposeWater);
		
		String garagetype = opportunity.getGarage_type();
		if (typeGarage != null && !typeGarage.equalsIgnoreCase("None")) {
			String garazeSize = dynamicForm.get("garageSize");
			opportunity.setGarage_size(getGarageSize(garazeSize));
			opportunity.setGarageSize(garazeSize);
		}
		Logger.debug("Garagetype:" + garagetype);
		int squreFootAgeInt = 0;
		try {
			squreFootAgeInt = Integer.parseInt(squareFootage);
			opportunity.setSquare_footage(squreFootAgeInt);

		} catch (NumberFormatException  e) {
			Logger.error("error in parsing squre footageStringValue  to integer for Given sqaurefootage= " + squareFootage,e);
		}
		
		return opportunity;
	}
	
	/**
	 * To Update opportunity with new details In DB and Update couchbase with Opporunity Pojo
	 * @param opportunity
	 * @return Opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity updateOpportunity(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		try {
			posService.updateOpportunityPage3(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating opporunity Details Into Db ",e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating Opportunity Details Into Couchbase ",e);

		}
		return opportunity;
		
	}
	
	/**
	 * To get TypeOfBuilding Value based on the given Form Input value 
	 * @param typeofbuilding
	 * @return String
	 */
	
	private String getTypeOfBuildingForPG(String typeofbuilding) {

		if (typeofbuilding != null && typeofbuilding.equalsIgnoreCase("House")) {
			return "1";
		} else if (typeofbuilding != null
				&& typeofbuilding.equalsIgnoreCase("Duplex")) {
			return "2";
		} else if (typeofbuilding != null
				&& typeofbuilding.equalsIgnoreCase("Appartment Condo")) {
			return "4";
		} else if (typeofbuilding != null
				&& typeofbuilding.equalsIgnoreCase("Townhouse")
				|| typeofbuilding.equalsIgnoreCase("Rowhouse")) {
			return "5";
		} else if (typeofbuilding != null
				&& typeofbuilding.equalsIgnoreCase("Mobile Home")
				|| typeofbuilding.equalsIgnoreCase("Modular Home")) {
			return "6";
		} else
			return "7";
	}
	
	/**
	 * To get PropertyStyle of OpenERP value based on the given form Input Value 
	 * @param propertyStyle
	 * @return String
	 */
	/**FOR PAGE3 PROPERTY STYLE TO INSERT INTO POSTGRESS*/
	private String getPropertyStyleForPG(String propertyStyle) {
		if (propertyStyle != null && propertyStyle.equalsIgnoreCase("Bunglow") || propertyStyle.equalsIgnoreCase("One Story")) {
			return "1";
		} else if (propertyStyle != null && propertyStyle.equalsIgnoreCase("BiLevel")) {
			return "2";
		}else if (propertyStyle != null && propertyStyle.equalsIgnoreCase("Two Story")) {
			return "3";
		}else if (propertyStyle != null && propertyStyle.equalsIgnoreCase("Split Level")) {
			return "4";
		}else if (propertyStyle != null && propertyStyle.equalsIgnoreCase("Story and A Half")) {
			return "5";
		}else if (propertyStyle != null && propertyStyle.equalsIgnoreCase("Three Story")) {
			return "6";
		}else
		return "7";
	}
	
	/**
	 * To get propertyHeated openERP value based on the given Form Input value 
	 * @param propertyheated
	 * @return String
	 */
	/**For Page3 Property Heated to insert into postgress*/
	private String getPropertyHeated(String propertyheated){
		if (propertyheated != null && propertyheated.equalsIgnoreCase("Forced Air Gas/Oil")) {
			return "1";
		} else if (propertyheated != null && propertyheated.equalsIgnoreCase("Electric baseboard")) {
			return "2";
		}else if (propertyheated != null && propertyheated.equalsIgnoreCase("Hot water heating") || propertyheated.equalsIgnoreCase("In Floor")) {
			return "3";
		}else
		return "4";
	}
	
	/**
	 * To get Water  OpenERP value based on given form input value 
	 * @param getWater
	 * @return String
	 */
	/**For Page3 Water to insert into postgress*/
	public String getWater(String getWater) {
		if (getWater != null && getWater.equalsIgnoreCase("Municipal")) {
			return "1";
		} else if (getWater != null
				&& getWater.equalsIgnoreCase("Well")
				|| getWater.equalsIgnoreCase("Shared Well")) {
			return "2";
		} else
			return "3";
	}
	
	
	/**
	 * To get sewagWater openErp value based on the given form Input Value 
	 * @param sewageWater
	 * @return String
	 */
	/**For Page3 Sewage Water to insert into postgress*/
	private String getSewage(String sewageWater) {
		if (sewageWater != null && sewageWater.equalsIgnoreCase("Municipality")) {
			return "1";
		} else if (sewageWater != null
				&& sewageWater.equalsIgnoreCase("Septic System")) {
			return "2";
		} else if (sewageWater != null
				&& sewageWater.equalsIgnoreCase("Holding Tank")) {
			return "3";
		} else
			return "4";
	}
	
	
	/**
	 * To get GarageType openERP value based on given form input value 
	 * @param garageType
	 * @return STring
	 */
	/**For Page3  GarageType to insert into postgress*/
	private String getGarageType(String garageType) {
		if (garageType != null && garageType.equalsIgnoreCase("Attached")) {
			return "1";
		} else if (garageType != null
				&& garageType.equalsIgnoreCase("Detached")) {
			return "2";
		} else
			return "3";
	}
	
	/**
	 * To get GarageSize OpenErp value based on the given form Input value 
	 * @param garageSize
	 * @return String 
	 */
	/**For Page3  Garage Size to insert into postgress*/
	private String getGarageSize(String garageSize) {
		if (garageSize != null && garageSize.equalsIgnoreCase("Single")) {
			return "1";
		} else if (garageSize != null
				&& garageSize.equalsIgnoreCase("Double")) {
			return "2";
		}else if (garageSize != null
				&& garageSize.equalsIgnoreCase("Triple")) {
			return "3";
		}else
			return "4";
	}
}
