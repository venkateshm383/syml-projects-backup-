<?php
  class BaseCalc {
    var $interest;
    var $numberOfPayment;

    function BaseCalc($interest) {
      $this->numberOfPayment = 52.0;
      $this->interest = $interest;
    }

    function WeeklyPayment($purchase, $downpayment, $AmortizationYears, $AmountOfMortgage) {
      $AmountOfMortgageCMHC = $purchase - $down_payment + $cmhc;

      $semiAnnualInterest = ($this->interest / 100.0 / 2.0);

      $powerSemiAnnualInterest = pow(1 + $semiAnnualInterest, 2);
      $PowerTwelvethSemiAnnual = pow($powerSemiAnnualInterest, 1 / $numberOfPayment);
      $monthlyInterestRate = $PowerTwelvethSemiAnnual - 1;
      $AmortizationPayments = $AmortizationYears * $numberOfPayment;
      $Payment = ($AmountOfMortgageCMHC * $monthlyInterestRate) / (1 - pow((1.0 + $monthlyInterestRate), ($AmortizationPayments * -1.0)));
      return round($Payment * 100) / 100;
    }

    function CMHC() {
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
      return $cmhc;
    }


  }

  class MortgageCalc extends BaseCalc {
    var $purchase;
    var $downpayment;
    var $AmortizationYears;

    function MortgageCalc($purchase, $downpayment, $interest_str, $AmortizationYears) {
      $this->purchase = $purchase;
      $this->downpayment = $downpayment;
      $this->BaseCalc($interest_str);
      $this->AmortizationYears = $AmortizationYears;
    }
  }

  class RefinanceCalc extends BaseCalc {
    var $value;
    var $amount;
    var $AmortizationYears;

    function RefinanceCalc($value, $amount, $interest_str, $cashback) {
      $this->purchase = $purchase;
      $this->downpayment = $downpayment;
      $this->BaseCalc($interest_str);
      $this->AmortizationYears = $AmortizationYears;
    }
  }

?>
