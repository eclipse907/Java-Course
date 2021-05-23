package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.hash.Util;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.blog.web.forms.LoginForm;

@WebServlet("/servlets/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 7241786640123487738L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("current.user.id") == null) {
			LoginForm form = new LoginForm();
			form.fillFromBlogUser(new BlogUser());
			req.setAttribute("form", form);
		}
		req.setAttribute("authors", DAOProvider.getDAO().getAllBlogUsers());
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("authors", DAOProvider.getDAO().getAllBlogUsers());
			LoginForm form = new LoginForm();
			form.fillFromHttpRequest(req);
			form.validate();
			if (form.hasErrors()) {
				req.setAttribute("form", form);
				req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
				return;
			}
			BlogUser user = DAOProvider.getDAO().getBlogUserByNickname(form.getNick());
			if (user == null) {
				req.setAttribute("nickError", "The given nickname doesn't exist.");
				req.setAttribute("form", form);
				req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
				return;
			}
			if (!user.getPasswordHash().equals(Util.messageDigest(form.getPassword()))) {
				req.setAttribute("passwordError", "The given password is wrong.");
				req.setAttribute("form", form);
				req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
				return;
			}
			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			req.getSession().setAttribute("current.user.email", user.getEmail());
			resp.sendRedirect("main");
		} catch (NoSuchAlgorithmException | IOException ex) {
			req.setAttribute("errorMessage", "There was an error while checking the login info.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}

}
