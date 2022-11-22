package ca.ubc.gpec.tmadb

/**
 * link between Keywords and Paraffin_blocks
 * - do not implement SecureMethod ... allow public access 
 */
class Keyword_paraffin_blocks implements Comparable<Keyword_paraffin_blocks>{

    Keywords keyword;
    Paraffin_blocks paraffin_block;
    String comment;
    Integer display_order; // display order: starts 0
    
    static mapping = {
        keyword column:'keyword_id'
        paraffin_block column:'paraffin_block_id'
    }
    
    static constraint = {
        comment(nullable:true)
    }
    
    /**
     * for Comparable interface
     */
    public int compareTo(Keyword_paraffin_blocks keyword_paraffin_block) {
        int orderByParaffin_block = this.paraffin_block.compareTo(keyword_paraffin_block.paraffin_block);
        return orderByParaffin_block == 0 ? this.display_order - keyword_paraffin_block.display_order : orderByParaffin_block;
    }
}
