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

    layui.upload({
        url:ma.host+"/uploadFile",
        ext: 'jpg|png|gif',
        name:"file",
        before:function(input){
            tempImg = $(input).parents('.layui-input-inline').find('label');

            // //模拟上传成功，线上请删除
            // $(tempImg).attr('src','../../images/user.png');
            // $(tempImg).show();
        },
        success:function (res) {
            console.log(res.data);
            $(tempImg).text(res.data);
            $('#uploadIdcard').text();

            var url = $('#uploadIdcard').text();
            aler(url)
            top.layer.success("上传成功");
            // console.log(res);
            // $(tempImg).attr('src',res.src);
            // $(tempImg).show();

        }
    });


    form.on('submit(orderAdd)', function(data) {
        var formData=data.field;
        var auditkeys = [];
        for(i in formData){
            if(i.indexOf('auditkeys')>-1){
                auditkeys.push(formData[i])
            }
        };
        var SubmitData={
            "userInfo": {
                "userEarns": formData.userEarns,
                "idcard": formData.idcard,
                "mobile": formData.mobile,
                "industry": formData.industry,
                "userName": formData.userName,
                "creditDec": formData.creditDec,
                "workNature": formData.workNature
            },
            "orderAudit": {
                "auditkeys": auditkeys
            },
            "borrowSalesman": {
                "salesName": formData.salesName,
                "salesMobile": formData.salesMobile
            },
            "userCar": {
                "plateNumber": formData.plateNumber
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

    form.on('select(inputType)',function (data) {
        var code = data.value;

        //车贷产品
        if(code == '0003'||code == '0004'||code == '0005'||code == '0006'||code == '0007'||code == '0008'){
            $('#inputType').val('carInput');
            changeInputType('car');
        }else {
            //房贷产品
            $('#inputType').val('houseInput');
            changeInputType('house');
        }
    });

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
