<?php 
  
 /**
 * @package Form Maker Lite
 * @author Web-Dorado
 * @copyright (C) 2011 Web-Dorado. All rights reserved.
 * @license GNU/GPLv3 http://www.gnu.org/licenses/gpl-3.0.html
 **/

defined('_JEXEC') or die('Restricted access');



class HTML_contact
{
    const first_css = ".wdform-page-and-images
{
font-size:14px;
font-weight:normal;
color:#000000;
width:100%;
}

.time_box
{
border-width:1px;
margin: 0px;
padding: 0px;
text-align:right;
width:30px;
vertical-align:middle
}

.mini_label
{
font-size:10px;
font-family: 'Lucida Grande', Tahoma, Arial, Verdana, sans-serif;
}

.ch-rad-label
{
display:inline;
margin-left:5px;
margin-right:15px;
float:none;
}

.label
{
border:none;
}


.td_am_pm_select
{
padding-left:5px;
}

.am_pm_select
{
width:62px !important;
vertical-align: middle;
}

.input_deactive
{
color:#999999;
font-style:italic;
border-width:1px;
margin: 0px;
padding: 0px
}

.input_active
{
color:#000000;
font-style:normal;
border-width:1px;
margin: 0px;
padding: 0px
}

.required
{
border:none;
color:red
}

.captcha_img
{
border-width:0px;
margin: 0px;
padding: 0px;
cursor:pointer;


}

.captcha_refresh
{
width:30px;
height:30px;
border-width:0px;
margin: 0px;
padding: 0px;
vertical-align:middle;
cursor:pointer;
background-image: url(components/com_formmaker/images/refresh_black.png);
}

.captcha_input
{
height:20px;
border-width:1px;
margin: 0px;
padding: 0px;
vertical-align:middle;
}

.file_upload
{
border-width:1px;
margin: 0px;
padding: 0px
}    

.page_deactive
{
border:1px solid black;
padding:4px 7px 4px 7px;
margin:4px;
cursor:pointer;
background-color:#DBDBDB;
}

.page_active
{
border:1px solid black;
padding:4px 7px 4px 7px;
margin:4px;
cursor:pointer;
background-color:#878787;
}

.page_percentage_active
{
padding:0px;
margin:0px;
border-spacing: 0px;
height:30px;
line-height:30px;
background-color:yellow;
border-radius:30px;
font-size:15px;
float:left;
text-align: right !important; 
}


.page_percentage_deactive
{
height:30px;
line-height:30px;
padding:5px;
border:1px solid black;
width:100%;
background-color:white;
border-radius:30px;
text-align: left !important; 
}

.page_numbers
{
font-size:11px;
}

.phone_area_code
{
width:50px;
}

.phone_number
{
width:100px;
}";

public static function form_layout(&$row, &$fields){
		JRequest::setVar( 'hidemainmenu', 1 );
		$document = JFactory::getDocument();
 		$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';

		$document->addScript($cmpnt_js_path.'/codemirror.js');
		$document->addScript($cmpnt_js_path.'/formatting.js');
		$document->addScript($cmpnt_js_path.'/css.js');
		$document->addScript($cmpnt_js_path.'/clike.js');
		$document->addScript($cmpnt_js_path.'/javascript.js');
		$document->addScript($cmpnt_js_path.'/jquery.min.js');
		$document->addScript($cmpnt_js_path.'/htmlmixed.js');
		$document->addScript($cmpnt_js_path.'/xml.js');
		$document->addScript($cmpnt_js_path.'/php.js');

		$document->addStyleSheet(JURI::root(true).'/administrator/components/com_formmaker/css/codemirror.css');
		
		?>
<script>

Joomla.submitbutton= function (pressbutton) {
	
	var form = document.adminForm;
	
	if (pressbutton == 'cancel') 
	{
		submitform( pressbutton );
		return;
	}
	
	if($('#autogen_layout').is(':checked'))
		$('#custom_front').val(custom_front.replace(/\s+/g, ' ').replace(/> </g, '><'));
	else
		$('#custom_front').val(editor.getValue().replace(/\s+/g, ' ').replace(/> </g, '><'));

	submitform( pressbutton );
}


var form_front ='<?php echo addslashes($row->form_front);?>';
var custom_front ='<?php echo addslashes($row->custom_front);?>';
if(custom_front=='')
	custom_front=form_front;

function insertAtCursor_form(myId, myLabel) 
{  
	if($('#autogen_layout').is(':checked'))
		return;
	myValue='<div wdid="'+myId+'" class="wdform_row">%'+myId+' - '+myLabel+'%</div>';

	line=editor.getCursor().line;
	ch=editor.getCursor().ch;
	
	text=editor.getLine(line);
	text1=text.substr(0,ch);
	text2=text.substr(ch);
	text=text1+myValue+text2;
	editor.setLine(line, text);
	editor.focus();
}


function autogen(status)
{

	if(status)
	{
		custom_front = editor.getValue();
		editor.setValue(form_front);
		editor.setOption('readOnly', true);
		autoFormat();
	}
	else
	{
		editor.setValue(custom_front);
		editor.setOption('readOnly', false);
		autoFormat();
	}
	
}

function autoFormat() {
	CodeMirror.commands["selectAll"](editor);
	editor.autoFormatRange(editor.getCursor(true), editor.getCursor(false));
	editor.scrollTo(0,0);
}

</script>        

<style>
button.submit {
	width: 100%;
	padding: 10px 0;
	cursor: pointer;
	margin: 0;
}
button.submit em {
	font-size: 11px;
	font-style: normal;
	color: #999;
}
label {
	cursor: pointer;
	display: inline-block;
}
.CodeMirror {
	border: 1px solid #ccc;
	font-size: 12px;
	margin-bottom: 6px;
	background: white;
}
.field_buttons
{
max-width:200px;
overflow: hidden;
white-space: nowrap;
text-overflow: ellipsis; 
word-break: break-all; 
word-wrap: break-word;
padding: 4px 15px;
font-weight:bold;
}

p
{
font-size: 14px;
font-family: segoe ui;
text-shadow: 0px 0px 1px rgb(202, 202, 202);
}
</style>
<h2> Description</h2>
<p>To customize the layout of the form fields uncheck the Auto-Generate Layout box and edit the HTML.</p>
<p>You can change positioning, add in-line styles and etc. Click on the provided buttons to add the corresponding field.<br/> This will add the following line:
 
 <b><span class="cm-tag">&lt;div</span> <span class="cm-attribute">wdid</span>=<span class="cm-string">"example_id"</span> <span class="cm-attribute">class</span>=<span class="cm-string">"wdform_row"</span><span class="cm-tag">&gt;</span>%example_id - Example%<span class="cm-tag">&lt;/div&gt;</span></b>
 
 , where <b><span class="cm-tag">&lt;div&gt;</span></b> is used to set a row.</p>
<p>To return to the default settings you should check Auto-Generate Layout box.</p>


<h3 style="color:red"> Notice</h3>
<p>Make sure not to publish the same field twice. This will cause malfunctioning of the form.</p>

<hr/>

<form action="index.php" method="post" name="adminForm" enctype="multipart/form-data">
	<label for="autogen_layout" style="font-size:20px; line-height:45px; margin-left:10px">Auto Generate Layout? </label>
	<input type="checkbox" value="1" name="autogen_layout" id="autogen_layout" <?php  if($row->autogen_layout) echo 'checked="checked"'?> />
    <input type="hidden" name="custom_front" id="custom_front" value="" />
    <input type="hidden" name="option" value="com_formmaker" />
    <input type="hidden" name="id" value="<?php echo $row->id?>" />
    <input type="hidden" name="cid[]" value="<?php echo $row->id; ?>" />
    <input type="hidden" name="task" value="" />
</form>


<br/>

<?php 
	$ids 	= $fields['ids'];
	$types 	= $fields['types'];
	$labels = $fields['labels'];
	



	foreach($ids as $key => $id)
	{
		if($types[$key]!="type_section_break")
		{
		?>
		<button class="btn" onClick="insertAtCursor_form('<?php echo $ids[$key]; ?>','<?php echo $labels[$key]; ?>')" class="field_buttons" title="<?php echo $labels[$key]; ?>"><?php echo $labels[$key]; ?></button>
		<?php
		}

	}


  ?>
<button  class="submit btn" onclick="autoFormat()"><strong>Apply Source Formatting</strong>  <em>(ctrl-enter)</em></button>
<textarea id="source" name="source" style="display: none;"></textarea>


<script>
var editor = CodeMirror.fromTextArea(document.getElementById("source"), {
    lineNumbers: true,
    lineWrapping: true,
    mode: "htmlmixed",
	value: form_front
    });
	
if($('#autogen_layout').is(':checked'))
{
	editor.setOption('readOnly',  true);
	editor.setValue(form_front);
}
else
{
	editor.setOption('readOnly',  false);
	editor.setValue(custom_front);
}

$('#autogen_layout').click(function(){autogen($(this).is(':checked'))});

autoFormat();

</script>


		<?php
}

public static function form_options(&$row, &$themes){
		
		JHtml::_('behavior.tooltip');
		JHtml::_('behavior.formvalidation');
		JHtml::_('behavior.switcher');
		JHtml::_('formbehavior.chosen', 'select');
		jimport('joomla.filesystem.path');
		jimport('joomla.filesystem.file');

		JRequest::setVar( 'hidemainmenu', 1 );
		
		$is_editor=false;
		
		$plugin = JPluginHelper::getPlugin('editors', 'tinymce');
		if (isset($plugin->type))
		{ 
			$editor	= JFactory::getEditor('tinymce');
			$is_editor=true;
		}
		
		$editor	= JFactory::getEditor('tinymce');

		$value="";

		$article = JTable::getInstance('content');
		if ($value) {
			$article->load($value);
		} else {
			$article->title = JText::_('Select an Article');
		}
		
			$label_id= array();		
			$label_label= array();		
			$label_type= array();			
			$label_all	= explode('#****#',$row->label_order_current);		
			$label_all 	= array_slice($label_all,0, count($label_all)-1);   	
			
		foreach($label_all as $key => $label_each) 		
		{			
			$label_id_each=explode('#**id**#',$label_each);			
			array_push($label_id, $label_id_each[0]);					
		
		$label_order_each=explode('#**label**#', $label_id_each[1]);				
			array_push($label_label, $label_order_each[0]);		
			array_push($label_type, $label_order_each[1]);		
		}			
		
		?>

<script language="javascript" type="text/javascript">
Joomla.submitbutton= function (pressbutton)
{
	var form = document.adminForm;
	if (pressbutton == 'cancel') 
	{
		submitform( pressbutton );
		return;
	}
	
	if(form.mail.value!='')
	{
		subMailArr=form.mail.value.split(',');
		emailListValid=true;
		for(subMailIt=0; subMailIt<subMailArr.length; subMailIt++)
		{
		trimmedMail = subMailArr[subMailIt].replace(/^\s+|\s+$/g, '') ;
		if (trimmedMail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1)
		{
					alert( "This is not a list of valid Email addresses." );	
					emailListValid=false;
					break;
		}
		}
		if(!emailListValid)	
		return;
	}	

	submitform( pressbutton );
}

function check_isnum(e)
{
	
   	var chCode1 = e.which || e.keyCode;
    	if (chCode1 > 31 && (chCode1 < 48 || chCode1 > 57))
        return false;
	return true;
}

function jSelectArticle(id, title, object) {
			document.getElementById('article_id').value = id;
			document.getElementById(object + '_name').value = title;
			document.getElementById('sbox-window').close();
			}
			
function remove_article()
{
	document.getElementById('id_name').value="Select an Article";
	document.getElementById('article_id').value="";
}

function set_type(type)
{
	switch(type)
	{
		case '2':
			document.getElementById('article').removeAttribute('style');
			document.getElementById('custom1').setAttribute('style','display:none');
			document.getElementById('url').setAttribute('style','display:none');
			document.getElementById('none').setAttribute('style','display:none');
			break;
			
		case '3':
			document.getElementById('article').setAttribute('style','display:none');
			document.getElementById('custom1').removeAttribute('style');
			document.getElementById('url').setAttribute('style','display:none');
			document.getElementById('none').setAttribute('style','display:none');
			break;
			
		case '4':
			document.getElementById('article').setAttribute('style','display:none');
			document.getElementById('custom1').setAttribute('style','display:none');
			document.getElementById('url').removeAttribute('style');
			document.getElementById('none').setAttribute('style','display:none');
			break;
			
		case '1':
			document.getElementById('article').setAttribute('style','display:none');
			document.getElementById('custom1').setAttribute('style','display:none');
			document.getElementById('url').setAttribute('style','display:none');
			document.getElementById('none').removeAttribute('style');
			break;
	}
}

function insertAtCursorform(myField, myValue) {  
	if(myField.style.display=="none")
	{

		tinyMCE.execCommand('mceInsertContent',false,"%"+myValue+"%");
		return;
	}

	
	   if (document.selection) {      
	   myField.focus();      
	   sel = document.selection.createRange();    
	   sel.text = myValue;    
	   }    
   else
		if (myField.selectionStart || myField.selectionStart == '0') {     
		   var startPos = myField.selectionStart;       
		   var endPos = myField.selectionEnd;      
		   myField.value = myField.value.substring(0, startPos)           
		   +  "%"+myValue+"%"        
		   + myField.value.substring(endPos, myField.value.length);   
		} 
   else {     
   myField.value += "%"+myValue+"%";    
   }


   }

function wdhide(id)
{
	document.getElementById(id).style.display="none";
}
function wdshow(id)
{
	document.getElementById(id).style.display="block";
}
   
   
function set_preview()
{
	appWidth			=parseInt(document.body.offsetWidth);
	appHeight			=parseInt(document.body.offsetHeight);

document.getElementById('modalbutton').href='<?php echo JURI::root(true) ?>/index.php?option=com_formmaker&amp;id=<?php echo $row->id ?>&tmpl=component&amp;test_theme='+document.getElementById('theme').value;
//document.getElementById('modalbutton').setAttribute("rel","{handler: 'iframe', size: {x:"+(appWidth-100)+", y: "+(appHeight-100)+"}}");

}

document.switcher = null;
			window.addEvent('domready', function(){
				toggler = document.id('submenu');
				element = document.id('config-document');
				if (element) {
					document.switcher = new JSwitcher(toggler, element, {cookieName: toggler.getProperty('class')});
				}
			});

gen="<?php echo $row->counter; ?>";
form_view_max=20;
</script>
<style>
.borderer
{
border-radius:5px;
padding-left:5px;
background-color:#F0F0F0;
height:19px;
width:153px;
}

fieldset.adminform1 {
border-radius: 7px;
border: 1px solid #CCC;
padding: 13px;
margin-top: 20px;
}

label
{
display:inline;
}

.btn-group.btn-group-yesno > .btn {
min-width: 84px;
padding: 2px 12px;
}

.admintable tr
{
margin-bottom: 18px;
}
#theme_chzn{
vertical-align: top;
}
</style>




<form action="index.php" method="post" name="adminForm">
	<div class="row-fluid">
		<div class="span12 form-horizontal">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#general_op" data-toggle="tab">General Options</a></li>
				<li><a href="#email_op" data-toggle="tab">Email Options</a></li>
				<li><a href="#actions_op" data-toggle="tab">Actions after Submission</a></li>

				<li><a href="#javascript_op" data-toggle="tab">JavaScript</a></li>
				
			</ul>
			
			<div class="tab-content">
				<div class="tab-pane active" id="general_op">
					<div class="row-fluid">
						<div class="span12">
							<div class="control-group">
								<div class="control-label">
									<label> <?php echo JText::_( 'Published' ); ?>: </label>
								</div>
								<div class="controls">
									<fieldset class="radio btn-group btn-group-yesno">
										<input type="radio" id="publishedyes" name="published" value="1" <?php if($row->published==1 ) echo "checked='checked'" ?> />
										<label for="publishedyes">Yes</label>
										<input type="radio" id="publishedno" name="published" value="0" <?php if($row->published==0 ) echo "checked='checked'" ?> />
										<label for="publishedno">No</label>
									</fieldset>	
								</div>
							</div>
							<div class="control-group">
								<div class="control-label">
									<label> <?php echo JText::_( 'Save data(to database)' ); ?>: </label>
								</div>
								<div class="controls">
									<fieldset class="radio btn-group btn-group-yesno">
										<input type="radio" id="savedbyes" name="savedb" value="1" <?php if($row->savedb==1 ) echo "checked='checked'" ?> />
										<label for="savedbyes">Yes</label>
										<input type="radio" id="savedbno" name="savedb" value="0" <?php if($row->savedb==0 ) echo "checked='checked'" ?> />
										<label for="savedbno">No</label>
									</fieldset>	
								</div>
							</div>
							<div class="control-group">
								<div class="control-label">
									<label> <?php echo JText::_( 'Theme' ); ?>: </label>
								</div>
								<div class="controls">
									<select id="theme" name="theme" onChange="set_preview()" >
									<?php 
									foreach($themes as $theme) 
									{
										if($theme->id==$row->theme)
										{
											echo '<option value="'.$theme->id.'" selected>'.$theme->title.'</option>';
										}
										else
											echo '<option value="'.$theme->id.'">'.$theme->title.'</option>';
									}
									?>
									</select>
									<a class="modal" id="modalbutton" href="<?php echo JURI::root(true) ?>/index.php?option=com_formmaker&amp;id=<?php echo $row->id ?>&tmpl=component&amp;test_theme=<?php echo $row->theme ?>" rel="{handler: 'iframe', size: {x:1000, y: 520}}">
										<div class="btn">
												<span class="icon-search" title="Preview" >
												</span>
											Preview
										</div>
									</a>
								</div>
							</div>
							<div class="control-group">
								<div class="control-label">
									<label> <?php echo JText::_( 'Required fields mark' ); ?>: </label>
								</div>
								<div class="controls">
									<input type="text" id="requiredmark" name="requiredmark" value="<?php echo $row->requiredmark ?>" />
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="tab-pane" id="email_op">
					<div class="control-group">
						<div class="control-label">
							<label> <?php echo JText::_( 'Send E-mail' ); ?>: </label>
						</div>
						<div class="controls">
								<fieldset class="radio btn-group btn-group-yesno">
									<input type="radio" id="sendemailyes" name="sendemail" value="1" <?php if($row->sendemail==1 ) echo "checked='checked'" ?> />
									<label for="sendemailyes">Yes</label>
									<input type="radio" id="sendemailno" name="sendemail" value="0" <?php if($row->sendemail==0 ) echo "checked='checked'" ?> />
									<label for="sendemailno">No</label>
								</fieldset>	
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6" style="">
							<fieldset class="form-horizontal">
								<legend>Email to Administrator</legend>
								<div class="control-group">
									<div class="control-label">
										<label> <?php echo JText::_( 'Email to Send Submissions to' ); ?>: </label>
									</div>
									<div class="controls">
										<input type="text" id="mail" name="mail" value="<?php echo $row->mail ?>" style="width:250px;" />
									</div>
								</div>
								<div class="control-group">
									<div class="control-label">
										<label> <?php echo JText::_( 'Email From' ); ?>: </label>
									</div>
									<div class="controls">
											<?php 
												$fields =$row->form_fields;
												$fields=explode('*:*id*:*type_submitter_mail*:*type*:*', $fields);
												$n=count($fields);
												$is_other=true;

												for($i=0; $i<$n-1; $i++)
												{
													echo '<div style="height: 20px;"><input type="radio" name="mail_from" id="mail_from'.$i.'" value="'.substr($fields[$i], strrpos($fields[$i], '*:*new_field*:*')+15, strlen($fields[$i])).'"  '.(substr($fields[$i], strrpos($fields[$i], '*:*new_field*:*')+15, strlen($fields[$i])) == $row->mail_from ? 'checked="checked"' : '' ).' style="margin:0px 5px 0px 0px" onclick="wdhide(\'mail_from_other\')"/><label for="mail_from'.$i.'" style="cursor:pointer">'.substr($fields[$i+1], 0, strpos($fields[$i+1], '*:*w_field_label*:*')).'</label></div>';
													
													if(substr($fields[$i], strrpos($fields[$i], '*:*new_field*:*')+15, strlen($fields[$i])) == $row->mail_from)
														$is_other=false;
												}
												

											?>
											<div style="height: 20px; <?php if($n==1) echo 'display:none;' ?>">
											<input type="radio" id="other" name="mail_from" value="other" <?php if($is_other) echo 'checked="checked"' ;?> style="margin:0px 5px 0px 0px" onclick="wdshow('mail_from_other')" />
												<label for="other" style="cursor:pointer">Other</label>
											</div>
											<input type="text" style="<?php if($n==1) echo 'width:250px'; else  echo 'width:230px' ?>; margin:0px; <?php if($n!=1) echo 'margin-left:20px' ?>;<?php if($is_other) echo 'display:block'; else  echo 'display:none';?>" id="mail_from_other" name="mail_from_other" value="<?php if($is_other)  echo $row->mail_from ?>" style="width:250px;" />
									</div>
								</div>
								<div class="control-group">
									<div class="control-label">
										<label> <?php echo JText::_( 'From Name' ); ?>: </label>
									</div>
									<div class="controls">
										<input type="text" id="mail_from_name" name="mail_from_name" value="<?php echo $row->mail_from_name ?>" style="width:250px;" />
									</div>
								</div>
								<div class="control-group">
									<div class="control-label">
										<label> <?php echo JText::_( 'Reply to' ); ?>:<br>(if different from "Email From") </label>
									</div>
									<div class="controls">
											<?php 
												$fields =$row->form_fields;
												$fields=explode('*:*id*:*type_submitter_mail*:*type*:*', $fields);
												$n=count($fields);
												$is_other=true;

												for($i=0; $i<$n-1; $i++)
												{
													echo '
													<div style="height: 20px;">
														<input type="radio" name="reply_to" id="reply_to'.$i.'" value="'.substr($fields[$i], strrpos($fields[$i], '*:*new_field*:*')+15, strlen($fields[$i])).'"  '.(substr($fields[$i], strrpos($fields[$i], '*:*new_field*:*')+15, strlen($fields[$i])) == $row->reply_to ? 'checked="checked"' : '' ).' style="margin:0px 5px 0px 0px" onclick="wdhide(\'reply_to_other\')"/>
														<label for="reply_to'.$i.'" style="cursor:pointer">'.substr($fields[$i+1], 0, strpos($fields[$i+1], '*:*w_field_label*:*')).'</label>
													</div>';
													
													if(substr($fields[$i], strrpos($fields[$i], '*:*new_field*:*')+15, strlen($fields[$i])) == $row->reply_to)
														$is_other=false;
												}
												

											?>
											<div style="height: 20px; <?php if($n==1) echo 'display:none;' ?>"><input type="radio" id="other1" name="reply_to" value="other" <?php if($is_other) echo 'checked="checked"' ;?> style="margin:0px 5px 0px 0px" onclick="wdshow('reply_to_other')" /><label for="other1" style="cursor:pointer">Other</label></div>
											<input type="text" style="<?php if($n==1) echo 'width:250px'; else  echo 'width:230px' ?>; margin:0px; <?php if($n!=1) echo 'margin-left:20px' ?>; <?php if($is_other) echo 'display:block'; else  echo 'display:none';?>" id="reply_to_other" name="reply_to_other" value="<?php if($is_other)  echo $row->reply_to; ?>" style="width:250px;" />
									</div>
								</div>
								<div class="control-group span10" style="border-top:1px solid #999; margin:0px;">
									<div style="margin:10px 0px">
										<label > <?php echo JText::_( 'Custom Text in Email For Administrator' ); ?>: </label>
									</div>
									<div>
										<?php 
										for($i=0; $i<count($label_label); $i++)			
										{ 			
											if($label_type[$i]=="type_submit_reset" || $label_type[$i]=="type_editor" || $label_type[$i]=="type_map" || $label_type[$i]=="type_mark_map" || $label_type[$i]=="type_captcha"|| $label_type[$i]=="type_recaptcha"|| $label_type[$i]=="type_button" )			
											continue;		
											
											$param = "'".htmlspecialchars(addslashes($label_label[$i]))."'";					
										
											$choise = 'document.getElementById(\'script_mail\')';	
											$fld_label = $param;
											if(strlen($fld_label)>30)
											{
												$fld_label = wordwrap(htmlspecialchars(addslashes($label_label[$i])), 30);
												$fld_label = explode("\n", $fld_label);
												$fld_label = $fld_label[0] . ' ...';	
											}	
											echo '<input type="button" class="btn" value="'.$fld_label.'" onClick="insertAtCursorform('.$choise.','.$param.')" /> ';	
										}	

										
											$choise = 'document.getElementById(\'script_mail\')';			
											echo '<br/><input style="margin:3px 0; font-weight:bold;" type="button" class="btn" value="All fields list" onClick="insertAtCursorform('.$choise.',\'all\')" /> ';			
										?>
									


							<?php if($is_editor) echo $editor->display('script_mail',$row->script_mail,'50%','350','40','6');
							else
							{
							?>
							<textarea name="script_mail" id="script_mail" cols="40" rows="6" style="width: 450px; height: 350px; " class="mce_editable" aria-hidden="true"><?php echo $row->script_mail ?></textarea>
							<?php

							}
							 ?>		   		   

									</div>	

								</div>
							</fieldset>
						</div>
						<div class="span6" style="">
							<fieldset class="form-horizontal">
								<legend>Email to User</legend>
								<div class="control-group">
									<div class="control-label">
										<label> <?php echo JText::_( 'Send to' ); ?>: </label>
									</div>
									<div class="controls">
										<?php 
											$fields =$row->form_fields;
											$fields=explode('*:*id*:*type_submitter_mail*:*type*:*', $fields);
											$n=count($fields);
											if($n==1)
												echo 'There is no email field';
											else
											for($i=0; $i<$n-1; $i++)
											{
												echo '
												<div style="height: 20px;">
													<input type="checkbox" name="send_to'.$i.'" id="send_to'.$i.'" value="'.substr($fields[$i], strrpos($fields[$i], '*:*new_field*:*')+15, strlen($fields[$i])).'"  '.(is_numeric(strpos($row->send_to, '*'.substr($fields[$i], strrpos($fields[$i], '*:*new_field*:*')+15, strlen($fields[$i])).'*')) ? 'checked="checked"' : '' ).' style="margin:0px 5px 0px 0px"/>
													<label for="send_to'.$i.'" style="cursor:pointer">'.substr($fields[$i+1], 0, strpos($fields[$i+1], '*:*w_field_label*:*')).'</label>
												</div>';
											}
										?>
									</div>
								</div>
								<div class="control-group">
									<div class="control-label">
										<label> <?php echo JText::_( 'Email From' ); ?>: </label>
									</div>
									<div class="controls">
										<input type="text" id="mail_from_user" name="mail_from_user" value="<?php echo $row->mail_from_user ?>" style="width:250px;" />
									</div>
								</div>
								<div class="control-group">
									<div class="control-label">
										<label> <?php echo JText::_( 'From Name' ); ?>: </label>
									</div>
									<div class="controls">
										<input type="text" id="mail_from_name_user" name="mail_from_name_user" value="<?php echo $row->mail_from_name_user ?>" style="width:250px;" />
									</div>
								</div>
								<div class="control-group">
									<div class="control-label">
										<label> <?php echo JText::_( 'Reply to' ); ?>:<br>(if different from "Email Form") </label>
									</div>
									<div class="controls">
										<input type="text" id="reply_to_user" name="reply_to_user" value="<?php echo $row->reply_to_user ?>" style="width:250px;" />
									</div>
								</div>
								<div class="control-group span10" style="border-top:1px solid #999; margin:0px;">
									<div style="margin:10px 0px">
										<label > <?php echo JText::_( 'Custom Text in Email For User' ); ?>: </label>
									</div>
									<div>
										<?php 
										for($i=0; $i<count($label_label); $i++)			
										{ 			
											if($label_type[$i]=="type_submit_reset" || $label_type[$i]=="type_editor" || $label_type[$i]=="type_map" || $label_type[$i]=="type_mark_map" || $label_type[$i]=="type_captcha"|| $label_type[$i]=="type_recaptcha"|| $label_type[$i]=="type_button" )		
												continue;		
											
											$param = "'".htmlspecialchars(addslashes($label_label[$i]))."'";					
										
											$choise = 'document.getElementById(\'script_mail_user\')';		
											$fld_label = $param;
											if(strlen($fld_label)>30)
											{
												$fld_label = wordwrap(htmlspecialchars(addslashes($label_label[$i])), 30);
												$fld_label = explode("\n", $fld_label);
												$fld_label = $fld_label[0] . ' ...';	
											}	
											echo '<input class="btn" type="button" value="'.$fld_label.'" onClick="insertAtCursorform('.$choise.','.$param.')" /> ';	
										}
										
										$choise = 'document.getElementById(\'script_mail_user\')';			
										echo '<br/><input style="margin:3px 0; font-weight:bold;" type="button" class="btn" value="All fields list" onClick="insertAtCursorform('.$choise.',\'all\')" /> ';					
										
										if($is_editor) echo $editor->display('script_mail_user',$row->script_mail_user,'50%','350','40','6');
										else
										{
										?>
										<textarea name="script_mail_user" id="script_mail_user" cols="40" rows="6" style="width: 450px; height: 350px; " class="mce_editable" aria-hidden="true"><?php echo $row->script_mail_user ?></textarea>
										<?php
										}
										?>		   		   

									</div>
								</div>
							</fieldset>					
						</div>
					</div>
				</div>
				
				<div class="tab-pane" id="actions_op">
					<div class="row-fluid">
						<div class="span12">
							<div class="control-group">
								<div class="control-label">
									<label> <?php echo JText::_( 'Action type' ); ?>: </label>
								</div>
								<div class="controls">
									<select id="submit_text_type" name="submit_text_type" onchange="set_type(this.value)">
										<option value="1"  <?php if($row->submit_text_type==1 ) echo "selected='selected'" ?>>Stay on Form</option>
										<option value="2"  <?php if($row->submit_text_type==2 ) echo "selected='selected'" ?>>Article</option>
										<option value="3"  <?php if($row->submit_text_type==3 ) echo "selected='selected'" ?>>Custom Text</option>
										<option value="4"  <?php if($row->submit_text_type==4 ) echo "selected='selected'" ?>>URL</option>
									</select>
								</div>
							</div>
							<div class="control-group"  id="none" <?php if($row->submit_text_type!=1) echo 'style="display:none"' ?> >
								<div class="control-label">
									<label> <?php echo JText::_( 'Stay on Form' ); ?>: </label>
								</div>
								<div class="controls">
									<i class="icon-ok"></i>
								</div>
							</div>
							<div class="control-group"  id="article" <?php if($row->submit_text_type!=2) echo 'style="display:none"' ?>   >
								<div class="control-label">
									<label> <?php echo JText::_( 'Article' ); ?>: </label>
								</div>
								<div class="controls">
									<?php 

									$name="id";
									$value=$row->article_id;
									$control_name="urlparams";

									$db		= JFactory::getDBO();
									$doc 		= JFactory::getDocument();
									$fieldName	= $control_name.'['.$name.']';
									$article = JTable::getInstance('content');
									if ($value) {
										$article->load($value);
									} else {
										$article->title = JText::_('Select an Article');
									}

									$js = "	function jSelectArticle_".$name."(id, title, object) {
										document.getElementById('article_id').value = id;
										document.getElementById('".$name."_name').value = title;
										SqueezeBox.close();
									}";
									$doc->addScriptDeclaration($js);

									$link = 'index.php?option=com_content&amp;view=articles&amp;layout=modal&amp;tmpl=component&amp;function=jSelectArticle_'.$name;

									JHTML::_('behavior.modal', 'a.modal');
									$html = "\n".'<div><a class="modal" title="'.JText::_('Select an Article').'"  href="'.$link.'" rel="{handler: \'iframe\', size: {x: 750, y: 500}}"><input style="background:none; border:none; font-size:11px" type="text" id="'.$name.'_name" value="'.htmlspecialchars($article->title, ENT_QUOTES, 'UTF-8').'"  readonly="readonly" /></a></div>';
									$html .= "\n".'<input type="hidden" id="article_id" name="article_id" value="'.(int)$value.'" />';

									echo $html;

									?>
									<span onclick="remove_article()" style="color:#000000; cursor:pointer; padding-left:5px;"><i>Remove article</i></span>			
								</div>
							</div>
							<div class="control-group" <?php if($row->submit_text_type!=3 ) echo 'style="display:none"' ?>  id="custom1">
								<div class="control-label">
									<label for="submissioni text"> <?php echo JText::_( 'Text' ); ?>: </label>
								</div>
								<div class="controls">
									<?php 
									if($is_editor) 
										echo $editor->display('submit_text',$row->submit_text,'50%','350','40','6');
									else
									{
										?>
										<textarea name="submit_text" id="submit_text" cols="40" rows="6" style="width: 450px; height: 350px; " class="mce_editable" aria-hidden="true"><?php echo $row->submit_text ?></textarea>
										<?php
									}
									?>		   		   
								</div>
							</div>
							<div class="control-group" <?php if($row->submit_text_type!=4 ) echo 'style="display:none"' ?>  id="url">
								<div class="control-label">
									<label for="submissioni text"> <?php echo JText::_( 'URL' ); ?>: </label>
								</div>
								<div class="controls">
									<input type="text" id="url" name="url" value="<?php echo $row->url ?>" />
								</div>
							</div>
						</div>
					</div>
				</div>

				
				<div class="tab-pane" id="javascript_op">
					<div class="row-fluid">
						<div class="span12">
							<div class="control-group">
								<div class="control-label">
									<label for="javascript"> <?php echo JText::_( 'JavaScript' ); ?> </label>
								</div>
								<div class="controls">
									<textarea style="margin: 0px; width:600px; height:500px"  name="javascript" id="javascript" ><?php echo $row->javascript; ?></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

    <input type="hidden" name="option" value="com_formmaker" />
    <input type="hidden" name="id" value="<?php echo $row->id?>" />
    <input type="hidden" name="cid[]" value="<?php echo $row->id; ?>" />
    <input type="hidden" name="task" value="" />
