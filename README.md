# Pismo API

## Pre requisitos
- [docker](https://docs.docker.com/get-docker/)
- [docker-compose](https://docs.docker.com/compose/install/) 
- [Java11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Make](https://tldp.org/HOWTO/Software-Building-HOWTO-3.html) 

Fazendo o build da aplicação
``` shell script
make build
```

Executando os testes
``` shell script
make build
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

Publicando a imagem docker, , é nessário informar o parametro `${DOCKER_HUB_USER}` e `${DOCKER_HUB_PASS}`
``` shell script
make docker-push DOCKER_HUB_USER=mzovico DOCKER_HUB_PASS=********
```


### Acessando o banco de dados

Acessando o container do mysql
```shell script
docker exec -it appsDB /bin/bash
```

Acessando mysql cli
```shell script
mysql -u user -p pismo 
```

### Exemplos de utlização da API
___
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
  "transaction_code": "ca40aec0-059a-4e1c-b162-0c19998c6f19",
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45,
  "event_date": "2020-10-12T22:10:42.80065"
}
```



 
