<script type="text/javascript">
  jQuery(document).ready(function(){
    jQuery("#js-mortgage").show();
    jQuery(".mortgage-result").show();

    jQuery("#result-weekly_payment").html("$<?php echo $result['WeeklyPayment'] ?>");
    var data = [
      {
        value: "<?php echo $result['Principal'] ?>",
        color:"#00c6e9",
        highlight: "#46d0e9",
        label: "Principal"
      },
      {
        value: "<?php echo $result['Interest'] ?>",
        color: "#03ff9d",
        highlight: "#4dffba",
        label: "Interest"
      },
      {
        value: "<?php echo $result['WeeklyCMHC'] ?>",
        color: "#ffad00",
        highlight: "#ffc64d",
        label: "CMHC Insurrance"
      }
    ]
    var ctx = document.getElementById("mortgage-chart").getContext("2d");
    var chart_options = {
      legendTemplate : "<table class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><tr><td width=\"15%\"><span class=\"fa-stack fa-lg\"><i class=\"fa fa-circle fa-stack-1x\" style=\"color:<%=segments[i].fillColor%>\"></i><i class=\"fa fa-circle-thin fa-stack-1x\"></i></span></td><td><%if(segments[i].label){%><%=segments[i].label%></td><td width=\"35%\">$<%=segments[i].value %></td><%}%></tr><%}%></table>",
      segmentShowStroke: false,
      showTooltips: false
    }
    var myPieChart = new Chart(ctx).Pie(data, chart_options);
    jQuery('#pie_legend').html(myPieChart.generateLegend());
  });
</script>

