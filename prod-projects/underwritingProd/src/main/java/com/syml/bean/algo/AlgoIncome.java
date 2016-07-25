package com.syml.bean.algo;

import com.syml.domain.Income;

public class AlgoIncome
{
	public Income income;
    //public Guid ApplicantID; // May not be required depending on WebServices Design, but I put it here so we can ensure that the Right List of Liabilites ends up with the Right Applicant

    public enum IncomeType { Employed, SelfEmployed, Retired, Commission, Interest, Pension, Overtime, Bonus, Rental, ChildTaxCredit, LivingAllowance, VehicleAllowance, Other };
    //public IncomeType typeOfIncome;
    //public String business; // Company that is paying the income to the applicant
    //public String position; // Not required for underwriting, but Lenders Require in their applications, this it exists in the OpenERP Database
    public enum Industry { BankingFinance, Government, Education, Health, Manufacturing, Services, ResourcesTransportation, Other };  // Not required for underwriting, but Lenders Require in their applications, this it exists in the OpenERP Database
    //public double annualIncome; // Incomes in WebForm are captured with period and type.   For simplicity, Webform will convert all incomes to Annual from Bi-weekly, monthly, hourly, etc before populating OpenERP
    //public int months; // This in income duration.  How long has the income been occurring?  It affects the underwriting algorithm for some products 

    public AlgoIncome(Income income)
    {
    	this.income = income;
        // Cantor - I left the constructor for your coding depending on how you are managing the WebService Sending the Data.
    }

}
