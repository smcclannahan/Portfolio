import employee

class Company:
    
    def __init__(self):
        self.employees = []
        
    def fire(self, employee):
        for emp in range(0,len(self.employees)):
            if employee.name == self.employees[emp].name:
                del self.employees[emp]
                print(f"{employee.name} has been fired")
                break
                
    
    def giveRaise(self, employee, newPay):
        for emp in range(0,len(self.employees)):
            if employee.name == self.employees[emp].name:
                self.employees[emp].perhour = newPay
                print(f"successfuly gave raise to {employee.name}")
                break
    
    def hire(self, employee):
        self.employees.append(employee)
        print(f"successfuly added {employee.name}")