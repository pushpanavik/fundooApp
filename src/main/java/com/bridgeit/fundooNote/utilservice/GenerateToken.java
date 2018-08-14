package com.bridgeit.fundooNote.utilservice;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateToken {
	private static String KEY="piyush19";
	public static String generateToken(int id)
	{
		long currentTime=System.currentTimeMillis();
		Date currentDate=new Date(currentTime);
		Date expireDate=new Date(currentTime+ 24*60*60*1000);
		
		
		String getToken=Jwts.builder()
				.setId(Integer.toString(id))
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS256,KEY)
				.compact().toString();
		
		return getToken;
	}
	
	public static String generateUserToken(String firstname,String lastname,int id,String email) {
		long currentTime=System.currentTimeMillis();
		Date currentDate=new Date(currentTime);
		Date expireDate=new Date(currentTime+ 24*60*60*1000);
		
		String fullName=firstname +lastname;
		
		String getToken=Jwts.builder()
				.setId(Integer.toString(id))
				.setIssuer(fullName)
				.setSubject(email)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS256,KEY)
				.compact().toString();
		
		return getToken;
		
	}
}
