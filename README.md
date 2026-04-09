# PamakBook - Social Networking Application

## Overview

PamakBook is a Java-based desktop social networking application that simulates a university social media platform. It allows users to create profiles, add friends, join groups, share posts, and analyze social connections through graph theory concepts like friend suggestions and infection tracking.

## Key Features

- **User Management**: Create new users with UoM email validation (dai/ics/iis prefix)
- **Friend System**: Add/remove friends with bidirectional relationships
- **Groups**: Open groups and closed groups (friends-only entry)
- **Posts**: Share text posts with timestamp tracking
- **Friend Suggestions**: Based on triangular closure (friends of friends)
- **Infection Tracking**: Identify potentially exposed users through social connections
- **Friend Graph Visualization**: Interactive graph showing social network structure with diameter calculation
- **Persistence**: Automatic save/load functionality via serialization

## Class Structure

### Main
Entry point that initializes sample users, groups, and launches the application. Handles loading saved data from `PamakBook.ser` or creates a new file if none exists.

### LoginWindow
The authentication gateway where users can:
- Create new accounts (validates UoM email format)
- Login to existing user pages
- Save the entire PamakBook state
- View potential infections for a user

### User
Core entity representing a platform member with:
- Personal details (name, email)
- Friend list (bidirectional)
- Group memberships
- Posts
- Methods for adding friends, checking friendships, finding common friends, and suggesting friends

### UserPage
Main dashboard after login featuring:
- Post creation area
- Recent posts feed from friends
- Group enrollment interface
- Friend addition with suggested friends list
- Friend graph visualization

### Post
Timestamped text content linked to a user. Supports chronological sorting for feed display.

### Group & ClosedGroup
- **Group**: Open membership with description
- **ClosedGroup**: Restricted access - new members must be friends with at least one existing member

### FriendGraph
Visual representation of the social network using the JUNG library:
- Circle layout for vertex positioning
- Graph diameter calculation (longest shortest path)
- Interactive visualization with labeled vertices

### PossibleInfectionsPanel
Contact tracing simulation that identifies:
- Direct friends of an infected user
- Friends of friends (second-degree contacts)
- Excludes the infected individual from results

## Technical Implementation

**Serialization**: All major classes implement `Serializable` for persistent storage between sessions.

**Graph Library**: Uses JUNG (Java Universal Network/Graph Framework) for friend graph visualization and shortest-path algorithms.

**GUI Framework**: Built with Swing components including `JFrame`, `JPanel`, custom `OutputStream` redirection to display console output in text areas.

**Stream Redirection**: Custom `TextAreaOutputStream` class redirects `System.out` to GUI text components for real-time display of method outputs.

## Data Flow

1. Application starts → loads existing `.ser` file or creates new one
2. User logs in or creates account → validated against existing users
3. UserPage displays personalized content
4. Actions (posts, friends, groups) modify user state
5. User can save manually or data persists on next launch

## Dependencies

- JUNG library (edu.uci.ics.jung) for graph algorithms and visualization
- Java Swing for GUI components
- Java I/O for serialization

## Usage Notes

- Email format required: `[dai|ics|iis][AM]@uom.edu.gr` where AM length determines email length
- Friend graph updates automatically when friendships are created
- Recent posts are shown in chronological order (newest first)
- Friend suggestions use triangular closure algorithm
- Closed groups require existing member friendship for enrollment
