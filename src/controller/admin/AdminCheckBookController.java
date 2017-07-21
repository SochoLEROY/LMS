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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class AdminCheckBookController {
    /*Instances of AdminLandingController and Stage created to refer to the 
    parameters recieved by setAdminLanding. These will be used to access caller
    class and to close the current window.
    */
    private AdminLandingController ALC;
    private Stage current;
    
    @FXML Button checkBookCancelButton;
    @FXML TextField titleField;
    @FXML TextField authorField;
    @FXML TextField categoryField;
    @FXML TextField publisherField;
    @FXML TextField isbnField;
    @FXML TextField libidField;
    @FXML TextField yearField;
    
    private String libid;
    private String title;
    private String author;
    private String category;
    private String publisher;
    private String isbn;
    private String year;
    
    
    //Function to access AdminLandingController class - needed for closing this window
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage){
        ALC = main;
        current = secondaryStage;
    }
    
    public void handleSearch(){
       
        libid = libidField.getText().trim();
        title = titleField.getText().trim();
        author = authorField.getText().trim();
        category = categoryField.getText().trim();
        publisher = publisherField.getText().trim();
        isbn = isbnField.getText().trim();
        year = yearField.getText().trim();
        String sql = buildSql();
        try{
            FXMLLoader loader2 = new FXMLLoader(LibraryManagementSystem.class.getResource("/view/CheckBookView.fxml"));
            AnchorPane pane2 = loader2.load();
            
            BookViewController bookViewController = loader2.getController();
            
            Scene scene2 = new Scene(pane2);
            Stage secondaryStage = new Stage();
            bookViewController.setAdminLanding(ALC, secondaryStage, sql);
            
            secondaryStage.setScene(scene2);
            secondaryStage.show();
            checkBookCancelButton.setText("Done");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void handleHelp(){
        String path = "src/help/AdminCheckAvail.txt";
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
    
    public void handleCancel(){
        current.close();
    }
    
    public String buildSql(){
        String sql = "Select * from book";
        boolean isFirst = true;
        
        if(!libid.isEmpty()){
            sql = sql+" where libid = '"+libid+"'";
            isFirst = false;
        }
        if (!title.isEmpty()){
            if(isFirst){
                sql = sql+" where title = '"+title+"'";
                isFirst = false;
            }
            else{
                sql = sql+" and title = '"+title+"'";
            }
        }
        if (!author.isEmpty()){
            if(isFirst){
                sql = sql+" where author = '"+author+"'";
                isFirst = false;
            }
            else{
                sql = sql+" and author = '"+author+"'";
            }
        }
        if (!category.isEmpty()){
            if(isFirst){
                sql = sql+" where category = '"+category+"'";
                isFirst = false;
            }
            else{
                sql = sql+" and category = '"+category+"'";
            }
        }
        if(!publisher.isEmpty()){
            if(isFirst){
                sql = sql+" where publisher = '"+publisher+"'";
                isFirst = false;
            }
            else{
                sql = sql+" and publisher = '"+publisher+"'";
            }
        }
        if (!isbn.isEmpty()){
            if(isFirst){
                sql = sql+" where isbn = '"+isbn+"'";
                isFirst = false;
            }
            else{
                sql = sql+" and isbn = '"+isbn+"'";
            }
        }
        if (!year.isEmpty()){
            if(isFirst){
                sql = sql+" where year = "+year;
                isFirst = false;
            }
            else{
                sql = sql+" and year = "+year;
            }
        }
        return sql;
    }

}
