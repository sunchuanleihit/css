<#import "/spring.ftl" as s />
<ul>
	<li>公司名称：<#if (store.companyName?exists)>${store.companyName}</#if></li>
	<li>店铺名称：<#if (store.storeName?exists)>${store.storeName}</#if></li>
	<li>店主：<#if (store.ownerName?exists)>${store.ownerName}</#if></li>
	<li>手机：<#if (store.ownerMob?exists)>${store.ownerMob}</#if></li>
	<li>电话：<#if (store.ownerTel?exists)>${store.ownerTel}</#if></li>
</ul>