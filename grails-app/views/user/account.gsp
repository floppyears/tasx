<!DOCTYPE html>
<html>
<head>
    <title>${g.message(code: "tasx.user.account.title")}</title>
</head>
<body>

<div id="welcome">
    ${g.message(code: "tasx.user.account.welcome")}
</div>

<table>
    <tr><td>Name:</td><td>${user?.getName()}</td></tr>
    <tr><td>Email:</td><td>${user?.getEmail()}</td></tr>
</table>

</body>
</html>
