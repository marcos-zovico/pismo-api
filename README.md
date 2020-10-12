# Pismo API

## Pre requisitos
- [docker](https://docs.docker.com/get-docker/)
- [docker-compose](https://docs.docker.com/compose/install/) 
- [Java11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Make](https://tldp.org/HOWTO/Software-Building-HOWTO-3.html) 

Para fazer o build
``` ssh
make build
```

Para executar os testes
``` ssh
make build
```

Para executar/subir a aplicação
``` ssh
make run
```

### Visualizando banco de dados

Para acessar o `phpMyAdmin` acesse http://localhost:8081/index.php 
Usuário: user
Senha: pass


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




 
