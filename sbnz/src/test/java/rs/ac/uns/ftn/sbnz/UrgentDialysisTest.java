package rs.ac.uns.ftn.sbnz;

import org.drools.core.ClassObjectFilter;
import org.drools.core.time.SessionPseudoClock;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import rs.ac.uns.ftn.sbnz.domain.icu.HeartbeatEvent;
import rs.ac.uns.ftn.sbnz.domain.icu.PatientMonitoring;
import rs.ac.uns.ftn.sbnz.domain.icu.UrgentDialysisEvent;
import rs.ac.uns.ftn.sbnz.domain.icu.UrinationEvent;

import java.text.ParseException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UrgentDialysisTest {

    private KieSession kieSession;

    @Before
    public void testCEPConfigThroughKModuleXML() throws ParseException {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        kieSession = kContainer.newKieSession("icu");
    }

    @Test
    public void urgentDialysisNotNeeded() throws ParseException {
        SessionPseudoClock clock = kieSession.getSessionClock();

        PatientMonitoring patient = new PatientMonitoring();
        patient.getDiseases().add("Groznica");

        kieSession.insert(patient);

        int ruleCount = kieSession.fireAllRules();
        assertThat(ruleCount, equalTo(0));

        UrinationEvent urinationEvent;
        for (int i = 0; i < 20; i++) {
            urinationEvent = new UrinationEvent(patient.getId(), 3);
            kieSession.insert(urinationEvent);
            clock.advanceTime(1, TimeUnit.HOURS);
        }

        HeartbeatEvent beat;
        for (int i = 0; i < 25; i++) {
            beat = new HeartbeatEvent(patient.getId());
            kieSession.insert(beat);

            clock.advanceTime(500, TimeUnit.MILLISECONDS);
        }

        kieSession.fireAllRules();

        Collection<?> urgentDialysisEvents = kieSession.getObjects(new ClassObjectFilter(UrgentDialysisEvent.class));
        assertThat(urgentDialysisEvents.size(), Matchers.equalTo(0));
    }

    @Test
    public void urgentDialysisNeeded() throws ParseException {
        SessionPseudoClock clock = kieSession.getSessionClock();

        PatientMonitoring patient = new PatientMonitoring();
        patient.getDiseases().add("Hronicna bubrezna bolest");

        kieSession.insert(patient);

        int ruleCount = kieSession.fireAllRules();
        assertThat(ruleCount, equalTo(0));

        UrinationEvent urinationEvent;
        for (int i = 0; i < 20; i++) {
            urinationEvent = new UrinationEvent(patient.getId(), 3);
            kieSession.insert(urinationEvent);
            clock.advanceTime(1, TimeUnit.HOURS);
        }

        HeartbeatEvent beat;
        for (int i = 0; i < 25; i++) {
            beat = new HeartbeatEvent(patient.getId());
            kieSession.insert(beat);
            clock.advanceTime(500, TimeUnit.MILLISECONDS);
        }
        kieSession.fireAllRules();
        Collection<?> urgentDialysisEvents = kieSession.getObjects(new ClassObjectFilter(UrgentDialysisEvent.class));
        assertThat(urgentDialysisEvents.size(), Matchers.equalTo(1));
    }

}
