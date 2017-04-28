package geo.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by ehm on 28.04.2017.
 */
@Getter
@ToString
@EqualsAndHashCode
public class Event implements Serializable {

    private static final long serialVersionUID = 3614014537706227273L;
    private final Object payload = UUID.randomUUID();

}
