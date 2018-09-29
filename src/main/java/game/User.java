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
	private String username;
	private String encodedPassword;

	User(String name, UserRole role) {
		this.name = name;
		this.role = role;
	}
}
