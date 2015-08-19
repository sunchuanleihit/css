
//邮件
//是否数字
//是否是正数
//是否电话号码
//是否是邮政编码
//字符长度不能小于多于，在什么之间
//----

//功能:判断一个控件是否为空
//参数说明: fieldName 表示当前控件名称, msg 表示
function IsFieldEmpty(fieldName, msg) {
    var objField = document.getElementById(fieldName);
    var fieldValue = objField.value;
    //alert(str);
    //str = trim(str);
    if (fieldValue.length == 0) {
        alert(msg);
        objField.focus();
        return false;
    }
    return true;
}

//验证时间
 function isShortTime(fieldName) {
     var objField = document.getElementById(fieldName);
     var str = objField.value;
     alert(str);
    var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);  
     if (a == null) {alert('输入的参数不是时间格式'); return false;}  
     if (a[1]>24 || a[3]>60 || a[4]>60)  
     {  
         alert("时间格式不对");
         return false;
     }  
     return true;
 }

//是否是短日期
 function isShortDate(fieldName) {
     var objField = document.getElementById(fieldName);
     var str = objField.value;
     var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
     if (r == null) {
         alert('日期格式不正确.请重新确认');
         return false; 
     }
    var d= new Date(r[1], r[3]-1, r[4]);  
    return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);  

 }

//验证完整日期
 function isLongDateTime(fieldName) {
     var objField = document.getElementById(fieldName);
     var str = objField.value; 
     var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;  
     var r = str.match(reg);
     if (r == null) {
         alert('日期时间格式不正确.请重新确认');
         return false;
     }
     var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);  
     return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5] &&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);

 }
 
 //是否是正整数
 function isInterger(fieldName) {
     var objField = document.getElementById(fieldName);
     var str = objField.value;
     var re = /^[1-9]\d*$/;
     if (!re.test(objField.value)) {
         alert("必须为正整数!");
         objField.value = "";
         objField.focus();
         return false;
     }

     return true;

 }
 
  //是否是正整数
 function CheckIsInterger(fieldName, sMsg) {
     var objField = document.getElementById(fieldName);
     var str = objField.value;
     var re = /^[1-9]\d*$/;
     if (!re.test(objField.value)) {
         alert(sMsg);
         objField.focus();
         return false;
     }

     return true;

 }

//是否是电话号码
 function isTelphone(ctrlId, sMsg) {
     var objCtrl = document.getElementById(ctrlId);
     var str = objCtrl.value;
     var reg = /^([0-9]|[\-])+$/g;
     if (str.length < 7 || str.length > 18) {
        
         alert('电话号码的位数不对');
         objCtrl.focus();
         return false;
     }

     if (reg.exec(str) == false) {
         alert(sMsg);
         objCtrl.focus();
         return false;
     }

     return true;
 }


 //是否是Email
 function isEmail(ctrlId, sMsg) {
     var objCtrl = document.getElementById(ctrlId);
     var str = objCtrl.value;
     var reg = /[a-z0-9-]{1,30}@[a-z0-9-]{1,65}.[a-z]{3}/;
     if (reg.test(str) == false) {
         alert(sMsg);
         objCtrl.focus();
         return false;
     }

     return true;
 }

 //是否是数字/^\d+(\.\d+)?$/;
 function checkIsNumber(ctrlId, sMsg) {
     var objCtrl = document.getElementById(ctrlId);
     var str = objCtrl.value;
     var reg = /^\d+(\.\d+)?$/;

     if (reg.exec(str) == false) {
         alert(sMsg);
         objCtrl.focus();
         return false;
     }

     return true;
 }


////////////------------------------
//function isEmail(strEmail)
// { 
// 
//if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1) 
//return true; 
//else 
//alert(strEmail); 
//return false;
//} 
///////////////////////////////////


//     var objCtrl = document.getElementById(ctrlId);
//     var str = objCtrl.value;


//添加验证身份证号码

function CheckValue(fieldName)
{
    //var id=idCard;
     var objField = document.getElementById(fieldName);
     var id = objField.value;
    var id_length=id.length;

        if (id_length==0)
        {
            alert("请输入身份证号码!");
            return false;
        }

        if (id_length!=15 && id_length!=18)
        {
            alert("身份证号长度应为15位或18位！");
            return false;
        }

    if (id_length==15)
    {
        yyyy="19"+id.substring(6,8);
        mm=id.substring(8,10);
        dd=id.substring(10,12);

            if (mm>12 || mm<=0)
            {
                alert("输入身份证号,月份非法！");
                return false;
            }

            if (dd>31 || dd<=0)
            {
                alert("输入身份证号,日期非法！");
                return false;
            }

        birthday=yyyy+ "-" +mm+ "-" +dd;

            if ("13579".indexOf(id.substring(14,15))!=-1)
            {
                sex="1";
            }
        else
        {
            sex="2";
        }
    } 
    else
     if (id_length==18)
     {
        if (id.indexOf("X") > 0 && id.indexOf("X")!=17 || id.indexOf("x")>0 && id.indexOf("x")!=17)
        {
            alert("身份证中\"X\"输入位置不正确！");
            return false;
        }

        yyyy=id.substring(6,10);
        if (yyyy>2200 || yyyy<1900)
        {
            alert("输入身份证号,年度非法！");
            return false;
        }

        mm=id.substring(10,12);
        if (mm>12 || mm<=0)
        {
            alert("输入身份证号,月份非法！");
            return false;
        }

        dd=id.substring(12,14);
        if (dd>31 || dd<=0)
        {
            alert("输入身份证号,日期非法！");
            return false;
        }

//        if (id.charAt(17)=="x" || id.charAt(17)=="X")
//        {
//           // if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id))
//           
//            if ("x"!=result && "X"!=result)
//            {
//                alert("身份证校验错误，请检查最后一位11！");
//                return false;
//            }

//       }
//        else
//        { 
//            if (id.charAt(17)!=result)
//            {document.write(result);
//                alert("身份证校验错误，请检查最后一位2！");
//                return false;
//            }
//        }

        birthday=id.substring(6,10) + "-" + id.substring(10,12) + "-" + id.substring(12,14);
        
        if ("13579".indexOf(id.substring(16,17)) > -1)
        {
            sex="1";
         }else
         {
            sex="2";
         }
    }

    return true;
}

//最后一位

//function GetVerifyBit(id){
//    var result;
//    var nNum=eval_r(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
//    nNum=nNum%11;
//    switch (nNum) {
//       case 0 :
//          result="1";
//          break;
//       case 1 :
//          result="0";
//          break;
//       case 2 :
//          result="X";
//          break;
//       case 3 :
//          result="9";
//          break;
//       case 4 :
//          result="8";
//          break;
//       case 5 :
//          result="7";
//          break;
//       case 6 :
//          result="6";
//          break;
//       case 7 :
//          result="5";
//          break;
//       case 8 :
//          result="4";
//          break;
//       case 9 :
//          result="3";
//          break;
//       case 10 :
//          result="2";
//          break;
//    }
//    //document.write(result);
//    var xx=result.value
//    alert('xx')
//    //return result;
//}
