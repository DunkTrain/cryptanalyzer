# Cryptanalyzer

**Cryptanalyzer** is a desktop application for encrypting and decrypting text using the Caesar cipher. It supports encoding/decoding messages with a given or random key, and includes an automatic brute-force mode to crack encrypted texts.

> 🇷🇺 [Read in Russian](README.ru.md)

---

## Features

- 🔒 **Encryption** with a specified or random key.
- 🔓 **Decryption** with a known key.
- 🔍 **Brute-force attack** with confidence scoring.
- 📂 **File support**: load input from and save output to a file.
- 🌐 **Support for Russian alphabet**: Cyrillic letters, digits, and punctuation.

---

## Requirements

- Java 21+
- Maven

---

## Run the app

### Option 1: Download release

- Visit the [Releases](https://github.com/DunkTrain/cryptanalyzer/releases) page.
- Download the latest `.jar` file.
- Run:

```bash
java -jar cryptanalyzer-2.2.1.jar
```

### Option 2: Build from source

```bash
git clone https://github.com/username/cryptanalyzer.git
cd cryptanalyzer
mvn clean package
java -jar target/cryptanalyzer-2.1.0.jar
```

### Option 3: Run via Maven

```bash
mvn clean javafx:run
```

### Usage

***Input***

Enter text manually or click “📂 Open File” to load from a text file.

***Key***

Enter a number from 1 to 83 (or leave empty to generate randomly).

***Operations***

- 🔒 Encrypt

- 🔓 Decrypt

- 🔍 Brute-force (automatic decryption and key detection)

***Output***

- View results in the output field.

- Click ➡ to copy result to input.

- Click 💾 to save the result to a file.

### Tech stack

- Java 21

- JavaFX

- Maven

- MVC architecture

***Encryption algorithm***

Modified Caesar cipher with support for the full Russian alphabet, digits, and punctuation. Each character is shifted by a given number of positions.
Brute-force algorithm

***The brute-force attack performs:***

1. Full key space scan.

2. Decryption for each key.

3. Plausibility scoring based on:

    - letter frequency
    - dictionary matches
    - sentence structure