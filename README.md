# Desenvolvimento-Java-Para-Dispositivos-Moveis
Disciplina voltada ao desenvolvimento de aplicativos Android nativos utilizando Java e Android Studio. Foram abordados conceitos como interfaces XML, Activities, navegação entre telas, menus, internacionalização, SharedPreferences, persistência de dados com Room Database e boas práticas de desenvolvimento móvel.


# Controle de Chaves PIX

Projeto desenvolvido na disciplina **CETEJ36 - Android (Java)** da **UTFPR**, com o objetivo de aplicar conceitos fundamentais do desenvolvimento de aplicativos Android utilizando Java e Android Studio.

O aplicativo permite o gerenciamento de chaves PIX, possibilitando cadastrar, listar, editar, excluir e organizar chaves próprias ou de terceiros.

---

# Objetivo

Desenvolver um aplicativo Android completo evoluindo gradualmente durante a disciplina, aplicando conceitos de:

- Activities
- Layouts XML
- Navegação entre telas
- ListView e Adapter customizado
- Menus e Menus Contextuais
- Persistência de dados
- Internacionalização
- SharedPreferences
- Banco de Dados Room
- Material Design
- Ciclo de vida de Activities

---

# Funcionalidades

## Cadastro de Chaves PIX

Permite cadastrar:

- Nome/Apelido
- Chave PIX
- Tipo da chave
  - E-mail
  - Telefone
  - CPF
  - Aleatória
- Dono da chave
  - Própria
  - Terceiro
- Favorita

Validações implementadas:

- Nome obrigatório
- Chave obrigatória
- Seleção obrigatória do dono da chave

---

## Listagem de Chaves

Exibição de todas as chaves cadastradas através de uma ListView com Adapter customizado.

Informações exibidas:

- Apelido
- Tipo da chave
- Proprietário
- Chave PIX
- Status de favorita

---

## Edição de Registros

Através do menu contextual é possível:

- Editar um registro existente
- Alterar qualquer informação cadastrada
- Persistir alterações no banco de dados

---

## Exclusão de Registros

Através do menu contextual é possível remover registros.

Antes da exclusão é exibido um:

- AlertDialog de confirmação

Evita exclusões acidentais.

---

## Tela Sobre

Tela contendo:

- Nome da autora
- Curso
- E-mail
- Descrição do aplicativo
- Informações da UTFPR
- Logo da UTFPR

---

# Evolução do Projeto

## Entrega 1 - Cadastro

Implementação da primeira Activity contendo:

- EditText
- Spinner
- RadioGroup
- CheckBox
- Validações
- Toasts
- Layout responsivo

---

## Entrega 2 - Listagem

Implementação de:

- Classe Entidade
- ArrayList
- ListView
- Adapter Customizado
- Evento de clique em itens
- Exibição dinâmica dos dados

---

## Entrega 3 - Navegação entre Activities

Implementação de:

- Activity de Cadastro
- Activity de Listagem
- Activity Sobre
- Navegação entre telas
- Retorno de resultados utilizando ActivityResultLauncher
- Atualização dinâmica da lista

---

## Entrega 4 - Menus e Botões Up

Implementação de:

### Menu de Opções

- Adicionar
- Sobre
- Salvar
- Limpar

### Menu Contextual

- Editar
- Excluir

### Botões Up

Navegação entre:

- Cadastro → Lista
- Sobre → Lista

---

## Entrega 5 - Internacionalização e SharedPreferences

Implementação de:

### Internacionalização

Suporte para:

- Inglês (padrão)
- Português do Brasil

Todos os textos foram externalizados para:

- values/strings.xml
- values-pt-rBR/strings.xml

### SharedPreferences

Persistência das preferências do usuário:

- Ordenação A-Z
- Ordenação Z-A
- Restauração da ordenação padrão

---

## Projeto Final - Persistência com Room

Substituição da estrutura temporária em memória por persistência real utilizando Room.

Implementado:

### Entity

Classe:

```java
ChavePix
```

### DAO

Interface:

```java
ChavePixDao
```

Operações:

- Insert
- Update
- Delete
- Select All

### Database

Classe:

```java
AppDatabase
```

### Provider

Classe:

```java
AppDatabaseProvider
```

Responsável por disponibilizar instância única do banco.

---

# Tecnologias Utilizadas

- Java
- Android SDK
- Android Studio Otter
- XML
- Room Persistence Library
- SharedPreferences
- Material Components

---

# Estrutura do Projeto

```
java/
 ├── MainActivity
 ├── ListaChavesActivity
 ├── SobreActivity
 ├── ChavePix
 ├── ChavePixAdapter
 ├── ChavePixDao
 ├── AppDatabase
 └── AppDatabaseProvider

res/
 ├── layout
 ├── menu
 ├── drawable
 ├── values
 └── values-pt-rBR
```

---

# Conceitos Android Aplicados

- Activities
- Intents
- ActivityResultLauncher
- ListView
- Adapter Customizado
- Spinner
- RadioGroup
- CheckBox
- Toast
- AlertDialog
- Menus
- Menu Contextual
- SharedPreferences
- Internacionalização
- Room Database
- Persistência Local
- Material Design

---

# Autor

**Jeniffer Cristina Freitas Ramos**

Curso: CETEJ36 - Android (Java)

Universidade Tecnológica Federal do Paraná (UTFPR)
