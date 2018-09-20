package rs.ac.uns.ftn.sbnz;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import rs.ac.uns.ftn.sbnz.domain.icu.OxygenMeasureEvent;
import rs.ac.uns.ftn.sbnz.domain.icu.OxygenProblemEvent;
import rs.ac.uns.ftn.sbnz.domain.icu.PatientMonitoring;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OxygenMeasureEventProblemTest {

    private KieSession kieSession;

    @Before
    public void testCEPConfigThroughKModuleXML() throws ParseException {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        kieSession = kContainer.newKieSession("icu");
    }

    /*@Test
    public void successfulOxygen() {

        runPseudoClockExampleSuccessfulFiring(kieSession);
    }

    private void runPseudoClockExampleSuccessfulFiring(KieSession ksession) {

        PatientMonitoring patient = new PatientMonitoring();

        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.getAgenda().getAgendaGroup("monitoring").setFocus();

        ksession.insert(new OxygenMeasureEvent( new Date(clock.getCurrentTime()), 5, patient.getId()));

        clock.advanceTime(10, TimeUnit.MINUTES);

        ksession.insert(new OxygenMeasureEvent(new Date(clock.getCurrentTime()), 2, patient.getId()));

        clock.advanceTime(10, TimeUnit.MINUTES);

        assertThat(ksession.fireAllRules(), equalTo(1));

        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(OxygenProblemEvent.class));
        assertThat(newEvents.size(), equalTo(1));
        ksession.dispose();
    }*/




    @Test
    public void runPseudoClockExample() throws ParseException {
        SessionPseudoClock clock = kieSession.getSessionClock();
        PatientMonitoring patient = new PatientMonitoring();

        OxygenMeasureEvent oxygen;
        int ruleCount = 0;

        oxygen = new OxygenMeasureEvent(65, patient.getId());
        System.out.println("Inserted: " + oxygen);
        kieSession.insert(oxygen);
        clock.advanceTime(5, TimeUnit.SECONDS);
        ruleCount = kieSession.fireAllRules();
        System.out.println(ruleCount);

        oxygen = new OxygenMeasureEvent(65, patient.getId());
        System.out.println("Inserted: " + oxygen);
        kieSession.insert(oxygen);
        clock.advanceTime(5, TimeUnit.SECONDS);
        ruleCount = kieSession.fireAllRules();
        System.out.println(ruleCount);

        oxygen = new OxygenMeasureEvent(71, patient.getId());
        System.out.println("Inserted: " + oxygen);
        kieSession.insert(oxygen);
        clock.advanceTime(5, TimeUnit.SECONDS);
        ruleCount = kieSession.fireAllRules();
        System.out.println(ruleCount);

        oxygen = new OxygenMeasureEvent(64, patient.getId());
        System.out.println("Inserted: " + oxygen);
        kieSession.insert(oxygen);
        clock.advanceTime(5, TimeUnit.SECONDS);
        ruleCount = kieSession.fireAllRules();
        System.out.println(ruleCount);

        clock.advanceTime(10, TimeUnit.MINUTES);
        kieSession.fireAllRules();

        oxygen = new OxygenMeasureEvent(63, patient.getId());
        System.out.println("Inserted: " + oxygen);
        kieSession.insert(oxygen);
        clock.advanceTime(5, TimeUnit.SECONDS);
        ruleCount = kieSession.fireAllRules();
        System.out.println(ruleCount);

        oxygen = new OxygenMeasureEvent(62, patient.getId());
        System.out.println("Inserted: " + oxygen);
        kieSession.insert(oxygen);
        clock.advanceTime(5, TimeUnit.SECONDS);
        ruleCount = kieSession.fireAllRules();
        System.out.println(ruleCount);

        clock.advanceTime(7, TimeUnit.MINUTES);
        kieSession.fireAllRules();
    }
}
