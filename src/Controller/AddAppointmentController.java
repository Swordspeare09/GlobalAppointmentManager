/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Model.Alerts.blankFieldMessage;
import static Model.Alerts.emptyContactCB;
import static Model.Alerts.emptyCustomerCB;
import static Model.Alerts.emptyDatePicker;
import static Model.Alerts.emptyEndTimeCB;
import static Model.Alerts.emptyStartTimeCB;
import Model.Appointments;
import Model.Contact;
import Model.Customer;
import Model.User;
import static Utils.DBQuerry.addAppointment;
import static Utils.DBQuerry.getAllAppointments;
import static Utils.DBQuerry.getAllContacts;
import static Utils.DBQuerry.getAllCustomers;
import static Utils.DBQuerry.getNewAppID;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
import javafx.scene.control.DatePicker;
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
public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private TextField appTitleTF;
    @FXML
    private TextField appDescriptionTF;
    @FXML
    private TextField appTypeTF;
    @FXML
    private TextField appLocationTF;
    @FXML
    private ComboBox<Contact> contactCB;
    @FXML
    private TextField appIDTF;
    @FXML
    private TableView<Appointments> appointmentsTV;
    @FXML
    private TableColumn<Appointments, Integer> appIDCol;
    @FXML
    private TableColumn<Appointments, String> appTitleCol;
    @FXML
    private TableColumn<Appointments, String> appDescriptionCol;
    @FXML
    private TableColumn<Appointments, String> appLocationCol;
    @FXML
    private TableColumn<Appointments, Integer> appContactCol;
    @FXML
    private TableColumn<Appointments, String> appTypeCol;
    @FXML
    private TableColumn<Appointments, LocalDateTime> appStartCol;
    @FXML
    private TableColumn<Appointments, LocalDateTime> appEndCol;
    @FXML
    private TableColumn<Appointments, Integer> appCustIDCol;
    
    @FXML
    private ComboBox<LocalTime> startTimeCB;
    
    @FXML
    private ComboBox<LocalTime> endTimeCB;
    
    @FXML
    private DatePicker appDatePicker;
    
    @FXML
    private ComboBox<Customer> customerCB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            contactCB.setItems(getAllContacts());
            customerCB.setItems(getAllCustomers());
            appIDTF.setText(String.valueOf(getNewAppID()));
        }catch(SQLException ex){
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Used to fill the Start and End time combo boxes
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);
        
        while(start.isBefore(end.plusSeconds(1))){
            startTimeCB.getItems().add(start);
            endTimeCB.getItems().add(start);
            start = start.plusMinutes(30);
        }
        
        // Loads All Appointments from the beginning
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        appointmentsTV.setItems(getAllAppointments());
    }    

    @FXML
    private void onActionAddAppointment(ActionEvent event) {
        
        LocalDate date1 = appDatePicker.getValue();
        System.out.println(date1);
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        
        System.out.println(localZoneId);
        
        String tID = appIDTF.getText();
        String tTitle = appTitleTF.getText();
        String tDescr = appDescriptionTF.getText();
        String tLocation =  appLocationTF.getText();
        String tType =  appTypeTF.getText();
        Customer tCustomer = null;
        Contact tContact = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        LocalDate appDate = null;
        
        if(!tTitle.isEmpty() && !tDescr.isEmpty() && !tLocation.isEmpty() && !tType.isEmpty())
        {
            tCustomer = customerCB.getSelectionModel().getSelectedItem();
            tContact = contactCB.getSelectionModel().getSelectedItem();
            startTime = startTimeCB.getSelectionModel().getSelectedItem();
            endTime = endTimeCB.getSelectionModel().getSelectedItem();
            appDate = appDatePicker.getValue();
            
            if( tCustomer instanceof Customer && tContact instanceof Contact && startTime instanceof LocalTime && endTime instanceof LocalTime && appDate instanceof LocalDate)
            {
                //Creates new Appointments Object to send to addAppointment function call
                Appointments tempApp = new Appointments(
                                            Integer.parseInt(tID),
                                            tTitle,
                                            tType,
                                            tDescr,
                                            tLocation,
                                            LocalDateTime.of(appDate, startTime),
                                            LocalDateTime.of(appDate, endTime),
                                            tCustomer.getId(),
                                            User.getUserID(),
                                            tContact.getId()
                                            );
                
                //Sends new app created to function call
                addAppointment(tempApp);
            } else{
                //Used to Display Appropriate Error Messages for User 
                if(tCustomer == null)
                    emptyCustomerCB("Customer");
                if(tContact == null)
                    emptyContactCB("Contact");
                if(startTime == null)
                    emptyStartTimeCB("Start Time");
                if(endTime == null)
                    emptyEndTimeCB("End Time");
                if(appDate == null)
                    emptyDatePicker("Date");
            }
        }else{
                //Used to Display Appropriate Error Messages for User 
                if(tTitle.isEmpty())
                    blankFieldMessage("Title");
                if(tDescr.isEmpty())
                    blankFieldMessage("Description");
                if(tLocation.isEmpty())
                    blankFieldMessage("Location");
                if(tType.isEmpty())
                    blankFieldMessage("Type");
        }
        
        
        
        
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
        
        System.out.println(customerCB.getSelectionModel().getSelectedItem());
    }
    
}
