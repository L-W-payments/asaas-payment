# Mini Asaas

O projeto "Mini Asaas" é uma aplicação de controle de recebíveis que oferece funcionalidades essenciais, como cadastro 
de pagadores, geração de cobranças e confirmação de pagamentos. Além disso, possui um sistema de notificações por e-mail,
gestão multiusuário e mecanismos específicos no backend, como o uso do Spring Security para controle de conta e soft
delete para registros no banco de dados.

## RFs (Requisitos funcionais)

- [ ] Deve ser possível um cliente se cadastrar;
- [ ] Deve ser possível um cliente se autenticar;
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
- [x] Deve ser possível que o pagador pague uma cobrança;
- [x] Deve ser possível um cliente confirmar o recebimento em dinheiro de uma cobrança;
- [ ] Deve ser possível receber um email após uma cobrança ser criada;
- [ ] Deve ser possível receber um email após uma cobrança ser deletada;
- [ ] Deve ser possível receber um email após uma cobrança ser vencida;
- [ ] Deve ser possível receber um email após uma cobrança ser paga;

## RNs (Regras de negócio)

- [x] O cliente não deve se cadastrar com um email duplicado;
- [x] O cliente não deve se cadastrar com um cpf ou cnpj duplicado;
- [x] O cliente não deve se cadastrar com informações inválidas;
- [x] O cliente não deve se cadastrar um pagador com informações inválidas;
- [ ] O cliente não deve deletar sua conta se possuir cobranças em aberto;
- [ ] O cliente não deve deletar um pagador que possui cobranças em aberto;
- [ ] O cliente não deve alterar uma cobrança;
- [x] O cliente não deve criar uma cobrança com data de vencimento menor que a data atual;
- [x] O cliente não deve criar uma cobrança com data de vencimento maior que 6 meses;
- [x] O cliente não deve criar uma cobrança com valor menor que R$ 10;
- [x] O cliente não deve criar uma cobrança com valor maior que R$ 10.000;
- [x] O cliente não deve criar uma cobrança com descrição maior que 500 caracteres;
- [x] O cliente não deve criar uma cobrança sem uma forma de pagamento;
- [x] O cliente não deve criar uma cobrança sem um pagador;
- [x] O cliente não deve confirmar o recebimento em dinheiro de uma cobrança se ela não estiver pendente;

## RNFs (Requisitos não funcionais)

- [ ] A senha do cliente deve ser criptografada;
- [x] Todos os domínios devem possuir um campo `dateCreated`, `lastUpdated` e `deleted`;
- [x] Todos os domínios que recebem informações de fora da aplicação devem possuir um adapter;
- [x] Todos os services devem ser implementados de maneira explícita;
- [x] Nenhum domínio deve ser deletado explicitamente;
- [x] Os dados da aplicação devem estar persistido em um banco MySQL;
- [x] A aplicação deve ser desenvolvida em Grails;
- [x] A aplicação deve ser desenvolvida em Groovy;
- [ ] A aplicação deve ser desenvolvida com Spring Security;
- [x] A aplicação deve ser desenvolvida com o Atlas;
- [x] A aplicação deve utilizar repositórios para interação com domínios;
- [x] A aplicação deve utilizar Jobs para vencer as cobranças;
