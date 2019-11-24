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

- Update Exam
    ```
    PATCH /api/exam/{examId}
    ```
    Expects:
    ```
    {
        "title": "TITLE", // Optional
        "questions": [{ "content": "CONTENT" }, { "content": "CONTENT" }, ...], //optional
        "keywords": ["key1", "key2", ...], //Optional
        "courseId" : "ID" // NOT REQUIRED //Optional
    }
    ```

	Returns:
	```
    {
        "message": "Successfully updated the exam."
    }
	```
