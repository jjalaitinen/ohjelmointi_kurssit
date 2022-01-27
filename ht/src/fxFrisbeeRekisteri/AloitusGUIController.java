package fxFrisbeeRekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * @author joona
 * @version 11 Feb 2019
 *
 */
public class AloitusGUIController implements ModalControllerInterface<Boolean> {
    @FXML void handleAloita() {
        aloita();
    }

    @FXML void handleLopeta() {
        lopeta();
    }
    

    @FXML private Label labelOtsikko;

    @Override
    public Boolean getResult() {
        return this.totuus;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setDefault(Boolean oletus) {
        //
    }

    
    
    //================================================================
    
    private boolean totuus = true;
    
    
    private void aloita() {
        totuus = true;
        ModalController.closeStage(labelOtsikko);
    }
    
    private void lopeta() {
        totuus = false;
        ModalController.closeStage(labelOtsikko);
    }
    
    /**
     * @param modalityStage stage
     * @param oletus totuusarvo
     * @return palautetaan true tai false sen mukaan mitä käyttäjä painaa.
     */
    public static boolean aloitetaanko(Stage modalityStage, boolean oletus) {
        return ModalController.showModal(
                AloitusGUIController.class.getResource("AloitusGUIView.fxml"),
                "FribaPedia", modalityStage, oletus);
    }

    


}
