package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository

		MoviesResponse moviesResponse = movieDataService.fetchAll();

		//Convert Object MovieData to Movie
		List<Movie> result = moviesResponse.stream()
				.map(movie -> {
					Movie obj = new Movie();
					obj.setName(movie.getTitle());
					obj.setActors(movie.getCast());
					return obj;
				}).collect(Collectors.toList());
		movieRepository.saveAll(result);
	}
}
