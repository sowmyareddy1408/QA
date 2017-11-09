package testDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TC001_getLocationDetails {
	
	@DataProvider(name="locations")
	public String[][] getData(){
		
		int rowCount=0;
	    int columnCount = 0;
	    String data[][] = null; 
	    
	    try{
			Class.forName("com.mysql.jdbc.Driver"); // loads the driver class
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root"); //creates connection with the database
			Statement st = con.createStatement();// create statement
			ResultSet result = st.executeQuery("select * from location ");//execute the statement and return the result into the ResultSet
	
	// for getting column count
			
	ResultSetMetaData mt = result.getMetaData(); // Here ResultSetMetaData is an interface which provides in getting
	                                             //data about data like columnCount, columnType, columnName
			columnCount = mt.getColumnCount();
			System.out.println(columnCount);
			
	// for getting row count		
	while(result.next())//processing the ResultSet
	{
		rowCount++;
		data = new String[rowCount][columnCount];
		result.beforeFirst();
		for(int row=0;row<rowCount;row++){
			result.next();
		for(int col=1;col<columnCount;col++)
			data[row][col-1]=result.getString(col);
		}
	}
    result.close();
	st.close();
	con.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return data;
		}
	@BeforeTest
	public void init(){
		RestAssured.baseURI="http://api.zippopotam.us";
		RestAssured.basePath="/us/";
			}
	@Test(dataProvider="locations")
	public void getLocationDetails(String zipcode,String city,String state){
	Response response = given().pathParameters("zipcode",zipcode).get("/{zipcode}");
	System.out.println(response.body().prettyPrint());
		
	}
}

