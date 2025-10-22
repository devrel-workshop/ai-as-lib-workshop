## Natural Language Processing module for AI Endpoints workshop

# ☕️ Java version ☕️ 

**ℹ️ All solutions to this part are in the [solution/nlp/java](../../solutions/nlp/java/) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
- A Java 25+ JDK
- [JBang](https://www.jbang.dev/)

### ❤️ Module 1: Emotion analysis with AI Endpoints 🤩

The goal of this module is to develop a script to analyse emotions in a text.  
The used model is [SamLowe/roberta-base-go_emotions](https://huggingface.co/SamLowe/roberta-base-go_emotions), and it does the following sentiments detection: admiration,amusement, anger, annoyance, ...

All the exercise must be done in [EmotionAnalysis.java](./EmotionAnalysis.java).

⚗️ Test your code by running the following commands: `./run-java-main.sh EmotionAnalysis.java`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [EmotionAnalysis.java](./EmotionAnalysis.java) file to discover the steps to create the script.

### 📝 Module 2: Text classification 🗃️

The goal of this module is to develop a script for Named Entity Recognition.
The used model is [bert-base-NER](https://huggingface.co/dslim/bert-base-NER), and it does the following extractions:
 - person's name
 - organization's name
 - location

All the exercise must be done in [TextClassification.java](TextClassification.java).

⚗️ Test your code by running the following commands: `./run-java-main.sh TextClassification.java`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [TextClassification.java](TextClassification.java) file to discover the steps to create the script.

### ⭐️ Module 3: product reviews  ⭐️

The goal of this module is to develop a script for sentiment analysis on product reviews.
The used model is [nlptown/bert-base-multilingual-uncased-sentiment](https://huggingface.co/nlptown/bert-base-multilingual-uncased-sentiment), and it does the following reviews: it predicts the sentiment of the review as a number of stars (between 1 and 5).

All the exercise must be done in [ProductReview.java](ProductReview.java).

⚗️ Test your code by running the following commands: `./run-java-main.sh ProductReview.java`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [ProductReview.java](ProductReview.java) file to discover the steps to create the script.
