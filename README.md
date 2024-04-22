# qp-assessment

#Grocery Booking API

The Grocery Booking API is a RESTful web service for grocery item management and booking for both admin and users. It allows admin to manage inventory, add, update, view, and remove grocery items, while users can view available items and book them for purchase.

#Features
1. Admin Responsibilities:
   - Add new grocery items to the system
   - View existing grocery items
   - Remove grocery items from the system
   - Update details (e.g., name, price) of existing grocery items
   - Manage inventory levels of grocery items
2. User Responsibilities:
   - View the list of available grocery items
   - Ability to book multiple grocery items in a single order
   
Database: PostgreSQL 
Authentication: Role-based authentication using JWT tokens
Containerization: Docker for containerizing the application



#API Endpoints
Admin Endpoints:
	1.POST /api/admin/save/items - Add a new grocery item
	2.GET /api/admin/items - View existing grocery items
	3.DELETE /api/admin/delete/items/{itemId} - Remove a grocery item
	4.PUT /api/admin/update/items/{itemId} - Update details of a grocery item
	5.patch /api/admin/manage/items/{itemId} -Manage inventory levels of grocery items
User Endpoints:
	GET /api/user/items - View available grocery items
	POST /api/user/book/orders - Book multiple grocery items in a single order
	
#Database & Table

1.Table- users

	CREATE TABLE IF NOT EXISTS public.users
	(
	    id bigint NOT NULL,
	    password character varying(255) COLLATE pg_catalog."default",
	    username character varying(255) COLLATE pg_catalog."default",
	    CONSTRAINT users_pkey PRIMARY KEY (id));

	INSERT INTO public.users(id, password, username) VALUES (1, 'Admin123', 'Admin');
	
	INSERT INTO public.users(id, password, username) VALUES (2, 'User123', 'User');
	
2.Table- roles

	CREATE TABLE IF NOT EXISTS public.roles
	(
	    id bigint NOT NULL,
	    name character varying(255) COLLATE pg_catalog."default",
	    CONSTRAINT roles_pkey PRIMARY KEY (id));
				
	INSERT INTO public.roles(id, name) VALUES (1, ADMIN);
	INSERT INTO public.roles(id, name) VALUES (2, USER);
	
