Courses API Docs
----------------

- Create Course
    ```
    POST /api/course/create
    ```
    Expects:
    ```
    {
        "name": "NAME",
		"code": "CODE",
		"exams": [{...}]			// OPTIONAL
    }
    ```

	Returns:
	```
    Successfully created course
	```

- Attach Exam to Course
    ```
    POST /api/course/exam/attach
    ```
    Expects:
    ```
    {
        "courseId": "ID",
		"examId": "ID"
    }
    ```

	Returns:
	```
    Successfully attached exam with id {examId} from course with id {courseId}
	```

- Detach Exam to Course
    ```
    POST /api/course/exam/detach
    ```
    Expects:
    ```
    {
        "courseId": "ID",
		"examId": "ID"
    }
    ```

	Returns:
	```
    Successfully detached exam with id {examId} from course with id {courseId}
	```

- Get Courses for User
    ```
    GET /api/course
    ```

	Returns:
	```
	[
		{
			"id": "id",
			"name": "Course 1",
			"code": "TESTCOURSE1",
			"created": "date",
			"updated": "date",
		},
		{
			"id": "id",
			"name": "Course 2",
			"code": "TESTCOURSE2",
			"created": "date",
			"updated": "date",
		}
	]
	```

- Get Course By Id
    ```
    GET /api/course/:id
    ```

	Returns:
	```
    {
        "id": "id",
        "name": "Course 1",
        "code": "TESTCOURSE1",
        "created": "date",
        "updated": "date",
    }
	```

- Get Exams from Course
    ```
    GET /api/course/:id/exams
    ```
    Returns (a list of exam objects):
    ```
    [
        {...},
        {...}
    ]
    ```


- Delete Course
    ```
    DELETE /api/course/:id
    ```
	Returns:
	```
	Deleted course with id <course_id>
	```
