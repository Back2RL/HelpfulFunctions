package sample;

public class Controller {

	private Thread task = null;

	public Model getModel() {
		return model;
	}

	private Model model = new Model();

	public void action() {
		if(task == null) {
			task = new Thread(() -> {
				model.setMessage("Action Pressed!");
				for (int i = 3; i > -1; --i) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					model.setMessage("Action Pressed! " + i);
				}
				model.setMessage("");
				task = null;
			});
			task.start();
		}
	}
}
