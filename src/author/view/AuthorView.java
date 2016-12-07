package author.view;

import java.io.File;
import java.io.FileNotFoundException;

import author.controller.IAuthorController;
import author.view.pages.level_editor.ILevelEditorExternal;
import author.view.pages.level_editor.LevelEditorFactory;
import author.view.pages.menu.MenuFactory;
import author.view.pages.sprite.page.SpritesPage;
import game_data.Level;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import util.facades.TabPaneFacade;
import util.filehelpers.FileLoader.FileExtension;
import util.filehelpers.FileLoader.FileLoader;

/**
 * AuthorView handles the JavaFX GUI.
 * 
 * @author George Bernard, Cleveland Thompson, Addison Howenstine, Jordan
 *         Frazier
 */
public class AuthorView {

	private static final String STYLESHEET = "data/gui/author-style.css";
	Scene myScene;
	Pane myPane = new VBox();
	TabPaneFacade myTabPaneFacade;
	IAuthorController authorController;

	private SpritesPage mySpritesPage;
	private ILevelEditorExternal myLevelEditor;

	// TODO move these to properties, as well as button names
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;

	public AuthorView(IAuthorController authorController) {
		this.authorController = authorController;
		myScene = new Scene(myPane, WIDTH, HEIGHT, Color.WHITE);
		myScene.getStylesheets().add(getStyleSheet());
		initializeView();
	}

	private void initializeView() {
		this.mySpritesPage = new SpritesPage(authorController);
		this.myLevelEditor = new LevelEditorFactory().create(this.authorController);

		myPane.getChildren().addAll(buildToolBar(), buildTabPane());
	}

	public void reinitializeView() {
		this.myPane.getChildren().clear();
		initializeView();
	}

	/**
	 * Returns Toolbar built for primary AuthorScene
	 */
	private Node buildToolBar() {

		MenuBar menuBar = new MenuBar();
		Menu menuNew = new Menu("New");
		Menu menuSave = new Menu("Save");
		Menu menuLoad = new Menu("Load");
		menuBar.getMenus().addAll(menuNew, menuSave, menuLoad);

		menuNew.getItems().addAll(new MenuFactory().createItem("New Game", e -> {
			this.authorController.getModel().newGame();
		}).getItem(), new MenuFactory().createItem("New Level", e -> {
			Level createdLevel = this.myLevelEditor.createLevel();
			if (createdLevel != null) {
				this.authorController.getModel().getGame().addNewLevel(createdLevel);
			}
		}).getItem());

		menuSave.getItems().add(new MenuFactory().createItem(("Save Game"), e -> {
			openSaveDialog();
		}).getItem());

		menuLoad.getItems().add(new MenuFactory().createItem("Load Game", e -> {
			authorController.getModel().loadGame(loadFileChooser());
			;
		}).getItem());

		return menuBar;
	}

	/**
	 * Returns TabPane built for primary AuthorScene
	 */
	private Node buildTabPane() {
		myTabPaneFacade = new TabPaneFacade();
		myTabPaneFacade.getTabPane().prefWidthProperty().bind(myScene.widthProperty());
		myTabPaneFacade.getTabPane().prefHeightProperty().bind(myScene.heightProperty());
		myTabPaneFacade.getTabPane().setSide(Side.BOTTOM);

		myTabPaneFacade.addTab(mySpritesPage.toString(), mySpritesPage.getRegion());
		myTabPaneFacade.addTab(myLevelEditor.toString(), myLevelEditor.getPane());

		return myTabPaneFacade.getTabPane();
	}

	private void openSaveDialog() {
		TextInputDialog input = new TextInputDialog("Sample_Name");
		input.setTitle("Save Dialog");
		input.setHeaderText("Input Game Name");
		input.setContentText("Name: ");
		input.setOnCloseRequest(e -> {
			authorController.getModel().saveGame(input.getResult());
		});
		input.showAndWait();
	}

	private File loadFileChooser() {
		File file;
		try {
			file = new FileLoader(FileExtension.XML).loadSingle();
			return file;
		} catch (FileNotFoundException e) {
			// TODO: Popup Error window
			e.printStackTrace();
			return null;
		}
	}

	private String getStyleSheet() {
		File css = new File(STYLESHEET);
		return css.toURI().toString();
	}

	public Scene getScene() {
		return myScene;
	}

}
