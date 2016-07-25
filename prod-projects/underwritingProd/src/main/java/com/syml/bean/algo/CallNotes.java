package com.syml.bean.algo;

public class CallNotes {
	/* There are 6 different times OpenERP will need interaction with The Underwriting Applicaiton:
    1) When the initial Application has been received via the Visdom Web Form and we are checking the "Desired Product"
       The Purpose of this interaction with the Underwriting application is primarily to understand the property infomation 
       and client credit and to generate for the Assistant a List of Notes that will be passed back to OpenERP as the Tasks 
       they need to complete on the opportunity.
       This interaction will be automated and will be triggered automatically from OpenERP when the webform has been completed 
       and all Opportunity / Applicant Data has been saved into OpenERP.  The Underwriting App needs only the OpportunityID 
       for this interaction.
       The UW App then queries OpenERP for the Opporutunity and Creates an Opportunity Class in Memory.
       With that Opportunity Class, UW App then creates a DesiredProductAlgorithm class.
       Within the DesiredProductAlgorithm class, the UW App queries the Products Table for Products that match the "Desired Product"
       Notes for this query are in line 55 of the DesiredProductAlgorithm class. 
       The list of Products returned by the query are then worked through in the Balance of the DesiredProductAlgorithm class.
       Once Completed, the list of Assistant Notes is then written into the Assistant Notes List within the Opportunity in OpenERP
       Also, the Product with the highest Fitness is also written back into OpenERP into the list of "Recommendations".
       For more detail, please see DesiredProductAlgorithm lines 165 - 190. 
     * The one thing that needs to be determined about this integration is the event / request that we want OpenERP to send 
     * to the underwriting App.  Cantor - Can you please begin a small requirements document for what you want this requext to contain.
     * In the interest of speed, if you already know python, you could even write it quickly and we could include the sample code in
     * a brief requirement document.  If you do not already know python, simply providing the required Webservice Information should suffice.
               
      
    2) Once the assistant has collected various tax documents and verified the information the client originally submitted, 
       we are ready to Underwrite all Products.  This interaction will be triggered manually from OpenERP with a button 
       in the Opportunity Record.  The Underwriting App needs only the OpportunityID for this interaction.
       The UW App then queries OpenERP for the Opporutunity and Creates an Opportunity Class in Memory.
       With that Opportunity Class, UW App then creates an AllProductsAlgorithm class.
       Within the AllProductsAlgorithm class, the UW App queries the Products Table for all Products that match the Province 
       Notes for this query are in line 50 of the AllProductsAlgorithm class. 
       The list of Products returned by the query are then worked through in the Balance of the AllProductsAlgorithm class.
       Once Completed, the list of Assistant Notes is then written into the Assistant Notes List within the Opportunity in OpenERP
       Also, the list of deal notes is written back into OpenERP and the list of Broker Notes.
       Also, the Product with the highest Fitness from each of the different types of products that have items are writtend 
       back into OpenERP into the list of "Recommendations".
       For more detail, please see AllProductsAlgorithm lines 1416 - 1445. 
      
     * The one thing that needs to be determined about this integration is the event / request that we want OpenERP to send 
     * to the underwriting App.  Cantor - Can you please begin a small requirements document for what you want this requext to contain.
     * In the interest of speed, if you already know python, you could even write it quickly and we could include the sample code in
     * a brief requirement document.  If you do not already know python, simply providing the required Webservice Information should suffice.
     
    3) Once the Broker has presented the Marketing Template with recommnedations to the Client and they have decided on 
       a single Product, we are ready to create final Assistant Tasks specific for that product and Lender.
       This interaction will be triggered manually from OpenERP with a button in the Opportunity Record (Verificaiton Tab).  
       The Underwriting App needs the OpportunityID and ProductID for this interaction.
       The UW App then queries OpenERP for the Opporutunity and the Product Database for the single client / Broker Slected product
       and Creates an Opportunity Class and Product class in Memory.
       With that Opportunity Class and product class, UW App then creates a PostSelectionAlgorithm class.
       Within the PostSelectionAlgorithm class, the UW App works the product through in the Balance of the PostSelectionAlgorithm class.
       Once Completed, the list of Assistant Notes, Deal Notes and Broker Notes are then written into the corresponding Lists within the Opportunity in OpenERP
       For more detail, please see PostSelectionAlgorithm lines 48 - 80. 
      
     * The one thing that needs to be determined about this integration is the event / request that we want OpenERP to send 
     * to the underwriting App.  Cantor - Can you please begin a small requirements document for what you want this requext to contain.
     * In the interest of speed, if you already know python, you could even write it quickly and we could include the sample code in
     * a brief requirement document.  If you do not already know python, simply providing the required Webservice Information should suffice.
     
    4) Once the Assistant has completed all fina tasks in preparation for Sending to the Lender, specific for that product and Lender.
       we are ready to send the Opportunity and Selected Product to UW App for creation of Lender Notes, Final Verification and Commission Calculations
       This interaction will be triggered manually from OpenERP with a button in the Opportunity Record (Final Solution Tab).  
       The Underwriting App needs the OpportunityID and ProductID for this interaction.
       The UW App then queries OpenERP for the Opporutunity and the Product Database for the single client / Broker Slected product
       and Creates an Opportunity Class and Product class in Memory.
       With that Opportunity Class and product class, UW App then creates a FinalVerifyAlgorithm class.
       Within the FinalVerifyAlgorithm class, the UW App works the product through in the Balance of the FinalVerifyAlgorithm class.
       Once Completed, the list of Assistant Notes, Deal Notes, Broker Notes and Lender Notes are then written into the corresponding Lists within the Opportunity in OpenERP
       Also written in to Opportunity fields in OpenERP is the Data in the Final Verify Tab of Opportunity Record
       For more detail, please see FinalVerifyAlgorithm lines 48 - 80. 
      
     * The one thing that needs to be determined about this integration is the event / request that we want OpenERP to send 
     * to the underwriting App.  Cantor - Can you please begin a small requirements document for what you want this requext to contain.
     * In the interest of speed, if you already know python, you could even write it quickly and we could include the sample code in
     * a brief requirement document.  If you do not already know python, simply providing the required Webservice Information should suffice.
     
    
       5) Update of Products upon change, delete or add to List of Products in OpenERP
     * 6) Update of Lenders upon change, delete or add to List of Lenders in OpenERP
    */
}
