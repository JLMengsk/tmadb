package ca.ubc.gpec.tmadb

class Image_analysis_settings {

    String name
    String description
    Image_analysis_types image_analysis_type
    Set<Image_analyses> image_analyses
        
    static constraints = {
        name (nullable: true)
        description (nullable: true)        
    }
    
    static mapping = {
        image_analysis_type column:'image_analysis_type_id'
    }
    
    static hasMany = [ image_analyses:Image_analyses ]
}
