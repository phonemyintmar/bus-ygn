package mm.gov.rtad.busygn.logic;

import org.springframework.http.ResponseEntity;

public interface IFindRouteLogic {
    ResponseEntity<?> findRoute(String startStopId, String endStopId);
}
