package com.keywordDriven.Base;


import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicScript {
	public WebDriver driver;
	public Properties prop;
	
	public WebDriver Initialize_Browser(String BrowserName) {
		if(BrowserName.equals("chrome")) {
			
			try {
				
				driver=new ChromeDriver();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		return driver;
	}
	
	public Properties Initialize_Properties(){
		prop = new Properties();
		FileInputStream Fis;
		try {
		    Fis = new FileInputStream("F:\\JavaPrograms\\JavaProjects\\KeywordDrivenFramework\\src\\main\\java\\com\\venu\\keyworddriven\\hrm\\config.properties");
		    prop.load(Fis);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return prop;
		}
}
