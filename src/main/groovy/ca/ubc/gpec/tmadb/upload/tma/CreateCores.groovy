package ca.ubc.gpec.tmadb.upload.tma

import java.util.List;

import org.apache.commons.logging.Log;

import ca.ubc.gpec.tmadb.Patient_sources;
import ca.ubc.gpec.tmadb.Patients;
import ca.ubc.gpec.tmadb.Paraffin_blocks;
import ca.ubc.gpec.tmadb.Surgical_blocks;
import ca.ubc.gpec.tmadb.Tma_blocks;
import ca.ubc.gpec.tmadb.Tma_core_images;
import ca.ubc.gpec.tmadb.Tma_cores;

class CreateCores {

	// some constants
	static final String HNAME_CORE_ID = "core_id"
	static final String HNAME_DESCRIPTION = "description"
	static final String HNAME_ROW = "row"
	static final String HNAME_COL = "col"
	static final String HNAME_SPECIMEN_NUMBER = "specimen_number"
	
	// instance variables
	Log log
	int tma_array_id;
	int tma_block_id;
	int patient_source_id;
	byte[] myFile;
	List<Tma_cores> tma_coresArr;
	float diameter;
		
	/**
	 * constructor
	 * @param log
	 */
	public CreateCores(Log log) {
		this.log = log
	}
	
	/**
	 * find item in inputArr and return index (starts with 0)
	 * - returns -1 if not found
	 * @param item
	 * @param input
	 * @return
	 */
	private int find(String item, String[] inputArr) throws CreateCoresParseFileException {
		int index=0;
		while(!item.trim().equalsIgnoreCase(inputArr[index].trim())) {
			index++;
			if (index == inputArr.length) {
				throw new CreateCoresParseFileException("column: "+item+", not found in input file!!!")
			}
		}
		return(index)
	}
	 
	public void parseFile(String sep) throws CreateCoresParseFileException, CreateCoresSaveException {
		// get some objects before reading/parsing the file
		Tma_blocks tma_block = Tma_blocks.get(tma_block_id)
		Patient_sources patient_source = Patient_sources.get(patient_source_id)
		
		// read/parse input file
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(myFile)))
		
		tma_coresArr = new ArrayList<Tma_cores>()
				  
		String line = br.readLine();
		if (line == null) {
			throw new CreateCoresParseFileException("file is empty!!!")
		}
		// skip blank lines ...
		while (line.trim().length()==0) {
			line = br.readLine();
			if (line == null) {
				// end of file reached but no non-blank lines!!!
				throw new CreateCoresParseFileException("file is empty!!!")
			}
		}
		
		// this is the header line ... look for the header columns
		String[] headerLineArr = line.split(sep)
		int indexCoreId         = find(HNAME_CORE_ID,         headerLineArr);
		int indexDescription    = find(HNAME_DESCRIPTION,     headerLineArr);
		int indexRow            = find(HNAME_ROW,             headerLineArr);
		int indexCol            = find(HNAME_COL,             headerLineArr);
		int indexSpecimenNumber = find(HNAME_SPECIMEN_NUMBER, headerLineArr);

		line = br.readLine();
		while(line != null) {
			String[] lineArr = line.split(sep)
		
			String core_id = lineArr[indexCoreId]
			String description = lineArr[indexDescription]
			int row = -1
			int col = -1
			try {
				row = Integer.parseInt(lineArr[indexRow])
				col = Integer.parseInt(lineArr[indexCol])
			} catch (NumberFormatException nfe) {
				throw new CreateCoresParseFileException("invalid row/col number: row="+line[indexRow]+", col="+line[indexCol])
			}
			String specimen_number = lineArr[indexSpecimenNumber]
			 
			// find core id in database ...
			def c = Tma_cores.createCriteria()
			ArrayList test_tma_coresArr = c {
				eq("tma_block",tma_block)
				eq("row", row)
				eq("col", col)
			}
			
			// check to see if core already exist
			if (test_tma_coresArr.size() != 0) {
				throw new CreateCoresParseFileException("TMA core record ALREADY EXIST for tma_block_id="+tma_block_id+" row="+row+" col="+col)
			}
			
			// find surgical block - assume is unique now :(
			def c2 = Paraffin_blocks.createCriteria()
			ArrayList paraffin_blocksArr = c2 {
				eq("specimen_number", specimen_number)
				'in'("patient", Patients.findAllByPatient_source(patient_source))
			}
			if (paraffin_blocksArr.size() == 0) {
				throw new CreateCoresParseFileException("Surgical Block NOT FOUND, specimennumber="+specimen_number)
			}			
			if (paraffin_blocksArr.size() > 1) {
				throw new CreateCoresParseFileException("more than ONE surgical block found for specimen number="+specimen_number)
			}
			
			
			// ready to create tma_core_image!!!
			Tma_cores tma_core = new Tma_cores()
			tma_core.setCore_id(core_id)
			tma_core.setDescription(description)
			tma_core.setRow(row)
			tma_core.setCol(col)
			tma_core.setTma_block(tma_block)
			tma_core.setSurgical_block(paraffin_blocksArr.get(0).getSurgical_block())
			tma_core.setDiameter(diameter);
			tma_coresArr.add(tma_core) // add core to list
			
			// insert core image ...
			log.info("inserting tma_core: core_id="+core_id+
				" description="+description+
				" row="+row+
				" col="+col+
				" paraffin block name="+specimen_number+"\n"
			)
			line = br.readLine();
		}
		
		// save tma_core_images to database
		log.info("finished parsing file ... "+tma_coresArr.size()+" images specified.")
		tma_coresArr.each {
			log.info("saving ... "+it.getCore_id())
			if (!it.save(flush:true)){ // save tma_core to database ... no return !!!
				String errMsg = "Grails/Hibernate error message while performing save operation:<br>"
				it.errors.allErrors.each {
					errMsg = errMsg + it + "\n"
				}
				throw new CreateCoresSaveException("Save FAILED !!! for core ID="+it.getCore_id()+"; database is now inconsistent!!! Clean up first!!!<br><br>"+errMsg)
			}
		}
		log.info("finished saving "+tma_coresArr.size()+" images to database.")
	} 
	
}
