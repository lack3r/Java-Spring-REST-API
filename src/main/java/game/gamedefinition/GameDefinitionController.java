package game.GameDefinition;

import game.GameInstanceResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

// tag::constructor[]
@RestController
public class GameDefinitionController {

	private final GameDefinitionRepository repository;

	private final GameDefinitionResourceAssembler assembler;

	public GameDefinitionController(GameDefinitionRepository repository,
                             GameDefinitionResourceAssembler assembler,
                             GameInstanceResourceAssembler gameInstanceAssembler) {
		
		this.repository = repository;
		this.assembler = assembler;
	}
	// end::constructor[]

	// Aggregate root

	@GetMapping("/gameDefinitions")
	public Resources<Resource<GameDefinition>> all() {

		List<Resource<GameDefinition>> gameDefinitions = repository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());
		
		return new Resources<>(gameDefinitions,
			linkTo(methodOn(GameDefinitionController.class).all()).withSelfRel());
	}

	@PostMapping("/gameDefinitions")
	public ResponseEntity<?> newGameDefinition(@RequestBody GameDefinition newGameDefinition) throws URISyntaxException {

		Resource<GameDefinition> resource = assembler.toResource(repository.save(newGameDefinition));

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	// Single item

	@GetMapping("/gameDefinitions/{id}")
	public Resource<GameDefinition> one(@PathVariable Long id) {

		GameDefinition gameDefinition = repository.findById(id)
			.orElseThrow(() -> new GameDefinitionNotFoundException(id));
		
		return assembler.toResource(gameDefinition);
	}

	@PutMapping("/gameDefinitions/{id}")
	public ResponseEntity<?> replaceGameDefinition(@RequestBody GameDefinition newGameDefinition, @PathVariable Long id) throws URISyntaxException {

		GameDefinition updatedGameDefinition = repository.findById(id)
			.map(gameDefinition -> {
				gameDefinition.setName(newGameDefinition.getName());
				gameDefinition.setDuration(newGameDefinition.getDuration());
				gameDefinition.setNetworkEnvironmentId(newGameDefinition.getNetworkEnvironmentId());
				return repository.save(gameDefinition);
			})
			.orElseGet(() -> {
				newGameDefinition.setId(id);
				return repository.save(newGameDefinition);
			});

		Resource<GameDefinition> resource = assembler.toResource(updatedGameDefinition);

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	@DeleteMapping("/gameDefinitions/{id}")
	public ResponseEntity<?> deleteGameDefinition(@PathVariable Long id) {

		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
