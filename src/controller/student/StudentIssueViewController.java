/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.student;

import controller.HelpController;
import controller.LibraryManagementSystem;
import controller.StudentLandingController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class StudentIssueViewController {
    
    private StudentLandingController SLC;
    private Stage current;
    
    @FXML private TextField libidField;
    
    private String libid;
    private String regno;
    
    public void setStudentLanding(StudentLandingController main, Stage secondaryStage, String regno){
        SLC = main;
        current = secondaryStage;
        this.regno = regno;
    }
    
    public void handleIssue(){
        libid = libidField.getText();
        boolean bookExists = getBook();
        if(bookExists == false){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Book could not be found");
            errorAlert.setContentText("Please Check the Lib id of the book");
            errorAlert.showAndWait();
        }
        else{ 
            boolean bookAvail = getAvail();
            if(bookAvail == false){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error occured");
                errorAlert.setHeaderText("Book Already Issued");
                errorAlert.setContentText("Look for Another Copy or come back later");
                errorAlert.showAndWait();
            }
            else{
                boolean isEligible = getEligible();
                if(isEligible==false){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error occured");
                    errorAlert.setHeaderText("Book could not be issued");
                    errorAlert.setContentText("You already have 2 books issued currently.");
                    errorAlert.showAndWait();
                }
                else{
                    String sql = "insert into issued values (?,?,date('now'))";
                    String sql2 = "update book set status = 'Issued' where libid = '"+libid+"'";
                    try (Connection conn = this.connect();
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                        pstmt.setString(1, regno);
                        pstmt.setString(2, libid);
                        pstmt.executeUpdate();
                        pstmt2.executeUpdate();
            
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Issued");
                        alert1.setHeaderText("Book Issued");
                        alert1.setContentText("The Book was issued successfully.");
                        Optional<ButtonType> result = alert1.showAndWait();
                        if(result.get() == ButtonType.OK){
                            current.close();
                        }
                    }
                    catch (SQLException e) {
                        System.out.println(e.getMessage());
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Failed");
                        alert1.setHeaderText("Book Issue Failed");
                        alert1.setContentText("The Book could not be Issued. Please Try again.");
                        alert1.showAndWait();
                    }
                }
            }
        }
    }    
    
    public boolean getEligible(){
        boolean elig = true;
        String sql = "Select count(*) as total from issued where regno = '"+regno+"'";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                if(rs.getInt("total")==2){
                    elig = false;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return elig;
    }
    
    public boolean getBook(){
        boolean exists = false;
        String sql = "Select * from book where libid = '"+libid+"'";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                exists = true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exists;
    }
    
    public boolean getAvail(){
        boolean avail = true;
        String sql = "Select * from book where libid = '"+libid+"'";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                if(!rs.getString("status").equals("Available"))
                    avail = false;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return avail;
    }

 
    public void handleCancel(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/IssueRequest.txt";
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
