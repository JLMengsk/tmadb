package ca.ubc.gpec.tmadb.upload.rnaExtractions
/**
 * upload RNA extraction records
 */


import java.util.Date;
import java.util.List;
import java.text.*

import org.apache.commons.logging.Log;

import ca.ubc.gpec.tmadb.Rna_extractions;
import ca.ubc.gpec.tmadb.Coring_projects;
import ca.ubc.gpec.tmadb.Coring;
import ca.ubc.gpec.tmadb.Patient_sources;
import ca.ubc.gpec.tmadb.Patients;
import ca.ubc.gpec.tmadb.Surgical_blocks;

class UploadRnaExtractions {
	// some constants
	static final String HNAME_SPECIMEN_NUMBER = "specimen_number"
	static final String HNAME_COMMENT = "comment"
	
	// instance variables
	Log log
	int patient_source_id;
	int coring_project_id;
	String record_date_year;
	String record_date_month;
	String record_date_day;
	byte[] myFile;
	List<Rna_extractions> rna_extractionsArr;

	/**
	 * constructor
	 * @param log
	 */
	public UploadRnaExtractions(Log log) {
		this.log = log
	}
	
	/**
	* find item in inputArr and return index (starts with 0)
	* - returns -1 if not found
	* @param item
	* @param input
	* @return
	*/
   private int find(String item, String[] inputArr) throws UploadRnaExtractionsParseFileException {
	   int index=0;
	   while(!item.trim().equalsIgnoreCase(inputArr[index].trim())) {
		   index++;
		   if (index == inputArr.length) {
			   throw new UploadRnaExtractionsParseFileException("column: "+item+", not found in input file!!!")
		   }
	   }
	   return(index)
   }
   
   public void parseFile(String sep) throws UploadRnaExtractionsParseFileException, UploadRnaExtractionsSaveException {
	   // get some objects before reading/parsing the file
	   Patient_sources patient_source = Patient_sources.get(patient_source_id)
	   Coring_projects coring_project = Coring_projects.get(coring_project_id)
	   Date record_date  = new SimpleDateFormat("yyyy-MM-dd").parse(record_date_year+ "-"+record_date_month+ "-"+record_date_day)
	   
	   // read/parse input file
	   BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(myFile)))
	   
	   rna_extractionsArr = new ArrayList<Rna_extractions>()
				 
	   String line = br.readLine();
	   if (line == null) {
		   throw new UploadRnaExtractionsParseFileException("file is empty!!!")
	   }
	   // skip blank lines ...
	   while (line.trim().length()==0) {
		   line = br.readLine();
		   if (line == null) {
			   // end of file reached but no non-blank lines!!!
			   throw new UploadRnaExtractionsParseFileException("file is empty!!!")
		   }
	   }
	   
	   // this is the header line ... look for the header columns
	   String[] headerLineArr = line.split(sep)
	   int indexSpecimenNumber = find(HNAME_SPECIMEN_NUMBER, headerLineArr);
	   int indexComment        = find(HNAME_COMMENT,         headerLineArr);
	   
	   line = br.readLine();
	   while(line != null) {
		   String[] lineArr = line.split(sep)
	   
		   String specimen_number = lineArr[indexSpecimenNumber]
		   String comment = lineArr[indexComment]
		   
			
		   // make sure surgical block exists and is unique
		   def c = Surgical_blocks.createCriteria()
		   ArrayList surgical_blocksArr = c {
			   eq("specimen_number", specimen_number)
			   'in'("patient", Patients.findAllByPatient_source(patient_source))
		   }
		   if (surgical_blocksArr.size() == 0) {
			   throw new UploadRnaExtractionsParseFileException("Surgical Block NOT FOUND, specimennumber="+specimen_number)
		   }
		   if (surgical_blocksArr.size() > 1) {
			   throw new UploadRnaExtractionsParseFileException("more than ONE surgical blocks found for specimen number="+specimen_number)
		   }
		   
		   // find coring record
		   def c2 = Coring.createCriteria()
		   ArrayList coringArr = c2 {
			   eq("surgical_block", surgical_blocksArr.getAt(0))
			   eq("coring_project", coring_project)
		   }
		   if (coringArr.size() == 0) {
			   throw new UploadRnaExtractionsParseFileException("Coring records NOT FOUND, specimennumber="+specimen_number+", coring project="+coring_project)
		   }
		   if (coringArr.size() > 1) {
			   throw new UploadRnaExtractionsParseFileException("more than ONE coring record found for specimen number="+specimen_number+", coring project="+coring_project)
		   }
		   
		   // ready to create rna_extraction object!!!
		   Rna_extractions rna_extraction = new Rna_extractions()
		   rna_extraction.setComment(comment)
		   rna_extraction.setRecord_date(record_date)
		   rna_extraction.setCoring(coringArr.getAt(0))
		   rna_extractionsArr.add(rna_extraction) // add rna_extraction to list
		   
		   // insert core image ...
		   log.info("inserting rna_extractions: surgical block name="+specimen_number+
			   " coring_project="+coring_project+
			   " comment="+comment+"\n"
		   )
		   line = br.readLine();
	   }
	   
	   // save rna_extractions to database
	   log.info("finished parsing file ... "+rna_extractionsArr.size()+" RNA extraction records specified.")
	   rna_extractionsArr.each {
		   log.info("saving ... RNA extraction record for "+it.getCoring().getSurgical_block())
		   if (!it.save(flush:true)){ // save rna_extraction to database ... no return !!!
			   String errMsg = "Grails/Hibernate error message while performing save operation:<br>"
			   it.errors.allErrors.each {
				   errMsg = errMsg + it + "\n"
			   }
			   throw new UploadRnaExtractionsSaveException("Save FAILED !!! for core ID="+it.getCoring().getSurgical_block()+"; database is now inconsistent!!! Clean up first!!!<br><br>"+errMsg)
		   }
	   }
	   log.info("finished saving "+rna_extractionsArr.size()+" RNA extraction records to database.")
   }

}
