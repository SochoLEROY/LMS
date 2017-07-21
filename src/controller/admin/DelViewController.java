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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class DelViewController {
    
    @FXML private Button delButton;
    @FXML private Button cancelButton;
    @FXML private Button helpButton;
    @FXML private Label titleLabel;
    @FXML private Label authorLabel;
    @FXML private Label publisherLabel;
    @FXML private Label isbnLabel;
    @FXML private Label libidLabel;
    
    private String lId;
    
    private AdminLandingController ALC;
    private Stage current;
    
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage, String id, String title, String author, String publisher, String isbn){
        ALC = main;
        current = secondaryStage;
        titleLabel.setText(title);
        authorLabel.setText(author);
        publisherLabel.setText(publisher);
        isbnLabel.setText(isbn);
        libidLabel.setText(id);
        lId = id;
    }
    
    public void handleDel(){
        
        String sql = "delete from book where libid = ?";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, lId);
            // execute the delete statement
            pstmt.executeUpdate();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Deleted");
            alert1.setHeaderText("Book Deleted");
            alert1.setContentText("The Book with Lib Id "+lId+" was deleted successfully");
            Optional<ButtonType> result = alert1.showAndWait();
            if(result.get() == ButtonType.OK){
                current.close();
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void handleCancel(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/DelBook.txt";
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
