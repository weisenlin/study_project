<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <link rel="stylesheet" type="text/css" href="/easy_ui/jquery-easyui-1.3.3/themes/default/easyui.css"></link>
    <link rel="stylesheet" type="text/css" href="/easy_ui/jquery-easyui-1.3.3/themes/icon.css"></link>
    <script type="text/javascript" src="/easy_ui/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="/easy_ui/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/easy_ui/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        var url;

        function formatEdit(val, row) {
            var roles = row.roles;
            var ids = [];
            for (var i = 0; i < roles.length; i++) {
                    ids.push(roles[i].id);
            }
            var role = ids.join(",");

            return "<a href=\"javascript:openRoleChooseDialog( '" + role + "'," + row.id + ")\"><img style='margin-top: 4px' src='/easy_ui/images/edit.gif' /></a>";
        }

        function formatRole(val, row) {
            var roles = row.roles;
            if (!roles) {
                return "";
            }
            var role = "";
            for (var i = 0; i < roles.length; i++) {
                role = role + roles[i].name + ",";
            }
            role = role.substring(0, role.length - 1);
            return role;
        }

        function openRoleChooseDialog(roles, userId) {
            var rolesArr = roles.split(",");
            $("#roleSetDialog").dialog("open").dialog("setTitle", "选择角色");
            $("#roleDg").datagrid({
                url: '/user/role/list.do',
                onLoadSuccess: function (data) {
                    var selectedRows = $("#roleDg").datagrid('getRows');
                    for (var i = 0; i < selectedRows.length; i++) {
                        var id = selectedRows[i].id;
                        for(var j=0;j<rolesArr.length;j++){
                            if(rolesArr[j]==id){
                                $("#roleDg").datagrid('checkRow', i);
                                break;
                            }
                        }
//                        if ($.inArray(id, rolesArr) >= 0) {
//                            $("#roleDg").datagrid('checkRow', i);
//                        }
                    }
                }
            });
            $("#userId").val(userId);
        }

        function saveRoleSet() {
            var userId = $("#userId").val();
            var selectedRows = $("#roleDg").datagrid("getSelections");
            var strRoleIds = [];
            for (var i = 0; i < selectedRows.length; i++) {
                strRoleIds.push(selectedRows[i].id);
            }
//            var roleIds = strRoleIds.join(",");
            $.ajax({
                type:'post',
                url:'/user/role/set.do',
                dataType:'json',
                traditional: true,
                data: {'uid':userId,'ids':strRoleIds},
                success:function(result){
                    if (result.success) {
                        closeRoleSetDialog();
                        $("#dg").datagrid("reload");
                    } else {
                        $.messager.alert("系统提示", "提交失败，请联系管理员！");
                    }
                }
            })
//            $.post("/user/role/set.do", {rid: strRoleIds, uid: userId}, function (result) {
//                if (result.success) {
//                    closeRoleSetDialog();
//                    $("#dg").datagrid("reload");
//                } else {
//                    $.messager.alert("系统提示", "提交失败，请联系管理员！");
//                }
//            }, "json");
        }

        function closeRoleSetDialog() {
            $("#roleSetDialog").dialog("close");
        }


        function deleteUser() {
            var selectedRows = $("#dg").datagrid("getSelections");
            if (selectedRows.length != 1) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            }
            var id = selectedRows[0].id;
            $.messager.confirm("系统提示", "您确定要删除这条数据吗？", function (r) {
                if (r) {
                    $.post("/user/delete.do", {id: id}, function (result) {
                        if (result.success) {
                            $.messager.alert("系统提示", "数据已成功删除！");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", result.errorInfo);
                        }
                    }, "json");
                }
            });
        }


        function openUserAddDialog() {
            $("#dlg").dialog("open").dialog("setTitle", "添加用户信息");
            url = "/user/save.do";
        }

        function openUserModifyDialog() {
            var selectedRows = $("#dg").datagrid("getSelections");
            if (selectedRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要修改的数据！");
                return;
            }
            var row = selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle", "修改用户信息");
            $("#fm").form("load", row);
            $("#userName").attr("readonly", "readonly")
            url = "/user/update.do?id=" + row.id;
        }


        function saveUser() {
            $("#fm").form("submit", {
                url: url,
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统提示", "保存成功！");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    } else {
                        $.messager.alert("系统提示", result.errorInfo);
                    }
                }
            });
        }


        function resetValue() {
            $("#nickname").val("");
            $("#pswd").val("");
            $("#eamil").val("");
        }

        function closeUserDialog() {
            $("#dlg").dialog("close");
            resetValue();
        }

        function searchUser() {
            $("#dg").datagrid('load', {
                "userName": $("#s_userName").val()
            });
        }
        function testAys(){
            $.ajax({
                url:'/test//asyn.do',
                success:function(){
                    $.messager.alert("提示","正在请求");
                }
            })
        }

        $(function () {

            $("#dg").datagrid({
                //双击事件
                onDblClickRow: function (index, row) {
                    $("#dlg").dialog("open").dialog("setTitle", "修改用户信息");
                    $("#fm").form("load", row);
                    $("#nickname").attr("readonly", "readonly")
                    url = "/user/udpate.do?id=" + row.id;
                },
                onLoadSuccess: function (data) {
                    console.log(data);
//                    $("#dg").datagrid("checkRow",0);
                }
            });
        });
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="用户管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true" singleSelect="true"
       url="/user/list.do" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="id" width="20" align="center">编号</th>
        <th field="nickname" width="50" align="center">用户名</th>
        <th field="pswd" width="50" align="center">密码</th>
        <th field="email" width="80" align="center">邮箱</th>
        <th field="roles" width="150" align="center" formatter="formatRole">拥有角色</th>
        <th field="aa" width="50" align="center" formatter="formatEdit">角色设置</th>
    </tr>
    </thead>
