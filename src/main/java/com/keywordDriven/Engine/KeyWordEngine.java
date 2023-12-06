package com.keywordDriven.Engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.keywordDriven.Base.BasicScript;

public class KeyWordEngine {

	public WebDriver driver;
	public Properties prop;

	public BasicScript base;

	public static Workbook book;
	public static Sheet sheet;

	public final String Scenario_Excel_Path = "F:\\JavaPrograms\\Frameworks\\com.keywordDriven\\src\\main\\java\\com\\keywordDriven\\scenarios\\TestSteps.xlsx";

	public void startExecution(String SheetName) {

		String LocatorName = null;
		String LocatorValue = null;
		FileInputStream FIS = null;
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		try {
			FIS = new FileInputStream(Scenario_Excel_Path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(FIS);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(SheetName);
		int k = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
				String LocatorColValue = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
				if (!LocatorColValue.equalsIgnoreCase("NA")) {
					
					 LocatorName = LocatorColValue.split("=")[0].trim(); // id,name
					System.out.println(LocatorName);
					 LocatorValue = LocatorColValue.split("=")[1].trim(); //value
					System.out.println(LocatorValue);
					
					/*
					 * if (sheet.getRow(i + 1) != null && sheet.getRow(i + 1).getLastCellNum() > k +
					 * 1) { LocatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
					 * 
					 * }
					 */
				}
				String Script_action = sheet.getRow(i + 1).getCell(k + 3).toString().trim();
				String Sript_Value = sheet.getRow(i + 1).getCell(k + 4).toString().trim();

				switch (Script_action) {
				case "Open browser":
					base = new BasicScript();
                    try {
                    	
                        prop = base.Initialize_Properties();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Sript_Value.isEmpty() || Sript_Value.equals("NA")) {
                    	driver = base.Initialize_Browser(prop.getProperty("browser"));
                    	
                        
                    } else {
                    	
                        driver = base.Initialize_Browser(Sript_Value);
                    }
                //  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
                    break;

				case "Enter URL":
					if (Sript_Value.isEmpty() || Sript_Value.equals("NA")) {
						
						driver.get(prop.getProperty("url"));
					} else {
						
						driver.get(Sript_Value);
						Thread.sleep(2000);
					}
					break;
				case "quit":
					Thread.sleep(5000);
					driver.quit();
					break;
				default:
					break;
				}

				switch (LocatorName) {
				case "name":					
					 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					 WebElement element = driver.findElement(By.name(LocatorValue));
				        wait.until(ExpectedConditions.elementToBeClickable(element));
					if (Script_action.equalsIgnoreCase("SendKeys")) {
						Thread.sleep(5000);
						//element.clear();
						element.sendKeys(Sript_Value);
						
					} else if (Script_action.equals("click")) {
						element.click();
					}
					LocatorName = null;
					break;
				case "xpath":
					WebElement elementBtn = driver.findElement(By.xpath(LocatorValue));
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
					wait2.until(ExpectedConditions.elementToBeClickable(elementBtn));
					elementBtn.click();
					Thread.sleep(5000);
					LocatorName=null;
					break;
				default:
					break;
				}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		}
}