package com.syml.test;

import java.math.BigDecimal;

import org.junit.Test;

import com.syml.filogix.PhoneType;
import com.syml.service.FilogixCallVO;

public class StringUtilTest {

	@Test
	public void phoneTypeTest() {
		PhoneType phoneType = FilogixCallVO.createFilogixPhoneType("(+6222)2012-754", 1);
		System.out.println(phoneType.getPhoneTypeDd());
		System.out.println(phoneType.getPhoneExtension());
		System.out.println(phoneType.getPhoneNumber());

		phoneType = FilogixCallVO.createFilogixPhoneType("(403)-200-9001", 1);
		System.out.println(phoneType.getPhoneTypeDd());
		System.out.println(phoneType.getPhoneExtension());
		System.out.println(phoneType.getPhoneNumber());
	}

	@Test
	public void oddTest() {
		System.out.println("Result = " + (110 % 12));
	}

	@Test
	public void integerToLowerCaseTest() {
	    BigDecimal result = FilogixCallVO.createInterestType("2");
	    System.out.println("Integer string to lower case=" + result);
	}
}
