package JavamailMessagCounter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.iharder.Base64;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class ImagePdf {
	

	public static void pdfImage(   ArrayList<String> list,String file1) throws IOException, COSVisitorException{
		
		PDDocument document =new PDDocument();

		BufferedImage image=null;
		for(int i=0;i<list.size();i++){
			PDPage page1 =new PDPage(PDPage.PAGE_SIZE_A4);
			File filedata=new File(list.get(i));
			String pdfFile=Base64.encodeFromFile(list.get(i));
			PDRectangle rect=page1.getMediaBox();
			document.addPage(page1);
			byte[] decodedBytes = Base64.decode(pdfFile);
			PDPageContentStream cos=new PDPageContentStream(document, page1);

			if (decodedBytes == null) {
				System.out.println("decodedBytes  is null");
			}

			image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
			PDFont fontplain = PDType1Font.TIMES_ROMAN;
			PDFont fontbold=PDType1Font.HELVETICA_BOLD;
			PDFont fontItalic =PDType1Font.HELVETICA_OBLIQUE;
			PDFont fontMono=PDType1Font.COURIER;
			
			
			
			
			int line=0;
			  
		
			int line2=500;

		  try {
	           // BufferedImage awtImage = ImageIO.read(new File("simple.jpg"));
	            PDXObjectImage ximage = new PDPixelMap(document, image);
	           float scale = 0.35f; // alter this value to set the image size
	        //  cos.drawImage(ximage, 180, 700);
	          cos.drawXObject(ximage,70,rect.getHeight() -100-line2 , ximage.getWidth()*scale, ximage.getHeight()*scale);
		  } catch (Exception fnfex) {
	            System.out.println("No image for you");
	        }
	        cos.close();
	        System.out.println("image is craeted");
		}
		

		document.save(file1);
		document.close();
			
			
			
			
	}

}
