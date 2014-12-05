<g:render template="/header" model="[action: 'user.account']" />

<dl>
    <dt>${g.message(code:"tasx.user.account.name")}</dt>
    <dd>${user?.name}</dd>
    <dt>${g.message(code:"tasx.user.account.email")}</dt>
    <dd>${user?.email}</dd>
</dl>

<a href="/tasx/task/list">
    ${g.message(code:"tasx.task.list.title")}
</a>

<a href="/tasx/user/logout">
    ${g.message(code:"tasx.user.logout.title")}
</a>

<g:render template="/footer" />
