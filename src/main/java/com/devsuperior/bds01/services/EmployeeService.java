package com.devsuperior.bds01.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.DepartmentRepository;
import com.devsuperior.bds01.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;

	public Page<EmployeeDTO> findAll(Pageable pageable) {
		Page<Employee> page = employeeRepository.findAll(pageable);
		return page.map(x -> new EmployeeDTO(x));
	}

	public EmployeeDTO insert(EmployeeDTO employee) {
		Employee entity = new Employee();
		copyDtoToEntity(entity, employee);
		entity = employeeRepository.save(entity);
		return new EmployeeDTO(entity);
	}
	
	private void copyDtoToEntity(Employee entity, EmployeeDTO employee) {
		Department department = new Department();
		department = departmentRepository.getOne(employee.getDepartmentId());
		entity.setName(employee.getName());
		entity.setEmail(employee.getEmail());		
		entity.setDepartment(department);
	}
}
