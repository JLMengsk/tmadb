package ca.ubc.gpec.tmadb.upload.rnaExtractions
/**
 * upload RNA yield records
 */

import java.util.Date;
import java.util.List;
import java.text.*

import org.apache.commons.logging.Log;

import ca.ubc.gpec.tmadb.Rna_extractions;
import ca.ubc.gpec.tmadb.Rna_yields;
import ca.ubc.gpec.tmadb.Coring_projects;
import ca.ubc.gpec.tmadb.Coring;
import ca.ubc.gpec.tmadb.Patient_sources;
import ca.ubc.gpec.tmadb.Patients;
import ca.ubc.gpec.tmadb.Surgical_blocks;

class UploadRnaYields {
	// some constants
	static final String HNAME_SPECIMEN_NUMBER = "specimen_number"
	static final String HNAME_CONCENTRATION_UG_PER_UL = "concentration_ug_per_ul"
	static final String HNAME_YIELD_UG = "yield_ug"
	static final String HNAME_COMMENT = "yield_comment"

	// instance variables
	Log log
	int patient_source_id;
	int coring_project_id;
	String record_date_year;
	String record_date_month;
	String record_date_day;
	String yield_record_date_year;
	String yield_record_date_month;
	String yield_record_date_day;
	String source_description
	byte[] myFile;
	List<Rna_yields> rna_yieldsArr;

	/**
	 * constructor
	 * @param log
	 */
	public UploadRnaYields(Log log) {
		this.log = log
	}

	/**
	 * find item in inputArr and return index (starts with 0)
	 * - returns -1 if not found
	 * @param item
	 * @param input
	 * @return
	 */
	private int find(String item, String[] inputArr) throws UploadRnaYieldsParseFileException {
		int index=0;
		while(!item.trim().equalsIgnoreCase(inputArr[index].trim())) {
			index++;
			if (index == inputArr.length) {
				throw new UploadRnaYieldsParseFileException("column: "+item+", not found in input file!!!")
			}
		}
		return(index)
	}

