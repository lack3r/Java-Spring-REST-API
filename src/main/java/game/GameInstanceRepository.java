package game;

import org.springframework.data.jpa.repository.JpaRepository;

interface GameInstanceRepository extends JpaRepository<GameInstance, Long> {

}
