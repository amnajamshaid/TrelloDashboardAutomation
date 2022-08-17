package trelloPackage;

import org.testng.annotations.Test;

public class CardTest extends Card{

    @Test
    void Test_Create_Card(){
        try {
            // Create a card using rest assured post api
            CreateCard();
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }

    @Test
    void Test_Move_Card_To_List(){
        // Open board to modify
        moveCard();
    }
}
