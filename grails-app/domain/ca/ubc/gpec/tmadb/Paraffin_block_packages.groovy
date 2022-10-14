package ca.ubc.gpec.tmadb

class Paraffin_block_packages {

    String name
    String description
    SortedSet<Paraffin_blocks> paraffin_blocks
    
    static constraints = {
        description (nullable: true)
    }
	
    static hasMany = [paraffin_blocks:Paraffin_blocks]
}
