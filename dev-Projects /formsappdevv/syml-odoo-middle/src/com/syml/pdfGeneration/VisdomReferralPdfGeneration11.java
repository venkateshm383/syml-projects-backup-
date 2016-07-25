package com.syml.pdfGeneration;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sendwithus.SendWithUsExample;
import com.syml.helper.GenericHelperClass;
import com.syml.openerp.ReferalToCreateReferralResoursce;

public class VisdomReferralPdfGeneration11 {
	
	public static void main(String[] args) throws IOException {
	String path=	VisdomReferralPdfGenerationMethod("test", "no", "test", "other", null);
		System.out.println(path);
		new SendWithUsExample().sendTOReferralSourceAndroid("tset", "https://dev-formsapp.visdom.ca/formsapp/SubmitReferalform1D.html", "venkatesh.m@bizruntime.com",path);

	}
	static Logger log = LoggerFactory.getLogger(VisdomReferralPdfGeneration11.class);

	public static String  VisdomReferralPdfGenerationMethod(String referralName,String areusingTouchScreenDevice,String myTypedName,String role,BufferedImage image ){
	
		
		
		
		
		
		
	String	referral1="WHEREAS the Referrer has a potential contact base which Visdom Mortgage Solutions Inc. (\"Visdom\") can benefit from for the";
				/*referral2=purpose of assisting Visdom in selling and distributing the products and services now and hereafter offered by Visdom;
				referral3=AND WHEREAS Visdom desires to compensate the Referrer for providing Referrals to Visdom;
				referral4=NOW THEREFORE THIS AGREEMENT WITNESSES THAT in consideration for the respective covenants and agreements of the parties
				referral5=contained herein and other good and valuable consideration (the receipt and sufficiency of which is hereby acknowledged by each
				referral6=of the parties) it is agreed as follows:  


				referral7=1. DEFINITIONS

				referral8=1.1  "Borrower" means a person who is referred to Visdom by a Referrer for the purpose of receiving funding for a Trade in Real Estate.

				referral9=1.2  "Construction Draw Mortgage" is a Mortgage where the Borrower receives Funding on a property under construction and
				referral10=Funding is provided at various stages of completion of the construction.

				referral11=1.3  "Funding" means the day on which the lender or financial institution advances funds to the Borrower on a Mortgage Trade.

				referral12=1.4  "Mortgage" means a mortgage of real property, of a lease of real property, or of a mortgage of real property or a lease of "
				referral13=real property OR any charge on real property, on a lease of real property, or on a mortgage of real property or a lease of real property"
				referral14=FOR THE PURPOSES OF securing the repayment of money or another consideration."
				referral15=1.5 "Real Estate" means real property, lease hold property or a portable dwelling other than a holiday trailer or recreational 
				referral16=vehicle wholly or mainly used for recreational purposes that is defined for use as and is used as a residence, is mounted on or 
				referral17=otherwise attached to its own  chassis  and running gear, is capable of being transported on its own chassis and running gear by towing
				referral18=or other means, and is situated on a site that is used or intended to be used, or that has been represented by the owner of the site 
				referral19=as being intended to be used, for residential purposes.

				referral20=1.6  "Referral" shall mean the act of recommending or directing a person for service, assistance or business for a Trade of Real 
				referral21=Estate regarding a new Mortgage or Mortgage renewal to Visdom.


				referral22=1.7  "Referrer" means a party who recommends or directs a person, including themselves, for service, assistance or business for
				referral23=a new mortgage or mortgage renewal in the trade of real estate to Visdom. The Referrer may be an individual licensed by a governing 
				referral24=provincial reality body, an unlicensed individual or a Canadian corporate entity incorporated in at least one province in Canada 
				referral25=(hereinafter referred to as the Referrer Corporation.)

				referral26=1.8  "Referral" Fee means the fee earned by a Referrer for providing a Referral to Visdom for a Mortgage on Real Estate that results
				referral27=in a Funding.

				referral28=1.9  "Referral" means a list of all of the Referrals made by the Referrer to Visdom.


				referral29=1.10 "Renewal" means a renewal on a Mortgage that results in a brokerage fee to Visdom.

				referral30=1.11 "Renewal Fee" means the fee that is earned upon the Trade in Real Estate for a Mortgage Renewal by the same Borrower on a 
				referral31= transaction that previously resulted in the receipt of a Referral Fee by the Referrer from Visdom.

				referral32=1.12 "Trade" includes a disposition or acquisition of, or transaction in, real estate by purchase or sale; an offer to purchase   
				referral33=or sell real estate; an offering, advertisement, listing or showing of real estate for purchase or sale; property management; 
				referral34=holding oneself out as  trading in real estate; the solicitation, negotiation or obtaining of a contract, agreement or any   
				referral35=arrangement for an activity referred to above;collecting, or offering or attempting to collect, on behalf of the owner or other 
				referral36=person in charge or real estate, money payable as rent for the use of real estate or contributions for the control, management or 
				referral37=administration of the real estate; or any conduct or act in furtherance or attempted furtherance of any activity referred to above. 


				referral38=2. REFERRAL ARRANGEMENT

				referral39=2.1  Referrer has the right but not the obligation to refer and forward potential customers to Visdom or its related entity, 
				referral40=successor or assign in consideration for the Referral Fees or Renewal Fees.

				referral41=2.2  Any Referrals made shall be made on an as is, where is basis and Visdom shall have the responsibility of determining the  
				referral42=suitability of any such Referrals as well as the discretion (but not the obligation) as to whether accept each such Referral.

				referral43=3.  REMUNERATION
				referral44=3.1  Visdom shall pay a Referral Fee or Renewal Fee to the Referrer, the amount of the Referral Fee or Renewal Fee shall be  
				referral45=determined based on Schedule ("Fee Schedule").

				referral46=3.2  Visdom has the right to alter, amend and modify Schedule B, without notice or consent of the Referrer.

				referral47=3.3  If a Referrer is a licensed individual as described above they hereby assign and direct their Referral Fee or Renewal Fee 
				referral48=to their brokerage. If the Referrer is a licensed individual Visdom will pay the Referral Fee or Renewal Fee to the brokerage,
				referral49=not the Referrer directly.

				referral50=3.4  If the Referrer is an unlicensed individual, Visdom will pay the Referral Fee or Renewal Fee directly to the individual.

				referral51=3.5  If, and only if, the Referrer is a Referrer Corporation incorporated in at least one Province of Canada and in accordance  
				referral52=with the laws of any Province in Canada, Schedule ("Additional Referrer Corporation Terms") shall apply to this Agreement. 

				referral53=3.6  The Referral Fee or Renewal Fee arising out of or related to such Referrals from the Referrer, shall be inclusive of all 
				referral54=sales taxes. The Referrer is solely responsible to file all appropriate reports, elections and returns pertaining to this 
				referral55=Agreement.The Referrer is responsible for any sums that may be payable by the Referrer under the Excise Tax Act (Canada) 
				referral56=including but not limited to Goods and Services Tax, interest and/or penalties.

				referral57=3.7  Visdom and the Referrer shall both keep a Referral List. The first party preceding all others in time to make a Referral
				referral58=of a Borrower to Visdom shall be entitled to the Referral Fee or Renewal Fee if and only if the Borrower's Mortgage or Renewal 
				referral59=is Funded within six (6) months of the Referral. In the case of a Borrower seeking a Construction Draw Mortgage a Referrer is 
				referral61=entitled to a Referral Fee or Renewal Fee if and only if there is a Funding of a first draw within six (6) months of the 
				referral62=Referral and final Funding is completed within eighteen (18) months of the Referral. Where there is a disagreement as to 
				referral63=whether a Referral was made by the Referrer, the Referral Fees or Renewal Fees payable in respect of such Referral shall
				referral64=be paid into Visdom be paid into Visdom counsel trust account (or as the parties may otherwise agree) pending the resolution
				referral65=of the dispute.


				referral66=3.8  Visdom shall pay to the Referrer such Referral Fees or Renewal Fees no later than the sixtieth (60th) day immediately
				referral67=following the Funding.

				referral68=4.  TERM & TERMINATION 

				referral69=4.1  The term of this Agreement shall commence as of the date first above written and shall continue indefinitely until 
				referral70=terminated in accordance with the provisions here of.

				referral71=4.2  Either party may terminate this Agreement at any time upon delivery to the other party of not less than ninety (90) 
				referral72=calendar days prior written notice of the effective date of termination (the Effective Date).

				referral73=4.3  In the case of any fraudulent activities including, without limitation, mortgage fraud for housing, mortgage fraud 
				referral74=for profit, mortgage fraud for title and Fore gage fore closure fraud, this Agreement shall be immediately terminable 
				referral75=by Visdom without Visdom providing any notice to the Referrer.

				referral76=4.4  In the case that Visdom determines a Referrer falsely represented that it has a bona-fide relationship and substantial
				referral77=connection with the Borrower or the Borrower is not a bona-fide party seeking legal and legitimate Funding, this Agreement 
				referral78=shall be immediately terminable by Visdom without Visdom providing any notice to the Referrer.

				referral79=5.  STATUS OF PARTIES 

				referral80=5.1  The Referrer and Visdom acknowledge that they are independent contracting parties, and this Agreement shall not constitute
				referral81=any such party as an agent, representative, partner, co-venturer, employee, employer or franchisee of any other party, 
				referral82=except as expressly provided for herein. None of the Referrer nor Visdom shall assume or create any obligation or responsibility
				referral83=whatsoever on behalf of, or in the name of, any other party except as otherwise provided for herein.

				referral84=6.  Referrer REPRESENTATIONS AND WARRANTIES 

				referral85=6.1  The Referrer represents that he/she is in compliance with all laws and regulations applicable to making a Referral of the 
				referral86=Borrower to Visdom.

				referral87=6.2  The Referrer represents that the execution, delivery and performance of this Agreement by the Referrer will not result in 
				referral88=any breaches, violations or defaults of any obligations, duties or requirements whatsoever of the Referrer.

				referral89=6.3  The Referrer represents that they are not a mortgage broker and does not deal in mortgages as defined in the Real Estate 
				referral90=Act. A mortgage broker means a person who on or behalf of another person for consideration or other compensation solicits a 
				referral91=person to borrow or lend money to be secured by a mortgage; negotiates a mortgage transaction; collects mortgage payments and 
				referral92=otherwise administers mortgages; buys, sells or exchanges mortgages or offers to do so or a person who holds out or represents 
				referral93=them self as  a person who can do any of the above.

				referral94=6.4  The Referrer represents that it has a bona-fide relationship and substantial connection with the Borrower and the Borrower
				referral95=is a bona-fide party seeking legal and legitimate Funding. Determination of bona-fide relationship, substantial connection and a  
				referral96=bona-fide party seeking Funding shall be at the sole discretion of Visdom.

				referral97=6.5  The Referrer represents that their declared status as a licensed individual, unlicensed individual or Referrer Corporation
				referral98=as the case may be is true.

				referral99=6.6  Both parties agree that if any one of the above representations and warranties are not true, then this Agreement shall 
				referral100=immediately be null and void and shall not be binding on either party.

				referral101=7.  Visdom representations and warranties 

				referral102=7.1  Visdom represents that it does not exclusively deal with one lender or financial institution for the purpose of securing
				referral103=Funding for a Borrower. Visdom deals with, conducts business with and sources Funding for Borrowers from multiple lenders and
				referral104=financial institutions.

				referral105=7.2  Visdom is a corporation incorporated and organized under the laws of the Province of Alberta and is a valid and subsisting
				referral106= corporation under such laws.

				referral107=7.3  Visdom and its agents have the corporate power and authority to enter into this Agreement and to perform their respective 
				referral108=obligations here under and thereunder; the execution and delivery of this Agreement and and the consummation of the transactions 
				referral109=contemplated by this Agreement have been duly authorized by all necessary corporate action on the part of the Visdom, its
				referral110=shareholders and directors.

				referral111=7.4  Visdom is a mortgage brokerage duly licensed to Trade in Real Estate in the jurisdiction of the Borrower.
				 
				referral112=7.5  Both parties agree that if any one of the above representations and warranties are not true, then this Agreement shall 
				referral113=immediately be null and void and shall not be binding on either party.

				referral114=8. INTERPRETATION AND GENERAL

				referral115=8.1  The headings in this Agreement are included for convenience of reference only and shall not affect the interpretation
				referral116=here of.

				referral117=8.2 Visdom shall have the right to assign this Agreement to assigns or transferees without providing notification to 
				referral118=the Referrer. The Referrer does not have any rights to assign this Agreement to any other party.

				referral119=8.3  Pursuant to Canada Anti-Spam Legislation, the Referrer consents to receive commercial electronic messages such as 
				referral120=e-mails from Visdom. If the Referrer wishes to withdraw consent to receive communications by electronic means, they 
				referral121=must notify Visdom in writing at the address for service noted above.

				referral122=8.4  The Referrer shall provide Visdom written notice of any changes in address, including both e-mail and physical mailing
				referral123=address, promptly. 

				referral124=8.5 This Agreement and any schedules attached hereto sets forth the entire agreement between the parties here to pertaining
				referral125=to the subject matter here of and supersedes all prior agreements. There are not and shall not be any oral statements, 
				referral126=representations, warranties, undertakings or agreements between the parties.

				referral127=8.6 Time shall in all respects be of the essence here of and no waiver of any time period prescribed here under shall be effective
				referral128=unless in writing signed by the parties here to.

				referral129=8.7  Words in the singular include the plural and vice versa and words importing gender include all genders.

				referral130=8.8  If any provision of this Agreement or its application to any party or circumstance is restricted, prohibited or 
				referral131=unenforceable, such provision shall be ineffective only to the extent of such restriction, prohibition or unenforceability
				referral132=without invalidating the remaining provisions here of and without affecting the application of such provision to other
				referral133=parties or circumstances.


				referral134=8.9  The Parties agree that good faith is of the essence of this Agreement and further agree that each shall act in good faith 
				referral135=to the other in all matters arising in connection with any provisions of this Agreement.

				referral136=8.10  This Agreement shall be governed by and interpreted in accordance with the laws of the Province where the Mortgage is 
				referral137=registered and the laws of Canada applicable therein. 

				referral138=8.11  Where any provincial sales tax, goods and services tax or any other applicable tax is required to be levied on any of the 
				referral139=payments contemplated under this Agreement, all such applicable taxes shall be claimed by Vidsom and any such taxes shall  be 
				referral140=deducted from the Referral Fees and Renewal Fees prescribed in Schedule B. In the case of an individual Referrer who is not 
				referral142=registered with a G.S.T. number, the Referral Fee or Renewal Fee shall not be subject to G.S.T. deduction and Vidsom shall 
				referral143=provide a T4A to an individual Referrer who receives more than $500.00 in Referral Fees and/or Renewal Fees in any given 
				referral144=taxation year. In the case assignment of an individual Referral Fee or Renewal Fee to a brokerage, the Referral Fee or Renewal 
				referral145=Fee shall be subject to G.S.T. deduction. In the case of a Referrer Corporation the Referral Fee or Renewal Fee shall be
				referral146=subject to G.S.T. deduction. The Referrer represents that if it is a brokerage or corporation registered with G.S.T. number 
				referral147=it will promptly provide this information to Visdom.

				referral148=8.12 Any notice or other communication required or permitted to be given hereunder shall be in writing and, if mailed by 
				referral149=prepaid first-class mail at any time other than during a general discontinuance of postal service due to strike, lockout or 
				referral150=otherwise, shall be sent to the other party at the last known address of the other party and be deemed to have been 
				referral151=received five (5) business days after the post-marked date thereof, or if telecopied, emailed or delivered by another form 
				referral152=of recorded communication, shall be deemed to have been received on the next business day following dispatch and acknowledgement 
				referral153=of receipt by the recipient telecopier machine or other form of recorded communication, or if delivered by hand shall be deemed
				referral154=to have been received at the time it is delivered. If either party changes its address during the term of this Agreement, it 
				referral155=shall immediately notify the other party of such change of address in the foregoing manner. 

				referral156=8.13 All payments required or permitted under this Agreement shall be denominated in the lawful money of Canada unless otherwise 
				referral157=expressly indicated herein. 

				referral158=8.14 This Agreement may be executed by the Parties in one or more counterparts, each one of which shall be deemed an original.
				referral159=Facsimile and electronic mail or digital copies and photocopies of this document that contain signatures shall be deemed to 
				referral160=have the full force and effect of the original.

				referral161=Schedule  A


				referral162=ADDITIONAL REFERRER CORPORATION TERMS

				referral163=1.  EXPENSES 

				referral164=1.1 Except as hereinafter provided, all expenses in connection with the Referrer Corporation performance of this Agreement,
				referral165=including but not limited to travel, automobile, salaries and supplies, shall be borne by the Referrer Corporation and it 
				referral166=shall be solely responsible for the payment thereof. Visdom shall not be responsible for any expenses of the Referrer Corporation.

				referral167=2. No power to bind .

				referral168=2.1  The Referrer Corporation does not have the right or authority to create a contract or obligation either express or implied,  
				referral169=on behalf of, Visdom unless otherwise agreed in writing.

				referral170=3.  Warranties and Representations 


				referral171=3.1 The Referrer Corporation and Visdom represent that their services are provided on a best effort basis. There are no guarantees,
				referral172=warranties and representations of any kind that services will produce any specific results for the benefit of the other. Each
				referral173=of the Referrer Corporation and Visdom represents and warrants to the other that:

				referral174=(a) it is under no contractual restriction or other restrictions or obligations that are inconsistent with this Agreement, 
				referral175=the performance of its duties and the covenants hereunder;

				referral176=(b) its management is under no physical or mental disability that would interfere with its keeping and performing all of the 
				referral177=agreements, covenants and conditions to be kept or performed hereunder;

				referral178=(c) it is familiar with all Federal and Provincial laws and regulations and regulations of any governing bodies applicable 
				referral179=to the performance of its services as contemplated in this Agreement;

				referral180=(d) it will comply with all applicable Federal and Provincial laws and regulations of any governing bodies in the performance 
				referral181=of the services under this Agreement; and,

				referral182=(e) nothing herein contained shall be construed to limit or restrict the other party in providing services to others.

				referral183=4.  Indemnity

				referral184=4.1 Visdom and the Referrer Corporation hold each other harmless and indemnify each other from and against any liability, loss, 
				referral185=cost, expenses or damages, including attorney's fees, howsoever caused by reason of any injury or loss sustained by
				referral186=or to any person or property by reason of any actual or alleged wrongful act, misrepresentation or omission except for gross 
				referral187=negligence, willful misconduct or malfeasance of, or breach of any representation, warranty or covenant.

				referral188=5.  No Solicitation 
				referral189=5.1  A Referrer Corporation may not solicit any officer, director, employee or associate of Visdom for employment or any type  
				referral190=of business relationship without prior written consent from Visdom.


		
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		 
		 
		  GregorianCalendar calendar=(GregorianCalendar) GregorianCalendar.getInstance();

		UUID uuid=UUID.randomUUID();
		log.debug("insdie Referreral  Agreement  Pdf generation");
		String file=  "/home/venkateshm/Desktop/";
	//String outputFileName=file+"Referral_Agreement_" +referralName+"_"+format.format(calendar.getTime())+".pdf";
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		String outputFileName = genericHelperClass.readConfigfile().getProperty("pdfPath")+"RerralAgreement_" + referralName +"_"+format.format(calendar.getTime())+".pdf";
	
		 Properties prop = new Properties();
	 		try {

	 		prop.load(VisdomReferralPdfGeneration11.class.getClassLoader().getResourceAsStream("visdomReferral.properties"));
	 		System.out.println("referralName "+ referralName  +"areusingTouchScreenDevice "+ areusingTouchScreenDevice +" myTypedName"+ myTypedName +"role "+ role + " image"+image);
System.out.println("prop "+prop.size());
prop.load(VisdomReferralPdfGeneration11.class.getClassLoader().getResourceAsStream("visdomReferral.properties"));
	

	PDDocument document =new PDDocument();
	PDPage page1 =new PDPage(PDPage.PAGE_SIZE_A4);
	
	PDRectangle rect=page1.getMediaBox();
	document.addPage(page1);
	
	PDFont fontplain = PDType1Font.TIMES_ROMAN;
	PDFont fontbold=PDType1Font.HELVETICA_BOLD;
	PDFont fontItalic =PDType1Font.HELVETICA_OBLIQUE;
	PDFont fontMono=PDType1Font.COURIER;
	
	
	
	PDPageContentStream cos=new PDPageContentStream(document, page1);
	
	
	int line=0;
	  
	cos.beginText();
	cos.setFont(fontbold, 12);
	cos.moveTextPositionByAmount(240, rect.getHeight() -50*(++line));
	cos.drawString("Mortgage Referral Agreement");
	cos.endText();
	int line2=0;
	cos.beginText();
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(25, rect.getHeight() -100);
	//cos.drawString(prop.getProperty("referral1"));
	cos.drawString(referral1);
	  cos.endText();
		line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
 cos.drawString(prop.getProperty("referral2"));
 cos.endText();
	line2=line2+15;


	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
 cos.drawString(prop.getProperty("referral3"));
	cos.endText();
	line2=line2+15;
 
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
 cos.drawString(prop.getProperty("referral4"));
	cos.endText();
	line2=line2+10;
 
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral5"));
	cos.endText();
	line2=line2+10;
 
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral6"));
 cos.endText();
		line2=line2+20;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral7"));
	cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral8"));
 cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral8"));
	cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
 cos.drawString(prop.getProperty("referral9"));
	cos.endText();
	line2=line2+10;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral10"));
	cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral11"));
	cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral12"));
	cos.endText();
	line2=line2+10;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral13"));
	cos.endText();
	line2=line2+10;
		
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral14"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	  cos.drawString(prop.getProperty("referral15"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	 cos.drawString(prop.getProperty("referral16"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral17"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral18"));
	cos.endText();
	line2=line2+10;
	
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral19"));
	cos.endText();
	line2=line2+15;

	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral20"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral21"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral22"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral23"));
	cos.endText();
	line2=line2+10;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral24"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral25"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral26"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral27"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral28"));
	cos.endText();
	line2=line2+15;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral29"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral30"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral31"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral32"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral33"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral34"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral35"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral36"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral37"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral38"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral39"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral40"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral41"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral42"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral43"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral44"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral45"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral46"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral47"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral48"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral49"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral50"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral51"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral52"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral53"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral54"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral55"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral56"));
	cos.endText();
	line2=line2+15;
cos.close();


PDPage page2=new PDPage(PDPage.PAGE_SIZE_A4);
document.addPage(page2);
cos =new PDPageContentStream(document, page2);
line2=0;
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral57"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral58"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral59"));
	cos.endText();
	line2=line2+10;
	
	/*cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral60"));
	cos.endText();
	line2=line2+10;*/
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral61"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral62"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral63"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral64"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral65"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral66"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral67"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral68"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral69"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral70"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral71"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral72"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral73"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral74"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral75"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral76"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral77"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral78"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral79"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral80"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral81"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral82"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral83"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral84"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral85"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral86"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral87"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral88"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral89"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral90"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral91"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral92"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral93"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral94"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral95"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral96"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral97"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral98"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral99"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral70"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral101"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral102"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral103"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral104"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral105"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral106"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral107"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral108"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral109"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral110"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral111"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral112"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral113"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral114"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral115"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral116"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral117"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral118"));
	cos.endText();
	line2=line2+15;
	
	   cos.close();
     
     
     PDPage page3=new PDPage(PDPage.PAGE_SIZE_A4);
     document.addPage(page3);
     cos =new PDPageContentStream(document, page3);
     
	line2=0;
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral119"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral120"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral121"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral122"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral123"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral124"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral125"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral126"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral127"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral128"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral129"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral130"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral131"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral132"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral133"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral134"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral135"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral136"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral137"));
	cos.endText();
	line2=line2+15;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral138"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral139"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral140"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral142"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral143"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral144"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral145"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral146"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral147"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral148"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral149"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral150"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral151"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral152"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral153"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral154"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral155"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral156"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral157"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral158"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral159"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral160"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral161"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral162"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral163"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral164"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral165"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral166"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral167"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral168"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral169"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral170"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral171"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral172"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral173"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral174"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral175"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral176"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral177"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral178"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral179"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral180"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral181"));
	cos.endText();
	line2=line2+15;
