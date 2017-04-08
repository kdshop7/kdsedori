package app.system;

import org.sql2o.Sql2o;
import static org.javalite.app_config.AppConfig.p;

public abstract class AbstractBaseService {

	protected static Sql2o sql2o;

	static {
		//sql2o = new Sql2o("jdbc:mysql://localhost/sedori", "root", "test");
		sql2o = new Sql2o(p("url"), p("username"), p("password"));
	}
}
