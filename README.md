# Farshid Abazari

# Getir Mobile Assignment

It's an app built using the MVVM, Repository Pattern and Clean Architecture.

## Index

- [Key Features](#key-features)
- [Architecture](#architecture)
- [Testing Strategy](#testing-strategy)
- [Libraries](#libraries)

## Key Features
1. **Separation of Concerns:** The app is built using Uncle Bob's clean architecture (see architecture section below)
2. **Less management code** left in activities and fragments by using ViewModels and DataBinding
3. **Dependency manager** Build a module in order to separate all needed dependencies and constants in a reusable module

## Architecture
The app is built with scalability in mind. To maintain the separation of concerns, [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) by Robert C. Martin, is used. The diagram below shows how the clean architecture is mapped to this android application


### Flow of control:
Flow of control is one directional only. Any inner layer doesn't know about any layer outside of it. If an inner layer needs to communicate to an outer layer, it is done via Dependency Inversion.

To maintain this separation over the course of time, separate android/kotlin modules have been used for each layer.


### Domain Module

This is a pure Kotlin module. As such, it is platform independent and can be reused anywhere. Domain module contains:

- **Business Models** - POJOs
- **Data Source Interfaces** - This definition of data sources allows the app to substitute the DB or remote implementations any time without breaking the logic.
- **Repositories** - No matter how the data sources are implemented (db, in memory, in-file), Repository has the business logic to choose the right data source for returning the data.
- **Use Cases** - These represent the user interactions with the system and contain business logic.

Note: Domain is totally unaware about the rest of the system.

### Data Module

Data module contains local data source implementations. It implements all the data source interfaces provided by the domain module.

The data module has separate data models for each data source. It is the responsibility of data module to transform this data to business model before returning it back. This ensures that actual implementation of the data sources can be changed any time without affecting the rest of the system.


### Presentation Module
This module contains all the platform specific, i.e. Android, code. It is responsible for User Interface and handling user actions.

Presentation module is dependent on both domain and data modules. Presentation module has its own UI models but when triggering a use case, presentation module will always use business model objects.

It is the job of presentation module to provide the data module implementations to domain module for business logic execution.


## Testing Strategy

The data module has the boundary component of our application, Room Database:
- DB is tested using **InMemory RoomDB** mock provided by Android.


### Testing Libraries:
- **JUnit4** - Unit testing framework
- **MockK** - Creating test doubles
- **InMemory Room DB** - For mocking local database
- **Google Truth** - For fluent, readable assertions
- **Turbine** - For easier testing of Kotlin flows.


## Libraries

- **Material Design** - UI design
- **AndroidX** - ViewModel, LiveData
- **KotlinX** - Coroutines, Flow, StateFlow, Serialization
- **Hilt** -  Dependency Injection
- **Navigation Component** - User navigation
- **Glide** - Loading Images
- **Room** - Database Storage


## Future Enhancements
These are the enhancements that are important but were left because of time constraints:

### Testing
1. Test coverage of presentation module such as Fragment and some logics.
2. Move helper classes and custom components to another sub-module.
3. Move UI logics from fragment to DataBinding
4. Better UX to get image url from usr
5. As we are passing data between fragment, it's possible to implement shared element transition

