# Movie Application

## Overview
This Android application is a comprehensive movie browsing platform developed as part of the Android Programming course for Spring 2024. It showcases a wide range of Android development best practices and modern architectural patterns.

## Features
- **Splash Screen**: Custom-designed introduction screen
- **User Registration**: Secure sign-up process with input validation
- **Home Screen**: Dynamic movie list with genre-based filtering and auto-scrolling slider
- **Movie Details**: In-depth information about each movie
- **Search Functionality**: Real-time search capabilities
- **Favorites**: Ability to save and view favorite movies
- **Responsive Design**: Supports various device sizes and orientations

## Technical Highlights
- **Architecture**: MVVM (Model-View-ViewModel)
- **Design Principles**: Adheres to SOLID principles
- **Networking**: Retrofit2 with OkHttp for API communication
- **Local Storage**: Room Database for persistent data
- **UI Components**: Fragments for modular UI design
- **Asynchronous Programming**: Coroutines for background tasks
- **Image Loading**: Glide library for efficient image handling
- **Animation**: Lottie for smooth, vector-based animations

## Challenges Overcome
1. **Real-time Search Implementation**: Developed an efficient algorithm to provide instant search results without overwhelming the API.
2. **Complex UI Layouts**: Created a responsive design that maintains integrity across various screen sizes and orientations.
3. **Data Persistence**: Implemented a robust local database system to store user favorites and cache movie data for offline access.
4. **Memory Management**: Optimized image loading and caching to prevent out-of-memory errors on devices with limited resources.
5. **Seamless User Experience**: Balanced between local data and API calls to ensure smooth navigation and quick load times.

## Future Enhancements
- Implement Dependency Injection using Hilt for improved modularity
- Add pagination support for movie listings to handle large datasets efficiently
- Introduce a dark theme option for better user experience in low-light conditions
- Implement more advanced animations for smoother transitions between screens

## Installation
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the application on an emulator or physical device

## Dependencies
- Retrofit2: REST API communication
- OkHttp: HTTP client for Retrofit
- Room: SQLite database abstraction
- Glide: Image loading and caching
- Lottie: Vector-based animations
- CircleImageView: Circular image views
- Coroutines: Asynchronous programming
- Lifecycle components: ViewModel and LiveData support

## Contributing
This project is part of an academic assignment and is not open for external contributions at this time.

## License
This project is for educational purposes only and is not licensed for commercial use.

## Acknowledgements
- Android Programming Course Instructors
- Open-source community for the invaluable libraries and tools