package kanta;

/**
 * Luokka ratingin tarkistamista varten
 * @author joona
 * @version 18 Mar 2019
 *
 */
public class RatingTarkistus {
    
    /**
     * Arvotaan random numero tietylle välille
     * @param ala alaraja 
     * @param yla yläraja
     * @return random numero
     */
    public static int rand(int ala, int yla) {
        double numero = (yla-ala)*Math.random() + ala;
        return (int)Math.round(numero);
    }

}
