/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.LibraryManagementSystem;
import controller.admin.AdminAcceptReturnController;
import controller.admin.AdminAddBookController;
import controller.admin.AdminCreateController;
import controller.admin.AdminCheckBookController;
import controller.admin.AdminCheckStudentController;
import controller.admin.AdminClearDuesController;
import controller.admin.AdminDelBookController;
import controller.admin.AdminUpdateStudentViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class AdminLandingController {

//Views
    @FXML private Button adminAddBookButton;
    @FXML private Button adminDelBookButton;
    @FXML private Button adminCheckBookButton;
    @FXML private Button logoutButton;
    @FXML private Button adminCheckStudentButton;
    @FXML private Button adminApproveButton;
    @FXML private Button adminUpdateStudentButton;
    @FXML private Button helpButton;
    
    
    //Instance of the main class that can be used to access the main class
    private LibraryManagementSystem LMS;
    
    
    //setMain method to create access to the main class
    public void setMain(LibraryManagementSystem main, Stage stage){
        this.LMS = main;
    }
    
    public void handleAddBook(){
        addBookWindow();
    }
    
    public void handleDelBook(){
        delBookWindow();
    }
    
    public void handleLogout(){
        LMS.loginWindow();
    }
    
    public void handleCheckBook(){
        checkBookWindow();
    }
    
    public void handleAcceptReturn(){
        returnBookWindow();
    }
    
    public void handleCreate(){
        createWindow();
    }
    
    public void handleUpdateStudent(){
        updateStudWindow();
    }
    
    public void handleClear(){
        clearDuesWindow();
    }
    
    public void handleHelp(){
        String path = "src/help/AdminLanding.txt";
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
    public void addBookWindow(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/AdminAddBookView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            AdminAddBookController adminAddBookController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            adminAddBookController.setAdminLanding(this, secondaryStage);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
      
    }
    
    public void delBookWindow(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/AdminDelBookView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            AdminDelBookController adminDelBookController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            adminDelBookController.setAdminLanding(this, secondaryStage);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void checkBookWindow(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/AdminCheckBookView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            AdminCheckBookController adminCheckBookController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            adminCheckBookController.setAdminLanding(this, secondaryStage);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void returnBookWindow(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/AdminAcceptReturnView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            AdminAcceptReturnController adminAcceptReturnController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            adminAcceptReturnController.setAdminLanding(this, secondaryStage);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void createWindow(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/AdminCreateView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            AdminCreateController adminCreateController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            adminCreateController.setAdminLanding(this, secondaryStage);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateStudWindow(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/AdminUpdateStudentView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            AdminUpdateStudentViewController adminUpdateStudController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            adminUpdateStudController.setAdminLanding(this, secondaryStage);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void clearDuesWindow(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/AdminClearDuesView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            AdminClearDuesController adminClearDuesController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            
            Stage secondaryStage = new Stage();
            
            adminClearDuesController.setAdminLanding(this, secondaryStage);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
