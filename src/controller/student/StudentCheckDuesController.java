/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.student;

import controller.AdminLandingController;
import controller.HelpController;
import controller.LibraryManagementSystem;
import controller.StudentLandingController;
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
import model.Dues;

/**
 *
 * @author sushantprasad
 */
public class StudentCheckDuesController {
    
    @FXML TableView<Dues> duesTable;
    @FXML TableColumn<Dues, String> libidCol;
    @FXML TableColumn<Dues, Integer> amountCol;
    
    public ObservableList<Dues> duesList = FXCollections.observableArrayList();
    
    private StudentLandingController SLC;
    private Stage current;
    private String regno;
    private String sql;
    
    public void initialize(){
        libidCol.setCellValueFactory(new PropertyValueFactory<Dues, String>("libid"));
        amountCol.setCellValueFactory(new PropertyValueFactory<Dues, Integer>("amount"));
    }
    
    public void setStudentLanding(StudentLandingController main, Stage secondaryStage, String regno){
        SLC = main;
        current = secondaryStage;
        duesTable.setItems(duesList);
        this.regno = regno;
        this.sql = "Select * from dues where regno = "+this.regno;
        setTableData();
    }
    
    public void setTableData(){
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
             
                while (rs.next()) {
                    Dues d1 = new Dues(rs.getString("libid"), rs.getInt("amount"));
                    duesList.add(d1);
                    System.out.println(d1.getLibid());
                    System.out.println(d1.getAmount());
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
        String path = "src/help/CheckDues.txt";
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
