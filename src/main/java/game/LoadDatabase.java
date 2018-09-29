package game;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository,
								   GameInstanceRepository gameInstanceRepository,GameDefinitionRepository gameDefinitionRepository) {
		return args -> {
			userRepository.save(new User("Yiannakis o Admin", UserRole.ADMIN));
			userRepository.save(new User("Andreas Mastrapas", UserRole.USER));

			userRepository.findAll().forEach(user -> {
				log.info("Preloaded " + user);
			});

			// tag::gameInstance[]
			gameInstanceRepository.save(new GameInstance("Leage of Legends", GameInstanceState.FINISHED_EXPIRED));
			gameInstanceRepository.save(new GameInstance("World of Warcraft", GameInstanceState.CREATED_STARTED));

			gameInstanceRepository.findAll().forEach(gameInstance -> {
				log.info("Preloaded " + gameInstance);
			});
			// end::gameInstance[]

			gameDefinitionRepository.save(new GameDefinition("Tetris", "Tetris is a tile-matching puzzle video game"));
			gameDefinitionRepository.save(new GameDefinition("Gradius", "Gradius is a series of shooter video games by Conami. ↑↑↓↓←→←→BA "));

            gameDefinitionRepository.findAll().forEach(gameInstance -> {
				log.info("Preloaded " + gameInstance);
			});
		};
	}
}