cos.close();
 
 
 PDPage page4=new PDPage(PDPage.PAGE_SIZE_A4);
 document.addPage(page4);
 cos =new PDPageContentStream(document, page4);
 line2=0;
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral182"));
	cos.endText();
	line2=line2+15;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral183"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral184"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral185"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral186"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral187"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral188"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral189"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral190"));
	cos.endText();
	line2=line2+25;
	line2=line2+20;
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString("Schedule B");
	cos.endText();
	line2=line2+15;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(200,rect.getHeight() -70-line2);
	cos.drawString("REFERRAL FEE SCHEDULE");
	cos.endText();
	line2=line2+20;
	
	if(role.equals("Business")|| role.equals("Customer")){
	
String[][] content = {{" Minimum Mortgage Value"," Maximum Mortgage Value", "    Referral Fee","  Renewal Fee"},
               {"$50,000.00","$200,000.00", "$50.00","$25.00"},       
               {"$200,001.00","$350,000.00","$125.00","$50.00"},
           	  {"$350,001.00","$600,000.00", "$250.00","$125.00"},
               {"$600,001.00","$900,000.00", "$350.00","$175.00"},
           	  {"$900,001.00","$3,000,000.00","$500.00","$250.00"} } ;
	
PdfTableCreation.PdfTableCreationMethod(page4, cos, rect.getHeight()-70-line2, 20, content);

}else if(role.equals("Realtor")||role.equals("Financial Planner")||role.equals("Builder")|| role.equals("Professional")){

String[][] content1 = {{" Minimum Mortgage Value"," Maximum Mortgage Value", "    Referral Fee","  Renewal Fee"},
 {"$50,000.00","$200,000.00", "$100.00","$50.00"},       
 {"$200,001.00","$350,000.00","$250.00","$100.00"},
	  {"$350,001.00","$600,000.00", "$500.00","$225.00"},
 {"$600,001.00","$900,000.00", "$750.00","$350.00"},
	  {"$900,001.00","$3,000,000.00","$1,000.00","$500.00"} } ;

PdfTableCreation.PdfTableCreationMethod(page4, cos, rect.getHeight()-70-line2, 20, content1);
}

	

