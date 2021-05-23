package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.blog.web.forms.NewBlogUserForm;

@WebServlet("/servlets/register")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 5440431138544248382L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("current.user.id") != null) {
			req.setAttribute("errorMessage", "You are already registered.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
		NewBlogUserForm form = new NewBlogUserForm();
		form.fillFromBlogUser(new BlogUser());
		req.setAttribute("form", form);
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			NewBlogUserForm form = new NewBlogUserForm();
			form.fillFromHttpRequest(req);
			form.validate();
			if (form.hasErrors()) {
				req.setAttribute("form", form);
				req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
				return;
			}
			BlogUser user = new BlogUser();
			form.fillGivenBlogUser(user);
			DAOProvider.getDAO().persistBlogUser(user);
			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			req.getSession().setAttribute("current.user.email", user.getEmail());
			req.getRequestDispatcher("/WEB-INF/pages/successfullyRegistered.jsp").forward(req, resp);
		} catch (NoSuchAlgorithmException | IOException ex) {
			req.setAttribute("errorMessage", "There was an error while saving the registration.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}

}
