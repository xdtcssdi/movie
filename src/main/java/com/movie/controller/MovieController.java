package com.movie.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.entity.Cinema;
import com.movie.entity.Movie;
import com.movie.service.ICinemaService;
import com.movie.service.IMovieService;
import com.movie.util.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Controller
@RequestMapping("/movie")
public class MovieController {
    @Resource
    private IMovieService movieService;
    @Resource
    private ICinemaService cinemaService;

    //1.电影详情页 findid
    //2.首页电影列表 + name搜索 + type搜素 + 时间、参评人数、评分排序
    //3.增加、删除、修改
    @RequestMapping("findMovieById")
    @ResponseBody
    public JSONObject findMovieById(@RequestParam("movie_id") long movie_id) {
        JSONObject obj = new JSONObject();
        Movie movie = movieService.findMovieById(movie_id);
        List<Cinema> list = this.cinemaService.findCinemasByMovieId(movie_id);
        obj.put("code", 0);
        obj.put("data", movie);
        obj.put("cinemaList", list);
        obj.put("cinemaCount", list.size());
        return obj;
    }

    @RequestMapping("findAllMovies")
    @ResponseBody
    public JSONObject findAllMovies() {

        JSONObject obj = new JSONObject();
        PageHelper.startPage(1,8);//分页起始码以及每页页数
        List<Movie> list = movieService.findAllMovies(1);
        PageInfo<Movie> pageInfo= new PageInfo<>(list);
        list = pageInfo.getList();

        PageHelper.startPage(1,8);//分页起始码以及每页页数
        List<Movie> upcomingList = movieService.findAllMovies(0);
        PageInfo<Movie> pageInfo1= new PageInfo<>(upcomingList);
        upcomingList = pageInfo1.getList();

        PageHelper.startPage(1,9);//分页起始码以及每页页数
        List<Movie> offList = movieService.sortMovieByBoxOffice();
        PageInfo<Movie> pageInfo2= new PageInfo<>(offList);
        offList = pageInfo2.getList();

        obj.put("code", 0);
        obj.put("count", Math.min(list.size(), 8));
        obj.put("upcomingCount", Math.min(upcomingList.size(), 8));
        obj.put("data", list.subList(0, 8));
        obj.put("data1", upcomingList.subList(0, Math.min(upcomingList.size(), 8)));
        obj.put("sort", offList.subList(0, 9));

        return obj;
    }

    @RequestMapping("findMoviesByName")
    @ResponseBody
    public JSONObject findMoviesByName(@RequestParam("name") String name) {
        JSONObject obj = new JSONObject();
        List<Movie> list = movieService.findMoviesLikeName(name);
        obj.put("code", 0);
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }

    @RequestMapping("search")
    @ResponseBody
    public JSONObject search(@RequestParam("searchMovie") String searchMovie){
        System.out.println(searchMovie);
        List<Movie> moviesLikeName = movieService.findMoviesLikeName(searchMovie);
        JSONObject obj = new JSONObject();
        obj.put("count", moviesLikeName.size());
        obj.put("code", 0);
        obj.put("data", moviesLikeName);
        return obj;
    }

    @RequestMapping("findMoviesByType")
    @ResponseBody
    public JSONObject findMoviesByType(@RequestParam(value="page",defaultValue="1")Integer page,
                                       @RequestParam(value="pageSize",defaultValue="50")Integer pageSize,
                                       @RequestParam("type") String type,
                                       @RequestParam("area") String area,
                                       @RequestParam("year") String year) {
        JSONObject obj = new JSONObject();
        if ("0".equals(area) || "全部".equals(area))
            area = "";
        if ("0".equals(year) || "全部".equals(year))
            year = "";

        System.out.println(type + " " + area + " " + year);
        PageHelper.startPage(page,pageSize);//分页起始码以及每页页数

        List<Movie> list = movieService.findMoviesLikeType2(type, area, year);
        PageInfo<Movie> pageInfo= new PageInfo<>(list);
        obj.put("code", 0);
        obj.put("count", pageInfo.getPages());
        obj.put("data", pageInfo.getList());
        return obj;
    }

