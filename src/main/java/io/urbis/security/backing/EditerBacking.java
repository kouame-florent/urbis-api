/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.backing;

import io.urbis.acte.mariage.backing.*;
import io.urbis.acte.mariage.domain.MentionOrdonRetranscription;
import io.urbis.acte.mariage.domain.Operation;
import static io.urbis.acte.mariage.domain.Operation.DECLARATION;
import static io.urbis.acte.mariage.domain.Operation.SAISIE_ACTE_EXISTANT;
import io.urbis.acte.mariage.domain.StatutActeMariage;
import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.acte.mariage.dto.MentionAnnulationMariageDto;
import io.urbis.acte.mariage.dto.MentionDivorceDto;
import io.urbis.acte.mariage.dto.MentionModifRegimeBiensDto;
import io.urbis.acte.mariage.dto.MentionOrdonRetranscriptionDto;
import io.urbis.acte.mariage.dto.MentionRectificationMariageDto;
import io.urbis.acte.mariage.dto.RegimeDto;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.acte.mariage.service.RegimeService;
import io.urbis.common.util.BaseBacking;

import io.urbis.common.dto.SituationMatrimonialeDto;
import io.urbis.common.service.SituationMatrimonialeService;

import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.param.service.OfficierService;

import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.service.RegistreService;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author florent
 */
@Named(value = "userEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
    
    
}
