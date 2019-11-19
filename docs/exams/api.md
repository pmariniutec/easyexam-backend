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
		"questions": [{question_object1}, {question_object2}, ...],
		"keywords": ["key1", "key2", ...]
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
			"keywords": [...]
		},
		{
			"title": "Exam 2",
			"questions": [...],
			"keywords": [...]
		},
	]
	```
