## TSBank. Using **Clean Architecture** appoarch

## Introduction
Side project implemented core functions of the online banking system.
## Project structure
```
tsbank/
├── entity/                # Domain Entities (Domain Layer)
├── usecase/               # Application Business Rules (Use Case Layer)
├── infrastructure-dao-sqlserver/   # Infrastructure: SQL Server DAO
├── infrastructure-dao-inmemory/    # Infrastructure: In-Memory DAO
├── infrastructure-kafka/           # Infrastructure: Kafka Adapter
├── infrastructure-restapi/         # Interface Adapter: REST API
├── pom.xml
└── README.md
```
## Technologies
- Spring Boot
- SQL Server: database for this project
- Kafka: use to communicate with TicSys to process the order payment.

## Features
- Deposit
- Withdraw
- Transfer
- Open account
- Check transation history