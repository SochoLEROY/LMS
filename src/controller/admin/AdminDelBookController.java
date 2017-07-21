/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import controller.AdminLandingController;
import controller.HelpController;
import controller.LibraryManagementSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class AdminDelBookController {
    
    //Views
    @FXML private TextField delBookLibIdField;
    @FXML private Button delBookDelButton;
    @FXML private Button delBookCancelButton;
    @FXML private Button helpButton;
    
    /*Instances of AdminLandingController and Stage created to refer to the 
    parameters recieved by setAdminLanding. These will be used to access caller
    class and to close the current window.
    */
    private AdminLandingController ALC;
    private Stage current;
    
    //Function to access AdminLandingController class - needed for closing this window
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage){
        ALC = main;
        current = secondaryStage;
    }
    
    public void handleDel(){
        
       String libid = delBookLibIdField.getText().trim();
       String sql = "SELECT libid, title, publisher, author, isbn FROM book";
       String title = "";
       String author = "";
       String isbn = "";
       String publisher = "";
       String id = "";
       
       try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){
            
                // loop through the result set
                boolean idFound = false;
                while (rs.next()) {
                    id = rs.getString("libid");
                    title = rs.getString("title");
                    publisher = rs.getString("publisher");
                    author = rs.getString("author");
                    isbn = rs.getString("isbn");
                    
                    if(libid.equals(id)){
                        idFound = true;
                        break;
                    }
                }
                if (idFound==false){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error occured");
                    errorAlert.setHeaderText("Book Not Found");
                    errorAlert.setContentText("Enter Correct Lib ID");
                    errorAlert.showAndWait();
                }
                else{
                    try{
                        FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/DelBookView.fxml"));
                        AnchorPane pane2 = loader2.load();
            
                        DelViewController delViewController = loader2.getController();
            
                        Scene scene2 = new Scene(pane2);
            
                        delViewController.setAdminLanding(ALC, current, id, title, author, publisher, isbn);
            
                        current.setScene(scene2);
                        current.show();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
        }
       catch(SQLException e){
           System.out.println(e.getMessage());
       }
        //Option 1 : Change Window to show details like title and then ask for confirmation via window
        //Option 2 : Directly Ask fro confirmation via Alert. Show Title in Alert.
    }
    
    public void handleCancel(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/AdminDelBook.txt";
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/HelpView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            HelpController helpController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            helpController.setHelpLanding(secondaryStage, path);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:library.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        
        return conn;
    }
}
