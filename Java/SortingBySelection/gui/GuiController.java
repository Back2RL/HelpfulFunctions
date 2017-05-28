package SortingBySelection.gui;

/**
 * Created by leonard on 22.01.17.
 */
public class GuiController {
	private static GuiController ourInstance = new GuiController();

	public static GuiController getInstance() {
		return ourInstance;
	}

	private GuiController() {
	}
}
