package trelloPackage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;

public class Board extends Base{
    protected static String boardID;
    protected static String boardName = "";

    void CreateBoard() {
        try {
            // Setting rest assured base URI
            RestAssured.baseURI = keyValue.getPropertyValues("TRELLO_URL");

            // Using JSON-Simple Library to create json
            JSONObject requestParams = new JSONObject();
            requestParams.put("name", "Test Board" + random(2, false, true));
            requestParams.put("key", keyValue.getPropertyValues("API_KEY"));
            requestParams.put("token", keyValue.getPropertyValues("TOKEN"));

            // Creating a board using rest assured post request
            Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestParams.toJSONString())
                    .when()
                    .post("/1/boards/")
                    .then()
                    .extract().response();

            // Verifying the response using assertions from TestNG
            Assert.assertEquals(200,response.getStatusCode());

            // Retrieve the board id to create a list on board
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.getBody().asString());
            boardID = (String) json.get("id");
            boardName = (String) json.get("name");
        } catch (Exception e) {
            System.out.println("Exception: "+ e);
        }
    }
}
