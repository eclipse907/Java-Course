package hr.fer.zemris.java.votingapp.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.votingapp.dao.DAO;
import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.model.Poll;
import hr.fer.zemris.java.votingapp.model.PollOption;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 *  
 * @author Tin Reiter
 */
public class SQLDAO implements DAO {

	@Override
	public Poll getPoll(long pollId) throws DAOException {
		Connection connection = SQLConnectionProvider.getConnection();
		try (
				PreparedStatement statement = connection.prepareStatement("select * from Polls where id = " + pollId);
				ResultSet result = statement.executeQuery();
			) {
			if (result.next()) {
				return new Poll(result.getLong(1), result.getString(2), result.getString(3));
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new DAOException("Error while retrieving poll from database.");
		}
	}

	@Override
	public List<Poll> getPolls() throws DAOException {
		Connection connection = SQLConnectionProvider.getConnection();
		try (
				PreparedStatement statement = connection.prepareStatement("select * from Polls");
				ResultSet result = statement.executeQuery();
		    ) {
			List<Poll> polls = new ArrayList<>();
			while (result.next()) {
				polls.add(new Poll(result.getLong(1), result.getString(2), result.getString(3)));
			}
			return polls;
		} catch (Exception ex) {
			throw new DAOException("Error while retrieving polls from database.");
		}
	}

	@Override
	public List<PollOption> getPollOptions(long pollId) throws DAOException {
		Connection connection = SQLConnectionProvider.getConnection();
		try (
				PreparedStatement statement = connection.prepareStatement("select * from PollOptions where pollID = " + pollId);
				ResultSet result = statement.executeQuery();
			) {
			List<PollOption> pollOptions = new ArrayList<>();
			while (result.next()) {
				pollOptions.add(new PollOption(result.getLong(1), result.getString(2), result.getString(3), result.getLong(4), result.getLong(5)));
			}
			return pollOptions;
		} catch (Exception ex) {
			throw new DAOException("Error while retrieving poll options from database.");
		}
	}
	
	@Override
	public void vote(long pollOptionId) throws DAOException {
		Connection connection = SQLConnectionProvider.getConnection();
		try (
				PreparedStatement statement = connection.prepareStatement("update PollOptions set votesCount = votesCount + 1 where id = " + pollOptionId); 
			) {
			statement.executeUpdate();
		} catch (Exception ex) {
			throw new DAOException("Error while saving vote to database.");
		}
	}

	@Override
	public PollOption gePollOption(long pollOptionId) throws DAOException {
		Connection connection = SQLConnectionProvider.getConnection();
		try (
				PreparedStatement statement = connection.prepareStatement("select * from PollOptions where id = " + pollOptionId);
				ResultSet result = statement.executeQuery();
			) {
			if (result.next()) {
				return new PollOption(result.getLong(1), result.getString(2), result.getString(3), result.getLong(4), result.getLong(5));
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new DAOException("Error while retrieving poll option from database.");
		}
	}

}
