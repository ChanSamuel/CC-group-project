package nz.ac.vuw.ecs.swen225.gp21.persistency;

/**
 * This class defines a PersistException which gets used by the persister to provide information
 * on what has gone wrong in attempting to persist a game
 *
 * @author Lucy Goodwin
 */
public class PersistException extends Exception {

    /**
     * Constructor for a PersistException. Only accessible in the Persistency package.
     *
     * @param message containing information about the exception to be shown to the user
     */
    protected PersistException(String message) {
        super(message);
    }
}
