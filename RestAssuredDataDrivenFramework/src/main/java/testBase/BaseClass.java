package testBase;

import java.sql.ResultSetMetaData;

import excelReaderClass.ExcelDataRead;

public class BaseClass {
	
	public String[][] excelData(String excelName,String sheetName ){
		String path= System.getProperty("user.dir")+"/src/main/java/testData/"+ excelName;
		 ExcelDataRead excel = new ExcelDataRead(path);
		String[][] data =  excel.getSheetData(sheetName, excelName);
		return data;
	}
		
}
