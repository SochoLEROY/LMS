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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class AdminCreateController {
    /*Instances of AdminLandingController and Stage created to refer to the 
    parameters recieved by setAdminLanding. These will be used to access caller
    class and to close the current window.
    */
    private AdminLandingController ALC;
    private Stage current;
    
    @FXML private TextField nameField;
    @FXML private TextField regField;
    @FXML private TextField userField;
    
    //Function to access AdminLandingController class - needed for closing this window
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage){
        ALC = main;
        current = secondaryStage;
    }
    
    public void handleCreate(){
        if (nameField.getText().trim().isEmpty()||regField.getText().trim().isEmpty()||userField.getText().trim().isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Creation Failed");
            errorAlert.setContentText("All Field are necessary");
            errorAlert.showAndWait();
        }
        else{
            String sql = "insert into student values (?, ?, ?, '', '', 0, '', 0)";
            String sql2 = "insert into stlog values (?,?)";
 
            try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                pstmt.setString(1, regField.getText().trim());
                pstmt.setString(2, userField.getText().trim());
                pstmt.setString(3, nameField.getText().trim());
                pstmt.executeUpdate();
                
                pstmt2.setString(1, userField.getText());
                pstmt2.setString(2, userField.getText());
                pstmt2.executeUpdate();
                
            
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Updated");
                alert1.setHeaderText("Student Updated");
                alert1.setContentText("The Student details were updated successfully");
                Optional<ButtonType> result = alert1.showAndWait();
                if(result.get() == ButtonType.OK){
                    current.close();
                }
            }
                        
            catch (SQLException e) {
                System.out.println(e.getMessage());
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText("Update Failed");
                alert1.setContentText("Student Details could not be updated. Please Try again.");
                alert1.showAndWait();
            }
        }
    }
    
    public void handleCancel(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/CreateAccounts.txt";
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
