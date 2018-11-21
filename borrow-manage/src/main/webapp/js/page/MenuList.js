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

function createBtnText(data) {
    var btnStr = '';
    if (data === '0') {
        btnStr = '添加系统';
    } else if (data === '1') {
        btnStr = '添加菜单';
    } else {
        var sData = data.split(',');
        if (sData.length === 2) {
            btnStr = '添加页面';
        } else {
            btnStr = '添加接口';
        }
    }
    return btnStr;
}
layui.use(["form", "treegrid"], function() {
    var form = layui.form(),
        treegrid = layui.treegrid;

    function getMenuId(menuList) {
        var id = [];
        for (var i = 0; i < menuList.length; i++) {
            if (menuList[i].parentIds == 1) {
                id.push(menuList[i].id);
            }
        }
        return id;
    }

    var treeMenu = treegrid.createNew({
        elem: 'treeMenu',
        view: 'viewMenu',
        data: { rows: menuList },
        parentid: 'parentId',
        singleSelect: true,
        rowClick: function (row) {
            if($('.treegrid-' + row.id).hasClass('treegrid-expanded')){
                treeMenu.collapse(row.id);
            }else {
                treeMenu.expand(row.id);
            }
        }
    });
    treeMenu.build();
    var menuId = getMenuId(menuList);
    for (var i = 0; i < menuId.length; i++) {
        treeMenu.collapseAll(menuId[i]);
    }

    $('.menu-add').click(function(event) {
        var _this = this;
        layer.iframe({
            title: $(_this).text(),
            content: ma.host + 'menu/info/add?menuUid=' + $(_this).attr('data-id') + '&permission=' + $(_this).attr('data-permission'),
            area: ['700px', '400px'],
            scrollbar: false,
            btn: ['确认', '取消'],
            yes: function(index, page) {
                layer.getChildFrame('#formMenuAddSubmit', index).click();
            }
        });
        event.stopPropagation();
    });
    $('.menu-modify').click(function(event) {
        var _this = this;
        layer.iframe({
            title: "修改",
            content: ma.host + 'menu/info/edit?menuUid=' + $(_this).attr('data-id'),
            area: ['710px', '400px'],
            scrollbar: false,
            btn: ['确认', '取消'],
            yes: function(index, page) {
                layer.getChildFrame('#formMenuModifySubmit', index).click();
            }
        });
        event.stopPropagation();
    });
    $('.menu-del').click(function(event) {
        var menuUid = $(this).data("id");
        var param = { menuUid: menuUid };
        layer.confirm({
            content: "确定要删除吗？",
            yes: function() {
                var addMenu = {
                    type: "POST",
                    url: ma.host + 'menu/info/del',
                    data: param,
                    done: function(res) {
                        top.layer.success('删除成功！');
                        window.location.reload();
                    }
                };
                layer.showLoad();
                ma.ajax(addMenu);
            }
        });
        event.stopPropagation();
    });
});