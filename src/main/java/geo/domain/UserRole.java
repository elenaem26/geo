package geo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehm on 25.03.2017.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@Entity
@Table(name = "ROLE")
public class UserRole extends AbstractEntity {

    private static final long serialVersionUID = -3798247766807737030L;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "userRoles")
    @Singular
    private List<User> users = new ArrayList<>();

    public UserRole(String roleName) { this.name = roleName; }

    @Override
    public String toString() {
        return name;
    }
}