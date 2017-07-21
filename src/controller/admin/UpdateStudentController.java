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
public class UpdateStudentController {
    
    @FXML private TextField regField;
    @FXML private TextField nameField;
    @FXML private TextField collegeField;
    @FXML private TextField deptField;
    @FXML private TextField yearField;
    @FXML private TextField emailField;
    @FXML private TextField duesField;
    
    private AdminLandingController ALC;
    private Stage current;
    private String oreg;
    
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage, String regno, String name, String college, String dept, int year, String email, int dues){
        ALC = main;
        this.current = secondaryStage;
        this.oreg = regno;
        regField.setText(regno);
        nameField.setText(name);
        collegeField.setText(college);
        deptField.setText(dept);
        yearField.setText(""+year);
        emailField.setText(email);
        duesField.setText(""+dues);
    }
    
    public void handleUpdate(){
        if (regField.getText().trim().isEmpty()||nameField.getText().trim().isEmpty()||collegeField.getText().trim().isEmpty()||deptField.getText().trim().isEmpty()||yearField.getText().trim().isEmpty()||emailField.getText().trim().isEmpty()||duesField.getText().trim().isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Update Failed");
            errorAlert.setContentText("All Field are necessary");
            errorAlert.showAndWait();
        }
        else{
            String sql = "update student set regno = ? , name = ? , college = ? , dept = ? , year = ? , email = ? , dues = ? where regno = +"+oreg;
 
            try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regField.getText().trim());
                pstmt.setString(2, nameField.getText().trim());
                pstmt.setString(3, collegeField.getText().trim());
                pstmt.setString(4, deptField.getText().trim());
                pstmt.setInt(5, Integer.parseInt(yearField.getText().trim()));
                pstmt.setString(6, emailField.getText().trim());
                pstmt.setInt(7, Integer.parseInt(duesField.getText().trim()));
                pstmt.executeUpdate();
            
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
        String path = "src/help/UpdateStudent.txt";
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
