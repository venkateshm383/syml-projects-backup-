package com.syml.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.bean.algo.AlgoAsset;
import com.syml.bean.algo.AlgoIncome;
import com.syml.bean.algo.AlgoLender;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;

public class SelectionHelper {

	private static final Logger logger = LoggerFactory.getLogger(SelectionHelper.class);

	public static boolean compareSelection(AlgoOpportunity.BuildingFunds e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.BuildingFunds.values()[i]) == 0;
		else
			return false;
	}

	public static boolean compareSelection(AlgoProduct.TreatmentOfRental e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.TreatmentOfRental.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoProduct.SupportTreatment e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.SupportTreatment.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoProduct.LOCRateForQualifying e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.LOCRateForQualifying.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoProduct.MortgageType e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.MortgageType.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoProduct.EquifaxScoring e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.EquifaxScoring.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoProduct.LiabilityCalculatonMethod e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.LiabilityCalculatonMethod.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoProduct.ApplicationMethod e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.ApplicationMethod.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoProduct.ExcludeOrIncludeCities e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.ExcludeOrIncludeCities.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoProduct.ProductTerm e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoProduct.ProductTerm.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.Charge e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.Charge.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.DesiredTypeOfProduct e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.DesiredTypeOfProduct.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.DesiredMortgageType e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.DesiredMortgageType.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.DesiredProductTerm e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.DesiredProductTerm.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.LendingGoal e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.LendingGoal.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.DownpaymentSource e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.DownpaymentSource.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.BorrowedSource e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.BorrowedSource.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.WhosLiving e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.WhosLiving.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.WhoPaysHeating e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.WhoPaysHeating.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoOpportunity.Looking e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoOpportunity.Looking.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoIncome.IncomeType e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoIncome.IncomeType.values()[i]) == 0;
		else
			return false;
	}

	public static boolean compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType e, String index)
    {
        int enumOrdinalValue = e.ordinal() + 1;
        if (!StringUtil.isNullOrEmpty(index)) {
            if (StringUtil.isInteger(index)) {
                final int parsedIndex = Integer.parseInt(index.trim());
                final boolean result = enumOrdinalValue == parsedIndex;
                logger.debug(">>>>> From Enum named=" + e.name() + ", ordinal enum value (plus one) is=" + enumOrdinalValue + ", and parsed index=" + parsedIndex + ", thus result is=" + result);

                return result;
            }
        }
        return false;
    }
	
	public static boolean compareSelection(AlgoLender.BonusCompMethod e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;
		int i = getIndex(index);
		if( i != -1)			
			return e.compareTo(AlgoLender.BonusCompMethod.values()[i]) == 0;
		else
			return false;
	}
	
	public static boolean compareSelection(AlgoAsset.AssetType e, String index)
	{
		if(index == null || index.trim().length() == 0)
			return false;

		try {
			int i = getIndex(index);
			if( i != -1)			
				return e.compareTo(AlgoAsset.AssetType.values()[i]) == 0;
			else
				return false;
		} catch (NumberFormatException ex) {
			return e.name().equalsIgnoreCase(index.trim());
		}
	}
	
	private static int getIndex(String index)
	{
		if(index != null)
		{
			try
			{
				return Integer.parseInt(index) - 1;
			}
			catch(NumberFormatException e)
			{
				logger.info(">>> incorrect index:{}. getIndex() method will return -1.", index);
				return -1;
			}
		}
		else
			return -1;
	}

	public static boolean compareSelection(AlgoIncome.Industry e, String index) {
		if(index == null || index.trim().length() == 0)
			return false;

		try {
			int i = getIndex(index);
			if( i != -1)			
				return e.compareTo(AlgoIncome.Industry.values()[i]) == 0;
			else
				return false;
		} catch (NumberFormatException ex) {
			return e.name().equalsIgnoreCase(index.trim());
		}
		
	}

}
