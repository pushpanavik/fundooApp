package com.bridgeit.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class Url {

	public static String getUrl(HttpServletRequest request,String path) throws MalformedURLException
	{
		URL url=new URL(request.getRequestURL().toString());
		String redirectUrl=url.getProtocol() + "//" +url.getHost() + ":" +url.getPort() +
			 request.getContextPath();
		
		return redirectUrl;
	}
	
	public static String addLink(String link)
	{
		return "<html>\n" + "<body>\n" 
				+ "<a href='"+ link +"'>Click here to activate your account!!!</a>\n" +
				 "</body>\n" +
				"</html>";
	}
}
