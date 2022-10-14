package ca.ubc.gpec.tmadb

class Preview_images implements Comparable<Preview_images>{

    public static final String PREVIEW_TYPE_LABEL_VIEW = "LABEL_VIEW";
    public static final String PREVIEW_TYPE_SLIDE_VIEW = "SLIDE_VIEW";
    public static final String PREVIEW_TYPE_THUMBNAIL_VIEW = "THUMBNAIL_VIEW";
    public static final String PREVIEW_TYPE_LOWRES_VIEW = "LOWRES_VIEW";
    
    String name;
    String description;
    Image_servers image_server;
    String server_path;
    String resource_name;
    String preview_type;
    Integer width;
    Integer height;
    Float scale_to_original;
        
    static constraints = {
        name(nullable: true)
        description(nullable: true)
        scale_to_original (nullable: true)
    }

    static mapping = {
        image_server column:'image_server_id'
        scanner_info column:'scanner_info_id'
    }
        
    /**
     * compare 2 preview type ...
     * PREVIEW_TYPE_THUMBNAIL_VIEW < PREVIEW_TYPE_LOWRES_VIEW < PREVIEW_TYPE_SLIDE_VIEW < PREVIEW_TYPE_LABEL_VIEW
     */
    private int compareByPreview_type(String type1, String type2) {
        if (type1 == type2) {return 0;}
        switch(type1) {
        case PREVIEW_TYPE_THUMBNAIL_VIEW:
            return -1; // thumbnail first
            break
        case PREVIEW_TYPE_LOWRES_VIEW:
            if (type2 == PREVIEW_TYPE_THUMBNAIL_VIEW) {
                return 1;
            } else if (type2 in [PREVIEW_TYPE_SLIDE_VIEW, PREVIEW_TYPE_LABEL_VIEW]) {
                return -1;
            } else {
                return 0;
            }
            break
        case PREVIEW_TYPE_SLIDE_VIEW:
            if (type2 in [PREVIEW_TYPE_THUMBNAIL_VIEW, PREVIEW_TYPE_LOWRES_VIEW]) {
                return 1;
            } else if (type2 == PREVIEW_TYPE_LABEL_VIEW) {
                return -1;
            } else {
                return 0;
            }
            break
        case PREVIEW_TYPE_LABEL_VIEW:
            return 1; // label last
            break
        default: 
            return 0;
        }
    }
    
    /**
     * for Comparable interface
     * 1. preview_type: PREVIEW_TYPE_THUMBNAIL_VIEW < PREVIEW_TYPE_SLIDE_VIEW < PREVIEW_TYPE_LABEL_VIEW
     * 2. compare by id
     */
    public int compareTo(Preview_images other) {
        int compareByPreview_type = compareByPreview_type(this.getPreview_type(), other.getPreview_type());
        return compareByPreview_type == 0 ? (this.getId() - other.getId()) : compareByPreview_type;
    }
    
    /**
     * return a nice name for preview type
     */
    public String showPreview_typeNiceName() {
        if (showIsLabel_view()) {
            return "Slide label";
        } else if (showIsSlide_view()) {
            return "Slide view";
        } else if (showIsThumbnail_view()) {
            return "Thumbnail";
        } else {
            return "Unknown preview type";
        }
    }
    
    /**
     * check to see if the preview type is label
     */
    public boolean showIsLabel_view() {
        return preview_type == PREVIEW_TYPE_LABEL_VIEW;
    }
    
    /**
     * check to see if the preview type is slide view
     */ 
    public boolean showIsSlide_view() {
        return preview_type == PREVIEW_TYPE_SLIDE_VIEW;
    }
    
    /**
     * check to see if the preview type is thumbnail view
     */
    public boolean showIsThumbnail_view() {
        return preview_type == PREVIEW_TYPE_THUMBNAIL_VIEW;
    }
    
    public boolean showIsLowres_view() {
        return preview_type == PREVIEW_TYPE_LOWRES_VIEW;
    }
    
    /**
     * show preview image url
     */
    public String showImageURL() {
        /**
         * show preview image url
         */
        return ImageViewerMethods.showPreviewImageURL(image_server, server_path, resource_name);
    }
}
