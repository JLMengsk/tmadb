/*
 * constants related to views ...
 */
package ca.ubc.gpec.tmadb.util;

/**
 *
 * @author samuelc
 */
public class ViewConstants {

    public static final String HELP_EMAIL = "sam.leung@vch.ca";
    public static final String NEGLIGIBLE_TEXT = "negative";
    public static final String COLOR_CODE_DARK_BROWN = "#493D26";
    public static final String COLOR_CODE_PINK = "#F660AB";
    public static final String COLOR_CODE_WHITE = "#FFFFFF";
    public static final String HIGHLIGHT_COLOR = "#FFFF00";
    public static final int FONT_SIZE_MEDIUM = 16;
    public static final String NA = "NA";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static final String SHOW_BODY_ONLY_CONTROLLER_ACTION_SUFFIX = "showPageBodyOnly";
    public static final String SHOW_BODY_ONLY_PARAM_FLAG = "showPageBodyOnly";
    public static final String TMA_CORE_IMAGE_DETAILS_DIV_ID = "tma_core_image_details_div_id";
    public static final String TMA_CORE_IMAGE_DETAILS_TABLE_ID = "tma_core_image_details_table_id";
    public static final String TMA_CORE_IMAGE_DETAILS_SHOW_BUTTON_ID = "tma_core_image_details_show_button_id";
    public static final String TMA_CORE_IMAGE_DETAILS_SHOW_BUTTON_HINT_MSG = "clic me to show core image details";
    public static final String TMA_CORE_IMAGE_DETAILS_SHOW_BUTTON_TEXT_SHOW = "show core image details";
    public static final String TMA_CORE_IMAGE_DETAILS_SHOW_BUTTON_TEXT_HIDE = "hide core image details";
    public static final String SCORING_SESSION_SCORING_TYPE = "scoring_session_scoring_type";
    public static final String SCORING_SESSION_SCORING_TYPE_TMA_SCORING = "scoring_session_scoring_type_tma_scoring";
    public static final String SCORING_SESSION_SCORING_TYPE_WHOLE_SECTION_SCORING = "scoring_session_scoring_type_whole_section_scoring";
    public static final int GRID_WIDTH = 800;
    public static final int TMA_SLICES_GRID_WIDTH = GRID_WIDTH;
    public static final int TMA_SLICES_GRID_HEIGHT = 400;
    public static final int TMA_PROJECTS_GRID_WIDTH = TMA_SLICES_GRID_WIDTH;
    public static final int TMA_PROJECTS_GRID_HEIGHT = TMA_SLICES_GRID_HEIGHT;
    public static final int WHOLE_SECTION_SLICES_GRID_WIDTH = TMA_SLICES_GRID_WIDTH;
    public static final int WHOLE_SECTION_SLICES_GRID_HEIGHT = TMA_SLICES_GRID_HEIGHT;
    public static final int SCORING_SESSIONS_GRID_WIDTH = TMA_SLICES_GRID_WIDTH;
    public static final int SCORING_SESSIONS_GRID_HEIGHT = TMA_SLICES_GRID_HEIGHT;
    public static final int SCORING_SESSION_REPORT_GRID_WIDTH = SCORING_SESSIONS_GRID_WIDTH;
    public static final int SCORING_SESSION_REPORT_GRID_HEIGHT = SCORING_SESSIONS_GRID_HEIGHT;
    public static final String CSS_CLASS_NAME_ROW_ODD = "odd";
    public static final String CSS_CLASS_NAME_ROW_EVEN = "even";
    public static final String AJAX_RESPONSE_DELIMITER = "___";
    public static final String AJAX_RESPONSE_DELIMITER_2 = "_2_"; // secondary delimiter
    public static final String AJAX_RESPONSE_NA = "NA";
    public static final String AJAX_RESPONSE_ERROR = "ERROR";
    public static final String AJAX_RESPONSE_SAVE_SUCCESSFUL = "SAVE SUCCESSFUL";
    public static final String AJAX_RESPONSE_OK = "OK";
    //
    // HTML PARAM & FORM NAME ...
    public static final String HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID = "tma_scoring_id";
    public static final String HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID = "whole_section_scoring_id";
    public static final String HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_X = "whole_section_region_scoring_x";
    public static final String HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_Y = "whole_section_region_scoring_y";
    public static final String HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_DIAMETER = "whole_section_region_scoring_diameter";
    public static final String HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_KI67STATE = "whole_section_region_scoring_ki67state";
    public static final String HTML_FORM_NAME_KI67_QC_PHASE3_INIT = "html_form_name_ki67_qc_phase3_init";
    public static final String HTML_FORM_NAME_KI67_QC_PHASE3_SELECT_REGION = "html_form_name_ki67_qc_phase3_select_region";
    public static final String HTML_PARAM_NAME_KI67_QC_PHASE3_40X_FIELD_DIAMETER_MM = "html_param_name_ki67_qc_phase3_40x_field_diameter_mm";
    public static final String HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_NEGLIGIBLE = "html_param_name_ki67_qc_phase3_estimate_level_negligible";
    public static final String HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_LOW = "html_param_name_ki67_qc_phase3_estimate_level_low";
    public static final String HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_MEDIUM = "html_param_name_ki67_qc_phase3_estimate_level_medium";
    public static final String HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_HIGH = "html_param_name_ki67_qc_phase3_estimate_level_high";
    public static final String HTML_PARAM_NAME_KI67_QC_PHASE3_WHOLE_SECTION_SCORING_COMMENT = "html_param_name_ki67_qc_phase3_whole_section_scoring_comment";
    public static final String HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING = "html_param_name_ki67_qc_phase3_select_region_paramstring";
    public static final String HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME = "html_param_name_create_account_input_name";
    public static final String HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_EMAIL = "html_param_name_create_account_input_name_email";
    public static final String HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORD = "html_param_name_create_account_input_name_password";
    public static final String HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORDCHECK = "html_param_name_create_account_input_name_passwordcheck";
    public static final String HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME = "html_param_name_edit_biomarker_input_name";
    public static final String HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_DESCRIPTION = "html_param_name_edit_biomarker_input_name_description";
    public static final String HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_BIOMARKER_TYPE_ID = "html_param_name_edit_biomarker_input_name_biomarker_type_id";
    public static final String HTML_FORM_NAME_EDIT_SURG_BLOCKS="html_form_name_edit_surg_blocks";
    public static final String HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_SPECIMEN_NUMBER = "html_param_name_edit_surg_blocks_input_name_specimen_number";
    public static final String HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_ADDITIONAL_INFO="html_param_name_edit_surg_blocks_input_name_additional_info";
    public static final String HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_TISSUE_TYPE_ID="html_param_name_edit_surg_blocks_input_name_tissue_type_id";
    public static final String HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_COMMENT="html_param_name_edit_surg_blocks_input_name_comment";
    public static final String HTML_PARAM_NAME_EDIT_BX_BLOCKS_INPUT_NAME_NUM_CORES="html_param_name_edit_bx_blocks_input_name_num_cores";    
    //
    public static final String HTML_PARAM_NAME_TMA_PROJECT_ID = "tma_project_id";
    public static final String HTML_PARAM_NAME_TMA_ARRAY_ID = "tma_array_id";
    //
    // HTML ELEMENT ID/NAMES
    public static final String JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR = "fieldSelector";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST = "html_element_id_ki67_qc_phase3_select_region_button_highest";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH = "html_element_id_ki67_qc_phase3_select_region_button_high";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM = "html_element_id_ki67_qc_phase3_select_region_button_medium";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW = "html_element_id_ki67_qc_phase3_select_region_button_low";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE = "html_element_id_ki67_qc_phase3_select_region_button_negligible";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DELETE = "html_element_id_ki67_qc_phase3_select_region_button_delete";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGHEST = "html_element_id_ki67_qc_phase3_select_region_button_desc_highest";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGH = "html_element_id_ki67_qc_phase3_select_region_button_desc_high";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_MEDIUM = "html_element_id_ki67_qc_phase3_select_region_button_desc_medium";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_LOW = "html_element_id_ki67_qc_phase3_select_region_button_desc_low";
    public static final String HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_NEGLIGIBLE = "html_element_id_ki67_qc_phase3_select_region_button_desc_negligible";
    public static final String HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV = "nucleiCounterAppletId";
    //
    //////////////////////////////////////////
    // placeHolder / tooltip messages
    // 
    // placeHolder / tooltip messages related to Ki67-QC phase 3 ...
    public static final String MSG_PLACEHOLDER_KI67_QC_PHASE3_PERCENTAGE = "percentage (0-100)";
    public static final String MSG_TOOLTIP_KI67_QC_PHASE3_PLEASE_ENTER_0_FOR_MISSING = "please enter 0 for absence of";
    public static final String MSG_INVALID_KI67_QC_PHASE3_PERCENTAGE = "please enter " + MSG_PLACEHOLDER_KI67_QC_PHASE3_PERCENTAGE;
    public static final String MSG_MISSING_KI67_QC_PHASE3_PERCENTAGE = "field required; " + MSG_TOOLTIP_KI67_QC_PHASE3_PLEASE_ENTER_0_FOR_MISSING;
    //
    //
    // end of placeHolder / tooltip messages
    //////////////////////////////////////////
    //
    //////////////////////////////////////////
    // error messages 
    // 
    // error messages related to user e.g. login
    public static final String MSG_PERMISSION_DENIED_PLEASE_SIGN_IN_AS_ANOTHER_USER = "Access denied.  Please sign in or sign in as another user.";
    public static final String UNKNOWN_SERVER_ERROR_MESSAGE = "Unknown server error occurred. Please refresh the page. We apologize for any inconvenience.";
    //
    // error messages related to Ki67-QC phase 3 ...
    //
    // 
    // misc error message
    public static final String ERROR_MSG_DB_SAVE_FAILED_PLS_CONTACT_SUPPORT = "A database error has occurred.  Please contact Samuel Leung (sam.leung@vch.ca) before continuing your session.  We apologize for the inconvenience.";
    //
    // end of all error messages /////////////
    //////////////////////////////////////////
    //
    // other misc message
    public static final String MSG_BROWSER_RECOMMENDATION = "If possible, please use <a href='http://www.mozilla.org/' target='_blank'>Mozilla/Firefox</a>.";
    // end of other misc message
    //////////////////////////////////////////
    //
    // other misc
    public static final String KI67_QC_PHASE3_FAQ_LINK = "docs/tmadb/faqs/ki67-qc3/";
    public static final String KI67_QC_CALIBRATOR_FAQ_LINK = "docs/tmadb/faqs/calibrator/";
    public static final String KI67_QC_CALIBRATOR_METHOD_LINK = "docs/tmadb/faqs/calibrator/nuclei_count_method.pdf";
    // end of other misc
    //////////////////////////////////////////
}
