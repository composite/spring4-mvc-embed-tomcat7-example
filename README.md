# Spring 4.0.0.RELEASE Template for MVC with Embedded Tomcat 7.0.47

This project is minimal setting of Spring 4 MVC. maybe.

## Before use this project

 - Java 1.6 and later
 - favorite text editor or Eclipse or STS.
 - Experience of Spring MVC.

## How to?

 - Download or `git clone`
 - `mvn install`
 - `java -jar "PROJECT.jar"`
 - `http://localhost:8080`

## Goals

 - JSP forward (but less PRIORITY. because I'll not use JSP View at my production.)
 - Websocket support with Spring 4, STOMP.
 - User-friendly port change. (If you aleady using 8080.)
 - **I wanna make Production use!!!**

## Changelog

 - View page solved. When get context, you must call `Tomcat.initWebappDefaults(context);`
 - Static resources solved. `registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/")`
 - jade4j and Thymeleaf support (but with Thymeleaf, JSP not work. why?)
 - JSP resolver removed dut to unable forward JSP file in JAR. use Thymeleaf or jade4j instead until solve this problem.

## Known Issues

 - Why can't forward JSP file in JAR? It's possible in Servlet 3.0 but not affect in embedded tomcat.
 - **NOT STABLE. PLZ DO NOT USE THIS AT PRODUCTION YET.**