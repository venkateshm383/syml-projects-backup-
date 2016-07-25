package helper;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import controllers.DocoSolo;

public class CheckRecivedDocuments {
	
	
	
	public static ArrayList<DocoSolo> checkRecivedDocuments(String opprtunityId){
		CouchBaseOperation couchbase=new CouchBaseOperation();
		ArrayList<DocoSolo> recivedDocumentList=new ArrayList<DocoSolo>();
		
		
		
		
		JSONObject oldSplited = null;
		try {
			oldSplited = couchbase.getCouchBaseData("DocumentSplitList"
					+ opprtunityId);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {

				for (int i = 0; i < jsonArray.length(); i++) {

					DocoSolo docData = new DocoSolo();
					JSONObject jsonData = couchbase.getCouchBaseData(jsonArray
							.get(i).toString());
					docData.setDocId(jsonArray.get(i).toString());
					docData.setRelatedDoc(jsonData.get("DocumentName")
							.toString());
					docData.setDocumentType(jsonData.get("documenTypeContent")
							.toString());
					docData.setLink(jsonData.get("link").toString());
					recivedDocumentList.add(docData);
				}

			} catch (Exception e) {

			}
		} catch (Exception e) {

		}

		
		return recivedDocumentList;
		
	}
	
	
	public static ArrayList<DocoSolo> checkRecived(String opprtunityId){
		CouchBaseOperation couchbase=new CouchBaseOperation();
		ArrayList<DocoSolo> recivedDocumentList=new ArrayList<DocoSolo>();
		
		
		
		
		JSONObject oldSplited = null;
		try {
			oldSplited = couchbase.getCouchBaseData("DocumentSplitList"
					+ opprtunityId);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {

				for (int i = 0; i < jsonArray.length(); i++) {

					DocoSolo docData = new DocoSolo();
					JSONObject jsonData = couchbase.getCouchBaseData(jsonArray
							.get(i).toString());
					docData.setDocId(jsonArray.get(i).toString());
					docData.setRelatedDoc(jsonData.get("DocumentName")
							.toString());
					docData.setDocumentType(jsonData.get("documenTypeContent")
							.toString());
					docData.setLink(jsonData.get("link").toString());
					recivedDocumentList.add(docData);
				}

			} catch (Exception e) {

			}
		} catch (Exception e) {

		}

		
		return recivedDocumentList;
		
	}

}
