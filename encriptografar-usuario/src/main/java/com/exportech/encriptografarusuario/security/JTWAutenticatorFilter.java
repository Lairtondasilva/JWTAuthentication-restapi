package com.exportech.encriptografarusuario.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.exportech.encriptografarusuario.data.UserDetailsData;
import com.exportech.encriptografarusuario.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JTWAutenticatorFilter extends UsernamePasswordAuthenticationFilter {

  public static final int TOKEN_EXPIRES = 600_000;
  public static final String TOKEN_SENHA = "4e21dd7a-d685-4929-a76b-c2f0eb84f3af";

  private final AuthenticationManager authenticationManager;

  public JTWAutenticatorFilter (AuthenticationManager authenticationManager){
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    
    try {
      UserModel userModel = new ObjectMapper()
        .readValue(request.getInputStream(), UserModel.class);

        return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(

          userModel.getLogin(),
          userModel.getPassword(),
          new ArrayList<>()
          ));

        } catch (IOException e) {
      throw new RuntimeException("Failed to authenticate user",e);
    }

    
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        FilterChain chain,
                                       Authentication authResult)
                                      throws IOException, ServletException {
    
    UserDetailsData userDetailsData = (UserDetailsData) authResult.getPrincipal();
    String token = JWT.create().withSubject(userDetailsData.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRES))
                    .sign(Algorithm.HMAC512(TOKEN_SENHA)) ;    
    response.getWriter().write(token); 
    response.getWriter().flush();                 
  }
}
