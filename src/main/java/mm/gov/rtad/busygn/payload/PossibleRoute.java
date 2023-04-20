package mm.gov.rtad.busygn.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PossibleRoute {
    //start of second will be end of first and start of third will be end of second pop
    private String firstBusId;

    private List<String> firstBusRoute;

    private String secondBusId;

    private List<String> secondBusRoute;

    private String thirdBusId;

    private List<String> thirdBusRoute;

}
