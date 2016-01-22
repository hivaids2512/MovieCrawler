package facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import crawler.MySqlConnectionManager;
import entitity.movie;

public class MovieFacade {

	private MySqlConnectionManager manager;

	public MovieFacade() {
		manager = new MySqlConnectionManager();
	}

	public void importMovie(movie movie) {
		Connection connection = null;
		try {
			connection = manager.getDBConnection();

			String sql = "insert into moviedata values(? , ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, Integer.valueOf(movie.getId()));
			stmt.setString(2, movie.getTitle());
			stmt.setString(3, movie.getYear());
			stmt.setString(4, movie.getRated());
			stmt.setString(5, movie.getReleased());
			stmt.setString(6, movie.getRuntime());
			stmt.setString(7, movie.getGenre());
			stmt.setString(8, movie.getDirector());
			stmt.setString(9, movie.getWriter());
			stmt.setString(10, movie.getActors());
			stmt.setString(11, movie.getPlot());
			stmt.setString(12, movie.getLanguage());
			stmt.setString(13, movie.getCountry());
			stmt.setString(14, movie.getAwards());
			stmt.setString(15, movie.getPoster());
			stmt.setString(16, movie.getMetascore());
			stmt.setString(17, movie.getImdbRating());
			stmt.setString(18, movie.getImdbVotes());
			stmt.setString(19, movie.getImdbID());
			stmt.setString(20, movie.getType());

			stmt.executeUpdate();

			stmt.close();
			connection.close();
		} catch (Exception ex) {
			System.out.println(ex);
			// importUnCompleteMovie(movie);
			Logger.getLogger(MySqlConnectionManager.class.getName()).log(
					Level.SEVERE, null, ex);

		}

	}

	public void importUnCompleteMovie(movie movie) {
		Connection connection = null;
		try {
			connection = manager.getDBConnection();

			String sql = "insert into uncomplete values(? , ? , ?);";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, Integer.valueOf(movie.getId()));
			stmt.setString(2, movie.getTitle());
			stmt.setString(3, movie.getGenre());

			stmt.executeUpdate();

			stmt.close();
			connection.close();
		} catch (Exception ex) {
			System.out.println(ex);
			Logger.getLogger(MySqlConnectionManager.class.getName()).log(
					Level.SEVERE, null, ex);

		}

	}

	public ArrayList<movie> getMovies() {
		Connection connection = null;
		ArrayList<movie> movieList = new ArrayList<movie>();
		try {
			connection = manager.getDBConnection();

			String sql = "select * from movies where MovieID > 752;";

			PreparedStatement stmt = connection.prepareStatement(sql);

			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				movie movie = new movie();
				String titleReal = "";
				String title = result.getString("Title");
				String genre = result.getString("Genres");
				int movieId = result.getInt("MovieID");
				String parts[] = title.split(" ");
				for (int i = 0; i < parts.length - 1; i++) {
					if (parts[i].startsWith("(")) {
						break;
					}
					if (parts[i].equals("The") && i != 0) {
						break;
					}
					titleReal = titleReal + " " + parts[i];
				}
				movie.setId(Integer.toString(movieId));
				movie.setTitle(titleReal);
				movie.setGenre(genre);
				movie.setUnCompleteTitle(title);
				movieList.add(movie);
			}

			stmt.close();
			connection.close();
		} catch (Exception ex) {
			System.out.println(ex);
			Logger.getLogger(MySqlConnectionManager.class.getName()).log(
					Level.SEVERE, null, ex);

		}
		return movieList;

	}

}
