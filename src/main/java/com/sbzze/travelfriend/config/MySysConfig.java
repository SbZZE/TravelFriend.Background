package com.sbzze.travelfriend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mysys.setting", ignoreInvalidFields = true)
public class MySysConfig {

	private boolean isMasterNode = true;

	public boolean isMasterNode() {
		return isMasterNode;
	}

	public void setMasterNode(boolean isMasterNode) {
		this.isMasterNode = isMasterNode;
	}

	
	
	
}
