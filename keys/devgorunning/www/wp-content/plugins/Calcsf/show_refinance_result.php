<script type="text/javascript">
  jQuery(document).ready(function(){
    jQuery("#js-refinance").show();
    jQuery(".refinance-result").show();

    jQuery("#ref-result-weekly_payment").html("$<?php echo $result['WeeklyPayment'] ?>");
    jQuery(".cashback-result").html("$<?php echo $result['Cashback'] ?>");

    var data = {
      labels: ["Dummy"],
      datasets: [
        {
          label: "Untouched Equity",
          fillColor: "#33ff00",
          strokeColor: "#33ff00",
          highlightFill: "#70ff4d",
          highlightStroke: "#70ff4d",
          data: ["<?php echo $result['UntouchedEquity'] ?>"]
        },
        {
          label: "Total Mortgage",
          fillColor: "#216d8d",
          strokeColor: "#216d8d",
          highlightFill: "#30a0cf",
          highlightStroke: "#30a0cf",
          data: ["<?php echo $result['TotalMortgage'] ?>"]
        },
        {
          label: "Cash Back",
          fillColor: "#00a2c4",
          strokeColor: "#00a2c4",
          highlightFill: "#3bacc4",
          highlightStroke: "#3bacc4",
          data: ["<?php echo $result['Cashback'] ?>"]
        }
      ]
    };
    var ctx = document.getElementById("refinance-chart").getContext("2d");
    var mask_ctx = document.getElementById("refinance-chart-mask").getContext("2d");
    var chart_options = {
      legendTemplate : "<table class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><tr><td width=\"15%\"><span class=\"fa-stack fa-lg\"><i class=\"fa fa-circle fa-stack-1x\" style=\"color:<%=datasets[i].fillColor%>\"></i><i class=\"fa fa-circle-thin fa-stack-1x\"></i></span></td><td><%if(datasets[i].label){%><%=datasets[i].label%></td><td width=\"35%\">$<%=datasets[i].bars[0].value %></td><%}%></tr><%}%></table>",
      scaleShowGridLines: false,
      showScale: false,
      showTooltips: false,
      barShowStroke: false
    }
    var myStackedBarChart = new Chart(ctx).StackedBar(data, chart_options);
    var img = document.getElementById("house");
    mask_ctx.drawImage(img, 0, 0);
    jQuery('#barchart_legend').html(myStackedBarChart.generateLegend());
  });

</script>

