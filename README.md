# Spring 4.0.0.RELEASE Template for MVC with Embedded Tomcat 7.0.47

This project is minimal setting of Spring 4 MVC. maybe.

## Before use this project

 - Java 1.6 and later
 - favorite text editor or Eclipse or STS.
 - Experience of Spring MVC.

## How to?

 - Download
 - `mvn install`
 - `java -jar "PROJECT.jar" class net.sample.Application`
 - `http://localhost:8080`

## Goals

 - resolve Static Resource.
 - JSTL, other template (ex. Thymeleaf) support.
 - Websocket support with Spring 4, STOMP.
 - User-friendly port change. (If you aleady using 8080.)
 - **I wanna make to Production use!!!**

## Known Issues

 - View page solved. When get context, you must call `Tomcat.initWebappDefaults(context);`
 - Static resources still not loaded.
 - JSTL still not work. plz help!
 - **NOT STABLE. PLZ DO NOT USE THIS AT PRODUCTION YET.**