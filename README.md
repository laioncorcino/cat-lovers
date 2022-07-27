# cat-lovers

Api para escolher gatos favoritos e votar características de gato por um rating

A api se comunica com a api https://api.thecatapi.com/v1/breeds para listar as raças de gatos e o usuário poderá escolher seus gatos favoritos
e também votar qualidades que ele encontra em determinada raça.

O domínio de favoritos é salvo em banco relacional - MYSQL

O domínio de rating é salvo em banco não relacional - Mongo

# Para subir a aplicação 

* Executar a task startdbmysql  ./gradle startdbmysql no diretório da api cat-lovers para subir um container docker do mysql

* Executar a task startdbmongo  ./gradle startdbmongo no diretório da api cat-lovers para subir um container docker do mongo

# Para rodar todos os testes

* Entrar no diretório da api cat-lovers/src/test/wiremock 

* Executar o comando ``` java -jar wiremock-standalone-2.25.1-ui.jar --port 9999 --verbose true ``` para subir o servidor wiremock

# Tecnologias utilizadas

* Java 17
* Spring Boot
* Open Api
* Lombok
* Slf4
* JUnit 5
* Mysql
* MongoDB

# Para acessar a documentação
``` http://localhost:8080/swagger-ui/index.html ```

# collections postman
https://documenter.getpostman.com/view/10168373/UzXNTcVN
