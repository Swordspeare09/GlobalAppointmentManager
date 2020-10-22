/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import Utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author corte
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Loads All Appointments from the beginning
        
        
        
    }    

    //Changes to Add Customer Screen
    @FXML
    private void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
	stage.setScene(new Scene(scene));
	stage.show();
    }

    //Used to handle logging out current User
    @FXML
    void onActionLogOutBtn(ActionEvent event) throws IOException {

        User.setUserName(null);
        User.setUserID(null);
        User.setActiveState(false);
        DBConnection.closeConnection();
        
        System.out.println("Log Out Successful.");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    
}
