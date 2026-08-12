# MedLookup

A small Android medicine lookup application built with Kotlin and Jetpack Compose using the public openFDA drug label API.

## Features

- Medicine search
- Debounced search input
- FDA drug label data
- Medicine detail screen
- Empty and error states
- Offline cached results
- Persistent DataStore cache
- Back navigation to search results
- Unit tests for repository and ViewModel behavior

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Retrofit
- OkHttp
- Gson
- Kotlin Coroutines / Flow
- Android DataStore Preferences
- JUnit

## Architecture

```text
Compose UI
    ↓
ViewModel
    ↓
Repository
    ↓
 ┌───────────────┐
 │               │
FDA API       DataStore