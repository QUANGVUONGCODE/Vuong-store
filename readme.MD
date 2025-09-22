


[//]: # (RUNDOCKER FILE)
docker run --name vuongstore -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://172.17.0.2:3306/vuongstore?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC vuongstore-0.0.1 

[//]: # (CREATE DOCKER NETWORK)
docker network create vuongstore-network

[//]: # (Start mysql in vuongstore-network:)

docker run --network vuongstore-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.43


[//]: # (Start vuongstore in vuongstore-network:)
docker run --name vuongstore --network vuongstore-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/vuongstore vuongstore-0.0.1
