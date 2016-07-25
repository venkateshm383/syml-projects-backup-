<?php 
  
 /**
 * @package Form Maker Lite
 * @author Web-Dorado
 * @copyright (C) 2011 Web-Dorado. All rights reserved.
 * @license GNU/GPLv3 http://www.gnu.org/licenses/gpl-3.0.html
 **/

defined('_JEXEC') or die('Restricted access'); 
jimport( 'joomla.application.component.model' );
jimport('joomla.filesystem.file');
jimport('joomla.filesystem.folder');
class formmakerModelformmaker extends JModelLegacy
{	
	function showform()
	{
	
		$input_get = JFactory::getApplication()->input;
		$id=$input_get->getString('id',0);
		$Itemid=$input_get->getString('Itemid'.$id);
		$db = JFactory::getDBO();
		$db->setQuery("SELECT * FROM #__formmaker WHERE id=".$db->escape($id) ); 
		$row = $db->loadObject();
		if ($db->getErrorNum())	{echo $db->stderr(); return false;}	
		if(!$row)
			return false;
		
		if($row->published!=1)
			return false;
		
		$test_theme = $input_get->getString('test_theme');
		$row->theme = (isset($test_theme) ? $test_theme : $row->theme);
		$db->setQuery("SELECT css FROM #__formmaker_themes WHERE id=".$db->escape($row->theme) ); 
		$form_theme = $db->loadResult();
		if ($db->getErrorNum())	{echo $db->stderr(); return false;}	
		
		$label_id= array();
		$label_type= array();
			
		$label_all	= explode('#****#',$row->label_order);
		$label_all 	= array_slice($label_all,0, count($label_all)-1);   
		
		foreach($label_all as $key => $label_each) 
		{
			$label_id_each=explode('#**id**#',$label_each);
			array_push($label_id, $label_id_each[0]);
			
			$label_order_each=explode('#**label**#', $label_id_each[1]);
			
			array_push($label_type, $label_order_each[1]);
		}
		
		return array($row, $Itemid, $label_id, $label_type, $form_theme);
	}

	function savedata($form)
	{	
	
		$input_get = JFactory::getApplication()->input;
		$correct=false;
		$all_files=array();
		$db = JFactory::getDBO();
		@session_start();
		$id=$input_get->getString('id',0);
		$captcha_input=$input_get->getString("captcha_input");
		$recaptcha_response_field=$input_get->getString("recaptcha_response_field");
		$counter=$input_get->getString("counter".$id);
		if(isset($counter))
		{	

			if (isset($captcha_input))
			{				
				$session_wd_captcha_code=isset($_SESSION[$id.'_wd_captcha_code'])?$_SESSION[$id.'_wd_captcha_code']:'-';

				if($captcha_input==$session_wd_captcha_code)
				{
					
					$correct=true;

				}
				else
				{
							echo "<script> alert('".JText::_('WDF_INCORRECT_SEC_CODE')."');
						</script>";
				}
			}	
			
			else
				if(isset($recaptcha_response_field))
				{	
				$privatekey = $form->private_key;
	
					$resp = recaptcha_check_answer ($privatekey,
													$_SERVER["REMOTE_ADDR"],
													$_POST["recaptcha_challenge_field"],
													$recaptcha_response_field);
					if($resp->is_valid)
					{
						$correct=true;
	
					}
					else
					{
								echo "<script> alert('".JText::_('WDF_INCORRECT_SEC_CODE')."');
							</script>";
					}
				}	
			
				else	
				{
					
					
					$correct=true;

		

				}

				

				if($correct)

			{

					$result_temp=$this->save_db($counter);

					

					$all_files=$result_temp[0];

					
					if(is_numeric($all_files))		
						$this->remove($all_files);
					else
						if(isset($counter))
							$this->gen_mail($counter, $all_files,$result_temp[1]);
		
				}
	

			return $all_files;
		}

		return $all_files;
			
			
	}
	
