package hr.fer.zemris.java.blog.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import hr.fer.zemris.java.blog.dao.DAO;
import hr.fer.zemris.java.blog.dao.DAOException;
import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntryById(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@Override
	public List<BlogEntry> getBlogEntriesByAuthor(BlogUser author) throws DAOException {
		return JPAEMProvider.getEntityManager()
				.createQuery("SELECT entry FROM BlogEntry entry WHERE entry.author = :author", BlogEntry.class)
				.setParameter("author", author)
				.getResultList();
	}

	@Override
	public void persistBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
	}

	@Override
	public void persistBlogComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}

	@Override
	public BlogUser getBlogUserById(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogUser.class, id);
	}

	@Override
	public BlogUser getBlogUserByNickname(String nickname) throws DAOException {
		try {
			return JPAEMProvider.getEntityManager()
					.createQuery("SELECT user FROM BlogUser user WHERE user.nick = :nickname", BlogUser.class)
					.setParameter("nickname", nickname)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<BlogUser> getAllBlogUsers() throws DAOException {
		return JPAEMProvider.getEntityManager()
				.createQuery("SELECT user FROM BlogUser user", BlogUser.class)
				.getResultList();
	}

	@Override
	public void persistBlogUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

}