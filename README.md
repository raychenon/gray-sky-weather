[![CircleCI](https://circleci.com/gh/raychenon/gray-sky-weather.svg?style=svg)](https://circleci.com/gh/raychenon/gray-sky-weather)

# Gray Sky
☀️ The app that makes your mood affect the weather.

![Oops 🧐](./images/gray_sky_locations.jpg)

## ☀️ About
Gray Sky Weather is the app to compare the closest locations weather on the same timeline.
At first, this project is part of the interview process at [Shadow](https://shadow.tech) and the initial inspirations come from [Sunly](https://github.com/carayolthomas/Sunly-Objc)

The name Gray Sky is after [Dark Sky got bought by Apple](https://blog.darksky.net/).
Of course, I am still hoping to join the [Shadow](https://shadow.tech) 💻
I plan to publish on the Google Play store and improve the project.

## 🏘️ Architecture
I use **MVVM**, which is de facto Clean Architecture promoted by Google.
Each location weather forecast is self contained in a fragment `WeatherForecastFragment`, responsible to request its data( weather forecast and reverse geocoding).
If the weather forecast were constantly updated, the references to this Fragment would be kept at the parent level(`MainActivity`) to coordinate.
The coroutines/LiveData are a good replacement for RxJava's SingleObserver.

## 🕵️‍♂️ How to test 
All the locations are hardcoded in `GlobalConstants.CITIES`

## 🦁 TODO
 - add the feature "user's current geolocation" . It needs an user flow(permission accepted, permission denied)
 - Unit testing
 - UI testing

## 🎁 Licence
This app is released under the [MIT License](https://github.com/raychenon/gray-sky-weather/blob/master/LICENSE).