package ca.ubc.gpec.tmadb
/**
 * this class is responsible for implementing methods for viewing images
 * 
 * @author samuelc
 *
 */
class ImageViewerMethods {

    // some constants
    public static int IMAGE_VIEWER_WIDTH = 800; // in pixal
    public static int IMAGE_VIEWER_HEIGHT = 650; // in pixal
    public static int IMAGE_PREVIEW_WIDTH_LARGE = 500; // preview mode ... large
    public static int IMAGE_PREVIEW_HEIGHT_LARGE = 500; // preview mode ... large
    public static String NUCLEI_COUNTER_CANVAS_ID = "nucleiCounterCanvas";
    public static String FIELD_SELECTOR_CANVAS_ID = "fieldSelectorCanvas";
   
    // the following constants NEED TO MATCH VALUES IN corresponding javascript!!!!
    public static String FIELD_SELECTOR_STATE_SELECTING = "field_selector_state_selecting";
    public static String FIELD_SELECTOR_STATE_SCORING = "field_selector_state_scoring";
    public static String FIELD_SELECTOR_STATE_READ_ONLY = "field_selector_state_read_only";
    
    /**
     * only good for TMA image ... for whole section images, get resources from whole_section_preview_images
     */
    public static String showPreviewImageURL(Image_servers image_server, String server_path, String resource_name) {
        if (resource_name == null) {
            return null
        }
        if (resource_name.endsWith(".jpg") | resource_name.endsWith(".jpeg") | resource_name.endsWith(".bmp")) {
            return ""+image_server+server_path+resource_name
        }
        return null;
    }

    public static String showImageURL(Image_servers image_server, String server_path, String resource_name) {
        if (resource_name?.endsWith(".zoomified")) {
            return ""+image_server+server_path+resource_name+"/"; // *.zoomified is a directory
        }
        return showPreviewImageURL(image_server, server_path, resource_name)
    }

    /**
     * show image using html & javascript using default width/height
     */
    public static String showImageHtml(String webAppRootDir, Image_servers image_server, String server_path, String resource_name) {
        return showImageHtml(webAppRootDir, image_server, server_path, resource_name, IMAGE_VIEWER_WIDTH, IMAGE_VIEWER_HEIGHT)
    }
    
    /**
     * show image using html & javascript
     * FIXED uniqueId ... there can be only ONE image viewer on html page
     */
    public static String showImageHtml(String webAppRootDir, Image_servers image_server, String server_path, String resource_name, int width, int height) {
        return showImageHtml(webAppRootDir, image_server, server_path, resource_name, width, height, 0);
    }
    
    /**
     * show image using html & javascript
     * WITH uniqueId so that there can be multiple image viewers on a single html page
     * TODO: this function uses dojo.ready ... REMEMBER TO CHANGE when upgrading dojo!!!
     */
    public static String showImageHtml(String webAppRootDir, Image_servers image_server, String server_path, String resource_name, int width, int height, int uniqueId) {
        String html=""
        if (resource_name == null) {
            return ImageType.IMAGE_NOT_AVAILABLE_NOTICE
        }
        if (resource_name?.endsWith(".jpg") | resource_name?.endsWith(".jpeg") | resource_name?.endsWith(".bmp")) {
            // use Simple Image Viewer (javascript) from http://www.spictrading.com/viewer/home.php
            html="<div class='dialog'><table><tbody><tr class='prop'><td><div id='parentElement"+uniqueId+"' style='overflow:hidden'><canvas id="+uniqueId+" width="+width+" height="+height+"></canvas></div></td></tr></tbody></table></div>\n"+
                    "<script type='text/javascript'> \n"+
                    "var zp;"+
                    "require(['dojo/ready'], function(ready){ ready(function(){"+
                        "zp = new Zoompan('"+showImageURL(image_server, server_path, resource_name)+"','"+uniqueId+"',false);"+        
                        "}); });\n</script>\n"
        } else if (resource_name?.endsWith(".zoomified")) {
            html="<OBJECT CLASSID='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' "+
            //"CODEBASE='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0' "+ // 2014-03-27 ... don't think we need this!
					"WIDTH='"+width+"' "+
					"HEIGHT='"+height+"' "+
					"ID='theMovie'>"+
					"<PARAM NAME='FlashVars' VALUE='zoomifyImagePath="+showImageURL(image_server, server_path, resource_name)+"'>"+
					"<PARAM NAME='MENU' VALUE='FALSE'>"+
					"<PARAM NAME='SRC' VALUE='"+(image_server.getAddress())+"images/aperio/zoomify/zoomifyViewer.swf'>"+
					"<EMBED FlashVars='zoomifyImagePath="+showImageURL(image_server, server_path, resource_name)+"' "+
					"SRC='"+(image_server.getAddress())+"images/aperio/zoomify/zoomifyViewer.swf' "+
					"MENU='false' "+
            //"PLUGINSPAGE='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' "+ // 2014-03-27 ... don't think we need this!
					"WIDTH='"+width+"' "+
					"HEIGHT='"+height+"' "+
					"ID='theMovieMain'>"+
					"</EMBED>"+
					"</OBJECT>"//+
            //"</TR>"+
            //"</TABLE>"
        }
        return html;
    }
    
}
