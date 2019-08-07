package co.grandcircus.WebAPI.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.WebAPI.entity.Movies;

public interface MoviesDao extends JpaRepository<Movies, Long> {

	List<Movies> findByTitleContainsIgnoreCase(String title);
	List<Movies> findByCategoryContainsIgnoreCase(String category);

}
