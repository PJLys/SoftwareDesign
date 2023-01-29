# Money Tracker Project
### University of Antwerp FTI/EI 5-Software Design

## Outline
Making a system that calculates your debt when going out in group.

## Functional Requirements
- Addition/Removal of people
- Global bill at end of trip (who pays who; how much)
- GUI
- Ticket inputting
- Different Ticket types (event dependant)
- Even/Uneven split tickets

## Non-functional requirements
- single People DB
- single Ticket DB
- Implementation of design patterns
- UML diagrams
- Tests 

## Used Patterns
- Singleton for the databases
- MVC for frontend/backend connectivity
- Observer for error messages
- Factory for creation of tickets
- Iterator for calculating the end bill

## Unique feature
Our system is designed to be resilient to different kinds of misuse and has therefore a basic error handling system that checks for:
(Frontend)
- Input type
- Input values
(Backend)
- Duplicate Person entry
- Removal of non-present persons
- Removal of Person linked in ticket
- People with the same name
- Duplicate Ticket entry

Errors will be shown using an error message in the frontend

## Possible improvements
- Error handling could be done using error codes.

