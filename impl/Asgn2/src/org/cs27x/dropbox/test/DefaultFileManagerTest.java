package org.cs27x.dropbox.test;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

import org.cs27x.dropbox.DefaultFileManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefaultFileManagerTest {
	private Path testRoot;
	private Path testFilePath;
	private File testFile;
	private DefaultFileManager dfm;

	@Before
	public void setUp() {
		try {
			setupFileSystem();
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		dfm = new DefaultFileManager(testRoot);
	}

	public void setupFileSystem() throws IOException {
		testRoot = FileSystems.getDefault().getPath("test-data");
		testFilePath = FileSystems.getDefault().getPath("test-data",
				"test-file.txt");
		testFile = testFilePath.toFile();
		testFile.mkdirs();
		testFile.createNewFile();
	}

	@Test
	public void testDefaultFileManagerDelete() {
		if (testFile.exists()) {
			try {
				dfm.delete(testFilePath);
			} catch (IOException e) {
				fail("IOException occurred.");
			}
		}
		assertFalse(testFile.exists());
	}

	@Test
	public void testDefaultFileManagerWrite() {
		Path writeTestPath = FileSystems.getDefault().getPath("test-data",
				"write-test.txt");
		byte[] data = new String("This is a test.").getBytes();

		try {
			dfm.write(writeTestPath, data, false);
		} catch (IOException e) {
			fail("IOException occurred.");
		}

		File wFile = writeTestPath.toFile();
		assert (wFile.exists());
		byte[] dataRead = new byte[(int) wFile.length()];
		DataInputStream dis;
		try {
			dis = new DataInputStream(new FileInputStream(wFile));
			dis.readFully(dataRead);
			dis.close();
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException");
		} catch (IOException e) {
			fail("IOException");
		}
		assert (Arrays.equals(data, dataRead));
	}

	@After
	public void tearDown() {
		testFile.delete();
	}

}