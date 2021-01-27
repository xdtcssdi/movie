package com.movie.mapper;

import com.movie.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MovieMapper {
	Movie findMovieById(long movie_id);
	Movie findMovieByName(String movie_cn_name);
	Integer addMovie(Movie movie);
	Integer deleteMovie(long movie_id);
	Integer updateMovie(Movie movie);
	Integer deleteMovieCommentCount(long movie_id);
	Integer addMovieCommentCount(long movie_id);
	Integer changeMovieBoxOffice(@Param("movie_boxOffice") float movie_boxOffice, @Param("movie_id") long movie_id);
	List<Movie> findAllMovies(int movie_state);
	List<Movie> findMoviesLikeName(String name);
	List<Movie> findMoviesLikeType(String type);
	List<Movie> findMoviesLikeType2(@Param("type") String type, @Param("area") String area, @Param("year") String year);
	//上映时间  参评人数  评分
	List<Movie> sortMovieByDate();
	List<Movie> sortMovieByCount();
	List<Movie> sortMovieByScore();
	//票房排序
	List<Movie> sortMovieByBoxOffice();
}
