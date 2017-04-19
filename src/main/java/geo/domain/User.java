package geo.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ehm on 25.03.2017.
 */
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User extends AbstractEntity {

    private static final String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private static final long serialVersionUID = 8326056865934663990L;

    @Column(unique = true, nullable = false, length = 100)
    private String uid;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "LASTNAME", length = 100)
    private String lastName;

    @Column(name = "FIRSTNAME", length = 100)
    private String firstName;

    @Column(name = "MIDDLENAME", length = 100)
    private String middleName;

    @Column(name = "EMAIL", unique = true, nullable = false, length = 100)
    @Pattern(regexp = EMAIL_PATTERN)
    private String email;

    @Column(name = "PHONE", length = 100)
    private String phone;

    @OneToMany(mappedBy = "id.user", orphanRemoval = true)
    private Set<UserChat> userChats = new HashSet<UserChat>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Message> messages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID", foreignKey = @ForeignKey(name = "FK_USER_CITY"))
    private City city;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<UserRole> userRoles = new ArrayList<>();

    @Column(name = "IS_ENABLED")
    @Type(type = "yes_no")
    private boolean enabled = true;

    @Builder
    public User(Long id, String uid, String username, String password, String lastName, String firstName,
                String middleName, String email,String phone, City city, List<UserRole> userRoles,boolean enabled) {
        super(id);
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.userRoles = userRoles;
        this.enabled = enabled;
    }
}
