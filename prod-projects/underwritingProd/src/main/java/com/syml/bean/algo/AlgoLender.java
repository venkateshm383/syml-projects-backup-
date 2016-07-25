package com.syml.bean.algo;

import com.syml.domain.Lender;

public class AlgoLender
{
	
	public Lender lender;
	
    // Other fields are required, but for the moment purposes of underwriting, I am just including threshold related info ... 
    public double ytdVolume;
    public double rolling12MoVolume;
    public double q1Volume;
    public double q2Volume;
    public double q3Volume;
    public double q4Volume;
    public double janVolume;
    public double febVolume;
    public double marVolume;
    public double aprVolume;
    public double mayVolume;
    public double juneVolume;
    public double julyVolume;
    public double augVolume;
    public double septVolume;
    public double octVolume;
    public double novVolume;
    public double decVolume;
//    public String lenderName;
//
    public enum BonusCompMethod { YearToDate, Monthly, Rolling12Month, Quarterly }
    public BonusCompMethod bonusCompPeriod;
    
    
    public AlgoLender(Lender lender){
    	this.lender = lender;
    }
    
}
