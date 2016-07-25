<?php 
  
 /**
 * @package Form Maker Lite
 * @author Web-Dorado
 * @copyright (C) 2011 Web-Dorado. All rights reserved.
 * @license GNU/GPLv3 http://www.gnu.org/licenses/gpl-3.0.html
 **/

defined('_JEXEC') or die('Restricted access');

require_once JPATH_COMPONENT . '/admin.formmaker.html.php';
require_once JPATH_COMPONENT . '/toolbar.formmaker.html.php';
require_once JPATH_COMPONENT . '/controller.php' ;

JTable::addIncludePath( JPATH_COMPONENT.'/tables' );


$controller = new FormmakerController();


$task	= JRequest::getCmd('task'); 

$id = JRequest::getVar('id', 0, 'get', 'int');


$bar= JToolBar::getInstance( 'toolbar' );
$bar->appendButton('Custom','<a href="http://web-dorado.com/products/joomla-form.html" target="_blank" style=""><img src="components/com_formmaker/images/buyme.png" border="0" alt="http://web-dorado.com/files/fromSpiderCatalogJoomla.php" style="float:left"></a>', 'custom');
// checks the $task variable and 
// choose an appropiate function


switch ( $task )

{

	case 'themes'  :
	{
		TOOLBAR_formmaker::_THEMES();
	}
		break;
		
	case 'add_themes'  :
	case 'edit_themes'  :
	{
		TOOLBAR_formmaker::_NEW_THEMES();
	}
		break;
		
	case 'submits'  :
	{
		TOOLBAR_formmaker::_SUBMITS();
	}
		break;
		
	case 'edit_submit'  :
	{
		TOOLBAR_formmaker::EDIT_SUBMITS();
	}
		break;
		
	case 'add'  :
	case 'edit'  :	
	{
		TOOLBAR_formmaker::_NEW();
	}
		break;

	case 'form_options'  :
	{
	TOOLBAR_formmaker::_NEW_Form_options();
	}
	break;

	case 'form_layout':
	{
	TOOLBAR_formmaker::_NEW_Form_form_layout();
	}
	break;


	default:
		TOOLBAR_formmaker::_DEFAULT();
		break;

}



switch($task){

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	case 'generate_xml':
		generate_xml();
		break;
		
	case 'generate_csv':
		generate_csv();
		break;

	case 'paypal_info':
		paypal_info();
		break;
		
	case 'default':
		setdefault();
		break;
		
	case 'product_option':
		product_option();
		break;
			
	case 'preview':
		preview_formmaker();
		break;
		
	case 'themes':
		show_themes();
		break;
		
	case 'add_themes':
		add_themes();
		break;
		
	case 'edit_themes':
		edit_themes();
		break;
		
	case 'apply_themes':	
	case 'save_themes':		
		save_themes($task);
		break;
		
	case 'remove_themes':
		remove_themes();
		break;
		
	case 'cancel_themes':
		cancel_themes();
		break;
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	case 'forms':
		show();
		
		break;
		
	case 'submits':
		show_submits();
		
		break;

	case 'element':

		$controller->execute( $task );

		$controller->redirect();

		break;

	/*case 'select_article':

		$controller->execute( $task );

		$controller->redirect();

		break;*/

	case 'select_article':

		select_article();
		break;

	case 'add':

		add();

		break;

	case 'cancel':		

		cancel();

		break;

	case 'apply':	
	case 'save_and_new':	
	case 'save':		

		save($task);

		break;

	case 'edit':

    		edit();

    		break;
			
	case 'save_as_copy':

    		save_as_copy();
    		break;
			
	case 'copy':

    		copy_form();
    		break;
			
	case 'form_options':
			form_options();
			break;
	case 'form_options_temp':
    		form_options_temp();
    		break;
			
	case 'form_layout':
			form_layout();
			break;	
	case 'form_layout_temp':
			form_layout_temp();
			break;	
			
//////////////////////////////////////////////////////////////////		
	case 'apply_form_options':
	case 'save_form_options':
    		save_form_options($task);
    		break;
	case 'apply_form_layout':
	case 'save_form_layout':
    		save_form_layout($task);
    		break;
//////////////////////////////////////////////////////////////////		
	case 'cancel_secondary':
    		cancelSecondary();
    		break;
		

//////////////////////////////////////////////////////////////////		
	case 'remove':

		remove();

		break;

		
	case 'remove_submit':
		removeSubmit();
		break;
		
	case 'edit_submit':
		editSubmit();
		break;

	case 'save_submit':
	case 'apply_submit':
		saveSubmit($task);
		break;

	case 'cancel_submit':
		cancelSubmit();
		break;

 	 case 'publish':
   		change(1 );
    		break;

	 case 'unpublish':
	   	change(0 );
	    	break;				

	 case 'gotoedit':
	   	gotoedit();
	    	break;	

	case 'country_list':
	   	country_list();
	    	break;
			
	 case 'show_map':
	   	show_map();
	    	break;

	case 'show_matrix':
	   	show_matrix();
	    	break;	
					
	default:
		showredirect();
		break;



}


function generate_csv()
{

	$db		= JFactory::getDBO();
	$id 	= JRequest::getVar('id');





$user = JFactory::getUser();
 
if ($user->guest)
{
	echo 'You have no permissions to download csv';
	return;
}
	
if(!(in_array(7,$user->groups) || in_array(8,$user->groups)))
{ 
	echo 'You have no permissions to download csv';
	return;
}

$form_id=$id;
$paypal_info_fields=array('currency', 'ord_last_modified', 'status', 'full_name', 'fax', 'mobile_phone', 'email', 'phone', 'address', 'paypal_info',  'ipn', 'tax', 'shipping');

 
	$query = "SELECT * FROM #__formmaker_submits where form_id=$form_id ";
	$db->setQuery( $query);
	$rows = $db->loadObjectList();
	if($db->getErrorNum()){
		echo $db->stderr();
		return false;
	}

	$n=count($rows);

	$labels= array();
	for($i=0; $i < $n ; $i++)
	{
		$row = &$rows[$i];
		if(!in_array($row->element_label, $labels))
		{
			array_push($labels, $row->element_label);
		}
	}
	$label_titles=array();
	$sorted_labels= array();
 
 $query_lable = "SELECT label_order,title FROM #__formmaker where id=$form_id ";

	$db->setQuery( $query_lable);

	$rows_lable = $db->loadObjectList();

	if($db->getErrorNum()){

		echo $db->stderr();

		return false;

	}
	
	$ptn = "/[^a-zA-Z0-9_]/";
			$rpltxt = "";
		
	
	$title=preg_replace($ptn, $rpltxt, $rows_lable[0]->title);

	$sorted_labels_id= array();
	$sorted_labels= array();
	$label_titles=array();
	if($labels)
	{
	
		$label_id= array();
		$label_order= array();
		$label_order_original= array();
		$label_type= array();
	
		///stexic
		$label_all	= explode('#****#',$rows_lable[0]->label_order);
		$label_all 	= array_slice($label_all,0, count($label_all)-1);   
	
	
	
		foreach($label_all as $key => $label_each) 
		{
			$label_id_each=explode('#**id**#',$label_each);
			array_push($label_id, $label_id_each[0]);
		
			$label_oder_each=explode('#**label**#', $label_id_each[1]);
		
			array_push($label_order_original, $label_oder_each[0]);
		
			$ptn = "/[^a-zA-Z0-9_]/";
			$rpltxt = "";
			$label_temp=preg_replace($ptn, $rpltxt, $label_oder_each[0]);
			array_push($label_order, $label_temp);
		
			array_push($label_type, $label_oder_each[1]);

	
			//echo $label."<br>";
		
		}
	
		foreach($label_id as $key => $label) 
			if(in_array($label, $labels))
			{
				array_push($sorted_labels, $label_order[$key]);
				array_push($sorted_labels_id, $label);
				array_push($label_titles, $label_order_original[$key]);
			}
		

	}

 	$m=count($sorted_labels);
	$group_id_s= array();
	$l=0;
	 

	if(count($rows)>0 and $m)
	for($i=0; $i <count($rows) ; $i++)
	{

		$row = &$rows[$i];

		if(!in_array($row->group_id, $group_id_s))
		{
	
			array_push($group_id_s, $row->group_id);
		
		}
	}
 

  
	 $data=array();

 
	for($www=0;  $www < count($group_id_s); $www++)
	{
	$i=$group_id_s[$www];

	$temp= array();
	for($j=0; $j < $n ; $j++)
	{

		$row = &$rows[$j];

		if($row->group_id==$i)
		{

			array_push($temp, $row);
		}
	}



	$f=$temp[0];
	$date=$f->date;
	$ip=$f->ip;	 
	$data_temp['Submit date']=$date;
	$data_temp['Ip']=$ip;


	$ttt=count($temp);

	for($h=0; $h < $m ; $h++)
	{	
		$data_temp[$label_titles[$h]]='';

		for($g=0; $g < $ttt ; $g++)
		{		
			$t = $temp[$g];
			if($t->element_label==$sorted_labels_id[$h])
			{
				if(strpos($t->element_value,"*@@url@@*"))
				{
					$new_file=str_replace("*@@url@@*",'', $t->element_value);
					$new_filename=explode('/', $new_file);
					$data_temp[$label_titles[$h]]=$new_file;
				}
				else
					if(strpos($t->element_value,"***br***"))
					{
						$data_temp[$label_titles[$h]]= substr(str_replace("***br***",', ', $t->element_value), 0, -2);
					}
						else
						if(strpos($t->element_value,"***map***"))
						{
							$data_temp[$label_titles[$h]]= 'Longitude:'.substr(str_replace("***map***",', Latitude:', $t->element_value), 0, -2);
						}
						else

							if(strpos($t->element_value,"***star_rating***"))

							{	

								$element = str_replace("***star_rating***",'', $t->element_value);

								$element = explode("***", $element);

							

								$data_temp[$label_titles[$h]]= ' '.$element[1].'/'.$element[0];

						
							}

							else

							if(strpos($t->element_value,"***grading***"))

							{	

								$element = str_replace("***grading***",'', $t->element_value);

								

								$grading = explode(":",$element);                        

								$items_count = sizeof($grading)-1;

								$items = "";

								$total = "";

								for($k=0;$k<$items_count/2;$k++)

								{

								$items .= $grading[$items_count/2+$k].": ".$grading[$k].", ";

								$total += $grading[$k];

								}

								$items .="Total: ".$total;

								

								

								$data_temp[$label_titles[$h]]= $items;

								

							

							}

						else
							if(strpos($t->element_value,"***matrix***"))
							{	
								$element = str_replace("***matrix***",'', $t->element_value);

								$matrix_value=explode('***', $element);
						$matrix_value = array_slice($matrix_value,0, count($matrix_value)-1);   
									
														
						$mat_rows=$matrix_value[0];
						$mat_columns=$matrix_value[$mat_rows+1];
									
						$matrix="";
						
						
						$aaa=Array();
						   $var_checkbox=1;
						   $selected_value="";
						   $selected_value_yes="";
						   $selected_value_no="";
						   
						for( $k=1;$k<=$mat_rows;$k++){
					
						  if($matrix_value[$mat_rows+$mat_columns+2]=="radio"){
						  
						  if($matrix_value[$mat_rows+$mat_columns+2+$k]==0){
								$checked="0";
								$aaa[1]="";
								}
								else
								$aaa=explode("_",$matrix_value[$mat_rows+$mat_columns+2+$k]);

								
								for( $l=1;$l<=$mat_columns;$l++){
									if($aaa[1]==$l)
									$checked='1';
									else
									$checked="0";
																			
								$matrix .= '['.$matrix_value[$k].','.$matrix_value[$mat_rows+1+$l].']='.$checked."; ";
								
								}
								
							} 
							else{
							if($matrix_value[$mat_rows+$mat_columns+2]=="checkbox")
							{
													  
								for( $l=1;$l<=$mat_columns;$l++){
								if( $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox]==1)
								 $checked ='1';
								 else
								 $checked ='0';
								
								$matrix .= '['.$matrix_value[$k].','.$matrix_value[$mat_rows+1+$l].']='.$checked."; ";
								
							 $var_checkbox++;
							}
							
							}
							else
							{
							if($matrix_value[$mat_rows+$mat_columns+2]=="text")
							{
													  
								for( $l=1;$l<=$mat_columns;$l++){
								 $text_value = $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox];
								
																
								$matrix .='['.$matrix_value[$k].','.$matrix_value[$mat_rows+1+$l].']='.$text_value."; ";
							 $var_checkbox++;
							}
							
							}
							else{
							  
							 for( $l=1;$l<=$mat_columns;$l++){
							  $selected_text = $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox];
					
							 $matrix .='['.$matrix_value[$k].','.$matrix_value[$mat_rows+1+$l].']='.$selected_text."; ";
							 $var_checkbox++;
						  
							}
							}
							
							}
							
							}
							
						}
					
								
								$data_temp[$label_titles[$h]]= $matrix;
								
							
							}
						
						else
							$data_temp[$label_titles[$h]]=' '.$t->element_value;
			}
		}
	}
	$query = "SELECT * FROM #__formmaker_sessions where group_id=".$f->group_id;
	$db->setQuery( $query);	$paypal_info = $db->loadObject();
	if($db->getErrorNum()){		echo $db->stderr();		return false;	}
	if($paypal_info){
		foreach($paypal_info_fields as $paypal_info_field)
		$data_temp['PAYPAL_'.$paypal_info_field]=$paypal_info->$paypal_info_field;
	}  
	$data[]=$data_temp;
	}


  // file name for download
  $filename = $title."_" . date('Ymd') . ".csv";
 
 	header('Content-Encoding: Windows-1252');
	header('Content-type: text/csv; charset=Windows-1252');
	header("Content-Disposition: attachment; filename=\"$filename\"");

  $flag = false;
  foreach($data as $row) {
    if(!$flag) {
      # display field/column names as first row
  	//  echo "sep=,\r\n";
    echo '"'.implode('","', array_keys($row));
	  if($paypal_info){
	  echo '","Currency","Last modified","Status","Full Name","Fax","Mobile phone","Email","Phone","Address","Paypal info","IPN","Tax","Shipping';

		}  
	  echo "\"\r\n";
      $flag = true;
    }
    array_walk($row, 'cleanData');
    echo '"'.implode('","',array_values($row))."\"\r\n";
  }










}

function generate_xml()
{

	$db		= JFactory::getDBO();
	$id 	= JRequest::getVar('id');

$user = JFactory::getUser();
 
if ($user->guest)
{
	echo 'You have no permissions to download xml';
	return;
}
	
if(!(in_array(7,$user->groups) || in_array(8,$user->groups)))
{ 
	echo 'You have no permissions to download xml';
	return;
}


$form_id=$id;
$paypal_info_fields=array('currency', 'ord_last_modified', 'status', 'full_name', 'fax', 'mobile_phone', 'email', 'phone', 'address', 'paypal_info',  'ipn', 'tax', 'shipping');


 $query = "SELECT * FROM #__formmaker_submits where form_id=$form_id ";

	$db->setQuery( $query);

	$rows = $db->loadObjectList();

	if($db->getErrorNum()){

		echo $db->stderr();

		return false;

	}
	
	$n=count($rows);
	
	$labels= array();
	for($i=0; $i < $n ; $i++)

	{
		$row = &$rows[$i];
		if(!in_array($row->element_label, $labels))
		{
			array_push($labels, $row->element_label);
		}
	}
	$label_titles=array();
	$sorted_labels= array();
 
 $query_lable = "SELECT label_order,title FROM #__formmaker where id=$form_id ";

	$db->setQuery( $query_lable);

	$rows_lable = $db->loadObjectList();

	if($db->getErrorNum()){

		echo $db->stderr();

		return false;

	}
		
	
	$ptn = "/[^a-zA-Z0-9_]/";
			$rpltxt = "";
			
		
	$title=preg_replace($ptn, $rpltxt, $rows_lable[0]->title);
	
	$sorted_labels_id= array();
	$sorted_labels= array();
	$label_titles=array();
	if($labels)
	{
		
		$label_id= array();
		$label_order= array();
		$label_order_original= array();
		$label_type= array();
		
		///stexic
		$label_all	= explode('#****#',$rows_lable[0]->label_order);
		$label_all 	= array_slice($label_all,0, count($label_all)-1);   
		
		
		
		foreach($label_all as $key => $label_each) 
		{
			$label_id_each=explode('#**id**#',$label_each);
			array_push($label_id, $label_id_each[0]);
			
			$label_oder_each=explode('#**label**#', $label_id_each[1]);
			
			array_push($label_order_original, $label_oder_each[0]);
			
			$ptn = "/[^a-zA-Z0-9_]/";
			$rpltxt = "";
			$label_temp=preg_replace($ptn, $rpltxt, $label_oder_each[0]);
			array_push($label_order, $label_temp);
			
			array_push($label_type, $label_oder_each[1]);

		
			//echo $label."<br>";
			
		}
		
		foreach($label_id as $key => $label) 
			if(in_array($label, $labels))
			{
				array_push($sorted_labels, $label_order[$key]);
				array_push($sorted_labels_id, $label);
				array_push($label_titles, $label_order_original[$key]);
			}
			

	}
	
 	$m=count($sorted_labels);
	$group_id_s= array();
	$l=0;
	 
//var_dump($label_titles);
	if(count($rows)>0 and $m)
	for($i=0; $i <count($rows) ; $i++)
	{
	
		$row = &$rows[$i];
	
		if(!in_array($row->group_id, $group_id_s))
		{
		
			array_push($group_id_s, $row->group_id);
			
		}
	}
 

  
 $data=array();

 
 for($www=0;  $www < count($group_id_s); $www++)
	{	
	$i=$group_id_s[$www];
	
		$temp= array();
		for($j=0; $j < $n ; $j++)
		{
		
			$row = &$rows[$j];
			
			if($row->group_id==$i)
			{
			
				array_push($temp, $row);
			}
		}
		
		
		
		$f=$temp[0];
		$date=$f->date;
		$ip=$f->ip;
 $data_temp['Submit date']=$date;
 $data_temp['Ip']=$ip;
  
 
 $ttt=count($temp);
 
// var_dump($temp);
		for($h=0; $h < $m ; $h++)
		{		
			
			for($g=0; $g < $ttt ; $g++)
			{			
				$t = $temp[$g];
				if($t->element_label==$sorted_labels_id[$h])
				{
					if(strpos($t->element_value,"*@@url@@*"))
					{
						$new_file=str_replace("*@@url@@*",'', $t->element_value);
						$new_filename=explode('/', $new_file);
						$data_temp[$label_titles[$h]]=$new_file;
					}
					else
						if(strpos($t->element_value,"***br***"))					
						{						
						$data_temp[$label_titles[$h]]= substr(str_replace("***br***",', ', $t->element_value), 0, -2);		
						}					
						else					
						if(strpos($t->element_value,"***map***"))		
						{								
						$data_temp[$label_titles[$h]]= 'Longitude:'.substr(str_replace("***map***",', Latitude:', $t->element_value), 0, -2);		
						}
						else
								if(strpos($t->element_value,"***star_rating***"))
								{	
								    $element = str_replace("***star_rating***",'', $t->element_value);
									$element = explode("***", $element);
								
									$data_temp[$label_titles[$h]]= ' '.$element[1].'/'.$element[0];
									
								
								}
                        else
								if(strpos($t->element_value,"***grading***"))
								{	
								    $element = str_replace("***grading***",'', $t->element_value);
									
								    $grading = explode(":",$element);                        
									$items_count = sizeof($grading)-1;
									$items = "";
									$total = "";
									for($k=0;$k<$items_count/2;$k++)
									{
									$items .= $grading[$items_count/2+$k].": ".$grading[$k].", ";
									$total += $grading[$k];
									}
									$items .="Total: ".$total;
									
									
									$data_temp[$label_titles[$h]]= $items;
									
								
								}
								else
								if(strpos($t->element_value,"***matrix***"))
								{	
								    $element = str_replace("***matrix***",'', $t->element_value);

									$matrix_value=explode('***', $element);
	                        $matrix_value = array_slice($matrix_value,0, count($matrix_value)-1);   
										
															
							$mat_rows=$matrix_value[0];
							$mat_columns=$matrix_value[$mat_rows+1];
										
							$matrix="";
							
	    					
							$aaa=Array();
							   $var_checkbox=1;
							   $selected_value="";
							   $selected_value_yes="";
							   $selected_value_no="";
							   
							for( $k=1;$k<=$mat_rows;$k++){
						
							  if($matrix_value[$mat_rows+$mat_columns+2]=="radio"){
							  
							  if($matrix_value[$mat_rows+$mat_columns+2+$k]==0){
								    $checked="0";
									$aaa[1]="";
									}
								    else
									$aaa=explode("_",$matrix_value[$mat_rows+$mat_columns+2+$k]);
 
									
							        for( $l=1;$l<=$mat_columns;$l++){
										if($aaa[1]==$l)
									    $checked='1';
									    else
									    $checked="0";
																				
							        $matrix .= '['.$matrix_value[$k].','.$matrix_value[$mat_rows+1+$l].']='.$checked."; ";
                                    
							        }
									
							    } 
								else{
								if($matrix_value[$mat_rows+$mat_columns+2]=="checkbox")
								{
								                          
							        for( $l=1;$l<=$mat_columns;$l++){
									if( $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox]==1)
									 $checked ='1';
									 else
									 $checked ='0';
									
							        $matrix .= '['.$matrix_value[$k].','.$matrix_value[$mat_rows+1+$l].']='.$checked."; ";
									
								 $var_checkbox++;
								}
								
								}
								else
								{
								if($matrix_value[$mat_rows+$mat_columns+2]=="text")
								{
								                          
							        for( $l=1;$l<=$mat_columns;$l++){
									 $text_value = $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox];
									
                                    								
							        $matrix .='['.$matrix_value[$k].','.$matrix_value[$mat_rows+1+$l].']='.$text_value."; ";
								 $var_checkbox++;
								}
								
								}
								else{
								  
								 for( $l=1;$l<=$mat_columns;$l++){
								  $selected_text = $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox];
						
								 $matrix .='['.$matrix_value[$k].','.$matrix_value[$mat_rows+1+$l].']='.$selected_text."; ";
								 $var_checkbox++;
							  
								}
								}
								
								}
								
								}
							    
							}
							$data_temp[$label_titles[$h]]= $matrix;
							}							
							
						else				
						$data_temp[$label_titles[$h]]=$t->element_value;

						

								

				}
			}
			
			
		}
		$query = "SELECT * FROM #__formmaker_sessions where group_id=".$f->group_id;
		$db->setQuery( $query);	$paypal_info = $db->loadObject();
		if($db->getErrorNum()){		echo $db->stderr();		return false;	}
		if($paypal_info){
			foreach($paypal_info_fields as $paypal_info_field)
			$data_temp['PAYPAL_'.$paypal_info_field]=$paypal_info->$paypal_info_field;
		}  

		$data[]=$data_temp;
 }
