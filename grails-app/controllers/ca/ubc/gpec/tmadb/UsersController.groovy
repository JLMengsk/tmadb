package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcCalibrator.Scoring_sessionInitializer;

class UsersController {
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def simpleCaptchaService // for captcha checking
    
    def login_showPageBodyOnly = {
        if (MiscUtil.hiddenParamsExist(params)) {
            // must be failing to do something because of not enough privileges
            flash.message = "Access denied.  Please login or login as another user."
        }
        render(view: "login")
    }
	
    /**
     * see if user login/email/password matches record in database
     */
    def authenticate = {
        Users user = Users.findByLoginAndPassword(params.login, params.password);
        if (user == null) {
            // try email ... email is case insensitive
            def users = Users.withCriteria {
                and {
                    eq("email", params.login, [ignoreCase: true])
                    eq("password", params.password)
                }
            }
            if (!users.isEmpty()) {
                user = users.first();
            }
        }
        if(user){
            session.setAttribute("user",user);
            flash.message = "Hello ${user.name}!";
		
            // see login.gsp
            if (MiscUtil.hiddenParamsExist(params)) {
                Map currentParams = MiscUtil.unhideCurrentParams(params)
                redirect(base: grailsApplication.config.grails.serverSecureURL, controller:currentParams.get("controller"), action:currentParams.get("action"), params:currentParams)
            } else {
                String preferredHomeController = user.preferredHomeControllerName();
                if (preferredHomeController != null) {
                    redirect(base: grailsApplication.config.grails.serverSecureURL, controller:preferredHomeController); 
                } else {
                    redirect(uri: grailsApplication.config.grails.serverSecureURL);
                }
            }
        }else{
            flash.message = "Sorry, ${params.login}. Please try again."
            redirect(base: grailsApplication.config.grails.serverSecureURL, action:"login_"+ViewConstants.SHOW_BODY_ONLY_CONTROLLER_ACTION_SUFFIX, params:params)
        }
    }
	
    /**
     * log out
     */
    def logout = {
        boolean isPaper_reader = false;
        flash.message = "Goodbye"
        if (session.user) {
            flash.message = "Goodbye ${session.user.name}"
            Users user = Users.findByLogin(session.user.login);
            isPaper_reader = user.showIsPaper_reader();
        }
        session.user = null
        if (isPaper_reader) {
            // if paper reader ... go to http://www.gpecimage.ubc.ca 
            redirect(uri:"http://www.gpecimage.ubc.ca");
        } else {
            redirect(uri:grailsApplication.config.grails.serverURL); // logout ... out back to http
        }
    }
	
    //////////////////////////////////////////////////////////////
    // create tester accounts ...
    // 
    // show input form
    def create_account_showPageBodyOnly = {
        if (session.user != null) {
            // the user is currently signed in and he/she wanted to create an account!!!
            // sign out first!
            session.user = null;
        }
        render(view: "create_account", model: [])
    }
    // create account and show results of account creation
    def save_create_account = { 
        // check captcha 

        if (!simpleCaptchaService.validateCaptcha(params.captcha)) {
            // failed captcha!!! try again!!
            redirect(base: grailsApplication.config.grails.serverSecureURL, action: "create_account_showPageBodyOnly");
            return;
        }
            
        String inputEmail = (params[ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_EMAIL]).trim();
        
        // check if user exist already ... assume each user must have unique email
        if (Users.countByEmail(inputEmail) != 0) {
            flash.message = "ERROR: a user with email ("+inputEmail+") exists already.  Please email "+ViewConstants.HELP_EMAIL+" for assistence.";
            redirect(base: grailsApplication.config.grails.serverSecureURL, action: "create_account_showPageBodyOnly");
        } else {
            // can create account now ...
                
            // 1. generate user name by concatenating first letter of first name with last name
            //    - all letter to lower case
            //    - remove all white spaces
            //    e.g. Sam Leung => sleung
            //    if username already exist, add number ... e.g. sleung1, sleung2 ... etc
            // CHANGED 2013-07-04 ...
            // user name = email address!!!
            String inputName = params[ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME];
            //String generatedLoginBase = (inputFirst_name.substring(0,1)+inputLast_name).replaceAll("\\s","").toLowerCase();
            //String generatedLogin = generatedLoginBase;
            //int counter = 1;
            //while (Users.countByLogin(generatedLogin) != 0) {
            //    generatedLogin = generatedLoginBase+counter;
            //    counter++;
            //}
            String generatedLogin = inputEmail;
                
            String inputPassword = params[ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORD];
            String generatedDescription = inputName+" (user self-created account via web)";

            // create tma_user object
            Tma_scorers newTma_scorer = new Tma_scorers();
            newTma_scorer.setName(inputName);
            newTma_scorer.setDescription(generatedDescription);
            newTma_scorer.setHuman(1); // must be human since self-created account via website
            newTma_scorer.setInstitution(Institutions.getInternet());
            newTma_scorer.setCreated_date(Calendar.getInstance().getTime());
            newTma_scorer.save(flush:true); // save new tma_scorer object
            
            // create user object
            Users newUser = new Users();
            newUser.setName(inputName);
            newUser.setLogin(generatedLogin);
            newUser.setEmail(inputEmail);
            newUser.setPassword(inputPassword);
            newUser.setDescription(generatedDescription);
            newUser.setTma_scorer(newTma_scorer);
            newUser.setUser_role(User_roles.getScorer());
            newUser.setCreated_date(Calendar.getInstance().getTime());
                
            newUser.save(flush:true); // save new user object
            
            Scoring_sessionInitializer scoring_sessionInitializer = new Scoring_sessionInitializer(newTma_scorer);
            scoring_sessionInitializer.initializeSession(); // let the scoring_sessionInitializer decide what (if any) scoring session to initialize
            
            flash.message = "Account creation successful.  Your login name is your email: "+generatedLogin;
            session.user = newUser; // i.e. log the user in
            if (session.user.preferredHomeControllerName()!=null) {
                redirect(controller:session.user.preferredHomeControllerName(), action:'list'); // WARNING hardcode list for now!!! since all auto-created user will be for scoreTma list
            } else {
                redirect(url: grailsApplication.config.grails.serverSecureURL);
            }
        }
    }
    //
    // end of create tester accounts
    ///////////////////////////////////////////////////////////////
}
