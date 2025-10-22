///usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS com.squareup.okhttp3:okhttp-jvm:5.1.0
//DEPS com.fasterxml.jackson.core:jackson-databind:2.17.2


record Entity(String entity, double score, int index, String word) {};

void main() throws IOException {
    // Initialise OkHttp client
    // java-66

    // Create the request with bearer token
    // java-67

    // Call the endpoint
}