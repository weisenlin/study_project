<!DOCTYPE html>
<html lang="en">
<head>
<#include "/common/header.ftl" >
    <meta charset="UTF-8">
    <title>学习计划</title>
</head>
<body>
    <table id="tt" class="easyui-datagrid" style="width:1280px;height: 768px">
    </table>
</body>
<script>
    /**
     * @author senlin
     *
     * @requires jQuery
     *
     * 格式化日期时间
     */
    function DateTimeFormatter(value) {
        if (value == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }

        return dateValue.format("yyyy-MM-dd hh:mm:ss");
    }
</script>
<script>
    $('#tt').datagrid({
        url: '/project/list.do',
        title: "计划信息",
        singleSeletct: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        pagesize: 10,
        columns: [[
            { field: 'id', title: 'id', hidden: true },

            { field: 'name', title: '名称', width:40},
            { field: 'description', title: '描述', width:150},
            {
                field: 'startDate', title: '开始时间',width:150,
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }

            },
            {
                field: 'endDate', title: '结束时间',width:150,
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }

            }
        ]]

    });
    //转换日期格式
    function changeDateFormat(cellval) {
        if (cellval != null) {
            var d = new Date(cellval);
            return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
        }
    }
</script>
</html>