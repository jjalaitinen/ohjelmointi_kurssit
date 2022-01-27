package fxFrisbeeRekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.ohj2.WildChars;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import rekisteri.Frisbeegolfrekisteri;
import rekisteri.Pelaaja;
import rekisteri.Rata;
import rekisteri.SailoException;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



/**
 * @author joona
 * @version 1 Feb 2019
 *
 */
public class FrisbeeRekisteriGUIController implements Initializable {
    
    @FXML private ScrollPane panelPelaaja;
    @FXML private ListChooser<Pelaaja> chooserPelaajat;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }
    
    @FXML void handleUusiPelaaja() {
        uusiPelaaja();
    }
    @FXML void handleApua() {
        apua();
    }

    @FXML void handleHaku() {
        haku();
    }

    @FXML void handleLopeta() {
        lopeta();
    }

    @FXML void handleMuokkaa() {
        muokkaa();
    }

    @FXML void handleMuokkaaRata() {
        muokkaaRata();
    }

    @FXML void handlePoistaPelaaja() {
        poistaPelaaja();
    }

    @FXML void handlePoistaRata() {
        poistaRata();
    }

    @FXML void handleTallenna() {
        tallenna();
    }

    @FXML void handleTietoja() {
        tietoja();
    }
    
    @FXML void handleTulosta() {
        //tulosta();
    }
    
    @FXML void handleUusiRata() {
        uusiRata();
    }
    
    @FXML void handleRataPeruuta() {
        peruuta();
    }
    
    @FXML void handleRataTallenna() {
        tallenna();
    }
    
    @FXML void handleAloita() {
        siirrySovellukseen();
    }
    
    @FXML void handlePeruuta() {
        peruuta();
    }

    @FXML void handleTulostus() {
        tulostaminen();
    }
    
    @FXML void handleOk() {
        tallenna();
    }
    
    @FXML private TextField editNimi;
    @FXML private TextField editRating;
    @FXML private TextField editRanking;
    @FXML private TextField editKatisyys;
    @FXML private TextField editSponsori;
    @FXML private TextField editLempirata;
    @FXML private TextField editKotikaupunki;
    @FXML private TextArea editLisatiedot;
    
    @FXML private StringGrid<Rata> tableRatatulokset;
    @FXML private TextField hakuehto;
    
    
    
    //=====================================================================================
    
    private Frisbeegolfrekisteri rekisteri;
    private Pelaaja pelaajaKohdalla;
    private TextField[] edits;
    private Rata apurata = new Rata();
    
    
    private void alusta() {
        panelPelaaja.setFitToHeight(true);
    
        chooserPelaajat.clear();
        chooserPelaajat.addSelectionListener(e -> naytaPelaaja());
        
        this.edits = new TextField[]{editNimi, editRating, editRanking, 
                                     editKatisyys, editSponsori, editLempirata, 
                                     editKotikaupunki};
        
        int eka = apurata.getEkaKentta();
        int lkm = apurata.getKenttia();
                
        
        String[] headings = new String[lkm-eka];
        for (int i = 0, k=eka; k<lkm; i++, k++)
            headings[i] = apurata.getKysymys(k);
        tableRatatulokset.initTable(headings);
        tableRatatulokset.setColumnSortOrderNumber(1);
        tableRatatulokset.setPlaceholder(new Label("ei vielä ratatuloksia"));
        
    }
    
    /**
     * Alustetaan ohjelma lukemalla tiedostosta
     * @param nimi tiedosto josta luetaan
     */
    protected void lueTiedosto(String nimi) {
        
        try {
            rekisteri.lueTiedostosta(nimi);
            hae(0);
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
        }
    }
    
    private void naytaRadat(Pelaaja pelaaja) {
        tableRatatulokset.clear();
        if(pelaaja==null) return;
        
            List<Rata> radat = rekisteri.annaRadat(pelaaja);
            if (radat.isEmpty()) return;
            for (Rata rat : radat) {
                naytaRata(rat);
            }
    }
    
    private void naytaRata(Rata rata) {
        int kenttia = rata.getKenttia();
        String[] rivi = new String[kenttia-rata.getEkaKentta()];
        for (int i = 0, k = rata.getEkaKentta(); k < kenttia; i++, k++)
            rivi[i] =rata.anna(k);
        tableRatatulokset.add(rata, rivi);
    }
    
    private void naytaPelaaja() {
        pelaajaKohdalla = chooserPelaajat.getSelectedObject();
        
        if (pelaajaKohdalla == null) return;
        
        PelaajaDialogController.naytaPelaaja(edits, pelaajaKohdalla);
        editLisatiedot.setText(pelaajaKohdalla.getLisatiedot());
        
        naytaRadat(pelaajaKohdalla);
        
    }
    
    
    private void uusiPelaaja() {
        Pelaaja uusi = new Pelaaja();
        uusi = PelaajaDialogController.kysyPelaaja(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroi();
        this.rekisteri.lisaa(uusi);
        
        
        hae(uusi.getTunnusNro());
    }
    
    private void hae(int idnro) {
        
        int apuNro = idnro;
        chooserPelaajat.clear();
        if (apuNro <= 0) {
            Pelaaja kohdalla = pelaajaKohdalla;
            if (kohdalla != null) apuNro = kohdalla.getTunnusNro();
        }
        
        String haku = hakuehto.getText();
        
        int indeksi = 0;
        String ehto = "*";
        if (haku != null && haku.length() > 0) ehto = haku;
        if (ehto.indexOf("*") < 0) ehto = "*" + ehto + "*";
        for (int i = 0; i < rekisteri.getPelaajia(); i++) {
            Pelaaja pelaaja = rekisteri.annaPelaaja(i);
            if (pelaaja.getTunnusNro() == apuNro) indeksi = i;
            if (WildChars.onkoSamat(pelaaja.getNimi(), ehto)) chooserPelaajat.add(pelaaja.getNimi(), pelaaja);
        }
        
        chooserPelaajat.getSelectionModel().select(indeksi);
    }
    
    private void apua() {
        Desktop desktop = Desktop.getDesktop();
                 try {
                     URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/laitjjxz");
                     desktop.browse(uri);
                 } catch (URISyntaxException e) {
                     return;
                 } catch (IOException e) {
                     return;
                 }
             
    }
    
    /**
     * Asetetaan viittaamaan meidän rekisteritietoihin
     * @param rekisteri käyttettävä rekisteri
     */
    public void setRekisteri(Frisbeegolfrekisteri rekisteri) {
        this.rekisteri = rekisteri;
    }
    
    private void haku() {
        hae(0);
    }
    
    private void lopeta() {
        Dialogs.showMessageDialog("Ei osata sulkea!");
    }
    
    private void muokkaa() {
        if (pelaajaKohdalla == null) return;
        try {    
                Pelaaja pelaaja = PelaajaDialogController.kysyPelaaja(null, pelaajaKohdalla.clone());
                if (pelaaja == null) return;
                rekisteri.korvaaTaiLisaa(pelaaja);
                hae(pelaaja.getTunnusNro());
            } catch (CloneNotSupportedException ex) {
                //
            }
        
    }
    
    private void muokkaaRata() {
        if (pelaajaKohdalla == null) return;
        int r = tableRatatulokset.getRowNr();
        if ( r < 0 ) return;
        Rata rat = tableRatatulokset.getObject();
        if (rat == null) return;
        int k = tableRatatulokset.getColumnNr() + rat.getEkaKentta();
        try {
                rat = RataDialogController.kysyRata(null, rat.clone(), k);
                if ( rat == null ) return;
                rekisteri.korvaaTaiLisaa(rat);
                naytaRadat(pelaajaKohdalla);
                tableRatatulokset.selectRow(r);
        } catch (CloneNotSupportedException ex) {
            //
        }
    }
    
    private void poistaPelaaja() {
        Pelaaja pelaaja = pelaajaKohdalla;
        if( pelaaja == null) return;
        if ( !Dialogs.showQuestionDialog("Pelaajan poisto", "Poistetaanko rekisteristä pelaaja : "  
                           + pelaaja.getNimi(), "Kyllä", "Ei") ) return;
        
        rekisteri.poista(pelaaja);
        int indeksi = chooserPelaajat.getSelectedIndex();
        hae(0);
        chooserPelaajat.setSelectedIndex(indeksi);
        
        
    }
    
    private void poistaRata() {
        int rivi = tableRatatulokset.getRowNr();
        if (rivi < 0) return;
        Rata rata = tableRatatulokset.getObject();
        if (rata == null) return;
        rekisteri.poistaRata(rata);
        naytaRadat(pelaajaKohdalla);
        int ratoja = tableRatatulokset.getItems().size();
        if (rivi >= ratoja) rivi = ratoja -1;
        tableRatatulokset.getFocusModel().focus(rivi);
        tableRatatulokset.getSelectionModel().select(rivi);
    }
    
    private void tallenna() {
        try {
            rekisteri.tallenna();
            return;
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennus ei onnistunut. " + e.getMessage());
        }
        
        
    }
    
    private void tulosta(PrintStream os, Pelaaja pelaaja) {
        os.println("-----");
        pelaaja.tulosta(os);
        os.println("-----");
        List<Rata> pelaajanRadat = rekisteri.annaRadat(pelaaja);
        for (Rata rat:pelaajanRadat) {
            rat.tulosta(os);
        }
    
    }
    
    private void uusiRata() {
        if (pelaajaKohdalla == null) return;
        
        Rata uusi = new Rata(pelaajaKohdalla.getTunnusNro());
        uusi = RataDialogController.kysyRata(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroi();
        this.rekisteri.lisaa(uusi);
        
        
        hae(pelaajaKohdalla.getTunnusNro());
    
    }
    
    private void tietoja() {
        ModalController.showModal(FrisbeeRekisteriGUIController.class.getResource("TietojaGUIView.fxml"),"Tietoja", null, "");
    }
    
    private void siirrySovellukseen() {
        Dialogs.showMessageDialog("Ei osata avata sovellusta vielä!");
    }
    
    private void peruuta() {
        Dialogs.showMessageDialog("Ei osata peruuttaa vielä!");
    }
    
    private void tulostaminen() {
        Dialogs.showMessageDialog("Ei osata tulostaa");
    }
    
    
    
}
