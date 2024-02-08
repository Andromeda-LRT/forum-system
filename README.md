# Forum System

## Project Description

Design and implement a Forum System where users can create posts, add comments, and upvote/downvote content. This forum
revolves around [Live-Travel-Relax].

## Functional Requirements

### Entities

- **User:**
    - First and last name (4-32 symbols)
    - Email (unique and valid)
    - Username and password

- **Admin:**
    - First and last name (4-32 symbols)
    - Email (unique and valid)
    - Phone number (optional)

- **Post:**
    - User who created it
    - Title (16-64 symbols)
    - Content (32-8192 symbols)
    - Comments and likes

### Public Part

- Home page for anonymous users with core features and statistics
- Registration and login for anonymous users
- Lists of top 10 most commented and recently created posts

### Private Part

- Authentication for users
- Browse, sort, and filter posts
- View single post details, comments, and likes
- Update profile information and upload profile photo
- Create new posts and edit personal posts or comments
- View user's own and others' posts and comments

### Administrative Part

- Search for users by username, email, or first name
- Admin privileges to block/unblock users and delete posts
- List of all posts with filtering and sorting options

### Optional Feature

- Post Tags: Users can add/remove/edit tags on their posts. Admins can manage tags on all posts.

## REST API

Documented using Swagger.

### Users

- CRUD operations
- Search by username, email, or first name
- Filter and sort certain users' posts

### Admin

- Make other users admin
- Delete posts
- Block/unblock user

### Posts

- CRUD operations
- Comment on and like other users' posts

### Tags (Optional)

- CRUD operations

## Use Cases

1. **Scenario 1:**
    - A friend of Pavel’s told him about this amazing forum, where lots of people share their ideas and perspectives on
      the crypto/stock market. Pavel enters the website and sees a feed of posts.
      He can sort them by most liked or newest. He can also filter them by a certain word/s. He is an anonymous user so
      he cannot create a post yet. He registers and then logs in to the forum. He can now start sharing his ideas with
      his buddy crypto “hodlers.”

2. **Scenario 2:**
    - Your forum has accumulated thousands of new users. Most of them are proactively helpful and positive, but some of
      them started posting spam or/and irrelevant information to the forum. You hire a moderator. You instruct the
      moderator to enter the forum and create a first-time registration. You as an admin give admin rights to your
      moderator through the forum. They can now start deleting posts and ban users that do not follow the forum rules!

## Technical Requirements

- Follow OOP, KISS, SOLID, DRY principles
- Implement REST API design best practices
- Use tiered project structure with 80%+ unit test code coverage
- Proper exception handling and propagation
- Relational database with normalized structure
- Git commit history reflects project development and contributions

## Database Relations

[Insert images or diagrams of your database relations here]

## GitHub Repository

- [Link to GitHub Repository]
- [Link to Swagger Documentation]
- [Link to Hosted Project (if applicable)]
- Instructions on how to set up and run the project locally
