/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import controller.AdminLandingController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author sushantprasad
 */
public class StudentViewController {
 
    @FXML private Label regnoLabel;
    @FXML private Label nameLabel;
    @FXML private Label collegeLabel;
    @FXML private Label deptLabel;
    @FXML private Label yearLabel;
    @FXML private Label duesLabel;
    @FXML private Label emailLabel;
    
    private AdminLandingController ALC;
    private Stage current;
    
    public void setAdminLanding(AdminLandingController main, Stage secondaryStage, String regno, String name, String college, String dept, int year, String email, int dues){
        ALC = main;
        this.current = secondaryStage;
        regnoLabel.setText(regno);
        nameLabel.setText(name);
        collegeLabel.setText(college);
        deptLabel.setText(dept);
        yearLabel.setText(""+year);
        emailLabel.setText(email);
        duesLabel.setText(""+dues);
    }
    
    public void handleDone(){
        current.close();
    }
    
    public void handleHelp(){
        // help window
    }
}
