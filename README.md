# Word Search Backend
This is the backend API for a Word Search application built using Spring Boot. It provides functionality to insert, search, and autocomplete words using a Trie data structure, along with ranking support.
The project is deployed on Railway. URL in description.

## 🚀 Features

- Insert: Insert a word into the system.
  - Method: POST
  - Endpoint: `https://word-search-app-production.up.railway.app/api/words/insert`
  - Body(text): (word to be inserted)
- Search: Check if a word exists in the system.
  - Method: GET
  - Endpoint: `https://word-search-app-production.up.railway.app/api/words/search?word=coffee`
  - Pass the word to be searched in the parameters of the endpoint
- Auto-complete: Suggest words based on a given prefix.
  - Method: GET
  - Endpoint: `https://word-search-app-production.up.railway.app/api/words/autocomplete?prefix=hell`
  - Pass the word in the params of the endpoint to get suggestions
- Increment rank: Increase the rank of a word based on its usage. Every time the word is searched its rank gets incremented by 1.
- Get rank: Retrieve the rank of a word.
  - Method: GET
  - Endpoint: `https://word-search-app-production.up.railway.app/api/words/rank?word=divine`
  - Pass the word in the params of the endpoint to obtain rank of the word

---

## 🔧 Running Locally

1.  Clone the repo
```
  git clone https://github.com/RJagga/word-search-app.git
  cd word-search-app
```
2. Run with Maven
```
   mvn spring-boot:run
```
