package me.szumielxd.tools_panel.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class TrailingSlashRedirectFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (servletRequest instanceof HttpServletRequest request && servletResponse instanceof HttpServletResponse response) {
			var path = request.getRequestURI();
			if (path.length() > 1 && path.endsWith("/") && !path.contains(".") && request.getMethod().equals("GET")) {
				var location = path.substring(0, path.length() - 1) + Optional.ofNullable(request.getQueryString())
						.map(q -> "?" + q)
						.orElse("");
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				response.addHeader(HttpHeaders.LOCATION, location);
				return;
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
