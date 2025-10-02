/* The goal of this script is to create an reviews classification program.
 * The steps to create this program :
 *    - set the bearer token and the header to access to AI Endpoint, see https://endpoints.ai.cloud.ovh.net/
 *    - use Axios (see https://github.com/axios/axios) to use nlptown/bert-base-multilingual-uncased-sentiment model
 */
import axios from "axios";

// Set the bearer token and the header to access to AI Endpoint, see https://endpoints.ai.cloud.ovh.net/
const options = {
  headers: {
    "Content-Type": "text/plain",
    Authorization: "Bearer " + process.env.OVH_AI_ENDPOINTS_ACCESS_TOKEN,
  },
};

// Use Axios (see https://github.com/axios/axios) to
//  - send a review to the model nlptown/bert-base-multilingual-uncased-sentiment, see https://endpoints.ai.cloud.ovh.net/
//  - display the result (number of stars from one to five)
axios
  .post(process.env.OVH_AI_ENDPOINT_MODEL_SENTIMENT_URL, "Bon produit, envoyé dans les temps. Service après vente perfectible.", options)
  .then((res) => {
    const maxRating = Math.max(...Object.values(res.data));
    const maxRatingKey = Object.keys(res.data).find(key => res.data[key] === maxRating);
    
    const numStars = parseInt(maxRatingKey.split(' ')[0]);

    let starString = '';
    for (let i = 0; i < numStars; i++) {
      starString += '⭐️';
    }

    console.log(starString);    
  })
  .catch((err) => {
    console.error(err);
  });
