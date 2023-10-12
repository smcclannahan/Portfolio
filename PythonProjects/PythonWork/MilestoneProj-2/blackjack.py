import cards
import player

def bust_check(handCards):
    total = 0
    for card in handCards:
        total += card.value
    return total >= 22

def printHand(handCards):
    for card in handCards:
        print(card)
        
def handSum(handCards):
    total = 0
    for card in handCards:
        total += card.value
    return total

def showdown(hand1, hand2):
    total1 = 0
    for card in hand1:
        total1 += card.value
    total2 = 0
    for card in hand2:
        total2 += card.value
        
    if total1 > total2:
        return hand1
    else:
        return hand2
    
def blackJack_Check(hand):
   if hand[0].rank != hand[1].rank:
       return hand[0].rank == "Queen" or hand[0].rank == "King" or hand[0].rank == "Jack" or hand[0].rank == "Ace" and hand[1].rank == "King" or hand[1].rank == "Queen" or hand[1].rank == "Jack" or hand[1].rank == "Ace"
   else:
       return False
            



dealer = player.Player("Dealer")
player_one = player.Player("Player", 1000)

print("Welcome to basic BlackJack!")
print(player_one)

while player_one.chips != 0:
    
    player_one.clear_hand()
    dealer.clear_hand()
    dealingDeck = cards.Deck()
    dealingDeck.shuffle()
    
    bet_amount = ''
    bet_int = 0
    decision = ''
    
    print("How much will your bet be?")
    print(player_one)
    
    #betting stuff
    while bet_amount.isdigit() == False:
        bet_amount = input("Enter a number here: ")
        if bet_amount.isdigit() == False:
            print("That is not a number, please try again.")
            continue
        elif int(bet_amount) > player_one.chips:
            print(f"You don't have that many chips, you currently have {player_one.chips}. Plrease try again.")
            bet_amount = ''
            continue
        elif int(bet_amount) == 0:
            print(f"You can't bet 0, you have: {player_one.chips}. Plrease try again.")
            bet_amount = ''
            continue
        else:
            bet_int = int(bet_amount)
            player_one.bet(bet_int)
            
    bet_amount = ''
    for card in range(0,2):
        dealer.add_cards(dealingDeck.deal_one())
        player_one.add_cards(dealingDeck.deal_one())
        

    print(f"Dealer: {dealer.all_cards[0]}\n")
    print(f"Your hand: {player_one.all_cards[0]} and {player_one.all_cards[1]}\n")
    blackJack_Check(player_one.all_cards)
    
    while decision == '':
        print("Will you hit or stay?")
        decision = input("Enter (H/h) for hit or (S/s) for stay: ")
        
        if decision.lower() == 'h': #hit
            decision = ''
            player_one.add_cards(dealingDeck.deal_one())
            printHand(player_one.all_cards)
            if bust_check(player_one.all_cards) == True:
                print("Busted!")
            else:
                pass
                
        elif decision.lower() == 's': #stay
            print("Then it's the Dealer's turn")
            if handSum(dealer.all_cards) < 17:
                while handSum(dealer.all_cards) < 16:
                    dealer.add_cards(dealingDeck.deal_one())
                    print(f"The dealer has drawn: {dealer.all_cards[-1]}\n")
                    if bust_check(dealer.all_cards) == True:
                        print("The dealer has busted! You win!")
                    else:
                        pass
                if bust_check(dealer.all_cards) == True:
                    print("The dealer has busted! You win!")
                    
                    
            print("It's showtime!")
            print("Your hand: \n")
            printHand(player_one.all_cards)
            print("The Dealer's hand: \n")
            printHand(dealer.all_cards)
            if showdown(player_one.all_cards, dealer.all_cards) == player_one.all_cards:
                print(f"You win! You won: {bet_int * 2}")
                player_one.won_bet(bet_int * 2)
                
            else:
                print("The dealer wins.")
                
        
        else:
            print("That is not a valid command")
            decision = ''


