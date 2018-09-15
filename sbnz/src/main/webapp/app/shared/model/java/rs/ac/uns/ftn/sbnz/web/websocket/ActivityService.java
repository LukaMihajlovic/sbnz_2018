package rs.ac.uns.ftn.sbnz.web.websocket;

import static rs.ac.uns.ftn.sbnz.config.WebsocketConfiguration.IP_ADDRESS;

import rs.ac.uns.ftn.sbnz.web.websocket.dto.ActivityDTO;

import java.security.Principal;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import rs.ac.uns.ftn.sbnz.web.websocket.dto.MessageDTO;
import rs.ac.uns.ftn.sbnz.web.websocket.icu.ICUEvent;

@Controller
public class ActivityService implements ApplicationListener<ICUEvent> {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public ActivityService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/topic/activity")
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
    public void onApplicationEvent(ICUEvent event) {
        messagingTemplate.convertAndSend("/topic/tracker",
            new MessageDTO(event.getEvent(), event.getDate(), event.getMessage(), event.getPatientId()));
    }
}