// var_dump($data);
 

  // file name for download
	$filename = $title."_" . date('Ymd') . ".xml";

  header("Content-Disposition: attachment; filename=\"$filename\"");
  header("Content-Type:text/xml,  charset=utf-8");

 
  $flag = false;
echo '<?xml version="1.0" encoding="utf-8" ?> 
  <form title="'.$title.'">';
 
  foreach ($data as $key1 => $value1){
  echo  '<submition>';
	 
  foreach ($value1 as $key => $value){
  echo  '<field title="'.$key.'">';
		echo   '<![CDATA['.$value."]]>";
 echo  '</field>';
  }  
  
   echo  '</submition>';
  }
	
	  echo '';
echo ' </form>
';

}

function cleanData(&$str)
{
	$str = preg_replace("/\t/", "\\t", $str);
	$str = preg_replace("/\r?\n/", "\\n", $str);
	if(strstr($str, '"')) $str = '"' . str_replace('"', '""', $str) . '"';
}



function form_layout()
{
	$db		= JFactory::getDBO();
	$cid 	= JRequest::getVar('cid', array(0), '', 'array');
	JArrayHelper::toInteger($cid, array(0));
	$id 	= $cid[0];
	$row = JTable::getInstance('formmaker', 'Table');
	// load the row from the db table
	$row->load( $id);
	
	$ids = array();
	$types = array();
	$labels = array();
	
	
	$fields=explode('*:*new_field*:*',$row->form_fields);
	$fields 	= array_slice($fields,0, count($fields)-1);   
	foreach($fields as $field)
	{
		$temp=explode('*:*id*:*',$field);
		array_push($ids, $temp[0]);
		$temp=explode('*:*type*:*',$temp[1]);
		array_push($types, $temp[0]);
		$temp=explode('*:*w_field_label*:*',$temp[1]);
		array_push($labels, $temp[0]);
	}
	
	
	$fields = array('ids' => $ids, 'types' => $types, 'labels' => $labels);
	
	HTML_contact::form_layout($row, $fields);
}


function form_options(){

	$db		= JFactory::getDBO();
	$cid 	= JRequest::getVar('cid', array(0), '', 'array');
	JArrayHelper::toInteger($cid, array(0));

	$id 	= $cid[0];
	$row = JTable::getInstance('formmaker', 'Table');

	// load the row from the db table
	$row->load( $id);

	
	$query = "SELECT * FROM #__formmaker_themes WHERE css LIKE '%.wdform_section%' ORDER BY title";
	$db->setQuery( $query);
	$themes_new = $db->loadObjectList();
	if($db->getErrorNum()){
		echo $db->stderr();
		return false;
	}
	
	$query = "SELECT * FROM #__formmaker_themes WHERE css NOT LIKE '%.wdform_section%' ORDER BY title";
	$db->setQuery( $query);
	$themes_old = $db->loadObjectList();
	if($db->getErrorNum()){
		echo $db->stderr();
		return false;
	}

	$old = false;
	
	if(isset($row->form))
		$old = true;
	

	
	if($old == false || ($old == true && $row->form=='')) 
	{
		HTML_contact::form_options($row, $themes_new);
	}
	else
		HTML_contact::form_options_old($row, $themes_old);


}


function paypal_info(){
	$id 	= JRequest::getVar('id');
    $db = JFactory::getDBO();
	$query = "SELECT * FROM #__formmaker_sessions where group_id=".$id;
	$db->setQuery( $query);
	$row = $db->loadObject();
	if($db->getErrorNum()){
		echo $db->stderr();
		return false;
	}

	HTML_contact::paypal_info($row);

}

function show_map(){

	$long 	= JRequest::getVar('long');
	$lat 	= JRequest::getVar('lat');

	HTML_contact::show_map($long,$lat);

}


function show_matrix(){

	$matrix_params 	= JRequest::getVar('matrix_params');
    
	HTML_contact::show_matrix($matrix_params);

}

function country_list(){

	$id 	= JRequest::getVar('field_id');

	HTML_contact::country_list($id);

}

function product_option(){

	$id 	= JRequest::getVar('field_id');
	$property_id= JRequest::getVar('property_id');
	HTML_contact::product_option($id,$property_id);

}


//////////////////////////////////////////////////////////////
function gotoedit(){
	$mainframe = JFactory::getApplication();

	$id 	= JRequest::getVar('id');

	$msg ="The form was saved successfully.";
	$link ='index.php?option=com_formmaker&task=edit&cid[]='.$id;

	$mainframe->redirect($link, $msg, 'message');

}

function showredirect(){
	$mainframe = JFactory::getApplication();

	$link = 'index.php?option=com_formmaker&task=forms';

	$mainframe->redirect($link);

}

function add(){


    $db = JFactory::getDBO();
	$query = "SELECT * FROM #__formmaker_themes ORDER BY title";
	$db->setQuery( $query);
	$themes = $db->loadObjectList();
	if($db->getErrorNum()){
		echo $db->stderr();
		return false;
	}
// display function

	HTML_contact::add($themes);
}

function show_submits(){

	$mainframe = JFactory::getApplication();
    $db = JFactory::getDBO();
	$query = "SELECT id, title FROM #__formmaker order by title";
	$db->setQuery( $query);
	$forms = $db->loadObjectList();
	if($db->getErrorNum()){
		echo $db->stderr();
		return false;
	}
//	$form_id = JRequest::getVar('form_id');

	$option='com_formmaker';
	$task	= JRequest::getCmd('task'); 
	$form_id= $mainframe-> getUserStateFromRequest( $option.'form_id', 'form_id','id','cmd' );
	
	if($form_id){
	
	$query = "SELECT id FROM #__formmaker where id=".$form_id;
	$db->setQuery( $query);
	$exists = $db->LoadResult();
	
	if(!$exists)
		$form_id=0;
	}
	
	$filter_order= $mainframe-> getUserStateFromRequest( $option.'filter_order2', 'filter_order2','id','cmd' );
	$filter_order_Dir= $mainframe-> getUserStateFromRequest( $option.'filter_order_Dir2', 'filter_order_Dir2','','word' );
	$search_submits = $mainframe-> getUserStateFromRequest( $option.'search_submits', 'search_submits','','string' );
	$search_submits = JString::strtolower( $search_submits );
	
	$ip_search = $mainframe-> getUserStateFromRequest( $option.'ip_search', 'ip_search','','string' );
	$ip_search = JString::strtolower( $ip_search );
	
	$limit= $mainframe-> getUserStateFromRequest('global.list.limit', 'limit', $mainframe->getCfg('list_limit'), 'int');
	$limitstart= $mainframe-> getUserStateFromRequest($option.'.limitstart', 'limitstart', 0, 'int');

	$where = array();
	$lists['startdate']= JRequest::getVar('startdate', "");
	$lists['enddate']= JRequest::getVar('enddate', "");
	$lists['hide_label_list']= JRequest::getVar('hide_label_list', "");
	
	if ( $search_submits ) {
		$where[] = 'element_label LIKE "%'.$db->escape($search_submits).'%"';
	}	
	
	if ( $ip_search ) {
		$where[] = 'ip LIKE "%'.$db->escape($ip_search).'%"';
	}	
	
	if($lists['startdate']!='')
		$where[] ="  `date`>='".$lists['startdate']." 00:00:00' ";
	if($lists['enddate']!='')
		$where[] ="  `date`<='".$lists['enddate']." 23:59:59' ";
	
	if ($form_id=='')
		if($forms)
		$form_id=$forms[0]->id;
	
	$where[] = 'form_id="'.$form_id.'"';

	$where 		= ( count( $where ) ? ' WHERE ' . implode( ' AND ', $where ) : '' );

	$orderby 	= ' ';
	if ($filter_order == 'id' or $filter_order == 'title' or $filter_order == 'mail')
	{
		$orderby 	= ' ORDER BY `date` desc';
	} 
	else 
		if(!strpos($filter_order,"_field")) 
		{
			$orderby 	= ' ORDER BY '.$filter_order .' '. $filter_order_Dir .'';
		}	
	
	$query = "SELECT * FROM #__formmaker_submits". $where;
	$db->setQuery( $query);
	$rows = $db->loadObjectList();
	if($db->getErrorNum()){echo $db->stderr();return false;}
	
	$where_labels=array();
	$n=count($rows);
	$labels= array();
	for($i=0; $i < $n ; $i++)
	{
		$row = &$rows[$i];
		if(!in_array($row->element_label, $labels))
		{
			array_push($labels, $row->element_label);
		}
	}
	
	$query = "SELECT id FROM #__formmaker_submits WHERE form_id=".$form_id." and element_label=0";
	$db->setQuery( $query);
	$ispaypal = $db->loadResult();
	if($db->getErrorNum()){echo $db->stderr();return false;}
	
	$sorted_labels_type= array();
	$sorted_labels_id= array();
	$sorted_labels= array();
	$label_titles=array();
	if($labels)
	{
		
		$label_id= array();
		$label_order= array();
		$label_order_original= array();
		$label_type= array();
		
		$this_form = JTable::getInstance('formmaker', 'Table');
		$this_form->load( $form_id);
		
		if(strpos($this_form->label_order, 'type_paypal_'))
		{
			$this_form->label_order=$this_form->label_order."item_total#**id**#Item Total#**label**#type_paypal_payment_total#****#total#**id**#Total#**label**#type_paypal_payment_total#****#0#**id**#Payment Status#**label**#type_paypal_payment_status#****#";
		}
	
		$label_all	= explode('#****#',$this_form->label_order);
		$label_all 	= array_slice($label_all,0, count($label_all)-1);   
		
		
		
		foreach($label_all as $key => $label_each) 
		{
			$label_id_each=explode('#**id**#',$label_each);
			array_push($label_id, $label_id_each[0]);
			
			$label_order_each=explode('#**label**#', $label_id_each[1]);
			
			array_push($label_order_original, $label_order_each[0]);
			
			$ptn = "/[^a-zA-Z0-9_]/";
			$rpltxt = "";
			$label_temp=preg_replace($ptn, $rpltxt, $label_order_each[0]);
			array_push($label_order, $label_temp);
			
			array_push($label_type, $label_order_each[1]);
		}
		
		foreach($label_id as $key => $label) 
			if(in_array($label, $labels))
			{
				array_push($sorted_labels_type, $label_type[$key]);
				array_push($sorted_labels, $label_order[$key]);
				array_push($sorted_labels_id, $label);
				array_push($label_titles, $label_order_original[$key]);
				$search_temp = $mainframe-> getUserStateFromRequest( $option.$form_id.'_'.$label.'_search', $form_id.'_'.$label.'_search','','string' );
				$search_temp = JString::strtolower( $search_temp );
				$lists[$form_id.'_'.$label.'_search']	 = $search_temp;
				
				if ( $search_temp ) {
					$where_labels[] = '(group_id in (SELECT group_id FROM #__formmaker_submits WHERE element_label="'.$label.'" AND element_value LIKE "%'.$db->escape($search_temp).'%"))';
				}	

			}
	}
	
	$where_labels 		= ( count( $where_labels ) ? ' ' . implode( ' AND ', $where_labels ) : '' );
	if($where_labels)
		$where=  $where.' AND '.$where_labels;

	$rows_ord = array();
	if(strpos($filter_order,"_field"))
	{
		
		$query = "insert into #__formmaker_submits (form_id,	element_label, element_value, group_id,`date`,ip) select $form_id,'".str_replace("_field","",$filter_order)."', '', group_id,`date`,ip from  #__formmaker_submits where `form_id`=$form_id and group_id not in (select group_id from #__formmaker_submits where `form_id`=$form_id and element_label='".str_replace("_field","",$filter_order)."' group by  group_id) group by group_id";
	
		$db->setQuery( $query);
		$db->query();
		if($db->getErrorNum()){	echo $db->stderr();	return false;}
		
		$query = "SELECT group_id, date, ip FROM #__formmaker_submits ". $where." and element_label='".str_replace("_field","",$filter_order)."' order by element_value ".$filter_order_Dir;
		//echo $query;
		$db->setQuery( $query);
		$rows_ord = $db->loadObjectList();
		if($db->getErrorNum()){	echo $db->stderr();	return false;}
	
	}

	$query = 'SELECT group_id, date, ip FROM #__formmaker_submits'. $where.' group by group_id'. $orderby;
	$db->setQuery( $query );
	$group_ids=$db->loadObjectList();
	$total = count($group_ids);
	
	
	$query = 'SELECT count(distinct group_id) FROM #__formmaker_submits where form_id ="'.$form_id.'"';
	$db->setQuery( $query );
	$total_entries=$db->LoadResult();
	
	if(count($rows_ord)!=0){
	$group_ids=$rows_ord;
	$total = count($rows_ord);
	
	$query = "SELECT group_id, date, ip FROM #__formmaker_submits ". $where." and element_label='".str_replace("_field","",$filter_order)."' order by element_value ".$filter_order_Dir." limit $limitstart, $limit ";
	$db->setQuery( $query);
	$rows_ord = $db->loadObjectList();

	if($db->getErrorNum()){

		echo $db->stderr();

		return false;

	}
	
	
	
	
	
	}
	jimport('joomla.html.pagination');
	$pageNav = new JPagination( $total, $limitstart, $limit );	
	
	
	
	$where2 = array();
	
	for($i=$pageNav->limitstart; $i<$pageNav->limitstart+$pageNav->limit; $i++)
	{
		if($i<$total)
$where2 [] ="group_id='".$group_ids[$i]->group_id."'";
 
	}
	$where2 = ( count( $where2 ) ? ' AND ( ' . implode( ' OR ', $where2 ).' )' : '' );
	$where3=$where;
	$where=$where.$where2;
	$query = "SELECT * FROM #__formmaker_submits ". $where.$orderby.'';
	$db->setQuery( $query);
	$rows = $db->loadObjectList();
	if($db->getErrorNum()){

		echo $db->stderr();

		return false;

	
	}
	
	$query = 'SELECT views FROM #__formmaker_views WHERE form_id="'.$form_id.'"'	;
	$db->setQuery( $query );
	$total_views = $db->loadResult();	
	

	$lists['order_Dir']	= $filter_order_Dir;

	$lists['order']		= $filter_order;	



	// search filter	

$lists['search_submits']= $search_submits;	
$lists['ip_search']=$ip_search;

	if(count($rows_ord)==0)
		$rows_ord=$rows;
    // display function
	

	HTML_contact::show_submits($rows, $forms, $lists, $pageNav, $sorted_labels, $label_titles, $rows_ord, $filter_order_Dir,$form_id, $sorted_labels_id, $sorted_labels_type, $total_entries, $total_views,$where, $where3);

}

function show(){



	$mainframe = JFactory::getApplication();
	
	
    $db = JFactory::getDBO();
	$option='com_formmaker';
	$filter_order= $mainframe-> getUserStateFromRequest( $option.'filter_order', 'filter_order','id','cmd' );
	

	$filter_order_Dir= $mainframe-> getUserStateFromRequest( $option.'filter_order_Dir', 'filter_order_Dir','','word' );
	$filter_state = $mainframe->getUserStateFromRequest( $option.'filter_state', 'filter_state', '','word' );
	$search = $mainframe-> getUserStateFromRequest( $option.'search', 'search','','string' );
	
	
	$search = JString::strtolower( $search );
	$limit= $mainframe-> getUserStateFromRequest('global.list.limit', 'limit', $mainframe->getCfg('list_limit'), 'int');
	$limitstart= $mainframe-> getUserStateFromRequest($option.'.limitstart', 'limitstart', 0, 'int');

	$where = array();
	if ( $search ) {

		$where[] = 'title LIKE "%'.$db->escape($search).'%"';

	}	


	$where 		= ( count( $where ) ? ' WHERE ' . implode( ' AND ', $where ) : '' );

	if ($filter_order == 'id' or $filter_order == 'group_id' or $filter_order == 'date' or $filter_order == 'ip'){

		$orderby 	= ' ORDER BY id';

	} else {

		$orderby 	= ' ORDER BY '. 

         $filter_order .' '. $filter_order_Dir .', id';

	}	

	

	// get the total number of records

	$query = 'SELECT COUNT(*)'

	. ' FROM #__formmaker'

	. $where

	;
	$db->setQuery( $query );

	$total = $db->loadResult();



	jimport('joomla.html.pagination');

	$pageNav = new JPagination( $total, $limitstart, $limit );	

	$query = "SELECT * FROM #__formmaker". $where. $orderby;

	$db->setQuery( $query, $pageNav->limitstart, $pageNav->limit );

	$rows = $db->loadObjectList();

	if($db->getErrorNum()){

		echo $db->stderr();

		return false;

	}



	// table ordering

	$lists['order_Dir']	= $filter_order_Dir;

	$lists['order']		= $filter_order;	



	// search filter	

        $lists['search']= $search;	

	

    // display function

	HTML_contact::show($rows, $pageNav, $lists);

}

function show_themes(){

	$mainframe = JFactory::getApplication();
	$option='com_formmaker';
	
    $db = JFactory::getDBO();
	
	$filter_order= $mainframe-> getUserStateFromRequest( $option.'filter_order_themes', 'filter_order_themes','id','cmd' );
	$filter_order_Dir= $mainframe-> getUserStateFromRequest( $option.'filter_order_Dir_themes', 'filter_order_Dir_themes','desc','word' );
	$filter_state = $mainframe-> getUserStateFromRequest( $option.'filter_state', 'filter_state', '','word' );
	$search_theme = $mainframe-> getUserStateFromRequest( $option.'search_theme', 'search_theme','','string' );
	$search_theme = JString::strtolower( $search_theme );
	$limit= $mainframe-> getUserStateFromRequest('global.list.limit', 'limit', $mainframe->getCfg('list_limit'), 'int');
	$limitstart= $mainframe-> getUserStateFromRequest($option.'.limitstart', 'limitstart', 0, 'int');
	$where = array();

	if ( $search_theme ) {
		$where[] = '#__formmaker_themes.title LIKE "%'.$db->escape($search_theme).'%"';
	}	
	
	$where 		= ( count( $where ) ? ' WHERE ' . implode( ' AND ', $where ) : '' );
	if ($filter_order == 'id'){
		$orderby 	= ' ORDER BY id '.$filter_order_Dir;
	} else {
		$orderby 	= ' ORDER BY '. 
         $filter_order .' '. $filter_order_Dir .', id';
	}	
	
	// get the total number of records
	$query = 'SELECT COUNT(*)'. ' FROM #__formmaker_themes'. $where;
	$db->setQuery( $query );
	$total = $db->loadResult();

	jimport('joomla.html.pagination');
	$pageNav = new JPagination( $total, $limitstart, $limit );	
	
	$query = "SELECT * FROM #__formmaker_themes". $where. $orderby;
	$db->setQuery( $query, $pageNav->limitstart, $pageNav->limit );
	$rows = $db->loadObjectList();
	if($db->getErrorNum()){		echo $db->stderr();		return false;	}

	// table ordering

	$lists['order_Dir']	= $filter_order_Dir;
	$lists['order']		= $filter_order;	

	// search filter	
        $lists['search_theme']= $search_theme;	

    // display function

	HTML_contact::show_themes($rows, $pageNav, $lists);

}

