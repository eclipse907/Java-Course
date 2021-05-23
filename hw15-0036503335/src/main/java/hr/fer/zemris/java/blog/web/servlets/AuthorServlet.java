package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.blog.web.forms.NewCommentForm;

@WebServlet("/servlets/author/*")
public class AuthorServlet extends HttpServlet {

	private static final long serialVersionUID = -8126200349230766598L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestedResource = req.getPathInfo();
		if (requestedResource == null || requestedResource.equals("/")) {
			req.setAttribute("errorMessage", "Invalid URL.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			return;
		}
		String[] parts = requestedResource.substring(1).split("/");
		if (parts.length == 1) {
			List<BlogEntry> userEntries = getAuthorEntries(parts[0]);
			if (userEntries == null) {
				req.setAttribute("errorMessage", "Requested user does not exist.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
				return;
			}
			req.setAttribute("userEntries", userEntries);
			req.setAttribute("author", parts[0]);
			req.getRequestDispatcher("/WEB-INF/pages/userEntries.jsp").forward(req, resp);
		} else if (parts.length == 2) {
			try {
				if (parts[1].equals("new")) {
					if (req.getSession().getAttribute("current.user.id") == null) {
						req.setAttribute("errorMessage", "Must be logged in to add blog entries.");
						req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
						return;
					}
					if (!parts[0].equals(req.getSession().getAttribute("current.user.nick"))) {
						req.setAttribute("errorMessage", "Logged in with wrong user profile.");
						req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
						return;
					}
					req.setAttribute("blogEntryMode", "New");
					req.getRequestDispatcher("/WEB-INF/pages/new-edit.jsp").forward(req, resp);
				} else if (parts[1].equals("edit")) {
					if (req.getSession().getAttribute("current.user.id") == null) {
						req.setAttribute("errorMessage", "Must be logged in to edit blog entries.");
						req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
						return;
					}
					if (!parts[0].equals(req.getSession().getAttribute("current.user.nick"))) {
						req.setAttribute("errorMessage", "Logged in with wrong user profile.");
						req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
						return;
					}
					BlogEntry entry = DAOProvider.getDAO().getBlogEntryById(Long.valueOf(req.getParameter("id")));
					if (entry == null) {
						req.setAttribute("errorMessage", "No blog entry with given id exists.");
						req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
						return;
					}
					req.setAttribute("blogEntryMode", "Edit");
					req.setAttribute("blogEntry", entry);
					req.getRequestDispatcher("/WEB-INF/pages/new-edit.jsp").forward(req, resp);
				} else {
					BlogEntry entry = DAOProvider.getDAO().getBlogEntryById(Long.valueOf(parts[1]));
					if (entry == null) {
						req.setAttribute("errorMessage", "No blog entry with given id exists.");
						req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
						return;
					}
					if (!parts[0].equals(entry.getAuthor().getNick())) {
						req.setAttribute("errorMessage", "No blog entry with given id exists.");
						req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
						return;
					}
					NewCommentForm form = new NewCommentForm();
					form.fillFromBlogComment(new BlogComment());
					req.setAttribute("form", form);
					req.setAttribute("blogEntry", entry);
					req.getRequestDispatcher("/WEB-INF/pages/showBlogEntry.jsp").forward(req, resp);
				}
			} catch (NumberFormatException ex) {
				req.setAttribute("errorMessage", "Invalid blog entry id.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			}
		} else {
			req.setAttribute("errorMessage", "Invalid URL.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestedResource = req.getPathInfo();
		if (requestedResource == null || requestedResource.equals("/")) {
			req.setAttribute("errorMessage", "Invalid URL.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			return;
		}
		String[] parts = requestedResource.substring(1).split("/");
		if (parts.length != 2) {
			req.setAttribute("errorMessage", "Invalid URL.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			return;
		}
		if (parts[1].equals("new")) {
			BlogEntry newEntry = createNewBlogEntry(parts[0], req.getParameter("title"), req.getParameter("text"));
			if (newEntry == null) {
				req.setAttribute("errorMessage", "The given parameters are wrong.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
				return;
			}
			resp.sendRedirect(newEntry.getId().toString());
		} else if (parts[1].equals("edit")) {
			try {
				BlogEntry editedEntry = editGivenBlogEntry(Long.valueOf(req.getParameter("id")), req.getParameter("title"), req.getParameter("text"));
				if (editedEntry == null) {
					req.setAttribute("errorMessage", "Requested blog entry does not exist.");
					req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
					return;
				}
				resp.sendRedirect(editedEntry.getId().toString());
			} catch (NumberFormatException ex) {
				req.setAttribute("errorMessage", "Invalid blog entry id.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			}
		} else {
			try {
				NewCommentForm form = new NewCommentForm();
				form.fillFromHttpRequest(req);
				form.validate();
				if (form.hasErrors()) {
					req.setAttribute("form", form);
					req.setAttribute("blogEntry", DAOProvider.getDAO().getBlogEntryById(Long.valueOf(parts[1])));
					req.getRequestDispatcher("/WEB-INF/pages/showBlogEntry.jsp").forward(req, resp);
					return;
				}
				BlogComment newComment = createNewBlogComment(Long.valueOf(parts[1]), form);
				if (newComment == null) {
					req.setAttribute("errorMessage", "The given parameters are wrong.");
					req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
					return;
				}
				resp.sendRedirect(parts[1]);
			} catch (NumberFormatException ex) {
				req.setAttribute("errorMessage", "Invalid blog entry id.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			}
		}
	}
	
	private List<BlogEntry> getAuthorEntries(String nick) {
		BlogUser user = DAOProvider.getDAO().getBlogUserByNickname(nick);
		if (user == null) {
			return null;
		}
		return DAOProvider.getDAO().getBlogEntriesByAuthor(user);
	}
	
	private BlogEntry createNewBlogEntry(String nick, String title, String text) {
		BlogUser user = DAOProvider.getDAO().getBlogUserByNickname(nick);
		if (user == null || title == null || text == null) {
			return null;
		}
		BlogEntry entry = new BlogEntry();
		entry.setCreatedAt(new Date());
		entry.setTitle(title);
		entry.setText(text);
		entry.setAuthor(user);
		DAOProvider.getDAO().persistBlogEntry(entry);
		return entry;
	}
	
	private BlogEntry editGivenBlogEntry(Long id, String newTitle, String newText) {
		BlogEntry entry = DAOProvider.getDAO().getBlogEntryById(id);
		if (entry == null) {
			return null;
		}
		if (newTitle != null) {
			entry.setTitle(newTitle);
		}
		if (newText != null) {
			entry.setText(newText);
		}
		return entry;
	}
	
	private BlogComment createNewBlogComment(Long blogEntryId, NewCommentForm form) {
		BlogEntry entry = DAOProvider.getDAO().getBlogEntryById(blogEntryId);
		if (entry == null || form == null) {
			return null;
		}
		BlogComment comment = new BlogComment();
		form.fillGivenBlogComment(comment);
		comment.setBlogEntry(entry);
		comment.setPostedOn(new Date());
		DAOProvider.getDAO().persistBlogComment(comment);
		return comment;
	}

}
