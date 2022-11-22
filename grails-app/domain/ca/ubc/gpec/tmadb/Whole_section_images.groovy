package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.ImageViewerMethods;
import ca.ubc.gpec.tmadb.util.MiscUtil;

class Whole_section_images implements SecuredMethods, ImageViewer, Comparable<Whole_section_images>{

    public static final String WHOLE_SECTION_IMAGE_VIEW_MODE_REGULAR = "whole_section_image_view_mode_regular";
    public static final String WHOLE_SECTION_IMAGE_VIEW_MODE_TEST = "whole_section_image_view_mode_test"; // view mode during test
    
    String name;
    String description;
    Date scanning_date;
    Whole_section_slices whole_section_slice;
    Image_servers image_server;
    String server_path;
    String resource_name;
    Scanner_infos scanner_info;
    Integer width;
    Integer height;
    Float microns_per_pixel;
    SortedSet<Whole_section_preview_images> whole_section_preview_images;
    
    static hasMany = [ whole_section_results:Whole_section_results, whole_section_preview_images:Whole_section_preview_images ]

    static constraints = {
        name(blank: false)
        scanner_info(blank: false)
    }

    static mapping = {
        whole_section_slice column:'whole_section_slice_id'
        image_server column:'image_server_id'
        scanner_info column:'scanner_info_id'
    }

    /**
     * for Comparable interface
     * 1. whole_section_slice
     * 2. scanning_date
     * 3. name
     * 4. id
     */
    public int compareTo(Whole_section_images other) {
        int compareByWhole_section_slice = this.getWhole_section_slice().compareTo(other.getWhole_section_slice());
        if (compareByWhole_section_slice != 0) {
            return compareByWhole_section_slice;
        }
        int compareByScanning_date = 0;
        if (this.getScanning_date() != null && other.getScanning_date() != null) {
            compareByScanning_date = this.getScanning_date().compareTo(other.getScanning_date());
        }
        if (compareByScanning_date != 0) {
            return compareByScanning_date;
        }
        int compareByName = this.getName().compareTo(other.getName());
        return compareByName == 0 ? (this.getId() - other.getId()) : compareByName;
    }
    
    /**
     * override toString
     */
    public String toString() {
        return scanner_info.getName() + (scanning_date!=null?(" ("+MiscUtil.formatDate(scanning_date)+")"):"");
    }

    /**
     * show preview image
     * @param type of preview image: slide label, slide view, thumbnail, low res image
     * @return whole_section_preview_images of desired type and null if not found
     */
    public Whole_section_preview_images showPreviewImage(String type) {
        for (Whole_section_preview_images w:whole_section_preview_images) {
            if(w.showPreview_type() == type) {
                return w;
            }
        }
        return null; // not found
    }
    
    /**
     * show preview image url
     * @param type of preview image: slide label, slide view, thumbnail, low res image
     * @return url link and null if image not found
     */
    public String showPreviewImageURL(String type) {
        Whole_section_preview_images w = showPreviewImage(type);
        return w == null ? null : w.showImageURL();
    }

    /**
     * show preview (thumbnail) image url
     * @return url link and null if image not found
     */
    public String showPreviewImageURL() {
        Whole_section_preview_images w = showPreviewImage(Preview_images.PREVIEW_TYPE_THUMBNAIL_VIEW);
        return w == null ? null : w.showImageURL();
    }
    
    /**
     * return lowres image
     * @return lowres image or null if image not found
     */
    public Whole_section_preview_images showLowResImage() {
        return showPreviewImage(Preview_images.PREVIEW_TYPE_LOWRES_VIEW);
    }
    
    /**
     * show lowres image url
     * @return url link and null if image not found
     */
    public String showLowResImageURL() {
        Whole_section_preview_images w = showPreviewImage(Preview_images.PREVIEW_TYPE_LOWRES_VIEW);
        return w == null ? null : w.showImageURL();
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
    public String showImageHtml(String webAppRootDir) {
        return ImageViewerMethods.showImageHtml(webAppRootDir, image_server, server_path, resource_name);
    }
    
    /**
     * show image using html & javascript with width/height parameter
     */
    public String showImageHtml(String webAppRootDir, int width, int height) {
        return ImageViewerMethods.showImageHtml(webAppRootDir, image_server, server_path, resource_name, width, height);
    }
    
    /**
     * show corresponding H&E images
     * return null if not found
     * TODO CURRENTLY HARD CODE H&E biomarker!!!!  NEED TO CHANGE
     * @return
     */
    public Whole_section_images showHandE() {
        for (Whole_section_slices slice:whole_section_slice.getParaffin_block().getWhole_section_slices()) {
            if (slice.getStaining_detail().getBiomarker().getName().equals(Biomarkers.BIOMARKER_NAME_HE)) {
                // there can be > 1 whole section images associated with a whole section slice ... just use the first one!
                if (!slice.getWhole_section_images().isEmpty()) {
                    return slice.getWhole_section_images().first();
                }
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
    public Whole_section_images showMM() {
        for (Whole_section_slices slice:whole_section_slice.getParaffin_block().getWhole_section_slices()) {
            if (slice.getStaining_detail().getBiomarker().getName().equals(Biomarkers.BIOMARKER_NAME_P63)) {
                // there can be > 1 whole section images associated with a whole section slice ... just use the first one!
                if (!slice.getWhole_section_images().isEmpty()) {
                    return slice.getWhole_section_images().first();
                }
            }
        }	
        return null; // not found!!!
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
                eq("whole_section_slice",whole_section_slice)
            }
        }
        if (results.size() > 0) {return true} else {return false}
    }
}