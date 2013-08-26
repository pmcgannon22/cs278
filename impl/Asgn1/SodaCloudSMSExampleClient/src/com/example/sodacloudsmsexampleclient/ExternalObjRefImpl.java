package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.proxy.ObjRef;

public class ExternalObjRefImpl implements ExternalObjRef {
	
	private ObjRef extObj;
	private String serverAddress;
	
	@Override
	public void setObjRef(ObjRef extObj) {
		this.extObj =  extObj;
	}

	public void setServerAddress(String sa) {
		serverAddress = sa;
	}
	
	@Override
	public String getPubSubHost() {
		return serverAddress;
	}
	
	public String toString() {
		return getPubSubHost()+"|"+getObjRef().getUri();
	}

	@Override
	public ObjRef getObjRef() {
		return extObj;
	}

}
