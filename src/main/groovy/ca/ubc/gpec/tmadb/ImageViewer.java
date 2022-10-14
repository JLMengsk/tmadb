package ca.ubc.gpec.tmadb;

/**
 * make sure the image viewing interface is consistent
 * @author samuelc
 *
 */

public interface ImageViewer {

	public String showPreviewImageURL();

	public String showImageURL();

	/**
	 * show image using html & javascript
	 */
	public String showImageHtml(String webAppRootDir);
}
