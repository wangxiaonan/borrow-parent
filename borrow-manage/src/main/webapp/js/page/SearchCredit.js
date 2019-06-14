/**
 *project JS main entrance
 *for example rely on  Layui'layer and form
 *userlist && pass modify  page
 *
 **/
$.cxSelect.defaults.url ='../../js/lib/cxSelect/cityData.min.json';
var cxSelectApi;
$('#selectAdd').cxSelect({
    selects: ['province', 'city', 'area'],
    nodata: 'none',
    required:false
}, function(api) {
    cxSelectApi = api;
});

var fn = {};
layui.use(["form", "grid", "layer", 'laypage', 'laydate',"upload"], function () {
    var layer = layui.layer,
        grid = layui.grid,
        gridTable,
        laypage = layui.laypage,
        formData = {},
        laydate = layui.laydate,
        form = layui.form();

    // 公共的图片
    var tempImg = null;
    var tempImgkey = null;
    var tempClose = null;  //图片关闭按钮显示
    var fileNames = {};
    var bigPic = null;
    var bussType = 1;   //默认车贷

    form.on('select(province)', function(data){
        $(".area").val("").siblings().find(".layui-unselect").val("").parent().siblings().html("");
        cxSelectApi.setOptions({selects: ['province', 'city', 'area']});
        form.render('select');
    });
    //第二个选中的是初始化数据
    form.on('select(city)', function(data){
        cxSelectApi.setOptions({selects: ['province', 'city', 'area']});

        form.render('select');
    });
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
    form.on('submit(orderUpdate)', function(data) {
        var formData=data.field;
        var auditkeys = [];
        for(i in formData){
            if(i.indexOf('auditkeys')>-1){
                auditkeys.push(formData[i])
            }
        };
        var  province  = formData.province;
        var  city  = formData.city;
        var  area  = formData.area;
        var address = formData.houseAddress;
        var houseAddressDetail = province +" "+ city + " "+ area+ " "+address;


        var SubmitData={

            "orderId":formData.orderIdDetail,
            "bussType":formData.bussTypeDetail,
            "boPaySource":formData.boPaySourceDetail,
            "boSource":formData.boSourceDetail,
            "auditkeys" : auditkeys,
            "userInfo": {
                "userEarns": formData.userEarnsDetail,
                "idcard": formData.idcardDetail,
                "mobile": formData.mobileDetail,
                "sex": formData.sexDetail,
                "userName": formData.userNameDetail,
                "marriage": formData.marriageDetail,
                "children": formData.childrenDetail,
                "workNature": formData.workNatureDetail,
                "userDebts": formData.userDebtsDetail,
                "userAssure": formData.userAssureDetail,
            },
            "userCarItemVo": {
                "plateNumber": formData.plateNumberDetail,
                "signTimeStr": formData.carDateDetail,
                "carModel": formData.carModelDetail,
                "carColor": formData.carColorDetail,
                "assessmentPrice": formData.assessmentPriceDetail,
                "mileageDesc": formData.mileageDescDetail,
                "authIdcardUrl": $("#authIdcardUrl")[0].src,
                "vehicleLicenseUrl": $("#vehicleLicenseUrl")[0].src,
                "pollingLicenseUrl": $("#pollingLicenseUrl")[0].src,
                "carSkinUrl": $("#carSkinUrl")[0].src,
                "insurancePolicyUrl": $("#insurancePolicyUrl")[0].src,
                "letterCommitmentUrl": $("#letterCommitmentUrl")[0].src,
                "authOtherUrl": $("#authOtherUrl")[0].src
            },
            "borrowSalesman": {
                "salesName": formData.salesNameDetail,
                "salesMobile": formData.salesMobileDetail
            },
            "orderAudit": {
                "auditkeys": auditkeys
            },
            "userHouseInfo" : {
                "houseName": formData.houseNameDetail,
                "housePart": formData.housePartDetail,
                "houseNum": formData.houseNumDetail,
                "houseArea": formData.houseAreaDetail,
                "houseAttr": formData.houseAttrDetail,
                "houseAddress": houseAddressDetail,
                "houseDate": formData.houseDateDetail,
                "housePrice": formData.housePriceDetail,
                "houseidcardPicUrl": $("#houseidcardPicUrl")[0].src,
                "housePicUrl": $("#housePicUrl")[0].src,
                "houseAuthorityCardPicUrl": $("#houseAuthorityCardPicUrl")[0].src,
                "houseGuaranteePicUrl": $("#houseGuaranteePicUrl")[0].src,
                "houseLetterCommitmentPicUrl": $("#houseLetterCommitmentPicUrl")[0].src,
                "houseAuthOtherPicurl": $("#houseAuthOtherPicurl")[0].src
            }
        }
        var updateUser = {
            url: ma.host+"/borrow/order/update",
            data: SubmitData,
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
        layer.showLoad();
        ma.ajax(updateUser);
        return false;
    });
    //图片删除
    // $('.close').click(function () {
    //     var key = $(this).parents('.layui-input-block').find('input').data('key');
    //     delete fileNames[key];
    //     // $(this).hide();
    //     $(this).parents('.layui-input-block').find('img').attr('src','');
    //     // $(this).parents('.layui-input-block').find('img').hide();
    // });

    function getDetailData(param) {
        var getDetail = {
            url: ma.host + "/borrow/order/sel/detail",
            data: {"orderId": param.orderId},
            done: function (res) {
                if (res.errorCode !== '0000000') {
                    top.layer.success("获取失败");
                    return;
                }
                bussType = res.data.bussType;

                $('#orderIdDetail').val(param.orderId);
                $('#bussTypeDetail').val(bussType);

                //动态切换
                changeInputType(bussType);
                $('#userNameDetail').val(res.data.userInfo.userName);
                $('#idcardDetail').val(res.data.userInfo.idcard);
                $('#mobileDetail').val(res.data.userInfo.mobile);
                $("input[name='sexDetail'][value='"+res.data.userInfo.sex+"']").attr("checked",true);
                $("input[name='marriageDetail'][value='"+res.data.userInfo.marriage+"']").attr("checked",true);

                $('#childrenDetail').val(res.data.userInfo.children);
                $('#workNatureDetail').val(res.data.userInfo.workNature);
                $('#userEarnsDetail').val(res.data.userInfo.userEarns);
                $('#userDebtsDetail').val(res.data.userInfo.userDebts);
                $('#userAssureDetail').val(res.data.userInfo.userAssure);
                $('#boPaySourceDetail').val(res.data.boPaySource);
                $('#salesNameDetail').val(res.data.borrowSalesman.salesName);
                $('#salesMobileDetail').val(res.data.borrowSalesman.salesMobile);
                $('#boPriceDetail').val(res.data.boPrice);
                $('#productNameDetail').val(res.data.productName);
                $('#boSourceDetail').val(res.data.boSource);
                form.render('radio');
                if (2 == bussType)  {
                    $('#houseNameDetail').val(res.data.userHouseInfo.houseName);
                    $('#housePartDetail').val(res.data.userHouseInfo.housePart);
                    $('#houseNumDetail').val(res.data.userHouseInfo.houseNum);
                    $('#houseAreaDetail').val(res.data.userHouseInfo.houseArea);
                    $('#houseAttrDetail').val(res.data.userHouseInfo.houseAttr);

                    var address = res.data.userHouseInfo.houseAddress.split(" ");
                    $("#province").find("option[value='"+address[0] +"']").prop("selected",true);
                    cxSelectApi.setOptions({selects: ['province', 'city', 'area']});

                    $("#city").find("option[value='"+address[1] +"']").prop("selected",true);
                    cxSelectApi.setOptions({selects: ['province', 'city', 'area']});

                    $("#area").find("option[value='"+address[2] +"']").prop("selected",true);
                    $('#houseAddress').val(address[3]);
                    form.render();


                    $('#houseAddressDetail').val(res.data.userHouseInfo.houseAddress);
                    $('#houseDateDetail').val(res.data.userHouseInfo.houseDate);
                    $('#housePriceDetail').val(res.data.userHouseInfo.housePrice);

                    if(!$.isEmptyObject(res.data.userHouseInfo.houseidcardPicUrl)) {
                        $("#houseidcardPicUrl").attr({"src":res.data.userHouseInfo.houseidcardPicUrl});
                        $("#houseidcardPicUrl").show();
                        tempClose = $("#houseidcardPicUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[houseidcardPicUrl] = res.data.userHouseInfo.houseidcardPicUrl;
                        //预览大图
                        $("#houseidcardPicUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#houseidcardPicUrl").removeAttr("src");
                        });

                    }

                    if(!$.isEmptyObject(res.data.userHouseInfo.housePicUrl)) {
                        $("#housePicUrl").attr({"src":res.data.userHouseInfo.housePicUrl});
                        $("#housePicUrl").show();
                        tempClose = $("#housePicUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[housePicUrl] = res.data.userHouseInfo.housePicUrl;
                        //预览大图
                        $("#housePicUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#housePicUrl").removeAttr("src");
                        });
                    }
                    if(!$.isEmptyObject(res.data.userHouseInfo.houseAuthorityCardPicUrl)) {
                        $("#houseAuthorityCardPicUrl").attr({"src":res.data.userHouseInfo.houseAuthorityCardPicUrl});
                        $("#houseAuthorityCardPicUrl").show();
                        tempClose = $("#houseAuthorityCardPicUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[housePicUrl] = res.data.userHouseInfo.houseAuthorityCardPicUrl;
                        //预览大图
                        $("#houseAuthorityCardPicUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#houseAuthorityCardPicUrl").removeAttr("src");
                        });
                    }


                    if(!$.isEmptyObject(res.data.userHouseInfo.houseGuaranteePicUrl)) {
                        $("#houseGuaranteePicUrl").attr({"src":res.data.userHouseInfo.houseGuaranteePicUrl});
                        $("#houseGuaranteePicUrl").show();
                        tempClose = $("#houseGuaranteePicUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[housePicUrl] = res.data.userHouseInfo.houseGuaranteePicUrl;
                        //预览大图
                        $("#houseGuaranteePicUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#houseGuaranteePicUrl").removeAttr("src");
                        });

                    }
                    if(!$.isEmptyObject(res.data.userHouseInfo.houseLetterCommitmentPicUrl)) {
                        $("#houseLetterCommitmentPicUrl").attr({"src":res.data.userHouseInfo.houseLetterCommitmentPicUrl});
                        $("#houseLetterCommitmentPicUrl").show();
                        tempClose = $("#houseLetterCommitmentPicUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[housePicUrl] = res.data.userHouseInfo.houseLetterCommitmentPicUrl;
                        //预览大图
                        $("#houseLetterCommitmentPicUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#houseLetterCommitmentPicUrl").removeAttr("src");
                        });
                    }
                    if(!$.isEmptyObject(res.data.userHouseInfo.houseAuthOtherPicurl)) {
                        $("#houseAuthOtherPicurl").attr({"src":res.data.userHouseInfo.houseAuthOtherPicurl});
                        $("#houseAuthOtherPicurl").show();
                        tempClose = $("#houseAuthOtherPicurl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[housePicUrl] = res.data.userHouseInfo.houseAuthOtherPicurl;
                        //预览大图
                        $("#houseAuthOtherPicurl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#houseAuthOtherPicurl").removeAttr("src");
                        });
                    }

                }else if (1 == bussType) {
                    $('#carModelDetail').val(res.data.userCarItemVo.carModel);
                    $("#carColorDetail").find("option[value='"+res.data.userCarItemVo.carColor +"']").prop("selected",true);
                    form.render();
                    $('#carDateDetail').val(res.data.userCarItemVo.signTimeStr);
                    $('#assessmentPriceDetail').val(res.data.userCarItemVo.assessmentPrice);
                    $('#plateNumberDetail').val(res.data.userCarItemVo.plateNumber);
                    $('#mileageDescDetail').val(res.data.userCarItemVo.mileageDesc);

                    if(!$.isEmptyObject(res.data.userCarItemVo.authIdcardUrl)) {
                        $("#authIdcardUrl").attr({"src":res.data.userCarItemVo.authIdcardUrl});
                        $("#authIdcardUrl").show();
                        tempClose = $("#authIdcardUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[authIdcardUrl] = res.data.userCarItemVo.authIdcardUrl;
                        //预览大图
                        $("#authIdcardUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#authIdcardUrl").removeAttr("src");
                        });
                    }
                    if(!$.isEmptyObject(res.data.userCarItemVo.vehicleLicenseUrl)) {
                        $("#vehicleLicenseUrl").attr({"src":res.data.userCarItemVo.vehicleLicenseUrl});
                        $("#vehicleLicenseUrl").show();
                        tempClose = $("#vehicleLicenseUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[vehicleLicenseUrl] = res.data.userCarItemVo.vehicleLicenseUrl;
                        //预览大图
                        $("#vehicleLicenseUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#vehicleLicenseUrl").removeAttr("src");
                        });
                    }
                    if(!$.isEmptyObject(res.data.userCarItemVo.pollingLicenseUrl)) {
                        $("#pollingLicenseUrl").attr({"src":res.data.userCarItemVo.pollingLicenseUrl});
                        $("#pollingLicenseUrl").show();
                        tempClose = $("#pollingLicenseUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[pollingLicenseUrl] = res.data.userCarItemVo.pollingLicenseUrl;
                        //预览大图
                        $("#pollingLicenseUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#pollingLicenseUrl").removeAttr("src");
                        });
                    }
                    if(!$.isEmptyObject(res.data.userCarItemVo.carSkinUrl)) {
                        $("#carSkinUrl").attr({"src":res.data.userCarItemVo.carSkinUrl});
                        $("#carSkinUrl").show();
                        tempClose = $("#carSkinUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[carSkinUrl] = res.data.userCarItemVo.carSkinUrl;
                        //预览大图
                        $("#carSkinUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#carSkinUrl").removeAttr("src");
                        });
                    }
                    if(!$.isEmptyObject(res.data.userCarItemVo.insurancePolicyUrl)) {
                        $("#insurancePolicyUrl").attr({"src":res.data.userCarItemVo.insurancePolicyUrl});
                        $("#insurancePolicyUrl").show();
                        tempClose = $("#insurancePolicyUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[insurancePolicyUrl] = res.data.userCarItemVo.insurancePolicyUrl;
                        //预览大图
                        $("#insurancePolicyUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#insurancePolicyUrl").removeAttr("src");
                        });
                    }

                    if(!$.isEmptyObject(res.data.userCarItemVo.letterCommitmentUrl)) {
                        $("#letterCommitmentUrl").attr({"src":res.data.userCarItemVo.letterCommitmentUrl});
                        $("#letterCommitmentUrl").show();
                        tempClose = $("#letterCommitmentUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[letterCommitmentUrl] = res.data.userCarItemVo.letterCommitmentUrl;
                        //预览大图
                        $("#letterCommitmentUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#letterCommitmentUrl").removeAttr("src");
                        });
                    }

                    if(!$.isEmptyObject(res.data.userCarItemVo.authOtherUrl)) {
                        $("#authOtherUrl").attr({"src":res.data.userCarItemVo.authOtherUrl});
                        $("#authOtherUrl").show();
                        tempClose = $("#authOtherUrl").parents('.layui-input-block').find('.close');
                        $(tempClose).show();
                        fileNames[authOtherUrl] = res.data.userCarItemVo.authOtherUrl;
                        //预览大图
                        $("#authOtherUrl").click(function () {
                            bigPic = $(this).attr('src');
                            viewBigPic(bigPic);
                        });
                        $(tempClose).click(function () {
                            $("#authOtherUrl").removeAttr("src");
                        });
                    }

                }
                createSpan(res.data.auditkeys);

            }
        }
        ma.ajax(getDetail);
    }

    function createSpan(arr) {
        var auditkeysArr = [
            "AUTH_IDCARD",
            "AUTH_CARPHOTO",
            "VEHICLE_LICENSE",
            "POLLING_LICENSE",
            "INSURANCE_POLICY",
            "LETTER_COMMITMENT",
            "VIOLATION_RECORD",
            "ADDRESS_LICENSE",
            "CAR_SKIN",
            "CAR_MILEAGE",
            "CAR_MOTOR",
            "CAR_NUMBER",
            "APPLY_TABLE",
            "CREDIT_REPORTING",
            "HOUSE_PIC",
            "AUTHORITY_CARD",
            "AUTH_OTHER"
            ]
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

    layui.upload({
        url:ma.host+"/uploadFile",
        ext: 'jpg|png|gif',
        name:"file",
        before:function(input){
            tempImg = $(input).parents('.layui-input-block').find('img');
            tempImgkey = $(input).data("key");
            tempClose = $(input).parents('.layui-input-block').find('.close');
            $(tempClose).show();

        },
        success:function (res) {
            $(tempImg).attr({"src":res.data});
            fileNames[tempImgkey] = res.data;
            console.log(fileNames);

            //预览大图
            $(tempImg).click(function () {
                bigPic = $(this).attr('src');
                viewBigPic(bigPic);
            });

            // $('#uploadIdcard').text();

            // var url = $('#uploadIdcard').text();
            // aler(url)
            top.layer.success("上传成功");
            // console.log(res);
            // $(tempImg).attr('src',res.src);
            $(tempImg).show();  //上传成功要显示

        }
    });

    //预览大图
    function viewBigPic(imgsrc){
        if(imgsrc){
            $('#bigPic').show();
            $('#bigPic img').attr('src',imgsrc);
        }
    }

    //关闭预览大图
    $('#bigPic .bg').click(function () {
        $('#bigPic').hide();
    });

    //图片删除
    $('.close').click(function () {
        var key = $(this).parents('.layui-input-block').find('input').data('key');
        delete fileNames[key];
        $(this).parents('.layui-input-block').find('img').removeAttr('src');
        $(this).parents('.layui-input-block').find('img').hide();
    });

    //动态表单类型切换
    function changeInputType(type) {
        if(type == 1){
            $('.form-house').hide();
            $('.form-car').show();
        }else {
            $('.form-car').hide();
            $('.form-house').show();
        }
    }


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