function preview_formmaker()
{
	$getparams=JRequest::get('get');
	
    $db = JFactory::getDBO();
	
	$query = "SELECT css FROM #__formmaker_themes WHERE id=".$getparams['theme'];
	$db->setQuery( $query);
	$css = $db->loadResult();
	if($db->getErrorNum()){		$css='';	}

	HTML_contact::preview_formmaker($css);
}

function setdefault()
{
	$mainframe = JFactory::getApplication();
	$cid	= JRequest::getVar( 'cid', array(), 'post', 'array' );
	JArrayHelper::toInteger($cid);
	
	if (isset($cid[0]) && $cid[0]) 
		$id = $cid[0];
	else 
	{
		$mainframe->redirect(  'index.php?option=com_formmaker&task=themes',JText::_('No Items Selected'), 'message' );
		return false;
	}
	
	$db = JFactory::getDBO();

	// Clear home field for all other items
	$query = 'UPDATE #__formmaker_themes SET `default` = 0 WHERE 1';
	$db->setQuery( $query );
	if ( !$db->query() ) {
		$msg =$db->getErrorMsg();
		echo $msg;
		return false;
	}

	// Set the given item to home
	$query = 'UPDATE #__formmaker_themes SET `default` = 1 WHERE id = '.(int) $id;
	$db->setQuery( $query );
	if ( !$db->query() ) {
		$msg = $db->getErrorMsg();
		return false;
	}
		
	$msg = JText::_( 'Default Theme Seted' );
	$mainframe->redirect( 'index.php?option=com_formmaker&task=themes' ,$msg, 'message');
}

function add_themes(){

	//$lists['published'] = JHTML::_('select.booleanlist', 'published' , 'class="inputbox"', 1);
		
	$db		= JFactory::getDBO();
	$query = "SELECT * FROM #__formmaker_themes where `default`=1";
	$db->setQuery($query);
	$def_theme = $db->loadObject();
// display function
		
	HTML_contact::add_themes($def_theme);
}

function edit_themes(){
	$db		= JFactory::getDBO();
	$cid 	= JRequest::getVar('cid', array(0), '', 'array');
	JArrayHelper::toInteger($cid, array(0));

	$id 	= $cid[0];
	$row = JTable::getInstance('formmaker_themes', 'Table');
	// load the row from the db table
	$row->load( $id);
	
	// display function 
	HTML_contact::edit_themes( $row);
}

function remove_themes(){
	$mainframe = JFactory::getApplication();
  // Initialize variables	
  $db = JFactory::getDBO();
  // Define cid array variable
  $cid = JRequest::getVar( 'cid' , array() , '' , 'array' );
  // Make sure cid array variable content integer format
  JArrayHelper::toInteger($cid);
  $query = 'SELECT id FROM #__formmaker_themes WHERE `default`=1 LIMIT 1';
  $db->setQuery( $query );
  $def = $db->loadResult();
  if($db->getErrorNum()){
	  echo $db->stderr();
	  return false;
  }
  $msg='';
  $k=array_search($def, $cid);
  if ($k>0)
  {
	  $cid[$k]=0;
	  $msg="You can't delete default theme";
  }
  
  if ($cid[0]==$def)
  {
	  $cid[0]=0;
	  $msg="You can't delete default theme";
  }
  
  // If any item selected
  if (count( $cid )) {
    // Prepare sql statement, if cid array more than one, 
    // will be "cid1, cid2, ..."
    $cids = implode( ',', $cid );
    // Create sql statement

    $query = 'DELETE FROM #__formmaker_themes'.' WHERE id IN ( '. $cids .' )';
    // Execute query
    $db->setQuery( $query );
    if (!$db->query()) {
      echo "<script> alert('".$db->getErrorMsg(true)."'); 
      window.history.go(-1); </script>\n";
    }
	
  }
  // After all, redirect again to frontpage
  if($msg)
  $mainframe->redirect( "index.php?option=com_formmaker&task=themes",  $msg, 'message');
  else
  $mainframe->redirect( "index.php?option=com_formmaker&task=themes");
}




function save_as_copy(){
	$old = false;
	$mainframe = JFactory::getApplication();
	$cid 	= JRequest::getVar('cid', array(0), '', 'array');
	JArrayHelper::toInteger($cid, array(0));
	$id 	= $cid[0];
	$row = JTable::getInstance('formmaker', 'Table');
	// load the row from the db table
	$row->load( $id);
	if(!$row->bind(JRequest::get('post')))
	{
		JError::raiseError(500, $row->getError() );
	}
	

	if(isset($row->form)) 
		$old = true;

	$fid=$row->id;
	if($fid && ($old == true && $row->form!=''))
		$row->form = JRequest::getVar( 'form', '','post', 'string', JREQUEST_ALLOWRAW );
	else
	{
		$row->form_fields = JRequest::getVar( 'form_fields', '','post', 'string', JREQUEST_ALLOWRAW );
	}
	
	$row->id='';
	$new=true;
	
	$row->form_front = JRequest::getVar( 'form_front', '','post', 'string', JREQUEST_ALLOWRAW );
	$fid = JRequest::getVar( 'id',0 );
	if(!$row->store()){
		JError::raiseError(500, $row->getError() );
	}
	
	if($new)
	{
		$db = JFactory::getDBO();
		$db->setQuery("INSERT INTO #__formmaker_views (form_id, views) VALUES('".$row->id."', 0)" ); 
		$db->query();
		if ($db->getErrorNum())
		{
			echo $db->stderr();
			return false;
		}
	}
		$msg = 'The form has been saved successfully.';
		$link = 'index.php?option=com_formmaker';
		$mainframe->redirect($link, $msg, 'message');
		break;
		
}

function save($task){

    $old = false;
	$mainframe = JFactory::getApplication();
	$row = JTable::getInstance('formmaker', 'Table');

	if(!$row->bind(JRequest::get('post')))
	{
		JError::raiseError(500, $row->getError() );
	}
	$new=(!isset($row->id));
	
	if(isset($row->form)) 
		$old = true;

	$fid=$row->id;
	if($fid && ($old == true && $row->form!=''))
		$row->form = JRequest::getVar( 'form', '','post', 'string', JREQUEST_ALLOWRAW );
	else
	{
		$row->form_fields = JRequest::getVar( 'form_fields', '','post', 'string', JREQUEST_ALLOWRAW );
	}
	
	$row->form_front = JRequest::getVar( 'form_front', '','post', 'string', JREQUEST_ALLOWRAW );

	if(!$row->store()){
		JError::raiseError(500, $row->getError() );
	}
	
	if($new)
	{
		$db = JFactory::getDBO();
		$db->setQuery("INSERT INTO #__formmaker_views (form_id, views) VALUES('".$row->id."', 0)" ); 
		$db->query();
		if ($db->getErrorNum())
		{
			echo $db->stderr();
			return false;
		}
	}
	
	
	switch ($task)
	{
		case 'apply':
		HTML_contact::forchrome($row->id);
		break;
		case 'save_and_new':
		$msg = 'The form has been saved successfully.';
		$link = 'index.php?option=com_formmaker&task=add';
		$mainframe->redirect($link, $msg, 'message');
		break;
		
		case 'save':
		$msg = 'The form has been saved successfully.';
		$link = 'index.php?option=com_formmaker';
		$mainframe->redirect($link, $msg, 'message');
		break;
		
		case 'return_id':
			return $row->id;
		break;
		default:
		break;
	}
}

function save_themes($task){

	$mainframe = JFactory::getApplication();
	$row = JTable::getInstance('formmaker_themes', 'Table');
	if(!$row->bind(JRequest::get('post')))
	{
		JError::raiseError(500, $row->getError() );
	}

	if(!$row->store()){
		JError::raiseError(500, $row->getError() );
	}

	switch ($task)

	{
		case 'apply_themes':
		$msg ='Theme has been saved successfully.';
		$link ='index.php?option=com_formmaker&task=edit_themes&cid[]='.$row->id;
		break;

		default:
		$msg = 'Theme has been saved successfully.';
		$link ='index.php?option=com_formmaker&task=themes';
		break;
	}
	
	$mainframe->redirect($link, $msg, 'message');

}

function save_form_options($task){

	$mainframe = JFactory::getApplication();
	$row = JTable::getInstance('formmaker', 'Table');
	if(!$row->bind(JRequest::get('post')))
	{
		JError::raiseError(500, $row->getError() );
	}
	
	if($row->mail_from=="other")
		$row->mail_from=JRequest::getVar( 'mail_from_other');
	if($row->reply_to=="other")
		$row->reply_to=JRequest::getVar( 'reply_to_other');

	$row->script_mail = JRequest::getVar( 'script_mail', '','post', 'string', JREQUEST_ALLOWRAW );
	$row->submit_text = JRequest::getVar( 'submit_text', '','post', 'string', JREQUEST_ALLOWRAW );
	$row->script_mail_user = JRequest::getVar( 'script_mail_user', '','post', 'string', JREQUEST_ALLOWRAW );

	$row->send_to="";
	for($i=0; $i<20; $i++)
	{
		$send_to=JRequest::getVar( 'send_to'.$i);
		if(isset($send_to))
		{
			$row->send_to.='*'.$send_to.'*';
		}
	}

	
	if(!$row->store()){
		JError::raiseError(500, $row->getError() );
	}

	switch ($task)

	{
		case 'apply_form_options':
		$msg ='Form options have been saved successfully.';
		$link ='index.php?option=com_formmaker&task=form_options&cid[]='.$row->id;
		break;
		case 'save_form_options':
		default:
		$msg = 'Form options have been saved successfully.';
		$link ='index.php?option=com_formmaker&task=edit&cid[]='.$row->id;
		break;
	}
	
	$mainframe->redirect($link, $msg, 'message');

}

function save_form_layout($task){
	$mainframe = JFactory::getApplication();
	$row = JTable::getInstance('formmaker', 'Table');

	if(!$row->bind(JRequest::get('post')))
	{
		JError::raiseError(500, $row->getError() );
	}
	$row->custom_front = JRequest::getVar( 'custom_front', '','post', 'string', JREQUEST_ALLOWRAW );
	$autogen_layout=JRequest::getVar( 'autogen_layout');
	if(!isset($autogen_layout))
		$row->autogen_layout = 0;
		
	if(!$row->store()){
		JError::raiseError(500, $row->getError() );
	}
	switch ($task)
	{
		case 'apply_form_layout':
		$msg ='Form layout have been saved successfully.';
		$link ='index.php?option=com_formmaker&task=form_layout&cid[]='.$row->id;
		break;
		case 'save_form_layout':
		default:
		$msg = 'Form layout have been saved successfully.';
		$link ='index.php?option=com_formmaker&task=edit&cid[]='.$row->id;
		break;
	}

	$mainframe->redirect($link, $msg, 'message');
}

