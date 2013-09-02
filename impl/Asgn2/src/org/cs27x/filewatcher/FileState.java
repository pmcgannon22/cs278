package org.cs27x.filewatcher;

import java.nio.file.attribute.FileTime;

public class FileState {

	private long size_;
	private FileTime lastModificationDate_;

	public FileState(long size, FileTime lastModificationDate) {
		super();
		size_ = size;
		lastModificationDate_ = lastModificationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((lastModificationDate_ == null) ? 0 : lastModificationDate_
						.hashCode());
		result = prime * result + (int) (size_ ^ (size_ >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileState other = (FileState) obj;
		if (lastModificationDate_ == null) {
			if (other.lastModificationDate_ != null)
				return false;
		} else if (!lastModificationDate_.equals(other.lastModificationDate_))
			return false;
		if (size_ != other.size_)
			return false;
		return true;
	}

	public long getSize() {
		return size_;
	}

	public void setSize(long size) {
		size_ = size;
	}

	public FileTime getLastModificationDate() {
		return lastModificationDate_;
	}

	public void setLastModificationDate(FileTime lastModificationDate) {
		lastModificationDate_ = lastModificationDate;
	}

}
