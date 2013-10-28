package org.cs282.SecureComputer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.cs282.SecureComputer.Command.Device;

public abstract class RegisteredDevice {

	
	private boolean lost_;
	private int id_;
	private Map<String, Command> commands_;
	
	public RegisteredDevice(int id) {
		id_ = id;
		commands_ = new HashMap<String, Command>();
	}
	
	public boolean hasCapability(String cap) {
		return commands_.containsKey(cap);
	}
	
	public void addCommand(String name, Command cmd) {
		commands_.put(name, cmd);
	}
	
	public File runCommand(String cmd) {
		if(hasCapability(cmd)) {
			return commands_.get(cmd).invoke(Device.MAC);	
		}
		return "";
	}
	
	public void markLost() {
		lost_ = true;
	}
	
	public boolean isLost() {
		return lost_;
	}
	
}
