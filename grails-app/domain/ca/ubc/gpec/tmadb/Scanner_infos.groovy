package ca.ubc.gpec.tmadb

class Scanner_infos {

	String name
	String description
	Integer magnification
	String scan_image_type
	Integer image_quality 
	
    static constraints = {
		name(blank:false)
		magnification (nullable: true)
		image_quality (nullable: true)
	}
	
	//static hasMany = [ tma_core_images:Tma_core_images, whole_section_images:Whole_section_images ]
	
	/**
	 * override default toString()
	 */
	public String toString() {
		return name;
	}
}
