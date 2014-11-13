<g:render template="header" model="[action: 'register']" />

<g:hasErrors bean="${user}">
    <div class="errors">
      <g:renderErrors bean="${user}" />
    </div>
</g:hasErrors>

<form action="/tasx/user/register" method="post" role="form">
    <div class="form-group">
        <label for="name">${g.message(code: "tasx.user.register.name")}</label>
        <input type="text" name="name" id="name" value="${user?.name}" />
    </div>
    <div class="form-group">
        <label for="email">${g.message(code: "tasx.user.register.email")}</label>
        <input type="email" name="email" id="email" value="${user?.email}" />
    </div>
    <div class="form-group">
        <label for="pass1">${g.message(code: "tasx.user.register.password")}</label>
        <input type="password" name="pass1" id="pass1" />
    </div>
    <div class="form-group">
        <label for="pass2">${g.message(code: "tasx.user.register.confirm-password")}</label>
        <input type="password" name="pass2" id="pass2" />
    </div>
    <input type="hidden" name="submitting" value="true" />
    <button type="submit" class="btn btn-default" name="submit">
        ${g.message(code: 'tasx.user.register.submit')}
    </button>
</form>
<hr />
<a href="/tasx/user/login">Login</a>

<g:render template="footer" />