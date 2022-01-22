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

@WebServlet(urlPatterns = "/UserLogin",

		// Setting a couple of initial parameter for the servlet
		initParams = { @WebInitParam(name = "user", value = "David"),
				@WebInitParam(name = "password", value = "hiThere123$") })
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Define the post method for when api calls the endpoint
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// The user and password are retrieved from the initial params of the servlet
		String user = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");

		// The user and password are taken from the request
		String userInput = request.getParameter("user");
		String pwdInput = request.getParameter("password");

		System.out.println(" user: " + user + " input: " + userInput);
		System.out.println(" pwd: " + password + " input: " + pwdInput);

		// Set up the matchers for the user name and the password
		Pattern userPattern = Pattern.compile("^([A-Z][a-zA-Z]{2,}[ ]?)+$");
		Matcher userMatcher = userPattern.matcher(userInput);

		Pattern pwdPattern = Pattern.compile("^(?=^[^\\W_]*[A-Z][^\\W_]*[0-9][^\\W_]*[\\W_][^\\W_]*$).{8,}$");
		Matcher pwdMatcher = pwdPattern.matcher(pwdInput);

		// Use the matcher to check if the user name is a match with the regex pattern
		if (!userMatcher.matches()) {
			System.out.println(" Invalid user");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.include(request, response);
			return;
		}

		// Use the matcher to check if the password is a match with the regex pattern
		if (!pwdMatcher.matches()) {
			System.out.println(" Invalid password");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.include(request, response);
			return;
		}

		// We compare the initial params of the servlet against the user inputs
		if (user.equalsIgnoreCase(userInput) && password.equalsIgnoreCase(pwdInput)) {

			// If it is a match we redirect to the login success page
			System.out.println(" Correct user");
			request.setAttribute("user", user);
			request.getRequestDispatcher("/LoginSuccess.jsp").forward(request, response);
		}

		else {

			// If it is not a match then we redirect to the empty index page with the form
			System.out.println(" Incorrect user");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.include(request, response);
		}
	}

}
