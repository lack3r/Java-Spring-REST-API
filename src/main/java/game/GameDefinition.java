package game;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "GAME_DEFINITION")
class GameDefinition {

    // TODO maybe put these values in a properties file?

    private static int MIN_DURATION = 5;
    private static int MAX_DURATION = 60;

    private @Id
    @GeneratedValue
    Long id;
    private String name;

    // TODO: Does it make sense for duration to be a type by it's onwn?
    private int duration;

    private String networkEnvironmentId;

    GameDefinition(String name, int duration, String networkEnvironmentId) {

        if (!isDurationValid(duration))
            throw new IllegalArgumentException("duration can only be between " + MIN_DURATION + " minutes and " + MAX_DURATION + "minutes");

        if (name == null || name.isEmpty() || networkEnvironmentId == null || networkEnvironmentId.isEmpty()) {
            throw new IllegalArgumentException("Name of game definition and networkEnvironment ID should not be null or empty");
        }

        this.name = name;
        this.networkEnvironmentId = networkEnvironmentId;
        this.duration = duration;
    }

    // Explicitely defined getters and setters is not needed since we import import lombok.Data;
    // However, I do define them so that IDE's linter does not get confused

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getNetworkEnvironmentId() {
        return networkEnvironmentId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setNetworkEnvironmentId(String networkEnvironmentId) {
        this.networkEnvironmentId = networkEnvironmentId;
    }

    private boolean isDurationValid(int duration) {
        return duration >= MIN_DURATION && duration <= MAX_DURATION;
    }
}
