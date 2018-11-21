/**
 * Created by lixiaojing on 2017/12/20.
 */
layui.use(["form", "element", "layer"], function () {
    var form = layui.form();
    var departmentId = layui.getQuery()['departmentId']
    form.on("submit(submitUserSearch)", function (data) {
        var userId  = "";
        $('.roleUserList:checked').each(function () {
            userId += $(this).val()+",";
        });

        if(userId .length == 0){
            top.layer.error("请选择要删除的用户！");
            return;
        }
        var delRoleUser = {
            url: "department/binduser/del",
            data: {
                departmentId  : departmentId  ,
                userId : userId 
            },
            done: function(data) {
                top.layer.success('删除成功！');
                var index = parent.layer.getFrameIndex(window.name); //关闭窗口
                parent.layer.close(index);
            }
        };
        layer.showLoad();
        ma.ajax(delRoleUser);
    });
    // 查询用户
      $(".searchUser").click(function () {
        var searchUser = {
            url: ma.host + "user/info/listnew",
            data: {
                loginName: $('.userAdmin').val()
            },
            done: function(res) {
                if(res.data.length == 0){
                    $('#realName').val("");
                    $('#ids').val("");
                    layer.warning('未查询到此用户，请确认后查询！');
                    return;
                }
                $('.userAdmin').keydown(function () {
                    $('#realName').val("用户名有修改，请重新查询");
                    $('#ids').val("");
                });
                $('#realName').val(res.data[0].name + '/' + res.data[0].email);
                $('#ids').val(res.data[0].uuid);
            }
        };
        ma.ajax(searchUser);
    });
    //添加用户
    $(".userAdd").click(function () {
        if(!$(".userAdmin").val()){
            layer.error("查询用户名不能为空哦！");
            return;
        }
        if(!$('#ids').val()){
            layer.error("请输入正确用户名并查询！");
            return;
        }
        var userAdd = {
            url: ma.host + "department/binduser/add",
            data: {
                departmentId : departmentId,
                userId :$('#ids').val()
            },
            done: function(res) {
                location.reload()
            },
            fail: function() {
                setTimeout(function(){
                    location.reload();
                },1000)
               layer.error("用户名已存在")
            }
        };
        ma.ajax(userAdd,this);
    });
});