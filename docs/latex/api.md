Latex API Docs
----------------

- Compile Latex by String
    ```
    POST /api/latex/compile
    ```

	Headers:
    ```
	Content-Type: application/json
    ```

    Expects:
    ```
	"LATEX STRING"
	NOTE: Replace Newlines with \n
    ```

	Returns:
	```
	PDF binary data	
	```

- Compile Exam
    ```
    POST /api/latex/compile
    ```
	Headers:
    ```
	Content-Type: application/json
    ```

    Expects:
    ```
    {
        title: "TITLE",
        questions: [{"content": "CONTENT"}, ...],
        courseId: COURSEID
    }
    ```

	Returns:
	```
	PDF binary data	
	```
