
# Cadastro de Funcionários
Desenvolvido por Brenda Martinez e Isaque Lis

### Páginas
- localhost:8080/funcionario/cadastrar_funcionario
- localhost:8080/funcionario/gerenciar_funcionario
- localhost:8080/cargo/cadastrar_cargo
- localhost:8080/cargo/gerenciar_cargo
- localhost:8080/departamento/cadastrar_departamento
- localhost:8080/departamento/gerenciar_departamento
- localhost:8080/h2-console

### O que falta implementar
- Regras de Negócio para Persistência de Dados
    - Campos obrigatórios de entidades sem registros
    - Remoção de entidades vínculadas à outras entidades
    - Salário de Funcionário deve ser igual ou maior ao Salário base de seu cargo
    
- Funcionário
    - API Rest
    
- Endereço
    - ?

- Estilização de Telas

### Requisitos
![](/diagram.svg)

### Para fazer uma build limpa do projeto, digite no console: 

`mvn clean install spring-boot:run`