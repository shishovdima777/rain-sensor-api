package eng.shishov.rainsensor.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "Sensor")
public class Sensor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "This field should not be empty")
    @Size(min = 3, max = 30, message = "Sensor name must be between 3 and 30 symbols length")
    @Column(name = "name")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
