mvn clean install &&
docker build -t credit-card-ddd . &&
docker run -p 9090:9090 credit-card-ddd 