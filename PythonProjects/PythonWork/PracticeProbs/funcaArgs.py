def myfunc(*args):
    #returns 5% of the sum of a and b
    return sum((args)) * 0.05

def myfunc2(**kwargs):
    if 'fruit' in kwargs:
        print("my fruit of choice is: {}".format(kwargs['fruit']))
    else:
        print("I did not find any fruit")
        
def myfunc3(*args, **kwargs):
    print("I would like {} {}".format(args[0], kwargs['food']))

print(myfunc(40,60,90,12,5213,51))

print(myfunc2(fruit = 'apple', veggie = 'lettuce'))

print(myfunc3(10,20,30, fruit = 'orange', food = 'eggs', animal = 'dog'))