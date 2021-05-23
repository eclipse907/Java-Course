package hr.fer.zemris.java.votingapp.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Initialization implements ServletContextListener {
	
	private static final String[] tablesToCreate = {
			"CREATE TABLE Polls (\r\n" + 
			" id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
			" title VARCHAR(150) NOT NULL,\r\n" + 
			" message CLOB(2048) NOT NULL\r\n" + 
			")",
			
			"CREATE TABLE PollOptions (\r\n" + 
			" id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
			" optionTitle VARCHAR(100) NOT NULL,\r\n" + 
			" optionLink VARCHAR(150) NOT NULL,\r\n" + 
			" pollID BIGINT,\r\n" + 
			" votesCount BIGINT,\r\n" + 
			" FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + 
			")"
	};

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			cpds.setJdbcUrl(loadConnectionURL(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")));
			for (String tableToCreate : tablesToCreate) {
				createTable(cpds, tableToCreate);
			}
			fillTablesIfEmpty(cpds);
		} catch (Exception ex) {
			throw new RuntimeException("Error during database initialization.", ex);
		}
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String loadConnectionURL(String fileToLoad) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(fileToLoad));
		if (lines.size() != 5) {
			throw new RuntimeException("The database configuration file is wrong.");
		}
		String host;
		String[] hostLine = lines.get(0).split("=");
		if (hostLine.length != 2 || !hostLine[0].equals("host")) {
			throw new RuntimeException("The database configuration file is wrong.");
		} else {
			host = hostLine[1];
		}
		String port;
		String[] portLine = lines.get(1).split("=");
		if (portLine.length != 2 || !portLine[0].equals("port")) {
			throw new RuntimeException("The database configuration file is wrong.");
		} else {
			port = portLine[1];
		}
		String name;
		String[] nameLine = lines.get(2).split("=");
		if (nameLine.length != 2 || !nameLine[0].equals("name")) {
			throw new RuntimeException("The database configuration file is wrong.");
		} else {
			name = nameLine[1];
		}
		String user;
		String[] userLine = lines.get(3).split("=");
		if (userLine.length != 2 || !userLine[0].equals("user")) {
			throw new RuntimeException("The database configuration file is wrong.");
		} else {
			user = userLine[1];
		}
		String password;
		String[] passwordLine = lines.get(4).split("=");
		if (passwordLine.length != 2 || !passwordLine[0].equals("password")) {
			throw new RuntimeException("The database configuration file is wrong.");
		} else {
			password = passwordLine[1];
		}
		return "jdbc:derby://" + host + ":" + port + "/" + name + ";user=" + user + ";password=" + password;
	}
	
	private void createTable(ComboPooledDataSource cpds, String tableToCreate) {
		try {
			cpds.getConnection().prepareStatement(tableToCreate).execute();
		} catch (SQLException ex) {
			if(!ex.getSQLState().equals("X0Y32")) {
				throw new RuntimeException("Error while attempting to create table.");
			}
		}
	}
	
	private void fillTablesIfEmpty(ComboPooledDataSource cpds) throws SQLException {
		Connection connection = cpds.getConnection();
		ResultSet numOfEntries = connection.prepareStatement("select count(*) from Polls").executeQuery();
		numOfEntries.next();
		if(numOfEntries.getInt(1) <= 0) {
			String[] pollsToAdd = {
					"'Vote for your favorite band', 'From the given bands which one is your favorite? Click on the link to vote!'"
			};
			for (String toAdd : pollsToAdd) {
				connection.prepareStatement("INSERT INTO Polls (title, message) VALUES (" + toAdd + ")").executeUpdate();
			}
		}
		numOfEntries = connection.prepareStatement("select count(*) from PollOptions").executeQuery();
		numOfEntries.next();
		if(numOfEntries.getInt(1) <= 0) {
			int[] pollIds = new int[1];
			ResultSet result = connection.prepareStatement("select id from Polls").executeQuery();
			for (int i = 0; result.next(); i++) {
				pollIds[i] = result.getInt(1);
			}
			String[] pollOptionsToAdd = {
					"'The Beatles', 'https://www.youtube.com/watch?v=z9ypq6_5bsg', " + pollIds[0] + ", 0",
					"'The Platters', 'https://www.youtube.com/watch?v=H2di83WAOhU', " + pollIds[0] + ", 0",
					"'The Beach Boys', 'https://www.youtube.com/watch?v=2s4slliAtQU', " + pollIds[0] + ", 0",
					"'The Four Seasons', 'https://www.youtube.com/watch?v=y8yvnqHmFds', " + pollIds[0] + ", 0",
					"'The Marcels', 'https://www.youtube.com/watch?v=qoi3TH59ZEs', " + pollIds[0] + ", 0",
					"'The Everly Brothers', 'https://www.youtube.com/watch?v=tbU3zdAgiX8', " + pollIds[0] + ", 0",
					"'The Mamas And The Papas', 'https://www.youtube.com/watch?v=N-aK6JnyFmk', " + pollIds[0] + ", 0"
			};
			for (String toAdd : pollOptionsToAdd) {
				connection.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (" + toAdd + ")").executeUpdate();
			}
		}
	}

}
