package bo.edu.ucb.chatbot.bl;

import bo.edu.ucb.chatbot.dto.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

/**
 * Procesar la lógica de negocio de las conversaciones del bo.
 * Recibe los mensajes enviados por el usuario como String.
 * Y restorna una List<String> como respuesta a estos mensajes
 * Con el proposito de hacer High Cohesion esta clase no debería utilizar ningun API de Telegram
 */

@Component
public class BotFilmSearchBl {

    private FilmSearchBl filmSearchBl;

    @Autowired
    public BotFilmSearchBl(FilmSearchBl filmSearchBl) {
        this.filmSearchBl = filmSearchBl;
    }

    public List<String> processMessage(String message) {
        List<String> result = new ArrayList<>();
        List<Film> filmList = new ArrayList<>();
        if(message.contains("|")){
            String[] aux = message.split("\\|");
            String p1=aux[0];
            String p2=aux[1];
            if(aux[0].toLowerCase().contains("Titulo".toLowerCase())){
                String[] auxTitle = aux[0].split("=");
                String[] auxActor = aux[1].split("=");

                Collator comparator = Collator.getInstance();
                comparator.setStrength(Collator.PRIMARY);

                if(comparator.compare(auxTitle[0],"Titulo")==0 && comparator.compare(auxActor[0],"Actor")==0){
                    filmList =  filmSearchBl.findByTitleAndActor(auxTitle[1],auxActor[1]);
                }
            }else{
                String[] auxActor = aux[0].split("=");
                String[] auxTitle = aux[1].split("=");

                Collator comparator = Collator.getInstance();
                comparator.setStrength(Collator.PRIMARY);

                if(comparator.compare(auxTitle[0],"Titulo")==0 && comparator.compare(auxActor[0],"Actor")==0){
                    filmList =  filmSearchBl.findByTitleAndActor(auxTitle[1],auxActor[1]);
                }
            }

        }else{
            String[] typeSearch = message.split("=");
            Collator comparator = Collator.getInstance();
            comparator.setStrength(Collator.PRIMARY);

            if(comparator.compare(typeSearch[0],"Titulo")==0){
                filmList =  filmSearchBl.findByTitle(typeSearch[1]);
            }if(comparator.compare(typeSearch[0],"Actor")==0){
                filmList =  filmSearchBl.findByActor(typeSearch[1]);
            }
        }

        if (!filmList.isEmpty()) {
            result.add("Encontré las siguientes películas:");
            for (Film film : filmList) {
                result.add(film.toString());
            }
        } else {
            result.add("No encontré ninguna película para: " + message);
        }

        return result;
    }


}
