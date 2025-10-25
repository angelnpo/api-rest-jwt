# Read Me First

The following was discovered as part of building this project:

-   The original package name 'ec.com.api.api-rest-jwt' is invalid and this project uses 'ec.com.api.api_rest_jwt' instead.

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

-   [Official Gradle documentation](https://docs.gradle.org)
-   [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.7/gradle-plugin)
-   [Create an OCI image](https://docs.spring.io/spring-boot/3.5.7/gradle-plugin/packaging-oci-image.html)
-   [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.7/reference/using/devtools.html)
-   [Spring Web](https://docs.spring.io/spring-boot/3.5.7/reference/web/servlet.html)
-   [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.7/reference/data/sql.html#data.sql.jpa-and-spring-data)
-   [Spring Security](https://docs.spring.io/spring-boot/3.5.7/reference/web/spring-security.html)

### Guides

The following guides illustrate how to use some features concretely:

-   [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
-   [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
-   [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
-   [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
-   [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
-   [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
-   [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Additional Links

These additional references should also help you:

-   [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Run app

Run with task gradle bootRun

```
./gradlew -Pmode=commandLine -Pdebug=1 -Dorg.gradle.java.home=/opt/java/jdk-21.0.6
```

## Generate keys

### OpenSSL

Example of how to generate public and private RSA keys with OpenSSL. Not used in this project.

```
openssl genrsa -out src/main/resources/certificates/keypair.pem 2048
```

Extract public key of keypair.pem

```
openssl rsa -in src/main/resources/certificates/keypair.pem -pubout -out src/main/resources/certificates/public.pem
```

Extract the private key from keyPair

-   Where:

    pkcs8 -topk8: convert it to PKCS8 format

    -inform PEM -nocrypt: indicates that the private key should not be encrypted

```
openssl pkcs8 -topk8 -inform PEM -nocrypt -in src/main/resources/certificates/keypair.pem -out src/main/resources/certificates/private.pem
```

### Java

To generate the keys from Java, run the GenerateKeyPair.java class.
