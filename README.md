tasx
====

Description
-----------
tasx is a web application allowing users to manage tasks. It is a test project
for learning the Groovy programming language and the Grails web application
framework.

Users have a
 * name,
 * email,
 * password, and
 * list of tasks.

Tasks have a
 * description,
 * schedule,
 * priority,
 * status, and
 * list of subtasks.

Users can
 * register,
 * login,
 * view account,
 * edit account,
 * logout, and
 * delete account.

Tasks can be
 * created,
 * viewed,
 * edited,
 * listed, and
 * deleted.

REST Interface
--------------
    $ curl -X GET -H "Accept:application/json" http://HOSTNAME/tasx/rest/user
    $ curl -X GET -H "Accept:application/json" http://HOSTNAME/tasx/rest/user/USERID
    $ curl -X GET -H "Accept:application/json" http://HOSTNAME/tasx/rest/user/USERID/task
    $ curl -X GET -H "Accept:application/json" http://HOSTNAME/tasx/rest/user/USERID/task/TASKID

Usage
-----
    $ grails -version
    Grails version: 2.4.3
    $ grails
    grails> run-app