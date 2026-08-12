# AI Usage

## Tools Used

AI tools were used throughout the development process as an engineering assistant.

### ChatGPT

Used for:

- Breaking the PRD into implementation steps.
- Planning the Android project architecture.
- Kotlin and Jetpack Compose implementation guidance.
- openFDA API integration.
- Debugging Kotlin and Gradle compilation errors.
- Designing repository and ViewModel tests.
- Reviewing edge cases around the FDA API.
- Drafting project documentation.

AI-generated code was reviewed, compiled, tested, and modified before being retained in the project.

## Effective AI Workflow

The most effective workflow was to provide the actual compiler error or project source code to the AI rather than asking it to guess the project structure.

For example:

1. Implement a small change.
2. Run `./gradlew test` or `./gradlew assembleDebug`.
3. Provide the exact compiler/test output.
4. Inspect the relevant project file.
5. Make the smallest correction.
6. Run the build/tests again.

This was particularly useful because the openFDA response models contain optional and inconsistent fields.

## Example of an AI Mistake

An AI-generated `FakeFdaApi` initially attempted to create an empty `FdaResponse` using:

`FdaResponse()`

However, the actual project model required two constructor parameters:

- `meta`
- `results`

The compiler reported:

`No value passed for parameter 'meta'`

and:

`No value passed for parameter 'results'`

I checked the actual `FdaResponse` definition in the project:

```kotlin
data class FdaResponse(
    val meta: FdaMeta?,
    val results: List<FdaDrug>
)