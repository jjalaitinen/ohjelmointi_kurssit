package rekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import rekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.05.12 23:41:30 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class RataTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi84 */
  @Test
  public void testRekisteroi84() {    // Rata: 84
    Rata keljo = new Rata(); 
    assertEquals("From: Rata line: 86", 0, keljo.getRadanTunnusNro()); 
    keljo.rekisteroi(); 
    Rata tuusula = new Rata(); 
    tuusula.rekisteroi(); 
    int num1 = keljo.getRadanTunnusNro(); 
    int num2 = tuusula.getRadanTunnusNro(); 
    assertEquals("From: Rata line: 92", num2-1, num1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse209 */
  @Test
  public void testParse209() {    // Rata: 209
    Rata rata = new Rata(); 
    rata.parse("    5  |  12  | Tali  | 56  "); 
    assertEquals("From: Rata line: 212", 12, rata.getPelaajaNro()); 
    assertEquals("From: Rata line: 213", "5|12|Tali|56", rata.toString()); 
    rata.rekisteroi(); 
    int n = rata.getRadanTunnusNro(); 
    rata.parse(""+(n+20)); 
    rata.rekisteroi(); 
    assertEquals("From: Rata line: 221", n+20+1, rata.getRadanTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString236 */
  @Test
  public void testToString236() {    // Rata: 236
    Rata rata = new Rata(); 
    rata.parse(" 3  |   8    |  Keljonkangas    |   49  "); 
    assertEquals("From: Rata line: 239", "3|8|Keljonkangas|49", rata.toString()); 
  } // Generated by ComTest END
}