package geo.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by ehm on 25.03.2017.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@Entity
@Table(name = "ATTACHMENT")
public class Attachment extends AbstractEntity {

    private static final long serialVersionUID = -5595619258479238885L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ATTACHMENT_MESSAGE"))
    private Message message;

    @Column(name = "CONTENT")
    private byte[] content;
}
