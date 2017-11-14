package testDatabase;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import testBase.Student;

public class TC005_getStudentList {

	@DataProvider(name="studentsList")
	public String[][] getData(){
		
		int rowCount=0;
	    int columnCount = 0;
	    String data[][] = null; 
	    
	    try{
			Class.forName("com.mysql.jdbc.Driver"); // loads the driver class
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root"); //creates connection with the database
			Statement st = con.createStatement();// create statement
			ResultSet result = st.executeQuery("select * from students ");//execute the statement and return the result into the ResultSet
	
	// for getting column count
			
	ResultSetMetaData mt = result.getMetaData(); // Here ResultSetMetaData is an interface which provides in getting
	                                             //data about data like columnCount, columnType, columnName
			columnCount = mt.getColumnCount();
			System.out.println(columnCount);
			
	// for getitng row count		
	while(result.next())//processing the ResultSet
	{
		
		System.out.println(result.getString("courses"));
		rowCount++;
		data = new String[rowCount][columnCount];
		result.beforeFirst(); //cursor will move to zero position
		for(int row=0;row<rowCount;row++){
			result.next(); // if true cursor will move to column 1 
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
		RestAssured.baseURI="http://localhost";
		RestAssured.port=8080;
		RestAssured.basePath="/student";
	}
	
	@Test(dataProvider="studentsList")
	public void getStudentDetails(String id,String firstName,String lastName,String email, String programme, String courses){
	Response response = given().pathParameters("id",id).when().get("/{id}");
	System.out.println(response.body().prettyPrint());
		
	}
	
	
	
}
