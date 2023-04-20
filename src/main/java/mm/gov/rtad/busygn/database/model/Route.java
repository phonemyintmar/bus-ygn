package mm.gov.rtad.busygn.database.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document(collection = "route")
public class Route {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String startingStopId;

    private String endingStopId;

    private List<String> busIds;

    private Integer timesUsed;
}
