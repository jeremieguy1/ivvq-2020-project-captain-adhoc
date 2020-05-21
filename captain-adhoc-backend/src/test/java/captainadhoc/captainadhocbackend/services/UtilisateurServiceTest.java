package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.repositories.UtilisateurRepository;
import captainadhoc.captainadhocbackend.services.implementations.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur;

    @BeforeEach
    public void setup() {
        utilisateur = new Utilisateur(1L, "Kevin", "Marchand", "marchand1", "mdp", true, emptyList());
    }


    @Test
    public void testSaveUtilisateur(){
        // Given: un UtilisateurService et un utilisateur
        // When: la méthode saveUtilisateur est invoquée
        when(utilisateurService.saveUtilisateur(utilisateur)).thenReturn(utilisateur);
        // then: la méthode findAll du Repository associé est invoquée
        verify(utilisateurService.getUtilisateurRepository()).findAll();
    }

    @Test
    public void testFindById() {
        // given: un UtilisateurService
        // when: la méthode findUtilisateurById est invoquée
        when(utilisateurService.findById(0L)).thenReturn(utilisateur);
        // then: la méthode findById du Repository associé est invoquée
        verify(utilisateurService.getUtilisateurRepository()).findById(0L);
    }

    @Test
    public void testFindByNomUtilisateur() {
        // given: un UtilisateurService
        // when: la méthode findByNomUtilisateur est invoquée
        when(utilisateurService.findByNomUtilisateur("nomUtilisateur")).thenReturn(utilisateur);
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
