/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ubc.gpec.tmadb;

/**
 *
 * @author samuelc
 */
public interface GenericParaffin_blockSlicePreview_images {

    /**
     * return a nice name for preview type
     */
    public String showPreview_typeNiceName();

    /**
     * check to see if the preview type is label
     */
    public boolean showIsLabel_view();

    /**
     * check to see if the preview type is slide view
     */
    public boolean showIsSlide_view();

    /**
     * check to see if the preview type is thumbnail view
     */
    public boolean showIsThumbnail_view();

    public boolean showIsLowres_view();

    /**
     * show preview image url
     */
    public String showImageURL();
    
    /**
     * get preview image width
     * @return 
     */
    public int showWidth();
    
    /**
     * get preview image height
     * @return 
     */
    public int showHeight();
    
    /**
     * get preview type of preview image
     *
     * @return
     */
    public String showPreview_type();
 
    /**
     * get scale to original image
     * 
     * @return 
     */
    public Float showScale_to_original();
}
