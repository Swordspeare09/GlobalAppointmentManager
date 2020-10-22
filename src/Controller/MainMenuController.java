/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointments;
import Model.User;
import Utils.DBConnection;
import static Utils.DBQuerry.getAllAppointments;
import static Utils.DBQuerry.getAllCustomers;
import static Utils.DBQuerry.getNewID;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author corte
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    
     @FXML
    private TableColumn<Appointments, LocalDateTime> appEndCol;

    @FXML
    private TableColumn<Appointments, Integer> appContactCol;

    @FXML
    private TableColumn<Appointments, String> appTypeCol;

    @FXML
    private TableView<Appointments> appointmentsTV;

    @FXML
    private TableColumn<Appointments, String> appTitleCol;

    @FXML
    private TableColumn<Appointments, String> appDescrCol;

    @FXML
    private TableColumn<Appointments, Integer> appCustIDCol;

    @FXML
    private TableColumn<Appointments, String> appLocCol;

    @FXML
    private TableColumn<Appointments, Integer> appIDCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appStartCol;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Loads All Appointments from the beginning
        //customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        
        //Prefills the Appointment TableView with all current Appointments    
        appointmentsTV.setItems(getAllAppointments());

        
        
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
    
    @FXML
    void onActionModifyCustomer(ActionEvent event) {

    }

    
}
