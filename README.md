# Funny Combination - Android Game

This is an Android game where players must repeat a sequence of emojis. It is developed using Jetpack Compose and a clean MVVM architecture with three layers (Data, Domain, Presentation) and Dependency Injection via Dagger Hilt.

## Functionality

### Main Game
- The player sees a sequence of emojis that is displayed on the screen.
- The player must repeat the sequence correctly by selecting the corresponding emojis in the correct order.
- With each level, the sequence becomes longer, adding more emojis for the player to remember.
- The game ends when the player incorrectly repeats the sequence.

### High Score System
- The system stores only the **3 highest scores**
- The score list shows the date and the result for each entry.
- The high score list is automatically updated when a new record is achieved.
- There is an option to clear all high scores if desired.

### Game Over Screen
- Displays the player's final score at the end of the game.
- Informs the player whether their score made it to the high score list.
- Includes a visually appealing design with animations to make the game over experience engaging.
- Buttons are provided for the player to either "Play Again" or return to the "Main Menu."

## Video Walkthrough


![FunnyCombinationGameVideo](https://github.com/user-attachments/assets/0856e1ba-6dc1-4daa-9af3-39a13886d38c)




GIF created with [LiceCap](http://www.cockos.com/licecap/).

## License

    Copyright 2025 Nikita Lytvyn

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
