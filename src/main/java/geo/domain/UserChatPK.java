package geo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ehm on 25.03.2017.
 */
@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserChatPK implements Serializable {

    private static final long serialVersionUID = -7112862485515072472L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_USERCHAT_USER"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ID", foreignKey = @ForeignKey(name = "FK_USERCHAT_CHAT"))
    private Chat chat;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserChatRole role;
}
