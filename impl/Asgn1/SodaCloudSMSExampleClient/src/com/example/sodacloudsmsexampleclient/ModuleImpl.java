package com.example.sodacloudsmsexampleclient;

import java.util.HashMap;
import java.util.Map;

public class ModuleImpl implements Module {
	private Map<Class,Object> componentMap;
	
	public ModuleImpl() {
		componentMap = new HashMap<Class, Object>();
	}

	@Override
	public <T> T getComponent(Class<T> type) {
		return (T)componentMap.get(type);
	}

	@Override
	public <T> void setComponent(Class<T> type, T component) {
		componentMap.put(type, component);
	}
	
}
