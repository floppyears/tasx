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
    <tr><td>Name:</td><td>${user?.name}</td></tr>
    <tr><td>Email:</td><td>${user?.email}</td></tr>
</table>

</body>
</html>
