package excelReaderClass;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataRead {
		
		public FileOutputStream fileOut = null;
		public FileInputStream fis;
		public String path;
		public XSSFWorkbook workbook;
		public XSSFSheet sheet;
		public XSSFRow row;
		public XSSFCell cell;


	public ExcelDataRead (String path){
		this.path=path;
		try{
		fis = new FileInputStream(path);      //create an object for FileInputStream to get the path of excel doc - it will read your data in excel sheet at one go
		workbook = new XSSFWorkbook(fis);    //create and object for XSSFWorkbook all the sheets form the excel workbook
		}catch(Exception e){
		 e.printStackTrace();
		}
	}

	@SuppressWarnings({ "deprecation"})
	public String[][] getSheetData(String sheetName,String excelName){  //return type is 2D array of String type for rows and columns
		String dataSets[][] = null;
		try{
			XSSFSheet sheet = workbook.getSheet(sheetName); //it will get the particular sheet from the excel file
			int totalRow = sheet.getLastRowNum()+1;            //it will give active rows which contains data to be tested
		int totalColumns = sheet.getRow(0).getLastCellNum(); //it will give no of active cells
		dataSets = new String[totalRow-1][totalColumns];                 // creating an object for 2D array of type String
	   		for(int i=1;i<totalRow;i++){            //to get the rows eliminating headings (username, password,run mode that is the reason we have to give 1)
			XSSFRow rows = sheet.getRow(i);      
			for(int j=0;j<totalColumns;j++){     //internal for loop starts for the columns for that particular row to get the cells
			XSSFCell cell = rows.getCell(j);      //here we have to start from 0 because here it will take based on rows.getCell(j) so rows=1
			if(cell!=null){
			if(cell.getCellType() == Cell.CELL_TYPE_STRING) //this is used for type of data in cell
				dataSets[i-1][j]  = String.valueOf(cell.getStringCellValue());
			else if (cell.getCellType()== Cell.CELL_TYPE_NUMERIC){
				dataSets[i-1][j]  = String.valueOf(cell.getNumericCellValue());
				
				} else
					dataSets[i-1][j]=String.valueOf(cell.getBooleanCellValue());
					}
		       }
	   		}
	         return dataSets;
	} catch(Exception e){
		System.out.println("Exception in reading file"+e.getMessage());
		e.printStackTrace();
	}
		return dataSets;
		
	}


}
