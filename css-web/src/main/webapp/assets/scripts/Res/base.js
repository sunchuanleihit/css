//提交form
function doSubmitForm(sFormName, sExeUrl) {

    var retValue = "";
    var options = {
        type: "post",
        url: sExeUrl,
        async: false,
        beforeSubmit: function (formArray, jqForm) {
        },
        success: function (data) {
            retValue = data;
        },
        error: function (aText) {
            alert(aText.status);
            alert(aText.responseText);
        }
    };

    $("#" + sFormName).ajaxSubmit(options);
    return retValue;
}
/*
返回得到数据集对象，同时格式化模板
*/
function ShowTemplateList(containId, templateId, dataUrl) {
    var retInfo = baseAjaxExecute(dataUrl);
    //var retData = eval('(' + retInfo + ')');
    var retData = retInfo;
    var listDatas = retData.rows;
    $("#" + containId).html($("#" + templateId).render(listDatas));
    return retData;
}
//通用执行Ajax的url的方法
function baseAjaxExecute(execUrl) {
    var retStatus = "";
    //alert(doExecUrl);
    $.ajax({
        url: execUrl,
        cache: false,
        type: "POST",
        async: false,
        beforeSend: function () {

        },
        success: function (msg) {
            retStatus = msg;
        },
        error: function (aText) {
            //alert(aText.responseText);
        }
    });

    return retStatus;
}
