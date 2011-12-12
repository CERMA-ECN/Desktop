package fr.ecn.common.desktop;

/**
 * @author jerome
 * 
 * Interface that define a few things about an application
 * 
 * An instance of this interface is passed to MainController on application start
 */
public interface Application {

	public Controller getFistController();

	public String getTitle();
	
}
