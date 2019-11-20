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

- Get Question
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
