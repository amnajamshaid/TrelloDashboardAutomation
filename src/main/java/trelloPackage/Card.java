package trelloPackage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static trelloPackage.Board.boardName;
import static trelloPackage.List.listID;

public class Card extends Base{
    protected static String cardName;
    protected static String cardID;

    String listName = "To Do";

    void CreateCard() {
        try {
            // Setting rest assured base URI
            RestAssured.baseURI = keyValue.getPropertyValues("TRELLO_URL");

            // Using JSON-Simple Library to create json
            JSONObject requestParams = new JSONObject();
            requestParams.put("name", "Test Card" + random(2, false, true));
            requestParams.put("desc","This card is for testing purpose");
            requestParams.put("idList",listID);
            requestParams.put("key", keyValue.getPropertyValues("API_KEY"));
            requestParams.put("token", keyValue.getPropertyValues("TOKEN"));

            // Creating a card using rest assured post request
            Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestParams.toJSONString())
                    .when()
                    .post("/1/cards")
                    .then()
                    .extract().response();

            // Verifying the response using assertions from TestNG
            Assert.assertEquals(200, response.getStatusCode());

            // Retrieve the card name
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.getBody().asString());
            cardName = (String) json.get("name");
            cardID = (String) json.get("id");
        }catch(Exception e){
            System.out.println("Exception :" + e);
        }
    }

    void moveCard() {
        try {
            boolean result = false;

            // Open board to move a card to list
            WebElement createdBoard = driver.findElement(By.xpath("//div[@title='" + boardName + "']"));
            createdBoard.click();

            // Wait for the card that you want to move to load
            WebElement createdCard = new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + cardName + "']")));
            createdCard.click();

            // Click on the move button
            WebElement moveTag = new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Move']")));
            moveTag.click();

            // Select board name on which you want to move your card
            selectBoard(boardName);

            // Select the list on which you want to move your card
            selectList(listName);

            // Click on move button
            WebElement moveButton = new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Move']")));
            moveButton.click();
            if(isCardOnCorrectList(cardID,"To Do")) {
                // Click on cross button
                WebElement closeButton = new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='#']")));
                Actions actions = new Actions(driver);
                actions.moveToElement(closeButton).click().build().perform();
            }else{
                System.err.println("Card didn't move on the correct list");
            }
        }catch (Exception e) {
            System.err.println("Exception :" + e);
        }
    }
    void selectBoard(String boardName) {
        new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class='js-select-board']")));
        Select select = new Select(driver.findElement(By.xpath("//select[@class='js-select-board']")));
        String selectedOption = select.getFirstSelectedOption().getText();
        if(!selectedOption.contains(boardName)) {
            select.selectByVisibleText(boardName);
        }
    }

    void selectList(String listName) {
        new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class='js-select-list']")));
        Select select = new Select(driver.findElement(By.xpath("//select[@class='js-select-list']")));
        select.selectByVisibleText(listName);
    }

    boolean isCardOnCorrectList(String cardID, String listName) {
        try {
            String cardListName = getCardListName(cardID);
            if(cardListName.equals(listName)){
                return  true;
            }
        }catch(Exception e){
            System.out.println("Exception :" + e);
        }
        return false;
    }

    String getCardListName(String cardID) {
        String cardListName = "";
        try {
            // Setting rest assured base URI
            RestAssured.baseURI = keyValue.getPropertyValues("TRELLO_URL");

            // Using JSON-Simple Library to create json
            JSONObject requestParams = new JSONObject();
            requestParams.put("key", keyValue.getPropertyValues("API_KEY"));
            requestParams.put("token", keyValue.getPropertyValues("TOKEN"));

            // Creating a card using rest assured post request
            Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestParams.toJSONString())
                    .when()
                    .get("/1/cards/"+cardID+"/list")
                    .then()
                    .extract().response();

            // Verifying the response using assertions from TestNG
            Assert.assertEquals(200, response.getStatusCode());

            // Retrieve the card name
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.getBody().asString());
            cardListName = (String) json.get("name");
        }catch (Exception e){
            System.out.println("Exception" + e);
        }
        return cardListName;
    }
}
