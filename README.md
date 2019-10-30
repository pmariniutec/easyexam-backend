# Easy Exam REST API
### SpringBoot + JPA + Postgres

### TODO: Integrate ElasticSearch

Setup
-----
- Change database connection config in `src/main/resources/application.properties`
- Install maven dependencies using IDE auto import or using the command ``mvn install``
- Run the app using ``mvn spring-boot:run``
- Browse ``http//localhost:8080/api/v1/users``
- Create package for deployment using ``mvn package`` (change properties if required)

Authentication
----------------
- JWT
- Frontend should set a header: "Authorization: Bearer 'token'"

API Doc & Sample
----------------
- List all users 
    ```
    GET /api/v1/users
    ```

- Create new user 
    ```
    POST /api/v1/users
    ```

    Body:
    ```
    {
        "first_name": "First",
        "last_name": "Last",
        "email": "EMAIL",
    }
    ```

- Get specific user 
    ```
    GET /api/v1/users/1
    ```

- Update user
    ```
    PUT /api/v1/users
    ```
    Body:
    ```
    {
        "id":1,
        "name": "Jeffrey Way",
        "email": "jeffrey@laracasts.com",
        "mobile": "0123456789"
    }
    ```
    
- Delete user
    ```
    DELETE /api/v1/users/1
    ```
