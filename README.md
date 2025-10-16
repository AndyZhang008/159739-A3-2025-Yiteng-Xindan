# Movie Management System

A Java-based movie management system with role-based access control for cinema staff. This application provides a user-friendly GUI for managing movie inventories with different permission levels for managers and ticket sellers.

## Project Overview

This system allows cinema staff to:
- **Login** with role-based authentication (Manager/Ticket Seller)
- **View** all movies in a searchable table
- **Search** movies by title or category
- **Add, Update, Delete** movies (Manager only)
- **Export** movie data to files
- **Manage** staff credentials

## Team Members

### Yiteng Zhang (Student ID: 24001868)
**Contributions:**
- Staff authentication and login system
- Role-based access control (Manager/TicketSeller)
- Menu bar implementation (Account menu, File menu)
- Authorization features to restrict unauthorized staff operations
- Test suite for staff management system
- GitHub Actions workflow configuration

### Xindan Zhang (Student ID: 23013603)
**Contributions:**
- Movie table GUI with data display
- Search functionality (by title and category)
- Movie detail GUI
- CRUD operations for movies (Add, Update, Delete)
- UI improvements and button layout
- Export functionality

## Prerequisites

- **Java Development Kit (JDK)**: Version 23 or higher
- **Maven**: For dependency management and building the project
- **Git**: For version control of the repository

## How to Run the Program

### Method 1: Using Maven Command Line

1. **Clone the repository:**
   ```bash
   git clone https://github.com/AndyZhang008/159739-A3-2025-Yiteng-Xindan.git
   cd 159739-A3-2025-Yiteng-Xindan
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn exec:java "-Dexec.mainClass=org.example.Main"
   ```

### Method 2: Using IDE (IntelliJ IDEA)

1. **Open the project in IntelliJ IDEA**
   - File → Open → Select the project folder

2. **Wait for Maven to download dependencies**

3. **Run the Main class**
   - Navigate to `src/main/java/org/example/Main.java`
   - Right-click and select "Run Main.main()"

## Default Login Credentials

The system comes with pre-configured staff accounts in `staffs.csv`:

- **Manager Account:**
  - Username: (check staffs.csv)
  - Role: Full access to all features

- **Ticket Seller Account:**
  - Username: (check staffs.csv)
  - Role: Read-only access

## Project Structure
```

159739-A3-2025-Yiteng-Xindan/
├── .github/              # GitHub Actions workflows
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/example/
│   │           ├── Main.java           # Application entry point
│   │           ├── model/              # Data models (Movie, Staff, etc.)
│   │           ├── service/            # Business logic (MovieManager, StaffManager)
│   │           └── ui/                 # GUI components
│   └── test/
│       └── java/             # Test files
├── reports/                  # Metrics reports
├── movies.txt               # Movie data file
├── staffs.csv              # Staff credentials
├── pom.xml                 # Maven configuration
└── README.md               # Project documentation
```
## Features

### Authentication
- Secure login system with username and password
- Role-based access control (Manager/Ticket Seller)

### Movie Management
- **View** all movies in a table format
- **Search** movies by title or category
- **Add** new movies (Manager only)
- **Update** existing movies (Manager only)
- **Delete** movies (Manager only)
- **Book tickets** for movies

### User Interface
- Clean and intuitive GUI built with Java Swing
- Responsive button layouts
- Menu bar with File and Account options
- Movie detail view panel
- Real-time search filtering

### Data Management
- Export movie data to files
- Persistent storage using CSV and text files
- Data validation
- Automatic data loading on startup

## Testing

Run the test suite:
```bash
mvn test
```

## Continuous Integration

This project uses GitHub Actions for CI/CD:
- Automatic builds on push to `main` branch
- Automated testing on pull requests
- Maven dependency caching for faster builds

## GitHub Repository

**Project Repository:** [https://github.com/AndyZhang008/159739-A3-2025-Yiteng-Xindan](https://github.com/AndyZhang008/159739-A3-2025-Yiteng-Xindan)

## Technologies Used

- **Java 23**
- **Maven** - Build and dependency management
- **JUnit 5** - Testing framework
- **Swing** - GUI framework
- **GitHub Actions** - CI/CD