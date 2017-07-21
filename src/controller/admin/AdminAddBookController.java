/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import controller.AdminLandingController;
import controller.HelpController;
import controller.LibraryManagementSystem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sushantprasad
 */
public class AdminAddBookController {
    
    //VIEWS
    @FXML private TextField addBookTitleField;
    @FXML private TextField addBookAuthorField;
    @FXML private TextField addBookCategoryField;
    @FXML private TextField addBookPublisherField;
    @FXML private TextField addBookYearField;
    @FXML private TextField addBookIsbnField;
    @FXML private TextField addBookLibIdField;
    @FXML private Button addBookCancelButton;
    @FXML private Button helpButton;
    
    /*Instances of AdminLandingController and Stage created to refer to the 
    parameters recieved by setAdminLanding. These will be used to access caller
    class and to close the current window.
    */
    private AdminLandingController ALC;
    private Stage current;
    
    //Function to access AdminLandingController class - needed for closing this window
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage){
        ALC = main;
        current = secondaryStage;
    }
    
    public void handleAddButton(){
        String title = addBookTitleField.getText();
        String author = addBookAuthorField.getText();
        String category = addBookCategoryField.getText();
        String publisher = addBookPublisherField.getText();
        String year = addBookYearField.getText();
        String isbn = addBookIsbnField.getText();
        String libid = addBookLibIdField.getText();
        if(title.isEmpty() || author.isEmpty() || category.isEmpty() || publisher.isEmpty() || year.isEmpty() || isbn.isEmpty() || libid.isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error occured");
            errorAlert.setHeaderText("Book Addition Failed");
            errorAlert.setContentText("All Field are necessary");
            errorAlert.showAndWait();
        }
        else{
        insert(libid,title,author,category,publisher,Integer.parseInt(year),isbn);
        }
        
        //Based on whether adding in database was successfull or not...
        //show confirmation or error alert accordingly
    }
    
    public void handleCancelButton(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/AdminAddBook.txt";
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
        // SQLite connection string
        String url = "jdbc:sqlite:library.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void insert(String libid, String title, String author, String category, String publisher, int year, String isbn) {
        String sql = "INSERT INTO book VALUES(?,?,?,?,?,?,?,'Available')";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libid);
            pstmt.setString(2, isbn);
            pstmt.setString(3, title);
            pstmt.setString(4, author);
            pstmt.setString(5, category);
            pstmt.setString(6, publisher);
            pstmt.setInt(7, year);
            pstmt.executeUpdate();
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Added");
            alert1.setHeaderText("Book Added");
            alert1.setContentText("The Book was added successfully");
            Optional<ButtonType> result = alert1.showAndWait();
            if(result.get() == ButtonType.OK){
                current.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Failed");
            alert1.setHeaderText("Book Addition Failed");
            alert1.setContentText("The Book could not be added. Please Try again.");
            alert1.showAndWait();
        }
    }
}
