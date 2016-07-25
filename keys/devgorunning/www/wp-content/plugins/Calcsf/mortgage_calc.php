<?php
/*
Plugin Name: Visdom Mortgage and Refinance Calculator
Description: Mortgage and Refinance Calculator for Visdom site
Author: Arpad Lukacs
Version: 1.0
*/

defined('VCALC_URL') or define('VCALC_URL', plugin_dir_url(__FILE__));

function display_vmcalc($options = array(), $content = null, $code = "") {
  global $vmcalcURL;

  if (!empty($code) || (!empty($options) && ($options[0] == 'vmcalc'))) {
    $shortcode = true;
  }

  if ($shortcode) ob_start();

  $rate_options = array(
    array(1, 2.19, 'FIXED'),
    array(2, 2.04, 'FIXED'),
    array(3, 2.19, 'FIXED'),
    array(4, 2.44, 'FIXED'),
    array(5, 2.38, 'FIXED'),
    array(6, 3.74, 'FIXED'),
    array(7, 2.99, 'FIXED'),
    array(8, 3.84, 'FIXED'),
    array(9, 3.84, 'FIXED'),
    array(10, 3.5, 'FIXED'),
    array(3, 1.95, 'VARIABLE'),
    array(5, 1.8, 'VARIABLE'),
  );

  if (isset($_POST['vmcalc-submit'])) {
    $purchase = intval($_POST['purchase']);
    if (!$purchase) $purchase = '';
    $down_payment = intval($_POST['down_payment']);
    if (!$down_payment) $down_payment = '';

    $interest = $_POST['interest'];
    if (!$interest) $interest = '';

    $amortization = intval($_POST['amortization']);
    if (!$amortization) $amortization = '';

  } elseif (isset($_POST['vrfcalc-submit'])) {
    $value = intval($_POST['value']);
    if (!$value) $value = '';
    $amount = intval($_POST['amount']);
    if (!$amount) $amount = '';

    $interest = $_POST['interest'];
    if (!$interest) $interest = '';

    $cashback = intval($_POST['cashback']);
    if (!$cashback) $cashback = '';
  }
  else {
    $interest = format_rate_option($rate_options[4][0], $rate_options[4][1], $rate_options[4][2]);
    $purchase = 850000;
    $down_payment = 250000;
    $amortization = 25;

    $value = 850000;
    $amount = 250000;
    $cashback = 80;
  }

  include("form.inc.php");

  if (isset($_POST['vmcalc-submit'])) {
    $result = calculate_mortgage($purchase, $downpayment, $interest, $amortization);
    include "show_mortgage_result.php";

  } elseif (isset($_POST['vrfcalc-submit'])) {
    $result = calculate_refinance($value, $amount, $interest, $cashback);
    include "show_refinance_result.php";

  } else {
    echo '<script type="text/javascript">
    jQuery(document).ready(function(){
      start_modals();
    });
    </script>';
  }

  if ($shortcode) {
    $result = ob_get_contents();
    ob_end_clean();
    if (is_null($content)) {
      return $result;
    } else {
      return $content . $result;
    }
  }
}

add_shortcode('vmcalc', 'display_vmcalc');

function add_styles_and_scripts() {
  wp_enqueue_script('jquery-effects-slide');

  wp_register_script('modaljs' , plugins_url('/js/bootstrap.min.js',  __FILE__), array('jquery'), '3.3.5', true);
  wp_register_style('modalcss' , plugins_url('/css/bootstrap.min.css',  __FILE__), '' , '3.3.5', 'all');

  wp_enqueue_script('modaljs');
  wp_enqueue_style('modalcss');

  wp_register_style('fontawesome', 'https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css');
  wp_enqueue_style('fontawesome');

  wp_register_script('chartjs', plugins_url('/js/Chart.min.js', __FILE__));
  wp_enqueue_script('chartjs');
  wp_register_script('stackedchartjs', plugins_url('/js/Chart.StackedBar.js', __FILE__));
  wp_enqueue_script('stackedchartjs');

  wp_register_style('vmcalc-form-style', plugins_url('widget-form.css', __FILE__));
  wp_enqueue_style('vmcalc-form-style');
  wp_register_style('vmcalc-modal-style', plugins_url('/css/modal.css', __FILE__));
  wp_enqueue_style('vmcalc-modal-style');

  wp_register_script('mymodaljs' , plugins_url('/js/display_modals.js',  __FILE__));
  wp_enqueue_script('mymodaljs');
}

add_action('wp_enqueue_scripts', 'add_styles_and_scripts' );

function format_rate_option($year, $rate, $text) {
  return(number_format($rate, 2) . '% ' . $year . ' yr '. $text);
}

function process_interest_param($input) {
  preg_match_all("/(\d.\d+)% (\d+) yr (\w+)/", $input, $interest_parts);

  return floatval($interest_parts[1][0]);
}

