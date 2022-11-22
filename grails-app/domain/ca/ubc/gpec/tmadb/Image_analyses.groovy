package ca.ubc.gpec.tmadb

class Image_analyses {

    Image_analysis_settings image_analysis_setting
    Set<Tma_core_image_analyses> tma_core_image_analyses
    Set<Whole_section_image_analyses> whole_section_image_analyses
    
    static constraints = {
    }
    
    static mapping = {
        image_analysis_setting column:'image_analysis_setting_id'
    }
    
    static hasMany = [ tma_core_image_analyses:Tma_core_image_analyses, whole_section_image_analyses:Whole_section_image_analyses ]
}
