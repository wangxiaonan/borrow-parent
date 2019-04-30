/**
 *project JS main entrance
 *for example rely on  Layui'layer and form
 *userlist && pass modify  page
 *
 **/
var fn = {};
layui.use(["form", "grid", "layer", 'laypage', 'laydate'], function () {
    var layer = layui.layer,
        grid = layui.grid,
        gridTable,
        laypage = layui.laypage,
        formData = {},
        laydate = layui.laydate,
        form = layui.form();

    function createTable(data) {
        gridTable = grid.createNew({
            elem: 'creditTable',
            view: 'view',
            data: {"rows": data || []},
            method: "post",
            pageSize: 10,
            singleSelect: true
        });
        return gridTable
    }

    //不带参数初始化一次，把所有的数据展示出来
    createTable();
    seachFun();
    form.on('submit(searchCredit)', function (data) {
        formData = data.field;
        seachFun(formData);
        return false;
    })

    //导出
    form.on('submit(searchCreditExport)', function (data) {
        window.open(ma.host + '/borrow/order/sel/export?' + layui.parseQuery(data.field));
    });

    function seachFun(param) {
        var searchData = {
            url: ma.host + "/borrow/order/sel",
            data: param || {},
            done: function (res) {
                if (res.errorCode !== '0000000') {
                    top.layer.success("查询失败");
                    return;
                }
                initLayPage(res.data)
                createTable(res.data.rows).build()

            },
            fail: function (res) {

            }
        }
        ma.ajax(searchData);
    }

    function initLayPage(data) {
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

    function dataPecker() {
        var start = {
            max: laydate.now(),
            format: 'YYYY-MM-DD',
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
                console.log(datas)
            }
        };

        var end = {
            max: laydate.now(),
            format: 'YYYY-MM-DD',
            istoday: false,
            choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };

        document.getElementById('createTimeStart').onclick = function () {
            start.elem = this;
            laydate(start);
        }
        document.getElementById('createTimeEnd').onclick = function () {
            end.elem = this
            laydate(end);
        }
    }

    function getDetailData(param) {
        var getDetail = {
            url: ma.host + "/borrow/order/sel/detail",
            data: {"orderId": param.orderId},
            done: function (res) {
                if (res.errorCode !== '0000000') {
                    top.layer.success("获取失败");
                    return;
                }


                // <td id="childrenDesc"></td>
                //         <td id="workNature"></td>
                //         <td id="userEarns"></td>
                //         <td id="liabilitiesDesc"></td>
                //         <td id="guaranteeDesc"></td>
                //         <td id="boPaySource"></td>
                //         <td id="BO_SOURCE"></td>
                // <td id="carModel"></td>
                //         <td id="carColor"></td>
                //         <td id="signTime"></td>
                //         <td id="assessmentPrice"></td>
                //         <td id="plateNumber"></td>
                //         <td id="mileageDesc"></td>
                $('#childrenDesc').html(res.data.childrenDesc);
                $('#workNature').html(res.data.workNature);
                $('#userEarns').html(res.data.userEarns);
                $('#liabilitiesDesc').html(res.data.liabilitiesDesc);
                $('#guaranteeDesc').html(res.data.guaranteeDesc);
                $('#boPaySource').html(res.data.boPaySource);

                $('#carModel').html(res.data.carModel);
                $('#carColor').html(res.data.carColor);
                $('#signTime').html(res.data.signTimeDesc);
                $('#assessmentPrice').html(res.data.assessmentPrice);
                $('#plateNumber').html(res.data.plateNumber);
                $('#mileageDesc').html(res.data.mileageDesc);

                createSpan(res.data.auditkeys)
            }
        }
        ma.ajax(getDetail);
    }

    function createSpan(arr) {
        $("#images").html("");
        var images = "";

        var auditkeysArr = [
            "AUTH_IDCARD",
            "VEHICLE_LICENSE",
            "POLLING_LICENSE",
            "INSURANCE_POLICY",
            "VIOLATION_RECORD",
            "APPLY_TABLE",
            "AUTH_CARPHOTO",
            "ADDRESS_LICENSE",
            "CAR_SKIN",
            "CAR_MILEAGE",
            "CAR_MOTOR",
            "CAR_NUMBER",
            "AUTH_OTHER",
            "MORTGAGE",]
        for (j in auditkeysArr) {
            if (arr[auditkeysArr[j]] != undefined) {
                $('#' + auditkeysArr[j]).prop("checked", true);
                // images += "<label>" + $('#' + auditkeysArr[j]).attr("title") + "</label>" +
                //     "<image src = " + arr[auditkeysArr[j]] + "></image>";
                images +="<image src = " + arr[auditkeysArr[j]] + "></image>";
            }
            // if (flag) {
            // }else {
            //     $('#'+auditkeysArr[j]).prop("checked", false);
            // }
            $("#images").html(images);
            form.render("checkbox");
        }

    }

    function confirm(orderId, url) {
        var searchData = {
            url: ma.host + url,
            data: {"orderId": orderId},
            done: function (res) {
                if (res.errorCode !== '0000000') {
                    top.layer.error(res.errorMessage);
                    return;
                }
                ;
                layer.success("操作成功!");
                var postData = formData
                seachFun(postData);
            }
        }
        ma.ajax(searchData);
    }

    fn.detail = function () {
        var row = gridTable.getRow();
        getDetailData(row);

        layer.dialog({
            title: '详情',
            area: ['100%', '100%'],
            content: $("#dialog1")
        })
    }
    fn.confirm = function () {
        var orderId = gridTable.getRow().orderId;
        layer.confirm({
            content: "确定筹标？",
            yes: function (index) {
                confirm(orderId, '/make/raise');
                layer.close(index);
            },
            no: function () {
                // layer.error("取消放款了哦!")
            }
        })
    };
    fn.cancel = function () {
        var orderId = gridTable.getRow().orderId;
        layer.confirm({
            content: "取消放款？",
            yes: function (index) {
                confirm(orderId, '/order/cancel');
                layer.close(index);
            },
            no: function () {
                // layer.error("取消放款了哦!")
            }
        })
    };

    function createPlanTable(data) {
        grid.createNew({
            elem: 'planTable',
            view: 'planView',
            data: {"rows": data || []},
            method: "post",
            pageSize: 10,
            singleSelect: true
        }).build();
    }

    function getPlanData(orderId) {
        var planData = {
            url: ma.host + '/order/repay/plan',
            data: {"orderId": orderId},
            done: function (res) {
                if (res.errorCode != '0000000') {
                    top.layer.error(res.errorMessage);
                    return;
                }
                ;
                $('#boPrice').html(res.data.boPrice)
                $('#boExpect').html(res.data.boExpect)
                $('#gpsCost').html(res.data.gpsCost)
                $('#earlyServiceRate').html(res.data.earlyServiceRate * 100 + "%")
                $('#boFinishPrice').html(res.data.boFinishPrice)
                createPlanTable(res.data.repayPlans);
            }
        }
        ma.ajax(planData);
    }

    fn.plan = function () {
        var orderId = gridTable.getRow().orderId;
        getPlanData(orderId);
        layer.dialog({
            area: ['100%', '100%'],
            title: "还款计划",
            content: $("#dialog")
        });
    }

    initProduct();

    function initProduct() {
        var produ = {
            url: ma.host + "/borrow/product/sel",
            data: {},
            done: function (res) {
                var data = res.data;
                for (var i = 0; i < data.length; i++) {
                    // document.getElementById("productCode").options.add(new Option(data[i].productName,data[i].productCode));
                    $("#pCode").append('<option value="' + data[i].productCode + '">' + data[i].productName + '</option>');

                    form.render('select');
                }
            },
            fail: function (re) {
                layer.error(re.errorMessage);
            }
        }
        ma.ajax(produ);
    }

});
