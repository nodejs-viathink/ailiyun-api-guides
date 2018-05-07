package com.viathink.springbootes.contorller;

import com.viathink.springbootes.dao.EmployeeRepository;
import com.viathink.springbootes.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/es")  //路由前缀
public class ElasticSearchController {
    @Autowired
    private EmployeeRepository er;

    //增加
    @PostMapping("/add")
    public String add(Employee employee){

        er.save(employee);

        System.out.println("add a user");
        return "add success";
    }

    //删除
    //elasticsearch删除文档不会立即将文档从磁盘中删除，只是将文档标记为已删除状态。随着你不断的索引更多的数据，Elasticsearch 将会在后台清理标记为已删除的文档。
    @PostMapping("/delete")
    public String delete(String id){

        er.deleteEmployeeById(id);

        System.out.println("delete a user");
        return "remove success";
    }

    //更新
    @PostMapping("/update")
    public String update(String id,String name,String lastname){

        Employee employee = er.queryEmployeeById(id);
        employee.setFirst_name(name);
        employee.setLast_name(lastname);
        er.save(employee);
        System.out.println("update a user");
        return "update success";
    }

    //查询
    @GetMapping("/search/{id}")
    public Employee query(@PathVariable String id){
        Employee employee = er.queryEmployeeById(id);
        return employee;
    }
}
