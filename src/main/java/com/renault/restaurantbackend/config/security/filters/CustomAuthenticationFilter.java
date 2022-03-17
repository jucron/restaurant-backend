package main.java.com.renault.restaurantbackend.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
/*
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager=authenticationManager;
    this.tokenService = tokenService;
  }



  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    log.info("Username is: {}", username); log.info("Password is {}", password);
    UsernamePasswordAuthenticationToken
        authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(authenticationToken);
  }

  @Override //This is called if attemptAuthentication is successful
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws
      IOException, ServletException {
    //Attention for this next 'User' which is the one from Spring security:
    User user = (User) authResult.getPrincipal();

    Map<String, String> tokens = tokenService.generateToken(user, request);

    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
  }

}
*/
