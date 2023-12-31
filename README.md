
# Rinha de Backend 2023 - Java

Link para o repo oficial da rinha: [`https://github.com/zanfranceschi/rinha-de-backend-2023-q3`](https://github.com/zanfranceschi/rinha-de-backend-2023-q3)

## Pré-requisitos

* Java 20
* Maven
* PostgreSQL
* Docker/Podman
* Gatling v3.9.5

## Como subir o projeto

#### - Clone o projeto com o Git e navegue até o diretório do mesmo.

#### - Faça o build da aplicação com o *Maven*:

```bash
  mvn clean install
```

#### - Em seguida, faça o build da imagem para a execução do container:

```bash
  docker build . -t localhost/rinhabackend2023:0.0.1-SNAPSHOT
```

##### - Você pode utilizar o docker-compose-local.yml ou o docker-compose-aws.yml para subir os serviços. Abra o arquivo da sua escolha para ajustar as variáveis de ambiente dos serviços.

#### - Para utilizar o docker-compose, digite:

```bash
  docker compose -f docker-compose-local.yml up
```
ou 

```bash
  docker compose -f docker-compose-aws.yml up
```

## Stress Test

#### - Mude para o diretório ```/rinhabackend/stress-test```. Abra o arquivo ```run-test.sh``` com o editor de texto da sua preferência e ajuste as variáveis ```GATLING_BIN_DIR``` e ```WORKSPACE```, que devem apontar para o bin do Gatling e o diretório do stress-test, respectivamente. 

#### - Em seguida execute o script para rodar o teste de stress:

```bash
  ./run-test.sh
```

Obs: É de extrema importância que você esteja com a mesma versão do java do projeto (Java 20).