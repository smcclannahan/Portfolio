import random
def readFile(filename):
    x = 0
    fileObj = open(filename, "r")
    words = fileObj.read().splitlines()
    fileObj.close()
    return words
def compare_string(str1, str2):
    i = 0
    j = 0
    correctSpots = 0
    for x in str1:
        for y in str2:
            if str1[i] == str2[j] and i == j:
                print(str2[j] + " is in the right spot!")
                correctSpots += 1
                if correctSpots == 5:
                    return True
            elif str1[i] == str2[j] and i != j:
                print(str2[j] + " is in the word!")
            j += 1
            if j >= len(str2):
                j = 0
                break
        i += 1
        if i >= len(str1):
            i = 0
            break   
    return False
            
print("Welcome to Sean's version of not-wordle!\nYou will be guessing a 5-letter word, who could've guessed!\n")
words = readFile("words.txt") 
word = words[random.randrange(0,len(words)-1)]
guess_count = 6

guess = raw_input('Type your first guess now (you have 5 guesses): ')
if guess == "Shaun\r":
    print("Hacker mode active, word is: " + word)
    guess = raw_input('Cheater, put the word here: ')
result = compare_string(word, guess)

while result is False:
    guess_count -= 1
    if guess_count == 0:
        break
    print("Remaning Guesses: " + str(guess_count))
    guess = raw_input('Try Again: ')
    result = compare_string(word, guess)
 

if guess_count > 0:
    print("You guessed it right!")
else:
    print("The word was: " + word)

