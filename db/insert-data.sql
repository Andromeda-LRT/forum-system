-- Inserting 5 rows into the 'users' table
INSERT INTO users (username, password, first_name, last_name, email, is_admin, phone_number)
VALUES
    ('john_doe', 'pass123', 'John', 'Doe', 'john.doe@example.com', false, null),
    ('jane_smith', 'pass456', 'Jane', 'Smith', 'jane.smith@example.com', false, null),
    ('admin_user', 'adminpass', 'Charlie', 'Sheen', 'admin@example.com', true, 0897633489),
    ('traveler_123', 'travel123', 'Oliver', 'Williams', 'oliverw@example.com', false, null),
    ('adventure_seeker', 'adventurepass', 'James', 'Wilson', 'james@example.com', false, null);

-- Inserting 5 rows into the 'posts' table
-- Inserting 5 rows into the 'posts' table
INSERT INTO posts (created_by, title, content, likes, dislikes)
VALUES
    (1, 'Favorite Destinations', ('Share your top travel spots! Whether it''s the serene beaches of Bali, the majestic mountains of the Swiss Alps, or the bustling streets of Tokyo, we all have our favorite destinations that hold a special place in our hearts. Take a moment to share your most cherished travel spots and the unforgettable experiences you''ve had there. Inspire fellow travelers with your stories and recommendations.'), 15, 3),
    (3, 'Travel Photography Tips', ('Let''s dive into the world of travel photography! Share your favorite tips, tricks, and techniques for capturing breathtaking moments during your adventures. Whether you''re a seasoned pro with a DSLR or a smartphone enthusiast, everyone has something valuable to contribute. Discuss the best camera settings, composition ideas, and post-processing secrets that make your travel photos stand out.'), 25, 5),
    (4, 'Road Trip Stories', 'Embark on a journey down memory lane and share your most memorable road trip experiences! From spontaneous detours to unexpected encounters, road trips often lead to some of the most unforgettable moments. Describe the scenic routes, quirky roadside attractions, and the camaraderie built along the way. Whether it''s a cross-country adventure or a weekend getaway, let your road trip stories come to life.'), 12, 6),
    (2, 'Budget Travel Hacks', ('Traveling on a budget doesn''t mean compromising on experiences! Share your best budget travel hacks that allow you to explore the world without breaking the bank. From finding affordable accommodation and scoring cheap flights to budget-friendly local eats, let''s compile a treasure trove of money-saving tips for fellow travelers. Your insider knowledge could make a significant impact on someone''s next adventure.'), 30, 2),
    (5, 'Extreme Sports Abroad', ('Calling all adrenaline junkies! If you''re passionate about extreme sports and seeking an adrenaline rush, this is the place to be. Share your experiences of conquering the world''s best spots for skydiving, bungee jumping, rock climbing, and other heart-pounding activities. Discuss safety tips, gear recommendations, and the unparalleled thrill of pushing your limits in exotic locations around the globe.'), 20, 8);


-- Inserting 5 rows into the 'comments' table
INSERT INTO comments (content, user_id)
VALUES
    ('I would love to visit those places!', 1),
    ('Great photography tips! Thanks for sharing.', 3),
    ('Road trips are the best way to explore!', 4),
    ('Budget travel is always a challenge, but worth it!', 2),
    ('Adrenaline junkie here! Where should I go first?', 5);

-- Inserting 5 rows into the 'post_comments' table
INSERT INTO post_comments (post_id, comment_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);