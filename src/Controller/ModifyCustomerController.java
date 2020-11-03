/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Country;
import Model.Customer;
import Model.Region;
import static Utils.DBQuerry.getAllCustomers;
import static Utils.DBQuerry.getCountries;
import static Utils.DBQuerry.getNewID;
import static Utils.DBQuerry.getRegions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author corte
 */
public class ModifyCustomerController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private Button returnMainMenuBtn;
    @FXML
    private TextField cNameTF;
    @FXML
    private TextField cAddressTF;
    @FXML
    private TextField cPhoneTF;
    @FXML
    private TextField cPostalCodeTF;
    @FXML
    private ComboBox<Region> regionCB;
    @FXML
    private ComboBox<Country> countryCB;
    @FXML
    private TextField cIDTF;
    @FXML
    private TableView<Customer> customersTV;
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> customerAddressCol;
    @FXML
    private TableColumn<Customer, String> customerZipCol;
    @FXML
    private TableColumn<Customer, String> customerPhoneCol;
    @FXML
    private TableColumn<Customer, String> customerDivCol;
    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Intitially makes all User Input Fields disabled
        cNameTF.setDisable(true);
        cAddressTF.setDisable(true);
        countryCB.setDisable(true);
        regionCB.setDisable(true);
        cPostalCodeTF.setDisable(true);
        cPhoneTF.setDisable(true);
        cIDTF.setDisable(true);
        
        //Fills the Customer Table
        try {
            customersTV.setItems(getAllCustomers());
        }catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Sets Cell values for each column used in Customer table        
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivCol.setCellValueFactory(new PropertyValueFactory<>("region"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        
        countryCB.setItems(getCountries());
        
    }    

    @FXML
    private void onActionUpdateCustomer(ActionEvent event) {
        
        //Troubleshooting
        System.out.println(customersTV.getSelectionModel().getSelectedItem().getRegion());
        
        
    }

    @FXML
    private void onActionReturnMainMenu(ActionEvent event) throws IOException {
        
        //Used to Return to main Menu Screen
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
	stage.setScene(new Scene(scene));
	stage.show();
    }

    @FXML
    private void onActionClearFields(ActionEvent event) {
    }

    @FXML
    private void onActionCountrySelected(ActionEvent event) {
        
        if(countryCB.getSelectionModel().getSelectedItem() instanceof Country)
        {
            Country selectedCountry = countryCB.getSelectionModel().getSelectedItem();
            regionCB.setItems(getRegions(selectedCountry));
            regionCB.setPromptText("Choose a Region");
            regionCB.setDisable(false);
        }
    }

    @FXML
    private void onActionDeleteCustomer(ActionEvent event) {
    }
    
    @FXML
    void onActionEditFields(ActionEvent event) {
        
        //Creates temporary object
        Customer modCustomer = customersTV.getSelectionModel().getSelectedItem();
        
        //Assigns text values to textfields
        cNameTF.setText(modCustomer.getName());
        cAddressTF.setText(modCustomer.getAddress());
        
        //Used to autofill Country Combo Box
        for(int i = 0; i < countryCB.getItems().size(); i++)
        {
            Country selCountry = countryCB.getItems().get(i);
            if(selCountry.getId() == modCustomer.getCountryID())
            {
             countryCB.setValue(selCountry);
             break;
            }
        
        }
        
        //Used to autofill Region combo Box
        for(int i = 0; i < regionCB.getItems().size(); i++)
        {
            Region selRegion = regionCB.getItems().get(i);
            if(selRegion.getId() == modCustomer.getRegionID())
            {
             regionCB.setValue(selRegion);
             break;
            }
        
        }
        
        
        cPostalCodeTF.setText(modCustomer.getPostalCode());
        cPhoneTF.setText(modCustomer.getPhone());
        cIDTF.setText(String.valueOf(modCustomer.getId()));
                
        cNameTF.setDisable(false);
        cAddressTF.setDisable(false);
        countryCB.setDisable(false);
        regionCB.setDisable(false);
        cPostalCodeTF.setDisable(false);
        cPhoneTF.setDisable(false);
    }
}
