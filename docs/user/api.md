User API Docs
----------------

- Get User Data
    ```
    GET /api/user/
    ```
    Expects:
    ```
	Authentication Header
    ```

	Returns:
	```
		UserData
	```
- Patch User Data
    ```
    PATCH /api/user/
    ```
    Expects (only specify the fields that will be updated):
    ```
    {
        "firstName": <NEW VALUE>, //optional
        "lastName": <NEW VALUE>, //optional
        "email": <NEW VALUE>, //optional
        "password": <NEW VALUE>, //optional
        "role": [<NEW VALUE>], //optional
        "points": [<NEW VALUE>] //optional
    }
    ```
    
    Returns (if successful):
    ```
        Successfully updated the user.
    ```
