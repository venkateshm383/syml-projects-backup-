package com.syml.test;

import static com.syml.service.FilogixCallVO.*;
import static com.syml.service.FilogixCallVO.AddressComponentType.*;
import org.junit.Test;

import com.syml.util.StringUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;

public class FilogixAddressUnitTest {

    public void print(String googleAddress) {
        final String streetNumber = createFilogixAddressComponent(googleAddress, STREET_NUMBER);
        final String unit = createFilogixAddressComponent(googleAddress, UNIT);
        final String streetName = createFilogixAddressComponent(googleAddress, STREET_NAME);
        final String streetType = createFilogixAddressComponent(googleAddress, STREET_TYPE);
        final String direction = createFilogixAddressComponent(googleAddress, DIRECTION);
        final String city = createFilogixAddressComponent(googleAddress, CITY);
        final String province = createFilogixAddressComponent(googleAddress, PROVINCE);
        final String postalCode = createFilogixAddressComponent(googleAddress, POSTAL_CODE);

        String result = new StringBuilder().append("From input '").append(googleAddress).
                append("' -- result is: \n").
                append(" UNIT=").append(unit).
                append(" STREET_NUMBER=").append(streetNumber).
                append(" STREET_NAME=").append(streetName).
                append(" STREET_TYPE=").append(streetType).
                append(" DIRECTION=").append(direction).
                append(" CITY=").append(city).
                append(" PROVINCE=").append(province).
                append(" POSTAL_CODE=").append(postalCode).
                toString();

        System.out.println(result);
    }

    @Test
    public void nullOrEmptyTest() {
        final String toTest = createFilogixAddressComponent("", UNIT);
        assertThat(toTest, is(""));
    }

    @Test
    public void unitComponentTest() {
        String address = "877 Bonnybrook Rd SE";
        String toTest = createFilogixAddressComponent(address, UNIT);
        assertThat(toTest, is(""));
    }

    @Test
    public void streetNumberComponentTest() {
        final String address = "877 32 Boonybrook Rd SE";
        final String toTest = createFilogixAddressComponent(address, STREET_NUMBER);
        assertThat(toTest, is("877"));
    }

    @Test
    public void testBug1() {
        final String address = "2345 St";
        createFilogixAddressComponent(address, UNIT);
        createFilogixAddressComponent(address, STREET_NAME);
    }

    @Test
    public void print1() {
        print("877 Bonnybrook Rd SE");
        print("877 32 Bonnybrook Rd SE");
        print("678 Green Court");
        print("135 Nowberry Street Calgary AB");
        print("135 32 Nowberry Street Calgary AB");
        print("135 32 Nowberry Street NE Calgary AB");
    }

    @Test
    public void print2() {
        // print("5678- 149 Street");
        final String address = "678 Green Court  ";
        System.out.println(address + ".");
        print("678 Green Court");
    }

    @Test
    public void testProvinceCode() {
        final String googleAddress = "47 Campbell Avenue";
        final String provinceCode = createFilogixAddressComponent(googleAddress, AddressComponentType.PROVINCE);
        if (!StringUtil.isNullOrEmpty(provinceCode)) {
            final BigDecimal result = new BigDecimal(provinceCode);
            System.out.println(result);
        }
    }

    @Test
    public void testPostalCode() {
        final String googleAddress = "26 Cochrane Crescent, Cochrane, AB T4C 1Z9";
        print(googleAddress);
    }

    @Test
    public void testFromGuy1() {
        print("602 Hastings St W 100, Vancouver, BC V6B 1P2 ");
        print("120 Adelaide St W, Toronto, ON M5H 1T1");
        print("120 Adelaide St W, Toronto, ON");
        print("120 Adelaide St W, Toronto");
        print("512 3 Calgary Trail Southwest, Edmonton, AB");
        print("512 3 Calgary Trail Southwest, Edmonton");
        print("213 Snowberry Circle, Calgary, AB T3Z 3C4");
        print("213 Snowberry Circle, Calgary, AB");
        print("213 Snowberry Circle, Calgary");
        print("213 Snowberry Circle");
    }
}
