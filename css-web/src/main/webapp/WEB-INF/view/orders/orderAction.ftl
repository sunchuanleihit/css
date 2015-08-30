<#include "/base/basehead.ftl">
<table>
<tr><th>操作时间</th><th>操作信息</th><th>操作人</th></tr>
<#list resultList as result>
<tr><td>${result.actionTime}</td><td>${result.note}</td><td>${result.actor}</td></tr>
</#list>
</table>
<#include "/base/basefooter.ftl">