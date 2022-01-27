package fxFrisbeeRekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rekisteri.Pelaaja;

/**
 * @author joona
 * @version 2 May 2019
 *
 */
public class PelaajaDialogController implements ModalControllerInterface<Pelaaja>, Initializable  {
    @FXML private TextField editNimi;
    @FXML private TextField editRating;
    @FXML private TextField editRanking;
    @FXML private TextField editKatisyys;
    @FXML private TextField editSponsori;
    @FXML private TextField editLempirata;
    @FXML private TextField editKotikaupunki;
    @FXML private TextArea editLisatiedot; 
   
    @FXML private Label labelTiedot;
    
    @FXML private void handleOK() {
       if (pelaajaKohdalla != null && pelaajaKohdalla.getNimi().trim().equals("") ) {
           naytaVirhe("nimi ei saa olla tyhjä!");
           return;
       }
    
       ModalController.closeStage(labelTiedot);
    }
   
    @FXML private void handleCancel() {
       pelaajaKohdalla = null;
       
       ModalController.closeStage(labelTiedot);
    }
    
   // ===========================================================================0
   
   private Pelaaja pelaajaKohdalla;
   private TextField[] edits;
   

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    @Override
    public Pelaaja getResult() {
        return pelaajaKohdalla;
    }

    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }

    @Override
    public void setDefault(Pelaaja oletus) {
        pelaajaKohdalla = oletus;
        naytaPelaaja(pelaajaKohdalla);
        
    }
    
    /**
     * Tehdään tarvittavat alustukset
     */
    public void alusta() {
        edits = new TextField[] {editNimi, editRating, editRanking, 
                                 editKatisyys, editSponsori, editLempirata, 
                                 editKotikaupunki };
        
        int i = 0;
        for (TextField edit: edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosPelaajaan(k, (TextField)(e.getSource())));
        }
        TextArea lisa = this.editLisatiedot;
        lisa.setOnKeyReleased(e -> kasitteleLisatiedot(lisa));
        
    }
    
    private void kasitteleLisatiedot(TextArea lisa) {
        if (pelaajaKohdalla == null) return;
        pelaajaKohdalla.setLisatiedot(lisa.getText());
    }
    
    private void naytaVirhe(String virhe) {
        Dialogs.showMessageDialog(virhe);
    }
    
    private void kasitteleMuutosPelaajaan(int k, TextField edit) {
        if (pelaajaKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch(k) {
            case 1 : virhe = pelaajaKohdalla.setNimi(s); break;
            case 2 : virhe = pelaajaKohdalla.setRating(s); break;
            case 3 : virhe = pelaajaKohdalla.setRanking(s); break;
            case 4 : virhe = pelaajaKohdalla.setKatisyys(s); break;
            case 5 : virhe = pelaajaKohdalla.setSponsori(s); break;
            case 6 : virhe = pelaajaKohdalla.setLempirata(s); break;
            case 7 : virhe = pelaajaKohdalla.setKotikaupunki(s); break;
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
     * Staattinen aliohjelma pelaajatietojen näyttämiseksi
     * @param edits textfieldit taulukossa
     * @param pelaaja pelaaja kenen tiedot näytetään
     */
    public static void naytaPelaaja(TextField[] edits, Pelaaja pelaaja) {
        if (pelaaja == null) return;
        edits[0].setText(pelaaja.getNimi());
        edits[1].setText(pelaaja.getRating());
        edits[2].setText(pelaaja.getRanking());
        edits[3].setText(pelaaja.getKatisyys());
        edits[4].setText(pelaaja.getSponsori());
        edits[5].setText(pelaaja.getLempirata());
        edits[6].setText(pelaaja.getKotikaupunki());
    }
    
    
    /**
     * Asettaa pelaajan tiedot tekstilootaan
     * @param pelaaja pelaaja minkä kohdalla ollaan
     */
    public void naytaPelaaja(Pelaaja pelaaja) {
        if (pelaaja == null) return;
        naytaPelaaja(edits, pelaaja);
        editLisatiedot.setText(pelaaja.getLisatiedot());
    }
    
    /**
     * Pelaajan tietojen kysymistä varten dialogi
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä dataa käytetään oletuksena
     * @return null jos käyttäjä painaa cancel, muuten true
     */
    public static Pelaaja kysyPelaaja(Stage modalityStage, Pelaaja oletus) {
        return ModalController.showModal(PelaajaDialogController.class.getResource("MuokkaaGUIView.fxml"), 
                                           "Rekisteri", modalityStage, oletus, null);
    }

}
