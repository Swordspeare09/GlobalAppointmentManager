/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Utils.DBQuerry.login;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author corte
 */
public class LoginController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private Label countryLabel;
    
    @FXML
    private Button exitButton;

    @FXML
    private TextField usernameTF;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordTF;

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {

        //Retrives text from usernameTF and passwordTF
        String usernameInput, passwordInput;
        usernameInput = usernameTF.getText();
        passwordInput = passwordTF.getText();
        
        //Validate whet
        if (login(usernameInput, passwordInput)) {
            
            //Changes Screens if Login was Successful
            System.out.println("Login Successful!");
            
            //Used to get time of user log in
            LocalDateTime now = LocalDateTime.now();
            //Formatting LDT object
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String newTimeNow = dtf.format(now);
            System.out.println("Local Time Zone: " + ZoneId.systemDefault().toString());
            System.out.println(Locale.getDefault().getCountry());
            System.out.println(newTimeNow);
            
            
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            
        }
        else {
            //Username and/or password is incorrect
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("Incorrect UserName and/Or Password");
                alert.setHeaderText("Please Enter the correct Credintials for Login");
                alert.setContentText("Either your username and/or password are incorrect. Please re-enter your credintials."); 
                alert.showAndWait();

        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //Used to Display User's System Location
        countryLabel.setText(Locale.getDefault().getCountry()  + " User Identified");
    }


    
}
