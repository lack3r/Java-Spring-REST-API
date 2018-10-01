package game.gameinstance;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::main[]
@RestController
public class GameInstanceController {

	private final GameInstanceRepository gameInstanceRepository;
	private final GameInstanceResourceAssembler assembler;

	public GameInstanceController(GameInstanceRepository gameInstanceRepository,
						   GameInstanceResourceAssembler assembler) {

		this.gameInstanceRepository = gameInstanceRepository;
		this.assembler = assembler;
	}

	@GetMapping("/gameInstances")
	public Resources<Resource<GameInstance>> all() {

		List<Resource<GameInstance>> gameInstances = gameInstanceRepository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());

		return new Resources<>(gameInstances,
			linkTo(methodOn(GameInstanceController.class).all()).withSelfRel());
	}

	@GetMapping("/gameInstances/{id}")
	public Resource<GameInstance> one(@PathVariable Long id) {
		return assembler.toResource(
			gameInstanceRepository.findById(id)
				.orElseThrow(() -> new GameInstanceNotFoundException(id)));
	}

	@PostMapping("/gameInstances")
	public ResponseEntity<Resource<GameInstance>> newGameInstance(@RequestBody GameInstance gameInstance) {

		gameInstance.setStatus(GameInstanceState.CREATED_STARTED);
		GameInstance newGameInstance = gameInstanceRepository.save(gameInstance);

		return ResponseEntity
			.created(linkTo(methodOn(GameInstanceController.class).one(newGameInstance.getId())).toUri())
			.body(assembler.toResource(newGameInstance));
	}
	// end::main[]
	
	// tag::delete[]
	@DeleteMapping("/gameInstances/{id}/cancel")
	public ResponseEntity<ResourceSupport> cancel(@PathVariable Long id) {

		GameInstance gameInstance = gameInstanceRepository.findById(id).orElseThrow(() -> new GameInstanceNotFoundException(id));

		if (gameInstance.getStatus() == GameInstanceState.CREATED_STARTED) {
			gameInstance.setStatus(GameInstanceState.FINISHED_EXPIRED);
			return ResponseEntity.ok(assembler.toResource(gameInstanceRepository.save(gameInstance)));
		}

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(new VndErrors.VndError("Method not allowed", "You can't cancel a game instance that is in the " + gameInstance.getStatus() + " status"));
	}
	// end::delete[]

	// tag::complete[]
	@PutMapping("/gameInstances/{id}/complete")
	public ResponseEntity<ResourceSupport> complete(@PathVariable Long id) {
		
			GameInstance gameInstance = gameInstanceRepository.findById(id).orElseThrow(() -> new GameInstanceNotFoundException(id));

			if (gameInstance.getStatus() == GameInstanceState.CREATED_STARTED) {
				gameInstance.setStatus(GameInstanceState.FINISHED_EXPIRED);
				return ResponseEntity.ok(assembler.toResource(gameInstanceRepository.save(gameInstance)));
			}

			return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new VndErrors.VndError("Method not allowed", "You can't complete a game instance that is in the " + gameInstance.getStatus() + " status"));
	}
	// end::complete[]
}
