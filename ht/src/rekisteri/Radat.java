package rekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * Radat- luokka, joka osaa esimerkiksi lisätä uuden radan
 * Vastuualueet:
 * - pitää yllä ratarekisteriä, eli osaa lisätä ja poistaa radan
 * - lukee ja kirjottaa radat tiedostoon 
 * - osaa etsiä ja lajitella
 * Avustajat:
 * -Rata luokka
 * 
 * @author joona
 * @version 21 Mar 2019
 * 
 */
public class Radat implements Iterable<Rata> {
    
    private String tiedostonPerusNimi = "";
    private boolean muutettu          = false;
    
    private final List<Rata> alkiot = new ArrayList<Rata>();
    
    /**
     * Konstruktori, radat alustetaan
     */
    public Radat() {
        //ei tee mitään
    }
    
    /**
     * Lisää uuden radan tietoineen tietorakenteeseen. 
     * @param rata lisättävä rata
     */
    public void lisaa(Rata rata) {
        this.alkiot.add(rata);
        muutettu = true;
    }
    
    /**
     * Korvaa radan tietorakenteessa. Jos vastaavaa rataa ei löydy
     * niin lisätään uutena ratana
     * @param rata rata jota ollaan mahdollisesti lisäämässä tai korvataan entinen
     */
    public void korvaaTaiLisaa(Rata rata) {
        int id = rata.getRadanTunnusNro();
        for (int i = 0; i < getLkm(); i++) {
            if (alkiot.get(i).getRadanTunnusNro() == id) {
                alkiot.set(i, rata);
                muutettu = true;
                return;
            }
        }
        lisaa(rata);
    }
    
    /**
     * poistaa radan
     * @param rata rata joka poistetaan
     * @return totuusarvo, onnistuiko poisto
     */
    public boolean poista(Rata rata) {
        boolean palautus = alkiot.remove(rata);
        if (palautus) muutettu = true;
        return palautus;
    }
    
    /**
     * Poistaa pelaajan kaikki radat
     * @param tunnusNro pelaajan id minkä mukaan poistettavia ratoja etsitään
     * @return montako rataa poistettin
     */
    public int poistaPelaajanRadat(int tunnusNro) {
        int n = 0;
        for (Iterator<Rata> it = alkiot.iterator(); it.hasNext(); ) {
            Rata rat = it.next();
            if ( rat.getPelaajaNro() == tunnusNro) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    /**
     * Palauttaa tiedostonnimen jota käytetään
     * @return tiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return this.tiedostonPerusNimi;
    }
    
    /**
     * Asetetaan tiedoston perusnimi
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        this.tiedostonPerusNimi = nimi;
    }
    
    /**
     * Palauttaa tallennukseen käytettävän tiedoston nimen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return this.tiedostonPerusNimi + ".dat";
    }
    
    /**
     * Luetaan annetusta tiedostosta
     * @throws SailoException heitetään poikkeus, jos täytyy
     * 
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  #import java.io.File;
     *      Radat ratoja = new Radat();
     *      Rata rata11 = new Rata(); rata11.taytaRandmTiedot(1);
     *      Rata rata21 = new Rata(); rata21.taytaRandmTiedot(2);
     *      Rata rata12 = new Rata(); rata12.taytaRandmTiedot(1);
     *      Rata rata22 = new Rata(); rata22.taytaRandmTiedot(2);
     *      Rata rata23 = new Rata(); rata23.taytaRandmTiedot(2);
     *      
     *      String tiedNimi = "testiRadat";
     *      File ftied = new File(tiedNimi+".dat");
     *      ftied.delete();
     *      ratoja.lueTiedostosta(tiedNimi); #THROWS SailoException
     *      ratoja.lisaa(rata21);
     *      ratoja.lisaa(rata11);
     *      ratoja.lisaa(rata22);
     *      ratoja.lisaa(rata12);
     *      ratoja.lisaa(rata23);
     *      ratoja.tallenna();
     *      ratoja = new Radat();
     *      ratoja.lueTiedostosta(tiedNimi);
     *      Iterator<Rata> i = ratoja.iterator();
     *      i.next().toString() === rata21.toString();
     *      i.next().toString() === rata11.toString();
     *      i.next().toString() === rata22.toString();
     *      i.next().toString() === rata12.toString();
     *      i.next().toString() === rata23.toString();
     *      i.hasNext() === false;
     *      ratoja.lisaa(rata23);
     *      ratoja.tallenna();
     *      ftied.delete() === true;
     *      File fbak = new File(tiedNimi +".bak");
     *      fbak.delete() === true;
     * </pre>
     *      
     */
    public void lueTiedostosta() throws SailoException{
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * Lukee radat tiedostosta
     * @param tiedosto tiedoston nimen alkuosa, ilman päätettä
     * @throws SailoException jos tiedoston lukeminen ei onnistu, heitetään poikkeus
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusNimi(tiedosto);
        try (Scanner fi = new Scanner(new FileInputStream(new File(getTiedostonNimi())))) {
            while ( fi.hasNext() ) { 
                
                    String rivi = fi.nextLine();
                    rivi = rivi.trim();
                    if (rivi.equals("") || rivi.charAt(0) == ';' ) continue;
                    Rata uusi = new Rata();
                    uusi.parse(rivi);
                    lisaa(uusi);
            }
            muutettu = false;
            
            } catch (FileNotFoundException ex) {
                throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
            }
    }
    
    /**
     * Palauttaa backup tiedoston nimen
     * @return backup tiedoston nimi ".bak" -pääte
     */
    public String getBakNimi() {
        return this.tiedostonPerusNimi + ".bak";
    }
    
    /**
     * Tallennetaan ratatiedot tiedostoon
     * @throws SailoException jos tallentaminen epäonnistuu, heitetään poikkeus
     */
    public void tallenna() throws SailoException {
        if (muutettu == false) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File (getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied.getCanonicalPath()))) {
            for (Rata rat: this) {
                fo.println(rat.toString());
            }
        } catch ( FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException("Tiedostoon " + ftied.getName() + " kirjoittamisessa on ongelmia");
        }
        
        muutettu = false;
    }
    
