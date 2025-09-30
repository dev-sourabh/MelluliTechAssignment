# Spring Backend Deployment on Tomcat

## Environment
- **Java:** 21
- **Maven:** 3.9.11
- **Tomcat:** 11.0.11
- **Database:** Postgres 17

---

## Added jetty

```bash
mvn jetty:run
````

## Steps

1. **Build the WAR**

```bash
mvn clean package
```

2.	Deploy to Tomcat

```bash
cp target/spring-rest-api.war /opt/homebrew/Cellar/tomcat/11.0.11/libexec/webapps/
```

3.	Start Tomcat

```bash
catalina run
```

4.	Access the Application

```bash
http://localhost:8080/spring-rest-api/
```


