<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <!-- 引入 ECharts 文件 -->
    <script src="../js/echarts.min.js"></script>
    <script src="../jquery/jquery-2.1.0.min.js"></script>
    <title>异步数据加载示例</title>
</head>

<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>

<ul id="list"></ul>

<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('main'));

    // 显示标题，图例和空的坐标轴
    myChart.setOption({
        title: {
            text: '异步数据加载示例'
        },
        tooltip: {},
        legend: {
            data: ['年龄']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '年龄',
            type: 'bar',
            data: []
        }]
    });

    $(function () {
        $.ajax({
            url: "http://localhost:8082/echarts/data.do",
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                var jsonData = JSON.parse(JSON.stringify(data));

                console.log("jsonData-age:" + jsonData.name);
                console.log("jsonData-name:" + jsonData.age);

                var arr = [ "one", "two", "three", "four"];
                $.each(arr, function(){
                    console.log(this);
                });

                var arr1 = [[1, 4, 3], [4, 6, 6], [7, 20, 9]]
                $.each(arr1, function(i, item){
                    //item[0]相对于取每一个一维数组里的第一个值
                    console.log(item[0]);
                    //循环的是二维
                    console.log(arr1[i]);

                });

                var obj = { one:1, two:2, three:3, four:4};
                //这个each就有更厉害了，能循环每一个属性
                $.each(obj, function(key, val) {
                    console.log(obj[key]);
                });

                myChart.setOption({
                    xAxis: {
                        data: jsonData.name
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '年龄',
                        data: jsonData.age
                    }]
                });


            },
            error: function (data) {
                alert(data.result);
            }
        });
    });


</script>

</body>


</html>

