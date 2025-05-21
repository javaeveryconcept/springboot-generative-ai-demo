# 🤖 Java + Spring Boot + Generative AI (OpenAI + DeepSeek + Gemini)

Hello Developers! 👋  
Do you want your **Java + Spring Boot** app to talk like **ChatGPT**, write code, and generate new ideas?  
Then today’s project is just for you!  

We’ll connect **OpenAI**, **DeepSeek**, and **Gemini APIs** with just a few lines of code and power up your app with the strength of **Generative AI**.

---
## 🎥 Watch the Full Tutorial

### 🔵 English Version
[![Watch in English](https://img.youtube.com/vi/4vtd7UfJlA0/maxresdefault.jpg)](https://www.youtube.com/watch?v=4vtd7UfJlA0)

### 🔴 Hindi Version
[![Watch in Hindi](https://img.youtube.com/vi/5jOLIYsH340/maxresdefault.jpg)](https://www.youtube.com/watch?v=5jOLIYsH340)


## 🚀 Features

- 🔥 Chat with AI (like ChatGPT)
- 💡 Generate content and ideas
- 🧠 Write and debug code with AI help
- 🤝 Use OpenAI, DeepSeek, and Gemini APIs interchangeably

---

## 🧰 Tech Stack

- Java 21
- Spring Boot 3.x
- REST API
- WebClient

---

## 📦 Project Structure

```
src/
├── main/
│   ├── java/com/example/aiintegration/
│   │   ├── controller/         # REST Controllers
│   │   ├── service/            # Business Logic, caling generative AI APIs
│   └── resources/
│       └── application.properties     # API Keys Config
```

---

## 🔧 Setup Instructions

### 1. Clone the Repo

```bash
git clone https://github.com/javaeveryconcept/springboot-generative-ai-demo.git
cd springboot-generative-ai-demo
```

### 2. Add API Keys

Edit the `application.properties` file and add your keys:

```properties
# openai
openai.api.url=https://api.openai.com/v1/chat/completions
openai.api.key=<API-KEY>

# deepkeek
deepkeek.api.url=https://api.deepseek.com/chat/completions
deepkeek.api.key=<API-KEY>

# Gemini
gemini.api.url=https://generativelanguage.googleapis.com
gemini.api.key=<API-KEY>
```

### 3. Build the Project

```bash
./mvnw clean install
```

### 4. Run the App

```bash
./mvnw spring-boot:run
```

Server will start at: `http://localhost:8080`

---

## 🧪 Sample API Endpoints

### POST `api/gpt/ask`

```json
{
  "prompt": "Explain Java Virtual Threads in simple terms."
}
```

### POST `/api/deepseek/ask`

```json
{
  "prompt": "Write a Java method to calculate factorial."
}
```

### POST `/api/gemini/ask`

```json
{
  "prompt": "Give me startup ideas using AI and fintech."
}
```

## Websites
### Get API Keys:
* https://platform.openai.com/api-keys
* https://aistudio.google.com/app/apikey
* https://platform.deepseek.com/api_keys

## 🎥 YouTube Tutorial

Watch the full step-by-step implementation video here:  
👉 [YouTube.com/@javaeveryconcept](https://www.youtube.com/@javaeveryconcept)

---

## 📬 Contact

Have questions or ideas?  
Connect on [GitHub](https://github.com/javaeveryconcept) to raise an issue.

---

## 📄 License

MIT License