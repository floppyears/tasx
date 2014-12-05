<g:render template="/header" model="[action: 'user.account']" />

<table>
    <tr><td>Name:</td><td>${user?.name}</td></tr>
    <tr><td>Email:</td><td>${user?.email}</td></tr>
</table>

<a href="/tasx/task/list">
    ${g.message(code:"tasx.task.list.title")}
</a>

<a href="/tasx/user/logout">
    ${g.message(code:"tasx.user.logout.title")}
</a>

<g:render template="/footer" />
