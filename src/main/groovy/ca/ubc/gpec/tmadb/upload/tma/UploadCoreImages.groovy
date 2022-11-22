/**
 * upload core images
 */
package ca.ubc.gpec.tmadb.upload.tma

import java.text.*
import org.apache.commons.logging.Log

import ca.ubc.gpec.tmadb.Tma_blocks
import ca.ubc.gpec.tmadb.Tma_slices
import ca.ubc.gpec.tmadb.Tma_cores
import ca.ubc.gpec.tmadb.Tma_core_images
import ca.ubc.gpec.tmadb.Image_servers
import ca.ubc.gpec.tmadb.Scanner_infos

class UploadCoreImages {
	
	// some constants
	static final String HNAME_NAME = "name"
	static final String HNAME_DESCRIPTION = "description"
	static final String HNAME_ROW = "row"
	static final String HNAME_COL = "col"
	static final String HNAME_SERVER_PATH = "server_path"
	static final String HNAME_RESOURCE_NAME = "resource_name"
	
	// instance variables
	Log log
	int tma_array_id;
	int tma_block_id;
	int tma_slice_id;
	int image_server_id;
	int scanner_info_id;
	String scanning_date_year;
	String scanning_date_month;
	String scanning_date_day;
	byte[] myFile;
	List<Tma_core_images> tma_core_imagesArr;
	
	/**
	 * constructor
	 * @param log
	 */
	public UploadCoreImages(Log log) {
		this.log = log
	}
	
	/**
	 * find item in inputArr and return index (starts with 0)
	 * - returns -1 if not found
	 * @param item
	 * @param input
	 * @return
	 */
	private int find(String item, String[] inputArr) throws UploadCoreImagesParseFileException {
		int index=0;
		while(!item.trim().equalsIgnoreCase(inputArr[index].trim())) {
			index++;
			if (index == inputArr.length) {
				throw new UploadCoreImagesParseFileException("column: "+item+", not found in input file!!!")
			}
		}
		return(index)
	} 
	 
	public void parseFile(String sep) throws UploadCoreImagesParseFileException, UploadCoreImagesSaveException {
		// get some objects before reading/parsing the file
		Tma_blocks tma_block = Tma_blocks.get(tma_block_id)
		Tma_slices tma_slice = Tma_slices.get(tma_slice_id)
		Scanner_infos scanner_info = Scanner_infos.get(scanner_info_id)
		Image_servers image_server = Image_servers.get(image_server_id)
		Date scanning_date = new SimpleDateFormat("yyyy-MM-dd").parse(scanning_date_year+"-"+scanning_date_month+"-"+scanning_date_day)
		
		// read/parse input file
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(myFile)))
		
		tma_core_imagesArr = new ArrayList<Tma_core_images>()
		 		 
		String line = br.readLine();
		if (line == null) {
			throw new UploadCoreImagesParseFileException("file is empty!!!")
		}
		// skip blank lines ...
		while (line.trim().length()==0) {
			line = br.readLine();
			if (line == null) {
				// end of file reached but no non-blank lines!!!
				throw new UploadCoreImagesParseFileException("file is empty!!!")
			}
		}
		
		// this is the header line ... look for the header columns
		String[] headerLineArr = line.split(sep)
		int indexName          = find(HNAME_NAME,          headerLineArr);
		int indexDescription   = find(HNAME_DESCRIPTION,   headerLineArr);
		int indexRow           = find(HNAME_ROW,           headerLineArr);
		int indexCol           = find(HNAME_COL,           headerLineArr);
		int indexServer_path   = find(HNAME_SERVER_PATH,   headerLineArr);
		int indexResource_name = find(HNAME_RESOURCE_NAME, headerLineArr);
		
		line = br.readLine();
		while(line != null) {
			String[] lineArr = line.split(sep)
		
			String name = lineArr[indexName]
			String description = lineArr[indexDescription]
			int row = -1
			int col = -1
			try {
				row = Integer.parseInt(lineArr[indexRow])
				col = Integer.parseInt(lineArr[indexCol])
			} catch (NumberFormatException nfe) {
				throw new UploadCoreImagesParseFileException("invalid row/col number: row="+line[indexRow]+", col="+line[indexCol]+" for image name="+name)
			}
			
			String server_path = lineArr[indexServer_path]
			String resource_name = lineArr[indexResource_name]
			 
			// find core id in database ...			
			def c = Tma_cores.createCriteria()
			ArrayList tma_coreArr = c {
				eq("tma_block",tma_block)
				eq("row", row)
				eq("col", col)
			}
			
			// not really possible to have more than one core for tma_block/row/col ... would be database inconsistency ...
			if (tma_coreArr.size() > 1) {
				throw new UploadCoreImagesParseFileException("more than one TMA cores found for tma_block_id="+tma_block_id+" row="+row+" col="+col)
			}
			if (tma_coreArr.size() == 0) {
				throw new UploadCoreImagesParseFileException("TMA core record NOT found for tma_block_id="+tma_block_id+" row="+row+" col="+col)
			}
			
			def tma_core = tma_coreArr.get(0)
			
			// ready to create tma_core_image!!!
			Tma_core_images tma_core_image = new Tma_core_images()
			tma_core_image.setName(name)
			tma_core_image.setDescription(description)
			tma_core_image.setTma_core(tma_core)
			tma_core_image.setTma_slice(tma_slice)
			tma_core_image.setScanner_info(scanner_info)
			tma_core_image.setScanning_date(scanning_date)
			tma_core_image.setServer_path(server_path)
			tma_core_image.setResource_name(resource_name)
			tma_core_image.setImage_server(image_server)
			tma_core_imagesArr.add(tma_core_image) // add core image to list
			
			// insert core image ...
			log.info("inserting tma_core_image: name="+name+
				" description="+description+
				" row="+row+
				" col="+col+
				" scanning_date="+scanning_date+
				" server_path="+server_path+
				" resource_name="+resource_name+
				" tma_core_id="+tma_core.getId()+"\n"
			)
					 
			line = br.readLine();
		}
		
		// save tma_core_images to database
		log.info("finished parsing file ... "+tma_core_imagesArr.size()+" images specified.")
		tma_core_imagesArr.each {
			log.info("saving ... "+it.getName())
			if (!it.save(flush:true)){ // save tma_core_image to database ... no return !!!
				String errMsg = "Grails/Hibernate error message while performing save operation:<br>"
				it.errors.allErrors.each {
					errMsg = errMsg + it + "\n"
				}
				throw new UploadCoreImagesSaveException("Save FAILED !!! for "+it.getName()+"; database is now inconsistent!!! Clean up first!!!<br><br>"+errMsg)
			}
		}	 
		log.info("finished saving "+tma_core_imagesArr.size()+" images to database.")
	}
	 
}
