package edu.oregonstate.mist.tasx

import grails.transaction.Transactional

@Transactional
class TaskService {

    private final String DATEFORMAT = "MM/dd/yyyy"

    void update(task, user, params) {
        Date from = stringToDate(params.from)
        Date to = stringToDate(params.to)

        Interval schedule = task.schedule
        schedule.setInterval(from, to)
        schedule.save()

        task.description = params.description
        task.schedule = schedule
        task.priority = Integer.parseInt(params.priority)
        task.status = stringToStatus(params.status)
        task.user = user

        task.save([flush:true])
    }

    Map detailsModel(task, params) {
        return [ params: params,
                 from:   task.schedule?.fromDate?.format(DATEFORMAT),
                 to:     task.schedule?.toDate?.format(DATEFORMAT),
                 task:   task ]
    }

    private Date stringToDate(String dateString) {
        return Date.parse(DATEFORMAT, dateString)
    }

    private static Task.Status stringToStatus(String statusString) {
        switch(statusString) {
            case "done":      return Task.Status.DONE
            case "cancelled": return Task.Status.CANCELLED
            case "deleted":   return Task.Status.DELETED
            case "todo":
            default:      return Task.Status.TODO
        }
    }
}
