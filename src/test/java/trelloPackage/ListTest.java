package trelloPackage;

import org.testng.annotations.Test;

public class ListTest extends List {

    @Test
    void Test_Create_List(){
        try {
            // Create a list using rest assured post api
            CreateList();
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }

    }
}
