package co.grandcircus.WebAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.grandcircus.WebAPI.dao.MoviesDao;
import co.grandcircus.WebAPI.entity.Movies;

@RestController
public class WebAPIController {
	
	@Autowired
	private MoviesDao dao;
	
	// GET /movies
	// GET /movies?category=
	// GET /movies?title=
	@GetMapping("/movies")
	public List<Movies> listMovies(
			@RequestParam(value="category", required=false) String category, @RequestParam(value="title", required=false) String title){
		if ((category == null || category.isEmpty()) && (title == null || title.isEmpty())) {
			return dao.findAll();
		} else if (category == null || category.isEmpty()){
			return dao.findByTitleContainsIgnoreCase(title);
		} else {
			return dao.findByCategoryContainsIgnoreCase(category);
		}
	}
	
	// GET /movies/random
	// GET /movies/random?category=
	@GetMapping("/movies/random")
	public Movies randomMovie(@RequestParam(value="category", required=false) String category) {
		long id = 1 + (long)(Math.random() * 10);
		if(category == null || category.isEmpty()) {
			return dao.findById(id).get();

		} else {
			List<Movies> movie = dao.findByCategoryContainsIgnoreCase(category);
			int listSize = movie.size();
			int randMovie = (int)(Math.random() * listSize);
			return movie.get(randMovie);
		}
		
	}
	
	// GET /movies/random-list?quantity=
	@GetMapping("/movies/random-list")
	public List<Movies> randomMovieList(@RequestParam("quantity") Integer quantity){
		List<Movies> movieList = dao.findAll();
		List<Movies> randMovies = new ArrayList<Movies>();
		Collections.shuffle(movieList);
		for(int i = 0; i < quantity; i++) {
			randMovies.add(movieList.get(i));
		}
		return randMovies;
	}
	
	// GET /categories
	@GetMapping("/categories")
	public List<String> categories(){
        List<Movies> movieList = dao.findAll();
        Map<String, String> map = new HashMap<>();
        for(Movies movies: movieList) {
            map.put(movies.getCategory(), movies.getTitle());
        }
        List<String> result = new ArrayList<String>(map.keySet());
        return result;
    } 
	
	// GET /movie/id
	@GetMapping("/movies/{id}")
    public Movies getMovie(@PathVariable("id") Long id) {
        return dao.findById(id).get();
	}
	


	
	
	
	
	

}
