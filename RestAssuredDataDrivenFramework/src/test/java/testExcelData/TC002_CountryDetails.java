package testExcelData;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import testBase.BaseClass;

public class TC002_CountryDetails extends BaseClass {
	@DataProvider(name="locations") // getting data from external sheet
	public String[][] getTestData(){
		String[][] finalData = excelData("Data.xlsx","Locations"); // calling the method which is in testbase and giving values as arguments
		return finalData;
	}
	
	@BeforeTest
	public void init(){
		RestAssured.baseURI="http://services.groupkt.com";
				RestAssured.basePath="/state/get/IND/";
			}
	
@SuppressWarnings("deprecation")
@Test(dataProvider="locations")
public void countryName(String id,String country,String name,String abbr,String area,String largest_city,String capital){
	Response response = given().pathParameters("abbr", abbr).get("/{abbr}");
	System.out.println(response.body().prettyPrint());
}

}