function edit(){
	$old = false;
	$db		= JFactory::getDBO();
	$cid 	= JRequest::getVar('cid', array(0), '', 'array');
	JArrayHelper::toInteger($cid, array(0));
	$id 	= $cid[0];
	$row = JTable::getInstance('formmaker', 'Table');
	// load the row from the db table
	$row->load( $id);
		
		$labels2= array();
		
		$label_id= array();
		$label_order_original= array();
		$label_type= array();
		
		$label_all	= explode('#****#',$row->label_order);
		$label_all 	= array_slice($label_all,0, count($label_all)-1);   

		foreach($label_all as $key => $label_each) 
		{
			$label_id_each=explode('#**id**#',$label_each);
			array_push($label_id, $label_id_each[0]);
			
			$label_oder_each=explode('#**label**#', $label_id_each[1]);
			array_push($label_order_original, addslashes($label_oder_each[0]));
			array_push($label_type, $label_oder_each[1]);
		
			
		}
		
	$labels2['id']='"'.implode('","',$label_id).'"';
	$labels2['label']='"'.implode('","',$label_order_original).'"';
	$labels2['type']='"'.implode('","',$label_type).'"';
	
	if(isset($row->form)) 
		$old = true;

	if($old == false || ($old == true && $row->form=='')) 
	{
	$ids = array();
	$types = array();
	$labels = array();
	$paramss = array();
	$fields=explode('*:*new_field*:*',$row->form_fields);
	$fields 	= array_slice($fields,0, count($fields)-1);   
	foreach($fields as $field)
	{
		$temp=explode('*:*id*:*',$field);
		array_push($ids, $temp[0]);
		$temp=explode('*:*type*:*',$temp[1]);
		array_push($types, $temp[0]);
		$temp=explode('*:*w_field_label*:*',$temp[1]);
		array_push($labels, $temp[0]);
		array_push($paramss, $temp[1]);
	}
	
	$form=$row->form_front;
	foreach($ids as $ids_key => $id)
	{	
		$label=$labels[$ids_key];
		$type=$types[$ids_key];
		$params=$paramss[$ids_key];
		if( strpos($form, '%'.$id.' - '.$label.'%'))
		{
			$rep='';
			$param=array();
			$param['attributes'] = '';
			switch($type)
			{
				case 'type_section_break':
				{
					$params_names=array('w_editor');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}
					$rep ='<div id="wdform_field'.$id.'" type="type_section_break" class="wdform_field_section_break"><span id="'.$id.'_element_labelform_id_temp" style="display: none;">custom_'.$id.'</span><div id="'.$id.'_element_sectionform_id_temp" align="left" class="wdform_section_break">'.$param['w_editor'].'</div></div>';
					break;
				}
				
				case 'type_editor':
				{
					$params_names=array('w_editor');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}
					
					$rep ='<div id="wdform_field'.$id.'" type="type_editor" class="wdform_field" style="display: table-cell;">'.$param['w_editor'].'</div><span id="'.$id.'_element_labelform_id_temp" style="display: none;">custom_'.$id.'</span>';
					break;
				}
				case 'type_text':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_first_val','w_title','w_required','w_unique');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$input_active = ($param['w_first_val']==$param['w_title'] ? "input_deactive" : "input_active");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
				
					$rep ='<div id="wdform_field'.$id.'" type="type_text" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_text" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_unique'].'" name="'.$id.'_uniqueform_id_temp" id="'.$id.'_uniqueform_id_temp"><input type="text" class="'.$input_active.'" id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp" value="'.$param['w_first_val'].'" title="'.$param['w_title'].'" onfocus="delete_value(&quot;'.$id.'_elementform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_elementform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_elementform_id_temp&quot;)" style="width: '.$param['w_size'].'px;" '.$param['attributes'].'></div></div>';
					break;
				}
				case 'type_number':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_first_val','w_title','w_required','w_unique','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$input_active = ($param['w_first_val']==$param['w_title'] ? "input_deactive" : "input_active");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
									
					$rep ='<div id="wdform_field'.$id.'" type="type_number" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp"  class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'"><input type="hidden" value="type_number" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_unique'].'" name="'.$id.'_uniqueform_id_temp" id="'.$id.'_uniqueform_id_temp"><input type="text" class="'.$input_active.'" id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp" value="'.$param['w_first_val'].'" title="'.$param['w_title'].'" onkeypress="return check_isnum(event)" onfocus="delete_value(&quot;'.$id.'_elementform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_elementform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_elementform_id_temp&quot;)" style="width: '.$param['w_size'].'px;" '.$param['attributes'].'></div></div>';
					break;
				}
				case 'type_password':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_required','w_unique','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$rep ='<div id="wdform_field'.$id.'" type="type_password" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp"  class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_password" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_unique'].'" name="'.$id.'_uniqueform_id_temp" id="'.$id.'_uniqueform_id_temp"><input type="password" id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp" style="width: '.$param['w_size'].'px;" '.$param['attributes'].'></div></div>';
					break;
				}
				case 'type_textarea':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size_w','w_size_h','w_first_val','w_title','w_required','w_unique','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}
					
					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$input_active = ($param['w_first_val']==$param['w_title'] ? "input_deactive" : "input_active");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
				
					$rep ='<div id="wdform_field'.$id.'" type="type_textarea" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display:'.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: table-cell;"><input type="hidden" value="type_textarea" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_unique'].'" name="'.$id.'_uniqueform_id_temp" id="'.$id.'_uniqueform_id_temp"><textarea class="'.$input_active.'" id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp" title="'.$param['w_title'].'"  onfocus="delete_value(&quot;'.$id.'_elementform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_elementform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_elementform_id_temp&quot;)" style="width: '.$param['w_size_w'].'px; height: '.$param['w_size_h'].'px;" '.$param['attributes'].'>'.$param['w_first_val'].'</textarea></div></div>';
					break;
				}
				case 'type_phone':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_first_val','w_title','w_mini_labels','w_required','w_unique', 'w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}
					
					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					
					$w_first_val = explode('***',$param['w_first_val']);
					$w_title = explode('***',$param['w_title']);
					$w_mini_labels = explode('***',$param['w_mini_labels']);
					
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$input_active = ($param['w_first_val']==$param['w_title'] ? "input_deactive" : "input_active");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
				
					$rep ='<div id="wdform_field'.$id.'" type="type_phone" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_phone" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_unique'].'" name="'.$id.'_uniqueform_id_temp" id="'.$id.'_uniqueform_id_temp"><div id="'.$id.'_table_name" style="display: table;"><div id="'.$id.'_tr_name1" style="display: table-row;"><div id="'.$id.'_td_name_input_first" style="display: table-cell;"><input type="text" class="'.$input_active.'" id="'.$id.'_element_firstform_id_temp" name="'.$id.'_element_firstform_id_temp" value="'.$w_first_val[0].'" title="'.$w_title[0].'" onfocus="delete_value(&quot;'.$id.'_element_firstform_id_temp&quot;)"onblur="return_value(&quot;'.$id.'_element_firstform_id_temp&quot;)"onchange="change_value(&quot;'.$id.'_element_firstform_id_temp&quot;)" onkeypress="return check_isnum(event)"style="width: 50px;" '.$param['attributes'].'><span class="wdform_line" style="margin: 0px 4px; padding: 0px;">-</span></div><div id="'.$id.'_td_name_input_last" style="display: table-cell;"><input type="text" class="'.$input_active.'" id="'.$id.'_element_lastform_id_temp" name="'.$id.'_element_lastform_id_temp" value="'.$w_first_val[1].'" title="'.$w_title[1].'" onfocus="delete_value(&quot;'.$id.'_element_lastform_id_temp&quot;)"onblur="return_value(&quot;'.$id.'_element_lastform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_element_lastform_id_temp&quot;)" onkeypress="return check_isnum(event)"style="width: '.$param['w_size'].'px;" '.$param['attributes'].'></div></div><div id="'.$id.'_tr_name2" style="display: table-row;"><div id="'.$id.'_td_name_label_first" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_area_code">'.$w_mini_labels[0].'</label></div><div id="'.$id.'_td_name_label_last" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_phone_number">'.$w_mini_labels[1].'</label></div></div></div></div></div>';
					break;
				}
				case 'type_name':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_first_val','w_title', 'w_mini_labels','w_size','w_name_format','w_required','w_unique', 'w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}
					
					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
				
					$w_first_val = explode('***',$param['w_first_val']);
					$w_title = explode('***',$param['w_title']);
					$w_mini_labels = explode('***',$param['w_mini_labels']);
					

					if($param['w_name_format']=='normal')
					{
						$w_name_format = '<div id="'.$id.'_td_name_input_first" style="display: table-cell;"><input type="text" class="'.($w_first_val[0]==$w_title[0] ? "input_deactive" : "input_active").'" id="'.$id.'_element_firstform_id_temp" name="'.$id.'_element_firstform_id_temp" value="'.$w_first_val[0].'" title="'.$w_title[0].'" onfocus="delete_value(&quot;'.$id.'_element_firstform_id_temp&quot;)"onblur="return_value(&quot;'.$id.'_element_firstform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_element_firstform_id_temp&quot;)" style="margin-right: 10px; width: '.$param['w_size'].'px;"'.$param['attributes'].'></div><div id="'.$id.'_td_name_input_last" style="display: table-cell;"><input type="text" class="'.($w_first_val[1]==$w_title[1] ? "input_deactive" : "input_active").'" id="'.$id.'_element_lastform_id_temp" name="'.$id.'_element_lastform_id_temp" value="'.$w_first_val[1].'" title="'.$w_title[1].'" onfocus="delete_value(&quot;'.$id.'_element_lastform_id_temp&quot;)"onblur="return_value(&quot;'.$id.'_element_lastform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_element_lastform_id_temp&quot;)" style="margin-right: 10px; width: '.$param['w_size'].'px;" '.$param['attributes'].'></div>';
						$w_name_format_mini_labels = '<div id="'.$id.'_tr_name2" style="display: table-row;"><div id="'.$id.'_td_name_label_first" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_first">'.$w_mini_labels[1].'</label></div><div id="'.$id.'_td_name_label_last" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_last">'.$w_mini_labels[2].'</label></div></div>';
					}
					else
					{
						$w_name_format = '<div id="'.$id.'_td_name_input_title" style="display: table-cell;"><input type="text" class="'.($w_first_val[0]==$w_title[0] ? "input_deactive" : "input_active").'" id="'.$id.'_element_titleform_id_temp" name="'.$id.'_element_titleform_id_temp" value="'.$w_first_val[0].'" title="'.$w_title[0].'" onfocus="delete_value(&quot;'.$id.'_element_titleform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_element_titleform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_element_titleform_id_temp&quot;)" style="margin: 0px 10px 0px 0px; width: 40px;"></div><div id="'.$id.'_td_name_input_first" style="display: table-cell;"><input type="text" class="'.($w_first_val[1]==$w_title[1] ? "input_deactive" : "input_active").'" id="'.$id.'_element_firstform_id_temp" name="'.$id.'_element_firstform_id_temp" value="'.$w_first_val[1].'" title="'.$w_title[1].'" onfocus="delete_value(&quot;'.$id.'_element_firstform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_element_firstform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_element_firstform_id_temp&quot;)" style="margin-right: 10px; width: '.$param['w_size'].'px;"></div><div id="'.$id.'_td_name_input_last" style="display: table-cell;"><input type="text" class="'.($w_first_val[2]==$w_title[2] ? "input_deactive" : "input_active").'" id="'.$id.'_element_lastform_id_temp" name="'.$id.'_element_lastform_id_temp" value="'.$w_first_val[2].'" title="'.$w_title[2].'" onfocus="delete_value(&quot;'.$id.'_element_lastform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_element_lastform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_element_lastform_id_temp&quot;)" style="margin-right: 10px; width: '.$param['w_size'].'px;"></div><div id="'.$id.'_td_name_input_middle" style="display: table-cell;"><input type="text" class="'.($w_first_val[3]==$w_title[3] ? "input_deactive" : "input_active").'" id="'.$id.'_element_middleform_id_temp" name="'.$id.'_element_middleform_id_temp" value="'.$w_first_val[3].'" title="'.$w_title[3].'" onfocus="delete_value(&quot;'.$id.'_element_middleform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_element_middleform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_element_middleform_id_temp&quot;)" style="width: '.$param['w_size'].'px;"></div>';
						$w_name_format_mini_labels ='<div id="'.$id.'_tr_name2" style="display: table-row;"><div id="'.$id.'_td_name_label_title" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_title">'.$w_mini_labels[0].'</label></div><div id="'.$id.'_td_name_label_first" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_first">'.$w_mini_labels[1].'</label></div><div id="'.$id.'_td_name_label_last" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_last">'.$w_mini_labels[2].'</label></div><div id="'.$id.'_td_name_label_middle" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_middle">'.$w_mini_labels[3].'</label></div></div>';
					}
		
					$rep ='<div id="wdform_field'.$id.'" type="type_name" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_name" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_unique'].'" name="'.$id.'_uniqueform_id_temp" id="'.$id.'_uniqueform_id_temp"><div id="'.$id.'_table_name" cellpadding="0" cellspacing="0" style="display: table;"><div id="'.$id.'_tr_name1" style="display: table-row;">'.$w_name_format.'    </div>'.$w_name_format_mini_labels.'   </div></div></div>';
					break;
				}
				
				case 'type_address':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_mini_labels','w_disabled_fields','w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					
					
					$w_mini_labels = explode('***',$param['w_mini_labels']);
					$w_disabled_fields = explode('***',$param['w_disabled_fields']);
					
					$hidden_inputs = '';

					$labels_for_id = array('street1', 'street2', 'city', 'state', 'postal', 'country');
					foreach($w_disabled_fields as $key=>$w_disabled_field)
					{
						if($w_disabled_field=='yes')
						$hidden_inputs .= '<input type="hidden" id="'.$id.'_'.$labels_for_id[$key].'form_id_temp" value="'.$w_mini_labels[$key].'" id_for_label="'.($id+$key).'">';
					
					}
					
										
					$address_fields ='';
					$g=0;
					if($w_disabled_fields[0]=='no')
					{
					$g+=2;
					$address_fields .= '<span style="float: left; width: 100%; padding-bottom: 8px; display: block;"><input type="text" id="'.$id.'_street1form_id_temp" name="'.$id.'_street1form_id_temp" onchange="change_value(&quot;'.$id.'_street1form_id_temp&quot;)" style="width: 100%;" '.$param['attributes'].'><label class="mini_label" id="'.$id.'_mini_label_street1" style="display: block;">'.$w_mini_labels[0].'</label></span>';
					}
					
					if($w_disabled_fields[1]=='no')
					{
					$g+=2;
					$address_fields .= '<span style="float: left; width: 100%; padding-bottom: 8px; display: block;"><input type="text" id="'.$id.'_street2form_id_temp" name="'.($id+1).'_street2form_id_temp" onchange="change_value(&quot;'.$id.'_street2form_id_temp&quot;)" style="width: 100%;" '.$param['attributes'].'><label class="mini_label" style="display: block;" id="'.$id.'_mini_label_street2">'.$w_mini_labels[1].'</label></span>';
					}
					
					if($w_disabled_fields[2]=='no')
					{
					$g++;
					$address_fields .= '<span style="float: left; width: 48%; padding-bottom: 8px;"><input type="text" id="'.$id.'_cityform_id_temp" name="'.($id+2).'_cityform_id_temp" onchange="change_value(&quot;'.$id.'_cityform_id_temp&quot;)" style="width: 100%;" '.$param['attributes'].'><label class="mini_label" style="display: block;" id="'.$id.'_mini_label_city">'.$w_mini_labels[2].'</label></span>';
					}
					if($w_disabled_fields[3]=='no')
					{
					$g++;
					

					$address_fields .= '<span style="float: '.(($g%2==0) ? 'right' : 'left').'; width: 48%; padding-bottom: 8px;"><input type="text" id="'.$id.'_stateform_id_temp" name="'.($id+3).'_stateform_id_temp" onchange="change_value(&quot;'.$id.'_stateform_id_temp&quot;)" style="width: 100%;" '.$param['attributes'].'><label class="mini_label" style="display: block;" id="'.$id.'_mini_label_state">'.$w_mini_labels[3].'</label></span>';
					}
					if($w_disabled_fields[4]=='no')
					{
					$g++;
					$address_fields .= '<span style="float: '.(($g%2==0) ? 'right' : 'left').'; width: 48%; padding-bottom: 8px;"><input type="text" id="'.$id.'_postalform_id_temp" name="'.($id+4).'_postalform_id_temp" onchange="change_value(&quot;'.$id.'_postalform_id_temp&quot;)" style="width: 100%;" '.$param['attributes'].'><label class="mini_label" style="display: block;" id="'.$id.'_mini_label_postal">'.$w_mini_labels[4].'</label></span>';
					}
					if($w_disabled_fields[5]=='no')
					{
					$g++;
					$address_fields .= '<span style="float: '.(($g%2==0) ? 'right' : 'left').'; width: 48%; padding-bottom: 8px;"><select type="text" id="'.$id.'_countryform_id_temp" name="'.($id+5).'_countryform_id_temp" onchange="change_value(&quot;'.$id.'_countryform_id_temp&quot;)" style="width: 100%;" '.$param['attributes'].'><option value=""></option><option value="Afghanistan">Afghanistan</option><option value="Albania">Albania</option><option value="Algeria">Algeria</option><option value="Andorra">Andorra</option><option value="Angola">Angola</option><option value="Antigua and Barbuda">Antigua and Barbuda</option><option value="Argentina">Argentina</option><option value="Armenia">Armenia</option><option value="Australia">Australia</option><option value="Austria">Austria</option><option value="Azerbaijan">Azerbaijan</option><option value="Bahamas">Bahamas</option><option value="Bahrain">Bahrain</option><option value="Bangladesh">Bangladesh</option><option value="Barbados">Barbados</option><option value="Belarus">Belarus</option><option value="Belgium">Belgium</option><option value="Belize">Belize</option><option value="Benin">Benin</option><option value="Bhutan">Bhutan</option><option value="Bolivia">Bolivia</option><option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option><option value="Botswana">Botswana</option><option value="Brazil">Brazil</option><option value="Brunei">Brunei</option><option value="Bulgaria">Bulgaria</option><option value="Burkina Faso">Burkina Faso</option><option value="Burundi">Burundi</option><option value="Cambodia">Cambodia</option><option value="Cameroon">Cameroon</option><option value="Canada">Canada</option><option value="Cape Verde">Cape Verde</option><option value="Central African Republic">Central African Republic</option><option value="Chad">Chad</option><option value="Chile">Chile</option><option value="China">China</option><option value="Colombi">Colombi</option><option value="Comoros">Comoros</option><option value="Congo (Brazzaville)">Congo (Brazzaville)</option><option value="Congo">Congo</option><option value="Costa Rica">Costa Rica</option><option value="Cote d\'Ivoire">Cote d\'Ivoire</option><option value="Croatia">Croatia</option><option value="Cuba">Cuba</option><option value="Cyprus">Cyprus</option><option value="Czech Republic">Czech Republic</option><option value="Denmark">Denmark</option><option value="Djibouti">Djibouti</option><option value="Dominica">Dominica</option><option value="Dominican Republic">Dominican Republic</option><option value="East Timor (Timor Timur)">East Timor (Timor Timur)</option><option value="Ecuador">Ecuador</option><option value="Egypt">Egypt</option><option value="El Salvador">El Salvador</option><option value="Equatorial Guinea">Equatorial Guinea</option><option value="Eritrea">Eritrea</option><option value="Estonia">Estonia</option><option value="Ethiopia">Ethiopia</option><option value="Fiji">Fiji</option><option value="Finland">Finland</option><option value="France">France</option><option value="Gabon">Gabon</option><option value="Gambia, The">Gambia, The</option><option value="Georgia">Georgia</option><option value="Germany">Germany</option><option value="Ghana">Ghana</option><option value="Greece">Greece</option><option value="Grenada">Grenada</option><option value="Guatemala">Guatemala</option><option value="Guinea">Guinea</option><option value="Guinea-Bissau">Guinea-Bissau</option><option value="Guyana">Guyana</option><option value="Haiti">Haiti</option><option value="Honduras">Honduras</option><option value="Hungary">Hungary</option><option value="Iceland">Iceland</option><option value="India">India</option><option value="Indonesia">Indonesia</option><option value="Iran">Iran</option><option value="Iraq">Iraq</option><option value="Ireland">Ireland</option><option value="Israel">Israel</option><option value="Italy">Italy</option><option value="Jamaica">Jamaica</option><option value="Japan">Japan</option><option value="Jordan">Jordan</option><option value="Kazakhstan">Kazakhstan</option><option value="Kenya">Kenya</option><option value="Kiribati">Kiribati</option><option value="Korea, North">Korea, North</option><option value="Korea, South">Korea, South</option><option value="Kuwait">Kuwait</option><option value="Kyrgyzstan">Kyrgyzstan</option><option value="Laos">Laos</option><option value="Latvia">Latvia</option><option value="Lebanon">Lebanon</option><option value="Lesotho">Lesotho</option><option value="Liberia">Liberia</option><option value="Libya">Libya</option><option value="Liechtenstein">Liechtenstein</option><option value="Lithuania">Lithuania</option><option value="Luxembourg">Luxembourg</option><option value="Macedonia">Macedonia</option><option value="Madagascar">Madagascar</option><option value="Malawi">Malawi</option><option value="Malaysia">Malaysia</option><option value="Maldives">Maldives</option><option value="Mali">Mali</option><option value="Malta">Malta</option><option value="Marshall Islands">Marshall Islands</option><option value="Mauritania">Mauritania</option><option value="Mauritius">Mauritius</option><option value="Mexico">Mexico</option><option value="Micronesia">Micronesia</option><option value="Moldova">Moldova</option><option value="Monaco">Monaco</option><option value="Mongolia">Mongolia</option><option value="Morocco">Morocco</option><option value="Mozambique">Mozambique</option><option value="Myanmar">Myanmar</option><option value="Namibia">Namibia</option><option value="Nauru">Nauru</option><option value="Nepa">Nepa</option><option value="Netherlands">Netherlands</option><option value="New Zealand">New Zealand</option><option value="Nicaragua">Nicaragua</option><option value="Niger">Niger</option><option value="Nigeria">Nigeria</option><option value="Norway">Norway</option><option value="Oman">Oman</option><option value="Pakistan">Pakistan</option><option value="Palau">Palau</option><option value="Panama">Panama</option><option value="Papua New Guinea">Papua New Guinea</option><option value="Paraguay">Paraguay</option><option value="Peru">Peru</option><option value="Philippines">Philippines</option><option value="Poland">Poland</option><option value="Portugal">Portugal</option><option value="Qatar">Qatar</option><option value="Romania">Romania</option><option value="Russia">Russia</option><option value="Rwanda">Rwanda</option><option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option><option value="Saint Lucia">Saint Lucia</option><option value="Saint Vincent">Saint Vincent</option><option value="Samoa">Samoa</option><option value="San Marino">San Marino</option><option value="Sao Tome and Principe">Sao Tome and Principe</option><option value="Saudi Arabia">Saudi Arabia</option><option value="Senegal">Senegal</option><option value="Serbia and Montenegro">Serbia and Montenegro</option><option value="Seychelles">Seychelles</option><option value="Sierra Leone">Sierra Leone</option><option value="Singapore">Singapore</option><option value="Slovakia">Slovakia</option><option value="Slovenia">Slovenia</option><option value="Solomon Islands">Solomon Islands</option><option value="Somalia">Somalia</option><option value="South Africa">South Africa</option><option value="Spain">Spain</option><option value="Sri Lanka">Sri Lanka</option><option value="Sudan">Sudan</option><option value="Suriname">Suriname</option><option value="Swaziland">Swaziland</option><option value="Sweden">Sweden</option><option value="Switzerland">Switzerland</option><option value="Syria">Syria</option><option value="Taiwan">Taiwan</option><option value="Tajikistan">Tajikistan</option><option value="Tanzania">Tanzania</option><option value="Thailand">Thailand</option><option value="Togo">Togo</option><option value="Tonga">Tonga</option><option value="Trinidad and Tobago">Trinidad and Tobago</option><option value="Tunisia">Tunisia</option><option value="Turkey">Turkey</option><option value="Turkmenistan">Turkmenistan</option><option value="Tuvalu">Tuvalu</option><option value="Uganda">Uganda</option><option value="Ukraine">Ukraine</option><option value="United Arab Emirates">United Arab Emirates</option><option value="United Kingdom">United Kingdom</option><option value="United States">United States</option><option value="Uruguay">Uruguay</option><option value="Uzbekistan">Uzbekistan</option><option value="Vanuatu">Vanuatu</option><option value="Vatican City">Vatican City</option><option value="Venezuela">Venezuela</option><option value="Vietnam">Vietnam</option><option value="Yemen">Yemen</option><option value="Zambia">Zambia</option><option value="Zimbabwe">Zimbabwe</option></select><label class="mini_label" style="display: block;" id="'.$id.'_mini_label_country">'.$w_mini_labels[5].'</span>';
					}				
				
					$rep ='<div id="wdform_field'.$id.'" type="type_address" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px; vertical-align:top;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_address" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" name="'.$id.'_disable_fieldsform_id_temp" id="'.$id.'_disable_fieldsform_id_temp" street1="'.$w_disabled_fields[0].'" street2="'.$w_disabled_fields[1].'" city="'.$w_disabled_fields[2].'" state="'.$w_disabled_fields[3].'" postal="'.$w_disabled_fields[4].'" country="'.$w_disabled_fields[5].'"><div id="'.$id.'_div_address" style="width: '.$param['w_size'].'px;">'.$address_fields.$hidden_inputs.'</div></div></div>';
					break;
				}
				
				case 'type_submitter_mail':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_first_val','w_title','w_required','w_unique', 'w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$input_active = ($param['w_first_val']==$param['w_title'] ? "input_deactive" : "input_active");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
				
					$rep ='<div id="wdform_field'.$id.'" type="type_submitter_mail" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_submitter_mail" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_unique'].'" name="'.$id.'_uniqueform_id_temp" id="'.$id.'_uniqueform_id_temp"><input type="text" class="'.$input_active.'" id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp" value="'.$param['w_first_val'].'" title="'.$param['w_title'].'" onfocus="delete_value(&quot;'.$id.'_elementform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_elementform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_elementform_id_temp&quot;)" style="width: '.$param['w_size'].'px;" '.$param['attributes'].'></div></div>';
					break;
				}
				case 'type_checkbox':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_flow','w_choices','w_choices_checked','w_rowcol', 'w_required','w_randomize','w_allow_other','w_allow_other_num','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$param['w_choices']	= explode('***',$param['w_choices']);
					$param['w_choices_checked']	= explode('***',$param['w_choices_checked']);
					
					foreach($param['w_choices_checked'] as $key => $choices_checked )
					{
						if($choices_checked=='true')
							$param['w_choices_checked'][$key]='checked="checked"';
						else
							$param['w_choices_checked'][$key]='';
					}
					
					$rep='<div id="wdform_field'.$id.'" type="type_checkbox" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_checkbox" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_randomize'].'" name="'.$id.'_randomizeform_id_temp" id="'.$id.'_randomizeform_id_temp"><input type="hidden" value="'.$param['w_allow_other'].'" name="'.$id.'_allow_otherform_id_temp" id="'.$id.'_allow_otherform_id_temp"><input type="hidden" value="'.$param['w_allow_other_num'].'" name="'.$id.'_allow_other_numform_id_temp" id="'.$id.'_allow_other_numform_id_temp"><input type="hidden" value="'.$param['w_rowcol'].'" name="'.$id.'_rowcol_numform_id_temp" id="'.$id.'_rowcol_numform_id_temp"><div style="display: table;"><div id="'.$id.'_table_little" style="display: table-row-group;" '.($param['w_flow']=='hor' ? 'for_hor="'.$id.'_hor"' : '').'>';
				
				if($param['w_flow']=='hor')
				{
						$j = 0;
						for($i=0; $i<(int)$param['w_rowcol']; $i++)
						{
							$rep.='<div id="'.$id.'_element_tr'.$i.'" style="display: table-row;">';
							
								for($l=0; $l<=(int)(count($param['w_choices'])/$param['w_rowcol']); $l++)
								{
									if($j >= count($param['w_choices'])%$param['w_rowcol'] && $l==(int)(count($param['w_choices'])/$param['w_rowcol']))
									continue;
									
									if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==(int)$param['w_rowcol']*$i+$l)
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$l+$i).'" idi="'.((int)$param['w_rowcol']*$l+$i).'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices'][(int)$param['w_rowcol']*$l+$i].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'" name="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'" other="1" onclick="if(set_checked(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$l+$i).'&quot;,&quot;form_id_temp&quot;)) show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$l+$i].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$l+$i).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'">'.$param['w_choices'][(int)$param['w_rowcol']*$l+$i].'</label></div>';
									else						
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$l+$i).'" idi="'.((int)$param['w_rowcol']*$l+$i).'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices'][(int)$param['w_rowcol']*$l+$i].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'" name="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'" onclick="set_checked(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$l+$i).'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$l+$i].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$l+$i).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'">'.$param['w_choices'][(int)$param['w_rowcol']*$l+$i].'</label></div>';		
								}
							
							$j++;
							$rep.='</div>';	
							
						}
			
				}
				else
				{
						for($i=0; $i<(int)(count($param['w_choices'])/$param['w_rowcol']); $i++)
						{
							$rep.='<div id="'.$id.'_element_tr'.$i.'" style="display: table-row;">';
							
							if(count($param['w_choices']) > (int)$param['w_rowcol'])
								for($l=0; $l<$param['w_rowcol']; $l++)
								{
									if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==(int)$param['w_rowcol']*$i+$l)
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$i+$l).'" idi="'.((int)$param['w_rowcol']*$i+$l).'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" name="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" other="1" onclick="if(set_checked(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$i+$l).'&quot;,&quot;form_id_temp&quot;)) show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$i+$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$i+$l).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'">'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'</label></div>';
									else						
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$i+$l).'" idi="'.((int)$param['w_rowcol']*$i+$l).'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" name="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" onclick="set_checked(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$i+$l).'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$i+$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$i+$l).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'">'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'</label></div>';
								}
							else
								for($l=0; $l<count($param['w_choices']); $l++)
								{
									if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==(int)$param['w_rowcol']*$i+$l)
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$i+$l).'" idi="'.((int)$param['w_rowcol']*$i+$l).'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" name="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" other="1" onclick="if(set_checked(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$i+$l).'&quot;,&quot;form_id_temp&quot;)) show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$i+$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$i+$l).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'">'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'</label></div>';
									else	
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$i+$l).'" idi="'.((int)$param['w_rowcol']*$i+$l).'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" name="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" onclick="set_checked(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$i+$l).'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$i+$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$i+$l).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'">'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'</label></div>';
								}
							
							$rep.='</div>';	
						}
						
						if(count($param['w_choices'])%$param['w_rowcol']!=0)
						{
							$rep.='<div id="'.$id.'_element_tr'.((int)(count($param['w_choices'])/(int)$param['w_rowcol'])).'" style="display: table-row;">';
							
							for($k=0; $k<count($param['w_choices'])%$param['w_rowcol']; $k++)
							{
								$l = count($param['w_choices']) - count($param['w_choices'])%$param['w_rowcol'] + $k;
								if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==$l)
									$rep.='<div valign="top" id="'.$id.'_td_little'.$l.'" idi="'.$l.'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices'][$l].'" id="'.$id.'_elementform_id_temp'.$l.'" name="'.$id.'_elementform_id_temp'.$l.'" other="1" onclick="if(set_checked(&quot;'.$id.'&quot;,&quot;'.$l.'&quot;,&quot;form_id_temp&quot;)) show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$l.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$l.'">'.$param['w_choices'][$l].'</label></div>';
								else	
									$rep.='<div valign="top" id="'.$id.'_td_little'.$l.'" idi="'.$l.'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices'][$l].'" id="'.$id.'_elementform_id_temp'.$l.'" name="'.$id.'_elementform_id_temp'.$l.'" onclick="set_checked(&quot;'.$id.'&quot;,&quot;'.$l.'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$l.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$l.'">'.$param['w_choices'][$l].'</label></div>';
							}
							
							$rep.='</div>';	
						}
						
	
					
				}
				$rep.='</div></div></div></div>';
					break;
				}
				case 'type_radio':
				{
				
					$params_names=array('w_field_label_size','w_field_label_pos','w_flow','w_choices','w_choices_checked','w_rowcol', 'w_required','w_randomize','w_allow_other','w_allow_other_num','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$param['w_choices']	= explode('***',$param['w_choices']);
					$param['w_choices_checked']	= explode('***',$param['w_choices_checked']);
					
					foreach($param['w_choices_checked'] as $key => $choices_checked )
					{
						if($choices_checked=='true')
							$param['w_choices_checked'][$key]='checked="checked"';
						else
							$param['w_choices_checked'][$key]='';
					}	
					
					$rep='<div id="wdform_field'.$id.'" type="type_radio" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_radio" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_randomize'].'" name="'.$id.'_randomizeform_id_temp" id="'.$id.'_randomizeform_id_temp"><input type="hidden" value="'.$param['w_allow_other'].'" name="'.$id.'_allow_otherform_id_temp" id="'.$id.'_allow_otherform_id_temp"><input type="hidden" value="'.$param['w_allow_other_num'].'" name="'.$id.'_allow_other_numform_id_temp" id="'.$id.'_allow_other_numform_id_temp"><input type="hidden" value="'.$param['w_rowcol'].'" name="'.$id.'_rowcol_numform_id_temp" id="'.$id.'_rowcol_numform_id_temp"><div style="display: table;"><div id="'.$id.'_table_little" style="display: table-row-group;" '.($param['w_flow']=='hor' ? 'for_hor="'.$id.'_hor"' : '').'>';
				
				
					if($param['w_flow']=='hor')
					{
						$j = 0;
						for($i=0; $i<(int)$param['w_rowcol']; $i++)
						{
							$rep.='<div id="'.$id.'_element_tr'.$i.'" style="display: table-row;">';
							
								for($l=0; $l<=(int)(count($param['w_choices'])/$param['w_rowcol']); $l++)
								{
									if($j >= count($param['w_choices'])%$param['w_rowcol'] && $l==(int)(count($param['w_choices'])/$param['w_rowcol']))
									continue;
									
									if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==(int)$param['w_rowcol']*$i+$l)
											$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$l+$i).'" idi="'.((int)$param['w_rowcol']*$l+$i).'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices'][(int)$param['w_rowcol']*$l+$i].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'" name="'.$id.'_elementform_id_temp" other="1" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$l+$i).'&quot;,&quot;form_id_temp&quot;); show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$l+$i].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$l+$i).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'">'.$param['w_choices'][(int)$param['w_rowcol']*$l+$i].'</label></div>';
										else						
											$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$l+$i).'" idi="'.((int)$param['w_rowcol']*$l+$i).'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices'][(int)$param['w_rowcol']*$l+$i].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'" name="'.$id.'_elementform_id_temp" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$l+$i).'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$l+$i].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$l+$i).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$l+$i).'">'.$param['w_choices'][(int)$param['w_rowcol']*$l+$i].'</label></div>';		
								}
								
							$j++;
							$rep.='</div>';	
							
						}
		
					}
					else
					{
						for($i=0; $i<(int)(count($param['w_choices'])/$param['w_rowcol']); $i++)
						{
							$rep.='<div id="'.$id.'_element_tr'.$i.'" style="display: table-row;">';
							
							if(count($param['w_choices']) > (int)$param['w_rowcol'])
								for($l=0; $l<$param['w_rowcol']; $l++)
								{
									if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==(int)$param['w_rowcol']*$i+$l)
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$i+$l).'" idi="'.((int)$param['w_rowcol']*$i+$l).'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" name="'.$id.'_elementform_id_temp" other="1" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$i+$l).'&quot;,&quot;form_id_temp&quot;); show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$i+$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$i+$l).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'">'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'</label></div>';
									else						
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$i+$l).'" idi="'.((int)$param['w_rowcol']*$i+$l).'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" name="'.$id.'_elementform_id_temp" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$i+$l).'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$i+$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$i+$l).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'">'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'</label></div>';
								}
							else
								for($l=0; $l<count($param['w_choices']); $l++)
								{
									if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==(int)$param['w_rowcol']*$i+$l)
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$i+$l).'" idi="'.((int)$param['w_rowcol']*$i+$l).'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" name="'.$id.'_elementform_id_temp" other="1" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$i+$l).'&quot;,&quot;form_id_temp&quot;); show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$i+$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$i+$l).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'">'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'</label></div>';
									else	
										$rep.='<div valign="top" id="'.$id.'_td_little'.((int)$param['w_rowcol']*$i+$l).'" idi="'.((int)$param['w_rowcol']*$i+$l).'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'" id="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'" name="'.$id.'_elementform_id_temp" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.((int)$param['w_rowcol']*$i+$l).'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][(int)$param['w_rowcol']*$i+$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.((int)$param['w_rowcol']*$i+$l).'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.((int)$param['w_rowcol']*$i+$l).'">'.$param['w_choices'][(int)$param['w_rowcol']*$i+$l].'</label></div>';
								}
							
							$rep.='</div>';	
						}
						
						if(count($param['w_choices'])%$param['w_rowcol']!=0)
						{
							$rep.='<div id="'.$id.'_element_tr'.((int)(count($param['w_choices'])/(int)$param['w_rowcol'])).'" style="display: table-row;">';
							
							for($k=0; $k<count($param['w_choices'])%$param['w_rowcol']; $k++)
							{
								$l = count($param['w_choices']) - count($param['w_choices'])%$param['w_rowcol'] + $k;
								if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==$l)
									$rep.='<div valign="top" id="'.$id.'_td_little'.$l.'" idi="'.$l.'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices'][$l].'" id="'.$id.'_elementform_id_temp'.$l.'" name="'.$id.'_elementform_id_temp" other="1" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$l.'&quot;,&quot;form_id_temp&quot;); show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$l.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$l.'">'.$param['w_choices'][$l].'</label></div>';
								else	
									$rep.='<div valign="top" id="'.$id.'_td_little'.$l.'" idi="'.$l.'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices'][$l].'" id="'.$id.'_elementform_id_temp'.$l.'" name="'.$id.'_elementform_id_temp" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$l.'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][$l].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$l.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$l.'">'.$param['w_choices'][$l].'</label></div>';
							}
							
							$rep.='</div>';	
						}
						
					}
							
					
		
				$rep.='</div></div></div></div>';
				
					break;
				}
				case 'type_own_select':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_choices','w_choices_checked', 'w_choices_disabled','w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$param['w_choices']	= explode('***',$param['w_choices']);
					$param['w_choices_checked']	= explode('***',$param['w_choices_checked']);
					$param['w_choices_disabled']	= explode('***',$param['w_choices_disabled']);
				
					foreach($param['w_choices_checked'] as $key => $choices_checked )
					{
						if($choices_checked=='true')
							$param['w_choices_checked'][$key]='selected="selected"';
						else
							$param['w_choices_checked'][$key]='';
					}
					
					$rep='<div id="wdform_field'.$id.'" type="type_own_select" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_own_select" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><select id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp" onchange="set_select(this)" style="width: '.$param['w_size'].'px;"  '.$param['attributes'].'>';
					foreach($param['w_choices'] as $key => $choice)
					{
						if($param['w_choices_disabled'][$key]=="true")
							$choice_value='';
						else
							$choice_value=$choice;
					  $rep.='<option id="'.$id.'_option'.$key.'" value="'.$choice_value.'" onselect="set_select(&quot;'.$id.'_option'.$key.'&quot;)" '.$param['w_choices_checked'][$key].'>'.$choice.'</option>';
					}
					$rep.='</select></div></div>';
					break;
				}
				
				case 'type_country':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_countries','w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$param['w_countries']	= explode('***',$param['w_countries']);
				
					$rep='<div id="wdform_field'.$id.'" type="type_country" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_country" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><select id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp" style="width: '.$param['w_size'].'px;"  '.$param['attributes'].'>';
					foreach($param['w_countries'] as $key => $choice)
					{
					  $choice_value=$choice;
					  $rep.='<option value="'.$choice_value.'">'.$choice.'</option>';
					}
					$rep.='</select></div></div>';
					break;
				}
				
				case 'type_time':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_time_type','w_am_pm','w_sec','w_hh','w_mm','w_ss','w_mini_labels','w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
				
					$w_mini_labels = explode('***',$param['w_mini_labels']);
					
				
					if($param['w_sec']=='1')
					{
						$w_sec = '<div align="center" style="display: table-cell;"><span class="wdform_colon" style="vertical-align: middle;">&nbsp;:&nbsp;</span></div><div id="'.$id.'_td_time_input3" style="width: 32px; display: table-cell;"><input type="text" value="'.$param['w_ss'].'" class="time_box" id="'.$id.'_ssform_id_temp" name="'.$id.'_ssform_id_temp" onkeypress="return check_second(event, &quot;'.$id.'_ssform_id_temp&quot;)" onkeyup="change_second(&quot;'.$id.'_ssform_id_temp&quot;)" onblur="add_0(&quot;'.$id.'_ssform_id_temp&quot;)" '.$param['attributes'].'></div>';
						$w_sec_label='<div style="display: table-cell;"></div><div id="'.$id.'_td_time_label3" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_ss">'.$w_mini_labels[2].'</label></div>';
					}
					else
					{
						$w_sec = '';
						$w_sec_label='';
					}
					
					if($param['w_time_type']=='12')
					{
						if($param['w_am_pm']=='am')
						{
							$am_ = "selected=\"selected\"";
							$pm_ = "";
						}	
						else
						{
							$am_ = "";
							$pm_ = "selected=\"selected\"";
							
						}	
					
					$w_time_type = '<div id="'.$id.'_am_pm_select" class="td_am_pm_select" style="display: table-cell;"><select class="am_pm_select" name="'.$id.'_am_pmform_id_temp" id="'.$id.'_am_pmform_id_temp" onchange="set_sel_am_pm(this)" '.$param['attributes'].'><option value="am" '.$am_.'>AM</option><option value="pm" '.$pm_.'>PM</option></select></div>';
					
					$w_time_type_label = '<div id="'.$id.'_am_pm_label" class="td_am_pm_select" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_am_pm">'.$w_mini_labels[3].'</label></div>';
					
					}
					else
					{
					$w_time_type='';
					$w_time_type_label = '';
					}

					
					$rep ='<div id="wdform_field'.$id.'" type="type_time" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_time" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><div id="'.$id.'_table_time" style="display: table;"><div id="'.$id.'_tr_time1" style="display: table-row;"><div id="'.$id.'_td_time_input1" style="width: 32px; display: table-cell;"><input type="text" value="'.$param['w_hh'].'" class="time_box" id="'.$id.'_hhform_id_temp" name="'.$id.'_hhform_id_temp" onkeypress="return check_hour(event, &quot;'.$id.'_hhform_id_temp&quot;, &quot;23&quot;)" onkeyup="change_hour(event, &quot;'.$id.'_hhform_id_temp&quot;,&quot;23&quot;)" onblur="add_0(&quot;'.$id.'_hhform_id_temp&quot;)" '.$param['attributes'].'></div><div align="center" style="display: table-cell;"><span class="wdform_colon" style="vertical-align: middle;">&nbsp;:&nbsp;</span></div><div id="'.$id.'_td_time_input2" style="width: 32px; display: table-cell;"><input type="text" value="'.$param['w_mm'].'" class="time_box" id="'.$id.'_mmform_id_temp" name="'.$id.'_mmform_id_temp" onkeypress="return check_minute(event, &quot;'.$id.'_mmform_id_temp&quot;)" onkeyup="change_minute(event, &quot;'.$id.'_mmform_id_temp&quot;)" onblur="add_0(&quot;'.$id.'_mmform_id_temp&quot;)" '.$param['attributes'].'></div>'.$w_sec.$w_time_type.'</div><div id="'.$id.'_tr_time2" style="display: table-row;"><div id="'.$id.'_td_time_label1" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_hh">'.$w_mini_labels[0].'</label></div><div style="display: table-cell;"></div><div id="'.$id.'_td_time_label2" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_mm">'.$w_mini_labels[1].'</label></div>'.$w_sec_label.$w_time_type_label.'</div></div></div></div>';
					
					break;
				}
				case 'type_date':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_date','w_required','w_class','w_format','w_but_val');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
				
	

					$rep ='<div id="wdform_field'.$id.'" type="type_date" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_date" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="text" value="'.$param['w_date'].'" class="wdform-date" id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp"  onchange="change_value(&quot;'.$id.'_elementform_id_temp&quot;)" '.$param['attributes'].' style="vertical-align: top; width: 80px;"><button id="'.$id.'_buttonform_id_temp" class="btn" type="reset" value="'.$param['w_but_val'].'" format="'.$param['w_format'].'" title="'.$param['w_but_val'].'"  '.$param['attributes'].' onclick="return showCalendar(&quot;'.$id.'_elementform_id_temp&quot; , &quot;'.$param['w_format'].'&quot;)"><i class="icon-calendar"></i></button></div></div>';
					
					break;
				}
				case 'type_date_fields':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_day','w_month','w_year','w_day_type','w_month_type','w_year_type','w_day_label','w_month_label','w_year_label','w_day_size','w_month_size','w_year_size','w_required','w_class','w_from','w_to','w_divider');
					
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
				
	
					if($param['w_day_type']=="SELECT")
					{

					
						$w_day_type = '<select id="'.$id.'_dayform_id_temp" name="'.$id.'_dayform_id_temp" onchange="set_select(this)" style="width: '.$param['w_day_size'].'px;" '.$param['attributes'].'><option value=""></option>';
						for($k=1; $k<=31; $k++)
						{
							if($k<10)
							{
								if($param['w_day']=='0'.$k)
								$selected = "selected=\"selected\"";
								else
								$selected = "";
								
								$w_day_type .= '<option value="0'.$k.'" '.$selected.'>0'.$k.'</option>';
							}
							else
							{
							if($param['w_day']==''.$k)
								$selected = "selected=\"selected\"";
								else
								$selected = "";
								
								$w_day_type .= '<option value="'.$k.'" '.$selected.'>'.$k.'</option>';
							}
							
							
						}
						$w_day_type .= '</select>';
					}
					else
					{
						$w_day_type = '<input type="text" value="'.$param['w_day'].'" id="'.$id.'_dayform_id_temp" name="'.$id.'_dayform_id_temp" onchange="change_value(&quot;'.$id.'_dayform_id_temp&quot;)" onkeypress="return check_day(event, &quot;'.$id.'_dayform_id_temp&quot;)" onblur="if (this.value==&quot;0&quot;) this.value=""; else add_0(&quot;'.$id.'_dayform_id_temp&quot;)" style="width: '.$param['w_day_size'].'px;" '.$param['attributes'].'>';
					}

					if($param['w_month_type']=="SELECT")
					{
					
						$w_month_type = '<select id="'.$id.'_monthform_id_temp" name="'.$id.'_monthform_id_temp" onchange="set_select(this)" style="width: '.$param['w_month_size'].'px;" '.$param['attributes'].'><option value=""></option><option value="01" '.($param['w_month']=="01" ? "selected=\"selected\"": "").'  ><!--repstart-->January<!--repend--></option><option value="02" '.($param['w_month']=="02" ? "selected=\"selected\"": "").'><!--repstart-->February<!--repend--></option><option value="03" '.($param['w_month']=="03"? "selected=\"selected\"": "").'><!--repstart-->March<!--repend--></option><option value="04" '.($param['w_month']=="04" ? "selected=\"selected\"": "").' ><!--repstart-->April<!--repend--></option><option value="05" '.($param['w_month']=="05" ? "selected=\"selected\"": "").' ><!--repstart-->May<!--repend--></option><option value="06" '.($param['w_month']=="06" ? "selected=\"selected\"": "").' ><!--repstart-->June<!--repend--></option><option value="07" '.($param['w_month']=="07" ? "selected=\"selected\"": "").' ><!--repstart-->July<!--repend--></option><option value="08" '.($param['w_month']=="08" ? "selected=\"selected\"": "").' ><!--repstart-->August<!--repend--></option><option value="09" '.($param['w_month']=="09" ? "selected=\"selected\"": "").' ><!--repstart-->September<!--repend--></option><option value="10" '.($param['w_month']=="10" ? "selected=\"selected\"": "").' ><!--repstart-->October<!--repend--></option><option value="11" '.($param['w_month']=="11" ? "selected=\"selected\"": "").'><!--repstart-->November<!--repend--></option><option value="12" '.($param['w_month']=="12" ? "selected=\"selected\"": "").' ><!--repstart-->December<!--repend--></option></select>';
					
					}
					else
					{
					$w_month_type = '<input type="text" value="'.$param['w_month'].'" id="'.$id.'_monthform_id_temp" name="'.$id.'_monthform_id_temp" onkeypress="return check_month(event, &quot;'.$id.'_monthform_id_temp&quot;)" onchange="change_value(&quot;'.$id.'_monthform_id_temp&quot;)" onblur="if (this.value==&quot;0&quot;) this.value=""; else add_0(&quot;'.$id.'_monthform_id_temp&quot;)" style="width: '.$param['w_day_size'].'px;" '.$param['attributes'].'>';
					
					}

					if($param['w_year_type']=="SELECT")
					{
						$w_year_type = '<select id="'.$id.'_yearform_id_temp" name="'.$id.'_yearform_id_temp" onchange="set_select(this)" from="'.$param['w_from'].'" to="'.$param['w_to'].'" style="width: '.$param['w_year_size'].'px;" '.$param['attributes'].'><option value=""></option>';
						for($k=$param['w_to']; $k>=$param['w_from']; $k--)
						{
							if($param['w_year']==$k)
							$selected = "selected=\"selected\"";
							else
							$selected = "";
							
							$w_year_type .= '<option value="'.$k.'" '.$selected.'>'.$k.'</option>';
						}
						$w_year_type .= '</select>';
					
					}
					else
					{
						$w_year_type = '<input type="text" value="'.$param['w_year'].'" id="'.$id.'_yearform_id_temp" name="'.$id.'_yearform_id_temp" onchange="change_year(&quot;'.$id.'_yearform_id_temp&quot;)" onkeypress="return check_year1(event, &quot;'.$id.'_yearform_id_temp&quot;)" onblur="check_year2(&quot;'.$id.'_yearform_id_temp&quot;)" from="'.$param['w_from'].'" to="'.$param['w_to'].'" style="width: '.$param['w_day_size'].'px;" '.$param['attributes'].'>';
					}

					
					$rep ='<div id="wdform_field'.$id.'" type="type_date_fields" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_date_fields" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><div id="'.$id.'_table_date" style="display: table;"><div id="'.$id.'_tr_date1" style="display: table-row;"><div id="'.$id.'_td_date_input1" style="display: table-cell;">
					'.$w_day_type.'
					
					</div><div id="'.$id.'_td_date_separator1" style="display: table-cell;"><span id="'.$id.'_separator1" class="wdform_separator">'.$param['w_divider'].'</span></div><div id="'.$id.'_td_date_input2" style="display: table-cell;">'.$w_month_type.'</div><div id="'.$id.'_td_date_separator2" style="display: table-cell;"><span id="'.$id.'_separator2" class="wdform_separator">'.$param['w_divider'].'</span></div><div id="'.$id.'_td_date_input3" style="display: table-cell;">'.$w_year_type.'</div></div><div id="'.$id.'_tr_date2" style="display: table-row;"><div id="'.$id.'_td_date_label1" style="display: table-cell;"><label class="mini_label" id="'.$id.'_day_label">'.$param['w_day_label'].'</label></div><div style="display: table-cell;"></div><div id="'.$id.'_td_date_label2" style="display: table-cell;"><label class="mini_label" id="'.$id.'_month_label">'.$param['w_month_label'].'</label></div><div style="display: table-cell;"></div><div id="'.$id.'_td_date_label3" style="display: table-cell;"><label class="mini_label" id="'.$id.'_year_label">'.$param['w_year_label'].'</label></div></div></div></div></div>';
					
					break;
				}
				case 'type_file_upload':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_destination','w_extension','w_max_size','w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	

					$rep ='<div id="wdform_field'.$id.'" type="type_file_upload" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_file_upload" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="***max_sizeskizb'.$id.'***'.$param['w_max_size'].'***max_sizeverj'.$id.'***" id="'.$id.'_max_size" name="'.$id.'_max_size"><input type="hidden" value="***destinationskizb'.$id.'***'.$param['w_destination'].'***destinationverj'.$id.'***" id="'.$id.'_destination" name="'.$id.'_destination"><input type="hidden" value="***extensionskizb'.$id.'***'.$param['w_extension'].'***extensionverj'.$id.'***" id="'.$id.'_extension" name="'.$id.'_extension"><input type="file" class="file_upload" id="'.$id.'_elementform_id_temp" name="'.$id.'_fileform_id_temp" '.$param['attributes'].'></div></div>';
					
					break;
				}
				case 'type_captcha':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_digit','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					
					$rep ='<div id="wdform_field'.$id.'" type="type_captcha" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display:'.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_captcha" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><div style="display: table;"><div style="display: table-row;"><div valign="middle" style="display: table-cell; vertical-align:top;"><img type="captcha" digit="'.$param['w_digit'].'" src="../index.php?option=com_formmaker&amp;view=wdcaptcha&amp;format=raw&amp;tmpl=component&amp;digit='.$param['w_digit'].'&amp;i=form_id_temp" id="_wd_captchaform_id_temp" class="captcha_img" onclick="captcha_refresh(&quot;_wd_captcha&quot;,&quot;form_id_temp&quot;)" '.$param['attributes'].'></div><div valign="middle" style="display: table-cell;"><div class="captcha_refresh" id="_element_refreshform_id_temp" onclick="captcha_refresh(&quot;_wd_captcha&quot;,&quot;form_id_temp&quot;)" '.$param['attributes'].'></div></div></div><div style="display: table-row;"><div style="display: table-cell;"><input type="text" class="captcha_input" id="_wd_captcha_inputform_id_temp" name="captcha_input" style="width: '.($param['w_digit']*10+15).'px;" '.$param['attributes'].'></div></div></div></div></div>';
					
					break;
				}
				case 'type_recaptcha':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_public','w_private','w_theme','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					
					$rep ='<div id="wdform_field'.$id.'" type="type_recaptcha" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_recaptcha" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><div id="wd_recaptchaform_id_temp" public_key="'.$param['w_public'].'" private_key="'.$param['w_private'].'" theme="'.$param['w_theme'].'" '.$param['attributes'].'><span style="color: red; font-style: italic;">Recaptcha don\'t display in back end</span></div></div></div>';
					
					break;
				}
				
				case 'type_hidden':
				{
					$params_names=array('w_name','w_value');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_name'] = str_replace('&nbsp;','',$param['w_name']);
					$rep ='<div id="wdform_field'.$id.'" type="type_hidden" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" style="display: table-cell;"><span id="'.$id.'_element_labelform_id_temp" style="display: none;">'.$param['w_name'].'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" style="display: table-cell;"><input type="hidden" value="'.$param['w_value'].'" id="'.$id.'_elementform_id_temp" name="'.$param['w_name'].'" '.$param['attributes'].'><input type="hidden" value="type_hidden" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"></div></div>';
					
					break;
				}
				case 'type_mark_map':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_center_x','w_center_y','w_long','w_lat','w_zoom','w_width','w_height','w_info','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
				
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
				
					$rep ='<div id="wdform_field'.$id.'" type="type_mark_map" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px; vertical-align:top;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_mark_map" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><div id="'.$id.'_elementform_id_temp" long0="'.$param['w_long'].'" lat0="'.$param['w_lat'].'" zoom="'.$param['w_zoom'].'" info0="'.$param['w_info'].'" center_x="'.$param['w_center_x'].'" center_y="'.$param['w_center_y'].'" style="width: '.$param['w_width'].'px; height: '.$param['w_height'].'px;" '.$param['attributes'].'></div></div></div>	';
					
					break;
				}
				
				case 'type_map':
				{
					$params_names=array('w_center_x','w_center_y','w_long','w_lat','w_zoom','w_width','w_height','w_info','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					
					$marker='';
					
					$param['w_long']	= explode('***',$param['w_long']);
					$param['w_lat']	= explode('***',$param['w_lat']);
					$param['w_info']	= explode('***',$param['w_info']);
					foreach($param['w_long'] as $key => $w_long )
					{
						$marker.='long'.$key.'="'.$w_long.'" lat'.$key.'="'.$param['w_lat'][$key].'" info'.$key.'="'.$param['w_info'][$key].'"';
					}
				
					$rep ='<div id="wdform_field'.$id.'" type="type_map" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: table-cell;"><span id="'.$id.'_element_labelform_id_temp" style="display: none;">'.$label.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: table-cell;"><input type="hidden" value="type_map" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><div id="'.$id.'_elementform_id_temp" zoom="'.$param['w_zoom'].'" center_x="'.$param['w_center_x'].'" center_y="'.$param['w_center_y'].'" style="width: '.$param['w_width'].'px; height: '.$param['w_height'].'px;" '.$marker.' '.$param['attributes'].'></div></div></div>';
					
					break;
				}
				case 'type_paypal_price':
				{
										
					$params_names=array('w_field_label_size','w_field_label_pos','w_first_val','w_title', 'w_mini_labels','w_size','w_required','w_hide_cents','w_class','w_range_min','w_range_max');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$input_active = ($param['w_first_val']==$param['w_title'] ? "input_deactive" : "input_active");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$hide_cents = ($param['w_hide_cents']=="yes" ? "none;" : "table-cell;");	
				

					$w_first_val = explode('***',$param['w_first_val']);
					$w_title = explode('***',$param['w_title']);
					$w_mini_labels = explode('***',$param['w_mini_labels']);
					
					$rep ='<div id="wdform_field'.$id.'" type="type_paypal_price" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required"style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_paypal_price" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_range_min'].'" name="'.$id.'_range_minform_id_temp" id="'.$id.'_range_minform_id_temp"><input type="hidden" value="'.$param['w_range_max'].'" name="'.$id.'_range_maxform_id_temp" id="'.$id.'_range_maxform_id_temp"><div id="'.$id.'_table_price" style="display: table;"><div id="'.$id.'_tr_price1" style="display: table-row;"><div id="'.$id.'_td_name_currency" style="display: table-cell;"><span class="wdform_colon" style="vertical-align: middle;"><!--repstart-->&nbsp;$&nbsp;<!--repend--></span></div><div id="'.$id.'_td_name_dollars" style="display: table-cell;"><input type="text" class="'.$input_active.'" id="'.$id.'_element_dollarsform_id_temp" name="'.$id.'_element_dollarsform_id_temp" value="'.$w_first_val[0].'" title="'.$w_title[0].'"onfocus="delete_value(&quot;'.$id.'_element_dollarsform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_element_dollarsform_id_temp&quot;)"onchange="change_value(&quot;'.$id.'_element_dollarsform_id_temp&quot;)" onkeypress="return check_isnum(event)" style="width: '.$param['w_size'].'px;" '.$param['attributes'].'></div><div id="'.$id.'_td_name_divider" style="display: '.$hide_cents.';"><span class="wdform_colon" style="vertical-align: middle;">&nbsp;.&nbsp;</span></div><div id="'.$id.'_td_name_cents" style="display: '.$hide_cents.'"><input type="text" class="'.$input_active.'" id="'.$id.'_element_centsform_id_temp" name="'.$id.'_element_centsform_id_temp" value="'.$w_first_val[1].'" title="'.$w_title[1].'"onfocus="delete_value(&quot;'.$id.'_element_centsform_id_temp&quot;)" onblur="return_value(&quot;'.$id.'_element_centsform_id_temp&quot;); add_0(&quot;'.$id.'_element_centsform_id_temp&quot;)"onchange="change_value(&quot;'.$id.'_element_centsform_id_temp&quot;)" onkeypress="return check_isnum_interval(event,&quot;'.$id.'_element_centsform_id_temp&quot;,0,99)"style="width: 30px;" '.$param['attributes'].'></div></div><div id="'.$id.'_tr_price2" style="display: table-row;"><div style="display: table-cell;"><label class="mini_label"></label></div><div align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_dollars">'.$w_mini_labels[0].'</label></div><div id="'.$id.'_td_name_label_divider" style="display: '.$hide_cents.'"><label class="mini_label"></label></div><div align="left" id="'.$id.'_td_name_label_cents" style="display: '.$hide_cents.'"><label class="mini_label" id="'.$id.'_mini_label_cents">'.$w_mini_labels[1].'</label></div></div></div></div></div>';
					break;
				}
				
				case 'type_paypal_select':
				{
				$params_names=array('w_field_label_size','w_field_label_pos','w_size','w_choices','w_choices_price','w_choices_checked', 'w_choices_disabled','w_required','w_quantity', 'w_quantity_value','w_class','w_property','w_property_values');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$param['w_choices']	= explode('***',$param['w_choices']);
					   
					$param['w_choices_price']	= explode('***',$param['w_choices_price']);
					   
					$param['w_choices_checked']	= explode('***',$param['w_choices_checked']);
					  
					$param['w_choices_disabled']	= explode('***',$param['w_choices_disabled']);
					$param['w_property']	= explode('***',$param['w_property']);
					$param['w_property_values']	= explode('***',$param['w_property_values']);
			
					foreach($param['w_choices_checked'] as $key => $choices_checked )
					{
						if($choices_checked=='true')
							$param['w_choices_checked'][$key]='selected="selected"';
						else
							$param['w_choices_checked'][$key]='';
					}

					
					$rep='<div id="wdform_field'.$id.'" type="type_paypal_select" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_paypal_select" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><select id="'.$id.'_elementform_id_temp" name="'.$id.'_elementform_id_temp" onchange="set_select(this)" style="width: '.$param['w_size'].'px;"  '.$param['attributes'].'>';
					foreach($param['w_choices'] as $key => $choice)
					{
						if($param['w_choices_disabled'][$key]=="true")
							$choice_value='';
						else
							$choice_value=$param['w_choices_price'][$key];
					  $rep.='<option id="'.$id.'_option'.$key.'" value="'.$choice_value.'" onselect="set_select(&quot;'.$id.'_option'.$key.'&quot;)" '.$param['w_choices_checked'][$key].'>'.$choice.'</option>';
					}
					$rep.='</select><div id="'.$id.'_divform_id_temp">';
					if($param['w_quantity']=="yes")
						$rep.='<span id="'.$id.'_element_quantity_spanform_id_temp" style="margin-right: 15px;"><label class="mini_label" id="'.$id.'_element_quantity_label_form_id_temp" style="margin-right: 5px;"><!--repstart-->Quantity<!--repend--></label><input type="text" value="'.$param['w_quantity_value'].'" id="'.$id.'_element_quantityform_id_temp" name="'.$id.'_element_quantityform_id_temp" onkeypress="return check_isnum(event)" onchange="change_value(&quot;'.$id.'_element_quantityform_id_temp&quot;, this.value)" style="width: 30px; margin: 2px 0px;"></span>';
					if($param['w_property'][0])					
					foreach($param['w_property'] as $key => $property)
					{
	
					$rep.='
					<span id="'.$id.'_property_'.$key.'" style="margin-right: 15px;">
					
					<label class="mini_label" id="'.$id.'_property_label_form_id_temp'.$key.'" style="margin-right: 5px;">'.$property.'</label>
					<select id="'.$id.'_propertyform_id_temp'.$key.'" name="'.$id.'_propertyform_id_temp'.$key.'" style="width: auto; margin: 2px 0px;">';
					$param['w_property_values'][$key]	= explode('###',$param['w_property_values'][$key]);
					$param['w_property_values'][$key]	= array_slice($param['w_property_values'][$key],1, count($param['w_property_values'][$key]));   
					foreach($param['w_property_values'][$key] as $subkey => $property_value)
					{
						$rep.='<option id="'.$id.'_'.$key.'_option'.$subkey.'" value="'.$property_value.'">'.$property_value.'</option>';
					}
					$rep.='</select></span>';
					}
					
					$rep.='</div></div></div>';
					break;
				}
				
				case 'type_paypal_checkbox':
				{
										
					$params_names=array('w_field_label_size','w_field_label_pos','w_flow','w_choices','w_choices_price','w_choices_checked','w_required','w_randomize','w_allow_other','w_allow_other_num','w_class','w_property','w_property_values','w_quantity','w_quantity_value');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$param['w_choices']	= explode('***',$param['w_choices']);				   
					$param['w_choices_price']	= explode('***',$param['w_choices_price']);					
					$param['w_choices_checked']	= explode('***',$param['w_choices_checked']);		  
					$param['w_property']	= explode('***',$param['w_property']);
					$param['w_property_values']	= explode('***',$param['w_property_values']);
					
					foreach($param['w_choices_checked'] as $key => $choices_checked )
					{
						if($choices_checked=='true')
							$param['w_choices_checked'][$key]='checked="checked"';
						else
							$param['w_choices_checked'][$key]='';
					}
					
					$rep='<div id="wdform_field'.$id.'" type="type_paypal_checkbox" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_paypal_checkbox" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_randomize'].'" name="'.$id.'_randomizeform_id_temp" id="'.$id.'_randomizeform_id_temp"><input type="hidden" value="'.$param['w_allow_other'].'" name="'.$id.'_allow_otherform_id_temp" id="'.$id.'_allow_otherform_id_temp"><input type="hidden" value="'.$param['w_allow_other_num'].'" name="'.$id.'_allow_other_numform_id_temp" id="'.$id.'_allow_other_numform_id_temp"><div style="display: table;"><div id="'.$id.'_table_little" style="display: table-row-group;">';
				
					if($param['w_flow']=='hor')
					{
						$rep.= '<div id="'.$id.'_hor" style="display: table-row;">';
						foreach($param['w_choices'] as $key => $choice)
						{
						if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==$key)
							$rep.='<div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="checkbox" other="1" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp'.$key.'" value="'.$param['w_choices_price'][$key].'" onclick="if(set_checked(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;)) show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div>';
						else
							$rep.='<div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="checkbox" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp'.$key.'" value="'.$param['w_choices_price'][$key].'" onclick="set_checked(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div>';
						}
						$rep.= '</div>';
					}
					else
					{
						$rep.= '<div id="'.$id.'_table_little" style="display: table-row-group;">';
						foreach($param['w_choices'] as $key => $choice)
						{
						if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==$key)
							$rep.='<div id="'.$id.'_element_tr'.$key.'" style="display: table-row;"><div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="checkbox" value="'.$param['w_choices_price'][$key].'" other="1" id="'.$id.'_elementform_id_temp'.$key.'" onclick="if(set_checked(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;)) show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" name="'.$id.'_elementform_id_temp'.$key.'" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div></div>';
						else
							$rep.='<div id="'.$id.'_element_tr'.$key.'" style="display: table-row;"><div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="checkbox" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp'.$key.'" value="'.$param['w_choices_price'][$key].'" onclick="set_checked(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div></div>';					}
						$rep.= '</div>';
					}
					$rep.='</div></div>';

					$rep.='<div id="'.$id.'_divform_id_temp">';
					if($param['w_quantity']=="yes")
						$rep.='<span id="'.$id.'_element_quantity_spanform_id_temp" style="margin-right: 15px;"><label class="mini_label" id="'.$id.'_element_quantity_label_form_id_temp" style="margin-right: 5px;"><!--repstart-->Quantity<!--repend--></label><input type="text" value="'.$param['w_quantity_value'].'" id="'.$id.'_element_quantityform_id_temp" name="'.$id.'_element_quantityform_id_temp" onkeypress="return check_isnum(event)" onchange="change_value(&quot;'.$id.'_element_quantityform_id_temp&quot;, this.value)" style="width: 30px; margin: 2px 0px;"></span>';
					if($param['w_property'][0])					
					foreach($param['w_property'] as $key => $property)
					{
					$rep.='
					<span id="'.$id.'_property_'.$key.'" style="margin-right: 15px;">
					
					<label class="mini_label" id="'.$id.'_property_label_form_id_temp'.$key.'" style="margin-right: 5px;">'.$property.'</label>
					<select id="'.$id.'_propertyform_id_temp'.$key.'" name="'.$id.'_propertyform_id_temp'.$key.'" style="width: auto; margin: 2px 0px;">';
					$param['w_property_values'][$key]	= explode('###',$param['w_property_values'][$key]);
					$param['w_property_values'][$key]	= array_slice($param['w_property_values'][$key],1, count($param['w_property_values'][$key]));   
					foreach($param['w_property_values'][$key] as $subkey => $property_value)
					{
						$rep.='<option id="'.$id.'_'.$key.'_option'.$subkey.'" value="'.$property_value.'">'.$property_value.'</option>';
					}
					$rep.='</select></span>';
					}
					
					$rep.='</div></div></div>';
					break;
				}
				case 'type_paypal_radio':
				{
					
					$params_names=array('w_field_label_size','w_field_label_pos','w_flow','w_choices','w_choices_price','w_choices_checked','w_required','w_randomize','w_allow_other','w_allow_other_num','w_class','w_property','w_property_values','w_quantity','w_quantity_value');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$param['w_choices']	= explode('***',$param['w_choices']);
					   
					$param['w_choices_price']	= explode('***',$param['w_choices_price']);
					
					$param['w_choices_checked']	= explode('***',$param['w_choices_checked']);
					  
					$param['w_property']	= explode('***',$param['w_property']);
					$param['w_property_values']	= explode('***',$param['w_property_values']);
					
					foreach($param['w_choices_checked'] as $key => $choices_checked )
					{
						if($choices_checked=='true')
							$param['w_choices_checked'][$key]='checked="checked"';
						else
							$param['w_choices_checked'][$key]='';
					}
					
					$rep='<div id="wdform_field'.$id.'" type="type_paypal_radio" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_paypal_radio" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_randomize'].'" name="'.$id.'_randomizeform_id_temp" id="'.$id.'_randomizeform_id_temp"><input type="hidden" value="'.$param['w_allow_other'].'" name="'.$id.'_allow_otherform_id_temp" id="'.$id.'_allow_otherform_id_temp"><input type="hidden" value="'.$param['w_allow_other_num'].'" name="'.$id.'_allow_other_numform_id_temp" id="'.$id.'_allow_other_numform_id_temp"><div style="display: table;"><div id="'.$id.'_table_little" style="display: table-row-group;">';
				
				if($param['w_flow']=='hor')
				{
					$rep.= '<div id="'.$id.'_hor" style="display: table-row;">';
					foreach($param['w_choices'] as $key => $choice)
					{
					if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==$key)
						$rep.='<div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="radio" other="1" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp" value="'.$param['w_choices_price'][$key].'" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;); show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div>';
					else
						$rep.='<div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="radio" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp" value="'.$param['w_choices_price'][$key].'" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div>';
					}
					$rep.= '</div>';
				}
				else
				{
					$rep.= '<div id="'.$id.'_table_little" style="display: table-row-group;">';
					foreach($param['w_choices'] as $key => $choice)
					{
					if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==$key)
						$rep.='<div id="'.$id.'_element_tr'.$key.'" style="display: table-row;"><div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices_price'][$key].'" other="1" id="'.$id.'_elementform_id_temp'.$key.'" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;); show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" name="'.$id.'_elementform_id_temp" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div></div>';
					else
						$rep.='<div id="'.$id.'_element_tr'.$key.'" style="display: table-row;"><div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="radio" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp" value="'.$param['w_choices_price'][$key].'" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div></div>';					}
					$rep.= '</div>';
				}
					$rep.='</div></div>';

					$rep.='<div id="'.$id.'_divform_id_temp">';
					if($param['w_quantity']=="yes")
						$rep.='<span id="'.$id.'_element_quantity_spanform_id_temp" style="margin-right: 15px;"><label class="mini_label" id="'.$id.'_element_quantity_label_form_id_temp" style="margin-right: 5px;"><!--repstart-->Quantity<!--repend--></label><input type="text" value="'.$param['w_quantity_value'].'" id="'.$id.'_element_quantityform_id_temp" name="'.$id.'_element_quantityform_id_temp" onkeypress="return check_isnum(event)" onchange="change_value(&quot;'.$id.'_element_quantityform_id_temp&quot;, this.value)" style="width: 30px; margin: 2px 0px;"></span>';
					if($param['w_property'][0])					
					foreach($param['w_property'] as $key => $property)
					{
					$rep.='
					<span id="'.$id.'_property_'.$key.'" style="margin-right: 15px;">
					
					<label class="mini_label" id="'.$id.'_property_label_form_id_temp'.$key.'" style="margin-right: 5px;">'.$property.'</label>
					<select id="'.$id.'_propertyform_id_temp'.$key.'" name="'.$id.'_propertyform_id_temp'.$key.'" style="width: auto; margin: 2px 0px;">';
					$param['w_property_values'][$key]	= explode('###',$param['w_property_values'][$key]);
					$param['w_property_values'][$key]	= array_slice($param['w_property_values'][$key],1, count($param['w_property_values'][$key]));   
					foreach($param['w_property_values'][$key] as $subkey => $property_value)
					{
						$rep.='<option id="'.$id.'_'.$key.'_option'.$subkey.'" value="'.$property_value.'">'.$property_value.'</option>';
					}
					$rep.='</select></span>';
					}
					
					$rep.='</div></div></div>';
				
					break;
				}
				case 'type_paypal_shipping':
				{
					
					$params_names=array('w_field_label_size','w_field_label_pos','w_flow','w_choices','w_choices_price','w_choices_checked','w_required','w_randomize','w_allow_other','w_allow_other_num','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");	
					$param['w_choices']	= explode('***',$param['w_choices']);
					   
					$param['w_choices_price']	= explode('***',$param['w_choices_price']);
					
					$param['w_choices_checked']	= explode('***',$param['w_choices_checked']);
					  
					
					foreach($param['w_choices_checked'] as $key => $choices_checked )
					{
						if($choices_checked=='true')
							$param['w_choices_checked'][$key]='checked="checked"';
						else
							$param['w_choices_checked'][$key]='';
					}
					
					$rep='<div id="wdform_field'.$id.'" type="type_paypal_shipping" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label" style="vertical-align: top;">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required" style="vertical-align: top;">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align:top;"><input type="hidden" value="type_paypal_shipping" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_randomize'].'" name="'.$id.'_randomizeform_id_temp" id="'.$id.'_randomizeform_id_temp"><input type="hidden" value="'.$param['w_allow_other'].'" name="'.$id.'_allow_otherform_id_temp" id="'.$id.'_allow_otherform_id_temp"><input type="hidden" value="'.$param['w_allow_other_num'].'" name="'.$id.'_allow_other_numform_id_temp" id="'.$id.'_allow_other_numform_id_temp"><div style="display: table;"><div id="'.$id.'_table_little" style="display: table-row-group;">';
				
				if($param['w_flow']=='hor')
				{
					$rep.= '<div id="'.$id.'_hor" style="display: table-row;">';
					foreach($param['w_choices'] as $key => $choice)
					{
					if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==$key)
						$rep.='<div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="radio" other="1" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp" value="'.$param['w_choices_price'][$key].'" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;); show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div>';
					else
						$rep.='<div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="radio" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp" value="'.$param['w_choices_price'][$key].'" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div>';
					}
					$rep.= '</div>';
				}
				else
				{
					$rep.= '<div id="'.$id.'_table_little" style="display: table-row-group;">';
					foreach($param['w_choices'] as $key => $choice)
					{
					if($param['w_allow_other']=="yes" && $param['w_allow_other_num']==$key)
						$rep.='<div id="'.$id.'_element_tr'.$key.'" style="display: table-row;"><div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="radio" value="'.$param['w_choices_price'][$key].'" other="1" id="'.$id.'_elementform_id_temp'.$key.'" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;); show_other_input(&quot;'.$id.'&quot;,&quot;form_id_temp&quot;);" name="'.$id.'_elementform_id_temp" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div></div>';
					else
						$rep.='<div id="'.$id.'_element_tr'.$key.'" style="display: table-row;"><div valign="top" id="'.$id.'_td_little'.$key.'" idi="'.$key.'" style="display: table-cell;"><input type="radio" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp" value="'.$param['w_choices_price'][$key].'" onclick="set_default(&quot;'.$id.'&quot;,&quot;'.$key.'&quot;,&quot;form_id_temp&quot;)" '.$param['w_choices_checked'][$key].' '.$param['attributes'].'><label id="'.$id.'_label_element'.$key.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$key.'">'.$choice.'</label></div></div>';					}
					$rep.= '</div>';
				}
					$rep.='</div></div>';

					$rep.='</div></div>';
				
					break;
				}
				case 'type_paypal_total':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
				
								
									
					$rep='<div id="wdform_field'.$id.'" type="type_paypal_total" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label">'.$label.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_paypal_total" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><div id="'.$id.'paypal_totalform_id_temp" class="wdform_paypal_total paypal_totalform_id_temp"><input type="hidden" value="" name="'.$id.'_paypal_totalform_id_temp" class="input_paypal_totalform_id_temp"><div id="'.$id.'div_totalform_id_temp" class="div_totalform_id_temp" style="margin-bottom: 10px;"><!--repstart-->$300<!--repend--></div><div id="'.$id.'paypal_productsform_id_temp" class="paypal_productsform_id_temp" style="border-spacing: 2px;"><div style="border-spacing: 2px;"><!--repstart-->product 1 $100<!--repend--></div><div style="border-spacing: 2px;"><!--repstart-->product 2 $200<!--repend--></div></div><div id="'.$id.'paypal_taxform_id_temp" class="paypal_taxform_id_temp" style="border-spacing: 2px; margin-top: 7px;"></div></div></div></div>';
				
				
					break;
				}

				case 'type_star_rating':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_field_label_col','w_star_amount','w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");
					
	
				$images = '';	
					for($i=0; $i<$param['w_star_amount']; $i++)
					{
						$images .= '<img id="'.$id.'_star_'.$i.'" src="components/com_formmaker/images/star.png" onmouseover="change_src('.$i.','.$id.',&quot;form_id_temp&quot;)" onmouseout="reset_src('.$i.','.$id.')" onclick="select_star_rating('.$i.','.$id.', &quot;form_id_temp&quot;)">';
					}
					
					$rep ='<div id="wdform_field'.$id.'" type="type_star_rating" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_star_rating" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_star_amount'].'" id="'.$id.'_star_amountform_id_temp" name="'.$id.'_star_amountform_id_temp"><input type="hidden" value="'.$param['w_field_label_col'].'" name="'.$id.'_star_colorform_id_temp" id="'.$id.'_star_colorform_id_temp"><div id="'.$id.'_elementform_id_temp" class="wdform_stars" '.$param['attributes'].'>'.$images.'</div></div></div>';
					
					
					break;
				}
				case 'type_scale_rating':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_mini_labels','w_scale_amount','w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");
					
					$w_mini_labels = explode('***',$param['w_mini_labels']);
					
					$numbers = '';	
					for($i=1; $i<=$param['w_scale_amount']; $i++)
					{
						$numbers .= '<div id="'.$id.'_scale_td1_'.$i.'form_id_temp" style="text-align: center; display: table-cell;"><span>'.$i.'</span></div>';
					}
					
										
					$radio_buttons = '';	
					for($k=1; $k<=$param['w_scale_amount']; $k++)
					{
						$radio_buttons .= '<div id="'.$id.'_scale_td2_'.$k.'form_id_temp" style="display: table-cell;"><input id="'.$id.'_scale_radioform_id_temp_'.$k.'" name="'.$id.'_scale_radioform_id_temp" value="'.$k.'" type="radio"></div>';
					}
					
					$rep ='<div id="wdform_field'.$id.'" type="type_scale_rating" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align: top; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_scale_rating" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_scale_amount'].'" id="'.$id.'_scale_amountform_id_temp" name="'.$id.'_scale_amountform_id_temp"><div id="'.$id.'_elementform_id_temp" '.$param['attributes'].'><label class="mini_label" id="'.$id.'_mini_label_worst" style="position: relative; top: 6px; font-size: 11px; display: inline-table;">'.$w_mini_labels[0].'</label><div id="'.$id.'_scale_tableform_id_temp" style="display: inline-table;"><div id="'.$id.'_scale_tr1form_id_temp" style="display: table-row;">'.$numbers.'</div><div id="'.$id.'_scale_tr2form_id_temp" style="display: table-row;">'.$radio_buttons.'</div></div><label class="mini_label" id="'.$id.'_mini_label_best" style="position: relative; top: 6px; font-size: 11px; display: inline-table;">'.$w_mini_labels[1].'</label></div></div></div>';
							
					break;
				}
				case 'type_spinner':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_field_width','w_field_min_value','w_field_max_value', 'w_field_step', 'w_field_value', 'w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");
					
					 
					$rep ='<div id="wdform_field'.$id.'" type="type_spinner" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_spinner" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_field_width'].'" name="'.$id.'_spinner_widthform_id_temp" id="'.$id.'_spinner_widthform_id_temp"><input type="hidden" value="'.$param['w_field_min_value'].'" id="'.$id.'_min_valueform_id_temp" name="'.$id.'_min_valueform_id_temp"><input type="hidden" value="'.$param['w_field_max_value'].'" name="'.$id.'_max_valueform_id_temp" id="'.$id.'_max_valueform_id_temp"><input type="hidden" value="'.$param['w_field_step'].'" name="'.$id.'_stepform_id_temp" id="'.$id.'_stepform_id_temp"><input type="" value="'.($param['w_field_value']!= 'null' ? $param['w_field_value'] : '').'" name="'.$id.'_elementform_id_temp" id="'.$id.'_elementform_id_temp" onkeypress="return check_isnum_or_minus(event)" style="width: '.$param['w_field_width'].'px;" '.$param['attributes'].'></div></div>';
					
					
							
					break;
				}
				case 'type_slider':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_field_width','w_field_min_value','w_field_max_value', 'w_field_value', 'w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");
					
					 
					$rep ='<div id="wdform_field'.$id.'" type="type_slider" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align: top; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_slider" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_field_width'].'" name="'.$id.'_slider_widthform_id_temp" id="'.$id.'_slider_widthform_id_temp"><input type="hidden" value="'.$param['w_field_min_value'].'" id="'.$id.'_slider_min_valueform_id_temp" name="'.$id.'_slider_min_valueform_id_temp"><input type="hidden" value="'.$param['w_field_max_value'].'" id="'.$id.'_slider_max_valueform_id_temp" name="'.$id.'_slider_max_valueform_id_temp"><input type="hidden" value="'.$param['w_field_value'].'" id="'.$id.'_slider_valueform_id_temp" name="'.$id.'_slider_valueform_id_temp"><div id="'.$id.'_slider_tableform_id_temp"><div><div id="'.$id.'_slider_td1form_id_temp"><div name="'.$id.'_elementform_id_temp" id="'.$id.'_elementform_id_temp" style="width: '.$param['w_field_width'].'px;" '.$param['attributes'].'"></div></div></div><div><div align="left" id="'.$id.'_slider_td2form_id_temp" style="display: inline-table; width: 33.3%; text-align:left;"><span id="'.$id.'_element_minform_id_temp" class="wd_form_label">'.$param['w_field_min_value'].'</span></div><div align="right" id="'.$id.'_slider_td3form_id_temp" style="display: inline-table; width: 33.3%; text-align: center;"><span id="'.$id.'_element_valueform_id_temp" class="wd_form_label">'.$param['w_field_value'].'</span></div><div align="right" id="'.$id.'_slider_td4form_id_temp" style="display: inline-table; width: 33.3%; text-align:right;"><span id="'.$id.'_element_maxform_id_temp" class="wd_form_label">'.$param['w_field_max_value'].'</span></div></div></div></div></div>';
							
					break;
				}
				case 'type_range':
				{
					$params_names=array('w_field_label_size','w_field_label_pos','w_field_range_width','w_field_range_step','w_field_value1', 'w_field_value2', 'w_mini_labels', 'w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");
					
					$w_mini_labels = explode('***',$param['w_mini_labels']);
					
					$rep ='<div id="wdform_field'.$id.'" type="type_range" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_range" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_field_range_width'].'" name="'.$id.'_range_widthform_id_temp" id="'.$id.'_range_widthform_id_temp"><input type="hidden" value="'.$param['w_field_range_step'].'" name="'.$id.'_range_stepform_id_temp" id="'.$id.'_range_stepform_id_temp"><div id="'.$id.'_elemet_table_littleform_id_temp" style="display: table;"><div style="display: table-row;"><div valign="middle" align="left" style="display: table-cell;"><input type="" value="'.($param['w_field_value1']!= 'null' ? $param['w_field_value1'] : '').'" name="'.$id.'_elementform_id_temp0" id="'.$id.'_elementform_id_temp0" onkeypress="return check_isnum_or_minus(event)" style="width: '.$param['w_field_range_width'].'px;"  '.$param['attributes'].'></div><div valign="middle" align="left" style="display: table-cell; padding-left: 4px;"><input type="" value="'.($param['w_field_value2']!= 'null' ? $param['w_field_value2'] : '').'" name="'.$id.'_elementform_id_temp1" id="'.$id.'_elementform_id_temp1" onkeypress="return check_isnum_or_minus(event)" style="width: '.$param['w_field_range_width'].'px;" '.$param['attributes'].'></div></div><div style="display: table-row;"><div valign="top" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_from">'.$w_mini_labels[0].'</label></div><div valign="top" align="left" style="display: table-cell;"><label class="mini_label" id="'.$id.'_mini_label_to">'.$w_mini_labels[1].'</label></div></div></div></div></div>';
											
					break;
				}
				case 'type_grading':
				{
					$params_names=array('w_field_label_size','w_field_label_pos', 'w_items', 'w_total', 'w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");
					
					$w_items = explode('***',$param['w_items']);
					
					
					$grading_items ='';
					
					for($i=0; $i<count($w_items); $i++)
					{
						$grading_items .= '<div id="'.$id.'_element_div'.$i.'" class="grading"><input type="text" id="'.$id.'_elementform_id_temp'.$i.'" name="'.$id.'_elementform_id_temp'.$i.'" onkeypress="return check_isnum_or_minus(event)" value=""  onkeyup="sum_grading_values('.$id.',&quot;form_id_temp&quot;)" onchange="sum_grading_values('.$id.',&quot;form_id_temp&quot;)" '.$param['attributes'].' style="width: 80px !important; margin-bottom: 5px;"><label id="'.$id.'_label_elementform_id_temp'.$i.'" class="ch-rad-label">'.$w_items[$i].'</label></div>';
					}
									
					$rep ='<div id="wdform_field'.$id.'" type="type_grading" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; vertical-align: top; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_grading" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_total'].'" name="'.$id.'_grading_totalform_id_temp" id="'.$id.'_grading_totalform_id_temp"><div id="'.$id.'_elementform_id_temp">'.$grading_items.'<div id="'.$id.'_element_total_divform_id_temp" class="grading_div">Total:<span id="'.$id.'_sum_elementform_id_temp" name="'.$id.'_sum_elementform_id_temp">0</span>/<span id="'.$id.'_total_elementform_id_temp" name="'.$id.'_total_elementform_id_temp">'.$param['w_total'].'</span><span id="'.$id.'_text_elementform_id_temp" name="'.$id.'_text_elementform_id_temp"></span></div></div></div></div>';
											
					break;
				}
				case 'type_matrix':
				{
					$params_names=array('w_field_label_size','w_field_label_pos', 'w_field_input_type', 'w_rows', 'w_columns', 'w_required','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}

					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					
					$param['w_field_label_pos'] = ($param['w_field_label_pos']=="left" ? "table-cell" : "block");	
					$required_sym = ($param['w_required']=="yes" ? " *" : "");
					
					$w_rows = explode('***',$param['w_rows']);
					$w_columns = explode('***',$param['w_columns']);
					
					
					$column_labels ='';
				
					for($i=1; $i<count($w_columns); $i++)
					{
						$column_labels .= '<div id="'.$id.'_element_td0_'.$i.'" class="matrix_" style="display: table-cell;"><label id="'.$id.'_label_elementform_id_temp0_'.$i.'" name="'.$id.'_label_elementform_id_temp0_'.$i.'" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$i.'" value="'.$w_columns[$i].'">'.$w_columns[$i].'</label></div>';
					}
					
					$rows_columns = '';
					
					for($i=1; $i<count($w_rows); $i++)
					{
					
						$rows_columns .= '<div id="'.$id.'_element_tr'.$i.'" style="display: table-row;"><div id="'.$id.'_element_td'.$i.'_0" class="matrix_" style="display: table-cell;"><label id="'.$id.'_label_elementform_id_temp'.$i.'_0" class="ch-rad-label" for="'.$id.'_elementform_id_temp'.$i.'" value="'.$w_rows[$i].'">'.$w_rows[$i].'</label></div>';
						
						
						for($k=1; $k<count($w_columns); $k++)
						{
							if($param['w_field_input_type']=='radio')
								$rows_columns .= '<div id="'.$id.'_element_td'.$i.'_'.$k.'" style="text-align: center; display: table-cell;  padding: 5px 0 0 5px;"><input id="'.$id.'_input_elementform_id_temp'.$i.'_'.$k.'" align="center" size="14" type="radio" name="'.$id.'_input_elementform_id_temp'.$i.'" value="'.$i.'_'.$k.'"></div>';
							else
								if($param['w_field_input_type']=='checkbox')
									$rows_columns .= '<div id="'.$id.'_element_td'.$i.'_'.$k.'" style="text-align: center; display: table-cell;  padding: 5px 0 0 5px;"><input id="'.$id.'_input_elementform_id_temp'.$i.'_'.$k.'" align="center" size="14" type="checkbox" name="'.$id.'_input_elementform_id_temp'.$i.'_'.$k.'" value="1"></div>';
								else
									if($param['w_field_input_type']=='text')
										$rows_columns .= '<div id="'.$id.'_element_td'.$i.'_'.$k.'" style="text-align: center; display: table-cell; padding: 5px 0 0 5px;"><input id="'.$id.'_input_elementform_id_temp'.$i.'_'.$k.'" align="center" type="text" name="'.$id.'_input_elementform_id_temp'.$i.'_'.$k.'" value="" style="width:100px"></div>';
									else
										if($param['w_field_input_type']=='select')
											$rows_columns .= '<div id="'.$id.'_element_td'.$i.'_'.$k.'" style="text-align: center; display: table-cell; padding: 5px 0 0 5px;"><select id="'.$id.'_select_yes_noform_id_temp'.$i.'_'.$k.'" name="'.$id.'_select_yes_noform_id_temp'.$i.'_'.$k.'" style="width:80px"><option value=""> </option><option value="yes">Yes</option><option value="no">No</option></select></div>';
								
						}
							
						$rows_columns .= '</div>';	
					}
						
					
						
					$rep ='<div id="wdform_field'.$id.'" type="type_matrix" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].'; width: '.$param['w_field_label_size'].'px;"><span id="'.$id.'_element_labelform_id_temp" class="wd_form_label">'.$label.'</span><span id="'.$id.'_required_elementform_id_temp" class="required">'.$required_sym.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: '.$param['w_field_label_pos'].';"><input type="hidden" value="type_matrix" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><input type="hidden" value="'.$param['w_required'].'" name="'.$id.'_requiredform_id_temp" id="'.$id.'_requiredform_id_temp"><input type="hidden" value="'.$param['w_field_input_type'].'" name="'.$id.'_input_typeform_id_temp" id="'.$id.'_input_typeform_id_temp"><div id="'.$id.'_elementform_id_temp" style="display: table;" '.$param['attributes'].'><div id="'.$id.'_table_little" style="display: table-row-group;"><div id="'.$id.'_element_tr0" style="display: table-row;"><div id="'.$id.'_element_td0_0" style="display: table-cell;"></div>'.$column_labels.'</div>'.$rows_columns.'</div></div></div></div>';
											
					break;
				}
			
				case 'type_submit_reset':
				{
					
					$params_names=array('w_submit_title','w_reset_title','w_class','w_act');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}
					
					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}

					$param['w_act'] = ($param['w_act']=="false" ? 'style="display: none;"' : "");	
					
					$rep='<div id="wdform_field'.$id.'" type="type_submit_reset" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: table-cell;"><span id="'.$id.'_element_labelform_id_temp" style="display: none;">type_submit_reset_'.$id.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: table-cell;"><input type="hidden" value="type_submit_reset" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp"><button type="button" class="button-submit" id="'.$id.'_element_submitform_id_temp" value="'.$param['w_submit_title'].'" onclick="check_required(&quot;submit&quot;, &quot;form_id_temp&quot;);" '.$param['attributes'].'>'.$param['w_submit_title'].'</button><button type="button" class="button-reset" id="'.$id.'_element_resetform_id_temp" value="'.$param['w_reset_title'].'" onclick="check_required(&quot;reset&quot;);" '.$param['w_act'].' '.$param['attributes'].'>'.$param['w_reset_title'].'</button></div></div>';
				
					break;
				}
				case 'type_button':
				{
					
					$params_names=array('w_title','w_func','w_class');
					$temp=$params;
					foreach($params_names as $params_name )
					{	
						$temp=explode('*:*'.$params_name.'*:*',$temp);
						$param[$params_name] = $temp[0];
						$temp=$temp[1];
					}
					
					if($temp)
					{	
						$temp	=explode('*:*w_attr_name*:*',$temp);
						$attrs	= array_slice($temp,0, count($temp)-1);   
						foreach($attrs as $attr)
							$param['attributes'] = $param['attributes'].' add_'.$attr;
					}
					
					$param['w_title']	= explode('***',$param['w_title']);
					$param['w_func']	= explode('***',$param['w_func']);

					$rep.='<div id="wdform_field'.$id.'" type="type_button" class="wdform_field" style="display: table-cell;"><div align="left" id="'.$id.'_label_sectionform_id_temp" class="'.$param['w_class'].'" style="display: table-cell;"><span id="'.$id.'_element_labelform_id_temp" style="display: none;">button_'.$id.'</span></div><div align="left" id="'.$id.'_element_sectionform_id_temp" class="'.$param['w_class'].'" style="display: table-cell;"><input type="hidden" value="type_button" name="'.$id.'_typeform_id_temp" id="'.$id.'_typeform_id_temp">';
					foreach($param['w_title'] as $key => $title)
					{
					$rep.='<button type="button" id="'.$id.'_elementform_id_temp'.$key.'" name="'.$id.'_elementform_id_temp'.$key.'" value="'.$title.'" onclick="'.$param['w_func'][$key].'" '.$param['attributes'].'>'.$title.'</button>';				
					}				
					$rep.='</div></div>';
					break;
				}
			}
			
			///// Arrows
			
			switch($type)
			{
				case 'type_section_break':
				{
					$rep =$rep.'<div id="wdform_arrows'.$id.'" class="wdform_arrows"><div id="X_'.$id.'" class="element_toolbar"><img src="components/com_formmaker/images/delete_el.png" title="Remove the field" onclick="remove_section_break(&quot;'.$id.'&quot;)"></div><div id="edit_'.$id.'" class="element_toolbar"><img src="components/com_formmaker/images/edit.png" title="Edit the field" onclick="edit(&quot;'.$id.'&quot;)"><span id="'.$id.'_element_labelform_id_temp" style="display: none;">custom_'.$id.'</span></div><div id="dublicate_'.$id.'" class="element_toolbar"><img src="components/com_formmaker/images/dublicate.png" title="Dublicate the field" onclick="dublicate(&quot;'.$id.'&quot;)"></div></div>';
					break;
				}
				
				case 'type_captcha':
				case 'type_recaptcha':
				{
					$rep =$rep.'<div id="wdform_arrows'.$id.'" class="wdform_arrows" style="display: table-cell;"><div id="X_'.$id.'" valign="middle" align="right" class="element_toolbar"><img src="components/com_formmaker/images/delete_el.png" title="Remove the field" onclick="remove_row(&quot;'.$id.'&quot;)"></div><div id="left_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/left.png" title="Move the field to the left" onclick="left_row(&quot;'.$id.'&quot;)"></div><div id="up_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/up.png" title="Move the field up" onclick="up_row(&quot;'.$id.'&quot;)"></div><div id="down_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/down.png" title="Move the field down" onclick="down_row(&quot;'.$id.'&quot;)"></div><div id="right_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/right.png" title="Move the field to the right" onclick="right_row(&quot;'.$id.'&quot;)"></div><div id="edit_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/edit.png" title="Edit the field" onclick="edit(&quot;'.$id.'&quot;)"></div><div id="page_up_'.$id.'" valign="middle" class="element_toolbar"></div><div id="page_up_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/page_up.png" title="Move the field to the upper page" onclick="page_up(&quot;'.$id.'&quot;)"></div><div id="page_down_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/page_down.png" title="Move the field to the lower page" onclick="page_down(&quot;'.$id.'&quot;)"></div></div>';
					break;
				}
				
				default :
				{
					$rep =$rep.'<div id="wdform_arrows'.$id.'" class="wdform_arrows" style="display: table-cell;"><div id="X_'.$id.'" valign="middle" align="right" class="element_toolbar"><img src="components/com_formmaker/images/delete_el.png" title="Remove the field" onclick="remove_row(&quot;'.$id.'&quot;)" onmouseover="chnage_icons_src(this,&quot;delete_el&quot;)" onmouseout="chnage_icons_src(this,&quot;delete_el&quot;)"></div><div id="left_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/left.png" title="Move the field to the left" onclick="left_row(&quot;'.$id.'&quot;)" onmouseover="chnage_icons_src(this,&quot;left&quot;)" onmouseout="chnage_icons_src(this,&quot;left&quot;)"></div><div id="up_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/up.png" title="Move the field up" onclick="up_row(&quot;'.$id.'&quot;)" onmouseover="chnage_icons_src(this,&quot;up&quot;)" onmouseout="chnage_icons_src(this,&quot;up&quot;)"></div><div id="down_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/down.png" title="Move the field down" onclick="down_row(&quot;'.$id.'&quot;)"  onmouseover="chnage_icons_src(this,&quot;down&quot;)" onmouseout="chnage_icons_src(this,&quot;down&quot;)"></div><div id="right_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/right.png" title="Move the field to the right" onclick="right_row(&quot;'.$id.'&quot;)" onmouseover="chnage_icons_src(this,&quot;right&quot;)" onmouseout="chnage_icons_src(this,&quot;right&quot;)"></div><div id="edit_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/edit.png" title="Edit the field" onclick="edit(&quot;'.$id.'&quot;)" onmouseover="chnage_icons_src(this,&quot;edit&quot;)" onmouseout="chnage_icons_src(this,&quot;edit&quot;)"></div><div id="dublicate_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/dublicate.png" title="Dublicate the field" onclick="dublicate(&quot;'.$id.'&quot;)"  onmouseover="chnage_icons_src(this,&quot;dublicate&quot;)" onmouseout="chnage_icons_src(this,&quot;dublicate&quot;)"></div><div id="page_up_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/page_up.png" title="Move the field to the upper page" onclick="page_up(&quot;'.$id.'&quot;)" onmouseover="chnage_icons_src(this,&quot;page_up&quot;)" onmouseout="chnage_icons_src(this,&quot;page_up&quot;)"></div><div id="page_down_'.$id.'" valign="middle" class="element_toolbar"><img src="components/com_formmaker/images/page_down.png" title="Move the field to the lower page" onclick="page_down(&quot;'.$id.'&quot;)" onmouseover="chnage_icons_src(this,&quot;page_down&quot;)" onmouseout="chnage_icons_src(this,&quot;page_down&quot;)"></div></div>';
					break;
				}
				
			}
			
			$form=str_replace('%'.$id.' - '.$labels[$ids_key].'%', $rep, $form);
		}
		
	}
	$row->form_front=$form;
	HTML_contact::edit($row, $labels2);
	
	}
	else		
	HTML_contact::edit_old($row, $labels2);
}


