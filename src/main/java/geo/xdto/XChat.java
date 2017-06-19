package geo.xdto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ehm on 16.04.2017.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class XChat implements Serializable {
    private static final long serialVersionUID = 4955639919122311820L;

    private Long id;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Size(max = 500)
    private String description;
    @NotNull
    private Double radius;
    private Date disposeDate;
    private Date lastActivity;
    private Integer amountOfPeople;
    private Long chatGroupId;
    private Integer disposeDays;
}
