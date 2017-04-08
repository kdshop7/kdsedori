package app.config;

import org.javalite.activeweb.AbstractControllerConfig;
import org.javalite.activeweb.AppContext;
import org.javalite.activeweb.controller_filters.DBConnectionFilter;
import org.javalite.activeweb.controller_filters.TimingFilter;

import app.controllers.AmazonItemController;


public class AppControllerConfig extends AbstractControllerConfig {

    @Override
	public void init(AppContext context) {
        addGlobalFilters(new TimingFilter());
        add(new DBConnectionFilter()).to(AmazonItemController.class);

    }
}
