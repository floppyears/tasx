package edu.oregonstate.mist.tasx

class TaskRestController {

    static responseFormats = ["json"]

    def taskService

    /**
     * Respond with all of a user's tasks serialized as JSON
     *
     * $ curl -X GET -H "Accept:application/json" /tasx/rest/user/UID/task
     *
     * @return      JSON response
     */
    def index() {
        User user = User.get(params.userRestId)

        respond(Task.findAllWhere([user: user])
                    .collect(taskService.&serializeTask))
    }

    /**
     * Respond with a user's task serialized as JSON
     *
     * $ curl -X GET -H "Accept:application/json" /tasx/rest/user/UID/task/TID
     *
     * @param task  from URL
     * @return      JSON response
     */
    def show(Task task) {
        User user = User.get(params.userRestId)

        respond(Task.findWhere([user: user, id: task?.id])
                    .collect(taskService.&serializeTask))
    }
}
