To run this in docker-mode:

docker build -t springboot-restful-webservices .
docker build -t springboot-restful-webservices:0.1.RELEASE .


docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=employeedb -d mysql

docker run --network springboot-mysql-net --name springboot-mysql-container -p 8080:8080 springboot-restful-webservices

To run docker-compose:

[//]: # (in detached mode)
docker-compose up -d

To stop all docker-compose containers:

docker-compose down
