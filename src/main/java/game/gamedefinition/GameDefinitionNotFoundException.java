package game.gamedefinition;

public class GameDefinitionNotFoundException extends RuntimeException {

	GameDefinitionNotFoundException(Long id) {
		super("Could not find game definition with " + id);
	}
}
