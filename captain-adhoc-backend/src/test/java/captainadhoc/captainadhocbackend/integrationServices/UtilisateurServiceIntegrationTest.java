package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.services.interfaces.IUtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UtilisateurServiceIntegrationTest {

    @Autowired
    IUtilisateurService utilisateurService;
    private Utilisateur utilisateur;


    @BeforeEach
    public void setupEach() {
        Utilisateur utilisateur = new Utilisateur("Kevin", "Marchand", "marchand1", "mdp", true);
        utilisateurService.saveUtilisateur(utilisateur);
    }

    @Test
    public void testSaveUtilisateur() {
        // given: un Uilisateur non persisté
        Utilisateur util = new Utilisateur("Test", "Utilisateur", "UtilisateurTest", "mdp", true);
        assertNull(util.getId());
        // when: l'utilisateur est persisté
        utilisateurService.saveUtilisateur(util);
        // then: l'utilisateur a un id
        assertNotNull(util.getId());
    }
}
