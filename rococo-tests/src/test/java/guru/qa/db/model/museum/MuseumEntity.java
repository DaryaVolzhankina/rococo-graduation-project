package guru.qa.db.model.museum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "museums")
@Getter
@Setter
public class MuseumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;

    @Column(name = "geo", nullable = false)
    private UUID geo;
}
