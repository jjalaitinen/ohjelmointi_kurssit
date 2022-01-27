/**
 * 
 */
package rekisteri;

import java.io.File;
import java.util.*;

/**
 * Vastuualueet:
 * - huolehtii pelaajat, ratatulokset, ((sponsorit)) välisestä yhteistyöstä ja
 *   välittää näitä tietoja tarvittaessa.
 * - lukee ja kirjoittaa rekisteriin pyytämällä apua avustajiltaan
 * Avustajat:
 * - Pelaajat
 * - Pelaaja
 * - Tulos
 * - Tulokset
 * - Rata
 * - Radat
 * - (Sponsori, Sponsorit)   
 * @author joona
 * @version 19 Mar 2019
 *
 */
public class Frisbeegolfrekisteri {
    
    private Pelaajat pelaajat = new Pelaajat();
    private Radat radat = new Radat();
    
    /**
     * Palautetaan pelaajien määrä rekisterissä
     * @return pelaajien määrä
     */
    public int getPelaajia() {
        return pelaajat.getLkm();
    }
    
    /**
     * Palautetaan annettussa indeksissä oleva pelaaja
     * @param i indeksi, mistä pelaaja halutaan
     * @return pelaajan viite
     * @throws IndexOutOfBoundsException jos indeksi "i" ei ole järkevä
     */
    public Pelaaja annaPelaaja(int i) throws IndexOutOfBoundsException {
        return pelaajat.anna(i);
    }
    
    /**
     * Haetaan kaikki pelaajan ratatiedot
     * @param pelaaja pelaaja jonka ratoja halutaan
     * @return ratatulokset (henkilökohtaiset tiedot ratoja koskien)
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Frisbeegolfrekisteri rekisteri = new Frisbeegolfrekisteri();
     * Pelaaja pelaaja1 = new Pelaaja(); 
     * Pelaaja pelaaja2 = new Pelaaja(); 
     * Pelaaja pelaaja3 = new Pelaaja();
     * pelaaja1.rekisteroi(); pelaaja2.rekisteroi(); pelaaja3.rekisteroi();
     * int id1 = pelaaja1.getTunnusNro();
     * int id2 = pelaaja2.getTunnusNro();
     * Rata rata11 = new Rata(id1); rekisteri.lisaa(rata11);
     * Rata rata12 = new Rata(id1); rekisteri.lisaa(rata12);
     * Rata rata21 = new Rata(id2); rekisteri.lisaa(rata21);
     * Rata rata22 = new Rata(id2); rekisteri.lisaa(rata22);
     * Rata rata23 = new Rata(id2); rekisteri.lisaa(rata23);
     * 
     * List<Rata> loytyneet;
     * loytyneet = rekisteri.annaRadat(pelaaja3);
     * loytyneet.size() === 0;
     * loytyneet = rekisteri.annaRadat(pelaaja1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == rata11 === true;
     * loytyneet.get(1) == rata12 === true;
     * loytyneet = rekisteri.annaRadat(pelaaja2);
     * loytyneet.size() === 3;
     * loytyneet.get(0) == rata21 === true;
     * </pre>
     */
    public List<Rata> annaRadat(Pelaaja pelaaja) {
        return radat.annaRadat(pelaaja.getTunnusNro());
    }
    
    /**
     * Pelaajan lisääminen rekisteriin
     * @param lisattava pelaaja joka halutaan lisätä
     * <pre name="test">
     * Frisbeegolfrekisteri rekisteri = new Frisbeegolfrekisteri();
     * Pelaaja make = new Pelaaja();
     * Pelaaja pera = new Pelaaja();
     * make.rekisteroi(); pera.rekisteroi();
     * rekisteri.getPelaajia() === 0;
     * rekisteri.lisaa(make);
     * rekisteri.getPelaajia() === 1;
     * rekisteri.lisaa(pera);
     * rekisteri.getPelaajia() === 2;
     * rekisteri.annaPelaaja(0) === make;
     * rekisteri.annaPelaaja(1) === pera;
     * rekisteri.lisaa(make);
     * rekisteri.getPelaajia() === 3;
     * </pre>  
     */
    public void lisaa(Pelaaja lisattava) {
        pelaajat.lisaa(lisattava);
    }
    
