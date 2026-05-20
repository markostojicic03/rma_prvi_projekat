# Catalist – Advanced Cat Breed Discovery & Trivia Engine

**University project - Mobile Application Development, 2025.**

<p align="center">
  <img src="link_do_logo_slike.png" alt="Catalist Logo" width="150"/>
</p>

## Project Overview
**Catalist** is a high-performance Android application developed as a centerpiece for the **"Mobile Apps Development"** curriculum. The app is designed as a dual-module platform: a comprehensive digital encyclopedia for feline breeds and an interactive, data-driven trivia system.

Built with a focus on **Reactive Programming** and **Modern Android Architecture**, Catalist demonstrates a seamless integration of local data persistence and a fluid user interface.

---

## 📸 Screenshots

<p align="center">
  <img width="1080" height="2198" alt="Image20260520133712" src="https://github.com/user-attachments/assets/9dbf83c3-f553-46ba-8288-e1ebc9ff9dad" height="400"/>
<img width="1080" height="2192" alt="Image20260520133716" src="https://github.com/user-attachments/assets/93590995-27bf-4ccd-b8df-7cf752b772ff" height="400"/>
<img width="1080" height="2055" alt="Image20260520133719" src="https://github.com/user-attachments/assets/908dae51-8164-46cd-9a54-38323c47af86" height="40"/>
</p>

---

## Technical Stack & Architecture
The project adheres to the highest standards of modern Android engineering:

* **Architecture Pattern:** Fully implemented using **Model-View-Intent (MVI)** for predictable state management and a unidirectional data flow.
* **UI Engine:** Developed with **Jetpack Compose**, leveraging **Material Design 3** for a sophisticated, adaptive interface.
* **Concurrency:** Powered by **Kotlin Coroutines** and **Flow** to handle asynchronous data streams and background tasks.
* **Data Management:** * **Jetpack Room:** A robust SQL abstraction layer for caching breed information.
    * **Jetpack DataStore:** Secure, reactive storage for user profiles and application preferences.
* **Dependency Injection:** Managed via **Hilt**, ensuring a modular, decoupled, and easily testable codebase.

*(Optional)*
<p align="center">
  <img src="link_do_arhitekture.png" alt="Architecture Diagram" width="80%"/>
</p>

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
