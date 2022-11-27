import { Component, OnInit, Input } from '@angular/core';

import { Employee } from '../employee';
import { EmployeeService } from '../services/employee.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.css'],
})
export class EmployeeDetailComponent implements OnInit {
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
    this.employeeService.getEmployee(this.id!).subscribe(
      (data) => {
        console.log(data);
        this.employee = data;
      },
      (error) => console.log(error)
    );
  }

  gotoList() {
    this.router.navigate(['/']);
  }
}
