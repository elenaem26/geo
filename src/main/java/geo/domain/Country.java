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
@Table(name = "COUNTRY")
public class Country extends AbstractEntity {

    private static final long serialVersionUID = 2941248400080729956L;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<City> cities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID", foreignKey = @ForeignKey(name = "FK_COUNTRY_LOCATION"))
    private Location location;

    @Override
    public String toString() {
        return name;
    }
}
