package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class for the table in the Editor window.
 */
public class EditorRecord {

	/* String properties */
	private SimpleStringProperty researcher, submission, reviewer;
	private LocalDate deadline;

	/* Constructor */
	public EditorRecord(String researcher, String submission, String reviewer, LocalDate deadline) {
		this.researcher = new SimpleStringProperty(researcher);
		this.submission = new SimpleStringProperty(submission);
		this.reviewer = new SimpleStringProperty(reviewer);
		this.deadline = deadline;
	}
	
	/* Getters and Setters */
	public String getResearcher() {
		return researcher.get();
	}

	public String getSubmission() {
		return submission.get();
	}

	public String getReviewer() {
		return reviewer.get();
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public void setSubmission(SimpleStringProperty submission) {
		this.submission = submission;
	}

	public void setReviewer(SimpleStringProperty reviewer) {
		this.reviewer = reviewer;
	}

	public void setResearcher(SimpleStringProperty researcher) {
		this.researcher = researcher;
	}

}
