package game;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "USER_GAME_INSTANCE")
public class GameInstance {

    private @Id
    @GeneratedValue
    Long id;

    private Long userId;
    private Long gameDefinitionID;
    private GameInstanceState status;

    GameInstance(Long userId, Long gameDefinitionID, GameInstanceState status) {

        this.userId = userId;
        this.gameDefinitionID = gameDefinitionID;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGameDefinitionID() {
        return gameDefinitionID;
    }

    public void setUserId() {
        this.userId = userId;
    }

    public GameInstanceState getStatus() {
        return status;
    }

    public void setStatus(GameInstanceState status) {
        this.status = status;
    }
}
