/**
 * 
 */
package rekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import static kanta.RatingTarkistus.*;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Pelaaja luokka
 * Vastuualueet:
 * - ei tiedä rekisteristä tai käyttöliitttmästä
 * - tietää pelaajan tiedot (etunimi, rating, kätisyys jne.)
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa merkkijonon "1|Pelaaja1|Rating|...|" jäsenen tiedoiksi
 * - osaa antaa merkkijonona tietyn kentän tiedot
 * - osaa laittaa tietyn merkkijonon tiettyyn kenttään
 * Avustajat:
 * -
 * @author joona
 * @version 7 Mar 2019
 *
 */
public class Pelaaja implements Cloneable {
    
    private int tunnusNro;
    private String nimi = "";
    private int rating;
    private int ranking;
    private String katisyys = "";
    private String sponsori = "";
    private String lempirata = "";
    private String kotikaupunki = "";
    private String lisatiedot = "";
    
    private static int seuraavanNro = 1;

    /**
     * Rekisteröi pelaajan rekisteriin, eli antaa pelaajalle tunnusnumeron
     * @return jäsenen uusi tunnusnumero
     * @example
     * <pre name="test">
     *   Pelaaja pelaaja1 = new Pelaaja();
     *   pelaaja1.getTunnusNro() === 0;
     *   pelaaja1.rekisteroi();
     *   pelaaja1.rekisteroi();
     *   Pelaaja pelaaja2 = new Pelaaja();
     *   pelaaja2.rekisteroi();
     *   int num1 = pelaaja1.getTunnusNro();
     *   int num2 = pelaaja2.getTunnusNro();
     *   num1 + 1 === num2;
     *  </pre>
     */
    public int rekisteroi() {
        this.tunnusNro = seuraavanNro;
        seuraavanNro++;
        return this.tunnusNro;
    }
    
    /**
     * getteri tunnusnumeroon
     * @return palautetaan pelaajan tunnusnumero
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    /**
     * Asetetaan tunnusnumero
     * @param no tunnusnumero
     */
    public void setTunnusNro(int no) {
        this.tunnusNro = no;
        if (tunnusNro >= seuraavanNro) seuraavanNro = this.tunnusNro+1;
    }
    
    /**
     * Päästään käsiksi pelaajan nimeen
     * @return palautetaan pelaajan nimi
     */
    public String getNimi() {
        return this.nimi;
    }
    
    /**
     * Päästään käsiksi pelaajan ratingiin
     * @return palautetaan pelaajan rating
     */
    public String getRating() {
        return this.rating + "";
    }
    
    /**
     * Päästään käsiksi pelaajan rankingiin
     * @return palautetaan pelaajan ranking
     */
    public String getRanking() {
        return this.ranking + "";
    }
    
    /**
     * Päästään käsiksi pelaajan kätisyyteen
     * @return palautetaan pelaajan kätisyys
     */
    public String getKatisyys() {
        return this.katisyys;
    }
    
    /**
     * Päästään käsiksi pelaajan sponsoriin
     * @return palautetaan pelaajan sponsori
     */
    public String getSponsori() {
        return this.sponsori;
    }
    
    /**
     * Päästään käsiksi pelaajan lempirataan
     * @return palautetaan pelaajan lempirata
     */
    public String getLempirata() {
        return this.lempirata;
    }
    
    /**
     * Päästään käsiksi pelaajan kotikaupunkiin
     * @return palautetaan pelaajan kotikaupunki
     */
    public String getKotikaupunki() {
        return this.kotikaupunki;
    }
    
    /**
     * Päästään käsiksi pelaajan lisätietoihin
     * @return palautetaan pelaajan lisätiedot
     */
    public String getLisatiedot() {
        return this.lisatiedot;
    }
    
    /**
     * asettaa nimen
     * @param s nimi
     * @return palautetaan null, kun homma onnistuu
     */
    public String setNimi(String s) {
        this.nimi = s;
        return null;
    }
    
    /**
     * asettaa rankingin
     * @param s ranking
     * @return palauttaa null, kun homma onnistuu
     */
    public String setRanking(String s) {
        int rank = Integer.parseInt(s.trim());
        this.ranking = rank;
        return null;
    }
    
    /**
     * asettaa pelaajalle ratingin
     * @param s rating
     * @return palautetaan null, kun homma onnistuu
     */
    public String setRating(String s) {
        int rat = Integer.parseInt(s.trim());
        this.rating = rat;
        return null;
    }
    
    /**
     * asettaa kotikaupungin pelaajalle
     * @param s kotikaupunki
     * @return palautetaan null, kun homma onnistuu
     */
    public String setKotikaupunki(String s) {
        this.kotikaupunki = s;
        return null;
    }
    
    /**
     * asettaa pelaajan kätisyyden
     * @param s kätisyys (vasen/oikea)
     * @return palauttaa null, jos kirjoitetaan oikein, muuten virheilmoitus
     */
    public String setKatisyys(String s) {
        String testattava = s.toLowerCase().trim();
        if (testattava.equals("vasen") || testattava.equals("oikea")) {
            this.katisyys = s.trim();
            return null;
        }
        return "Kätisyys voi olla vain " + '"' + "oikea" + '"' + " tai " + '"' + "vasen" + '"';
        
    }
    
