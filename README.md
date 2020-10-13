# Pismo API

O projeot foi construido utilizando [Spring Boot](https://spring.io/projects/spring-boot) que facilita o desenvolvimeto, \
com banco de dados [mysql](https://www.mysql.com/), também foi utilizado [flywaydb](https://flywaydb.org/) para o gerenciamento de migração da banco.

Para iniciar a aplicação execute `make run` dois containers devem subir.
```shell script
❯ docker ps
CONTAINER ID        IMAGE                      COMMAND                  CREATED             STATUS              PORTS               NAMES
6fce0a373bf4        mzovico/pismo-api:latest   "java -cp app:app/li…"   14 minutes ago      Up 13 minutes                           pismo-api
581178b750fa        mysql:8.0                  "docker-entrypoint.s…"   14 minutes ago      Up 13 minutes                           appsDB
```

Se os dois containers não subirem, execute o comando `make run` novamente.
 
Foi gerado uma imagem docker da aplicaçao que esta disponível em: [pismo-api](https://hub.docker.com/r/mzovico/pismo-api)

### Pre requisitos
- [docker](https://docs.docker.com/get-docker/)
- [docker-compose](https://docs.docker.com/compose/install/) 
- [Java11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Make](https://tldp.org/HOWTO/Software-Building-HOWTO-3.html) 

#
### Principais Tecnologias
- [spring-boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) 
- [flywaydb](https://flywaydb.org/)


#
### Execução de scripts com MakeFile
Fazendo o build da aplicação
``` shell script
make build
```

Executando os testes
``` shell script
make test
```

Subindo a aplicação
``` shell script
make run
```

Parando a aplicação
``` shell script
make stop
```

Gerando a imagem docker, é nessário informar o parametro `${DOCKER_HUB_USER}`
``` shell script
make docker-build DOCKER_HUB_USER=mzovico
```

Publicando a imagem docker, é nessário informar o parametro `${DOCKER_HUB_USER}` e `${DOCKER_HUB_PASS}`
``` shell script
make docker-push DOCKER_HUB_USER=mzovico DOCKER_HUB_PASS=********
```

#
### Acessando o banco de dados

Acessando o container do mysql
```shell script
docker exec -it appsDB /bin/bash
```

Acessando mysql cli, ao ser solicitado a senha utiliza a senha `pass`
```shell script
mysql -u user -p pismo 
```

#
### Exemplos de utlização da API

#### Criando e consutando contas
Na criação da conta é retornado o id.
 
```shell scrip
# cria conta
curl -X POST -H "Content-Type: application/json" -d '{"document_number": "12345678900"}' http://localhost:8080/accounts

# consulta conta por id
curl -X GET  http://localhost:8080/accounts/2
```

Tanto na criação como na consulta deve retornar
```json
{
  "account_id": 2,
  "document_number": "12345678901"
}
```

#### Criando transação
Na criação da transação é retornado o id.
 
```shell scrip
# cria transação
curl -X POST -H "Content-Type: application/json" \
-d '{
        "transaction_code": "ca40aec0-059a-4e1c-b162-0c19998c6f19",
        "account_id": 1,
        "operation_type_id": 4,
        "amount": 123.45
    }' \
http://localhost:8080/transactions


```

Na criação da transação deve retornar
```json
{
  "transaction_id": 1,
  "transaction_code": "ca40aec0-059a-4e1c-b162-0c19998c6f29",
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45,
  "event_date": "2020-10-12T22:10:42.80065"
}
```
`IMPORTANTE` o campo `transaction_code` deve ser gerado pelo client a cada chamada \
para a criação de uma nova transação. Este campo é utilizado para o controle de idempotência \
não sendo possível cria duas transações com o mesmo `transaction_code`.

No caso de timeout na requisição, deve-se refazer a chamado utilizando o mesmo `transaction_code`, \
caso a transação tenha sido criado pelo servidor, vai ser ignorado a criaçção da transação.

``` shell script
pismo-api    | 2020-10-12 22:54:28.945  INFO 1 --- [nio-8080-exec-4] i.p.p.c.account.AccountController        : creating account
pismo-api    | 2020-10-12 22:54:28.958  INFO 1 --- [nio-8080-exec-4] i.p.p.c.account.AccountController        : Account successful created
pismo-api    | 2020-10-12 22:55:15.771  INFO 1 --- [nio-8080-exec-5] i.p.p.service.TransactionServiceImpl     : Creating transaction with code ca40aec0-059a-4e1c-b162-0c19998c6f29
pismo-api    | 2020-10-12 22:55:15.808  INFO 1 --- [nio-8080-exec-5] i.p.p.service.TransactionServiceImpl     : Transaction successful created
pismo-api    | 2020-10-12 22:55:23.787  INFO 1 --- [nio-8080-exec-6] i.p.p.service.TransactionServiceImpl     : Creating transaction with code ca40aec0-059a-4e1c-b162-0c19998c6f29
pismo-api    | 2020-10-12 22:55:23.795  WARN 1 --- [nio-8080-exec-6] i.p.p.service.TransactionServiceImpl     : Transaction already exist with code ca40aec0-059a-4e1c-b162-0c19998c6f29
``` 


 