function copy_form()
{

	$db		= JFactory::getDBO();
	$cid 	= JRequest::getVar('cid', array(0), '', 'array');
	JArrayHelper::toInteger($cid, array(0));
	$id 	= $cid[0];
	$row = JTable::getInstance('formmaker', 'Table');

	// load the row from the db table
	$row->load( $id);
		
	$mainframe = JFactory::getApplication();
	
	$row->id='';
	$new=true;

	if(!$row->store()){

		JError::raiseError(500, $row->getError() );

	}
	
	if($new)
	{
		$db = JFactory::getDBO();
		$db->setQuery("INSERT INTO #__formmaker_views (form_id, views) VALUES('".$row->id."', 0)" ); 
		$db->query();
		if ($db->getErrorNum())
		{
			echo $db->stderr();
			return false;
		}

	}
	
		$msg = 'The form has been saved successfully.';

		$link = 'index.php?option=com_formmaker';
		$mainframe->redirect($link, $msg, 'message');
}



function editSubmit(){

	$db		= JFactory::getDBO();
	$cid 	= JRequest::getVar('cid', array(0), '', 'array');
	JArrayHelper::toInteger($cid, array(0));
	$id 	= $cid[0];
	
	$query = "SELECT * FROM #__formmaker_submits WHERE group_id=".$id;
	$db->setQuery( $query);
	$rows = $db->loadObjectList();
	if($db->getErrorNum()){echo $db->stderr();return false;}
	
	$form = JTable::getInstance('formmaker', 'Table');
	$form->load( $rows[0]->form_id);
	
		$label_id= array();
		$label_order_original= array();
		$label_type= array();
		$ispaypal=strpos($form->label_order, 'type_paypal_');
		if($form->paypal_mode==1)
			if($ispaypal)
				$form->label_order=$form->label_order."0#**id**#Payment Status#**label**#type_paypal_payment_status#****#";
		
		$label_all	= explode('#****#',$form->label_order);
		$label_all 	= array_slice($label_all,0, count($label_all)-1);   
		
		
		
		foreach($label_all as $key => $label_each) 
		{
			$label_id_each=explode('#**id**#',$label_each);
			array_push($label_id, $label_id_each[0]);
			
			$label_oder_each=explode('#**label**#', $label_id_each[1]);
			array_push($label_order_original, $label_oder_each[0]);
			array_push($label_type, $label_oder_each[1]);

		
			
		}
	 
	// display function 

	HTML_contact::editSubmit($rows, $label_id ,$label_order_original,$label_type,$ispaypal);

}