    /**
     * Palauttaa ratojen määrän
     * @return ratojen määrä kokonaislukuna
     */
    public int getLkm() {
        return this.alkiot.size();
    }
    
    
    
    
    /**
     * Iteraattori kaikille harrastuksille ja niiden läpi "viuhtomiseen"
     * @return iteraattori
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Radat ratoja = new Radat();
     *  Rata jankha1 = new Rata(2); ratoja.lisaa(jankha1);
     *  Rata jankha2 = new Rata(1); ratoja.lisaa(jankha2);
     *  Rata jankha3 = new Rata(2); ratoja.lisaa(jankha3);
     *  Rata jankha4 = new Rata(1); ratoja.lisaa(jankha4);
     *  Rata jankha5 = new Rata(2); ratoja.lisaa(jankha5);
     *  
     *  Iterator<Rata> ite = ratoja.iterator();
     *  ite.next() === jankha1;
     *  ite.next() === jankha2;
     *  ite.next() === jankha3;
     *  ite.next() === jankha4;
     *  ite.next() === jankha5;
     *  ite.next() === jankha3; #THROWS NoSuchElementException
     *  
     *  int n=0;
     *  int[] pelnum = {2,1,2,1,2};
     *  
     *  for (Rata rat : ratoja) {
     *    rat.getPelaajaNro() === pelnum[n]; n++;
     *  }
     *  n === 5;
     *  
     * </pre> 
     */
    @Override
    public Iterator<Rata> iterator() {
        return this.alkiot.iterator();
    }
    
    /**
     * Tutkitaan kaikki radat läpi ja palautetaan parametrina tuodon pelaajan ratatietoja
     * @param tunnusnro kenelle pelaajalle etsitään tietoja
     * @return löydetyt radat
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Radat ratoja1 = new Radat();
     * Rata lauste12 = new Rata(2); ratoja1.lisaa(lauste12);
     * Rata lauste11 = new Rata(1); ratoja1.lisaa(lauste11);
     * Rata lauste21 = new Rata(1); ratoja1.lisaa(lauste21);
     * Rata lauste22 = new Rata(2); ratoja1.lisaa(lauste22);
     * Rata lauste15 = new Rata(5); ratoja1.lisaa(lauste15);
     * Rata lauste32 = new Rata(2); ratoja1.lisaa(lauste32);
     * 
     * List<Rata> palautettavat;
     * palautettavat = ratoja1.annaRadat(3);
     * palautettavat.size() === 0;
     * palautettavat = ratoja1.annaRadat(1);
     * palautettavat.size() === 2;
     * palautettavat.get(0) == lauste11 === true;
     * palautettavat.get(1) == lauste21 === true;
     * palautettavat = ratoja1.annaRadat(5);
     * palautettavat.size() === 1;
     * palautettavat.get(0) == lauste15 === true;
     * </pre>
     */
    public List<Rata> annaRadat(int tunnusnro) {
        List<Rata> palautukset = new ArrayList<Rata>();
        for(Rata rat: this.alkiot) {
            if(rat.getPelaajaNro() == tunnusnro) palautukset.add(rat);
        }
        
        return palautukset;
    }
    
    
    /**
     * Testi pääohjelma radat-luokan testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Radat ratoja = new Radat();
        
        Rata testi = new Rata();
        testi.taytaRandmTiedot(2);
        Rata testi2 = new Rata();
        testi2.taytaRandmTiedot(1);
        Rata testi3 = new Rata();
        testi3.taytaRandmTiedot(2);
        Rata testi4 = new Rata();
        testi4.taytaRandmTiedot(2);
        
        ratoja.lisaa(testi);
        ratoja.lisaa(testi2);
        ratoja.lisaa(testi3);
        ratoja.lisaa(testi2);
        ratoja.lisaa(testi4);
        
        
        System.out.println("====================== RATOJEN TESTI =======================");
        
        List<Rata> ratoja2 = ratoja.annaRadat(2);
        
        for (Rata track : ratoja2) {
            System.out.println(track.getPelaajaNro() + " ");
            track.tulosta(System.out);
        }

        
        
    }

    

}
