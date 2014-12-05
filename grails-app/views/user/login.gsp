<g:render template="/header" model="[action: 'user.login']" />


<form action="/tasx/user/login" method="post" role="form">
    <div class="form-group">
        <label for="name">${g.message(code: "tasx.user.login.name")}</label>
        <input type="text" name="name" id="name" />
    </div>
    <div class="form-group">
        <label for="password">${g.message(code: "tasx.user.login.password")}</label>
        <input type="password" name="password" id="password" />
    </div>
    <button type="submit" class="btn btn-default" name="submit">
        ${g.message(code: 'tasx.user.login.submit')}
    </button>
</form>

<g:render template="/footer" />
