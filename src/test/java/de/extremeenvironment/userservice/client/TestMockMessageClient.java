package de.extremeenvironment.userservice.client;

import de.extremeenvironment.userservice.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by on 14.07.16.
 *
 * @author David Steiman
 */
@Component
public class TestMockMessageClient implements MessageClient {

    private Long conversationCounter = 1L;

    private Long userHolderCounter = 1L;

    private List<Conversation> conversations = new LinkedList<>();


    @Override
    public List<Conversation> getConversations() {
        return conversations;
    }

    @Override
    public Conversation addConversation(@RequestBody Conversation conversation) {
        Conversation saved = new Conversation(conversationCounter++, "test mocked conversation");
        conversations.add(saved);
        return saved;
    }

    @Override
    public User addMember(@RequestBody User user, @PathVariable("conversationId") Long conversationId) {
        return new User(userHolderCounter++);
    }
}
