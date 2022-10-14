/*
 * access info of a generic paraffin block
 */
package ca.ubc.gpec.tmadb;

import java.util.SortedSet;

/**
 *
 * @author samuelc
 */
public interface GenericParaffin_blocks {
    /**
     * set specimen number
     * @param specimen_number 
     */
    public void inputSpecimen_number(String specimen_number);
    
    /**
     * show specimen number
     * @return 
     */
    public String showSpecimen_number();
    
    /**
     * set additional info
     * @param additional_info 
     */
    public void inputAdditional_info(String additional_info);
    
    /**
     * show additional info
     * @return 
     */
    public String showAdditional_info();
    
    /**
     * set comment
     * @param comment 
     */
    public void inputComment(String comment);
    
    /**
     * show comment
     * @return 
     */
    public String showComment();
    
    /**
     * set tissue type
     * @param tissue_type 
     */
    public void inputTissue_type(Tissue_types tissue_type);
    
    /**
     * show tissue type
     * @return 
     */
    public Tissue_types showTissue_type();
    
    /**
     * set patient
     * @param patient 
     */
    public void inputPatient(Patients patient);
    
    /**
     * show patient
     * @return 
     */
    public Patients showPatient();
    
    /**
     * set paraffin block package
     * @param paraffin_block_package 
     */
    public void inputParaffin_block_package(Paraffin_block_packages paraffin_block_package);
    
    /**
     * show paraffin block package
     * @return 
     */
    public Paraffin_block_packages showParaffin_block_package();
    
    /**
     * set whole section slice
     * @param whole_section_slices 
     */
    public void inputWhole_section_slice(Whole_section_slices whole_section_slices);
    
    /**
     * show whole section slice
     * @return 
     */
    public SortedSet<Whole_section_slices> showWhole_section_slices();
}
