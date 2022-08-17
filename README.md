### Implemented Solution
I have created a suite of five testcases. Some of them are implemented using UI and some with RestAssured. 
Below is the detail for it.

### **Test Suite**
1. Login into trello using selenium.  
2. Create a board using rest assured. (Trello POST /1/boards/)
3. Create a card using rest assured.  (Trello POST /1/cards)
4. Create a list using rest assured.  (Trello POST /1/lists)
5. Move the created card to another list using selenium and then verification is done using rest assured. (Trello GET /1/cards/{id}/list) 

### **Testing Flow**
* Application will start and user will be logged in.
* A new board will be created with name **"Test Board(RandomNumber)"**.
* After that  **"Test Board(RandomNumber)"** will open and a new list with name **"Test List(RandomNumber)"** will be created in it.
* After **"Test List(RandomNumber)"** is created, A new card with name **"Test Card(RandomNumber)"** will be added into **"Test List(RandomNumber)"**.
* After that **"Test Card(RandomNumber)"** will be moved to **"To Do"** List. 
* Browser will close.

### Tools
The tools,language and framework that is used is detailed as:

* **Programming Language**
: Java

* **UI Automotaion**
: Selenium

* **Testing Framework**
: Testng

* **Backend Automation**
: RestAssured

### How to Execute Trello Suite
* Change config.properties file by adding user email,password,token and API key in the config.properties file.
![](D:\config.PNG)
* Click on testng.xml and run testng.xml.
* In test-output folder you will see emailable-report.html. In this report you can see the status of all the passed/failed testcases.
![](D:\Capture.PNG)

