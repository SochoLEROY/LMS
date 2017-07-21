/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class LoginWindowController {
    
    //Views
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Button loginAdminButton;
    @FXML private Button loginStudentButton;
    
    //Instance of the main class that can be used to access the main class
    private LibraryManagementSystem LMS;
    //Instance of Stage used to refer to the primary stage - used for closing parent window
    private Stage primaryStage;
    
    private String user;
    private String pass;
    
    //setMain method to create access to the main class
    public void setMain(LibraryManagementSystem main, Stage stage){
        this.LMS = main;
        //initializing the instance created with the parameters passed
        primaryStage = stage;
    }
    
    public void handleButtonAdmin(){
        user = userField.getText().trim();
        pass = passField.getText().trim();
        if(user.isEmpty() || pass.isEmpty()){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Login as Admin Failed");
            errorAlert.setContentText("ID or Password cannot be empty");
            errorAlert.showAndWait();
        }
        else{
            String sql = "SELECT id, pass FROM adlog";
        
            try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){
            
                // loop through the result set
                boolean userFound = false;
                boolean passMatch = false;
                while (rs.next()) {
                    String id = rs.getString("id");
                    String password = rs.getString("pass");
                    if(user.equals(id)){
                        userFound = true;
                        if(password.equals(pass)){
                            passMatch = true;
                        }
                        break;
                    }
                }
                if(userFound == false){
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error occured");
                    errorAlert.setHeaderText("Login as Admin Failed");
                    errorAlert.setContentText("Admin not Found");
                    errorAlert.showAndWait();
                }
                else if (passMatch == false){
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error occured");
                    errorAlert.setHeaderText("Login as Admin Failed");
                    errorAlert.setContentText("Incorrect password");
                    errorAlert.showAndWait();
                }
                else{
                    LMS.adminWindow();
                }
            } 
            catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }
    }
    
    public void handleButtonStudent(){
        user = userField.getText().trim();
        pass = passField.getText().trim();
        if(user.isEmpty() || pass.isEmpty()){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Login as Student Failed");
            errorAlert.setContentText("Please enter the correct credentials");
            errorAlert.showAndWait();
        }
        else{
            String sql = "SELECT id, pass FROM stlog";
        
            try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){
            
                // loop through the result set
                boolean userFound = false;
                boolean passMatch = false;
                while (rs.next()) {
                    String id = rs.getString("id");
                    String password = rs.getString("pass");
                    if(user.equals(id)){
                        userFound = true;
                        if(password.equals(pass)){
                            passMatch = true;
                        }
                        break;
                    }
                }
                if(userFound == false){
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error occured");
                    errorAlert.setHeaderText("Login as Student Failed");
                    errorAlert.setContentText("Student not Found");
                    errorAlert.showAndWait();
                }
                else if (passMatch == false){
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error occured");
                    errorAlert.setHeaderText("Login as Student Failed");
                    errorAlert.setContentText("Incorrect password");
                    errorAlert.showAndWait();
                }
                else{
                    LMS.studentWindow(user);
                }
            } 
            catch (SQLException e) {
            System.out.println(e.getMessage());
            }
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
