package com.syml.bean.algo;

import com.syml.domain.Asset;

public class AlgoAsset
{        
	public Asset asset;
    public enum AssetType { Property, NonRRSPs, Savings, RRSPs, Gift, Vehicle, StocksOrBondsOrMutuals, HouseholdGoods, LifeInsurance, DepositOnPurchase, other }; // Type is used in determining whether Liquid in order to qualify for certain products
    //public AssetType assetType;
    //public String name; // Description entered by Applicant, Not needed in Algo, but needed by Lender
    //public double value; // Current Market Value of Asset
    
    // Not Presently In OpenERP
    public boolean selling; // True if the Property is being sold and Property Asset will not longer be in place thus not included in TotalAssets of Applicant.

    // No Property Taxes in the event it is a Property... 

    public AlgoAsset(Asset asset)
    {
        // Cantor - I left most of the constructor for your coding depending on how you are managing the WebService Sending the Data.
       this.asset = asset;
    }


}
