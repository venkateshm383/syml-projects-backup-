package com.syml.service;


import com.syml.bean.algo.UnderwritePostSel;
import com.syml.morweb.BDIRequest;
import com.syml.morweb.CommonData;
import com.syml.morweb.CustomerData;
import com.syml.morweb.MortgageApplication;
import com.syml.morweb.ObjectFactory;

/**
 * Hold morweb's object creator value such as {@link MorwebCommonDataVO}, 
 * {@link MorwebCustomerDataVO}, and {@link MorwebMortgageApplicationVO}.
 * @see MorwebCommonDataVO
 * @see MorwebCustomerDataVO
 * @see MorwebMortgageApplicationVO
 */
public class MorwebService {

	private final BDIRequest bdiRequest;

	public MorwebService(final UnderwritePostSel underwrite) {
		final ObjectFactory objectFactory = new ObjectFactory();

		final MorwebCustomerDataVO customerDataVO = new MorwebCustomerDataVO(underwrite);
		final CustomerData customerData = customerDataVO.createCustomerData();

		final MorwebMortgageApplicationVO mortgageApplicationVO = new MorwebMortgageApplicationVO(underwrite);
		final MortgageApplication mortgageApplication = mortgageApplicationVO.createMortgageApplication();

		final MorwebCommonDataVO commonDataVO = new MorwebCommonDataVO(underwrite);
		final CommonData commonData = commonDataVO.createCommonData(customerDataVO, mortgageApplicationVO);
		commonData.getAddressList().getApplicationAddress().add(mortgageApplicationVO.getSubjectPropertyAddressAsJaxbElement());

		this.bdiRequest = objectFactory.createBDIRequest();
		this.bdiRequest.setCommonData(commonData);
		this.bdiRequest.setCustomerData(customerData);
		this.bdiRequest.setMortgageApplication(mortgageApplication);
	}

	public final BDIRequest getBdiRequest() {
		return this.bdiRequest;
	}
}
