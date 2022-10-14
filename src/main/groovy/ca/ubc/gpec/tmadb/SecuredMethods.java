package ca.ubc.gpec.tmadb;

/**
 * make sure domain class implement these methods for secure related functions
 *
 * @author samuelc
 *
 */
public interface SecuredMethods {

    /**
     * check to see if this is available to the user
     *
     * @param user
     * @return
     */
    public boolean isAvailable(Users user);
}
