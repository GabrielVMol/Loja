# Loja em Java 1.8 e SpringBoot
#Classes
```
![LOJA - Imagem Classes](URL_da_Imagem)
![LOJA - Imagem Classes](URL_da_Imagem)
```
#Regras
```
Produto
Não pode ser excluido com estoque, e ser salvo com preço e estoque negativo

Cliente
Não pode ser salvo com cpf e email repitido

Vendedor
Não pode ser salvo com cpf repitido e salario negativo

Vendas
Cliente e vendedor não pode ser nulo, quantidade não pode ser maior que o estoque disponível, data de garantia nao pode ser menor que a data da venda, ao efetuar ou excluir uma venda o estoque é atualizado
```

#Testes
```
![LOJA - Imagem Testes](URL_da_Imagem)
```
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
