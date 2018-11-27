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
        form.on('submit(searchCredit)', function(data) {
            formData=data.field;
            seachFun(formData);
            return false;
            })

        //导出
        form.on('submit(searchCreditExport)', function (data) {
            window.open(ma.host + '/borrow/order/sel/export?' + layui.parseQuery(data.field));
        });
        function seachFun(param){
            var searchData = {
                    url: ma.host+"/borrow/order/sel",
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

            document.getElementById('createTimeStart').onclick = function() {
                start.elem = this;
                laydate(start);
            }
            document.getElementById('createTimeEnd').onclick = function() {
                end.elem = this
                laydate(end);
            }
        }
        function getDetailData(param){
            var getDetail = {
                    url: ma.host+"/borrow/order/sel/detail",
                    data: {"orderId": param.orderId},
                    done: function(res) {
                        if(res.errorCode!=='0000000'){
                            top.layer.success("获取失败");
                            return;
                        }

                        $('#industry').html(res.data.industry);
                        $('#workNature').html(res.data.workNature);
                        $('#userEarns').html(res.data.userEarns);
                        $('#creditDec').html(res.data.creditDec);
                        $('#boPaySource').html(res.data.boPaySource);
                        createSpan(res.data.auditkeys)
                    }
                }
                ma.ajax(getDetail);
        }
        function createSpan(arr){
            var auditkeysArr = [
                    "AUTH_IDCARD",
                    "AUTH_CARPHOTO",
                    "VEHICLE_LICENSE",
                    "APPLY_TABLE",
                    "MORTGAGE",
                    "AUTH_OTHER"]
            for(j in auditkeysArr){
                var flag =false;
                for(var i=0; i<arr.length;i++){
                    if(arr[i] == auditkeysArr[j]){
                        flag = true;
                    }
                }
                if (flag) {
                    $('#'+auditkeysArr[j]).prop("checked", true);
                }else {
                    $('#'+auditkeysArr[j]).prop("checked", false);
                }
                form.render("checkbox");
            }

        }
        function confirm(orderId,url){
            var searchData = {
                url: ma.host+url,
                data: {"orderId":orderId },
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
        fn.detail=function(){
            var row = gridTable.getRow();
            getDetailData(row);

            layer.dialog({
                title: '详情',
                area: ['100%', '100%'],
                content: $("#dialog1")
            })
        }
        fn.confirm=function(){
            var orderId = gridTable.getRow().orderId;
            layer.confirm({
                content: "确定筹标？",
                yes: function(index) {
                    confirm(orderId,'/make/raise');
                    layer.close(index);
                },
                no: function() {
                    // layer.error("取消放款了哦!")
                }
            })
        };
        fn.cancel=function(){
            var orderId = gridTable.getRow().orderId;
            layer.confirm({
                content: "取消放款？",
                yes: function(index) {
                    confirm(orderId,'/order/cancel');
                    layer.close(index);
                },
                no: function() {
                    // layer.error("取消放款了哦!")
                }
            })
        };
        function createPlanTable(data){
            grid.createNew({
                elem: 'planTable',
                view: 'planView',
                data:{"rows": data || [] },
                method:"post",
                pageSize: 10,
                singleSelect: true
            }).build();
        }
        function getPlanData(orderId){
            var planData = {
                url: ma.host+'/order/repay/plan',
                data: {"orderId":orderId },
                done: function(res) {
                    if(res.errorCode!='0000000'){
                        top.layer.error(res.errorMessage);
                        return;
                    };
                    $('#boPrice').html(res.data.boPrice)
                    $('#boExpect').html(res.data.boExpect)
                    $('#gpsCost').html(res.data.gpsCost)
                    $('#earlyServiceRate').html(res.data.earlyServiceRate*100+"%")
                    $('#boFinishPrice').html(res.data.boFinishPrice)
                    createPlanTable(res.data.repayPlans);
                }
            }
            ma.ajax(planData);
        }
        fn.plan=function(){
            var orderId = gridTable.getRow().orderId;
            getPlanData(orderId);
            layer.dialog({
                area: ['100%', '100%'],
                title:"还款计划",
                content: $("#dialog")
            });
        }

});
