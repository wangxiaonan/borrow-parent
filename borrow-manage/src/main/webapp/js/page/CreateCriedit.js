/**
 *project JS main entrance
 *for example rely on  Layui'layer and form
 *usermanege && pass modify  page
 *
 **/
layui.use(["form", "layer", "element","laydate","upload"], function() {
    var layer = layui.layer,

        form = layui.form();


    var tempImg = null;
    var tempImgkey = null;
    var tempClose = null;  //图片关闭按钮显示
    var fileNames = {};
    var bigPic = null;

    var bussType = 1;   //默认车贷

    layui.upload({
        url:ma.host+"/uploadFile",
        ext: 'jpg|png|gif',
        name:"file",
        before:function(input){
            tempImg = $(input).parents('.layui-input-inline').find('img');
            tempImgkey = $(input).data("key");
            tempClose = $(input).parents('.layui-input-inline').find('.close');
            $(tempClose).show();

            //预览大图
            // $(tempImg).click(function () {
            //     bigPic = $(this).attr('src');
            //     viewBigPic(bigPic);
            // });

            // //模拟上传成功，线上请删除
            // $(tempImg).attr('src','../../images/user.png');
            // $(tempImg).show();
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
            top.layer.success("上传成功");
        },
        fail: function(re) {
            layer.error(re.errorMessage);
            layer.hideLoad();
        }
    });


    //自定义校验规则
    form.verify({
        myrequired: function (val,item) {
            var visible = $(item).parents('.layui-form-item').find('.layui-form-label');
            if($(visible).is(':visible')){
                if(!val){
                    return '必填项不能为空';
                }
            }
        }
    });

    form.on('submit(orderAdd)', function(data) {
        var formData=data.field;
        var auditkeys = {};
        for(i in formData){
            if(i.indexOf('auditkeys')>-1){
                let key = formData[i];
                // let data = {};
                if (!fileNames[key]) {
                    auditkeys[key] = "";
                } else {
                    auditkeys[key] = fileNames[key];
                }

            }
        };
        var SubmitData={
            "userInfo": {
                "userEarns": formData.userEarns,
                "idcard": formData.idcard,
                "mobile": formData.mobile,
                "industry": formData.sex,
                "userName": formData.userName,
                "marriage": formData.marriage,
                "children": formData.children,
                "workNature": formData.workNature,
                "userEarns": formData.userEarns,
                "userDebts": formData.userDebts,
                "userAssure": formData.userAssure,
            },
            "orderAudit": {
                "auditkeys": auditkeys
            },
            "borrowSalesman": {
                "salesName": formData.salesName,
                "salesMobile": formData.salesMobile
            },
            "userCar": {
                "plateNumber": formData.plateNumber,
                "signTime": formData.signTime,
                "carModel": formData.carModel,
                "carColor": formData.carColor,
                "assessmentPrice": formData.assessmentPrice,
                "mileageDesc": formData.mileageDesc,
            },
            "borrowOrder": {
                "boPrice": formData.boPrice,
                "boPaySource": formData.boPaySource,
                "productCode": formData.productCode
            },
            "boOrderItem": {
                "boSource": formData.boSource
            }

        }
        var addUser = {
            url: ma.host+"/borrow/order/add",
            data: SubmitData,
            done: function(res) {
                top.layer.success("添加成功");
                self.location.reload();
            },
            fail: function(re) {
                layer.error(re.errorMessage);
                layer.hideLoad();
            }
        }
        layer.showLoad();
        ma.ajax(addUser);
        return false;
    });


    //图片删除
    $('.close').click(function () {
        var key = $(this).parents('.layui-input-inline').find('input').data('key');
        delete fileNames[key];
        $(this).hide();
        $(this).parents('.layui-input-inline').find('img').attr('src','');
    });

    //获取选择状态
    function getSelectType(resData){
        form.on('select(inputType)',function (data) {
            var code = data.value;
            for (var i = 0; i < resData.length; i++) {
                if(code == resData[i].productCode){
                    bussType = resData[i].bussType;
                }
            }
            changeInputType(bussType);
        });
    }


    $("#addPlanSubmit").click(function() {
        var pCode = $('#productCode').val();
        var boPrice = $('#boPrice').val();
        if(!boPrice){
            layer.error("借款金额不能为空!")
            return
        }
        layer.iframe({
            title: '计算还款计划',
            content: 'layertable.html?pCode='+pCode+'&boPrice='+boPrice,
        })
    })

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

    //默认车贷类型
    changeInputType(bussType);

    initProduct();
    function initProduct(){
        var produ = {
            url: ma.host+"/borrow/product/sel",
            data: {},
            done: function(res) {
                var data = res.data;
                for (var i =0;i< data.length;i++) {
                    // document.getElementById("productCode").options.add(new Option(data[i].productName,data[i].productCode));
                    $("#productCode").append('<option value="' + data[i].productCode + '">' + data[i].productName + '</option>');

                    form.render('select');
                }
                getSelectType(data);
            },
            fail: function(re) {
                layer.error(re.errorMessage);
            },
            //本地mock数据，上线删除
            // error:function (err) {
            //     var resData = {"errorCode":"0000000","errorMessage":"成功","data":[{"pType":"0","productCode":"0003","productName":"车贷3期-4%-2%","productExpect":"3","earlyServiceRate":"0","monthServiceRate":"0.04","monthAccrualRate":"0.02","guaranteeViolateRate":"0","serviceViolateRate":"0","earlyPayRate":"0.01","pPayType":"1","bussType":"1","pPayTypeName":"一次性还本息","fineServiceRate":"0.0016","createTime":"2018-12-12 21:34:36"},{"pType":"0","productCode":"0004","productName":"车贷6期-7.5%-4.5%","productExpect":"6","earlyServiceRate":"0","monthServiceRate":"0.075","monthAccrualRate":"0.045","guaranteeViolateRate":"0","serviceViolateRate":"0","earlyPayRate":"0.01","pPayType":"1","bussType":"1","pPayTypeName":"一次性还本息","fineServiceRate":"0.0016","createTime":"2018-12-12 21:40:09"},{"pType":"0","productCode":"0005","productName":"车贷6期-7%-5%","productExpect":"6","earlyServiceRate":"0","monthServiceRate":"0.07","monthAccrualRate":"0.05","guaranteeViolateRate":"0","serviceViolateRate":"0","earlyPayRate":"0.01","pPayType":"1","bussType":"1","pPayTypeName":"一次性还本息","fineServiceRate":"0.0016","createTime":"2018-12-12 21:51:42"},{"pType":"0","productCode":"0006","productName":"车贷12期-12%-12%","productExpect":"12","earlyServiceRate":"0","monthServiceRate":"0.12","monthAccrualRate":"0.12","guaranteeViolateRate":"0","serviceViolateRate":"0","earlyPayRate":"0.01","pPayType":"1","bussType":"1","pPayTypeName":"一次性还本息","fineServiceRate":"0.0016","createTime":"2018-12-12 22:14:28"},{"pType":"0","productCode":"0007","productName":"车贷12期-10.4%-13.6%","productExpect":"12","earlyServiceRate":"0","monthServiceRate":"0.104","monthAccrualRate":"0.136","guaranteeViolateRate":"0","serviceViolateRate":"0","earlyPayRate":"0.01","pPayType":"1","bussType":"1","pPayTypeName":"一次性还本息","fineServiceRate":"0.0016","createTime":"2018-12-12 22:19:40"},{"pType":"0","productCode":"0008","productName":"车贷12期-先息后本","productExpect":"12","earlyServiceRate":"0","monthServiceRate":"0.009","monthAccrualRate":"0.009","guaranteeViolateRate":"0","serviceViolateRate":"0","earlyPayRate":"0.01","pPayType":"2","bussType":"1","pPayTypeName":"先息后本","fineServiceRate":"0.0016","createTime":"2019-02-23 11:09:56"},{"pType":"0","productCode":"0009","productName":"车贷24期-等额本息","productExpect":"24","earlyServiceRate":"0","monthServiceRate":"0.007","monthAccrualRate":"0.007","guaranteeViolateRate":"0","serviceViolateRate":"0","earlyPayRate":"0.01","pPayType":"3","bussType":"1","pPayTypeName":"等额本息","fineServiceRate":"0.0016","createTime":"2019-02-23 11:10:40"},{"pType":"0","productCode":"0010","productName":"房贷24期-等额本息","productExpect":"24","earlyServiceRate":null,"monthServiceRate":null,"monthAccrualRate":null,"guaranteeViolateRate":null,"serviceViolateRate":null,"earlyPayRate":null,"pPayType":"3","bussType":"2","pPayTypeName":"等额本息","fineServiceRate":null,"createTime":"2019-02-23 11:10:40"}],"succeed":true};
            //     getSelectType(resData.data);
            // }
        }
        ma.ajax(produ);
    }
});
