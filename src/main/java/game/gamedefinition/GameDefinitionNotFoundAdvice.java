package game.gamedefinition;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GameDefinitionNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(GameDefinitionNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String userNotFoundHandler(GameDefinitionNotFoundException ex) {
		return ex.getMessage();
	}
}
