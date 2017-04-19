package geo.xdto;

import lombok.*;

import java.io.Serializable;

/**
 * Created by ehm on 15.04.2017.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class AccountCredentials implements Serializable {

    private static final long serialVersionUID = -7802428863225329980L;
    private String username;
    private String password;
    // getters & setters
}