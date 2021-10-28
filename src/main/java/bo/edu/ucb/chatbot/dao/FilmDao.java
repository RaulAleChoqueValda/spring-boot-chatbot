package bo.edu.ucb.chatbot.dao;

import bo.edu.ucb.chatbot.dto.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilmDao {

    private DataSource dataSource;

    @Autowired
    public FilmDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Film> findByTitle(String title) {
        List<Film> result = new ArrayList<>();
        String query = "SELECT f.film_id, " +
                "   f.title, " +
                "   f.description, " +
                "   f.release_year, " +
                "   l.name as language , " +
                "   ol.name as original_language, " +
                "   f.length, " +
                "   f.rating, " +
                "   f.special_features, " +
                "   f.last_update " +
                " FROM film f " +
                "     LEFT JOIN language l ON ( f.language_id = l.language_id) " +
                "     LEFT JOIN language ol ON ( f.original_language_id = ol.language_id) " +
                " WHERE " +
                "   UPPER(title) LIKE ( ? )" ;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt =  conn.prepareStatement(query);
                ) {
            System.out.println(query);
            pstmt.setString(1, "%"+title.toUpperCase()+ "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setLanguage("language");
                film.setOriginalLanguage("original_language");
                film.setLength(rs.getInt("length"));
                film.setRating(rs.getString("rating"));
                film.setSpecialFeatures(rs.getString("special_features"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                result.add(film);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // TODO gestionar correctamente la excepción
        }
        return result;
    }
    public List<Film> findByActor(String actor) {
        List<Film> result = new ArrayList<>();
        String query = "SELECT f.film_id, " +
                "   f.title, " +
                "   f.description, " +
                "   f.release_year, " +
                "   l.name as language , " +
                "   ol.name as original_language, " +
                "   f.length, " +
                "   f.rating, " +
                "   f.special_features, " +
                "   f.last_update, " +
                "   concat(a.first_name,' ',a.last_name) as full_name " +
                " FROM film f " +
                "     LEFT JOIN language l ON ( f.language_id = l.language_id) " +
                "     LEFT JOIN language ol ON ( f.original_language_id = ol.language_id) " +
                "     LEFT JOIN film_actor fa ON (f.film_id = fa.film_id) " +
                "     LEFT JOIN actor a ON (fa.actor_id  = a.actor_id) " +
                " WHERE " +
                "   UPPER(concat(a.first_name,' ',a.last_name)) LIKE ( ? )" ;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt =  conn.prepareStatement(query);
        ) {
            System.out.println(query);
            pstmt.setString(1, "%"+actor.toUpperCase()+ "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setLanguage("language");
                film.setOriginalLanguage("original_language");
                film.setLength(rs.getInt("length"));
                film.setRating(rs.getString("rating"));
                film.setSpecialFeatures(rs.getString("special_features"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                List<String> actors = new ArrayList<>();
                actors.add("full_name");
                film.setActors(actors);
                result.add(film);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // TODO gestionar correctamente la excepción
        }
        return result;
    }public List<Film> findByTitleAndActor(String title,String actor) {
        List<Film> result = new ArrayList<>();
        String query = "SELECT f.film_id, " +
                "   f.title, " +
                "   f.description, " +
                "   f.release_year, " +
                "   l.name as language , " +
                "   ol.name as original_language, " +
                "   f.length, " +
                "   f.rating, " +
                "   f.special_features, " +
                "   f.last_update, " +
                "   concat(a.first_name,' ',a.last_name) as full_name " +
                " FROM film f " +
                "     LEFT JOIN language l ON ( f.language_id = l.language_id) " +
                "     LEFT JOIN language ol ON ( f.original_language_id = ol.language_id) " +
                "     LEFT JOIN film_actor fa ON (f.film_id = fa.film_id) " +
                "     LEFT JOIN actor a ON (fa.actor_id  = a.actor_id) " +
                " WHERE " +
                "   UPPER(title) LIKE ( ? ) "+
                " AND "+
                "   UPPER(concat(a.first_name,' ',a.last_name)) LIKE ( ? )";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt =  conn.prepareStatement(query);
        ) {
            System.out.println(query);
            pstmt.setString(1, "%"+title.toUpperCase()+ "%");
            pstmt.setString(2, "%"+actor.toUpperCase()+ "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setLanguage("language");
                film.setOriginalLanguage("original_language");
                film.setLength(rs.getInt("length"));
                film.setRating(rs.getString("rating"));
                film.setSpecialFeatures(rs.getString("special_features"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                List<String> actors = new ArrayList<>();
                actors.add("full_name");
                film.setActors(actors);
                result.add(film);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // TODO gestionar correctamente la excepción
        }
        return result;
    }

}
