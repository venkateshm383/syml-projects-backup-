package com.syml.test.junit;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;

public class AddressTest {

	public static void main(String[] args) {

		
		
		Session openERPSession = new Session("107.23.27.61", 8069, "symlsys", "guy@visdom.ca", "VisdomTesting");
		try {
		       // startSession logs into the server and keeps the userid of the logged in user
		       openERPSession.startSession();
		       /*ObjectAdapter propertyAd = openERPSession.getObjectAdapter("applicant.record");
				ObjectAdapter propertyAd2 = openERPSession.getObjectAdapter("applicant.property");
				ObjectAdapter propertyAd3 = openERPSession.getObjectAdapter("applicant.mortgage");

*/
				
				ObjectAdapter applicantAd = openERPSession.getObjectAdapter("applicant.record");
				//ObjectAdapter addressAd1 = openERPSession.getObjectAdapter("applicant.address");
				ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("crm.asset");
		       
		       
		       FilterCollection filters = new FilterCollection();
		       filters.add("id","=",2173);
		    // RowCollection partners = addressAd.searchAndReadObject(filters, new String[]{"applicant_name","address_ids"});

		     
		       /*for (Row row : partners){
		           System.out.println("address_ids:" + row.get("address_ids".toString()));
		           System.out.println("applicant_name:" + row.get("applicant_name"));
		       }*/
System.out.println("Displaying..................");
/*for (Row row : partners){
Object [] address_ids = (Object[]) row.get("address_ids");
if (address_ids != null){
  System.out.println("Addresses:");
  RowCollection addresses = addressAd1.readObject(address_ids, new String[]{"name", "street", "city","province","postal_code","date"});
  int i = 1;
  for (Row ad : addresses){
   System.out.println("  Number: " + String.valueOf(i));
    System.out.println("  name: " + ad.get("name"));
    System.out.println("  Street: " + ad.get("street"));
    System.out.println("  City: " + ad.get("city"));
    System.out.println("  province: " + ad.get("province"));
    System.out.println("  postal_code: " + ad.get("postal_code"));
    System.out.println("  date: " + ad.get("date"));


    i++;
  }*/
  
 /* Row ad = addressAd1.getNewRow(new String[]{"name", "street", "city","province","postal_code","date"});
  ad.put("name", "54 Strathcona Close SW");
	ad.put("street", "AZ T3H 1L3");
	ad.put("city", "Calgary");
	ad.put("province", "Canada");
	ad.put("postal_code", "76787");
	ad.put("date", "");

  addressAd1.createObject(ad);

  System.out.println("New Row ID: " + ad.getID());
*/

//Object[] ids = new Object[] {0,1,2,3,4,5};
  FilterCollection filters1 = new FilterCollection();
  filters.add("id","=",2166);
//  RowCollection partners1 = addressAd.searchAndReadObject(filters1, new String[]{"applicant_name","address_ids"});

  // You could do some validation here to see if the customer was found
  //Row AgrolaitRow = partners1.get(0);
 // AgrolaitRow.put("applicant_name", "sali");
 // AgrolaitRow.put("address_ids",5022);
 // AgrolaitRow.putMany2ManyValue("address_ids", new Object[]{5022}, true);
  

  // Tell writeObject to only write changes ie the name isn't updated because it wasn't changed.
 // boolean success = addressAd.writeObject(AgrolaitRow, true);

  
  Boolean bool=true;
  /*if (success)
      System.out.println("Update was successful");
*/
  Integer i=2000;
 
 Row ad = applicantAd2.getNewRow(new String[]{"asset_type", "name","value","opportunity_id"});
	
  
	ad.put("asset_type", "Vehicle");
	ad.put("name", "Developer");
	ad.put("value", "1234");
	ad.put("opportunity_id", 2166);

	applicantAd2.createObject(ad);
  
  System.out.println("Assets Created");
  
  
  
  
  //System.out.println("Displayed address.................."+i);



System.out.println("===================");

		     
		   
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
/*Object [] address_ids = (Object[]) new Object();

		       Row newPartner = addressAd.getNewRow(new String[]{"applicant_name","name"});
		       newPartner.put("applicant_name", "love");
		       newPartner.put("name","banglore");

		       System.out.println("created");

		       
		       
		       addressAd.createObject(newPartner);

		       System.out.println("New Row ID: " + newPartner.getID());*/

		       
		}catch (Exception e) {
e.printStackTrace();			}
	}

}
