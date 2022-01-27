package fxFrisbeeRekisteri;
	
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import rekisteri.Frisbeegolfrekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author joona
 * @version 1 Feb 2019
 *
 */
public class FrisbeeRekisteriMain extends Application {
	@Override
	public void start(Stage primaryStage) {
	    try {
	         final FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaIkkunaGUIView.fxml"));
	         final Pane root = (Pane)ldr.load();
	         final FrisbeeRekisteriGUIController cntrl = (FrisbeeRekisteriGUIController)ldr.getController();
	         
	         Scene scene = new Scene(root);
	         scene.getStylesheets().add(getClass().getResource("frisbeerekisteri.css").toExternalForm());
	         primaryStage.setScene(scene);
	         primaryStage.setTitle("FribaPedia");
	         
	         boolean totuus = ModalController.showModal(FrisbeeRekisteriGUIController.class.getResource("AloitusGUIView.fxml"),
	                                                                                 "FribaPedia", null, true);
	         
	         if (totuus == true) {  
	         
    	         Frisbeegolfrekisteri rekisteri = new Frisbeegolfrekisteri();
    	         cntrl.setRekisteri(rekisteri);
    	         
    	         primaryStage.show();
    	         
    	         cntrl.lueTiedosto("");
	         }
	         else Platform.exit(); 
	         
	         
             
	    
	    } catch(Exception e) {
	         e.printStackTrace();
	                  }
	              }
	
	
	/**
	 * Käynnistetään ohjelma
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