</form>
	

<div style="display:none" id="pages" show_title="<?php echo $row->show_title; ?>" show_numbers="<?php echo $row->show_numbers; ?>" type="<?php echo $row->pagination; ?>"></div>	
<div id="take" style="display:none">
<?php echo $row->form?>
</div>	
	
	<script language="javascript" type="text/javascript">

		set_preview();
	</script>

<?php		

       }
	   
public static function form_options_old(&$row, &$themes){

		JRequest::setVar( 'hidemainmenu', 1 );
		
		$is_editor=false;
		
		$plugin = JPluginHelper::getPlugin('editors', 'tinymce');
		if (isset($plugin->type))
		{ 
			$editor	= JFactory::getEditor('tinymce');
			$is_editor=true;
		}
		
		$editor	= JFactory::getEditor('tinymce');

		$value="";

		$article = JTable::getInstance('content');
		if ($value) {
			$article->load($value);
		} else {
			$article->title = JText::_('Select an Article');
		}
		
			$label_id= array();		
			$label_label= array();		
			$label_type= array();			
			$label_all	= explode('#****#',$row->label_order_current);		
			$label_all 	= array_slice($label_all,0, count($label_all)-1);   	
			
		foreach($label_all as $key => $label_each) 		
		{			
			$label_id_each=explode('#**id**#',$label_each);			
			array_push($label_id, $label_id_each[0]);					
		
		$label_order_each=explode('#**label**#', $label_id_each[1]);				
			array_push($label_label, $label_order_each[0]);		
			array_push($label_type, $label_order_each[1]);		
		}			
		
		?>

<script language="javascript" type="text/javascript">
Joomla.submitbutton= function (pressbutton)
{
	var form = document.adminForm;
	if (pressbutton == 'cancel') 
	{
		submitform( pressbutton );
		return;
	}
	
	if(form.mail.value!='')
	{
		subMailArr=form.mail.value.split(',');
		emailListValid=true;
		for(subMailIt=0; subMailIt<subMailArr.length; subMailIt++)
		{
		trimmedMail = subMailArr[subMailIt].replace(/^\s+|\s+$/g, '') ;
		if (trimmedMail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1)
		{
					alert( "This is not a list of valid Email addresses." );	
					emailListValid=false;
					break;
		}
		}
		if(!emailListValid)	
		return;
	}	

	submitform( pressbutton );
}

function check_isnum(e)
{
	
   	var chCode1 = e.which || e.keyCode;
    	if (chCode1 > 31 && (chCode1 < 48 || chCode1 > 57))
        return false;
	return true;
}

function jSelectArticle(id, title, object) {
			document.getElementById('article_id').value = id;
			document.getElementById(object + '_name').value = title;
			document.getElementById('sbox-window').close();
			}
			
function remove_article()
{
	document.getElementById('id_name').value="Select an Article";
	document.getElementById('article_id').value="";
}

function set_type(type)
{
	switch(type)
	{
		case 'article':
			document.getElementById('article').removeAttribute('style');
			document.getElementById('custom1').setAttribute('style','display:none');
			document.getElementById('url').setAttribute('style','display:none');
			document.getElementById('none').setAttribute('style','display:none');
			break;
			
		case 'custom':
			document.getElementById('article').setAttribute('style','display:none');
			document.getElementById('custom1').removeAttribute('style');
			document.getElementById('url').setAttribute('style','display:none');
			document.getElementById('none').setAttribute('style','display:none');
			break;
			
		case 'url':
			document.getElementById('article').setAttribute('style','display:none');
			document.getElementById('custom1').setAttribute('style','display:none');
			document.getElementById('url').removeAttribute('style');
			document.getElementById('none').setAttribute('style','display:none');
			break;
			
		case 'none':
			document.getElementById('article').setAttribute('style','display:none');
			document.getElementById('custom1').setAttribute('style','display:none');
			document.getElementById('url').setAttribute('style','display:none');
			document.getElementById('none').removeAttribute('style');
			break;
	}
}

function insertAtCursorform(myField, myValue) {  
	if(myField.style.display=="none")
	{

		tinyMCE.execCommand('mceInsertContent',false,"%"+myValue+"%");
		return;
	}

	
	   if (document.selection) {      
	   myField.focus();      
	   sel = document.selection.createRange();    
	   sel.text = myValue;    
	   }    
   else
		if (myField.selectionStart || myField.selectionStart == '0') {     
		   var startPos = myField.selectionStart;       
		   var endPos = myField.selectionEnd;      
		   myField.value = myField.value.substring(0, startPos)           
		   +  "%"+myValue+"%"        
		   + myField.value.substring(endPos, myField.value.length);   
		} 
   else {     
   myField.value += "%"+myValue+"%";    
   }


   }

   
   
function set_preview()
{
	appWidth			=parseInt(document.body.offsetWidth);
	appHeight			=parseInt(document.body.offsetHeight);

document.getElementById('modalbutton').href='index.php?option=com_formmaker&task=preview&format=raw&theme='+document.getElementById('theme').value;
document.getElementById('modalbutton').setAttribute("rel","{handler: 'iframe', size: {x:"+(appWidth-100)+", y: "+(appHeight-100)+"}}");

}

gen="<?php echo $row->counter; ?>";
form_view_max=20;
</script>
<style>
.borderer
{
border-radius:5px;
padding-left:5px;
background-color:#F0F0F0;
height:19px;
width:153px;
}

fieldset.adminform1 {
border-radius: 7px;
border: 1px solid #CCC;
padding: 13px;
margin-top: 20px;
}

label
{
display:inline;
}


</style>

<form action="index.php" method="post" name="adminForm">

	<div class="row-fluid">
		<div class="span10 form-horizontal">
		
		
		
			<fieldset>
				<ul class="nav nav-tabs">
					<li class="active"><a href="#general_op" data-toggle="tab">General Options</a></li>
					<li><a href="#actions_op" data-toggle="tab">Actions after Submission</a></li>

					<li><a href="#javascript_op" data-toggle="tab">JavaScript</a></li>
					<li><a href="#email_op" data-toggle="tab">Custom Text in Email</a></li>
				</ul>
			</fieldset>
			
			
			
			
			<div class="tab-content">
				<div class="tab-pane active" id="general_op">
						<table class="admintable"  style="float:left">
							<tr valign="top">
								<td class="key">
									<label> <?php echo JText::_( 'Email to Send Submissions to' ); ?>: </label>
								</td>
								<td>
									<input type="text" id="mail" name="mail" value="<?php echo $row->mail ?>" style="width:235px;" />
								</td>
							</tr>
							<tr valign="top">
						<td class="key">
							<label> <?php echo JText::_( 'Email From' ); ?>: </label>
						</td>
						<td>
							<input type="text" id="mail_from" name="mail_from" value="<?php echo $row->mail_from ?>" style="width:235px;" />
						</td>
					</tr>
					<tr valign="top">
						<td class="key">
							<label> <?php echo JText::_( 'From Name' ); ?>: </label>
						</td>
						<td>
							<input type="text" id="mail_from_name" name="mail_from_name" value="<?php echo $row->mail_from_name ?>" style="width:235px;" />
						</td>
					</tr>
							<tr valign="top">
								<td class="key">
									<label> <?php echo JText::_( 'Theme' ); ?>: </label>
								</td>
								<td>
									<select id="theme" name="theme" style="width:250px; " onChange="set_preview()" >
									<?php 
									foreach($themes as $theme) 
									{
										if($theme->id==$row->theme)
										{
											echo '<option value="'.$theme->id.'" selected>'.$theme->title.'</option>';
										}
										else
											echo '<option value="'.$theme->id.'">'.$theme->title.'</option>';
									}
									?>
									</select> 
								
								</td>
							</tr>
						</table>
						<style>
						div.wd_preview span{ float: none; width: 32px; height: 32px; margin: 0 auto; display: block; }
						div.wd_preview a {display: block; float: left;	white-space: nowrap;border: 1px solid #fbfbfb;	padding: 1px 5px;cursor: pointer; text-decoration:none; margin-top:62px;}

						</style>
						
						
						
						
					<div class="button wd_preview" id="toolbar-popup-popup">
					<a class="modal" id="modalbutton" href="index.php?option=com_formmaker&amp;task=preview&amp;tmpl=component&amp;theme=<?php echo $row->theme ?>" rel="{handler: 'iframe', size: {x:1000, y: 520}}">
					<span class="icon-32-preview" title="Preview" >
					</span>
					Preview
					</a>
					</div>

				</div>
				
				<div class="tab-pane" id="actions_op">
					<table class="admintable">

						<tr valign="top">
							<td class="key">
								<label for="submissioni text"> <?php echo JText::_( 'Action type' ); ?>: </label>
							</td>
							<td>
							<input type="radio" name="submit_text_type" onclick="set_type('none')"		value="1" <?php if($row->submit_text_type!=2 and $row->submit_text_type!=3 ) echo "checked" ?> /> Stay on Form<br/>
							<input type="radio" name="submit_text_type" onclick="set_type('article')"  	value="2" <?php if($row->submit_text_type==2 ) echo "checked" ?> /> Article<br/>
							<input type="radio" name="submit_text_type" onclick="set_type('custom')" 	value="3" <?php if($row->submit_text_type==3 ) echo "checked" ?> /> Custom Text<br/>
							<input type="radio" name="submit_text_type" onclick="set_type('url')" 		value="4" <?php if($row->submit_text_type==4 ) echo "checked" ?> /> URL
							</td>
						</tr>
						<tr  id="none" <?php if($row->submit_text_type==2 or $row->submit_text_type==3 or $row->submit_text_type==4 ) echo 'style="display:none"' ?> >
							<td class="key">
								<label for="submissioni text"> <?php echo JText::_( 'Stay on Form' ); ?>: </label>
							</td>
							<td >
								<i class="icon-checkin "></i>
							</td>
					   </tr>
						<tr id="article" <?php if($row->submit_text_type!=2) echo 'style="display:none"' ?>   >
							<td class="key">
								<label for="submissioni text"> <?php echo JText::_( 'Article' ); ?>: </label>
							</td>
			<td >
		<?php 

$name="id";
$value=$row->article_id;
$control_name="urlparams";

		$db		= JFactory::getDBO();
		$doc 		= JFactory::getDocument();
		$fieldName	= $control_name.'['.$name.']';
		$article = JTable::getInstance('content');
		if ($value) {
			$article->load($value);
		} else {
			$article->title = JText::_('Select an Article');
		}

		$js = "	function jSelectArticle_".$name."(id, title, object) {
			document.getElementById('article_id').value = id;
			document.getElementById('".$name."_name').value = title;
			SqueezeBox.close();
		}";
		$doc->addScriptDeclaration($js);

		$link = 'index.php?option=com_content&amp;view=articles&amp;layout=modal&amp;tmpl=component&amp;function=jSelectArticle_'.$name;

		JHTML::_('behavior.modal', 'a.modal');
		$html = "\n".'<div style="background-color:white; height:19px"><a class="modal" title="'.JText::_('Select an Article').'"  href="'.$link.'" rel="{handler: \'iframe\', size: {x: 750, y: 500}}"><input style="background:none; width:151px; height:17px; border:none; font-size:11px" type="text" id="'.$name.'_name" value="'.htmlspecialchars($article->title, ENT_QUOTES, 'UTF-8').'"  readonly="readonly" /></a></div>';
		$html .= "\n".'</div><input type="hidden" id="article_id" name="article_id" value="'.(int)$value.'" />';

		echo $html;

?>
			<span onclick="remove_article()" style="color:#000000; cursor:pointer; padding-left:5px;"><i>Remove article</i></span>			
			</td>
						</tr>
						<tr  <?php if($row->submit_text_type!=3 ) echo 'style="display:none"' ?>  id="custom1">
						   <td class="key">
								<label for="submissioni text"> <?php echo JText::_( 'Text' ); ?>: </label>
						   </td>
						   <td >
						   
				<?php if($is_editor) echo $editor->display('submit_text',$row->submit_text,'50%','350','40','6');
				else
				{
				?>
				<textarea name="submit_text" id="submit_text" cols="40" rows="6" style="width: 450px; height: 350px; " class="mce_editable" aria-hidden="true"><?php echo $row->submit_text ?></textarea>
				<?php

				}
				 ?>		   		   
							</td>
						</tr>
						<tr  <?php if($row->submit_text_type!=4 ) echo 'style="display:none"' ?>  id="url">
						   <td class="key">
								<label for="submissioni text"> <?php echo JText::_( 'URL' ); ?>: </label>
						   </td>
						   <td >
							   <input type="text" id="url" name="url" style="width:300px" value="<?php echo $row->url ?>" />
							</td>
						</tr>
				   

					</table>
				</div>

				
				<div class="tab-pane" id="javascript_op">
					<table class="admintable">

						<tr valign="top">

							<td  class="key">

								<label for="javascript"> <?php echo JText::_( 'JavaScript' ); ?> </label>

							</td>
							<td >
								<textarea style="margin: 0px; width:600px; height:500px"  name="javascript" id="javascript" ><?php echo $row->javascript; ?></textarea>
							</td>
						</tr>
					</table>
				</div>

				<div class="tab-pane" id="email_op">
					<table class="admintable">
					
						<tr>
							<td class="key" valign="top">
								<label > <?php echo JText::_( 'For Administrator' ); ?>: </label>
							</td>

							<td>
							<div style="margin-bottom:5px">
							<?php 
							for($i=0; $i<count($label_label); $i++)			
							{ 			
								if($label_type[$i]=="type_submit_reset" || $label_type[$i]=="type_editor" || $label_type[$i]=="type_map" || $label_type[$i]=="type_mark_map" || $label_type[$i]=="type_captcha"|| $label_type[$i]=="type_recaptcha"|| $label_type[$i]=="type_button" )			
								continue;		
								
								$param = "'".htmlspecialchars(addslashes($label_label[$i]))."'";					
							
								$choise = 'document.getElementById(\'script_mail\')';		
								echo '<input type="button" value="'.htmlspecialchars(addslashes($label_label[$i])).'" onClick="insertAtCursorform('.$choise.','.$param.')" /> ';	
							}	

							
								$choise = 'document.getElementById(\'script_mail\')';			
								echo '<br/><input style="margin:3px 0; font-weight:bold;" type="button" class="btn" value="All fields list" onClick="insertAtCursorform('.$choise.',\'all\')" /> ';			
							?>

							  
						</div>


				<?php if($is_editor) echo $editor->display('script_mail',$row->script_mail,'50%','350','40','6');
				else
				{
				?>
				<textarea name="script_mail" id="script_mail" cols="40" rows="6" style="width: 450px; height: 350px; " class="mce_editable" aria-hidden="true"><?php echo $row->script_mail ?></textarea>
				<?php

				}
				 ?>		   		   

							</td>

						</tr>
						<tr>
							<td  valign="top" height="30">
							</td>
							<td  valign="top">
							</td>
						</tr>

						<tr>
							<td class="key" valign="top">
								<label > <?php echo JText::_( 'For User' ); ?>: </label>
							</td>

							<td>
							<div style="margin-bottom:5px">
							<?php 
							for($i=0; $i<count($label_label); $i++)			
							{ 			
							if($label_type[$i]=="type_submit_reset" || $label_type[$i]=="type_editor" || $label_type[$i]=="type_map" || $label_type[$i]=="type_mark_map" || $label_type[$i]=="type_captcha"|| $label_type[$i]=="type_recaptcha"|| $label_type[$i]=="type_button" )			
							continue;		
								
								$param = "'".htmlspecialchars(addslashes($label_label[$i]))."'";					
							
							$choise = 'document.getElementById(\'script_mail\')';		
							echo '<input type="button" value="'.htmlspecialchars(addslashes($label_label[$i])).'" onClick="insertAtCursorform('.$choise.','.$param.')" /> ';	
							
							}	

							
							
								$choise = 'document.getElementById(\'script_mail\')';			
								echo '<br/><input style="margin:3px 0; font-weight:bold;" type="button"  class="btn" value="All fields list" onClick="insertAtCursorform('.$choise.',\'all\')" /> ';					
							?>

							  
						</div>

				<?php if($is_editor) echo $editor->display('script_mail_user',$row->script_mail_user,'50%','350','40','6');
				else
				{
				?>
				<textarea name="script_mail_user" id="script_mail_user" cols="40" rows="6" style="width: 450px; height: 350px; " class="mce_editable" aria-hidden="true"><?php echo $row->script_mail_user ?></textarea>
				<?php

				}
				 ?>		   		   

							</td>

						</tr>

					
					
					</table>

				</div>

			</div>
		</div>
	</div>

    <input type="hidden" name="option" value="com_formmaker" />
    <input type="hidden" name="id" value="<?php echo $row->id?>" />
    <input type="hidden" name="cid[]" value="<?php echo $row->id; ?>" />
    <input type="hidden" name="task" value="" />
</form>
	

<div style="display:none" id="pages" show_title="<?php echo $row->show_title; ?>" show_numbers="<?php echo $row->show_numbers; ?>" type="<?php echo $row->pagination; ?>"></div>	
<div id="take" style="display:none">
<?php echo $row->form?>
</div>	
	
	<script language="javascript" type="text/javascript">

		set_preview();
	</script>

<?php		

       }	   
	   

public static function paypal_info($row){
if(!isset($row->ipn))
{
echo "<div style='width:100%; text-align:center; height: 100%; vertical-align:middle'><h1 style='top: 44%;position: absolute;left:38%; color:#000'>No information yet<p></h1>";
return;
}
?>
<h2>Payment Info</h2>
<table class="admintable">
	<tr>
		<td class="key">Currency</td>
		<td><?php echo $row->currency; ?></td>
	</tr>
	<tr>
		<td class="key">Last modified</td>
		<td><?php echo $row->ord_last_modified; ?></td>
	</tr>
	<tr>
		<td class="key">Status</td>
		<td><?php echo $row->status; ?></td>
	</tr>
	<tr>
		<td class="key">Full name</td>
		<td><?php echo $row->full_name; ?></td>
	</tr>
	<tr>
		<td class="key">Email</td>
		<td><?php echo $row->email; ?></td>
	</tr>
	<tr>
		<td class="key">Phone</td>
		<td><?php echo $row->phone; ?></td>
	</tr>
	<tr>
		<td class="key">Mobile phone</td>
		<td><?php echo $row->mobile_phone; ?></td>
	</tr>
	<tr>
		<td class="key">Fax</td>
		<td><?php echo $row->fax; ?></td>
	</tr>
	<tr>
		<td class="key">Address</td>
		<td><?php echo $row->address; ?></td>
	</tr>
	<tr>
		<td class="key">PayPal info</td>
		<td><?php echo $row->paypal_info; ?></td>
	</tr>	
	<tr>
		<td class="key">IPN</td>
		<td><?php echo $row->ipn; ?></td>
	</tr>
	<tr>
		<td class="key">Tax</td>
		<td><?php echo $row->tax; ?>%</td>
	</tr>
	<tr>
		<td class="key">Shipping</td>
		<td><?php echo $row->shipping; ?></td>
	</tr>
	<tr>
		<td class="key">Total</td>
		<td><b><?php echo $row->total; ?></b></td>
	</tr>
</table>


<?php

}


public static function show_map($long,$lat){

		$document = JFactory::getDocument();
 		$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';

		$document->addScript($cmpnt_js_path.'/if_gmap.js');
		$document->addScript('http://maps.google.com/maps/api/js?sensor=false');
?>
<table style="margin:0px; padding:0px">
<tr><td><b>Address:</b></td><td><input type="text" id="addrval0" style="border:0px; background:none" size="100" readonly /> </td></tr>
<tr><td><b>Longitude:</b></td> <td><input type="text" id="longval0" style="border:0px; background:none" size="100" readonly /> </td></tr>
<tr><td><b>Latitude:</b></td><td><input type="text" id="latval0" style="border:0px; background:none" size="100" readonly /> </td></tr>
</table>
		
<div id="0_elementform_id_temp" long="<?php echo $long ?>" center_x="<?php echo $long ?>" center_y="<?php echo $lat ?>" lat="<?php echo $lat ?>" zoom="8" info="" style="width:600px; height:500px; "></div>

<script>
		if_gmap_init("0");
		add_marker_on_map(0, 0, "<?php echo $long ?>", "<?php echo $lat ?>", '');


</script>

<?php		

}



public static function show_matrix($matrix_params){


$new_filename= str_replace("***matrix***",'', $matrix_params);	

                $new_filename=explode('***', $matrix_params);
				$mat_params=array_slice($new_filename,0, count($new_filename)-1);
        $mat_rows=$mat_params[0];
		$mat_columns=$mat_params[$mat_rows+1];
							
				$matrix="<table >";
							
							
				
							$matrix .='<tr><td></td>';
						
							for( $k=1;$k<=$mat_columns;$k++)
							$matrix .='<td style="background-color:#BBBBBB; padding:5px;">'.$mat_params[$mat_rows+1+$k].'</td>';
							$matrix .='</tr>';
							
							$aaa=Array();
							   $var_checkbox=1;
							for( $k=1;$k<=$mat_rows;$k++){
							$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px; ">'.$mat_params[$k].'</td>';
							  if($mat_params[$mat_rows+$mat_columns+2]=="radio"){
							  
							  if($mat_params[$mat_rows+$mat_columns+2+$k]==0){
								    $checked=0;
									$aaa[1]="";
									}
								    else{
									$aaa=explode("_",$mat_params[$mat_rows+$mat_columns+2+$k]);
									
								    
							        }
									
							        for( $l=1;$l<=$mat_columns;$l++){
										if($aaa[1]==$l)
									    $checked="checked";
					 				    else
									    $checked="";
							        $matrix .='<td style="text-align:center"><input  type="radio" '.$checked.' disabled /></td>';
                                    
							        }
									
							    } 
								else{
								if($mat_params[$mat_rows+$mat_columns+2]=="checkbox")
								{
								                          
							        for( $l=1;$l<=$mat_columns;$l++){
									if($mat_params[$mat_rows+$mat_columns+2+$var_checkbox]=="1")
									$checked="checked";
									    else
									    $checked="";
										
							        $matrix .='<td style="text-align:center"><input  type="checkbox" '.$checked.' disabled /></td>';
								 $var_checkbox++;
								}
								
								}
								else
								{
								if($mat_params[$mat_rows+$mat_columns+2]=="text")
								{
								                          
							        for( $l=1;$l<=$mat_columns;$l++){
									 $checked = $mat_params[$mat_rows+$mat_columns+2+$var_checkbox];
										
							        $matrix .='<td style="text-align:center"><input  type="text" value="'.$checked.'" disabled /></td>';
								 $var_checkbox++;
								}
								
								}
								else{
								 for( $l=1;$l<=$mat_columns;$l++){
									 $checked = $mat_params[$mat_rows+$mat_columns+2+$var_checkbox];
										
							        $matrix .='<td style="text-align:center">'.$checked.'</td>';
								 $var_checkbox++;
								
								}
								}
								
								}
								
								}
							    $matrix .='</tr>';
							}
							 $matrix .='</table>';
		echo $matrix;

}



