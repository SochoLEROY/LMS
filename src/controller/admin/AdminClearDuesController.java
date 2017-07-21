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
public class AdminClearDuesController {
    
    private AdminLandingController ALC;
    private Stage current;
    
    @FXML private TextField regnoField;
    
    private String regno;
    
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage){
        ALC = main;
        current = secondaryStage;
    }
    
    public void handleClear(){
        regno = regnoField.getText().trim();
        boolean doClear = false;
        boolean exists = false;
        int dueamt = 0;
        String sql = "Select * from student where regno = '"+regno+"'";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                exists = true;
                dueamt = rs.getInt("dues");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (exists==true){
            if(dueamt <= 0){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("No Dues");
                alert1.setHeaderText("Dues: 0");
                alert1.setContentText("There is no Due pending for this Reg No");
                Optional<ButtonType> result = alert1.showAndWait();
                if(result.get() == ButtonType.OK){
                    current.close();
                }
            }
            
            else{
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Clear Dues");
                alert1.setHeaderText("Dues : "+dueamt);
                alert1.setContentText("Do you want to clear all dues for Reg No : "+regno);
                Optional<ButtonType> result = alert1.showAndWait();
                if(result.get() == ButtonType.OK){
                    doClear = false;
                    clearDue();
                }
                else{
                    current.close();
                }
            }
        }
        else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Student Not Found");
            alert1.setContentText("Please Check the Reg No");
            Optional<ButtonType> result = alert1.showAndWait();
            if(result.get() == ButtonType.OK){
                current.close();
            }
        }
    }
    
    public void handleCancel(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/ClearDues.txt";
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
    
    public void clearDue(){
        String sql = "update student set dues = 0 where regno = '"+regno+"'";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        sql = "delete from dues where regno = '"+regno+"'";
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Cleared");
        alert1.setHeaderText("All Dues cleared");
        alert1.setContentText("All Dues have been cleared for Reg No: "+regno);
        Optional<ButtonType> result = alert1.showAndWait();
        if(result.get() == ButtonType.OK){
            current.close();
        }
    }
}
