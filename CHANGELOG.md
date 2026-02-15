# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [2.0.0] - 2026-02-15

### Added 

- CodeQL workflow
- CI status badge

### Changed

- adesso viene usato un JWT generato e consumato con chiavi interne <https://github.com/fugerit79/unit-test-demo-app/issues/11>
- quarkus version 3.31.1
- junit5-tag-check-maven-plugin version 1.2.2
- revisione README

## [1.2.0] - 2026-01-29

### Changed

- utilizzo del plugin [junit5-tag-check-maven-plugin](https://github.com/fugerit-org/junit5-tag-check-maven-plugin) invece di surefire per il controllo tag
- il profilo di verifica dei controlli ora si chiama 'security'
- quarkus version 3.31.1

## [1.1.0] - 2025-11-24

### Added

- output in PDF (autorizzazione ADMIN e USER)
- profilo pom 'release', verifica che sia presente almeno un test per ogni tag "security", "authorized", "unauthorized", "forbidden"
- junit tags "security", "authorized", "unauthorized", "forbidden"
- test eccezione generica

### Changed

- Divisione tra 401 unauthorized quando l'utente non esiste e 403 forbidden quando non ha i ruoli necessari
- la CI viene eseguita anche sul ramo develop
- suddivisione test normali (DocResourceTest) / test sicurezza (DocResourceSicurezzaTest)

## [1.0.0] - 2025-11-12

### Added

- Gestione autorizzazioni e relative junit
- Devcontaienr Java 21 + Maven 3.9
- Progetto di esempio per le unit test
