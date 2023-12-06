package com.keywordDriven.TestScript;

import org.testng.annotations.Test;

import com.keywordDriven.Engine.KeyWordEngine;

public class Loginpage {
	
	public KeyWordEngine Keywordengine;
	
	@Test
	public void loignTest() {
		Keywordengine =new KeyWordEngine();
		Keywordengine.startExecution("login");
	}
	

}
