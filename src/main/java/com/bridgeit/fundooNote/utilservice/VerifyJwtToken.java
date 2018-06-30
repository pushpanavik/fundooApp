package com.bridgeit.fundooNote.utilservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class VerifyJwtToken {
	private static final String KEY="piyush19";
	
	public static int getId(String token)
	 {
		 int id=0;
		 System.out.println("token is:" +token);
		 Claims claim = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
		 id=Integer.parseInt(claim.getId());
		 return id;
	 }
}