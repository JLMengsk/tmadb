package ca.ubc.gpec.tmadb

class Whole_section_image_analyses {

    Image_analyses image_analysis
    Whole_section_images whole_section_image
    
    static constraints = {
    }
    
    static mapping = {
        image_analysis column:'image_analysis_id'
        whole_section_image column:'whole_section_image_id'
    }
}
