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
        "questions": [{"content": "CONTENT" }, {"content": "CONTENT" }, ...],
        "keywords": ["key1", "key2", ...],
        "courseId" : "ID" // OPTIONAL
    }
    ```

	Returns:
	```
    {
        "id": "examId",
        "message": "Successfully created Exam."
    }
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
            "course": {...}
		},
		{
			"title": "Exam 2",
			"questions": [...],
			"keywords": [...],
			"created": "date",
			"updated": "date",
            "course" : null
		},
	]
	```

- Get User Exams by Id
    ```
    GET /api/exam/:examId
    ```

	Returns:
	```
    {
        "created": "2019-11-24T21:26:19.437+0000",
        "updated": "2019-11-24T21:26:19.511+0000",
        "id": 5,
        "title": "TITLE",
        "questions": [{...}],
        "solutions": [{...}],
        "keywords": [
            "algorithms", ...
        ],
        "course": {...}
    }
	```

- Update Exam
    ```
    PATCH /api/exam/:examId
    ```
    Expects:
    ```
    {
        "title": "TITLE",
        "questions": [{ "content": "CONTENT" }, ...],
        "solutions": [{...}, ...],
        "keywords": ["KEYWORD", ...]
    }
    ```
	Returns:
	```
    Successfully updated the exam."
	```

- Delete Exam
    ```
    DELETE /api/exam/:examId
    ```
    Returns:
    ```
    Successfully deleted Exam with id: {examId}
    ```
