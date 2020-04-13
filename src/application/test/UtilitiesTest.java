package application.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.Utilities;

class UtilitiesTest {

	Utilities testingUtil = new Utilities();
	String testJournalName = "OnlyForTesting";
	String testingUsername = "testSubject";

	@Test
	void testReadRevDeadlines() {
		String StoredDates = "";
		String deadlinesRead = "";
		File deleteFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + testJournalName + File.separator
				+ "researchers" + File.separator + testingUsername + File.separator + "reviewerDeadlines.txt");

		LocalDate date = LocalDate.now();
		for (int i = 0; i < 3; i++) {

			testingUtil.writeRevDeadlines(testingUsername, testJournalName, date.plusDays(i).toString());
			StoredDates += date.plusDays(i) + " ";
		}
		try {
			deadlinesRead = testingUtil.readRevDeadlines(testingUsername, testJournalName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(StoredDates, deadlinesRead);
		// delete file to use test again
		deleteFile.delete();

	}

	@Test
	void testListFilesInDir() {

		File JournalTestFIles = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + testJournalName + File.separator);
		// change this array with more files or folder names in needed more testing
		String[] filesThatExist = { "testingDir2", "researchers", "testingDir" };
		String[] testFilesExist = testingUtil.listFilesInDir(JournalTestFIles);

		assertArrayEquals(filesThatExist, testFilesExist);

	}

	@Test
	void testReadNomRevFile() {
		File deleteFIle = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + testJournalName + File.separator
				+ "researchers" + File.separator + testingUsername + File.separator + "nominatedReviewers.txt");
		String reviewerName = "testReviewer0 testReviewer1 testReviewer2 ";
		String readResult = "";
		for (int i = 0; i <= 2; i++) {
			testingUtil.writeNominatedRev(testingUsername, "testReviewer" + i, testJournalName);
		}

		try {
			readResult = testingUtil.readNomRevFile(testingUsername, testJournalName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(reviewerName, readResult);
		// delete file to use test again
		deleteFIle.delete();

	}

	@Test
	void testCheckResearcherFileExists() {
		boolean exists = testingUtil.checkResearcherFileExists(testJournalName, testingUsername);
		boolean notExists = testingUtil.checkResearcherFileExists(testJournalName, "shouldn't exist");

		assertTrue(exists);
		assertFalse(notExists);
	}

	@Test
	void testCreateUserDir() {

		String User = "testSubject2";
		File deleteFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + testJournalName + File.separator
				+ "researchers" + File.separator + User);
		boolean noError = testingUtil.createUserDir(User, 1, testJournalName);
		boolean error = testingUtil.createUserDir(testingUsername, 4, User);
//		System.out.println(testingUtil.getMessage());
//		System.out.println(testingUtil.getMessage());

		assertFalse(noError);
		assertTrue(error);
		// delete file to use test again
		deleteFile.delete();

	}

	@Test
	void testReadJournalList() {
		// PLease update if new journals are added before testing
		String JournalsAvailable = "journal1 journal2 journal3 journal4 journal5 journal6 testJournal ";
		String readJournals = "";
		try {
			readJournals = testingUtil.readJournalList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(JournalsAvailable, readJournals);
	}

}
