package org.georgich.straightrazor.storages;

import org.georgich.straightrazor.domain.Employee;
import org.georgich.straightrazor.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    Employee findBySurname(String surname);

    Employee findByPosition(String position);

    List<Employee> findAllByAuthor(User user);
}