    /**
     * asettaa pelaajalle sponsorin
     * @param s sponsori
     * @return palauttaa nullin kun homma on hoidettu
     */
    public String setSponsori(String s) {
        this.sponsori = s;
        return null;
    }
    
    /**
     * asetetaan pelaajalle lempirata
     * @param s lempirata
     * @return palautetaan null, kun homma on hoidettu
     */
    public String setLempirata(String s) {
        this.lempirata = s;
        return null;
    }
    
    /**
     * asetetaan pelaajalle lisätiedot
     * @param s lisätiedot
     * @return palauttaa null, kun homma on ohi
     */
    public String setLisatiedot(String s) {
        this.lisatiedot = s;
        return null;
    }
    
    
    
    /**
     * Tulostaa pelaajan tiedot
     * @param out minne tulostetaan (tietovirta)
     */
    public void tulosta(PrintStream out) {
        out.println("Pelaajan id: " + String.format("%03d", this.tunnusNro));
        out.println("Nimi: " + this.nimi);
        out.println("Kotikaupunki: " + this.kotikaupunki);
        out.println("Ranking:   " + this.ranking + "   rating:   " + this.rating);
        out.println("Kätisyys:   " + this.katisyys);
        out.println("Sponsori:   " + this.sponsori);
        out.println("Lempirata:   " + this.lempirata);
        out.println("Lisätietoja:   " + this.lisatiedot);
        out.println();
    }
    
    /**
     * Luodaan mahdollisuus tulostaa erilaisiin tietovirtoihin.
     * Outputstreamin avulla onnistuu monet eri tavat tulostaa.
     * @param os tietovirta, minne halutaan tulostaa
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Ottaa pelaajan tiedot riviltä 
     * @param rivi rivi, josta tiedot otetaan
     * @example
     *  <pre name="test">
     *      Pelaaja pelaaja = new Pelaaja();
     *      pelaaja.parse(" 2  |    Testi Pelaaja |  198");
     *      pelaaja.getTunnusNro() === 2;
     *      pelaaja.toString().startsWith("2|Testi Pelaaja|198|") === true;
     *      
     *      pelaaja.rekisteroi();
     *      int n = pelaaja.getTunnusNro();
     *      pelaaja.parse(""+(n+20));
     *      pelaaja.rekisteroi();
     *      pelaaja.getTunnusNro() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        this.nimi = Mjonot.erota(sb, '|', nimi);
        this.rating = Mjonot.erota(sb, '|', rating);
        this.ranking = Mjonot.erota(sb, '|', ranking);
        this.katisyys = Mjonot.erota(sb, '|', katisyys);
        this.sponsori = Mjonot.erota(sb, '|', sponsori);
        this.lempirata = Mjonot.erota(sb, '|', lempirata);
        this.kotikaupunki = Mjonot.erota(sb, '|', kotikaupunki);
        this.lisatiedot = Mjonot.erota(sb, '|', lisatiedot);
    }
    
    /**
     * Palauttaa pelaajan tiedot tiedostoystävällisessä muodossa paalumerkeillä erotettuna
     * @return pelaajan tiedot tolppamerkeillä erotettuna
     * @example
     * <pre name="test">
     *  Pelaaja pelaaja= new Pelaaja();
     *  pelaaja.parse(" 2   | Testi Pelaaja     |  123");
     *  pelaaja.toString().startsWith("2|Testi Pelaaja|123|") === true;
     * </pre> 
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + this.nimi + "|" + this.rating + "|" + this.ranking + "|" + 
                this.katisyys + "|" + this.sponsori + "|" + this.lempirata + "|" + this.kotikaupunki + "|" +
                this.lisatiedot;
    }
    
    @Override
    public Pelaaja clone() throws CloneNotSupportedException {
        Pelaaja uusiKlooni;
        uusiKlooni = ( Pelaaja ) super.clone();
        return uusiKlooni;
    }

    
    /**
     * "Rakennus-apupala", apumetodi, joka täyttää pelaajan tiedot testitiedoilla.
     * @param rndmRating randomilla valittu rating, joka erottaa aluksi pelaajia toisistaan
     */
    public void taytaTestiTiedot(int rndmRating) {
        this.nimi = "Pelaaja playeri";
        this.rating = rndmRating;
        this.ranking = 250;
        this.katisyys = "vasen";
        this.sponsori = "latitude";
        this.lempirata = "Tuusula";
        this.kotikaupunki = "Helsinki";
        this.lisatiedot = "lempiruoka: lasagne";
    }
    
    /**
     * Täyttää testitiedot random ratingilla.
     */
    public void taytaTestiTiedot() {
        int rndmRating = rand(1, 1000);
        taytaTestiTiedot(rndmRating);
    }
    
    @Override
    public boolean equals(Object pelaaja) {
        if (pelaaja == null) return false;
        return this.toString().equals(pelaaja.toString());
    }
    
    
    @Override
    public int hashCode() {
        return this.tunnusNro;
    }

    /**
     * Testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pelaaja vane = new Pelaaja();
        Pelaaja jone = new Pelaaja();
        vane.rekisteroi();
        jone.rekisteroi();
        
        
        vane.tulosta(System.out);
        jone.tulosta(System.out);
        
        vane.taytaTestiTiedot();
        jone.taytaTestiTiedot();
        
        vane.tulosta(System.out);
        jone.tulosta(System.out);
    }

}
