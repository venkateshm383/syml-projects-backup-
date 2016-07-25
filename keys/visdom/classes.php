<?php
error_reporting(0);
@session_start();
ob_start();
class main 
{
   function main()
   {   
   }	 
	  function login(){	  
	  if(isset($_POST['login'])){
	  
	    $user = $_POST['W1TxtLoginEmail'];
	    $password = $_POST['W1TxtLoginPassword'];
		
		
		
		if($user=="rayan" && $password=="rayan")
		{
		$_SESSION['user']= $user;
		$_SESSION['password']= $password;
		header('Location: index.php');
		}
		else 
		{?><div class="error"><?php echo "You have to enter valid details";}?></div> <?php
		}
		} 
		
	/** function for checking login **/
	
	function check_login()
	{
		if(!isset($_SESSION['user']) && !isset($_SESSION['password']))
		{			   
			//header('Location: login.php');
		} 			
	}
		
}
$objMain = new main;
?>
		