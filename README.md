# PSGTech-FineArts-Android-App

## Overview
PSGTech FineArts Android App is designed for managing users, events, and resources for the PSGTech FineArts club. It provides admin and user functionalities, event management, and Firebase integration for authentication and data storage.

## Features
- **User Authentication:** Login and registration using Firebase Auth.
- **Admin Dashboard:** Add users, view users, manage events, and assign admin privileges.
- **Event Management:** View, add, and manage events with details like name, date, and incharge.
- **User Management:** Add new users, view user details, and manage user roles and departments.
- **Modern UI:** Material Design components and custom layouts for a clean user experience.

## Main Screens
- **Splash Screen:** App logo and creator info.
- **Login Screen:** Email/password login with validation.
- **Admin Panel:** Grid view for admin functions (Add User, View Users, Events, Privilege, Profile).
- **Add User:** Form for adding users with department, year, role, and admin privilege selection.
- **View Users:** RecyclerView listing all users except main admin.
- **Events:** StackView listing all events with details.

## Tech Stack
- **Language:** Java
- **Framework:** Android SDK
- **UI:** Material Design, ConstraintLayout, LinearLayout, RecyclerView, StackView
- **Backend:** Firebase Auth, Firebase Firestore

## Dependencies
See `app/build.gradle` for all dependencies. Key ones include:
- androidx.appcompat:appcompat
- com.google.android.material:material
- androidx.constraintlayout:constraintlayout
- com.google.firebase:firebase-auth
- com.google.firebase:firebase-firestore

## Setup & Installation
1. Clone the repository:
   ```
   git clone https://github.com/kaushiksaravanan/PSGTech-FineArts-Android-App.git
   ```
2. Open in Android Studio.
3. Add your Firebase `google-services.json` to `app/`.
4. Sync Gradle and build the project.
5. Run on an emulator or physical device (minSdk 21).

## Usage
- **Admin Login:** Use registered admin credentials to access admin features.
- **Add User:** Fill in user details and assign privileges.
- **View Users:** Browse all users except the main admin.
- **Events:** View and manage event details.

## Project Structure
- `app/src/main/java/com/psgtech/fineartsapp/` - Main Java source files
- `app/src/main/res/layout/` - XML layout files for activities and views
- `app/build.gradle` - App-level dependencies
- `google-services.json` - Firebase config (not included)

## License
See [LICENSE](LICENSE).

## Authors
- Janath JSK
- Kaushik S
- Navin Praanav P

---
For any issues or feature requests, please open an issue on GitHub.