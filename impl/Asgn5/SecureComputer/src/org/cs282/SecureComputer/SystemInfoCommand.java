package org.cs282.SecureComputer;


public class SystemInfoCommand extends Command {
	private String data;
	
	@Override
	public String invoke(Device d) {
		switch(d) {
		case MAC:
			return invokeMac();
		case LINUX:
			return invokeLinux();
		default:
			return null;
		}	
	}
	
	public String invokeMac() {
		String data = "Current User: " + System.getProperty("user.name");
		
		return data;
	}
	
	public String invokeLinux() {
		return null;
	}
	
	public String getData() {
		return null;
	}

}
