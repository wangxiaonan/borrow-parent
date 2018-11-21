layui.use(["form", "element", "layer"], function () {
    var form = layui.form();

    form.on("submit(submitRoleUser)", function (data) {
        var roleUid = data.field.roleUid;
        var bindUserUids = [];
        $('.roleUserList:checked').each(function () {
            bindUserUids.push($(this).val());
        });

        if(bindUserUids.length == 0){
            top.layer.error("请选择要删除的用户！");
            return;
        }
        var delRoleUser = {
            url: "role/bind/user/del",
            data: {
                roleUid: roleUid,
                bindUserUids: bindUserUids
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
});