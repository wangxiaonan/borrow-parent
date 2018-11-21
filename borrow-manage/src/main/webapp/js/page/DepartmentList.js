/**
 * Created by lixiaojing on 2017/12/20.
 */
function permission(data) {
    return data.split(',').length == 3 ? "1" : '0';
}

function isTop(data) {
    if (data === '0') {
        return 'display: none;'
    }
}

function isHref(data) {
    return data === '1' ? 'href' : 'target'
}

function isDisabled(data) {
    if (data === '1') {
        return 'position:relative; z-index: -99999;';
    }
}

Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

layui.use(["form", "treegrid"], function () {
    var form = layui.form(),
        treegrid = layui.treegrid;

    function getMenuId(menuList) {
        var id = [];
        for (var i = 0; i < menuList.length; i++) {
            id.push(menuList[i].id);
        }
        return id;
    }

    var treeMenu = treegrid.createNew({
        elem: 'treeMenu',
        view: 'viewMenu',
        data: {rows: menuList},
        parentid: 'parentId',
        singleSelect: true,
        rowClick: function (row) {
            if ($('.treegrid-' + row.id).hasClass('treegrid-expanded')) {
                treeMenu.collapse(row.id);
            } else {
                treeMenu.expand(row.id);
            }
        }
    });
    treeMenu.build();
    treeMenu.collapseAll();

    var menuId = getMenuId(menuList);
    var TreeIds = sessionStorage.TreeId || '';
    TreeIdsArr = TreeIds.split(',');
    for(var i = 0, len = TreeIdsArr.length; i < len; i++){
        menuId.remove(parseInt(TreeIdsArr[i]));
    }
    for (var i = 0; i < TreeIdsArr.length; i++) {
        treeMenu.expand(TreeIdsArr[i]);
    }
    $(".department-add").click(function (event) {
        var _this = this;
        layer.iframe({
            title: "添加部门",
            content: ma.host + 'department/info/addlist?jobtitle=0&departmentId=' + $(_this).attr('data-id'),
            area: ['800px', '420px'],
            btn: ['确认', '取消'],
            yes: function (index, page) {
                layer.getChildFrame('#formDepartmentAddSubmit', index).click();
            }
        })
        event.stopPropagation();
    });
    $(".post-add").click(function (event) {
        var _this = this;
        layer.iframe({
            title: "添加岗位",
            content: ma.host + 'department/info/addlist?jobtitle=1&departmentId=' + $(_this).attr('data-id'),
            area: ['800px', '420px'],
            btn: ['确认', '取消'],
            yes: function (index, page) {
                layer.getChildFrame('#formDepartmentAddSubmit', index).click();
            }
        })
        event.stopPropagation();
    });
    $(".department-assign").click(function (event) {
        var _this = this;
        layer.iframe({
            title: "分配角色",
            content: ma.host + 'department/bindrole/info?departmentId=' + $(_this).attr('data-id'),
            area: ['800px', '400px'],
            btn: ['确认', '取消'],
            yes: function (index, page) {
                layer.getChildFrame('#formRoleAssignSubmit', index).click();
            }
        })
        event.stopPropagation();
    });
    $(".department-manage").click(function (event) {
        var _this = this;
        layer.iframe({
            title: "查询用户",
            content: ma.host + 'department/binduser/info?departmentId=' + $(_this).attr('data-id'),
            area: ['800px', '400px'],
            btn: ['删除', '取消'],
            yes: function (index, page) {
                layer.getChildFrame('#formUserSearchSubmit', index).click();
            }
        })
        event.stopPropagation();
    });
    $(".department-modify").click(function (event) {
        var _this = this;
        layer.iframe({
            title: "修改",
            content: ma.host + 'department/info/updatelist?departmentId=' + $(_this).attr('data-id') + '&parentId=' + $(_this).attr('data-parentid') + '&thisId=' + $(_this).attr('data-thisid'),
            area: ['800px', '400px'],
            btn: ['确认', '取消'],
            yes: function (index, page) {
                layer.getChildFrame('#formDepartmentModifySubmit', index).click();
            }
        })
        event.stopPropagation();
    })
    $(".department-del").click(function (event) {
        var param = {uuid: $(this).data("id"), master: '测试删除'};
        layer.confirm({
            content: "确定要删除吗？",
            yes: function () {
                var addDepartment = {
                    type: "POST",
                    url: ma.host + 'department/info/del',
                    data: param,
                    done: function (res) {
                        top.layer.success('删除成功！');
                        window.location.reload();
                    }
                }
                layer.showLoad();
                ma.ajax(addDepartment);
            }
        });
        event.stopPropagation();
    });

    window.onunload = function () {
        var TreeId = [];
        $('.treegrid-expanded').each(function (k, v) {
            if($(this).css('display') != 'none'){
                TreeId.push($(this).data('id'));
            }
        });
        sessionStorage.TreeId = TreeId;
    }
});
