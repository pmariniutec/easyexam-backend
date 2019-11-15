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
		"exams": []			// NOT REQUIRED
    }
    ```

	Returns:
	```
	Success response
	```

- Add Exam to Course
    ```
    POST /api/course/exam/add
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
	Success response
	```

- Get Courses for User
    ```
    GET /api/course
    ```

	Returns:
	```
	[
		{
			"name": "Course 1",
			"code": "TESTCOURSE1",
			"exams": []
		},
		{
			"name": "Course 2",
			"code": "TESTCOURSE2",
			"exams": []
		}
	]
	```

- Delete Course
    ```
    DELETE /api/course/<id>
    ```

	Returns:
	```
	Deleted course <course_name>
	```
