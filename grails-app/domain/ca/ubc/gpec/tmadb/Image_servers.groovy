package ca.ubc.gpec.tmadb

class Image_servers {

    String address
    String name
    String description
    SortedSet<Tma_core_images> tma_core_images
    SortedSet<Whole_section_images> whole_section_images
    SortedSet<Preview_images> preview_images
        
    static constraints = {
        address(blank:false)
    }
	
    static hasMany = [ tma_core_images:Tma_core_images, whole_section_images:Whole_section_images, preview_images:Preview_images]
	
    // override default toString method
    public String toString() {
        return(address);
    }
}
