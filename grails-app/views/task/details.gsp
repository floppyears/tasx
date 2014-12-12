<g:render template="/header" model="[action: 'task.details']" />

<form action="/tasx/task/details/" method="post" role="form">
    <div class="form-group">
        <label for="description">
            ${g.message(code:"tasx.task.details.description")}
        </label>
        <textarea name="description" id="description">${task.description}</textarea>
    </div>
    <div>
        ${g.message(code:"tasx.task.details.schedule")}
        <div class="form-group">
            <label for="from">
                ${g.message(code:"tasx.task.details.from")}
            </label>
            <input type="date" name="from" id="from" value="${from}" />
        </div>
        <div class="form-group">
            <label for="to">
                ${g.message(code:"tasx.task.details.to")}
            </label>
            <input type="date" name="to" id="to" value="${to}" />
        </div>
    </div>
    <div class="form-group">
        <label for="priority">
            ${g.message(code:"tasx.task.details.priority")}
        </label>
        <input type="number" name="priority" id="priority" value="${task.priority}" />
    </div>
    <div class="form-group">
        <tasx:status task="${task}" />
    </div>
    <input type="hidden" name="id" value="${task.id}" />
    <input type="hidden" name="submitting" value="true" />
    <button type="submit" class="btn btn-default" name="submit">
        ${g.message(code:"tasx.task.details.submit")}
    </button>
</form>

<a href="/tasx/task/list">
    ${g.message(code:"tasx.task.list.title")}
</a>

<g:render template="/footer" />