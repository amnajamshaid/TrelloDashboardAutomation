package trelloPackage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static trelloPackage.Board.boardID;

public class List extends Base {

    protected static String listID = "";

    protected static String listName= "";

    void CreateList() {
        try {
            // Setting rest assured base URI
            RestAssured.baseURI = keyValue.getPropertyValues("TRELLO_URL");

            // Using JSON-Simple Library to create json
            JSONObject requestParams = new JSONObject();
            requestParams.put("name", "Test List" + random(2, false, true));
            requestParams.put("idBoard",boardID);
            requestParams.put("key", keyValue.getPropertyValues("API_KEY"));
            requestParams.put("token", keyValue.getPropertyValues("TOKEN"));

            // Creating a list using rest assured post request
            Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestParams.toJSONString())
                    .when()
                    .post("/1/lists")
                    .then()
                    .extract().response();

            // Verifying the response using assertions from TestNG
            Assert.assertEquals(200, response.getStatusCode());

            // Retrieve the list id to create a card in the list
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.getBody().asString());
            listID = (String) json.get("id");
            listName = (String) json.get("name");
        }catch(Exception e){
            System.out.println("Exception :" + e);
        }

    }
}
