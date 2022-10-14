package ca.ubc.gpec.tmadb

class Image_analysis_types {

    String name
    String description
    Set<Image_analysis_settings> image_analysis_settings
        
    static constraints = {
        name (nullable: true)
        description (nullable: true)
    }
    
    static hasMany = [ image_analysis_settings:Image_analysis_settings ]
}
