package com.payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggreage root
    // tag::get-aggreate-root[]
    // @GetMapping("/api/v1/employees")
    // List<Employee> all() {
    // return repository.findAll();
    // }
    // end::get-aggreate-root[]

    // Using Hal to format HATEOAS
    @GetMapping("api/v1/employees")
    public CollectionModel<EntityModel<Employee>> all() {
        // List<EntityModel<Employee>> employees = repository.findAll().stream()
        // .map(employee -> EntityModel.of(employee,
        // linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
        // linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
        // .collect(Collectors.toList());

        // return CollectionModel.of(employees,
        // linkTo(methodOn(EmployeeController.class).all()).withSelfRel());

        // Using the Assembler
        List<EntityModel<Employee>> employees = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("api/v1/employees")
    public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

        EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single employee
    @GetMapping("api/v1/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {

        Employee employee = repository.findById(id) //
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        // return EntityModel.of(employee, //
        // linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
        // linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));

        // Using the Assemble
        return assembler.toModel(employee);
    }

    @PutMapping("api/v1/employees/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        Employee updatedEmployee = repository.findById(id) //
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                }) //
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("api/v1/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
