package org.cs27x.dropbox.test;

import static org.junit.Assert.*;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.ConcurrentHashMap;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileStatesTest {
	private Path testPath;
	@Mock private ConcurrentHashMap<String, FileState> mMap;
	
	private final long FSIZE = 1000;
	private final long FTIME = 1378067536;

	@Before
	public void setUp() throws Exception {
		testPath = FileSystems.getDefault().getPath("test-data");
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetState() {
		FileState fs = new FileState(FSIZE,FileTime.fromMillis(FTIME));
		when(mMap.get(testPath.toAbsolutePath().toString())).thenReturn(fs);
		FileState fsReturned = new FileStates(mMap).getState(testPath);
		assertEquals(fs,fsReturned);
		verify(mMap).get(testPath.toAbsolutePath().toString());
		verifyNoMoreInteractions(mMap);
	}
	
	@Test
	public void testGetOrCreateStateNull() {
		when(mMap.get(anyString())).thenReturn(null);
		FileState fs = new FileStates(mMap).getOrCreateState(testPath);
		assertNull(fs.getLastModificationDate());
		assertEquals(fs.getSize(), -1);
		verify(mMap).put(eq(testPath.toAbsolutePath().toString()), eq(new FileState(-1,null)));
	}
	
	@Test
	public void testGetOrCreateState() {
		FileState fs = new FileState(FSIZE, FileTime.fromMillis(FTIME));
		when(mMap.get(testPath.toAbsolutePath().toString()))
			.thenReturn(fs);
		FileState returnedFs = new FileStates(mMap).getOrCreateState(testPath);
		assertEquals(fs, returnedFs);
		verify(mMap, never()).put(anyString(), any(FileState.class));
	}
	
	@Test
	public void testInsert() {
		try {
			new FileStates(mMap).insert(testPath);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
		verify(mMap).put(eq(testPath.toAbsolutePath().toString()), any(FileState.class));
		verifyNoMoreInteractions(mMap);
	}
	
	@Test
	public void testFilterCreate() {
		when(mMap.get(testPath.toAbsolutePath().toString())).thenReturn(null);
		FileEvent fe = new FileEvent(ENTRY_CREATE,testPath);
		try {
			FileEvent returnedFe = new FileStates(mMap).filter(fe);
			assertEquals(fe,returnedFe);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}
}
