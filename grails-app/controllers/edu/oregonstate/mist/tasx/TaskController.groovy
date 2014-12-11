package edu.oregonstate.mist.tasx

class TaskController {

    static defaultAction = "list"

    static scaffold = false

    def taskService

    /**
     * Create, update, or read a task
     *
     * @return  task view model
     */
    Map details() {
        User user = getUserOrLogin()

        Task task = Task.get(params.id) ?:
                    new Task()

        if (params.submitting) {
            taskService.update(task, user, params)
            redirect([action: "details", id: task.id])
        } else {
            return [task:task] << task.serialize()
        }
    }

    /**
     * Display a list of tasks
     *
     * @return  task list view model
     */
    Map list() {
        User user = getUserOrLogin()

        return [ taskList: Task.findAllWhere([user: user]) ]
    }

    private User getUserOrLogin() {
        return (User)session["user"] ?:
               redirect([controller: "user", action: "login"])
    }
}
