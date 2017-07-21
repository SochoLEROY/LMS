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
public class AdminAcceptReturnController {
    
    private AdminLandingController ALC;
    private Stage current;
    
    @FXML private TextField regField;
    @FXML private TextField libidField;
    @FXML private TextField dateField;
    
    private String regno;
    private String libid;
    private String dateText;

    public void setAdminLanding(AdminLandingController main, Stage secondaryStage){
        ALC = main;
        this.current = secondaryStage;
    }
    
    public void handleReturn(){
        regno = regField.getText().trim();
        libid = libidField.getText().trim();
        dateText = dateField.getText().trim();
        boolean regExists = checkReg();
        if(regExists == false){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Student not be found");
            errorAlert.setContentText("Please Check the Reg No of Student");
            errorAlert.showAndWait();
        }
        else{
            System.out.println("Student exists");
            boolean hasBorrowed = checkBorrow();
            if(hasBorrowed == false){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error occured");
                errorAlert.setHeaderText("No Books are issued to the student");
                errorAlert.setContentText("Please Check the Reg No of Student.");
                errorAlert.showAndWait();
            }
            else{
                System.out.println("Student Borrowed");
                boolean bookExists = getBook();
                if(bookExists == false){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error occured");
                    errorAlert.setHeaderText("Book could not be found");
                    errorAlert.setContentText("Please Check the Lib id of the book");
                    errorAlert.showAndWait();
                }
                else{ 
                    System.out.println("Book exists");
                    boolean bookIssued = checkBook();
                    if(bookIssued == false){
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error occured");
                        errorAlert.setHeaderText("Book Not Issued");
                        errorAlert.setContentText("Check Lib Id of the Book");
                        errorAlert.showAndWait();
                    }
                    else{
                        int diff = checkDiff();
                        if(diff>=0){
                            if(diff>=15){
                                int extra = diff - 14;
                                updateDues(extra);
                            }
                            updateTables();
                        }
                        else{
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Error occured");
                            errorAlert.setHeaderText("Invalid Return Date");
                            errorAlert.setContentText("Return Date must be greater than issue date");
                            errorAlert.showAndWait();
                        }
                    }
                }
            }
        }
    }
    
    public void handleCancel(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/AcceptReturn.txt";
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
    
    public boolean checkReg(){
        boolean exists = false;
        String sql = "Select * from student where regno = '"+regno+"'";
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
    
    private boolean checkBorrow(){
        boolean exists = false;
        String sql = "Select * from issued where regno = '"+regno+"'";
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
    
    public boolean checkBook(){
        boolean issued = false;
        String sql = "Select * from issued where libid = '"+libid+"'";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                issued = true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return issued;
    }
    
    public void updateTables(){
        String sql = "delete from issued where libid = '"+libid+"'";
        String sql2 = "update book set status = 'Available' where libid = '"+libid+"'";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Returned");
            alert1.setHeaderText("Returned Succesfully");
            alert1.setContentText("The Book was returned successfully.");
            Optional<ButtonType> result = alert1.showAndWait();
            if(result.get() == ButtonType.OK){
                current.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Failed");
            alert1.setHeaderText("Book Return Failed");
            alert1.setContentText("The Book could not be Returned. Please Try again.");
            alert1.showAndWait();
        }
    }
    
    public int checkDiff(){
        int diff = 0;
        String sql = "SELECT julianday('"+dateText+"') - julianday(retdate) as diff FROM issued";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                System.out.println(rs.getInt("diff"));
                diff = rs.getInt("diff");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return diff;
    }
    
    public void updateDues(int dif){
        String sql = "insert into dues values (?,?,?)";
        String sql2 = "update student set dues = dues + "+dif+" where regno = '"+regno+"'";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, regno);
            pstmt.setString(2, libid);
            pstmt.setInt(3,dif);
            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Dues updated");
            alert1.setHeaderText("Book was Overdue Issued");
            alert1.setContentText("A fine of Rs. "+dif+" was imposed");
            Optional<ButtonType> result = alert1.showAndWait();
            if(result.get() == ButtonType.OK){
                current.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Dues update Failed");
            alert1.setHeaderText("Dues could not be updated");
            alert1.setContentText("Kindly collect in cash");
            alert1.showAndWait();
        }
    }

}
