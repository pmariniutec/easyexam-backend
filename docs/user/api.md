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
    {
        "created": "2019-11-24T21:04:24.467+0000",
        "updated": "2019-11-24T23:59:18.927+0000",
        "id": 1,
        "points": 0,
        "firstName": "FIRSTNAME",
        "lastName": "LASTNAME",
        "email": "EMAIL",
        "roles": [
            {
                "id": 1,
                "name": "ROLE_"
            }
        ],
        "fullName": "FIRSTNAME LASTNAME"
    }
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
        "points": [<NEW VALUE>] //optional
    }
    ```
    
    Returns (if successful):
    ```
    Successfully updated the user.
    ```
