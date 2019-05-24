# Daifugo
This is console-based Daifugo game program.  
The program is stronger than you thought, maybe.

## How to Play
1. install Git & Java(JDK)
    - Java version should be 8 or later.
1. execute below commands in terminal
    ```bash
    git clone https://github.com/furimako/daifugo.git
    cd daifugo
    bash scripts/init.sh
    cd out
    java main/Daifugo
    ```

## What is Daifugo?
> Daifugo (大富豪, Grand Millionaire) or Daihinmin (大貧民, Extremely Poor) is a Japanese card game for three or more players played with a standard 52-card pack. The objective of the game is to get rid of all the cards one has as fast as possible by playing progressively stronger cards than those of the previous player. The winner is called the daifugō (the grand millionaire) earning various advantages in the next round, and the last person is called the daihinmin (the extreme indigent). In that following round, winners can exchange their one or more unnecessary cards for advantageous ones that losers have.

Daifugō. In Wikipedia, The Free Encyclopedia. Retrieved 08:17, May 24, 2019, from https://en.wikipedia.org/w/index.php?title=Daifug%C5%8D&oldid=896008542

### Special Rules
In this program, there are some special rules described in below.

* Yagiri
  * Playing 8 can end the trick immediately. The player who plays 8 leads the next trick.

* Bind
  * When cards of the same suit are played consecutively, all subsequent cards in that trick must also be of the same suit.

* Revolution
  * Playing 4 cards or more causes a revolution, which makes the strength of cards reversed until the end of the game.

## Sample Images
### Show Mode: off
![sample(show_off)](https://github.com/FullyHatter/daifugo/blob/master/images/sample_image.png)

### Show Mode: on
![sample(show_on)](https://github.com/FullyHatter/daifugo/blob/master/images/sample_image(show).png)
