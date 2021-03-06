/**
 *project JS main entrance
 *for example rely on  Layui'layer and form
 *userlist && pass modify  page
 *
 **/
var fn = {};
layui.use(["form", "grid", "layer",'laypage'], function() {
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
    //导出
    form.on('submit(searchRepayCreditExport)', function (data) {
        window.open(ma.host + '/borrow/pay/sel/export?' + layui.parseQuery(data.field));
    });
    function seachFun(param){
        var searchData = {
            url: ma.host+"/borrow/pay/sel",
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
    function getDetailData(param){
        var getDetail = {
            url: ma.host+"/order/repay/detail",
            data: {"orderId": param.orderId},
            done: function(res) {
                if(res.errorCode!=='0000000'){
                    top.layer.success("获取失败");
                    return;
                };
                $('#orderId').html(res.data.orderId);
                $('#userName').html(res.data.userName);
                $('#plateNumber').html(res.data.plateNumber);
                $('#boPrice').html(res.data.boPrice);
                $('#productName').html(res.data.productName);
                $('#boExpect').html(res.data.boExpect);
                createDetailTable(res.data.repayDetails,'secoundDetail','seccoundView').build();
                createDetailPayTable(res.data.orderPayRecords,'secoundDetailPay','secoundDetailPayView');
            }
        }
        ma.ajax(getDetail);
    }
    var detailGridTable;
    function createDetailTable(data,ele,view){
        detailGridTable = grid.createNew({
            elem: ele,
            view: view,
            data:{"rows": data || [] },
            method:"post",
            pageSize: 100,
            singleSelect: true
        })
        return detailGridTable

    }
    function createDetailPayTable(data,ele,view){
        grid.createNew({
            elem: ele,
            view: view,
            data:{"rows": data || [] },
            method:"post",
            pageSize: 100,
            singleSelect: true
        }).build();
    }

    //获取提前还款需要的数据
    function getRepayCal(param){
        var RepayCalData = {
            url: ma.host+"/order/up/repay/cal",
            data: {"orderId": param.orderId,repayId:param.repayId},
            done: function(res) {
                if(res.errorCode!=='0000000'){
                    top.layer.success("获取失败");
                    return;
                };
                $('#dpayTotalAmount').html(res.data.payTotalAmount);
                $('#dexpectTotal').html(res.data.expectTotal);
                $('#dpayExpect').html(res.data.payExpect);
                $('#dfinishExpect').html(res.data.finishExpect);
                $('#duserName').html(res.data.userName);
                // $('#repatDetail').html('还款本金'+res.data.payTotalCapitalAmount+'，利息'+res.data.payTotalInterestAmount+'，服务费'+res.data.payTotalServiceFee+'，提前结清费用'+res.data.earlyPayCostAmount);
            }
        }
        ma.ajax(RepayCalData);
    }
    //提前还款提交数据
    function submitFun(param){
        var submitData = {
            url: ma.host+"/order/up/repay",
            data: param || {},
            done: function(res) {
                if(res.errorCode!=='0000000'){
                    top.layer.success("获取失败");
                    return;
                }
                layer.success('提交成功');
                var postData = formData
                seachFun(postData);
            }
        }
        ma.ajax(submitData);
    }
    fn.detail=function(){
        var row = gridTable.getRow();
        getDetailData(row);
        layer.dialog({
            title: '详情',
            area: ['100%', '100%'],
            content: $("#dialogDetail")
        })
    };

    fn.AdvanceRepatment=function(){
        var row = gridTable.getRow();
        getRepayCal(row);
        layer.dialog({
            type:1,
            width:'500px',
            title:'提前还款',
            content: $("#dialog")
        })
    };
    $('#submitBtn').on('click',function(){
        var row = gridTable.getRow();
        var upPayType = $('input[name="upPayType"]:checked').val();
        var payAmount = $('#payAmount').val();
        if(upPayType == 2 && !payAmount){
            layer.error('手动填写还款额不能为空');
            return;
        }
        var submitData = {"orderId":row.orderId,upPayType:upPayType,payAmount:payAmount};
        submitFun(submitData);
        layer.closeAll()
    })

    initProduct();
    function initProduct(){
        var produ = {
            url: ma.host+"/borrow/product/sel",
            data: {},
            done: function(res) {
                var data = res.data;
                for (var i =0;i< data.length;i++) {
                    // document.getElementById("productCode").options.add(new Option(data[i].productName,data[i].productCode));
                    $("#pCode").append('<option value="' + data[i].productCode + '">' + data[i].productName + '</option>');

                    form.render('select');
                }
            },
            fail: function(re) {
                layer.error(re.errorMessage);
            }
        }
        ma.ajax(produ);
    }

    fn.selReceivable=function(){
        var row = detailGridTable.getRow();
        getReceivableList(row);
        layer.dialog({
            title: '出借人明细',
            area: ['100%', '100%'],
            content: $("#dialogoselReceivableList")
        })
    };

    function getReceivableList(param) {
        var getReceivable = {
            url: ma.host + "/order/receivable/sel",
            data: {"repayId": param.repayId},
            done: function (res) {
                if (res.errorCode !== '0000000') {
                    top.layer.success("获取失败");
                    return;
                }
                ;
                createReceivableListTable(res.data, 'secoundReceivableList', 'secoundReceivableListView');
            }
        }
        ma.ajax(getReceivable);
    }
    function createReceivableListTable(data,ele,view){
        grid.createNew({
            elem: ele,
            view: view,
            data:{"rows": data || [] },
            method:"post",
            pageSize: 100,
            singleSelect: true
        }).build();
    }

});
