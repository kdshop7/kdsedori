package app.controllers;

import app.system.AbstractBaseAppController;

public class HomeController extends AbstractBaseAppController {

	public void index() {
		redirect(AmazonItemController.class, "list");
	}
}
