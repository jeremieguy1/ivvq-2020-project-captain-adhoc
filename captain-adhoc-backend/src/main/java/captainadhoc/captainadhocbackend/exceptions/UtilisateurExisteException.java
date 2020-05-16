package captainadhoc.captainadhocbackend.exceptions;

public class UtilisateurExisteException extends RuntimeException{

    public UtilisateurExisteException(String nomUtilisateur){
        super("L'utilisateur " + nomUtilisateur + " existe déjà.");
    }
}
