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

 - **Fully Websocket!**
 - **I wanna make Production use!!!**
 - JSP forward (but less PRIORITY. because I'll not use JSP View at my production.)

## Changelog

 - View page solved. When get context, you must call `Tomcat.initWebappDefaults(context);`
 - Static resources solved. `registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/")`
 - jade4j and Thymeleaf support (but with Thymeleaf, JSP not work. why?)
 - JSP resolver removed dut to unable forward JSP file in JAR. use Thymeleaf or jade4j instead until solve this problem.
 - parameterized port change and tomcat workdir changeable. `java -jar "PROJECT.jar" port=8081 basedir=/path/to/tomcat/workdir`
 - Websocket support by [puryfury](https://github.com/puryfury). thanks.
 - embedded Tomcat will run with Non-blocking HTTP IO (Http11NioProtocol).

## Websocket notes

 - This example will running with SockJS websocket emulation. If you want native websocket, convert this to war project and run with server below,
   + Tomcat 8
   + Jetty 9
   + Glassfish 4.0
 - looking for similar project? google or check out project below.
   + [rstoyanchev / spring-websocket-test](https://github.com/rstoyanchev/spring-websocket-test)
 - If you want technical infomation, see below,
   + [JSR-356 Java EE 7 Specification](https://www.jcp.org/en/jsr/detail?id=356)
   + [SockJS](http://sockjs.org)
   + [STOMP](http://stomp.github.io/)

## Known Issues

 - Why can't forward JSP file in JAR? It's possible in Servlet 3.0 but not affect in embedded tomcat.
 - **NOT STABLE. PLZ DO NOT USE THIS AT PRODUCTION YET.**