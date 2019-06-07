<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <!-- 引入 ECharts 文件 -->
  <script src="../js/echarts.min.js"></script>
  <title>饼图示例</title>
</head>

<body>
  <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
  <div id="main" style="width: 600px;height:400px;"></div>

  <script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 绘制图表
    var option = {
      title: {
        text: '饼图示例'
      },
      series: {
        type: 'pie',
        data: [
          {name: 'A', value: 1212},
          {name: 'B', value: 2323},
          {name: 'C', value: 1919}
        ]
      }
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

  </script>

</body>



</html>

