Latex API Docs
----------------

- Compile Latex
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
