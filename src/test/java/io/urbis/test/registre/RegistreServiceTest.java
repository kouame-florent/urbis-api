/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.test.registre;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.urbis.param.domain.Centre;
import io.urbis.param.domain.Localite;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.param.domain.TitreOfficier;
import io.urbis.param.domain.Tribunal;
import io.urbis.param.domain.TypeLocalite;
import io.urbis.registre.domain.Registre;
import io.urbis.registre.domain.StatutRegistre;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.service.RegistreService;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author florent
 */
//@QuarkusTest
public class RegistreServiceTest {

    private static final Logger LOG = Logger.getLogger(RegistreServiceTest.class.getName());
   
    @Inject
    RegistreService registreService;
    
   
    //@BeforeAll
    //@Transactional
    //@TestSecurity(user = "lisa", roles = {"admin", "user"})
    public static void createGlobalParams(){
        Localite localite = new Localite("l-01", "gagnoa", TypeLocalite.COMMUNE);
        localite.id = "f79908c8-23b8-4946-a38e-5e0ac889ef25";
        localite.updatedBy = "lisa";
        localite.persist();
        
        Centre centre = new Centre("c-01", "centre principal", localite);
        centre.id = "813e987d-9dc6-49e6-9e78-57807041f8bf";
        centre.updatedBy = "lisa";
        centre.persist();
        
        
        Tribunal tribunal = new Tribunal("t-01", "Tribunal de gagnoa");
        tribunal.id = "dad34686-1442-49ac-bc82-a85b4d59fae2";
        tribunal.updatedBy = "lisa";
        tribunal.persist();
        
        OfficierEtatCivil officier = new OfficierEtatCivil();
        officier.id = "40b22773-1977-44de-ab31-5649260b9b1b";
        officier.actif = true;
        officier.nom = "De Blazio";
        officier.prenoms = "Bill";
        officier.profession = "avocat";
        officier.titre = TitreOfficier.MAIRE;
        officier.updatedBy = "lisa";
        officier.persist();
     
    }
    
    
    
    //@Test
    //@Transactional
    //@TestSecurity(user = "lisa", roles = {"admin", "user"})
    public void creerRegistresTest(){
        
        LOG.log(Level.INFO, "testing creer registre");
        String reg1ID = creerRegistre();
        String reg2ID = creerRegistre();
        
        Registre registre1 = Registre.findById(reg1ID);
        Registre registre2 = Registre.findById(reg2ID);
        
        Assertions.assertEquals(1, registre1.reference.numero);
        Assertions.assertEquals(2, registre2.reference.numero);
        
        Assertions.assertEquals(1, registre1.numeroPremierActe);
        Assertions.assertEquals(50, registre1.numeroDernierActe);
        
        Assertions.assertEquals(51, registre2.numeroPremierActe);
        Assertions.assertEquals(100, registre2.numeroDernierActe);
        
    }
    
    
    //@Test
    //@Transactional
    //@TestSecurity(user = "lisa", roles = {"admin", "user"})
    public void creerSerieRegistresTest(){
        
        String[] ids = creerSerieRegistre();
        
        Registre registre1 = Registre.findById(ids[0]);
        Registre registre2 = Registre.findById(ids[1]);
        Registre registre3 = Registre.findById(ids[2]);
        
        Assertions.assertEquals(1, registre1.reference.numero);
        Assertions.assertEquals(2, registre2.reference.numero);
        Assertions.assertEquals(3, registre3.reference.numero);
        
        Assertions.assertEquals(1, registre1.numeroPremierActe);
        Assertions.assertEquals(50, registre1.numeroDernierActe);
        
        Assertions.assertEquals(51, registre2.numeroPremierActe);
        Assertions.assertEquals(100, registre2.numeroDernierActe);
        
        Assertions.assertEquals(101, registre3.numeroPremierActe);
        Assertions.assertEquals(150, registre3.numeroDernierActe);
     
    
    }
    
