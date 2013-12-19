package net.sample.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {
	
	@RequestMapping("/sample")
	public String index(HttpServletRequest req, Model model){
		
		model.addAttribute("date", DateFormat.getDateInstance(DateFormat.MEDIUM, req.getLocale()).format(new Date()));
		
		return "index";
	}
	
	@RequestMapping("/")
	public @ResponseBody void htmlString(HttpServletResponse res) throws IOException{
		res.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = res.getWriter();
		
		out.write("<h1>Hello World!</h1>");
		out.write("<a href='sample'>View sample</a>");
		
	}
}