line2=line2+170;
	cos.beginText(); 
	cos.setFont(fontbold, 10);
	cos.moveTextPositionByAmount(60,rect.getHeight() -70-line2);
	cos.drawString("Referrer Signature :");
	cos.endText();
	line2=line2+110;
	       
if(areusingTouchScreenDevice.equals("Yes")){
	

 try {
    // BufferedImage awtImage = ImageIO.read(new File("simple.jpg"));
    PDXObjectImage ximage = new PDPixelMap(document, image);
     float scale = 0.5f; // alter this value to set the image size
     cos.drawXObject(ximage, 50, rect.getHeight() -70-line2, ximage.getWidth()*scale, ximage.getHeight()*scale);
 } catch (Exception fnfex) {
     System.out.println("No image for you");
 }
cos.close();
}else{

	cos.beginText(); 
	cos.setFont(fontbold, 10);
	cos.moveTextPositionByAmount(90,rect.getHeight() -70-line2+80);
	cos.drawString(myTypedName);
	cos.endText();
	line2=line2+20;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(60,rect.getHeight() -70-line2+70);
	cos.drawString("My typed name above serves as my electronic signature for the above agreement.");
	cos.endText();  
	cos.close();
}

	 			

/*	 			System.out.println("daoument "+document.toString()+"ddd"+document.getCurrentAccessPermission()+"do "+document.getDocumentInformation() +"doc ");
*/	 		
document.save(outputFileName);
	 			document.close();
	 			try{
	 				File fl=new File(outputFileName);
	 				System.out.println("size "+fl.exists() +""+(file.length()/1024));
	 			}catch(Exception e){
	 				
	 			}
log.debug("The path pdf file created");
	 			return outputFileName;
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			
	 		}
		
		return outputFileName;

	}
}
