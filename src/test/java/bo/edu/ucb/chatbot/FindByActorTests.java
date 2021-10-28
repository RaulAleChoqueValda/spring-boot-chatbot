package bo.edu.ucb.chatbot;

import bo.edu.ucb.chatbot.bl.FilmSearchBl;
import bo.edu.ucb.chatbot.dto.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testcase 1: Busqueda por Actor de forma exacta
 * Descripción:
 *   - Se debe buscar una pelicula por Nombre Completo del Actor participante que solo retorne una ocurrencía.
 * Precondiciones:
 *   - Se conoce de antemano que pelicuals en BBDD tienen  muchas ocurrencia por actor.
 * Valores de entrada:
 *   - Tom Miranda
 * Resultado esperado:
 *   - Solo un elemento para cada valor de entrada
 *   - maude:
 *         [
 *           {
 *             "film_id": 566,
 *             "title": "MAUDE MOD",
 *             "description": "A Beautiful Documentary of a Forensic Psychologist And a Cat who must Reach a Astronaut in Nigeria",
 *             "release_year": 2006,
 *             "language": "English",
 *             "originalLanguage": null,
 *             "rental_duration": 6,
 *             "rental_rate": 0.99,
 *             "length": 72,
 *             "replacement_cost": 20.99,
 *             "rating": "R",
 *             "special_features": "Trailers,Behind the Scenes",
 *             "last_update": "2006-02-15 05:03:42"
 *           }
 *         ]
 *
 *
 */
@SpringBootTest
class FindByActorTests {

	@Autowired
	FilmSearchBl filmSearchBl;

	@Test
	void findExactlyOne() {
		// Buscamos la pelicula
		List<Film> films = filmSearchBl.findByActor("Tom Miranda");
		// Probamos que el resultado sea el esperado
		assertNotNull(films, "La busqueda retorno una lista nula");
		Film film = films.get(0);
		assertEquals("ANALYZE HOOSIERS", film.getTitle(), "El titulo de la película no coincide");
		assertTrue( film.getDescription().startsWith("A Thoughtful Display of a Explorer And"), "La descripcion de la película no coincide");
		assertEquals("3h 1m", film.getLengthLabel(), "La hora no coincide o esta en formato incorrecto");
	}

}
