package hr.fer.zemris.java.webapp.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/voting-vote")
public class VotingVoteFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		Object hasVoted = session.getAttribute("hasVoted");
		if (hasVoted == null) {
			session.setAttribute("hasVoted", true);
			chain.doFilter(request, response);
		} else {
			request.setAttribute("errorMessage", "You have already voted.");
			request.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(request, response);
		}
	}

	@Override
	public void destroy() {	
	}

}
