package com.syml.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;

import com.syml.bean.algo.UnderwritePostSel;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.morweb.MortgageApplication;
import com.syml.morweb.ObjectFactory;
import com.syml.morweb.SubjectProperty;
import com.syml.morweb.SubjectPropertyList;
import com.syml.morweb.TypeApplicationAddress;

import static com.syml.service.MorwebServiceUtil.*;
import static com.syml.util.StringUtil.*;

public class MorwebMortgageApplicationVO {

	private final ObjectFactory objectFactory;
	private final UnderwritePostSel underwrite;

	private TypeApplicationAddress subjectPropertyAddress;

	public MorwebMortgageApplicationVO(UnderwritePostSel underwrite) {
		this.objectFactory = new ObjectFactory();
		this.underwrite = underwrite;
	}

	public final MortgageApplication createMortgageApplication() {
		final MortgageApplication mortgageApplication = this.objectFactory.createMortgageApplication();
		mortgageApplication.setSubjectPropertyList( createSubjectPropertyList(this.underwrite) );
		return mortgageApplication;
	}

	protected SubjectPropertyList createSubjectPropertyList(UnderwritePostSel underwrite) {
		final SubjectPropertyList subjectPropertyList = this.objectFactory.createSubjectPropertyList();
		final Opportunity clientOpportunity = underwrite.clientOpportunity;
		final Product potentialProduct = underwrite.potentialProduct;

		final SubjectProperty subjectProperty = this.objectFactory.createSubjectProperty();
		subjectProperty.setCondo( createCondo(clientOpportunity, this.objectFactory) );
		subjectProperty.setLegalAddress( createLegalAddress(clientOpportunity, this.objectFactory) );
		subjectProperty.setNumberOfUnitsTotal(1);
		subjectProperty.setOccupancyPurpose( createOccupancyPurpose(clientOpportunity.getLivingInProperty()) );

		subjectProperty.setSubjectPropertyOccupancyOwnerOccupied( createSubjectPropertyOccupancyOwnerOccupied(clientOpportunity, this.objectFactory) );
		subjectProperty.setSubjectPropertyOccupancyPartialOwnerOccupied( createSubjectPropertyOccupancyPartialOwnerOccupied(clientOpportunity, this.objectFactory) );

		subjectProperty.setPropertyTax( createPropertyTax(clientOpportunity, this.objectFactory) );
		subjectProperty.setPropertyType( createPropertyType(clientOpportunity.getPropertyStyle()) );
		subjectProperty.setPropertyDescriptionType( createPropertyDescriptionType(clientOpportunity.getPropertyType()) );
		subjectProperty.setHeatingType( createHeatingType(clientOpportunity.getHeating()) );
		subjectProperty.setWaterSupplyType( createWaterSupplyType(clientOpportunity.getWater()) );
		subjectProperty.setWaterWasteType( createWaterWasteType(clientOpportunity.getSewage()) );
		subjectProperty.setParkingType( createParkingType(clientOpportunity.getGarageSize(), clientOpportunity.getGarageType()) );

		final String buildingFunds = clientOpportunity.getBuildingFunds();
		subjectProperty.setSelfBuildIndicator( isNullOrEmpty(buildingFunds) ? false : (buildingFunds.contains("3") ? true : false) );

		final Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, clientOpportunity.getAge());
		subjectProperty.setYearBuilt( createLocalMorwebDate(calendar.getTime()) );

		if (potentialProduct.getMinHeatCost() != null) {
			subjectProperty.setAnnualHeatingAmount(BigDecimal.valueOf(potentialProduct.getMinHeatCost() * 12));
		}

		if ( isNullOrEmpty(clientOpportunity.getMls()) ) {
			subjectProperty.setMLSListed(true);
			subjectProperty.setMLSNumber(clientOpportunity.getMls());
		}

		if (clientOpportunity.getSquareFootage() != null) {
			subjectProperty.setPropertySize(clientOpportunity.getSquareFootage());
			subjectProperty.setPropertySizeUnits("Feet");
		}

		final SubjectPropertyAddressDTO subjectPropertyDTO = createSubjectPropertyAddressDTO(clientOpportunity, this.objectFactory);
		subjectProperty.setSubjectPropertyAddress(subjectPropertyDTO.getSubjectPropertyAddress());
		this.subjectPropertyAddress = subjectPropertyDTO.getTypeApplicationAddress();

		subjectPropertyList.setSubjectProperty(subjectProperty);
		return subjectPropertyList;
	}


	public JAXBElement<? extends TypeApplicationAddress> getSubjectPropertyAddressAsJaxbElement() {
		JAXBElement<TypeApplicationAddress> subjectPropertyElement = this.objectFactory.createApplicationAddress(this.subjectPropertyAddress);
		return subjectPropertyElement;
	}

}
