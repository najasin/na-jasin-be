# Na Jasin BackEnd

## Introduce

> ### `나 자신`(Na Jasin) Project BackEnd Repository

## Skill Stack

- Java 17.0.2
- Gradle 8.2.1
    - checkStyle
    - jacoco
- Spring Boot 3.1.2
    - Spring Data Jpa, QueryDsl
    - Spring Data Redis
    - Spring Security, OAuth2.0, Jwt
    - Swagger
    - JUnit, Mockito
- AWS
    - EC2 (ubuntu)
    - RDS (MySQL Default 8)
    - S3
    - ElasticCache for Redis
- CICD DevOps
    - Github Actions
    - AWS
      - Route 53 
      - S3
      - CodeDeploy
    - Docker
    - Nginx
    - Cerbot

## Build and Run

```shell
$ git clone https://github.com/najasin/na-jasin-be.git
$ ./gradlew clean build
$ docker build --platform linux/amd64 --build-arg DEPENDENCY=build/dependency -t najasin .
$ docker run --restart=always --name=najasin -d -p 8080:8080 najasin
```