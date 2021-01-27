package com.movie.service;

import com.movie.entity.Cinema;

import java.util.List;

public interface ICinemaService {
	Cinema findCinemaById(long cinema_id);
	Integer addCinema(Cinema cinema);
	Integer updateCinema(Cinema cinema);
	Integer deleteCinema(long cinema_id);
	List<Cinema> findAllCinemas();
	List<Cinema> findCinemasLikeName(String cinema_name);
	List<Cinema> findCinemasByMovieId(long movie_id);
}
