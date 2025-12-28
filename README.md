# Uno Server

Project @ MJT 2024 - 2025 @ FMI Sofia University "St. Kliment Ohridski"

Multiplayer server for playing the game Uno 

```
List ot commands:
-----------------------------------------------------------------
(*) Press Enter to refresh!
(*) register --username=<username> --password=<password> (registers an account)
(*) login --username=<username> --password=<password> (logs in an account)
(*) logout (logs out of an account)
(*) list-games --status=<started/ended/available/all> (lists all the game with the chosen status; when not given a status, it is all by default)
(*) create-game --game-id=<game-id> --number-of-players=<number> (creates a game; must have unique id; when not given number of players, it is 2 by default)
(*) join --game-id=<game-id> --display-name=<display-name> (joins a game; game must be available and not full; when not given display name, it is your username by default)
(*) start (starts the game you have created)
(*) show-hand (shows your hand)
(*) show-last-card (shows last played card)
(*) show-played-cards (shows all played cards in the game)
(*) play --card-id=<card-id> (plays a non-wild card from the your hand)
(*) play-choose --card-id=<card-id> --color=<red/green/blue/yellow> (plays a choose-color card from your hand; if a color is not chosen, it is the same as last card's)
(*) play-plus-four --card-id=<card-id> --color=<red/green/blue/yellow> (plays a plus-four card from your hand; if a color is not chosen, it is the same as last card's)
(*) draw (draws a card; you can only draw a card if you can't play any cards in your hand)
(*) accept-effect (accepts an effect given by another player)
(*) leave (leaves a game)
(*) spectate (spectates the game you have just won)
(*) summary --game-id=<game-id> (gets a ended game summary)
-----------------------------------------------------------------
```
