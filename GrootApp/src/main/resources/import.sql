INSERT INTO `course` (`id`, `name`) VALUES (1,'MU');
INSERT INTO `course` (`id`, `name`) VALUES (2,'BSEP');
INSERT INTO `course` (`id`, `name`) VALUES (3,'NTP');
INSERT INTO `course` (`id`, `name`) VALUES (4, 'SBNZ');
INSERT INTO `course` (`id`, `name`) VALUES (5, 'USI');

INSERT INTO `user` (`id`, `name`, `password`, `role`, `surname`, `token`, `username`) VALUES (1,'Chandler','password','STUDENT','Bing','754c9604-d4ad-4fa6-b869-59778d4553d1','student');
INSERT INTO `user` (`id`, `name`, `password`, `role`, `surname`, `token`, `username`) VALUES (2,'Ross','password','TEACHER','Geller','754c9604-d4ad-4fa6-b869-59778d4553d2','teacher');
INSERT INTO `user` (`id`, `name`, `password`, `role`, `surname`, `token`, `username`) VALUES (3,'Joey','password','ADMIN','Tribbiani','754c9604-d4ad-4fa6-b869-59778d4553d3','admin');
INSERT INTO `user` (`id`, `name`, `password`, `role`, `surname`, `token`, `username`) VALUES (4,'Phoebe','password','STUDENT','Buffay','754c9604-d4ad-4fa6-b869-59778d4553d4','student1');

INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (1,'2017-06-16 23:30:15','Joey doesnt share food','Announcement 1',2,1);
INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (2,'2017-06-15 23:16:47','Oh...my...God','Announcement 2',2,1);
INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (3,'2017-06-10 20:15:43','How you doing','Announcement 3',2,1);
INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (4,'2017-06-10 20:15:43','Smelly cat..','Announcement 4',2,1);
INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (4,'2017-06-10 20:15:43','Smelly cat..','Announcement 4',2,1);
INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (5,'2017-06-10 20:15:43','We were on a break!','Announcement 5',2,1);
INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (6,'2017-06-15 23:16:47','Unagi','Announcement 6',2,1);
INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (7,'2017-06-15 23:16:47','Custard? Good. Jam? Good. Meat? Good','Announcement 7',2,1);
INSERT INTO `announcement` (`id`, `announced_on`, `content`, `title`, `author_id`, `course_id`) VALUES (8,'2017-06-15 23:16:47','Pivot! Pivot! Pivot! Pivot. Pivot. Pivot','Announcement 8',2,1);

INSERT INTO `course_users` (`courses_id`, `users_id`) VALUES (2,1);
INSERT INTO `course_users` (`courses_id`, `users_id`) VALUES (2,2);
INSERT INTO `course_users` (`courses_id`, `users_id`) VALUES (1,1);
INSERT INTO `course_users` (`courses_id`, `users_id`) VALUES (1,2);

INSERT INTO `user_permission` (`user`, `permission`) VALUES (1,'EDIT_PROFILE');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (2,'ADD_ANNOUNCEMENT');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (2,'EDIT_ANNOUNCEMENT');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (2,'DELETE_ANNOUNCEMENT');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (2,'EDIT_PROFILE');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (3,'ADD_USER');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (3,'DELETE_USER');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (3,'ADD_COURSE');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (3,'DELETE_COURSE');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (3,'ADD_USER_TO_COURSE');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (4,'EDIT_PROFILE');