    /**
     * Lisätään uusi rata rekisteriin
     * @param rata lisättävä rata
     * @example
     * <pre name="test">
     *  Frisbeegolfrekisteri rekisteri = new Frisbeegolfrekisteri();
     *  Pelaaja pelaaja1 = new Pelaaja();
     *  Pelaaja pelaaja2 = new Pelaaja();
     *  rekisteri.lisaa(pelaaja1);
     *  rekisteri.lisaa(pelaaja2);
     *  rekisteri.lisaa(pelaaja1);
     *  Collection<Pelaaja> loytyneet = rekisteri.etsi("", -1);
     *  Iterator<Pelaaja> it = loytyneet.iterator();
     *  it.next() === pelaaja1;
     *  it.next() === pelaaja2;
     *  it.next() === pelaaja1;
     * </pre>
     */
    public void lisaa(Rata rata) {
        radat.lisaa(rata);
    }
    
    /**
     * Korvataan tai lisätään uusipelaaja tietorakenteeseen riippuen onko vastaavaa pelaajaa jo siellä
     * @param pelaaja muokatessa tullut "klooni"
     */
    public void korvaaTaiLisaa(Pelaaja pelaaja) {
        pelaajat.korvaaTaiLisaa(pelaaja);
    }
    
    /**
     * Korvataan tai lisätään uusi rata tietorakenteeseen riippuen onko vastaavaa rata jo siellä
     * @param rata klooni joka on muokatessa syntynyt
     */
    public void korvaaTaiLisaa(Rata rata) {
        radat.korvaaTaiLisaa(rata);
    }
    
    /**
     * Poistetaan pelaaja rekisteristä
     * @param pelaaja pelaaja jota ollaan poistamassa
     * @return totuusarvo, true/false, onnistuiko poisto
     */
    public int poista(Pelaaja pelaaja) {
        if (pelaaja == null) return 0;
        int palautus = pelaajat.poista(pelaaja.getTunnusNro());
        radat.poistaPelaajanRadat(pelaaja.getTunnusNro());
        return palautus;
        
    }
    
    /**
     * Poistaa radan rekisteristä
     * @param rata rata, jota ollaan poistamassa
     */
    public void poistaRata(Rata rata) {
        radat.poista(rata);
    }
    
