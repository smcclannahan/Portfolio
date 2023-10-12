import cards

class Player:
    def __init__(self, name, chips=0):
        self.name = name
        self.chips = chips
        self.all_cards = []
        
    def remove_one(self):
        return self.all_cards.pop(0)
    
    def add_cards(self, new_cards):
        #multiple cards
        if type(new_cards) == type([]):
            self.all_cards.extend(new_cards)
        #single card
        else:
            self.all_cards.append(new_cards)
    
    def bet(self, bet_amt):
        self.chips -= bet_amt
        
    def won_bet(self, bet_amt):
        self.chips += bet_amt
        
    def clear_hand(self):
        self.all_cards = []
    
    def __str__(self):
        return f'Player {self.name} has {self.chips}.'