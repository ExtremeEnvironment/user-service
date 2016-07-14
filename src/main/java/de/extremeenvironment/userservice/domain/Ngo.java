package de.extremeenvironment.userservice.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ngo.
 */
@Entity
@Table(name = "ngo")
public class Ngo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column
    private Long conversationId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ngo_user",
               joinColumns = @JoinColumn(name="ngos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="ID"))
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ngo_admins",
               joinColumns = @JoinColumn(name="ngos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="admins_id", referencedColumnName="ID"))
    private Set<User> admins = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> users) {
        this.admins = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ngo ngo = (Ngo) o;
        if(ngo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ngo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ngo{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
