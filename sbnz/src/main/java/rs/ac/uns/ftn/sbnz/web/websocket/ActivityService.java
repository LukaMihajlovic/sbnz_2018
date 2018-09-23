package rs.ac.uns.ftn.sbnz.web.websocket;

import rs.ac.uns.ftn.sbnz.web.websocket.dto.ActivityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import rs.ac.uns.ftn.sbnz.web.websocket.dto.RealtimeDTO;
import rs.ac.uns.ftn.sbnz.web.websocket.dto.RealtimeEvent;

import java.security.Principal;
import java.time.Instant;

import static rs.ac.uns.ftn.sbnz.config.WebsocketConfiguration.IP_ADDRESS;

@Controller
public class ActivityService implements ApplicationListener<RealtimeEvent> {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public ActivityService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @SubscribeMapping("/topic/activity")
    @SendTo("/topic/tracker")
    public ActivityDTO sendActivity(@Payload ActivityDTO activityDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        activityDTO.setUserLogin(principal.getName());
        activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
        activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        activityDTO.setTime(Instant.now());
        log.debug("Sending user tracking data {}", activityDTO);
        return activityDTO;
    }


    @Override
    public void onApplicationEvent(RealtimeEvent rt) {
        messagingTemplate.convertAndSend("/topic/tracker",
            new RealtimeDTO(rt.getEvent(), rt.getDate(), rt.getMessage(), rt.getPatientId()));
    }
}
