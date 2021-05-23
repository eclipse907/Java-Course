package hr.fer.zemris.java.votingapp.dao;

import java.util.List;
import hr.fer.zemris.java.votingapp.model.Poll;
import hr.fer.zemris.java.votingapp.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Tin Reiter
 *
 */
public interface DAO {
	
	Poll getPoll(long pollId) throws DAOException;
	
	List<Poll> getPolls() throws DAOException;
	
	PollOption gePollOption(long pollOptionId) throws DAOException;
	
	List<PollOption> getPollOptions(long pollId) throws DAOException;
	
	void vote(long pollOptionId) throws DAOException;
	
}
