# Beacon Utility System (BUS)

Beacon Utility System (BUS) is a Java-based desktop application developed using Object-Oriented Programming (OOP) concepts and database integration.  
This system was created as an academic project for the **Object Oriented Programming** module at the **University of Kelaniya**.

---

## Academic Information
- **Course Code:** COST 21053  
- **Course Name:** Object Oriented Programming  
- **Student Number:** HS/2021/0051  
- **Department:** Industrial Management  
- **Faculty:** Science  
- **University:** University of Kelaniya  

---

## Project Overview
Beacon Utility System is designed to address common problems faced by bus passengers, such as sudden ticket price changes, lack of accurate arrival time information, and difficulty in managing bus reservations and lost items.  
The system provides a centralized solution for bus tracking, ticket pricing, and utility management for a private bus transportation company.

**BUS stands for:**
- **B – Beacon:** Represents guiding signals and bus tracking  
- **U – Utility:** Highlights usefulness for passengers and staff  
- **S – System:** Represents an integrated transportation management solution  

---

## Main Features
- User registration and login system
- Role-based access for **Staff** and **Normal Users**
- View bus routes, available times, and ticket prices
- Automatic ticket price calculation
- Display bus arrival times for selected routes
- Bus reservation system for tours
- Lost item management with image support
- Automatic removal of lost items after 7 days
- Password reset using NIC verification
- Database connectivity using JDBC
- Input validation and exception handling

---

## User Roles
### Staff
- Update ticket prices
- View bus reservations
- Perform staff authentication before restricted actions

### Normal User
- Register and manage user profile
- Check bus routes, arrival times, and ticket prices
- Add and view lost items
- Reset password using NIC
- Remove user account

---

## Technologies Used
- Java
- Java Swing (GUI)
- JDBC
- SQL Database
- NetBeans IDE

---

## Database Design
- **Database Name:** BUS  
- **Main Tables:**
  - NORMAL_USER
  - PASSWORD
  - BUS_INFO
  - TICKET_PRICE
  - STAFF
  - ROUTE_1_TIMES
  - LOST_ITEMS
  - BUS_RESERVATIONS

---

## Use of OOP Concepts
- **Inheritance:** All GUI interfaces extend `javax.swing.JFrame`
- **Encapsulation:** Database connection details managed via `DatabaseManager` class
- **Abstraction:** Abstract methods used for future route-based enhancements
- **Polymorphism:** Staff authentication behavior overridden for different actions
- **Exception Handling:**  
  - SQLException  
  - IOException  
  - ClassNotFoundException  

---

## How to Run the Project
1. Open **NetBeans**
2. Select **File → Open Project**
3. Choose the project folder
4. Configure the database connection
5. Run the project

---

## Future Enhancements (Not Implemented)
- Add new bus halts dynamically
- Add new bus routes
- Modify bus arrival times via the system

---

## Conclusion
Beacon Utility System demonstrates the practical application of Java OOP principles, database integration, and exception handling in solving real-world transportation problems.  
It provides a structured and user-friendly solution for managing bus utility services efficiently.

---

