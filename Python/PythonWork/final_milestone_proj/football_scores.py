from selenium import webdriver
from selenium.webdriver.common.by import By

driver = webdriver.Firefox()

driver.get("https://www.livesport.com/en/nfl/#/f3Ed5KM2/table/overall")

games = []

homeTeam = driver.find_elements(By.CLASS_NAME, "event__participant--home")
awayTeam = driver.find_elements(By.CLASS_NAME, "event__participant--away")
homeScore = driver.find_elements(By.CLASS_NAME, "event__score--home")
awayScore = driver.find_elements(By.CLASS_NAME, "event__score--away")


for game in range(0, len(homeTeam)):
    games.append((homeTeam[game].text,awayTeam[game].text,homeScore[game].text,awayScore[game].text))
    
print(games[0])

driver.close()