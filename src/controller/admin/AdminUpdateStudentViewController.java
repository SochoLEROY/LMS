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
public class AdminUpdateStudentViewController {
    
    //Views
    @FXML private TextField regnoField;
    @FXML private Button updStudUpdateButton;
    @FXML private Button updStudCancelButton;
    @FXML private Button helpButton;
    
    /*Instances of AdminLandingController and Stage created to refer to the 
    parameters recieved by setAdminLanding. These will be used to access caller
    class and to close the current window.
    */
    private AdminLandingController ALC;
    private Stage current;
    
    private String regno;
    private String name;
    private String college;
    private String dept;
    private int year;
    private int dues;
    private String email;
    
    //Function to access AdminLandingController class - needed for closing this window
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage){
        ALC = main;
        current = secondaryStage;
    }
    
    public void handleUpdate(){
        System.out.println("update function called");
        regno = regnoField.getText();
        boolean regFound = false;
        if (regno.isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Could not find Students");
            errorAlert.setContentText("At least one field is necessary.");
            errorAlert.showAndWait();
        }
        
        else{   
            String sql = "Select * from student where regno = '"+regno+"'";
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
            
            if (regFound==false){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error occured");
                errorAlert.setHeaderText("Student Not Found");
                errorAlert.setContentText("Enter Correct Details");
                errorAlert.showAndWait();
            }
            else{   
                try{
                    FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/UpdateStudentView.fxml"));
                    AnchorPane pane2 = loader2.load();
                    
                    UpdateStudentController updateStudController = loader2.getController();
            
                    Scene scene2 = new Scene(pane2);
            
                    updateStudController.setAdminLanding(ALC, current, regno, name, college, dept, year, email, dues);
            
                    current.setScene(scene2);
                    current.show();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        
    }
    
    public void handleCancel(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/AdminCheckStudent.txt";
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