package game;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class GameInstanceResourceAssembler implements ResourceAssembler<GameInstance, Resource<GameInstance>> {

	@Override
	public Resource<GameInstance> toResource(GameInstance gameInstance) {

		// Unconditional links to single-item resource and aggregate root

		Resource<GameInstance> gameInstanceResource = new Resource<>(gameInstance,
			linkTo(methodOn(GameInstanceController.class).one(gameInstance.getId())).withSelfRel(),
			linkTo(methodOn(GameInstanceController.class).all()).withRel("gameInstances")
		);

		// Conditional links based on state of the gameInstance
		
		if (gameInstance.getStatus() == GameInstanceState.CREATED_STARTED) {
			gameInstanceResource.add(
				linkTo(methodOn(GameInstanceController.class)
					.cancel(gameInstance.getId())).withRel("cancel"));
			gameInstanceResource.add(
				linkTo(methodOn(GameInstanceController.class)
					.complete(gameInstance.getId())).withRel("complete"));
		}

		return gameInstanceResource;
	}
}
