-- Inserting 5 rows into the 'users' table
INSERT INTO users (username, password, first_name, last_name, email, is_blocked, is_archived)
VALUES ('john_doe', 'pass123', 'John', 'Doe', 'john.doe@example.com', false, false),
       ('jane_smith', 'pass456', 'Jane', 'Smith', 'jane.smith@example.com', false, false),
       ('admin_user', 'adminpass', 'Charlie', 'Sheen', 'admin@example.com', false, false),
       ('traveler_123', 'travel123', 'Oliver', 'Williams', 'oliverw@example.com', false, false),
       ('adventure_seeker', 'adventurepass', 'James', 'Wilson', 'james@example.com', false, false);

INSERT INTO posts (created_by, title, content, total_comments, total_likes, total_dislikes, is_archived, date_time)
VALUES (1, 'Favorite Destinations',
        ('Share your top travel spots! Whether it''s the serene beaches of Bali, the majestic mountains of the Swiss Alps, or the bustling streets of Tokyo, we all have our favorite destinations that hold a special place in our hearts. Take a moment to share your most cherished travel spots and the unforgettable experiences you''ve had there. Inspire fellow travelers with your stories and recommendations.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (3, 'Travel Photography Tips',
        ('Let''s dive into the world of travel photography! Share your favorite tips, tricks, and techniques for capturing breathtaking moments during your adventures. Whether you''re a seasoned pro with a DSLR or a smartphone enthusiast, everyone has something valuable to contribute. Discuss the best camera settings, composition ideas, and post-processing secrets that make your travel photos stand out.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (4, 'Road Trip Stories',
        ('Embark on a journey down memory lane and share your most memorable road trip experiences! From spontaneous detours to unexpected encounters, road trips often lead to some of the most unforgettable moments. Describe the scenic routes, quirky roadside attractions, and the camaraderie built along the way. Whether it''s a cross-country adventure or a weekend getaway, let your road trip stories come to life.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (2, 'Budget Travel Hacks',
        ('Traveling on a budget doesn''t mean compromising on experiences! Share your best budget travel hacks that allow you to explore the world without breaking the bank. From finding affordable accommodation and scoring cheap flights to budget-friendly local eats, let''s compile a treasure trove of money-saving tips for fellow travelers. Your insider knowledge could make a significant impact on someone''s next adventure.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (5, 'Extreme Sports Abroad',
        ('Calling all adrenaline junkies! If you''re passionate about extreme sports and seeking an adrenaline rush, this is the place to be. Share your experiences of conquering the world''s best spots for skydiving, bungee jumping, rock climbing, and other heart-pounding activities. Discuss safety tips, gear recommendations, and the unparalleled thrill of pushing your limits in exotic locations around the globe.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (3, 'Hidden Gems of Lisbon',
        ('Discover Lisbon''s Untouched Corners: Venture beyond the tourist spots to explore Lisbon''s hidden gems. From the quaint alleys of Alfama to the secret viewpoints that offer stunning city vistas, experience the authentic charm of Portugal''s capital.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (2, 'A Foodie''s Guide to Tokyo',
        ('Savor Tokyo''s Best Bites: Tokyo, a paradise for food lovers, offers an array of culinary delights. Dive into the heart of Japanese cuisine by exploring local izakayas, sushi bars, and ramen shops, and discover why Tokyo is a UNESCO-listed city of gastronomy.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (1, 'Adventure Awaits in Patagonia',
        ('Embrace the Wild of Patagonia: Patagonia is a dream for adventurers. Hike through its breathtaking landscapes, from the rugged mountains of Torres del Paine to the icy expanses of the Perito Moreno Glacier, and witness nature''s wonders at their finest.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (1, 'Cultural Immersion in Marrakech',
        ('Experience Marrakech''s Rich Heritage: Marrakech invites travelers to immerse themselves in a vibrant cultural tapestry. Explore the bustling souks, majestic palaces, and serene gardens, and get a taste of the city''s history and traditions.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (4, 'Island Hopping in the Philippines',
        ('Explore the Pristine Beaches of the Philippines: The Philippines, with its 7,000+ islands, offers endless adventures for beach lovers. Discover the crystal-clear waters of Palawan, the white sands of Boracay, and the hidden lagoons of Cebu. Each island promises its own slice of paradise.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (4, 'A Journey Through the Scottish Highlands',
        ('Unveil the Mysteries of the Scottish Highlands: Embark on a journey through the Scottish Highlands, a land of breathtaking landscapes, rich history, and enduring legends. This rugged region offers more than just scenic beauty; it''s a haven for outdoor enthusiasts and history buffs alike. Hike the windswept valleys of Glencoe, sail the mysterious waters of Loch Ness in search of its legendary monster, and explore ancient castles that whisper tales of Scotland''s turbulent past. The Highlands are also a cultural tapestry, with vibrant Gaelic traditions, haunting bagpipe music, and the warm hospitality of its people. Whether you''re climbing Ben Nevis, Britain''s highest peak, or sampling whisky at a traditional distillery, the Highlands promise an unforgettable adventure steeped in mystery and majesty.'),
        0, 0, 0, 0, '2024-01-28 20:50:00');

-- Inserting 5 rows into the 'comments' table
INSERT INTO comments (content, user_id, post_id, is_archived)
VALUES ('I would love to visit those places!', 1, 2, false),
       ('Great photography tips! Thanks for sharing.', 3, 3, false),
       ('Road trips are the best way to explore!', 4, 2, false),
       ('Budget travel is always a challenge, but worth it!', 2, 4, false),
       ('Adrenaline junkie here! Where should I go first?', 5, 5, false);

insert into user_likes (user_id, post_id, is_liked, is_disliked)
values (1, 1, true, false),
       (2, 2, false, true),
       (3, 3, true, false),
       (4, 4, true, false),
       (5, 5, false, true);

INSERT INTO admins (user_id, phone_number)
VALUES (1, 0886644258);

INSERT INTO tags(tag_id, name, is_archived)
VALUES (1, '#photo', false),
       (2, '#road', false),
       (3, '#trip', false),
       (4, '#first', false),
       (5, '#worth', false);

INSERT INTO posts_tags(post_id, tag_id)
VALUES (1, 1),
       (2, 3),
       (3, 5),
       (4, 2),
       (5, 4);

INSERT INTO posts_tags(post_id, tag_id)
VALUES (1, 2),
       (1, 3);