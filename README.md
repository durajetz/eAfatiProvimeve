<img src="https://images4.imagebam.com/ee/3a/1a/ME4808M_o.png" alt="eAfatiProvimeve" width="350"/>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**eAfatiProvimeve** is a mobile app for Android using *MVVM (Model-view-viewmodel)* architecture made with Java which based on the permanent terms of exam and the start date of exam deadline generates the correct dates for exams.

## Features
---
* **Firebase login:** supports Firebase login and register +google integration,forgot password.
* **Room DB:** exams are saved in the inside database.
* **JSON Parsing:** for parsing image urls from api to adapter.
* **CRUD operations:** you can edit,insert,delete exams inside ROOM database.
* **Create Event:** creating event with the generated date of exams directly to google calendar.
* **Google map:**  google map implementation with clusters where the exam will take place.


## Prerequisites

 * Download the [**JDK 8**](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) from Oracle and install it.
 * [The latest stable version of Android Studio.](https://developer.android.com/studio/)
 * Download & install the Android SDK **Build-Tools 29.0.3**, (for example through Android Studio’s **Android SDK Manager**).
 * Add the Android home environment variable to your profile:
  
    ```
    export ANDROID_HOME=~/Library/Android/sdk
    ````

## Download sources

You can download the source code of eAfatiProvimeve Java by using git. 

```
gh repo clone durajetz/eAfatiProvimeve
```
You can download apk file **[here](https://github.com/durajetz/eAfatiProvimeve/raw/main/app/build/outputs/apk/debug/app-debug.apk)**.

## Preview
<img src="https://images4.imagebam.com/60/ac/84/ME48245_o.png" alt="eAfatiProvimevePreview" />

## How does it work?

Currently **eAfatiProvimeve** is implemented only for FIEK bachelor exams based on **[Exam schedule](https://fiek.uni-pr.edu/desk/inc/media/CFBB3C9D-3688-4C00-9CE8-397BB1FF12B2.pdf)**.

>e.g. The exam deadline starts on September 27 (*The first Monday*), 2021
based on the **Exam schedule** the exam *'Data Mining'* is held on the **2nd TUESDAY** (05.10.2021)
so eAfatiProvimeve automatically generates the correct date and fills the fields of event with date,location,start time and end time for that particular exam.

<p align="center">
  <img src="https://media3.giphy.com/media/vzFrPs1rhc9MV2j5RG/giphy.gif?cid=790b761187058c43228b6873a322bd27311c4b91b078bf7f&rid=giphy.gif&ct=g" alt="Test preview"/>
</p>

---
Uni project, to be continued.
