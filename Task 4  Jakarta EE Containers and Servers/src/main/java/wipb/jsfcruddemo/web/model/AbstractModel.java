package wipb.jsfcruddemo.web.model;

import jakarta.persistence.*;
import java.util.UUID;

@MappedSuperclass
public class AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Transient  // Keep this as Transient since it doesn't need to be persisted
    private String uid;

    public AbstractModel() {
        this.uid = UUID.randomUUID().toString();  // Generate UID when the object is created
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }
}
