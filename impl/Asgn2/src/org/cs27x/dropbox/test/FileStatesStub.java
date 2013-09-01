package org.cs27x.dropbox.test;

import java.io.IOException;
import java.nio.file.Path;

import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.States;

public class FileStatesStub implements States {

	@Override
	public FileState getState(Path p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileState getOrCreateState(Path p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileState insert(Path p) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileEvent filter(FileEvent evt) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
