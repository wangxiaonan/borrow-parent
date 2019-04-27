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

    var fileNames = {};

    layui.upload({
        url:ma.host+"/uploadFile",
        ext: 'jpg|png|gif',
        name:"file",
        before:function(input){
            tempImg = $(input).parents('.layui-input-inline').find('img');
            tempImgkey = $(input).data("key");

            // //模拟上传成功，线上请删除
            // $(tempImg).attr('src','../../images/user.png');
            // $(tempImg).show();
        },
        success:function (res) {
            $(tempImg).attr({"src":res.data});
            fileNames[tempImgkey] = res.data;
            console.log(fileNames);
            // $('#uploadIdcard').text();

            // var url = $('#uploadIdcard').text();
            // aler(url)
            top.layer.success("上传成功");
            // console.log(res);
            // $(tempImg).attr('src',res.src);
            // $(tempImg).show();

        }
    });


    form.on('submit(orderAdd)', function(data) {
        var formData=data.field;
        var auditkeys = {};
        debugger;
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
    // 切换房贷
    // form.on('select(inputType)',function (data) {
    //     var code = data.value;
    //
    //     //车贷产品
    //     if(code == '0003'||code == '0004'||code == '0005'||code == '0006'||code == '0007'||code == '0008'){
    //         $('#inputType').val('carInput');
    //         changeInputType('car');
    //     }else {
    //         //房贷产品
    //         $('#inputType').val('houseInput');
    //         changeInputType('house');
    //     }
    // });

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
        if(type == 'car'){
            $('.form-house').hide();
            $('.form-car').show();
        }else {
            $('.form-car').hide();
            $('.form-house').show();
        }
    }

    //默认车贷类型
    changeInputType('car');
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
            },
            fail: function(re) {
                layer.error(re.errorMessage);
            }
        }
        ma.ajax(produ);
    }
});
