/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.*;
/**
 *
 * @author sushantprasad
 */
public class HelpController {
    
    @FXML private TextArea helpArea;
    private Stage current;
    String help;
    
    public void setHelpLanding(Stage secondaryStage, String path){
        current = secondaryStage;
        this.help = getText(path);
        helpArea.setText(help);
    }
    
    public void handleClose(){
        current.close();
    }
    
    public String getText(String path){
        String text = "";
        int c;
        File f = new File(path);
        FileInputStream f1 = null;
        try{
            f1 = new FileInputStream(f);
            while((c=f1.read())!=-1){
                text = text + (char)c;
            }
	f1.close();
	}
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return text;
    }
}
