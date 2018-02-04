<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
freemarker hot loading!!!
<P>插值1:${placeholder}</p>
<p>插值2:${clientInfo.clientAppKey}</p>
<!--html 注释 输出-->
<#--freemarker 注释 不会输出-->

<p>FTL标签${placeholder}<#if clientInfo.clientIp=="test if">OK</#if></p>
<p>
    <#if clientInfo.platform=="ANDROID">
        android
    <#elseif clientInfo.platform="IPHONE">
        iphone
    <#else >
        other
    </#if>
</p>
</body>
</html>