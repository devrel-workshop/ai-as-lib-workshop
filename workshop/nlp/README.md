## Natural Language Processing module for AI Endpoints workshop

**ℹ️ All solutions to this part are in the [solution/nlp](../../solutions/nlp/) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - NodeJS 20.x
 - run `npm i` to install dependencies

### ❤️ Module 1: Emotion analysis with AI Endpoints 🤩

The goal of this module is to develop a script to analyse emotions in a text.  
The used model is [SamLowe/roberta-base-go_emotions](https://huggingface.co/SamLowe/roberta-base-go_emotions) and it does the following sentiments detection: admiration,amusement, anger, annoyance, ...

All the exercise must be done in [emotion-analysis.js](./emotion-analysis.js).

⚗️ Test your code by running the following commands: `node emotion-analysis.js`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [emotion-analysis.js](./emotion-analysis.js) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/nlp](.) folder
 - the main resources:
    - [package.json](./package.json) file

### 📝 Module 2: Text classification 🗃️

The goal of this module is to develop a script for Named Entity Recognition.
The used model is [bert-base-NER](https://huggingface.co/dslim/bert-base-NER) and it does the following extractions:
 - person's name
 - organization's name
 - location

All the exercise must be done in [text-classification.js](./text-classification.js).

⚗️ Test your code by running the following commands: `node text-classification.js`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [text-classification.js](./text-classification.js) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/nlp](.) folder
 - the main resources:
    - [package.json](./package.json) file

### ⭐️ Module 3: product reviews  ⭐️

The goal of this module is to develop a script for sentiment analysis on product reviews.
The used model is [nlptown/bert-base-multilingual-uncased-sentiment](https://huggingface.co/nlptown/bert-base-multilingual-uncased-sentiment) and it does the following reviews: it predicts the sentiment of the review as a number of stars (between 1 and 5).

All the exercise must be done in [product-reviews.js](./product-reviews.js).

⚗️ Test your code by running the following commands: `node product-reviews.js`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [product-reviews.js](./product-reviews.js) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/nlp](.) folder
 - the main resources:
    - [package.json](./package.json) file
