# Topology API

<div align="center">

![Github Language Count](https://img.shields.io/github/languages/count/mohamedhossam01/Topology-API)
![GitHub contributors](https://img.shields.io/github/contributors/mohamedhossam01/Topology-API?color=%2300&logo=GitHub)
![GitHub top language](https://img.shields.io/github/languages/top/mohamedhossam01/Topology-API?color=%2300)

</div>

## Table of Contents

1. [Background](#Background)
2. [Technologies](#Technologies)
3. [Documentation](#Documentation)
4. [Examples](#Examples)
5. [Testing](#Testing)
6. [Code Analysis](#Code-Analysis)

## Background

Topology API (Java Package) which can help to store JSON files from files to memory and vice versa. It supports the following:
<ol>
    <li>
        <b> Read a topology from a given JSON File and store it in the memory.</b>
    </li>
    <li>
        <b>Write a given topology from the memory to a JSON file.</b>
    </li>
    <li>
        <b>Query about which topologies are currently in the memory.</b>
    </li>
    <li>
        <b>Delete a given topology from memory.</b>
    </li>
    <li>
        <b>Query about which devices are in a given topology.</b>
    </li>
    <li>
        <b>Query about which devices are connected to a given netlist node in
a given topology.</b>
    </li>
</ol>

The files are parsed using [Gson](https://github.com/google/gson) library. And testing is done by [Junit5](https://github.com/junit-team/junit5) for both the API and the classes

## Technologies

- Java
- HTML, CSS and JS for documentation and PVS report
- JUnit5 for testing
- Gson for parsing JSON files
- PVS-Studio for static code analysis

## Documentation

Java API documentation is available [here](https://github.com/mohamedhossam01/Topology-API/tree/main/Docs/JavaDoc) To see the documentation in a browser, please click on the following link: <a href="https://mohamedhossam01.github.io/Topology-API-Documentation/com/example/api/package-summary.html" target = "_blank">Java API Documentation</a><br>
It's also in the code.

## Examples
Correct JSON file is available [here](https://github.com/mohamedhossam01/Topology-API/blob/main/src/main/resources/topologies1.json) <br>
<b>Assumptions</b>
<ul>
<li> Each file must contain "id" and "components" fields. </li>
<li> Each file must contain only one topology. </li>
<li> Any additional tags such as "comments", "description", "tags" are allowed in the topology or in components</li>
</ul>

## Testing
The testing is done by [Junit5](https://github.com/junit-team/junit5) for both the API and the classes.<br>
Testing code is available [here](https://github.com/mohamedhossam01/Topology-API/tree/main/src/test)

## Code Analysis
The code analysis is done by [PVS-Studio](https://pvs-studio.com/en/) for static code analysis.<br>
The report is available [here](https://github.com/mohamedhossam01/Topology-API/tree/main/Docs/PVS-Studio%20Code%20Analysis%20Output) To see the report in a browser, please click on the following link: <a href="https://mohamedhossam01.github.io/Topology-API-Code-Analysis/" target = "_blank">PVS-Studio Report</a><br>