function calculate_mortgage($purchase, $downpayment, $interest_str, $AmortizationYears) {
  $interest = process_interest_param($interest_str);

  $numberOfPayment = 52.0;
  $AmountOfMortgage = $purchase - $down_payment;

  // CMHC Insurrance
  $Up65Percent = .60;
  $Up75Percent = .75;
  $Up80Percent = 1.25;
  $Up85Percent = 1.80;
  $Up90Percent = 2.40;
  $Up95Percent = 3.60;

  $loanToValue = $AmountOfMortgage / $purchase;

  $cmhc = 0;
  if ($loanToValue >= 0.8) {
    if ($loanToValue <= .65)
      $percentPrem = $Up65Percent;
    elseif (($loanToValue > .65) && ($loanToValue <= .75))
      $percentPrem = $Up75Percent;
    elseif (($loanToValue > .75) && ($loanToValue <= .80))
      $percentPrem = $Up80Percent;
    elseif (($loanToValue > .80) && ($loanToValue <= .85))
      $percentPrem = $Up85Percent;
    elseif (($loanToValue > .85) && ($loanToValue <= .90))
      $percentPrem = $Up90Percent;
    else
      $percentPrem = $Up95Percent;

    $cmhc = ($percentPrem / 100.0) * $AmountOfMortgage;
  }

  $AmountOfMortgageCMHC = $purchase - $down_payment + $cmhc;

  $semiAnnualInterest = ($interest / 100.0 / 2.0);

  $powerSemiAnnualInterest = pow(1 + $semiAnnualInterest, 2);
  $PowerTwelvethSemiAnnual = pow($powerSemiAnnualInterest, 1 / $numberOfPayment);
  $monthlyInterestRate = $PowerTwelvethSemiAnnual - 1;
  $AmortizationPayments = $AmortizationYears * $numberOfPayment;
  $Payment = ($AmountOfMortgageCMHC * $monthlyInterestRate) / (1 - pow((1.0 + $monthlyInterestRate), ($AmortizationPayments * -1.0)));
  $WeeklyPayment = round($Payment*100)/100;

  $semiAnnualInterest = ($interest / 100.0 / 2.0);

  $powerSemiAnnualInterest = pow(1 + $semiAnnualInterest, 2);
  $PowerTwelvethSemiAnnual = pow($powerSemiAnnualInterest, 1 / $numberOfPayment);
  $monthlyInterestRate = $PowerTwelvethSemiAnnual - 1;
  $AmortizationPayments = $AmortizationYears * $numberOfPayment;
  $Payment = ($AmountOfMortgage * $monthlyInterestRate) / (1 - pow((1.0 + $monthlyInterestRate), ($AmortizationPayments * -1.0)));
  $RoundedPayment2 = round($Payment*100)/100;
  $WeeklyCMHC = round($WeeklyPayment - $RoundedPayment2);

  $semiAnnualInterestLOC = ($interest / 100 / 2); // By law, in Canada, all Mortgages are Calculates on Semi-Annual Interest
  $powerSemiAnnualInterestLOC = pow(1 + $semiAnnualInterestLOC, 2);
  $PowerTwelvethSemiAnnualLOC = pow($powerSemiAnnualInterestLOC, 1 / 52);//round()1 / 12, 15) // Monthly only
  $monthlyInterestRateLOC = $PowerTwelvethSemiAnnualLOC - 1;
  $paymentLOC = $AmountOfMortgage * $monthlyInterestRateLOC;

  $Interest = round($paymentLOC * 100) / 100;
  $Principal = $WeeklyPayment - ($WeeklyPayment - $RoundedPayment2) - $Interest;

  return array(
    'WeeklyPayment' => number_format($WeeklyPayment, 2, '.', ''),
    'Principal' => number_format($Principal, 2, '.', ''),
    'Interest' => number_format($Interest, 2, '.', ''),
    'WeeklyCMHC' => number_format($WeeklyCMHC, 2, '.', '')
  );
}

function calculate_refinance($value, $amount, $interest_str, $equityInCashBack) {
  $interest = process_interest_param($interest_str);
  $numberOfPayment = 52.0;
  $AmortizationYears = 25;//in this calculator this is always set at 25.
  $cashBack = (($equityInCashBack / 100.0) * $value) - $amount;

  $AmountOfMortgage = $amount + $cashBack;//figure the AmountOfMortgage

  $semiAnnualInterest = ($interest / 100.0 / 2.0);

  $powerSemiAnnualInterest = pow(1 + $semiAnnualInterest, 2);
  $PowerTwelvethSemiAnnual = pow($powerSemiAnnualInterest, 1 / $numberOfPayment);
  $monthlyInterestRate = $PowerTwelvethSemiAnnual - 1;
  $AmortizationPayments = $AmortizationYears * $numberOfPayment;
  $Payment = ($AmountOfMortgage * $monthlyInterestRate) / (1 - pow((1.0 + $monthlyInterestRate), ($AmortizationPayments * -1.0)));
  $WeeklyPayment = round($Payment*100)/100;

  return array(
    'WeeklyPayment' => number_format($WeeklyPayment, 0, '.', ''),
    'Cashback' => number_format($cashBack, 0, '.', ''),
    'UntouchedEquity' => number_format($value - $AmountOfMortgage, 0, '.', ''),
    'TotalMortgage' => number_format($AmountOfMortgage, 0, '.', '')
  );
}

?>
