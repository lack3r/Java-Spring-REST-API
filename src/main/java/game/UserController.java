package game;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::constructor[]
@RestController
class UserController {

	private final UserRepository repository;

	private final UserResourceAssembler assembler;
	private final GameInstanceResourceAssembler gameInstanceAssembler;

	UserController(UserRepository repository,
				   UserResourceAssembler assembler,
				   GameInstanceResourceAssembler gameInstanceAssembler) {
		
		this.repository = repository;
		this.assembler = assembler;
		this.gameInstanceAssembler = gameInstanceAssembler;
	}
	// end::constructor[]

	// Aggregate root

	@GetMapping("/users")
	Resources<Resource<User>> all() {

		List<Resource<User>> users = repository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());
		
		return new Resources<>(users,
			linkTo(methodOn(UserController.class).all()).withSelfRel());
	}

	@PostMapping("/users")
	ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException {

		Resource<User> resource = assembler.toResource(repository.save(newUser));

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	// Single item

	@GetMapping("/users/{id}")
	Resource<User> one(@PathVariable Long id) {

		User user = repository.findById(id)
			.orElseThrow(() -> new UserNotFoundException(id));
		
		return assembler.toResource(user);
	}

	@PutMapping("/users/{id}")
	ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) throws URISyntaxException {

		User updatedUser = repository.findById(id)
			.map(user -> {
				user.setName(newUser.getName());
				user.setRole(newUser.getRole());
				return repository.save(user);
			})
			.orElseGet(() -> {
				newUser.setId(id);
				return repository.save(newUser);
			});

		Resource<User> resource = assembler.toResource(updatedUser);

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	@DeleteMapping("/users/{id}")
	ResponseEntity<?> deleteUser(@PathVariable Long id) {

		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
