# Coding Challenge

In this repository I solve a coding challenge.

This code mostly demonstrates that I'm able to code. It is not my finest work. I focused more on showing a "full stack" of things (e.g. incl. CI/CD), instead of a perfect API
implementation or perfect architecture.

## Technologies

* Java 21
* Spring Boot 3
* Spring WebMVC for the API
* Spring Data JPA for persistence (instead of QueryDSL for reasons I can elaborate)
* springdoc-openapi brings a Swagger UI
* docker compose for local development
* PostgreSQL as a database (16 because that's what Azure currently has)
* Flyway for schema migrations
* Some devtools, incl. spring-boot-docker-compose
* JUnit, WebTestClient + AssertJ for a simple integration test
* `demo.http` file shows how to use the API

## Design & Architecture

Honestly, not a lot of architecture to see here.

* Layered architecture (Contoller, Service, Persistence), as opposed to onion/hexagonal, because with WebMVC and JPA decoupling the layers is not worth the effort
* Separate DTOs (as Java Records) from Entities (as standard Java classes), because that's *definitely* worth the low effort
* Package-per-domain/use-case, because I find this better than package-per-layer for reasons I can elaborate
* Flat/no-packages until modules get too big, and not earlier (though the Java "one thing per file" convention makes me re-consider this opinion...)

## TODO / Shortcomings

* [ ] Test is very minimal, I can elaborate more on testing strategy
* [x] Linting via sonarlint
* [x] [CI/CD via GitHub Actions](https://github.com/Kampfgnom/gbtec-challenge/actions/workflows/integration.yaml)
* [ ] Observability: Logs, metrics, etc. via Spring standard technologies (micrometer etc.)
* [ ] Security: User management and login (e.g. via OIDC provider integration) and access control (via Spring Security)
