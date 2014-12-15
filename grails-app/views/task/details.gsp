<g:render template="/header" model="[action: 'task.details']" />

<g:if test="${(flash.message)}">
    <div class="message">${flash.message}</div>
</g:if>

<form action="/tasx/task/details/" method="post" role="form">
    <div class="form-group">
        <label for="description">
            ${g.message(code:"tasx.task.details.description")}
        </label>
        <textarea name="description" id="description">${description}</textarea>
    </div>
    <div>
        <div class="form-group">
            <label for="from">
                ${g.message(code:"tasx.task.details.from")}
            </label>
            <input type="date" name="from" id="from" value="${from}" placeholder="${g.message(code:"tasx.task.details.dateformat")}" />
        </div>
        <div class="form-group">
            <label for="to">
                ${g.message(code:"tasx.task.details.to")}
            </label>
            <input type="date" name="to" id="to" value="${to}" placeholder="${g.message(code:"tasx.task.details.dateformat")}" />
        </div>
    </div>
    <div class="form-group">
        <label for="priority">
            ${g.message(code:"tasx.task.details.priority")}
        </label>
        <input type="number" name="priority" id="priority" value="${priority}" />
    </div>
    <div class="form-group">
        <tasx:status task="${task}" />
    </div>
    <input type="hidden" name="id" value="${id}" />
    <input type="hidden" name="submitting" value="true" />
    <button type="submit" class="btn btn-default" name="submit">
        ${g.message(code:"tasx.task.details.submit")}
    </button>
</form>

<g:render template="/footer" />