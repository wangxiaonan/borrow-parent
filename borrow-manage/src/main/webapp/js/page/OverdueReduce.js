/**
 *project JS main entrance
 *for example rely on  Layui'layer and form
 *userlist && pass modify  page
 *
 **/
var fn = {};
layui.use(["form", "grid", "layer",'laypage','laydate'], function() {
    var layer = layui.layer,
        grid = layui.grid,
        gridTable,
        laypage = layui.laypage,
        formData={},
        form = layui.form();
    function createTable(data){
        gridTable = grid.createNew({
            elem: 'creditTable',
            view: 'view',
            data:{"rows": data || [] },
            method:"post",
            pageSize: 10,
            singleSelect: true
        });
        return gridTable
    }
    //不带参数初始化一次，把所有的数据展示出来
    createTable();
    seachFun();
    form.on('submit(searchCredit)', function(data) {
        formData=data.field;
        seachFun(formData);
        return false;
    })

    function seachFun(param){
        var searchData = {
            url: ma.host+"/borrow/reduce/sel",
            data: param || {},
            done: function(res) {
                if(res.errorCode!=='0000000'){
                    top.layer.success("查询失败");
                    return;
                }
                initLayPage(res.data)
                createTable(res.data.rows).build()

            },
            fail: function(res) {

            }
        }
        ma.ajax(searchData);
    }
    function initLayPage(data){
        laypage({
            cont: 'laypage',
            pages: data.pages,  //分页数量
            curr: data.pageNum,  //当前页
            jump: function (obj, first) {  //点击分页触发回调
                if (!first) {
                    var curr = obj.curr;
                    formData.pageNo = curr;
                    var postData = formData
                    seachFun(postData);
                }

            }
        });
    }
});
