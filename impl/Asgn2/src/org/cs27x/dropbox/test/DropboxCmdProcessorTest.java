package org.cs27x.dropbox.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DropboxCmdProcessorTest {
	@Mock private FileStates mFileStates;
	@Mock private FileState mState;
	@Mock private FileManager mMgr;
	@Mock private DropboxCmd mCmd;
	private Path testPath;

	final long FILETIME = 1378067536;
	
	@Before
	public void setUp() throws Exception {
		testPath = FileSystems.getDefault().getPath("test-data");
		MockitoAnnotations.initMocks(this);
	}
	
	public void setupMocks(OpCode op){
		when(mFileStates.getState(testPath)).thenReturn(mState);
		when(mFileStates.getOrCreateState(testPath)).thenReturn(mState);
		
		when(mCmd.getOpCode()).thenReturn(op);
		when(mCmd.getData()).thenReturn(new byte[]{100,101});
		
		when(mMgr.resolve(anyString())).thenReturn(testPath);
		
		try {
			when(mMgr.getLastModifiedTime(testPath)).thenReturn(FileTime.fromMillis(FILETIME));
		} catch (IOException e) { e.printStackTrace(); }
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdateFileStateRemove() {
		setupMocks(OpCode.REMOVE);
		new DropboxCmdProcessor(mFileStates, mMgr).updateFileState(mCmd, testPath);
		verify(mState,never()).setLastModificationDate(any(FileTime.class));
		verify(mState,times(1)).setSize(-1);
	}
	
	@Test
	public void testUpdateFileStateNull() {
		setupMocks(OpCode.REMOVE);
		when(mFileStates.getState(testPath)).thenReturn(null);
		new DropboxCmdProcessor(mFileStates,mMgr).updateFileState(mCmd, testPath);
		verifyZeroInteractions(mState);
	}
	
	
	@Test
	public void testUpdateFileStateAdd(){
		setupMocks(OpCode.ADD);
		new DropboxCmdProcessor(mFileStates, mMgr).updateFileState(mCmd, testPath);
		verify(mState,times(1)).setLastModificationDate((FileTime)anyObject());
		verify(mState,times(1)).setSize(2);
	}
	
	@Test
	public void testUpdateFileStateUpdate(){
		setupMocks(OpCode.UPDATE);
		new DropboxCmdProcessor(mFileStates, mMgr).updateFileState(mCmd, testPath);
		verify(mState,times(1)).setLastModificationDate(FileTime.fromMillis(FILETIME));
		verify(mState,times(1)).setSize(2);
	}
	
	@Test
	public void testUpdateFileStateSync() {
		setupMocks(OpCode.SYNC);
		new DropboxCmdProcessor(mFileStates, mMgr).updateFileState(mCmd, testPath);
		verifyZeroInteractions(mState);
		verifyZeroInteractions(mMgr);
	}
	
	@Test
	public void testUpdateFileStateGet() {
		setupMocks(OpCode.GET);
		new DropboxCmdProcessor(mFileStates, mMgr).updateFileState(mCmd, testPath);
		verifyZeroInteractions(mState);
		verifyZeroInteractions(mMgr); 
	}
	
	@Test
	public void testCmdReceivedUpdate() {
		setupMocks(OpCode.UPDATE);
		new DropboxCmdProcessor(mFileStates,mMgr).cmdReceived(mCmd);
		try {
			verify(mMgr,times(1)).write(testPath, new byte[]{100, 101},true);
			verify(mMgr,never()).delete(testPath);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	@Test
	public void testCmdReceivedAdd() {
		setupMocks(OpCode.ADD);
		new DropboxCmdProcessor(mFileStates,mMgr).cmdReceived(mCmd);
		try {
			verify(mMgr,times(1)).write(testPath, new byte[]{100, 101},false);
			verify(mMgr,never()).delete(testPath);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	@Test
	public void testCmdReceivedRemove() {
		setupMocks(OpCode.REMOVE);
		new DropboxCmdProcessor(mFileStates,mMgr).cmdReceived(mCmd);
		try {
			verify(mMgr,never()).write(testPath, new byte[]{100, 101},false);
			verify(mMgr,times(1)).delete(testPath);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	@Test
	public void testCmdReceivedSync() {
		setupMocks(OpCode.SYNC);
		new DropboxCmdProcessor(mFileStates,mMgr).cmdReceived(mCmd);
		try {
			verify(mMgr,never()).write(testPath, new byte[]{100, 101},false);
			verify(mMgr,never()).delete(testPath);
		} catch (IOException e) { e.printStackTrace(); }
	}

	@Test
	public void testCmdReceivedGet() {
		setupMocks(OpCode.GET);
		new DropboxCmdProcessor(mFileStates,mMgr).cmdReceived(mCmd);
		try {
			verify(mMgr,never()).write(testPath, new byte[]{100, 101},false);
			verify(mMgr,never()).delete(testPath);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
}
