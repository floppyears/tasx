<!DOCTYPE html>
<html lang="${org.springframework.web.servlet.support.RequestContextUtils.getLocale(request)}">
<head>
    <title>${g.message(code: "tasx." + action + ".title")}</title>
    <link rel="stylesheet" href="${resource(plugin:'twitter-bootstrap',file:'bootstrap.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css', file:'tasx.css')}" />
</head>
<body>