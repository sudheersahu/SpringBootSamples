package com.example.demo.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;


import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CartLineInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.model.OrderDetailInfo;
import com.example.demo.model.OrderInfo;
import com.example.demo.pagination.PaginationResult;

@Repository
@Transactional
public class OrderDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ProductDAO productDAO;

	
	
	public Order findOrder(String orderId) {
		Session session = this.sessionFactory.getCurrentSession();
        return session.find(Order.class, orderId);

	}
	
	public OrderInfo getOrderInfo(String orderId) {

		Order order = this.findOrder(orderId);

		if (order == null) {
			return null;
		}
		return new OrderInfo(order.getId(), order.getOrderDate(), //
				order.getOrderNum(), order.getAmount(), order.getCustomerName(), //
				order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());

	}
	
	private int getMaxOrderNum() {

		String sql = "Select max(o.OrderNum) from " + Order.class.getName() + "o";
		 Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Integer> query = session.createQuery(sql, Integer.class);
		Integer value = (Integer) query.getSingleResult();
		if (value == null) {
			return 0;
		}
		return value;

	}

	@Transactional
	public void saveOrder(CartInfo cartInfo) {
		Session session = this.sessionFactory.getCurrentSession();
		int orderNum = this.getMaxOrderNum() + 1;

		Order order = new Order();
		order.setOrderNum(orderNum);
		order.setId(UUID.randomUUID().toString());
		order.setOrderDate(new Date());
		order.setAmount(cartInfo.getAmountTotal());

		CustomerInfo customerInfo = cartInfo.getCustomerInfo();

		order.setCustomerName(customerInfo.getName());
		order.setCustomerPhone(customerInfo.getPhone());
		order.setCustomerEmail(customerInfo.getEmail());
		order.setCustomerAddress(customerInfo.getAddress());

		session.persist(order);

		List<CartLineInfo> lines = cartInfo.getCartlines();

		for (CartLineInfo line : lines) {
			OrderDetail detail = new OrderDetail();
			detail.setAmount(line.getAmount());
			detail.setQuanity(line.getQuantity());
			detail.setId(UUID.randomUUID().toString());
			detail.setPrice(line.getProductInfo().getPrice());
			detail.setOrder(order);
			String code = line.getProductInfo().getCode();
			Product product = this.productDAO.findProduct(code);
			detail.setProduct(product);

			session.persist(detail);
		}

		// Order Number!
		cartInfo.setOrderNum(orderNum);
		// Flush
		session.flush();

	}
	
	// @page = 1, 2, ...
	public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
		String sql = "Select new " + OrderInfo.class.getName()//
				+ "(ord.id, ord.orderDate, ord.orderNum, ord.amount, "
				+ " ord.customerName, ord.customerAddress, ord.customerEmail, ord.customerPhone) " + " from "
				+ Order.class.getName() + " ord "//
				+ " order by ord.orderNum desc";

		Session session = this.sessionFactory.getCurrentSession();
		Query<OrderInfo> query = session.createQuery(sql, OrderInfo.class);
		return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
	}
    
	public List<OrderDetailInfo> listOrderDetailInfos(String orderId) {
		String sql = "Select new " + OrderDetailInfo.class.getName() //
				+ "(d.id, d.product.code, d.product.name , d.quanity,d.price,d.amount) "//
				+ " from " + OrderDetail.class.getName() + " d "//
				+ " where d.order.id = :orderId ";
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<OrderDetailInfo> query = session.createQuery(sql, OrderDetailInfo.class);
		query.setParameter("orderId", orderId);

		return query.getResultList();
	}
	
}
