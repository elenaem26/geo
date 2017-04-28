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
@Table(name = "LOCATION")
public class Location extends AbstractEntity {

    private static final long serialVersionUID = 5222872291632508240L;

    @Column(columnDefinition="Decimal(10,8)")
    private Double latitude;

    @Column(columnDefinition="Decimal(10,8)")
    private Double longitude;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<City> cities;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Country> countries;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Chat> chats;

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
