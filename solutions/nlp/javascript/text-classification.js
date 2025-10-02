/* The goal of this script is to create an text classification program.
 * The steps to create this program :
 *    - set the bearer token and the header to access to AI Endpoint, see https://endpoints.ai.cloud.ovh.net/
 *    - use Axios (see https://github.com/axios/axios) to use bert-base-NER model
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
//  - send a phrase to the model bert-base-NER, see https://endpoints.ai.cloud.ovh.net/
//  - display the result (which type of texts is in the text)
axios
  .post(process.env.OVH_AI_ENDPOINT_MODEL_CLASSIFICATION_URL, "My name is Clara and I live in Berkeley, California.", options)
  .then((res) => {
    console.log(res.data);
    const output = res.data.map((entity) => {
      let description;
      switch (entity.entity) {
        case "B-PER":
          description = "is a person";
          break;
        case "B-LOC":
          description = "a location";
          break;
        case "B-ORG":
          description = "an organization";
          break;
        case "B-MISC":
          description = "other";
          break;

        default:
          description = "undefined";
      }
      return `${entity.word} is ${description}`;
    });

    console.log(output.join(", "));
  })
  .catch((err) => {
    console.error(err);
  });
