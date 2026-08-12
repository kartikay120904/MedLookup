# Engineering Decisions

## 1. Architecture

I used a lightweight layered architecture:

UI → ViewModel → Repository → Remote/Local data sources.

I avoided introducing a dependency injection framework because the assignment is intentionally small and timeboxed.

## 2. Search Strategy

Search input is debounced by 400 ms and queries are only sent after at least two characters.

I used an openFDA prefix wildcard query:

`openfda.brand_name:${query}*`

This allows useful results while the user is typing without making one request per keystroke.

## 3. Error Handling

A 404 from the FDA API is treated as an empty result rather than a generic application failure.

Network failures fall back to the last successful cached result when available.

If no cached result exists, the UI shows an error and retry action.

## 4. Offline Cache

I used Android DataStore Preferences to persist serialized medicine results.

The cache is accessed through a `MedicineCacheDataSource` abstraction so repository behavior can be tested without Android framework dependencies.

## 5. Detail Data

The detail screen uses the medicine data already returned by the search request rather than making another network request.

This keeps the detail screen responsive and reduces API traffic.

## 6. Testing

Repository dependencies are abstracted behind interfaces/fakes so unit tests do not depend on the live FDA API.

The ViewModel tests cover debounce behavior, minimum query length, success, empty, error, and cached-result states.

## 7. Scope

I intentionally did not implement features outside the PRD such as authentication, favorites, advanced filtering, complex animations, or additional screens because the assessment prioritizes a small finished and tested application.