package com.syml.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.bean.algo.AlgoApplicant.RelationshipStatus;
import com.syml.bean.algo.UnderwritePostSel;
import com.syml.domain.Address;
import com.syml.domain.Applicant;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.filogix.Address1Type;
import com.syml.filogix.ApplicantAddressType;
import com.syml.filogix.DealType.DownPaymentSource;
import com.syml.filogix.MortgageType;
import com.syml.filogix.PhoneType;
import com.syml.filogix.PropertyType;
import com.syml.filogix.PropertyType.PropertyExpense;
import com.syml.filogix.PropertyType.RentalIncome;
import static com.syml.util.StringUtil.*;

/**
 * Value object like class to help deal with filogix pojo classes and 
 * underwriting classes.
 */
public class FilogixCallVO {

    private static final Logger logger = LoggerFactory.getLogger(FilogixCallVO.class);
    private static final String[] CANADA_STREET_TYPE = {
            "Avenue", "Boulevard", "Circle", "Crescent", "Drive", "Gate", "Highway", 
            "Lane", "Line", "Road", "Route", "Street", "Terrace", "Trail", "Way", 
            "Common", "Court", "Green", "Manor", "Place", "Rise", "Landing", "Bay", 
            "Close", "Link", "Mews", "View", "Quay", "Square", "Abbey", "Acres", 
            "Alley", "Autoroute", "Beach", "Bend", "By-Pass", "Byway", "Campus", 
            "Cape", "Centre", "Chase", "Circuit", "Concession", "Corners", "Crossing", 
            "Cul-de-sac", "Cove", "Dale", "Dell", "Diversion", "Downs", "End", "Estates", 
            "Expressway", "Extension", "Farm", "Field", "Forest", "Freeway", "Front", 
            "Gardens", "Glade", "Glen", "Grounds", "Grove", "Harbour", "Heights", 
            "Highlands", "Hill", "Hollow", "Impasse", "Island", "Key", "Knoll", 
            "Limits", "Lookout", "Loop", "Mall", "Maze", "Meadow", "Moor", "Mount", 
            "Mountain", "Orchard", "Parade", "Park", "Parkway", "Passage", "Path", 
            "Pathway", "Pines", "Plaza", "Point", "Port", "Private", "Promenade", 
            "Range", "Ridge", "Row", "Run", "Subdivision", "Thicket", "Towers", 
            "Townline", "Turnabout", "Vale", "Village", "Vista", "Walk", "Wharf", 
            "Wood", "Heath", "Inlet", "Plateau", "Ramp", "Villas", "Wynd", 
            "Allée", "Carré", "Carrefour", "Cercle", "Chemin", "Côte", "Cour", 
            "Cours", "Croissant", "Échangeur", "Esplanade", "Île", "Montée", "Parc", 
            "Pointe", "Quai", "Rang", "Rond-point", "Rue", "Ruelle", "Sentier", 
            "Terrasse", "Via", "Villas", "Voie", "Alley", "Annex", "Arcade", "Avenue", 
            "Bayou", "Beach", "Bend", "Bluff", "Bluffs", "Bottom", "Boulevard", "Branch", 
            "Bridge", "Brook", "Brooks", "Burg", "Burgs", "Bypass", "Camp", "Canyon", 
            "Cape", "Causeway", "Center", "Centers", "Circle", "Circles", "Cliff", 
            "Cliffs", "Club", "Common", "Commons", "Corner", "Corners", "Course", 
            "Court", "Courts", "Cove", "Coves", "Creek", "Crescent", "Crest", 
            "Crossing", "Crossroad", "Crossroads", "Curve", "Dale", "Dam", "Divide", 
            "Drive", "Drives", "Estate", "Estates", "Expressway", "Extension", 
            "Extensions", "Fall", "Falls", "Ferry", "Field", "Fields", "Flat", "Flats", 
            "Ford", "Fords", "Forest", "Forge", "Forges", "Fork", "Forks", "Fort", 
            "Freeway", "Garden", "Gardens", "Gateway", "Glen", "Glens", "Green", "Greens", 
            "Grove", "Groves", "Harbor", "Harbors", "Haven", "Heights", "Highway", "Hill", 
            "Hills", "Hollow", "Inlet", "Island", "Islands", "Isle", "Junction", 
            "Junctions", "Key", "Keys", "Knoll", "Knolls", "Lake", "Lakes", "Land", 
            "Landing", "Lane", "Light", "Lights", "Loaf", "Lock", "Locks", "Lodge", 
            "Loop", "Mall", "Manor", "Manors", "Meadow", "Meadows", "Mews", "Mill", 
            "Mills", "Mission", "Motorway", "Mount", "Mountain", "Mountains", "Neck", 
            "Orchard", "Park", "Parks", "Parkway", "Parkways", "Pass", "Passage", "Path", 
            "Pike", "Pine", "Pines", "Place", "Plain", "Plains", "Plaza", "Point", "Points", 
            "Port", "Ports", "Prairie", "Radial", "Ramp", "Ranch", "Rapid", "Rapids", 
            "Rest", "Ridge", "Ridges", "River", "Road", "Roads", "Route", "Row", "Rue", 
            "Run", "Shoal", "Shoals", "Shore", "Shores", "Skyway", "Spring", "Springs", 
            "Spur", "Spurs", "Square", "Squares", "Station", "Stravenue", "Stream", "Street", 
            "Streets", "Summit", "Terrace", "Throughway", "Trace", "Track", "Trafficway", 
            "Trail", "Trailer", "Tunnel", "Turnpike", "Underpass", "Union", "Unions", 
            "Valley", "Valleys", "Viaduct", "View", "Views", "Village", "Villages", "Ville", 
            "Vista", "Walk", "Walks", "Wall", "Way", "Ways", "Well", "Wells"
    };
    private static List<String> CANADA_STREET_TYPE_LIST = new ArrayList<>();

