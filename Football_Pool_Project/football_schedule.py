from selenium import webdriver
from selenium.webdriver.common.by import By
import csv
from datetime import datetime


driver = webdriver.Firefox()

driver.get("https://fftoday.com/nfl/schedule.php")

games = []
schedule = open('schedule.csv', 'w')
#csv_schedule = csv.writer(schedule)
#csv_schedule.writerow(['gamenum, date, time, away, home, awayscore, homescore, spread, final, overunder'])

#Gathering data from schedule website
table = driver.find_element(By.XPATH, "/html/body/center/table[2]/tbody/tr[2]/td[1]/table[4]/tbody/tr/td/table")

allRows = table.find_elements(By.TAG_NAME, "tr")

for row in allRows:
    cells = row.find_elements(By.TAG_NAME, "td")
    
    for cell in cells:
        with open("Football_Pool_Project/output.txt", "a") as f:
            print(cell.text, file = f)
#Don't need website anymore
driver.close()