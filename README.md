# Controle-de-Estoque

<p align="left">
<img alt="Java" title="Java" width="35px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg"/>
<img alt="Maven" title="Maven" width="35px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/maven/maven-original.svg"/>
<img alt="Git" title="Git" width="35px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg"/>
<img alt="GitHub" title="GitHub" width="35px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg"/>
<img alt="IntelliJ IDEA" title="IntelliJ IDEA" width="35px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg"/>
</p>

## Academic Information

* **Educational Institution:** UNICEPLAC
* **Degree Program:** Software Engineering
* **Course:** Elicitation to Programming
* **Instructor:** Prof. Hudson Neves

## 1. Description

Controle-de-Estoque is a Java desktop application with a graphical interface built with Swing, developed to control the registration and the stock movement of products of a commercial establishment. The system allows users to register, search, edit, and remove products, record stock entries and withdrawals, and generate a stock report.

The project was developed to apply fundamental programming concepts and Object-Oriented Programming, organizing the code with the MVC (Model–View–Controller) architecture and using dynamic in-memory collections to store the data.

## 2. General Objective

To develop a Java desktop application that allows users to control products and their stock movements, applying Object-Oriented Programming concepts, dynamic in-memory collections (ArrayList), graphical interfaces with Swing, business rules, and the MVC architectural pattern.

## 3. Problem the System Solves

Controlling the products of a store without any organized record makes it difficult to know how many units are available, how much the stock is worth, and which products need to be restocked.

This project proposes a simple solution by centralizing product registration, stock entries and withdrawals, and a report that shows the total financial value of the stock and the products below the minimum quantity.

## 4. Target Audience

The system is intended for small commercial establishments and for people who need a simple tool to control the products and the stock of a business.

## 5. Features

* **Product registration:** register code, description, category, unit price, quantity, and minimum stock.
* **Product listing:** display all registered products in a table (`JTable`), with sorting by column. Products below the minimum stock are highlighted in red.
* **Product search:** find products by code or description.
* **Edit and delete:** change the information of a product or remove it from the registry, with confirmation before deleting.
* **Stock entry:** register restocking and update the available quantity.
* **Stock withdrawal:** register withdrawals and update the available quantity.
* **Stock report:** calculate the total financial value of the stock and list the products below the minimum quantity.
* **Main menu:** a main screen that gives access to all the other screens.

### Business rules

* Duplicate product codes are not allowed.
* Negative prices are not allowed.
* Stock movements that would result in negative stock are blocked.
* Movement quantities must be greater than zero.

## 6. Technologies Used

* Java
* Java Swing
* MVC (Model–View–Controller)
* ArrayList
* Maven
* Git
* GitHub
* IntelliJ IDEA

## 7. Solution Architecture

The application follows the MVC pattern, separating the data, the graphical interface, and the control of the operations.

### Model (`com.project.model`)

* ``Produto:`` Represents the product, containing code, description, category, unit price, quantity, and minimum stock. It also provides helper methods to calculate the total value of the product in stock and to check if it is below the minimum.
* ``Movimentacao:`` Represents a record of the movement history (product code, type, quantity, and date and time).
* ``TipoMovimentacao:`` Enum with the movement types: `ENTRADA` (entry) and `SAIDA` (withdrawal).

### Controller (`com.project.controller`)

* ``ProdutoController:`` Stores the product list and applies the registration rules (duplicate code, negative price, empty fields). Provides register, list, search, edit, and delete operations.
* ``MovimentacaoController:`` Registers stock entries and withdrawals, updates the quantities, blocks movements that would result in negative stock, and keeps the movement history.
* ``RelatorioController:`` Calculates the total value of the stock and identifies the products below the minimum quantity, providing the data to the report screen.

### View (`com.project.view`)

* ``TelaPrincipal:`` Main screen with the menu to access the other screens.
* ``TelaCadastroProduto:`` Form used both to register a new product and to edit an existing one.
* ``TelaConsultaProdutos:`` Product table with search, and the options to create, edit, and delete.
* ``TelaMovimentacoes:`` Screen to register stock entries and withdrawals.
* ``TelaRelatorio:`` Screen that shows the stock report.
* ``ProdutoTableModel:`` Table model that connects the product list to the `JTable`.

### Main

* ``Main:`` Entry point of the application. Creates a single instance of each controller, shared by all screens, and opens the main screen.

The separation between data, interface, and control keeps the code organized and maintainable, and the views contain no business rules.

## 8. Database

The project uses `ArrayList` dynamic collections to store products and movements in memory during application execution.

No external database is used in this version, so the data is lost when the application is closed.

## 9. Frameworks and Libraries

* Java Standard Library.
* `javax.swing` and `java.awt`, used to build the graphical interface.
* `java.util.ArrayList`, used to store products and movements.
* `java.time`, used to record the date and time of each movement.
* `java.text.NumberFormat`, used to format currency values.

No external frameworks or libraries are used.

## 10. Requirements

* Java Development Kit (JDK) installed, compatible with the version configured in the `pom.xml`.
* A Java-compatible IDE, such as IntelliJ IDEA.
* Git installed to clone the repository, if necessary.

## 11. Installation and Execution

1. Clone the repository.
2. Open the project in a Java-compatible IDE.
3. Make sure the JDK is properly configured.
4. Run the `Main.java` class (`com.project.Main`).
5. Use the main menu to access the screens of the system.

## 12. How to Use

When the application starts, the main screen shows a menu with the available options.

* **Product registration:** fill in the product data and save. The system warns the user if the code already exists or if any value is invalid.
* **Product search:** type a code or part of a description and click search. The table can be sorted by clicking on the column headers. Select a product to edit or delete it.
* **Movements:** enter the product code, choose entry or withdrawal, and inform the quantity. The current stock is displayed and updated after each movement.
* **Report:** shows the total value of the stock and the table of products below the minimum quantity.

## 13. Project Structure

```text
Controle-de-Estoque/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── project/
│                   ├── Main.java
│                   ├── controller/
│                   │   ├── ProdutoController.java
│                   │   ├── MovimentacaoController.java
│                   │   └── RelatorioController.java
│                   ├── model/
│                   │   ├── Produto.java
│                   │   ├── Movimentacao.java
│                   │   └── TipoMovimentacao.java
│                   └── view/
│                       ├── TelaPrincipal.java
│                       ├── TelaCadastroProduto.java
│                       ├── TelaConsultaProdutos.java
│                       ├── TelaMovimentacoes.java
│                       ├── TelaRelatorio.java
│                       └── ProdutoTableModel.java
├── pom.xml
└── README.md
```

## 14. Project Status

Developed.

## 15. Team Members

* Joao Marcos Marques Silva
* Julia Medeiros Evangelista
* Lara Pereira Alves
* Joao Gabriel Soares Alves da Silva
* Esther da Silva Marques
* Anna Yasmin Alves Ferreira
* Heveli Ribeiro Pereira

## 16. Future Improvements

* Persist the data in files or in a database, so the information is not lost when the application is closed.
* Add a screen to view the movement history.
* Export the stock report to PDF or CSV.
* Add user authentication with different access levels.
* Add filters by category and charts to the report.

## [![Clique para assistir ao vídeo](https://img.youtube.com/vi/as5nEYtqBYA/0.jpg)](https://www.youtube.com/watch?v=as5nEYtqBYA)
