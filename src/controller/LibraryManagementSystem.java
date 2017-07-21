/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class LibraryManagementSystem extends Application {
    
    //This instance of stage is created to refer to the original stage created 
    //when the application starts
    private Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Initializing the instance created with the default stage created
        primaryStage = stage;
        primaryStage.show();
        loginWindow();
    }
    
    public void loginWindow(){
        try{
            FXMLLoader loader = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/LoginWindowView.fxml"));
            AnchorPane pane1 = loader.load();
            
            LoginWindowController loginWindowController = loader.getController();
            loginWindowController.setMain(this, primaryStage);
            
            Scene scene = new Scene(pane1);
            
            primaryStage.setScene(scene);
            primaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
      
    }
    
    public void adminWindow(){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/AdminLandingView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            AdminLandingController adminLandingController = loader2.getController();
            adminLandingController.setMain(this, primaryStage);
            
            Scene scene2 = new Scene(pane2);
            
            primaryStage.setScene(scene2);
            primaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
      
    }
    
    public void studentWindow(String userid){
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/StudentLandingView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            StudentLandingController studentLandingController = loader2.getController();
            studentLandingController.setMain(this, primaryStage, userid);
            
            Scene scene2 = new Scene(pane2);
            
            primaryStage.setScene(scene2);
            primaryStage.show();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
      
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
