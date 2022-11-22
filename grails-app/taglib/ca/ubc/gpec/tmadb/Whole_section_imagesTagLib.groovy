package ca.ubc.gpec.tmadb

class Whole_section_imagesTagLib {

    /**
     * display all available preview images linke to this whole_section_image
     * order of display:
     * 1. thumbnail
     * 2. slide view
     * 3. label
     * 
     * NOTE: need to wrap around this taglib with a div with fixed width/height!!!
     * @attr id of the whole_section_image
     * @attr width of the tab container
     * @attr mode
     */
    def display_all_whole_section_preview_images = {attr, body ->
        Whole_section_images whole_section_image = Whole_section_images.get(attr['id']);
        String mode = attr['mode'];
        int width = attr['width'];
        boolean displayLink = mode == Whole_section_images.WHOLE_SECTION_IMAGE_VIEW_MODE_REGULAR;
        boolean topTab = mode == Whole_section_images.WHOLE_SECTION_IMAGE_VIEW_MODE_TEST;
        out << "<div dojoType=\"dijit.layout.TabContainer\" tabPosition=\""+(topTab?"top":"right-h")+"\" style=\"width: 100%; height: 100%;\">";
        whole_section_image.whole_section_preview_images.each { whole_section_preview_image ->
            if (!whole_section_preview_image.showIsLowres_view()) {
                // figure out width/height to display image, so that it will fit the tab panel best
                String widthParam = "width='";
                if (whole_section_preview_image.showWidth() > whole_section_preview_image.showHeight()) {
                    widthParam = widthParam+(width-(topTab?50:80));
                } else {
                    widthParam = widthParam+(Math.round((float)width*(float)whole_section_preview_image.showWidth()/(float)whole_section_preview_image.showHeight())-50);
                }
                widthParam = widthParam+"px'";
                
                out << "<div dojoType=\"dijit.layout.ContentPane\" title=\""+whole_section_preview_image.showPreview_typeNiceName()+"\">";
                out << (displayLink ? "<a href='"+g.createLink(controller:"whole_section_images", action:"show", id:attr['id'])+"' title='click me to view virtual slide image'>    " : "");
                out << "<img src='"+whole_section_preview_image.showImageURL()+"' "+widthParam+"/>";
                out << (displayLink ? "</a>" : "");
                out << "</div>";
            } // no need to show low res view as it will look the same as thumbnail
        }
        out << "</div>";
    }
}
