package geo.domain;

import lombok.*;

import javax.persistence.*;
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
@Table(name = "CITY")
public class City extends AbstractEntity {

    private static final long serialVersionUID = 7264263861498146665L;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID", foreignKey = @ForeignKey(name = "FK_CITY_COUNTRY"))
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID", foreignKey = @ForeignKey(name = "FK_CITY_LOCATION"))
    private Location location;

    @Override
    public String toString() {
        return name;
    }
}
