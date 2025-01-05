# Anemoi: WeatherApp

Anemoi is an Android application that provides weather information and music suggestions based on the current weather conditions. The app uses the OpenWeatherMap API to fetch weather data and the Spotify API to suggest music playlists.

## Features

- Display current weather information including temperature, humidity, wind speed, and cloudiness.
- Suggest music playlists based on the current weather conditions.
- Interactive map to show weather information for different locations.
- Fetch weather data based on the user's current location or a specified city.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/WeatherApp.git
    ```
2. Open the project in Android Studio.
3. Build the project to install the necessary dependencies.

## Setup

1. Obtain an API key from [OpenWeatherMap](https://openweathermap.org/api) and replace the `api_key` variable in `MainActivity.java` with your API key.
2. Obtain a client ID from [Spotify Developer Dashboard](https://developer.spotify.com/dashboard/applications) and replace the `client_id` variable in `MainActivity.java` with your client ID.
3. Set the redirect URI in the Spotify Developer Dashboard to `https://localhost:8888/callback`.

## Usage

1. Run the application on an Android device or emulator.
2. Allow location permissions to fetch weather data based on your current location.
3. Use the search bar to enter a city name and fetch weather data for that city.
4. Tap on the music icon to get music suggestions based on the current weather conditions.
5. Interact with the map to get weather information for different locations by tapping on the map.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.