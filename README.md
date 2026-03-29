# Gym Management System (SQLite Version)

A smart gym management system that helps reduce overcrowding by analyzing crowd patterns and suggesting optimal workout times.

## 🎯 Features

- **🔐 User Authentication** - Registration and login system with validation
- **📋 Workout Split Setup** - Configure weekly workout schedules
- **👥 Crowd Analysis** - Real-time gym crowd level monitoring (simulated)
- **🎯 Smart Recommendations** - AI-powered time slot suggestions based on fitness level
- **📱 Responsive Design** - Works on all devices with modern UI
- **🗄️ SQLite Database** - Simple file-based database, no setup required

## 🛠️ Technology Stack

### Backend
- **Java** - Core application logic
- **Java HttpServer** - Built-in HTTP server
- **SQLite** - Database for data persistence
- **JDBC** - Database connectivity

### Frontend
- **HTML5** - Semantic markup
- **CSS3** - Modern styling with animations
- **JavaScript (Vanilla)** - Interactive functionality
- **Responsive Design** - Mobile-first approach

### Database
- **SQLite** - File-based database (gym_management.db)
- **Auto-initialization** - Tables and sample data created automatically

## 📁 Project Structure

```
gym_managementv1/
├── src/                    # Java backend source files
│   ├── Main.java           # Application entry point
│   ├── Server.java         # HTTP server and routing
│   ├── DatabaseConnection.java  # SQLite database management
│   ├── UserHandler.java    # User authentication logic
│   ├── SplitHandler.java   # Workout split management
│   ├── CrowdHandler.java   # Crowd analysis simulation
│   └── RecommendationHandler.java  # Smart recommendations
├── frontend/               # Web interface files
│   ├── index.html         # Login/registration page
│   ├── dashboard.html     # Main dashboard interface
│   └── style.css          # Complete styling system
├── database/               # Database setup files
│   └── setup.sql          # SQLite schema reference
├── run.bat               # Application startup script
├── sqlite-jdbc-3.45.1.0.jar  # SQLite JDBC driver
└── README.md             # This documentation file
```

## 🚀 Quick Start

### Prerequisites
- **Java 8+** installed
- **SQLite JDBC driver** (included)

### Running the Application

1. **Clone or download** the project
2. **Navigate** to the project directory
3. **Run the application:**
   ```bash
   run.bat
   ```
4. **Open browser** to `http://localhost:8080`
5. **Register** a new account or login with existing credentials

### Sample Login Credentials
- **Username:** john_doe
- **Password:** pass123

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE,
    password TEXT,
    name TEXT,
    age INTEGER,
    membership TEXT,
    fitness_level TEXT
);
```

### Workout Splits Table
```sql
CREATE TABLE workout_splits (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT,
    day_of_week TEXT,
    body_part TEXT,
    preferred_time TEXT
);
```

## 🔧 API Endpoints

### User Management
- `POST /register` - Create new user account
- `POST /login` - Authenticate user credentials

### Workout Management
- `POST /split` - Create workout split
- `GET /split?username=X` - Get user's workout splits

### Features
- `GET /crowd` - Get crowd analysis (simulated)
- `GET /recommend?username=X` - Get workout recommendations

## 🎨 UI Features

### Login/Register Page
- Modern glass morphism design
- Form validation and error handling
- Smooth transitions between modes
- Responsive layout

### Dashboard
- Tabbed navigation system
- Real-time data fetching
- Interactive forms
- Mobile-responsive design

## 🔧 Configuration

### Database Connection
The application uses SQLite with automatic database creation. No additional configuration required.

### Server Configuration
- **Port:** 8080
- **Host:** localhost
- **Static Files:** Served from `frontend/` directory

## 🐛 Troubleshooting

### Common Issues

1. **Port 8080 in use:**
   - Stop other services using port 8080
   - Or modify the port in `Server.java`

2. **SQLite driver not found:**
   - Ensure `sqlite-jdbc-3.45.1.0.jar` is in the project directory
   - The `run.bat` script will download it automatically if missing

3. **Registration fails:**
   - Check browser console for JavaScript errors
   - Verify server is running and accessible

### Debug Mode
- Check server console for detailed error messages
- Use browser developer tools for frontend debugging
- Database file: `gym_management.db` (created automatically)

## 📝 Development Notes

### Architecture
- **MVC Pattern** - Separation of concerns
- **RESTful API** - Standard HTTP methods
- **CORS Enabled** - Cross-origin requests supported
- **Error Handling** - Comprehensive error management

### Security Notes
- **Demo Application** - Passwords stored in plain text for simplicity
- **Production Ready** - Add password hashing and input validation
- **Session Management** - Basic session storage

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is for educational purposes. Feel free to use and modify as needed.

## 📞 Support

For issues and questions:
- Check the troubleshooting section
- Review the code comments
- Test with the provided sample credentials

---

**Version:** 1.0  
**Last Updated:** 2024  
**Author:** Gym Management System Team

- **Backend**: Plain Java (Java 8+)
- **Database**: MongoDB
- **Connection**: MongoDB Java Driver
- **Server**: Java's built-in HttpServer
- **Frontend**: HTML + CSS + JavaScript (No frameworks)

## 📋 Prerequisites

1. **Java 8+** installed
2. **MongoDB** installed and running
3. **MongoDB Java Driver** (mongodb-driver-sync)

## 🚀 Setup Instructions

### 1. Install and Start MongoDB

**Windows:**
```bash
# Download and install MongoDB from https://www.mongodb.com/try/download/community
# Start MongoDB service
net start MongoDB
```

**Linux/Mac:**
```bash
# Install MongoDB
sudo apt-get install mongodb  # Ubuntu/Debian
brew install mongodb          # macOS

