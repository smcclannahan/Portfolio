import csv
from datetime import datetime

def team_number_convert(team_str, bye):
    if bye == 1:
        switch={
        'SanFrancisco':0,
        'Chicago':1,
        "Cincinnati":2,
        "Denver":3,
        'Cleveland':4,
        "TampaBay":5,
        "Buffalo":6,
        "Arizona":7,
        "L.A.Chargers":8,
        'KansasCity':9,
        "Indianapolis":10,
        "Dallas":11,
        "Miami":12,
        "Philadelphia":13,
        "Atlanta":14,
        "N.Y.Giants":15,
        "Jacksonville":16,
        "N.Y.Jets":17,
        "Detroit":18,
        "GreenBay":19,
        "Carolina":20,
        "NewEngland":21,
        "LasVegas":22,
        "L.A.Rams":23,
        "Baltimore":24,
        "Washington":25,
        "NewOrleans":26,
        "Seattle":27,
        "Pittsburgh":28,
        "Houston":29,
        "Tennessee":30,
        "Minnesota":31
        }
        return switch.get(team_str, "Something's gone wrong") 
    else:
        switch={
            " San Francisco 49ers":0,
            " Chicago Bears":1,
            " Cincinnati Bengals":2,
            " Denver Broncos":3,
            " Cleveland Browns":4,
            " Tampa Bay Buccaneers":5,
            " Buffalo Bills":6,
            " Arizona Cardinals":7,
            " Los Angeles Chargers":8,
            " Kansas City Chiefs":9,
            " Indianapolis Colts":10,
            " Dallas Cowboys":11,
            " Miami Dolphins":12,
            " Philadelphia Eagles":13,
            " Atlanta Falcons":14,
            " New York Giants":15,
            " Jacksonville Jaguars":16,
            " New York Jets":17,
            " Detroit Lions":18,
            " Green Bay Packers":19,
            " Carolina Panthers":20,
            " New England Patriots":21,
            " Las Vegas Raiders":22,
            " Los Angeles Rams":23,
            " Baltimore Ravens":24,
            " Washington Commanders":25,
            " New Orleans Saints":26,
            " Seattle Seahawks":27,
            " Pittsburgh Steelers":28,
            " Houston Texans":29,
            " Tennessee Titans":30,
            " Minnesota Vikings":31
            }
        return switch.get(team_str, "Something's gone wrong")
    #SDG = 8 #come back to this one

#schedule = open('Football_Pool_Project/schedule.csv', 'w', newline="")
#csv_schedule = csv.writer(schedule)
bye_teams = open("Football_Pool_Project/bye_weeks.csv", 'w', newline="")
bye_writer = csv.writer(bye_teams)

#Start Parsing
data_file = open("Football_Pool_Project/output.txt", 'r')
lines = data_file.readlines()


home_check = 0
Byes = []
cur_week = 0
game_of_week = 1
final_gameTime = ""
gameDay = ""
home_team = ""
away_team = ""
for line in lines: 
    if "Week" in line:
        cur_week = int(line[7:9]) #* 100
        gameNum = cur_week + game_of_week
        #print(cur_week)
    
    if "Bye" in line:
        team_num = 0
        byes_week = "".join(line.split())
        byes_week = byes_week[4:].split(",")
        for team in byes_week:
            byes_week[team_num] = team_number_convert(team, 1)
            team_num += 1
        while(len(byes_week) < 5):
            byes_week.append(32)
        bye_writer.writerow([cur_week, byes_week])
        
bye_teams.close()
    
 
#        
#    elif "Date" in line or "Time (ET)" in line or "Away Team" in line or "Bye" in line or "Home Team" in line or "This game is being played in" in line or line == " \n":
#        pass
#    
#    elif "Thu" in line or "Fri" in line or "Mon" in line or "Sun" in line or "Sat" in line:
#        year = 0
#        cur_day = line[5:]
#        cur_day = cur_day.split("\n")
#        if "Jan" in line:
#            year = 2024
#        else:
#            year = 2023
#        cur_day = cur_day[0] + ", " + str(year)
#        d = datetime.strptime(cur_day, '%b %d, %Y')
#        gameDay = d.strftime('%Y-%m-%d')
#        
#    elif " pm" in line or " am" in line:
#        gameTime = line.split(" ")
#        gameTime = gameTime[0].split(":")
#        gameTime[0] = int(gameTime[0]) - 1 #cst conversion
#        if gameTime[0] == 0:
#            gameTime[0] = 12
#        gameTime[1] = int(gameTime[1])
#        if " pm" in line:
#            if gameTime[0] == 12:
#                pass
#            else:
#                gameTime[0] += 12
#        if gameTime[1] == 0:
#            final_gameTime = str(gameTime[0]) + ":" + str(gameTime[1]) + "0:" + "00"
#        else:
#            final_gameTime = str(gameTime[0]) + ":" + str(gameTime[1]) + ":" + "00"
#        
#        t = datetime.strptime(final_gameTime, '%H:%M:%S')
#        final_gameTime = t.strftime('%H:%M:%S')
#        
#    else:
#        if home_check == 0:
#            away_team = line.split("\n")
#            away_team = away_team[0]
#            home_check = 1
#        else:
#            home_team = line.split("\n")
#            home_team = home_team[0]
#            home_check = 0
#        #print(line)
#    
#    if home_team != "" and away_team != "":
#        csv_schedule.writerow([gameNum , gameDay, final_gameTime, team_number_convert(away_team, 0), team_number_convert(home_team, 0), 0, 0, 0, 0, 0])
#        home_team = ""
#        away_team = ""
#        gameNum += 1
#csv_schedule.writerow(['gamenum, date, time, away, home, awayscore, homescore, spread, final, overunder'])
data_file.close()
#schedule.close()