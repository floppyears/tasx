<g:render template="/header" model="[action:'task.list']" />

<table>
    <tr>
        <th>${g.message(code:"tasx.task.list.status")}</th>
        <th>${g.message(code:"tasx.task.list.task")}</th>
    </tr>
    <g:each var="task" in="${taskList}">
        <tr>
            <td>
                ${formatStatus(task.status)}
            </td>
            <td>
                <a href="/tasx/task/details/${task.id}">
                    ${formatDescription(task.description)}
                </a>
            </td>
        </tr>
    </g:each>
</table>

<a href="/tasx/task/details">
    ${g.message(code:"tasx.task.list.new")}
</a>

<g:render template="/footer" />