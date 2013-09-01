package org.cs27x.dropbox.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.FileManager;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DropboxCmdProcessorTest {
	FileStates mFileStates;
	FileManager mFileManager;
	FileState mState;
	FileManager mMgr;
	DropboxCmd mCmd;
	Path testPath;

	@Before
	public void setUp() throws Exception {
		mFileStates = mock(FileStates.class);
		mState = mock(FileState.class);
		testPath = FileSystems.getDefault().getPath("test-data");
		mCmd = mock(DropboxCmd.class);
		mMgr = mock(FileManager.class);
		when(mFileStates.getState(testPath)).thenReturn(mState);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdateFileStateRemove() {
		setupUpdateFileState(OpCode.REMOVE);
		verify(mState,never()).setLastModificationDate((FileTime)anyObject());
	}
	
	@Test
	public void testUpdateFileStateAdd(){
		setupUpdateFileState(OpCode.ADD);
		verify(mState,times(1)).setLastModificationDate((FileTime)anyObject());
		verify(mState,times(1)).setSize(anyLong());
	}
	
	public void setupUpdateFileState(OpCode op){
		when(mCmd.getOpCode()).thenReturn(op);
		DropboxCmdProcessor dcp = new DropboxCmdProcessor(mFileStates, mMgr);
		dcp.updateFileState(mCmd, testPath);
	}

}
