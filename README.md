# Loja em Java 1.8 e SpringBoot
#Classes

![Resources](https://github.com/GabrielVMol/Loja/assets/96446469/fd2758d7-c52c-43b4-83d1-653e4ee1f1c0)
![escopo](https://github.com/GabrielVMol/Loja/assets/96446469/2fe092c1-1f1f-4e51-ad27-97095b3b837f)

#Regras
```
Produto
Não pode ser excluido com estoque, e ser salvo com preço e estoque negativo

Cliente
Não pode ser salvo com cpf e email repitido

Vendedor
Não pode ser salvo com cpf repitido e salario negativo

Vendas
Cliente e vendedor não pode ser nulo, quantidade não pode ser maior que o estoque disponível,
data de garantia nao pode ser menor que a data da venda, ao efetuar ou excluir uma venda o estoque é atualizado
```

#Testes

![test](https://github.com/GabrielVMol/Loja/assets/96446469/04a710f5-3c53-4608-bbad-af3539146a1b)


# Projeto Ultiliza
```
Java 1.8
SpringBoot
Maven
Autenticação e autorização, usando Oauth2 e JWT
JUnit para Testes 
Swagger para documentar a Api
H2 Database
```
