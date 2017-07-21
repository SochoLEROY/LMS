/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.LibraryManagementSystem;
import controller.student.StudentCheckBookController;
import controller.student.StudentCheckDuesController;
import controller.student.StudentIssueViewController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class StudentLandingController {
    
    @FXML private Button studLandSearchButton;
    @FXML private Button studLandDuesButton;
    @FXML private Button studLandRequestButton;
    @FXML private Button studLandingLogout;
    @FXML private Button helpButton;
    @FXML private Label userLabel;
    @FXML private Label regLabel;

    //Instance of the main class that can be used to access the main class
    private LibraryManagementSystem LMS;
    private String userid;
    private String regno;
    
    
    //setMain method to create access to the main class
    public void setMain(LibraryManagementSystem main, Stage stage, String userid){
        this.LMS = main;
        this.userid = userid;
        this.regno = getReg();
        userLabel.setText(userLabel.getText()+userid);
        regLabel.setText(regLabel.getText()+regno);
    }
    
    public void handleSearch(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/StudentCheckBookView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            StudentCheckBookController studCheckBookController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            studCheckBookController.setStudentLanding(this, secondaryStage);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void handleDues(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/StudentCheckDuesView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            StudentCheckDuesController duesViewController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            Stage secondaryStage = new Stage();
            duesViewController.setStudentLanding(this, secondaryStage, regno);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void handleRequest(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/StudentIssueView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            StudentIssueViewController issueViewController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            Stage secondaryStage = new Stage();
            issueViewController.setStudentLanding(this, secondaryStage, regno);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void handleLogout(){
        LMS.loginWindow();
    }
    
    public void handleHelp(){
        String path = "src/help/StudentLanding.txt";
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
    
    public String getReg(){
        String reg = "";
        String sql = "Select regno from student where userid = '"+userid+"'";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                    reg = rs.getString("regno");
            }
            
        }
        catch(SQLException e){
                System.out.println(e.getMessage());
        }
        return reg;
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
