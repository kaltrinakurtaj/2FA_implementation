# Two-Factor Authentication (2FA) in Android Studio

This project demonstrates how to implement a **Two-Factor Authentication (2FA)** system in an Android app using **Java** and the **JavaMail API**. The app securely sends a one-time password (OTP) to the user's email for verification.

---

## Features
- Generate a secure 6-digit OTP.
- Send OTP via email using JavaMail API.
- Validate the OTP within the app.
- User-friendly Android UI.

---

## Prerequisites
- **Android Studio** installed.
- A **Google account** with:
  - [App Password enabled](https://support.google.com/accounts/answer/185833?hl=en) (recommended).
  - Or access to Less Secure Apps enabled (not recommended).
- Internet permission in the `AndroidManifest.xml`.

---




## Dependencies

Add the following dependencies to the `build.gradle` file of your app module (`app/build.gradle`):

```gradle
dependencies {
    implementation 'com.sun.mail:android-mail:1.6.2'
    implementation 'com.sun.mail:android-activation:1.6.2'
}

```

### Clone the Repository
Clone this repository to your local machine:
```sh
git clone https://github.com/kaltrinakurtaj/2FA-Android-Studio-Java.git
```



