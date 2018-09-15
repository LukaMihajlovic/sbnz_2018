package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import java.util.HashMap;
import java.util.Optional;

@Service
public class RunService implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(RunService.class);


    @Autowired
    private HashMap<String, KieSession> kieSessions;

    private ApplicationEventPublisher publisher;

    public void executeRules(Anamnesis anamnesis) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (!login.isPresent())
            return;
        //KieServices kieServices = KieServices.Factory.get();
        //KieContainer kContainer = kieServices.getKieClasspathContainer();
        //KieSession kieSession = kContainer.newKieSession();
        KieSession kieSession = kieSessions.get(login.get());
        kieSession.setGlobal("runService", this);
        FactHandle factHandle = kieSession.insert(anamnesis);
        kieSession.getAgenda().getAgendaGroup("AnalizeDiseases").setFocus();
        int rulesFired = kieSession.fireAllRules();
        log.debug("Number of rules fired on event: " + rulesFired);
        kieSession.delete(factHandle);
    }

    public void diagnoseDisease(String diseaseName,double percent, Anamnesis anamnesis){

    }



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}

