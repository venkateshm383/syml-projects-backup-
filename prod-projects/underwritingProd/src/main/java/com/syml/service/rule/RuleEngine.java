package com.syml.service.rule;

import java.util.HashMap;

import com.syml.domain.Opportunity;

public class RuleEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Opportunity opp1 = new Opportunity();
		opp1.setId(11);
		int resultId1 = RuleEngine.getInstance().startProcess(opp1);
		Opportunity opp2 = new Opportunity();
		opp2.setId(101);
		int resultId2 = RuleEngine.getInstance().startProcess(opp2);
		while(true)
		{
			RuleResult result = RuleEngine.getInstance().getResult(resultId1);
			if(result != null)
				System.out.println("id :" + resultId1  + " " + result.toString());
			result = RuleEngine.getInstance().getResult(resultId2);
			if(result != null)
				System.out.println("id :" + resultId2  + " " + result.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private static RuleEngine instance;
	
	private RuleEngine()
	{
		
	}
	
	public static RuleEngine getInstance()
	{
		if(instance == null)
			instance = new RuleEngine();
		return instance;
	}
	
	int id = 0;
	
	private HashMap<Integer, RuleResult> results = new HashMap<Integer, RuleResult>();
	
	public synchronized int startProcess(Opportunity opp)
	{
		int resultId = generateId();
		RuleResult result = new RuleResult();
		
		BaseRule rule = new BaseRule(result, opp);
		results.put(resultId, result);
		RuleQueue.getInstance().execute(rule);
		return resultId;
	}
	
	public RuleResult getResult(int resultId)
	{
		return results.get(resultId);
	}
	
	private int generateId()
	{
		return ++id;
	}

}
