package game;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameInstanceRepository extends JpaRepository<GameInstance, Long> {

}
