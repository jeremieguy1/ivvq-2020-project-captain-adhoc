package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.services.interfaces.IUtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
@Transactional
public class UtilisateurServiceIntegrationTest {

    @Autowired
    IUtilisateurService utilisateurService;

    @BeforeEach
    public void setupEach() {

        Utilisateur utilisateur = Utilisateur.builder()
                .nom("Kevin")
                .prenom("Marchand")
                .nomUtilisateur("marchand1")
                .motDePasse("mdp")
                .isAdmin(true)
                .build();

       utilisateurService.saveUtilisateur(utilisateur);
    }

    @Test
    public void testSaveUtilisateur() {

        // given: un Uilisateur non persisté
        Utilisateur util = Utilisateur.builder()
                .nom("Test")
                .prenom("Utilisateur")
                .nomUtilisateur("UtilisateurTest")
                .motDePasse("mdp")
                .isAdmin(true)
                .build();

        assertNull(util.getId());

        // when: l'utilisateur est persisté
        utilisateurService.saveUtilisateur(util);

        // then: l'utilisateur a un id
        assertNotNull(util.getId());
    }
}
