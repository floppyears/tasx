<!DOCTYPE html>
<html>
<head>
    <title>${g.message(code: "tasx.user.register.title")}</title>
    <style type="text/css">
        #registration-form tr td:first-child {
            text-align: right;
        }
    </style>
    <%-- include bootstrap --%>
</head>
<body>

<div id="welcome">
    ${g.message(code: "tasx.user.register.welcome")}
</div>

<g:hasErrors bean="${user}">
    <div class="errors">
      <g:renderErrors bean="${user}" />
    </div>
</g:hasErrors>

<div id="registration-form">
    <form action="/tasx/user/register" method="post">
        <table>
            <tr>
                <td><label for="name">${g.message(code: "tasx.user.register.name")}</label></td>
                <td><input type="text" name="name" id="name" value="${user?.getName()}" /></td>
            </tr>
            <tr>
                <td><label for="email">${g.message(code: "tasx.user.register.email")}</label></td>
                <td><input type="email" name="email" id="email" value="${user?.getEmail()}" /></td>
            </tr>
            <tr>
                <td><label for="pass1">${g.message(code: "tasx.user.register.password")}</label></td>
                <td><input type="password" name="pass1" id="pass1" /></td>
            </tr>
            <tr>
                <td><label for="pass2">${g.message(code: "tasx.user.register.confirm-password")}</label></td>
                <td><input type="password" name="pass2" id="pass2" /></td>
            </tr>
            <tr>
                <input type="hidden" name="submitting" value="true" />
                <td colspan="2"><input type="submit" name="submit" value="${g.message(code: 'tasx.user.register.submit')}" /></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>