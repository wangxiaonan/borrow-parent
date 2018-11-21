layui.use(["grid"], function() {
    var grid = layui.grid;
    seachFun();
        function createTable(data){
            var gridTable = grid.createNew({
                elem: 'loanProductTable',
                view: 'view',
                data:{"rows": data || [] },
                method:"post",
                pageSize: 10,
                singleSelect: true
            });
        return gridTable
    }
    function seachFun(){
        var searchData = {
            url: ma.host+"/borrow/product/sel",
            done: function(res) {
                if(res.errorCode!=='0000000') {
                    top.layer.success("查询失败");
                    return;
                }
                createTable(res.data).build()

            },
            fail: function(res) {

            }
        }
        ma.ajax(searchData);
    }
});
