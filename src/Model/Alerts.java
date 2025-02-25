/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
/**
 *
 * @author corte
 */
public class Alerts {
    
    public static void blankFieldMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Error");
        alert.setHeaderText("Blank Field");
        alert.setContentText(s + " Textfield needs to include a(n) " + s);  
        alert.showAndWait();
    }

    public static void emptyCountryRegionCB(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Error");
        alert.setHeaderText("No Country/Region Selected");
        alert.setContentText(s + " Selection Required");  
        alert.showAndWait();
    }
    
    public static void emptyCustomerCB(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Error");
        alert.setHeaderText("No Country was Selected");
        alert.setContentText(s + " Selection Required");  
        alert.showAndWait();
    }
    
    public static void emptyContactCB(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Error");
        alert.setHeaderText("No Contact was Selected");
        alert.setContentText(s + " Selection Required");  
        alert.showAndWait();
    }
    
    public static void emptyStartTimeCB(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Error");
        alert.setHeaderText("No Start Time was Selected");
        alert.setContentText(s + " Selection Required");  
        alert.showAndWait();
    }
    
    public static void emptyEndTimeCB(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Error");
        alert.setHeaderText("No End Time was Selected");
        alert.setContentText(s + " Selection Required");  
        alert.showAndWait();
    }
    
    public static void emptyDatePicker(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Error");
        alert.setHeaderText("No Date was Selected");
        alert.setContentText(s + " Selection Required");  
        alert.showAndWait();
    }


}
