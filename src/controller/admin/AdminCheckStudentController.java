/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import controller.AdminLandingController;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class AdminCheckStudentController {
    /*Instances of AdminLandingController and Stage created to refer to the 
    parameters recieved by setAdminLanding. These will be used to access caller
    class and to close the current window.
    */
    private AdminLandingController ALC;
    private Stage current;
    
    private String userid;
    private String regno;
    private String name;
    private String college;
    private String dept;
    private int year;
    private int dues;
    private String email;
    
    @FXML private TextField userField;
    @FXML private TextField regField;
    
    
    
    //Function to access AdminLandingController class - needed for closing this window
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage){
        ALC = main;
        current = secondaryStage;
    }
    
    public void handleSearch(){
    
        userid = userField.getText();
        regno = regField.getText();
        boolean regFound = false;
        if (userid.isEmpty() && regno.isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Could not find Students");
            errorAlert.setContentText("At least one field is necessary.");
            errorAlert.showAndWait();
        }
        else{   
            String sql = "";
            if(!regno.isEmpty()){
                sql = "Select * from student where regno = '"+regno+"'";
            }
            else{
                sql = "Select * from student where userid = '"+userid+"'";
            }
        
            try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
                // loop through the result set
                while (rs.next()) {
                    regno = rs.getString("regno");
                    name = rs.getString("name");
                    college = rs.getString("college");
                    dept = rs.getString("dept");
                    year = rs.getInt("year");
                    email = rs.getString("email");
                    dues = rs.getInt("dues");
                    System.out.println(regno+'\n'+name+'\n'+college+'\n'+dept+'\n'+year+'\n'+email+'\n'+dues);
                   
                    regFound = true;
                }
                
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        if (regFound==false){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Student Not Found");
            errorAlert.setContentText("Enter Correct Details");
            errorAlert.showAndWait();
        }
        else{   
            try{
                FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/CheckStudentView.fxml"));
                AnchorPane pane2 = loader2.load();
            
                StudentViewController studViewController = loader2.getController();
            
                Scene scene2 = new Scene(pane2);
            
                studViewController.setAdminLanding(ALC, current, regno, name, college, dept, year, email, dues);
            
                current.setScene(scene2);
                current.show();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void handleCancel(){
        current.close();
    }
    
    public void handleHelp(){
        //New window that displays user-manual
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
