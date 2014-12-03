<g:render template="/header" model="[action:'task.list']" />

<g:each var="task" in="${taskList}">
    <tasx:display task="${task}" />
</g:each>

<a href="/tasx/task/details">
    ${g.message(code:"tasx.task.list.new")}
</a>

<g:render template="/footer" />