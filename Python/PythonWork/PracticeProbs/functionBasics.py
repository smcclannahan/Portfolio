def hello_World():
    #basic bitch function
    return "Hello World"

def hello_Name(name):
    #basic function with param
    return "Hello " + name

def calculator(num1, num2, op):
    #math function
    if(op == '+'):
        return float(num1 + num2)
    elif(op == '-'):
        return float(num1 - num2)
    elif(op == '*'):
        return float(num1 * num2)
    elif(op == '/'):
        return float(num1 / num2)
    else:
        return "invalid operator"
    
def isEven(num):
    #basic boolean function
    return num % 2 == 0

def onlyEvenList(num_list):
    #adding stuff to a list in a function
    even_nums = []
    for num in num_list:
        if num % 2 == 0:
            even_nums.append(num)
        else:
            pass  
    return even_nums 

def employee_check(work_hours):
    #tuple unpacking
    current_max = 0
    employee_of_month = ''
    
    for name, hours in work_hours:
        if hours > current_max:
            current_max = hours
            employee_of_month = name
        else:
            pass    
    return (employee_of_month, current_max)



work_hours = [('Abby', 100), ('Billy', 400), ('Cassie', 800)] 
        
print(hello_World())
print(hello_Name("Sean"))
print(calculator(5, 8, '/'))
print(isEven(40))
print(onlyEvenList([1,3,5,7,9]))
print(employee_check(work_hours))