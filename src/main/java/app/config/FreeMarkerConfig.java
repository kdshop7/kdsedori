package app.config;


public class FreeMarkerConfig extends org.javalite.activeweb.freemarker.AbstractFreeMarkerConfig {
    @Override
    public void init() {
        //this is to override a strange FreeMarker default processing of numbers 
        getConfiguration().setNumberFormat("0.##");

		getConfiguration().setEncoding(getConfiguration().getLocale(), "UTF-8");
		getConfiguration().setDefaultEncoding("UTF-8");
		getConfiguration().setOutputEncoding("UTF-8");
		
	
    }
}
