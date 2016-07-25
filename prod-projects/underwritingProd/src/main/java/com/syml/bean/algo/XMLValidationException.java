package com.syml.bean.algo;

import javax.xml.bind.ValidationException;
import javax.xml.bind.util.ValidationEventCollector;

public class XMLValidationException extends ValidationException {

	private static final long serialVersionUID = 2176683324314785455L;

	private final String webServiceProviderName;
	private final ValidationEventCollector validationCollector;

	/**
	 * Create new instance of {@link XMLValidationException}.
	 * @param message general message.
	 * @param webServiceProviderName WS Provider name, such as "Morweb", "Filogix", or others.
	 * @param validationCollector instance of {@link ValidationEventCollector}.
	 */
	public XMLValidationException(final String message, final String webServiceProviderName, final ValidationEventCollector validationCollector) {
		super(message);
		this.webServiceProviderName = webServiceProviderName;
		this.validationCollector = validationCollector;
	}

	public String getWebServiceProviderName() {
		return webServiceProviderName;
	}

	public ValidationEventCollector getValidationCollector() {
		return validationCollector;
	}

}
