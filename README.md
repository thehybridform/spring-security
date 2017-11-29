# spring-security

Requires jdk1.8.x and maven

```bash
mvn clean package
mvn jetty:run
```

```html
http://localhost:8080
```

#### Users
| Username | Password | Roles         |
| :------: | :------: | :-----------: |
| super    | password | ADMIN, MEMBER |
| admin    | password | ADMIN         |
| member   | password | MEMBER        |
