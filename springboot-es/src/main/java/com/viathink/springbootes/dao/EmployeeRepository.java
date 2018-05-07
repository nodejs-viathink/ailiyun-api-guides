package com.viathink.springbootes.dao;

import com.viathink.springbootes.domain.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeRepository extends ElasticsearchRepository<Employee,String> {
    Employee queryEmployeeById(String id);
    Employee deleteEmployeeById(String id);
}
