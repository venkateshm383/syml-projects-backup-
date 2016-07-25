<html
<body>
	Hi  ${user.firstName} ,<br><br><br>


	    Thanks for Completing Mortagage form and you need to submit the below listed document before  deadline time. <br><br>
		
	The document list <br><br>
	
	Applicants Documents :
&nbsp;&nbsp;[	<#list user.documents as doc>
	${doc},
	</#list>]<br>
	Co_Applicants Documents : [
	<#list user.co_documents as doc1>
	${doc1},
	</#list>]<br>	
	
	Proprty Documents :
	
	[<#list user.propertyDocments as doc2>
	${doc2},
	</#list>]<br>
	
	Downpayment Documents:
	[<#list user.downPayments as doc3>
	${doc3},
	</#list>]<br>
	
	Thank you for choosing Visdom! If you have any questions, please do not hesitate to contact our broker team.<br><br>

			Touch Dial: <b>1-855-699-2400</b>; <b>201</b><br>
			Manual Dial:<b> 1-855-699-2400 extension: 201</b><br><br>

			Best Regards,<br><br>"

			Visdom Mortgage Solutions<br>
			broker@visdom.ca<br><br>
			
			
			CONFIDENTIALITY: This e-mail message (including attachments, if any) is confidential and is intended only for the addressee. Any unauthorized use or disclosure is strictly prohibited. Disclosure of this e-mail to anyone other than the intended addressee does not constitute waiver of privilege. If you have received this communication in error, please notify us immediately and delete this. Thank you for your cooperation. This message has not been encrypted. Special arrangements can be made for encryption upon request. If you no longer wish to receive e-mail messages from Visdom Mortgage Solutions, please contact the sender. <br>

	  Visit our website at www.visdom.ca for information about our company and the services we provide. You can subscribe to Visdom Mortgage Solutions free electronic communications, or unsubscribe at any time.\n\n";
	
</body>
</html>