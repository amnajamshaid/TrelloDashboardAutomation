package trelloPackage;

import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends LoginBase {


    @Test
    void Test_Login_Correct_Password_Email() throws IOException {
        try {
            // Login in trello
            loginTrelloDashboard();
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }

}
