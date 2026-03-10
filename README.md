# Catalist – Advanced Cat Breed Discovery & Trivia Engine

## Project Overview
**Catalist** is a high-performance Android application developed as a centerpiece for the **"Mobile Apps Development"** curriculum. The app is designed as a dual-module platform: a comprehensive digital encyclopedia for feline breeds and an interactive, data-driven trivia system.

Built with a focus on **Reactive Programming** and **Modern Android Architecture**, Catalist demonstrates a seamless integration of local data persistence and a fluid user interface.



---

## Technical Stack & Architecture
The project adheres to the highest standards of modern Android engineering:

* **Architecture Pattern:** Fully implemented using **Model-View-Intent (MVI)** for predictable state management and a unidirectional data flow.
* **UI Engine:** Developed with **Jetpack Compose**, leveraging **Material Design 3** for a sophisticated, adaptive interface.
* **Concurrency:** Powered by **Kotlin Coroutines** and **Flow** to handle asynchronous data streams and background tasks.
* **Data Management:** * **Jetpack Room:** A robust SQL abstraction layer for caching breed information.
    * **Jetpack DataStore:** Secure, reactive storage for user profiles and application preferences.
* **Dependency Injection:** Managed via **Hilt**, ensuring a modular, decoupled, and easily testable codebase.

---

## Core Application Modules

### **1. User Profile & Personalization**
* **Onboarding:** Automated local account initialization upon the first application launch.
* **Activity Hub:** A detailed profile dashboard that tracks user progress, including a comprehensive history of played quiz sessions.
* **Profile Customization:** Integrated tools for updating user details and tailoring the app experience.

### **2. Breed Intelligence (Discovery)**
* **Global Breed Directory:** An optimized list of all cat breeds, featuring real-time search filtering and high-resolution previews.
* **Detailed Analytics:** In-depth profiles for each breed, covering origin, physical traits, and behavioral characteristics.
* **Media Gallery:** An immersive visual library with a dedicated full-screen photo viewer for high-fidelity browsing.

### **3. Cognitive Trivia (Quiz)**
* **Dynamic Session Generation:** Every quiz consists of 20 randomized questions pulled from the local database to ensure unique gameplay.
* **Challenge Logic:** Questions test the user's knowledge on breed identification and temperament analysis (identifying both existing and non-existent traits).
* **UX Refinement:** Smooth, physics-based animations facilitate the transition between questions, maintaining user engagement.

---

## Visual & Experience Requirements
* **Adaptive Theming:** Full support for **Dark and Light modes**, utilizing the dynamic color capabilities of Material 3.
* **Navigation Architecture:** Implements a structured **App Drawer** for intuitive movement between the encyclopedia, quiz, and profile sections.
* **Enhanced Interactivity:** Custom UI transitions and motion layouts that bring the interface to life.

---

## Installation & Deployment
To run **Catalist** in a development environment:

1.  **Clone** the repository to your local machine.
2.  Open the project in **Android Studio** (Hedgehog or newer recommended).
3.  Sync **Gradle** to resolve all Jetpack and Hilt dependencies.
4.  Deploy to an **Android Emulator** or physical device (Min API level 24).

---
*Developed for the University course: Mobile Apps Development*
