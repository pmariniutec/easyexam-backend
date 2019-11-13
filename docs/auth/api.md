Authentication API Docs
----------------

- Login user
    ```
    POST /api/auth/login
    ```
    Body:
    ```
    {
        "email": "EMAIL",
		"password": "PASSWORD"
    }
    ```

- Create new user 
    ```
    POST /api/auth/register
    ```

    Body:
    ```
    {
        "name": "NAME",
        "email": "EMAIL",
		"password": "PASSWORD",
		"role": "ROLE"
    }
    ```
