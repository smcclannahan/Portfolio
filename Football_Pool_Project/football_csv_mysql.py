import csv


with open('Football_Pool_Project/schedule.csv', 'r') as schedule:
    csv_schedule = csv.reader(schedule)
    
    sql_start_statement = "INSERT INTO 'games'('gamenum', 'date', 'time', 'away', 'home', 'awayscore', 'homescore', 'spread', 'final', 'overunder') VALUES"

    sql_staements = []
    vals = []

    for row in csv_schedule:
        vals.append(", ".join(row))

    for data in vals:
        sql_start_statement += "(" + data + "),"
        
    with open('Football_Pool_Project/sql_statement.txt', 'a') as sql_file:
        sql_file.writelines(sql_start_statement)
