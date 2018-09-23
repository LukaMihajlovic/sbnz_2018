package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.domain.Disease;
import rs.ac.uns.ftn.sbnz.domain.icu.HeartbeatEvent;
import rs.ac.uns.ftn.sbnz.domain.icu.PatientMonitoring;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import rs.ac.uns.ftn.sbnz.web.websocket.dto.RealtimeEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class RunService implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(RunService.class);


    @Autowired
    private HashMap<String, KieSession> kieSessions;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private AnamnesisService anamnesisService;

    @Autowired
    private DiseaseService diseaseService;

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

    public void diagnoseDisease(String diseaseName,Number percent, Anamnesis anamnesis){
        Disease disease = diseaseService.findByName(diseaseName);
        //disease.setName(diseaseName);
        int factor = (int)(percent.doubleValue() + 0.5d);
        disease.setFactor(factor);
        Diagnosis d = anamnesis.getCurrentDiagnosis();
        d.getDiseases().clear();
        d.getDiseases().add(disease);
        diagnosisService.save(d);
        anamnesisService.save(anamnesis);
    }

    public void socketSend(String message, Date date, int id, String event) {
        log.info("Message from realtime test: " + message);
        publisher.publishEvent(new RealtimeEvent(this, event, date, message, id));
    }

    /*public void clearSession() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if (!userLogin.isPresent())
            return;
        KieSession kieSession = kieSessions.get(userLogin.get());
        QueryResults queryResults = kieSession.getQueryResults("");
        for (QueryResultsRow queryResult: queryResults) {
            Anamnesis a = (Anamnesis)queryResult.get("$anamnesis");
            System.out.println(a);
            kieSession.delete(kieSession.getFactHandle(a));
        }
    }*/



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void realtimeSimulation() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");


        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("realtime");
        kieSession.setGlobal("runService", this);
        PatientMonitoring patient = new PatientMonitoring();
        kieSession.insert(patient);

        Thread t = new Thread(() -> {
            while (true) {
                for (int i = 0; i < 30; i++) {
                    HeartbeatEvent event = new HeartbeatEvent(patient.getId());
                    kieSession.insert(event);

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        //do nothing
                    }
                }

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    //do nothing
                }
            }
        });
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            //do nothing
        }
        kieSession.fireUntilHalt();
    }

}

