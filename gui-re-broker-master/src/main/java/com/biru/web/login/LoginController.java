package com.biru.web.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.LoginAttemptComponent;
import com.biru.component.SessionComponent;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(REST.LOGIN)
public class LoginController {
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private InMemoryUserDetailsManager  inMemoryUserDetailsManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private LoginAttemptComponent loginAttemptComponent;
	
	@RequestMapping(value = REST.L_LOGIN_FORM, method = RequestMethod.GET)
	public String login(@RequestParam(name = "error", defaultValue = "false")String error, Model model) {
		model.addAttribute("error", error);
		return HTML.LOGIN;
	}
	
	@RequestMapping(value = REST.L_LOGIN_PAGE_ITEM, method = RequestMethod.GET)
	public @ResponseBody String loginItem() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
				
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(new HashMap<String, Object>()), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.L_LOGIN_PAGE_ITEM + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
		
		
	}
	
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void create(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpg");
		int iTotalChars = 6;
		int iHeight = 40;
		int iWidth = 150;
		Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
		Random randChars = new Random();
		String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, iTotalChars);
		BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();
//		int iCircle = 15;
//		for (int i = 0; i < iCircle; i++) {
//			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
//		}
		g2dImage.setFont(fntStyle1);
		for (int i = 0; i < iTotalChars; i++) {
//			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
			g2dImage.setColor(new Color(255, 255, 255)); // warna huruf putih
			if (i % 2 == 0) {
				g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
			} else {
				g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
			}
		}
		OutputStream osImage = response.getOutputStream();
		ImageIO.write(biImage, "jpeg", osImage);
		g2dImage.dispose();
		session.setAttribute("captcha_security", sImageCode);
	}
	
	@RequestMapping(value = "/validate-captcha", method = RequestMethod.POST)
	public @ResponseBody Object validateCaptcha(HttpSession session, @RequestBody Map<String, Object> data) {
		String captcha = session.getAttribute("captcha_security").toString();
		String verifyCaptcha = data.get("captcha").toString();
		data.clear();
		
		if (captcha.equals(verifyCaptcha)) {
			data.put("status", "success");
			data.put("message", "");
		} else {
			data.put("status", "error");
			data.put("message", "Wrong Captcha");
		}
		return data;
	}
	
	@RequestMapping(value = REST.L_LOGIN_RESET_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody Object resetPassword(@RequestBody Map<String, Object> data) throws JsonProcessingException {
		try {
			String username = data.get("username").toString();
			UserDetails u = inMemoryUserDetailsManager.loadUserByUsername(username);
			System.out.println(u);
			String password = UUID.randomUUID().toString().substring(0, 8);
			
			inMemoryUserDetailsManager.updatePassword(u, passwordEncoder.encode(password));
			
			data.put("password", password);
			
			System.out.println(password);
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					
			HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(data), httpHeaders);
				
			String uri = sessionComponent.getHost() + URI.L_LOGIN_RESET_PASSWORD + "?tenantId=" + sessionComponent.getTenantId();
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
			data.put("status", "success");
			return response.getBody();	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		data.put("status", "failed");
		
		return data;
	}
	
	@RequestMapping(value = REST.L_LOGIN_LOGOUT, method = RequestMethod.GET)
	  public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response){
	        
	      HttpSession session = request.getSession(false);
	      SecurityContextHolder.clearContext();

	      session = request.getSession(false);
	      if(session != null) {
	          session.invalidate();
	      }

	      for(Cookie cookie : request.getCookies()) {
	          cookie.setMaxAge(0);
	      }

	      return "redirect:/login/form?logout";
	  }
	
	@RequestMapping(value = REST.L_LOGIN_IS_USER_LOKED, method = RequestMethod.POST)
	public @ResponseBody Object isUserLocked(@RequestBody Map<String, Object> data) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		System.out.println(data);
				
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(data), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.L_LOGIN_IS_USER_LOCKED + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.L_LOGIN_SYNC_USER, method = RequestMethod.GET)
	public @ResponseBody Object syncUser() throws JsonProcessingException {
		loginAttemptComponent.syncronizeUser();
		return "User sync!";
	}
}