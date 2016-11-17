package author.view.pages.level_editor.windows;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The abstract class that windows in the level editor will extend
 * 
 * @author Jordan Frazier
 *
 */
public abstract class AbstractLevelEditorWindow {

	private Pane myWindow;

	public <T extends Node> void addChild(T child) {
		myWindow.getChildren().add(child);
	}

	public Pane getWindow() {
		return myWindow;
	}

	protected Pane createWindow() {
		return myWindow = new VBox();
	}
	
	protected abstract void createToolBar();
}
