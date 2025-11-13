# unit-test-demo-app

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](CHANGELOG.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit79_unit-test-demo-app&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit79_unit-test-demo-app)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit79_unit-test-demo-app&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit79_unit-test-demo-app)
[![License: MIT](https://img.shields.io/badge/License-MIT-teal.svg)](https://opensource.org/licenses/MIT)
[![code of conduct](https://img.shields.io/badge/conduct-Contributor%20Covenant-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)

## Quickstart

Requirement :

* maven 3.9.x
* java 21+ (GraalVM for native version)

1. Verify the app

```shell
mvn verify
```

2. Start the app

```shell
mvn quarkus:dev
```

3. Try the app

Open the [swagger-ui](http://localhost:8080/q/swagger-ui/)

Test available paths (for instance : [/doc/example.md](http://localhost:8080/doc/example.md))

## Note sugli unit test

Le classi di test principali sono : 

- [DocResourceTest](src/test/java/test/org/fugerit/java/demo/unittestdemoapp/DocResourceTest.java), testa i casi positivi
- [DocResourceSicurezzaTest](src/test/java/test/org/fugerit/java/demo/unittestdemoapp/DocResourceSicurezzaTest.java), test di sicurezza, in particolare gli accessi non autorizzati
- [MockJwtHelperTest](src/test/java/test/org/fugerit/java/demo/unittestdemoapp/MockJwtHelperTest.java), test l'eccezione generica nel parsing del JWT tramite mockito e quarkus mock