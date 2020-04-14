package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;

/**
 * ReviewerRecord
 * Class for the table in the Editor window
 */
public class ReviewerRecord {

	// string prperties
	private SimpleStringProperty researcher, submission;
	private LocalDate review, deadline;

	// constructor
	public ReviewerRecord(String researcher, String submission, LocalDate deadline, LocalDate review) {
		this.researcher = new SimpleStringProperty(researcher);
		this.submission = new SimpleStringProperty(submission);
		this.review = review;
		this.deadline = deadline;
	}

	public String getResearcher() {
		return researcher.get();
	}

	// Getters and Setters
	public String getSubmission() {
		return submission.get();
	}

	

	public void setSubmission(SimpleStringProperty submission) {
		this.submission = submission;
	}

	
	

	public LocalDate getReview() {
		return review;
	}

	public void setReview(LocalDate review) {
		this.review = review;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public void setResearcher(SimpleStringProperty researcher) {
		this.researcher = researcher;
	}

}
