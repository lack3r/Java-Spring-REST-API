package game;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class GameDefinition {

	private @Id @GeneratedValue Long id;
	private String name;
	private String description;

	GameDefinition(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
