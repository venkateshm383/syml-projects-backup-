package document;

import helper.Odoo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


import net.iharder.Base64;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import play.Logger;


import controllers.Application;
import controllers.CompressZipFolderExample;
import controllers.Pages;

/**
 * 
 * @author udaykiran.pulipati
 * 
 */

@SuppressWarnings("unchecked")
public class ExtractPagesFromPdfAndSaveAsNewPDFPage {
	
	private static org.slf4j.Logger logger = play.Logger.underlying();

	public ArrayList pdfFilePath(ArrayList<Pages> pagesList, byte[] content) {

		String filePath="";
		String path="";
    	Properties prop=null;
    	UUID id1 = UUID.randomUUID();
		String uniqueId = id1.toString();
    	try{
    		prop=new Odoo().readConfigfile();
    		path=prop.getProperty("path");
    		filePath=CompressZipFolderExample.writeByteArraysToFile(path+""+uniqueId+".pdf", content);
    	}catch(Exception e){
    		 logger.error("Error is odoo connection"+e.getMessage());
    	}
		
		boolean properPages = false;
		ArrayList listOFdata = new ArrayList();
		try {
		
			/*	BufferedOutputStream writer = new BufferedOutputStream(
					new FileOutputStream(file));
			writer.write(content);
			writer.flush();
			writer.close();*/

			String sourceDir = filePath;
			String destinationDir = path;
			File oldFile = new File(sourceDir);
			String fileName = oldFile.getName().replace(".pdf", "");
			if (oldFile.exists()) {
				File newFile = new File(destinationDir);
				if (!newFile.exists()) {
					newFile.mkdir();
				}

				int fromPageOne = 0;
				int toPageOne = 0;

				int fromPageTwo = 0;
				int toPageTwo = 0;

				int fromPageThree = 0;
				int toPageThree = 0;

				int fromPageFour = 0;
				int toPageFour = 0;

				int fromPageFive = 0;
				int toPageFive = 0;

				int fromPageSix = 0;
				int toPageSix = 0;
				int i = 0;
				for (Iterator iterator = pagesList.iterator(); iterator
						.hasNext();) {
					Pages pages = (Pages) iterator.next();
					++i;
					switch (i) {
					case 1:
						fromPageOne = pages.getFromPage();
						toPageOne = pages.getToPage();
						break;

					case 2:
						fromPageTwo = pages.getFromPage();
						toPageTwo = pages.getToPage();
						break;

					case 3:
						fromPageThree = pages.getFromPage();
						toPageThree = pages.getToPage();
						break;

					case 4:
						fromPageFour = pages.getFromPage();
						toPageFour = pages.getToPage();
						break;

					case 5:
						fromPageFive = pages.getFromPage();
						toPageFive = pages.getToPage();
						break;

					case 6:
						fromPageSix = pages.getFromPage();
						toPageSix = pages.getToPage();
						break;

					default:
						break;
					}

				}

				PDDocument document = PDDocument.load(sourceDir);
				List<PDPage> list = document.getDocumentCatalog().getAllPages();
int pdfSize=list.size();
				int pageNumber = 1;
				PDDocument newDocument = new PDDocument();
				if (!pagesList.isEmpty()) {
					if(toPageOne<=pdfSize&&toPageTwo<=pdfSize&&toPageFive<=pdfSize&&toPageFour<=pdfSize&&toPageThree<=pdfSize&&toPageSix<=pdfSize&&fromPageOne<=pdfSize&&fromPageTwo<=pdfSize&&fromPageFive<=pdfSize&&fromPageFour<=pdfSize&&fromPageThree<=pdfSize&&fromPageSix<=pdfSize){

					for (PDPage page : list) {

						if (pageNumber >= fromPageOne
								&& pageNumber <= toPageOne) {
							newDocument.addPage(page);

						} else if (pageNumber == fromPageOne) {
							newDocument.addPage(page);

						} else if (pageNumber >= fromPageTwo
								&& pageNumber <= toPageTwo) {
							newDocument.addPage(page);

						} else if (pageNumber == fromPageTwo) {
							newDocument.addPage(page);

						} else if (pageNumber >= fromPageThree
								&& pageNumber <= toPageThree) {
							newDocument.addPage(page);

						} else if (pageNumber == fromPageThree) {
							newDocument.addPage(page);

						} else if (pageNumber >= fromPageFour
								&& pageNumber <= toPageFour) {
							newDocument.addPage(page);

						} else if (pageNumber == fromPageFour) {
							newDocument.addPage(page);

						}

						else if (pageNumber >= fromPageFive
								&& pageNumber <= toPageFive) {
							newDocument.addPage(page);

						} else if (pageNumber == fromPageFive) {
							newDocument.addPage(page);

						}

						else if (pageNumber >= fromPageSix
								&& pageNumber <= toPageSix) {
							newDocument.addPage(page);

						} else if (pageNumber == fromPageSix) {
							newDocument.addPage(page);

						}
						pageNumber++;

					}
				logger.debug("page naumner" + pageNumber);
					newFile = new File(destinationDir + fileName + "_"
							+ pageNumber + ".pdf");
					filePath = destinationDir + fileName + "_" + pageNumber
							+ ".pdf";
					newDocument.save(newFile);
					newDocument.close();
					properPages = true;
					}else{
						properPages = false;

					}
				} else {
					properPages = false;

				}
			} else {
				logger.debug(fileName + " File not exists");
			}
			try {

				logger.debug("" + filePath);
				oldFile.delete();
				logger.debug("original pdf deleted");
			} catch (Exception e) {
				 logger.error("Error is pdfFilePath"+e.getMessage());
			}
		} catch (Exception e) {
			properPages = false;

			 logger.error("Error is pdfFilePath"+e.getMessage());
		}
		listOFdata.add(filePath);
		listOFdata.add(properPages);
		return listOFdata;
	}
}