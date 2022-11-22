package ca.ubc.gpec.tmadb

class Tma_result_default_score_name_view implements Serializable { // when using composite id, must implements seriablizable

    Tma_core_images tma_core_image
    Tma_result_names tma_result_name
    String default_score_name
    
    static constraints = {
        default_score_name (nullable: true)
    }
    
    static mapping = {
        id composite:['tma_core_image', 'tma_result_name'] // required since we don't have a primary key
        tma_core_image column:'tma_core_image_id'
        tma_result_name column:'tma_result_name_id'
        version false // this domain class is mapping to a view, therefore, do not use version
    }
}