function saveSubmit($task){

	$mainframe = JFactory::getApplication();
	$db		= JFactory::getDBO();
	$id 	= JRequest::getVar('id');
	$date 	= JRequest::getVar('date');
	$ip 	= JRequest::getVar('ip');
	
	$form_id 	= JRequest::getVar('form_id');
	$form = JTable::getInstance('formmaker', 'Table');
	$form->load($form_id);
		$label_id= array();
		$label_order_original= array();
		$label_type= array();
		if(strpos($form->label_order, 'type_paypal_'))
		{
			$form->label_order=$form->label_order."0#**id**#Payment Status#**label**#type_paypal_payment_status#****#";
		}

		
		$label_all	= explode('#****#',$form->label_order);
		$label_all 	= array_slice($label_all,0, count($label_all)-1);   
		
		
		
		foreach($label_all as $key => $label_each) 
		{
			$label_id_each=explode('#**id**#',$label_each);
			array_push($label_id, $label_id_each[0]);
			
			$label_oder_each=explode('#**label**#', $label_id_each[1]);
			array_push($label_order_original, $label_oder_each[0]);
			array_push($label_type, $label_oder_each[1]);

		
			
		}
	
	foreach($label_id as $key => $label_id_1)
	{
		$element_value=JRequest::getVar("submission_".$label_id_1);
		if(isset($element_value))
		{
			$query = "SELECT id FROM #__formmaker_submits WHERE group_id='".$id."' AND element_label='".$label_id_1."'";
			$db->setQuery( $query);
			$result=$db->loadResult();
			if($db->getErrorNum()){	echo $db->stderr();	return false;}
			
			if($label_type[$key]=='type_file_upload')
				$element_value=$element_value."*@@url@@*";
			
			if($result)
			{
				$query = "UPDATE #__formmaker_submits SET `element_value`='".$element_value."' WHERE group_id='".$id."' AND element_label='".$label_id_1."'";
				$db->setQuery( $query);
				$db->query();
				if($db->getErrorNum()){	echo $db->stderr();	return false;}
			}
			else
			{
				$query = "INSERT INTO #__formmaker_submits (form_id, element_label, element_value, group_id, date, ip) VALUES('".$form_id."', '".$label_id_1."', '".$element_value."','".$id."', '".$date."', '".$ip."')" ;
				$db->setQuery( $query);
				$db->query();
				if($db->getErrorNum()){	echo $db->stderr();	return false;}
			}
		}
		else
		{
			$element_value_ch=JRequest::getVar("submission_".$label_id_1.'_0');
			if(isset($element_value_ch))
			{
				for($z=0; $z<21; $z++ )
				{
					$element_value_ch=JRequest::getVar("submission_".$label_id_1.'_'.$z);
					if(isset($element_value_ch))
						$element_value=$element_value.$element_value_ch.'***br***';
					else
						break;
				}
				$query = "SELECT id FROM #__formmaker_submits WHERE group_id='".$id."' AND element_label='".$label_id_1."'";
				$db->setQuery( $query);
				$result=$db->loadResult();
				if($db->getErrorNum()){	echo $db->stderr();	return false;}
				
				if($result)
				{
					$query = "UPDATE #__formmaker_submits SET `element_value`='".$element_value."' WHERE group_id='".$id."' AND element_label='".$label_id_1."'";
					$db->setQuery( $query);
					$db->query();
					if($db->getErrorNum()){	echo $db->stderr();	return false;}
				}
				else
				{
					$query = "INSERT INTO #__formmaker_submits (form_id, element_label, element_value, group_id, date, ip) VALUES('".$form_id."', '".$label_id_1."', '".$element_value."','".$id."', '".$date."', '".$ip."')" ;
					$db->setQuery( $query);
					$db->query();
					if($db->getErrorNum()){	echo $db->stderr();	return false;}
				}
				
			}

		}
		
		
	}
	switch ($task)
	{
		case 'save_submit':
		$msg = 'Submission has been saved successfully.';
		$link ='index.php?option=com_formmaker&task=submits';
		break;
		case 'apply_submit':
		default:
		$msg = 'Submission has been saved successfully.';
		$link ='index.php?option=com_formmaker&task=edit_submit&cid[]='.$id;
		break;
	}
	
	$mainframe->redirect($link, $msg, 'message');

}

