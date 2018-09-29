package game;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class User {

	private @Id @GeneratedValue Long id;
	private String name;
	private UserRole role;

	User(String name, String role) {
		this.name = name;
		this.role = Enum.valueOf(UserRole.class, role);
	}
}
