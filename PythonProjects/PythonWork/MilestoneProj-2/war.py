import cards
import player

game_deck = cards.Deck()
player_one = player.Player("One")
player_two = player.Player("Two")
game_on = True

game_deck.shuffle()
for num in range(26):
    player_one.add_cards(game_deck.deal_one())
    player_two.add_cards(game_deck.deal_one())

round_num = 0
war = False
while game_on:
    
    round_num += 1
    print(f"Round {round_num}")
    
    if len(player_one.all_cards) == 0:
        print('Player two has Won!')
        game_on = False
        break
    
    if len(player_two.all_cards) == 0:
        print('Player one has Won!')
        game_on = False
        break
    
    #New Round
    player_one_cards = []
    player_one_cards.append(player_one.remove_one())
    
    player_two_cards = []
    player_two_cards.append(player_two.remove_one())
    
    war = True
    while war:
        if player_one_cards[-1].value > player_two_cards[-1].value:
            player_one.add_cards(player_one_cards)
            player_one.add_cards(player_two_cards)
            war = False
            
        elif player_one_cards[-1].value < player_two_cards[-1].value:
            player_two.add_cards(player_one_cards)
            player_two.add_cards(player_two_cards)
            war = False
        else:
            print("It's War!")
            
            if len(player_one.all_cards) < 5:
                print("Player one doesn't have enough cards for war")
                print("Player two wins")
                game_on = False
                break
            elif len(player_two.all_cards) < 5:
                print("Player two doesn't have enough cards for war")
                print("Player one wins")
                game_on = False
                break
            else:
                for num in range(0,5):
                    player_one_cards.append(player_one.remove_one())
                    player_two_cards.append(player_two.remove_one())
        
            
    
    
    
    
    
    
