package nl.brighton.zolder.filter;

import lombok.RequiredArgsConstructor;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.auth.exception.InvalidTokenException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    private final UserDetailsService userDetailsService;

    public String HEADER_STRING = "authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            try {
                var authToken = authService.getToken(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(authToken.getUsername());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                System.out.println(userDetails);
                System.out.println(authToken);

                authenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (InvalidTokenException e) {
                System.out.println(e);
            }
        }
        chain.doFilter(request, response);
    }
}
