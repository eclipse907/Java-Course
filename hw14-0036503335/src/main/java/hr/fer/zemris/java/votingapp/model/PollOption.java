package hr.fer.zemris.java.votingapp.model;

public class PollOption {

	private long id;
	private String optionTitle;
	private String optionLink;
	private long pollId;
	private long votesCount;
	
	public PollOption(long id, String optionTitle, String optionLink, long pollId, long votesCount) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollId = pollId;
		this.votesCount = votesCount;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * @return the pollId
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * @return the votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}
	
}