    public String creerRegistre(){
        int numeroRegistre = registreService.numeroRegistre(TypeRegistre.NAISSANCE.name(), 2022);
        int numeroPremierActe = registreService.numeroPremierActeCourant(TypeRegistre.NAISSANCE.name(), 2022);
        int nombreFeuillets = 50;
        int numeroDernierActe = numeroPremierActe + nombreFeuillets -1;
        
        Localite localite = Localite.findById("f79908c8-23b8-4946-a38e-5e0ac889ef25");
        Centre centre = Centre.findById("813e987d-9dc6-49e6-9e78-57807041f8bf");
        Tribunal tribunal = Tribunal.findById("dad34686-1442-49ac-bc82-a85b4d59fae2");
        OfficierEtatCivil officier = OfficierEtatCivil.findById("40b22773-1977-44de-ab31-5649260b9b1b");
        
           
        RegistreDto registreDto = new RegistreDto();
        registreDto.setAnnee(2022);
        registreDto.setCentreID(centre.id);
        registreDto.setDateOuverture(LocalDate.now());
        registreDto.setLibelle(TypeRegistre.NAISSANCE.getLibelle());
        registreDto.setLocaliteID(localite.id);
        registreDto.setNombreDeFeuillets(nombreFeuillets);
        registreDto.setNumero(numeroRegistre);
        registreDto.setNumeroPremierActe(numeroPremierActe);
        registreDto.setNumeroDernierActe(numeroDernierActe);
        registreDto.setOfficierEtatCivilID(officier.id);
        registreDto.setStatut(StatutRegistre.PROJET.name());
        registreDto.setTribunalID(tribunal.id);
        registreDto.setTypeRegistre(TypeRegistre.NAISSANCE.name());
        
        
        String registreID = registreService.creer(registreDto);
        
        return registreID;
    
    }
    
    public String[] creerSerieRegistre(){
        String[] ids = new String[3];
        int nombreFeuillets = 50;
        int annee = 2020;
        
        Localite localite = Localite.findById("f79908c8-23b8-4946-a38e-5e0ac889ef25");
        Centre centre = Centre.findById("813e987d-9dc6-49e6-9e78-57807041f8bf");
        Tribunal tribunal = Tribunal.findById("dad34686-1442-49ac-bc82-a85b4d59fae2");
        OfficierEtatCivil officier = OfficierEtatCivil.findById("40b22773-1977-44de-ab31-5649260b9b1b");
       
        for (int i = 1; i <= 3; i++ ){
            int numeroPremierActe = registreService.numeroPremierActe(TypeRegistre.NAISSANCE.name(),annee,i-1);
            int numeroDernierActe = registreService.numeroDernierActe(TypeRegistre.NAISSANCE.name(), annee,i+1,
                    numeroPremierActe, nombreFeuillets);
            LOG.log(Level.INFO, "NUMERO PREMIER ACTE: {0}", numeroPremierActe);
            
            RegistreDto registreDto = new RegistreDto();
            
            registreDto.setTypeRegistre(TypeRegistre.NAISSANCE.name());
            registreDto.setLibelle(TypeRegistre.NAISSANCE.getLibelle());
            registreDto.setLocaliteID(localite.id);
            registreDto.setCentreID(centre.id);
            registreDto.setAnnee(annee);
            registreDto.setNumero(i);
            registreDto.setTribunalID(tribunal.id);
            registreDto.setOfficierEtatCivilID(officier.id);
            registreDto.setNumeroPremierActe(numeroPremierActe);
            registreDto.setNumeroDernierActe(numeroDernierActe);
            //registreDto.setNumeroDernierActe(nombreDeFeuillets + numeroPremierActe - 1);
            registreDto.setNombreDeFeuillets(nombreFeuillets);
            registreDto.setNombreActe(0);
            registreDto.setStatut("");
            registreDto.setDateAnnulation(null);
            registreDto.setMotifAnnulation("");
            
            
            String id = registreService.creer(registreDto);
            ids[i-1] = id;
            
        
        }
        
        return ids;
    }
    
    
}
