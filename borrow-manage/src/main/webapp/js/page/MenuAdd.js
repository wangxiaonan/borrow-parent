/**
 *project JS main entrance
 *for example rely on  Layui'layer and form
 *userinfo && pass modify  page
 *
 **/
layui.use(["form", "layer"], function() {
    var layer = layui.layer,
        form = layui.form();
    form.on('submit(formMenuAdd)', function(data) {
        // if(!data.ids){
        //     layer.warning('请输入要绑定的系统管理员并查询确认！');
        //     return;
        // }
        var departmentids = [];
        $('.departmentName').each(function () {
            departmentids.push($(this).val());
        });
        var data = data.field;
        var addMenu = {
            url: ma.host + "menu/info/insert",
            data: {
                name: data.name,
                parentId: data.parentId,
                parentIds: data.parentIds,
                permission: layui.getQuery().permission,
                target: data.target || "",
                remark: data.remark,
                ids: data.ids,
                departmentids: departmentids.join(','),//添加部门
            },
            done: function(res) {
                top.layer.success("数据添加成功!");
                parent.window.location.reload();
            }
        };
        layer.showLoad();
        ma.ajax(addMenu,this);
    });
    var listDepartment = {
        url: ma.host + "department/info/byname",
        data: {
            id: ""
        },
        done: function (res) {
            var _html ='';
            for (var i = 0; i < res.data.length; i++) {
                   _html += '<option value="'+res.data[i].id+'">'+res.data[i].name+'-'+res.data[i].remark+'</option>'
            }
            $('.departmentName').append(_html);
            form.render('select');
        }
    };
    ma.ajax(listDepartment);
    $('#addDepartment').click(function () {
        var _html = '<div class="departmentItem">' +
            '<div class="layui-input-inline">' +
            '<select name="departmentids" lay-search class="departmentName">' +
            '<option value="">部门名称</option></select>' +
            '</div>' +
            '<i class="layui-icon delDepartment" style="font-size: 34px; color: #1E9FFF; cursor: pointer;">&#xe640;</i>  ' +
            '</div>';
        $('.departmentWrap').append(_html);
        ma.ajax(listDepartment);
        initEvent() 
    });
    function initEvent() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.iframeAuto(index); 
        $('.delDepartment').click(function () {
            $(this).parents('.departmentItem').remove();
            initEvent();
        });
    }
    initEvent();
});