from selenium import webdriver
from selenium.webdriver.common.by import By
import csv

driver = webdriver.Firefox()

driver.get("https://www.livesport.com/en/nfl/#/f3Ed5KM2/table/overall")

games = []
scores = open('scores.csv', 'w')
csv_scores = csv.writer(scores)
csv_scores.writerow(['Home Team', 'Away Team', 'Home Score', 'Away Score'])

homeTeam = driver.find_elements(By.CLASS_NAME, "event__participant--home")
awayTeam = driver.find_elements(By.CLASS_NAME, "event__participant--away")
homeScore = driver.find_elements(By.CLASS_NAME, "event__score--home")
awayScore = driver.find_elements(By.CLASS_NAME, "event__score--away")


for game in range(0, len(homeTeam)):
    if(homeScore[game].text == '-' or awayScore[game].text == '-'):
        continue
    else:
        games.append((homeTeam[game].text,awayTeam[game].text,homeScore[game].text,awayScore[game].text))
        csv_scores.writerow([homeTeam[game].text,awayTeam[game].text,homeScore[game].text,awayScore[game].text])

driver.close()
scores.close()