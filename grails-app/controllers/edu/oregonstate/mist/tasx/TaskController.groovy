package edu.oregonstate.mist.tasx

class TaskController {

    static defaultAction = "list"

    static scaffold = false

    Map details() {
        User user = getUserOrLogin()

        Task task = Task.get(params.id) ?:
                    new Task()

        if (params.submitting) {
            Date from = stringToDate(params.from)
            Date to   = stringToDate(params.to)

            Interval schedule = task.schedule
            schedule.setInterval(from, to)
            schedule.save()

            task.description = params.description
            task.schedule    = schedule
            task.priority    = Integer.parseInt(params.priority)
            task.status      = stringToStatus(params.status)
            task.user        = user

            task.save([flush:true])
            redirect([action: "details", id: task.id])
        } else {
            [ params: params,
              selectStatus: {
                  option ->
                      if ((option.equals("todo") && task.isIncomplete()) ||
                          (option.equals("done") && task.isComplete())   ||
                          (option.equals("canc") && task.isCancelled())  ||
                          (option.equals("dele") && task.isDeleted())) {
                          return " selected=selected"
                      } else {
                          return ""
                      }
              },
              description: task.description,
              from: task.schedule?.fromDate?.format(DATEFORMAT),
              to: task.schedule?.toDate?.format(DATEFORMAT),
              priority: task.priority,
              id: task.id
            ]
        }
    }

    Map list() {
        User user = getUserOrLogin()

        List taskList = Task.findAllWhere([user: user])

        return [ taskList: taskList,
                 formatStatus: {
                     status ->
                         switch(status) {
                             case Task.Status.TODO:
                                 "todo:"
                                 break
                             case Task.Status.DONE:
                                 "done:"
                                 break
                             case Task.Status.CANCELLED:
                                 "canc:"
                                 break
                             case Task.Status.DELETED:
                             default:
                                 "dele:"
                                 break
                         }
                 },
                 formatDescription: {
                     String description ->
                         String firstLine = description.split("\n")[0]

                         Integer length = firstLine.length()
                         Integer    max = 100
                         Integer  index = (length < max) ? length : max

                         String summary = firstLine.substring(0, index)

                         return summary
                 }
               ]
    }

    private final String DATEFORMAT = "MM/dd/yyyy"

    private Closure stringToDate = {
        dateString ->
            Date.parse(DATEFORMAT, dateString)
    }

    private Closure stringToStatus = {
        statusString ->
            switch(statusString) {
                case "done":
                    return Task.Status.DONE
                case "canc":
                    return Task.Status.CANCELLED
                case "dele":
                    return Task.Status.DELETED
                case "todo":
                default:
                    return Task.Status.TODO
            }
    }

    private User getUserOrLogin() {
        return (User)session["user"] ?:
               redirect([controller: "user", action: "login"])
    }
}
