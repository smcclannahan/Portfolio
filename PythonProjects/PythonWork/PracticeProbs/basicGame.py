from random import shuffle

def list_shuffle(mylist):
    shuffle(mylist)
    return mylist

def player_guess():
    guess = ''
    
    while(guess not in ['0', '1', '2']):
        guess = input("Pick a number: 0, 1, 2: ")
        
    return int(guess)

def check_guess(mylist,guess):
    if mylist[guess] == 'O':
        print("Correct!")
    else:
        print("incorrect...")
        print(mylist)
#initial list
mylist = [' ', 'O', ' ']

#shuffled list and user guess
shuffled_list = list_shuffle(mylist)
plGuess = player_guess()

#check if guess is correct
check_guess(mylist, plGuess)