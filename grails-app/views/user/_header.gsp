<!DOCTYPE html>
<html lang="${org.springframework.web.servlet.support.RequestContextUtils.getLocale(request)}">
<head>
    <title>${g.message(code: "tasx.user." + action + ".title")}</title>
    <link rel="stylesheet" href="${resource(plugin:'twitter-bootstrap',file:'bootstrap.css')}" />
</head>
<body>
<div id="welcome">
    ${g.message(code: "tasx.user." + action + ".welcome")}
</div>