    @RequestMapping("sortAllMovies")
    @ResponseBody
    public JSONObject sortAllMovies(@RequestParam(value="page",defaultValue="1")Integer page,
                                    @RequestParam(value="pageSize",defaultValue="50")Integer pageSize ,
                                    @RequestParam("order") String order) {
        JSONObject obj = new JSONObject();
        List<Movie> list = new ArrayList<>();
        PageHelper.startPage(page,pageSize);//分页起始码以及每页页数

        switch (order) {
            case "热门":
                list = movieService.sortMovieByCount();
                break;
            case "时间":
                list = movieService.sortMovieByDate();
                break;
            case "评价":
                list = movieService.sortMovieByScore();
                break;
        }

        PageInfo<Movie> pageInfo= new PageInfo<>(list);
        obj.put("code", 0);
        obj.put("count", pageInfo.getPages());
        obj.put("data", pageInfo.getList());
        return obj;
    }

    @RequestMapping("deleteMovie")
    @ResponseBody
    public JSONObject deleteMovie(@RequestParam("movie_id") long movie_id) {
        JSONObject obj = new JSONObject();
        Integer rs = movieService.deleteMovie(movie_id);
        if (rs > 0) {
            obj.put("code", 0);
            obj.put("msg", "下架成功~");
        } else {
            obj.put("code", 200);
            obj.put("msg", "下架失败~");
        }
        return obj;
    }

    @RequestMapping("addMovie")
    @ResponseBody
    public JSONObject addMovie(@RequestParam(value = "file", required = false) MultipartFile file, Movie movie, HttpServletRequest request) throws IOException {
        String str = file.getOriginalFilename();
        System.out.println("file:" + str);
        String name = UUIDUtil.getUUID() + str.substring(str.lastIndexOf("."));
        System.out.println("name:" + name);
        String path = request.getServletContext().getRealPath("/upload/movies") + "/" + name;
        System.out.println("path:" + path);
        String filePath = "../upload/movies/" + name;
        movie.setMovie_picture(filePath);
        Date date = new Date();
        java.sql.Date releaseDate = new java.sql.Date(date.getYear(), date.getMonth(), date.getDay());
        //SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        float random = 5 + (new Random().nextFloat() * 4);
        DecimalFormat fnum = new DecimalFormat("##0.0");
        String score = fnum.format(random);
        movie.setMovie_score(Float.parseFloat(score));
        movie.setReleaseDate(releaseDate);
        Integer rs = movieService.addMovie(movie);
        JSONObject obj = new JSONObject();
        if (rs > 0) {
            file.transferTo(new File(path));
            System.out.println("文件写入成功,Path:" + path);
            obj.put("code", 0);
            obj.put("msg", "添加成功~");
        } else {
            obj.put("code", 200);
            obj.put("msg", "添加失败~");
        }
        return obj;
    }

    @RequestMapping("updateMovie")
    @ResponseBody
    public JSONObject updateMovie(@RequestParam(value = "file", required = false) MultipartFile file, Movie movie, HttpServletRequest request) throws IOException {
        JSONObject obj = new JSONObject();
        if (file != null) {
            String str = file.getOriginalFilename();
            System.out.println("file:" + str);
            String name = UUIDUtil.getUUID() + str.substring(str.lastIndexOf("."));
            System.out.println("name:" + name);
            String path = request.getServletContext().getRealPath("/upload/movies") + "/" + name;
            System.out.println("path:" + path);
            String filePath = "../upload/movies/" + name;
            file.transferTo(new File(path));
            System.out.println("文件写入成功,Path:" + path);
            movie.setMovie_picture(filePath);
        } else {
            Movie oldMovie = this.movieService.findMovieById(movie.getMovie_id());
            movie.setMovie_picture(oldMovie.getMovie_picture());
        }
        Integer rs = movieService.updateMovie(movie);
        if (rs > 0) {
            obj.put("code", 0);
            obj.put("msg", "修改成功~");
        } else {
            obj.put("code", 200);
            obj.put("msg", "修改失败~");
        }
        return obj;
    }

}
