package edu.oregonstate.mist.tasx

class TaskController {

    static defaultAction = "index"

    static scaffold = true

    Map details() {
        User user = (User)session["user"] ?:
                    redirect([controller: "user", action: "login"])

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
}
