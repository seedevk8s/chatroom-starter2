package edu.udacity.java.nano;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.path.loginPath}")
	private String loginPath;

	@Value("${spring.path.chatPath}")
	private String chatPath;

	public static void main(String[] args) {
		SpringApplication.run(WebSocketChatApplication.class, args);
	}

	/**
	 * Login Page
	 */
	@GetMapping("/")
	public ModelAndView login() { return new ModelAndView(loginPath); }

	/**
	 * Chatroom Page
	 */
	@GetMapping("/index")
	public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {

		// add code for login to chatroom.
		// Set the username as "CUSTOMER" in case the login input is empty
		if(StringUtils.isEmpty(username)) {
			username = "CUSTOMER";
		}

		ModelAndView modelAndView = new ModelAndView(chatPath);

		modelAndView.addObject("applicationName", applicationName);
		modelAndView.addObject("chatPath", chatPath);
		modelAndView.addObject("loginPath", loginPath);
		modelAndView.addObject("username", username);
		modelAndView.addObject("webSocketUrl", "ws://"
			+ InetAddress.getLocalHost().getHostAddress()
			+ ":"
			+ request.getServerPort()
			+ request.getContextPath()
			+"/chat");

		return modelAndView;
	}

}




























