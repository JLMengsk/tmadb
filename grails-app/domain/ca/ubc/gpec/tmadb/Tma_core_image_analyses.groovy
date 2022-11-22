package ca.ubc.gpec.tmadb

class Tma_core_image_analyses {

    Image_analyses image_analysis
    Tma_core_images tma_core_image
    
    static constraints = {
    }
    
    static mapping = {
        image_analysis column:'image_analysis_id'
        tma_core_image column:'tma_core_image_id'
    }
}
