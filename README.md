# spring graphql

## Setup

```shell
sdk rm java
sdk i java 17.0.6-zulu
java --version

sdk rm gradle 
sdk i gradle
gradle --version

sdk i springboot
sdk i quarkus
```

## Run

```shell
gradle clean bootRun
gradle test
```

## Test

http://localhost:8080/graphiql
http://localhost:8080/h2-console

## Maintain

```shell
gradle flywayInfo
gradle flywayMigrate
gradle flywayRepair
gradle flywayValidate
```