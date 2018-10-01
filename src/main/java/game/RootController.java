package game;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import game.gamedefinition.GameDefinitionController;
import game.gameinstance.GameInstanceController;
import game.user.UserController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the rest controller that maps the different entities to the corresponding controllers
 */
@RestController
public class RootController {

	@GetMapping
	ResourceSupport index() {
		ResourceSupport rootResource = new ResourceSupport();
		rootResource.add(linkTo(methodOn(UserController.class).all()).withRel("users"));
		rootResource.add(linkTo(methodOn(GameDefinitionController.class).all()).withRel("gameDefinitions"));
		rootResource.add(linkTo(methodOn(GameInstanceController.class).all()).withRel("gameInstances"));
		return rootResource;
	}

}
