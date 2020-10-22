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
        alert.setContentText(s + " Textfield needs to include an " + s);  
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


}
