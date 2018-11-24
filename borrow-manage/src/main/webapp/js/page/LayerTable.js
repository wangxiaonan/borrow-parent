/**
 *project JS main entrance
 *for example rely on  Layui'layer and form
 *userlist && pass modify  page
 *
 **/
layui.use(["grid"], function() {
    var grid = layui.grid;
    seachFun();
    function createTable(data){
        var table = grid.createNew({
            elem: 'creditTable',
            view: 'view',
            data:{"rows": data || [] },
            method:"post",
            pageSize: 10,
            singleSelect: true
        });
        return table;
    }
    function seachFun(){
        var pCode = ma.getParameterByName('pCode');
        var boPrice = ma.getParameterByName('boPrice');
        var searchData = {
            url: ma.host+"/borrow/repayplan/cal",
            data: {
                "pCode": pCode,
                "boPrice": boPrice
            },
            done: function(res) {
                if(res.errorCode!=='0000000'){
                    top.layer.success("成功");
                    return;
                };
                $('#boPrice').html(res.data.boPrice)
                $('#boExpect').html(res.data.boExpect)
                $('#gpsCost').html(res.data.gpsCost)
                $('#earlyServiceRate').html(res.data.earlyServiceRate*100+"%")
                $('#boFinishPrice').html(res.data.boFinishPrice)
                createTable(res.data.repayPlans).build()

            },
            fail: function(res) {

            }
        }
        ma.ajax(searchData);
    }

});
