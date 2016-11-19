package author.controller;

import author.model.AuthorModelFactory;
import author.model.IAuthorModel;
import author.view.AuthorView;
import javafx.scene.Scene;

/**
 * @author George Bernard
 */
public class ConcreteAuthorController implements IAuthorController, IAuthorControllerExternal{

	private IAuthorModel authorModel;
	private AuthorView authorView;
	/* (non-Javadoc)
	 * @see author.controller.IAuthorControllerExternal#getScene()
	 */
	public ConcreteAuthorController() {
		this.authorModel = new AuthorModelFactory().create((IAuthorController) this);
		this.authorView = new AuthorView((IAuthorController) this);
	}
	
	@Override
	public Scene getScene() {
		return this.authorView.getScene();
	}

	/* (non-Javadoc)
	 * @see author.controller.IAuthorController#getModel()
	 */
	@Override
	public IAuthorModel getModel() {
		return this.authorModel;
	}

}
