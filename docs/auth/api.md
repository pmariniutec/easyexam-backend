Authentication API Docs
----------------
- NOTE: Possible user roles are: `['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT']`

- Login user
    ```
    POST /api/auth/login
    ```
    Expects:
    ```
    {
        "email": "EMAIL",
		"password": "PASSWORD"
    }
    ```

	Returns:
	```
		Success
	```

- Create new user 
    ```
    POST /api/auth/register
    ```

    Expects:
    ```
    {
        "name": "NAME",
        "email": "EMAIL",
		"password": "PASSWORD",
		"role": [roles]
    }
    ```

	Returns:
	```
		Success
	```

- Reset password
    ```
    POST /api/auth/password/reset
    ```
    Expects:
    ```
    {
        "email": "EMAIL"
    }
    ```
    Returns:
    ```
    Successful request. New password sent to email.
    ```
