# Budget Tracker
## Group Members: </br>
Jaishri Thakur </br>
William Watson </br>
Takuro Fukuda </br>

## Introduction
Always spending a lot of money and want to control it? The Budget Tracker is the app for you! The Budget Tracker can</br>
* Can categorize it into different categories
* Let's you know when you're about to go over board
* If you don't know how to budget yourself, the Budget Tracker can do it for you!

## Storyboard
https://www.fluidui.com/editor/live/preview/cF96UlQ0RDJjTWRXaFZmWkpvQXE5Z21oa2JBcTJuT3JONg==
## Functional Requirements
#### As a user I want to be able to store my budget in the application
###### Given:
* I am aware of my budget
* I have not made any changes to my budget
* I have not passed a date that would trigger a payment to increment my budget
###### When:
* I input $1000 as my budget into the app through a field
###### Then:
* When I close and return to the app my budget should be $1000

#### As a user I want to be able to increment my budget by a set amount at a set time interval
###### Given:
* I have setup my budget on the app
###### When:
* I input $3000 to be incremented to my budget at the first of every month
###### Then:
* At the first of every month my budget should increase by $3000

#### As a user I would like to be able to set aside a certain amount of money to be reserved in my budget at a set time interval
###### Given:
* I have a budget setup on the app
###### When:
* I input $2000 set to be taken from the budget a month labled "Bills" at the first of every month
###### Then:-
* On the budget screen an indicator should display showing $2000 deducted from the budget due to the "Bills" item

#### As a user I would like to be alerted when I have spent over 90% of my budget and/or I am over my budget
###### Given:
* My budget is $500
###### When:
* I have spent at least $450 or over $500
###### Then:
* I should be alerted that I am nearing passing my budget, or that I have already passed my budget

## Class Diagram
https://github.com/jthakur2023/budgetTracker/blob/master/BudgetTracker.pdf

## Class Diagram Description
edu.uc.budgettracker.ui: Defining UI. Also describing how will user intract with our app to manage their budgets.

edu.uc.budgettracker.dto: Defining Data types. This one is showing what will user required to be input to complete their missions.

edu.uc.budgettracker.dao: This one is defining about mostly for the base thing. By adding interfaces, we can make contract for the data and by adding database, we can store the data so use can continue work seamlessly without retyping.

## Scrum Roles
Scrum Master/Product Owner/Team: Jaishri Thakur </br>
Frontend Developer: William Watson   </br>
Integration Developer: Takuro Fukuda

## Scrum meeting method
Meetings are on Teams, Friday's at 11:30 am. 
