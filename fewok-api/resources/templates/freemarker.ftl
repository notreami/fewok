<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>freemarker study!</title>
</head>
<body>
freemarker learn
<P class="text-primary">插值1:${placeholder}</p>
<p>插值2:${clientInfo.clientAppKey}</p>

<#--#号开头的标签 是FTL的标签  @号开头的标签 是FTL的自定义标签-->

<!--html 注释 输出-->
<#--freemarker 注释 不会输出 -->

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