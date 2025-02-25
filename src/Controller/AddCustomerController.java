/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Model.Alerts.blankFieldMessage;
import static Model.Alerts.emptyCountryRegionCB;
import Model.Country;
import Model.Customer;
import Model.Region;
import static Utils.DBQuerry.addCustomer;
import static Utils.DBQuerry.getAllCustomers;
import static Utils.DBQuerry.getCountries;
import static Utils.DBQuerry.getNewID;
import static Utils.DBQuerry.getRegions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
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
public class AddCustomerController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private TextField cPhoneTF;

    @FXML
    private TextField cIDTF;

    @FXML
    private TextField cPostalCodeTF;

    @FXML
    private ComboBox<Region> regionCB;

    @FXML
    private TextField cAddressTF;

    @FXML
    private ComboBox<Country> countryCB;

    @FXML
    private TextField cNameTF;
    
    @FXML
    private TableView<Customer> customersTV;
    
    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;
    
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;
    
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    
    @FXML
    private TableColumn<Customer, String> customerDivCol;
    
    @FXML
    private TableColumn<Customer, String> customerZipCol;

    @FXML
    private void onActionAddCustomer(ActionEvent event) throws SQLException, IOException {
        
        String tID = cIDTF.getText();
        String tName = cNameTF.getText();
        String tAddress = cAddressTF.getText();
        String tPost =  cPostalCodeTF.getText();
        String tPhone =  cPhoneTF.getText();
        String tRegion = null;
        String tCountry = null ;

            //Validating user input before creating any objects or queries
            if(!tName.isEmpty() && !tAddress.isEmpty() && !tPost.isEmpty() && !tPhone.isEmpty())
            {
                
                try{
                    //Creates new customer object for SQL INSERT statement
                    Customer tempCust = new Customer(
                                        Integer.parseInt(tID), 
                                        tName, tAddress, 
                                        tPost, 
                                        tPhone, 
                                        tRegion, 
                                        regionCB.getSelectionModel().getSelectedItem().getId(),
                                        tCountry,
                                        countryCB.getSelectionModel().getSelectedItem().getId());
                    addCustomer(tempCust, regionCB.getSelectionModel().getSelectedItem().getId());
                
                    //Loads up Main Menu Screen
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }catch(Exception e){
                    //Alert informs user that no country or region was selected
                    emptyCountryRegionCB("Country/Region");
                }
            } else{
                //Used to Display Appropriate Error Messages for User 
                if(tName.isEmpty())
                    blankFieldMessage("Name");
                if(tAddress.isEmpty())
                    blankFieldMessage("Address");
                if(tPost.isEmpty())
                    blankFieldMessage("Postal Code");
                if(tPhone.isEmpty())
                    blankFieldMessage("Phone Number");
            }
    }

    @FXML
    private void onActionClearFields(ActionEvent event) {

        cIDTF.clear();
        cNameTF.clear();
        cAddressTF.clear();
        cPostalCodeTF.clear();
        cPhoneTF.clear();
        countryCB.getSelectionModel().clearSelection();
        regionCB.getSelectionModel().clearSelection();
        
        //Used to reset Regions Combo Box 
        regionCB.setItems(null);
        regionCB.setPromptText(null);
        regionCB.setDisable(true);//Used to prevent errors of loading incorrect regions
        
    }

    @FXML
    private void onActionReturnMainMenu(ActionEvent event) throws IOException {

        //Used to Return to main Menu Screen
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
	stage.setScene(new Scene(scene));
	stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //TODO
            customersTV.setItems(getAllCustomers());
            cIDTF.setText(String.valueOf(getNewID()));
        } catch (SQLException ex) {
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
        countryCB.setPromptText("Choose a Country");
        regionCB.setDisable(true); //Used to prevent errors of loading incorrect regions
        
    }
    
    //Used to 
    @FXML
    void onActionCountrySelected(ActionEvent event) {
 
        if(countryCB.getSelectionModel().getSelectedItem() instanceof Country)
        {
            Country selectedCountry = countryCB.getSelectionModel().getSelectedItem();
            regionCB.setItems(getRegions(selectedCountry));
            regionCB.setPromptText("Choose a Region");
            regionCB.setDisable(false);
        }
    }
    
    


}
