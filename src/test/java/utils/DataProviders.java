package utils;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	String excelFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\OpenCartTestData.xlsx";
	MyXLSReader myXLSReader ;
	Object[][] data;
	
	@DataProvider(name="ValidloginDataSupplier")
	public Object[][] dataProviderMethodForLoginTest() {
		
		try {
			myXLSReader = new MyXLSReader(excelFilePath);
			data = ExcelUtil.getTestData(myXLSReader,"LoginTest Valid Credentials","data");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return data;
	}
	
	@DataProvider(name="InvalidloginDataSupplier")
	public Object[][] dataProviderMethodForInvalidLoginTest() {
		try {
			myXLSReader = new MyXLSReader(excelFilePath);
		    data = ExcelUtil.getTestData(myXLSReader,"LoginTest Invalid Credentials","data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

}