# Speech Recognition
### Prerequisites
* Maven
* Java 8
* Google Cloud
* Michrophone
* Speakers

### How to build
First at all we have to download the repo. We will clone it whereever we want.
```sh
git clone https://github.com/Chillaso/Speech-Recognition.git
```
After this we are going to compile the source code. This will download all the dependencies ang generate a JAR
```sh
mvn clean package
```
After this we need to export our credentials in Google Cloud (See how to create and account and get JSON [here](https://cloud.google.com/docs/authentication/production))
```sh
export GOOGLE_APPLICATION_CREDENTIALS
```
Finally, let's play
```sh
mvn exec:java -DStartStream
```
