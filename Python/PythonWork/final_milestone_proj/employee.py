class Employee:
    def __init__(self, name, hours, perhour):
        self.name = name
        self.hours = hours
        self.perhour = perhour
    
    def pay(self):
        raise NotImplementedError
    
class HourlyEmployee(Employee):
    
    def pay(self):
        print("Current Pay is: " + str(self.hours * self.perhour)) 
        return self.hours * self.perhour

class SalariedEmployee(Employee):
    
    def pay(self):
        print("Current Pay is: " + str(40 * self.perhour))
        return 40 * self.perhour

class Manager(Employee):
    
    def pay(self):
        bonusPay = self.perhour * 5
        print("Current Pay is: " + str(40 * self.perhour + bonusPay))
        return 40 * self.perhour + bonusPay

class Executive(Employee):
    
    def pay(self):
        bonusPay = self.perhour * 20
        print("Current Pay is: " + str(40 * self.perhour + bonusPay))
        return self.perhour * 40 + bonusPay