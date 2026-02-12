# unit-test-demo-app

Questo progetto mostra come è possibile definire dei criteri minimi di compliance per un progetto attraverso delle JUnit opportunamente etichettate (tag).

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](CHANGELOG.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit79_unit-test-demo-app&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit79_unit-test-demo-app)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit79_unit-test-demo-app&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit79_unit-test-demo-app)
[![License: MIT](https://img.shields.io/badge/License-MIT-teal.svg)](https://opensource.org/licenses/MIT)
[![code of conduct](https://img.shields.io/badge/conduct-Contributor%20Covenant-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)

## Quickstart

Requirement :

* maven 3.9.x
* java 21+

1. Verify the app

```shell
mvn verify
```

Per verificare la presenza degli unit test di sicurezza 

```shell
mvn verify -P security
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

## Security Junit con tagging

### Definizione dei tag di sicurezza

Definiamo i gruppi di test con cui vogliamo classificare i nostri test, ad esempio

* authorized, per i test che dovrebbero concludersi positivamente (nel caso di servizi rest, ad esempio 200 o 201)
* unauthorized, per i test che dovrebbero terminare con un errore per un utente non esistente e non configurato correttamente (ad esempio JWT mancante o non valido. Nel caso di servizi rest un 401). 
* forbidden, per un utente che è formalmente valido ma non ha i permessi (es. ruoli) per accedere ad una specifica funzione (nel caso dei servizi rest un 403).
* security, tag gnerico per qualsiasi altro controllo di sicurezza ritenuto necessario.

Ecco un esempio di test 'forbidden'

```java
    @Test
@Tag("security")
@Tag("forbidden")
void testMarkdown403NoAdminRole() {
    given()
            .header("Authorization", "Bearer " + DocResourceTest.JWT_USER2)
            .when().get("/doc/example.md").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
}
```

### Verifica presenza test

Ci sono vari modi per verificare la presenza di test sui tag definiti.

Per questa demo useremo il più semplice, ovvero andremo a verificare con
il [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
che sia presente almeno un test per ogni tag.

Questo può essere fatto con una execution del plugin per ogni tag, es.

```xml
<execution>
    <id>verify-security-tests</id>
    <phase>test</phase>
    <goals>
        <goal>test</goal>
    </goals>
    <configuration>
        <groups>security</groups>
        <failIfNoTests>true</failIfNoTests>
    </configuration>
</execution>
```

Nel nostro caso attiviamo questo controllo con il profilo 'security'

```shell
mvn verify -P security
```

NOTA: ovviamente è possibile usare questo meccanismo per verificare anche altri tag custom definiti dallo sviluppatore.

### Note su test e coverage

Un effetto collaterale dell'utilizzo del profilo 'security' è che vengono eseguiti solo i test con i tag definiti.

Nella nostra CI per ovviare a questa situazione, abbiamo separato lo step di verifica da quello per il calcolo del quality gate e coverage.

```yaml
      - name: Check security unit test tags
        run: mvn verify -P security
      - name: Build and analyze
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: mvn -B clean install org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.organization=fugerit79 -Dsonar.projectKey=fugerit79_unit-test-demo-app
```

NOTA: in futuro potremmo rendere più robusto il meccanismo, ad esempio con un sistema più personalizzabile di verifica (es. custom maven plugin).