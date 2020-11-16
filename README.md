# webflux-tweet-example
# Build Reactive Rest APIs with Spring WebFlux and Reactive MongoDB

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. MongoDB - 3.x.x

## Steps to Setup

**1. Database
In this project I used mongodb on Ubunto, to install just follow the commands below I will not go into detail on what the commands do, if you want to know more access [this link](https://www.digitalocean.com/community/tutorials/how-to-install-mongodb-on-ubuntu-20-04-pt).
```bash - Ubunto
sudo apt update
sudo apt install -y mongodb
sudo systemctl status mongodb
mongo --eval 'db.runCommand({ connectionStatus: 1 })'


For other operating systems access [this link](https://treehouse.github.io/installation-guides/).

**2. Clone the application**

```bash
git clone https://github.com/Jonajor/webflux-tweet-example.git
```

**3. Build and run the app using maven**

```bash
cd webflux-tweet-example
mvn spring-boot:run
```

The server will start at <http://localhost:8080>.

## Exploring the Rest APIs

The application defines following REST APIs

```
1. GET /tweets - Get All Tweets

2. POST /tweets - Create a new Tweet

3. GET /tweets/{id} - Retrieve a Tweet by Id

3. PUT /tweets/{id} - Update a Tweet

4. DELETE /tweets/{id} - Delete a Tweet

4. GET /stream/tweets - Stream tweets to a browser as Server-Sent Events
```


## Reference:
```

I used the link below as a reference but developed the project a little differently, creating a service layer and using some spring-boot-starter-data-mongodb-reactive resources like existsById that returns a boolean if the id is being researched exists.
1. https://www.callicoder.com/reactive-rest-apis-spring-webflux-reactive-mongo/

```

## Running integration tests

The project also contains integration tests for all the Rest APIs. For running the integration tests, go to the root directory of the project and type `mvn test` in your terminal.
