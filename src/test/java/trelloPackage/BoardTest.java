package trelloPackage;

import org.testng.annotations.Test;

public class BoardTest extends Board{

    @Test
    void Test_Create_Board(){
        try {
            // Create a board using rest assured post api
            CreateBoard();
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }

}
