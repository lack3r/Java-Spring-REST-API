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
								   GameInstanceRepository gameInstanceRepository) {
		return args -> {
			userRepository.save(new User("Yiannakis o Admin", "ADMIN"));
			userRepository.save(new User("Andreas Mastrapaps", "NORMAL_USER"));

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
		};
	}
}
