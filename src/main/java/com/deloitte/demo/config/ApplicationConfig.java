package com.deloitte.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Helper class to standardize access to application properties
 * 
 * @author Sagar Parmar
 *
 */
@Component
@ConfigurationProperties("todo-app")
public class ApplicationConfig {

	private Security security;

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public static class Security {

		private boolean csrfEnabled;

		public boolean isCsrfEnabled() {
			return csrfEnabled;
		}

		public void setCsrfEnabled(boolean csrfEnabled) {
			this.csrfEnabled = csrfEnabled;
		}
	}

}
