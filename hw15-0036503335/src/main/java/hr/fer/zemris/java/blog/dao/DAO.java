package hr.fer.zemris.java.blog.dao;

import java.util.List;
import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	BlogEntry getBlogEntryById(Long id) throws DAOException;
	
	List<BlogEntry> getBlogEntriesByAuthor(BlogUser author) throws DAOException;
	
	void persistBlogEntry(BlogEntry entry) throws DAOException;
	
	void persistBlogComment(BlogComment comment) throws DAOException;
	
	BlogUser getBlogUserById(Long id) throws DAOException;
	
	BlogUser getBlogUserByNickname(String nickname) throws DAOException;
	
	List<BlogUser> getAllBlogUsers() throws DAOException;
	
	void persistBlogUser(BlogUser user) throws DAOException;
		
}