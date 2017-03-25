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
@Table(name = "MESSAGE")
public class Message extends AbstractEntity {

    private static final long serialVersionUID = -138477990461466107L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_MESSAGE_USER"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_MESSAGE_CHAT"))
    private Chat chat;

    @Column(name = "CONTENT", length = 140)
    private String content;

    @OneToMany(mappedBy = "message")
    private List<Attachment> attachments;
}
