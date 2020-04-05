package com.umssonline.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umssonline.auth.controller.dto.request.CredentialsDto;
import com.umssonline.auth.controller.dto.response.UserResponseDto;
import com.umssonline.auth.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Base64Utils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;


    public AuthenticationFilter(UserService userService, Environment environment, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            CredentialsDto credentials = new ObjectMapper()
                                         .readValue(request.getInputStream(), CredentialsDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    credentials.getAccount(),
                    credentials.getPassword(),
                    new ArrayList<>()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String userName = ((User)authResult.getPrincipal()).getUsername();
        UserResponseDto userDetails = userService.getUserDetailsByAccount(userName);

        String tokenBase64Bytes = Base64Utils.encodeToString(environment.getProperty("uo.auth.security.jwt.token.secret").getBytes());
        String jwtToken = Jwts.builder()
                .setSubject(userDetails.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("uo.auth.security.jwt.token.expiration"))))
                .signWith(SignatureAlgorithm.HS512, tokenBase64Bytes.getBytes())
                .compact();

        response.addHeader("token", jwtToken);
        response.addHeader("userId", userDetails.getId().toString());
    }


}
