package geo.xdto;

import lombok.*;
import java.io.Serializable;

/**
 * Created by ehm on 19.06.2017.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class XUser implements Serializable {
    private static final long serialVersionUID = 4355171013467270535L;

    private Long id;
    private String username;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String phone;
}
