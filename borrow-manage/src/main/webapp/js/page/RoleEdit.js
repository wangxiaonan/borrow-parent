layui.use(["form", "element", "layer"], function() {
    var form = layui.form();

    initPage();

    function initPage() {
        //提交 
        form.on("submit(submitRoleEdit)", function(data) {
            var departmentids = [];
            $('.userAdmin').each(function () {
                departmentids.push($(this).val());
            });
            var roleId = layui.getQuery(),
                topMenuUid = $("#topMenuUid").val(),
                idArr = [];

            $("#menuList .layui-table input:checked").each(function(i, value) {
                var uid = $(value).data("id");
                idArr.push(uid);
            });

            if (!idArr.length) {
                layer.error("至少要选择一个权限");
                return false;
            }

            $("#menuList table").each(function(i, value) {
                var _$this = $(value)
                if (_$this.find("input:checked").length) {
                    idArr.push(_$this.data("roleuid"))
                }
            })
            idArr.push(topMenuUid);

            var param = Object.assign({ menuUids: idArr, roleUid: roleId.roleUid ,departmentids:departmentids.join(',')}, data.field);

            var updatePasswd = {
                url: "role/info/update",
                data: param,
                done: function(data) {
                    top.layer.success('角色修改成功！');
                    var index = parent.layer.getFrameIndex(window.name); //关闭窗口
                    parent.layer.close(index);
                    parent.location.reload();
                }
            }
            if(departmentids == ""){
                layer.warning('请添加部门！')
                return
            } else{
                layer.showLoad();
                ma.ajax(updatePasswd);
            }
        });

        form.on("checkbox", function(data) {
            var dom = data.elem;
            changeChild(dom);
            changeParent(dom);
            form.render("checkbox");
        });
    }
    //  部门添加
    var listDepartment = {
        url: ma.host + "department/info/byname",
        data: {
            id: ""
        },
        done: function (res) {
            var _html ='<option value="">部门名称</option>';
            for (var i = 0; i < res.data.length; i++) {
                   _html += '<option value="'+res.data[i].uuid+'">'+res.data[i].name+'-'+res.data[i].remark+'</option>'
            }
            $('.userAdmin').append(_html);
            form.render('select');
        }
    };
     $('#addUserAdmin').click(function () {
        var _html = '<div class="adminItem">' +
            '<div class="layui-input-inline" style="width: 165px;">' +
            '<select name="userAdmin" lay-verify="required" lay-search class="userAdmin"></select>' +
            '</div>' +
            '<i class="layui-icon delAdmin" style="font-size: 34px; color: #1E9FFF; cursor: pointer;">&#xe640;</i>  ' +
            '</div>';
        $('.adminWrap').append(_html);
         ma.ajax(listDepartment);
        initEvent();
    });
    function initEvent() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.iframeAuto(index); 
        $('.delAdmin').click(function () {
            $(this).parents('.adminItem').remove();
            initEvent();
        });
    }
    initEvent();
    function changeChild(dom) {
        var checked = dom.checked,
            id = $(dom).val();
        $("#menuList input[type=checkbox]").filter("[data-parent=" + id + "]").each(function(index, ele) {
            ele.checked = checked
            changeChild(ele)
        })
    }

    function changeParent(dom) {
        var checked = dom.checked,
            pid = $(dom).attr("data-parent");

        var parent = $("#menuList input[type=checkbox]").filter("[value=" + pid + "]")[0];
        if (!parent) { return }
        var pchecked = parent.checked;

        //子勾选 父未勾选  
        if (checked && !pchecked) {
            parent.checked = true;
            changeParent(parent);
        }

        //子未勾选 父勾选
        if (!checked && pchecked) {
            var _change = true;
            $("#menuList input[type=checkbox]").filter("[data-parent=" + pid + "]").each(function(index, ele) {
                if (ele.checked) {
                    _change = false;
                }
            })

            if (_change) {
                parent.checked = false;
                changeParent(parent);
            }
        }
    }
});