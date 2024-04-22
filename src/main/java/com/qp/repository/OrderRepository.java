package com.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qp.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
