<g:render template="/header" model="[action: 'user.account']" />

<dl>
    <dt>${g.message(code:"tasx.user.account.name")}</dt>
    <dd>${user?.name}</dd>
    <dt>${g.message(code:"tasx.user.account.email")}</dt>
    <dd>${user?.email}</dd>
</dl>

<g:render template="/footer" />
