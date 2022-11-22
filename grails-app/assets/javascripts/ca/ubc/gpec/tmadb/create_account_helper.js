/* 
 * some function for create account
 */

/**
 * check user enter password ... make sure password1 and passord2 are the same
 * @param password1Id = id of text field
 * @param password2Id = id of text field
 */
function checkPassword(password1Id, password2Id) {
    if (document.getElementById(password1Id).value == document.getElementById(password2Id).value) {
        return true;
    } else {
        alert("ERROR: the two passwords entered are different.  Please try again.");
        return false;
    }
}


/**
 * make sure the field is not empty
 */
function checkNonEmptyField(fieldId, fieldNameToDisplay) {
    if (trim(document.getElementById(fieldId).value).length > 0) {
        return true;
    } else {
        alert("ERROR: " + fieldNameToDisplay + " cannot be empty.  Please try again.");
        return false;
    }
}

/**
 * check fields for change passwords ...
 * @param oldPasswordId = id of text field for old password
 * @param password1Id = id of text field for new password
 * @param password2Id = id of text field for new password check
 */
function checkFieldsForChangePassword(oldPasswordId, password1Id, password2Id) {
    return checkPassword(password1Id, password2Id) &&
            checkNonEmptyField(oldPasswordId, 'old password') &&
            checkNonEmptyField(password1Id, 'new password') &&
            checkNonEmptyField(password2Id, 'new password');
}

/**
 * check fields for create passwords ...
 * @param password1Id = id of text field for new password
 * @param password2Id = id of text field for new password check
 * @param first_nameId = id of text field for first name
 * @param last_nameId = id for text field for last name
 * @param emailId = id for text field for email 
 */
function checkFieldsForCreatePassword(password1Id, password2Id, nameId, emailId) {
    return checkPassword(password1Id, password2Id) &&
            checkNonEmptyField(nameId, 'name') &&
            checkNonEmptyField(emailId, 'email') &&
            checkNonEmptyField(password1Id, 'password') &&
            checkNonEmptyField(password2Id, 'password');
}