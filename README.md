# AI-Powered SonarQube Fix Assistant

An AI-driven platform that automatically imports SonarQube issues, generates code fixes using local LLMs (Ollama + Llama3), stores suggestions in PostgreSQL, and produces PDF reports.

## Features

* SonarQube Integration
* Automatic Issue Synchronization
* AI-Based Fix Suggestions
* Spring AI + Ollama Integration
* PostgreSQL Persistence
* PDF Report Generation
* Scheduled Report Automation
* Dockerized Infrastructure

## Architecture

SonarQube → Spring Boot → PostgreSQL → Ollama (Llama3) → PDF Reports

## Tech Stack

* Java 21
* Spring Boot 3
* Spring Data JPA
* PostgreSQL
* SonarQube
* Spring AI
* Ollama
* Docker
* Maven

## Modules

### sonar-module

* SonarQube API Integration
* Issue Synchronization

### ai-module

* AI Fix Generation
* Ollama Integration

### report-module

* PDF Report Generation
* Scheduled Reporting

### common-module

* Shared DTOs
* Common Utilities

### application-module

* Application Bootstrap

## Setup

### Start Infrastructure

```bash
docker compose up -d
```

### Run Application

```bash
mvn clean install
mvn spring-boot:run
```

### Sync Sonar Issues

```http
POST /api/sonar/sync/{projectKey}
```

### Generate AI Fix

```http
POST /api/ai/fix/{issueId}
```

### Generate PDF Report

```http
GET /api/reports/pdf/{projectKey}
```

## Future Enhancements

* Email Delivery of Reports
* JWT Authentication
* Multi-Model AI Support

## Author

Pranav Kumar Jha
Senior Java Developer# AI-Sonarqube-Fix-Assistant