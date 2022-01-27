package rekisteri;
import static kanta.RatingTarkistus.rand;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Rata-luokka
 * Vastuualueet:
 * -ei tiedä rekisteristä, eikä käyttöliittymästä
 * -tietää ratojen kentät
 * -osaa tarkistaa tietyn kentän syntaksin, esim. ratanimi alkaa aina isolla, ja samaa rataa
 * ei voi ilmoittaa kahta kertaa
 * -osaa muuttaa 2|Nokia- merkkijonon radan tiedoiksi
 * -osaa antaa merkkijonona radan nimen
 * -osaa laittaa merkkijonon radan nimeksi
 * -osaa antaa numerona radan tuloksen
 * -osaa laittaa numerona tuloksen radan tulokseksi
 * @author joona
 * @version 22 Mar 2019
 *
 */
public class Rata implements Cloneable{
    
    private int ratanro;
    private int pelaajanro;
    private String rataNimi;
    private int tulos;
    
    
    private static int seuraavaNro = 1;
    
    /**
     * Rata-luokan konstruktori
     */
    public Rata() {
        //
    }
    
    /**
     * Yhdistetään/alustetaan tietylle pelaajalle rata
     * @param pelaajaNro pelaajan id-numero viittenä
     */
    public Rata(int pelaajaNro) {
        this.pelaajanro = pelaajaNro;
    }
    
    /**
     * Apumetodi "rakennuspalikka", jonka avulla saadaan täytettyä jotain ratatietoja
     * ratatulos arvotaan, jotta olisi erilaisia arvoja.
     * @param nro numero, viite henkilöön kenen "radasta" on kyse.
     */
    public void taytaRandmTiedot(int nro) {
        this.pelaajanro = nro;
        this.rataNimi = "Lauste";
        this.tulos = rand(50, 114);
        
    }
    
    /**
     * Tulostetaan radan tiedot
     * @param out tietovirta, minne tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.rataNimi + " " + this.tulos);
    }
    
    
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta, minne tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream (os));
    }
    
    /**
     * Annetaan radalle seuraava rek. numero
     * @return harrastuksen uusi numero identifioitavaksi
     * @example
     * <pre name="test">
     *  Rata keljo = new Rata();
     *  keljo.getRadanTunnusNro() === 0;
     *  keljo.rekisteroi();
     *  Rata tuusula = new Rata();
     *  tuusula.rekisteroi();
     *  int num1 = keljo.getRadanTunnusNro();
     *  int num2 = tuusula.getRadanTunnusNro();
     *  num1 === num2-1;
     *  </pre>
     */
    public int rekisteroi() {
        this.ratanro = seuraavaNro;
        seuraavaNro++;
        return this.ratanro;
    }
    
    /**
     * Palautetaan radan oma numero(id)
     * @return radan id
     */
    public int getRadanTunnusNro() {
        return this.ratanro;
    }
    
    /**
     * Palauttaa mille pelaajalla ratatiedot kuuluvat
     * @return pelaajan id
     */
    public int getPelaajaNro() {
        return this.pelaajanro;
    }
    
    /**
     * Palautetaan radan nimi
     * @return palauttaa radan nimen
     */
    public String getNimi() {
        return this.rataNimi;
    }
    
    /**
     * @return palauttaa ekan "järkevän kentän"
     */
    public int getEkaKentta() {
        return 2;
    }
    
    /**
     * @return palautetaan kenttien määrä
     */
    public int getKenttia() {
        return 4;
    }
    
    
    /**
     * palautetaan tietyn kentän kysymys
     * @param k mikä kenttä "indeksi"
     * @return valitun kentän teksti
     */
    public String getKysymys(int k) {
        switch (k) {
            case 0 : return "id";
            case 1 : return "pelaajaId";
            case 2 : return "rata";
            case 3 : return "tulos";
            default: return "???";
        }
    }
    
  /**
   * Annetaan tietyn kentän sisältö
   * @param k Minkä kentän sisältö halutaan
   * @return valitun kentän sisältö
   */
  public String anna(int k) {
      switch (k) {
          case 0:
              return "" + ratanro;
          case 1:
              return "" + pelaajanro;
          case 2:
              return rataNimi;
          case 3:
              return "" + tulos;
          default:
              return "???";
      }
  }
    
    /**
     * Asetetaan tunnusnumero radalle, ja varmistetaan että seuraava on aina suurempi kuin tähän mennessä suurin
     * @param no asetettava tunnusnro
     */
    public void setRataNro(int no) {
        this.ratanro = no;
        if (ratanro >= seuraavaNro) seuraavaNro = ratanro + 1;
    }
    
    /**
     * Asettaa nimen radalle
     * @param s nimi
     * @return palauttaa jos tulee virhe
     */
    public String setNimi(String s) {
        this.rataNimi = s;
        return null;
    }
    
    /**
     * Asettaa tuloksen
     * @param s tulos String tyyppisenä
     * @return palauttaa jos tulee virhe
     */
    public String setTulos(String s) {
        StringBuilder sb = new StringBuilder(s);
        this.tulos = Mjonot.erotaInt(sb, 0);
        return null;
    }
    
    /**
     * Otetaan radan tiedot merkkijonosta (erotellaan |-merkin kummaltakin puolelta)
     * @param rivi rivi josta otetaan tiedot radalle
     * @example
     * <pre name="test">
     *  Rata rata = new Rata();
     *  rata.parse("    5  |  12  | Tali  | 56  ");
     *  rata.getPelaajaNro() === 12;
     *  rata.toString() === "5|12|Tali|56";
     *  
     *  
     *  
     *  rata.rekisteroi();
     *  int n = rata.getRadanTunnusNro();
     *  rata.parse(""+(n+20));
     *  rata.rekisteroi();
     *  rata.getRadanTunnusNro() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setRataNro(Mjonot.erota(sb, '|', getRadanTunnusNro()));
        this.pelaajanro = Mjonot.erota(sb, '|', pelaajanro);
        this.rataNimi = Mjonot.erota(sb, '|', rataNimi);
        this.tulos = Mjonot.erota(sb, '|', tulos);
    }
    
    /**
     * Palauttaa radantiedot merkkijonona, joka voidaan tallentaa tiedostoon
     * @return ratatiedot eroteltuna tolppamerkeillä
     * @example
     *  <pre name="test">
     *      Rata rata = new Rata();
     *      rata.parse(" 3  |   8    |  Keljonkangas    |   49  ");
     *      rata.toString() === "3|8|Keljonkangas|49";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getRadanTunnusNro() + "|" + this.pelaajanro + "|" + this.rataNimi + "|" + this.tulos;
    }
    
    @Override
    public Rata clone() throws CloneNotSupportedException {
        Rata uusiKlooni;
        uusiKlooni = ( Rata ) super.clone();
        return uusiKlooni;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return this.toString().equals(obj.toString());
    }
    
    

    @Override
    public int hashCode() {
        return this.ratanro;
    }

    /**
     * testi pääohjelma Rata-luokalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Rata rata = new Rata();
        rata.taytaRandmTiedot(2);
        rata.tulosta(System.out);

    }

}
