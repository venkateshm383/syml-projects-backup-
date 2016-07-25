/**
 * DAOI.java
 */
package postgresdao;

import dto.Applicant;
import dto.Opportunity;

/**
 * 
 * @author bizruntime.
 *
 */
public interface PostgresDAOI{
	
	
	//TO READ THE OPPORTUNITY DETAILS
	public Opportunity getOpportunityDetails(String opportunityId) ;
	
	
}
