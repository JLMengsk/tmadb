/*
 * to capture anything went wrong during generation of copy image script
 */
package ca.ubc.gpec.tmadb.download.tma;

/**
 *
 * @author samuelc
 */
public class CopyCoreImageScriptGeneratorException extends Exception {
    public CopyCoreImageScriptGeneratorException(String msg) {
        super(msg);
    }
    
}
