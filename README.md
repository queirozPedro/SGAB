<h1 align="center">SISTEMA DE GERENCIAMENTO DE ACERVO BIBLIOTECÁRIO (SGAB)</h1>


## **Descrição**
O Sistema de Gerenciamento de Acervo Bibliotecário (SGAB) é um software que tem por objetivo organizar e gerenciar acervos bibliotecários, sendo responsável pelo gerenciamento dos volumes e de usuários deste acervo. Ele se dispõe a ser uma feramenta de fácil utilização mesmo para usuários com pouca ou nenhuma familiaridade com softwares. 

## Requisitos Funcionais
Os requisitos funcionais do sistema estão listados de acordo com sua determinada área.

**Acervo** : O acervo físico da biblioteca, onde podem ser realizadas leituras dentro do espaço da biblioteca ou exemplares podem ser retirados por usuários cadastrados no sistema.
* <b> [RF001] Cadastro de Exemplares </b>: O Cadastro de novos volumes no acervo.
* <b> [RF002] Remoção de Exemplares </b>: A Remoção de exemplares do acervo.
* <b> [RF003] Alteração de Exemplares </b>: Alterações dos dados de exemplares.
* <b> [RF017] Busca de Exemplares </b>: Usuários do sistema podem pesquisar por livros no acervo. 
* <b> [RF018] Exibição do Acervo </b>: O sistema pode exibir todo o acervo cadastrado.

**Usuários** : Os usuários do sistema, que são os leitores que possuem conta cadastrada no sistema, e podem pegar exemplares emprestados.
* <b> [RF004] Cadastro de Usuários </b>: O cadastro dos usuários do acervo.
* <b> [RF016] Login de Usuários </b>: Usuários (Clientes ou Administradores) devem ser capazes de realizar login em suas contas no sistema.
* <b> [RF005] Remoção de Usuários </b>: A remoção da usuários cadastrados no SGAB.
* <b> [RF006] Alteração de Usuários </b>: Alterações de dados de usuários cadastrados.

**Empréstimo** : Os usuários podem pegar volumes do acervo, para serem devolvidos dentro de um tempo definido.
* <b> [RF007] Empréstimo de Exemplares </b>: Usuários cadastrados no sistema estão aptos a retirar exemplares do acervo em condição de empréstimo.
* <b> [RF008] Devolução de Exemplares </b>: A devolução de exemplares que foram emprestados.
* <b> [RF009] Renovação de Empréstimo </b>: A renovação de um empréstimo, que aumenta o prazo de entrega de um exemplar.
* <b> [RF010] Penalização por Devolução Fora do Prazo em Empréstimo </b>: Penalização aplicada ao usuário no caso de atraso na devolução de um exemplar.

**Administradores** : São os usuários com permissão de administradores do sistema. Os funcionários da biblioteca.
* <b> [RF011] Cadastro de Administradores </b>: O cadastro de administradores do SGAB.
* <b> [RF012] Remoção de Administradores </b>: A remoção de administradores dos sistema.
* <b> [RF013] Alteração de Administradores </b>: A possibilidade de alterar dados dos administradores do sistema.
* <b> [RF014] Alteração de Cliente por Administradores </b>: Administradores do sistema devem ser aptos a realizar alterações nos dados de um cliente ou de outro administrador.
* <b> [RF015] Remoção de Cliente por Administradores </b>: Administradores podem excluir a conta de um cliente do sistema.

## Requisitos Não Funcionais
* <b> [RNF001] Facilidade de Uso </b>: O sistema tem o objetivo de ser implementado em bibliotecas públicas, de escolas e universidades, portanto deve ser de fácil entendimento, para que mesmo pessoas com baixo nível de instrução sejam capazes de operar e utilizar. 
* <b> [RNF002] Segurança </b>: Dados de usuários são confidenciais e só devem ser acessados por administradores. Vale salientar que esses também não têm total liberdade sobre esses dados, uma vez que a senha só pode ser acessada pelo próprio usuário.
* <b> [RNF003] Disponibilidade </b>: O SGAB deve estar sempre disponível para utilização, mesmo em cenários que a internet estiver instável.
* <b> [RNF004] Desempenho </b>: Deve ser rápido, com tempo de inicialização inferior a 5 segundos.
  
## Teste de Caixa Cinza
Levando em consideração a modularidade do sistema, e a disponibilidade de todo o código fonte, o tipo de teste escolhido foi o teste de caixa cinza. Os testes de caixa cinza são um tipo de teste de software que combina elementos dos testes de caixa preta (onde o teste é realizado sem conhecimento interno do código) e dos testes de caixa branca (onde o teste é realizado com conhecimento total do código). No teste de caixa cinza, os testadores têm algum conhecimento parcial do código-fonte ou da estrutura interna do software, permitindo-lhes projetar casos de teste mais eficazes com base nesse entendimento limitado. Isso ajuda a identificar falhas no software de maneira mais abrangente do que nos testes de caixa preta, mas sem a complexidade dos testes de caixa branca.

## 📑 Documentação do Projeto
<table>
  Documento do Projeto: 
  <a href= "https://www.overleaf.com/read/dyykcdsyvqdw">
    Sistema de Gerenciamento de Acervo Bibliotecário (SGAB)
  </a>
</table>
<table>
  Apresentação: 
  <a href="https://www.canva.com/design/DAFqe9jHT6s/8AqfwoRr9gzYGnxeYwapXA/edit">
    SGAB
  </a>
</table>

## Diagrama de Classes
![Diagrama de Classes](https://raw.githubusercontent.com/CaraChaato/SGAB/main/Arquivos/Diagrama%20de%20Classes%20-%20SGAB.png)

## :handshake: Colaboradores
<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/LilPuppet">
        <img src="https://avatars.githubusercontent.com/u/100712081?v=4" width="100px;" alt="Foto de Lavinia Dantas no GitHub"/><br>
        <sub>
          <b>Lavinia Dantas</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/CaraChaato">
        <img src="https://avatars.githubusercontent.com/u/110605121?v=4" width="100px;" alt="Foto de Pedro Vinícius no GitHub"/><br>
        <sub>
          <b>Pedro Vinícius</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Pollyanadias">
        <img src="https://avatars.githubusercontent.com/u/110605099?v=4" width="100px;" alt="Foto de Pollyana Dias no GitHub"/><br>
        <sub>
          <b>Pollyana Dias</b>
        </sub>
      </a>
    </td>
  </tr>
</table> 
