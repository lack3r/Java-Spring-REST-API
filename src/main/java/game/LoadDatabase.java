package game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is responsible to create some basic dummy data in the database
 */
@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   GameInstanceRepository gameInstanceRepository, GameDefinitionRepository gameDefinitionRepository) {

        return args -> {

            User admin = new User("Yiannakis o Admin", "admin", "admin", UserRole.ADMIN);
            User normalUser = new User("Andreas Mastrapas", "user", "user", UserRole.USER);

            userRepository.save(admin);
            userRepository.save(normalUser);

            printAllFromRepository(userRepository);

            GameDefinition tetris = new GameDefinition("Tetris", 10, "YOLO_01");
            GameDefinition gradius = new GameDefinition("Gradius - Conami ↑↑↓↓←→←→BA", 60, "DRY_SOLID_YAGNY");
            gameDefinitionRepository.save(tetris);
            gameDefinitionRepository.save(gradius);

            printAllFromRepository(gameDefinitionRepository);

            gameInstanceRepository.save(new GameInstance(normalUser.getId(), tetris.getId(), GameInstanceState.FINISHED_EXPIRED));
            gameInstanceRepository.save(new GameInstance(normalUser.getId(), gradius.getId(), GameInstanceState.CREATED_STARTED));

            printAllFromRepository(gameInstanceRepository);
        };
    }

    private void printAllFromRepository(JpaRepository repository) {
        repository.findAll().forEach(entity -> {
            log.info("Preloaded " + entity);
        });
    }
}
