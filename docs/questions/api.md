Questions API Docs
----------------

- Create Question
    ```
    POST /api/question/create
    ```
    Expects:
    ```
    {
		"content": "CONTENT",
        "keywords": ["KEY1", "KEY2"] // OPTIONAL
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
        "keywords": ["KEY1", "KEY2", ...],
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
        "keywords": ["KEY1", "KEY2", ...],
		"content": "CONTENT",
		"created": "date",
		"updated": "date"
	}
	```
