/* The goal of this script is to create an emotion analysis program.
 * The steps to create this program : 
 *    - set the bearer token and the header to access to AI Endpoint, see https://endpoints.ai.cloud.ovh.net/
 *    - use Axios (see https://github.com/axios/axios) to use roberta-base-go_emotions model
 */
import axios from 'axios';

// Set the bearer token and the header to access to AI Endpoint, see https://endpoints.ai.cloud.ovh.net/

// Use Axios (see https://github.com/axios/axios) to 
//  - send a phrase to the model roberta-base-go_emotions, see https://endpoints.ai.cloud.ovh.net/
//  - display the result (which type of emotion is in the text)
