package testExcelData;

import org.testng.annotations.BeforeTest;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import testBase.BaseClass;

import static io.restassured.RestAssured.given;

public class TC001_CircuitLocation extends BaseClass {
	
	
	@DataProvider(name="circuits") // getting data from external sheet
	@Test
	public String[][] getLocationTestData(){
		String[][] finalData = excelData("Data.xlsx","Circuits"); // calling the method which is in testbase and giving values as arguments
	System.out.println(finalData);
		return finalData;
	}
	
	@BeforeTest
	public void init(){
		RestAssured.baseURI="http://ergast.com/";
				RestAssured.basePath="api/f1/circuits/";
			}
	
	@SuppressWarnings("deprecation")
	@Test(dataProvider="circuits")
	public void locationTest(String circuitId,String location){
		Response response = given().pathParameters("city", circuitId).get("/{city}");
		System.out.println(response.body().prettyPrint());
				
	}
}
