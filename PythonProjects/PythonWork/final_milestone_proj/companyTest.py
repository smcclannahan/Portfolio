import company
import employee

fake = company.Company()

Steve = employee.HourlyEmployee('Steve', 24, 15)
John = employee.SalariedEmployee('John', 40, 40)
Xavier = employee.Manager('Xavier', 40, 100)
Boss = employee.Executive('Boss', 40, 1000)

newHires = [Steve, John, Xavier, Boss]

for emps in range(0, len(newHires)):
    fake.hire(newHires[emps])
    
fake.giveRaise(Steve, 17)
fake.fire(Steve)

print(fake.employees)

