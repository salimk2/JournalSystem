package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class for the table in the Admin window.
 */
public class AdminRecord {

	/* String properties */
	private SimpleStringProperty researcher, submission, reviewer;
	private LocalDate review;

	/* Constructor */
	public AdminRecord(String researcher, String submission, String reviewer, LocalDate review) {
		this.researcher = new SimpleStringProperty(researcher);
		this.submission = new SimpleStringProperty(submission);
		this.reviewer = new SimpleStringProperty(reviewer);
		this.review = review;
	}

	public String getResearcher() {
		return researcher.get();
	}

	/* Getters and Setters */
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

	public String getReviewer() {
		return reviewer.get();
	}

	public void setReviewer(SimpleStringProperty reviewer) {
		this.reviewer = reviewer;
	}

	public void setResearcher(SimpleStringProperty researcher) {
		this.researcher = researcher;
	}

}
