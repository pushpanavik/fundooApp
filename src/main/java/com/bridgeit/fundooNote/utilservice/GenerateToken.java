package com.bridgeit.fundooNote.utilservice;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateToken {
	private static String KEY="ARefbnionUIO";
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
}
