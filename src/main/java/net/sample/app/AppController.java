package net.sample.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {
	
	private final static Logger log = LoggerFactory.getLogger(AppController.class);
	
	@RequestMapping("/sample{ext:\\.\\w+}")
	public String index(@PathVariable("ext") String ext, HttpServletRequest req, Model model){
		
		model.addAttribute("date", DateFormat.getDateInstance(DateFormat.MEDIUM, req.getLocale()).format(new Date()));
		
		log.info("sample with ext : " + ext);
		
		if(".jade".equals(ext)){
			return "jade4j";
		}else if(".tl".equals(ext)){
			return "thymeleaf";
		}else return "main";
	}
	
	@RequestMapping("/")
	public @ResponseBody void htmlString(HttpServletResponse res) throws IOException{
		res.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = res.getWriter();
		
		out.write("<h1>Hello World!</h1>");
		out.write("<ul>");
		out.write("<li><a href='sample.other'>View sample with JSP</a></li>");
		out.write("<li><a href='sample.jade'>View sample with jade4j</a></li>");
		out.write("<li><a href='sample.tl'>View sample with thymeleaf</a></li>");
		out.write("<li><a href='resource/index.html'>View resource</a></li>");
		
		out.write("</ul>");
		
	}
}
