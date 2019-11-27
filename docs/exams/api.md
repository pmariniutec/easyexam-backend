Exams API Docs
----------------

- Create Exam
    ```
    POST /api/exam/create
    Note: If the id of a question is specified, then such question will be linked to its representation in the db,
    otherwise a new question will be created.
    ```
    Expects:
    ```
    {
        "title": "TITLE",
        "questions": [{"id": OPTIONAL_ID, "content": "CONTENT"}, {"content": "CONTENT" }, ...],
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
			"created": "date",
			"updated": "date",
            "course": {...}
		},
		{
			"title": "Exam 2",
			"questions": [...],
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
