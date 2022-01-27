package rekisteri;

/**
 * Poikkeusluokka 
 * @author joona
 * @version 3 Apr 2019
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
      
      
    /**
     * Poikkeuksen konstruktori
     * @param viesti poikkeukseen liittyvä viesti
     */
      public SailoException(String viesti) {
          super(viesti);
      }

}
