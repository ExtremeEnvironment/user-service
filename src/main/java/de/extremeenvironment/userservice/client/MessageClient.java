package de.extremeenvironment.userservice.client;

import de.extremeenvironment.userservice.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by on 14.07.16.
 *
 * @author David Steiman
 */
@FeignClient("messageservice")
public interface MessageClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/conversations")
    List<Conversation> getConversations();

    @RequestMapping(method = RequestMethod.POST, value = "/api/conversations")
    Conversation addConversation(@RequestBody Conversation conversation);

    @RequestMapping(method = RequestMethod.POST, value = "/api/conversations/{conversationId}/members")
    User addMember(@RequestBody User user, @PathVariable("conversationId") Long conversationId);
}
