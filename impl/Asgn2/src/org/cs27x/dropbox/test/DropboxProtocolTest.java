package org.cs27x.dropbox.test;

import static org.junit.Assert.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.DropboxTransport;
import org.cs27x.dropbox.FileManager;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.filewatcher.FileStates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class DropboxProtocolTest {
	
	@Mock private DropboxTransport mTransport;
	@Mock private FileStates mStates;
	@Mock private FileManager mManager;
	@Mock private DropboxCmd cmd;
	private Path testPath;
	private Path testFilePath;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testPath = FileSystems.getDefault().getPath("test-data");
		testFilePath = testPath.toAbsolutePath().resolve("invariant/vandy.png");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListenerAdded() {
		new DropboxProtocol(mTransport, mStates, mManager);
		verify(mTransport).addListener(any(DropboxCmdProcessor.class));
	}
	
	@Test
	public void testAddFile() {
		ArgumentCaptor<DropboxCmd> cmdCaptor = ArgumentCaptor.forClass(DropboxCmd.class);
		new DropboxProtocol(mTransport,mStates,mManager).addFile(testFilePath);
		verify(mTransport).publish(cmdCaptor.capture());
		DropboxCmd dc = cmdCaptor.getValue();
		assertEquals(dc.getOpCode(), OpCode.ADD);
		assertEquals(dc.getPath(), testFilePath.getFileName().toString());
		assertTrue(dc.getData().length > 0);
	}
	
	@Test
	public void testRemoveFile() {
		ArgumentCaptor<DropboxCmd> cmdCaptor = ArgumentCaptor.forClass(DropboxCmd.class);
		new DropboxProtocol(mTransport,mStates,mManager).removeFile(testFilePath);
		verify(mTransport).publish(cmdCaptor.capture());
		DropboxCmd dc = cmdCaptor.getValue();
		assertEquals(dc.getOpCode(), OpCode.REMOVE);
		assertEquals(dc.getPath(), testFilePath.getFileName().toString());
	}
	
	@Test
	public void testUpdateFile() {
		ArgumentCaptor<DropboxCmd> cmdCaptor = ArgumentCaptor.forClass(DropboxCmd.class);
		new DropboxProtocol(mTransport,mStates,mManager).updateFile(testFilePath);
		verify(mTransport).publish(cmdCaptor.capture());
		DropboxCmd dc = cmdCaptor.getValue();
		assertEquals(dc.getOpCode(), OpCode.UPDATE);
		assertEquals(dc.getPath(), testFilePath.getFileName().toString());
		assertTrue(dc.getData().length > 0);
	}

}
