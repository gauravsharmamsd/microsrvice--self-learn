package com.address__app.address_service;

import com.address__app.address_service.pojo.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
    //find address by EmployeeID
    @Query(value = "select ea.id, ea.lane1, ea.lane2, ea.city, ea.country,ea.employee_id from address ea join employee e on e.id=ea.employee_id where ea.employee_id = :employeeId", nativeQuery = true)
    Address findByEmployeeId(@Param("employeeId") int employeeId);
}