    static {
        CANADA_STREET_TYPE_LIST.addAll(Arrays.asList(CANADA_STREET_TYPE));
    }

    public static enum AddressComponentType {
        UNIT, STREET_NUMBER, STREET_NAME, STREET_TYPE, DIRECTION, CITY /** Use Wisely! */, PROVINCE, /** Use wisely! */ POSTAL_CODE;
    }

    /**
     * TODO: "Widowed" is not covered in Underwrite App.
     */
    public static final BigDecimal underwriteToFilogixMaritalStatus(final String uwRelationshipStatus) {
        BigDecimal result = null;
        if (isNullOrEmpty(uwRelationshipStatus)) {
            result = new BigDecimal("7");
            return result;
        }

        if (uwRelationshipStatus.equalsIgnoreCase(RelationshipStatus.Single.name())) {
            result = BigDecimal.ZERO;
        } else if (uwRelationshipStatus.equalsIgnoreCase(RelationshipStatus.Married.name())) {
            result = new BigDecimal("2");
        } else if (uwRelationshipStatus.equalsIgnoreCase(RelationshipStatus.CommonLaw.name())) {
            result = new BigDecimal("3");
        } else if (uwRelationshipStatus.equalsIgnoreCase(RelationshipStatus.Separated.name())) {
            result = new BigDecimal("4");
        } else if (uwRelationshipStatus.equalsIgnoreCase(RelationshipStatus.Divorced.name())) {
            result = new BigDecimal("5");
        } else {
            result = new BigDecimal("7");
        }
        return result;
    }


