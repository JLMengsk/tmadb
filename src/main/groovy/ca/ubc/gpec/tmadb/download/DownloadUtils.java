package ca.ubc.gpec.tmadb.download;

/**
 * some helper functions, constants related to download data
 *
 * @author samuelc
 *
 */
public class DownloadUtils {

    /////// SOME CONSTANTS /////////////////////////////
    public static final String TABLE_FORMAT_ONE_ROW_PER_CORE_IMAGE = "one row per core image";
    public static final String TABLE_FORMAT_ONE_ROW_PER_CORE = "one row per core";
    public static final String TABLE_FORMAT_ONE_ROW_PER_SURGICAL_BLOCK = "one row per surgical block";
    public static final String TABLE_FORMAT_ONE_ROW_PER_PATIENT = "one row per patient";
    public static final String TABLE_FORMAT_ONE_NUCLEI_SELECTION_PER_ROW = "one nuclei selection per row";
    public static final String[] TABLE_FORMATS = {
        TABLE_FORMAT_ONE_ROW_PER_CORE_IMAGE,
        TABLE_FORMAT_ONE_ROW_PER_CORE,
        TABLE_FORMAT_ONE_ROW_PER_SURGICAL_BLOCK,
        TABLE_FORMAT_ONE_ROW_PER_PATIENT,
        TABLE_FORMAT_ONE_NUCLEI_SELECTION_PER_ROW};
    public static final String FILE_FORMAT_PLAIN_TEXT = "text/plain";
    public static final String FILE_FORMAT_TEXT_TAB = "text/tab-delimited";
    public static final String FILE_FORMAT_TEXT_CSV = "text/csv";
    public static final String FILE_FORMAT_EXCEL_XLS = "Excel/xls";
    public static final String FILE_FORMAT_EXCEL_XLSX = "Excel/xlsx";
    public static final String[] FILE_FORMATS = {
        FILE_FORMAT_TEXT_TAB,
        FILE_FORMAT_TEXT_CSV,
        FILE_FORMAT_EXCEL_XLS,
        FILE_FORMAT_EXCEL_XLSX};
    public static final String DELIMITER_TAB = "\t";
    public static final String DELIMITER_COMMA = ",";
    /////// END OF CONTSTANTS //////////////////////////

    /**
     * get the appropriate http response contentType given the outputFileFormat
     *
     * @param outputFileFormat
     * @return
     */
    public static String getOuputFileContentType(String outputFileFormat) {
        String contentType = "";
        if (outputFileFormat.equals(FILE_FORMAT_TEXT_TAB)) {
            contentType = "text/plain";
        } else if (outputFileFormat.equals(FILE_FORMAT_TEXT_CSV)) {
            contentType = "text/csv";
        }
        return contentType;
    }

    public static String getOutputFileExtension(String outputFileFormat) {
        String extension = "";
        if (outputFileFormat.equals(FILE_FORMAT_TEXT_TAB)
                | outputFileFormat.equals(FILE_FORMAT_TEXT_CSV)) {
            extension = "txt";
        }
        return extension;
    }
}
