package geo.domain;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * Created by ehm on 25.03.2017.
 */
@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "entity_generator")
    @TableGenerator(name = "entity_generator", initialValue = 0, allocationSize = 100)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hello")
    private String hello;

    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
