import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs';
import { EmployeeService } from '../services/employee.service';
import { Employee } from '../employee';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css'],
})
export class EmployeeListComponent implements OnInit {
  id!: number;
  employees!: Observable<Employee[]>;

  constructor(
    private router: Router,
    private employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    this.employees = this.employeeService.getEmployee(this.id);
  }

  reloadData(): void {
    this.employees = this.employeeService.getEmployeesList();
  }

  deleteEmployee(id: number): void {
    this.employeeService.deleteEmployee(id).subscribe(
      (data) => {
        console.log(data);
        this.reloadData();
      },
      (error) => console.log(error)
    );
  }

  employeeDetails(id: number): void {
    this.router.navigate(['details', id]);
  }

  updateEmployee(id: number): void {
    this.router.navigate(['update', id]);
  }
}