	function save_db($counter)
	{
		$input_get = JFactory::getApplication()->input;

		$id=$input_get->getString('id');
		$chgnac=true;	
		$all_files=array();
		$paypal=array();

		$paypal['item_name']=array();

		$paypal['quantity']=array();

		$paypal['amount']=array();

		$paypal['on_os']=array();

		$total=0;

		$form_currency='$';

		$currency_code=array('USD', 'EUR', 'GBP', 'JPY', 'CAD', 'MXN', 'HKD', 'HUF', 'NOK', 'NZD', 'SGD', 'SEK', 'PLN', 'AUD', 'DKK', 'CHF', 'CZK', 'ILS', 'BRL', 'TWD', 'MYR', 'PHP', 'THB');

		$currency_sign=array('$'  , '€'  , '£'  , '¥'  , 'C$', 'Mex$', 'HK$', 'Ft' , 'kr' , 'NZ$', 'S$' , 'kr' , 'zł' , 'A$' , 'kr' , 'CHF' , 'Kč', '₪'  , 'R$' , 'NT$', 'RM' , '₱'  , '฿'  );

		
		
		JTable::addIncludePath(JPATH_ADMINISTRATOR.DS.'components'.DS.'com_formmaker'.DS.'tables');
		$form = JTable::getInstance('formmaker', 'Table');
		$form->load( $id);
		
		if($form->payment_currency)
		$form_currency=	$currency_sign[array_search($form->payment_currency, $currency_code)];
		
		$old = false;
		
		if(isset($form->form))
			$old = true;
		
		$label_id= array();
		$label_label= array();
		$label_type= array();
			
		if($old == false || ($old == true && $form->form=='')) 
				$label_all	= explode('#****#',$form->label_order_current);		
			else
				$label_all	= explode('#****#',$form->label_order);	
		$label_all 	= array_slice($label_all,0, count($label_all)-1);   
		
		foreach($label_all as $key => $label_each) 
		{
			$label_id_each=explode('#**id**#',$label_each);
			array_push($label_id, $label_id_each[0]);
			
			$label_order_each=explode('#**label**#', $label_id_each[1]);
			
			array_push($label_label, $label_order_each[0]);
			array_push($label_type, $label_order_each[1]);
		}
		
		
		
		$db = JFactory::getDBO();
		$db->setQuery("SELECT MAX( group_id ) FROM #__formmaker_submits" ); 
		$db->query();
		$max = $db->loadResult();
		
		if($old == false || ($old == true && $form->form=='')) 
		{
		
			
			foreach($label_type as $key => $type)
			{

				$value='';
				if($type=="type_submit_reset" or $type=="type_map" or $type=="type_editor" or  $type=="type_captcha" or  $type=="type_recaptcha" or  $type=="type_button" or $type=="type_paypal_total")
					continue;

				$i=$label_id[$key];
					
				switch ($type)
				{
					case 'type_text':
					case 'type_password':
					case 'type_textarea':
					case "type_submitter_mail":
					case "type_date":
					case "type_own_select":					
					case "type_country":				
					case "type_number":				
					{
						$value=$input_get->getString('wdform_'.$i."_element".$id);
						break;
					}
					
					case "type_mark_map":				
					{
						$value=$input_get->getString('wdform_'.$i."_long".$id).'***map***'.$input_get->getString('wdform_'.$i."_lat".$id);
						break;
					}
										
					case "type_date_fields":
					{
						$value=$input_get->getString('wdform_'.$i."_day".$id).'-'.$input_get->getString('wdform_'.$i."_month".$id).'-'.$input_get->getString('wdform_'.$i."_year".$id);
						break;
					}
					
					case "type_time":
					{
						$ss=$input_get->getString('wdform_'.$i."_ss".$id);
						if(isset($ss))
							$value=$input_get->getString('wdform_'.$i."_hh".$id).':'.$input_get->getString('wdform_'.$i."_mm".$id).':'.$input_get->getString('wdform_'.$i."_ss".$id);
						else
							$value=$input_get->getString('wdform_'.$i."_hh".$id).':'.$input_get->getString('wdform_'.$i."_mm".$id);
								
						$am_pm=$input_get->getString('wdform_'.$i."_am_pm".$id);
						if(isset($am_pm))
							$value=$value.' '.$input_get->getString('wdform_'.$i."_am_pm".$id);
							
						break;
					}
					
					case "type_phone":
					{
						$value=$input_get->getString('wdform_'.$i."_element_first".$id).' '.$input_get->getString('wdform_'.$i."_element_last".$id);
							
						break;
					}
		
					case "type_name":
					{
				
						$element_title=$input_get->getString('wdform_'.$i."_element_title".$id);
						if(isset($element_title))
							$value=$input_get->getString('wdform_'.$i."_element_title".$id).' '.$input_get->getString('wdform_'.$i."_element_first".$id).' '.$input_get->getString('wdform_'.$i."_element_last".$id).' '.$input_get->getString('wdform_'.$i."_element_middle".$id);
						else
							$value=$input_get->getString('wdform_'.$i."_element_first".$id).' '.$input_get->getString('wdform_'.$i."_element_last".$id);
							
						break;
					}
		
					case "type_file_upload":
					{
						$file = JRequest::getVar('wdform_'.$i.'_file'.$id, null, 'files', 'array');
						if($file['name'])
						{	
							$untilupload = $form->form_fields;

							$untilupload = substr($untilupload, strpos($untilupload,$i.'*:*id*:*type_file_upload'), -1);
							$untilupload = substr($untilupload, 0, strpos($untilupload,'*:*new_field*:'));
							$untilupload = explode('*:*w_field_label_pos*:*',$untilupload);
							$untilupload = $untilupload[1];
							$untilupload = explode('*:*w_destination*:*',$untilupload);
							$destination = $untilupload[0];
							$untilupload = $untilupload[1];
							$untilupload = explode('*:*w_extension*:*',$untilupload);
							$extension 	 = $untilupload[0];
							$untilupload = $untilupload[1];
							$untilupload = explode('*:*w_max_size*:*',$untilupload);
							$max_size 	 = $untilupload[0];
							$untilupload = $untilupload[1];
							
							$fileName = $file['name'];
						
							$fileSize = $file['size'];

							if($fileSize > $max_size*1024)
							{
								echo "<script> alert('".JText::sprintf('WDF_FILE_SIZE_ERROR',$max_size)."');</script>";
								return array($max+1);;
							}
							
							$uploadedFileNameParts = explode('.',$fileName);
							$uploadedFileExtension = array_pop($uploadedFileNameParts);
							$to=strlen($fileName)-strlen($uploadedFileExtension)-1;
							
							$fileNameFree= substr($fileName,0, $to);
							$invalidFileExts = explode(',', $extension);
							$extOk = false;

							foreach($invalidFileExts as $key => $value)
							{
							if(  is_numeric(strpos(strtolower($value), strtolower($uploadedFileExtension) )) )
								{
									$extOk = true;
								}
							}
							 
							if ($extOk == false) 
							{
								echo "<script> alert('".JText::_('WDF_FILE_TYPE_ERROR')."');</script>";
								return array($max+1);;
							}
							
							$fileTemp = $file['tmp_name'];
							$p=1;
							while(file_exists( $destination.DS.$fileName))
							{
							$to=strlen($file['name'])-strlen($uploadedFileExtension)-1;
							$fileName= substr($fileName,0, $to).'('.$p.').'.$uploadedFileExtension;
							$p++;
							}
							
							if(!JFile::upload($fileTemp, $destination.DS.$fileName)) 
							{	
								echo "<script> alert('".JText::_('WDF_FILE_MOVING_ERROR')."');</script>";
								return array($max+1);;
							}

							$value= JURI::root(true).'/'.$destination.'/'.$fileName.'*@@url@@*';
			
							$file['tmp_name']=$destination.DS.$fileName;
							array_push($all_files,$file);

						}
						break;
					}
					
					case 'type_address':
					{
						$value='*#*#*#';
						$element=$input_get->getString('wdform_'.$i."_street1".$id);
						if(isset($element))
						{
							$value=$input_get->getString('wdform_'.$i."_street1".$id);
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_street2".$id);
						if(isset($element))
						{
							$value=$input_get->getString('wdform_'.$i."_street2".$id);
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_city".$id);
						if(isset($element))
						{
							$value=$input_get->getString('wdform_'.$i."_city".$id);
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_state".$id);
						if(isset($element))
						{
							$value=$input_get->getString('wdform_'.$i."_state".$id);
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_postal".$id);
						if(isset($element))
						{
							$value=$input_get->getString('wdform_'.$i."_postal".$id);
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_country".$id);
						if(isset($element))
						{
							$value=$input_get->getString('wdform_'.$i."_country".$id);
							break;
						}
						
						break;
					}
					
					case "type_hidden":				
					{
						$value=$input_get->getString($label_label[$key]);
						break;
					}
					
					case "type_radio":				
					{
						$element=$input_get->getString('wdform_'.$i."_other_input".$id);
						if(isset($element))
						{
							$value=$element;	
							break;
						}
						
						$value=$input_get->getString('wdform_'.$i."_element".$id);
						break;
					}
					
					case "type_checkbox":				
					{
						$start=-1;
						$value='';
						for($j=0; $j<100; $j++)
						{
						
							$element=$input_get->getString('wdform_'.$i."_element".$id.$j);
			
							if(isset($element))
							{
								$start=$j;
								break;
							}
						}
							
						$other_element_id=-1;
						$is_other=$input_get->getString('wdform_'.$i."_allow_other".$id);
						if($is_other=="yes")
						{
							$other_element_id=$input_get->getString('wdform_'.$i."_allow_other_num".$id);
						}
						
						if($start!=-1)
						{
							for($j=$start; $j<100; $j++)
							{
								$element=$input_get->getString('wdform_'.$i."_element".$id.$j);
								if(isset($element))
								if($j==$other_element_id)
								{
									$value=$value.$input_get->getString('wdform_'.$i."_other_input".$id).'***br***';
								}
								else
								
									$value=$value.$input_get->getString('wdform_'.$i."_element".$id.$j).'***br***';
							}
						}
						
						break;
					}
					
					case "type_paypal_price":	
					{		
						$value=0;
						if($input_get->getString('wdform_'.$i."_element_dollars".$id))
							$value=$input_get->getString('wdform_'.$i."_element_dollars".$id);
							
						$value = (int) preg_replace('/\D/', '', $value);
						
						if($input_get->getString('wdform_'.$i."_element_cents".$id))
							$value=$value.'.'.( preg_replace('/\D/', '', $input_get->getString('wdform_'.$i."_element_cents".$id))    );
										
						$total+=(float)($value);
						
						$paypal_option=array();

						if($value!=0)
						{
							array_push ($paypal['item_name'], $label_label[$key]);
							array_push ($paypal['quantity'], $input_get->getString('wdform_'.$i."_element_quantity".$id,1));
							array_push ($paypal['amount'], $value);
							array_push ($paypal['on_os'], $paypal_option);
						}
						$value=$value.$form_currency;
						break;
					}			
					
					case "type_paypal_select":	
					{	
			
						if($input_get->getString('wdform_'.$i."_element_label".$id))
							$value=$input_get->getString('wdform_'.$i."_element_label".$id).' : '.$input_get->getString('wdform_'.$i."_element".$id).$form_currency;
						else
							$value='';
						$total+=(float)($input_get->getString('wdform_'.$i."_element".$id))*(float)($input_get->getString('wdform_'.$i."_element_quantity".$id,1));
						array_push ($paypal['item_name'],$label_label[$key].' '.$input_get->getString('wdform_'.$i."_element_label".$id));
						array_push ($paypal['quantity'], $input_get->getString('wdform_'.$i."_element_quantity".$id,1));
						array_push ($paypal['amount'], $input_get->getString('wdform_'.$i."_element".$id));
						
						$element_quantity_label=$input_get->getString('wdform_'.$i."_element_quantity_label".$id);
						if(isset($element_quantity_label))
							$value.='***br***'.$input_get->getString('wdform_'.$i."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$i."_element_quantity".$id);
						
						$paypal_option=array();
						$paypal_option['on']=array();
						$paypal_option['os']=array();

						for($k=0; $k<50; $k++)
						{
							$temp_val=$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
							if(isset($temp_val))
							{			
								array_push ($paypal_option['on'], $input_get->getString('wdform_'.$i."_element_property_label".$id.$k));
								array_push ($paypal_option['os'], $input_get->getString('wdform_'.$i."_element_property_value".$id.$k));
								$value.='***br***'.$input_get->getString('wdform_'.$i."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
							}
						}
						array_push ($paypal['on_os'], $paypal_option);
						break;
					}
					
					case "type_paypal_radio":				
					{
						
						if($input_get->getString('wdform_'.$i."_element_label".$id))
							$value=$input_get->getString('wdform_'.$i."_element_label".$id).' : '.$input_get->getString('wdform_'.$i."_element".$id).$form_currency;
						else
							$value='';

						$total+=(float)($input_get->getString('wdform_'.$i."_element".$id))*(float)($input_get->getString('wdform_'.$i."_element_quantity".$id,1));
						array_push ($paypal['item_name'], $label_label[$key].' '.$input_get->getString('wdform_'.$i."_element_label".$id));
						array_push ($paypal['quantity'], $input_get->getString('wdform_'.$i."_element_quantity".$id,1));
						array_push ($paypal['amount'], $input_get->getString('wdform_'.$i."_element".$id));
						
						
						$element_quantity_label=$input_get->getString('wdform_'.$i."_element_quantity_label".$id);
						if(isset($element_quantity_label))
							$value.='***br***'.$input_get->getString('wdform_'.$i."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$i."_element_quantity".$id);
						
						
						
						
						
						$paypal_option=array();
						$paypal_option['on']=array();
						$paypal_option['os']=array();

						for($k=0; $k<50; $k++)
						{
							$temp_val=$input_get->getString($i."_element_property_value".$id.$k);
							if(isset($temp_val))
							{			
								array_push ($paypal_option['on'], $input_get->getString('wdform_'.$i."_element_property_label".$id.$k));
								array_push ($paypal_option['os'], $input_get->getString('wdform_'.$i."_element_property_value".$id.$k));
								$value.='***br***'.$input_get->getString('wdform_'.$i."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
							}
						}
						array_push ($paypal['on_os'], $paypal_option);
						break;
					}

					case "type_paypal_shipping":				
					{
						
						if($input_get->getString('wdform_'.$i."_element_label".$id))
							$value=$input_get->getString('wdform_'.$i."_element_label".$id).' : '.$input_get->getString('wdform_'.$i."_element".$id).$form_currency;
						else
							$value='';
						$value=$input_get->getString('wdform_'.$i."_element_label".$id).' - '.$input_get->getString('wdform_'.$i."_element".$id).$form_currency;
						
						$paypal['shipping']=$input_get->getString('wdform_'.$i."_element".$id);

						break;
					}

					case "type_paypal_checkbox":				
					{
						$start=-1;
						$value='';
						for($j=0; $j<100; $j++)
						{
						
							$element=$input_get->getString('wdform_'.$i."_element".$id.$j);
			
							if(isset($element))
							{
								$start=$j;
								break;
							}
						}
						
						$other_element_id=-1;
						$is_other=$input_get->getString('wdform_'.$i."_allow_other".$id);
						if($is_other=="yes")
						{
							$other_element_id=$input_get->getString('wdform_'.$i."_allow_other_num".$id);
						}
						
						if($start!=-1)
						{
							for($j=$start; $j<100; $j++)
							{
								$element=$input_get->getString('wdform_'.$i."_element".$id.$j);
								if(isset($element))
								if($j==$other_element_id)
								{
									$value=$value.$input_get->getString('wdform_'.$i."_other_input".$id).'***br***';
									
								}
								else
								{
							
									$value=$value.$input_get->getString('wdform_'.$i."_element".$id.$j."_label").' - '.($input_get->getString('wdform_'.$i."_element".$id.$j)=='' ? '0' : $input_get->getString('wdform_'.$i."_element".$id.$j)).$form_currency.'***br***';
									$total+=(float)($input_get->getString('wdform_'.$i."_element".$id.$j))*(float)($input_get->getString('wdform_'.$i."_element_quantity".$id,1));
									array_push ($paypal['item_name'], $label_label[$key].' '.$input_get->getString('wdform_'.$i."_element".$id.$j."_label"));
									array_push ($paypal['quantity'], $input_get->getString('wdform_'.$i."_element_quantity".$id,1));
									array_push ($paypal['amount'], $input_get->getString('wdform_'.$i."_element".$id.$j)=='' ? '0' : $input_get->getString('wdform_'.$i."_element".$id.$j));
									$paypal_option=array();
									$paypal_option['on']=array();
									$paypal_option['os']=array();

									for($k=0; $k<50; $k++)
									{
										$temp_val=$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
										if(isset($temp_val))
										{			
											array_push ($paypal_option['on'], $input_get->getString('wdform_'.$i."_element_property_label".$id.$k));
											array_push ($paypal_option['os'], $input_get->getString('wdform_'.$i."_element_property_value".$id.$k));
										}
									}
									array_push ($paypal['on_os'], $paypal_option);
								}
							}
							
							$element_quantity_label=$input_get->getString('wdform_'.$i."_element_quantity_label".$id);
							if(isset($element_quantity_label))
								$value.=$input_get->getString('wdform_'.$i."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$i."_element_quantity".$id).'***br***';
							
							for($k=0; $k<50; $k++)
							{
								$temp_val=$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
								if(isset($temp_val))
								{			
									$value.=$input_get->getString('wdform_'.$i."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$i."_element_property_value".$id.$k).'***br***';
								}
							}
							
						}
						
						
						break;
					}
					
					case "type_star_rating":				
					{
					
						if($input_get->getString('wdform_'.$i."_selected_star_amount".$id)=="")
						$selected_star_amount=0;
						else
						$selected_star_amount=$input_get->getString('wdform_'.$i."_selected_star_amount".$id);
						
						$value=$input_get->getString('wdform_'.$i."_star_amount".$id).'***'.$selected_star_amount.'***star_rating***';									
						break;
					}
				
					case "type_scale_rating":				
					{
																	
						$value=$input_get->getString('wdform_'.$i."_scale_radio".$id,0).'/'.$input_get->getString('wdform_'.$i."_scale_amount".$id);									
						break;
					}
					
					case "type_spinner":				
					{
						$value=$input_get->getString('wdform_'.$i."_element".$id);		
					
						break;
					}
					
					case "type_slider":				
					{
						$value=$input_get->getString('wdform_'.$i."_slider_value".$id);		
					
						break;
					}
					case "type_range":				
					{
						$value = $input_get->getString('wdform_'.$i."_element".$id.'0').'-'.$input_get->getString('wdform_'.$i."_element".$id.'1');	
					
						break;
					}
					case "type_grading":				
					{
						$value ="";
						$items = explode(":",$input_get->getString('wdform_'.$i."_hidden_item".$id));
						for($k=0; $k<sizeof($items)-1; $k++)
						$value .= $input_get->getString('wdform_'.$i."_element".$id.'_'.$k).':';
						$value .= $input_get->getString('wdform_'.$i."_hidden_item".$id).'***grading***';
				
						break;
					}
					
					case "type_matrix":				
					{
					
						$rows_of_matrix=explode("***",$input_get->getString('wdform_'.$i."_hidden_row".$id));
						$rows_count= sizeof($rows_of_matrix)-1;
						$column_of_matrix=explode("***",$input_get->getString('wdform_'.$i."_hidden_column".$id));
						$columns_count= sizeof($column_of_matrix)-1;
						
					
						if($input_get->getString('wdform_'.$i."_input_type".$id)=="radio")
						{
							$input_value="";

							for($k=1; $k<=$rows_count; $k++)
							$input_value.=$input_get->getString('wdform_'.$i."_input_element".$id.$k,0)."***";
							
						}
						if($input_get->getString('wdform_'.$i."_input_type".$id)=="checkbox")
						{
							$input_value="";
							
							for($k=1; $k<=$rows_count; $k++)
							for($j=1; $j<=$columns_count; $j++)
							$input_value.=$input_get->getString('wdform_'.$i."_input_element".$id.$k.'_'.$j,0)."***";
						}
						
						if($input_get->getString('wdform_'.$i."_input_type".$id)=="text")
						{
							$input_value="";
							for($k=1; $k<=$rows_count; $k++)
							for($j=1; $j<=$columns_count; $j++)
							$input_value.=$input_get->getString('wdform_'.$i."_input_element".$id.$k.'_'.$j)."***";
						}
						
						if($input_get->getString('wdform_'.$i."_input_type".$id)=="select")
						{
							$input_value="";
							for($k=1; $k<=$rows_count; $k++)
							for($j=1; $j<=$columns_count; $j++)
							$input_value.=$input_get->getString('wdform_'.$i."_select_yes_no".$id.$k.'_'.$j)."***";	
						}
						
						$value=$rows_count.$input_get->getString('wdform_'.$i."_hidden_row".$id).'***'.$columns_count.$input_get->getString('wdform_'.$i."_hidden_column".$id).'***'.$input_get->getString('wdform_'.$i."_input_type".$id).'***'.$input_value.'***matrix***';	
					
						break;
					}
					
				}

				if($type=="type_address")
					if(	$value=='*#*#*#')
						continue;

				$unique_element=$input_get->getString('wdform_'.$i."_unique".$id);
				if($unique_element=='yes')
				{
					$db->setQuery("SELECT id FROM #__formmaker_submits WHERE form_id='".$db->getEscaped($id)."' and element_label='".$db->getEscaped($i)."' and element_value='".$db->getEscaped($value)."'");					
					$unique = $db->loadResult();
					if ($db->getErrorNum()){echo $db->stderr();	return false;}
		
					if ($unique) 
					{
						echo "<script> alert('".JText::sprintf('WDF_UNIQUE', '"'.$label_label[$key].'"')	."');</script>";		
						return array($max+1);;
					}
				}

				$ip=$_SERVER['REMOTE_ADDR'];
				if($form->savedb)
				{
				$db->setQuery("INSERT INTO #__formmaker_submits (form_id, element_label, element_value, group_id, date, ip) VALUES('".$id."', '".$i."', '".addslashes($value)."','".($max+1)."', now(), '".$ip."')" ); 
				$rows = $db->query();
				if ($db->getErrorNum()){echo $db->stderr();	return false;}
				}
				$chgnac=false;
			}
			
		}
		else
		{
			foreach($label_type as $key => $type)
			{
				$value='';
				if($type=="type_submit_reset" or $type=="type_map" or $type=="type_editor" or  $type=="type_captcha" or  $type=="type_recaptcha" or  $type=="type_button" or $type=="type_paypal_total")
					continue;

				$i=$label_id[$key];
				
				if($type!="type_address")
				{
					$deleted=$input_get->getString($i."_type".$id);
					if(!isset($deleted))
						break;
				}
					
				switch ($type)
				{
					case 'type_text':
					case 'type_password':
					case 'type_textarea':
					case "type_submitter_mail":
					case "type_date":
					case "type_own_select":					
					case "type_country":				
					case "type_number":				
					{
						$value=$input_get->getString($i."_element".$id);
						break;
					}
					
					case "type_mark_map":				
					{
						$value=$input_get->getString($i."_long".$id).'***map***'.$input_get->getString($i."_lat".$id);
						break;
					}
										
					case "type_date_fields":
					{
						$value=$input_get->getString($i."_day".$id).'-'.$input_get->getString($i."_month".$id).'-'.$input_get->getString($i."_year".$id);
						break;
					}
					
					case "type_time":
					{
						$ss=$input_get->getString($i."_ss".$id);
						if(isset($ss))
							$value=$input_get->getString($i."_hh".$id).':'.$input_get->getString($i."_mm".$id).':'.$input_get->getString($i."_ss".$id);
						else
							$value=$input_get->getString($i."_hh".$id).':'.$input_get->getString($i."_mm".$id);
								
						$am_pm=$input_get->getString($i."_am_pm".$id);
						if(isset($am_pm))
							$value=$value.' '.$input_get->getString($i."_am_pm".$id);
							
						break;
					}
					
					case "type_phone":
					{
						$value=$input_get->getString($i."_element_first".$id).' '.$input_get->getString($i."_element_last".$id);
							
						break;
					}
		
					case "type_name":
					{
						$element_title=$input_get->getString($i."_element_title".$id);
						if(isset($element_title))
							$value=$input_get->getString($i."_element_title".$id).' '.$input_get->getString($i."_element_first".$id).' '.$input_get->getString($i."_element_last".$id).' '.$input_get->getString($i."_element_middle".$id);
						else
							$value=$input_get->getString($i."_element_first".$id).' '.$input_get->getString($i."_element_last".$id);
							
						break;
					}
		
					case "type_file_upload":
					{
						$file = JRequest::getVar($i.'_file'.$id, null, 'files', 'array');
						if($file['name'])
						{	
							$untilupload = $form->form;
							
							$pos1 = strpos($untilupload, "***destinationskizb".$i."***");
							$pos2 = strpos($untilupload, "***destinationverj".$i."***");
							$destination = substr($untilupload, $pos1+(23+(strlen($i)-1)), $pos2-$pos1-(23+(strlen($i)-1)));
							$pos1 = strpos($untilupload, "***extensionskizb".$i."***");
							$pos2 = strpos($untilupload, "***extensionverj".$i."***");
							$extension = substr($untilupload, $pos1+(21+(strlen($i)-1)), $pos2-$pos1-(21+(strlen($i)-1)));
							$pos1 = strpos($untilupload, "***max_sizeskizb".$i."***");
							$pos2 = strpos($untilupload, "***max_sizeverj".$i."***");
							$max_size = substr($untilupload, $pos1+(20+(strlen($i)-1)), $pos2-$pos1-(20+(strlen($i)-1)));
							
							$fileName = $file['name'];
							/*$destination = JPATH_SITE.DS.$input_get->getString($i.'_destination');
							$extension = $input_get->getString($i.'_extension');
							$max_size = $input_get->getString($i.'_max_size');*/
						
							$fileSize = $file['size'];

							if($fileSize > $max_size*1024)
							{
								echo "<script> alert('".JText::sprintf('WDF_FILE_SIZE_ERROR',$max_size)."');</script>";
								return array($max+1);;
							}
							
							$uploadedFileNameParts = explode('.',$fileName);
							$uploadedFileExtension = array_pop($uploadedFileNameParts);
							$to=strlen($fileName)-strlen($uploadedFileExtension)-1;
							
							$fileNameFree= substr($fileName,0, $to);
							$invalidFileExts = explode(',', $extension);
							$extOk = false;

							foreach($invalidFileExts as $key => $value)
							{
							if(  is_numeric(strpos(strtolower($value), strtolower($uploadedFileExtension) )) )
								{
									$extOk = true;
								}
							}
							 
							if ($extOk == false) 
							{
								echo "<script> alert('".JText::_('WDF_FILE_TYPE_ERROR')."');</script>";
								return array($max+1);;
							}
							
							$fileTemp = $file['tmp_name'];
							$p=1;
							while(file_exists( $destination.DS.$fileName))
							{
							$to=strlen($file['name'])-strlen($uploadedFileExtension)-1;
							$fileName= substr($fileName,0, $to).'('.$p.').'.$uploadedFileExtension;
							$p++;
							}
							
							if(!JFile::upload($fileTemp, $destination.DS.$fileName)) 
							{	
								echo "<script> alert('".JText::_('WDF_FILE_MOVING_ERROR')."');</script>";
								return array($max+1);;
							}

							$value= JURI::root(true).'/'.$destination.'/'.$fileName.'*@@url@@*';
			
							$file['tmp_name']=$destination.DS.$fileName;
							array_push($all_files,$file);

						}
						break;
					}
					
					case 'type_address':
					{
						$value='*#*#*#';
						$element=$input_get->getString($i."_street1".$id);
						if(isset($element))
						{
							$value=$input_get->getString($i."_street1".$id);
							break;
						}
						
						$element=$input_get->getString($i."_street2".$id);
						if(isset($element))
						{
							$value=$input_get->getString($i."_street2".$id);
							break;
						}
						
						$element=$input_get->getString($i."_city".$id);
						if(isset($element))
						{
							$value=$input_get->getString($i."_city".$id);
							break;
						}
						
						$element=$input_get->getString($i."_state".$id);
						if(isset($element))
						{
							$value=$input_get->getString($i."_state".$id);
							break;
						}
						
						$element=$input_get->getString($i."_postal".$id);
						if(isset($element))
						{
							$value=$input_get->getString($i."_postal".$id);
							break;
						}
						
						$element=$input_get->getString($i."_country".$id);
						if(isset($element))
						{
							$value=$input_get->getString($i."_country".$id);
							break;
						}
						
						break;
					}
					
					case "type_hidden":				
					{
						$value=$input_get->getString($label_label[$key]);
						break;
					}
					
					case "type_radio":				
					{
						$element=$input_get->getString($i."_other_input".$id);
						if(isset($element))
						{
							$value=$element;	
							break;
						}
						
						$value=$input_get->getString($i."_element".$id);
						break;
					}
					
					case "type_checkbox":				
					{
						$start=-1;
						$value='';
						for($j=0; $j<100; $j++)
						{
						
							$element=$input_get->getString($i."_element".$id.$j);
			
							if(isset($element))
							{
								$start=$j;
								break;
							}
						}
							
						$other_element_id=-1;
						$is_other=$input_get->getString($i."_allow_other".$id);
						if($is_other=="yes")
						{
							$other_element_id=$input_get->getString($i."_allow_other_num".$id);
						}
						
						if($start!=-1)
						{
							for($j=$start; $j<100; $j++)
							{
								$element=$input_get->getString($i."_element".$id.$j);
								if(isset($element))
								if($j==$other_element_id)
								{
									$value=$value.$input_get->getString($i."_other_input".$id).'***br***';
								}
								else
								
									$value=$value.$input_get->getString($i."_element".$id.$j).'***br***';
							}
						}
						
						break;
					}
					
					case "type_paypal_price":	

					{		

						$value=0;
					  
						if($input_get->getString($i."_element_dollars".$id))

							$value=$input_get->getString($i."_element_dollars".$id);
							

						$value = (int) preg_replace('/\D/', '', $value);

						
						
						if($input_get->getString($i."_element_cents".$id))

							$value=$value.'.'.( preg_replace('/\D/', '', $input_get->getString($i."_element_cents".$id)));

										

						$total+=(float)($value);

						

						$paypal_option=array();



						if($value!=0)

						{
					   
							array_push ($paypal['item_name'], $label_label[$key]);

							array_push ($paypal['quantity'], $input_get->getString($i."_element_quantity".$id,1));

							array_push ($paypal['amount'], $value);

							array_push ($paypal['on_os'], $paypal_option);

						}

						$value=$value.$form_currency;

						break;

					}		

					case "type_paypal_select":	

					{	

						if($input_get->getString($i."_element_label".$id))
							$value=$input_get->getString($i."_element_label".$id).' : '.$input_get->getString($i."_element".$id).$form_currency;
						else
							$value='';

						$total+=(float)($input_get->getString($i."_element".$id))*(float)($input_get->getString($i."_element_quantity".$id,1));

						array_push ($paypal['item_name'],$label_label[$key].' '.$input_get->getString($i."_element_label".$id));

						array_push ($paypal['quantity'], $input_get->getString($i."_element_quantity".$id,1));

						array_push ($paypal['amount'], $input_get->getString($i."_element".$id));

						

						$element_quantity_label=$input_get->getString($i."_element_quantity_label".$id);

						if(isset($element_quantity_label))

							$value.='***br***'.$input_get->getString($i."_element_quantity_label".$id).': '.$input_get->getString($i."_element_quantity".$id);

						

						$paypal_option=array();

						$paypal_option['on']=array();

						$paypal_option['os']=array();



						for($k=0; $k<50; $k++)

						{
							$temp_val=$input_get->getString($i."_element_property_value".$id.$k);

							if(isset($temp_val))

							{			

								array_push ($paypal_option['on'], $input_get->getString($i."_element_property_label".$id.$k));

								array_push ($paypal_option['os'], $input_get->getString($i."_element_property_value".$id.$k));

								$value.='***br***'. $input_get->getString($i."_element_property_label".$id.$k).': '.$input_get->getString($i."_element_property_value".$id.$k);

							}

						}

						array_push ($paypal['on_os'], $paypal_option);

						break;

					}
					
						case "type_paypal_radio":				

					{

			

						if($input_get->getString($i."_element_label".$id))
							$value=$input_get->getString($i."_element_label".$id).' : '.$input_get->getString($i."_element".$id).$form_currency;
						else
							$value='';

						$total+=(float)($input_get->getString($i."_element".$id))*(float)($input_get->getString($i."_element_quantity".$id,1));

						array_push ($paypal['item_name'], $label_label[$key].' '.$input_get->getString($i."_element_label".$id));

						array_push ($paypal['quantity'], $input_get->getString($i."_element_quantity".$id,1));

						array_push ($paypal['amount'], $input_get->getString($i."_element".$id));

						

						

						$element_quantity_label=$input_get->getString($i."_element_quantity_label".$id);

						if(isset($element_quantity_label))

							$value.='***br***'.$input_get->getString($i."_element_quantity_label".$id).': '.$input_get->getString($i."_element_quantity".$id);

						

						

						

						

						

						$paypal_option=array();

						$paypal_option['on']=array();

						$paypal_option['os']=array();

		

						for($k=0; $k<50; $k++)

						{

							$temp_val=$input_get->getString($i."_element_property_value".$id.$k);

							if(isset($temp_val))

							{			

								array_push ($paypal_option['on'], $input_get->getString($i."_element_property_label".$id.$k));

								array_push ($paypal_option['os'], $input_get->getString($i."_element_property_value".$id.$k));

								$value.='***br***'.$input_get->getString($i."_element_property_label".$id.$k).': '.$input_get->getString($i."_element_property_value".$id.$k);

							}

						}

						array_push ($paypal['on_os'], $paypal_option);

						break;

					}
					
								case "type_paypal_shipping":				

					{

						

						if($input_get->getString($i."_element_label".$id))
							$value=$input_get->getString($i."_element_label".$id).' : '.$input_get->getString($i."_element".$id).$form_currency;
						else
							$value='';

						

						$paypal['shipping']=$input_get->getString($i."_element".$id);



						break;

					}



					case "type_paypal_checkbox":				

					{

						$start=-1;

						$value='';

						for($j=0; $j<100; $j++)

						{

						

							$element=$input_get->getString($i."_element".$id.$j);

			

							if(isset($element))

							{

								$start=$j;

								break;

							}

						}

						

						$other_element_id=-1;

						$is_other=$input_get->getString($i."_allow_other".$id);

						if($is_other=="yes")

						{

							$other_element_id=$input_get->getString($i."_allow_other_num".$id);

						}

						

						if($start!=-1)

						{

							for($j=$start; $j<100; $j++)

							{

								$element=$input_get->getString($i."_element".$id.$j);

								if(isset($element))

								if($j==$other_element_id)

								{

									$value=$value.$input_get->getString($i."_other_input".$id).'***br***';

									

								}

								else

								{

							

									$value=$value.$input_get->getString($i."_element".$id.$j."_label").' - '.($input_get->getString($i."_element".$id.$j)=='' ? '0' : $input_get->getString($i."_element".$id.$j)).$form_currency.'***br***';

									$total+=(float)($input_get->getString($i."_element".$id.$j))*(float)($input_get->getString($i."_element_quantity".$id,1));

									array_push ($paypal['item_name'], $label_label[$key].' '.$input_get->getString($i."_element".$id.$j."_label"));

									array_push ($paypal['quantity'], $input_get->getString($i."_element_quantity".$id,1));

									array_push ($paypal['amount'], $input_get->getString($i."_element".$id.$j)=='' ? '0' : $input_get->getString($i."_element".$id.$j));

									$paypal_option=array();

									$paypal_option['on']=array();

									$paypal_option['os']=array();



									for($k=0; $k<50; $k++)

									{

										$temp_val=$input_get->getString($i."_element_property_value".$id.$k);

										if(isset($temp_val))

										{			

											array_push ($paypal_option['on'], $input_get->getString($i."_element_property_label".$id.$k));

											array_push ($paypal_option['os'], $input_get->getString($i."_element_property_value".$id.$k));

										}

									}

									array_push ($paypal['on_os'], $paypal_option);

								}

							}

							

							$element_quantity_label=$input_get->getString($i."_element_quantity_label".$id);

							if(isset($element_quantity_label))

								$value.=$input_get->getString($i."_element_quantity_label".$id).': '.$input_get->getString($i."_element_quantity".$id).'***br***';

							

							for($k=0; $k<50; $k++)

							{

								$temp_val=$input_get->getString($i."_element_property_value".$id.$k);

								if(isset($temp_val))

								{			

									$value.=$input_get->getString($i."_element_property_label".$id.$k).': '.$input_get->getString($i."_element_property_value".$id.$k).'***br***';

								}

							}

							

						}

						

						

						break;

					}
					
						case "type_star_rating":				
					{
					
						if($input_get->getString($i."_selected_star_amount".$id)=="")
						$selected_star_amount=0;
						else
						$selected_star_amount=$input_get->getString($i."_selected_star_amount".$id);
						
						$value=$input_get->getString($i."_star_amount".$id).'***'.$selected_star_amount.'***'.$input_get->getString($i."_star_color".$id).'***star_rating***';									
						break;
					}
					

						case "type_scale_rating":				
					{
																	
						$value=$input_get->getString($i."_scale_radio".$id,0).'/'.$input_get->getString($i."_scale_amount".$id);									
						break;
					}
					
						case "type_spinner":				
					{
						$value=$input_get->getString($i."_element".$id);		
					
						break;
					}
					
						case "type_slider":				
					{
						$value=$input_get->getString($i."_slider_value".$id);		
					
						break;
					}
					case "type_range":				
					{
						$value = $input_get->getString($i."_element".$id.'0').'-'.$input_get->getString($i."_element".$id.'1');	
					
						break;
					}
					case "type_grading":				
					{
						$value ="";
						$items = explode(":",$input_get->getString($i."_hidden_item".$id));
						for($k=0; $k<sizeof($items)-1; $k++)
						$value .= $input_get->getString($i."_element".$id.$k).':';
						$value .= $input_get->getString($i."_hidden_item".$id).'***grading***';
				
						break;
					}
					
					
					case "type_matrix":				
					{
						$rows_of_matrix=explode("***",$input_get->getString($i."_hidden_row".$id));
						$rows_count= sizeof($rows_of_matrix)-1;
						$column_of_matrix=explode("***",$input_get->getString($i."_hidden_column".$id));
						$columns_count= sizeof($column_of_matrix)-1;
						$row_ids=explode(",",substr($input_get->getString($i."_row_ids".$id), 0, -1));
						$column_ids=explode(",",substr($input_get->getString($i."_column_ids".$id), 0, -1));
						
					
						if($input_get->getString($i."_input_type".$id)=="radio")
							{
							$input_value="";
							foreach($row_ids as $row_id)
							$input_value.=$input_get->getString($i."_input_element".$id.$row_id,0)."***";
							
							}
						if($input_get->getString($i."_input_type".$id)=="checkbox")
							{
							
							$input_value="";
							foreach($row_ids as $row_id)
							foreach($column_ids as $column_id)
							$input_value.=$input_get->getString($i."_input_element".$id.$row_id.'_'.$column_id,0)."***";
							
						
							}
						
						if($input_get->getString($i."_input_type".$id)=="text")
							{
							$input_value="";
							foreach($row_ids as $row_id)
							foreach($column_ids as $column_id)
							$input_value.=$input_get->getString($i."_input_element".$id.$row_id.'_'.$column_id)."***";
							}
						
						if($input_get->getString($i."_input_type".$id)=="select")
							{
							$input_value="";
							foreach($row_ids as $row_id)
							foreach($column_ids as $column_id)
							$input_value.=$input_get->getString($i."_select_yes_no".$id.$row_id.'_'.$column_id)."***";	
							}
						
						
						$value=$rows_count.'***'.$input_get->getString($i."_hidden_row".$id).$columns_count.'***'.$input_get->getString($i."_hidden_column".$id).$input_get->getString($i."_input_type".$id).'***'.$input_value.'***matrix***';	
					
						break;
					}
				}
		
				if($type=="type_address")
					if(	$value=='*#*#*#')
						continue;

				$unique_element=$input_get->getString($i."_unique".$id);
				if($unique_element=='yes')
				{
					$db->setQuery("SELECT id FROM #__formmaker_submits WHERE form_id='".$db->escape($id)."' and element_label='".$db->escape($i)."' and element_value='".$db->escape($value)."'");					
					$unique = $db->loadResult();
					if ($db->getErrorNum()){echo $db->stderr();	return false;}
		
					if ($unique) 
					{
						echo "<script> alert('".JText::sprintf('WDF_UNIQUE', '"'.$label_label[$key].'"')	."');</script>";		
						return array($max+1);;
					}
				}

				$ip=$_SERVER['REMOTE_ADDR'];
				
				$db->setQuery("INSERT INTO #__formmaker_submits (form_id, element_label, element_value, group_id, date, ip) VALUES('".$id."', '".$i."', '".addslashes($value)."','".($max+1)."', now(), '".$ip."')" ); 
				$rows = $db->query();
				if ($db->getErrorNum()){echo $db->stderr();	return false;}
				$chgnac=false;
				
					}

		}

		$str='';	

			

		if($form->paypal_mode)	

		if($paypal['item_name'])	

		if($paypal['amount'][0])	

		{

						

			$tax=$form->tax;

			$currency=$form->payment_currency;

			$business=$form->paypal_email;

			

			

			$ip=$_SERVER['REMOTE_ADDR'];

			

			$total2=round($total, 2);



			$db->setQuery("INSERT INTO #__formmaker_submits (form_id, element_label, element_value, group_id, date, ip) VALUES('".$id."', 'item_total', '".$total2.$form_currency."','".($max+1)."', now(), '".$ip."')" );

			

			$rows = $db->query();

			

			

			

			$total=$total+($total*$tax)/100;

			

			if(isset($paypal['shipping']))

			{

				$total=$total+$paypal['shipping'];

			}



			$total=round($total, 2);

			

			if ($db->getErrorNum()){echo $db->stderr();	return false;}

		

			$db->setQuery("INSERT INTO #__formmaker_submits (form_id, element_label, element_value, group_id, date, ip) VALUES('".$id."', 'total', '".$total.$form_currency."','".($max+1)."', now(), '".$ip."')" );

			

			$rows = $db->query();

			

			if ($db->getErrorNum()){echo $db->stderr();	return false;}

		

			

			$db->setQuery("INSERT INTO #__formmaker_submits (form_id, element_label, element_value, group_id, date, ip) VALUES('".$id."', '0', 'In progress','".($max+1)."', now(), '".$ip."')" );

			

			$rows = $db->query();

			

			if ($db->getErrorNum()){echo $db->stderr();	return false;}

		

			

			

			$str='';

			

			if($form->checkout_mode=="production")

				$str.="https://www.paypal.com/cgi-bin/webscr?";

			else		

				$str.="https://www.sandbox.paypal.com/cgi-bin/webscr?";

				

			$str.="currency_code=".$currency;

			$str.="&business=".$business;

			$str.="&cmd="."_cart";
			$str.="&charset=utf8";

			$str.="&notify_url=".JUri::root().'index.php?option=com_formmaker%26view=checkpaypal%26form_id='.$id.'%26group_id='.($max+1);

			$str.="&upload="."1";



			if(isset($paypal['shipping']))

			{

					$str=$str."&shipping_1=".$paypal['shipping'];

				//	$str=$str."&weight_cart=".$paypal['shipping'];

				//	$str=$str."&shipping2=3".$paypal['shipping'];

					$str=$str."&no_shipping=2";

			}



			

			foreach($paypal['item_name'] as $pkey => $pitem_name)

			{

				$str=$str."&item_name_".($pkey+1)."=".$pitem_name;

				$str=$str."&amount_".($pkey+1)."=".$paypal['amount'][$pkey];

				$str=$str."&quantity_".($pkey+1)."=".$paypal['quantity'][$pkey];

				

				if($tax)

					$str=$str."&tax_rate_".($pkey+1)."=".$tax;

					

				if($paypal['on_os'][$pkey])

				{

					foreach($paypal['on_os'][$pkey]['on'] as $on_os_key => $on_item_name)

					{

							

						$str=$str."&on".$on_os_key."_".($pkey+1)."=".$on_item_name;

						$str=$str."&os".$on_os_key."_".($pkey+1)."=".$paypal['on_os'][$pkey]['os'][$on_os_key];

					}

				}

			

			}
		}

		if($chgnac)
		{		$mainframe = JFactory::getApplication();
	
				if(count($all_files)==0)
					$mainframe->redirect($_SERVER["REQUEST_URI"], addslashes(JText::_('WDF_EMPTY_SUBMIT')));
		}
		
		return array($all_files, $str);
	}
	
	
	
	function gen_mail($counter, $all_files, $str)
	{
		$input_get = JFactory::getApplication()->input;
		@session_start();
		$mainframe = JFactory::getApplication();
		$id=$input_get->getString('id');
		$Itemid=$input_get->getString('Itemid'.$id);
		JTable::addIncludePath(JPATH_ADMINISTRATOR.DS.'components'.DS.'com_formmaker'.DS.'tables');
		$row = JTable::getInstance('formmaker', 'Table');
		$row->load( $id);
		$total=0;
		$form_currency='$';
		$currency_code=array('USD', 'EUR', 'GBP', 'JPY', 'CAD', 'MXN', 'HKD', 'HUF', 'NOK', 'NZD', 'SGD', 'SEK', 'PLN', 'AUD', 'DKK', 'CHF', 'CZK', 'ILS', 'BRL', 'TWD', 'MYR', 'PHP', 'THB');
		$currency_sign=array('$'  , '€'  , '£'  , '¥'  , 'C$', 'Mex$', 'HK$', 'Ft' , 'kr' , 'NZ$', 'S$' , 'kr' , 'zł' , 'A$' , 'kr' , 'CHF' , 'Kč', '₪'  , 'R$' , 'NT$', 'RM' , '₱'  , '฿'  );
		
		if($row->payment_currency)
			$form_currency=	$currency_sign[array_search($row->payment_currency, $currency_code)];
		
		$old = false;
		
		if(isset($row->form) )
			$old = true;

			$cc=array();
			$label_order_original= array();
			$label_order_ids= array();
			$label_type= array();
			
			if($old == false || ($old == true && $row->form=='')) 
			$label_all	= explode('#****#',$row->label_order_current);
			else
			$label_all	= explode('#****#',$row->label_order);
			$label_all 	= array_slice($label_all,0, count($label_all)-1);   
			foreach($label_all as $key => $label_each) 
			{
				$label_id_each=explode('#**id**#',$label_each);
				$label_id=$label_id_each[0];
				array_push($label_order_ids,$label_id);
				
				$label_oder_each=explode('#**label**#', $label_id_each[1]);							
				$label_order_original[$label_id]=$label_oder_each[0];
				$label_type[$label_id]=$label_oder_each[1];
			}
		
			$list='<table border="1" cellpadding="3" cellspacing="0" style="width:600px;">';
			

				if($old == false || ($old == true && $row->form=='')) 
		{

			foreach($label_order_ids as $key => $label_order_id)
			{
				$i=$label_order_id;
				$type=$label_type[$i];

				if($type!="type_map" and  $type!="type_submit_reset" and  $type!="type_editor" and  $type!="type_captcha" and  $type!="type_recaptcha" and  $type!="type_button")
				{	
					$element_label=$label_order_original[$i];
							
					switch ($type)
					{
						case 'type_text':
						case 'type_password':
						case 'type_textarea':
						case "type_date":
						case "type_own_select":					
						case "type_country":				
						case "type_number":	
						{
							$element=$input_get->getString('wdform_'.$i."_element".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;
						
						
						}
						
						case "type_hidden":				
						{
							$element=$input_get->getString($element_label);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;
						}
						
						
						case "type_mark_map":
						{
							$element=$input_get->getString('wdform_'.$i."_long".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >Longitude:'.$input_get->getString('wdform_'.$i."_long".$id).'<br/>Latitude:'.$input_get->getString('wdform_'.$i."_lat".$id).'</td></tr>';
							}
							break;		
						}
											
						case "type_submitter_mail":
						{
							$element=$input_get->getString('wdform_'.$i."_element".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';		
							}
							break;		
						}
						
						case "type_time":
						{
							
							$hh=$input_get->getString('wdform_'.$i."_hh".$id);
							if(isset($hh))
							{
								$ss=$input_get->getString('wdform_'.$i."_ss".$id);
								if(isset($ss))
									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString('wdform_'.$i."_hh".$id).':'.$input_get->getString('wdform_'.$i."_mm".$id).':'.$input_get->getString('wdform_'.$i."_ss".$id);
								else
									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString('wdform_'.$i."_hh".$id).':'.$input_get->getString('wdform_'.$i."_mm".$id);
								$am_pm=$input_get->getString('wdform_'.$i."_am_pm".$id);
								if(isset($am_pm))
									$list=$list.' '.$input_get->getString('wdform_'.$i."_am_pm".$id).'</td></tr>';
								else
									$list=$list.'</td></tr>';
							}
								
							break;
						}
						
						case "type_phone":
						{
							$element_first=$input_get->getString('wdform_'.$i."_element_first".$id);
							if(isset($element_first))
							{
									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString('wdform_'.$i."_element_first".$id).' '.$input_get->getString('wdform_'.$i."_element_last".$id).'</td></tr>';
							}	
							break;
						}
						
						case "type_name":
						{
							$element_first=$input_get->getString('wdform_'.$i."_element_first".$id);
							if(isset($element_first))
							{
								$element_title=$input_get->getString('wdform_'.$i."_element_title".$id);
								if(isset($element_title))
									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString('wdform_'.$i."_element_title".$id).' '.$input_get->getString('wdform_'.$i."_element_first".$id).' '.$input_get->getString('wdform_'.$i."_element_last".$id).' '.$input_get->getString('wdform_'.$i."_element_middle".$id).'</td></tr>';
								else
									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString('wdform_'.$i."_element_first".$id).' '.$input_get->getString('wdform_'.$i."_element_last".$id).'</td></tr>';
							}	   
							break;		
						}
						
						case "type_address":
						{

							$element=$input_get->getString('wdform_'.$i."_street1".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString('wdform_'.$i."_street1".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_street2".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString('wdform_'.$i."_street2".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_city".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString('wdform_'.$i."_city".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_state".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString('wdform_'.$i."_state".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_postal".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString('wdform_'.$i."_postal".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString('wdform_'.$i."_country".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString('wdform_'.$i."_country".$id).'</td></tr>';
							break;
						}
						
						break;


						}


						case "type_date_fields":
						{
							$day=$input_get->getString('wdform_'.$i."_day".$id);
							if(isset($day))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString('wdform_'.$i."_day".$id).'-'.$input_get->getString('wdform_'.$i."_month".$id).'-'.$input_get->getString('wdform_'.$i."_year".$id).'</td></tr>';
							}
							break;
						}
						
						case "type_radio":
						{
							$element=$input_get->getString('wdform_'.$i."_other_input".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString('wdform_'.$i."_other_input".$id).'</td></tr>';
								break;
							}	
							
							$element=$input_get->getString('wdform_'.$i."_element".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;	
						}
						
						case "type_checkbox":
						{
							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >';
						
							$start=-1;
							for($j=0; $j<100; $j++)
							{
								$element=$input_get->getString('wdform_'.$i."_element".$id.$j);
								if(isset($element))
								{
									$start=$j;
									break;
								}
							}	
							
							$other_element_id=-1;
							$is_other=$input_get->getString('wdform_'.$i."_allow_other".$id);
							if($is_other=="yes")
							{
								$other_element_id=$input_get->getString('wdform_'.$i."_allow_other_num".$id);
							}
							
					
							if($start!=-1)
							{
								for($j=$start; $j<100; $j++)
								{
									
									$element=$input_get->getString('wdform_'.$i."_element".$id.$j);
									if(isset($element))
									if($j==$other_element_id)
									{
										$list=$list.$input_get->getString('wdform_'.$i."_other_input".$id).'<br>';
									}
									else
									
										$list=$list.$input_get->getString('wdform_'.$i."_element".$id.$j).'<br>';
								}
								$list=$list.'</td></tr>';
							}
										
							
							break;
						}
						case "type_paypal_price":	
						{		
							$value=0;
							if($input_get->getString('wdform_'.$i."_element_dollars".$id))
								$value=$input_get->getString('wdform_'.$i."_element_dollars".$id);
							
							if($input_get->getString('wdform_'.$i."_element_cents".$id))
								$value=$value.'.'.$input_get->getString('wdform_'.$i."_element_cents".$id);
						
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$value.$form_currency.'</td></tr>';
							break;

						}			
				
						case "type_paypal_select":	
						{	
							if($input_get->getString('wdform_'.$i."_element_label".$id))
								$value=$input_get->getString('wdform_'.$i."_element_label".$id).' : '.$input_get->getString('wdform_'.$i."_element".$id).$form_currency;
							else
								$value='';
							$element_quantity_label=$input_get->getString('wdform_'.$i."_element_quantity_label".$id);
								if(isset($element_quantity_label))
									$value.='<br/>'.$input_get->getString('wdform_'.$i."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$i."_element_quantity".$id);
					
							for($k=0; $k<50; $k++)
							{
								$temp_val=$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
								if(isset($temp_val))
								{			
									$value.='<br/>'.$input_get->getString('wdform_'.$i."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
								}
							}
							
							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$value.'</pre></td></tr>';					
							break;
						}
				
						case "type_paypal_radio":				
						{
									
							if($input_get->getString('wdform_'.$i."_element_label".$id))
								$value=$input_get->getString('wdform_'.$i."_element_label".$id).' : '.$input_get->getString('wdform_'.$i."_element".$id).$form_currency;
							else
								$value='';
							$element_quantity_label=$input_get->getString('wdform_'.$i."_element_quantity_label".$id);
								if(isset($element_quantity_label))
									$value.='<br/>'.$input_get->getString('wdform_'.$i."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$i."_element_quantity".$id);
					
							for($k=0; $k<50; $k++)
							{
								$temp_val=$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
								if(isset($temp_val))
								{			
									$value.='<br/>'.$input_get->getString('wdform_'.$i."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
								}
							}
							
							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$value.'</pre></td></tr>';				
							
							break;	
						}

						case "type_paypal_shipping":				
						{
							
							if($input_get->getString('wdform_'.$i."_element_label".$id))
								$value=$input_get->getString('wdform_'.$i."_element_label".$id).' : '.$input_get->getString('wdform_'.$i."_element".$id).$form_currency;
							else
								$value='';
							
							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$value.'</pre></td></tr>';				

							break;
						}

						case "type_paypal_checkbox":				
						{
						
							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >';
						
							$start=-1;
							for($j=0; $j<100; $j++)
							{
								$element=$input_get->getString('wdform_'.$i."_element".$id.$j);
								if(isset($element))
								{
									$start=$j;
									break;
								}
							}	
							
							if($start!=-1)
							{
								for($j=$start; $j<100; $j++)
								{
									
									$element=$input_get->getString('wdform_'.$i."_element".$id.$j);
									if(isset($element))
									{
										$list=$list.$input_get->getString('wdform_'.$i."_element".$id.$j."_label").' - '.($input_get->getString('wdform_'.$i."_element".$id.$j)=='' ? '0'.$form_currency : $input_get->getString('wdform_'.$i."_element".$id.$j)).$form_currency.'<br>';
									}
								}
								
								
							}
							$element_quantity_label=$input_get->getString('wdform_'.$i."_element_quantity_label".$id);
								if(isset($element_quantity_label))
									$list=$list.'<br/>'.$input_get->getString('wdform_'.$i."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$i."_element_quantity".$id);
					
							for($k=0; $k<50; $k++)
							{
								$temp_val=$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
								if(isset($temp_val))
								{			
									$list=$list.'<br/>'.$input_get->getString('wdform_'.$i."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$i."_element_property_value".$id.$k);
								}
							}
														
							$list=$list.'</td></tr>';
							break;
						}
						
						case "type_paypal_total":				
						{
						
						
							$element=$input_get->getString('wdform_'.$i."_paypal_total".$id);

							
							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';				



							break;

						}


						case "type_star_rating":
						{
							$element=$input_get->getString('wdform_'.$i."_star_amount".$id);
							$selected=$input_get->getString('wdform_'.$i."_selected_star_amount".$id,0);
							
							
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$selected.'/'.$element.'</pre></td></tr>';					
							}
							break;
						}
						

						case "type_scale_rating":
						{
						$element=$input_get->getString('wdform_'.$i."_scale_amount".$id);
						$selected=$input_get->getString('wdform_'.$i."_scale_radio".$id,0);
						
							
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$selected.'/'.$element.'</pre></td></tr>';					
							}
							break;
						}
						
						case "type_spinner":
						{

							$element=$input_get->getString('wdform_'.$i."_element".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;
						}
						
						case "type_slider":
						{

							$element=$input_get->getString('wdform_'.$i."_slider_value".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;
						}
						case "type_range":
						{

							$element0=$input_get->getString('wdform_'.$i."_element".$id.'0');
							$element1=$input_get->getString('wdform_'.$i."_element".$id.'1');
							if(isset($element0) || isset($element1))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">From:'.$element0.'<span style="margin-left:6px">To</span>:'.$element1.'</pre></td></tr>';					
							}
							break;
						}
						
						case "type_grading":
						{
							$element=$input_get->getString('wdform_'.$i."_hidden_item".$id);
							$grading = explode(":",$element);
							$items_count = sizeof($grading)-1;
							
							$element = "";
							$total = "";
							
							for($k=0;$k<$items_count;$k++)

							{
								$element .= $grading[$k].":".$input_get->getString('wdform_'.$i."_element".$id.'_'.$k)." ";
								$total += $input_get->getString('wdform_'.$i."_element".$id.'_'.$k);
							}

							$element .="Total:".$total;

																				
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;
						}
						
						case "type_matrix":
						{
						
							
							$input_type=$input_get->getString('wdform_'.$i."_input_type".$id); 
					
							$mat_rows=explode("***",$input_get->getString('wdform_'.$i."_hidden_row".$id));
							$rows_count= sizeof($mat_rows)-1;
							$mat_columns=explode("***",$input_get->getString('wdform_'.$i."_hidden_column".$id));
							$columns_count= sizeof($mat_columns)-1;
							
					
										
							$matrix="<table>";
										
								$matrix .='<tr><td></td>';
							
							for( $k=1;$k< count($mat_columns) ;$k++)
								$matrix .='<td style="background-color:#BBBBBB; padding:5px; ">'.$mat_columns[$k].'</td>';
								$matrix .='</tr>';
							
							$aaa=Array();
							
							for($k=1; $k<=$rows_count; $k++)
							{
							$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px;">'.$mat_rows[$k].'</td>';
							
								if($input_type=="radio")
								{
							
							
									$mat_radio = $input_get->getString('wdform_'.$i."_input_element".$id.$k,0);											
									if($mat_radio==0)
									{
										$checked="";
										$aaa[1]="";
									}
									else
									$aaa=explode("_",$mat_radio);
									
									
									for($j=1; $j<=$columns_count; $j++)
									{
										if($aaa[1]==$j)
										$checked="checked";
										else
										$checked="";
										
										$matrix .='<td style="text-align:center"><input  type="radio" '.$checked.' disabled /></td>';
									
									}
							
								} 
								else
								{
									if($input_type=="checkbox")
									{                
										for($j=1; $j<=$columns_count; $j++)
										{
											$checked = $input_get->getString('wdform_'.$i."_input_element".$id.$k.'_'.$j);                     
											if($checked==1)						
											$checked = "checked";				
											else								
											$checked = "";

											$matrix .='<td style="text-align:center"><input  type="checkbox" '.$checked.' disabled /></td>';
									
										}
								
									}
									else
									{
										if($input_type=="text")
										{
																  
											for($j=1; $j<=$columns_count; $j++)
											{
												$checked = $input_get->getString('wdform_'.$i."_input_element".$id.$k.'_'.$j);
												$matrix .='<td style="text-align:center"><input  type="text" value="'.$checked.'" disabled /></td>';
								
											}
										
										}
										else{
											for($j=1; $j<=$columns_count; $j++)
											{
												$checked = $input_get->getString('wdform_'.$i."_select_yes_no".$id.$k.'_'.$j);
												$matrix .='<td style="text-align:center">'.$checked.'</td>';
											
							
										
											}
										}
									
									}
									
								}
								$matrix .='</tr>';
							
							}
							 $matrix .='</table>';

		
		
		
																			
							if(isset($matrix))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$matrix.'</pre></td></tr>';					
							}
						
							break;
						}
					
						
						default: break;
					}
				
				}	
				
			}
			
			$list=$list.'</table>';
			$list = wordwrap($list, 70, "\n", true);

			$config = JFactory::getConfig();
			if($row->mail_from)
				$site_mailfrom = $row->mail_from;
			else
				$site_mailfrom=$config->get( 'config.mailfrom' );								

			if($row->mail_from_name)
				$site_fromname = $row->mail_from_name;
			else
				$site_fromname=$config->get( 'config.fromname' );	
				
			for($k=0;$k<count($all_files);$k++)
				$attachment[]=array($all_files[$k]['tmp_name'], $all_files[$k]['name'], $all_files[$k]['name'] );
			
			if($row->sendemail)
			if($row->send_to)
			{
				$recipient='';
				$send_tos=explode('**',$row->send_to);
				if($row->mail_from_user)
					$from = $row->mail_from_user;
				else
					$from=$config->get( 'config.mailfrom' );								

				if($row->mail_from_name_user)
					$fromname = $row->mail_from_name_user;
				else
					$fromname=$config->get( 'config.fromname' );								

				$subject 	= $row->title;
				if($row->reply_to_user)
					$replyto	= $row->reply_to_user;
				
				$new_script = $row->script_mail_user;
				foreach($label_order_original as $key => $label_each) 
				{	
					if(strpos($row->script_mail_user, "%".$label_each."%"))
					{				
						$type=$label_type[$key];
						if($type!="type_submit_reset" or $type!="type_map" or $type!="type_editor" or  $type!="type_captcha" or  $type!="type_recaptcha" or  $type!="type_button")
						{
							$new_value ="";
							switch ($type)
							{
								case 'type_text':
								case 'type_password':
								case 'type_textarea':
								case "type_date":
								case "type_own_select":					
								case "type_country":				
								case "type_number":	
								{
									$element=$input_get->getString('wdform_'.$key."_element".$id);
									if(isset($element))
									{
										$new_value = $element;					
									}
									break;
								
								
								}
								
								case "type_hidden":				
								{
									$element=$input_get->getString($element_label);
									if(isset($element))
									{
										$new_value = $element;	
									}
									break;
								}
								
								
								case "type_mark_map":
								{
									$element=$input_get->getString('wdform_'.$key."_long".$id);
									if(isset($element))
									{
										$new_value = 'Longitude:'.$input_get->getString('wdform_'.$key."_long".$id).'<br/>Latitude:'.$input_get->getString('wdform_'.$key."_lat".$id);
									}
									break;		
								}
													
								case "type_submitter_mail":
								{
									$element=$input_get->getString('wdform_'.$key."_element".$id);
									if(isset($element))
									{
										$new_value = $element;					
									}
									break;		
								}
								
								case "type_time":
								{
									
									$hh=$input_get->getString('wdform_'.$key."_hh".$id);
									if(isset($hh))
									{
										$ss=$input_get->getString('wdform_'.$key."_ss".$id);
										if(isset($ss))
											$new_value = $input_get->getString('wdform_'.$key."_hh".$id).':'.$input_get->getString('wdform_'.$key."_mm".$id).':'.$input_get->getString('wdform_'.$key."_ss".$id);
										else
											$new_value = $input_get->getString('wdform_'.$key."_hh".$id).':'.$input_get->getString('wdform_'.$key."_mm".$id);
										$am_pm=$input_get->getString('wdform_'.$key."_am_pm".$id);
										if(isset($am_pm))
											$new_value=$new_value.' '.$input_get->getString('wdform_'.$key."_am_pm".$id);
										
									}
										
									break;
								}
								
								case "type_phone":
								{
									$element_first=$input_get->getString('wdform_'.$key."_element_first".$id);
									if(isset($element_first))
									{
											$new_value = $input_get->getString('wdform_'.$key."_element_first".$id).' '.$input_get->getString('wdform_'.$key."_element_last".$id);
									}	
									break;
								}
								
								case "type_name":
								{
									$element_first=$input_get->getString('wdform_'.$key."_element_first".$id);
									if(isset($element_first))
									{
										$element_title=$input_get->getString('wdform_'.$key."_element_title".$id);
										if(isset($element_title))
											$new_value = $input_get->getString('wdform_'.$key."_element_title".$id).' '.$input_get->getString('wdform_'.$key."_element_first".$id).' '.$input_get->getString('wdform_'.$key."_element_last".$id).' '.$input_get->getString('wdform_'.$key."_element_middle".$id);
										else
											$new_value = $input_get->getString('wdform_'.$key."_element_first".$id).' '.$input_get->getString('wdform_'.$key."_element_last".$id);
									}	   
									break;		
								}
								
								case "type_address":

									{

										$street1=$input_get->getString('wdform_'.$key."_street1".$id);

										if(isset($street1))

										{

											$new_value=$input_get->getString('wdform_'.$key."_street1".$id);

											break;

										}
										
										$street2=$input_get->getString('wdform_'.$key."_street2".$id);

										if(isset($street2))

										{

											$new_value=$input_get->getString('wdform_'.$key."_street2".$id);

											break;

										}	
										
										$city=$input_get->getString('wdform_'.$key."_city".$id);
										
										if(isset($city))

										{

											$new_value=$input_get->getString('wdform_'.$key."_city".$id);

										break;

										}
										
										$state=$input_get->getString('wdform_'.$key."_state".$id);
										
										if(isset($state))

										{

											$new_value=$input_get->getString('wdform_'.$key."_state".$id);

											break;

										}
											
											$postal=$input_get->getString('wdform_'.$key."_postal".$id);
										
										if(isset($postal))

										{

											$new_value=$input_get->getString('wdform_'.$key."_postal".$id);

											break;
										}	

										
										$country = $input_get->getString('wdform_'.$key."_country".$id);
										
											if(isset($country))
											{

											$new_value=$input_get->getString('wdform_'.$key."_country".$id);

											break;
											}
										

										break;

									}


								case "type_date_fields":
								{
									$day=$input_get->getString('wdform_'.$key."_day".$id);
									if(isset($day))
									{
										$new_value = $input_get->getString('wdform_'.$key."_day".$id).'-'.$input_get->getString('wdform_'.$key."_month".$id).'-'.$input_get->getString('wdform_'.$key."_year".$id);
									}
									break;
								}
								
								case "type_radio":
								{
									$element=$input_get->getString('wdform_'.$key."_other_input".$id);
									if(isset($element))
									{
										$new_value = $input_get->getString('wdform_'.$key."_other_input".$id);
										break;
									}	
									
									$element=$input_get->getString('wdform_'.$key."_element".$id);
									if(isset($element))
									{
										$new_value = $element;					
									}
									break;	
								}
								
								case "type_checkbox":
								{
															
									$start=-1;
									for($j=0; $j<100; $j++)
									{
										$element=$input_get->getString('wdform_'.$key."_element".$id.$j);
										if(isset($element))
										{
											$start=$j;
											break;
										}
									}	
									
									$other_element_id=-1;
									$is_other=$input_get->getString('wdform_'.$key."_allow_other".$id);
									if($is_other=="yes")
									{
										$other_element_id=$input_get->getString('wdform_'.$key."_allow_other_num".$id);
									}
									
							
									if($start!=-1)
									{
										for($j=$start; $j<100; $j++)
										{
											
											$element=$input_get->getString('wdform_'.$key."_element".$id.$j);
											if(isset($element))
											if($j==$other_element_id)
											{
												$new_value = $new_value.$input_get->getString('wdform_'.$key."_other_input".$id).'<br>';
											}
											else
											
												$new_value = $new_value.$input_get->getString('wdform_'.$key."_element".$id.$j).'<br>';
										}
										
									}
												
									
									break;
								}
								
								
								case "type_paypal_price":	
								{		
									$new_value=0;
									if($input_get->getString('wdform_'.$key."_element_dollars".$id))
										$new_value=$input_get->getString('wdform_'.$key."_element_dollars".$id);
									
									if($input_get->getString('wdform_'.$key."_element_cents".$id))
										$new_value=$new_value.'.'.$input_get->getString('wdform_'.$key."_element_cents".$id);
								
									$new_value=$new_value.$form_currency;


										
			
									break;
								}			
								
								case "type_paypal_select":	
								{	
						
									$new_value=$input_get->getString('wdform_'.$key."_element_label".$id).':'.$input_get->getString('wdform_'.$key."_element".$id).$form_currency;
									$element_quantity_label=$input_get->getString('wdform_'.$key."_element_quantity_label".$id);
									if(isset($element_quantity_label))
										$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$key."_element_quantity".$id);
							
									for($k=0; $k<50; $k++)
									{
										$temp_val=$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
										if(isset($temp_val))
										{			
											$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
										}
									}

		
									break;
								}
								
								case "type_paypal_radio":				
								{
									
								$new_value=$input_get->getString('wdform_'.$key."_element_label".$id).' - '.$input_get->getString('wdform_'.$key."_element".$id).$form_currency;
									
									$element_quantity_label=$input_get->getString('wdform_'.$key."_element_quantity_label".$id);
										if(isset($element_quantity_label))
											$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$key."_element_quantity".$id);
							
									for($k=0; $k<50; $k++)
									{
										$temp_val=$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
										if(isset($temp_val))
										{			
											$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
										}
									}
							
									break;
								}

								case "type_paypal_shipping":				
								{
									
									$new_value=$input_get->getString('wdform_'.$key."_element_label".$id).' : '.$input_get->getString('wdform_'.$key."_element".$id).$form_currency;		

									break;
								}

								case "type_paypal_checkbox":				
								{
									$start=-1;
									for($j=0; $j<100; $j++)
									{
										$element=$input_get->getString('wdform_'.$key."_element".$id.$j);
										if(isset($element))
										{
											$start=$j;
											break;
										}
									}	
									
									if($start!=-1)
									{
										for($j=$start; $j<100; $j++)
										{
											
											$element=$input_get->getString('wdform_'.$key."_element".$id.$j);
											if(isset($element))
											{
												$new_value=$new_value.$input_get->getString('wdform_'.$key."_element".$id.$j."_label").' - '.($input_get->getString('wdform_'.$key."_element".$id.$j)=='' ? '0'.$form_currency : $input_get->getString('wdform_'.$key."_element".$id.$j)).$form_currency.'<br>';
											}
										}
									}
									
									$element_quantity_label=$input_get->getString('wdform_'.$key."_element_quantity_label".$id);
										if(isset($element_quantity_label))
											$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$key."_element_quantity".$id);
							
									for($k=0; $k<50; $k++)
									{
										$temp_val=$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
										if(isset($temp_val))
										{			
											$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
										}
									}
									
									break;
								}
								
								case "type_paypal_total":				
								{
									$element=$input_get->getString('wdform_'.$key."_paypal_total".$id);
									$new_value=$new_value.$element;				
									break;
								}

								case "type_star_rating":
								{
									$element=$input_get->getString('wdform_'.$key."_star_amount".$id);
									$selected=$input_get->getString('wdform_'.$key."_selected_star_amount".$id,0);
									
									if(isset($element))
									{
										$new_value=$new_value.$selected.'/'.$element;					
									}
									break;
								}
								

								case "type_scale_rating":
								{
									$element=$input_get->getString('wdform_'.$key."_scale_amount".$id);
									$selected=$input_get->getString('wdform_'.$key."_scale_radio".$id,0);

									if(isset($element))
									{
										$new_value=$new_value.$selected.'/'.$element;					
									}
									break;
								}
								
								case "type_spinner":
								{

									$element=$input_get->getString('wdform_'.$key."_element".$id);
									if(isset($element))
									{
										$new_value=$new_value.$element;					
									}
									break;
								}
								
								case "type_slider":
								{

									$element=$input_get->getString('wdform_'.$key."_slider_value".$id);
									if(isset($element))
									{
										$new_value=$new_value.$element;					
									}
									break;
								}
								case "type_range":
								{

									$element0=$input_get->getString('wdform_'.$key."_element".$id.'0');
									$element1=$input_get->getString('wdform_'.$key."_element".$id.'1');
									if(isset($element0) || isset($element1))
									{
										$new_value=$new_value.$element0.'-'.$element1;					
									}
									break;
								}
								
								case "type_grading":
								{
									$element=$input_get->getString('wdform_'.$key."_hidden_item".$id);
									$grading = explode(":",$element);
									$items_count = sizeof($grading)-1;
									
									$element = "";
									$total = "";
									
									for($k=0;$k<$items_count;$k++)

									{
										$element .= $grading[$k].":".$input_get->getString('wdform_'.$key."_element".$id.'_'.$k)." ";
								$total += $input_get->getString('wdform_'.$key."_element".$id.'_'.$k);
							}

							$element .="Total:".$total;

							if(isset($element))
							{
								$new_value=$new_value.$element;					
							}
							break;
						}
						
							case "type_matrix":
								{
								
									$input_type=$input_get->getString('wdform_'.$key."_input_type".$id); 
										
									$mat_rows=explode("***",$input_get->getString('wdform_'.$key."_hidden_row".$id));
									$rows_count= sizeof($mat_rows)-1;
									$mat_columns=explode("***",$input_get->getString('wdform_'.$key."_hidden_column".$id));
									$columns_count= sizeof($mat_columns)-1;

												
									$matrix="<table>";
												
										$matrix .='<tr><td></td>';
								
							
							
										for( $k=1;$k< count($mat_columns) ;$k++)
											$matrix .='<td style="background-color:#BBBBBB; padding:5px; ">'.$mat_columns[$k].'</td>';
											$matrix .='</tr>';
										
										$aaa=Array();
										
										for($k=1; $k<=$rows_count; $k++)
										{
										$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px;">'.$mat_rows[$k].'</td>';
										
											if($input_type=="radio")
											{
										
										
												$mat_radio = $input_get->getString('wdform_'.$key."_input_element".$id.$k,0);											
												if($mat_radio==0)
												{
													$checked="";
													$aaa[1]="";
												}
												else
												$aaa=explode("_",$mat_radio);
												
												
												for($j=1; $j<=$columns_count; $j++)
												{
													if($aaa[1]==$j)
													$checked="checked";
													else
													$checked="";
													
													$matrix .='<td style="text-align:center"><input  type="radio" '.$checked.' disabled /></td>';
												
												}
										
											} 
											else
											{
												if($input_type=="checkbox")
												{                
													for($j=1; $j<=$columns_count; $j++)
													{
														$checked = $input_get->getString('wdform_'.$key."_input_element".$id.$k.'_'.$j);                     
														if($checked==1)						
														$checked = "checked";				
														else								
														$checked = "";

														$matrix .='<td style="text-align:center"><input  type="checkbox" '.$checked.' disabled /></td>';
												
													}
											
												}
												else
												{
													if($input_type=="text")
													{
																			  
														for($j=1; $j<=$columns_count; $j++)
														{
															$checked = $input_get->getString('wdform_'.$key."_input_element".$id.$k.'_'.$j);
															$matrix .='<td style="text-align:center"><input  type="text" value="'.$checked.'" disabled /></td>';
											
														}
													
													}
													else{
														for($j=1; $j<=$columns_count; $j++)
														{
															$checked = $input_get->getString('wdform_'.$key."_select_yes_no".$id.$k.'_'.$j);
															$matrix .='<td style="text-align:center">'.$checked.'</td>';
														
										
													
														}
													}
												
												}
												
											}
											$matrix .='</tr>';
										
										}
										 $matrix .='</table>';

						
																					
									if(isset($matrix))
									{
										$new_value=$new_value.$matrix;					
									}
								
									break;
								}
							
								
								default: break;
							}
							$new_script = str_replace("%".$label_each."%", $new_value, $new_script);	
						}
					}
				}	
				
				if(strpos($new_script, "%all%")>-1)
					$new_script = str_replace("%all%", $list, $new_script);	

				$body      = $new_script;
				$mode      = 1; 
				foreach($send_tos as $send_to)
				{
					$recipient=$input_get->getString('wdform_'.str_replace('*', '', $send_to)."_element".$id);
					if($recipient)
						$send=$this->sendMail($from, $fromname, $recipient, $subject, $body, $mode, $cca, $bcc, $attachment, $replyto, $replytoname); 
				}								
			}				
					
			if($row->sendemail)
			if($row->mail)
			{
				if($row->mail_from)
				{
					$from      = $input_get->getString('wdform_'.$row->mail_from."_element".$id);
					if(!isset($from))
						$from = $row->mail_from;
				}
				else
				{
					$from      = $config->get( 'config.mailfrom' );
				}
				
				if($row->mail_from_name)
					$fromname     = $row->mail_from_name;
				else
					$fromname      = $config->get( 'config.fromname' );
				
				if($row->reply_to)
				{
					$replyto      = $input_get->getString('wdform_'.$row->reply_to."_element".$id);
					if(!isset($replyto))
						$replyto = $row->reply_to;
				}
				$recipient = $row->mail;
				$subject   = $row->title;
				$new_script = $row->script_mail;
				foreach($label_order_original as $key => $label_each) 
				{	
					if(strpos($row->script_mail, "%".$label_each."%"))
					{				
						$type=$label_type[$key];
						if($type!="type_submit_reset" or $type!="type_map" or $type!="type_editor" or  $type!="type_captcha" or  $type!="type_recaptcha" or  $type!="type_button")
						{
							$new_value ="";
							switch ($type)
								{
									case 'type_text':
									case 'type_password':
									case 'type_textarea':
									case "type_date":
									case "type_own_select":					
									case "type_country":				
									case "type_number":	
									{
										$element=$input_get->getString('wdform_'.$key."_element".$id);
										if(isset($element))
										{
											$new_value = $element;					
										}
										break;
									
									
									}
									
									case "type_hidden":				
									{
										$element=$input_get->getString($element_label);
										if(isset($element))
										{
											$new_value = $element;	
										}
										break;
									}
									
									
									case "type_mark_map":
									{
										$element=$input_get->getString('wdform_'.$key."_long".$id);
										if(isset($element))
										{
											$new_value = 'Longitude:'.$input_get->getString('wdform_'.$key."_long".$id).'<br/>Latitude:'.$input_get->getString('wdform_'.$key."_lat".$id);
										}
										break;		
									}
														
									case "type_submitter_mail":
									{
										$element=$input_get->getString('wdform_'.$key."_element".$id);
										if(isset($element))
										{
											$new_value = $element;					
										}
										break;		
									}
									
									case "type_time":
									{
										
										$hh=$input_get->getString('wdform_'.$key."_hh".$id);
										if(isset($hh))
										{
											$ss=$input_get->getString('wdform_'.$key."_ss".$id);
											if(isset($ss))
												$new_value = $input_get->getString('wdform_'.$key."_hh".$id).':'.$input_get->getString('wdform_'.$key."_mm".$id).':'.$input_get->getString('wdform_'.$key."_ss".$id);
											else
												$new_value = $input_get->getString('wdform_'.$key."_hh".$id).':'.$input_get->getString('wdform_'.$key."_mm".$id);
											$am_pm=$input_get->getString('wdform_'.$key."_am_pm".$id);
											if(isset($am_pm))
												$new_value=$new_value.' '.$input_get->getString('wdform_'.$key."_am_pm".$id);
											
										}
											
										break;
									}
									
									case "type_phone":
									{
										$element_first=$input_get->getString('wdform_'.$key."_element_first".$id);
										if(isset($element_first))
										{
												$new_value = $input_get->getString('wdform_'.$key."_element_first".$id).' '.$input_get->getString('wdform_'.$key."_element_last".$id);
										}	
										break;
									}
									
									case "type_name":
									{
										$element_first=$input_get->getString('wdform_'.$key."_element_first".$id);
										if(isset($element_first))
										{
											$element_title=$input_get->getString('wdform_'.$key."_element_title".$id);
											if(isset($element_title))
												$new_value = $input_get->getString('wdform_'.$key."_element_title".$id).' '.$input_get->getString('wdform_'.$key."_element_first".$id).' '.$input_get->getString('wdform_'.$key."_element_last".$id).' '.$input_get->getString('wdform_'.$key."_element_middle".$id);
											else
												$new_value = $input_get->getString('wdform_'.$key."_element_first".$id).' '.$input_get->getString('wdform_'.$key."_element_last".$id);
										}	   
										break;		
									}
									
									case "type_address":
									{

										$street1=$input_get->getString('wdform_'.$key."_street1".$id);

										if(isset($street1))

										{

											$new_value=$input_get->getString('wdform_'.$key."_street1".$id);

											break;

										}
										
										$street2=$input_get->getString('wdform_'.$key."_street2".$id);

										if(isset($street2))

										{

											$new_value=$input_get->getString('wdform_'.$key."_street2".$id);

											break;

										}	
										
										$city=$input_get->getString('wdform_'.$key."_city".$id);
										
										if(isset($city))

										{

											$new_value=$input_get->getString('wdform_'.$key."_city".$id);

											break;

										}
										
										$state=$input_get->getString('wdform_'.$key."_state".$id);
										
										if(isset($state))

										{

											$new_value=$input_get->getString('wdform_'.$key."_state".$id);

											break;

										}
											
											$postal=$input_get->getString('wdform_'.$key."_postal".$id);
										
										if(isset($postal))

										{

											$new_value=$input_get->getString('wdform_'.$key."_postal".$id);

											break;

										}	

										
										$country = $input_get->getString('wdform_'.$key."_country".$id);
										
											if(isset($country))
											{

											$new_value=$input_get->getString('wdform_'.$key."_country".$id);

											break;		
											}
										

										break;

									}

									case "type_date_fields":
									{
										$day=$input_get->getString('wdform_'.$key."_day".$id);
										if(isset($day))
										{
											$new_value = $input_get->getString('wdform_'.$key."_day".$id).'-'.$input_get->getString('wdform_'.$key."_month".$id).'-'.$input_get->getString('wdform_'.$key."_year".$id);
										}
										break;
									}
									
									case "type_radio":
									{
										$element=$input_get->getString('wdform_'.$key."_other_input".$id);
										if(isset($element))
										{
											$new_value = $input_get->getString('wdform_'.$key."_other_input".$id);
											break;
										}	
										
										$element=$input_get->getString('wdform_'.$key."_element".$id);
										if(isset($element))
										{
											$new_value = $element;					
										}
										break;	
									}
									
									case "type_checkbox":
									{
																
										$start=-1;
										for($j=0; $j<100; $j++)
										{
											$element=$input_get->getString('wdform_'.$key."_element".$id.$j);
											if(isset($element))
											{
												$start=$j;
												break;
											}
										}	
										
										$other_element_id=-1;
										$is_other=$input_get->getString('wdform_'.$key."_allow_other".$id);
										if($is_other=="yes")
										{
											$other_element_id=$input_get->getString('wdform_'.$key."_allow_other_num".$id);
										}
										
								
										if($start!=-1)
										{
											for($j=$start; $j<100; $j++)
											{
												
												$element=$input_get->getString('wdform_'.$key."_element".$id.$j);
												if(isset($element))
												if($j==$other_element_id)
												{
													$new_value = $new_value.$input_get->getString('wdform_'.$key."_other_input".$id).'<br>';
												}
												else
												
													$new_value = $new_value.$input_get->getString('wdform_'.$key."_element".$id.$j).'<br>';
											}
											
										}
										break;
										}
										case "type_paypal_price":	
									{		
										$new_value=0;
										if($input_get->getString('wdform_'.$key."_element_dollars".$id))
											$new_value=$input_get->getString('wdform_'.$key."_element_dollars".$id);
										
										if($input_get->getString('wdform_'.$key."_element_cents".$id))
											$new_value=$new_value.'.'.$input_get->getString('wdform_'.$key."_element_cents".$id);
									
										$new_value=$new_value.$form_currency;


											
											
										break;

									}			

									case "type_paypal_select":	
									{	
										$new_value=$input_get->getString('wdform_'.$key."_element_label".$id).':'.$input_get->getString('wdform_'.$key."_element".$id).$form_currency;
										$element_quantity_label=$input_get->getString('wdform_'.$key."_element_quantity_label".$id);
										if(isset($element_quantity_label))
											$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$key."_element_quantity".$id);
								
										for($k=0; $k<50; $k++)
										{
											$temp_val=$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
											if(isset($temp_val))
											{			
												$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_property_label".$id.$k).': '.$input_get->getString($i."_element_property_value".$id.$k);
											}
										}

										break;
										
									}
							
									case "type_paypal_radio":				
									{
												
										$new_value=$input_get->getString('wdform_'.$key."_element_label".$id).' - '.$input_get->getString('wdform_'.$key."_element".$id).$form_currency;
										
										$element_quantity_label=$input_get->getString('wdform_'.$key."_element_quantity_label".$id);
											if(isset($element_quantity_label))
												$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$key."_element_quantity".$id);
								
										for($k=0; $k<50; $k++)
										{
											$temp_val=$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
											if(isset($temp_val))
											{			
												$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
											}
										}
										
										break;	
									}

									case "type_paypal_shipping":				
									{
										
										$new_value=$input_get->getString('wdform_'.$key."_element_label".$id).' : '.$input_get->getString('wdform_'.$key."_element".$id).$form_currency;		

										break;
									}

									case "type_paypal_checkbox":				
									{										
										$start=-1;
										for($j=0; $j<100; $j++)
										{
											$element=$input_get->getString('wdform_'.$key."_element".$id.$j);
											if(isset($element))
											{
												$start=$j;
												break;
											}
										}	
										
										if($start!=-1)
										{
											for($j=$start; $j<100; $j++)
											{
												
												$element=$input_get->getString('wdform_'.$key."_element".$id.$j);
												if(isset($element))
												{
													$new_value=$new_value.$input_get->getString('wdform_'.$key."_element".$id.$j."_label").' - '.($input_get->getString('wdform_'.$key."_element".$id.$j)=='' ? '0'.$form_currency : $input_get->getString('wdform_'.$key."_element".$id.$j)).$form_currency.'<br>';
												}
											}
										}
										
										$element_quantity_label=$input_get->getString('wdform_'.$key."_element_quantity_label".$id);
											if(isset($element_quantity_label))
												$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_quantity_label".$id).': '.$input_get->getString('wdform_'.$key."_element_quantity".$id);
								
										for($k=0; $k<50; $k++)
										{
											$temp_val=$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
											if(isset($temp_val))
											{			
												$new_value.='<br/>'.$input_get->getString('wdform_'.$key."_element_property_label".$id.$k).': '.$input_get->getString('wdform_'.$key."_element_property_value".$id.$k);
											}
										}
																	
								
										break;
									}			
											
									case "type_paypal_total":				

									{
									
									
										$element=$input_get->getString('wdform_'.$key."_paypal_total".$id);

										
										$new_value=$new_value.$element;				



										break;

									}
									
									case "type_star_rating":
								{
									$element=$input_get->getString('wdform_'.$key."_star_amount".$id);
									$selected=$input_get->getString('wdform_'.$key."_selected_star_amount".$id,0);
									//$star_color=$input_get->getString($key."_star_color_id_temp");
									
									if(isset($element))
									{
										$new_value=$new_value.$selected.'/'.$element;					
									}
									break;
								}
								

								case "type_scale_rating":
								{
								$element=$input_get->getString('wdform_'.$key."_scale_amount".$id);
								$selected=$input_get->getString('wdform_'.$key."_scale_radio".$id,0);
								
									
									if(isset($element))
									{
										$new_value=$new_value.$selected.'/'.$element;					
									}
									break;
								}
								
								case "type_spinner":
								{

									$element=$input_get->getString('wdform_'.$key."_element".$id);
									if(isset($element))
									{
										$new_value=$new_value.$element;					
									}
									break;
								}
								
								case "type_slider":
								{

									$element=$input_get->getString('wdform_'.$key."_slider_value".$id);
									if(isset($element))
									{
										$new_value=$new_value.$element;					
									}
									break;
								}
								case "type_range":
								{

									$element0=$input_get->getString('wdform_'.$key."_element".$id.'0');
									$element1=$input_get->getString('wdform_'.$key."_element".$id.'1');
									if(isset($element0) || isset($element1))
									{
										$new_value=$new_value.$element0.'-'.$element1;					
									}
									break;
								}
								
								case "type_grading":
								{
									$element=$input_get->getString('wdform_'.$key."_hidden_item".$id);
									$grading = explode(":",$element);
									$items_count = sizeof($grading)-1;
									
									$element = "";
									$total = "";
									
									for($k=0;$k<$items_count;$k++)

									{
										$element .= $grading[$k].":".$input_get->getString('wdform_'.$key."_element".$id.'_'.$k)." ";
								$total += $input_get->getString('wdform_'.$key."_element".$id.'_'.$k);
							}

							$element .="Total:".$total;

																				
							if(isset($element))
							{
								$new_value=$new_value.$element;					
							}
							break;
						}
						
							case "type_matrix":
								{
								
									$input_type=$input_get->getString('wdform_'.$key."_input_type".$id); 
										
									$mat_rows=explode("***",$input_get->getString('wdform_'.$key."_hidden_row".$id));
									$rows_count= sizeof($mat_rows)-1;
									$mat_columns=explode("***",$input_get->getString('wdform_'.$key."_hidden_column".$id));
									$columns_count= sizeof($mat_columns)-1;

												
									$matrix="<table>";
												
										$matrix .='<tr><td></td>';
								
							
							
										for( $k=1;$k< count($mat_columns) ;$k++)
											$matrix .='<td style="background-color:#BBBBBB; padding:5px; ">'.$mat_columns[$k].'</td>';
											$matrix .='</tr>';
										
										$aaa=Array();
										
										for($k=1; $k<=$rows_count; $k++)
										{
										$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px;">'.$mat_rows[$k].'</td>';
										
											if($input_type=="radio")
											{
										
										
												$mat_radio = $input_get->getString('wdform_'.$key."_input_element".$id.$k,0);											
												if($mat_radio==0)
												{
													$checked="";
													$aaa[1]="";
												}
												else
												$aaa=explode("_",$mat_radio);
												
												
												for($j=1; $j<=$columns_count; $j++)
												{
													if($aaa[1]==$j)
													$checked="checked";
													else
													$checked="";
													
													$matrix .='<td style="text-align:center"><input  type="radio" '.$checked.' disabled /></td>';
												
												}
										
											} 
											else
											{
												if($input_type=="checkbox")
												{                
													for($j=1; $j<=$columns_count; $j++)
													{
														$checked = $input_get->getString('wdform_'.$key."_input_element".$id.$k.'_'.$j);                     
														if($checked==1)						
														$checked = "checked";				
														else								
														$checked = "";

														$matrix .='<td style="text-align:center"><input  type="checkbox" '.$checked.' disabled /></td>';
												
													}
											
												}
												else
												{
													if($input_type=="text")
													{
																			  
														for($j=1; $j<=$columns_count; $j++)
														{
															$checked = $input_get->getString('wdform_'.$key."_input_element".$id.$k.'_'.$j);
															$matrix .='<td style="text-align:center"><input  type="text" value="'.$checked.'" disabled /></td>';
											
														}
													
													}
													else{
														for($j=1; $j<=$columns_count; $j++)
														{
															$checked = $input_get->getString('wdform_'.$key."_select_yes_no".$id.$k.'_'.$j);
															$matrix .='<td style="text-align:center">'.$checked.'</td>';
														
										
													
														}
													}
												
												}
												
											}
											$matrix .='</tr>';
										
										}
										 $matrix .='</table>';

						
																					
									if(isset($matrix))
									{
										$new_value=$new_value.$matrix;					
									}
								
									break;
								}
								
									default: break;
								}
								
							$new_script = str_replace("%".$label_each."%", $new_value, $new_script);	
							}

						
					
					}
				}	
				
				if(strpos($new_script, "%all%")>-1)
					$new_script = str_replace("%all%", $list, $new_script);	

				$body      = $new_script;
				$mode      = 1; 

				if($row->sendemail)
				{
				$send=$this->sendMail($from, $fromname, $recipient, $subject, $body, $mode, $cca, $bcc, $attachment, $replyto, $replytoname);  
				}
			}
		
			$msg =JFactory::getApplication()->enqueueMessage(JText::_('WDF_SUBMITTED'),'Success');
			//$msg=JText::_('WDF_SUBMITTED');
			$succes = 1;

			if($row->sendemail)
			if($row->mail || $row->send_to)
			{
				if ( $send !== true ) 
				{
				$msg =JFactory::getApplication()->enqueueMessage(JText::_('WDF_MAIL_SEND_ERROR'),'error');
				//	$msg=JText::_('WDF_MAIL_SEND_ERROR');
					$succes = 0;
				}
				else 
				$msg =JFactory::getApplication()->enqueueMessage(JText::_('WDF_MAIL_SENT'),'Success');
			//		$msg=JText::_('WDF_MAIL_SENT');
			}
			
		}
		
		else
		{
		
	
		foreach($label_order_ids as $key => $label_order_id)
			{

				$i=$label_order_id;

				$type=$input_get->getString($i."_type".$id);

				if(isset($type))

				if($type!="type_map" and  $type!="type_submit_reset" and  $type!="type_editor" and  $type!="type_captcha" and  $type!="type_recaptcha" and  $type!="type_button")

				{	

					$element_label=$label_order_original[$i];

							

					switch ($type)

					{

						case 'type_text':

						case 'type_password':

						case 'type_textarea':

						case "type_date":

						case "type_own_select":					

						case "type_country":				

						case "type_number":	

						{

							$element=$input_get->getString($i."_element".$id);

							if(isset($element))

							{

								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					

							}

							break;

						

						

						}

						

						case "type_hidden":				

						{

							$element=$input_get->getString($element_label);

							if(isset($element))

							{

								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					

							}

							break;

						}

						

						

						case "type_mark_map":

						{

							$element=$input_get->getString($i."_long".$id);

							if(isset($element))

							{

								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >Longitude:'.$input_get->getString($i."_long".$id).'<br/>Latitude:'.$input_get->getString($i."_lat".$id).'</td></tr>';

							}

							break;		

						}

											

						case "type_submitter_mail":

						{

							$element=$input_get->getString($i."_element".$id);

							if(isset($element))

							{

								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					

								if($input_get->getString($i."_send".$id)=="yes")

									array_push($cc, $element);

							}

							break;		

						}

						

						case "type_time":

						{

							

							$hh=$input_get->getString($i."_hh".$id);

							if(isset($hh))

							{

								$ss=$input_get->getString($i."_ss".$id);

								if(isset($ss))

									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString($i."_hh".$id).':'.$input_get->getString($i."_mm".$id).':'.$input_get->getString($i."_ss".$id);

								else

									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString($i."_hh".$id).':'.$input_get->getString($i."_mm".$id);

								$am_pm=$input_get->getString($i."_am_pm".$id);

								if(isset($am_pm))

									$list=$list.' '.$input_get->getString($i."_am_pm".$id).'</td></tr>';

								else

									$list=$list.'</td></tr>';

							}

								

							break;

						}

						

						case "type_phone":

						{

							$element_first=$input_get->getString($i."_element_first".$id);

							if(isset($element_first))

							{

									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString($i."_element_first".$id).' '.$input_get->getString($i."_element_last".$id).'</td></tr>';

							}	

							break;

						}

						

						case "type_name":

						{

							$element_first=$input_get->getString($i."_element_first".$id);

							if(isset($element_first))

							{

								$element_title=$input_get->getString($i."_element_title".$id);

								if(isset($element_title))

									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString($i."_element_title".$id).' '.$input_get->getString($i."_element_first".$id).' '.$input_get->getString($i."_element_last".$id).' '.$input_get->getString($i."_element_middle".$id).'</td></tr>';

								else

									$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString($i."_element_first".$id).' '.$input_get->getString($i."_element_last".$id).'</td></tr>';

							}	   

							break;		

						}

						

						case "type_address":

						{

							$element=$input_get->getString($i."_street1".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString($i."_street1".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString($i."_street2".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString($i."_street2".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString($i."_city".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString($i."_city".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString($i."_state".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString($i."_state".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString($i."_postal".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString($i."_postal".$id).'</td></tr>';
							break;
						}
						
						$element=$input_get->getString($i."_country".$id);
						if(isset($element))
						{
							$list=$list.'<tr valign="top"><td >'.$label_order_original[$i].'</td><td >'.$input_get->getString($i."_country".$id).'</td></tr>';
							break;
						}
						
						break;


						}

						

						case "type_date_fields":

						{

							$day=$input_get->getString($i."_day".$id);

							if(isset($day))

							{

								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString($i."_day".$id).'-'.$input_get->getString($i."_month".$id).'-'.$input_get->getString($i."_year".$id).'</td></tr>';

							}

							break;

						}

						

						case "type_radio":

						{

							$element=$input_get->getString($i."_other_input".$id);

							if(isset($element))

							{

								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$input_get->getString($i."_other_input".$id).'</td></tr>';

								break;

							}	

							

							$element=$input_get->getString($i."_element".$id);

							if(isset($element))

							{

								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					

							}

							break;	

						}

						

						case "type_checkbox":

						{

							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >';

						

							$start=-1;

							for($j=0; $j<100; $j++)

							{

								$element=$input_get->getString($i."_element".$id.$j);

								if(isset($element))

								{

									$start=$j;

									break;

								}

							}	

							

							$other_element_id=-1;

							$is_other=$input_get->getString($i."_allow_other".$id);

							if($is_other=="yes")

							{

								$other_element_id=$input_get->getString($i."_allow_other_num".$id);

							}

							

					

							if($start!=-1)

							{

								for($j=$start; $j<100; $j++)

								{

									

									$element=$input_get->getString($i."_element".$id.$j);

									if(isset($element))

									if($j==$other_element_id)

									{

										$list=$list.$input_get->getString($i."_other_input".$id).'<br>';

									}

									else

									

										$list=$list.$input_get->getString($i."_element".$id.$j).'<br>';

								}

								$list=$list.'</td></tr>';

							}

										

							

							break;

						}

						case "type_paypal_price":	

						{		

							$value=0;

							if($input_get->getString($i."_element_dollars".$id))

								$value=$input_get->getString($i."_element_dollars".$id);

							

							if($input_get->getString($i."_element_cents".$id))

								$value=$value.'.'.$input_get->getString($i."_element_cents".$id);

						

								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >'.$value.$form_currency.'</td></tr>';

							break;



						}			

				

						case "type_paypal_select":	

						{	
							if($input_get->getString($i."_element_label".$id))
								$value=$input_get->getString($i."_element_label".$id).' : '.$input_get->getString($i."_element".$id).$form_currency;
							else
								$value='';
							$element_quantity_label=$input_get->getString($i."_element_quantity_label".$id);

								if(isset($element_quantity_label))

									$value.='<br/>'.$input_get->getString($i."_element_quantity_label".$id).': '.$input_get->getString($i."_element_quantity".$id);

					

							for($k=0; $k<50; $k++)

							{

								$temp_val=$input_get->getString($i."_element_property_value".$id.$k);

								if(isset($temp_val))

								{			

									$value.='<br/>'.$input_get->getString($i."_element_property_label".$id.$k).': '.$input_get->getString($i."_element_property_value".$id.$k);

								}

							}

							

							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$value.'</pre></td></tr>';					

							break;

						}

				

						case "type_paypal_radio":				

						{

							if($input_get->getString($i."_element_label".$id))
								$value=$input_get->getString($i."_element_label".$id).' : '.$input_get->getString($i."_element".$id).$form_currency;
							else
								$value='';
							$element_quantity_label=$input_get->getString($i."_element_quantity_label".$id);

								if(isset($element_quantity_label))

									$value.='<br/>'.$input_get->getString($i."_element_quantity_label".$id).': '.$input_get->getString($i."_element_quantity".$id);

					

							for($k=0; $k<50; $k++)

							{

								$temp_val=$input_get->getString($i."_element_property_value".$id.$k);

								if(isset($temp_val))

								{			

									$value.='<br/>'.$input_get->getString($i."_element_property_label".$id.$k).': '.$input_get->getString($i."_element_property_value".$id.$k);

								}

							}

							

							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$value.'</pre></td></tr>';				

							

							break;	

						}



						case "type_paypal_shipping":				

						{

							if($input_get->getString($i."_element_label".$id))
								$value=$input_get->getString($i."_element_label".$id).' : '.$input_get->getString($i."_element".$id).$form_currency;
							else
								$value='';

							

							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$value.'</pre></td></tr>';				



							break;

						}



						case "type_paypal_checkbox":				

						{
							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td >';

						

							$start=-1;

							for($j=0; $j<100; $j++)

							{

								$element=$input_get->getString($i."_element".$id.$j);

								if(isset($element))

								{

									$start=$j;

									break;

								}

							}	

							

							if($start!=-1)

							{

								for($j=$start; $j<100; $j++)

								{

									

									$element=$input_get->getString($i."_element".$id.$j);

									if(isset($element))

									{

										$list=$list.$input_get->getString($i."_element".$id.$j."_label").' - '.($input_get->getString($i."_element".$id.$j)=='' ? '0'.$form_currency : $input_get->getString($i."_element".$id.$j)).$form_currency.'<br>';

									}

								}

								

								

							}

							$element_quantity_label=$input_get->getString($i."_element_quantity_label".$id);

								if(isset($element_quantity_label))

									$list=$list.'<br/>'.$input_get->getString($i."_element_quantity_label".$id).': '.$input_get->getString($i."_element_quantity".$id);

					

							for($k=0; $k<50; $k++)

							{

								$temp_val=$input_get->getString($i."_element_property_value".$id.$k);

								if(isset($temp_val))

								{			

									$list=$list.'<br/>'.$input_get->getString($i."_element_property_label".$id.$k).': '.$input_get->getString($i."_element_property_value".$id.$k);

								}

							}

														

							$list=$list.'</td></tr>';

							break;

						}

						
						case "type_paypal_total":				

						{
						
						
							$element=$input_get->getString($i."_paypal_total".$id);

							
							$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';				



							break;

						}


						case "type_star_rating":
						{
							$element=$input_get->getString($i."_star_amount".$id);
							$selected=$input_get->getString($i."_selected_star_amount".$id,0);
							//$star_color=$input_get->getString($i."_star_color_id_temp");
							
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$selected.'/'.$element.'</pre></td></tr>';					
							}
							break;
						}
						

						case "type_scale_rating":
						{
						$element=$input_get->getString($i."_scale_amount".$id);
						$selected=$input_get->getString($i."_scale_radio".$id,0);
						
							
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$selected.'/'.$element.'</pre></td></tr>';					
							}
							break;
						}
						
						case "type_spinner":
						{

							$element=$input_get->getString($i."_element".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;
						}
						
						case "type_slider":
						{

							$element=$input_get->getString($i."_slider_value".$id);
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;
						}
						case "type_range":
						{

							$element0=$input_get->getString($i."_element".$id.'0');
						    $element1=$input_get->getString($i."_element".$id.'1');
							if(isset($element0) || isset($element1))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">From:'.$element0.'<span style="margin-left:6px">To</span>:'.$element1.'</pre></td></tr>';					
							}
							break;
						}
						
						case "type_grading":
						{
							$element=$input_get->getString($i."_hidden_item".$id);
							$grading = explode(":",$element);
							$items_count = sizeof($grading)-1;
							
							$element = "";
							$total = "";
							
							for($k=0;$k<$items_count;$k++)

							{
								$element .= $grading[$k].":".$input_get->getString($i."_element".$id.$k)." ";
								$total += $input_get->getString($i."_element".$id.$k);
							}

							$element .="Total:".$total;

																				
							if(isset($element))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$element.'</pre></td></tr>';					
							}
							break;
						}
						
							case "type_matrix":
						{
						
							
							$input_type=$input_get->getString($i."_input_type".$id); 
													
							$mat_rows = $input_get->getString($i."_hidden_row".$id);
							$mat_rows = explode('***', $mat_rows);
							$mat_rows = array_slice($mat_rows,0, count($mat_rows)-1);
							
							$mat_columns = $input_get->getString($i."_hidden_column".$id);
							$mat_columns = explode('***', $mat_columns);
							$mat_columns = array_slice($mat_columns,0, count($mat_columns)-1);
					  
							$row_ids=explode(",",substr($input_get->getString($i."_row_ids".$id), 0, -1));
							$column_ids=explode(",",substr($input_get->getString($i."_column_ids".$id), 0, -1)); 
										
							$matrix="<table>";
										
								$matrix .='<tr><td></td>';
							
							for( $k=0;$k< count($mat_columns) ;$k++)
								$matrix .='<td style="background-color:#BBBBBB; padding:5px; ">'.$mat_columns[$k].'</td>';
								$matrix .='</tr>';
							
							$aaa=Array();
							   $k=0;
							foreach($row_ids as $row_id)
							{
							$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px;">'.$mat_rows[$k].'</td>';
							
								if($input_type=="radio")
								{
							
							
									$mat_radio = $input_get->getString($i."_input_element".$id.$row_id,0);											
									if($mat_radio==0)
									{
										$checked="";
										$aaa[1]="";
									}
								    else
									$aaa=explode("_",$mat_radio);
									
							        foreach($column_ids as $column_id)
									{
										if($aaa[1]==$column_id)
									    $checked="checked";
									    else
									    $checked="";
										
										$matrix .='<td style="text-align:center"><input  type="radio" '.$checked.' disabled /></td>';
                                    
							        }
							
							    } 
								else
								{
									if($input_type=="checkbox")
									{                
										foreach($column_ids as $column_id)
										{
											$checked = $input_get->getString($i."_input_element".$id.$row_id.'_'.$column_id);                     
											if($checked==1)						
											$checked = "checked";				
											else								
											$checked = "";

											$matrix .='<td style="text-align:center"><input  type="checkbox" '.$checked.' disabled /></td>';
									
										}
								
									}
									else
									{
										if($input_type=="text")
										{
																  
											foreach($column_ids as $column_id)
											{
												$checked = $input_get->getString($i."_input_element".$id.$row_id.'_'.$column_id);
												$matrix .='<td style="text-align:center"><input  type="text" value="'.$checked.'" disabled /></td>';
								
											}
										
										}
										else{
											foreach($column_ids as $column_id)
											{
												$checked = $input_get->getString($i."_select_yes_no".$id.$row_id.'_'.$column_id);
												$matrix .='<td style="text-align:center">'.$checked.'</td>';
											
							
										
											}
										}
									
									}
									
								}
							    $matrix .='</tr>';
								$k++;
							}
							 $matrix .='</table>';

		
		
		
																			
							if(isset($matrix))
							{
								$list=$list.'<tr valign="top"><td >'.$element_label.'</td><td ><pre style="font-family:inherit; margin:0px; padding:0px">'.$matrix.'</pre></td></tr>';					
							}
						
							break;
						}
					
						

						default: break;

					}

				

				}	

					

				

				

			}

			

			$list=$list.'</table>';

			$list = wordwrap($list, 70, "\n", true);



							$config = JFactory::getConfig();

							if($row->mail_from)
							$site_mailfrom = $row->mail_from;
							else
							$site_mailfrom=$config->get( 'config.mailfrom' );								

							if($row->mail_from_name)
							$site_fromname = $row->mail_from_name;
							else
							$site_fromname=$config->get( 'config.fromname' );					

							for($k=0;$k<count($all_files);$k++)

								$attachment[]=array($all_files[$k]['tmp_name'], $all_files[$k]['name'], $all_files[$k]['name'] );

							

							if(isset($cc[0]))

							{

								foreach	($cc as $c)	

								{	

									if($c)

									{

										$from      = $site_mailfrom;

										$fromname  = $site_fromname; 

										$recipient = $c;

										$subject   = $row->title;

										 

//////////////////////////////////////////////////////////////////////////////////////////////////////////										 

//////////////////////////////////////////////////////////////////////////////////////////////////////////										 

										$new_script = $row->script_mail_user;

										foreach($label_order_original as $key => $label_each) 

										{	

											if(strpos($row->script_mail_user, "%".$label_each."%")!=-1)

											{				

												$type = $label_type[$key];

												if($type!="type_submit_reset" or $type!="type_map" or $type!="type_editor" or  $type!="type_captcha" or  $type!="type_recaptcha" or  $type!="type_button")

												{


													$new_value ="";

													switch ($type)

														{

															case 'type_text':

															case 'type_password':

															case 'type_textarea':

															case "type_date":

															case "type_own_select":					

															case "type_country":				

															case "type_number":	

															{

																$element=$input_get->getString($key."_element".$id);

																if(isset($element))

																{

																	$new_value = $element;					

																}

																break;

															

															

															}

															

															case "type_hidden":				

															{

																$element=$input_get->getString($element_label);

																if(isset($element))

																{

																	$new_value = $element;	

																}

																break;

															}

															

															

															case "type_mark_map":

															{

																$element=$input_get->getString($key."_long".$id);

																if(isset($element))

																{

																	$new_value = 'Longitude:'.$input_get->getString($key."_long".$id).'<br/>Latitude:'.$input_get->getString($key."_lat".$id);

																}

																break;		

															}

																				

															case "type_submitter_mail":

															{

																$element=$input_get->getString($key."_element".$id);

																if(isset($element))

																{

																	$new_value = $element;					

																}

																break;		

															}

															

															case "type_time":

															{

																

																$hh=$input_get->getString($key."_hh".$id);

																if(isset($hh))

																{

																	$ss=$input_get->getString($key."_ss".$id);

																	if(isset($ss))

																		$new_value = $input_get->getString($key."_hh".$id).':'.$input_get->getString($key."_mm".$id).':'.$input_get->getString($key."_ss".$id);

																	else

																		$new_value = $input_get->getString($key."_hh".$id).':'.$input_get->getString($key."_mm".$id);

																	$am_pm=$input_get->getString($key."_am_pm".$id);

																	if(isset($am_pm))

																		$new_value=$new_value.' '.$input_get->getString($key."_am_pm".$id);

																	

																}

																	

																break;

															}

															

															case "type_phone":

															{

																$element_first=$input_get->getString($key."_element_first".$id);

																if(isset($element_first))

																{

																		$new_value = $input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id);

																}	

																break;

															}

															

															case "type_name":

															{

																$element_first=$input_get->getString($key."_element_first".$id);

																if(isset($element_first))

																{

																	$element_title=$input_get->getString($key."_element_title".$id);

																	if(isset($element_title))

																		$new_value = $input_get->getString($key."_element_title".$id).' '.$input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id).' '.$input_get->getString($key."_element_middle".$id);

																	else

																		$new_value = $input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id);

																}	   

																break;		

															}

															

															case "type_address":

																{

																	$street1=$input_get->getString($key."_street1".$id);

																	if(isset($street1))

																	{

																		$new_value=$input_get->getString($key."_street1".$id);

																		break;

																	}
																	
																	$street2=$input_get->getString($key."_street2".$id);

																	if(isset($street2))

																	{

																		$new_value=$input_get->getString($key."_street2".$id);

																		break;

																	}	
																	
																	$city=$input_get->getString($key."_city".$id);
																	
																	if(isset($city))

																	{

																		$new_value=$input_get->getString($key."_city".$id);

																	break;

																	}
																	
																	$state=$input_get->getString($key."_state".$id);
																	
																	if(isset($state))

																	{

																		$new_value=$input_get->getString($key."_state".$id);

																		break;

																	}
																		
																		$postal=$input_get->getString($key."_postal".$id);
																	
																	if(isset($postal))

																	{

																		$new_value=$input_get->getString($key."_postal".$id);

																		break;
																	}	

																	
																	$country = $input_get->getString($key."_country".$id);
																	
																		if(isset($country))
																		{

																		$new_value=$input_get->getString($key."_country".$id);

																		break;
																		}
																	

																	break;

																}

																

															case "type_date_fields":

															{

																$day=$input_get->getString($key."_day".$id);

																if(isset($day))

																{

																	$new_value = $input_get->getString($key."_day".$id).'-'.$input_get->getString($key."_month".$id).'-'.$input_get->getString($key."_year".$id);

																}

																break;

															}

															

															case "type_radio":

															{

																$element=$input_get->getString($key."_other_input".$id);

																if(isset($element))

																{

																	$new_value = $input_get->getString($key."_other_input".$id);

																	break;

																}	

																

																$element=$input_get->getString($key."_element".$id);

																if(isset($element))

																{

																	$new_value = $element;					

																}

																break;	

															}

															

															case "type_checkbox":

															{

																						

																$start=-1;

																for($j=0; $j<100; $j++)

																{

																	$element=$input_get->getString($key."_element".$id.$j);

																	if(isset($element))

																	{

																		$start=$j;

																		break;

																	}

																}	

																

																$other_element_id=-1;

																$is_other=$input_get->getString($key."_allow_other".$id);

																if($is_other=="yes")

																{

																	$other_element_id=$input_get->getString($key."_allow_other_num".$id);

																}

																

														

																if($start!=-1)

																{

																	for($j=$start; $j<100; $j++)

																	{

																		

																		$element=$input_get->getString($key."_element".$id.$j);

																		if(isset($element))

																		if($j==$other_element_id)

																		{

																			$new_value = $new_value.$input_get->getString($key."_other_input".$id).'<br>';

																		}

																		else

																		

																			$new_value = $new_value.$input_get->getString($key."_element".$id.$j).'<br>';

																	}

																	

																}

																			

																

																break;

															}

															

															

															case "type_paypal_price":	

															{		

																$new_value=0;

																if($input_get->getString($key."_element_dollars".$id))

																	$new_value=$input_get->getString($key."_element_dollars".$id);

																

																if($input_get->getString($key."_element_cents".$id))

																	$new_value=$new_value.'.'.$input_get->getString($key."_element_cents".$id);

															

																$new_value=$new_value.$form_currency;





																	

										

																break;

															}			

															

															case "type_paypal_select":	

															{	

													

																$new_value=$input_get->getString($key."_element_label".$id).':'.$input_get->getString($key."_element".$id).$form_currency;

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																if(isset($element_quantity_label))

																	$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($i."_element_property_value".$id.$k);

																	}

																}



									

																break;

															}

															

															case "type_paypal_radio":				

															{

																

															$new_value=$input_get->getString($key."_element_label".$id).' - '.$input_get->getString($key."_element".$id).$form_currency;

																

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																	if(isset($element_quantity_label))

																		$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($key."_element_property_value".$id.$k);

																	}

																}

														

																break;

															}



															case "type_paypal_shipping":				

															{

																

																$new_value=$input_get->getString($key."_element_label".$id).' : '.$input_get->getString($key."_element".$id).$form_currency;		



																break;

															}



															case "type_paypal_checkbox":				

															{

																$start=-1;

																for($j=0; $j<100; $j++)

																{

																	$element=$input_get->getString($key."_element".$id.$j);

																	if(isset($element))

																	{

																		$start=$j;

																		break;

																	}

																}	

																

																if($start!=-1)

																{

																	for($j=$start; $j<100; $j++)

																	{

																		

																		$element=$input_get->getString($key."_element".$id.$j);

																		if(isset($element))

																		{

																			$new_value=$new_value.$input_get->getString($key."_element".$id.$j."_label").' - '.($input_get->getString($key."_element".$id.$j)=='' ? '0'.$form_currency : $input_get->getString($key."_element".$id.$j)).$form_currency.'<br>';

																		}

																	}

																}

																

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																	if(isset($element_quantity_label))

																		$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($key."_element_property_value".$id.$k);

																	}

																}

																

																break;

															}

															case "type_paypal_total":				

															{
															
															
																$element=$input_get->getString($key."_paypal_total".$id);

																
																$new_value=$new_value.$element;				



																break;

															}

															case "type_star_rating":
															{
																$element=$input_get->getString($key."_star_amount".$id);
																$selected=$input_get->getString($key."_selected_star_amount".$id,0);
																
																
																if(isset($element))
																{
																	$new_value=$new_value.$selected.'/'.$element;					
																}
																break;
															}
															

															case "type_scale_rating":
															{
															$element=$input_get->getString($key."_scale_amount".$id);
															$selected=$input_get->getString($key."_scale_radio".$id,0);
															
																
																if(isset($element))
																{
																	$new_value=$new_value.$selected.'/'.$element;					
																}
																break;
															}
															
															case "type_spinner":
															{

																$element=$input_get->getString($key."_element".$id);
																if(isset($element))
																{
																	$new_value=$new_value.$element;					
																}
																break;
															}
															
															case "type_slider":
															{

																$element=$input_get->getString($key."_slider_value".$id);
																if(isset($element))
																{
																	$new_value=$new_value.$element;					
																}
																break;
															}
															case "type_range":
															{

																$element0=$input_get->getString($key."_element".$id.'0');
																$element1=$input_get->getString($key."_element".$id.'1');
																if(isset($element0) || isset($element1))
																{
																	$new_value=$new_value.$element0.'-'.$element1;					
																}
																break;
															}
															
															case "type_grading":
															{
																$element=$input_get->getString($key."_hidden_item".$id);
																$grading = explode(":",$element);
																$items_count = sizeof($grading)-1;
																
																$element = "";
																$total = "";
																
																for($k=0;$k<$items_count;$k++)

																{
																	$element .= $grading[$k].":".$input_get->getString($key."_element".$id.$k)." ";
															$total += $input_get->getString($key."_element".$id.$k);
														}

														$element .="Total:".$total;

																											
														if(isset($element))
														{
															$new_value=$new_value.$element;					
														}
														break;
													}
													
														case "type_matrix":
													{
													
														
														$input_type=$input_get->getString($key."_input_type".$id); 
																				
														$mat_rows = $input_get->getString($key."_hidden_row".$id);
														$mat_rows = explode('***', $mat_rows);
														$mat_rows = array_slice($mat_rows,0, count($mat_rows)-1);
														
														$mat_columns = $input_get->getString($key."_hidden_column".$id);
														$mat_columns = explode('***', $mat_columns);
														$mat_columns = array_slice($mat_columns,0, count($mat_columns)-1);
												  
														$row_ids=explode(",",substr($input_get->getString($key."_row_ids".$id), 0, -1));
														$column_ids=explode(",",substr($input_get->getString($key."_column_ids".$id), 0, -1)); 
								
																	
														$matrix="<table>";
																	
															$matrix .='<tr><td></td>';
														
														for( $k=0;$k< count($mat_columns) ;$k++)
															$matrix .='<td style="background-color:#BBBBBB; padding:5px; ">'.$mat_columns[$k].'</td>';
															$matrix .='</tr>';
														
														$aaa=Array();
														   $k=0;
														foreach( $row_ids as $row_id){
														$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px;">'.$mat_rows[$k].'</td>';
														
														  if($input_type=="radio"){
														 
														$mat_radio = $input_get->getString($key."_input_element".$id.$row_id,0);											
														  if($mat_radio==0){
																$checked="";
																$aaa[1]="";
																}
																else{
																$aaa=explode("_",$mat_radio);
																}
																
																foreach( $column_ids as $column_id){
																	if($aaa[1]==$column_id)
																	$checked="checked";
																	else
																	$checked="";
																$matrix .='<td style="text-align:center"><input  type="radio" '.$checked.' disabled /></td>';
																
																}
																
															} 
															else{
															if($input_type=="checkbox")
															{                
																foreach( $column_ids as $column_id){
																 $checked = $input_get->getString($key."_input_element".$id.$row_id.'_'.$column_id);                              
																 if($checked==1)							
																 $checked = "checked";						
																 else									 
																 $checked = "";

																$matrix .='<td style="text-align:center"><input  type="checkbox" '.$checked.' disabled /></td>';
															
															}
															
															}
															else
															{
															if($input_type=="text")
															{
																					  
																foreach( $column_ids as $column_id){
																 $checked = $input_get->getString($key."_input_element".$id.$row_id.'_'.$column_id);
																	
																$matrix .='<td style="text-align:center"><input  type="text" value="'.$checked.'" disabled /></td>';
													
															}
															
															}
															else{
																foreach( $column_ids as $column_id){
																 $checked = $input_get->getString($key."_select_yes_no".$id.$row_id.'_'.$column_id);
																	 $matrix .='<td style="text-align:center">'.$checked.'</td>';
																
												
															
																}
															}
															
															}
															
															}
															$matrix .='</tr>';
															$k++;
														}
														 $matrix .='</table>';

									
									
									
																										
														if(isset($matrix))
														{
															$new_value=$new_value.$matrix;					
														}
													
														break;
													}
												
													


																

																default: break;

															}

															

														$new_script = str_replace("%".$label_each."%", $new_value, $new_script);	

														}



													

												

											}

										}	

										

										if(strpos($new_script, "%all%")!=-1)

											$new_script = str_replace("%all%", $list, $new_script);	



										$body      = $new_script;

//////////////////////////////////////////////////////////////////////////////////////////////////////////										 

////////////////////////////////////////////////////////////////////////

										 $mode      = 1; 

									$send=$this->sendMail($from, $fromname, $recipient, $subject, $body, $mode, $cca, $bcc, $attachment, $replyto, $replytoname);  

									}	

									

									

									if($row->mail)

									{



										if($c)

										{

											 $from      = $c;

											 $fromname  = $c; 

										}

										else

										{

											 $from      = $site_mailfrom;

											 $fromname  = $site_fromname; 

										}

											 $recipient = $row->mail;

											$subject   = $row->title;

											$new_script = $row->script_mail;

											
											
											foreach($label_order_original as $key => $label_each) 

											{	

												if(strpos($row->script_mail, "%".$label_each."%")!=-1)

												{				

													$type = $label_type[$key];

													if($type!="type_submit_reset" or $type!="type_map" or $type!="type_editor" or  $type!="type_captcha" or  $type!="type_recaptcha" or  $type!="type_button")

													{

														$new_value ="";

														switch ($type)

															{

																case 'type_text':

																case 'type_password':

																case 'type_textarea':

																case "type_date":

																case "type_own_select":					

																case "type_country":				

																case "type_number":	

																{

																	$element=$input_get->getString($key."_element".$id);

																	if(isset($element))

																	{

																		$new_value = $element;					

																	}

																	break;

																

																

																}

																

																case "type_hidden":				

																{

																	$element=$input_get->getString($element_label);

																	if(isset($element))

																	{

																		$new_value = $element;	

																	}

																	break;

																}

																

																

																case "type_mark_map":

																{

																	$element=$input_get->getString($key."_long".$id);

																	if(isset($element))

																	{

																		$new_value = 'Longitude:'.$input_get->getString($key."_long".$id).'<br/>Latitude:'.$input_get->getString($key."_lat".$id);

																	}

																	break;		

																}

																					

																case "type_submitter_mail":

																{

																	$element=$input_get->getString($key."_element".$id);

																	if(isset($element))

																	{

																		$new_value = $element;					

																	}

																	break;		

																}

																

																case "type_time":

																{

																	

																	$hh=$input_get->getString($key."_hh".$id);

																	if(isset($hh))

																	{

																		$ss=$input_get->getString($key."_ss".$id);

																		if(isset($ss))

																			$new_value = $input_get->getString($key."_hh".$id).':'.$input_get->getString($key."_mm".$id).':'.$input_get->getString($key."_ss".$id);

																		else

																			$new_value = $input_get->getString($key."_hh".$id).':'.$input_get->getString($key."_mm".$id);

																		$am_pm=$input_get->getString($key."_am_pm".$id);

																		if(isset($am_pm))

																			$new_value=$new_value.' '.$input_get->getString($key."_am_pm".$id);

																		

																	}

																		

																	break;

																}

																

																case "type_phone":

																{

																	$element_first=$input_get->getString($key."_element_first".$id);

																	if(isset($element_first))

																	{

																			$new_value = $input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id);

																	}	

																	break;

																}

																

																case "type_name":

																{

																	$element_first=$input_get->getString($key."_element_first".$id);

																	if(isset($element_first))

																	{

																		$element_title=$input_get->getString($key."_element_title".$id);

																		if(isset($element_title))

																			$new_value = $input_get->getString($key."_element_title".$id).' '.$input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id).' '.$input_get->getString($key."_element_middle".$id);

																		else

																			$new_value = $input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id);

																	}	   

																	break;		

																}

																

																case "type_address":

																{

																	$street1=$input_get->getString($key."_street1".$id);

																	if(isset($street1))

																	{

																		$new_value=$input_get->getString($key."_street1".$id);

																		break;

																	}
																	
																	$street2=$input_get->getString($key."_street2".$id);

																	if(isset($street2))

																	{

																		$new_value=$input_get->getString($key."_street2".$id);

																		break;

																	}	
																	
																	$city=$input_get->getString($key."_city".$id);
																	
																	if(isset($city))

																	{

																		$new_value=$input_get->getString($key."_city".$id);

																		break;

																	}
																	
																	$state=$input_get->getString($key."_state".$id);
																	
																	if(isset($state))

																	{

																		$new_value=$input_get->getString($key."_state".$id);

																		break;

																	}
																		
																		$postal=$input_get->getString($key."_postal".$id);
																	
																	if(isset($postal))

																	{

																		$new_value=$input_get->getString($key."_postal".$id);

																		break;

																	}	

																	
																	$country = $input_get->getString($key."_country".$id);
																	
																		if(isset($country))
																		{

																		$new_value=$input_get->getString($key."_country".$id);

																		break;		
																		}
																	

																	break;

																}

																

																case "type_date_fields":

																{

																	$day=$input_get->getString($key."_day".$id);

																	if(isset($day))

																	{

																		$new_value = $input_get->getString($key."_day".$id).'-'.$input_get->getString($key."_month".$id).'-'.$input_get->getString($key."_year".$id);

																	}

																	break;

																}

																

																case "type_radio":

																{

																	$element=$input_get->getString($key."_other_input".$id);

																	if(isset($element))

																	{

																		$new_value = $input_get->getString($key."_other_input".$id);

																		break;

																	}	

																	

																	$element=$input_get->getString($key."_element".$id);

																	if(isset($element))

																	{

																		$new_value = $element;					

																	}

																	break;	

																}

																

																case "type_checkbox":

																{

																							

																	$start=-1;

																	for($j=0; $j<100; $j++)

																	{

																		$element=$input_get->getString($key."_element".$id.$j);

																		if(isset($element))

																		{

																			$start=$j;

																			break;

																		}

																	}	

																	

																	$other_element_id=-1;

																	$is_other=$input_get->getString($key."_allow_other".$id);

																	if($is_other=="yes")

																	{

																		$other_element_id=$input_get->getString($key."_allow_other_num".$id);

																	}

																	

															

																	if($start!=-1)

																	{

																		for($j=$start; $j<100; $j++)

																		{

																			

																			$element=$input_get->getString($key."_element".$id.$j);

																			if(isset($element))

																			if($j==$other_element_id)

																			{

																				$new_value = $new_value.$input_get->getString($key."_other_input".$id).'<br>';

																			}

																			else

																			

																				$new_value = $new_value.$input_get->getString($key."_element".$id.$j).'<br>';

																		}

																		

																	}

																	break;

																	}

																	case "type_paypal_price":	

															{		

																$new_value=0;

																if($input_get->getString($key."_element_dollars".$id))

																	$new_value=$input_get->getString($key."_element_dollars".$id);

																

																if($input_get->getString($key."_element_cents".$id))

																	$new_value=$new_value.'.'.$input_get->getString($key."_element_cents".$id);

															

																$new_value=$new_value.$form_currency;





																	

																	

																break;



															}			

				

															case "type_paypal_select":	

															{	

																$new_value=$input_get->getString($key."_element_label".$id).':'.$input_get->getString($key."_element".$id).$form_currency;

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																if(isset($element_quantity_label))

																	$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($key."_element_property_value".$id.$k);

																	}

																}



																break;

																

															}

													

															case "type_paypal_radio":				

															{

																		

																$new_value=$input_get->getString($key."_element_label".$id).' - '.$input_get->getString($key."_element".$id).$form_currency;

																

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																	if(isset($element_quantity_label))

																		$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($key."_element_property_value".$id.$k);

																	}

																}

																

																break;	

															}



															case "type_paypal_shipping":				

															{

																

																$new_value=$input_get->getString($key."_element_label".$id).' : '.$input_get->getString($key."_element".$id).$form_currency;		



																break;

															}



															case "type_paypal_checkbox":				

															{										

																$start=-1;

																for($j=0; $j<100; $j++)

																{

																	$element=$input_get->getString($key."_element".$id.$j);

																	if(isset($element))

																	{

																		$start=$j;

																		break;

																	}

																}	

																

																if($start!=-1)

																{

																	for($j=$start; $j<100; $j++)

																	{

																		

																		$element=$input_get->getString($key."_element".$id.$j);

																		if(isset($element))

																		{

																			$new_value=$new_value.$input_get->getString($key."_element".$id.$j."_label").' - '.($input_get->getString($key."_element".$id.$j)=='' ? '0'.$form_currency : $input_get->getString($key."_element".$id.$j)).$form_currency.'<br>';

																		}

																	}

																}

																

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																	if(isset($element_quantity_label))

																		$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($key."_element_property_value".$id.$k);

																	}

																}

																							

														

																break;

															}			

															case "type_paypal_total":				

															{
															
															
																$element=$input_get->getString($key."_paypal_total".$id);

																
																$new_value=$new_value.$element;				



																break;

															}
															
																case "type_star_rating":
															{
																$element=$input_get->getString($key."_star_amount".$id);
																$selected=$input_get->getString($key."_selected_star_amount".$id,0);
																//$star_color=$input_get->getString($key."_star_color_id_temp");
																
																if(isset($element))
																{
																	$new_value=$new_value.$selected.'/'.$element;					
																}
																break;
															}
															

															case "type_scale_rating":
															{
															$element=$input_get->getString($key."_scale_amount".$id);
															$selected=$input_get->getString($key."_scale_radio".$id,0);
															
																
																if(isset($element))
																{
																	$new_value=$new_value.$selected.'/'.$element;					
																}
																break;
															}
															
															case "type_spinner":
															{

																$element=$input_get->getString($key."_element".$id);
																if(isset($element))
																{
																	$new_value=$new_value.$element;					
																}
																break;
															}
															
															case "type_slider":
															{

																$element=$input_get->getString($key."_slider_value".$id);
																if(isset($element))
																{
																	$new_value=$new_value.$element;					
																}
																break;
															}
															case "type_range":
															{

																$element0=$input_get->getString($key."_element".$id.'0');
																$element1=$input_get->getString($key."_element".$id.'1');
																if(isset($element0) || isset($element1))
																{
																	$new_value=$new_value.$element0.'-'.$element1;					
																}
																break;
															}
															
															case "type_grading":
															{
																$element=$input_get->getString($key."_hidden_item".$id);
																$grading = explode(":",$element);
																$items_count = sizeof($grading)-1;
																
																$element = "";
																$total = "";
																
																for($k=0;$k<$items_count;$k++)

																{
																	$element .= $grading[$k].":".$input_get->getString($key."_element".$id.$k)." ";
															$total += $input_get->getString($key."_element".$id.$k);
														}

														$element .="Total:".$total;

																											
														if(isset($element))
														{
															$new_value=$new_value.$element;					
														}
														break;
													}
													
														case "type_matrix":
													{
													
														
														$input_type=$input_get->getString($key."_input_type".$id); 
																				
														$mat_rows = $input_get->getString($key."_hidden_row".$id);
														$mat_rows = explode('***', $mat_rows);
														$mat_rows = array_slice($mat_rows,0, count($mat_rows)-1);
														
														$mat_columns = $input_get->getString($key."_hidden_column".$id);
														$mat_columns = explode('***', $mat_columns);
														$mat_columns = array_slice($mat_columns,0, count($mat_columns)-1);
												  
														$row_ids=explode(",",substr($input_get->getString($key."_row_ids".$id), 0, -1));
														$column_ids=explode(",",substr($input_get->getString($key."_column_ids".$id), 0, -1)); 
								
														
														$matrix="<table>";
																	
															$matrix .='<tr><td></td>';
														
														for( $k=0;$k< count($mat_columns) ;$k++)
															$matrix .='<td style="background-color:#BBBBBB; padding:5px; ">'.$mat_columns[$k].'</td>';
															$matrix .='</tr>';
														
														$aaa=Array();
														   $k=0;
														foreach( $row_ids as $row_id){
														$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px;">'.$mat_rows[$k].'</td>';
														
														  if($input_type=="radio"){
														 
														$mat_radio = $input_get->getString($key."_input_element".$id.$row_id,0);											
														  if($mat_radio==0){
																$checked="";
																$aaa[1]="";
																}
																else{
																$aaa=explode("_",$mat_radio);
																}
																
																foreach( $column_ids as $column_id){
																	if($aaa[1]==$column_id)
																	$checked="checked";
																	else
																	$checked="";
																$matrix .='<td style="text-align:center"><input  type="radio" '.$checked.' disabled /></td>';
																
																}
																
															} 
															else{
															if($input_type=="checkbox")
															{                
																foreach( $column_ids as $column_id){
																 $checked = $input_get->getString($key."_input_element".$id.$row_id.'_'.$column_id);                              
																 if($checked==1)							
																 $checked = "checked";						
																 else									 
																 $checked = "";

																$matrix .='<td style="text-align:center"><input  type="checkbox" '.$checked.' disabled /></td>';
															
															}
															
															}
															else
															{
															if($input_type=="text")
															{
																					  
																foreach( $column_ids as $column_id){
																 $checked = $input_get->getString($key."_input_element".$id.$row_id.'_'.$column_id);
																	
																$matrix .='<td style="text-align:center"><input  type="text" value="'.$checked.'" disabled /></td>';
													
															}
															
															}
															else{
																foreach( $column_ids as $column_id){
																 $checked = $input_get->getString($key."_select_yes_no".$id.$row_id.'_'.$column_id);
																	 $matrix .='<td style="text-align:center">'.$checked.'</td>';
																
												
															
															}
															}
															
															}
															
															}
															$matrix .='</tr>';
															$k++;
														}
														 $matrix .='</table>';

									
									
									
																										
														if(isset($matrix))
														{
															$new_value=$new_value.$matrix;					
														}
													
														break;
													}
												
														

																

																default: break;

															}

															

														$new_script = str_replace("%".$label_each."%", $new_value, $new_script);	

														}



													

												

												}

											}	

											

											if(strpos($new_script, "%all%")!=-1)

												$new_script = str_replace("%all%", $list, $new_script);	



											$body      = $new_script;

											$mode      = 1; 



										$send=$this->sendMail($from, $fromname, $recipient, $subject, $body, $mode, $cca, $bcc, $attachment, $replyto, $replytoname);  

									}

								}

							}

							else 

							{ 

								if($row->mail)

								{



								 $from      = $site_mailfrom;

								 $fromname  = $site_fromname; 

								 $recipient = $row->mail;

								 $subject     = $row->title;

								$new_script = $row->script_mail;

	
											foreach($label_order_original as $key => $label_each) 

											{	

												if(strpos($row->script_mail, "%".$label_each."%")!=-1)

												{				

										
													$type = $label_type[$key];

													if($type!="type_submit_reset" or $type!="type_map" or $type!="type_editor" or  $type!="type_captcha" or  $type!="type_recaptcha" or  $type!="type_button")

													{

														$new_value ="";

														switch ($type)

															{

																case 'type_text':

																case 'type_password':

																case 'type_textarea':

																case "type_date":

																case "type_own_select":					

																case "type_country":				

																case "type_number":	

																{

																	$element=$input_get->getString($key."_element".$id);

																	if(isset($element))

																	{

																		$new_value = $element;					

																	}

																	break;

																

																

																}

																

																case "type_hidden":				

																{

																	$element=$input_get->getString($element_label);

																	if(isset($element))

																	{

																		$new_value = $element;	

																	}

																	break;

																}

																

																

																case "type_mark_map":

																{

																	$element=$input_get->getString($key."_long".$id);

																	if(isset($element))

																	{

																		$new_value = 'Longitude:'.$input_get->getString($key."_long".$id).'<br/>Latitude:'.$input_get->getString($key."_lat".$id);

																	}

																	break;		

																}

																					

																case "type_submitter_mail":

																{

																	$element=$input_get->getString($key."_element".$id);

																	if(isset($element))

																	{

																		$new_value = $element;					

																	}

																	break;		

																}

																

																case "type_time":

																{

																	

																	$hh=$input_get->getString($key."_hh".$id);

																	if(isset($hh))

																	{

																		$ss=$input_get->getString($key."_ss".$id);

																		if(isset($ss))

																			$new_value = $input_get->getString($key."_hh".$id).':'.$input_get->getString($key."_mm".$id).':'.$input_get->getString($key."_ss".$id);

																		else

																			$new_value = $input_get->getString($key."_hh".$id).':'.$input_get->getString($key."_mm".$id);

																		$am_pm=$input_get->getString($key."_am_pm".$id);

																		if(isset($am_pm))

																			$new_value=$new_value.' '.$input_get->getString($key."_am_pm".$id);

																		

																	}

																		

																	break;

																}

																

																case "type_phone":

																{

																	$element_first=$input_get->getString($key."_element_first".$id);

																	if(isset($element_first))

																	{

																			$new_value = $input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id);

																	}	

																	break;

																}

																

																case "type_name":

																{

																	$element_first=$input_get->getString($key."_element_first".$id);

																	if(isset($element_first))

																	{

																		$element_title=$input_get->getString($key."_element_title".$id);

																		if(isset($element_title))

																			$new_value = $input_get->getString($key."_element_title".$id).' '.$input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id).' '.$input_get->getString($key."_element_middle".$id);

																		else

																			$new_value = $input_get->getString($key."_element_first".$id).' '.$input_get->getString($key."_element_last".$id);

																	}	   

																	break;		

																}

																

																case "type_address":

																{

																	$street1=$input_get->getString($key."_street1".$id);

																	if(isset($street1))

																	{

																		$new_value=$input_get->getString($key."_street1".$id);

																		break;

																	}
																	
																	$street2=$input_get->getString($key."_street2".$id);

																	if(isset($street2))

																	{

																		$new_value=$input_get->getString($key."_street2".$id);

																		break;

																	}	
																	
																	$city=$input_get->getString($key."_city".$id);
																	
																	if(isset($city))

																	{

																		$new_value=$input_get->getString($key."_city".$id);

																	break;

																	}
																	
																	$state=$input_get->getString($key."_state".$id);
																	
																	if(isset($state))

																	{

																		$new_value=$input_get->getString($key."_state".$id);

																		break;

																	}
																		
																		$postal=$input_get->getString($key."_postal".$id);
																	
																	if(isset($postal))

																	{

																		$new_value=$input_get->getString($key."_postal".$id);

																		break;
																	}	

																	
																	$country = $input_get->getString($key."_country".$id);
																	
																		if(isset($country))
																		{

																		$new_value=$input_get->getString($key."_country".$id);

																		break;
																		}
																	

																	break;

																}

																

																case "type_date_fields":

																{

																	$day=$input_get->getString($key."_day".$id);

																	if(isset($day))

																	{

																		$new_value = $input_get->getString($key."_day".$id).'-'.$input_get->getString($key."_month".$id).'-'.$input_get->getString($key."_year".$id);

																	}

																	break;

																}

																

																case "type_radio":

																{

																	$element=$input_get->getString($key."_other_input".$id);

																	if(isset($element))

																	{

																		$new_value = $input_get->getString($key."_other_input".$id);

																		break;

																	}	

																	

																	$element=$input_get->getString($key."_element".$id);

																	if(isset($element))

																	{

																		$new_value = $element;					

																	}

																	break;	

																}

																

																case "type_checkbox":

																{

																							

																	$start=-1;

																	for($j=0; $j<100; $j++)

																	{

																		$element=$input_get->getString($key."_element".$id.$j);

																		if(isset($element))

																		{

																			$start=$j;

																			break;

																		}

																	}	

																	

																	$other_element_id=-1;

																	$is_other=$input_get->getString($key."_allow_other".$id);

																	if($is_other=="yes")

																	{

																		$other_element_id=$input_get->getString($key."_allow_other_num".$id);

																	}

																	

															

																	if($start!=-1)

																	{

																		for($j=$start; $j<100; $j++)

																		{

																			

																			$element=$input_get->getString($key."_element".$id.$j);

																			if(isset($element))

																			if($j==$other_element_id)

																			{

																				$new_value = $new_value.$input_get->getString($key."_other_input".$id).'<br>';

																			}

																			else

																			

																				$new_value = $new_value.$input_get->getString($key."_element".$id.$j).'<br>';

																		}

																		

																	}

																				

																	

																	break;

																}



																case "type_paypal_price":	

															{		

																$new_value=0;

																if($input_get->getString($key."_element_dollars".$id))

																	$new_value=$input_get->getString($key."_element_dollars".$id);

																

																if($input_get->getString($key."_element_cents".$id))

																	$new_value=$new_value.'.'.$input_get->getString($key."_element_cents".$id);

															

																$new_value=$new_value.$form_currency;





																	

																	

																break;

															}			

															

															case "type_paypal_select":	

															{	

													

																$new_value=$input_get->getString($key."_element_label".$id).':'.$input_get->getString($key."_element".$id).$form_currency;

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																if(isset($element_quantity_label))

																	$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($key."_element_property_value".$id.$k);

																	}

																}



											

																break;

															}

															

															case "type_paypal_radio":				

															{

																

																	$new_value=$input_get->getString($key."_element_label".$id).' - '.$input_get->getString($key."_element".$id).$form_currency;

																

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																	if(isset($element_quantity_label))

																		$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($key."_element_property_value".$id.$k);

																	}

																}

																break;

															}



															case "type_paypal_shipping":				

															{

																

																$new_value=$input_get->getString($key."_element_label".$id).' : '.$input_get->getString($key."_element".$id).$form_currency;

																

															

																break;

															}



															case "type_paypal_checkbox":				

															{

																												$start=-1;

																for($j=0; $j<100; $j++)

																{

																	$element=$input_get->getString($key."_element".$id.$j);

																	if(isset($element))

																	{

																		$start=$j;

																		break;

																	}

																}	

																

																if($start!=-1)

																{

																	for($j=$start; $j<100; $j++)

																	{

																		

																		$element=$input_get->getString($key."_element".$id.$j);

																		if(isset($element))

																		{

																			$new_value=$new_value.$input_get->getString($key."_element".$id.$j."_label").' - '.($input_get->getString($key."_element".$id.$j)=='' ? '0'.$form_currency : $input_get->getString($key."_element".$id.$j)).$form_currency.'<br>';

																		}

																	}

																}

																

																$element_quantity_label=$input_get->getString($key."_element_quantity_label".$id);

																	if(isset($element_quantity_label))

																		$new_value.='<br/>'.$input_get->getString($key."_element_quantity_label".$id).': '.$input_get->getString($key."_element_quantity".$id);

														

																for($k=0; $k<50; $k++)

																{

																	$temp_val=$input_get->getString($key."_element_property_value".$id.$k);

																	if(isset($temp_val))

																	{			

																		$new_value.='<br/>'.$input_get->getString($key."_element_property_label".$id.$k).': '.$input_get->getString($key."_element_property_value".$id.$k);

																	}

																}	

																

																break;

															}

															case "type_paypal_total":				

															{
															
															
																$element=$input_get->getString($key."_paypal_total".$id);

																
																$new_value=$new_value.$element;				



																break;

															}

																case "type_star_rating":
															{
																$element=$input_get->getString($key."_star_amount".$id);
																$selected=$input_get->getString($key."_selected_star_amount".$id,0);
																//$star_color=$input_get->getString($key."_star_color_id_temp");
																
																if(isset($element))
																{
																	$new_value=$new_value.$selected.'/'.$element;					
																}
																break;
															}
															

															case "type_scale_rating":
															{
															$element=$input_get->getString($key."_scale_amount".$id);
															$selected=$input_get->getString($key."_scale_radio".$id,0);
															
																
																if(isset($element))
																{
																	$new_value=$new_value.$selected.'/'.$element;					
																}
																break;
															}
															
															case "type_spinner":
															{

																$element=$input_get->getString($key."_element".$id);
																if(isset($element))
																{
																	$new_value=$new_value.$element;					
																}
																break;
															}
															
															case "type_slider":
															{

																$element=$input_get->getString($key."_slider_value".$id);
																if(isset($element))
																{
																	$new_value=$new_value.$element;					
																}
																break;
															}
															case "type_range":
															{

																$element0=$input_get->getString($key."_element".$id.'0');
																$element1=$input_get->getString($key."_element".$id.'1');
																if(isset($element0) || isset($element1))
																{
																	$new_value=$new_value.$element0.'-'.$element1;					
																}
																break;
															}
															
															case "type_grading":
															{
																$element=$input_get->getString($key."_hidden_item".$id);
																$grading = explode(":",$element);
																$items_count = sizeof($grading)-1;
																
																$element = "";
																$total = "";
																
																for($k=0;$k<$items_count;$k++)

																{
																	$element .= $grading[$k].":".$input_get->getString($key."_element".$id.$k)." ";
															$total += $input_get->getString($key."_element".$id.$k);
														}

														$element .="Total:".$total;

																											
														if(isset($element))
														{
															$new_value=$new_value.$element;					
														}
														break;
													}
													
														case "type_matrix":
													{
													
														
														$input_type=$input_get->getString($key."_input_type".$id); 
																				
														$mat_rows = $input_get->getString($key."_hidden_row".$id);
														$mat_rows = explode('***', $mat_rows);
														$mat_rows = array_slice($mat_rows,0, count($mat_rows)-1);
														
														$mat_columns = $input_get->getString($key."_hidden_column".$id);
														$mat_columns = explode('***', $mat_columns);
														$mat_columns = array_slice($mat_columns,0, count($mat_columns)-1);
												  
														$row_ids=explode(",",substr($input_get->getString($key."_row_ids".$id), 0, -1));
														$column_ids=explode(",",substr($input_get->getString($key."_column_ids".$id), 0, -1)); 
																				  
																	
														$matrix="<table>";
																	
															$matrix .='<tr><td></td>';
														
														for( $k=0;$k< count($mat_columns) ;$k++)
															$matrix .='<td style="background-color:#BBBBBB; padding:5px; ">'.$mat_columns[$k].'</td>';
															$matrix .='</tr>';
														
														$aaa=Array();
														   $k=0;
														foreach($row_ids as $row_id)
														{
														$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px;">'.$mat_rows[$k].'</td>';
														
														  if($input_type=="radio"){
														 
														$mat_radio = $input_get->getString($key."_input_element".$id.$row_id,0);											
														  if($mat_radio==0){
																$checked="";
																$aaa[1]="";
																}
																else{
																$aaa=explode("_",$mat_radio);
																}
																
																foreach($column_ids as $column_id){
																	if($aaa[1]==$column_id)
																	$checked="checked";
																	else
																	$checked="";
																$matrix .='<td style="text-align:center"><input  type="radio" '.$checked.' disabled /></td>';
																
																}
																
															} 
															else{
															if($input_type=="checkbox")
															{                
																foreach($column_ids as $column_id){
																 $checked = $input_get->getString($key."_input_element".$id.$row_id.'_'.$column_id);                              
																 if($checked==1)							
																 $checked = "checked";						
																 else									 
																 $checked = "";

																$matrix .='<td style="text-align:center"><input  type="checkbox" '.$checked.' disabled /></td>';
															
															}
															
															}
															else
															{
															if($input_type=="text")
															{
																					  
																foreach($column_ids as $column_id){
																 $checked = $input_get->getString($key."_input_element".$id.$row_id.'_'.$column_id);
																	
																$matrix .='<td style="text-align:center"><input  type="text" value="'.$checked.'" disabled /></td>';
													
															}
															
															}
															else{
																foreach($column_ids as $column_id){
																 $checked = $input_get->getString($i."_select_yes_no".$id.$row_id.'_'.$column_id);
																	 $matrix .='<td style="text-align:center">'.$checked.'</td>';
																
												
															
																}
															}
															
															}
															
															}
															$matrix .='</tr>';
															$k++;
														}
														 $matrix .='</table>';

									
									
									
																										
														if(isset($matrix))
														{
															$new_value=$new_value.$matrix;					
														}
													
														break;
													}
												
												



																

																default: break;

															}

															

														$new_script = str_replace("%".$label_each."%", $new_value, $new_script);	

														}



													

												

												}

											}	

											

