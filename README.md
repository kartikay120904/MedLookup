# MedLookup

MedLookup is a small Android medicine lookup application built with **Kotlin** and **Jetpack Compose**.

The application allows users to search medicine brand names and view official FDA drug-label information using the public **openFDA Drug Label API**.

The project was developed as a timeboxed Android take-home assignment, with the focus placed on completing the core requirements, handling imperfect API data, supporting offline behavior, and providing automated tests.

---

## Features

### Medicine Search

- Search medicines by brand name.
- Search as the user types.
- 400 ms debounce prevents excessive API requests.
- Search requests are only triggered after at least two characters.
- Results are displayed in a vertically scrollable list.
- Each result displays:
    - Brand name
    - Generic name
    - Manufacturer
- Missing FDA fields are handled safely.
- Loading state is displayed while searching.
- Empty state is displayed when no medicines are found.
- Error state includes a retry action.

### Medicine Details

Selecting a medicine opens a detail screen containing available FDA label information.

The detail screen can display:

- Brand name
- Generic name
- Manufacturer
- Route
- Product type
- Purpose
- Indications and usage
- Dosage and administration
- Warnings
- Do not use
- Stop use
- Active ingredients
- Inactive ingredients
- Storage and handling

Only sections for which data is available are displayed.

Long text is vertically scrollable.

A medical disclaimer is displayed on the detail screen.

### Offline Support

The application stores successful medicine search results locally using **Android DataStore Preferences**.

When a network request fails:

- If cached results exist for the requested query, the application displays those saved results.
- The UI indicates that the displayed results are offline/cached data.
- If no cached results exist, an error state with retry is displayed.

The cache is persistent and is not dependent on the application remaining in memory.

### Navigation

The application contains two primary screens:

```text
Search Screen
     ↓
Medicine Detail Screen

#Architecture
                                                            
┌─────────────────────────────┐
│      Jetpack Compose UI     │
│ Search Screen / Detail      │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│         ViewModel           │
│ Search state / debounce     │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│         Repository          │
│ Remote + local coordination │
└──────────────┬──────────────┘
               │
        ┌──────┴──────┐
        ▼             ▼
┌──────────────┐ ┌──────────────┐
│  openFDA API │ │   DataStore  │ 
│   Retrofit   │ │    Cache     │
└──────────────┘ └──────────────┘