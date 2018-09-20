package rs.ac.uns.ftn.sbnz;

import org.drools.core.time.SessionPseudoClock;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import rs.ac.uns.ftn.sbnz.domain.icu.HeartbeatEvent;
import rs.ac.uns.ftn.sbnz.domain.icu.PatientMonitoring;
import rs.ac.uns.ftn.sbnz.domain.icu.FastHeartbeatEvent;

import java.text.ParseException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RapidHeartBeatTest {

    private KieSession kieSession;

    @Before
    public void testCEPConfigThroughKModuleXML() throws ParseException {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        kieSession = kContainer.newKieSession("icu");
    }

    @Test
    public void normalHeartBeatTest() throws ParseException {
        SessionPseudoClock clock = kieSession.getSessionClock();
        PatientMonitoring patient = new PatientMonitoring();

        for (int index = 0; index < 10; index++) {
            HeartbeatEvent beat = new HeartbeatEvent(patient.getId());
            kieSession.insert(beat);

            beat = new HeartbeatEvent(patient.getId());
            kieSession.insert(beat);
            clock.advanceTime(1, TimeUnit.SECONDS);
            int ruleCount = kieSession.fireAllRules();

            assertThat(ruleCount, equalTo(0));
        }

        Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(FastHeartbeatEvent.class));
        assertThat(newEvents.size(), Matchers.equalTo(0));
    }

    @Test
    public void rapidHeartBeatTest() throws ParseException {
        SessionPseudoClock clock = kieSession.getSessionClock();
        PatientMonitoring patient = new PatientMonitoring();
        kieSession.insert(patient);

        HeartbeatEvent beat;
        for (int index = 0; index < 40; index++) {
            beat = new HeartbeatEvent(patient.getId());
            kieSession.insert(beat);

            clock.advanceTime(300, TimeUnit.MILLISECONDS);
        }
        Collection<?> heartbeatEvents = kieSession.getObjects(new ClassObjectFilter(HeartbeatEvent.class));
        assertThat(heartbeatEvents.size(), Matchers.equalTo(40));
        int ruleCount = kieSession.fireAllRules();
        assertThat(ruleCount, equalTo(1));
        Collection<?> tachycardiaEvents = kieSession.getObjects(new ClassObjectFilter(FastHeartbeatEvent.class));
        assertThat(tachycardiaEvents.size(), Matchers.equalTo(1));
    }
}
