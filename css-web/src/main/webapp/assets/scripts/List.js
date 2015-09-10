function GetSearceWhere(searchDiv,defaultWhere) {
    //控件要包含这三个属性<fieldType="string" operator="=" fieldName="proxy.Name" /> 

    searchDiv = searchDiv || "searchDiv";
    searchDiv = "#" + searchDiv + " *[fieldName]";
    var wheres = [];
    wheres.push("(1=1)");

    if (defaultWhere) { wheres.push(defaultWhere); }

    $(searchDiv).each(function (index) {
        var value = GetValue(this);
        if (value) {
            var where = GetWhere($(this).attr("fieldName"), $(this).attr("fieldType"), $(this).attr("operator"), value);
            wheres.push(where);
        }
    });

    return wheres.join(' and ');
    //alert(wheres.join(' and '));
}


function GetValue(control) {
    //nodeName
    return $(control).val();
}

function GetWhere(fieldName, fieldType, operator, fieldValue) {

    fieldValue = FormatValue(fieldType, operator, fieldValue);

    return "(" + fieldName + " " + operator + " " + fieldValue + ")";
}

function FormatValue(fieldType, operator, fieldValue) {
    fieldValue = fieldValue.toString().replace("'", "''");

    if (operator == "like") {
        fieldValue = "'%" + fieldValue + "%'";
    }
    
    else if (fieldType == "string" || fieldType == "datetime") {
        fieldValue = "'" + fieldValue + "'";
    }
    else if (fieldType == "time" && operator == "between") {
        fieldValue = "'" + fieldValue + " 00:00:00' and '" + fieldValue + " 23:59:59'";
    }

    else if (fieldType == "beginDateTime") {
        fieldValue = "'" + fieldValue + " 00:00:00'";
    }

    else if (fieldType == "endDateTime") {
        fieldValue = "'" + fieldValue + " 23:59:59'";
    }

    else if (fieldType == "SendTime") {
        fieldValue = "'" + fieldValue + ":00'";
    }

    else if (fieldType == "datetime1") {

        fieldValue = getLocalTime(fieldValue + " 00:00:00");
    }

    else if (fieldType == "datetime2") {

        fieldValue = getLocalTime(fieldValue + " 23:59:59");
    }

    else if (fieldType == "datetime3") {
        fieldValue = YearMonthToDateTime(fieldValue);
    }
    

    return fieldValue;
}

//将日期转换成时间戳
function getLocalTime(str) {
    var new_str = str.replace(/:/g, '-');
    new_str = new_str.replace(/ /g, '-');
    var arr = new_str.split("-");
    var datum = new Date(Date.UTC(arr[0], arr[1] - 1, arr[2], arr[3] - 8, arr[4], arr[5]));
    var t = datum.getTime() / 1000;
    return t;
}

function GetTableRows(tableId, rowIndex) {
    var t = $("#" + tableId);
    var tb = t.children();
    var rows = tb.children(":gt(0)");

    if (rowIndex) {
        var row = rows.eq(rowIndex);

        return row;
    }

    return rows;
}

function GetColIndex(row, colIndex) {
    if (colIndex != undefined) {
        return colIndex;
    }

    return row.children().length-1;
}

function GetCellValue(tableId,rowIndex,colIndex) {
    var row = GetTableRows(tableId, rowIndex);
    colIndex = GetColIndex(row, colIndex);
    var cell = row.children()[colIndex];
    var cellValue = $(cell).text();
    //alert(cellValue);

    return cellValue;
}

function GetSelectedValue(tableId, colIndex) {
    var rows = GetTableRows(tableId);
    var cellValues=[];

    rows.each(function (index) {
        var row = $(this);
        var cell = row.children()[0];
        var cb = $(cell).find('input[type=checkbox]');
        if (cb.length > 0) {
            if (cb.prop("checked")) {
                colIndex = GetColIndex(row, colIndex);
                cell = row.children()[colIndex];

                if ($(cell).text()) {
                    cellValues.push($(cell).text());
                    //alert(cell.innerText);
                }
            }
        }
    });

    return cellValues;
}

function SelectAll(tableId,ctr) {
    var rows = GetTableRows(tableId);

    rows.each(function (index) {
        var row = $(this);
        var cell = row.children()[0];
        var cb = $(cell).find('input[type=checkbox]');
        if ($(ctr).prop("checked")) {
            cb.prop("checked", true);
        }
        else {
            cb.prop("checked", false);
        }
    });
}

//fieldName 字段名
//ym 年月 如:2012-02
function YearMonthToDateTime( ym) {
    var sp = ym.split('-');
    var count = getCountDays(sp[0], sp[1]);

    var begin = ym + "-01 00:00:00";
    var end = ym + "-" + count + " 23:59:59";

    return " between '" + begin + "' and '" + end + "'";
}

function getCountDays(year,month) {
    month = parseInt(month, 10) + 1;
    var d = year + "/" + month + "/01";
    var temp = new Date(d);
    temp.setDate(0); 
    return temp.getDate();
}

function trMourseOver(currentTr) {
    $(currentTr).addClass("ov");
}

function trMourseOut(currentTr) {
    $(currentTr).removeClass("ov");
}