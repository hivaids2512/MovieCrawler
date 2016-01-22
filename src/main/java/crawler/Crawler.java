package crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import entitity.movie;
import facade.MovieFacade;

public class Crawler {
	public static void main(String args[]) {
		Client client = new Client("46.101.87.21", 8080);
		MultiThread threads = new MultiThread(client);
		threads.run();
		System.out.println("ok");
	}

	public static class MultiThread extends Thread {

		public Client client;

		public MultiThread(Client client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				ArrayList<movie> movieList = new MovieFacade().getMovies();
				for (movie element : movieList) {
					String url = "http://www.omdbapi.com/?t="
							+ URLEncoder.encode(element.getTitle(), "UTF-8");
					TimeUnit.SECONDS.sleep(5);
					System.out.println("Getting data for: "
							+ element.getTitle() + " id = " + element.getId());
					String json = client.getMovie(url);
					JSONObject obj = new JSONObject(json);

					MovieFacade facade = new MovieFacade();
					if (obj.getString("Response").equals("True")) {
						obj.put("Id", element.getId());
						movie movie = this.Map(obj, element.getId(),
								element.getTitle());
						facade.importMovie(movie);
					} else {
						System.out.println("Dont know this movie");
						movie errormovie = new movie();
						errormovie.setId(element.getId());
						errormovie.setTitle(element.getTitle());
						errormovie.setGenre(element.getGenre());
						facade.importUnCompleteMovie(errormovie);
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public movie Map(JSONObject JSONObject, String id, String title) {
			movie movie = new movie();
			try {
				if (JSONObject.getString("Title") != null) {
					movie.setId(JSONObject.getString("Id"));
					movie.setTitle(JSONObject.getString("Title"));
					movie.setYear(JSONObject.getString("Year"));
					movie.setRated(JSONObject.getString("Rated"));
					movie.setReleased(JSONObject.getString("Released"));
					movie.setRuntime(JSONObject.getString("Runtime"));
					movie.setGenre(JSONObject.getString("Genre"));
					movie.setDirector(JSONObject.getString("Director"));
					movie.setWriter(JSONObject.getString("Writer"));
					movie.setActors(JSONObject.getString("Actors"));
					movie.setPlot(JSONObject.getString("Plot"));
					movie.setLanguage(JSONObject.getString("Language"));
					movie.setCountry(JSONObject.getString("Country"));
					movie.setAwards(JSONObject.getString("Awards"));
					movie.setPoster(JSONObject.getString("Poster"));
					movie.setMetascore(JSONObject.getString("Metascore"));
					movie.setImdbRating(JSONObject.getString("imdbRating"));
					movie.setImdbVotes(JSONObject.getString("imdbVotes"));
					movie.setImdbID(JSONObject.getString("imdbID"));
					movie.setType(JSONObject.getString("Type"));
				} else {
					movie.setId(id);
					movie.setTitle(title);
				}

			} catch (Exception e) {

			}
			return movie;
		}

	}
}
