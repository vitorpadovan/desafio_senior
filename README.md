# Desafio Senior 
## Obrigatoriedades para executar a aplicação
1. Banco de dados Mysql
2. Variáveis de ambientes
	1. `DESAFIO_USERNAME`. Usuário do banco
	2. `DESAFIO_PASSWORD`. Usuário do banco
	3. `DESAFIO_DATABASE_IP`. Ip onde se encontra o banco de dados
	4. `DESAFIO_DATABASE_PORT`. Porta para acesso o bando de dados
	5. `DESAFIO_DATABASE_NAME`. Nome do banco de dados 
3. Banco `DESAFIO_DATABASE_NAME` deve obrigatóriamente estar criado.

## Execução da aplicação
### Execução via jar
JAR está disponível na pasta /releases já complilado. Para executar o mesmo basta apenas rodar o comando `java -jar Desafio-Senior-0.0.1-SNAPSHOT.jar`
### Documentação da API
Documentação da API pode ser encontrada em http://localhost:8080/swagger-ui.html após a execução da aplicação
### Testes
Testes podem ser feitos com o [Postman](https://www.postman.com/) nos endpoints que estão na documentação acima


## Observações importantes
Não foi possível finalizar o **item 12** por questões de alto processamento necessário, seja na parte de banco de dados ou na parte da aplicação.
Sendo assim a unica solução seria ter um banco já préviamente preenchido com as distancias entre todas as cidades para poder apresentar para o usuário assim que ele solicitar.
A tentativa de fazer este calculo em tempo real só levaria um **TIMEOUT** por conta do front-end.
Para se calcular a distância em kilometros usa-se o algoritmo de Haversine, o mesmo está em `com.vitor.desafio.tools.Haversine`, ou para encontra as cidades que estão mais distântes podemos usar o calculo do triangulo retângulo onde **(CATETO_ADJACENTE)<sup>2</sup> + (CATETO_OPOSTO)<sup>2</sup> = (HIPOTENUSA)<sup>2</sup>** usando as cordenadas no local dos catetos.
### Manieras de resolver o item 12.
1. Através de banco de dados diretamente
	1. usando a consulta `SELECT A.idIbge, B.idIbge, SQRT(POW(A.latitude - B.latitude, 2) + POW(A.longitude - B.longitude, 2)) as distancia FROM Cidade A INNER JOIN Cidade B on A.idIbge != B.idIbge`, porem este item não dá a distância em km, mais é possível encontrar as cidades mais distantes.
	2. Criando uma procedure que alimenta uma tabela auxiliar para fazer este processamento de cidade por cidade. Porem o mesmo também demanda processamento alto e a tabela com 667 cidades concluidas gerou uma tabela de 3.439.920 e 304 MB
2. Através da aplicação
	1. Usando loop foreach `for(Cidade c : cidades)`, porem este processamento é mais lento do que o processamento do banco
	2. Usando loop foreach em paralelo, que apenas melhora um pouco a performace da aplicação, mais aumenta significativamente o processamento de dados.
