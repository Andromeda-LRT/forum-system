-- Inserting 5 rows into the 'users' table
INSERT INTO users (username, password, first_name, last_name, email, is_blocked, is_archived)
VALUES ('john_doe', 'pass123', 'John', 'Doe', 'john.doe@example.com', false, false),
       ('jane_smith', 'pass456', 'Jane', 'Smith', 'jane.smith@example.com', false, false),
       ('admin_user', 'adminpass', 'Charlie', 'Sheen', 'admin@example.com', false, false),
       ('traveler_123', 'travel123', 'Oliver', 'Williams', 'oliverw@example.com', false, false),
       ('adventure_seeker', 'adventurepass', 'James', 'Wilson', 'james@example.com', false, false),
       ('julia_davis', 'pass123', 'Julia', 'Davis', 'julia.davis@example.com', false, false),
       ('robert_miller', 'pass890', 'Robert', 'Miller', 'robert.miller@example.com', false, false),
       ('laura_moore', 'pass567', 'Laura', 'Moore', 'laura.moore@example.com', false, false),
       ('james_thomas', 'pass234', 'James', 'Thomas', 'james.thomas@example.com', false, false),
       ('lisa_taylor', 'pass987', 'Lisa', 'Taylor', 'lisa.taylor@example.com', false, false),
       ('david_wilson', 'pass654', 'David', 'Wilson', 'david.wilson@example.com', false, false),
       ('sarah_white', 'pass321', 'Sarah', 'White', 'sarah.white@example.com', false, false),
       ('michael_brown', 'pass456', 'Michael', 'Brown', 'michael.brown@example.com', false, false),
       ('emily_jones', 'pass789', 'Emily', 'Jones', 'emily.jones@example.com', false, false),
       ('alejandro_gomez', 'passAlej456', 'Alejandro', 'Gomez', 'alejandro.gomez@example.com', false, false),
       ('chen_wang', 'passChen789', 'Chen', 'Wang', 'chen.wang@example.com', false, false),
       ('olivia_dubois', 'passOliv321', 'Olivia', 'Dubois', 'olivia.dubois@example.com', false, false),
       ('arjun_patel', 'passArju654', 'Arjun', 'Patel', 'arjun.patel@example.com', false, false),
       ('sofia_ivanova', 'passSofi987', 'Sofia', 'Ivanova', 'sofia.ivanova@example.com', false, false),
       ('yuki_takahashi', 'passYuki234', 'Yuki', 'Takahashi', 'yuki.takahashi@example.com', false, false),
       ('mohamed_hassan', 'passMoh567', 'Mohamed', 'Hassan', 'mohamed.hassan@example.com', false, false),
       ('clara_schmidt', 'passClar890', 'Clara', 'Schmidt', 'clara.schmidt@example.com', false, false),
       ('luca_rossi', 'passLuca123', 'Luca', 'Rossi', 'luca.rossi@example.com', false, false),
       ('amina_mohamed', 'passAmin456', 'Amina', 'Mohamed', 'amina.mohamed@example.com', false, false);

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
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (1, 'The Moon Men of Mexico’s Sonoran Desert',
        ('The El Pinacate and Gran Desierto de Altar Biosphere Reserve can seem otherworldly – but did the Apollo 11 astronauts actually train here? Covering 2,760mi2 (7,150km2) of Sonora, Mexico’s second largest state, the El Pinacate and Gran Desierto de Altar Biosphere Reserve is vast – “visible from space” vast. But its size is not the reason UNESCO made it a World Heritage Site. Its otherworldly landscapes – part dormant volcanic, part active sand dunes – provide habitat for a stupendous variety of fauna and flora, including the gila monster, bighorn sheep and a subspecies of pronghorn antelope; and it erupts into color, throwing up sprays of verbena and lily, as soon as it sniffs water. I had always wanted to go there, and when my duties as a drug-war correspondent took me to nearby Mexicali, my nerves, after a couple of close things with cartel heavies, begged for some remission from violence, horror and politics. I headed straight to Puerto Peñasco, on the north shore of the Sea of Cortez, a good base from which to explore the reserve.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (12, 'Desert, Dunes & an Epic Festival: 10 Days in Mongolia',
        ('The COVID pandemic delayed World Nomads’ Travel Photography Scholarship trip to Mongolia, which was planned for 2020. The winner was Jasmin Bauomy, a talented creative from Egypt but living in Germany with a background in journalism, podcasting, and blogging, and a passion for photography. We were finally able to deliver the trip in 2022, with me on board as mentor – but unfortunately, without Jasmin. She tested positive for COVID the day before she was supposed to fly.
Isaac (World Nomad’s Social & Content Marketing Manager and trip producer) and I were already in Ulaanbaatar in Mongolia when we got the news Jasmin couldn’t travel, so we went ahead with the trip as a photography assignment. Isaac acted as producer on the trip, handling a lot of the logistics. Our Discover Mongolia guide was Munkhbold. He was very enthusiastic, quite experienced, and he’s quite educated as well – he’s a teacher and very keen on archaeology and anthropology.
Our ten-day trip was divided into three parts, largely focused around the Naadam festival, which happens in July each year. From Ulaanbaatar, we headed south to the Gobi Desert, then north-central to a the regional town of Kharkhorin, and then back to Ulaanbaatar. So, one of the features of the trip was the long drives.
We went to three destinations in the Gobi Desert. The first was Tsagaan Suvarga, more commonly known as the White Stupa. These are white sand hills streaked with minerals, and white sand cliffs that run for about 400m (1,321ft).'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (10, 'Just Keep Running',
        ('It was a Sunday, and I was sore. This was not the burns-so-good kind of sore. Sunday was my final day in Lisbon – a city peppered with stairs and hills and all types of quad killers – and I was butt-burning, calf-stinging, omigod-call-me-a-taxi kind of sore. I’d also been training for a marathon – that would make any person sore. But this was different. My legs were supposed to be running 17 miles the next morning, and now, they could barely bend enough to let me sit down on the bank of the Tagus River.
That’s where I was, sitting alongside Portugal’s longest river and mapping out tomorrow’s run, when I spotted the statue – a massive wedge of stone, carved to resemble the bow and sails of a ship and depicting a queue of men looking ahead, toting maps and telescopes. Padrão dos Descobrimentos, I read. Monument of the Discoveries. People flocked around it, posing in front of Henry the Navigator and Vasco da Gama.
These age-old explorers still capture the imagination of their country today. I was surprised by how many Portuguese I spoke to mentioned them in casual conversation. Earlier, a bellhop told me that Vasco da Gama “wasn’t afraid of anything”, and suggested I channel him during my run.
I turned back to my map, sketching out my route. Start there, Cabo da Roca, I traced. Weave through that village there, Azóia, and into the Sintra forest. Then back towards Cabo da Roca again.

I folded my map, the one tool I shared in common with the great explorers. If they could do it, so could I.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (15, 'How to Plan a Safe Road Trip',
        ('Embarking on a road trip is no simple undertaking. It’s important to pack absolutely everything you need if you are driving to remote areas, just in case you don''t have access to the internet, groceries or clean drinking water.

From double-checking your vehicle is in working order to studying the route, before you hit the open road, here are 12 tips to stay safe and avoid any preventable road trip mishaps.

Know the route
Offline maps and paper maps
Take a break
Stock up on water
Carry sufficient fuel
Pack enough food for two extra days
Check the weather forecast
Check your vehicle before you go
Carry spare tools
Kit your car out for offroad travel
First aid kit
Other things to pack
Know the route'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (18, 'Essential Snow and Avalanche Safety Travel Tips',
        ('Learn how to avoid an avalanche and how to survive if you do get caught in one. Nomad Bill shares his experience taking an avalanche safety course.
There’s a certain majesty – and terror – in looking up at steep, snow-covered mountain peaks, even in the confines of a patrolled ski resort like Lake Tahoe’s Kirkwood. “You know, the thing about the ski runs here is that only 20% are man-made gaps from trees we cut down. The rest were created by avalanche paths through the forest.” says Andrew DeGuzman, backcountry guide and instructor at Expedition Kirkwood. It’s enough to make you want to enroll in one of their Avalanche Safety classes, as I did, even if not planning to head to the backcountry.

During the 2021 winter season, avalanches killed 37 people across the US – and not just backcountry skiers. The Colorado Avalanche Information Center reported snowmobilers, hikers, climbers, and snowshoers accounted for nearly half the total fatalities, the largest number in over a decade. In the words of the avalanche training manual, “Nature is notorious for keeping quiet most of the time and providing unforgiving feedback when you want it least.” So, no matter what your outdoor winter activity, it’s a good idea to at least brush up on some snow safety essentials in a class like Kirkwood’s AIARE Rec Level 1 course.

AIARE, the American Institute for Avalanche Research and Education, is an organization that develops and supplies training programs to more than 100 different course-providers across the U.S., for both recreational and professional customers. The REC Level 1 class about avalanche safety basics is tailored for skiers and snowboarders, but relevant for anyone who may explore in snowy, mountainous terrain. It can be followed with further AIARE courses on snow science, avalanche rescue, and backcountry guiding.
'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (6, 'How to Pick the Best Travel Shoes for Your Next Adventure',
        ('Choosing the right travel shoes depends a lot on what kind of activities you’ll be doing. Here’s what to look for in trail runners, water shoes, hiking boots, walking sandals and more.
Picking out your footwear requires just as much thought and consideration as choosing proper gear for your next camping trip.

Here’s what you should know.

Trail running shoes
Trail running shoes are the versatile all-stars of the footwear world. Whether you''re tackling rocky paths, muddy tracks, or forest floors, these travel shoes are designed to handle it all.

Hiking boots
When it comes to hiking, not all trails are created equal. Depending on where you’re going hiking, you may or may not need hiking boots.

Walking sandals
Walking sandals are your best friends for casual strolls, city tours, and light hikes. Based on my experience, they''re ideal for warm climates where breathability and comfort take precedence over heavy-duty support.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (3, 'Stargazing in the Atacama Desert',
        ('There''s something magical about the Atacama Desert. It''s not just the silence or the vast, open spaces; it''s the sky. At night, it comes alive with millions of stars, unlike anything I''ve ever seen. The Milky Way stretches across the sky so brightly, it''s breathtaking. I spent my nights there wrapped in a blanket, just staring upwards, feeling both insignificant and deeply connected to the universe. The Atacama isn''t just a place on the map; it''s a reminder of the endless beauty our world holds.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (16, 'Adventures in Cappadocia: Hot Air Balloons and Fairy Chimneys',
        ('Cappadocia is a dreamland. Imagine floating in a hot air balloon as the sun rises, casting a soft glow over a landscape of fairy chimneys and cave dwellings. That''s how I started my day, and it felt surreal. Walking through the valleys, like the Ihlara Valley, was like stepping into a storybook, with ancient churches carved into the rocks. The locals were incredibly welcoming, sharing stories and inviting me into their homes for a cup of tea. Cappadocia isn''t just a place; it''s an experience that touches your heart.'),
        0, 0, 0, 0, '2024-01-28 20:50:00'),
       (12, 'How to Be a Better Traveler',
        ('Discover how to be a more eco-friendly, ethical and responsible traveler.
The choices we make on the road can have a big impact on the people and places we encounter on our travels. With a little knowhow, we can all travel more responsibly.

This guide makes it easy to become a better traveler. Beginning with packing your bags and planning your trip, to getting there and around, we give you all the tips you need to travel with kindness and ensure the environments you visit stay pristine for the generations to come.
Plan your trip
How to find truly eco accommodation and choose an ethical tour operator.

Eco-friendly packing tips
How to lighten your load, travel mindfully and reduce waste.

Get there and around

Greener air travel and low-impact ideas to immerse yourself in a destination.

Eat and drink
How to eat like a local and ensure what you put on your plate does no harm. '),
        0, 0, 0, 0, '2024-01-28 20:50:00');

-- Inserting 5 rows into the 'comments' table
INSERT INTO comments (content, user_id, post_id, is_archived)
VALUES ('I would love to visit those places!', 1, 2, false),
    ('Great photography tips! Thanks for sharing.', 3, 3, false),
    ('Road trips are the best way to explore!', 4, 2, false),
    ('I''ve been there! Your photos bring back so many wonderful memories.', 2, 13, false),
    ('Wow, what an adventure! Can you share more about how you planned your trip?', 3, 13, false),
    ('Incredible! Which was your favorite spot? I''m planning my visit.', 4, 13, false),
    ('This is so inspiring! I''ve always wanted to travel there. Any tips for a first-timer?', 5, 5, false),
    ('"I can almost feel the peace and tranquility through your words. Beautifully written.', 6, 5, false),
    ('Been dreaming of going there! Did you find it to be touristy, or is it still a hidden gem?', 7, 4, false),
    ('Your experiences sound so rich and authentic. Makes me want to pack my bags right now!', 8, 4, false),
    ('That sunset shot is epic! What time of year would you recommend visiting?', 9, 11, false),
    ('This post is giving me major wanderlust vibes. Saving this for later!', 12, 12, false),
    ('How did you manage the language barrier? Any funny stories to share?', 11, 1, false),
    ('Your travel stories are the best! Waiting eagerly for your next post.', 4, 2, false),
    ('I love how you capture the essence of the places you visit. It''s not just about the sights, but the emotions and experiences.', 20, 3, false),
    ('Can you recommend any local dishes to try? I love exploring food cultures!', 20, 6, false),
    ('This makes me miss traveling so much. Can''t wait for my next adventure!', 2, 6, false),
    ('Seeing your journey makes me so excited for my upcoming trip. Thanks for the inspiration!', 15, 7, false),
    ('I''ve always been hesitant about solo travel. Your posts make it seem so rewarding, though. Might just have to give it a try!', 16, 8, false),
    ('Reading about your travels is like a breath of fresh air. Looking forward to seeing where you go next!', 17, 9, false),
    ('This blog post about your culinary journey through Tokyo has me drooling! The detail you put into describing the flavors and your experience at the Tsukiji Fish Market was so vivid, I felt like I was there with you. I''m curious, were there any foods that you were hesitant to try at first? And how did you find the best spots – was it research, recommendations, or just stumbling upon them?', 11, 9, false),
    ('Your safari experience in the Serengeti sounds like a once-in-a-lifetime adventure. Reading about the Great Migration and how you managed to see the Big Five up close was absolutely thrilling. I''m planning a similar trip and would love to know more about the eco-friendly accommodations you mentioned. Also, how did you ensure that your visit was respectful and supportive of local conservation efforts?', 19, 11, false),
    ('I''ve always dreamt of seeing the Northern Lights, and your post from Iceland just reignited that dream. The photographs are surreal! I imagine it must take a lot of patience (and luck) to catch the Aurora Borealis at its peak. Do you have any tips for photographers aiming to capture this natural wonder? And what''s the best way to stay warm while you wait for the lights to appear?', 21, 12, false),
    ('Reading about your road trip along the Amalfi Coast has me longing for the sea and sun. The way you described the quaint villages, the breathtaking views, and, of course, the delicious Italian cuisine was so engaging. I''d be interested to know how you planned your route and if there were any hidden gems along the way that weren''t originally on your itinerary.', 8, 5, false),
    ('Your exploration of Rajasthan''s vibrant culture and history is fascinating. The detail you provided on the architectural marvels and the local customs was so enriching. I''m particularly interested in the traditional music and dance you mentioned. Could you share more about where one might experience these performances firsthand? Also, any advice on respectful cultural engagement while traveling?', 9, 6, false),
    ('The serenity and natural beauty you''ve captured in Kyoto''s temples are mesmerizing. It''s interesting to see how each temple offers a different experience and atmosphere. I''m planning a trip there and would love to know how you chose which temples to visit. Also, any tips on finding those quieter, less touristy spots would be greatly appreciated!', 9, 7, false),
    ('Your adventure through Cappadocia looks like something out of a fairy tale. Floating over the landscape in a hot air balloon must have been an unforgettable experience. I''m curious about the logistics of booking such a flight – any recommendations or things to look out for? Additionally, the cave dwellings you mentioned sound intriguing. Did you get a chance to stay in one, and if so, how was that experience?', 4, 8, false),
    ('The vibrant streets of Havana, as you''ve described them, seem full of life and color. It''s fascinating how music and art play such a central role in the city''s atmosphere. I''d love to hear more about your interactions with the locals and any cultural insights you gained. Also, were there any challenges you faced while traveling there, and how did you overcome them?', 11, 11, false),
    ('Your journey to the fjords of Norway sounds like it was an incredible blend of adventure and tranquility. The photos of the towering cliffs and the emerald waters are stunning. It must have been amazing to hike up to those viewpoints. Can you share more about how you prepared for the hikes and what gear you found essential? Also, any recommendations for those looking to explore the fjords beyond the typical tourist spots would be invaluable.', 11, 11, false),
    ('Adrenaline junkie here! Where should I go first?', 5, 5, false);

insert into user_likes (user_id, post_id, is_liked, is_disliked)
values (1, 1, true, false),
       (2, 2, false, true),
       (3, 11, true, false),
       (4, 14, true, false),
       (4, 3, true, false),
       (4, 5, true, false),
       (4, 6, true, false),
       (4, 11, true, false),
       (4, 8, false, true),
       (4, 9, true, false),
       (4, 1, true, false),
       (5, 1, true, false),
       (5, 11, true, false),
       (5, 12, true, false),
       (5, 14, true, false),
       (5, 6, true, false),
       (5, 7, true, false),
       (6, 4, true, false),
       (6, 5, true, false),
       (6, 6, true, false),
       (6, 7, false, true),
       (7, 11, true, false),
       (7, 12, true, false),
       (7, 9, true, false),
       (7, 8, true, false),
       (8, 7, true, false),
       (8, 6, true, false),
       (8, 5, true, false),
       (16, 4, true, false),
       (17, 5, false, true);

INSERT INTO admins (user_id, phone_number)
VALUES (1, 0886644258);

INSERT INTO tags(tag_id, name, is_archived)
VALUES (1, '#photo', false),
       (2, '#road', false),
       (3, '#trip', false),
       (4, '#first', false),
       (5, '#adventure', false),
       (6, '#travel', false),
       (7, '#explore', false),
       (8, '#culture', false),
       (9, '#nature', false),
       (10, '#wanderlust', false),
       (11,  '#foodie', false),
       (12,  '#history', false),
       (13, '#landscape', false),
       (14, '#trees', false),
       (15, '#bucketlist', false);

INSERT INTO posts_tags(post_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (3, 5),
       (4, 2),
       (6, 4),
       (6, 3),
       (7, 4),
       (8, 4),
       (9, 4),
       (10, 4),
       (10, 3),
       (11, 4),
       (12, 4),
       (12, 6),
       (13, 4),
       (14, 4),
       (15, 4),
       (15, 8),
       (16, 4),
       (16, 4);

