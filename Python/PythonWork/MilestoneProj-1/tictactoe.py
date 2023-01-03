def display_Board(row1, row2, row3):
    print(f" {row1[0]} | {row1[1]} | {row1[2]}")
    print("----------")
    print(f" {row2[0]} | {row2[1]} | {row2[2]}")
    print("----------")
    print(f" {row3[0]} | {row3[1]} | {row3[2]}")
    
def win_Check(row1, row2, row3):
    playerXWin = ['X','X','X']
    playerOWin = ['O','O','O']
    possible_wins = [row1, row2, row3, #rows 
                     [row1[0], row2[0], row3[0]], [row1[1], row2[1], row3[1]], [row1[2], row2[2], row3[2]], #Colums
                     [row1[0], row2[1], row3[2]], [row3[0], row2[1], row1[2]]] #diagonals
    if playerXWin in possible_wins:
        print("Player 1 Wins!")
        return True
    elif playerOWin in possible_wins:
        print("Player 2 Wins!")
        return True
    else:
        return False
    
def player_Move(player, row1, row2, row3):
    #ask for row, than ask for column
    board = [row1,row2,row3]
    row = ''
    column = ''
    rowInt = 10
    columnInt = 10
    freeSpot = False
    while freeSpot == False:
        #Getting the row
        while row.isdigit() == False:
            row = input("Please give the row (0-2): ")
            if row.isdigit() == False:
                print("That is not a number, please enter a number between 0 and 2")
            elif int(row) not in range(0,3):
                print("Too high, please select a number between 0 and 2")
            else:
                rowInt = int(row)
        #getting the column
        while column.isdigit() == False:
            column = input("Please give the column (0-2): ")
            if column.isdigit() == False:
                print("That is not a number, please enter a number between 0 and 2")
            elif int(column) not in range(0,3):
                print("Too high, please select a number between 0 and 2")
            else:
                columnInt = int(column)
        #check if that spot is free
        if board[rowInt][columnInt] == ' ':
            freeSpot = True
            board[rowInt][columnInt] = player
        else:
            print("That space is occupied, please make a different selection")
            row = ''
            column = ''
    return board

def game_set_up():
    print("It's time for a real basic game of Tic-Tac-Toe!\n")
    return [[' ', ' ', ' '],[' ', ' ', ' '],[' ', ' ', ' ']]

def tie_check(row1,row2,row3):
    board = [row1,row2,row3]
    for item in board:
        if ' ' in item:
            return False
    print("It's a Tie!")
    return True
    

win = False
tie = False
player = 1
game_Board = game_set_up()
display_Board(game_Board[0],game_Board[1],game_Board[2])
while win == False and tie == False:
    if player == 1:
        print("It's your move, Player 1")
        game_Board = player_Move('X', game_Board[0], game_Board[1], game_Board[2])
        win = win_Check(game_Board[0],game_Board[1],game_Board[2])
        tie = tie_check(game_Board[0],game_Board[1],game_Board[2])
        player = 2
        
    elif player == 2:
        print("It's your move, Player 2")
        game_Board = player_Move('O', game_Board[0], game_Board[1], game_Board[2])
        win = win_Check(game_Board[0],game_Board[1],game_Board[2])
        tie = tie_check(game_Board[0],game_Board[1],game_Board[2])
        player = 1
    
    else:
        pass
    display_Board(game_Board[0],game_Board[1],game_Board[2])

print("Thanks for playing!")

