# Easy Exam REST API
### SpringBoot + JPA + Postgres

TODOS:
-----
- Integrate ElasticSearch

Setup
-----
- Change database connection config in `src/main/resources/application.properties`
- Install maven dependencies ``mvn install``
- Run the app using ``mvn spring-boot:run``
- Browse ``http//localhost:8080/``
- Create package for deployment using ``mvn package`` (change properties if required)

Authentication
----------------
- JWT
- Frontend should set a header: "Authorization: Bearer 'token'"

API Doc & Sample (WIP)
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
