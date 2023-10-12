import csv

with open("Football_Pool_Project/bye_weeks.csv", 'r') as byes:
    csv_byes = csv.reader(byes)
    
    sql_start_statement = "INSERT INTO 'idle'('week','idle1','idle2','idle3','idle4','idle5','idle6') VALUES"
    
    vals = []
    
    for row in csv_byes:
        vals.append(", ".join(row))
        
    for data in vals:
        sql_start_statement += "(" + data + "), "
    
    with open('Football_Pool_Project/bye_query.txt', 'a') as sql_file:
        sql_file.writelines(sql_start_statement)