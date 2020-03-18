package application;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class for the table in the Editor window
 */
public class EditorRecord {
	
	// string prperties
	private SimpleStringProperty submission, reviewer;
	private LocalDate minorRevision, revision1, revision2;
	
	// constructor
	public EditorRecord(String submission, String reviewer, LocalDate minorRevision, LocalDate revision1, LocalDate revision2) {
		this.submission = new SimpleStringProperty(submission);
		this.reviewer = new SimpleStringProperty(reviewer);
		this.minorRevision = minorRevision;
		this.revision1 = revision1;
		this.revision2 = revision2;
	}	

	// Getters and Setters
	public String getSubmission() {
		return submission.get();
	}

	public String getReviewer() {
		return reviewer.get();
	}

	public LocalDate getMinorRevision() {
		return minorRevision;
	}

	public LocalDate getRevision1() {
		return revision1;
	}

	public LocalDate getRevision2() {
		return revision2;
	}

	public void setSubmission(SimpleStringProperty submission) {
		this.submission = submission;
	}

	public void setReviewer(SimpleStringProperty reviewer) {
		this.reviewer = reviewer;
	}

	public void setMinorRevision(LocalDate minorRevision) {
		this.minorRevision = minorRevision;
	}

	public void setRevision1(LocalDate revision1) {
		this.revision1 = revision1;
	}

	public void setRevision2(LocalDate revision2) {
		this.revision2 = revision2;
	}

}
