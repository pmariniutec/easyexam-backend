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
	Success response
	```

- Get Questions
    ```
    GET /api/question
    ```

	Returns:
	```
	[{
		"title": "TITLE",
		"content": "CONTENT",
		"created": "date",
		"updated": "date"
	},...]
	```

- Get Question by Id
    ```
    GET /api/question/{questionId}
    ```

	Returns:
	```
	{
		"title": "TITLE",
		"content": "CONTENT",
		"created": "date",
		"updated": "date"
	}
	```
