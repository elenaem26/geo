package geo.xdto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by ehm on 15.04.2017.
 */
@Getter
@Setter
public class AccountCredentials implements Serializable {

    private static final long serialVersionUID = -7802428863225329980L;
    private String username;
    private String password;
    // getters & setters
}