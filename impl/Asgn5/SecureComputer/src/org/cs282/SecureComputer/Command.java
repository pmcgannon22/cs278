package org.cs282.SecureComputer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/*
 * An interface for a command that will be run on the lost computer. 
 */
public abstract class Command {
	
	protected enum Device {
		MAC, WINDOWS, LINUX
	};
	
	protected Map<String, String> data_ = new HashMap<String, String>();
	
	public File invoke(Device d, boolean file) {
		return null;
	}
	public String invoke(Device d) {
		return null;
	}
	
	public void putData(String key, String data) {
		data_.put(key, data);
	}
}
