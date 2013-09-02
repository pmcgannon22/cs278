package org.cs27x.dropbox.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.DropboxFileEventHandler;
import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileStates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DropboxFileEventHandlerTest {

	@Mock DropboxProtocol mTransport;
	@Mock FileManager mFileHandler;
	@Mock FileStates mStates;
	Path testPath;
	FileEvent event;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testPath = FileSystems.getDefault().getPath("test-data");
	}
	
	
	public void setupHandle(Kind<?> eventType) {
		event = new FileEvent(eventType, testPath);
		try {
			when(mStates.filter(any(FileEvent.class))).thenReturn(event);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHandleCreate() {
		setupHandle(ENTRY_CREATE);
		new DropboxFileEventHandler(mFileHandler, mStates, mTransport).handle(event);
		verify(mTransport).addFile(testPath);
		verifyNoMoreInteractions(mTransport);
	}
	
	@Test
	public void testHandleModify() {
		setupHandle(ENTRY_MODIFY);
		new DropboxFileEventHandler(mFileHandler, mStates, mTransport).handle(event);
		verify(mTransport).updateFile(testPath);
		verifyNoMoreInteractions(mTransport);
	}
	
	@Test
	public void testHandleDelete() {
		setupHandle(ENTRY_DELETE);
		new DropboxFileEventHandler(mFileHandler, mStates, mTransport).handle(event);
		verify(mTransport).removeFile(testPath);
		verifyNoMoreInteractions(mTransport);
	}
	
	@Test
	public void testHandleNull() {
		setupHandle(ENTRY_CREATE);
		try {
			when(mStates.filter(any(FileEvent.class))).thenReturn(null);
		} catch (IOException e) { 
			e.printStackTrace();
			fail("IOException");
		}
		new DropboxFileEventHandler(mFileHandler, mStates, mTransport).handle(event); 
		verifyZeroInteractions(mTransport);
	}
}
