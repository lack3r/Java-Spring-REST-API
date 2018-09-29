package game;

import org.springframework.data.jpa.repository.JpaRepository;

interface GameDefinitionRepository extends JpaRepository<GameDefinition, Long> {

}
