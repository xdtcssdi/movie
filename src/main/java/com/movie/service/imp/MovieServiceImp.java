package com.movie.service.imp;

import com.movie.entity.Comment;
import com.movie.entity.Movie;
import com.movie.mapper.CommentMapper;
import com.movie.mapper.MovieMapper;
import com.movie.mapper.UserMapper;
import com.movie.service.IMovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieServiceImp implements IMovieService{
	@Resource
	private MovieMapper movieMapper;
	@Resource
	private CommentMapper commentMapper;
	@Resource
	private UserMapper userMapper;

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public Movie findMovieById(long movie_id) {
		Movie movie = this.movieMapper.findMovieById(movie_id);
		List<Comment> list = this.commentMapper.findCommentsByMoiveId(movie_id);
		for(Comment comment : list) {
			comment.setComment_user(this.userMapper.findUserById(comment.getUser_id()));
		}
		movie.setCommentList(list);
		return movie;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer changeMovieBoxOffice(float price, long movie_id) {
		return this.movieMapper.changeMovieBoxOffice(price, movie_id);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public Movie findMovieByName(String movie_cn_name) {
		return this.movieMapper.findMovieByName(movie_cn_name);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer addCommentCount(long movie_id) {
		return this.movieMapper.addMovieCommentCount(movie_id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer delCommentCount(long movie_id) {
		return this.movieMapper.deleteMovieCommentCount(movie_id);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer addMovie(Movie movie) {
		return this.movieMapper.addMovie(movie);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer deleteMovie(long movie_id) {
		return this.movieMapper.deleteMovie(movie_id);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer updateMovie(Movie movie) {
		return this.movieMapper.updateMovie(movie);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Movie> findAllMovies(int movie_state) {
		return this.movieMapper.findAllMovies(movie_state);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Movie> findMoviesLikeName(String name){

		return this.movieMapper.findMoviesLikeName(name);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Movie> findMoviesLikeType(String type) {
		return this.movieMapper.findMoviesLikeType(type);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Movie> findMoviesLikeType2(String type, String area, String year) {
		return this.movieMapper.findMoviesLikeType2(type, area, year);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Movie> sortMovieByDate() {
		return this.movieMapper.sortMovieByDate();
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Movie> sortMovieByCount() {
		return this.movieMapper.sortMovieByCount();
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Movie> sortMovieByScore() {
		return this.movieMapper.sortMovieByScore();
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Movie> sortMovieByBoxOffice() {
		return this.movieMapper.sortMovieByBoxOffice();
	}
	
	
}
