package ca.ubc.gpec.tmadb.scoreTma

import java.util.List;

import org.apache.commons.logging.Log;

import ca.ubc.gpec.tmadb.Tma_projects;
import ca.ubc.gpec.tmadb.Biomarkers;
import ca.ubc.gpec.tmadb.Tma_arrays;
import ca.ubc.gpec.tmadb.Tma_blocks;
import ca.ubc.gpec.tmadb.Tma_slices;
import ca.ubc.gpec.tmadb.Staining_details;
import ca.ubc.gpec.tmadb.Tma_cores;
import ca.ubc.gpec.tmadb.Tma_core_images;
import ca.ubc.gpec.tmadb.Scoring_sessions;
import ca.ubc.gpec.tmadb.Scorings
import ca.ubc.gpec.tmadb.Tma_scorings;

class UploadCoreImagesForScoring {
	// some constants
	static final String HNAME_CORE_ID = "core_id"
	
	// instance variables
	Log log
	byte[] myFile;
	int tma_project_id;
	int biomarker_id;
	int scoring_session_id;
	List<Tma_scorings> tma_scoringsArr;
		
	/**
	 * constructor
	 * @param log
	 */
	public UploadCoreImagesForScoring(Log log) {
		this.log = log
	}
	
	/**
	 * find item in inputArr and return index (starts with 0)
	 * - returns -1 if not found
	 * @param item
	 * @param input
	 * @return
	 */
	private int find(String item, String[] inputArr) throws UploadCoreImagesForScoringParseFileException {
		int index=0;
		while(!item.trim().equalsIgnoreCase(inputArr[index].trim())) {
			index++;
			if (index == inputArr.length) {
				throw new UploadCoreImagesForScoringParseFileException("column: "+item+", not found in input file!!!")
			}
		}
		return(index)
	}
	 
	public void parseFile(String sep) throws UploadCoreImagesForScoringParseFileException, UploadCoreImagesForScoringSaveException {
		// get some objects before reading/parsing the file
		Tma_projects tma_project = Tma_projects.get(tma_project_id);
		Biomarkers biomarker = Biomarkers.get(biomarker_id);
		Scoring_sessions scoring_session = Scoring_sessions.get(scoring_session_id);
		
		// read/parse input file
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(myFile)))
		
		tma_scoringsArr = new ArrayList<Tma_scorings>()
				  
		String line = br.readLine();
		if (line == null) {
			throw new UploadCoreImagesForScoringParseFileException("file is empty!!!")
		}
		// skip blank lines ...
		while (line.trim().length()==0) {
			line = br.readLine();
			if (line == null) {
				// end of file reached but no non-blank lines!!!
				throw new UploadCoreImagesForScoringParseFileException("file is empty!!!")
			}
		}
		
		// this is the header line ... look for the header columns
		String[] headerLineArr = line.split(sep)
		int indexCoreId        = find(HNAME_CORE_ID, headerLineArr);
		
		line = br.readLine();
		while(line != null) {
			String[] lineArr = line.split(sep)
		
			String core_id = lineArr[indexCoreId]
 
			// find tma_core_image in database ...
			Tma_core_images tma_core_image = Tma_core_images.withCriteria {
				and {
					'in'("tma_core", Tma_cores.withCriteria {
						and {
							'in'("tma_block", Tma_blocks.withCriteria {
								'in'("tma_array", Tma_arrays.findAllByTma_project(tma_project))
							})
							eq("core_id", core_id)
						}
					})
					'in'("tma_slice", Tma_slices.withCriteria {
						'in'("staining_detail",Staining_details.findAllByBiomarker(biomarker))
					})
				}
			}.iterator().next();
			
			// ready to create tma_scoring object!!!
			Tma_scorings tma_scoring = new Tma_scorings();
			tma_scoring.setScoring_session(scoring_session);
			tma_scoring.setTma_core_image(tma_core_image);
                        tma_scoring.setScoring(new Scorings());
			
			tma_scoringsArr.add(tma_scoring) // add tma_scoring to list
			
			// insert core image ...
			log.info("inserting tma_scoring: core_id="+core_id+
				" tma_core_image name="+tma_core_image.name+
				" project name="+tma_project.name+
				" biomarker name="+biomarker.name+"\n"
			)
			line = br.readLine();
		}
		
		// save tma_scorings to database
		log.info("finished parsing file ... "+tma_scoringsArr.size()+" tma_scoring objects specified.")
		tma_scoringsArr.each {
			log.info("saving ... "+it.getTma_core_image().name)
			if (!it.getScoring().save(flush:true) | !it.save(flush:true)){ // save tma_scorings to database ... no return !!!
				String errMsg = "Grails/Hibernate error message while performing save operation:<br>"
				it.errors.allErrors.each {
					errMsg = errMsg + it + "\n"
				}
				throw new UploadCoreImagesForScoringSaveException("Save FAILED !!! for core ID="+it.getTma_core_image().name+"; database is now inconsistent!!! Clean up first!!!<br><br>"+errMsg)
			}
		}
		log.info("finished saving "+tma_scoringsArr.size()+" images to database.")
	}
	
}
