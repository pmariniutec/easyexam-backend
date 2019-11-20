Solutions API Docs
----------------

- Create Solution
    ```
    POST /api/solution/create
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

- Get Solution
    ```
    GET /api/solution/{solutionId}
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
