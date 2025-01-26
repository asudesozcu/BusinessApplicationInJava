package wipb.jsfcruddemo.web.model;

import jakarta.persistence.*;
import java.util.UUID;

@MappedSuperclass
public class AbstractModel {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Transient
    private String uid;

    public AbstractModel() {
        this.uid = UUID.randomUUID().toString();
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
