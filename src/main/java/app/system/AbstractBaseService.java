package app.system;

import org.sql2o.Sql2o;

import am.ik.aws.apa.AwsApaRequester;
import am.ik.aws.apa.AwsApaRequesterImpl;

import static org.javalite.app_config.AppConfig.p;

public abstract class AbstractBaseService {

	protected static Sql2o sql2o;

	protected static AwsApaRequester requester;

	protected final static String YAHOO_AUC_ID = p("yahoo.auc.id");

	protected final static String AWS_ACCESS_KEY = p("aws.accesskey.id");

	protected final static String AWS_SECRET_KEY = p("aws.secret.accesskey");

	protected final static String AWS_ENDPOINT = p("aws.endpoint");

	protected final static String AWS_ASSOCIATE_TAG = p("aws.associate.tag");

	public AbstractBaseService() {
		sql2o = new Sql2o(p("url"), p("username"), p("password"));
		requester = new AwsApaRequesterImpl(AWS_ENDPOINT, AWS_ACCESS_KEY, AWS_SECRET_KEY, AWS_ASSOCIATE_TAG);
	}
}
