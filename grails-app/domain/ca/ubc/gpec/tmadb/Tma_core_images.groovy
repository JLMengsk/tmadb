package ca.ubc.gpec.tmadb

class Tma_core_images implements Comparable<Tma_core_images>, SecuredMethods, ImageViewer {
    //
    String name;
    String description;
    Tma_cores tma_core;
    Tma_slices tma_slice;
    Date scanning_date;
    Image_servers image_server;
    String server_path;
    String resource_name;
    Scanner_infos scanner_info;
    String comment;
    Tma_preview_images tma_preview_image; // a preview e.g. thumbnail of the TMA block (i.e. not the TMA core)
    Set<Tma_results> tma_results;
    Set<Tma_result_default_score_name_view> tma_result_default_score_names;
    
    static hasMany = [ tma_results:Tma_results, tma_result_default_score_names:Tma_result_default_score_name_view ]

    static constraints = { 
        name(blank: false) 
        comment(nullable: true)
        tma_preview_image(nullable: true)
    }
    
    static mapping = {
        tma_core column:'tma_core_id'
        tma_slice column:'tma_slice_id'
        image_server column:'image_server_id'
        scanner_info column:'scanner_info_id'
        tma_preview_image column:'tma_preview_image_id'
    }

    /**
     * for Comparable interface
     * 1. compare by Tma_slice
     * 2. compare by Tma_core
     */
    public int compareTo(Tma_core_images obj){
        int compareByTma_slice = this.tma_slice.compareTo(obj.getTma_slice());
        return compareByTma_slice == 0 ? this.tma_core.compareTo(obj.getTma_core()) : compareByTma_slice;
    }
    
    /**
     * override default toString
     */
    public String toString() {
        return(""+tma_slice.staining_detail.biomarker+"; "+tma_core)
    }

    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}

        def results = User_permits.withCriteria {
            and {
                eq("user", user)
                eq("tma_slice",this.tma_slice)
            }
        }
        if (results.size() > 0) {return true} else {return false}
    }

    /**
     * return null if not found or user is not permitted
     * @param inputId
     * @param user
     * @return
     */
    public static Tma_core_images get(String inputId, Users user) {
        def result = Tma_core_images.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }

    /**
     * show corresponding H&E images
     * return null if not found
     * TODO CURRENTLY HARD CODE H&E biomarker!!!!  NEED TO CHANGE
     * @return
     */
    public Tma_core_images showHandE() {
		
        Iterator<Tma_core_images> otherTma_core_imagesItr = tma_core.tma_core_images.iterator();
		
        while (otherTma_core_imagesItr.hasNext()) {
            Tma_core_images other_tma_core_image = otherTma_core_imagesItr.next();
            if (other_tma_core_image.tma_slice.staining_detail.biomarker.name.equals(Biomarkers.BIOMARKER_NAME_HE)) {
                return other_tma_core_image;
            }
        }
		
        return null; // not found!!!
    }

    /**
     * show corresponding myoepithelial marker images
     * return null if not found
     * TODO CURRENTLY HARD CODE myoepithelial marker name (to P63)!!!!  NEED TO CHANGE
     * @return
     */
    public Tma_core_images showMM() {
		
        Iterator<Tma_core_images> otherTma_core_imagesItr = tma_core.tma_core_images.iterator();
		
        while (otherTma_core_imagesItr.hasNext()) {
            Tma_core_images other_tma_core_image = otherTma_core_imagesItr.next();
            if (other_tma_core_image.tma_slice.staining_detail.biomarker.name.equals(Biomarkers.BIOMARKER_NAME_P63)) {
                return other_tma_core_image;
            }
        }
		
        return null; // not found!!!
    }
    
    /**
     * show biomarkers link to this image
     * @return
     */
    public String showBiomarker() {
        return(""+tma_slice.staining_detail.biomarker)
    }
	
    /**
     * show preview image url
     */
    public String showPreviewImageURL() {
        return ImageViewerMethods.showPreviewImageURL(image_server, server_path, resource_name);
    }

    /**
     * show image url
     */
    public String showImageURL() {
        return ImageViewerMethods.showImageURL(image_server, server_path, resource_name);
    }

    /**
     * show image using html & javascript
     */
    public String showImageHtml(String webAppRootDir, int width, int height) {
        return ImageViewerMethods.showImageHtml(webAppRootDir, image_server, server_path, resource_name, width, height,(int)id);
    }
   
    /**
     * show image using html & javascript
     */
    public String showImageHtml(String webAppRootDir) {
        return showImageHtml(webAppRootDir, ImageViewerMethods.IMAGE_VIEWER_WIDTH, ImageViewerMethods.IMAGE_VIEWER_HEIGHT);
    }

}
