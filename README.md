# Gym Management System (MongoDB Version)

A smart gym management system that helps reduce overcrowding by analyzing crowd patterns and suggesting optimal workout times.

## 🎯 Features

- **🔐 User Authentication** - Registration and login system
- **📋 Workout Split Setup** - Configure weekly workout schedules
- **👥 Crowd Analysis** - Real-time gym crowd level monitoring
- **🎯 Smart Recommendations** - AI-powered time slot suggestions
- **📱 Responsive Design** - Works on all devices

## 🛠️ Technology Stack

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
