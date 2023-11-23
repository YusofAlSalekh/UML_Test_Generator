# UML_Test_Generator
Bachelor's Graduation Project.
Subject: Development of tests from the UML-models of applications.

One of the recognized principles behind the organization of software engineering processes is the "Tests first principle" — that is, the initial development of software product tests is carried out in parallel with its design and construction, as soon as the product requirements are specified. In such software product lifecycle models, such as RUP, a UML model is an integral part of the requirements for a software product. The UML model is a formalized artifact suitable for automated analysis and eventual transformation into a program by a programmer.
Thorough and effective testing is necessary to create high-quality and reliable software projects. Therefore, test development is an important task throughout the design of a software project. The main purpose of testing is to cover as many errors in the software project as possible. In addition, manual testing of large software projects requires a lot of time and labor. In this regard, the automated design of test cases is a very important activity in the process of testing software projects. Using the UML activity diagram, it is possible to generate test cases automatically.
The purpose of the final qualifying work is to create a software product — a test generator in the Java programming language that is capable of developing tests for a UML activity diagram of a software product presented in XML format.
To achieve this goal, the following tasks are solved:
Analyze UML activity diagrams in order to investigate the possibility of generating tests based on the presentation of their XML files.
Develop several UML activity diagrams that differ in structure.
Develop an algorithm for creating a test generator based on the XML representation of UML diagrams based on the analyzed information.
Implement and test a software product — a test generator.
To solve these problems, studies were conducted on the UML models of activity diagrams of software products. The subject of these studies is the process of creating a test generator based on UML activity diagrams.
As a result of this work, a test generator was created, which, using the input file of the XML format of the UML activity diagram, produces a representative suite of unit tests of some function for which the UML activity diagram was designed. It achieves both statement coverage and branch coverage by traversing the graph representation of the diagram by means of well-known graph traversal algorithms. The generated test cases will then be used by software engineers to test their software product.