</table>

<div id="tb">
    <div>
        <a href="javascript:openUserAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:openUserModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteUser()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        <a href="javascript:testAys()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">测试</a>
    </div>
    <div>
        &nbsp;用户名：&nbsp;<input type="text" id="s_userName" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
        <a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width:450px;height:300px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons" data-options="onClose:function(){resetValue()}">

    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>用户名：</td>
                <td><input type="text" id="nickname" name="nickname" class="easyui-validatebox" required="true"
                           data-options="missingMessage:'请输入用户名'"/></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="text" id="pswd" name="pswd" class="easyui-validatebox" required="true"
                           data-options="missingMessage:'请输入密码'" style="width: 250px"/></td>
            </tr>
            <tr>
                <td>邮箱：</td>
                <td><input type="text" id="email" name="email" class="easyui-validatebox"
                           data-options="missingMessage:'请输入邮箱'" style="width: 250px"/></td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveUser()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<div id="roleSetDialog" class="easyui-dialog" style="width:500px;height:350px;"
     closed="true" buttons="#dlg-buttons2">
    <input type="hidden" id="userId" name="userId"/>
    <table id="roleDg" class="easyui-datagrid" fitColumns="true"
           fit="true">
        <thead>
        <tr>
            <th field="cb" checkbox="true" align="center"></th>
            <th field="id" width="50" align="left" hidden="true">编号</th>
            <th field="name" width="100" align="left">角色名称</th>
            <th field="remarks" width="200" align="left">备注</th>
        </tr>
        </thead>
    </table>

</div>

<div id="dlg-buttons2">
    <a href="javascript:saveRoleSet()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeRoleSetDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
<script>
//    var user = [{
//        'id':1,
//        'name':'admin'
//    },{
//        'id':2,
//        'name':'admin2'
//    }];
//    var role = {
//        'id':1,
//        'name':'name1'
//    };
//    var para = {
//        'user':JSON.stringify(user),
//        'role':JSON.stringify(role)
//    }
//    $.ajax({
//        type:'post',
//        url:'/user/test.do',
//        data:JSON.stringify(para),
//        dataType:'json',
//        contentType:'application/json',
//        success:function(){
//            alert("success");
//        }
//    })
</script>
</body>
</html>