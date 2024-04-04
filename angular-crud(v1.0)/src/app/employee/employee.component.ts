import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Employee} from "../Employee";
import {EmployeeService} from "../employeeservice";

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {
  employees: Employee[] = [];
  employeeForm: FormGroup;
  editing = false;
  selectedEmployee: Employee | null = null;

  constructor(private fb: FormBuilder, private employeeService: EmployeeService) {
    this.employeeForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      address: ['', Validators.required],
      phoneNumber: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.fetchEmployees();
  }

  fetchEmployees() {
    this.employeeService.getEmployees().subscribe(
      (employees) => {
        this.employees = employees;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  editEmployee(employee: Employee) {
    this.editing = true;
    this.selectedEmployee = employee;
    this.employeeForm.patchValue(employee);
  }

  saveEmployee() {
    if (this.editing && this.selectedEmployee) {
      const updatedEmployee: Employee = {
        ...this.selectedEmployee,
        ...this.employeeForm.value
      };

      this.employeeService.updateEmployee(updatedEmployee).subscribe(
        () => {
          this.fetchEmployees();
          this.cancelEdit();
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      const newEmployee: Employee = this.employeeForm.value;

      this.employeeService.createEmployee(newEmployee).subscribe(
        () => {
          this.fetchEmployees();
          this.cancelEdit();
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }

  deleteEmployee(employeeId: number) {
    this.employeeService.deleteEmployee(employeeId).subscribe(
      () => {
        this.fetchEmployees();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  cancelEdit() {
    this.editing = false;
    this.selectedEmployee = null;
    this.employeeForm.reset();
  }
}