# Start MongoDB
sudo systemctl start mongod  # Linux
brew services start mongodb-community  # macOS
```

### 2. Setup Database

Run the MongoDB setup script to create collections and insert sample data:

```bash
# Navigate to database directory
cd database

# Run setup script
mongo gym_db setup-mongodb.js
```

### 3. Download MongoDB Java Driver

Download the MongoDB Java Driver:
- Visit: https://mongodb.github.io/mongo-java-driver/
- Download: `mongodb-driver-sync-4.x.x.jar`
- Place it in your project root directory

### 4. Compile and Run

```bash
# Compile all Java files
javac -cp mongodb-driver-sync-4.x.x.jar src/*.java

# Run the application
java -cp .:mongodb-driver-sync-4.x.x.jar src.Main
```

**Windows users:**
```bash
# Compile
javac -cp "mongodb-driver-sync-4.x.x.jar" src\*.java

# Run
java -cp ".;mongodb-driver-sync-4.x.x.jar" src.Main
```

### 5. Access the Application

Open your browser and navigate to:
```
http://localhost:8080/frontend/
```

## 🔑 Sample Login Credentials

| Username | Password | Name | Fitness Level |
|----------|----------|------|---------------|
| john_doe | password123 | John Doe | Intermediate |
| jane_smith | pass456 | Jane Smith | Advanced |
| mike_wilson | mike123 | Mike Wilson | Beginner |
| sarah_jones | sarah789 | Sarah Jones | Intermediate |
| alex_brown | alex321 | Alex Brown | Advanced |

## 📁 Project Structure

```
GymManagementSystem/
├── src/
│   ├── Main.java                    # Application entry point
│   ├── Server.java                  # HTTP server handling
│   ├── DatabaseConnection.java      # MongoDB connection
│   ├── UserHandler.java             # User operations
│   ├── SplitHandler.java            # Workout split management
│   ├── CrowdHandler.java            # Crowd analysis
│   └── RecommendationHandler.java   # Smart recommendations
├── frontend/
│   ├── index.html                   # Login/Register page
│   ├── dashboard.html               # Main dashboard
│   └── style.css                    # Styling
├── database/
│   └── setup-mongodb.js             # Database setup script
└── README.md                       # This file
```

## 🎮 How to Use

### 1. Register/Login
- Create a new account or use sample credentials
- Fill in your details: name, age, membership type, fitness level

### 2. Setup Workout Split
- Select your workout split type (Push Pull Legs, Bro Split, Full Body)
- Assign body parts to each day (Monday → Chest, Tuesday → Back, etc.)
- Choose preferred time slots (Morning, Afternoon, Evening)

### 3. View Crowd Analysis
- See real-time crowd levels for each body part and time slot
- 🔴 PEAK = Very crowded (4+ people)
- 🟡 MODERATE = Some crowd (2-3 people)  
- 🟢 OFF-PEAK = Not crowded (0-1 person)

### 4. Get Smart Recommendations
- Enter your preferred body part and time
- Get AI-powered suggestions for optimal workout times
- See warnings if your preferred time is too crowded

## 🔧 MongoDB Collections

### Users Collection
```javascript
{
  username: "john_doe",
  password: "password123",
  name: "John Doe",
  age: 25,
  membership: "monthly",
  fitness_level: "intermediate"
}
```

### Workout Splits Collection
```javascript
{
  username: "john_doe",
  day_of_week: "Monday",
  body_part: "Chest",
  preferred_time: "Morning"
}
```

## 🐛 Troubleshooting

### Common Issues

1. **MongoDB Connection Failed**
   - Make sure MongoDB is running: `net start MongoDB` (Windows)
   - Check connection string: `mongodb://localhost:27017`
   - Verify MongoDB service status

2. **Port Already in Use**
   - Change port in `Main.java` (line 15)
   - Kill process using port 8080: `netstat -ano | findstr :8080`

3. **Compilation Errors**
   - Ensure MongoDB driver JAR is in correct location
   - Check classpath includes MongoDB driver
   - Verify Java 8+ is installed

4. **Browser Not Loading**
   - Check server started successfully
   - Verify URL: `http://localhost:8080/frontend/`
   - Check firewall settings

### Debug Mode

Enable debug logging by modifying `DatabaseConnection.java`:
```java
System.out.println("MongoDB Connection String: " + URI);
System.out.println("Database: " + DATABASE_NAME);
```

## 🎓 Learning Objectives

This project demonstrates:
- **MongoDB Integration** - NoSQL database operations
- **REST API Development** - HTTP server and endpoints
- **Frontend Development** - HTML/CSS/JavaScript
- **Database Design** - Collection structure and indexing
- **Error Handling** - Exception management
- **Authentication** - User login system
- **Data Analysis** - Crowd pattern analysis

## 📝 Notes for Beginners

- **No SQL Knowledge Required** - Uses MongoDB document structure
- **Simple Architecture** - Easy to understand and modify
- **Beginner-Friendly** - Every line commented
- **No External Dependencies** - Pure Java implementation
- **Educational Purpose** - Perfect for learning full-stack development

## 🚀 Future Enhancements

- [ ] User profile pictures
- [ ] Equipment availability tracking
- [ ] Mobile app version
- [ ] Social features (friend workouts)
- [ ] Progress tracking
- [ ] Trainer recommendations
- [ ] Nutrition planning

## 📞 Support

If you encounter any issues:
1. Check the troubleshooting section above
2. Verify MongoDB is running properly
3. Ensure all files are in correct locations
4. Check Java and MongoDB versions compatibility

---

**Happy Coding! 💪🏋️‍♂️**
