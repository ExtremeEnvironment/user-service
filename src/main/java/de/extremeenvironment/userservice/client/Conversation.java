package de.extremeenvironment.userservice.client;

import java.io.Serializable;

/**
 * Created by on 14.07.16.
 *
 * @author David Steiman
 */
public class Conversation implements Serializable {

    private static final long serialVersionUID = 189369962L;

    private Long id;

    private Boolean active = true;

    private String title;

    private String type;

    private Long matchedActionId;

    public Conversation() {
    }

    public Conversation(String title) {
        this.title = title;
        this.type = "ngo";
    }

    public Conversation(Long id, String title) {
        this.id = id;
        this.title = title;
        this.type = "ngo";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMatchedActionId() {
        return matchedActionId;
    }

    public void setMatchedActionId(Long matchedActionId) {
        this.matchedActionId = matchedActionId;
    }
}
