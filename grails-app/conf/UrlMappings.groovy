class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')

        "/rest/user"(resources: 'userRest') {
            "/task"(resources: 'taskRest')
        }
	}
}
