package testExcelData;

import static io.restassured.RestAssured.given;

import org.apache.xmlbeans.impl.common.SystemCache;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import testBase.BaseClass;

public class TC003_StudentList extends BaseClass {

	@DataProvider(name = "studentList")
	public String[][] getTestData() {
		String[][] finalData = excelData("Data.xlsx", "studentApp");
		return finalData;
	}

	@BeforeTest
	public void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/student";
	}

	@Test(dataProvider = "studentList")
	public void getStudentDetails(String id, String firstName, String lastName, String email, String programme,
			String courses) {
		Response response = given().pathParameters("id", id).when().get("/{id}");
		System.out.println(response.body().prettyPrint());
	}

}
