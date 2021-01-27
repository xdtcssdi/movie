package com.movie.mapper;

import com.movie.entity.Cinema;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CinemaMapper {
	Cinema findCinemaById(long cinema_id);
	Integer addCinema(Cinema cinema);
	Integer updateCinema(Cinema cinema);
	Integer deleteCinema(long cinema_id);
	List<Cinema> findAllCinemas();
	List<Cinema> findCinemasLikeName(String cinema_name);
	List<Cinema> findCinemasByMovieId(long movie_id);
}
