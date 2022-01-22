package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns = "/UserLogin",
		initParams = {
				@WebInitParam(name = "user", value = "David"),
				@WebInitParam(name = "password", value = "123")
		}
)
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");
		
		String userInput = request.getParameter("user");
		String pwdInput = request.getParameter("password");
		
		System.out.println(" user: " + user + " input: " + userInput);
		System.out.println(" pwd: " + password + " input: " + pwdInput);
		
		Pattern pattern = Pattern.compile("^([A-Z][a-zA-Z]{2,}[ ]?)+$");
		Matcher matcher = pattern.matcher(userInput);
		if(!matcher.matches()) {
			System.out.println(" Invalid user");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.include(request, response);
			return;
		}
		
		if (user.equalsIgnoreCase(userInput) && password.equalsIgnoreCase(pwdInput)) {
			
			System.out.println(" Correct user");
			request.setAttribute("user", user);
			request.getRequestDispatcher("/LoginSuccess.jsp").forward(request, response);
		}
		
		else {
			System.out.println(" Incorrect user");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.include(request, response);
		}
	}

}
