package mm.gov.rtad.busygn.database.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document(collection = "bus")
public class Bus {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String name;

    private List<String> busRoute; //busStopId list
}
