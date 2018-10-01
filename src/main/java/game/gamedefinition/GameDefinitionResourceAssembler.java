package game.gamedefinition;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class GameDefinitionResourceAssembler implements ResourceAssembler<GameDefinition, Resource<GameDefinition>> {

	@Override
	public Resource<GameDefinition> toResource(GameDefinition gameDefinition) {
		return new Resource<>(gameDefinition,
			linkTo(methodOn(GameDefinitionController.class).one(gameDefinition.getId())).withSelfRel(),
			linkTo(methodOn(GameDefinitionController.class).all()).withRel("gameDefinitions"));
	}
}
