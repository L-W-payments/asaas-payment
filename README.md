# Mini Asaas

O projeto "Mini Asaas" é uma aplicação de controle de recebíveis que oferece funcionalidades essenciais, como cadastro 
de pagadores, geração de cobranças e confirmação de pagamentos. Além disso, possui um sistema de notificações por e-mail,
gestão multiusuário e mecanismos específicos no backend, como o uso do Spring Security para controle de conta e soft
delete para registros no banco de dados.

## Quickstart

### Requisitos

- JDK 11
- Grails 5
- MySQL

### Passos para Rodar o Projeto

1. Clone o repositório:

    ```sh
    git clone https://github.com/L-W-payments/asaas-payment.git
    cd asaas-payment
    ```

2. Configure o banco de dados no arquivo `application.yml`:

    ```yaml
    dataSource:
        pooled: true
        jmxExport: true
        driverClassName: com.mysql.cj.jdbc.Driver
        dialect: org.hibernate.dialect.MySQL8Dialect
        username: yourUsername
        password: yourPassword
    
      environments:
            development:
                dataSource:
                    dbCreate: update
                    url: yourDatabaseUrl
    ```
   
3. Configure o smtp no arquivo `application.yml`:
    ```yaml
    grails:
            mail:
                host: "smtp.gmail.com"
                port: 587
                username: "youremail@email.com"
                password: "yourAppPassowrd"
    ```

4. Execute o projeto:

    ```sh
    grails run-app
    ```

5. Acesse a aplicação em `http://localhost:8080`

## RFs (Requisitos funcionais)

- [x] Deve ser possível um cliente se cadastrar;
- [x] Deve ser possível um cliente se autenticar;
- [x] Deve ser possível um cliente deletar sua conta;
- [x] Deve ser possível um cliente atualizar seus dados;
- [x] Deve ser possível um cliente criar uma cobrança;
- [x] Deve ser possível um cliente listar todas as cobranças;
- [x] Deve ser possível um cliente listar uma cobrança específica;
- [x] Deve ser possível um cliente deletar uma cobrança;
- [x] Deve ser possível um cliente restaurar uma cobrança;
- [x] Deve ser possível um cliente selecionar um pagador para uma cobrança;
- [x] Deve ser possível um cliente criar um pagador;
- [x] Deve ser possível um cliente listar todos os pagadores;
- [x] Deve ser possível um cliente listar um pagador específico;
- [x] Deve ser possível um cliente atualizar um pagador;
- [x] Deve ser possível um cliente deletar um pagador;
- [x] Deve ser possível um cliente restaurar um pagador;
- [x] Deve ser possível um cliente listar todas as notificações;
- [x] Deve ser possível um cliente confirmar o recebimento em dinheiro de uma cobrança;
- [x] Deve ser possível um pagador pagar uma cobrança;
- [x] Deve ser possível um pagador ver o comprovante de pagamento;
- [x] Deve ser possível receber um email após uma cobrança ser criada;
- [x] Deve ser possível receber um email após uma cobrança ser deletada;
- [x] Deve ser possível receber um email após uma cobrança ser vencida;
- [x] Deve ser possível receber um email após uma cobrança ser paga;

## RNs (Regras de negócio)

- [x] O cliente não deve se cadastrar com um email duplicado;
- [x] O cliente não deve se cadastrar com um cpf ou cnpj duplicado;
- [x] O cliente não deve se cadastrar com informações inválidas;
- [x] O cliente não deve se cadastrar um pagador com informações inválidas;
- [ ] O cliente não deve deletar sua conta se possuir cobranças em aberto;
- [x] O cliente não deve deletar um pagador que possui cobranças em aberto;
- [x] O cliente não deve alterar uma cobrança;
- [x] O cliente não deve criar uma cobrança com data de vencimento menor que a data atual;
- [x] O cliente não deve criar uma cobrança com data de vencimento maior que 6 meses;
- [x] O cliente não deve criar uma cobrança com valor menor que R$ 10;
- [x] O cliente não deve criar uma cobrança com valor maior que R$ 10.000;
- [x] O cliente não deve criar uma cobrança com descrição maior que 500 caracteres;
- [x] O cliente não deve criar uma cobrança sem uma forma de pagamento;
- [x] O cliente não deve criar uma cobrança sem um pagador;
- [x] O cliente não deve confirmar o recebimento em dinheiro de uma cobrança se ela não estiver pendente;
- [x] O pagador não deve pagar uma cobrança recebida em dinheiro;

## RNFs (Requisitos não funcionais)

- [x] A senha do cliente deve ser criptografada;
- [x] Todos os domínios devem possuir um campo `dateCreated`, `lastUpdated` e `deleted`;
- [x] Todos os domínios que recebem informações de fora da aplicação devem possuir um adapter;
- [x] Todos os services devem ser implementados de maneira explícita;
- [x] Nenhum domínio deve ser deletado explicitamente;
- [x] Os dados da aplicação devem estar persistido em um banco MySQL;
- [x] A aplicação deve ser desenvolvida em Grails;
- [x] A aplicação deve ser desenvolvida em Groovy;
- [x] A aplicação deve ser desenvolvida com Spring Security;
- [x] A aplicação deve ser desenvolvida com o Atlas;
- [x] A aplicação deve utilizar repositórios para interação com domínios;
- [x] A aplicação deve utilizar Jobs para vencer as cobranças;

## Criadores
Este projeto foi criado por [Luiz Filipe](https://github.com/luizfiliperm) e [Wollace Buarque](https://github.com/Wollace-Buarque).