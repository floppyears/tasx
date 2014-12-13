<!DOCTYPE html>
<html lang="${org.springframework.web.servlet.support.RequestContextUtils.getLocale(request)}">
<head>
    <title>${g.message(code: "tasx." + action + ".title")}</title>
    <link rel="stylesheet" href="${resource(plugin:'twitter-bootstrap',file:'bootstrap.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css', file:'tasx.css')}" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>
<div class="container">

<tasx:navigate authenticated="${(Boolean)session["user"]}"
               active="${action}" />

<h1>${g.message(code: "tasx." + action + ".title")}</h1>