package geo.xdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ehm on 28.04.2017.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class XMessage implements Serializable {

    private static final long serialVersionUID = 5150920932406821224L;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long chatId;

    private String username;

    @Size(min = 1, max = 140)
    @NotNull
    private String content;

    private Date date;

    List<XAttachment> attachments;
}
