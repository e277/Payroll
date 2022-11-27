import { Component, OnInit } from '@angular/core';

import { Employee } from '../employee';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../services/employee.service';

@Component({
  selector: 'app-employee-update',
  templateUrl: './employee-update.component.html',
  styleUrls: ['./employee-update.component.css'],
})
export class EmployeeUpdateComponent implements OnInit {
  id!: number;
  employee!: Employee;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    this.employee = new Employee();
    this.id = this.route.snapshot.params['id'];
    this.employeeService.getEmployee(this.id).subscribe(
      (data) => {
        console.log(data);
        this.employee = data;
      },
      (error) => console.log(error)
    );
  }

  updateEmployee() {
    this.employeeService.updateEmployee(this.id, this.employee).subscribe(
      (data) => {
        console.log(data);
        this.employee = new Employee();
        this.gotoList();
      },
      (error) => console.log(error)
    );
  }

  onSubmit() {
    this.updateEmployee();
  }

  gotoList() {
    this.router.navigate(['/']);
  }
}
