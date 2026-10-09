# Mini Shop – Spring Boot shopping app

Small web shopping app for practising Docker, Jenkins and Kubernetes.

- **Backend:** Java 21, Spring Boot 3.3, Maven (REST API, in-memory data, no database)
- **Frontend:** plain HTML/CSS/JS in `src/main/resources/static`, served by the same Spring Boot app
- **Output:** one runnable jar: `target/shopping-app.jar`, listening on port **8080**

## Run locally

    mvn clean package        # compiles, runs tests, builds target/shopping-app.jar
    java -jar target/shopping-app.jar

Open http://localhost:8080

## Endpoints

| Method | URL                      | Purpose                  |
|--------|--------------------------|--------------------------|
| GET    | /                        | Shop UI                  |
| GET    | /api/products            | List products            |
| GET    | /api/products/{id}       | One product              |
| POST   | /api/orders              | Place an order           |
| GET    | /api/orders              | List placed orders       |
| GET    | /actuator/health         | Health check (probes)    |

Sample order:

    curl -X POST localhost:8080/api/orders -H "Content-Type: application/json" \
         -d '{"items":[{"productId":1,"quantity":2}]}'


