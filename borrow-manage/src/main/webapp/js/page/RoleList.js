 layui.use(["form", "laypage", "layer"], function() {
     var laypage = layui.laypage,
         form = layui.form();

     initPage()

     initTable()
    $("#refresh").click(function(){
        layer.showLoad();
        location.href = "?" + layui.parseQuery({});
    })
     function initPage() {
        $("#roleSearch1").keyup(function(event){
            if(event.keyCode ==13){
                layer.showLoad();
                var param = {"roleName":$("#roleSearch1").val()};
                location.href = "?" + layui.parseQuery(param);
              }
            });
        $("#roleSearch2").keyup(function(event){
            if(event.keyCode ==13){
                layer.showLoad();
                var param = {"roleTypeValue":$("#roleSearch2").val()};
                location.href = "?" + layui.parseQuery(param);
            }
        });
         form.on("submit(roleSearch)", function(data) {
             layer.showLoad();
             var param = data.field;
             location.href = "?" + layui.parseQuery(param);
         });
         $("#btnAdd").click(function() {
             layer.iframe({
                 title: "新建角色",
                 content: ma.host + "role/info/add",
                 area: ['1000px', '600px'],
                 btn: ["保存", "取消"],
                 yes: submitIframe
             });
         })
     }

     function initTable() {
         var table = $("#tableRole");
         var pageCount = table.attr("data-pageCount");
         var pageNo = layui.getQuery().pageNo || 1;

         laypage({
             cont: 'pagerRole',
             pages: pageCount,
             curr: pageNo,
             jump: function(obj, first) {
                 if (!first) {
                     var que = layui.getQuery();
                     que.pageNo = obj.curr;
                     location.href = "?" + layui.parseQuery(que)
                 }
             }
         })

         $("#tableRole .btnEdit").click(function() {
             var id = $(this).data("id")
             tableEdit(id)
         });

         $("#tableRole .btnDel").click(function() {
             var id = $(this).data("id");
             tableDel(id)
         });
         $("#tableRole .roleUser").click(function() {
             var id = $(this).data("id");
             delRoleUser(id);
         });
     }



     //修改角色
     function tableEdit(id) {;
         layer.iframe({
             title: "修改角色",
             content: ma.host + "role/info/edit?roleUid=" + id,
             area: ['1000px', '600px'],
             btn: ["保存", "取消"],
             yes: submitEditIframe
         });
     }

     //删除角色
     function tableDel(id) {
         layer.confirm({
             content: "确定要删除？",
             yes: function() {
                 layer.close(layer.index);
                 layer.showLoad();
                 ma.ajax({
                     url: "role/info/del",
                     data: { roleUid: id },
                     done: function(re) {
                         top.layer.success('角色删除成功！');
                         window.location.reload();
                     }
                 })
             }
         })
     }

     //删除角色用户
     function delRoleUser(id) {
         layer.open({
             type: 2,
             title: "删除角色绑定的用户",
             area: ['60%', '420px'],
             scrollbar: false,
             btn: ["删除", "取消"],
             yes: submitRoleUserIframe,
             content: ma.host + "role/binduser/info/list?roleUid=" + id
         });
         // layer.iframe({
         //
         //     content: ma.host + "role/binduser/info/list?roleUid=" + id,
         //     area: ['60%', '500px'],
         //     btn: ["删除", "取消"],
         //     yes: submitRoleUserIframe
         // });
     }

     function submitIframe(index, page) {
         layer.getChildFrame('#formRoleAddSubmit', index).click();
     }

     function submitEditIframe(index, page) {
         layer.getChildFrame('#formRoleModifySubmit', index).click();
     }

     function submitRoleUserIframe(index, page) {
         layer.getChildFrame('#formRoleUserSubmit', index).click();
     }


 });