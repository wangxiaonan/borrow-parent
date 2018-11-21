/**
 * Created by ontheway on 2017/8/25.
 */
layui.use(["form", "layer"], function () {
    var layer = layui.layer,
        form = layui.form();
    form.on('submit(formMenuModify)', function (data) {
        var ids = [];
        var departmentids = [];
        // $('.ids').each(function () {
        //     ids.push($(this).val());
        // });
         $('.departmentName').each(function () {
            departmentids.push($(this).val());
        });
        var data = data.field;
        var addMenu = {
            url: ma.host + "menu/info/update",
            data: {
                menuUid: data.menuUid,
                name: data.name,
                parentId: data.parentId,
                parentIds: data.parentIds,
                permission: data.permission,
                target: data.target || "",
                remark: data.remark,
                departmentids: departmentids.join(','),//添加部门
                ids: ids.join(',')
            },
            done: function (res) {
                top.layer.success('修改成功！');
                parent.window.location.reload();
            }
        };
        layer.showLoad();
        ma.ajax(addMenu);
    });
    var listDepartment = {
        url: ma.host + "department/info/byname",
        data: {
            id: ""
        },
        done: function (res) {
            var _html ='<option value="">部门名称</option>';
            for (var i = 0; i < res.data.length; i++) {
                   _html += '<option value="'+res.data[i].id+'">'+res.data[i].name+'-'+res.data[i].remark+'</option>'
            }
            $('.departmentName').append(_html);
            form.render('select');
        }
    };
    var searchDepartment = {
        url: ma.host + "department/bindmenu/info",
        data: {
            menuUid: $('#menuUid').val()
        },
        done: function (res) {
            var _html = '';
            for (var i = 0; i < res.data.length; i++) {
                _html += '<div class="departmentItem">' +
                    '<div class="layui-input-inline" style="width: 165px;">' +
                    '<select name="departmentids" lay-search class="departmentName">'+
                    '<option value="'+res.data[i].id+'">'+res.data[i].name+ '-' + res.data[i].remark+'</option>'+
                    '</select>' +
                    '</div>' +
                    '<i class="layui-icon delDepartment" style="font-size: 34px; color: #1E9FFF; cursor: pointer;">&#xe640;</i>  ' +
                    '</div>'
            }
            $('.departmentWrap').html(_html);
            form.render('select');
            initEvent();
        }
    };
    ma.ajax(searchDepartment);
  // var searchUser = {
  //       url: ma.host + "usermenuid/info/list",
  //       data: {
  //           menuUid: $('#menuUid').val()
  //       },
  //       done: function (res) {
  //           var _html = '';
  //           for (var i = 0; i < res.data.length; i++) {
  //               _html += '<div class="adminItem">' +
  //                   '<div class="layui-input-inline" style="width: 165px;">' +
  //                   '<input type="text" name="userAdmin" placeholder="请输入用户名并查询" value="' + res.data[i].loginName + '" class="layui-input userAdmin"/>' +
  //                   '</div>' +
  //                   '<div class="layui-input-inline">' +
  //                   '<button class="layui-btn searchUser">查询用户</button>' +
  //                   '</div>' +
  //                   '<div class="layui-input-inline" style="width: 235px;">' +
  //                   '<input type="text" name="realName" placeholder="姓名/邮箱" disabled value="' + res.data[i].name + '/' + res.data[i].email + '" class="layui-input realName">' +
  //                   '<input type="hidden" disabled value="' + res.data[i].id + '" name="ids" class="ids">' +
  //                   '</div>' +
  //                   '<i class="layui-icon delAdmin" style="font-size: 34px; color: #1E9FFF; cursor: pointer;">&#xe640;</i>  ' +
  //                   '</div>'
  //           }
  //           $('.adminWrap').html(_html);
  //           initEvent();
  //       }
  //   };
  //   ma.ajax(searchUser);
    // $('#addUserAdmin').click(function () {
    //     var _html = '<div class="adminItem">' +
    //         '<div class="layui-input-inline" style="width: 165px;">' +
    //         '<input type="text" name="userAdmin" placeholder="请输入用户名并查询" class="layui-input userAdmin"/>' +
    //         '</div>' +
    //         '<div class="layui-input-inline">' +
    //         '<button class="layui-btn searchUser">查询用户</button>' +
    //         '</div>' +
    //         '<div class="layui-input-inline" style="width: 235px;">' +
    //         '<input type="text" name="realName" disabled placeholder="姓名/邮箱" class="layui-input realName">' +
    //         '<input type="hidden" name="ids" class="ids">' +
    //         '</div>' +
    //         '<i class="layui-icon delAdmin" style="font-size: 34px; color: #1E9FFF; cursor: pointer;">&#xe640;</i>  ' +
    //         '</div>';
    //     $('.adminWrap').append(_html);
    //      initEvent() 
    // });
     $('#addDepartment').click(function () {
        var _html = '<div class="departmentItem">' +
            '<div class="layui-input-inline" style="width: 165px;">' +
            '<select name="departmentids" lay-search class="departmentName"></select>' +
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
        // $('.userAdmin').change(function () {
        //     $(this).parents('.adminItem').find('.realName').val('用户名有修改，请重新查询');
        //     $(this).parents('.adminItem').find('.ids').val('');
        // });
        // $(".searchUser").click(function () {
        //     var _this = this;
        //     var searchUser = {
        //         url: ma.host + "user/info/listnew",
        //         data: {
        //             loginName: $(_this).parents('.adminItem').find('.userAdmin').val()
        //         },
        //         done: function (res) {
        //             if (res.data.length == 0) {
        //                 $(_this).parents('.adminItem').find('.realName').val('');
        //                 $(_this).parents('.adminItem').find('.ids').val('');
        //                 layer.warning('未查询到此用户，请确认后查询！');
        //                 return;
        //             }
        //             $(_this).parents('.adminItem').find('.realName').val(res.data[0].name + '/' + res.data[0].email);
        //             $(_this).parents('.adminItem').find('.ids').val(res.data[0].id);
        //         }
        //     };
        //     ma.ajax(searchUser);
        // });
        // $('.delAdmin').click(function () {
        //     $(this).parents('.adminItem').remove();
        //     initEvent();
        // });
        $('.delDepartment').click(function () {
            $(this).parents('.departmentItem').remove();
            initEvent();
        });
    };
});