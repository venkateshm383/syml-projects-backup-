package Infrastracture;

import java.io.ByteArrayInputStream;
import java.util.GregorianCalendar;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class EmailDataFromMailGun {
	
	public static void main(String[] args) throws JSONException {

String myvar = "{"+
"  \"X-Mailgun-Sid\": ["+
"    \"WyJiNzRlMyIsICJndXlAdmlzZG9tLmNhIiwgIjQ3N2NlMyJd\""+
"  ],"+
"  \"swu_template_version_id\": ["+
"    \"ver_uP6Gpj9GRrqY5jLA3Gq3uf\""+
"  ],"+
"  \"domain\": ["+
"    \"go.visdom.ca\""+
"  ],"+
"  \"X-Mailgun-Tag\": ["+
"    \"Client\""+
"  ],"+
"  \"receipt_id\": ["+
"    \"log_c95f71d893fb82e0456e4ef7f6cdb9b4\""+
"  ],"+
"  \"swu_template_id\": ["+
"    \"tem_2UVVLDiFtRR3oMEBukDfWT\""+
"  ],"+

"  \"Message-Id\": ["+
"    \"<20150724151959.22235.73506@go.visdom.ca>\""+
"  ],"+
"  \"recipient\": ["+
"    \"guy@visdom.ca\""+
"  ],"+
"  \"event\": ["+
"    \"delivered\""+
"  ],"+
"  \"timestamp\": ["+
"    \"1437751199\""+
"  ],"+
"  \"token\": ["+
"    \"a625871f4cfb8758f69279a7efea1ddd09a12525880a6b184e\""+
"  ],"+
"  \"signature\": ["+
"    \"4497079e3e71ca96c3f185457ed9e01fcef4286ea15019473b6b847863597a2b\""+
"  ],"+
"  \"body-plain\": ["+
"    \"\""+
"  ],"+
"  \"submissionDate\": \"2015/07/24 15:19:49\""+
"}";
	
JSONObject jsonEmailData=new JSONObject(myvar);


		new EmailDataFromMailGun().storeMailGundata(jsonEmailData);
	}
	
	
	public void storeMailGundata(JSONObject jsonData) throws JSONException{
		CouchBaseOperation couchbase=new CouchBaseOperation();
	
		String toEmail="";
	
		System.out.println("data  "+jsonData);
try{
	
JSONArray jsonArr	=new JSONArray(jsonData.get("recipient").toString());
for (int i = 0; i < jsonArr.length(); i++) {
	toEmail=jsonArr.get(0).toString();
	
			}
}catch(Exception e){
	e.printStackTrace();
}

		
	/*	String toEmail="";
		
		String content="";
		
		String timeStamp="";
		String sentDate="";
		String attachment="";
		JSONObject jsonEmailData=new JSONObject();
		JSONObject jsonAttachmentData=new JSONObject();
		try{
			jsonAttachmentData.put("attachment", attachment);
			couchbase.storeDataInCouchBase("MailGun_Attachment_"+fromEmail+"_"+sentDate, jsonAttachmentData);
		}catch(Exception e){
			
		}
		jsonEmailData.put("fromEmail", fromEmail);
		jsonEmailData.put("toEmail", toEmail);

		jsonEmailData.put("content", content);

		jsonEmailData.put("sentDate", sentDate);*/

		GregorianCalendar calender=new GregorianCalendar();
		
		couchbase.storeDataInCouchBase("Mailgun_SentMail_To_"+toEmail+"_"+calender.getTime(), jsonData);
		
	}
	

}