public static function country_list($id){

		$document		= JFactory::getDocument();
		
		$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';

		$document->addScript(JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/wdformjquery.js');
		$document->addScript($cmpnt_js_path.'/jquery.ui.core.js');
		$document->addScript($cmpnt_js_path.'/jquery.ui.widget.js');
		$document->addScript($cmpnt_js_path.'/jquery.ui.mouse.js');
    	$document->addScript($cmpnt_js_path.'/jquery.ui.slider.js');
		$document->addScript($cmpnt_js_path.'/jquery.ui.sortable.js');
		$document->addStyleSheet($cmpnt_js_path.'/jquery-ui.css');
		$document->addStyleSheet($cmpnt_js_path.'/parseTheme.css');

?>
<span style=" position: absolute;right: 29px;" >
<img alt="ADD" title="add" style="cursor:pointer; vertical-align:middle; margin:5px; " src="components/com_formmaker/images/save.png" onclick="save_list()">
<img alt="CANCEL" title="cancel" style=" cursor:pointer; vertical-align:middle; margin:5px; " src="components/com_formmaker/images/cancel_but.png" onclick="window.parent.SqueezeBox.close();">
</span>
<button onclick="select_all()">Select all</button>
<button onclick="remove_all()">Remove all</button>
<ul id="countries_list" style="list-style:none; padding:0px">
</ul>

<script>


selec_coutries=[];

coutries=["","Afghanistan","Albania",	"Algeria","Andorra","Angola","Antigua and Barbuda","Argentina","Armenia","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Central African Republic","Chad","Chile","China","Colombi","Comoros","Congo (Brazzaville)","Congo","Costa Rica","Cote d'Ivoire","Croatia","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","East Timor (Timor Timur)","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Fiji","Finland","France","Gabon","Gambia, The","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea, North","Korea, South","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepa","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Qatar","Romania","Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia and Montenegro","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","Spain","Sri Lanka","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Yemen","Zambia","Zimbabwe"];	

select_=window.parent.document.getElementById('<?php echo $id ?>_elementform_id_temp');
n=select_.childNodes.length;
for(i=0; i<n; i++)
{

	selec_coutries.push(select_.childNodes[i].value);
	var ch = document.createElement('input');
		ch.setAttribute("type","checkbox");
		ch.setAttribute("checked","checked");
		ch.value=select_.childNodes[i].value;
		ch.id=i+"ch";
		//ch.setAttribute("id",i);
	
	
	var p = document.createElement('span');
	    p.style.cssText ="color:#000000; font-size: 13px; cursor:move";
		p.innerHTML=select_.childNodes[i].value;

	var li = document.createElement('li');
	    li.style.cssText ="margin:3px; vertical-align:middle";
		li.id=i;
		
	li.appendChild(ch);
	li.appendChild(p);
	
	document.getElementById('countries_list').appendChild(li);
}
cur=i;
m=coutries.length;
for(i=0; i<m; i++)
{
	isin=isValueInArray(selec_coutries, coutries[i]);
	
	if(!isin)
	{
		var ch = document.createElement('input');
			ch.setAttribute("type","checkbox");
			ch.value=coutries[i];
			ch.id=cur+"ch";
		
		
		var p = document.createElement('span');
			p.style.cssText ="color:#000000; font-size: 13px; cursor:move";
			p.innerHTML=coutries[i];

		var li = document.createElement('li');
			li.style.cssText ="margin:3px; vertical-align:middle";
			li.id=cur;
			
		li.appendChild(ch);
		li.appendChild(p);
		
		document.getElementById('countries_list').appendChild(li);
		cur++;
	}
}




	$( "#countries_list" ).sortable();
	$( "#countries_list" ).disableSelection();

function isValueInArray(arr, val) {
	inArray = false;
	for (x = 0; x < arr.length; x++)
		if (val == arr[x])
			inArray = true;
	return inArray;
}
function save_list()
{
select_.innerHTML=""
	ul=document.getElementById('countries_list');
	n=ul.childNodes.length;
	for(i=0; i<n; i++)
	{
		if(ul.childNodes[i].tagName=="LI")
		{
			id=ul.childNodes[i].id;
			if(document.getElementById(id+'ch').checked)
			{
				var option_ = document.createElement('option');
					option_.setAttribute("value", document.getElementById(id+'ch').value);
					option_.innerHTML=document.getElementById(id+'ch').value;

				select_.appendChild(option_);
			}
				
		}
		
		
	}
	window.parent.SqueezeBox.close();


}

function select_all()
{
	for(i=0; i<194; i++)
	{
		document.getElementById(i+'ch').checked=true;;	
	}
}

function remove_all()
{
	for(i=0; i<194; i++)
	{
		document.getElementById(i+'ch').checked=false;;	
	}
}
</script>




<?php

}


public static function product_option($id, $property_id){

		$document		= JFactory::getDocument();
		
		$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';

		$document->addScript(JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/wdformjquery.js');
		$document->addScript($cmpnt_js_path.'/jquery.ui.core.js');
		$document->addScript($cmpnt_js_path.'/jquery.ui.widget.js');
		$document->addScript($cmpnt_js_path.'/jquery.ui.mouse.js');
    	$document->addScript($cmpnt_js_path.'/jquery.ui.slider.js');
		$document->addScript($cmpnt_js_path.'/jquery.ui.sortable.js');
		$document->addStyleSheet($cmpnt_js_path.'/jquery-ui.css');
		$document->addStyleSheet($cmpnt_js_path.'/parseTheme.css');
		JHTML::_('behavior.modal');

?>
<style>
label{
display:inline;
}
</style>
<span style=" position:fixed; right:10px" >
<img alt="ADD" title="add" style="cursor:pointer; vertical-align:middle; margin:5px; " src="components/com_formmaker/images/save.png" onclick="save_options()">
<img alt="CANCEL" title="cancel" style=" cursor:pointer; vertical-align:middle; margin:5px; " src="components/com_formmaker/images/cancel_but.png" onclick="window.parent.SqueezeBox.close();">
</span>

<div style="margin-left:10px">
	<br>
	<fieldset>
		<legend>
			<label style="color: rgb(0, 174, 239); font-weight: bold; font-size: 13px;">Properties</label>
		</legend>
		<br>
		<div style="margin-left:10px">
		<label style="color: rgb(0, 174, 239); font-weight: bold; font-size: 13px; margin-right:20px">Type </label>
		<select id="option_type" style="width: 200px; border-width: 1px;" onchange="type_add_predefined(this.value)">
			<option value="Custom" selected="selected">Custom</option>
			<option value="Color">Color</option>
			<option value="T-Shirt Size">T-Shirt Size</option>
			<option value="Print Size">Print Size</option>
			<option value="Screen Resolution">Screen Resolution</option>
			<option value="Shoe Size">Shoe Size</option>
		</select>
		<br>

		<label style="color: rgb(0, 174, 239); font-weight: bold; font-size: 13px; margin-right:23px">Title </label>
		<input type="text" style="width:200px"  id="option_name" />
		<br>
		<br>
		<label style="color: rgb(0, 174, 239); font-weight: bold; font-size: 13px;">Properties</label> &nbsp;
		<img id="el_choices_add" src="components/com_formmaker/images/add.png" style="cursor: pointer;" title="add" onclick="add_choise_option()">
		<br>

		<div style="margin-left:0px" id="options" ></div>

		</div>
	</fieldset>



</div>


<script>
var j=0;
function save_options()
{
	
	if( document.getElementById('option_name').value=='')
	{
		alert('The option must have a title')
		return;
	}
<?php

if(!isset($property_id))
{
?>

	
		for(i=30; i>=0; i--)
		{
			if(window.parent.document.getElementById(<?php echo $id ?>+"_propertyform_id_temp"+i))
			{
				i=i+1;
				select_ = document.createElement('select');
				select_.setAttribute("id", <?php echo $id ?>+"_propertyform_id_temp"+i);
				select_.setAttribute("name", <?php echo $id ?>+"_propertyform_id_temp"+i);
				select_.style.cssText = "width:auto; margin:2px 0px";
				break;	
			}
		}
		
		if(i==-1)
		{
			i=0;
			select_ = document.createElement('select');
			select_.setAttribute("id", <?php echo $id ?>+"_propertyform_id_temp"+i);
			select_.setAttribute("name", <?php echo $id ?>+"_propertyform_id_temp"+i);
			select_.style.cssText = "width:auto; margin:2px 0px";;
		}
		
		
		for(k=0; k<=50; k++)
		{
			if(document.getElementById('el_option'+k))
			{
				var option = document.createElement('option');
					option.setAttribute("id","<?php echo $id ?>_"+i+"_option"+k);
					option.setAttribute("value", document.getElementById('el_option'+k).value);
					option.innerHTML =  document.getElementById('el_option'+k).value;
					select_.appendChild(option);	
			}
		}
	

	
	var select_label = document.createElement('label');
			select_label.innerHTML =  document.getElementById('option_name').value;
			select_label.style.cssText = "margin-right:5px";		
			select_label.setAttribute("class", 'mini_label');
			select_label.setAttribute("id", '<?php echo $id ?>_property_label_form_id_temp'+i);

		var span_ = document.createElement('span');
			span_.style.cssText = "margin-right:15px";
			span_.setAttribute("id", '<?php echo $id ?>_property_'+i);
			

		
		div_=window.parent.document.getElementById("<?php echo $id ?>_divform_id_temp");
		span_.appendChild(select_label);
		span_.appendChild(select_);
		div_.appendChild(span_);
		
		var li_ = document.createElement('li');
			li_.setAttribute("id", 'property_li_'+i);

		var li_label = document.createElement('label');
			li_label.innerHTML=document.getElementById('option_name').value;
			li_label.setAttribute("id", 'label_property_'+i);
			li_label.style.cssText ="font-weight:bold; font-size: 13px; display:inline";
			
		var li_edit = document.createElement('a');	
			li_edit.setAttribute("rel", "{handler: 'iframe', size: {x: 650, y: 375}}"	);
			li_edit.setAttribute("href","index.php?option=com_formmaker&task=product_option&field_id=<?php echo $id ?>&property_id="+i+"&tmpl=component");
			li_edit.setAttribute("class","modal");

		var li_edit_img = document.createElement('img');
			li_edit_img.setAttribute("src", 'components/com_formmaker/images/edit.png');
			li_edit_img.style.cssText = "width:14px; height:14px;  display:inline-block; vertical-align:middle; margin:2px; margin-left:13px;";

		li_edit.appendChild(li_edit_img);
			
		var li_x = document.createElement('img');
			li_x.setAttribute("src", 'components/com_formmaker/images/delete.png');
			li_x.setAttribute("onClick", 'remove_property(<?php echo $id ?>,'+i+')');
			li_x.style.cssText = "width:14px; height:14px;  display:inline-block; cursor:pointer; vertical-align:middle; margin:2px";
			
			
		ul_=window.parent.document.getElementById("option_ul");
		
		li_.appendChild(li_label);
		li_.appendChild(li_edit);
		li_.appendChild(li_x);
		ul_.appendChild(li_);
		window.parent.SqueezeBox.assign(li_edit, {
					parse: 'rel'
				});
	
<?php
}	
else
	
{

?>
	
		i=<?php echo $property_id ?>;
		var select_ = window.parent.document.getElementById('<?php echo $id ?>_propertyform_id_temp<?php echo $property_id ?>');	
		select_.innerHTML="";
		for(k=0; k<=j; k++)
		{
			if(document.getElementById('el_option'+k))
			{
				var option = document.createElement('option');
					option.setAttribute("id","<?php echo $id ?>_"+i+"_option"+k);
					option.setAttribute("value", document.getElementById('el_option'+k).value);
					option.innerHTML =  document.getElementById('el_option'+k).value;
					select_.appendChild(option);	
			}
		}
		
		

		var select_label = document.createElement('label');
			select_label.innerHTML =  document.getElementById('option_name').value;
			select_label.style.cssText = "margin-right:5px";
			select_label.setAttribute("class", 'mini_label');
			select_label.setAttribute("id", '<?php echo $id ?>_property_label_form_id_temp'+i);

		var span_ = window.parent.document.getElementById('<?php echo $id ?>_property_<?php echo $property_id ?>');	
			span_.innerHTML='';
		
		span_.appendChild(select_label);
		span_.appendChild(select_);
		window.parent.document.getElementById('label_property_<?php echo $property_id ?>').innerHTML=document.getElementById('option_name').value;

	
<?php
}	

?>

	window.parent.SqueezeBox.close();
}


function type_add_predefined( type )
{
	document.getElementById('options').innerHTML='';
	
	switch(type)
	{
		case 'Custom': 
		{
			w_choices=[];
			break;	
		}

		case 'Color': 
		{
			w_choices=["Red", "Blue", "Green", "Yellow", "Black"];
			break;	
		}

		case 'T-Shirt Size': 
		{
			w_choices=["XS","S","M","L","XL","XXL","XXXL"];
			break;	
		}

		case 'Print Size': 
		{
			w_choices=["A4","A3","A2","A1"];
			break;	
		}

		case 'Screen Resolution': 
		{
			w_choices=["1024x768","1152x864","1280x768","1280x800","1280x960","1280x1024","1366x768","1440x900","1600x1200","1680x1050","1920x1080","1920x1200"];
			break;	
		}

		case 'Shoe Size': 
		{
			w_choices=["8","8.5","9","9.5","10","10.5","11","11.5","12","13","14"];
			break;	
		}

	}
	type_add_options( w_choices);

}



function delete_options()
{
document.getElementById('options').innerHTML='';
}

function type_add_options( w_choices){
	
	i=0;
	edit_main_td3=document.getElementById('options');
	var br = document.createElement('br');
	edit_main_td3.appendChild(br);

	n=w_choices.length;
	for(j=0; j<n; j++)
	{	
		var br = document.createElement('br');
		br.setAttribute("id", "br"+j);
		var el_choices = document.createElement('input');
			el_choices.setAttribute("id", "el_option"+j);
			el_choices.setAttribute("type", "text");
			el_choices.setAttribute("value", w_choices[j]);
			el_choices.style.cssText =   "width:100px; margin:0; padding:0; border-width: 1px";
	//		el_choices.setAttribute("onKeyUp", "change_label('"+i+"_option"+j+"', this.value)");
	
		var el_choices_remove = document.createElement('img');
			el_choices_remove.setAttribute("id", "el_option"+j+"_remove");
			el_choices_remove.setAttribute("src", 'components/com_formmaker/images/delete.png');
			el_choices_remove.style.cssText = 'cursor:pointer; vertical-align:middle; margin:3px';
			el_choices_remove.setAttribute("align", 'top');
			el_choices_remove.setAttribute("onClick", "remove_option("+j+","+i+")");
			
			
		edit_main_td3.appendChild(br);
		edit_main_td3.appendChild(el_choices);
		edit_main_td3.appendChild(el_choices_remove);
	
	}

}



function add_choise_option()
{
		num=0;
		j++;	
		
		var choices_td= document.getElementById('options');
		var br = document.createElement('br');
		br.setAttribute("id", "br"+j);
		var el_choices = document.createElement('input');
			el_choices.setAttribute("id", "el_option"+j);
			el_choices.setAttribute("type", "text");
			el_choices.setAttribute("value", "");
			el_choices.style.cssText =   "width:100px; margin:0; padding:0; border-width: 1px";
		//	el_choices.setAttribute("onKeyUp", "change_label('"+num+"_option"+j+"', this.value)");
			
		var el_choices_remove = document.createElement('img');
			el_choices_remove.setAttribute("id", "el_option"+j+"_remove");
			el_choices_remove.setAttribute("src", 'components/com_formmaker/images/delete.png');
			el_choices_remove.style.cssText = 'cursor:pointer; vertical-align:middle; margin:3px';
			el_choices_remove.setAttribute("align", 'top');
			el_choices_remove.setAttribute("onClick", "remove_option('"+j+"','"+num+"')");
			
	    choices_td.appendChild(br);
	    choices_td.appendChild(el_choices);
	    choices_td.appendChild(el_choices_remove);

}

function remove_option(id, num)
{
		
		var choices_td= document.getElementById('options');
		var el_choices = document.getElementById('el_option'+id);
		var el_choices_remove = document.getElementById('el_option'+id+'_remove');
		var br = document.getElementById('br'+id);
		
		choices_td.removeChild(el_choices);
		choices_td.removeChild(el_choices_remove);
		choices_td.removeChild(br);
}

<?php
if(isset($property_id))
{
?>

	label_	=		window.parent.document.getElementById('<?php echo $id ?>_property_label_form_id_temp<?php echo $property_id ?>').innerHTML;
	select_	=		window.parent.document.getElementById('<?php echo $id ?>_propertyform_id_temp<?php echo $property_id ?>');
	n = select_.childNodes.length;
	delete_options();
 	w_choices=[ ];

	document.getElementById('option_name').value=label_;
	
	
	for(k=0; k<n; k++)
	{
	w_choices.push(select_.childNodes[k].value);
	}
	type_add_options( w_choices);

<?php
}

?>


</script>
<?php

}




public static function preview_formmaker($css){
 /**
 * @package SpiderFC
 * @author Web-Dorado
 * @copyright (C) 2011 Web-Dorado. All rights reserved.
 * @license GNU/GPLv3 http://www.gnu.org/licenses/gpl-3.0.html
 **/
		JHTML::_('behavior.tooltip');	
		JHTML::_('behavior.calendar');
		$document = JFactory::getDocument();
 		$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';

				
		//$document->addStyleSheet(JURI::root(true).'/administrator/components/com_formmaker/css/style.css');
		
	
		$id='form_id_temp';
?>
<script src="<?php echo $cmpnt_js_path.'/if_gmap.js'; ?>" ></script>
<script src="<?php echo $cmpnt_js_path.'/main.js'; ?>" ></script>
<script src="http://maps.google.com/maps/api/js?sensor=false" ></script>
<script src="<?php echo $cmpnt_js_path ?>/formmaker_div1.js" type="text/javascript" style=""></script>
<script src="<?php echo  JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/wdformjquery.js'; ?>" type="text/javascript"></script>
<script src="<?php echo  JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/jquery-ui.js'; ?>" type="text/javascript"></script>
<link rel="stylesheet" href="<?php echo $cmpnt_js_path ?>/jquery-ui-spinner.css" type="text/css">
<style>
html
{
height:90%;
}
<?php echo str_replace('[SITE_ROOT]', JURI::root(true), $css); ?>
</style>
<div id="form_id_temppages" class="wdform_page_navigation" show_title="" show_numbers="" type=""></div>

  <form id="form_preview"></form>
<input type="hidden" id="counter<?php echo $id ?>" value="" name="counter<?php echo $id ?>" />

<script>
	JURI_ROOT				='<?php echo JURI::root(true) ?>';  
	
	document.getElementById('form_preview').innerHTML = window.parent.document.getElementById('take').innerHTML;
	document.getElementById('form_id_temppages').setAttribute('show_title', window.parent.document.getElementById('pages').getAttribute('show_title'));
	document.getElementById('form_id_temppages').setAttribute('show_numbers', window.parent.document.getElementById('pages').getAttribute('show_numbers'));
	document.getElementById('form_id_temppages').setAttribute('type', window.parent.document.getElementById('pages').getAttribute('type'));
	document.getElementById('counterform_id_temp').value=window.parent.gen;;

	form_view_count<?php echo $id ?>=0;
	for(i=1; i<=30; i++)
	{
		if(document.getElementById('<?php echo $id ?>form_view'+i))
		{
			form_view_count<?php echo $id ?>++;
			form_view_max<?php echo $id ?>=i;
			
			document.getElementById('<?php echo $id ?>form_view'+i).parentNode.removeAttribute('style');
		}
	}
	
	refresh_first();

	
	if(form_view_count<?php echo $id ?>>1)
	{
		for(i=1; i<=form_view_max<?php echo $id ?>; i++)
		{
			if(document.getElementById('<?php echo $id ?>form_view'+i))
			{
				first_form_view<?php echo $id ?>=i;
				break;
			}
		}
		
		generate_page_nav(first_form_view<?php echo $id ?>, '<?php echo $id ?>', form_view_count<?php echo $id ?>, form_view_max<?php echo $id ?>);
	}
	

function remove_add_(id)
{
			attr_name= new Array();
			attr_value= new Array();
			var input = document.getElementById(id); 
			atr=input.attributes;
			for(v=0;v<30;v++)
				if(atr[v] )
				{
					if(atr[v].name.indexOf("add_")==0)
					{
						attr_name.push(atr[v].name.replace('add_',''));
						attr_value.push(atr[v].value);
						input.removeAttribute(atr[v].name);
						v--;
					}
				}
			for(v=0;v<attr_name.length; v++)
			{
				input.setAttribute(attr_name[v],attr_value[v])
			}
}

function remove_whitespace(node)
{
    var ttt;
	for (ttt=0; ttt < node.childNodes.length; ttt++)
	{
        if( node.childNodes[ttt] && node.childNodes[ttt].nodeType == '3' && !/\S/.test(  node.childNodes[ttt].nodeValue ) )
		{
			
			node.removeChild(node.childNodes[ttt]);
            ttt--;
		}
		else
		{
			if(node.childNodes[ttt].childNodes.length)
				remove_whitespace(node.childNodes[ttt]);
		}
	}
	return
}

function refresh_first()
{
		
	n=window.parent.gen;
	for(i=0; i<n; i++)
	{
		if(document.getElementById(i))
		{	
			for(z=0; z<document.getElementById(i).childNodes.length; z++)
				if(document.getElementById(i).childNodes[z].nodeType==3)
					document.getElementById(i).removeChild(document.getElementById(i).childNodes[z]);

			if(document.getElementById(i).getAttribute('type')=="type_map")
			{
				if_gmap_init(i);
				for(q=0; q<20; q++)
					if(document.getElementById(i+"_elementform_id_temp").getAttribute("long"+q))
					{
					
						w_long=parseFloat(document.getElementById(i+"_elementform_id_temp").getAttribute("long"+q));
						w_lat=parseFloat(document.getElementById(i+"_elementform_id_temp").getAttribute("lat"+q));
						w_info=parseFloat(document.getElementById(i+"_elementform_id_temp").getAttribute("info"+q));
						add_marker_on_map(i,q, w_long, w_lat, w_info, false);
					}
			}
			
			if(document.getElementById(i).getAttribute('type')=="type_mark_map")
			{
				if_gmap_init(i);
				w_long=parseFloat(document.getElementById(i+"_elementform_id_temp").getAttribute("long"+0));
				w_lat=parseFloat(document.getElementById(i+"_elementform_id_temp").getAttribute("lat"+0));
				w_info=parseFloat(document.getElementById(i+"_elementform_id_temp").getAttribute("info"+0));
				add_marker_on_map(i,0, w_long, w_lat, w_info, true);
			}
			
			
			
			if(document.getElementById(i).getAttribute('type')=="type_captcha" || document.getElementById(i).getAttribute('type')=="type_recaptcha")
			{
				if(document.getElementById(i).childNodes[10])
				{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				}
				else
				{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				}
				continue;
			}
			
			if(document.getElementById(i).getAttribute('type')=="type_section_break")
			{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				continue;
			}
						

			if(document.getElementById(i).childNodes[10])
			{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
			}
			else
			{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
			}
		}
	}
	
	for(i=0; i<=n; i++)
	{	
		if(document.getElementById(i))
		{
			type=document.getElementById(i).getAttribute("type");
				switch(type)
				{
					case "type_text":
					case "type_number":
					case "type_password":
					case "type_submitter_mail":
					case "type_own_select":
					case "type_country":
					case "type_hidden":
					case "type_map":
					{
						remove_add_(i+"_elementform_id_temp");
						break;
					}
					
					case "type_submit_reset":
					{
						remove_add_(i+"_element_submitform_id_temp");
						if(document.getElementById(i+"_element_resetform_id_temp"))
							remove_add_(i+"_element_resetform_id_temp");
						break;
					}
					
					case "type_captcha":
					{
						remove_add_("_wd_captchaform_id_temp");
						remove_add_("_element_refreshform_id_temp");
						remove_add_("_wd_captcha_inputform_id_temp");
						break;
					}
					
					case "type_recaptcha":
					{
						remove_add_("wd_recaptchaform_id_temp");
						break;
					}
						
					case "type_file_upload":
						{
							remove_add_(i+"_elementform_id_temp");
								break;
						}
						
					case "type_textarea":
						{
						remove_add_(i+"_elementform_id_temp");

								break;
						}
						
					case "type_name":
						{
						
						if(document.getElementById(i+"_element_titleform_id_temp"))
							{
							remove_add_(i+"_element_titleform_id_temp");
							remove_add_(i+"_element_firstform_id_temp");
							remove_add_(i+"_element_lastform_id_temp");
							remove_add_(i+"_element_middleform_id_temp");
							}
							else
							{
							remove_add_(i+"_element_firstform_id_temp");
							remove_add_(i+"_element_lastform_id_temp");

							}
							break;

						}
						
					case "type_phone":
						{
						
							remove_add_(i+"_element_firstform_id_temp");
							remove_add_(i+"_element_lastform_id_temp");

							break;

						}
						case "type_address":
							{	
								if(document.getElementById(i+"_disable_fieldsform_id_temp").getAttribute('street1')=='no')
								remove_add_(i+"_street1form_id_temp");
							if(document.getElementById(i+"_disable_fieldsform_id_temp").getAttribute('street2')=='no')	
								remove_add_(i+"_street2form_id_temp");
							if(document.getElementById(i+"_disable_fieldsform_id_temp").getAttribute('city')=='no')
								remove_add_(i+"_cityform_id_temp");
							if(document.getElementById(i+"_disable_fieldsform_id_temp").getAttribute('state')=='no')
								remove_add_(i+"_stateform_id_temp");
							if(document.getElementById(i+"_disable_fieldsform_id_temp").getAttribute('postal')=='no')
								remove_add_(i+"_postalform_id_temp");
							if(document.getElementById(i+"_disable_fieldsform_id_temp").getAttribute('country')=='no')
								remove_add_(i+"_countryform_id_temp");
							
							
								break;
	
							}
							
						
					case "type_checkbox":
					case "type_radio":
						{
							is=true;
							for(j=0; j<100; j++)
								if(document.getElementById(i+"_elementform_id_temp"+j))
								{
									remove_add_(i+"_elementform_id_temp"+j);
								}
						/*	if(document.getElementById(i+"_randomize").value=="yes")
								choises_randomize(i);*/
							
							break;
						}
						
					case "type_button":
						{
							for(j=0; j<100; j++)
								if(document.getElementById(i+"_elementform_id_temp"+j))
								{
									remove_add_(i+"_elementform_id_temp"+j);
								}
							break;
						}
						
					case "type_time":
						{	
						if(document.getElementById(i+"_ssform_id_temp"))
							{
							remove_add_(i+"_ssform_id_temp");
							remove_add_(i+"_mmform_id_temp");
							remove_add_(i+"_hhform_id_temp");
							}
							else
							{
							remove_add_(i+"_mmform_id_temp");
							remove_add_(i+"_hhform_id_temp");
							}
							break;

						}
						
					case "type_date":
						{	
						remove_add_(i+"_elementform_id_temp");
						remove_add_(i+"_buttonform_id_temp");
							break;
						}
					case "type_date_fields":
						{	
						remove_add_(i+"_dayform_id_temp");
						remove_add_(i+"_monthform_id_temp");
						remove_add_(i+"_yearform_id_temp");
								break;
						}
						
					case "type_star_rating":
						{	
							remove_add_(i+"_elementform_id_temp");
						
								break;
						}
					case "type_scale_rating":
						{	
							remove_add_(i+"_elementform_id_temp");
						
								break;
						}
					case "type_spinner":
						{	
							remove_add_(i+"_elementform_id_temp");
							
							var spinner_value = jQuery('#'+i+"_elementform_id_temp").get( "aria-valuenow" );
							var spinner_min_value = document.getElementById(i+"_min_valueform_id_temp").value;
							var spinner_max_value = document.getElementById(i+"_max_valueform_id_temp").value;
							var spinner_step = document.getElementById(i+"_stepform_id_temp").value;
								  
									 jQuery( "#"+i+"_elementform_id_temp" ).removeClass( "ui-spinner-input" )
							.prop( "disabled", false )
							.removeAttr( "autocomplete" )
							.removeAttr( "role" )
							.removeAttr( "aria-valuemin" )
							.removeAttr( "aria-valuemax" )
							.removeAttr( "aria-valuenow" );
				
							span_ui= document.getElementById(i+"_elementform_id_temp").parentNode;
								span_ui.parentNode.appendChild(document.getElementById(i+"_elementform_id_temp"));
								span_ui.parentNode.removeChild(span_ui);
								
								jQuery("#"+i+"_elementform_id_temp")[0].spin = null;
								
								spinner = jQuery( "#"+i+"_elementform_id_temp" ).spinner();
								spinner.spinner( "value", spinner_value );
								jQuery( "#"+i+"_elementform_id_temp" ).spinner({ min: spinner_min_value});    
								jQuery( "#"+i+"_elementform_id_temp" ).spinner({ max: spinner_max_value});
								jQuery( "#"+i+"_elementform_id_temp" ).spinner({ step: spinner_step});
									break;
						}
						
								case "type_slider":
						{	
								remove_add_(i+"_elementform_id_temp");	
								
							var slider_value = document.getElementById(i+"_slider_valueform_id_temp").value;
							var slider_min_value = document.getElementById(i+"_slider_min_valueform_id_temp").value;
							var slider_max_value = document.getElementById(i+"_slider_max_valueform_id_temp").value;
							
							var slider_element_value = document.getElementById( i+"_element_valueform_id_temp" );
							var slider_value_save = document.getElementById( i+"_slider_valueform_id_temp" );
					
							document.getElementById(i+"_elementform_id_temp").innerHTML = "";
							document.getElementById(i+"_elementform_id_temp").removeAttribute( "class" );
							document.getElementById(i+"_elementform_id_temp").removeAttribute( "aria-disabled" );
							jQuery("#"+i+"_elementform_id_temp")[0].slide = null;	
							
							
							jQuery( "#"+i+"_elementform_id_temp").slider({
								range: "min",
								value: eval(slider_value),
								min: eval(slider_min_value),
								max: eval(slider_max_value),
								slide: function( event, ui ) {	
									slider_element_value.innerHTML = "" + ui.value ;
									slider_value_save.value = "" + ui.value; 

								}
							});
                         break;
						}
								case "type_range":
						{	
							remove_add_(i+"_elementform_id_temp0");
							remove_add_(i+"_elementform_id_temp1");
						
							var spinner_value0 = jQuery('#'+i+"_elementform_id_temp0").get( "aria-valuenow" );
							var spinner_step = document.getElementById(i+"_range_stepform_id_temp").value;
								  
									 jQuery( "#"+i+"_elementform_id_temp0" ).removeClass( "ui-spinner-input" )
							.prop( "disabled", false )
							.removeAttr( "autocomplete" )
							.removeAttr( "role" )
							.removeAttr( "aria-valuenow" );
							
							span_ui= document.getElementById(i+"_elementform_id_temp0").parentNode;
								span_ui.parentNode.appendChild(document.getElementById(i+"_elementform_id_temp0"));
								span_ui.parentNode.removeChild(span_ui);
							
							jQuery("#"+i+"_elementform_id_temp0")[0].spin = null;
							jQuery("#"+i+"_elementform_id_temp1")[0].spin = null;
							
							
							spinner0 = jQuery( "#"+i+"_elementform_id_temp0" ).spinner();
							spinner0.spinner( "value", spinner_value0 );
							jQuery( "#"+i+"_elementform_id_temp0" ).spinner({ step: spinner_step});
							
							
							
								var spinner_value1 = jQuery('#'+i+"_elementform_id_temp1").get( "aria-valuenow" );
												  
									 jQuery( "#"+i+"_elementform_id_temp1" ).removeClass( "ui-spinner-input" )
							.prop( "disabled", false )
							.removeAttr( "autocomplete" )
							.removeAttr( "role" )
							.removeAttr( "aria-valuenow" );
							
							span_ui1= document.getElementById(i+"_elementform_id_temp1").parentNode;
							span_ui1.parentNode.appendChild(document.getElementById(i+"_elementform_id_temp1"));
							span_ui1.parentNode.removeChild(span_ui1);
							
							spinner1 = jQuery( "#"+i+"_elementform_id_temp1" ).spinner();
							spinner1.spinner( "value", spinner_value1 );
							jQuery( "#"+i+"_elementform_id_temp1" ).spinner({ step: spinner_step});
				
								break;
						}
						case "type_grading":
						{
							
							for(k=0; k<100; k++)
								if(document.getElementById(i+"_elementform_id_temp"+k))
								{
									remove_add_(i+"_elementform_id_temp"+k);
								}
						
							
							break;
						}
						
						case "type_matrix":
						{	
							remove_add_(i+"_elementform_id_temp");
						
								break;
						}	
				}	
		}
	}
	

	for(t=1;t<=form_view_max<?php echo $id ?>;t++)
	{
		if(document.getElementById('form_id_tempform_view'+t))
		{
			form_view_element=document.getElementById('form_id_tempform_view'+t);
			remove_whitespace(form_view_element);
			xy=form_view_element.childNodes.length-2;
			for(z=0;z<=xy;z++)
			{
				if(form_view_element.childNodes[z])
				if(form_view_element.childNodes[z].nodeType!=3)
				if(!form_view_element.childNodes[z].id)
				{
					del=true;
					GLOBAL_tr=form_view_element.childNodes[z];
					//////////////////////////////////////////////////////////////////////////////////////////
					for (x=0; x < GLOBAL_tr.firstChild.childNodes.length; x++)
					{
						table=GLOBAL_tr.firstChild.childNodes[x];
						tbody=table.firstChild;
						if(tbody.childNodes.length)
							del=false;
					}
					
					if(del)
					{
						form_view_element.removeChild(form_view_element.childNodes[z]);
					}

				}
			}
		}
	}


	for(i=1; i<=window.parent.form_view_max; i++)
		if(document.getElementById('form_id_tempform_view'+i))
		{
			document.getElementById('form_id_tempform_view'+i).parentNode.removeChild(document.getElementById('form_id_tempform_view_img'+i));
			document.getElementById('form_id_tempform_view'+i).removeAttribute('style');
		}
	
}


</script>

	
	
<?php 


}


public static function add($themes){

		JRequest::setVar( 'hidemainmenu', 1 );
		
		$document = JFactory::getDocument();

		$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';

		$document->addScript(JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/wdformjquery.js');
		$document->addScript($cmpnt_js_path.'/if_gmap.js');
		

		$document->addScript('http://maps.google.com/maps/api/js?sensor=false');
		
		$document->addStyleSheet(JURI::root(true).'/administrator/components/com_formmaker/css/style.css');
		$document->addStyleSheet(JURI::root(true).'/administrator/components/com_formmaker/js/jquery-ui-spinner.css');
		
		$is_editor=false;
		
		$plugin = JPluginHelper::getPlugin('editors', 'tinymce');
		if (isset($plugin->type))
		{ 
			$editor	= JFactory::getEditor('tinymce');
			$is_editor=true;
		}
		$editor	= JFactory::getEditor('tinymce');
		JHTML::_('behavior.tooltip');	
		JHTML::_('behavior.calendar');
		JHTML::_('behavior.modal');
		?>
	
 
<script type="text/javascript">
if($)
if(typeof $.noConflict === 'function'){
   $.noConflict();
}
var already_submitted=false;
function refresh_()
{
				
	document.getElementById('counter').value=gen;
	
	for(i=1; i<=form_view_max; i++)
		if(document.getElementById('form_id_tempform_view'+i))
		{
			if(document.getElementById('page_next_'+i))
				document.getElementById('page_next_'+i).removeAttribute('src');
			if(document.getElementById('page_previous_'+i))
				document.getElementById('page_previous_'+i).removeAttribute('src');
			document.getElementById('form_id_tempform_view'+i).parentNode.removeChild(document.getElementById('form_id_tempform_view_img'+i));
			document.getElementById('form_id_tempform_view'+i).removeAttribute('style');
		}
		
	document.getElementById('form_front').value=document.getElementById('take').innerHTML;
}

Joomla.submitbutton= function (pressbutton){

	var form = document.adminForm;
	if (pressbutton == 'cancel') 
	{
		submitform( pressbutton );
		return;
	}
if (already_submitted ) 
	{
		submitform( pressbutton );
		return;
	}
	if (form.title.value == "")
	{
		alert( "The form must have a title." );	
		return ;
	}		

	
	document.getElementById('take').style.display="none";
	document.getElementById('page_bar').style.display="none";
	
	document.getElementById('saving').style.display="block";
	remove_whitespace(document.getElementById('take'));
	tox='';
	form_fields='';
	
	for(t=1;t<=form_view_max;t++)
	{
		if(document.getElementById('form_id_tempform_view'+t))
		{
			wdform_page=document.getElementById('form_id_tempform_view'+t);
			n=wdform_page.childNodes.length-2;

			for(z=0;z<=n;z++)
			{
				if(!wdform_page.childNodes[z].getAttribute("wdid"))
				{
					wdform_section=wdform_page.childNodes[z];
					for (x=0; x < wdform_section.childNodes.length; x++)
					{
						wdform_column=wdform_section.childNodes[x];
						if(wdform_column.firstChild)
						for (y=0; y < wdform_column.childNodes.length; y++)
						{
							wdform_row=wdform_column.childNodes[y];
							wdid=wdform_row.getAttribute("wdid");
							l_label = document.getElementById( wdid+'_element_labelform_id_temp').innerHTML;
							l_label = l_label.replace(/(\r\n|\n|\r)/gm," ");
							wdtype=wdform_row.firstChild.getAttribute('type');

							if(wdtype=="type_address")
							{
								addr_id=parseInt(wdid);
								id_for_country= addr_id;
								
								if(document.getElementById(id_for_country+"_mini_label_street1"))
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_street1").innerHTML+'#**label**#type_address#****#';addr_id++; 
								if(document.getElementById(id_for_country+"_mini_label_street2"))	
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_street2").innerHTML+'#**label**#type_address#****#';addr_id++; 	
								if(document.getElementById(id_for_country+"_mini_label_city"))	
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_city").innerHTML+'#**label**#type_address#****#';	addr_id++;
								if(document.getElementById(id_for_country+"_mini_label_state"))	
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_state").innerHTML+'#**label**#type_address#****#';	addr_id++;		
								if(document.getElementById(id_for_country+"_mini_label_postal"))
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_postal").innerHTML+'#**label**#type_address#****#';	addr_id++; 
								if(document.getElementById(id_for_country+"_mini_label_country"))
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_country").innerHTML+'#**label**#type_address#****#'; 
							}
							else
								tox=tox+wdid+'#**id**#'+l_label+'#**label**#'+wdtype+'#****#';
								
							
							id=wdid;
							form_fields+=wdid+"*:*id*:*";
							form_fields+=wdtype+"*:*type*:*";
							
							
							w_choices=new Array();	
							w_choices_checked=new Array();
							w_choices_disabled=new Array();
							w_allow_other_num=0;
							w_property=new Array();	
							w_property_type=new Array();	
							w_property_values=new Array();
							w_choices_price=new Array();
							
							if(document.getElementById(id+'_element_labelform_id_temp').innerHTML)
								w_field_label=document.getElementById(id+'_element_labelform_id_temp').innerHTML.replace(/(\r\n|\n|\r)/gm," ");
								
							if(document.getElementById(id+'_label_sectionform_id_temp'))
							if(document.getElementById(id+'_label_sectionform_id_temp').style.display=="block")
								w_field_label_pos="top";
							else
								w_field_label_pos="left";
								
							if(document.getElementById(id+"_elementform_id_temp"))
							{
								s=document.getElementById(id+"_elementform_id_temp").style.width;
								 w_size=s.substring(0,s.length-2);
							}
							
							if(document.getElementById(id+"_label_sectionform_id_temp"))
							{
								s=document.getElementById(id+"_label_sectionform_id_temp").style.width;
								w_field_label_size=s.substring(0,s.length-2);
							}
							
							if(document.getElementById(id+"_requiredform_id_temp"))
								w_required=document.getElementById(id+"_requiredform_id_temp").value;
								
							if(document.getElementById(id+"_uniqueform_id_temp"))
								w_unique=document.getElementById(id+"_uniqueform_id_temp").value;
								
							if(document.getElementById(id+'_label_sectionform_id_temp'))
							{
								w_class=document.getElementById(id+'_label_sectionform_id_temp').getAttribute("class");
								if(!w_class)
									w_class="";
							}
								
							gen_form_fields();
							wdform_row.innerHTML="%"+id+" - "+l_label+"%";
							
						}
					
					}
					
					
				}
				
				else
				{
						id=wdform_page.childNodes[z].getAttribute("wdid");
						w_editor=document.getElementById(id+"_element_sectionform_id_temp").innerHTML;
						
						form_fields+=id+"*:*id*:*";
						form_fields+="type_section_break"+"*:*type*:*";
												
						form_fields+="custom_"+id+"*:*w_field_label*:*";
						form_fields+=w_editor+"*:*w_editor*:*";
						form_fields+="*:*new_field*:*";
						wdform_page.childNodes[z].innerHTML="%"+id+" - "+"custom_"+id+"%";
						

				}
			}
		}
	}
	
	document.getElementById('form_fields').value=form_fields;
	document.getElementById('label_order').value=tox;
	document.getElementById('label_order_current').value=tox;
	refresh_();
	document.getElementById('pagination').value=document.getElementById('pages').getAttribute("type");
	document.getElementById('show_title').value=document.getElementById('pages').getAttribute("show_title");
	document.getElementById('show_numbers').value=document.getElementById('pages').getAttribute("show_numbers");
	
	already_submitted= true; 
		
	submitform( pressbutton );

}

gen=1; 
form_view=1; 
form_view_max=1; 
form_view_count=1;



 //add main form  id
    function enable()
	{
	alltypes=Array('customHTML','text','checkbox','radio','time_and_date','select','file_upload','captcha','map','button','page_break','section_break','paypal','survey');
	for(x=0; x<14;x++)
	{
		document.getElementById('img_'+alltypes[x]).src="components/com_formmaker/images/"+alltypes[x]+".png";
	}
	
		if(document.getElementById('formMakerDiv').style.display=='block'){jQuery('#formMakerDiv').slideToggle(200);}else{jQuery('#formMakerDiv').slideToggle(400);}
		
		if(document.getElementById('formMakerDiv').offsetWidth)
			document.getElementById('formMakerDiv1').style.width	=(document.getElementById('formMakerDiv').offsetWidth - 60)+'px';
		if(document.getElementById('formMakerDiv1').style.display=='block'){jQuery('#formMakerDiv1').slideToggle(200);}else{jQuery('#formMakerDiv1').slideToggle(400);}
		document.getElementById('when_edit').style.display		='none';
	}

    function enable2()
	{
	alltypes=Array('customHTML','text','checkbox','radio','time_and_date','select','file_upload','captcha','map','button','page_break','section_break','paypal','survey');
	for(x=0; x<14;x++)
	{
		document.getElementById('img_'+alltypes[x]).src="components/com_formmaker/images/"+alltypes[x]+".png";
	}
	
		if(document.getElementById('formMakerDiv').style.display=='block'){jQuery('#formMakerDiv').slideToggle(200);}else{jQuery('#formMakerDiv').slideToggle(400);}
		
		if(document.getElementById('formMakerDiv').offsetWidth)
			document.getElementById('formMakerDiv1').style.width	=(document.getElementById('formMakerDiv').offsetWidth - 60)+'px';
	if(document.getElementById('formMakerDiv1').style.display=='block'){jQuery('#formMakerDiv1').slideToggle(200);}else{jQuery('#formMakerDiv1').slideToggle(400);}

	document.getElementById('when_edit').style.display		='block';
		if(document.getElementById('field_types').offsetWidth)
			document.getElementById('when_edit').style.width	=document.getElementById('field_types').offsetWidth+'px';
		
		if(document.getElementById('field_types').offsetHeight)
			document.getElementById('when_edit').style.height	=document.getElementById('field_types').offsetHeight+'px';
		
	}
	

    </script>
<style>
#take_temp .element_toolbar, #take_temp .page_toolbar, #take_temp .captcha_img, #take_temp .wdform_stars
{
display:none;
}
#when_edit
{
position:absolute;
background-color:#666;
z-index:101;
display:none;
width:100%;
height:100%;
opacity: 0.7;
filter: alpha(opacity = 70);
}
#formMakerDiv
{
position:fixed;
background-color:#666;
z-index:100;
display:none;
left:0;
top:0;
width:100%;
height:100%;
opacity: 0.7;
filter: alpha(opacity = 70);
}
#formMakerDiv1
{
position:fixed;
z-index:100;
background-color:transparent;
top:0;
left:0;
display:none;
margin-left:30px;
margin-top:35px;
}

input[type="radio"], input[type="checkbox"] {
margin: 0px 4px 5px 5px;
}


.pull-left
{
float:none !important;
}

.modal-body
{
max-height:100%;
}

.wdform_date
{
margin:0px !important;
}

img
{
max-width:none;
}

.formmaker_table input
{
border-radius: 3px;
padding: 2px;
}

.formmaker_table
{
border-radius:8px;
border:6px #00aeef solid;
background-color:#00aeef;
height:120px;
}

.formMakerDiv1_table
{
border:6px #00aeef solid;
background-color:#FFF;
border-radius:8px;
}

label
{
display:inline;
}

</style>
<form action="index.php" method="post" name="adminForm" id="adminForm" enctype="multipart/form-data">
<div  class="formmaker_table" width="100%" >
<div style="float:left; text-align:center">
 	</br>
   <img src="components/com_formmaker/images/FormMaker.png" />
	</br>
	</br>
	<img src="components/com_formmaker/images/logo.png" />

</div>

<div style="float:right">

    <span style="font-size:16.76pt; font-family:tahoma; color:#FFFFFF; vertical-align:middle;">Form title:&nbsp;&nbsp;</span>

    <input id="title" name="title" style="width:151px; height:19px; border:none; font-size:11px; "  />
	<br/>
	<a href="#" onclick="Joomla.submitbutton('form_options_temp')" style="cursor:pointer;margin:10px; float:right; color:#fff; font-size:20px"><img src="components/com_formmaker/images/formoptions.png" /></a>    
	<br/>
	<img src="components/com_formmaker/images/addanewfield.png" onclick="enable(); Enable()" style="cursor:pointer;margin:10px; float:right" />

</div>
	
  

</div>
 
  
<div id="formMakerDiv" onclick="close_window()"></div>   

<div id="formMakerDiv1" align="center">
    
<table border="0" width="100%" cellpadding="0" cellspacing="0" height="100%" style="border:6px #00aeef solid; background-color:#FFF">
  <tr>
    <td style="padding:0px">
    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
		 <td width="15%" height="100%" style="border-right:dotted black 1px;" id="field_types">
			<div id="when_edit" style="display:none"></div>
            <table border="0" cellpadding="0" cellspacing="3" width="100%" style="border-collapse: separate; border-spacing: 3px;">
				<tr>
					<td align="center" onClick="addRow('customHTML')" class="field_buttons" id="table_editor"><img src="components/com_formmaker/images/customHTML.png" style="margin:5px" id="img_customHTML"/></td>
					<td align="center" onClick="addRow('text')" class="field_buttons" id="table_text"><img src="components/com_formmaker/images/text.png" style="margin:5px" id="img_text"/></td>
				</tr>
				<tr>             
					<td align="center" onClick="addRow('checkbox')" class="field_buttons" id="table_checkbox"><img src="components/com_formmaker/images/checkbox.png" style="margin:5px" id="img_checkbox"/></td>
					<td align="center" onClick="addRow('radio')" class="field_buttons" id="table_radio"><img src="components/com_formmaker/images/radio.png" style="margin:5px" id="img_radio"/></td>
				</tr>
				<tr>
					<td align="center" onClick="addRow('survey')" class="field_buttons" id="table_survey"><img src="components/com_formmaker/images/survey.png" style="margin:5px" id="img_survey"/></td>           
					<td align="center" onClick="addRow('time_and_date')" class="field_buttons" id="table_time_and_date"><img src="components/com_formmaker/images/time_and_date.png" style="margin:5px" id="img_time_and_date"/></td>
			   </tr>
				<tr>
					<td align="center" onClick="addRow('select')" class="field_buttons" id="table_select"><img src="components/com_formmaker/images/select.png" style="margin:5px" id="img_select"/></td>
					<td align="center" onClick="alert('This field type is disabled in free version. If you need this functionality, you need to buy the commercial version.')" class="field_buttons" id="table_file_upload" style="background:#727171 !important;"><img src="components/com_formmaker/images/file_upload.png" style="margin:5px" id="img_file_upload"/></td>
				</tr>
				<tr>
					<td align="center" onClick="addRow('section_break')" class="field_buttons" id="table_section_break"><img src="components/com_formmaker/images/section_break.png" style="margin:5px" id="img_section_break"/></td>
					<td align="center" onClick="addRow('page_break')" class="field_buttons" id="table_page_break"><img src="components/com_formmaker/images/page_break.png" style="margin:5px" id="img_page_break"/></td>  
				</tr>
				<tr>
					<td align="center" onClick="alert('This field type is disabled in free version. If you need this functionality, you need to buy the commercial version.')" class="field_buttons" id="table_map" style="background:#727171 !important;"><img src="components/com_formmaker/images/map.png" style="margin:5px" id="img_map"/></td>  
					<td align="center" onClick="alert('This field type is disabled in free version. If you need this functionality, you need to buy the commercial version.')" id="table_paypal" class="field_buttons" style="background:#727171 !important;"><img src="components/com_formmaker/images/paypal.png" style="margin:5px" id="img_paypal" /></td>       
			   </tr>
				<tr>
					<td align="center" onClick="addRow('captcha')" class="field_buttons" id="table_captcha"><img src="components/com_formmaker/images/captcha.png" style="margin:5px" id="img_captcha"/></td>
					<td align="center" onClick="addRow('button')" id="table_button" class="field_buttons" ><img src="components/com_formmaker/images/button.png" style="margin:5px" id="img_button"/></td>			
				</tr>
            </table>
         </td>
         <td width="35%" height="100%" align="left"><div id="edit_table" style="padding:0px; overflow-y:scroll; height:535px" ></div></td>
   <td align="center" valign="top" style="background:url(components/com_formmaker/images/border2.png) repeat-y;">&nbsp;</td>
         <td style="padding:15px">
         <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
         
            <tr>
                <td align="right"><input type="radio" value="end" name="el_pos" checked="checked" id="pos_end" onclick="Disable()"/>
                  At The End
                  <input type="radio" value="begin" name="el_pos" id="pos_begin" onclick="Disable()"/>
                  At The Beginning
                  <input type="radio" value="before" name="el_pos" id="pos_before" onclick="Enable()"/>
                  Before
                  <select style="width:100px; margin-left:5px" id="sel_el_pos" onclick="change_before()" disabled="disabled">
                  </select>
                  <img alt="ADD" title="add" style="cursor:pointer; vertical-align:middle; margin:5px" src="components/com_formmaker/images/save.png" onClick="add(0)"/>
                  <img alt="CANCEL" title="cancel"  style=" cursor:pointer; vertical-align:middle; margin:5px" src="components/com_formmaker/images/cancel_but.png" onClick="close_window()"/>
				
                	<hr style=" margin-bottom:10px" />
                  </td>
              </tr>
              
              <tr height="100%" valign="top">
                <td  id="show_table"></td>
              </tr>
              
            </table>
         </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" id="old" />
<input type="hidden" id="old_selected" />
<input type="hidden" id="element_type" />
<input type="hidden" id="editing_id" />
<input type="hidden" id="editing_page_break" />
<div id="main_editor" style="position:absolute; display:none; z-index:140;"><?php if($is_editor) echo $editor->display('editor','','440','350','40','6');
else
{
?>
<textarea name="editor" id="editor" cols="40" rows="6" style="width: 440px; height: 350px; " class="mce_editable" aria-hidden="true"></textarea>
<?php

}
 ?></div>

</div>


<?php if(!$is_editor)
?>
<iframe id="editor_ifr" style="display:none"></iframe>

<?php
?>

<br />
<br />

<fieldset>

    <legend>

    <h2 style="color:#00aeef">Form</h2>
    
    </legend>

     <style><?php echo self::first_css; ?></style>
<div id="saving" style="display:none">
	<div id="saving_text">Saving</div>
	<div id="fadingBarsG">
	<div id="fadingBarsG_1" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_2" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_3" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_4" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_5" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_6" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_7" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_8" class="fadingBarsG">
	</div>
	</div>
</div>


<div style="margin:8px; display:table; width:100%"  id="page_bar"><div id="page_navigation" style="display:table-row"><div align="center" id="pages" show_title="false" show_numbers="true" type="none" style="display:table-cell;  width:90%"></div><div align="left" id="edit_page_navigation" style="display:table-cell; vertical-align: middle;"></div></div></div><div id="take" class="main"><div class="wdform-page-and-images" style="display:table; border-top:0px solid black;"><div id="form_id_tempform_view1" class="wdform_page" page_title="Untitled page" next_title="Next" next_type="text" next_class="wdform-page-button" next_checkable="false" previous_title="Previous" previous_type="text" previous_class="wdform-page-button" previous_checkable="false"><div class="wdform_section"><div class="wdform_column"></div></div><div valign="top" class="wdform_footer" style="width: 100%;"><div style="width: 100%;"><div style="width: 100%; display: table;"><div style="display: table-row-group;"><div id="form_id_temppage_nav1" style="display: table-row;"></div></div></div></div></div></div><div id="form_id_tempform_view_img1" style="float:right ;"><div><img src="components/com_formmaker/images/minus.png" title="Show or hide the page" class="page_toolbar" onClick="show_or_hide('1')" onmouseover="chnage_icons_src(this,'minus')"  onmouseout="chnage_icons_src(this,'minus')" id="show_page_img_1" style="margin: 5px 5px 5px 0;"/><img src="components/com_formmaker/images/page_delete.png" title="Delete the page" class="page_toolbar" onClick="remove_page('1')" onmouseover="chnage_icons_src(this,'page_delete')"  onmouseout="chnage_icons_src(this,'page_delete')" style="margin: 5px 5px 5px 0;"/><img src="components/com_formmaker/images/page_delete_all.png" title="Delete the page with fields" class="page_toolbar" onClick="remove_page_all('1')" onmouseover="chnage_icons_src(this,'page_delete_all')"  onmouseout="chnage_icons_src(this,'page_delete_all')" style="margin: 5px 5px 5px 0;"/><img src="components/com_formmaker/images/page_edit.png" title="Edit the page" class="page_toolbar" onClick="edit_page_break('1')" onmouseover="chnage_icons_src(this,'page_edit')"  onmouseout="chnage_icons_src(this,'page_edit')" style="margin: 5px 5px 5px 0;"/></div></div></div></div></fieldset>

    <input type="hidden" name="form_front" id="form_front" />
    <input type="hidden" name="counter" id="counter" />
    <input type="hidden" name="mail" id="mail" />
	
	<?php 
	$form_theme='';
	foreach($themes as $theme) 
	{
		if($theme->default == 1 )
			$form_theme=$theme->id;		
	}
	?>
	<input type="hidden" name="theme" id="theme" value="<?php echo $form_theme?>" />

    <input type="hidden" name="pagination" id="pagination" />
    <input type="hidden" name="show_title" id="show_title" />
    <input type="hidden" name="show_numbers" id="show_numbers" />
    <input type="hidden" name="payment_currency" id="show_numbers" value="USD"/>
	
    <input type="hidden" name="public_key" id="public_key" />
    <input type="hidden" name="private_key" id="private_key" />
    <input type="hidden" name="recaptcha_theme" id="recaptcha_theme" />
	<input type="hidden" name="javascript" id="javascript" value="// Occurs before the form is loaded
function before_load()
{

}

// Occurs just before submitting  the form
function before_submit()
{

}

// Occurs just before resetting the form
function before_reset()
{

}">
	<input type="hidden" name="script_mail" id="script_mail" value="%all%" />
	<input type="hidden" name="script_mail_user" id="script_mail_user"  value="%all%" />
	<input type="hidden" name="form_fields" id="form_fields" />
	<input type="hidden" name="label_order" id="label_order" />
	<input type="hidden" name="label_order_current" id="label_order_current" />
    <input type="hidden" name="option" value="com_formmaker" />

    <input type="hidden" name="task" value="" />

</form>




	<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
	<script src="<?php echo  $cmpnt_js_path ?>/formmaker_div1.js" type="text/javascript" style=""></script>
	<script src="<?php echo  JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/wdformjquery.js'; ?>" type="text/javascript"></script>
	<script src="<?php echo  JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/jquery-ui.js'; ?>" type="text/javascript"></script>
	
	
  <?php
 
	}

public static function show_submits(&$rows, &$forms, &$lists, &$pageNav, &$labels, $label_titles, $rows_ord, $filter_order_Dir,$form_id, $labels_id, $sorted_labels_type, $total_entries, $total_views, $where, $where3)
{
	$label_titles_copy=$label_titles;
	JHTML::_('behavior.tooltip');
	JHTML::_('behavior.calendar');
	JHTML::_('behavior.modal');
	JHtml::_('formbehavior.chosen', 'select');
	$mainframe = JFactory::getApplication();
JSubMenuHelper::addEntry(JText::_('Forms'), 'index.php?option=com_formmaker&amp;task=forms' );
JSubMenuHelper::addEntry(JText::_('Submissions'), 'index.php?option=com_formmaker&amp;task=submits',true );
JSubMenuHelper::addEntry(JText::_('Themes'), 'index.php?option=com_formmaker&amp;task=themes' );
	$language = JFactory::getLanguage();
	$language->load('com_formmaker', JPATH_SITE, null, true);	

	$n=count($rows);
	$m=count($labels);
	$group_id_s= array();
	
	$rows_ord_none=array();
	
	if(count($rows_ord)>0 and $m)
	for($i=0; $i <count($rows_ord) ; $i++)
	{
	
		$row = &$rows_ord[$i];
	
		if(!in_array($row->group_id, $group_id_s))
		{
		
			array_push($group_id_s, $row->group_id);
			
		}
	}
	?>

<script type="text/javascript">
Joomla.tableOrdering=  function( order, dir, task ) 
{
    var form = document.adminForm;
    form.filter_order2.value     = order;
    form.filter_order_Dir2.value = dir;
    submitform( task );
}

function renderColumns()
{
	allTags=document.getElementsByTagName('*');
	
	for(curTag in allTags)
	{
		if(typeof(allTags[curTag].className)!="undefined")
		if(allTags[curTag].className.indexOf('_fc')>0)
		{
			curLabel=allTags[curTag].className.replace('_fc','');
			if(document.forms.adminForm.hide_label_list.value.indexOf('@'+curLabel+'@')>=0)
				allTags[curTag].style.display = 'none';
			else
				allTags[curTag].style.display = '';
		}
	}
}

function clickLabChB(label, ChB)
{
	document.forms.adminForm.hide_label_list.value=document.forms.adminForm.hide_label_list.value.replace('@'+label+'@','');
	if(document.forms.adminForm.hide_label_list.value=='') document.getElementById('ChBAll').checked=true;
	
	if(!(ChB.checked)) 
	{
		document.forms.adminForm.hide_label_list.value+='@'+label+'@';
		document.getElementById('ChBAll').checked=false;
	}
	renderColumns();
}

function toggleChBDiv(b)
{
	if(b)
	{
		sizes=window.getSize();
		document.getElementById("sbox-overlay").style.width=sizes.x+"px";
		document.getElementById("sbox-overlay").style.height=sizes.y+"px";
		document.getElementById("ChBDiv").style.left=Math.floor((sizes.x-350)/2)+"px";
		
		document.getElementById("ChBDiv").style.display="block";
		document.getElementById("sbox-overlay").style.display="block";
	}
	else
	{
		document.getElementById("ChBDiv").style.display="none";
		document.getElementById("sbox-overlay").style.display="none";
	}
}

function clickLabChBAll(ChBAll)
{
	<?php
	if(isset($labels))
	{
		$templabels=array_merge(array('submitid','submitdate','submitterip'),$labels_id);
		$label_titles=array_merge(array('ID','Submit date', 'Submitter\'s IP Address'),$label_titles);
	}
	?>

	if(ChBAll.checked)
	{ 
		document.forms.adminForm.hide_label_list.value='';

		for(i=0; i<=ChBAll.form.length; i++)
			if(typeof(ChBAll.form[i])!="undefined")
				if(ChBAll.form[i].type=="checkbox")
					ChBAll.form[i].checked=true;
	}
	else
	{
		document.forms.adminForm.hide_label_list.value='@<?php echo implode($templabels,'@@') ?>@'+'@payment_info@';

		for(i=0; i<=ChBAll.form.length; i++)
			if(typeof(ChBAll.form[i])!="undefined")
				if(ChBAll.form[i].type=="checkbox")
					ChBAll.form[i].checked=false;
	}

	renderColumns();
}

function remove_all()
{
	document.getElementById('startdate').value='';
	document.getElementById('enddate').value='';
	document.getElementById('ip_search').value='';
	<?php
		$n=count($rows);
	
	for($i=0; $i < count($labels) ; $i++)
	{
	echo "
	if(document.getElementById('".$form_id.'_'.$labels_id[$i]."_search'))
		document.getElementById('".$form_id.'_'.$labels_id[$i]."_search').value='';
	";
	}
	?>
}

function show_hide_filter()
{	
	if(document.getElementById('fields_filter').style.display=="none")
	{
		document.getElementById('fields_filter').style.display='';
		document.getElementById('filter_img').src='components/com_formmaker/images/filter_hide.png';
	}
	else
	{
		document.getElementById('fields_filter').style.display="none";
		document.getElementById('filter_img').src='components/com_formmaker/images/filter_show.png';
	}
}
</script>

<style>
.reports
{
	border:1px solid #DEDEDE;
	border-radius:11px;
	background-color:#F0F0F0;
	text-align:center;
	width:100px;
}

.bordered
{
	border-radius:8px
}

pre
{
background:none;
border:0px;
}

#fields_filter th
{
vertical-align:middle !important;
}

input[type="radio"], input[type="checkbox"] {
margin: 0px 4px 5px 5px;
}

select{
margin: 0px !important;
}
</style>
<form action="index.php?option=com_formmaker&task=submits" method="post" name="adminForm" id="adminForm">
    <input type="hidden" name="option" value="com_formmaker">
    <input type="hidden" name="task" value="submits">
<br />
    <table width="100%" style="border-collapse: separate; border-spacing: 2px;">

        <tr >
            <td align="left" width="300"> Select a form:             
                <select name="form_id" id="form_id" onchange="if(document.getElementById('startdate'))remove_all();document.adminForm.submit();">
                    <option value="0" selected="selected"> Select a Form </option>
                    <?php 
            $option='com_formmaker';
            
            if( $forms)
            for($i=0, $n=count($forms); $i < $n ; $i++)
        
            {
                $form = &$forms[$i];
        
        
                if($form_id==$form->id)
                {
                    echo "<option value='".$form->id."' selected='selected'>".$form->title."</option>";
                    $form_title=$form->title;
                }
                else
                    echo "<option value='".$form->id."' >".$form->title."</option>";
            }
        ?>
                    </select>
            </td>
			<?php if(isset($form_id) and $form_id>0):  ?>
			<td class="reports" ><strong>Entries</strong><br /><?php echo $total_entries; ?></td>
			<td class="reports"><strong>Views</strong><br /><?php echo $total_views ?></td>
            <td class="reports"><strong>Conversion Rate</strong><br /><?php  if($total_views) echo round((($total_entries/$total_views)*100),2).'%'; else echo '0%' ?></td>
			<td style="font-size:24px;text-align:center;">
				<?php echo $form_title ?>
			</td>
			<td style="text-align:right;" width="300">
                Export to
 				<input type="button" value="CSV" class="btn" onclick="window.location='index.php?option=com_formmaker&task=generate_csv&format=row&id=<?php echo $form_id; ?>'" />&nbsp;
				<input type="button" value="XML" class="btn" onclick="window.location='index.php?option=com_formmaker&task=generate_xml&format=row&id=<?php echo $form_id; ?>'" />
			</td>
		
			
        </tr>
        
        <tr>

            <td colspan=5>
                <br />
                <input type="hidden" name="hide_label_list" value="<?php  echo $lists['hide_label_list']; ?>" /> 
                <img id="filter_img" src="components/com_formmaker/images/filter_show.png" width="40" style="vertical-align:middle; cursor:pointer" onclick="show_hide_filter()"  title="Search by fields" />
				<button class="btn tip hasTooltip" type="submit" data-original-title="Search"><i class="icon-search"></i></button>
				<button class="btn tip hasTooltip" type="button" onclick="remove_all();this.form.submit();" data-original-title="Clear">
				<i class="icon-remove"></i></button>
            </td>
			<td align="right">
                <br /><br />
                <?php if(isset($labels)) echo '<input type="button" class="btn" onclick="toggleChBDiv(true)" value="Add/Remove Columns" style="vertical-align: top;" />'; ?>
				<?php echo $pageNav->getLimitBox(); ?>

			</td>
        </tr>

		<?php else: echo '<td><br /><br /><br /></td></tr>'; endif; ?>
    </table>
    <table class="table table-striped" width="100%">
        <thead>
            <tr>

                <th width="3%"><?php echo '#'; ?></th>

				<th width="4%" class="hidden-phone">
						<input type="checkbox" name="checkall-toggle" value="" title="<?php echo JText::_('JGLOBAL_CHECK_ALL'); ?>" onclick="Joomla.checkAll(this)" />
				</th>
				
				 <?php
				 echo '<th width="4%" class="submitid_fc"';
				 if(!(strpos($lists['hide_label_list'],'@submitid@')===false)) 
				 echo 'style="display:none;"';
				 echo '>';
				 echo JHTML::_('grid.sort', 'Id', 'group_id', @$lists['order_Dir'], @$lists['order'] );
				 echo '</th>';
				 
				 echo '<th width="150" class="submitdate_fc"';
				 if(!(strpos($lists['hide_label_list'],'@submitdate@')===false)) 
				 echo 'style="display:none;"';
				 echo '>';
				 echo JHTML::_('grid.sort', 'Submit date', 'date', @$lists['order_Dir'], @$lists['order'] );
				 echo '</th>';

				 echo '<th width="100" class="submitterip_fc"';
				 if(!(strpos($lists['hide_label_list'],'@submitterip@')===false)) 
				 echo 'style="display:none;"';
				 echo '>';
				 echo JHTML::_('grid.sort', 'Submitter\'s IP Address', 'ip', @$lists['order_Dir'], @$lists['order'] );
				 echo '</th>';
				 
				 
	$n=count($rows);
	$ispaypal=false;
	
	for($i=0; $i < count($labels) ; $i++)
	{
		if(strpos($lists['hide_label_list'],'@'.$labels_id[$i].'@')===false)  $styleStr='';
		else $styleStr='style="display:none;"';
		
		
			$field_title=$label_titles_copy[$i];
			
		if($sorted_labels_type[$i]=='type_paypal_payment_status')		
		{	
			$ispaypal=true;
			echo '<th class="'.$labels_id[$i].'_fc" '.$styleStr.'>'.JHTML::_('grid.sort', $field_title, $labels_id[$i]."_field", @$lists['order_Dir'], @$lists['order'] ).'</th>';
						if(strpos($lists['hide_label_list'],'@payment_info@')===false)  
							$styleStr2='aa';
                        else 
							$styleStr2='style="display:none;"';
							
			echo '<th class="payment_info_fc" '.$styleStr2.'>Payment Info</th>';
			}
		else
			echo '<th class="'.$labels_id[$i].'_fc" '.$styleStr.'>'.JHTML::_('grid.sort', $field_title, $labels_id[$i]."_field", @$lists['order_Dir'], @$lists['order'] ).'</th>';
	}
?>

            </tr>
            <tr id="fields_filter" style="display:none; background:#F1F1F1">
                <th width="3%"></th>
                <th width="3%"></th>
				<th width="4%" class="submitid_fc" <?php if(!(strpos($lists['hide_label_list'],'@submitid@')===false)) echo 'style="display:none;"';?> ></th>
				
				
				<th width="150" class="submitdate_fc" style="text-align:left; <?php if(!(strpos($lists['hide_label_list'],'@submitdate@')===false)) echo 'display:none;';?>" align="center"> 
				<table class="simple_table">
					<tr class="simple_table">
						<td class="simple_table">From:</td>
						<td class="simple_table"><input class="inputbox" type="text" name="startdate" id="startdate" style="width:70px" maxlength="10" value="<?php echo $lists['startdate'];?>" /> </td>
						<td class="simple_table">
						<button class="btn" id="startdate_but"><i class="icon-calendar"></i></button>
						</td>
					</tr>
					<tr class="simple_table">
						<td class="simple_table">To:</td>
						<td class="simple_table"><input class="inputbox" type="text" name="enddate" id="enddate" style="width:70px" maxlength="10" value="<?php echo $lists['enddate'];?>" /> </td>
						<td class="simple_table">
						<button class="btn" id="enddate_but"><i class="icon-calendar"></i></button>
						</td>
					</tr>
				</table>
				
				</th>
				
				
				
				
				<th width="100"class="submitterip_fc"  <?php if(!(strpos($lists['hide_label_list'],'@submitterip@')===false)) echo 'style="display:none;"';?>>
                 <input type="text" name="ip_search" id="ip_search" value="<?php echo $lists['ip_search'] ?>" onChange="this.form.submit();" style="width:150px"/>
				</th>
				<?php				 
                    $n=count($rows);
					$ka_fielderov_search=false;
					
					if($lists['ip_search'] || $lists['startdate'] || $lists['enddate']){
						$ka_fielderov_search=true;
					}
					
                    for($i=0; $i < count($labels) ; $i++)
                    {
                        if(strpos($lists['hide_label_list'],'@'.$labels_id[$i].'@')===false)  
							$styleStr='';
                        else 
							$styleStr='style="display:none;"';
						
						if(!$ka_fielderov_search)
							if($lists[$form_id.'_'.$labels_id[$i].'_search'])
							{
								$ka_fielderov_search=true;
							} 



						switch($sorted_labels_type[$i])
						{
							case 'type_mark_map': echo '<th class="'.$labels_id[$i].'_fc" '.$styleStr.'>'.'</th>'; break;
							case 'type_paypal_payment_status':
							echo '<th class="'.$labels_id[$i].'_fc" '.$styleStr.'>';
?>
<select name="<?php echo $form_id.'_'.$labels_id[$i]; ?>_search" id="<?php echo $form_id.'_'.$labels_id[$i]; ?>_search" onChange="this.form.submit();" value="<?php echo $lists[$form_id.'_'.$labels_id[$i].'_search']; ?>" >
	<option value="" ></option>
	<option value="canceled" >Canceled</option>
	<option value="cleared" >Cleared</option>
	<option value="cleared by payment review" >Cleared by payment review</option>
	<option value="completed" >Completed</option>
	<option value="denied" >Denied</option>
	<option value="failed" >Failed</option>
	<option value="held" >Held</option>
	<option value="in progress" >In progress</option>
	<option value="on hold" >On hold</option>
	<option value="paid" >Paid</option>
	<option value="partially refunded" >Partially refunded</option>
	<option value="pending verification" >Pending verification</option>
	<option value="placed" >Placed</option>
	<option value="processing" >Processing</option>
	<option value="refunded" >Refunded</option>
	<option value="refused" >Refused</option>
	<option value="removed" >Removed</option>
	<option value="returned" >Returned</option>
	<option value="reversed" >Reversed</option>
	<option value="temporary hold" >Temporary hold</option>
	<option value="unclaimed" >Unclaimed</option>
</select>	
<script> 
    var element = document.getElementById('<?php echo $form_id.'_'.$labels_id[$i]; ?>_search');
    element.value = '<?php echo $lists[$form_id.'_'.$labels_id[$i].'_search']; ?>';
</script>
		<?php				
							echo '</th>';
							
							 
						if(strpos($lists['hide_label_list'],'@payment_info@')===false)  
							$styleStr2='aa';
                        else 
							$styleStr2='style="display:none;"';
							
						echo	'<th class="payment_info_fc" '.$styleStr2.'></th>';
							
						
							break;
							default : 			  echo '<th class="'.$labels_id[$i].'_fc" '.$styleStr.'>'.'<input name="'.$form_id.'_'.$labels_id[$i].'_search" id="'.$form_id.'_'.$labels_id[$i].'_search" type="text" value="'.$lists[$form_id.'_'.$labels_id[$i].'_search'].'"  onChange="this.form.submit();" >'.'</th>'; break;			
						
						}
						
						
                 }
                ?>
            </tr>
        </thead>
        <tfoot>
            <tr>
                <td colspan="100"> <?php echo $pageNav->getListFooter(); ?>

				</td>
            </tr>
        </tfoot>

        <?php
    $k = 0;
	$m=count($labels);
	$group_id_s= array();
	$l=0;
	if(count($rows_ord)>0 and $m)
	for($i=0; $i <count($rows_ord) ; $i++)
	{
	
		$row = &$rows_ord[$i];
	
		if(!in_array($row->group_id, $group_id_s))
		{
		
			array_push($group_id_s, $row->group_id);
			
		}
	}
	

	for($www=0, $qqq=count($group_id_s); $www < $qqq ; $www++)
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
		$checked 	= JHTML::_('grid.id', $www, $group_id_s[$www]);
		$link="index.php?option=com_formmaker&task=edit_submit&cid[]=".$f->group_id;
		?>

        <tr class="<?php echo "row$k"; ?>">

              <td align="center"><?php echo $www+1;?></td>

          <td align="center"><?php echo $checked?></td>
		  
<?php

if(strpos($lists['hide_label_list'],'@submitid@')===false)
echo '<td align="center" class="submitid_fc"><a href="'.$link.'" >'.$f->group_id.'</a></td>';
else 
echo '<td align="center" class="submitid_fc" style="display:none;"><a href="'.$link.'" >'.$f->group_id.'</a></td>';

if(strpos($lists['hide_label_list'],'@submitdate@')===false)
echo '<td align="center" class="submitdate_fc"><a href="'.$link.'" >'.$date.'</a></td>';
else 
echo '<td align="center" class="submitdate_fc" style="display:none;"><a href="'.$link.'" >'.$date.'</a></td>'; 

if(strpos($lists['hide_label_list'],'@submitterip@')===false)
echo '<td align="center" class="submitterip_fc"><a href="'.$link.'" >'.$ip.'</a></td>';
else 
echo '<td align="center" class="submitterip_fc" style="display:none;"><a href="'.$link.'" >'.$ip.'</a></td>';


		$ttt=count($temp);
		for($h=0; $h < $m ; $h++)
		{		
			$not_label=true;
			for($g=0; $g < $ttt ; $g++)
			{			
				$t = $temp[$g];
				if(strpos($lists['hide_label_list'],'@'.$labels_id[$h].'@')===false)  $styleStr='';
				else $styleStr='style="display:none;"';
				if($t->element_label==$labels_id[$h])
				{
					if(strpos($t->element_value,"***map***"))
					{
						$map_params=explode('***map***',$t->element_value);
						
						$longit	=$map_params[0];
						$latit	=$map_params[1];
						
						echo  '<td align="center" class="'.$labels_id[$h].'_fc" '.$styleStr.'><a class="modal"  href="index.php?option=com_formmaker&task=show_map&long='.$longit.'&lat='.$latit.'&tmpl=component" rel="{handler: \'iframe\', size: {x:630, y: 570}}">'.'Show on Map'."</a></td>";
					}
					else

						if(strpos($t->element_value,"*@@url@@*"))
						{
							$new_file=str_replace("*@@url@@*",'', str_replace("***br***",'<br>', $t->element_value));
							$new_filename=explode('/', $new_file);
							echo  '<td align="center" class="'.$labels_id[$h].'_fc" '.$styleStr.'><a target="_blank" href="'.$new_file.'">'.$new_filename[count($new_filename)-1]."</td>";
						}
					else
						if(strpos($t->element_value,"***star_rating***"))
						{	
                            $new_filename= str_replace("***star_rating***",'', $t->element_value);	
                            						
                            $stars="";						
							$new_filename=explode('***', $new_filename);
							for( $j=0;$j<$new_filename[1];$j++)
							$stars.='<img id="'.$t->element_label.'_star_'.$j.'" src="components/com_formmaker/images/star_yellow.png" /> ';
							for( $k=$new_filename[1];$k<$new_filename[0];$k++)
							$stars.='<img id="'.$t->element_label.'_star_'.$k.'" src="components/com_formmaker/images/star.png" /> ';
							
							echo  '<td align="center" class="'.$labels_id[$h].'_fc" '.$styleStr.'>'.$stars."</td>";
						}
					else
						if(strpos($t->element_value,"***matrix***"))
						{	
						
						echo  '<td align="center" class="'.$labels_id[$h].'_fc" '.$styleStr.'><a class="modal"  href="index.php?option=com_formmaker&task=show_matrix&matrix_params='.$t->element_value.'&tmpl=component" rel="{handler: \'iframe\', size: {x:650, y: 450}}">'.'Show Matrix'.'</a></td>';
						}
						
					else
						if(strpos($t->element_value,"***grading***"))
						{	
							$new_filename= str_replace("***grading***",'', $t->element_value);	
							$grading = explode(":",$new_filename);                        
							
							$items_count = sizeof($grading)-1;
							$items = "";
							$total = "";
						
							for($k=0;$k<$items_count/2;$k++)
							{
								$items .= $grading[$items_count/2+$k].": ".$grading[$k]."</br>";
								$total += $grading[$k];
							}
							
							$items .="Total: ".$total;
						
							echo  '<td align="center" class="'.$labels_id[$h].'_fc" '.$styleStr.'><pre style="font-family:inherit">'.$items.'</pre></td>';
						}			
						
						else
							echo  '<td align="center" class="'.$labels_id[$h].'_fc" '.$styleStr.'><pre style="font-family:inherit; white-space: pre;">'.str_replace("***br***",'<br>', $t->element_value).'</pre></td>';
					$not_label=false;
				}
			}
			if($not_label)
					echo  '<td align="center" class="'.$labels_id[$h].'_fc" '.$styleStr.'></td>';
		}
		
		if($ispaypal)
		{
			if(strpos($lists['hide_label_list'],'@payment_info@')===false)  $styleStr='';
				else $styleStr='style="display:none;"';
			echo  '<td align="center" class="payment_info_fc" '.$styleStr.'>		
			<a class="modal" href="index.php?option=com_formmaker&amp;task=paypal_info&amp;tmpl=component&amp;id='.$i.'" rel="{handler: \'iframe\', size: {x:703, y: 550}}">
	<img src="components/com_formmaker/images/info.png" /></a>
		
			</td>';
		}

?>
        </tr>

        <?php


		$k = 1 - $k;

	}

	?>

    </table>

	
	
        <?php
		    $db = JFactory::getDBO();

foreach($sorted_labels_type as $key => $label_type)
{
	if($label_type=="type_checkbox" || $label_type=="type_radio" || $label_type=="type_own_select" || $label_type=="type_country" || $label_type=="type_paypal_select" || $label_type=="type_paypal_radio" || $label_type=="type_paypal_checkbox" || $label_type=="type_paypal_shipping")
	{	
		?>
<br />
<br />

        <strong><?php echo $label_titles_copy[$key]?></strong>
<br />
<br />

    <?php

		$query = "SELECT element_value FROM #__formmaker_submits ".$where3." AND element_label='".$labels_id[$key]."'";
		$db->setQuery( $query);
		$choices = $db->loadObjectList();
	
		if($db->getErrorNum()){
			echo $db->stderr();
			return false;}
			
	$colors=array('#2CBADE','#FE6400');
	$choices_labels=array();
	$choices_count=array();
	$all=count($choices);
	$unanswered=0;	
	foreach($choices as $key => $choice)
	{
		if($choice->element_value=='')
		{
			$unanswered++;
		}
		else
		{
			if(!in_array( $choice->element_value,$choices_labels))
			{
				array_push($choices_labels, $choice->element_value);
				array_push($choices_count, 0);
			}

			$choices_count[array_search($choice->element_value, $choices_labels)]++;
		}
	}
	array_multisort($choices_count,SORT_DESC,$choices_labels);
	?>
	<table width="50%" class="adminlist">
		<thead>
			<tr>
				<th width="20%">Choices</th>
				<th>Percentage</th>
				<th width="10%">Count</th>
			</tr>
		</thead>
    <?php 
	foreach($choices_labels as $key => $choices_label)
	{
	?>
		<tr>
			<td><?php echo str_replace("***br***",'<br>', $choices_label)?></td>
			<td><div class="bordered" style="width:<?php echo ($choices_count[$key]/($all-$unanswered))*100; ?>%; height:18px; background-color:<?php echo $colors[$key % 2]; ?>"></td>
			<td><?php echo $choices_count[$key]?></td>
		</tr>
    <?php 
	}
	
	if($unanswered){
	?>
    <tr>
    <td colspan="2" align="right">Unanswered</th>
    <td><strong><?php echo $unanswered;?></strong></th>
	</tr>

	<?php	
	}
	?>
    <tr>
    <td colspan="2" align="right"><strong>Total</strong></th>
    <td><strong><?php echo $all;?></strong></th>
	</tr>

	</table>
	<?php
	}
}
	?>

	
	
    <input type="hidden" name="boxchecked" value="0">

    <input type="hidden" name="filter_order2" value="<?php echo $lists['order']; ?>" />

    <input type="hidden" name="filter_order_Dir2" value="<?php echo $lists['order_Dir']; ?>" />

</form>
<?php 
if(isset($labels))
{
?>
<div id="sbox-overlay" style="z-index: 65555; position: fixed; top: 0px; left: 0px; visibility: visible; zoom: 1; background-color:#000000; opacity: 0.7; filter: alpha(opacity=70); display:none;" onclick="toggleChBDiv(false)"></div>
<div style="background-color:#FFFFFF; width: 350px; height: 350px; overflow-y: scroll; padding: 20px; position: fixed; top: 100px;display:none; border:2px solid #AAAAAA;  z-index:65556" id="ChBDiv">

<form action="#">
    <p style="font-weight:bold; font-size:18px;margin-top: 0px;">
    Select Columns
    </p>

    <input type="checkbox" <?php if($lists['hide_label_list']==='') echo 'checked="checked"' ?> onclick="clickLabChBAll(this)" id="ChBAll" />All</br>

	<?php 
    
        foreach($templabels as $key => $curlabel)
        {
            if(strpos($lists['hide_label_list'],'@'.$curlabel.'@')===false)
            echo '<input type="checkbox" checked="checked" onclick="clickLabChB(\''.$curlabel.'\', this)" />'.$label_titles[$key].'<br />';
            else
            echo '<input type="checkbox" onclick="clickLabChB(\''.$curlabel.'\', this)" />'.$label_titles[$key].'<br />';
        }
    
 		
    if($ispaypal)
	{
        if(strpos($lists['hide_label_list'],'@payment_info@')===false)
			echo '<input type="checkbox" onclick="clickLabChB(\''.'payment_info'.'\', this)" checked="checked" />Payment Info<br />';
        else
			echo '<input type="checkbox" onclick="clickLabChB(\''.'payment_info'.'\', this)"  />Payment Info<br />';
	}
    
   
    ?>
    <br />
    <div style="text-align:center;">
        <input type="button" onclick="toggleChBDiv(false);" value="Done"  class="btn" /> 
    </div>
</form>
</div>

<?php } ?>


<script>
<?php if($ka_fielderov_search){?> 
document.getElementById('fields_filter').style.display='';
document.getElementById('filter_img').src='components/com_formmaker/images/filter_hide.png';
	<?php
 }?>
 
				Calendar.setup({
						inputField: "startdate",
						ifFormat: "%Y-%m-%d",
						button: "startdate_but",
						align: "Tl",
						singleClick: true,
						firstDay: 0
						});
						
				Calendar.setup({
						inputField: "enddate",
						ifFormat: "%Y-%m-%d",
						button: "enddate_but",
						align: "Tl",
						singleClick: true,
						firstDay: 0
						});


</script>

<?php


}

public static function show(&$rows, &$pageNav, &$lists){

JSubMenuHelper::addEntry(JText::_('Forms'), 'index.php?option=com_formmaker&amp;task=forms', true );
JSubMenuHelper::addEntry(JText::_('Submissions'), 'index.php?option=com_formmaker&amp;task=submits' );
JSubMenuHelper::addEntry(JText::_('Themes'), 'index.php?option=com_formmaker&amp;task=themes' );

JHtml::_('behavior.tooltip');
JHtml::_('behavior.formvalidation');
JHtml::_('formbehavior.chosen', 'select');


	?>
<script>

Joomla.tableOrdering= function ( order, dir, task )  {
    var form = document.adminForm;
    form.filter_order.value     = order;
    form.filter_order_Dir.value = dir;
    submitform( task );
}


 function SelectAll(obj) { obj.focus(); obj.select(); } 
 </script>
<form action="index.php?option=com_formmaker" method="post" name="adminForm" id="adminForm">

    <table width="100%">

        <tr>

            <td align="left" width="100%">
                <input type="text" name="search" id="search" value="<?php echo $lists['search'];?>" class="text_area" placeholder="Search title" style="margin:0px" />

				<button class="btn tip hasTooltip" type="submit" data-original-title="Search"><i class="icon-search"></i></button>
				<button class="btn tip hasTooltip" type="button" onclick="document.id('search').value='';this.form.submit();" data-original-title="Clear">
				<i class="icon-remove"></i></button>
				<div class="btn-group pull-right hidden-phone">
					<label for="limit" class="element-invisible"><?php echo JText::_('JFIELD_PLG_SEARCH_SEARCHLIMIT_DESC');?></label>
					<?php echo $pageNav->getLimitBox(); ?>
				</div>


            </td>

        </tr>

    </table>

    <table class="table table-striped" width="100%" >

        <thead>

            <tr>

                <th width="4%"><?php echo '#'; ?></th>
                <th width="6%"><?php echo  JHTML::_('grid.sort', 'Id', 'Id', @$lists['order_Dir'], @$lists['order'] ); ?></th>

				<th width="4%" class="hidden-phone">
						<input type="checkbox" name="checkall-toggle" value="" title="<?php echo JText::_('JGLOBAL_CHECK_ALL'); ?>" onclick="Joomla.checkAll(this)" />
				</th>

                <th width="34%"><?php echo JHTML::_('grid.sort', 'Title', 'title', @$lists['order_Dir'], @$lists['order'] ); ?></th>

                <th width="35%"><?php echo JHTML::_('grid.sort', 'Email to Send Submissions to', 'mail', @$lists['order_Dir'], @$lists['order'] ); ?></th>
				
                <th width="15%"><?php echo 'Plugin Code<br/>(Copy to article)'; ?></th>

            </tr>

        </thead>

        <tfoot>

            <tr>

                <td colspan="6"> <?php echo $pageNav->getListFooter(); ?> </td>

            </tr>

        </tfoot>

        <?php

	

    $k = 0;

	for($i=0, $n=count($rows); $i < $n ; $i++)

	{

		$row = &$rows[$i];

		$checked 	= JHTML::_('grid.id', $i, $row->id);

		$published 	= JHTML::_('grid.published', $row, $i); 


		// prepare link for id column

		$link 		= JRoute::_( 'index.php?option=com_formmaker&task=edit&cid[]='. $row->id );

		?>

        <tr class="<?php echo "row$k"; ?>">

                      <td align="center"><?php echo $i+1?></td>
                      <td align="center"><?php echo $row->id?></td>

          <td align="center"><?php echo $checked?></td>

            <td align="center"><a href="<?php echo $link; ?>"><?php echo $row->title?></a></td>

            <td align="center"><?php echo $row->mail?></td>
            <td align="center"><input type="text" readonly="readonly" value="{loadformmaker <?php echo $row->id?>}" onclick="SelectAll(this)" width="130"></td>

        </tr>

        <?php

		$k = 1 - $k;

	}

	?>

    </table>

    <input type="hidden" name="option" value="com_formmaker">
    <input type="hidden" name="task" value="forms">
    <input type="hidden" name="boxchecked" value="0"  >
    <input type="text" name="filter_order"  id="filter_order" value="<?php echo $lists['order']; ?>"  class="text_area" style="display:none"/>
    <input type="text" name="filter_order_Dir" id="filter_order_Dir" value="<?php echo $lists['order_Dir']; ?>" class="text_area" style="display:none" />


</form>

<?php
}

public static function edit(&$row, &$labels){

JRequest::setVar( 'hidemainmenu', 1 );
		
		$document = JFactory::getDocument();

		$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';
		
	
		$document->addScript($cmpnt_js_path.'/if_gmap.js');
		
		$document->addScript('http://maps.google.com/maps/api/js?sensor=false');
		$document->addStyleSheet(JURI::root(true).'/administrator/components/com_formmaker/css/style.css');
		$document->addStyleSheet(JURI::root(true).'/administrator/components/com_formmaker/js/jquery-ui-spinner.css');
		
		$is_editor=false;
		
		$plugin = JPluginHelper::getPlugin('editors', 'tinymce');
		if (isset($plugin->type))
		{ 
			$editor	= JFactory::getEditor('tinymce');
			$is_editor=true;
		}
		

		JHTML::_('behavior.tooltip');	
		JHTML::_('behavior.calendar');
		JHTML::_('behavior.modal');
	?>

<script type="text/javascript">
if($)
if(typeof $.noConflict === 'function'){
   $.noConflict();
}
var already_submitted=false;
Joomla.submitbutton= function (pressbutton) 
{

	if(!document.getElementById('araqel'))
	{
		alert('Please wait while page loading');
		return;
	}
	else
		if(document.getElementById('araqel').value=='0')
		{
			alert('Please wait while page loading');
			return;
		}
		
	var form = document.adminForm;

	if (already_submitted) 
	{
		submitform( pressbutton );
		return;
	}
	
	if (pressbutton == 'cancel') 

	{

		submitform( pressbutton );

		return;

	}
	
	if (form.title.value == "")

	{
				alert( "The form must have a title." );	
				return;
	}		

		
	document.getElementById('take').style.display="none";
	document.getElementById('page_bar').style.display="none";
	document.getElementById('saving').style.display="block";
	remove_whitespace(document.getElementById('take'));
	tox='';
	form_fields='';

	l_id_array=[<?php echo $labels['id']?>];
	l_label_array=[<?php echo $labels['label']?>];
	l_type_array=[<?php echo $labels['type']?>];
	l_id_removed=[];

	for(x=0; x< l_id_array.length; x++)
	{
		l_id_removed[l_id_array[x]]=true;
	}
		
	for(t=1;t<=form_view_max;t++)
	{
		if(document.getElementById('form_id_tempform_view'+t))
		{
			wdform_page=document.getElementById('form_id_tempform_view'+t);
			remove_whitespace(wdform_page);
			n=wdform_page.childNodes.length-2;	
			for(q=0;q<=n;q++)
			{
				if(!wdform_page.childNodes[q].getAttribute("wdid"))
				{
					wdform_section=wdform_page.childNodes[q];
					for (x=0; x < wdform_section.childNodes.length; x++)
					{
						wdform_column=wdform_section.childNodes[x];
						if(wdform_column.firstChild)
						for (y=0; y < wdform_column.childNodes.length; y++)
						{
							is_in_old=false;
							wdform_row=wdform_column.childNodes[y];
							if(wdform_row.nodeType==3)
								continue;
							wdid=wdform_row.getAttribute("wdid");
							if(!wdid)
								continue;
							l_id=wdid;
							l_label = document.getElementById( wdid+'_element_labelform_id_temp').innerHTML;
							l_label = l_label.replace(/(\r\n|\n|\r)/gm," ");
							wdtype=wdform_row.firstChild.getAttribute('type');

							for(z=0; z< l_id_array.length; z++)
							{
								if(l_id_array[z]==wdid)
								{
									if(l_type_array[z]=="type_address")
									{
										if(document.getElementById(l_id+"_mini_label_street1"))		
											l_id_removed[l_id_array[z]]=false;
							
											
										if(document.getElementById(l_id+"_mini_label_street2"))		
										l_id_removed[parseInt(l_id_array[z])+1]=false;	
									
										
										if(document.getElementById(l_id+"_mini_label_city"))
										l_id_removed[parseInt(l_id_array[z])+2]=false;	
																			
										if(document.getElementById(l_id+"_mini_label_state"))
										l_id_removed[parseInt(l_id_array[z])+3]=false;	
										
										
										if(document.getElementById(l_id+"_mini_label_postal"))
										l_id_removed[parseInt(l_id_array[z])+4]=false;	
										
										
										if(document.getElementById(l_id+"_mini_label_country"))
										l_id_removed[parseInt(l_id_array[z])+5]=false;	
										
										z=z+5;
									}
									else
										l_id_removed[l_id]=false;

								}
							}
							
							if(wdtype=="type_address")
							{
								addr_id=parseInt(wdid);
								id_for_country= addr_id;
								
								if(document.getElementById(id_for_country+"_mini_label_street1"))
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_street1").innerHTML+'#**label**#type_address#****#';addr_id++; 
								if(document.getElementById(id_for_country+"_mini_label_street2"))	
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_street2").innerHTML+'#**label**#type_address#****#';addr_id++; 	
								if(document.getElementById(id_for_country+"_mini_label_city"))	
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_city").innerHTML+'#**label**#type_address#****#';	addr_id++;
								if(document.getElementById(id_for_country+"_mini_label_state"))	
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_state").innerHTML+'#**label**#type_address#****#';	addr_id++;		
								if(document.getElementById(id_for_country+"_mini_label_postal"))
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_postal").innerHTML+'#**label**#type_address#****#';	addr_id++; 
								if(document.getElementById(id_for_country+"_mini_label_country"))
								tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_country").innerHTML+'#**label**#type_address#****#'; 
							}
							else
								tox=tox+wdid+'#**id**#'+l_label+'#**label**#'+wdtype+'#****#';
								
							
							id=wdid;
							form_fields+=wdid+"*:*id*:*";
							form_fields+=wdtype+"*:*type*:*";
							
							
							w_choices=new Array();	
							w_choices_checked=new Array();
							w_choices_disabled=new Array();
							w_allow_other_num=0;
							w_property=new Array();	
							w_property_type=new Array();	
							w_property_values=new Array();
							w_choices_price=new Array();
							
							if(document.getElementById(id+'_element_labelform_id_temp').innerHTML)
								w_field_label=document.getElementById(id+'_element_labelform_id_temp').innerHTML.replace(/(\r\n|\n|\r)/gm," ");
								
							if(document.getElementById(id+'_label_sectionform_id_temp'))
							if(document.getElementById(id+'_label_sectionform_id_temp').style.display=="block")
								w_field_label_pos="top";
							else
								w_field_label_pos="left";
														
							if(document.getElementById(id+"_elementform_id_temp"))
							{
								s=document.getElementById(id+"_elementform_id_temp").style.width;
								 w_size=s.substring(0,s.length-2);
							}
							
							if(document.getElementById(id+"_label_sectionform_id_temp"))
							{
								s=document.getElementById(id+"_label_sectionform_id_temp").style.width;
								w_field_label_size=s.substring(0,s.length-2);
							}
							
							if(document.getElementById(id+"_requiredform_id_temp"))
								w_required=document.getElementById(id+"_requiredform_id_temp").value;
								
							if(document.getElementById(id+"_uniqueform_id_temp"))
								w_unique=document.getElementById(id+"_uniqueform_id_temp").value;
								
							if(document.getElementById(id+'_label_sectionform_id_temp'))
							{
								w_class=document.getElementById(id+'_label_sectionform_id_temp').getAttribute("class");
								if(!w_class)
									w_class="";
							}
								
							gen_form_fields();
							wdform_row.innerHTML="%"+id+" - "+l_label+"%";
							
						}
					}
				}
			
				else
				{
						id=wdform_page.childNodes[q].getAttribute("wdid");
						w_editor=document.getElementById(id+"_element_sectionform_id_temp").innerHTML;
						
						form_fields+=id+"*:*id*:*";
						form_fields+="type_section_break"+"*:*type*:*";
												
						form_fields+="custom_"+id+"*:*w_field_label*:*";
						form_fields+=w_editor+"*:*w_editor*:*";
						form_fields+="*:*new_field*:*";
						wdform_page.childNodes[q].innerHTML="%"+id+" - "+"custom_"+id+"%";
						
					

				}

			}
		}	
	}

	document.getElementById('label_order_current').value=tox;
	
	for(x=0; x< l_id_array.length; x++)
	{
		if(l_id_removed[l_id_array[x]])
			tox=tox+l_id_array[x]+'#**id**#'+l_label_array[x]+'#**label**#'+l_type_array[x]+'#****#';
	}
	
	
	document.getElementById('label_order').value=tox;
	document.getElementById('form_fields').value=form_fields;
	
	
	refresh_()
	document.getElementById('pagination').value=document.getElementById('pages').getAttribute("type");
	document.getElementById('show_title').value=document.getElementById('pages').getAttribute("show_title");
	document.getElementById('show_numbers').value=document.getElementById('pages').getAttribute("show_numbers");
	
		already_submitted=true;
		submitform( pressbutton );
}

function remove_whitespace(node)
{
var ttt;
	for (ttt=0; ttt < node.childNodes.length; ttt++)
	{
        if( node.childNodes[ttt] && node.childNodes[ttt].nodeType == '3' && !/\S/.test(  node.childNodes[ttt].nodeValue ))
		{
			
			node.removeChild(node.childNodes[ttt]);
			 ttt--;
		}
		else
		{
			if(node.childNodes[ttt].childNodes.length)
				remove_whitespace(node.childNodes[ttt]);
		}
	}
	return
}

function refresh_()
{
				
	document.getElementById('counter').value=gen;
	
	for(i=1; i<=form_view_max; i++)
		if(document.getElementById('form_id_tempform_view'+i))
		{
			if(document.getElementById('page_next_'+i))
				document.getElementById('page_next_'+i).removeAttribute('src');
			if(document.getElementById('page_previous_'+i))
				document.getElementById('page_previous_'+i).removeAttribute('src');
			document.getElementById('form_id_tempform_view'+i).parentNode.removeChild(document.getElementById('form_id_tempform_view_img'+i));
			document.getElementById('form_id_tempform_view'+i).removeAttribute('style');
		}
		
	document.getElementById('form_front').value=document.getElementById('take').innerHTML;
}




	gen=<?php echo $row->counter; ?>;//add main form  id
    function enable()
	{
	alltypes=Array('customHTML','text','checkbox','radio','time_and_date','select','file_upload','captcha','map','button','page_break','section_break','paypal','survey');
	for(x=0; x<14;x++)
	{
		document.getElementById('img_'+alltypes[x]).src="components/com_formmaker/images/"+alltypes[x]+".png";
	}
	

		if(document.getElementById('formMakerDiv').style.display=='block'){jQuery('#formMakerDiv').slideToggle(200);}else{jQuery('#formMakerDiv').slideToggle(400);}
		
		if(document.getElementById('formMakerDiv').offsetWidth)
			document.getElementById('formMakerDiv1').style.width	=(document.getElementById('formMakerDiv').offsetWidth - 60)+'px';
		if(document.getElementById('formMakerDiv1').style.display=='block'){jQuery('#formMakerDiv1').slideToggle(200);}else{jQuery('#formMakerDiv1').slideToggle(400);}
		document.getElementById('when_edit').style.display		='none';
	}

    function enable2()
	{
	alltypes=Array('customHTML','text','checkbox','radio','time_and_date','select','file_upload','captcha','map','button','page_break','section_break','paypal','survey');
	for(x=0; x<14;x++)
	{
		document.getElementById('img_'+alltypes[x]).src="components/com_formmaker/images/"+alltypes[x]+".png";
	}
	

		if(document.getElementById('formMakerDiv').style.display=='block'){jQuery('#formMakerDiv').slideToggle(200);}else{jQuery('#formMakerDiv').slideToggle(400);}
		
		if(document.getElementById('formMakerDiv').offsetWidth)
			document.getElementById('formMakerDiv1').style.width	=(document.getElementById('formMakerDiv').offsetWidth - 60)+'px';
	
    if(document.getElementById('formMakerDiv1').style.display=='block'){jQuery('#formMakerDiv1').slideToggle(200);}else{jQuery('#formMakerDiv1').slideToggle(400);}
	document.getElementById('when_edit').style.display		='block';
		if(document.getElementById('field_types').offsetWidth)
			document.getElementById('when_edit').style.width	=document.getElementById('field_types').offsetWidth+'px';
		
		if(document.getElementById('field_types').offsetHeight)
			document.getElementById('when_edit').style.height	=document.getElementById('field_types').offsetHeight+'px';
		
		//document.getElementById('when_edit').style.position='none';
		
	}
	
    </script>

<style>
#take_temp .element_toolbar, #take_temp .page_toolbar, #take_temp .captcha_img, #take_temp .wdform_stars
{
display:none;
}

#when_edit
{
position:absolute;
background-color:#666;
z-index:101;
display:none;
width:100%;
height:100%;
opacity: 0.7;
filter: alpha(opacity = 70);
}

#formMakerDiv
{
position:fixed;
background-color:#666;
z-index:100;
display:none;
left:0;
top:0;
width:100%;
height:100%;
opacity: 0.7;
filter: alpha(opacity = 70);
}
#formMakerDiv1
{
position:fixed;
z-index:100;
background-color:transparent;
top:0;
left:0;
display:none;
margin-left:30px;
margin-top:35px;
}

input[type="radio"], input[type="checkbox"] {
margin: 0px 4px 5px 5px;
}

.pull-left
{
float:none !important;
}

.modal-body
{
max-height:100%;
}

img
{
max-width:none;
}
.wdform_date
{
margin:0px !important;
}

.formmaker_table input
{
border-radius: 3px;
padding: 2px;
}

.formmaker_table
{
border-radius:8px;
border:6px #00aeef solid;
background-color:#00aeef;
height:120px;
}

.formMakerDiv1_table
{
border:6px #00aeef solid;
background-color:#FFF;
border-radius:8px;
}

label
{
display:inline;
}
</style>

<form action="index.php" method="post" name="adminForm" id="adminForm" enctype="multipart/form-data">
<div  class="formmaker_table" width="100%" >
<div style="float:left; text-align:center">
 	</br>
   <img src="components/com_formmaker/images/FormMaker.png" />
	</br>
	</br>
	   <img src="components/com_formmaker/images/logo.png" />


</div>

<div style="float:right">

    <span style="font-size:16.76pt; font-family:tahoma; color:#FFFFFF; vertical-align:middle;">Form title:&nbsp;&nbsp;</span>

    <input id="title" name="title" style="width:151px; height:19px; border:none; font-size:11px; " value="<?php echo $row->title; ?>" />
 <br/>
	<a href="#" onclick="Joomla.submitbutton('form_options_temp')" style="cursor:pointer;margin:10px; float:right; color:#fff; font-size:20px"><img src="components/com_formmaker/images/formoptions.png" /></a>    
   <br/>
	<img src="components/com_formmaker/images/addanewfield.png" onclick="enable(); Enable()" style="cursor:pointer;margin:10px; float:right" />

</div>
	
  

</div>

<div id="formMakerDiv" onclick="close_window()"></div>  
<div id="formMakerDiv1"  align="center">
    
    
<table border="0" width="100%" cellpadding="0" cellspacing="0" height="100%" class="formMakerDiv1_table">
  <tr>
    <td style="padding:0px">
    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr valign="top">
         <td width="15%" height="100%" style="border-right:dotted black 1px;" id="field_types">
            <div id="when_edit" style="display:none"></div>
			<table border="0" cellpadding="0" cellspacing="3" width="100%" style="border-collapse: separate; border-spacing: 3px;">
				<tr>
					<td align="center" onClick="addRow('customHTML')" class="field_buttons" id="table_editor"><img src="components/com_formmaker/images/customHTML.png" style="margin:5px" id="img_customHTML"/></td>
					<td align="center" onClick="addRow('text')" class="field_buttons" id="table_text"><img src="components/com_formmaker/images/text.png" style="margin:5px" id="img_text"/></td>
				</tr>
				<tr>             
					<td align="center" onClick="addRow('checkbox')" class="field_buttons" id="table_checkbox"><img src="components/com_formmaker/images/checkbox.png" style="margin:5px" id="img_checkbox"/></td>
					<td align="center" onClick="addRow('radio')" class="field_buttons" id="table_radio"><img src="components/com_formmaker/images/radio.png" style="margin:5px" id="img_radio"/></td>
				</tr>
				<tr>
					<td align="center" onClick="addRow('survey')" class="field_buttons" id="table_survey"><img src="components/com_formmaker/images/survey.png" style="margin:5px" id="img_survey"/></td>           
					<td align="center" onClick="addRow('time_and_date')" class="field_buttons" id="table_time_and_date"><img src="components/com_formmaker/images/time_and_date.png" style="margin:5px" id="img_time_and_date"/></td>
			   </tr>
				<tr>
					<td align="center" onClick="addRow('select')" class="field_buttons" id="table_select"><img src="components/com_formmaker/images/select.png" style="margin:5px" id="img_select"/></td>
					<td align="center" onClick="alert('This field type is disabled in free version. If you need this functionality, you need to buy the commercial version.')" class="field_buttons" id="table_file_upload" style="background:#727171 !important;"><img src="components/com_formmaker/images/file_upload.png" style="margin:5px" id="img_file_upload"/></td>
				</tr>
				<tr>
					<td align="center" onClick="addRow('section_break')" class="field_buttons" id="table_section_break"><img src="components/com_formmaker/images/section_break.png" style="margin:5px" id="img_section_break"/></td>
					<td align="center" onClick="addRow('page_break')" class="field_buttons" id="table_page_break"><img src="components/com_formmaker/images/page_break.png" style="margin:5px" id="img_page_break"/></td>  
				</tr>
				<tr>
					<td align="center" onClick="alert('This field type is disabled in free version. If you need this functionality, you need to buy the commercial version.')" class="field_buttons" id="table_map" style="background:#727171 !important;"><img src="components/com_formmaker/images/map.png" style="margin:5px" id="img_map" /></td>  
					<td align="center" onClick="alert('This field type is disabled in free version. If you need this functionality, you need to buy the commercial version.')" id="table_paypal" class="field_buttons" style="background:#727171 !important;"><img src="components/com_formmaker/images/paypal.png" style="margin:5px" id="img_paypal" /></td>       
			   </tr>
				<tr>
					<td align="center" onClick="addRow('captcha')" class="field_buttons" id="table_captcha"><img src="components/com_formmaker/images/captcha.png" style="margin:5px" id="img_captcha"/></td>
					<td align="center" onClick="addRow('button')" id="table_button" class="field_buttons" ><img src="components/com_formmaker/images/button.png" style="margin:5px" id="img_button"/></td>			
				</tr>
            </table>
         </td>
         <td width="35%" height="100%" align="left"><div id="edit_table" style="padding:0px; overflow-y:scroll; height:535px" ></div></td>

		 <td align="center" valign="top" style="background:url(components/com_formmaker/images/border2.png) repeat-y;">&nbsp;</td>
         <td style="padding:15px">
         <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
         
            <tr>
                <td align="right"><input type="radio" value="end" name="el_pos" checked="checked" id="pos_end" onclick="Disable()"/>
                  At The End
                  <input type="radio" value="begin" name="el_pos" id="pos_begin" onclick="Disable()"/>
                  At The Beginning
                  <input type="radio" value="before" name="el_pos" id="pos_before" onclick="Enable()"/>
                  Before
                  <select style="width:100px; margin-left:5px" id="sel_el_pos" onclick="change_before()" disabled="disabled">
                  </select>
                  <img alt="ADD" title="add" style="cursor:pointer; vertical-align:middle; margin:5px" src="components/com_formmaker/images/save.png" onClick="add(0)"/>
                  <img alt="CANCEL" title="cancel"  style=" cursor:pointer; vertical-align:middle; margin:5px" src="components/com_formmaker/images/cancel_but.png" onClick="close_window()"/>
				
                	<hr style=" margin-bottom:10px" />
                  </td>
              </tr>
              
              <tr height="100%" valign="top">
                <td  id="show_table"></td>
              </tr>
              
            </table>
         </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<input type="hidden" id="old" />
<input type="hidden" id="old_selected" />
<input type="hidden" id="element_type" />
<input type="hidden" id="editing_id" />
<div id="main_editor" style="position:absolute; display:none; z-index:140;"><?php if($is_editor) echo $editor->display('editor','','440px','350px','40','6');
else
{
?>
<textarea name="editor" id="editor" cols="40" rows="6" style="width: 440px; height: 350px; " class="mce_editable" aria-hidden="true"></textarea>
<?php

}
 ?></div>
 </div>
 
 <?php if(!$is_editor)
?>
<iframe id="tinymce" style="display:none"></iframe>

<?php
?>


 
 
<br />
<br />

    <fieldset>

    <legend>

    <h2 style="color:#00aeef">Form</h2>

    </legend>

        <?php
		
    echo '<style>'.self::first_css.'</style>';

?>

<div id="saving" style="display:none">
	<div id="saving_text">Saving</div>
	<div id="fadingBarsG">
	<div id="fadingBarsG_1" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_2" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_3" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_4" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_5" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_6" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_7" class="fadingBarsG">
	</div>
	<div id="fadingBarsG_8" class="fadingBarsG">
	</div>
	</div>
</div>


<div style="margin:8px; display:table; width:100%" id="page_bar"><div id="page_navigation" style="display:table-row"><div align="center" id="pages" show_title="<?php echo $row->show_title; ?>" show_numbers="<?php echo $row->show_numbers; ?>" type="<?php echo $row->pagination; ?>" style="display:table-cell;  width:90%"></div><div align="left" id="edit_page_navigation" style="display:table-cell; vertical-align: middle;"></div></div></div>

    <div id="take"><?php
	
	    echo $row->form_front;
		
	 ?></div>

    </fieldset>

    <input type="hidden" name="form_front" id="form_front">
	<input type="hidden" name="form_fields" id="form_fields"/>
	  
    <input type="hidden" name="pagination" id="pagination" />
    <input type="hidden" name="show_title" id="show_title" />
    <input type="hidden" name="show_numbers" id="show_numbers" />
	
    <input type="hidden" name="public_key" id="public_key" />
    <input type="hidden" name="private_key" id="private_key" />
    <input type="hidden" name="recaptcha_theme" id="recaptcha_theme" />

	<input type="hidden" id="label_order_current" name="label_order_current" value="<?php echo $row->label_order_current;?>" />
    <input type="hidden" id="label_order" name="label_order" value="<?php echo $row->label_order;?>" />
    <input type="hidden" name="counter" id="counter" value="<?php echo $row->counter;?>">

<script type="text/javascript">

function formOnload()
{

//enable maps
for(t=0; t<<?php echo $row->counter;?>; t++)
	if(document.getElementById(t+"_typeform_id_temp"))
	{
		if(document.getElementById(t+"_typeform_id_temp").value=="type_map" || document.getElementById(t+"_typeform_id_temp").value=="type_mark_map")
		{
			if_gmap_init(t);
			for(q=0; q<20; q++)
				if(document.getElementById(t+"_elementform_id_temp").getAttribute("long"+q))
				{
				
					w_long=parseFloat(document.getElementById(t+"_elementform_id_temp").getAttribute("long"+q));
					w_lat=parseFloat(document.getElementById(t+"_elementform_id_temp").getAttribute("lat"+q));
					w_info=parseFloat(document.getElementById(t+"_elementform_id_temp").getAttribute("info"+q));
					add_marker_on_map(t,q, w_long, w_lat, w_info, false);
				}
		}
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_date")
				Calendar.setup({
						inputField: t+"_elementform_id_temp",
						ifFormat: document.getElementById(t+"_buttonform_id_temp").getAttribute('format'),
						button: t+"_buttonform_id_temp",
						align: "Tl",
						singleClick: true,
						firstDay: 0
						});
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_name")
		{
				var myu = t;
				jQuery(document).ready(function() {	

				jQuery("#"+myu+"_mini_label_first").click(function() {		
			
				if (jQuery(this).children('input').length == 0) {	

					var first = "<input type='text' id='first' class='first' style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
						jQuery(this).html(first);							
						jQuery("input.first").focus();			
						jQuery("input.first").blur(function() {	
					
					var id_for_blur = document.getElementById('first').parentNode.id.split('_');
					var value = jQuery(this).val();			
				jQuery("#"+id_for_blur[0]+"_mini_label_first").text(value);		
				});	
			}	
			});	    
				
				jQuery("label#"+myu+"_mini_label_last").click(function() {	
			if (jQuery(this).children('input').length == 0) {	
			
				var last = "<input type='text' id='last' class='last'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(last);			
					jQuery("input.last").focus();					
					jQuery("input.last").blur(function() {	
					var id_for_blur = document.getElementById('last').parentNode.id.split('_');			
					var value = jQuery(this).val();			
					
					jQuery("#"+id_for_blur[0]+"_mini_label_last").text(value);	
				});	
				 
			}	
			});
			
				jQuery("label#"+myu+"_mini_label_title").click(function() {	
			if (jQuery(this).children('input').length == 0) {		
				var title_ = "<input type='text' id='title_' class='title_'  style='outline:none; border:none; background:none; width:50px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(title_);			
					jQuery("input.title_").focus();					
					jQuery("input.title_").blur(function() {	
					var id_for_blur = document.getElementById('title_').parentNode.id.split('_');			
					var value = jQuery(this).val();			
					
					jQuery("#"+id_for_blur[0]+"_mini_label_title").text(value);	
				});	
			}	
			});


			jQuery("label#"+myu+"_mini_label_middle").click(function() {	
			if (jQuery(this).children('input').length == 0) {		
				var middle = "<input type='text' id='middle' class='middle'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(middle);			
					jQuery("input.middle").focus();					
					jQuery("input.middle").blur(function() {	
					var id_for_blur = document.getElementById('middle').parentNode.id.split('_');			
					var value = jQuery(this).val();			
					
					jQuery("#"+id_for_blur[0]+"_mini_label_middle").text(value);	
				});	
			}	
			});
			
			});		
		}						
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_phone")
		{
				var myu = t;
			  
			jQuery(document).ready(function() {	
			jQuery("label#"+myu+"_mini_label_area_code").click(function() {		
			if (jQuery(this).children('input').length == 0) {		

				var area_code = "<input type='text' id='area_code' class='area_code' style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";		

				jQuery(this).html(area_code);		
				jQuery("input.area_code").focus();		
				jQuery("input.area_code").blur(function() {	
				var id_for_blur = document.getElementById('area_code').parentNode.id.split('_');
				var value = jQuery(this).val();			
				jQuery("#"+id_for_blur[0]+"_mini_label_area_code").text(value);		
				});		
			}	
			});	

			
			jQuery("label#"+myu+"_mini_label_phone_number").click(function() {		

			if (jQuery(this).children('input').length == 0) {			
				var phone_number = "<input type='text' id='phone_number' class='phone_number'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";						

				jQuery(this).html(phone_number);					

				jQuery("input.phone_number").focus();			
				jQuery("input.phone_number").blur(function() {		
				var id_for_blur = document.getElementById('phone_number').parentNode.id.split('_');
				var value = jQuery(this).val();			
				jQuery("#"+id_for_blur[0]+"_mini_label_phone_number").text(value);		
				});	
			}	
			});
			
			});	
		}						
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_date_fields")
		{
				var myu = t;
			  
			jQuery(document).ready(function() {	
			jQuery("label#"+myu+"_day_label").click(function() {		
				if (jQuery(this).children('input').length == 0) {				
					var day = "<input type='text' id='day' class='day' style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
						jQuery(this).html(day);							
						jQuery("input.day").focus();			
						jQuery("input.day").blur(function() {	
					var id_for_blur = document.getElementById('day').parentNode.id.split('_');
					var value = jQuery(this).val();			

				jQuery("#"+id_for_blur[0]+"_day_label").text(value);		
				});	
			}	
			});		


			jQuery("label#"+myu+"_month_label").click(function() {	
			if (jQuery(this).children('input').length == 0) {		
				var month = "<input type='text' id='month' class='month' style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(month);			
					jQuery("input.month").focus();					
					jQuery("input.month").blur(function() {	
					var id_for_blur = document.getElementById('month').parentNode.id.split('_');			
					var value = jQuery(this).val();			
					
					jQuery("#"+id_for_blur[0]+"_month_label").text(value);	
				});	
			}	
			});
			
				jQuery("label#"+myu+"_year_label").click(function() {	
			if (jQuery(this).children('input').length == 0) {		
				var year = "<input type='text' id='year' class='year' style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(year);			
					jQuery("input.year").focus();					
					jQuery("input.year").blur(function() {	
				var id_for_blur = document.getElementById('year').parentNode.id.split('_');				
					var value = jQuery(this).val();			
					
					jQuery("#"+id_for_blur[0]+"_year_label").text(value);	
				});	
			}	
			});
			
			});	

	
		}						
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_time")
		{
			var myu = t;
      
			jQuery(document).ready(function() {	
				jQuery("label#"+myu+"_mini_label_hh").click(function() {		
					if (jQuery(this).children('input').length == 0) {				
						var hh = "<input type='text' id='hh' class='hh' style='outline:none; border:none; background:none; width:40px;' value=\""+jQuery(this).text()+"\">";	
							jQuery(this).html(hh);							
							jQuery("input.hh").focus();			
							jQuery("input.hh").blur(function() {	
							var id_for_blur = document.getElementById('hh').parentNode.id.split('_');	
						var value = jQuery(this).val();			


					jQuery("#"+id_for_blur[0]+"_mini_label_hh").text(value);		
					});	
				}	
				});		


				jQuery("label#"+myu+"_mini_label_mm").click(function() {	
				if (jQuery(this).children('input').length == 0) {		
					var mm = "<input type='text' id='mm' class='mm' style='outline:none; border:none; background:none; width:40px;' value=\""+jQuery(this).text()+"\">";	
						jQuery(this).html(mm);			
						jQuery("input.mm").focus();					
						jQuery("input.mm").blur(function() {
						var id_for_blur = document.getElementById('mm').parentNode.id.split('_');				
						var value = jQuery(this).val();			
						
						jQuery("#"+id_for_blur[0]+"_mini_label_mm").text(value);	
					});	
				}	
				});
				
					jQuery("label#"+myu+"_mini_label_ss").click(function() {	
				if (jQuery(this).children('input').length == 0) {		
					var ss = "<input type='text' id='ss' class='ss' style='outline:none; border:none; background:none; width:40px;' value=\""+jQuery(this).text()+"\">";	
						jQuery(this).html(ss);			
						jQuery("input.ss").focus();					
						jQuery("input.ss").blur(function() {
			   var id_for_blur = document.getElementById('ss').parentNode.id.split('_');				
						var value = jQuery(this).val();			
						
						jQuery("#"+id_for_blur[0]+"_mini_label_ss").text(value);	
					});	
				}	
				});
				
					jQuery("label#"+myu+"_mini_label_am_pm").click(function() {		
					if (jQuery(this).children('input').length == 0) {				
						var am_pm = "<input type='text' id='am_pm' class='am_pm' style='outline:none; border:none; background:none; width:40px;' value=\""+jQuery(this).text()+"\">";	
							jQuery(this).html(am_pm);							
							jQuery("input.am_pm").focus();			
							jQuery("input.am_pm").blur(function() {	
						var id_for_blur = document.getElementById('am_pm').parentNode.id.split('_');	
						var value = jQuery(this).val();			

					jQuery("#"+id_for_blur[0]+"_mini_label_am_pm").text(value);		
					});	
				}	
				});	
				});
		
	}	
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_paypal_price")
		{
				var myu = t;
				jQuery(document).ready(function() {	

				jQuery("#"+myu+"_mini_label_dollars").click(function() {		
			
				if (jQuery(this).children('input').length == 0) {	

					var dollars = "<input type='text' id='dollars' class='dollars' style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
						jQuery(this).html(dollars);							
						jQuery("input.dollars").focus();			
						jQuery("input.dollars").blur(function() {	
					
					var id_for_blur = document.getElementById('dollars').parentNode.id.split('_');
					var value = jQuery(this).val();			
				jQuery("#"+id_for_blur[0]+"_mini_label_dollars").text(value);		
				});	
			}	
			});	    
				
				jQuery("label#"+myu+"_mini_label_cents").click(function() {	
			if (jQuery(this).children('input').length == 0) {	
			
				var cents = "<input type='text' id='cents' class='cents'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(cents);			
					jQuery("input.cents").focus();					
					jQuery("input.cents").blur(function() {	
					var id_for_blur = document.getElementById('cents').parentNode.id.split('_');			
					var value = jQuery(this).val();			
					
					jQuery("#"+id_for_blur[0]+"_mini_label_cents").text(value);	
				});	
				 
			}	
			});
			
			
			
			});		
		}	
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_address")
		{
			var myu = t;
       
			jQuery(document).ready(function() {		
			jQuery("label#"+myu+"_mini_label_street1").click(function() {			

				if (jQuery(this).children('input').length == 0) {				
				var street1 = "<input type='text' id='street1' class='street1' style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";
				jQuery(this).html(street1);					
				jQuery("input.street1").focus();		
				jQuery("input.street1").blur(function() {	
				var id_for_blur = document.getElementById('street1').parentNode.id.split('_');
				var value = jQuery(this).val();			
				jQuery("#"+id_for_blur[0]+"_mini_label_street1").text(value);		
				});		
				}	
				});		
			
			jQuery("label#"+myu+"_mini_label_street2").click(function() {		
			if (jQuery(this).children('input').length == 0) {		
			var street2 = "<input type='text' id='street2' class='street2'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";
			jQuery(this).html(street2);					
			jQuery("input.street2").focus();		
			jQuery("input.street2").blur(function() {	
			var id_for_blur = document.getElementById('street2').parentNode.id.split('_');
			var value = jQuery(this).val();			
			jQuery("#"+id_for_blur[0]+"_mini_label_street2").text(value);		
			});		
			}	
			});	
			
			
			jQuery("label#"+myu+"_mini_label_city").click(function() {	
				if (jQuery(this).children('input').length == 0) {	
				var city = "<input type='text' id='city' class='city'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";
				jQuery(this).html(city);			
				jQuery("input.city").focus();				
				jQuery("input.city").blur(function() {	
				var id_for_blur = document.getElementById('city').parentNode.id.split('_');		
				var value = jQuery(this).val();		
				jQuery("#"+id_for_blur[0]+"_mini_label_city").text(value);		
			});		
			}	
			});	
			
			jQuery("label#"+myu+"_mini_label_state").click(function() {		
				if (jQuery(this).children('input').length == 0) {	
				var state = "<input type='text' id='state' class='state'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(state);		
					jQuery("input.state").focus();		
					jQuery("input.state").blur(function() {	
				var id_for_blur = document.getElementById('state').parentNode.id.split('_');					
				var value = jQuery(this).val();			
			jQuery("#"+id_for_blur[0]+"_mini_label_state").text(value);	
			});	
			}
			});		

			jQuery("label#"+myu+"_mini_label_postal").click(function() {		
			if (jQuery(this).children('input').length == 0) {			
			var postal = "<input type='text' id='postal' class='postal'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";
			jQuery(this).html(postal);			
			jQuery("input.postal").focus();			
			jQuery("input.postal").blur(function() {
			var id_for_blur = document.getElementById('postal').parentNode.id.split('_');	
			var value = jQuery(this).val();		
			jQuery("#"+id_for_blur[0]+"_mini_label_postal").text(value);		
			});	
			}
			});	
			
			
			jQuery("label#"+myu+"_mini_label_country").click(function() {		
				if (jQuery(this).children('input').length == 0) {		
					var country = "<input type='country' id='country' class='country'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";
					jQuery(this).html(country);		
					jQuery("input.country").focus();	
					jQuery("input.country").blur(function() {	
					var id_for_blur = document.getElementById('country').parentNode.id.split('_');				
					var value = jQuery(this).val();			
					jQuery("#"+id_for_blur[0]+"_mini_label_country").text(value);			
					});	
				}	
			});
			});	

		}						
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_scale_rating")
		{
				var myu = t;
				jQuery(document).ready(function() {	

				jQuery("#"+myu+"_mini_label_worst").click(function() {		
			
				if (jQuery(this).children('input').length == 0) {	

					var worst = "<input type='text' id='worst' class='worst' size='6' style='outline:none; border:none; background:none; font-size:11px; width:100px;' value=\""+jQuery(this).text()+"\">";	
						jQuery(this).html(worst);							
						jQuery("input.worst").focus();			
						jQuery("input.worst").blur(function() {	
					
					var id_for_blur = document.getElementById('worst').parentNode.id.split('_');
					var value = jQuery(this).val();			
				jQuery("#"+id_for_blur[0]+"_mini_label_worst").text(value);		
				});	
			}	
			});	    
				
				jQuery("label#"+myu+"_mini_label_best").click(function() {	
			if (jQuery(this).children('input').length == 0) {	
			
				var best = "<input type='text' id='best' class='best' style='outline:none; border:none; background:none; font-size:11px; width:100px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(best);			
					jQuery("input.best").focus();					
					jQuery("input.best").blur(function() {	
					var id_for_blur = document.getElementById('best').parentNode.id.split('_');			
					var value = jQuery(this).val();			
					
					jQuery("#"+id_for_blur[0]+"_mini_label_best").text(value);	
				});	
				 
			}	
			});
			
			
			
			});		
		}			 
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_spinner")
		{
				var spinner_value = document.getElementById(t+"_elementform_id_temp").value;
				var spinner_min_value = document.getElementById(t+"_min_valueform_id_temp").value;
				var spinner_max_value = document.getElementById(t+"_max_valueform_id_temp").value;
				var spinner_step = document.getElementById(t+"_stepform_id_temp").value;
								  
				
								jQuery("#"+t+"_elementform_id_temp")[0].spin = null;
								
								spinner = jQuery( "#"+t+"_elementform_id_temp" ).spinner();
								spinner.spinner( "value", spinner_value );
								jQuery( "#"+t+"_elementform_id_temp" ).spinner({ min: spinner_min_value});    
								jQuery( "#"+t+"_elementform_id_temp" ).spinner({ max: spinner_max_value});
								jQuery( "#"+t+"_elementform_id_temp" ).spinner({ step: spinner_step});
								
	
		}
			else
			if(document.getElementById(t+"_typeform_id_temp").value=="type_slider")	
			{
 
				var slider_value = document.getElementById(t+"_slider_valueform_id_temp").value;
				var slider_min_value = document.getElementById(t+"_slider_min_valueform_id_temp").value;
				var slider_max_value = document.getElementById(t+"_slider_max_valueform_id_temp").value;
				
				var slider_element_value = document.getElementById( t+"_element_valueform_id_temp" );
				var slider_value_save = document.getElementById( t+"_slider_valueform_id_temp" );
				
				 jQuery("#"+t+"_elementform_id_temp")[0].slide = null;	
			
					jQuery(function() {
				jQuery( "#"+t+"_elementform_id_temp").slider({
				range: "min",
				value: eval(slider_value),
				min: eval(slider_min_value),
				max: eval(slider_max_value),
				slide: function( event, ui ) {	
					slider_element_value.innerHTML = "" + ui.value ;
					slider_value_save.value = "" + ui.value; 

				}
				});
	
	
			});	
		
				
		}
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_range")
		{
				var spinner_value0 = document.getElementById(t+"_elementform_id_temp0").value;
				var spinner_step = document.getElementById(t+"_range_stepform_id_temp").value;
								  
						
					jQuery("#"+t+"_elementform_id_temp0")[0].spin = null;
					jQuery("#"+t+"_elementform_id_temp1")[0].spin = null;
								
							
					spinner0 = jQuery( "#"+t+"_elementform_id_temp0" ).spinner();
					spinner0.spinner( "value", spinner_value0 );
					jQuery( "#"+t+"_elementform_id_temp0" ).spinner({ step: spinner_step});
							
							
							
				var spinner_value1 = document.getElementById(t+"_elementform_id_temp1").value;
								
							
							spinner1 = jQuery( "#"+t+"_elementform_id_temp1" ).spinner();
							spinner1.spinner( "value", spinner_value1 );
							jQuery( "#"+t+"_elementform_id_temp1" ).spinner({ step: spinner_step});
							
							
						var myu = t;
			jQuery(document).ready(function() {	

			jQuery("#"+myu+"_mini_label_from").click(function() {		
		
			if (jQuery(this).children('input').length == 0) {	

				var from = "<input type='text' id='from' class='from' style='outline:none; border:none; background:none; font-size:11px; width:100px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(from);							
					jQuery("input.from").focus();			
					jQuery("input.from").blur(function() {	
				
				var id_for_blur = document.getElementById('from').parentNode.id.split('_');
				var value = jQuery(this).val();			
			jQuery("#"+id_for_blur[0]+"_mini_label_from").text(value);		
			});	
		}	
		});	    
			
			jQuery("label#"+myu+"_mini_label_to").click(function() {	
		if (jQuery(this).children('input').length == 0) {	
		
			var to = "<input type='text' id='to' class='to' size='6' style='outline:none; border:none; background:none; font-size:11px; width:100px;' value=\""+jQuery(this).text()+"\">";	
				jQuery(this).html(to);			
				jQuery("input.to").focus();					
				jQuery("input.to").blur(function() {	
				var id_for_blur = document.getElementById('to').parentNode.id.split('_');			
				var value = jQuery(this).val();			
				
				jQuery("#"+id_for_blur[0]+"_mini_label_to").text(value);	
			});	
			 
		}	
		});
		
		
	
	});						
	
		}			 
	

	}
	
remove_whitespace(document.getElementById('take'));
	
	form_view=1;
	form_view_count=0;
	for(i=1; i<=30; i++)
	{
		if(document.getElementById('form_id_tempform_view'+i))
		{
			form_view_count++;
			form_view_max=i;
			

		tbody_img=document.createElement('div');
			tbody_img.setAttribute('id','form_id_tempform_view_img'+i);
			tbody_img.style.cssText = "float:right";
			
		tr_img=document.createElement('div');
			
		var	img=document.createElement('img');
			img.setAttribute('src','components/com_formmaker/images/minus.png');
			img.setAttribute('title','Show or hide the page');
			img.setAttribute("class", "page_toolbar");
			img.setAttribute('id','show_page_img_'+i);
			img.setAttribute('onClick','show_or_hide("'+i+'")');
			img.setAttribute("onmouseover", 'chnage_icons_src(this,"minus")');
			img.setAttribute("onmouseout", 'chnage_icons_src(this,"minus")');
			img.style.cssText = "margin:5px 5px 5px 0;";

			
		var img_X = document.createElement("img");
			img_X.setAttribute("src", "components/com_formmaker/images/page_delete.png");
			img_X.setAttribute('title','Delete the page');
			img_X.setAttribute("class", "page_toolbar");
			img_X.setAttribute("onclick", 'remove_page("'+i+'")');
			img_X.setAttribute("onmouseover", 'chnage_icons_src(this,"page_delete")');
			img_X.setAttribute("onmouseout", 'chnage_icons_src(this,"page_delete")');
			img_X.style.cssText = "margin:5px 5px 5px 0;";
			
		var img_X_all = document.createElement("img");
			img_X_all.setAttribute("src", "components/com_formmaker/images/page_delete_all.png");
			img_X_all.setAttribute('title','Delete the page with fields');
			img_X_all.setAttribute("class", "page_toolbar");
			img_X_all.setAttribute("onclick", 'remove_page_all("'+i+'")');
			img_X_all.setAttribute("onmouseover", 'chnage_icons_src(this,"page_delete_all")');
			img_X_all.setAttribute("onmouseout", 'chnage_icons_src(this,"page_delete_all")');
			img_X_all.style.cssText = "margin:5px 5px 5px 0;";
			
		var img_EDIT = document.createElement("img");
			img_EDIT.setAttribute("src", "components/com_formmaker/images/page_edit.png");
			img_EDIT.setAttribute('title','Edit the page');
			img_EDIT.setAttribute("class", "page_toolbar");
			img_EDIT.setAttribute("onclick", 'edit_page_break("'+i+'")');
			img_EDIT.setAttribute("onmouseover", 'chnage_icons_src(this,"page_edit")');
			img_EDIT.setAttribute("onmouseout", 'chnage_icons_src(this,"page_edit")');
			img_EDIT.style.cssText = "margin:5px 5px 5px 0;";
					
		tr_img.appendChild(img);
		tr_img.appendChild(img_X);
		tr_img.appendChild(img_X_all);
		tr_img.appendChild(img_EDIT);
		tbody_img.appendChild(tr_img);
			
		document.getElementById('form_id_tempform_view'+i).parentNode.appendChild(tbody_img);
		}
	}
	
	if(form_view_count>1)
	{
		for(i=1; i<=form_view_max; i++)
		{
			if(document.getElementById('form_id_tempform_view'+i))
			{
				first_form_view=i;
				break;
			}
		}
		form_view=form_view_max;
		need_enable=false;
		generate_page_nav(first_form_view);
		
	var img_EDIT = document.createElement("img");
			img_EDIT.setAttribute("src", "components/com_formmaker/images/edit.png");
			img_EDIT.style.cssText = "margin-left:40px; cursor:pointer";
			img_EDIT.setAttribute("onclick", 'el_page_navigation()');
			
	var td_EDIT = document.getElementById("edit_page_navigation");
			td_EDIT.appendChild(img_EDIT);
	
	document.getElementById('page_navigation').appendChild(td_EDIT);

			
	}



	document.getElementById('araqel').value=1;

}

function formAddToOnload()
{ 
	if(formOldFunctionOnLoad){ formOldFunctionOnLoad(); }
	formOnload();
}

function formLoadBody()
{
	formOldFunctionOnLoad = window.onload;
	window.onload = formAddToOnload;
}

var formOldFunctionOnLoad = null;

formLoadBody();


</script>


	<script src="<?php echo  $cmpnt_js_path ?>/formmaker_div1.js" type="text/javascript" style=""></script>
	<script src="<?php echo  JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/wdformjquery.js'; ?>" type="text/javascript"></script>
	<script src="<?php echo  JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/jquery-ui.js'; ?>" type="text/javascript"></script>

    <input type="hidden" name="option" value="com_formmaker" />

    <input type="hidden" name="id" value="<?php echo $row->id?>" />

    <input type="hidden" name="cid[]" value="<?php echo $row->id; ?>" />

    <input type="hidden" name="task" value="" />
    <input type="hidden" id="araqel" value="0" />

</form>

<?php		

       }	


public static function edit_old(&$row, &$labels){

JRequest::setVar( 'hidemainmenu', 1 );
		
		$document = JFactory::getDocument();

		$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';
		
		
		$document->addScript($cmpnt_js_path.'/if_gmap.js');
		$document->addScript('http://maps.google.com/maps/api/js?sensor=false');
		$document->addStyleSheet(JURI::root(true).'/administrator/components/com_formmaker/css/style.css');
		$document->addStyleSheet(JURI::root(true).'/administrator/components/com_formmaker/js/jquery-ui-spinner.css');
	
		
		$is_editor=false;
		
		$plugin = JPluginHelper::getPlugin('editors', 'tinymce');
		if (isset($plugin->type))
		{ 
			$editor	= JFactory::getEditor('tinymce');
			$is_editor=true;
		}
		

		JHTML::_('behavior.tooltip');	
		JHTML::_('behavior.calendar');
		JHTML::_('behavior.modal');
	?>
    
<script type="text/javascript">
if($)
if(typeof $.noConflict === 'function'){
   $.noConflict();
}
var already_submitted=false;

Joomla.submitbutton= function (pressbutton) 

{
	if (already_submitted) 
	{
		submitform( pressbutton );
		return;
	}

	var form = document.adminForm;

	if (pressbutton == 'cancel') 

	{

		submitform( pressbutton );

		return;

	}

	if(!document.getElementById('araqel'))
	{
		alert('Please wait while page loading');
		return;
	}
	else
		if(document.getElementById('araqel').value=='0')
		{
			alert('Please wait while page loading');
			return;
		}
		

	if (form.title.value == "")

	{

				alert( "The form must have a title." );	
				return;

	}		

	
		tox='';
		l_id_array=[<?php echo $labels['id']?>];
		l_label_array=[<?php echo $labels['label']?>];
		l_type_array=[<?php echo $labels['type']?>];
		l_id_removed=[];
		
		for(x=0; x< l_id_array.length; x++)
			{
				l_id_removed[l_id_array[x]]=true;
			}

for(t=1;t<=form_view_max;t++)
{
	if(document.getElementById('form_id_tempform_view'+t))
	{
		form_view_element=document.getElementById('form_id_tempform_view'+t);
		n=form_view_element.childNodes.length-2;	
		
		for(q=0;q<=n;q++)
		{
				if(form_view_element.childNodes[q].nodeType!=3)
				if(!form_view_element.childNodes[q].id)
				{
					GLOBAL_tr=form_view_element.childNodes[q];
		
					for (x=0; x < GLOBAL_tr.firstChild.childNodes.length; x++)
					{
			
						table=GLOBAL_tr.firstChild.childNodes[x];
						tbody=table.firstChild;
						for (y=0; y < tbody.childNodes.length; y++)
						{
							is_in_old=false;
							tr=tbody.childNodes[y];
							l_id=tr.id;
				
							l_label=document.getElementById( tr.id+'_element_labelform_id_temp').innerHTML;
							l_label = l_label.replace(/(\r\n|\n|\r)/gm," ");
							l_type=tr.getAttribute('type');
							for(z=0; z< l_id_array.length; z++)
							{
								if(l_id_array[z]==l_id)
								{
									if(l_type_array[z]=="type_address")
									{
										if(document.getElementById(l_id+"_mini_label_street1"))		
											l_id_removed[l_id_array[z]]=false;
							
											
																		
										if(document.getElementById(l_id+"_mini_label_street2"))		
										l_id_removed[parseInt(l_id_array[z])+1]=false;	
									
										
										if(document.getElementById(l_id+"_mini_label_city"))
										l_id_removed[parseInt(l_id_array[z])+2]=false;	
																			
										if(document.getElementById(l_id+"_mini_label_state"))
										l_id_removed[parseInt(l_id_array[z])+3]=false;	
										
										
										if(document.getElementById(l_id+"_mini_label_postal"))
										l_id_removed[parseInt(l_id_array[z])+4]=false;	
										
										
										if(document.getElementById(l_id+"_mini_label_country"))
										l_id_removed[parseInt(l_id_array[z])+5]=false;	
										
										
										z=z+5;
									}
									else
										l_id_removed[l_id]=false;

								}
							}
							
								if(tr.getAttribute('type')=="type_address")
								{
									addr_id=parseInt(tr.id);
									id_for_country= addr_id;
								
									if(document.getElementById(id_for_country+"_mini_label_street1"))
										tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_street1").innerHTML+'#**label**#'+tr.getAttribute('type')+'#****#';addr_id++; 
									if(document.getElementById(id_for_country+"_mini_label_street2"))
										tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_street2").innerHTML+'#**label**#'+tr.getAttribute('type')+'#****#';addr_id++; 
									if(document.getElementById(id_for_country+"_mini_label_city"))
										tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_city").innerHTML+'#**label**#'+tr.getAttribute('type')+'#****#';	addr_id++; 
									if(document.getElementById(id_for_country+"_mini_label_state"))
										tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_state").innerHTML+'#**label**#'+tr.getAttribute('type')+'#****#';	addr_id++;
									if(document.getElementById(id_for_country+"_mini_label_postal"))									
										tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_postal").innerHTML+'#**label**#'+tr.getAttribute('type')+'#****#';	addr_id++; 
									if(document.getElementById(id_for_country+"_mini_label_country"))									
										tox=tox+addr_id+'#**id**#'+document.getElementById(id_for_country+"_mini_label_country").innerHTML+'#**label**#'+tr.getAttribute('type')+'#****#'; 
									}
								else
									tox=tox+l_id+'#**id**#'+l_label+'#**label**#'+l_type+'#****#';

							
							
						}
					}
				}
		}
	}	
}

	document.getElementById('label_order_current').value=tox;

	for(x=0; x< l_id_array.length; x++)
	{
		
		if(l_id_removed[l_id_array[x]])	{	
		
	
				
	
			tox=tox+l_id_array[x]+'#**id**#'+l_label_array[x]+'#**label**#'+l_type_array[x]+'#****#';
		}
	}
	
	
	document.getElementById('label_order').value=tox;
	
	
	refresh_()
	document.getElementById('pagination').value=document.getElementById('pages').getAttribute("type");
	document.getElementById('show_title').value=document.getElementById('pages').getAttribute("show_title");
	document.getElementById('show_numbers').value=document.getElementById('pages').getAttribute("show_numbers");
	
	already_submitted=true;
	
		submitform( pressbutton );
}

function remove_whitespace(node)
{
    var ttt;
	for (ttt=0; ttt < node.childNodes.length; ttt++)
	{
        if( node.childNodes[ttt] && node.childNodes[ttt].nodeType == '3' && !/\S/.test(  node.childNodes[ttt].nodeValue ) )
		{
			
			node.removeChild(node.childNodes[ttt]);
            ttt--;
		}
		else
		{
			if(node.childNodes[ttt].childNodes.length)
				remove_whitespace(node.childNodes[ttt]);
		}
	}
	return
}

function refresh_()
{
			
	document.getElementById('form').value=document.getElementById('take').innerHTML;
	document.getElementById('counter').value=gen;
	n=gen;
	for(i=0; i<n; i++)
	{
		if(document.getElementById(i))
		{	
			for(z=0; z<document.getElementById(i).childNodes.length; z++)
				if(document.getElementById(i).childNodes[z].nodeType==3)
					document.getElementById(i).removeChild(document.getElementById(i).childNodes[z]);

			if(document.getElementById(i).getAttribute('type')=="type_captcha" || document.getElementById(i).getAttribute('type')=="type_recaptcha")
			{
				if(document.getElementById(i).childNodes[10])
				{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				}
				else
				{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				}
				continue;
			}
						
			if(document.getElementById(i).getAttribute('type')=="type_section_break")
			{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				continue;
			}
						


			if(document.getElementById(i).childNodes[10])
			{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[2]);
			}
			else
			{
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
				document.getElementById(i).removeChild(document.getElementById(i).childNodes[1]);
			}
		}
	}
	
	for(i=0; i<=n; i++)
	{	
		if(document.getElementById(i))
		{
			type=document.getElementById(i).getAttribute("type");
				switch(type)
				{
					case "type_text":
					case "type_number":
					case "type_password":
					case "type_submitter_mail":
					case "type_own_select":
					case "type_country":
					case "type_hidden":
					case "type_map":
					case "type_mark_map":
					case "type_paypal_select":
					{
						remove_add_(i+"_elementform_id_temp");
						break;
					}
					
					case "type_submit_reset":
					{
						remove_add_(i+"_element_submitform_id_temp");
						if(document.getElementById(i+"_element_resetform_id_temp"))
							remove_add_(i+"_element_resetform_id_temp");
						break;
					}
					
					case "type_captcha":
					{
						remove_add_("_wd_captchaform_id_temp");
						remove_add_("_element_refreshform_id_temp");
						remove_add_("_wd_captcha_inputform_id_temp");
						break;
					}
					
					case "type_recaptcha":
					{
						document.getElementById("public_key").value = document.getElementById("wd_recaptchaform_id_temp").getAttribute("public_key");
						document.getElementById("private_key").value= document.getElementById("wd_recaptchaform_id_temp").getAttribute("private_key");
						document.getElementById("recaptcha_theme").value= document.getElementById("wd_recaptchaform_id_temp").getAttribute("theme");
						document.getElementById('wd_recaptchaform_id_temp').innerHTML='';
						remove_add_("wd_recaptchaform_id_temp");
						break;
					}
						
					case "type_file_upload":
						{
							remove_add_(i+"_elementform_id_temp");
							
								break;
						}
						
					case "type_textarea":
						{
						remove_add_(i+"_elementform_id_temp");

								break;
						}
						
					case "type_name":
						{
						
							if(document.getElementById(i+"_element_titleform_id_temp"))
							{
								remove_add_(i+"_element_titleform_id_temp");
								remove_add_(i+"_element_firstform_id_temp");
								remove_add_(i+"_element_lastform_id_temp");
								remove_add_(i+"_element_middleform_id_temp");
							}
							else
							{
								remove_add_(i+"_element_firstform_id_temp");
								remove_add_(i+"_element_lastform_id_temp");
							}
								break;

						}
						
					case "type_phone":
						{
						
							remove_add_(i+"_element_firstform_id_temp");
							remove_add_(i+"_element_lastform_id_temp");
							break;

						}
					case "type_paypal_price":
						{
						
							remove_add_(i+"_element_dollarsform_id_temp");
							remove_add_(i+"_element_centsform_id_temp");
							break;

						}
						case "type_address":
							{	
							if(document.getElementById(id_for_country+"_disable_fieldsform_id_temp").getAttribute('street1')=='no')
								remove_add_(i+"_street1form_id_temp");
							if(document.getElementById(id_for_country+"_disable_fieldsform_id_temp").getAttribute('street2')=='no')	
								remove_add_(i+"_street2form_id_temp");
							if(document.getElementById(id_for_country+"_disable_fieldsform_id_temp").getAttribute('city')=='no')
								remove_add_(i+"_cityform_id_temp");
							if(document.getElementById(id_for_country+"_disable_fieldsform_id_temp").getAttribute('state')=='no')
								remove_add_(i+"_stateform_id_temp");
							if(document.getElementById(id_for_country+"_disable_fieldsform_id_temp").getAttribute('postal')=='no')
								remove_add_(i+"_postalform_id_temp");
							if(document.getElementById(id_for_country+"_disable_fieldsform_id_temp").getAttribute('country')=='no')
								remove_add_(i+"_countryform_id_temp");
							
							
								break;
	
							}
							
						
					case "type_checkbox":
					case "type_radio":
					case "type_paypal_checkbox":
					case "type_paypal_radio":
					case "type_paypal_shipping":
						{
							is=true;
							for(j=0; j<100; j++)
								if(document.getElementById(i+"_elementform_id_temp"+j))
								{
									remove_add_(i+"_elementform_id_temp"+j);
								}

							/*if(document.getElementById(i+"_randomize").value=="yes")
								choises_randomize(i);*/
							
							break;
						}
					
					case "type_star_rating":
						{	
							remove_add_(i+"_elementform_id_temp");
						
								break;
						}	
						
					case "type_scale_rating":
						{	
						remove_add_(i+"_elementform_id_temp");
						
								break;
						}
					case "type_spinner":
						{	
						remove_add_(i+"_elementform_id_temp");
						
								break;
						}
					case "type_slider":
						{	
						remove_add_(i+"_elementform_id_temp");
										
								break;
						}
					case "type_range":
						{	
						remove_add_(i+"_elementform_id_temp0");
						remove_add_(i+"_elementform_id_temp1");
							
								break;
						}
					case "type_grading":
						{
							
							for(k=0; k<100; k++)
								if(document.getElementById(i+"_elementform_id_temp"+k))
								{
									remove_add_(i+"_elementform_id_temp"+k);
								}
						
							
							break;
						}
					case "type_matrix":
						{	
						remove_add_(i+"_elementform_id_temp");
						
								break;
						}
					
					case "type_button":
						{
							for(j=0; j<100; j++)
								if(document.getElementById(i+"_elementform_id_temp"+j))
								{
									remove_add_(i+"_elementform_id_temp"+j);
								}
							break;
						}
						
					case "type_time":
						{	
						if(document.getElementById(i+"_ssform_id_temp"))
							{
							remove_add_(i+"_ssform_id_temp");
							remove_add_(i+"_mmform_id_temp");
							remove_add_(i+"_hhform_id_temp");
							}
							else
							{
							remove_add_(i+"_mmform_id_temp");
							remove_add_(i+"_hhform_id_temp");

							}
							break;

						}
						
					case "type_date":
						{	
						remove_add_(i+"_elementform_id_temp");
						remove_add_(i+"_buttonform_id_temp");
						
							break;
						}
					case "type_date_fields":
						{	
						remove_add_(i+"_dayform_id_temp");
						remove_add_(i+"_monthform_id_temp");
						remove_add_(i+"_yearform_id_temp");
								break;
						}
						
						
				}	
		}
	}
	
	for(i=1; i<=form_view_max; i++)
	{
		if(document.getElementById('form_id_tempform_view'+i))
		{
			if(document.getElementById('page_next_'+i))
				document.getElementById('page_next_'+i).removeAttribute('src');
			if(document.getElementById('page_previous_'+i))
				document.getElementById('page_previous_'+i).removeAttribute('src');
			document.getElementById('form_id_tempform_view'+i).parentNode.removeChild(document.getElementById('form_id_tempform_view_img'+i));
			document.getElementById('form_id_tempform_view'+i).removeAttribute('style');
		}
	}
	
for(t=1;t<=form_view_max;t++)
{
	if(document.getElementById('form_id_tempform_view'+t))
	{
		form_view_element=document.getElementById('form_id_tempform_view'+t);		
		remove_whitespace(form_view_element);
		n=form_view_element.childNodes.length-2;
		
		for(q=0;q<=n;q++)
		{
				if(form_view_element.childNodes[q])
				if(form_view_element.childNodes[q].nodeType!=3)
				if(!form_view_element.childNodes[q].id)
				{
					del=true;
					GLOBAL_tr=form_view_element.childNodes[q];
					
					for (x=0; x < GLOBAL_tr.firstChild.childNodes.length; x++)
					{
			
						table=GLOBAL_tr.firstChild.childNodes[x];
						tbody=table.firstChild;
						
						if(tbody.childNodes.length)
							del=false;
					}
				
					if(del)
					{
						form_view_element.removeChild(form_view_element.childNodes[q]);
					}
				
				}
		}
	}	
}
	

	document.getElementById('form_front').value=document.getElementById('take').innerHTML;

}





	gen=<?php echo $row->counter; ?>;//add main form  id
    function enable()
	{
		alltypes=Array('customHTML','text','checkbox','radio','time_and_date','select','file_upload','captcha','map','button','page_break','section_break','paypal','survey');
		for(x=0; x<14;x++)
	
		{
			document.getElementById('img_'+alltypes[x]).src="components/com_formmaker/images/"+alltypes[x]+".png";
		}
	
		if(document.getElementById('formMakerDiv').style.display=='block'){jQuery('#formMakerDiv').slideToggle(200);}else{jQuery('#formMakerDiv').slideToggle(400);}
		
		if(document.getElementById('formMakerDiv').offsetWidth)
			document.getElementById('formMakerDiv1').style.width	=(document.getElementById('formMakerDiv').offsetWidth - 60)+'px';
		
		if(document.getElementById('formMakerDiv1').style.display=='block'){jQuery('#formMakerDiv1').slideToggle(200);}else{jQuery('#formMakerDiv1').slideToggle(400);}
		document.getElementById('when_edit').style.display		='none';
	}

    function enable2()
	{
		alltypes=Array('customHTML','text','checkbox','radio','time_and_date','select','file_upload','captcha','map','button','page_break','section_break','paypal','survey');
		for(x=0; x<14;x++)
		{
			document.getElementById('img_'+alltypes[x]).src="components/com_formmaker/images/"+alltypes[x]+".png";
		}
	
		if(document.getElementById('formMakerDiv').style.display=='block'){jQuery('#formMakerDiv').slideToggle(200);}else{jQuery('#formMakerDiv').slideToggle(400);}
		
		if(document.getElementById('formMakerDiv').offsetWidth)
			document.getElementById('formMakerDiv1').style.width	=(document.getElementById('formMakerDiv').offsetWidth - 60)+'px';
		
		if(document.getElementById('formMakerDiv1').style.display=='block'){jQuery('#formMakerDiv1').slideToggle(200);}else{jQuery('#formMakerDiv1').slideToggle(400);}
		document.getElementById('when_edit').style.display		='block';
		
		if(document.getElementById('field_types').offsetWidth)
			document.getElementById('when_edit').style.width	=document.getElementById('field_types').offsetWidth+'px';
		
		if(document.getElementById('field_types').offsetHeight)
			document.getElementById('when_edit').style.height	=document.getElementById('field_types').offsetHeight+'px';
		
	}
    </script>
<style>
#when_edit
{
position:absolute;
background-color:#666;
z-index:101;
display:none;
width:100%;
height:100%;
opacity: 0.7;
filter: alpha(opacity = 70);
}

#formMakerDiv
{
position:fixed;
background-color:#666;
z-index:100;
display:none;
left:0;
top:0;
width:100%;
height:100%;
opacity: 0.7;
filter: alpha(opacity = 70);
}
#formMakerDiv1
{
position:fixed;
z-index:100;
background-color:transparent;
top:0;
left:0;
display:none;
margin-left:30px;
margin-top:35px;
}

input[type="radio"], input[type="checkbox"] {
margin: 0px 4px 5px 5px;
}

.pull-left
{
float:none !important;
}

.modal-body
{
max-height:100%;
}

img
{
max-width:none;
}
.wdform_date
{
margin:0px !important;
}

.formmaker_table input
{
border-radius: 3px;
padding: 2px;
}

.formmaker_table
{
border-radius:8px;
border:6px #00aeef solid;
background-color:#00aeef;
height:120px;
}

.formMakerDiv1_table
{
border:6px #00aeef solid;
background-color:#FFF;
border-radius:8px;
}

label
{
display:inline;
}
</style>

<form action="index.php" method="post" name="adminForm" id="adminForm" enctype="multipart/form-data">
<div  class="formmaker_table" width="100%" >
<div style="float:left; text-align:center">
 	</br>
   <img src="components/com_formmaker/images/FormMaker.png" />
	</br>
	</br>
   <img src="components/com_formmaker/images/logo.png" />

</div>

<div style="float:right">

    <span style="font-size:16.76pt; font-family:tahoma; color:#FFFFFF; vertical-align:middle;">Form title:&nbsp;&nbsp;</span>

    <input id="title" name="title" style="width:151px; height:19px; border:none; font-size:11px; " value="<?php echo $row->title; ?>"/>
   <br/>
	<a href="#" onclick="Joomla.submitbutton('form_options_temp')" style="cursor:pointer;margin:10px; float:right; color:#fff; font-size:20px"><img src="components/com_formmaker/images/formoptions.png" /></a>    
   <br/>
	<img src="components/com_formmaker/images/addanewfield.png" onclick="enable(); Enable()" style="cursor:pointer;margin:10px; float:right" />

</div>
	
  

</div>
  
<div id="formMakerDiv" onclick="close_window()"></div>  
<div id="formMakerDiv1"  align="center">
    
    
<table border="0" width="100%" cellpadding="0" cellspacing="0" height="100%" class="formMakerDiv1_table">
  <tr>
    <td style="padding:0px">
    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr valign="top">
         <td width="15%" height="100%" style="border-right:dotted black 1px;" id="field_types">
            <div id="when_edit" style="display:none"></div>
			<table border="0" cellpadding="0" cellspacing="3" width="100%" style="border-collapse: separate; border-spacing: 3px;">
			<tr>
            <td align="center" onClick="addRow('customHTML')" style="cursor:pointer" id="table_editor"  class="field_buttons"><img src="components/com_formmaker/images/customHTML.png" style="margin:5px" id="img_customHTML"/></td>
            
            <td align="center" onClick="addRow('text')" style="cursor:pointer" id="table_text" class="field_buttons"><img src="components/com_formmaker/images/text.png" style="margin:5px" id="img_text"/></td>
            </tr>
            <tr>
            <td align="center" onClick="addRow('time_and_date')" style="cursor:pointer" id="table_time_and_date" class="field_buttons"><img src="components/com_formmaker/images/time_and_date.png" style="margin:5px" id="img_time_and_date"/></td>
            
            <td align="center" onClick="addRow('select')" style="cursor:pointer" id="table_select" class="field_buttons"><img src="components/com_formmaker/images/select.png" style="margin:5px" id="img_select"/></td>
            </tr>
            <tr>             
            <td align="center" onClick="addRow('checkbox')" style="cursor:pointer" id="table_checkbox" class="field_buttons"><img src="components/com_formmaker/images/checkbox.png" style="margin:5px" id="img_checkbox"/></td>
            
            <td align="center" onClick="addRow('radio')" style="cursor:pointer" id="table_radio" class="field_buttons"><img src="components/com_formmaker/images/radio.png" style="margin:5px" id="img_radio"/></td>
            </tr>
            <tr>
            <td align="center" onClick="addRow('file_upload')" style="cursor:pointer" id="table_file_upload" class="field_buttons"><img src="components/com_formmaker/images/file_upload.png" style="margin:5px" id="img_file_upload"/></td>
            
            <td align="center" onClick="addRow('captcha')" style="cursor:pointer" id="table_captcha" class="field_buttons"><img src="components/com_formmaker/images/captcha.png" style="margin:5px" id="img_captcha"/></td>
            </tr>
            <tr>
            <td align="center" onClick="addRow('page_break')" style="cursor:pointer" id="table_page_break" class="field_buttons"><img src="components/com_formmaker/images/page_break.png" style="margin:5px" id="img_page_break"/></td>  
            
            <td align="center" onClick="addRow('section_break')" style="cursor:pointer" id="table_section_break" class="field_buttons"><img src="components/com_formmaker/images/section_break.png" style="margin:5px" id="img_section_break"/></td>
            </tr>
            <tr>
            <td align="center" onClick="addRow('map')" id="table_map" class="field_buttons"><img src="components/com_formmaker/images/map.png" style="margin:5px" id="img_map"/></td>  
  			<td align="center" onClick="addRow('paypal')" id="table_paypal" class="field_buttons"><img src="components/com_formmaker/images/paypal.png" style="margin:5px" id="img_paypal" /></td>       
            </tr>		
			<tr>
            <td align="center" onClick="addRow('survey')" class="field_buttons" id="table_survey"><img src="components/com_formmaker/images/survey.png" style="margin:5px" id="img_survey"/></td>           
            <td align="center" onClick="addRow('button')" id="table_button" class="field_buttons"><img src="components/com_formmaker/images/button.png" style="margin:5px" id="img_button"/></td>
			  
            </tr>
            </table>

         </td>
         <td width="35%" height="100%" align="left"><div id="edit_table" style="padding:0px; overflow-y:scroll; height:535px" ></div></td>

		 <td align="center" valign="top" style="background:url(components/com_formmaker/images/border2.png) repeat-y;">&nbsp;</td>
         <td style="padding:15px">
         <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
         
            <tr>
                <td align="right"><input type="radio" value="end" name="el_pos" checked="checked" id="pos_end" onclick="Disable()"/>
                  At The End
                  <input type="radio" value="begin" name="el_pos" id="pos_begin" onclick="Disable()"/>
                  At The Beginning
                  <input type="radio" value="before" name="el_pos" id="pos_before" onclick="Enable()"/>
                  Before
                  <select style="width:100px; margin-left:5px" id="sel_el_pos" disabled="disabled">
                  </select>
                  <img alt="ADD" title="add" style="cursor:pointer; vertical-align:middle; margin:5px" src="components/com_formmaker/images/save.png" onClick="add(0)"/>
                  <img alt="CANCEL" title="cancel"  style=" cursor:pointer; vertical-align:middle; margin:5px" src="components/com_formmaker/images/cancel_but.png" onClick="close_window()"/>
				
                	<hr style=" margin-bottom:10px" />
                  </td>
              </tr>
              
              <tr height="100%" valign="top">
                <td  id="show_table"></td>
              </tr>
              
            </table>
         </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<input type="hidden" id="old" />
<input type="hidden" id="old_selected" />
<input type="hidden" id="element_type" />
<input type="hidden" id="editing_id" />
<div id="main_editor" style="position:absolute; display:none; z-index:140;"><?php if($is_editor) echo $editor->display('editor','','440','350','40','6');
else
{
?>
<textarea name="editor" id="editor" cols="40" rows="6" style="width: 440px; height: 350px; " class="mce_editable" aria-hidden="true"></textarea>
<?php

}
 ?></div>
 </div>
 
 <?php if(!$is_editor)
?>
<iframe id="editor_ifr" style="display:none"></iframe>

<?php
?>


 
 
<br />
<br />

    <fieldset>

    <legend>

    <h2 style="color:#00aeef">Form</h2>

    </legend>

        <?php
		
    echo '<style>'.self::first_css.'</style>';

?>
<table width="100%" style="margin:8px"><tr id="page_navigation"><td align="center" width="90%" id="pages" show_title="<?php echo $row->show_title; ?>" show_numbers="<?php echo $row->show_numbers; ?>" type="<?php echo $row->pagination; ?>"></td><td align="left" id="edit_page_navigation"></td></tr></table>

    <div id="take">
    <?php
	
	    echo $row->form;
		
	 ?> </div>

    </fieldset>

    <input type="hidden" name="form" id="form">
    <input type="hidden" name="form_front" id="form_front">
    <input type="hidden" name="theme" id="theme" value="<?php echo $row->theme; ?>">
   
    <input type="hidden" name="pagination" id="pagination" />
    <input type="hidden" name="show_title" id="show_title" />
    <input type="hidden" name="show_numbers" id="show_numbers" />
	
    <input type="hidden" name="public_key" id="public_key" />
    <input type="hidden" name="private_key" id="private_key" />
    <input type="hidden" name="recaptcha_theme" id="recaptcha_theme" />

	<input type="hidden" id="label_order_current" name="label_order_current" value="<?php echo $row->label_order_current;?>" />
    <input type="hidden" id="label_order" name="label_order" value="<?php echo $row->label_order;?>" />
    <input type="hidden" name="counter" id="counter" value="<?php echo $row->counter;?>">

<script type="text/javascript">

function formOnload()
{
//enable maps
for(t=0; t<<?php echo $row->counter;?>; t++)
	if(document.getElementById(t+"_typeform_id_temp"))
	{
		if(document.getElementById(t+"_typeform_id_temp").value=="type_map" || document.getElementById(t+"_typeform_id_temp").value=="type_mark_map")
		{
			if_gmap_init(t);
			for(q=0; q<20; q++)
				if(document.getElementById(t+"_elementform_id_temp").getAttribute("long"+q))
				{
				
					w_long=parseFloat(document.getElementById(t+"_elementform_id_temp").getAttribute("long"+q));
					w_lat=parseFloat(document.getElementById(t+"_elementform_id_temp").getAttribute("lat"+q));
					w_info=parseFloat(document.getElementById(t+"_elementform_id_temp").getAttribute("info"+q));
					add_marker_on_map(t,q, w_long, w_lat, w_info, false);
				}
		}
		else
		if(document.getElementById(t+"_typeform_id_temp").value=="type_date")
				Calendar.setup({
						inputField: t+"_elementform_id_temp",
						ifFormat: document.getElementById(t+"_buttonform_id_temp").getAttribute('format'),
						button: t+"_buttonform_id_temp",
						align: "Tl",
						singleClick: true,
						firstDay: 0
						});
						
		else				
		if(document.getElementById(t+"_typeform_id_temp").value=="type_spinner")	{

				var spinner_value = document.getElementById(t+"_elementform_id_temp").get( "aria-valuenow" );
				var spinner_min_value = document.getElementById(t+"_min_valueform_id_temp").value;
				var spinner_max_value = document.getElementById(t+"_max_valueform_id_temp").value;
			    var spinner_step = document.getElementById(t+"_stepform_id_temp").value;
					  
					 jQuery( "#"+t+"_elementform_id_temp" ).removeClass( "ui-spinner-input" )
			.prop( "disabled", false )
			.removeAttr( "autocomplete" )
			.removeAttr( "role" )
			.removeAttr( "aria-valuemin" )
			.removeAttr( "aria-valuemax" )
			.removeAttr( "aria-valuenow" );
			
			span_ui= document.getElementById(t+"_elementform_id_temp").parentNode;
				span_ui.parentNode.appendChild(document.getElementById(t+"_elementform_id_temp"));
				span_ui.parentNode.removeChild(span_ui);
				
				jQuery("#"+t+"_elementform_id_temp")[0].spin = null;
				
				spinner = jQuery( "#"+t+"_elementform_id_temp" ).spinner();
				spinner.spinner( "value", spinner_value );
				jQuery( "#"+t+"_elementform_id_temp" ).spinner({ min: spinner_min_value});    
                jQuery( "#"+t+"_elementform_id_temp" ).spinner({ max: spinner_max_value});
                jQuery( "#"+t+"_elementform_id_temp" ).spinner({ step: spinner_step});
				
		}
				else
			if(document.getElementById(t+"_typeform_id_temp").value=="type_slider")	{
 
				var slider_value = document.getElementById(t+"_slider_valueform_id_temp").value;
				var slider_min_value = document.getElementById(t+"_slider_min_valueform_id_temp").value;
				var slider_max_value = document.getElementById(t+"_slider_max_valueform_id_temp").value;
				
				var slider_element_value = document.getElementById( t+"_element_valueform_id_temp" );
				var slider_value_save = document.getElementById( t+"_slider_valueform_id_temp" );
				
				document.getElementById(t+"_elementform_id_temp").innerHTML = "";
				document.getElementById(t+"_elementform_id_temp").removeAttribute( "class" );
				document.getElementById(t+"_elementform_id_temp").removeAttribute( "aria-disabled" );

				 jQuery("#"+t+"_elementform_id_temp")[0].slide = null;	
			
					jQuery(function() {
				jQuery( "#"+t+"_elementform_id_temp").slider({
				range: "min",
				value: eval(slider_value),
				min: eval(slider_min_value),
				max: eval(slider_max_value),
				slide: function( event, ui ) {	
					slider_element_value.innerHTML = "" + ui.value ;
					slider_value_save.value = "" + ui.value; 

		}
	});
	
	
});	
		
				
		}
		else
       if(document.getElementById(t+"_typeform_id_temp").value=="type_range"){
                var spinner_value0 = document.getElementById(t+"_elementform_id_temp0").get( "aria-valuenow" );
			    var spinner_step = document.getElementById(t+"_range_stepform_id_temp").value;
					  
					 jQuery( "#"+t+"_elementform_id_temp0" ).removeClass( "ui-spinner-input" )
			.prop( "disabled", false )
			.removeAttr( "autocomplete" )
			.removeAttr( "role" )
			.removeAttr( "aria-valuenow" );
			
			span_ui= document.getElementById(t+"_elementform_id_temp0").parentNode;
				span_ui.parentNode.appendChild(document.getElementById(t+"_elementform_id_temp0"));
				span_ui.parentNode.removeChild(span_ui);
				
				
				jQuery("#"+t+"_elementform_id_temp0")[0].spin = null;
				jQuery("#"+t+"_elementform_id_temp1")[0].spin = null;
				
				spinner0 = jQuery( "#"+t+"_elementform_id_temp0" ).spinner();
				spinner0.spinner( "value", spinner_value0 );
                jQuery( "#"+t+"_elementform_id_temp0" ).spinner({ step: spinner_step});
				
				
				
				var spinner_value1 = document.getElementById(t+"_elementform_id_temp1").get( "aria-valuenow" );
			   					  
					 jQuery( "#"+t+"_elementform_id_temp1" ).removeClass( "ui-spinner-input" )
			.prop( "disabled", false )
			.removeAttr( "autocomplete" )
			.removeAttr( "role" )
			.removeAttr( "aria-valuenow" );
			
			span_ui1= document.getElementById(t+"_elementform_id_temp1").parentNode;
				span_ui1.parentNode.appendChild(document.getElementById(t+"_elementform_id_temp1"));
				span_ui1.parentNode.removeChild(span_ui1);
					
				spinner1 = jQuery( "#"+t+"_elementform_id_temp1" ).spinner();
				spinner1.spinner( "value", spinner_value1 );
                jQuery( "#"+t+"_elementform_id_temp1" ).spinner({ step: spinner_step});
				
					var myu = t;
        jQuery(document).ready(function() {	

		jQuery("#"+myu+"_mini_label_from").click(function() {		
	
		if (jQuery(this).children('input').length == 0) {	

			var from = "<input type='text' id='from' class='from'  style='outline:none; border:none; background:none; font-size:11px; width:100px;' value=\""+jQuery(this).text()+"\">";	
				jQuery(this).html(from);							
				jQuery("input.from").focus();			
				jQuery("input.from").blur(function() {	
			
			var id_for_blur = document.getElementById('from').parentNode.id.split('_');
			var value = jQuery(this).val();			
		jQuery("#"+id_for_blur[0]+"_mini_label_from").text(value);		
		});	
	}	
	});	    
     	
		jQuery("label#"+myu+"_mini_label_to").click(function() {	
	if (jQuery(this).children('input').length == 0) {	
	
		var to = "<input type='text' id='to' class='to' style='outline:none; border:none; background:none; font-size:11px; width:100px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(to);			
			jQuery("input.to").focus();					
			jQuery("input.to").blur(function() {	
			var id_for_blur = document.getElementById('to').parentNode.id.split('_');			
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_mini_label_to").text(value);	
		});	
		 
	}	
	});
	
	
	
	});	
     }	

		else
       if(document.getElementById(t+"_typeform_id_temp").value=="type_name"){
		var myu = t;
        jQuery(document).ready(function() {	

		jQuery("#"+myu+"_mini_label_first").click(function() {		
	
		if (jQuery(this).children('input').length == 0) {	

			var first = "<input type='text' id='first' class='first' style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
				jQuery(this).html(first);							
				jQuery("input.first").focus();			
				jQuery("input.first").blur(function() {	
			
			var id_for_blur = document.getElementById('first').parentNode.id.split('_');
			var value = jQuery(this).val();			
		jQuery("#"+id_for_blur[0]+"_mini_label_first").text(value);		
		});	
	}	
	});	    
     	
		jQuery("label#"+myu+"_mini_label_last").click(function() {	
	if (jQuery(this).children('input').length == 0) {	
	
		var last = "<input type='text' id='last' class='last'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(last);			
			jQuery("input.last").focus();					
			jQuery("input.last").blur(function() {	
			var id_for_blur = document.getElementById('last').parentNode.id.split('_');			
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_mini_label_last").text(value);	
		});	
		 
	}	
	});
	
		jQuery("label#"+myu+"_mini_label_title").click(function() {	
			if (jQuery(this).children('input').length == 0) {		
				var title_ = "<input type='text' id='title_' class='title_'  style='outline:none; border:none; background:none; width:50px;' value=\""+jQuery(this).text()+"\">";	
					jQuery(this).html(title_);			
					jQuery("input.title_").focus();					
					jQuery("input.title_").blur(function() {	
					var id_for_blur = document.getElementById('title_').parentNode.id.split('_');			
					var value = jQuery(this).val();			
					
					jQuery("#"+id_for_blur[0]+"_mini_label_title").text(value);	
				});	
			}	
			});


	jQuery("label#"+myu+"_mini_label_middle").click(function() {	
	if (jQuery(this).children('input').length == 0) {		
		var middle = "<input type='text' id='middle' class='middle'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(middle);			
			jQuery("input.middle").focus();					
			jQuery("input.middle").blur(function() {	
            var id_for_blur = document.getElementById('middle').parentNode.id.split('_');			
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_mini_label_middle").text(value);	
		});	
	}	
	});
	
	});		
		 }						
		else
       if(document.getElementById(t+"_typeform_id_temp").value=="type_address"){
		var myu = t;
       
	jQuery(document).ready(function() {		
	jQuery("label#"+myu+"_mini_label_street1").click(function() {			

		if (jQuery(this).children('input').length == 0) {				
		var street1 = "<input type='text' id='street1' class='street1' style='outline:none; border:none; background:none; width:150px;' value=\""+jQuery(this).text()+"\">";
		jQuery(this).html(street1);					
		jQuery("input.street1").focus();		
		jQuery("input.street1").blur(function() {	
		var id_for_blur = document.getElementById('street1').parentNode.id.split('_');
		var value = jQuery(this).val();			
		jQuery("#"+id_for_blur[0]+"_mini_label_street1").text(value);		
		});		
		}	
		});		
	
	jQuery("label#"+myu+"_mini_label_street2").click(function() {		
	if (jQuery(this).children('input').length == 0) {		
	var street2 = "<input type='text' id='street2' class='street2'  style='outline:none; border:none; background:none; width:150px;' value=\""+jQuery(this).text()+"\">";
	jQuery(this).html(street2);					
	jQuery("input.street2").focus();		
	jQuery("input.street2").blur(function() {	
	var id_for_blur = document.getElementById('street2').parentNode.id.split('_');
	var value = jQuery(this).val();			
	jQuery("#"+id_for_blur[0]+"_mini_label_street2").text(value);		
	});		
	}	
	});	
	
	
	jQuery("label#"+myu+"_mini_label_city").click(function() {	
		if (jQuery(this).children('input').length == 0) {	
		var city = "<input type='text' id='city' class='city'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";
		jQuery(this).html(city);			
		jQuery("input.city").focus();				
		jQuery("input.city").blur(function() {	
		var id_for_blur = document.getElementById('city').parentNode.id.split('_');		
		var value = jQuery(this).val();		
		jQuery("#"+id_for_blur[0]+"_mini_label_city").text(value);		
	});		
	}	
	});	
	
	jQuery("label#"+myu+"_mini_label_state").click(function() {		
		if (jQuery(this).children('input').length == 0) {	
		var state = "<input type='text' id='state' class='state'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(state);		
			jQuery("input.state").focus();		
			jQuery("input.state").blur(function() {	
		var id_for_blur = document.getElementById('state').parentNode.id.split('_');					
		var value = jQuery(this).val();			
	jQuery("#"+id_for_blur[0]+"_mini_label_state").text(value);	
	});	
	}
	});		

	jQuery("label#"+myu+"_mini_label_postal").click(function() {		
	if (jQuery(this).children('input').length == 0) {			
	var postal = "<input type='text' id='postal' class='postal'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";
	jQuery(this).html(postal);			
	jQuery("input.postal").focus();			
	jQuery("input.postal").blur(function() {
    var id_for_blur = document.getElementById('postal').parentNode.id.split('_');	
	var value = jQuery(this).val();		
	jQuery("#"+id_for_blur[0]+"_mini_label_postal").text(value);		
	});	
	}
	});	
	
	
	jQuery("label#"+myu+"_mini_label_country").click(function() {		
		if (jQuery(this).children('input').length == 0) {		
			var country = "<input type='text' id='country' class='country'  style='outline:none; border:none; background:none; width:130px;' value=\""+jQuery(this).text()+"\">";
			jQuery(this).html(country);		
			jQuery("input.country").focus();	
			jQuery("input.country").blur(function() {	
			var id_for_blur = document.getElementById('country').parentNode.id.split('_');				
			var value = jQuery(this).val();			
			jQuery("#"+id_for_blur[0]+"_mini_label_country").text(value);			
			});	
		}	
	});
	});	

	   }						
		else
       if(document.getElementById(t+"_typeform_id_temp").value=="type_phone"){
		var myu = t;
      
	jQuery(document).ready(function() {	
	jQuery("label#"+myu+"_mini_label_area_code").click(function() {		
	if (jQuery(this).children('input').length == 0) {		

		var area_code = "<input type='text' id='area_code' class='area_code' style='outline:none; border:none; background:none; width:100px;' value=\""+jQuery(this).text()+"\">";		

		jQuery(this).html(area_code);		
		jQuery("input.area_code").focus();		
		jQuery("input.area_code").blur(function() {	
		var id_for_blur = document.getElementById('area_code').parentNode.id.split('_');
		var value = jQuery(this).val();			
		jQuery("#"+id_for_blur[0]+"_mini_label_area_code").text(value);		
		});		
	}	
	});	

	
	jQuery("label#"+myu+"_mini_label_phone_number").click(function() {		

	if (jQuery(this).children('input').length == 0) {			
		var phone_number = "<input type='text' id='phone_number' class='phone_number'  style='outline:none; border:none; background:none; width:100px;' value=\""+jQuery(this).text()+"\">";						

		jQuery(this).html(phone_number);					

		jQuery("input.phone_number").focus();			
		jQuery("input.phone_number").blur(function() {		
		var id_for_blur = document.getElementById('phone_number').parentNode.id.split('_');
		var value = jQuery(this).val();			
		jQuery("#"+id_for_blur[0]+"_mini_label_phone_number").text(value);		
		});	
	}	
	});
	
	});	
		 }						
		else
       if(document.getElementById(t+"_typeform_id_temp").value=="type_date_fields"){
		var myu = t;
      
	jQuery(document).ready(function() {	
	jQuery("label#"+myu+"_day_label").click(function() {		
		if (jQuery(this).children('input').length == 0) {				
			var day = "<input type='text' id='day' class='day' style='outline:none; border:none; background:none; width:80px;' value=\""+jQuery(this).text()+"\">";	
				jQuery(this).html(day);							
				jQuery("input.day").focus();			
				jQuery("input.day").blur(function() {	
			var id_for_blur = document.getElementById('day').parentNode.id.split('_');
			var value = jQuery(this).val();			

		jQuery("#"+id_for_blur[0]+"_day_label").text(value);		
		});	
	}	
	});		


	jQuery("label#"+myu+"_month_label").click(function() {	
	if (jQuery(this).children('input').length == 0) {		
		var month = "<input type='text' id='month' class='month' style='outline:none; border:none; background:none; width:80px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(month);			
			jQuery("input.month").focus();					
			jQuery("input.month").blur(function() {	
			var id_for_blur = document.getElementById('month').parentNode.id.split('_');			
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_month_label").text(value);	
		});	
	}	
	});
	
		jQuery("label#"+myu+"_year_label").click(function() {	
	if (jQuery(this).children('input').length == 0) {		
		var year = "<input type='text' id='year' class='year' style='outline:none; border:none; background:none; width:80px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(year);			
			jQuery("input.year").focus();					
			jQuery("input.year").blur(function() {	
		var id_for_blur = document.getElementById('year').parentNode.id.split('_');				
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_year_label").text(value);	
		});	
	}	
	});
	
	});	

	
		 }						
			else
       if(document.getElementById(t+"_typeform_id_temp").value=="type_time"){
		var myu = t;
      
jQuery(document).ready(function() {	
	jQuery("label#"+myu+"_mini_label_hh").click(function() {		
		if (jQuery(this).children('input').length == 0) {				
			var hh = "<input type='text' id='hh' class='hh' style='outline:none; border:none; background:none; width:50px;' value=\""+jQuery(this).text()+"\">";	
				jQuery(this).html(hh);							
				jQuery("input.hh").focus();			
				jQuery("input.hh").blur(function() {	
				var id_for_blur = document.getElementById('hh').parentNode.id.split('_');	
			var value = jQuery(this).val();			


		jQuery("#"+id_for_blur[0]+"_mini_label_hh").text(value);		
		});	
	}	
	});		


	jQuery("label#"+myu+"_mini_label_mm").click(function() {	
	if (jQuery(this).children('input').length == 0) {		
		var mm = "<input type='text' id='mm' class='mm' style='outline:none; border:none; background:none; width:50px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(mm);			
			jQuery("input.mm").focus();					
			jQuery("input.mm").blur(function() {
            var id_for_blur = document.getElementById('mm').parentNode.id.split('_');				
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_mini_label_mm").text(value);	
		});	
	}	
	});
	
		jQuery("label#"+myu+"_mini_label_ss").click(function() {	
	if (jQuery(this).children('input').length == 0) {		
		var ss = "<input type='text' id='ss' class='ss' style='outline:none; border:none; background:none; width:50px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(ss);			
			jQuery("input.ss").focus();					
			jQuery("input.ss").blur(function() {
   var id_for_blur = document.getElementById('ss').parentNode.id.split('_');				
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_mini_label_ss").text(value);	
		});	
	}	
	});
	
		jQuery("label#"+myu+"_mini_label_am_pm").click(function() {		
		if (jQuery(this).children('input').length == 0) {				
			var am_pm = "<input type='text' id='am_pm' class='am_pm' size='4' style='outline:none; border:none; background:none; width:50px;' value=\""+jQuery(this).text()+"\">";	
				jQuery(this).html(am_pm);							
				jQuery("input.am_pm").focus();			
				jQuery("input.am_pm").blur(function() {	
		    var id_for_blur = document.getElementById('am_pm').parentNode.id.split('_');	
			var value = jQuery(this).val();			

		jQuery("#"+id_for_blur[0]+"_mini_label_am_pm").text(value);		
		});	
	}	
	});	
	});
		
		 }	

		else
       if(document.getElementById(t+"_typeform_id_temp").value=="type_paypal_price"){
		var myu = t;
        jQuery(document).ready(function() {	

		jQuery("#"+myu+"_mini_label_dollars").click(function() {		
	
		if (jQuery(this).children('input').length == 0) {	

			var dollars = "<input type='text' id='dollars' class='dollars' style='outline:none; border:none; background:none; width:100px;' value=\""+jQuery(this).text()+"\">";	
				jQuery(this).html(dollars);							
				jQuery("input.dollars").focus();			
				jQuery("input.dollars").blur(function() {	
			
			var id_for_blur = document.getElementById('dollars').parentNode.id.split('_');
			var value = jQuery(this).val();			
		jQuery("#"+id_for_blur[0]+"_mini_label_dollars").text(value);		
		});	
	}	
	});	    
     	
		jQuery("label#"+myu+"_mini_label_cents").click(function() {	
	if (jQuery(this).children('input').length == 0) {	
	
		var cents = "<input type='text' id='cents' class='cents'  style='outline:none; border:none; background:none; width:100px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(cents);			
			jQuery("input.cents").focus();					
			jQuery("input.cents").blur(function() {	
			var id_for_blur = document.getElementById('cents').parentNode.id.split('_');			
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_mini_label_cents").text(value);	
		});	
		 
	}	
	});
	
	
	
	});		
		 }	

	else
       if(document.getElementById(t+"_typeform_id_temp").value=="type_scale_rating"){
		var myu = t;
        jQuery(document).ready(function() {	

		jQuery("#"+myu+"_mini_label_worst").click(function() {		
	
		if (jQuery(this).children('input').length == 0) {	

			var worst = "<input type='text' id='worst' class='worst' style='outline:none; border:none; background:none; font-size:11px; width:100px;' value=\""+jQuery(this).text()+"\">";	
				jQuery(this).html(worst);							
				jQuery("input.worst").focus();			
				jQuery("input.worst").blur(function() {	
			
			var id_for_blur = document.getElementById('worst').parentNode.id.split('_');
			var value = jQuery(this).val();			
		jQuery("#"+id_for_blur[0]+"_mini_label_worst").text(value);		
		});	
	}	
	});	    
     	
		jQuery("label#"+myu+"_mini_label_best").click(function() {	
	if (jQuery(this).children('input').length == 0) {	
	
		var best = "<input type='text' id='best' class='best' style='outline:none; border:none; background:none; font-size:11px; width:100px;' value=\""+jQuery(this).text()+"\">";	
			jQuery(this).html(best);			
			jQuery("input.best").focus();					
			jQuery("input.best").blur(function() {	
			var id_for_blur = document.getElementById('best').parentNode.id.split('_');			
			var value = jQuery(this).val();			
			
			jQuery("#"+id_for_blur[0]+"_mini_label_best").text(value);	
		});	
		 
	}	
	});
	
	
	
	});		
		 }			 
					

	}
	
	form_view=1;
	form_view_count=0;
	for(i=1; i<=30; i++)
	{
		if(document.getElementById('form_id_tempform_view'+i))
		{
			form_view_count++;
			form_view_max=i;
		}
	}
	
	if(form_view_count>1)
	{
		for(i=1; i<=form_view_max; i++)
		{
			if(document.getElementById('form_id_tempform_view'+i))
			{
				first_form_view=i;
				break;
			}
		}
		form_view=form_view_max;
		need_enable=false;
		
		generate_page_nav(first_form_view);
		
	var img_EDIT = document.createElement("img");
			img_EDIT.setAttribute("src", "components/com_formmaker/images/edit.png");
			img_EDIT.style.cssText = "margin-left:40px; cursor:pointer";
			img_EDIT.setAttribute("onclick", 'el_page_navigation()');
			
	var td_EDIT = document.getElementById("edit_page_navigation");
			td_EDIT.appendChild(img_EDIT);
	
	document.getElementById('page_navigation').appendChild(td_EDIT);

			
	}


//if(document.getElementById('take').innerHTML.indexOf('up_row(')==-1) location.reload(true);
//else 
document.getElementById('form').value=document.getElementById('take').innerHTML;
document.getElementById('araqel').value=1;

}

function formAddToOnload()
{ 
	if(formOldFunctionOnLoad){ formOldFunctionOnLoad(); }
	formOnload();
}

function formLoadBody()
{
	formOldFunctionOnLoad = window.onload;
	window.onload = formAddToOnload;
}

var formOldFunctionOnLoad = null;

formLoadBody();


</script>

    <input type="hidden" name="option" value="com_formmaker" />

    <input type="hidden" name="id" value="<?php echo $row->id?>" />

    <input type="hidden" name="cid[]" value="<?php echo $row->id; ?>" />

    <input type="hidden" name="task" value="" />
    <input type="hidden" id="araqel" value="0" />

</form>
<script>
	appWidth			=parseInt(document.body.offsetWidth);
	appHeight			=parseInt(document.body.offsetHeight);
	
	jQuery('#modal-preview').on('show', function () {
document.getElementById('modal-preview-container').innerHTML = '<div class="modal-body"><iframe class="iframe" src="index.php?option=com_formmaker&task=preview&format=raw&theme='+document.getElementById('theme').value+'" height="'+(appHeight-200)+"px"+'" width="100%" style="border:0px"></iframe></div>';
});

document.getElementById('modal-preview').style.width=(appWidth-130)+"px";
document.getElementById('modal-preview').style.height=(appHeight-90)+"px";
document.getElementById('modal-preview').style.left="50px";
document.getElementById('modal-preview').style.top="30px";
document.getElementById('modal-preview').style.margin="0px";
</script>


	
	<script src="<?php echo  $cmpnt_js_path ?>/formmaker1.js" type="text/javascript" style=""></script>
	<script src="<?php echo  JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/wdformjquery.js'; ?>" type="text/javascript"></script>
	<script src="<?php echo  JURI::root(true).'/components/com_formmaker/views/formmaker/tmpl/jquery-ui.js'; ?>" type="text/javascript"></script>
	


<?php		
$bar= JToolBar::getInstance( 'toolbar' );
$bar->appendButton( 'popup', 'preview', 'Preview', 'index.php?option=com_formmaker&task=preview&format=raw', '617', '500' );

}	

public static function show_themes(&$rows, &$pageNav, &$lists){

	JSubMenuHelper::addEntry(JText::_('Forms'), 'index.php?option=com_formmaker&amp;task=forms' );
	JSubMenuHelper::addEntry(JText::_('Submissions'), 'index.php?option=com_formmaker&amp;task=submits' );
	JSubMenuHelper::addEntry(JText::_('Themes'), 'index.php?option=com_formmaker&amp;task=themes', true );

	JHTML::_('behavior.tooltip');	
	JHtml::_('formbehavior.chosen', 'select');

	?>
<script type="text/javascript">
Joomla.tableOrdering= function ( order, dir, task )  {
    var form = document.adminForm;
    form.filter_order_themes.value     = order;
    form.filter_order_Dir_themes.value = dir;
    submitform( task );
}


function SelectAll(obj) { obj.focus(); obj.select(); } 
</script>
	
   
	<form action="index.php?option=com_formmaker" method="post" name="adminForm" id="adminForm">
    
		<table width="100%">
		<tr>
			<td align="left" width="100%">
				<input type="text" name="search_theme" id="search_theme" value="<?php echo $lists['search_theme'];?>" class="text_area"  placeholder="Search theme" style="margin:0px" />
				<button class="btn tip hasTooltip" type="submit" data-original-title="Search"><i class="icon-search"></i></button>
				<button class="btn tip hasTooltip" type="button" onclick="document.id('search_theme').value='';this.form.submit();" data-original-title="Clear">
				<i class="icon-remove"></i></button>
				
				<div class="btn-group pull-right hidden-phone">
					<label for="limit" class="element-invisible"><?php echo JText::_('JFIELD_PLG_SEARCH_SEARCHLIMIT_DESC');?></label>
					<?php echo $pageNav->getLimitBox(); ?>
				</div>

			</td>
		</tr>
		</table>    
    
        
    <table class="table table-striped"  width="100%">
    <thead>
    	<tr>            
            <th width="30" class="title"><?php echo "#" ?></td>
			<th width="20"><input type="checkbox" name="checkall-toggle" value="" onclick="Joomla.checkAll(this)"></th>
            <th width="30" class="title"><?php echo JHTML::_('grid.sort',   'ID', 'id', @$lists['order_Dir'], @$lists['order'] ); ?></td>
            <th><?php echo JHTML::_('grid.sort', 'Title', 'title', @$lists['order_Dir'], @$lists['order'] ); ?></th>
            <th><?php echo JText::_('Default'); ?></th>
        </tr>
    </thead>
	<tfoot>
		<tr>
			<td colspan="11">
			 <?php echo $pageNav->getListFooter(); ?>
			</td>
		</tr>
	</tfoot>
                
    <?php
    $k = 0;
	for($i=0, $n=count($rows); $i < $n ; $i++)
	{
		$row = &$rows[$i];
		$checked 	= JHTML::_('grid.id', $i, $row->id);
		$link 		= JRoute::_( 'index.php?option=com_formmaker&task=edit_themes&cid[]='. $row->id );
?>
        <tr class="<?php echo "row$k"; ?>">
        	<td align="center"><?php echo $i+1?></td>
        	<td><?php echo $checked?></td>
        	<td align="center"><?php echo $row->id?></td>
        	<td><a href="<?php echo $link;?>"><?php echo $row->title?></a></td>            
			<td align="center">
				<?php if ( $row->default == 1 ) : ?>
				<i class="icon-star"></i>
				<?php else : ?>
				&nbsp;
				<?php endif; ?>
			</td>
       </tr>
        <?php
		$k = 1 - $k;
	}
	?>
    </table>
	
    <input type="hidden" name="option" value="com_formmaker">
    <input type="hidden" name="task" value="themes">    
    <input type="hidden" name="boxchecked" value="0"> 
    <input type="hidden" name="filter_order_themes" value="<?php echo $lists['order']; ?>" />
    <input type="hidden" name="filter_order_Dir_themes" value="<?php echo $lists['order_Dir']; ?>" />       
    </form>

<?php
}

public static function add_themes($def_theme){

		JRequest::setVar( 'hidemainmenu', 1 );
		
		?>
        
<script>

Joomla.submitbutton= function (pressbutton) {
	
	var form = document.adminForm;
	
	if (pressbutton == 'cancel_themes') 
	{
		submitform( pressbutton );
		return;
	}
	if(form.title.value=="")
	{
		alert('Set Theme title');
		return;
	}

	submitform( pressbutton );
}


</script>        
<form action="index.php" method="post" name="adminForm" id="adminForm" enctype="multipart/form-data" >
<table class="admintable" >

 
				<tr>
					<td class="key">
						<label for="title">
							Title of theme:
						</label>
					</td>
					<td >
                                    <input type="text" name="title" id="title" size="80"/>
					</td>
				</tr>
				<tr>
					<td class="key" valign="top">
						<label for="title">
							Css:
						</label>
					</td>
					<td >
                                    <textarea name="css" id="css" rows=30 style="width:500px"><?php echo $def_theme->css ?></textarea>
					</td>
				</tr>
</table>           
    <input type="hidden" name="option" value="com_formmaker" />
    <input type="hidden" name="task" value="" />
</form>

	   <?php	
	
}


public static function edit_themes(&$row){

		JRequest::setVar( 'hidemainmenu', 1 );
		
		?>
        
<script>

Joomla.submitbutton= function (pressbutton) {
	
	var form = document.adminForm;
	
	if (pressbutton == 'cancel_themes') 
	{
		submitform( pressbutton );
		return;
	}
	if(form.title.value=="")
	{
		alert('Set Theme title');
		return;
	}

	submitform( pressbutton );
}


</script>        
<form action="index.php" method="post" name="adminForm" id="adminForm" enctype="multipart/form-data" >
<table class="admintable" >

 
				<tr>
					<td class="key">
						<label for="title">
							Title of theme:
						</label>
					</td>
					<td >
                        <input type="text" name="title" id="title" value="<?php echo htmlspecialchars($row->title) ?>" size="80"/>
					</td>
				</tr>
				<tr>
					<td class="key" valign="top">
						<label for="title">
							Css:
						</label>
					</td>
					<td >
                        <textarea name="css" id="css" rows=30 style="width:500px"><?php echo htmlspecialchars($row->css) ?></textarea>
					</td>
				</tr>
</table>           
    <input type="hidden" name="option" value="com_formmaker" />
	<input type="hidden" name="id" value="<?php echo $row->id?>" />        
	<input type="hidden" name="cid[]" value="<?php echo $row->id; ?>" />        
	<input type="hidden" name="task" value="" />        
</form>

	   <?php	
	
}

/////////////////////////////////////////////////////////////////////// THEME /////////////////////////////////










	
public static function editSubmit($rows, $labels_id ,$labels_name,$labels_type, $ispaypal){
JRequest::setVar( 'hidemainmenu', 1 );
$editor	= JFactory::getEditor();

$document = JFactory::getDocument();
 	$cmpnt_js_path = JURI::root(true).'/administrator/components/com_formmaker/js';
	$document->addScript($cmpnt_js_path.'/main_div.js');
		?>
        
<script language="javascript" type="text/javascript">

Joomla.submitbutton= function (pressbutton) {
	var form = document.adminForm;

	if (pressbutton == 'cancel_submit') 
	{
	submitform( pressbutton );
	return;
	}

	submitform( pressbutton );
}

</script>        

<form action="index.php" method="post" name="adminForm"  id="adminForm">
<table class="admintable" style="border-spacing:5px; border-collapse: separate;">
				<tr>
					<td class="key">
						<label for="ID">
							<?php echo JText::_( 'ID' ); ?>:
						</label>
					</td>
					<td >
                       <?php echo $rows[0]->group_id;?>
					</td>
				</tr>
                
                <tr>
					<td class="key">
						<label for="Date">
							<?php echo JText::_( 'Date' ); ?>:
						</label>
					</td>
					<td >
                       <?php echo $rows[0]->date;?>
					</td>
				</tr>
                <tr>
					<td class="key">
						<label for="IP">
							<?php echo JText::_( 'IP' ); ?>:
						</label>
					</td>
					<td >
                       <?php echo $rows[0]->ip;?>
					</td>
                </tr>
                
<?php 
foreach($labels_id as $key => $label_id)
{
	if($labels_type[$key]!='' and $labels_type[$key]!='type_editor' and $labels_type[$key]!='type_submit_reset' and $labels_type[$key]!='type_map' and $labels_type[$key]!='type_mark_map' and $labels_type[$key]!='type_captcha' and $labels_type[$key]!='type_recaptcha' and $labels_type[$key]!='type_button')
	{
		$element_value='';
		foreach($rows as $row)
		{
			if($row->element_label==$label_id)
			{		
				$element_value=	$row->element_value;
				break;
			}
			else
			{	
				$element_value=	'element_valueelement_valueelement_value';
				
			}
			
		}
		
		if($element_value=="element_valueelement_valueelement_value")
			continue;
		
		switch ($labels_type[$key])
		{
			case 'type_checkbox':
			{
			$choices	= explode('***br***',$element_value);
			$choices 	= array_slice($choices,0, count($choices)-1);   
		echo '		<tr>
						<td class="key" rowspan="'.count($choices).'">
							<label for="title">
								'.$labels_name[$key].'
							</label>
						</td>';
			foreach($choices as $choice_key => $choice)
		echo '
						<td >
							<input type="text" name="submission_'.$label_id.'_'.$choice_key.'" id="submission_'.$label_id.'_'.$choice_key.'" value="'.$choice.'" size="80" />
						</td>
					</tr>
					';
				
			break;
			}
			case 'type_paypal_payment_status':
			{
			echo '		<tr>
							<td class="key">
								<label for="title">
									'.$labels_name[$key].'
								</label>
							</td>
							<td >'
							
							?>
							
<select name="submission_0" id="submission_0" >
	<option value="" ></option>
	<option value="Canceled" >Canceled</option>
	<option value="Cleared" >Cleared</option>
	<option value="Cleared by payment review" >Cleared by payment review</option>
	<option value="Completed" >Completed</option>
	<option value="Denied" >Denied</option>
	<option value="Failed" >Failed</option>
	<option value="Held" >Held</option>
	<option value="In progress" >In progress</option>
	<option value="On hold" >On hold</option>
	<option value="Paid" >Paid</option>
	<option value="Partially refunded" >Partially refunded</option>
	<option value="Pending verification" >Pending verification</option>
	<option value="Placed" >Placed</option>
	<option value="Processing" >Processing</option>
	<option value="Refunded" >Refunded</option>
	<option value="Refused" >Refused</option>
	<option value="Removed" >Removed</option>
	<option value="Returned" >Returned</option>
	<option value="Reversed" >Reversed</option>
	<option value="Temporary hold" >Temporary hold</option>
	<option value="Unclaimed" >Unclaimed</option>
</select>	
<script> 
    var element = document.getElementById('submission_0');
    element.value = '<?php echo $element_value; ?>';
</script>
							
							<?php
							echo '
							</td>
						</tr>
						';

			
			break;
			}
			 case 'type_star_rating':   
		   {
                        $edit_stars="";	
						$element_value1 = str_replace("***star_rating***",'',$element_value);
						$stars_value=explode('***', $element_value1);
	                    
						for( $j=0;$j<$stars_value[1];$j++)
							$edit_stars.='<img id="'.$label_id.'_star_'.$j.'" onclick="edit_star_rating('.$j.','.$label_id.')" src="components/com_formmaker/images/star_yellow.png" /> ';
						
						for( $k=$stars_value[1];$k<$stars_value[0];$k++)
							$edit_stars.='<img id="'.$label_id.'_star_'.$k.'" onclick="edit_star_rating('.$k.','.$label_id.')" src="components/com_formmaker/images/star.png" /> ';
					
								
				echo '		<tr>
						<td class="key">
							<label for="title">
								'.$labels_name[$key].'
							</label>
						</td>
						<td >
								
				<input type="hidden"  id="'.$label_id.'_star_amountform_id_temp" name="'.$label_id.'_star_amountform_id_temp" value="'.$stars_value[0].'">
				
				<input type="hidden"  id="'.$label_id.'_selected_star_amountform_id_temp" name="'.$label_id.'_selected_star_amountform_id_temp" value="'.$stars_value[1].'">
								'.$edit_stars.'
								
								<input type="hidden" name="submission_'.$label_id.'" id="submission_'.$label_id.'" value="'.$element_value.'" size="80" />
								</td>
							</tr>
							';
			break;							
		}
		
			 case "type_scale_rating": 
		        {           					
							$scale_radio = explode('/', $element_value);
	                        $scale_value = $scale_radio[0]; 
							
							$scale ='<table><tr>';
						for( $k=1;$k<=$scale_radio[1];$k++)
						$scale .= '<td style="text-align:center"><span>'.$k.'</span></td>';
						$scale .='<tr></tr>';
						for( $l=1;$l<=$scale_radio[1];$l++){
						if($l==$scale_radio[0])
						$checked="checked";
						else
						$checked="";
						$scale .= '<td><input type="radio" name = "'.$label_id.'_scale_rating_radio" id = "'.$label_id.'_scale_rating_radio_'.$l.'" value="'.$l.'" '.$checked.' onClick="edit_scale_rating(this.value,'.$label_id.')" /></td>';
						}	
                $scale .= '</tr></table>';
				
		echo '		<tr>
						<td class="key">
							<label for="title">
								'.$labels_name[$key].'
							</label>
						</td>
						<td >
						
		<input type="hidden"  id="'.$label_id.'_scale_checkedform_id_temp" name="'.$label_id.'_scale_checkedform_id_temp" value="'.$scale_radio[1].'">
		
						'.$scale.'
						
						<input type="hidden" name="submission_'.$label_id.'" id="submission_'.$label_id.'" value="'.$element_value.'" size="80" />
						</td>
					</tr>
					';
		
	    break;
		}
		case 'type_range':
            {                						
							$range_value = explode('-', $element_value);		
             $range = '<input name="'.$label_id.'_element0"  id="'.$label_id.'_element0" type="text" value="'.$range_value[0].'" onChange="edit_range(this.value,'.$label_id.',0)" style="width:90px;"/> - <input name="'.$label_id.'_element1"  id="'.$label_id.'_element1" type="text" value="'.$range_value[1].'" onChange="edit_range(this.value,'.$label_id.',1)" style="width:90px;"/>';							
		
		echo '		<tr>
						<td class="key">
							<label for="title">
								'.$labels_name[$key].'
							</label>
						</td>
						<td >
							'.$range.'
						
						<input type="hidden" name="submission_'.$label_id.'" id="submission_'.$label_id.'" value="'.$element_value.'"  />
						</td>
					</tr>
					';
        break;
		}
		case 'type_spinner':
            {                						
							echo '		<tr>
							<td class="key">
								<label for="title">
									'.$labels_name[$key].'
								</label>
							</td>
							<td >
								<input type="text" name="submission_'.$label_id.'" id="submission_'.$label_id.'" value="'.str_replace("*@@url@@*",'',$element_value).'" style="width:90px;" />
							</td>
						</tr>
						';

        break;
		}
		case 'type_grading':
		{
            				$element_value1 = str_replace("***grading***",'',$element_value);					
							$garding_value = explode(':', $element_value1);
							
							$items_count = sizeof($garding_value)-1;
							$garding = "";
							$sum = "";
							for($k=0;$k<$items_count/2;$k++)
							{						
             $garding .= '<input name="'.$label_id.'_element'.$k.'"  id="'.$label_id.'_element'.$k.'" type="text" value="'.$garding_value[$k].'" onKeyUp="edit_grading('.$label_id.','.$items_count.')" style="width:90px;"/> '.$garding_value[$items_count/2+$k].'</br>';							
		     $sum += $garding_value[$k];
			  }
		echo '		<tr>
						<td class="key">
							<label for="title">
								'.$labels_name[$key].'
							</label>
						</td>
						<td >
							'.$garding.'<div><span id="'.$label_id.'_grading_sumform_id_temp">'.$sum.'</span>/<span id="'.$label_id.'_grading_totalform_id_temp">'.$garding_value[$items_count].'</span><span id="'.$label_id.'_text_elementform_id_temp"></span>
						<input type="hidden"  id="'.$label_id.'_element_valueform_id_temp" name="'.$label_id.'_element_valueform_id_temp" value="'.$element_value1.'">
						<input type="hidden"  id="'.$label_id.'_grading_totalform_id_temp" name="'.$label_id.'_grading_totalform_id_temp" value="'.$garding_value[$items_count].'">
						<input type="hidden" name="submission_'.$label_id.'" id="submission_'.$label_id.'" value="'.$element_value.'" size="80" />
						</td>
					</tr>
					';
        break;
		}
        case 'type_matrix':
		{
                     				
                                  $new_filename= str_replace("***matrix***",'', $element_value);	
									$matrix_value=explode('***', $new_filename);
	                        $matrix_value = array_slice($matrix_value,0, count($matrix_value)-1);   
					
							
					
        $mat_rows=$matrix_value[0];
		$mat_columns=$matrix_value[$mat_rows+1];
					
				$matrix="<table>";
							
	    
							$matrix .='<tr><td></td>';
					
							for( $k=1;$k<=$mat_columns;$k++)
							$matrix .='<td style="background-color:#BBBBBB; padding:5px; border:1px; ">'.$matrix_value[$mat_rows+1+$k].'</td>';
							$matrix .='</tr>';
							
							$aaa=Array();
							   $var_checkbox=1;
							   $selected_value="";
							   $selected_value_yes="";
							   $selected_value_no="";
							for( $k=1;$k<=$mat_rows;$k++)
							{
								$matrix .='<tr><td style="background-color:#BBBBBB; padding:5px; border:1px;">'.$matrix_value[$k].'</td>';
									if($matrix_value[$mat_rows+$mat_columns+2]=="radio")
									{
								  
										if($matrix_value[$mat_rows+$mat_columns+2+$k]==0)
										{
											$checked="";
											$aaa[1]="";
										}
										else	
										$aaa=explode("_",$matrix_value[$mat_rows+$mat_columns+2+$k]);
										
										
									   
										
										for( $l=1;$l<=$mat_columns;$l++)
										{
											if($aaa[1]==$l){
											$checked='checked';
											
											}
											else
											$checked="";
											$index = "'".$k.'_'.$l."'";
											
										$matrix .='<td style="text-align:center;"><input name="'.$label_id.'_input_elementform_id_temp'.$k.'"  id="'.$label_id.'_input_elementform_id_temp'.$k.'_'.$l.'" type="'.$matrix_value[$mat_rows+$mat_columns+2].'" '.$checked.' onClick="change_radio_values('.$index.','.$label_id.','.$mat_rows.','.$mat_columns.')" /></td>';
										
										}
										
									} 
									else
									{
										if($matrix_value[$mat_rows+$mat_columns+2]=="checkbox")
										{
																  
											for( $l=1;$l<=$mat_columns;$l++)
											{
												if( $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox]==1)
												 $checked ='checked';
												 else
												 $checked ='';
												$index = "'".$k.'_'.$l."'";	
												$matrix .='<td style="text-align:center;"><input name="'.$label_id.'_input_elementform_id_temp'.$k.'_'.$l.'"  id="'.$label_id.'_input_elementform_id_temp'.$k.'_'.$l.'" type="'.$matrix_value[$mat_rows+$mat_columns+2].'" '.$checked.' onClick="change_checkbox_values('.$index.','.$label_id.','.$mat_rows.','.$mat_columns.')"/></td>';
												
											 $var_checkbox++;
											}
										
										}
										else
										{
										
											if($matrix_value[$mat_rows+$mat_columns+2]=="text")
											{
															  
												for( $l=1;$l<=$mat_columns;$l++)
												{
													$text_value = $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox];
												
													$index = "'".$k.'_'.$l."'";									
													$matrix .='<td style="text-align:center;"><input name="'.$label_id.'_input_elementform_id_temp'.$k.'_'.$l.'"  id="'.$label_id.'_input_elementform_id_temp'.$k.'_'.$l.'" type="'.$matrix_value[$mat_rows+$mat_columns+2].'" 
													value="'.$text_value.'" onKeyUp="change_text_values('.$index.','.$label_id.','.$mat_rows.','.$mat_columns.')" style="width:120px; margin:2px 4px;"/></td>';
													$var_checkbox++;
												}
									
											}	
											else
											{
									  
												for( $l=1;$l<=$mat_columns;$l++)
												{
												  $selected_text = $matrix_value[$mat_rows+$mat_columns+2+$var_checkbox];
													if($selected_text=='yes')
													{
														$selected_value_yes ='selected';
														$selected_value_no ='';
														$selected_value ='';
													}									 
													else
													{
														if($selected_text=='no')
														{
														
															$selected_value_yes ='';
															$selected_value_no ='selected';
															$selected_value ='';
														}
														else
														{
															$selected_value_yes ='';
															$selected_value_no ='';
															$selected_value ='selected';
														}
													}
										 
										 
													$index = "'".$k.'_'.$l."'";	
													$matrix .='<td style="text-align:center;"><select name="'.$label_id.'_select_yes_noform_id_temp'.$k.'_'.$l.'"  id="'.$label_id.'_select_yes_noform_id_temp'.$k.'_'.$l.'" onChange="change_option_values('.$index.','.$label_id.','.$mat_rows.','.$mat_columns.')" style="width:90px; margin:2px 4px;"><option value="" '.$selected_value.'></option><option value="yes" '.$selected_value_yes.' >Yes</option><option value="no" '.$selected_value_no.'>No</option></select></td>';
													$var_checkbox++;
									
												}
											}
									
										}
									
									}
									$matrix .='</tr>';
							}
							 $matrix .='</table>';
									
		echo '		<tr>
						<td class="key">
							<label for="title">
								'.$labels_name[$key].'
							</label>
						</td>
						<td >
						<input type="hidden"  id="'.$label_id.'_matrixform_id_temp" name="'.$label_id.'_matrixform_id_temp" value="'.$new_filename.'">
	                     '.$matrix.'
						
						<input type="hidden" name="submission_'.$label_id.'" id="submission_'.$label_id.'" value="'.$element_value.'" size="80" />
						</td>
					</tr>
					';
		
        break;
		}
			default:
			{
			echo '		<tr>
							<td class="key">
								<label for="title">
									'.$labels_name[$key].'
								</label>
							</td>
							<td >
								<input type="text" name="submission_'.$label_id.'" id="submission_'.$label_id.'" value="'.str_replace("*@@url@@*",'',$element_value).'" size="80" />
							</td>
						</tr>
						';

			}
			break;
		}

		
	}
}

