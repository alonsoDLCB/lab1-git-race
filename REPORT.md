# Lab 1 Git Race -- Project Report

This note uses the same disclosure fields as the group-project **AI use (10%)** slice. Lab 1 is still **limited**: assistive GenAI only — not a full or substantial generated solution. The project will later expect agents plus `AGENTS.md` and one skill; you do **not** need those here.

Do not invent a percentage of “AI vs original” lines. Empty or fake disclosure fails this lab.

## What I specified

I decided to add an in-memory log to keep track of the greetings. The application saves the name and the exact time of each visit. I know it works because the homepage displays a list of visitors that grows every time I provide a new name in the URL (using the ?name= parameter), and the unit tests pass successfully.

## What I changed

-HelloController.kt: I added a GreetingLog data class and a mutableListOf to store the history of visits. I also modified the welcome function to save new entries to this list and pass it to the model.

-welcome.html: Added a new section below the main message using Bootstrap classes and a Thymeleaf th:each loop to display the memory logs.

-HelloControllerUnitTests.kt: Created a new unit test that calls the controller with a specific name and checks if the memory list increases its size to 1.

## Technical decisions

I chose to use a simple Kotlin data class for the log entries because it is clean and doesn't need getters or setters.

I added an if (name.isNotBlank()) validation before saving the log. I decided to reject saving empty names because I noticed that the LiveReload background pings were filling my memory list with "Anónimo".

I decided to pass the whole list directly to the Thymeleaf model to render it on the server, instead of making a JavaScript Fetch request from the frontend.

## How I verified

I verified the visual part manually by running gradlew bootRun and checking the browser. Then, I used gradlew check to verify the logic.

At first, the tests failed completely with a ClassNotFoundException for all test files. I discovered this was a Gradle issue in Windows caused by the Spanish accent mark in my folder path ("Ingeniería Web"). I fixed it by closing the terminal, renaming my local folder to "Ingenieria Web" without the accent, and running gradlew clean check. After that, everything passed successfully.

## AI disclosure

Fill **either** the list **or** the no-AI line.

- **Tools / skills:** Gemini
- **Purpose:** To understand the Spring Boot file structure, learn how to loop through and check lists in Thymeleaf, and understand how to handle Kotlin warnings in my tests using @Suppress("UNCHECKED_CAST").
- **Representative prompts:** "¿Cómo recorro una lista en HTML usando Thymeleaf y cómo muestro un mensaje si está vacía?", "¿Cómo extraigo la lista del Model en el test unitario sin que me salga un warning de 'unchecked cast'?".
- **Affected files/sections:** welcome.html (Thymeleaf th:each, th:if, and th:unless tags), HelloControllerUnitTests.kt (suppressing the cast warning).
- **Validation steps:** Manual verification in the browser (http://localhost:8080) and automated verification using gradlew check.
- **Citations:** … (external snippets you adapted)
- **Human-reviewed:** I reviewed the Thymeleaf code to understand my doubts: I learned that th:if and th:unless are used to render different HTML parts depending on if the list is empty or not. The AI suggested using @Suppress("UNCHECKED_CAST") to fix the test warning, and I kept it because I understood it just tells the compiler to trust the type of the list when we extract it from the Model object.

