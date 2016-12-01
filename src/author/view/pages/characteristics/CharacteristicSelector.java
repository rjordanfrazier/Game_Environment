package author.view.pages.characteristics;

import java.io.File;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;

import author.view.util.FolderListor;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CharacteristicSelector {
	
	private Map<String, BooleanProperty> myCharacteristicSelectedMap;
	private Pane myPane;
	private ResourceBundle myCharacteristicResources;

	
	private static final String RESOURCE_PATH = "data.sprite.";
	
	public CharacteristicSelector(String aSpriteType) {
		myPane = new VBox();
		myCharacteristicSelectedMap = new TreeMap<String, BooleanProperty>();
		
		myCharacteristicResources = PropertyResourceBundle.getBundle( RESOURCE_PATH + "player");
		
		for(String s : myCharacteristicResources.keySet() ) {
			CheckBox check = new CheckBox(s);
			check.setAllowIndeterminate(false);
			
			myPane.getChildren().add(check);
			myCharacteristicSelectedMap.put(s, check.selectedProperty());
		}
	}
	
	public Pane getPane(){
		return myPane;
	}
	
	public Map<String, BooleanProperty> getCharacteristicSelectedMap(){
		return myCharacteristicSelectedMap;
	}
}
