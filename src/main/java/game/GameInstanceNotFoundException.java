package game;

class GameInstanceNotFoundException extends RuntimeException {

	GameInstanceNotFoundException(Long id) {
		super("Could not find game instance " + id);
	}
}
