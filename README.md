# Bot Memo Polish

```markdown
# Bot Memo Polish
```

A **Java-based flashcard application** designed for **Polish language learners** to enhance memorization using spaced repetition.

---

## 🚀 Features

- **Flashcard System**: Create and review flashcards easily.
- **Spaced Repetition**: Optimized review intervals to improve memory retention.
- **Polish Language Support**: Tailored for learners of the Polish language.
- **Interactive CLI**: Easy-to-use command-line interface.

---

## ⚙️ Prerequisites

- Java 8 or higher
- Maven

---

## 🏁 Getting Started

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/ca5ual/bot_memo_polish.git
cd bot_memo_polish
```

### 2️⃣ Build the Project
```bash
mvn clean install
```

### 3️⃣ Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.example.Main"
```

---

## 🎮 Usage Commands

- **Create a new flashcard:**
```text
create
```
- **Review flashcards:**
```text
review
```
- **Show all flashcards:**
```text
list
```
- **Exit application:**
```text
exit
```

> 💡 The `/health` endpoint displays system info if implemented for testing.

---

## 🤝 Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature-name`)
3. Commit your changes (`git commit -m 'Add feature'`)
4. Push to the branch (`git push origin feature-name`)
5. Open a Pull Request

---

## 🛡️ License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

*Created with ❤️ for Polish language learners*
