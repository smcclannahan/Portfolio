import csv

schedule = open('Football_Pool_Project/schedule.csv', 'r')
csv_schedule = csv.reader(schedule)
new_list = []

for row in csv_schedule:
    new_list.append(", ".join(row))
    
print(new_list[0:5])