function form_options_temp(){
	$mainframe = JFactory::getApplication();
	$row_id=save('return_id');
	$link = 'index.php?option=com_formmaker&task=form_options&cid[]='.$row_id;

	$mainframe->redirect($link);

}
function form_layout_temp(){
	
	$mainframe = JFactory::getApplication();
	$row_id=save('return_id');
	$link = 'index.php?option=com_formmaker&task=form_layout&cid[]='.$row_id;
	$mainframe->redirect($link);
}

function removeSubmit(){

  $mainframe = JFactory::getApplication();

  // Initialize variables	

  $db = JFactory::getDBO();

  // Define cid array variable

  $form_id = JRequest::getVar( 'form_id');
  $cid = JRequest::getVar( 'cid' , array() , '' , 'array' );

  // Make sure cid array variable content integer format

  JArrayHelper::toInteger($cid);

  // If any item selected

  if (count( $cid )) {


    $cids = implode( ',', $cid );

    // Create sql statement

    $query = 'DELETE FROM #__formmaker_submits'

    . ' WHERE group_id IN ( '. $cids .' )'

    ;

    // Execute query

    $db->setQuery( $query );

    if (!$db->query()) {

      echo "<script> alert('".$db->getErrorMsg(true)."'); 

      window.history.go(-1); </script>\n";

    }
	
    $query = 'DELETE FROM #__formmaker_sessions'

    . ' WHERE group_id IN ( '. $cids .' )'

    ;

    // Execute query

    $db->setQuery( $query );

    if (!$db->query()) {

      echo "<script> alert('".$db->getErrorMsg(true)."'); 

      window.history.go(-1); </script>\n";

    }
	
	

  }

  // After all, redirect again to frontpage

  $mainframe->redirect( "index.php?option=com_formmaker&task=submits&form_id=".$form_id );


}

