package controllers;

import helper.CouchBaseOperation;
import helper.Odoo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
 
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.iharder.Base64;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
 
public class CompressZipFolderExample {
	
	public static void main(String[] args) throws IOException {
		String filename="Floor plans/ specs/test";
	
		
	File file=new File("/home/venkateshm/Desktop/Untitled Folder");
FileInputStream fildata=new FileInputStream(file);
	
delete(file);

System.out.println("fiel"+fildata.available());
	
	/*	
		String array[]=filename.split("/");
		String hello="";
		for (int i = 0; i < array.length; i++) {
			hello=hello+array[i];
			System.out.println(hello);
		}*/
	}
	
	 public static void delete(File file)
		    	throws IOException{
		 
		    	if(file.isDirectory()){
		 
		    		//directory is empty, then delete it
		    		//directory is empty,thend delete it
		    		if(file.list().length==0){
		    			
		    		   file.delete();
		    		   System.out.println("Directory is deleted : " 
		                                                 + file.getAbsolutePath());
		    			
		    		}else{
		    			
		    		   //list all the directory contents
		        	   String files[] = file.list();
		     
		        	   for (String temp : files) {
		        	      //construct the file structure
		        	      File fileDelete = new File(file, temp);
		        		 
		        	      //recursive delete
		        	     delete(fileDelete);
		        	   }
		        		
		        	   //check the directory again, if empty then delete it
		        	   if(file.list().length==0){
			    		   file.delete();
		        	     System.out.println("Directory is deleted : " 
		                                                  + file.getAbsolutePath());
		        	   }
		    		}
		    		
		    	}else{
		    		//if file, then delete it
		    		   file.delete();
		    		System.out.println("File is deleted : " + file.getAbsolutePath());
		    	}
		    }
 
    public static ArrayList<String> download(String id ) throws Exception{
    	
    	
    	ArrayList<String> arrayList=new ArrayList<String>();
    	
    	CouchBaseOperation couchBaseOperation=new CouchBaseOperation();
    	
    	String ApplicantFirstname="";
    	String ApplicantLastName="";

    	String leandingGoal="";

    	try{
    		JSONObject doclist = null;
    		try {
    			doclist = couchBaseOperation
    					.getCouchBaseData("DocumentListOfApplicationNo_"
    							+ id);
    			ApplicantFirstname=doclist.get("firstName").toString();
    			ApplicantLastName=doclist.get("lastName").toString();
    			leandingGoal=doclist.get("lendingGoal").toString();
    		} catch (Exception e) {

    		}
    	}catch(Exception e){
    		
    	}
    	String filePath="";
    	Properties prop=null;
    	try{
    		prop=new Odoo().readConfigfile();
    		filePath=prop.getProperty("path");
    	}catch(Exception e){
    		
    	}
    	//System.out.println("file craeted"+file.mkdirs());
    	String opprtunity="";
    	
  opprtunity=ApplicantFirstname.trim()+"_"+ApplicantLastName.trim()+"_"+leandingGoal+"_"+id;
    	String path=filePath+opprtunity+"/"+opprtunity;
    	File file=new File(path);
    	file.mkdirs();
    	JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + id);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());
			String filename="";
			String attachment="";
			 byte []  content=null;
				String slpitedFile="";

			try {
				for (int i = 0; i < jsonArray.length(); i++) {
					slpitedFile="";
					JSONObject jsonData = couchBaseOperation
				
						.getCouchBaseData(jsonArray.get(i).toString());
					try{
			 filename=jsonData.get("DocumentType")
					.toString();
			 try{
				String array[]=filename.split("/");
			
				for (int k = 0; k< array.length; k++) {
					slpitedFile=slpitedFile+array[k];
					System.out.println(slpitedFile);
				}
			 }catch(Exception e){
				 
			 }
					System.out.println("documentType ------------"+slpitedFile);
			JSONObject jsonDataAttachemnt = couchBaseOperation
					
					.getCouchBaseData(
					jsonData.get("DocSoloAttachment")
					.toString());
			 attachment=jsonDataAttachemnt.get("attachement").toString();
			 content=Base64.decode(attachment);
					}catch(Exception e){
						
					}
					writeByteArraysToFile(path+"/"+slpitedFile+".pdf", content);
					
				}}catch(Exception e){
					e.printStackTrace();
				}
		}catch(Exception e){
			
		}
    	
    	
    	
    	
 
        String sourceFolderName =  path;
        String outputFileName = filePath+opprtunity+".zip";
 
        FileOutputStream fos = new FileOutputStream(outputFileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        //level - the compression level (0-9)
        zos.setLevel(9);
 
        System.out.println("Begin to compress folder : " + sourceFolderName + " to " + outputFileName);
        addFolder(zos, sourceFolderName, sourceFolderName);
 
        zos.close();
        System.out.println("Program ended successfully!");
        arrayList.add(opprtunity+".zip");
        arrayList.add(filePath+opprtunity);
        arrayList.add(outputFileName);

		return arrayList;
    }
 
    private static void addFolder(ZipOutputStream zos,String folderName,String baseFolderName)throws Exception{
        File f = new File(folderName);
        if(f.exists()){
 
            if(f.isDirectory()){
                //Thank to peter 
                //For pointing out missing entry for empty folder
                if(!folderName.equalsIgnoreCase(baseFolderName)){
                    String entryName = folderName.substring(baseFolderName.length()+1,folderName.length()) + File.separatorChar;
                    System.out.println("Adding folder entry " + entryName);
                    ZipEntry ze= new ZipEntry(entryName);
                    zos.putNextEntry(ze);    
                }
                File f2[] = f.listFiles();
                for(int i=0;i<f2.length;i++){
                    addFolder(zos,f2[i].getAbsolutePath(),baseFolderName);    
                }
            }else{
                //add file
                //extract the relative name for entry purpose
                String entryName = folderName.substring(baseFolderName.length()+1,folderName.length());
                System.out.print("Adding file entry " + entryName + "...");
                ZipEntry ze= new ZipEntry(entryName);
                zos.putNextEntry(ze);
                FileInputStream in = new FileInputStream(folderName);
                int len;
                byte buffer[] = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
                zos.closeEntry();
                System.out.println("OK!");
 
            }
        }else{
            System.out.println("File or directory not found " + folderName);
        }
 
    }
    public static String writeByteArraysToFile(String fileName, byte[] content) throws IOException {
    	 
        File file = new File(fileName);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        writer.write(content);
        writer.flush();
        writer.close();
        return fileName;
 
    }
}
