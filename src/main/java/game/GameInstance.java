package game;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "USER_GAME_INSTANCE")
class GameInstance {

	private @Id @GeneratedValue Long id;

	private String description;
	private GameInstanceState status;

	GameInstance(String description, GameInstanceState status) {

		this.description = description;
		this.status = status;
	}
}
