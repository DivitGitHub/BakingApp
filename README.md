# BakingApp

Here is an android application for the Udacity Baking App project. This application shows a list of recipes with their respective ingredients and videos.

## What have I learnt?
In this project I've learnt:

- Use of MediaPlayer/Exoplayer to display videos.
- Handle error cases in Android.
- Add a widget to my app experience.
- Leverage a third-party library in my app.
- Use of Fragments to create a responsive design that works on phones and tablets.

## PROJECT SPECIFICATION

### General App Usage
- App should display recipes from provided network resource.
- App should allow navigation between individual recipes and recipe steps.
- App uses RecyclerView and can handle recipe steps that include videos or images.
- App has a companion homescreen widget.
- Widget displays ingredient list for desired recipe.

### Components and Libraries
- Application uses Master Detail Flow to display recipe steps and navigation between them.
- Application uses Exoplayer to display videos.
- Application properly initializes and releases video assets when appropriate.
- Application should properly retrieve media assets from the provided network links. It should properly handle network requests.

### UI Testing
- Application makes use of Espresso to test aspects of the UI.

## Libraries used
- ExoPlayer
- Picasso
- Espresso
- Butterknife
- RecyclerView

## Screenshots
### Phones
![screenshot_1528328589 1](https://user-images.githubusercontent.com/26686429/41075333-14ff78ca-6a04-11e8-9cf3-53f592ceca38.png)
![screenshot_1528328593](https://user-images.githubusercontent.com/26686429/41075364-431bba70-6a04-11e8-8262-3a7912d838c4.png)
![screenshot_1528328596](https://user-images.githubusercontent.com/26686429/41075366-439e412a-6a04-11e8-90aa-a595e7aeab70.png)
![screenshot_1528328616](https://user-images.githubusercontent.com/26686429/41075368-443d9c48-6a04-11e8-99c0-913a6859adb0.png)
### Tablets
![screenshot_1528328904](https://user-images.githubusercontent.com/26686429/41075371-451d76f6-6a04-11e8-8bdb-11e51e3df08f.png)

## License

    Copyright 2018 Divit Bui

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
