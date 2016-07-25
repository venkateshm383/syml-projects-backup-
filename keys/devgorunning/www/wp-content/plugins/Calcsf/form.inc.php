<div class="row visdom" id="js-mortgage" style="display: none">
  <h1>New Mortgage Payment Caclulator</h1>
  <p>Use this calculator to estimate your monthly mortgage payment including estimates for taxes insurance and PMI.</p><br></br>
  <div class="col-xs-5 no-padding">
    <form action="<?php esc_url($_SERVER['REQUEST_URI']) ?>" method="post">
      <?php render_dollar_input('purchase', 'Purchase Price', $purchase) ?>
      <?php render_dollar_input('down_payment', 'Down Payment', $down_payment) ?>
      <?php render_rate_select('interest', 'mortgage', 'Interest Rate', $rate_options, $interest); ?>
      <div class="form-group">
        <label for="amortization">Amortization</label>
        <div class="input-group wide">
          <select id="mortgage_amortization" name="amortization" class="form-control">
            <?php render_select_options(array('30' => 30, '25' => 25, '20' => 20, '15' => 15, '10' => 10), $amortization) ?>
          </select>
        </div>
      </div>
      <p class="submit">
        <input type="submit" name="vmcalc-submit" id="mortgage-submit" class="button" value="Calculate">
      </p>
    </form>
  </div>

  <div class="col-xs-7 mortgage-result visdom-result" style="display: none">
    <h4>Your weekly payment will be:</h4>
    <h1 id='result-weekly_payment'></h1>
    <div class="row">
      <div class="col-xs-7 legend" id="pie_legend"></div>
      <div class="col-xs-5 text-center">
        <canvas id="mortgage-chart" width="150" height="150"></canvas>
      </div>
    </div>
  </div>
</div>

<div class="row visdom" id="js-refinance" style="display: none">
  <h1>Refinance Caclulator</h1>
  <p>Use this calculator to estimate your monthly refinanced payment including estimates for prepayment penalties, closing costs, taxes insurance and PMI.</p><br></br>
  <div class="col-xs-5 no-padding">
    <form action="<?php esc_url($_SERVER['REQUEST_URI']) ?>" method="post">
      <?php render_dollar_input('value', 'Current value', $value) ?>
      <?php render_dollar_input('amount', 'Amount Owed', $amount) ?>
      <?php render_rate_select('interest', 'refinance', 'Interest Rate', $rate_options, $interest); ?>
      <div class="form-group">
        <label for="cashback">Equity in Cash Back</label>
        <div class="input-group wide">
          <select id="refinance_cashback" name="cashback" class="form-control">
            <?php render_select_options(array('80%' => 80, '60%' => 60, '40%' => 40, '20%' => 20, '10%' => 10), $cashback) ?>
          </select>
        </div>
      </div>
      <p class="submit">
        <input type="submit" name="vrfcalc-submit" id="refinance-submit" class="button" value="Calculate">
      </p>
    </form>
  </div>

  <div class="col-xs-7 refinance-result visdom-result" style="display: none">
    <div class="row">
      <div class="col-xs-7 cashback">
        <h4>Cash Back:</h4>
        <h1 class='cashback-result'style="font-size: 36px;"></h1>
      </div>
      <div class="col-xs-5 text-center weekly">
        <h4>Your weekly payment will be:</h4>
        <h1 id='ref-result-weekly_payment'></h1>
      </div>
    </div>
    <div class="row">
      <div class="col-xs-7  legend" id="barchart_legend"></div>
      <div class="col-xs-5 ">
        <canvas id="refinance-chart" width="150" height="210"></canvas>
          <canvas id="refinance-chart-mask" width="150" height="210"></canvas>
        <img src="<?php echo VCALC_URL ?>house_shape.png" id="house" width="150" height="210" style="display: none">
      </div>
    </div>
  </div>

</div>

<div id="main_modal" class="modal visdom" tabindex="-1" role="dialog">
</div>

<div id="calc_chooser_content" class="hide">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-body clearfix">
        <p class="pull-right"><label>Choose:</label><button type="button" class="btn btn-default btn-lg" data-next="mortgage_1" aria-label="New Mortgage">New Mortgage</button></p>
        <p class="pull-right"><label>Or:</label><button type="button" class="btn btn-default btn-lg" data-next="refinance_1" aria-label="Refinance">Refinance</button></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default pull-left js-close">Close</button>
      </div>
    </div>
  </div>
</div>

<?php
// the first part of the modal's id will be used to find the form to submit
// and for the destination field to copy the value from the modal to
?>
<?php render_modal('mortgage_1', 'mortgage_2', 'purchase', 'Purchase Price', $purchase); ?>
<?php render_modal('mortgage_2', 'mortgage_3', 'down_payment', 'Down Payment', $down_payment); ?>
<?php render_select_modal('mortgage_3', 'interest', 'Rate', $rate_options, $interest); ?>

<?php render_modal('refinance_1', 'refinance_2', 'value', 'Current Value', $value); ?>
<?php render_modal('refinance_2', 'refinance_3', 'amount', 'Amount Owed', $amount); ?>
<?php render_select_modal('refinance_3', 'interest', 'Rate', $rate_options, $interest); ?>

<?php function render_modal($modal_id, $next_modal_id, $input_id, $input_label, $default_value) {
  echo '<div id="'.$modal_id.'_content"  class="hide">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-body clearfix">';
  render_dollar_input($modal_id.'_'.$input_id, $input_label, $default_value);
      echo '</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default pull-left js-close">Close</button>
        <button type="button" class="btn btn-default pull-right" data-next="' . $next_modal_id . '" data-set="'.$input_id.'">Next</button>
      </div>
    </div>
  </div>
</div>';
}

function render_select_modal($modal_id, $input_id, $input_label, $options, $selected) {
  $form_details = explode("_", $modal_id);
  $form_id = $form_details[0];
  echo '<div id="'.$modal_id.'_content" class="hide">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-body clearfix">';
  render_rate_select($input_id, $modal_id, $input_label, $options, $selected);
  echo '</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default pull-left js-close">Close</button>
        <button type="button" class="btn btn-default pull-right" data-submit="'.$form_id.'" data-set="'.$form_id.'_'.$input_id.'">Calculate</button>
      </div>
    </div>
  </div>
</div>';
}

function render_rate_select($input_id, $id_prefix, $input_label, $options, $selected) {
    echo '<div class="form-group">
          <label for="'.$input_id.'">'.$input_label.'</label>
          <div class="input-group wide">
          <select id="'.$id_prefix.'_'.$input_id.'" name="'.$input_id.'" class="form-control">';
  foreach ($options as $values) {
    $label = format_rate_option($values[0], $values[1], $values[2]);

    echo '<option value="'.$label.'" '.($label == $selected ? ' selected="selected" ' : '').'>'.$label.'</option>';
  }
  echo '</select></div></div>';
}

function render_select_options($options, $selected) {
  foreach ($options as $label => $value) {
    echo '<option value="'.$label.'" '.($value == $selected ? ' selected="selected" ' : '').'>'.$label.'</option>';
  }
}

function render_dollar_input($id, $label, $value) {
  echo '
      <div class="form-group">
        <label for="'.$id.'">'.$label.'</label>
        <div class="input-group">
          <input type="text" class="form-control" id="'.$id.'" name="'.$id.'" value="'.$value.'">
          <div class="input-group-addon">$</div>
        </div>
      </div>';
}
?>
