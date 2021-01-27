package com.movie.service.imp;

import com.movie.entity.Hall;
import com.movie.entity.Order;
import com.movie.entity.Schedule;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.mapper.*;
import com.movie.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp implements IOrderService{
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private ScheduleMapper scheduleMapper;
	@Resource
	private HallMapper hallMapper;
	@Resource
	private MovieMapper movieMapper;
	@Resource
	private CinemaMapper cinemaMapper;
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public Order findOrderById(String order_id) {
		Order order = this.orderMapper.findOrderById(order_id);
		if(order != null) {
			order.setOrder_user(this.userMapper.findUserById(order.getUser_id()));
			Schedule schedule = this.scheduleMapper.findScheduleById(order.getSchedule_id());
			Hall hall = this.hallMapper.findHallById(schedule.getHall_id());
			hall.setHall_cinema(this.cinemaMapper.findCinemaById(hall.getCinema_id()));
			schedule.setSchedule_hall(hall);
			schedule.setSchedule_movie(this.movieMapper.findMovieById(schedule.getMovie_id()));
			order.setOrder_schedule(schedule);
		}else {
			order = null;
		}
		return order;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Order> findRefundOrderByUserName(String user_name) {
		List<Order> list = this.orderMapper.findRefundOrderByUserName(user_name);
		if(list.size() > 0) {
			for(Order order : list) {
				order.setOrder_user(this.userMapper.findUserById(order.getUser_id()));
				Schedule schedule = this.scheduleMapper.findScheduleById(order.getSchedule_id());
				Hall hall = this.hallMapper.findHallById(schedule.getHall_id());
				hall.setHall_cinema(this.cinemaMapper.findCinemaById(hall.getCinema_id()));
				schedule.setSchedule_hall(hall);
				schedule.setSchedule_movie(this.movieMapper.findMovieById(schedule.getMovie_id()));
				order.setOrder_schedule(schedule);
			}
		}else {
			list = null;
		}
		return list;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer addOrder(Order order) {
		return this.orderMapper.addOrder(order);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer updateOrderStateToRefund(String order_id) {
		return this.orderMapper.updateOrderStateToRefund(order_id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	
	public Integer updateOrderStateToRefunded(String order_id) {
		return this.orderMapper.updateOrderStateToRefunded(order_id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public PageInfo<Order> findOrdersByUserName(Integer page,Integer limit,String user_name) {
		PageHelper.startPage(page, limit);
		List<Order> list = this.orderMapper.findOrdersByUserName(user_name);
		for(Order order : list) {
			order.setOrder_user(this.userMapper.findUserById(order.getUser_id()));
			Schedule schedule = this.scheduleMapper.findScheduleById(order.getSchedule_id());
			Hall hall = this.hallMapper.findHallById(schedule.getHall_id());
			hall.setHall_cinema(this.cinemaMapper.findCinemaById(hall.getCinema_id()));
			schedule.setSchedule_hall(hall);
			schedule.setSchedule_movie(this.movieMapper.findMovieById(schedule.getMovie_id()));
			order.setOrder_schedule(schedule);
		}
		PageInfo<Order> info = new PageInfo<Order>(list);
		return info;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public List<Order> findAllOrders() {
		List<Order> list = this.orderMapper.findAllOrders();
		for(Order order : list) {
			order.setOrder_user(this.userMapper.findUserById(order.getUser_id()));
			Schedule schedule = this.scheduleMapper.findScheduleById(order.getSchedule_id());
			Hall hall = this.hallMapper.findHallById(schedule.getHall_id());
			hall.setHall_cinema(this.cinemaMapper.findCinemaById(hall.getCinema_id()));
			schedule.setSchedule_hall(hall);
			schedule.setSchedule_movie(this.movieMapper.findMovieById(schedule.getMovie_id()));
			order.setOrder_schedule(schedule);
		}
		return list;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public PageInfo<Order> findOrdersByState(Integer page,Integer limit,int order_state) {
		PageHelper.startPage(page, limit);
		List<Order> list = this.orderMapper.findOrdersByState(order_state);
		for(Order order : list) {
			order.setOrder_user(this.userMapper.findUserById(order.getUser_id()));
			Schedule schedule = this.scheduleMapper.findScheduleById(order.getSchedule_id());
			Hall hall = this.hallMapper.findHallById(schedule.getHall_id());
			hall.setHall_cinema(this.cinemaMapper.findCinemaById(hall.getCinema_id()));
			schedule.setSchedule_hall(hall);
			schedule.setSchedule_movie(this.movieMapper.findMovieById(schedule.getMovie_id()));
			order.setOrder_schedule(schedule);
		}
		PageInfo<Order> info = new PageInfo<Order>(list);
		return info;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	
	public PageInfo<Order> findAllOrdersBySplitPage(Integer page, Integer limit, String keyword) {
		PageHelper.startPage(page, limit);
		List<Order> list = new ArrayList<Order>();
		if(keyword != null && !keyword.trim().equals("")) {
			list = this.orderMapper.findOrdersByUserName(keyword);
		}else {
			list = this.orderMapper.findAllOrders();
		}
		for(Order order : list) {
			order.setOrder_user(this.userMapper.findUserById(order.getUser_id()));
			Schedule schedule = this.scheduleMapper.findScheduleById(order.getSchedule_id());
			Hall hall = this.hallMapper.findHallById(schedule.getHall_id());
			hall.setHall_cinema(this.cinemaMapper.findCinemaById(hall.getCinema_id()));
			schedule.setSchedule_hall(hall);
			schedule.setSchedule_movie(this.movieMapper.findMovieById(schedule.getMovie_id()));
			order.setOrder_schedule(schedule);
		}
		PageInfo<Order> info = new PageInfo<Order>(list);
		return info;
	}
	
}