?>
 </table>        
<input type="hidden" name="option" value="com_formmaker" />
<input type="hidden" name="id" value="<?php echo $rows[0]->group_id?>" />        
<input type="hidden" name="form_id" value="<?php echo $rows[0]->form_id?>" />        
<input type="hidden" name="date" value="<?php echo $rows[0]->date?>" />        
<input type="hidden" name="ip" value="<?php echo $rows[0]->ip?>" />        
<input type="hidden" name="task" value="save_submit" />        
</form>
        <?php		
       

}
	   
	   
	   
public static function forchrome($id){
?>
<script type="text/javascript">


window.onload=val; 

function val()
{
var form = document.adminForm;
	submitform();
}

</script>
<form action="index.php" method="post" name="adminForm"  id="adminForm">

    <input type="hidden" name="option" value="com_formmaker" />

    <input type="hidden" name="id" value="<?php echo $id;?>" />

    <input type="hidden" name="cid[]" value="<?php echo $id; ?>" />

    <input type="hidden" name="task" value="gotoedit" />
</form>
<?php
}


public static function select_article(&$rows, &$pageNav, &$lists)
{



		JHTML::_('behavior.tooltip');	

	?>

<form action="index.php?option=com_formmaker" method="post" name="adminForm" id="adminForm">

    <table width="100%">

        <tr>

            <td align="left" width="100%"> <?php echo JText::_( 'Filter' ); ?>:

                <input type="text" name="search" id="search" value="<?php echo $lists['search'];?>" class="text_area" onchange="document.adminForm.submit();" />

                <button onclick="this.form.submit();"> <?php echo JText::_( 'Go' ); ?></button>

                <button onclick="document.getElementById('search').value='';this.form.submit();"> <?php echo JText::_( 'Reset' ); ?></button>

            </td>

        </tr>

    </table>

    <table class="adminlist" width="100%">

        <thead>

            <tr>

                <th width="4%"><?php echo '#'; ?></th>

                <th width="8%">

                    <input type="checkbox" name="toggle"

 value="" onclick="checkAll(<?php echo count($rows)?>)">

                </th>

                <th width="50%"><?php echo JHTML::_('grid.sort', 'Title', 'title', @$lists['order_Dir'], @$lists['order'] ); ?></th>

                <th width="38%"><?php echo JHTML::_('grid.sort', 'Email to Send Submissions to', 'mail', @$lists['order_Dir'], @$lists['order'] ); ?></th>

            </tr>

        </thead>

        <tfoot>

            <tr>

                <td colspan="50"> <?php echo $pageNav->getListFooter(); ?> </td>

            </tr>

        </tfoot>

        <?php

	

    $k = 0;

	for($i=0, $n=count($rows); $i < $n ; $i++)

	{

		$row = &$rows[$i];

		$checked 	= JHTML::_('grid.id', $i, $row->id);

		$published 	= JHTML::_('grid.published', $row, $i); 

		// prepare link for id column

		$link 		= JRoute::_( 'index.php?option=com_formmaker&task=edit&cid[]='. $row->id );

		?>

        <tr class="<?php echo "row$k"; ?>">

              <td align="center"><?php echo $row->id?></td>

          <td align="center"><?php echo $checked?></td>

            <td align="center"><a href="<?php echo $link; ?>"><?php echo $row->title?></a></td>

            <td align="center"><?php echo $row->mail?></td>

        </tr>

        <?php

		$k = 1 - $k;

	}

	?>

    </table>

    <input type="hidden" name="option" value="com_formmaker">
    <input type="hidden" name="task" value="forms">
    <input type="hidden" name="boxchecked" value="0">
    <input type="hidden" name="filter_order" value="<?php echo $lists['order']; ?>" />
    <input type="hidden" name="filter_order_Dir" value="" />

</form>

<?php

}







//////////////////////////////glxavor 
}
?>