Exams API Docs
----------------

- Create Exam
    ```
    POST /api/exam/create
    ```
    Expects:
    ```
    {
        "title": "TITLE",
		    "questions": [{ "content": "CONTENT" }, { "content": "CONTENT" }, ...],
		    "keywords": ["key1", "key2", ...],
        "courseId" : "ID" // NOT REQUIRED
    }
    ```

	Returns:
	```
	Success response
	```

- Get User Exams
    ```
    GET /api/exam/
    ```

	Returns:
	```
	[
		{
			"title": "Exam 1",
			"questions": [...],
			"keywords": [...],
			"created": "date",
			"updated": "date",
		},
		{
			"title": "Exam 2",
			"questions": [...],
			"keywords": [...],
			"created": "date",
			"updated": "date",
		},
	]
	```
