## Natural Language Processing module for AI Endpoints workshop

# 🐍 Python version 🐍

**ℹ️ All solutions to this part are in the [solution/nlp/python](../../solutions/nlp/python/) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - NodeJS 20.x
 - run `npm i` to install dependencies

### ❤️ Module 1: Emotion analysis with AI Endpoints 🤩

The goal of this module is to develop a script to analyse emotions in a text.  
The used model is [SamLowe/roberta-base-go_emotions](https://huggingface.co/SamLowe/roberta-base-go_emotions), and it does the following sentiments detection: admiration,amusement, anger, annoyance, ...

All the exercise must be done in [emotion-analysis.py](emotion-analysis.py).

⚗️ Test your code by running the following commands: `./run-python-script.sh emotion-analysis.py`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [emotion-analysis.py](emotion-analysis.py) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/nlp/python](./python/) folder
 - the main resources:
    - [emotion-analysis.py](../javascript/emotion-analysis.js) file

### 📝 Module 2: Text classification 🗃️

The goal of this module is to develop a script for Named Entity Recognition.
The used model is [bert-base-NER](https://huggingface.co/dslim/bert-base-NER), and it does the following extractions:
 - person's name
 - organization's name
 - location

All the exercise must be done in [text-classification.py](text-classification.py).

⚗️ Test your code by running the following commands: `./run-python-script.sh text-classification.py`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [text-classification.py](text-classification.py) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/nlp/python](./python/) folder
 - the main resources:
    - [text-classification.py](text-classification.py) file

### ⭐️ Module 3: product reviews  ⭐️

The goal of this module is to develop a script for sentiment analysis on product reviews.
The used model is [nlptown/bert-base-multilingual-uncased-sentiment](https://huggingface.co/nlptown/bert-base-multilingual-uncased-sentiment), and it does the following reviews: it predicts the sentiment of the review as a number of stars (between 1 and 5).

All the exercise must be done in [product-review.py](product-review.py).

⚗️ Test your code by running the following commands: `./run-python-script.sh product-review.py`

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [product-reviews.py](./python/product-reviews.py) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/nlp/python](./python/) folder
 - the main resources:
    - [product-reviews.py](product-review.py) file
