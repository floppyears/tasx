<g:render template="header" model="[action: 'account']" />

<table>
    <tr><td>Name:</td><td>${user?.name}</td></tr>
    <tr><td>Email:</td><td>${user?.email}</td></tr>
</table>

<a href="/tasx/task/list">
    ${g.message(code:"tasx.task.list.title")}
</a>

<g:render template="footer" />
