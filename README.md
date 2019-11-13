# Easy Exam REST API
### SpringBoot + JPA + Postgres

Dependencies
------------
- ElasticSearch
- Logstash
- Springboot
- Maven
- Java 13

Setup
-----
- Change database connection config in `src/main/resources/application.properties`
- Install ElasticSearch and Logstash (TODO: Add links)
- Install maven dependencies ``mvn install``
- Run the server using ``mvn spring-boot:run``
- Browse ``http//localhost:8080/``
- Create package for deployment using ``mvn package`` (change properties if required)

Sections
--------
- [Authentication API Docs](docs/auth/api.md)
- [ElasticSearch and Logstash](docs/elasticsearch/README.md)


#### Additional Notes:

Authentication
----------------
- JWT
- Frontend should set the following header: "Authorization: Bearer 'token'"
