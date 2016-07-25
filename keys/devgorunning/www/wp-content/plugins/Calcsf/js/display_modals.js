var start_modals = function() {

  function myShow(e) {
    jQuery(e).show('slide', {direction: 'right'});
  }

  function ShowModal(modal, callback) {
    jQuery('#main_modal').html(jQuery('#' + modal + '_content').html()).show('slide', { direction: 'right' }, 400, function() {
      jQuery(this).modal('show');
    });
  }

  jQuery("#js-mortgage").show();

  jQuery(".js-close").live("click", function(e) {
    var this_modal = jQuery(e.target).parents('.modal');
    this_modal.hide('slide', { direction: 'left' }, 400, function() {
      jQuery(this).modal('hide');
    });
  });

  jQuery("button[data-next]").live("click", function(e) {
    var btn = jQuery(e.target);
    var this_modal = btn.parents('.modal');
    var next_modal = btn.data('next');

    if (next_modal == 'refinance_1') {
      jQuery("#js-mortgage").hide();
      jQuery("#js-refinance").show();
    }

    this_modal.hide('slide', { direction: 'left' }, 400, function() {
      jQuery('#main_modal').html(jQuery('#' + next_modal + '_content').html())
        .show('slide', { direction: 'right' }, 400, function() {
        });
    });
  });

  jQuery("button[data-set]").live("click", function(e) {
    var btn = jQuery(e.target);
    var target_input = btn.data('set');
    if (target_input) {
      var i = btn.parents('.modal-content').find('.form-control');
      jQuery('#'+btn.data('set')).val(i.val());
    }
  });

  jQuery("button[data-submit]").live("click", function(e) {
    var form = jQuery(e.target).data('submit');
    jQuery('#' + form + '-submit').click();
  });

  ShowModal('calc_chooser');
}
