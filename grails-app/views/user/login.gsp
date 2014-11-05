<!DOCTYPE html>
<html>
<head>
    <title>${g.message(code: "tasx.user.login.title")}</title>
    <style type="text/css">
    #login-form tr td:first-child {
        text-align: right;
    }
    </style>
</head>
<body>

<div id="welcome">
    ${g.message(code: "tasx.user.login.welcome")}
</div>

<div id="login-form">
    <form action="/tasx/user/login" method="post">
        <table>
            <tr>
                <td><label for="name">${g.message(code: "tasx.user.login.name")}</label></td>
                <td><input type="text" name="name" id="name" /></td>
            </tr>
            <tr>
                <td><label for="password">${g.message(code: "tasx.user.login.password")}</label></td>
                <td><input type="password" name="password" id="password" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" name="submit" value="${g.message(code: 'tasx.user.login.submit')}" /></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>