function remove(){



  $mainframe = JFactory::getApplication();

  // Initialize variables	

  $db = JFactory::getDBO();

  // Define cid array variable

  $cid = JRequest::getVar( 'cid' , array() , '' , 'array' );

  // Make sure cid array variable content integer format

  JArrayHelper::toInteger($cid);



  // If any item selected

  if (count( $cid )) {

    // Prepare sql statement, if cid array more than one, 

    // will be "cid1, cid2, ..."

    $cids = implode( ',', $cid );

    // Create sql statement

    $query = 'DELETE FROM #__formmaker' . ' WHERE id IN ( '. $cids .' )'  ;

    // Execute query

    $db->setQuery( $query );

    if (!$db->query()) {

      echo "<script> alert('".$db->getErrorMsg(true)."'); 

      window.history.go(-1); </script>\n";

    }
	
    $query = 'DELETE FROM #__formmaker_views' . ' WHERE form_id IN ( '. $cids .' )'  ;

    // Execute query

    $db->setQuery( $query );

    if (!$db->query()) {

      echo "<script> alert('".$db->getErrorMsg(true)."'); 

      window.history.go(-1); </script>\n";

    }
	

  }

	remove_submits_all( $cids );

  // After all, redirect again to frontpage

  $mainframe->redirect( "index.php?option=com_formmaker" );

}

function remove_submits_all( $cids ){
  $db = JFactory::getDBO();
	$query = 'DELETE FROM #__formmaker_submits'

    . ' WHERE form_id IN ( '. $cids .' )'

    ;

    // Execute query

    $db->setQuery( $query );

    if (!$db->query()) {

      echo "<script> alert('".$db->getErrorMsg(true)."'); 

      window.history.go(-1); </script>\n";

    }

}

function change( $state=0 ){

  $mainframe = JFactory::getApplication();



  // Initialize variables

  $db 	= JFactory::getDBO();



  // define variable $cid from GET

  $cid = JRequest::getVar( 'cid' , array() , '' , 'array' );	

  JArrayHelper::toInteger($cid);



  // Check there is/are item that will be changed. 

  //If not, show the error.

  if (count( $cid ) < 1) {

    $action = $state ? 'publish' : 'unpublish';

    JError::raiseError(500, JText::_( 'Select an item 

    to' .$action, true ) );

  }



  // Prepare sql statement, if cid more than one, 

  // it will be "cid1, cid2, cid3, ..."

  $cids = implode( ',', $cid );



  $query = 'UPDATE #__formmaker'

  . ' SET published = ' . (int) $state

  . ' WHERE id IN ( '. $cids .' )'

  ;

  // Execute query

  $db->setQuery( $query );

  if (!$db->query()) {

    JError::raiseError(500, $db->getErrorMsg() );

  }



  if (count( $cid ) == 1) {

    $row = JTable::getInstance('formmaker', 'Table');

    $row->checkin( intval( $cid[0] ) );

  }



  // After all, redirect to front page

  $mainframe->redirect( 'index.php?option=com_formmaker' );

}

function cancel(){

  $mainframe = JFactory::getApplication();

  $mainframe->redirect( 'index.php?option=com_formmaker&task=forms' );



}

function cancel_themes(){

  $mainframe = JFactory::getApplication();
  $mainframe->redirect( 'index.php?option=com_formmaker&task=themes' );
}

function cancelSecondary(){

	$mainframe = JFactory::getApplication();

	if(JRequest::getVar('id')==0)

	$link = 'index.php?option=com_formmaker&task=add';

	else

	$link = 'index.php?option=com_formmaker&task=edit&cid[]='.JRequest::getVar('id');

	$mainframe->redirect($link);

}

function cancelSubmit(){
	$mainframe = JFactory::getApplication();
	$link = 'index.php?option=com_formmaker&task=submits';
	$mainframe->redirect($link);

}
		
function select_article(){


	$mainframe = JFactory::getApplication();
	
	    $db = JFactory::getDBO();

	$option='com_formmaker';

	$filter_order= $mainframe-> getUserStateFromRequest( $option.'filter_order', 'filter_order','id','cmd' );
	$filter_order_Dir= $mainframe-> getUserStateFromRequest( $option.'filter_order_Dir', 'filter_order_Dir','','word' );
	$filter_state = $mainframe->getUserStateFromRequest( $option.'filter_state', 'filter_state', '','word' );
	$search = $mainframe-> getUserStateFromRequest( $option.'search', 'search','','string' );
	$search = JString::strtolower( $search );
	$limit= $mainframe-> getUserStateFromRequest('global.list.limit', 'limit', $mainframe->getCfg('list_limit'), 'int');
	$limitstart= $mainframe-> getUserStateFromRequest($option.'.limitstart', 'limitstart', 0, 'int');

	$where = array();

	if ( $search ) {

		$where[] = 'title LIKE "%'.$db->escape($search).'%"';

	}	

	

	$where 		= ( count( $where ) ? ' WHERE ' . implode( ' AND ', $where ) : '' );

	if ($filter_order == 'id' or $filter_order == 'group_id' or $filter_order == 'group_id' or $filter_order == 'date' or $filter_order == 'ip'){

		$orderby 	= ' ORDER BY id';

	} else {

		$orderby 	= ' ORDER BY '. 

         $filter_order .' '. $filter_order_Dir .', id';

	}	

	

	// get the total number of records

	$query = 'SELECT COUNT(*)'

	. ' FROM #__content'

	. $where

	;

	$db->setQuery( $query );

	$total = $db->loadResult();



	jimport('joomla.html.pagination');

	$pageNav = new JPagination( $total, $limitstart, $limit );	

	$query = "SELECT * FROM #__content". $where. $orderby;

	$db->setQuery( $query, $pageNav->limitstart, $pageNav->limit );

	$rows = $db->loadObjectList();

	if($db->getErrorNum()){

		echo $db->stderr();

		return false;

	}



	// table ordering

	$lists['order_Dir']	= $filter_order_Dir;

	$lists['order']		= $filter_order;	



	// search filter	

        $lists['search']= $search;	

	

    // display function

	HTML_contact::select_article($rows, $pageNav, $lists);

}
?>