	public void parseFile(String sep) throws UploadRnaYieldsParseFileException, UploadRnaYieldsSaveException {
		// get some objects before reading/parsing the file
		Patient_sources patient_source = Patient_sources.get(patient_source_id)
		Coring_projects coring_project = Coring_projects.get(coring_project_id)
		Date record_date  = new SimpleDateFormat("yyyy-MM-dd").parse(record_date_year+ "-"+record_date_month+ "-"+record_date_day)
		Date yield_record_date = new SimpleDateFormat("yyyy-MM-dd").parse(yield_record_date_year+ "-"+yield_record_date_month+ "-"+yield_record_date_day)
		// read/parse input file
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(myFile)))

		rna_yieldsArr = new ArrayList<Rna_yields>()

		String line = br.readLine();
		if (line == null) {
			throw new UploadRnaYieldsParseFileException("file is empty!!!")
		}
		// skip blank lines ...
		while (line.trim().length()==0) {
			line = br.readLine();
			if (line == null) {
				// end of file reached but no non-blank lines!!!
				throw new UploadRnaYieldsParseFileException("file is empty!!!")
			}
		}

		// this is the header line ... look for the header columns
		String[] headerLineArr = line.split(sep)
		int indexSpecimenNumber       = find(HNAME_SPECIMEN_NUMBER,         headerLineArr);
		int indexConcentrationUgPerUl = find(HNAME_CONCENTRATION_UG_PER_UL, headerLineArr);
		int indexYieldUg              = find(HNAME_YIELD_UG,                headerLineArr);
		int indexComment              = find(HNAME_COMMENT,                 headerLineArr);

		line = br.readLine();
		while(line != null) {
			String[] lineArr = line.split(sep)

			String specimen_number = lineArr[indexSpecimenNumber]
			String comment = lineArr[indexComment]
			Float concentrationUgPerUl = null;
			Float yieldUg = null;
			// NOTE: use empty concentration/yield to indicate missing value
			if (lineArr[indexConcentrationUgPerUl].trim().length() > 0) {
				try {
					concentrationUgPerUl = Float.parseFloat(lineArr[indexConcentrationUgPerUl])
					yieldUg = Float.parseFloat(lineArr[indexYieldUg])
				} catch (NumberFormatException nfe) {
					throw new UploadRnaYieldsParseFileException("Invalid number format: concentration="+lineArr[indexConcentrationUgPerUl]+"; yield="+lineArr[indexYieldUg])
				}
			}

			// make sure surgical block exists and is unique
			def c = Surgical_blocks.createCriteria()
			ArrayList surgical_blocksArr = c {
				eq("specimen_number", specimen_number)
				'in'("patient", Patients.findAllByPatient_source(patient_source))
			}
			if (surgical_blocksArr.size() == 0) {
				throw new UploadRnaYieldsParseFileException("Surgical Block NOT FOUND, specimennumber="+specimen_number)
			}
			if (surgical_blocksArr.size() > 1) {
				throw new UploadRnaYieldsParseFileException("more than ONE surgical blocks found for specimen number="+specimen_number)
			}

			// make sure coring record exists and is unique
			def c2 = Coring.createCriteria()
			ArrayList coringArr = c2 {
				eq("surgical_block", surgical_blocksArr.getAt(0))
				eq("coring_project", coring_project)
			}
			if (coringArr.size() == 0) {
				throw new UploadRnaYieldsParseFileException("Coring record NOT FOUND, specimennumber="+specimen_number+", coring project="+coring_project)
			}
			if (coringArr.size() > 1) {
				throw new UploadRnaYieldsParseFileException("more than ONE coring records found for specimen number="+specimen_number+", coring project="+coring_project)
			}

			// find RNA extraction record
			def c3 = Rna_extractions.createCriteria()
			ArrayList rna_extractionsArr = c3 {
				eq("coring", coringArr.getAt(0))
				eq("record_date", record_date)
			}
			if (rna_extractionsArr.size() == 0) {
				throw new UploadRnaYieldsParseFileException("RNA extraction record NOT FOUND, specimennumber="+specimen_number+", coring project="+coring_project+", record date="+record_date)
			}
			if (rna_extractionsArr.size() > 1) {
				throw new UploadRnaYieldsParseFileException("more than ONE RNA extraction records found for specimennumber="+specimen_number+", coring project="+coring_project+", record date="+record_date)
			}

			// ready to create rna_yield object!!!
			Rna_yields rna_yield = new Rna_yields()
			rna_yield.setConcentration_ug_per_ul(concentrationUgPerUl)
			rna_yield.setYield_ug(yieldUg)
			rna_yield.setRecord_date(yield_record_date)
			rna_yield.setRna_extraction(rna_extractionsArr.getAt(0))
			rna_yield.setComment(comment)
			rna_yield.setSource_description(source_description)
			rna_yieldsArr.add(rna_yield) // add rna_yield to list

			// insert core image ...
			log.info("inserting rna_yield: surgical block name="+specimen_number+
					" coring_project="+coring_project+
					" concentration="+concentrationUgPerUl+
					" yield="+yieldUg+
					" source_description="+source_description+"\n"
					)
			line = br.readLine();
		}

		// save rna_yields to database
		log.info("finished parsing file ... "+rna_yieldsArr.size()+" RNA yield records specified.")
		rna_yieldsArr.each {
			log.info("saving ... RNA yield record for "+it.getRna_extraction().getCoring().getSurgical_block())
			if (!it.save(flush:true)){ // save rna_yield to database ... no return !!!
			   String errMsg = "Grails/Hibernate error message while performing save operation:<br>"
			   it.errors.allErrors.each {
				   errMsg = errMsg + it + "\n"
			   }
			   throw new UploadRnaYieldsSaveException("Save FAILED !!! for core ID="+it.getRna_extraction().getCoring().getSurgical_block()+"; database is now inconsistent!!! Clean up first!!!<br><br>"+errMsg)
			}
		}
		log.info("finished saving "+rna_yieldsArr.size()+" RNA yields records to database.")
	}

}
