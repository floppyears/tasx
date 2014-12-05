<g:render template="/header" model="[action:'task.list']" />

<g:each var="task" in="${taskList}">
    <tasx:display task="${task}" />
</g:each>

<g:render template="/footer" />