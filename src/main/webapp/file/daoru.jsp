<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String base = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>模板下载->上传文件</title>
    <link rel="stylesheet" href="<%=base%>/layui/css/layui.css">
    <link rel="stylesheet" href="<%=base%>/layui/css/style.css">
    <script src="<%=base%>/layui/layui.js"></script>
    <script type="text/javascript" src="<%=base%>/jquery/jquery-2.2.4.min.js"></script>
    <style>

        html, body {
            background: #fff;
        }

        .mbxiazai {
            color: #4472C4;
        }

        .mbxiazai:hover {
            text-decoration: underline;
        }

        .xzwj {
            height: 30px;
            line-height: 30px;
            background-color: #ddd;
            color: #666;
        }

        .xzwja:hover {
            color: #666;
        }
    </style>
</head>
<body style="background: #fff;">
<!--导出弹出层-->
<div style="padding:20px 50px 0">
    <div class="tc">

        <div>
            请选择要上传的文件：<a href="<%=base%>/file/downloadExcel" class="mbxiazai">模板下载</a>
        </div>
        <div style="padding: 20px 0 30px 0;">
            <button type="button" class="layui-btn xzwj xzwja">选择文件</button>
            <input type="file" name="objFile" id="objFile">
            <button onclick="uploadExcel()" type="button" class="layui-btn xzwj"
                    style="background-color:#4472C4;color: #fff;">
                上传文件
            </button>
        </div>

    </div>
</div>

<script>
    function uploadExcel() {
        var filepath = $("#objFile").val();
        var objFile = $("#objFile").get(0).files[0];
        if (!objFile) {
            alert("请选择上传文件");
            return false;
        }
        var fileType = filepath.substring(filepath.length - 4, filepath.length);
        if (fileType.toLowerCase() != "xlsx") {
            alert("文件格式不正确!!!");
            return false;
        }

        var formData = new FormData();
        formData.append("objFile", objFile);
        $.ajax({
            type: "POST",
            url: "<%=base%>/file/uploadExcel",
            data: formData,
            // 告诉jQuery不要去处理发送的数据
            processData: false,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType: false,
            dataType: "json",
            success: function (data) {
                if (data.code == "success") {
                    alert("上传成功！");
                    window.location.href = "<%=base%>/file/findExcelList";
                } else {
                    alert("上传失败！");
                }
            }
        });
    }
</script>

</body>
</html>