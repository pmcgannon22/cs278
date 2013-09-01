package org.cs27x.dropbox.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.FileStates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DropboxFileEventHandlerTest {

	DropboxProtocol mTransport;
	FileManager mHdlr;
	FileStates mStates;
	
	@Before
	public void setUp() throws Exception {
		mTransport = mock(DropboxProtocol.class);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
