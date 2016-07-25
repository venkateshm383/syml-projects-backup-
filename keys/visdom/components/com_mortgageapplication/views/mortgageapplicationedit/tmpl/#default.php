<?php
/*------------------------------------------------------------------------
# default.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');


include('xmlrpc.inc');
	
   //$user = 'admin';
   //$password = 'syml';
   //$dbname = 'symlsys';
   //$server_url = 'http://107.23.27.61:8069/xmlrpc/';

	/*
   if(isset($_COOKIE["user_id"]) == true)  {
       if($_COOKIE["user_id"]>0) {
       //return $_COOKIE["user_id"];
       echo $_COOKIE["user_id"];
       }
   }*/

	$db= JFactory::getDbo();
	
	$query = $db->getQuery(true);//
		    $table=$db->quoteName('g21yj_openerp_config');
		   
		    $query->clear();
		    $query->select($db->quoteName(array('state','database_name', 'user_id', 'password', 'url','email','send_mails_to_user','text_to_send')));
			$query->from($db->quoteName('g21yj_openerp_config'));
			
			$db->setQuery($query);
			
			$configs = $db->loadObjectList();
			$client ="";
			$dbname ="";
			$userid ="";
			$password ="";
			foreach($configs as $config){
				if($config->state == 1){
					$base_url = $config->url;
					
					$dbname =$config->database_name;
					$userid = $config->user_id;
					$password = $config->password;
				}
			}
	

	/*
   $sock = new xmlrpc_client($server_url.'common');
   $msg = new xmlrpcmsg('login');
   $msg->addParam(new xmlrpcval($dbname, "string"));
   $msg->addParam(new xmlrpcval($user, "string"));
   $msg->addParam(new xmlrpcval($password, "string"));
   $resp =  $sock->send($msg);
   //print_r($resp);
  
   $val = $resp->value();
   $userid = $val->scalarval();
   */
   //echo $client;echo $userid;echo $password;exit;
   
	$arrayValread = array(
			new xmlrpcval('deal_ids','string'),
			
		);
		
		$crm_lead = $_GET['crm_lead'];
		$client = new xmlrpc_client($base_url);
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("crm.lead", "string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($crm_lead, "int"));
			$msg->addParam(new xmlrpcval($arrayValread, "array"));
			$res = $client->send($msg);
			//print_r($res);
			if ($res->faultCode()){
			 throw new Exception( 'Error: '.$res->faultString());
			}
			 
				$deal_ids = $res->value()->scalarval();
				
				//print_r($deal_ids);exit;
				//echo "<pre>";
				foreach($deal_ids as $keys=>$values){
					$arr[$keys] = $values->scalarval();
					
				}
				
				$deal_line_ids=$arr['deal_ids'];
				$deal_lines_ids=array();
				foreach($deal_line_ids as $deal_line_id){
					$deal_lines_ids[]= $deal_line_id->me['int'];
					
					
				}
				//print_r($deal_lines_ids);
				//die('bmnbmb');
				$OriginalDesired ="";
				$OriginalDetails ="";
				$RecommendDetails = "";
				$notes ="";
				$OriginalDetailhtml ="";
				$RecommendDetailhtml ="";
				foreach($deal_lines_ids as $deal_lines_id){
					//echo $deal_lines_id." ";
					$arrayValread = array(
						new xmlrpcval('name','string'),new xmlrpcval('marketing_field','string')
					);	
					$client = new xmlrpc_client($base_url);
					$msg = new xmlrpcmsg('execute');
					$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
					$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
					$msg->addParam(new xmlrpcval($password, "string"));/** password */
					$msg->addParam(new xmlrpcval("deal", "string"));
					$msg->addParam(new xmlrpcval("read", "string"));
					$msg->addParam(new xmlrpcval($deal_lines_id, "int"));
					$msg->addParam(new xmlrpcval($arrayValread, "array"));
					$res = $client->send($msg);
					if ($res->faultCode()){
					 echo 'Error: '.$res->faultString();
					}
						$deal_line_details = $res->value()->scalarval();
						if($deal_line_details['marketing_field']->scalarval() == 'OriginalDesired'){
								$OriginalDesired = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'OriginalDetails'){
								$OriginalDetails = $deal_line_details['name']->scalarval();
								//print_r(preg_split('/[\n\r]+/', $OriginalDetails));
								$OriginalDetails = preg_split('/[\n\r]+/', $OriginalDetails);
								foreach($OriginalDetails as $OriginalDetail){
									$OriginalDetailval = explode(':', $OriginalDetail);
									//print_r($OriginalDetailval);
									if($OriginalDetailval[0]!="" || $OriginalDetailval[1]!=""){
										$OriginalDetailhtml .= '<div><label class="subject">'.$OriginalDetailval[0].': </label><label class="value">'.trim($OriginalDetailval[1]).'</label></div>';
									}
								}
						}
						if($deal_line_details['marketing_field']->scalarval() == 'RecommendDetails'){
								$RecommendDetails = $deal_line_details['name']->scalarval();
								$RecommendDetails = preg_split('/[\n\r]+/', $RecommendDetails);
								
								foreach($RecommendDetails as $RecommendDetail){
									$RecommendDetailval = explode(':', $RecommendDetail);
									//print_r($OriginalDetailval);
									if($RecommendDetailval[0]!="" || $RecommendDetailval[1]!=""){
										if (strpos($RecommendDetailval[1],'Initial Here') == false) {
										
											$RecommendDetailhtml .= '<div><label class="subject">'.$RecommendDetailval[0].': </label><label class="value">'.trim($RecommendDetailval[1]).'</label></div>';
										}
										else{
											//$RecommendDetaillastline = explode(' ', $RecommendDetailval[1]);
											//var_dump($RecommendDetaillastline);
											$RecommendDetailval[1] = str_ireplace('Initial',' ',$RecommendDetailval[1]);
											$RecommendDetailval[1] = str_ireplace('here',' ',$RecommendDetailval[1]);
											
											$RecommendDetailhtml .='<div class="col1"><label class="subject">'.$RecommendDetailval[0].': </label><label class="value">'.trim($RecommendDetailval[1]).'</label></div><div class="col2"><label class="subject">Initial Here:</label><input type="text"></div>';
										}
										
										
									}
								}
								//$RecommendDetailhtml .= '<input type="text"></input>';
						}
						if($deal_line_details['marketing_field']->scalarval() == 'VariableRecommendations'){
								$VariableRecommendations = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'Notes'){
								$notes = $deal_line_details['name']->scalarval();
								$notes = explode(': ', $notes);
								$notes_first_line = $notes[0];
								//var_dump($notes[1]);
								$notes_lines = preg_split('/[\n\r]+/', $notes[1]);
								foreach($notes_lines as $notes_line){
									if($notes_line){
										$notes_lines_html .= "<li>".$notes_line."</li>";
									}
								}
						}
				}
				
				$arrayValread = array(
			new xmlrpcval('recommendations_ids','string'),
			
		);
		
		$client = new xmlrpc_client($base_url);
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("crm.lead", "string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($crm_lead, "int"));
			$msg->addParam(new xmlrpcval($arrayValread, "array"));
			$res = $client->send($msg);
			//print_r($res);
			if ($res->faultCode()){
			 throw new Exception( 'Error: '.$res->faultString());
			}
			 
				$recommendations_ids = $res->value()->scalarval();
				//echo "<pre>";
				//print_r($deal_ids);
				//echo "<pre>";
				foreach($recommendations_ids as $keys=>$values){
					$arr[$keys] = $values->scalarval();
					
				}
				
				$recommendations_line_ids=$arr['recommendations_ids'];
				$recommendation_lines_ids=array();
				foreach($recommendations_line_ids as $recommendation_line_id){
					$recommendation_lines_ids[]= $recommendation_line_id->me['int'];
					
					
				}
				//print_r($recommendation_lines_ids);
				//die('bmnbmb');
				$variable_options_html ="";
				$fixed_options_html ="";
				$loc_options_html ="";
				foreach($recommendation_lines_ids as $recommendation_lines_id){
					//echo $deal_lines_id." ";
					$arrayValread = array(
						new xmlrpcval("lender",'string'),new xmlrpcval("product",'string'),new xmlrpcval("mortgage_type",'string'),new xmlrpcval("term",'string'),new xmlrpcval("maximum_amortization",'string'),new xmlrpcval("mortgage_amount",'string'),new xmlrpcval("mortgage_payment",'string'),new xmlrpcval("cashback",'string'),new xmlrpcval("name",'string')
					);	
					$client = new xmlrpc_client($base_url);
					$msg = new xmlrpcmsg('execute');
					$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
					$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
					$msg->addParam(new xmlrpcval($password, "string"));/** password */
					$msg->addParam(new xmlrpcval("opp.recommendations", "string"));
					$msg->addParam(new xmlrpcval("read", "string"));
					$msg->addParam(new xmlrpcval($recommendation_lines_id, "int"));
					$msg->addParam(new xmlrpcval($arrayValread, "array"));
					$res = $client->send($msg);
					if ($res->faultCode()){
					 echo 'Error: '.$res->faultString();
					}
						$recommendation_line_details = $res->value()->scalarval();
						
						if($recommendation_line_details['mortgage_type']->scalarval() == 2){
							$product_details = array();
							$product_details = $recommendation_line_details['product']->scalarval();
							$prod = $product_details[1]->me['string'];
							$lender_details = array();
							$lender_details = $recommendation_line_details['lender']->scalarval();
							$lender = $lender_details[1]->me['string'];
							//echo $recommendation_line_details['product']->scalarval()[1]->me['string'];
							$variable_options_html .="<tr><td>".$lender."</td><td>".$prod."</td><td>".$recommendation_line_details['term']->scalarval()."</td><td>".$recommendation_line_details['maximum_amortization']->scalarval()."</td><td>".$recommendation_line_details['mortgage_amount']->scalarval()."</td><td>".$recommendation_line_details['name']->scalarval()."</td><td><input type='text'></input></td></tr>";
						}
						if($recommendation_line_details['mortgage_type']->scalarval() == 3){
							$product_details = array();
							$product_details = $recommendation_line_details['product']->scalarval();
							$prod = $product_details[1]->me['string'];
							$lender_details = array();
							$lender_details = $recommendation_line_details['lender']->scalarval();
							$lender = $lender_details[1]->me['string'];
							//echo $recommendation_line_details['product']->scalarval()[1]->me['string'];
							$fixed_options_html .="<tr><td>".$lender."</td><td>".$prod."</td><td>".$recommendation_line_details['term']->scalarval()."</td><td>".$recommendation_line_details['maximum_amortization']->scalarval()."</td><td>".$recommendation_line_details['mortgage_amount']->scalarval()."</td><td>".$recommendation_line_details['name']->scalarval()."</td><td><input type='text'></input></td></tr>";
						}
						if($recommendation_line_details['mortgage_type']->scalarval() == 1){
							$product_details = array();
							$product_details = $recommendation_line_details['product']->scalarval();
							$prod = $product_details[1]->me['string'];
							$lender_details = array();
							$lender_details = $recommendation_line_details['lender']->scalarval();
							$lender = $lender_details[1]->me['string'];
							//echo $recommendation_line_details['product']->scalarval()[1]->me['string'];
							$loc_options_html .="<tr><td>".$lender."</td><td>".$product."</td><td>".$recommendation_line_details['term']->scalarval()."</td><td>".$recommendation_line_details['maximum_amortization']->scalarval()."</td><td>".$recommendation_line_details['mortgage_amount']->scalarval()."</td><td>".$recommendation_line_details['name']->scalarval()."</td><td><input type='text'></input></td></tr>";
						}
						
				}
				
?>
<title>Mortgage Proposal</title>
<link rel="stylesheet" type="text/css" href="templates/protostar/css/wizard2.css" />
<div id="wrap1">
<div id="mortgage-form" class="">
<div id="container" style="margin-top:2px">
	<div id="header">
		<img src="templates/protostar/images/wizard/vission_11.png" align="left" width="300"  style="padding-left:20px;">
		<h1><span>M</span>ortgage <span>P</span>roposal</h1>
		<h2>
			Darryl Andersen<br>
			<span>and</span><br>
			Cheryl Andersen<br>	
		</h2>
		<h3>
			321 Summit Dr.<br>
			Calgary, AB
		</h3>
	</div>
	<div class="content">
		<h3>Goal</h3>
		<p>Congratulations on your goal to own the property at 321 Summit Drive, Calgary, AB. In your application, you indicated you wanted to purchase this property with a down payment of $70,000 and financing of $375,000. As we have explored mortgage options for you, we have also considered goals of cost savings, ease, payout flexibility and financial saving. In short we have sought to discover a mortgage which serves you rather than you serving a Mortgage.</p>
		<h3>Our Recommendation:</h3>
		<p><?php echo $OriginalDesired ?></p>
		<div class="square_box">
		<!--div><label class="subject">Mortgage Amount:</label><label class="value">$750,000</label></div>
		<div><label class="subject">Insurance Amount:</label><label class="value">$0</label></div>
		<div><label class="subject">Mortgage Type:</label><label class="value">Fixed</label></div>
		<div><label class="subject">Mortgage Term:</label><label class="value">5 Year</label></div>
		<div><label class="subject">Payment Amount:</label><label class="value">$3,584</label></div>
		<div><label class="subject">Annual Interest Rate:</label><label class="value">3.09%To Select</label></div-->
		<?php echo $OriginalDetailhtml ?>
		<!--div class="col1"><label class="subject">Lender:</label><label class="value">Merix</label></div>
		<div class="col2"><label class="subject">Initial Here:</label><input type="text"></input></div-->
		</div>
		<h3>Helping you achieve your goals</h3>
		<p>When considering a significant financial decision such as a mortgage, wouldn’t you like to know you are getting the best option for which you can qualify? Achieving this can be a challenge because there are thousands of different Mortgage Products available in Canada. Understanding the options and those which you may qualify for is a daunting and time consuming task.</p>
		<p>To help you achieve your goals, we have virtually underwritten your application across <b>2,343</b> Mortgage Products. Of those options, the property you are financing could potentially qualify for <b>1,763</b> different possibilities. We have filtered these options for you (based upon cost effectiveness, interest rate, flexibility and other criteria) to the best 15 Mortgages presently offered by lenders which fit your goals. And finally, we have also provided a single recommendation for your consideration along with the reasons for our recommendation.</p>
		<h3>Our Recommendation:</h3>
		<p style="display:none;">We would recommend you consider the following option:</p>
		<div class="square_box">
		<!--div><label class="subject">Mortgage Amount:</label><label class="value">$750,000</label></div>
		<div><label class="subject">Insurance Amount:</label><label class="value">$0</label></div>
		<div><label class="subject">Mortgage Type:</label><label class="value">Variable</label></div>
		<div><label class="subject">Mortgage Term:</label><label class="value">5 Year</label></div>
		<div><label class="subject">Payment Amount:</label><label class="value">$3,510</label></div>
		<div><label class="subject">Annual Interest Rate:</label><label class="value">2.5%To Select</label></div-->
		<?php echo $RecommendDetailhtml ?>
		<!--div class="col1"><label class="subject">Lender:</label><label class="value">Scotia</label></div>
		<div class="col2"><label class="subject">Initial Here:</label--><!--/div-->
		</div>
		<p>For many people, the selection of a mortgage often involves an effort to understand the balance between reducing cost and reducing risk. Most of us like to save as much money as possible, though we also do not want our costs to rise in the future in case interest rates go up. Additionally, if interest rates fall, we don’t want to be locked in at a higher rate. A third consideration in mortgage decisions involves payout penalties if you sell your property or refinance it before the term is complete. In our recommendation, we have sought to understand your personal “balance” between reducing risk and reducing cost.</p>
		<p>Based upon the future lifestyle questions you answered in your application, your “bias” towards reducing future risk (the possibility of rising interest rates and/or payout penalties) in your mortgage is low. This creates an opportunity for you to reduce your current mortgage costs with a variable Mortgage. We recommended the above mortgage solution because it most closely aligns with your personal “balance” between reducing risk and reducing cost.</p>
		<p>The following characteristics of this mortgage may help you consider this recommendation:<br> 
		<b>Cost Comparison:</b> This recommendation <b>is $74 / month less</b> expensive than a 5 year fixed mortgage.<br>
		<b>Payout Risk Comparison:</b> This recommendation has a maximum payout penalty of 3 months of interest (currently <b>$9,827</b>) as opposed to a Fixed Mortgage which could have a payout penalty of as much as <b>$23,543</b>.<br>
		<b>Interest Rate Trend:</b> The current interest rate trend is level. In the past 20 years, there have been 2 periods of rising interest rates (17 months & 30 months). The average increase in rates during these periods (prior to rates falling again) was 1.68%. Unless economic activity picks up significantly, it is unlikely rates will rise significantly. Interest rates would need to rise by <b>0.93%</b> over the next <b>2 years</b> before a 5 Year Fixed mortgage would save you any money.</p>
		<p>While we recommend the above mortgage because it best suits our understanding of your needs, we also recognize our understanding of your financing goals may be imperfect. Accordingly, we have also provided core details for an additional <b>15</b> mortgages. You will find these listed in the tables below.</p>
		<h3>Variable Options:</h3>
		<p>A variable mortgage has an interest rate that will fluctuate with changes in the Bank of Canada Prime rate. This means payments on a variable mortgage will also fluctuate. If interest rates fall, so will the payments on a variable mortgage. Conversely, if interest rates rise, so will your payments. With that said though, the rates and payment on a variable mortgage are often lower than the rates on a fixed mortgage so as long as rates remain reasonably stable, one can often save money by taking a variable mortgage rather than a fixed mortgage. <p><?php echo $VariableRecommendations?></p></p>
		<div>
		<div class="CSSTableGenerator" >
                <table >
                    <tr class="heading">
                        <td>
                            Lender
                        </td>
                        <td >
                            Product Name
                        </td>
                        <td>
                            Term
                        </td>
						<td>
                            Amortization
                        </td>
                        <td >
                            Monthly<br> Payment
                        </td>
                        <td>
                            Interest<br> Rate
                        </td>
						<td>
                            Initial For<br> Approval
                        </td>
                    </tr>
                     <?php echo $variable_options_html ?>    
                </table>
            </div>
            </div>
		<h3>Fixed Options:</h3>
		<p>With a fixed term mortgage, the rate and payment will stay the same for the entire term of the mortgage. This type of mortgage is suitable if interest rates are rising or if you need to be entirely certain that your payments are fixed (i.e. of you would find an increase in payment to really difficult financially). The benefit of certainty in interest rate and payment comes with the disadvantage where rates are often higher than variable mortgages and there is also an increased risk of higher payout penalties in the event you either refinance your mortgage or sell your property prior to the end of the term. The below table represents the best 12 options* from the 2581 fixed rate mortgages we examined to determine the fit for your goals.</p>
		<div>
		<div class="CSSTableGenerator" >
                <table >
                    <tr class="heading">
                        <td>
                            Lender
                        </td>
                        <td >
                            Product Name
                        </td>
                        <td>
                            Term
                        </td>
						<td>
                            Amortization
                        </td>
                        <td >
                            Monthly<br> Payment
                        </td>
                        <td>
                            Interest<br> Rate
                        </td>
						<td>
                            Initial For<br> Approval
                        </td>
                    </tr>
                    <?php echo $fixed_options_html ?>
                </table>
            </div>
		</div>
		<h3>LOC Options:</h3>
		<p>With a fixed term mortgage, the rate and payment will stay the same for the entire term of the mortgage. This type of mortgage is suitable if interest rates are rising or if you need to be entirely certain that your payments are fixed (i.e. of you would find an increase in payment to really difficult financially). The benefit of certainty in interest rate and payment comes with the disadvantage where rates are often higher than variable mortgages and there is also an increased risk of higher payout penalties in the event you either refinance your mortgage or sell your property prior to the end of the term. The below table represents the best 12 options* from the 2581 fixed rate mortgages we examined to determine the fit for your goals.</p>
		<div>
		<div class="CSSTableGenerator" >
                <table >
                    <tr class="heading">
                        <td>
                            Lender
                        </td>
                        <td >
                            Product Name
                        </td>
                        <td>
                            Term
                        </td>
						<td>
                            Amortization
                        </td>
                        <td >
                            Monthly<br> Payment
                        </td>
                        <td>
                            Interest<br> Rate
                        </td>
						<td>
                            Initial For<br> Approval
                        </td>
                    </tr>
                    <?php echo $loc_options_html ?>
                </table>
            </div>
		</div>
		<h3>Notes:</h3>
		<!--p>* The “Best” mortgages options available are determined based upon the following factors:
		<ul>
		<li>Interest Rate</li>
		<li>Monthly Cost</li>
		<li>Payment flexibility</li>
		<li>Payout Penalty Cost</li>
		<li>Ease of dealing with Lender</li>
		<li>Speed (Responsiveness) Lender</li>
		</ul>
		</p-->
		<!--p>Opportunity.DealNotes.MarketingField(“Notes”))</p-->
		<p><?php echo $notes_first_line ?></p>
		<ul><?php echo $notes_lines_html ?></ul>
		<!--p>Disclosure around past not same as future, simply an analysis of historical interest rate events. (Guy to provide final text to cover off liability. )</p-->
		<div class="button"><button>Close</button><button>Submit</button></div>
	</div>
</div>
</div>
</div>
