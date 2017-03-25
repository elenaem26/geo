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
@Table(name = "CHAT_GROUP")
public class ChatGroup extends AbstractEntity {

    private static final long serialVersionUID = -3836821600542436429L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID", foreignKey = @ForeignKey(name = "FK_CHATGROUP_LOCATION"))
    private Location location;

    @OneToMany(mappedBy = "chatGroup", fetch = FetchType.EAGER)
    private List<Chat> chats;
}