    /**
     * Lukee tallenetut tiedot tiedostosta
     * @param nimi tiedoston nimi, jota lukemisessa käytetään
     * @throws SailoException jos tiedostonlukeminen ei onnistu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Frisbeegolfrekisteri rekisteri = new Frisbeegolfrekisteri();
     *  
     *  Pelaaja pelaaja1 = new Pelaaja(); pelaaja1.taytaTestiTiedot(); pelaaja1.rekisteroi();
     *  Pelaaja pelaaja2 = new Pelaaja(); pelaaja2.taytaTestiTiedot(); pelaaja2.rekisteroi();
     *  Rata rata11 = new Rata(); rata11.taytaRandmTiedot(pelaaja1.getTunnusNro());
     *  Rata rata21 = new Rata(); rata21.taytaRandmTiedot(pelaaja2.getTunnusNro());
     *  Rata rata12 = new Rata(); rata12.taytaRandmTiedot(pelaaja1.getTunnusNro());
     *  Rata rata22 = new Rata(); rata22.taytaRandmTiedot(pelaaja2.getTunnusNro());
     *  Rata rata23 = new Rata(); rata23.taytaRandmTiedot(pelaaja2.getTunnusNro());
     *  
     *  String hakemisto = "testirekisteri";
     *  File dir = new File(hakemisto);
     *  File ftied = new File(hakemisto+"/pelaajat.dat");
     *  File frtied = new File(hakemisto+"/radat.dat");
     *  dir.mkdir();
     *  ftied.delete();
     *  frtied.delete();
     *  rekisteri.lueTiedostosta(hakemisto); #THROWS SailoException
     *  rekisteri.lisaa(pelaaja1);
     *  rekisteri.lisaa(pelaaja2);
     *  rekisteri.lisaa(rata11);
     *  rekisteri.lisaa(rata12);
     *  rekisteri.lisaa(rata21);
     *  rekisteri.lisaa(rata22);
     *  rekisteri.lisaa(rata23);
     *  rekisteri.tallenna();
     *  rekisteri = new Frisbeegolfrekisteri();
     *  rekisteri.lueTiedostosta(hakemisto);
     *  Collection<Pelaaja> kaikki = rekisteri.etsi("", -1);
     *  Iterator<Pelaaja> it = kaikki.iterator();
     *  it.next() === pelaaja1;
     *  it.next() === pelaaja2;
     *  it.hasNext() === false;
     *  List<Rata> loytyneet = rekisteri.annaRadat(pelaaja1);
     *  Iterator<Rata> ih = loytyneet.iterator();
     *  ih.next() === rata11;
     *  ih.next() === rata12;
     *  ih.hasNext() === false;
     *  loytyneet = rekisteri.annaRadat(pelaaja2);
     *  ih = loytyneet.iterator();
     *  ih.next() === rata21;
     *  ih.next() === rata22;
     *  ih.next() === rata23;
     *  ih.hasNext() === false;
     *  rekisteri.lisaa(pelaaja2);
     *  rekisteri.lisaa(rata23);
     *  rekisteri.tallenna();
     *  ftied.delete() === true;
     *  frtied.delete() === true;
     *  File fbak = new File(hakemisto+"/pelaajat.bak");
     *  File frbak = new File(hakemisto+"/radat.bak");
     *  fbak.delete() === true;
     *  frbak.delete() === true;
     *  dir.delete() === true; 
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        pelaajat = new Pelaajat();
        radat = new Radat();
        
        setTiedosto(nimi);
        pelaajat.lueTiedostosta();
        radat.lueTiedostosta();
    }
    
    /**
     * Tallentaa rekisterin tiedot tiedostoon
     * @throws SailoException heittää poikkeuksen jos tallentamisessa ilmenee ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            pelaajat.tallenna();
        } catch (SailoException e) {
            virhe = e.getMessage();
        }
        
        try {
            radat.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        
        if(virhe.equals("")) return;
        throw new SailoException(virhe);
    }
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + '/';
        pelaajat.setTiedostonPerusNimi(hakemistonNimi + "pelaajat");
        radat.setTiedostonPerusNimi(hakemistonNimi + "radat");
        
    }
    
    /**
     * Palautetaan "taulukossa" hakuehtoon vastaavien pelaajien viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenne löytyneistä pelaajista
     */
    public Collection<Pelaaja> etsi(String hakuehto, int k) {
        return pelaajat.etsi(hakuehto, k);
    }
    

    /**
     * Testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Frisbeegolfrekisteri rekisteri = new Frisbeegolfrekisteri();
        Pelaaja pelaaja1 = new Pelaaja();
        Pelaaja pelaaja2 = new Pelaaja();
        
        pelaaja1.rekisteroi();
        pelaaja2.rekisteroi();
        pelaaja1.taytaTestiTiedot();
        pelaaja2.taytaTestiTiedot();
        
        rekisteri.lisaa(pelaaja1);
        rekisteri.lisaa(pelaaja2);
        
        int id1 = pelaaja1.getTunnusNro();
        int id2 = pelaaja2.getTunnusNro();
        
        Rata lauste11 = new Rata(id1); lauste11.taytaRandmTiedot(id1); rekisteri.lisaa(lauste11);
        Rata lauste12 = new Rata(id1); lauste12.taytaRandmTiedot(id1); rekisteri.lisaa(lauste12);
        Rata lauste21 = new Rata(id2); lauste21.taytaRandmTiedot(id2); rekisteri.lisaa(lauste21);
        Rata lauste22 = new Rata(id2); lauste22.taytaRandmTiedot(id2); rekisteri.lisaa(lauste22);
        Rata lauste23 = new Rata(id2); lauste23.taytaRandmTiedot(id2); rekisteri.lisaa(lauste23);
        
        
        System.out.println("________________TESTATAAN REKISTERIN TOIMIVUUTTA______________");
        
        for (int i = 0; i < rekisteri.getPelaajia(); i++) {
            Pelaaja apu = rekisteri.annaPelaaja(i);
            System.out.println("Pelaaja paikassa: " + i);
            apu.tulosta(System.out);
            List<Rata> loydetyt = rekisteri.annaRadat(apu);
            for (Rata rat : loydetyt) {
                rat.tulosta(System.out);
            }
            System.out.println();
        }

    }

}
