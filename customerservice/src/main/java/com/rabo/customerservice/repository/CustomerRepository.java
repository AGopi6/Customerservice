package com.rabo.customerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rabo.customerservice.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query("select c from Customer c where upper(c.fname) like concat('%', upper(?1), '%') and upper(c.lname) like concat('%', upper(?2), '%')")
	List<Optional<Customer>> findByFnameAndLname(@Param("fname") String fname, @Param("lname") String lname);

	@Query("select c from Customer c where upper(c.fname) like concat('%', upper(?1), '%') or upper(c.lname) like concat('%', upper(?2), '%')")
	List<Optional<Customer>> findByFnameOrLname(@Param("fname") String fname, @Param("lname") String lname);

}
