# Credit Card Transactin Service  - Domain Driven Design


![Build Status](https://github.com/ecfghjp/credit-card-ddd/actions/workflows/credit-card-prod-ci-cd.yml/badge.svg)


## Contents

  - [Introduction](#introduction)
  - [Requirements](#requirements)
  - [Build Steps](#build-steps)
  - [Validation](#validation)
  - [Code Coverage](#code-coverage)
  - [Quality Check](#quality-check)
  
## Introduction
 
My First attempt at DDD using Spring boot microservices. Appreciate this is a very basic design where I have tried to keep the domain at the heart of the software.
The project follows some patterns and best practices in Domain Driven Design such as :

 - Layered Architecture
 - Rich Domain Models as against aneamic domain models
 - Aggregates
 - Value Objects
 - Onion Architecture
 - Domain Services
 - Repositories
 - CI/CD using Github Actions
 - Docker containirisations
 - Autodeployment to ECS 
 - Sonar Cube Integration
 - Jacoco Integration
 - Dependency Injection

## Requirements

For building and running the application you need:

- [JDK 1.11 and above](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- Assumes you have an account on Sonaqube



## Build Steps

We can run the appplication locally in multiple ways on your local machine.

One way is by running the `main` method of `CreditCardApplication` as a java application. This will start the server on port 9090.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Exception Handling
Exception handling is a very essential part of any Project as correct messages need to be deliveered across to the consumer in case anything fails.
This project returns the exception message with the right http code and message to make cionsumer aware of any issues.

## Validation
The code uses javax.validation framework for implementig data vaidation for requests.
The validation failures are thrown back as exceptions back to consumers with reason and occurence

## Security
Following API best practices are implemented in the project.
- [Enable TLS security](https://tools.ietf.org/html/rfc8446)
- [Test Your Dependencies and Find Spring Boot Vulnerabilities]
- [Enable CSRF Protection] 
- [Always have validations on at the server side and use post requests]
- [Use a Content Security Policy for Spring Boot XSS Protection]
- [Use Open ID connect for authentication]
- [Hash your passwords and use char[] rather than strings]
- [Store Secrets Securely]
- [Do penetration testing]
- [Code reviews from security team -- important]

## Code coverage
 
 The project uses Jacoco plugin for Code coverage
 
## Quality Check
 
 Sonarqube is used for quality checks
 





