package rekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Pelaajat-luokka:
 * Vastuualueet:
 * - pitää yllä varsinaista pelaajarekisteriä eli osaa lisätä ja poistaa pelaajan
 * - lukee ja kirjoittaa pelaajan tiedostoon
 * - osaa etsiä ja lajitella
 * Avustajat:
 * -Pelaaja luokka
 * @author joona
 * @version 18 Mar 2019
 *
 */
public class Pelaajat implements Iterable<Pelaaja> {
    
    private static final int MAX_PELAAJIA       = 10;
    private int              lkm                = 0;
    //private String         tiedostonNimi      = "";
    private Pelaaja[]        alkiot             = new Pelaaja[MAX_PELAAJIA];
    private String           tiedostonPerusNimi = "";
    private boolean          muutettu           = false;
    
    /**
     * "Oletuskonsturktori"
     * Tuo ylempi attribuuttien alustaminen riittää, niitä ei tarvitse tässä määrittää
     */
    public Pelaajat() {
        // ei tarvita mitään
    }
    
    /**
     * Lisätään pelaaja rekisteriin
     * @param lisattava pelaaja, joka lisätään
     * @example
     * <pre name="test">
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja testi = new Pelaaja();
     * Pelaaja testi2 = new Pelaaja();
     * pelaajat.getLkm() === 0;
     * pelaajat.lisaa(testi); pelaajat.getLkm() === 1;
     * pelaajat.lisaa(testi2); pelaajat.getLkm() === 2;
     * pelaajat.anna(0) === testi;
     * pelaajat.anna(1) === testi2;
     * pelaajat.lisaa(testi); pelaajat.getLkm() === 3;
     * pelaajat.lisaa(testi); pelaajat.getLkm() === 4;
     * pelaajat.lisaa(testi); pelaajat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Pelaaja lisattava) {
        if (lkm >= this.alkiot.length) {
            Pelaaja[] uusi = new Pelaaja[alkiot.length+10];
            for (int i = 0; i < lkm; i++) {
                uusi[i] = alkiot[i];
            }
            this.alkiot = uusi;
        }
        this.alkiot[lkm++] = lisattava;
        muutettu = true;
    }
    
    /**
     * Korvataan tai lisätään tietorakenteeseen pelaaja
     * @param pelaaja lisättävään pelaajaan viite joka joko lisätään kokonaan uutena vai korvataan vanha
     */
    public void korvaaTaiLisaa(Pelaaja pelaaja) {
        int id = pelaaja.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getTunnusNro() == id) {
                alkiot[i] = pelaaja;
                muutettu = true;
                return;
            }
        }
        lisaa(pelaaja);
    }
    
    /**
     * poistaa pelaajan rekisteristä
     * @param id pelaajan id
     * @return palautetaan 1 jos onnistuu 0, jos ei onnistu (eli pelaaja ei ole/löydy)
     */
    public int poista(int id) {
        int indeksi = etsiId(id);
        if (indeksi < 0) return 0;
        this.lkm--;
        for (int i = indeksi; i<this.lkm; i++) {
            alkiot[i] = alkiot[i+1];
        }
        
        alkiot[lkm] = null;
        muutettu = true;
        return 1;
    }
    
    /**
     * etsii idn mukaan pelaajan paikan rakenteessa
     * @param id pelaajan id
     * @return palautta indeksin, jos löytyy, muuten -1
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++) {
            if (id == alkiot[i].getTunnusNro() ) return i;
        }
        return -1;
    }
    
    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien pelaajien viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenne löytyneistä
     * @example
     *  <pre name="test">
     *  #THROWS SailoException
     *      Pelaajat pelaajat = new Pelaajat();
     *      Pelaaja pelaaja1 = new Pelaaja(); pelaaja1.parse("1|Jouni Ogeli|123|340|");
     *      Pelaaja pelaaja2 = new Pelaaja(); pelaaja2.parse("2|Omra Jatka|321|452|");
     *      Pelaaja pelaaja3 = new Pelaaja(); pelaaja3.parse("3|Osom Mattsby|23|10|");
     *      Pelaaja pelaaja4 = new Pelaaja(); pelaaja4.parse("4|Fyrer Maltsu|300|350|");
     *      Pelaaja pelaaja5 = new Pelaaja(); pelaaja5.parse("5|Kiwi Fruitti|170|780|");
     *      pelaajat.lisaa(pelaaja1); pelaajat.lisaa(pelaaja2); pelaajat.lisaa(pelaaja3);
     *      pelaajat.lisaa(pelaaja4); pelaajat.lisaa(pelaaja5);
     * </pre>
     */
    @SuppressWarnings("unused")
    public Collection<Pelaaja> etsi(String hakuehto, int k) {
        Collection<Pelaaja> loytyneet = new ArrayList<Pelaaja>();
        for (Pelaaja pelaaja : this) loytyneet.add(pelaaja);
        return loytyneet;
    }
    
    /**
     * Kertoo tällä hetkellä kuinka paljon pelaajia on rekisterissä
     * @return lukumäärä
     * @example
     * <pre name="test">
     * Pelaajat pelaajat = new Pelaajat();
     * pelaajat.getLkm() === 0;
     * Pelaaja lisays = new Pelaaja();
     * Pelaaja toinen = new Pelaaja();
     * pelaajat.lisaa(lisays);
     * pelaajat.getLkm() === 1;
     * pelaajat.lisaa(toinen);
     * pelaajat.getLkm() === 2;
     * </pre>
     */
    public int getLkm() {
        return this.lkm;
    }
    
    /**
     * Palauttaa viitteen pyydettyyn (i) pelaajaan
     * @param i mikä viite halutaan
     * @return viite tiettyyn pelaajaan
     * @throws IndexOutOfBoundsException heitetään poikkeus, jos haluttu viite ei löydy taulukosta
     */
    public Pelaaja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= this.lkm) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * Asettaa tiedoston nimen
     * @param nimi tiedoston nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        this.tiedostonPerusNimi = nimi;
    }
    
    /**
     * Palautetaan tiedoston nimi (alku)
     * @return tiedoston nimi ilman loppua
     */
    public String getTiedostonPerusNimi() {
        return this.tiedostonPerusNimi;
    }
    
    /**
     * Palauttaa tiedoston nimen
     * @return tiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * Palauttaa backup filen nimen
     * @return backup filen nimi
     */
    public String getBakNimi() {
        return this.tiedostonPerusNimi + ".bak";
    }
    
    /**
     * Lukee pelaajat tiedostosta
     * @param tdosto tiedoston perusnimi
     * @throws SailoException poikkeus, jos tiedoston lukeminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * #import java.util.*;
     * 
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja pelaaja1 = new Pelaaja(); Pelaaja pelaaja2 = new Pelaaja();
     * pelaaja1.taytaTestiTiedot();
     * pelaaja2.taytaTestiTiedot();
     * String hakemisto = "testipelaajat";
     * String tiedNimi = hakemisto+"/pelaajat";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * pelaajat.lueTiedostosta(tiedNimi); #THROWS SailoException
     * pelaajat.lisaa(pelaaja1);
     * pelaajat.lisaa(pelaaja2);
     * pelaajat.tallenna();
     * pelaajat = new Pelaajat();
     * pelaajat.lueTiedostosta(tiedNimi);
     * Iterator<Pelaaja> i = pelaajat.iterator();
     * i.next() === pelaaja1;
     * i.next() === pelaaja2;
     * i.hasNext() === false;
     * pelaajat.lisaa(pelaaja2);
     * pelaajat.tallenna();
     * ftied.delete() === true;
     * File fbak = new File(tiedNimi+".bak");
     * fbak.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tdosto) throws SailoException {
        setTiedostonPerusNimi(tdosto);
        try (Scanner fi = new Scanner(new FileInputStream(new File(getTiedostonNimi())))) {
            while ( fi.hasNext() ) { 
                
                    String rivi = fi.nextLine();
                    rivi = rivi.trim();
                    if (rivi.equals("") || rivi.charAt(0) == ';' ) continue;
                    Pelaaja uusi = new Pelaaja();
                    uusi.parse(rivi);
                    lisaa(uusi);
            }
            muutettu = false;
            
            } catch (FileNotFoundException ex) {
                throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
            }
    }
    
    
    /**
     * Luetaan annetun niminen tiedosto
     * @throws SailoException poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * Tallennetaan pelaajatiedot tiedostoon
     * @throws SailoException jos tallentaminen epäonnistuu, heitetään poikkeus
     */
    public void tallenna() throws SailoException {
        if (muutettu == false) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File (getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied.getCanonicalPath()))) {
            for (int i = 0; i < getLkm(); i++) {
                fo.println(alkiot[i].toString());
            }
        } catch ( FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException("Tiedostoon " + ftied.getName() + " kirjoittamisessa on ongelmia");
        }
        
        muutettu = false;
    }
    
    /**
     * @author joona
     * @version 12 May 2019
     *
     */
    public class PelaajatIterator implements Iterator<Pelaaja> {
        
        private int kohdalla = 0;
        
        /**
         * Onko olemassa mahdollisesti seuraavaa pelaajaa
         * @see java.util.Iterator#hasNext()
         * @return true jos vielä pelaajia
         */
        @Override
        public boolean hasNext() {
            return this.kohdalla < getLkm();
        }
        
        /**
         * Annetaan seuraava pelaaja
         * @see java.util.Iterator#next()
         * @return seuraava pelaaja
         * @throws NoSuchElementException jos seuraavaa alkiota ei ole
         */
        @Override
        public Pelaaja next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("EI OLE!");
            return anna(this.kohdalla++);
        }
        
        /**
         * Poistamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Ei poistu");
        }
        
        
    }
    
    /**
     * Palauttaa iteraattori pelaajia varten
     * @return pelaaja iteraattori
     */
    @Override
    public Iterator<Pelaaja> iterator() {
        return new PelaajatIterator();
    }

    
   
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pelaajat pelaajat = new Pelaajat();
        
        Pelaaja kimmo = new Pelaaja();
        Pelaaja tomi = new Pelaaja();
        tomi.rekisteroi();
        kimmo.rekisteroi();
        tomi.taytaTestiTiedot();
        kimmo.taytaTestiTiedot();
        
        pelaajat.lisaa(tomi);
        pelaajat.lisaa(kimmo);
        
        
        System.out.println("==================== Testaus =======================");
        for (int i = 0; i < pelaajat.getLkm(); i++) {
            Pelaaja pelaaja = pelaajat.anna(i);
            System.out.println("Pelaaja paikassa: " + i);
            pelaaja.tulosta(System.out);
        }

    }

    
}
