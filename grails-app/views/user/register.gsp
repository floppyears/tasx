<!DOCTYPE html>
<html>
<head>
    <title><g:message code="tasx.user.register.title" /></title>
    <style type="text/css">
        #registration-form tr td:first-child {
            text-align: right;
        }
    </style>
    <%-- include bootstrap --%>
</head>
<body>

<div id="welcome">
    <g:message code="tasx.user.register.welcome" />
</div>

<g:hasErrors bean="${user}">
    <div class="errors">
      <g:renderErrors bean="${user}" />
    </div>
</g:hasErrors>

<div id="registration-form">
    <g:form action="register">
        <table>
            <tr>
                <td><g:message code="tasx.user.register.name" /></td>
                <td><g:field type="text" name="name" value="${user.getName()}" /></td>
            </tr>
            <tr>
                <td><g:message code="tasx.user.register.email" /></td>
                <td><g:field type="email" name="email" value="${user.getEmail()}" /></td>
            </tr>
            <tr>
                <td><g:message code="tasx.user.register.password" /></td>
                <td><g:passwordField name="pass1" /></td>
            </tr>
            <tr>
                <td><g:message code="tasx.user.register.confirm-password" /></td>
                <td><g:passwordField name="pass2" /></td>
            </tr>
            <tr>
                <td colspan="2"><g:submitButton name="submit" value="${g.message(code: 'tasx.user.register.submit')}" /></td>
            </tr>
        </table>
    </g:form>
</div>

</body>
</html>