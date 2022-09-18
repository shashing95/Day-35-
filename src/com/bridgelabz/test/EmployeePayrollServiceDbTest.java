package com.bridgelabz.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.bridgelabz.EmployeePayrollData;
import com.bridgelabz.EmployeePayrollService;
import com.bridgelabz.EmployeePayrollService.IOService;

public class EmployeePayrollServiceDbTest {
	@Test
	public void givenEmployeePayrollDb_whenRetrived_shouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData=employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		assertEquals(3,employeePayrollData.size());
	}
	@Test
	public void givenNewSalaryForEmployee_whenUpdated_shouldSyncWithDb() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Xyz",50002.00);
		boolean result=employeePayrollService.checkEmployeePayrollSyncWithDb("Xyz");
		assertTrue(result);
	}
	@Test
	public void givenDateRange_whenRetrived_shouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate=LocalDate.of(2022,01,10);
		LocalDate endDate=LocalDate.now();
		List<EmployeePayrollData> employeePayrollData=employeePayrollService.readEmployeePayrollForDateRange(IOService.DB_IO,startDate,endDate);
		assertEquals(3,employeePayrollData.size());
	}
	@Test
	public void givenPayrollData_whenAverageSalaryRetrievedByGender_shouldReturnProperValue() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String,Double> averageSalryByGender=employeePayrollService.readAverageSalaryByGender(IOService.DB_IO);
		assertTrue(averageSalryByGender.get("F").equals(30001.00) && averageSalryByGender.get("M").equals(20000.00));
	}
	@Test
	public void givenNewEmployee_whenAdded_shouldSyncWithDb() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.addEmployeeToPayroll("Abc",5000.0,LocalDate.now(),"F");
		boolean result=employeePayrollService.checkEmployeePayrollSyncWithDb("Abc");
		assertTrue(result);
	}
	@Test
	public void givenNewEmployee_whenAdded_shouldAddedToTableAndSyncWithDb() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.addEmployeeToPayrollMultipleTable("Cab",12000.0,LocalDate.now(),"M");
		boolean result=employeePayrollService.checkEmployeePayrollSyncWithDb("Cab");
		assertTrue(result);
	}

}
