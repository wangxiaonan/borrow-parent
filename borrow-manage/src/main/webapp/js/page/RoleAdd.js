 layui.use(["form", "element", "layer"], function() {
     var form = layui.form()

     initPage();

     function initPage() {
         //提交
         form.on("submit(submitRole)", function(data) {
             var topMenuUid = $("#topMenuUid").val(),
                 idArr = [];
             $("#menuList .layui-table input:checked").each(function(i, value) {
                 var uid = $(value).data("id");
                 idArr.push(uid);
             })

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

            var departmentids = [];
            $('.userAdmin').each(function () {
                departmentids.push($(this).val());
            });
            var param = Object.assign({ menuUids: idArr ,departmentids:departmentids.join(',')}, data.field);
            var roleAdd = {
                 url: "role/info/insert",
                 data: param,
                 done: function(data) {
                     top.layer.success('角色添加成功！');
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
                ma.ajax(roleAdd);
            }
         });
         var listDepartment = {
            url: ma.host + "department/info/byname",
            data: {
                id: ""
            },
            done: function (res) {
                var _html ='';
                for (var i = 0; i < res.data.length; i++) {
                       _html += '<option value="'+res.data[i].uuid+'">'+res.data[i].name+'-'+res.data[i].remark+'</option>'
                }
                $('.userAdmin').append(_html);
                form.render('select');
            }
        };
        ma.ajax(listDepartment);
        $('#addDepartment').click(function () {
            var _html = '<div class="departmentItem">' +
                '<div class="layui-input-inline">' +
                '<select name="departmentids" lay-verify="required" lay-search class="userAdmin">' +
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
        $("input[name='roleName']").on("blur", function() {
             var rName = $("input[name='roleName']").val();
             var param = { roleName: rName }
             var roleAdd = {
                 url: ma.host + "role/uniqCheckName",
                 data: param,
                 done: function(data) {

                 },
                 fail: function(re) {
                     layer.error("角色名已存在");
                 }
             }
             ma.ajax(roleAdd);
        });

         $("input[name='roleTypeValue']").on("blur", function() {
             var rtValue = $("input[name='roleTypeValue']").val();
             var param = { roleTypeValue: rtValue }
             var roleAdd = {
                 url: ma.host + "role/uniqCheck",
                 data: param,
                 done: function(data) {

                 },
                 fail: function(re) {
                     layer.error("角色CODE已存在");
                 }
             }
             ma.ajax(roleAdd);
         });

         form.on("checkbox", function(data) {
             var dom = data.elem;
             changeChild(dom);
             changeParent(dom);
             form.render("checkbox");
         });
     }

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
         if (!parent) {
             return
         }
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