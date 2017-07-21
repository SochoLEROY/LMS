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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Book;

/**
 *
 * @author sushantprasad
 */
public class BookViewController {
    
    @FXML TableView<Book> bookTable;
    @FXML TableColumn<Book, String> libidCol;
    @FXML TableColumn<Book, String> isbnCol;
    @FXML TableColumn<Book, String> titleCol;
    @FXML TableColumn<Book, String> authorCol;
    @FXML TableColumn<Book, String> categoryCol;
    @FXML TableColumn<Book, String> publisherCol;
    @FXML TableColumn<Book, Integer> yearCol;
    @FXML TableColumn<Book, String> statusCol;
    
    public ObservableList<Book> bookList = FXCollections.observableArrayList();
    
    private AdminLandingController ALC;
    private Stage current;
    private String sql;
    
    public void initialize(){
        libidCol.setCellValueFactory(new PropertyValueFactory<Book, String>("libid"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
    }
    
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage, String sql){
        ALC = main;
        current = secondaryStage;
        bookTable.setItems(bookList);
        this.sql = sql;
        setTableData();
    }
    
    public void setTableData(){
        
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
             
                while (rs.next()) {
                    Book b = new Book(rs.getString("libid"), rs.getString("isbn"), rs.getString("title"), rs.getString("author"), rs.getString("category"), rs.getString("publisher"), rs.getInt("year"), rs.getString("status"));
                    bookList.add(b);
                }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
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
    
    public void handleDone(){
        current.close();
    }
    
    public void handleHelp(){
        String path = "src/help/BookResults.txt";
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
    
}
