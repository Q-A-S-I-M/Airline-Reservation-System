from flask import Flask, request, jsonify
from textblob import TextBlob

app = Flask(__name__)

@app.route('/analyze', methods=['POST'])
def analyze_sentiment():
    data = request.get_json()
    comment = data['comment']
    polarity = TextBlob(comment).sentiment.polarity

    if polarity > 0.1:
        score = 1
    elif polarity < -0.1:
        score = -1
    else:
        score = 0

    return jsonify({'sentiment': score})

if __name__ == '__main__':
    app.run(port=5001)
