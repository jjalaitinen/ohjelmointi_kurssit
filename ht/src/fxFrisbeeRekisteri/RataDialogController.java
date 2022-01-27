package fxFrisbeeRekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rekisteri.Rata;

/**
 * @author joona
 * @version 5 May 2019
 *
 */
public class RataDialogController implements ModalControllerInterface<Rata>, Initializable {
    
    @FXML private TextField editRataNimi;
    @FXML private TextField editTulos;
    
    @FXML private Label labelRata;
    
    @FXML private void handleOK() {
        if (rataKohdalla != null && rataKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("nimi ei saa olla tyhjä!");
            return;
        }
     
        ModalController.closeStage(labelRata);
     }
    
     @FXML private void handleCancel() {
        rataKohdalla = null;
        
        ModalController.closeStage(labelRata);
     }
     
     
     // ========================================
     
     private Rata rataKohdalla;
     private TextField[] edits;
     private int kentta;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }

    @Override
    public Rata getResult() {
        return rataKohdalla;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(Rata oletus) {
        rataKohdalla = oletus;
        
    }
    
    /**
     * Tarvittavat alustukset
     */
    public void alusta() {
        edits = new TextField[] { editRataNimi, editTulos };
        
        int i = 0;
        for (TextField edit: edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosRataan(k, (TextField)(e.getSource())));
        }
    }
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    private void naytaVirhe(String virhe) {
        Dialogs.showMessageDialog(virhe);
    }
    
    private void kasitteleMuutosRataan(int k, TextField edit) {
        if (rataKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch(k) {
            case 1 : virhe = rataKohdalla.setNimi(s); break;
            case 2 : virhe = rataKohdalla.setTulos(s); break;
            default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
        }
    }
    
    /**
     * Pelaajan ratatietojen kysymistä varten dialogi
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä dataa käytetään oletuksena
     * @param kentta kenttä jolle annetaan fokus valitessa
     * @return null jos käyttäjä painaa cancel, muuten true
     */
    public static Rata kysyRata(Stage modalityStage, Rata oletus, int kentta) { 
        return ModalController.showModal(RataDialogController.class.getResource("MuokkaaRataGUIView.fxml"), 
                                           "Rekisteri", modalityStage, oletus, ctrl -> ((RataDialogController) ctrl).setKentta(kentta));
    }
    
    /**
     * Ilman kolmatta parametria
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä dataa käytetään oletuksena
     * @return null jos painetaan cancel
     */
    public static Rata kysyRata(Stage modalityStage, Rata oletus) { 
        return ModalController.showModal(RataDialogController.class.getResource("MuokkaaRataGUIView.fxml"), 
                                           "Rekisteri", modalityStage, oletus, null);
    }

}
