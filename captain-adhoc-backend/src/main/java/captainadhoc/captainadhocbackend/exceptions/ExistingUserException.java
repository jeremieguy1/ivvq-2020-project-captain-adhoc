package captainadhoc.captainadhocbackend.exceptions;

public class ExistingUserException extends RuntimeException {

    public ExistingUserException(String userName) {
        super("L'utilisateur " + userName + " existe déjà.");
    }
}
