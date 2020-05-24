package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.repositories.UtilisateurRepository;
import captainadhoc.captainadhocbackend.services.implementations.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UtilisateurServiceTest {

    @MockBean
    private UtilisateurRepository utilisateurRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private UtilisateurService utilisateurService;
    private Utilisateur utilisateur;

    @BeforeEach
    public void setupEach() {
        utilisateurService = new UtilisateurService();
        utilisateurService.setUtilisateurRepository(utilisateurRepository);
        utilisateurService.setBCryptPasswordEncoder(passwordEncoder);

        utilisateur = Utilisateur.builder()
                .nom("Kevin")
                .prenom("Marchand")
                .nomUtilisateur("marchand1")
                .motDePasse("mdp")
                .isAdmin(true)
                .build();
    }


    @Test
    public void testSaveUtilisateur() {

        // Given: un UtilisateurService et un utilisateur
        // When: la méthode saveUtilisateur est invoquée
        when(utilisateurService.getUtilisateurRepository().save(utilisateur)).thenReturn(utilisateur);
        utilisateurService.saveUtilisateur(utilisateur);

        // then: la méthode findAll du Repository associé est invoquée
        verify(utilisateurService.getUtilisateurRepository()).save(utilisateur);
    }

    @Test
    public void testFindById() {

        // given: un UtilisateurService
        // when: la méthode findUtilisateurById est invoquée
        utilisateurService.findById(0L);

        // then: la méthode findById du Repository associé est invoquée
        verify(utilisateurService.getUtilisateurRepository()).findById(0L);
    }

    @Test
    public void testFindByNomUtilisateur() {

        // given: un UtilisateurService
        // when: la méthode findByNomUtilisateur est invoquée
        utilisateurService.findByNomUtilisateur("nomUtilisateur");

        // then: la méthode findById du Repository associé est invoquée
        verify(utilisateurService.getUtilisateurRepository()).findByNomUtilisateur("nomUtilisateur");
    }

    @Test
    public void testLoadUserByUsername() {

        // given: un UtilisateurService
        when(utilisateurService.getUtilisateurRepository().findByNomUtilisateur("nomUtilisateur")).thenReturn(utilisateur);

        // when: la méthode loadUserByUsername est invoquée
        utilisateurService.loadUserByUsername("nomUtilisateur");

        // then: la méthode findById du Repository associé est invoquée
        verify(utilisateurService.getUtilisateurRepository()).findByNomUtilisateur("nomUtilisateur");
    }
}
