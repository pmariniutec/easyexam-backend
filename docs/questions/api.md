Questions API Docs
----------------

- Create Question
    ```
    POST /api/question/create
    ```
    Expects:
    ```
    {
        "title": "TITLE",
		"content": "CONTENT"
    }
    ```

	Returns:
	```
    {
        "id": ID,
        "message": "Successfully created Question"
    }
	```

- Get Questions
    ```
    GET /api/question
    ```

	Returns:
	```
	[{
        "id": ID,
        title": "TITLE",
		"content": "CONTENT",
		"created": "date",
		"updated": "date"
	},
    {...},
    ...
    ]
	```

- Get Question by Id
    ```
    GET /api/question/{questionId}
    ```

	Returns:
	```
	{
        "id": ID,
		"title": "TITLE",
		"content": "CONTENT",
		"created": "date",
		"updated": "date"
	}
	```
