package app.system;

import static org.javalite.app_config.AppConfig.p;

import com.google.inject.AbstractModule;

import am.ik.aws.apa.AwsApaRequester;
import am.ik.aws.apa.AwsApaRequesterImpl;

public class AwsApaRequesterModule extends AbstractModule {

	protected final static String AWS_ACCESS_KEY = p("aws.accesskey.id");

	protected final static String AWS_SECRET_KEY = p("aws.secret.accesskey");

	protected final static String AWS_ENDPOINT = p("aws.endpoint");

	protected final static String AWS_ASSOCIATE_TAG = p("aws.associate.tag");

	@Override
	protected void configure() {
		bind(AwsApaRequester.class)
				.toInstance(new AwsApaRequesterImpl(AWS_ENDPOINT, AWS_ACCESS_KEY, AWS_SECRET_KEY, AWS_ASSOCIATE_TAG));
	}
}