    /** Default to "Purchase" */
    public static BigDecimal createFilogixDealPurposeDD(Opportunity opportunity) {
        final String whatIsYourLendingGoal = opportunity.whatIsYourLendingGoal;
        BigDecimal result = null;
        if (isNullOrEmpty(whatIsYourLendingGoal)) {
            result = new BigDecimal("0");
        }

        if (opportunity.whatIsYourLendingGoal.toLowerCase().contains("purchase")) {
            if (opportunity.renovationValue != null && opportunity.renovationValue > 0) {
                result = new BigDecimal(0); // Purchase
            } else {
                result = new BigDecimal(1); // Purchase + improvement
            }
        } else if (opportunity.whatIsYourLendingGoal.toLowerCase().contains("refinance")) {
            result = new BigDecimal(2);
        // FIXME: Until this line of method, the rest is no documentation at all.
        } else if (opportunity.whatIsYourLendingGoal.toLowerCase().contains("eto")) {
            result = new BigDecimal(3);
        } else if (opportunity.whatIsYourLendingGoal.toLowerCase().contains("switch")) {
            result = new BigDecimal(4);
        } else if (opportunity.whatIsYourLendingGoal.toLowerCase().contains("port")) {
            result = new BigDecimal(5);
        } else if (opportunity.whatIsYourLendingGoal.toLowerCase().contains("deficiency")) {
            result = new BigDecimal(6);
        } else if (opportunity.whatIsYourLendingGoal.toLowerCase().contains("workout")) {
            result = new BigDecimal(7);
        } else {
            result = new BigDecimal("0");
        }
        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static BigDecimal underwriteToFilogixIncomeType(final String uwIncometype) {
        BigDecimal result = null;
        if (isNullOrEmpty(uwIncometype)) {
            result = new BigDecimal("11");
            return result;
        }

        if (uwIncometype.equalsIgnoreCase("2")) {
            result = new BigDecimal("6");
        } else if (uwIncometype.equalsIgnoreCase("4")) {
            result = new BigDecimal("3");
        } else if (uwIncometype.equalsIgnoreCase("1")) {
            result = new BigDecimal("0");
        } else {
            result = new BigDecimal("11"); // Other UW income type not mapped in referralApplication_1_0_1.doc page 182.
        }
        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    /** 1=Home, 2=Cellphone, 3=Work, 4=Fax */
    public static PhoneType createFilogixPhoneType(String phoneNumber, int phoneTypeInNumber) {
        if (isNullOrEmpty(phoneNumber))
            return null;

        PhoneType phoneType = new PhoneType();

        BigDecimal phoneTypeDD = new BigDecimal(phoneTypeInNumber);
        phoneTypeDD.setScale(0, RoundingMode.DOWN);
        phoneType.setPhoneTypeDd(phoneTypeDD);

        // Remove "-" value.
        phoneNumber = phoneNumber.replaceAll("-", "");
        String extension = "";
        // They have an extension.
        if (phoneNumber.contains("(")) {
            final String[] split = phoneNumber.split("\\)");
            extension = split[0].substring(1, split[0].length());
            if (extension.contains("+")) {
                extension = extension.replaceAll("\\+", "");
            }
            phoneNumber = split[1];
        }

        // Setting phone extension making weird result. Lets discard it:
        // https://docs.google.com/drawings/d/1i252248csPDXHlnuKhQDWMcCafcAZFJ3-QxeiMtWHYc/edit
        // https://docs.google.com/drawings/d/16AZ-IZIgczxNRrBFgNjRoBTuKb6IUIoOWTpfIjkulx0/edit
        // phoneType.setPhoneExtension(extension);
        phoneType.setPhoneNumber(extension + phoneNumber);

        return phoneType;
    }


    public static BigDecimal createFilogixPreferredContactMethod(final String bestNumber, List<PhoneType> applicantPhoneTypes) {
        // Make cell phone default, just in case 'best number' value is not in applicantPhoneType.
        BigDecimal result = BigDecimal.ONE;
        if (isNullOrEmpty(bestNumber)) return result;

        final PhoneType bestNumberAsPhoneType = createFilogixPhoneType(bestNumber, 1);
        for (final PhoneType phoneType : applicantPhoneTypes) {
            if (bestNumberAsPhoneType.getPhoneNumber().equals(phoneType.getPhoneNumber())) {
                result = phoneType.getPhoneTypeDd();
                break;
            }
        }

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static BigDecimal createFilogixAssetType(final String assetType) {
        if (isNullOrEmpty(assetType))
            return BigDecimal.valueOf(8).setScale(0, RoundingMode.DOWN);

        final String type = assetType.toLowerCase();
        if (type.contains("vehicle")) return BigDecimal.valueOf(4).setScale(0, RoundingMode.DOWN);
        if (type.contains("non-rrsp")) return BigDecimal.valueOf(6).setScale(0, RoundingMode.DOWN);
        if (type.contains("rrsp")) return BigDecimal.valueOf(1).setScale(0, RoundingMode.DOWN);

        // Other value from CRM is "non-rrsp", which is not mapped in filogix.

        return BigDecimal.valueOf(8).setScale(0, RoundingMode.DOWN);
    }


    public static List<ApplicantAddressType> createApplicantAddressDetail(Applicant applicant) {
        List<ApplicantAddressType> result = new ArrayList<ApplicantAddressType>();

        List<Address> addresses = applicant.addresses;
        Collections.sort(addresses);
        final int addressDataSize = addresses.size();
        Date nextDate = null;
        logger.debug(">>> Address size for applicant '{}', is:{}", applicant.getApplicantName(), addressDataSize);
        for (int i = 0; i < addressDataSize; i++) {
            final Address address = addresses.get(i);
            String postalFSA = null;
            String postalLDU = null;
            String postalIntl = address.getPostalCode();
            if (!isNullOrEmpty(postalIntl)) {
                postalFSA = postalIntl.substring(0, 3); // Is this correct?
                postalLDU = postalIntl.substring(3, 6); // is this correct?
            }

            final String googleAddress = address.getName();
            Address1Type address1Type = new Address1Type();
            address1Type.setUnitNumber(createFilogixAddressComponent(googleAddress, AddressComponentType.UNIT));
            address1Type.setStreetNumber( createFilogixAddressComponent(googleAddress, AddressComponentType.STREET_NUMBER) );
            address1Type.setStreetName( createFilogixAddressComponent(googleAddress, AddressComponentType.STREET_NAME) );
            address1Type.setStreetDirectionDd( createFilogixAddressDirection(googleAddress) );
            address1Type.setStreetTypeDd( createFilogixAddressStreetType(googleAddress) );
            address1Type.setInternationalPostalCode(address.getPostalCode());
            address1Type.setPostalFsa(postalFSA);
            address1Type.setPostalLdu(postalLDU);
            address1Type.setCity(address.getCity());
            address1Type.setProvinceDd(createFilogixProvinceIdByCode(address.getProvince()));

            logger.debug(">>> Address for applicant: {}, {}", applicant.getApplicantName(), address1Type);
            ApplicantAddressType applicantAddressType = new ApplicantAddressType();
            applicantAddressType.setAddressTypeDd(BigDecimal.ZERO);
            applicantAddressType.setAddress(address1Type);
            applicantAddressType.setAddressTypeDd( (i == (addressDataSize - 1) ? BigDecimal.ZERO : BigDecimal.ONE) );
            nextDate = (i == (addressDataSize - 1)) ? new Date() : addresses.get(i + 1).getDate();
                if (address.getDate() == null)
                    applicantAddressType.setMonthsAtAddress(createApplicantMonthsAtAddress(nextDate, address.getDate()));
            

            result.add(applicantAddressType);
        }


        return result;
    }


    public static int createApplicantMonthsAtAddress(Date nextDate, Date date) {
        if (date == null) {
            logger.warn("Cannot set months at address for 'time at residence'. Date is null.");
            return 0;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(nextDate == null ? new Date() : nextDate);

        Calendar howLong = Calendar.getInstance();
        howLong.setTime(date);

        final int nowYear = now.get(Calendar.YEAR);
        final int nowMonth = now.get(Calendar.MONTH);
        int howLongYear = howLong.get(Calendar.YEAR);
        final int howLongMonth = howLong.get(Calendar.MONTH);

        int totalMonth = Math.abs( nowMonth - howLongMonth );
        if (howLongMonth > nowMonth) {
            howLongYear = howLongYear + 1;
            totalMonth = 12 - totalMonth;
        }
        int totalYear = (nowYear - howLongYear) * 12;

        return totalMonth + totalYear;
    }


    public static BigDecimal createOccupancyType(final String livingInProperty) {
        BigDecimal result = null;
        logger.debug(">>> createOccupancyType() living in property: {}", livingInProperty);
        if (isNullOrEmpty(livingInProperty)) { 
            return BigDecimal.ZERO;
        } else {
            if (livingInProperty.toLowerCase().contains("owner")) {
                result = BigDecimal.ZERO;
            } else if (livingInProperty.toLowerCase().contains("renter")) {
                result = new BigDecimal("2");
            } else if (livingInProperty.toLowerCase().contains("owner and renter")) {
                result = BigDecimal.ONE;
            } else if (livingInProperty.toLowerCase().contains("second home")) {
                result = new BigDecimal("3");
            } else {
                result = BigDecimal.ZERO;
            }
        }
        result = result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static final BigDecimal createFilogixProvinceIdByCode(final String code) {
        if (isNullOrEmpty(code))
            return null;

        BigDecimal result = null;
        switch (code) {
        case "AB": result = new BigDecimal("1"); break;
        case "BC": result = new BigDecimal("2"); break;
        case "MB": result = new BigDecimal("3"); break;
        case "NB": result = new BigDecimal("4"); break;
        case "NL": result = new BigDecimal("5"); break;
        case "NS": result = new BigDecimal("6"); break;
        case "ON": result = new BigDecimal("7"); break;
        case "PE": result = new BigDecimal("8"); break;
        case "QC": result = new BigDecimal("9"); break;
        case "SK": result = new BigDecimal("10"); break;
        case "NT": result = new BigDecimal("11"); break;
        case "NU": result = new BigDecimal("12"); break;
        case "YT": result = new BigDecimal("13"); break;
        default: result = new BigDecimal("79"); break; // Armed Forces Americas???
        }
        result = result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static final String createFilogixAddressComponent(String address, final AddressComponentType addressComponentType) {
        if (isNullOrEmpty(address)) return "";
        address = address.trim();
        final String[] addressComponent = address.split(" ");
        final int tokenSize = addressComponent.length;
        boolean isContainsUnit = false;
        boolean isContainsDirection = false;
        boolean isContainsPostalCode = tokenSize > 0 ? (addressComponent[tokenSize -1].length() == 3) : false;
        int tokenReductionValue = 0;

        logger.info(">>> address:{}, tokenSize:{}", address, tokenSize);

        switch (addressComponentType) {
        case STREET_NUMBER:
            final String streetNumber = addressComponent[0];
            return isInteger(streetNumber) ? streetNumber.trim() : "";

        case UNIT: 
            if (tokenSize > 1) {
                final String unit = addressComponent[1];
                return isInteger(unit) ? unit.trim() : "";
            }
            return "";

        case STREET_NAME:
            isContainsUnit = !isNullOrEmpty(createFilogixAddressComponent(address, AddressComponentType.UNIT));
            if (isContainsUnit) {
                return (tokenSize > 2) ? addressComponent[2].trim() : "";
            } else {
                return (tokenSize > 1) ? addressComponent[1].trim() : "";
            }

        case STREET_TYPE:
            String streetType = null;
            int streetTypeDD = 0;
            isContainsUnit = !isNullOrEmpty(createFilogixAddressComponent(address, AddressComponentType.UNIT));
            if (isContainsUnit) {
                if (tokenSize > 3) {
                    streetType = addressComponent[3] == null ? "" : addressComponent[3].trim();
                    streetType = streetType.toLowerCase().equals("st") ? "Street" : streetType;
                    final int index = CANADA_STREET_TYPE_LIST.indexOf(streetType.trim().replaceAll(",", ""));
                    streetTypeDD = index + 1;
                }
            } else {
                if (tokenSize > 2) {
                    streetType = addressComponent[2] == null ? "" : addressComponent[2].trim();
                    streetType = streetType.toLowerCase().equals("st") ? "Street" : streetType;
                    final int index = CANADA_STREET_TYPE_LIST.indexOf(streetType.trim().replaceAll(",", ""));
                    streetTypeDD = index + 1;
                }
            }
            // return ""; // No, probability of people put street type in <component1> <component2> is nearly impossible! 
            return streetTypeDD == 0 ? "" : String.valueOf(streetTypeDD);
        
        case DIRECTION:
            String direction = null;
            isContainsUnit = !isNullOrEmpty(createFilogixAddressComponent(address, AddressComponentType.UNIT));
            if (isContainsUnit) {
                if (tokenSize > 4) {
                    direction = addressComponent[4] == null ? "" : addressComponent[4].trim().replaceAll(",", "");
                    return findAndGetDirection(direction);
                } else 
                    return "";
            } else {
                if (tokenSize > 3) {
                    direction = addressComponent[3] == null ? "" : addressComponent[3].trim().replaceAll(",", "");
                    return findAndGetDirection(direction);
                } else 
                    return "";
            }
        case PROVINCE:
            /** These annoying if else used to make sure no city named "street", or false province code parsed with value 'N'. */
            tokenReductionValue = isContainsPostalCode ? 3 : 1;
            isContainsUnit = !isNullOrEmpty(createFilogixAddressComponent(address, AddressComponentType.UNIT));
            isContainsDirection = !isNullOrEmpty(createFilogixAddressComponent(address, AddressComponentType.DIRECTION));
            if (isContainsDirection && isContainsUnit) {
                if (tokenSize > 6) {
                    final String provinceCode = addressComponent[tokenSize - tokenReductionValue];
                    final BigDecimal code = createFilogixProvinceIdByCode(provinceCode);
                    return code == null ? null : code.toPlainString();
                } else 
                    return "";
            } else if (isContainsDirection || isContainsUnit) {
                if (tokenSize > 5) {
                    final String provinceCode = addressComponent[tokenSize - tokenReductionValue];
                    final BigDecimal code = createFilogixProvinceIdByCode(provinceCode);
                    return code == null ? null : code.toPlainString();
                } else 
                    return "";
            } else {
                if (tokenSize > 4) {
                    final String provinceCode = addressComponent[tokenSize - tokenReductionValue];
                    final BigDecimal code = createFilogixProvinceIdByCode(provinceCode);
                    return code == null ? null : code.toPlainString();
                } else 
                    return "";
            }
        case CITY:
            /** These annoying if else used to make sure no city named "street", or false province code parsed with value 'N'. */
            tokenReductionValue = isContainsPostalCode ? 4 : 2;
            isContainsUnit = !isNullOrEmpty(createFilogixAddressComponent(address, AddressComponentType.UNIT));
            isContainsDirection = !isNullOrEmpty(createFilogixAddressComponent(address, AddressComponentType.DIRECTION));
            if (isContainsDirection && isContainsUnit) {
                if (tokenSize > 6) {
                    return addressComponent[tokenSize - tokenReductionValue].replaceAll(",", "");
                } else 
                    return "";
            } else if (isContainsDirection || isContainsUnit) {
                if (tokenSize > 5) {
                    return addressComponent[tokenSize - tokenReductionValue].replaceAll(",", "");
                } else 
                    return "";
            } else {
                if (tokenSize > 4) {
                    return addressComponent[tokenSize - tokenReductionValue].replaceAll(",", "");
                } else 
                    return "";
            }

        case POSTAL_CODE:
            return isContainsPostalCode ? addressComponent[tokenSize - 2] + " " + addressComponent[tokenSize - 1] : "";
            
        default: return "";
        }
    }


    public static final BigDecimal createFilogixAddressDirection(String fullAddressString) {
        BigDecimal result = null;
        final String direction = createFilogixAddressComponent(fullAddressString, AddressComponentType.DIRECTION);
        if (!isNullOrEmpty(direction)) {
            result = new BigDecimal(direction);
            result = result.setScale(0, RoundingMode.DOWN);
            return result;
        }
        return result;
    }


    public static final BigDecimal createFilogixAddressStreetType(String fullAddressString) {
        BigDecimal result = null;
        final String streetTypeDD = createFilogixAddressComponent(fullAddressString, AddressComponentType.STREET_TYPE);
        if ( !isNullOrEmpty(streetTypeDD) ) {
            result = new BigDecimal(streetTypeDD);
            result.setScale(0, RoundingMode.DOWN);
        }
        return result;
    }


    // N, S, E, W, NW, NE, SW, SW
    private static String findAndGetDirection(String s) {
        if (isNullOrEmpty(s)) return "";
        
        if (s.equalsIgnoreCase("N")) return "1"; 
        if (s.equalsIgnoreCase("S")) return "2";
        if (s.equalsIgnoreCase("E")) return "3";
        if (s.equalsIgnoreCase("W")) return "4";
        if (s.equalsIgnoreCase("NE")) return "5";
        if (s.equalsIgnoreCase("NW")) return "6";
        if (s.equalsIgnoreCase("SE")) return "7";
        if (s.equalsIgnoreCase("SW") || s.equalsIgnoreCase("southwest")) return "8";
        return "";
    }


    public static BigDecimal createFilogixPropertyType(final String type) {
        logger.info(">>> propertyTypeDD {}", type);
        BigDecimal result = null;
        if (isNullOrEmpty(type)) 
            return new BigDecimal(1);

        if (type.toLowerCase().contains("condo"))
            result = new BigDecimal(2);
        else 
            result = new BigDecimal(1);

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    // House, Duplex, FourPlex, TowHouse, RowHouse, ApartmentStyle, MobileHome, ModularHome, Other
    // This is should referenced by index, and start from house = 1, duplex = 2, etc.
    public static BigDecimal createFilogixDwellingType(final String crmPropertyType) {
        logger.info(">>> propertyTypeDDd for dwelling {}", crmPropertyType);
        BigDecimal result = null;
        if (isNullOrEmpty(crmPropertyType)) 
            return new BigDecimal(1);

        // House
        if (crmPropertyType.contains("1")) 
            result = new BigDecimal(0); // Detached
        // Duplex
        else if (crmPropertyType.contains("2")) 
            result = new BigDecimal(2); // Duplex - Detached
        // Fourplex
        else if (crmPropertyType.contains("3")) 
            result = new BigDecimal(13); // fourplex - detached
        // Town house
        else if (crmPropertyType.contains("4"))  
            result = new BigDecimal(4); // Row housing
        // Row house
        else if (crmPropertyType.contains("5"))
            result = new BigDecimal(4); // row housing
        // ApartmentStyle
        else if (crmPropertyType.contains("6"))
            result = new BigDecimal(5); // Apartment Low Rise
        // Mobile Home
        else if (crmPropertyType.contains("7"))
            result = new BigDecimal(7); // Mobile
        // Modular Home
        else if (crmPropertyType.contains("8"))
            result = new BigDecimal(11); // Modular Home - Detached
        else 
            result = new BigDecimal(0);

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static BigDecimal createFilogixHeatingType(final String heating) {
        logger.debug(">>> heating type: {}", heating);
        BigDecimal result = null;
        if (isNullOrEmpty(heating)) 
            return new BigDecimal(4);

        // forced air
        if (heating.contains("1"))
            result = new BigDecimal(2);
        // electric
        else if (heating.contains("2"))
            result = new BigDecimal(1);
        // hot water
        else if (heating.contains("3"))
            result = new BigDecimal(3);
        else
            result = new BigDecimal(4);

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static BigDecimal createFilogixWaterType(final String water) {
        logger.debug(">>> water type: {}", water);
        BigDecimal result = null;
        if (isNullOrEmpty(water)) 
            return new BigDecimal(1);

        // municipal
        if (water.contains("1"))
            result = new BigDecimal(0);
        // well
        else
            result = new BigDecimal(1);

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    /** FIXME: One thing that bugs me is that, in crm-app, those enum values is "story" instead of "storey". */
    public static BigDecimal createFilogixPropertyStyle(final String style) {
        logger.debug(">>> createFilogixPropertyStyle value: {}", style);
        BigDecimal result = null;
        if (isNullOrEmpty(style)) 
            return new BigDecimal(6);

        if (style.toLowerCase().contains("1")) // Bungalow/One Story
            result = new BigDecimal(0);
        else if (style.toLowerCase().contains("2") || style.toLowerCase().contains("2")) // BiLevel
            result = new BigDecimal(1);
        else if (style.toLowerCase().contains("3")) // Two storey
            result = new BigDecimal(2);
        else if (style.toLowerCase().contains("4")) // Split level
            result = new BigDecimal(3);
        else if (style.toLowerCase().contains("5")) // storey and a half.
            result = new BigDecimal(4);
        else if (style.toLowerCase().contains("6")) // Three storey
            result = new BigDecimal(5);
        else
            result = new BigDecimal(6);

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    // Single = 1, Double = 2, Triple = 3, Quadraple = 4
    public static BigDecimal createFilogixGarageSize(final String size) {
        logger.info(">>> GarageSize: {}", size);
        BigDecimal result = null;
        if (isNullOrEmpty(size)) 
            return new BigDecimal(4);

        if (size.contains("1"))
            result = new BigDecimal(1);
        else if (size.contains("2"))
            result = new BigDecimal(2);
        else if (size.contains("3"))
            result = new BigDecimal(3);
        else
            result = new BigDecimal(4);

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    // Attached = 1, Detached = 2, None = 3
    public static BigDecimal createFilogixGarageType(final String type) {
        logger.info(">>> GarageType: {}", type);
        BigDecimal result = null;
        if (isNullOrEmpty(type)) 
            return new BigDecimal(1);

        if (type.toLowerCase().contains("2"))
            result = new BigDecimal(2);
        else
            result = new BigDecimal(1);

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static BigDecimal createFilogixSewageType(final String sewage) {
        BigDecimal result = null;
        if (isNullOrEmpty(sewage)) 
            return new BigDecimal(0);

        if (sewage.toLowerCase().contains("municipal"))
            result = new BigDecimal(0);
        else if (sewage.toLowerCase().contains("septic")) // septic system
            result = new BigDecimal(1);
        else if (sewage.toLowerCase().contains("holding")) // holding system
            result = new BigDecimal(2);
        else
            result = new BigDecimal(0);

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static List<PropertyExpense> createPropertyExpanses(UnderwritePostSel underwrite) {
        List<PropertyExpense> expenses = new ArrayList<PropertyType.PropertyExpense>();
        final Opportunity opportunity = underwrite.clientOpportunity;
        final Product potentialProduct = underwrite.potentialProduct;

        if (opportunity.propertyTaxes != null) {
            PropertyExpense taxExpense = new PropertyExpense();
            taxExpense.setPropertyExpenseTypeDd(createPropertyExpanseType("taxes"));
            taxExpense.setPropertyExpenseAmount(new BigDecimal(opportunity.propertyTaxes));
            taxExpense.setPropertyExpensePeriodDd(new BigDecimal(0).setScale(0, RoundingMode.DOWN));
            expenses.add(taxExpense);
        }

        if (potentialProduct.minHeatCost != null) {
            PropertyExpense heatingCost = new PropertyExpense();
            heatingCost.setPropertyExpenseTypeDd(createPropertyExpanseType("heating"));
            heatingCost.setPropertyExpenseAmount(new BigDecimal(potentialProduct.minHeatCost));
            heatingCost.setPropertyExpenseDescription(potentialProduct.description);
            heatingCost.setPropertyExpensePeriodDd(new BigDecimal(3).setScale(0, RoundingMode.DOWN));
            expenses.add(heatingCost);
        }

        if (opportunity.condoFees != null) {
            PropertyExpense condoFees = new PropertyExpense();
            condoFees.setPropertyExpenseTypeDd(createPropertyExpanseType("condo-fees"));
            condoFees.setPropertyExpenseAmount(new BigDecimal(opportunity.condoFees));
            condoFees.setPropertyExpensePeriodDd(new BigDecimal(3).setScale(0, RoundingMode.DOWN));
            expenses.add(condoFees);
        }
        
        return expenses;
    }

    public static BigDecimal createPropertyExpanseType(final String type) { // FIXME: Enum would be better, really.
        if (isNullOrEmpty(type)) 
            throw new NullPointerException("expanseType should be not null in FilogixCallVO#createPropertyExpanseType.");

        BigDecimal result = null;
        switch (type) {
            case "taxes": result = new BigDecimal(0); break;
            case "condo-fees": result = new BigDecimal(1); break;
            case "heating": result = new BigDecimal(2); break;
            default: throw new IllegalArgumentException("propertyExpanseType '" + type + "' is unknown.");
        }

        result.setScale(0, RoundingMode.DOWN);
        return result;
    }


    public static RentalIncome createRentalIncome(UnderwritePostSel underwrite) {
        RentalIncome result = new RentalIncome();
        result.setIncomeTypeDd(new BigDecimal(9).setScale(0, RoundingMode.DOWN));
        result.setIncomeAmount(new BigDecimal(underwrite.clientOpportunity.monthlyRentalIncome));
        result.setIncomePeriodDd(new BigDecimal(3).setScale(0, RoundingMode.DOWN));

        return result;
    }


    public static Integer createFilogixStructureAge(final int uwStuctureAge) {
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        final int age = currentYear - uwStuctureAge;
        if (age < 0 || age > 999) {
            return 0;
        } else return age;
    }


    public static List<MortgageType> createFilogixMortgageTypesForSubjectProperty(UnderwritePostSel underwrite) {
        List<MortgageType> result = new ArrayList<MortgageType>();

        final Product potentialProduct = underwrite.potentialProduct;
        final Opportunity clientOpportunity = underwrite.clientOpportunity;

        MortgageType mortgage1 = new MortgageType();
        mortgage1.setMortgageTypeDd( createMortgageTypeDDForSubjectProperty(clientOpportunity.chargeOnTitle) );
        mortgage1.setInsuredFlag(underwrite.algoClientOpportunity.highRatio ? "Y" : "N");
        mortgage1.setMtgInsIncludeFlag( underwrite.insuranceAmount > 0 ? "Y" : "N" );
        mortgage1.setInterestRate(new BigDecimal(potentialProduct.interestRate));

        // This is "Rate Type" in UI : https://docs.google.com/drawings/d/1FLRZZOe4IcXLk0dDjbX84M8zkQAmbe1XhubvoUww1SQ/edit
        mortgage1.setInterestTypeDd(createInterestType(potentialProduct.mortgageType));

        // This is "Term Type" in UI : https://docs.google.com/drawings/d/1FLRZZOe4IcXLk0dDjbX84M8zkQAmbe1XhubvoUww1SQ/edit
        // From Guy: "Please map from Underwrite2.potentialProduct.OpenClosed"
        mortgage1.setPaymentTermDd( createTermType(potentialProduct.openClosed) );
        
        mortgage1.setPaymentFrequencyDd(new BigDecimal(2).setScale(0, RoundingMode.DOWN));
        mortgage1.setInterestCompoundDd(BigDecimal.ZERO.setScale(0, RoundingMode.DOWN));

        mortgage1.setActualPaymentTerm(createActualPaymentTerm(potentialProduct.term));
        mortgage1.setRefiBlendedAmortization("Y");
        mortgage1.setInterestRate( createInterestRate(underwrite).setScale(4, RoundingMode.CEILING) );
        mortgage1.setCashBackPercentage( BigDecimal.valueOf(potentialProduct.cashback).setScale(4, RoundingMode.CEILING) );

        mortgage1.setNetLoanAmount( BigDecimal.valueOf(clientOpportunity.desiredMortgageAmount).setScale(4, RoundingMode.CEILING) );
        mortgage1.setAmortizationTerm(underwrite.amortization * 12); // UWApp in year. Filogix ask in month.

        MortgageType.Rate rate = new MortgageType.Rate();
        rate.setRequestedRate( BigDecimal.valueOf(potentialProduct.qualifyingRate) );

        mortgage1.setRate(rate);

        result.add(mortgage1);

        return result;
    }


    public static BigDecimal createInterestType(String mortgageType) {
        BigDecimal result = null;
        if (isNullOrEmpty(mortgageType)) {
            logger.warn(">>> mortgageType is null or empty. Return default 'FIXED' value. mortgageType:{}", mortgageType);
            return BigDecimal.ZERO.setScale(0, RoundingMode.DOWN);
        }

        logger.debug(">>> mortgageType value={}", mortgageType);

        // Change logic because of this log message ">>> mortgageType value=3".
        if (mortgageType.toLowerCase().contains("1")) // Fixed
            result = new BigDecimal("0");
        if (mortgageType.toLowerCase().contains("2")) // variable
            result = new BigDecimal("3");
        if (mortgageType.toLowerCase().contains("3")) // Cashback
            result = new BigDecimal("0");
        if (mortgageType.toLowerCase().contains("4")) // LOC
            result = new BigDecimal("1");
        if (mortgageType.toLowerCase().contains("5")) // Combined
            result = new BigDecimal("1");

        if (result != null)
            result.setScale(0, RoundingMode.DOWN);

        return result;
    }


    public static BigDecimal createTermType(String openClosed) {
        logger.debug(">>> openClosed value:{}", openClosed);

        if (isNullOrEmpty(openClosed))
            return BigDecimal.ZERO;

        if (openClosed.toLowerCase().contains("open"))
            return BigDecimal.ONE;

        return BigDecimal.ZERO;
    }

    /** This is in month. Read: referralApplication doc line 229. */
    private static Integer createActualPaymentTerm(String term) {
        if (isNullOrEmpty(term)) {
            logger.warn(">>> term is null or empty. createActualPaymentTerm would return 0. Term:{}", term);
            return 0;
        }

        logger.debug(">>> Term value={}", term);

        if (term.contains("1")) return 6;
        if (term.contains("2")) return 12;
        if (term.contains("3")) return 24;
        if (term.contains("4")) return 36;
        if (term.contains("5")) return 48;
        if (term.contains("6")) return 60;
        if (term.contains("7")) return 72;
        if (term.contains("8")) return 84;
        if (term.contains("9")) return 96;
        if (term.contains("10")) return 108;
        if (term.contains("11")) return 120;

        return 0;
    }


    private static BigDecimal createInterestRate(UnderwritePostSel underwrite) {
        final String mortgageType = underwrite.potentialProduct.mortgageType;
        double interestRate = 0d;

        if (mortgageType.contains("2"))
            interestRate = underwrite.potentialProduct.qualifyingRate;
        if (mortgageType.contains("3")) {
            final Integer term = createActualPaymentTerm(underwrite.potentialProduct.term);
            if (term < 60) {
                interestRate = underwrite.potentialProduct.qualifyingRate;
            } else {
                interestRate = underwrite.potentialProduct.interestRate;
            }
        }
        if (mortgageType.contains("1"))
            interestRate = underwrite.potentialProduct.qualifyingRate;

        return new BigDecimal(interestRate);
    }


    public static BigDecimal createMortgageTypeDDForMortgage(final String mortgageType) {
        logger.debug(">>> mortgageType for mortgageType: {}", mortgageType);
        if (isNullOrEmpty(mortgageType) || !isInteger(mortgageType))
            return BigDecimal.ONE;

        if (mortgageType.equals("1")) return BigDecimal.ONE;
        if (mortgageType.equals("2")) return BigDecimal.valueOf(2);
        if (mortgageType.equals("3")) return BigDecimal.valueOf(3);

        return BigDecimal.ONE;
    }


    private static BigDecimal createMortgageTypeDDForSubjectProperty(final String crmChargeOnTitle) {
        logger.debug(">>> crmChargeOnTitle for mortgageType: {}", crmChargeOnTitle);
        if (isNullOrEmpty(crmChargeOnTitle) || !isInteger(crmChargeOnTitle)) 
            return BigDecimal.ONE;

        if (crmChargeOnTitle.equals("1")) return BigDecimal.ONE;
        else if (crmChargeOnTitle.equals("2")) return BigDecimal.valueOf(2).setScale(0, RoundingMode.DOWN);
        else if (crmChargeOnTitle.equals("3")) return BigDecimal.valueOf(3).setScale(0, RoundingMode.DOWN);
        else return BigDecimal.ONE;
    }


    public static List<DownPaymentSource> createDownpaymentSourceForDeal(Opportunity clientOpportunity) {
        final List<DownPaymentSource> result = new ArrayList<>();

        final double dpBankAccount = clientOpportunity.bankAccount;
        final double dpPersonalCashAmount = clientOpportunity.personalCashAmount;
        final double dpRrspAmount = clientOpportunity.rrspAmount;
        final double dpGiftedAmount = clientOpportunity.giftedAmount;
        final double dpBorrowedAmount = clientOpportunity.borrowedAmount;
        final double dpSaleOfExistingAmount = clientOpportunity.saleOfExistingAmount;
        final double dpExistingEquityAmount = clientOpportunity.existingEquityAmount;
        final double dpSweatEquityAmount = clientOpportunity.sweatEquityAmount;
        final double dpSecondaryFinancingAmount = clientOpportunity.secondaryFinancingAmount;
        final double dpOtherAmount = clientOpportunity.otherAmount;

        if (dpBankAccount > 0) { // FIXME No type of bank account in filogix.
            logger.debug(">>> downpayment bank account: {}", dpBankAccount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpBankAccount));
            downPaymentSource.setDescription("bank account.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.ONE);
            result.add(downPaymentSource);
        }

        if (dpPersonalCashAmount > 0) {
            logger.debug(">>> downpayment personal cash: {}", dpPersonalCashAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpPersonalCashAmount));
            downPaymentSource.setDescription("personal cash.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.ONE);
            result.add(downPaymentSource);
        }

        if (dpRrspAmount > 0) {
            logger.debug(">>> downpayment rrsp: {}", dpRrspAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpRrspAmount));
            downPaymentSource.setDescription("RRSP.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.valueOf(2));
            result.add(downPaymentSource);
        }

        if (dpGiftedAmount > 0) {
            logger.debug(">>> downpayment gifted amount: {}", dpGiftedAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpGiftedAmount));
            downPaymentSource.setDescription("Gifted amount.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.valueOf(4));
            result.add(downPaymentSource);
        }

        if (dpBorrowedAmount > 0) {
            logger.debug(">>> downpayment borrowed amount: {}", dpBorrowedAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpBorrowedAmount));
            downPaymentSource.setDescription("Borrowed amount.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.valueOf(3));
            result.add(downPaymentSource);
        }

        if (dpSaleOfExistingAmount > 0) {
            logger.debug(">>> downpayment sale of existing amount: {}", dpSaleOfExistingAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpSaleOfExistingAmount));
            downPaymentSource.setDescription("Sale of existing amount.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.ZERO);
            result.add(downPaymentSource);
        }

        if (dpExistingEquityAmount > 0) {
            logger.debug(">>> downpayment existing equity amount: {}", dpExistingEquityAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpExistingEquityAmount));
            downPaymentSource.setDescription("Existing equity amount.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.valueOf(8));
            result.add(downPaymentSource);
        }

        if (dpSweatEquityAmount > 0) {
            logger.debug(">>> downpayment sweat equity amount: {}", dpSweatEquityAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpSweatEquityAmount));
            downPaymentSource.setDescription("Sweat equity amount.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.valueOf(6));
            result.add(downPaymentSource);
        }

        if (dpSecondaryFinancingAmount > 0) {
            logger.debug(">>> downpayment secondary financing amount: {}", dpSecondaryFinancingAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpSecondaryFinancingAmount));
            downPaymentSource.setDescription("Secondary financing amount.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.valueOf(10));
            result.add(downPaymentSource);
        }

        if (dpOtherAmount > 0) {
            logger.debug(">>> downpayment other: {}", dpOtherAmount);
            final DownPaymentSource downPaymentSource = new DownPaymentSource();
            downPaymentSource.setAmount(BigDecimal.valueOf(dpOtherAmount));
            downPaymentSource.setDescription("Other amount.");
            downPaymentSource.setDownPaymentSourceTypeDd(BigDecimal.valueOf(7));
            result.add(downPaymentSource);
        }

        return result;
    }
}