											if(strpos($new_script, "%all%")!=-1)

												$new_script = str_replace("%all%", $list, $new_script);	



											$body      = $new_script;

								$mode        = 1; 

								 $send=$this->sendMail($from, $fromname, $recipient, $subject, $body, $mode, $cca, $bcc, $attachment, $replyto, $replytoname); 

								} 

							}

									
		$succes=1;
		if($row->mail)
			{
				if ( $send !== true ) 
				{
					$msg=JText::_('WDF_MAIL_SEND_ERROR');
					$succes = 0;
				}
				else 
					$msg=JText::_('WDF_MAIL_SENT');
			}
		else	

			$msg=JText::_('WDF_SUBMITTED');

			
		
		}
									
		switch($row->submit_text_type)
		{
					case "2":
					{
						$redirect_url=JUri::root()."index.php?option=com_content&view=article&id=".$row->article_id."&Itemid=".$Itemid;
					//	$mainframe->redirect("index.php?option=com_content&view=article&id=".$row->article_id."&Itemid=".$Itemid, $msg);
						break;
					}
					case "3":
					{
						$_SESSION['show_submit_text'.$id]=1;
						$redirect_url=$_SERVER["HTTP_REFERER"];
						//$mainframe->redirect($_SERVER["REQUEST_URI"], $msg);
						break;
					}											
					case "4":
					{
						$redirect_url=$row->url;
						//$mainframe->redirect($row->url, $msg);
						break;
					}
					default:
					{
						$redirect_url=$_SERVER["HTTP_REFERER"];
						//$mainframe->redirect($_SERVER["REQUEST_URI"], $msg);
						break;
					}
		}		
			if(!$str)

				$mainframe->redirect($redirect_url, $msg);

			else

			{

				$str.="&return=".urlencode($redirect_url);

				$mainframe->redirect($str);



			}		

		
	}
	
	function sendMail(&$from, &$fromname, &$recipient, &$subject, &$body, &$mode=0, &$cc=null, &$bcc=null, &$attachment=null, &$replyto=null, &$replytoname=null)
    {
			$input_get = JFactory::getApplication()->input;
				$recipient=explode (',', str_replace(' ', '', $recipient ));
                // Get a JMail instance
                $mail = JFactory::getMailer();
 
                $mail->setSender(array($from, $fromname));
                $mail->setSubject($subject);
                $mail->setBody($body);
 
                // Are we sending the email as HTML?
                if ($mode) {
                        $mail->IsHTML(true);
                }
 
                $mail->addRecipient($recipient);
                $mail->addCC($cc);
                $mail->addBCC($bcc);
				
				if($attachment)
					foreach($attachment as $attachment_temp)
					{
						$mail->AddEmbeddedImage($attachment_temp[0], $attachment_temp[1], $attachment_temp[2]);
					}
 
                // Take care of reply email addresses
                if (is_array($replyto)) {
                        $numReplyTo = count($replyto);
                        for ($i=0; $i < $numReplyTo; $i++){
                                $mail->addReplyTo(array($replyto[$i], $replytoname[$i]));
                        }
                } elseif (isset($replyto)) {
                        $mail->addReplyTo(array($replyto, $replytoname));
                }
 
                return  $mail->Send();
        }
	
	
	
	function remove($group_id)
	{
		$input_get = JFactory::getApplication()->input;

		$db = JFactory::getDBO();
		$db->setQuery('DELETE FROM #__formmaker_submits WHERE group_id="'.$db->escape($group_id).'"');
		$db->query();
	}
	
}
	
	?>
