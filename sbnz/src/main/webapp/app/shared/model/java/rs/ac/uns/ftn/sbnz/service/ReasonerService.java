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
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.domain.MedicalCondition;
import rs.ac.uns.ftn.sbnz.domain.Patient;
import rs.ac.uns.ftn.sbnz.domain.icu.HeartbeatEvent;
import rs.ac.uns.ftn.sbnz.domain.icu.ICUPatient;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import rs.ac.uns.ftn.sbnz.service.dto.ExaminationDTO;
import rs.ac.uns.ftn.sbnz.web.websocket.icu.ICUEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ReasonerService implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(ReasonerService.class);

    private final MedicalConditionService medicalConditionService;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    @Autowired
    private AnamnesisService anamnesisService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private PatientService patientService;

    private ApplicationEventPublisher publisher;

    public ReasonerService(MedicalConditionService medicalConditionService) {
        this.medicalConditionService = medicalConditionService;
    }

    public void addMedicalCondition(Anamnesis anamnesis, String medicalConditionName, int probability) {
        Optional<MedicalCondition> medicalCondition = medicalConditionService.findOneByName(medicalConditionName);
        medicalCondition.get().setProbability(probability);
        medicalConditionService.save(medicalCondition.get());

        Diagnosis diagnosis = anamnesis.getCurrentDiagnosis();
        diagnosis.getDiagnosedConditions().clear();
        diagnosis.getDiagnosedConditions().add(medicalCondition.get());

        try {
            diagnosisService.save(diagnosis);
            anamnesisService.save(anamnesis);
        } catch(Exception e) {
            log.info("Exception while persisting!");
        }
    }

    public void insertAnamnesis(Anamnesis anamnesis) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent())
            return;

        KieSession kieSession = kieSessions.get(userLogin.get());
        kieSession.setGlobal("reasonerService", this);
        FactHandle factHandle = kieSession.insert(anamnesis);

        kieSession.getAgenda().getAgendaGroup("Medical conditions").setFocus();

        int handler = kieSession.fireAllRules();
        System.out.println(String.format("Rules fired: %s", handler));

        kieSession.delete(factHandle);
    }

    public void clearSession() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent())
            return;

        KieSession kieSession = kieSessions.get(userLogin.get());

        QueryResults queryResults = kieSession.getQueryResults("Get all Anamnesis facts");
        for (QueryResultsRow queryResult: queryResults) {
            Anamnesis a = (Anamnesis)queryResult.get("$a");
            System.out.println(a);
            kieSession.delete(kieSession.getFactHandle(a));
        }
    }

    public void sendMessage(String message, Date date, int id, String event) {
        log.info("Message from realtime test: " + message);
        publisher.publishEvent(new ICUEvent(this, event, date, message, id));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void realtimeSimulation() {
        log.info("Starting realtime simulation!");

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("icu-realtime-session");
        kieSession.setGlobal("reasonerService", this);
        ICUPatient patient = new ICUPatient();
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
