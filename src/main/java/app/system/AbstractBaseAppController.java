package app.system;

import org.javalite.activeweb.AppController;

public abstract class AbstractBaseAppController extends AppController {

		
	protected String $(String value) {
		return param(value);
	}
	
	protected Integer $i(String value) {
		return to_i(param(value));
	}

	protected Integer to_i(String value) {
		if (value == null) {
			return null;
		}		
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
