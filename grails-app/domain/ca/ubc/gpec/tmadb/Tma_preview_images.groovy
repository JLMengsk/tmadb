package ca.ubc.gpec.tmadb

class Tma_preview_images implements GenericParaffin_blockSlicePreview_images, Comparable<Tma_preview_images>{

    Preview_images preview_image;
    
    static constraints = {
    }
    
    static mapping = {
        preview_image column:'preview_image_id'
    }
    
    /**
     * for Comparable interface
     * 1. preview image
     * 2. id
     */
    public int compareTo(Tma_preview_images other) {
        int compareByPreview_image = this.getPreview_image().compareTo(other.getPreview_image());
        return compareByPreview_image == 0 ? (this.getId() - other.getId()) : compareByPreview_image;
    }
    
    /**
     * return a nice name for preview type
     */
    public String showPreview_typeNiceName() {
        return preview_image.showPreview_typeNiceName();
    }
    
    /**
     * check to see if the preview type is label
     */
    public boolean showIsLabel_view() {
        return preview_image.showIsLabel_view();
    }
    
    /**
     * check to see if the preview type is slide view
     */ 
    public boolean showIsSlide_view() {
        return preview_image.showIsSlide_view();
    }
    
    /**
     * check to see if the preview type is thumbnail view
     */
    public boolean showIsThumbnail_view() {
        return preview_image.showIsThumbnail_view();
    }
    
    public boolean showIsLowres_view() {
        return preview_image.showIsLowres_view();
    }
    
    /**
     * show preview image url
     */
    public String showImageURL() {
        /**
         * show preview image url
         */
        return preview_image.showImageURL();
    }
    
    /**
     * get preview image width
     * @return 
     */
    public int showWidth(){
        return preview_image.getWidth();
    }
    
    /**
     * get preview image height
     * @return 
     */
    public int showHeight() {
        return preview_image.getHeight();
    }
    
    /**
     * get preview type of preview image
     * @return 
     */
    public String showPreview_type() {
        return preview_image.getPreview_type();
    }
    
    /**
     * get scale to original image
     * NOTE: can be null!!!
     * @return 
     */
    public Float showScale_to_original() {
        return preview_image.getScale_to_original();
    }
}
