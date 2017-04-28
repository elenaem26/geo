package geo.xdto;

import lombok.*;

import java.io.Serializable;

/**
 * Created by ehm on 28.04.2017.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class XAttachment implements Serializable {
    private static final long serialVersionUID = -2332375152415912031L;
    private byte[] content;
}
