package com.syml.service;

import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.syml.bean.algo.UnderwritePostSel;
import com.syml.domain.Applicant;
import com.syml.domain.Opportunity;
import com.syml.morweb.AddressList;
import com.syml.morweb.AssetList;
import com.syml.morweb.CommonData;
import com.syml.morweb.CreditBureauList;
import com.syml.morweb.LiabilityList;
import com.syml.morweb.ObjectFactory;
import com.syml.morweb.TypeCreditBureau;
import com.syml.morweb.TypeCreditReportEquifax;
import com.syml.morweb.TypeCreditReportProvider;

import static com.syml.service.MorwebServiceUtil.*;

public class MorwebCommonDataVO {

	private final UnderwritePostSel underwrite;
	private final ObjectFactory objectFactory;

	public MorwebCommonDataVO(UnderwritePostSel underwrite) {
		this.underwrite = underwrite;
		this.objectFactory = new ObjectFactory();
	}


	public CommonData createCommonData(final MorwebCustomerDataVO customerDataVO, final MorwebMortgageApplicationVO mortgageApplicationVO) {
		final CommonData commonData = this.objectFactory.createCommonData();

		final Opportunity opportunity = this.underwrite.clientOpportunity;
		final List<Applicant> applicants = opportunity.getApplicants();

		final AddressList addressList = this.objectFactory.createAddressList();
		addressList.getApplicationAddress().addAll(customerDataVO.getCustomerAddressesAsJaxbElement());
		addressList.getApplicationAddress().add(mortgageApplicationVO.getSubjectPropertyAddressAsJaxbElement());
		commonData.setAddressList(addressList);

		final AssetList assetList = this.objectFactory.createAssetList();
		assetList.getAsset().addAll(customerDataVO.getCustomerAssetRealEstates());
		assetList.getAsset().addAll(customerDataVO.getCustomerAssetOthers());
		commonData.setAssetList(assetList);

		final LiabilityList liabilityList = this.objectFactory.createLiabilityList();
		liabilityList.getLiability().addAll(customerDataVO.getCustomerLiabilityRealEstates());
		liabilityList.getLiability().addAll(customerDataVO.getCustomerLiabilityOthers());
		commonData.setLiabilityList(liabilityList);

		commonData.setCreditBureauList(this.createCreditBureauList(applicants)); // FIXME Some fields not mapped correctly

		return commonData;
	}


	protected CreditBureauList createCreditBureauList(List<Applicant> applicants) {
		final CreditBureauList creditBureauList = this.objectFactory.createCreditBureauList();

		for (final Applicant applicant : applicants) {
			final Integer applicantId = applicant.getId();
			final Integer creditBureauId = applicants.indexOf(applicant);

			final TypeCreditReportEquifax creditReport = this.objectFactory.createTypeCreditReportEquifax();
			creditReport.setValue(applicant.getCreditReport());
			creditReport.setReportDate(createLocalMorwebDate(new Date())); // FIXME: This is incorrect.
			final JAXBElement<TypeCreditReportEquifax> creditReportJaxbElement = this.objectFactory.createCreditReportEquifax(creditReport);

			final TypeCreditBureau creditBureau = this.objectFactory.createTypeCreditBureau();
			creditBureau.setKey("APPL-" + applicantId + "_CRB-" + creditBureauId);
			creditBureau.setProvider(TypeCreditReportProvider.EQUIFAX);
			creditBureau.setCreditReport(creditReportJaxbElement);

			creditBureauList.getCreditBureau().add(creditBureau);
		}
		return creditBureauList;
	}
}
