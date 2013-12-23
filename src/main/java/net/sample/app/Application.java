package net.sample.app;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.deploy.ApplicationListener;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Application {
	
	private final static String ROOT = "/";
	private final static String SERVLET = "appServlet";
	
	private final static Log log = LogFactory.getLog(Application.class);
	
	public static void main(String[] args) throws Exception {
		
		final File tmp = createBaseDirectory();
		final Tomcat tomcat = new Tomcat();
		File appbase = null;
		int port = 8080;
		
		//인자 파싱
		List<String> argkeys = Arrays.asList("appbase","port");
		for(String arg : args){
			if(arg.contains("=")){
				String[] kv = arg.split("=");
				if(kv.length == 2){
					switch(argkeys.indexOf(kv[0])){
						case 0://appbase
							appbase = new File(kv[1]);
							if(!appbase.isDirectory() || !appbase.exists()){
								log.warn("path '"+kv[1]+"' is not vaild directory or not exists.");
								appbase = tmp;
							}
							break;
						case 1://port
							try{
								port = Integer.parseInt(kv[1]);
							}catch(NumberFormatException e){
								log.warn("port '"+kv[1]+"' is not vaild port.");
							}
							break;
					}
				}
				
			}
			
		}
		
		ServerSocket socket = null;
		try{
			socket = new ServerSocket(port);
		}catch(IOException e){
			log.error("port " + port + " is already in use. please try another port.");
			System.exit(1);
			return;
		}finally{
			socket.close();
		}
		
		log.info("Tomcat port: " + port);
		log.info("Tomcat workdir: " + tmp.getAbsolutePath());
		
		tomcat.setPort(port);
		tomcat.setBaseDir(tmp.getAbsolutePath());
		
		Host host = tomcat.getHost();
		host.setAppBase(tmp.getAbsolutePath());
		host.setAutoDeploy(true);
		host.setDeployOnStartup(true);
		
		//web.xml 구현
		Context context = tomcat.addContext(ROOT, "");
		Tomcat.initWebappDefaults(context);//기본형 web.xml 구현화 (필수)
		
		context.setLoader(new WebappLoader(Thread.currentThread().getContextClassLoader()));
		
		//Spring 리스너 및 AppServlet 구성
		context.addApplicationListener(new ApplicationListener(ContextLoaderListener.class.getName(), false));
		Wrapper appServlet =  tomcat.addServlet(ROOT, SERVLET, new DispatcherServlet(new AnnotationConfigWebApplicationContext(){{
			register(ServletConfig.class);
		}}));
		appServlet.setAsyncSupported(true);
		appServlet.setLoadOnStartup(1);
		context.addServletMapping(ROOT, SERVLET);
		
		//???
		context.addParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
		context.addParameter("contextConfigLocation", AppConfig.class.getName());
		
		// Non-blocking IO 프로토콜로 작동
		Connector connector = new Connector(Http11NioProtocol.class.getName());
		connector.setPort(port);
		connector.setProperty("address", InetAddress.getByName("localhost").getHostAddress());
		connector.setXpoweredBy(false);
		connector.setURIEncoding("UTF-8");
		connector.setAttribute("connectionTimeout", "3000");
		connector.setAttribute("useComet", "false");
		connector.setAttribute("socket.directBuffer", "true");
		connector.setAttribute("pollerThreadCount", "2");
		connector.setAttribute("maxThreads", "800");
		connector.setAttribute("processorCache", "800");
		tomcat.getService().addConnector(connector);
		tomcat.setConnector(connector);
		
		
		//톰캣 시작
		tomcat.start();
		tomcat.getServer().await();
	}

	private static File createBaseDirectory() throws IOException {
		final File base = File.createTempFile("tmp-", "");

		if (!base.delete()) {
			throw new IOException("Cannot (re)create base folder: "
					+ base.getAbsolutePath());
		}

		if (!base.mkdir()) {
			throw new IOException("Cannot create base folder: "
					+ base.getAbsolutePath());
		}

		return base;
	}
}
