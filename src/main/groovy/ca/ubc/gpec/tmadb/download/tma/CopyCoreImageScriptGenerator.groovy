/*
 * generate a script to copy selected core images
 */

package ca.ubc.gpec.tmadb.download.tma

import java.io.BufferedWriter;

import ca.ubc.gpec.tmadb.Staining_details;
import ca.ubc.gpec.tmadb.Users;

/**
 *
 * @author samuelc
 */
class CopyCoreImageScriptGenerator {
    String tma_project_id;
    String staining_details_id;
    String core_ids;
    Users user;
        
    public CopyCoreImageScriptGenerator(Users user) {
        this.user = user;
        // other data will be filled out via bindData(x,params)
    }
    
    public String getTma_project_id() {
        return tma_project_id;
    }
    
    public String getStaining_details_id() {
        return staining_details_id;
    }
    
    public String getCore_ids() {
        return core_ids;
    }
    
    /**
     * get a tree set of core id's 
     * - assume input core_ids is a comma-separated string
     **/
    private TreeSet<String> generateCoreIdSet(String separator) {
        String[] core_ids_arr = core_ids.split(separator);
        TreeSet<String> core_ids_set = new TreeSet<String>();
        core_ids_arr.each {
            core_ids_set.add(it.trim());
        }
        return core_ids_set;
    }
    
    /**
     * find core images and generate copy script
     */
    public void generateCopyScript(BufferedWriter bw, String server_root_path, String separator) throws CopyCoreImageScriptGeneratorException {        
        // 1. get the staining detail object
        Staining_details staining_details = Staining_details.get(staining_details_id, user);
        if (staining_details == null) {
            throw new CopyCoreImageScriptGeneratorException("Staining_details (id="+staining_details_id+") not found.");
        }
        // 2. iteratate through the tma_slices/cores to find the core image
        TreeSet<String> core_ids_set = generateCoreIdSet(separator);
        staining_details.tma_slices.each { tma_slice ->
            tma_slice.tma_core_images.each { tma_core_image ->
                if (core_ids_set.contains(tma_core_image.tma_core.core_id)) {
                    // core found!!!
                    String imagePath = server_root_path + tma_core_image.server_path + tma_core_image.resource_name;
                    //bw.writeLine("cp "+imagePath+" ."); 
                    bw.writeLine("cp "+imagePath+" "+tma_core_image.tma_core.core_id+".jpg"); // TODO: only works for singel core TMA and ASSUME jpg!!!!
                    core_ids_set.remove(tma_core_image.tma_core.core_id);
                }
            }
        }
        if (core_ids_set.size() != 0) {
            // not all core ids found!!!
            String errMsg = "core id(s) not found: ";
            core_ids_set.each {
                errMsg = errMsg+it+",";
            }
            throw new CopyCoreImageScriptGeneratorException(errMsg.substring(0,errMsg.length()-1));
        }
    }
}

