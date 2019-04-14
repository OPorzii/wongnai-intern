package com.wongnai.interview.movie.search;

import java.util.List;
import java.util.stream.Collectors;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class

		MoviesResponse moviesResponse = movieDataService.fetchAll();
		List<MovieData> searchResult = moviesResponse.stream().filter(m ->
				m.getTitle().toLowerCase().matches(".*\\b"+queryText.toLowerCase()+"\\b.*"))
				.collect(Collectors.toList());

		return convertMovieDataToMovieObj(searchResult);
	}

	private List<Movie> convertMovieDataToMovieObj(List<MovieData> movieData) {
		List<Movie> result = movieData.stream()
				.map(movie -> {
					Movie obj = new Movie();
					obj.setName(movie.getTitle());
					obj.setActors(movie.getCast());
					return obj;
				}).collect(Collectors.toList());
		return result;
	}
}
