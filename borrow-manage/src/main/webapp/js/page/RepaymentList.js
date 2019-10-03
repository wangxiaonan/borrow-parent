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
        laydate = layui.laydate,
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
    //dataPecker();
    form.on('submit(searchCredit)', function(data) {
        formData=data.field;
        seachFun(formData);
        return false;
    })

    function seachFun(param){
        var searchData = {
            url: ma.host+"/borrow/repayment/sel",
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
    function dataPecker(){
        var start = {
            max: laydate.now(),
            format: 'YYYY-MM-DD',
            istoday: false,
            choose: function(datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
                console.log(datas)
            }
        };

        var end = {
            max: laydate.now(),
            format: 'YYYY-MM-DD',
            istoday: false,
            choose: function(datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };

        document.getElementById('LAY_demorange_s').onclick = function() {
            start.elem = this;
            laydate(start);
        }
        document.getElementById('LAY_demorange_e').onclick = function() {
            end.elem = this
            laydate(end);
        }
    }
    function confirm(param,url){
        var searchData = {
            url: ma.host+url,
            data: {"orderId":param.orderId,"repayId":param.repayId },
            done: function(res) {
                if(res.errorCode!=='0000000'){
                    top.layer.error(res.errorMessage);
                    return;
                };
                layer.success("操作成功!");
                var postData = formData
                seachFun(postData);
            }
        }
        ma.ajax(searchData);
    }
    fn.confirm=function(){
        var row = gridTable.getRow();
        layer.confirm({
            content: "还款确认？",
            yes: function(index) {
                confirm(row,'/order/repay/over');
                layer.close(index);
            },
            no: function() {
                // layer.error("取消确定还款了哦!")
            }
        })
    };

    fn.suretyconfirm=function(){
        var row = gridTable.getRow();
        layer.confirm({
            content: "请确认借款人未按时还款，才可进行垫付操作",
            yes: function(index) {
                confirm(row,'/order/repay/surety');
                layer.close(index);
            },
            no: function() {
                // layer.error("取消确定还款了哦!")
            }
        })
    };
    fn.overdueReduce=function(){
        var row = gridTable.getRow();
        getOverdueReduce(row);
        layer.dialog({
            title: '逾期减免',
            area: ['100%', '100%'],
            content: $("#dialogoOverdueReduce")
        })
    };

    fn.waitOverdueReduce=function(){


        var actualPunishAmount = $('#actualPunishAmount').html();
        var punishAmount = $('#punishAmount').val();
        if (!(parseFloat("0") <= parseFloat(punishAmount) && parseFloat(punishAmount) <= parseFloat(actualPunishAmount))) {
            layer.alert("违约金最小金额是0 最大金额不能超过 应还金额");
            return;
        }
        var actualFineAmount = $('#actualFineAmount').html();
        var fineAmount = $('#fineAmount').val();
        if (!(parseFloat("0") <= parseFloat(fineAmount) && parseFloat(fineAmount) <= parseFloat(actualFineAmount))) {
            layer.alert("罚息最小金额是0 最大金额不能超过 应还金额");
            return;
        }
        layer.confirm({
            content: "确定",
            yes: function(index) {
                var row = gridTable.getRow();
                var addOverdueReduce = {
                    url: ma.host+"/order/overdue/addReduce",
                    data: {"orderId":row.orderId,"repayId":row.repayId,"punishAmount":punishAmount,"fineAmount":fineAmount },
                    done: function(res) {
                        top.layer.success("修改成功");
                        layer.closeAll();
                        self.location.reload();
                    },
                    fail: function(re) {
                        layer.error(re.errorMessage);
                        layer.hideLoad();
                    }
                }
                ma.ajax(addOverdueReduce);
            },
            no: function() {
            }
        })
    };

    fn.cannelOverdueReduce=function(){
        layer.closeAll();
    };

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
    function getOverdueReduce(param){
        var getReduce = {
            url: ma.host+"/order/overdue/reduce",
            data: {"repayId":param.repayId },
            done: function(res) {
                if(res.errorCode!=='0000000'){
                    top.layer.success("获取失败");
                    return;
                };
                $('#totalPunishAmount').html(res.data.totalPunishAmount);
                $('#reducePunishAmount').html(res.data.reducePunishAmount);
                $('#actualPunishAmount').html(res.data.actualPunishAmount);

                $('#totalFineAmount').html(res.data.totalFineAmount);
                $('#reduceFineAmount').html(res.data.reduceFineAmount);
                $('#actualFineAmount').html(res.data.actualFineAmount);
                createOverdueReduceTable(res.data.overdueReduceRecords,'secoundOverdueReduceRecord','secoundOverdueReduceRecordView');
            }
        }
        ma.ajax(getReduce);
    }
    function createOverdueReduceTable(data,ele,view){
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
