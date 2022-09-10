# Docker-Spring-Cloud-Gateway

This project was created to learn reactive programming, functional programming and custom api gateway with Spring Cloud Gateway

## How To Run

1. Run docker compose with command `docker-compose up`
2. Run application with command `mvn clean spring-boot:run -Pdev`

## How To Build

1. Run build jar with command `mvn clean install -Pdev`
2. Run build docker image with command `mvn clean docker:build -Pdev`

## Feature

- [x] Dynamic Routing
- [x] Custom Filter
- [x] Logging Request and Response
- [x] Database Migration
- [x] Docker Support
- [x] View api route and applications credential
- [ ] Create api route and applications credential
- [ ] Update api route and applications credential
- [ ] Delete api route and applications credential
- [ ] User Management
- [ ] Cache To Redis
- [ ] Send Log to ELK