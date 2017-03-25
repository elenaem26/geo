package geo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ehm on 25.03.2017.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@Entity
@Table(name = "CHAT")
public class Chat extends AbstractEntity{

    private static final long serialVersionUID = -7128821085658197780L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID", foreignKey = @ForeignKey(name = "FK_CHAT_LOCATION"))
    private Location location;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 400)
    private String description;

    @Column(name = "RADIUS")
    private Double radius;

    @Column(name = "DISPOSE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date disposeDate;

    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy = "id.chat")
    private Set<UserChat> chatUsers = new HashSet<UserChat>();

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATGROUP_ID", foreignKey = @ForeignKey(name = "FK_CHAT_CHATGROUP"))
    private ChatGroup chatGroup;
}
