/**
 *project JS main entrance
 *for example rely on  Layui'layer and form
 *usermanege && pass modify  page
 *
 **/
 layui.use(["form", "layer", "element"], function() {
    var layer = layui.layer,
    form = layui.form();

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
