-- Created by Vertabelo (http://vertabelo.com)
-- Script type: create
-- Scope: [tables, references, sequences, views, procedures]
-- Generated at Wed Oct 15 17:54:18 UTC 2014




-- tables
-- Table: Comment
CREATE TABLE Comment (
    id  SERIAL NOT NULL,
    content text  NOT NULL,
    post_date date NOT NULL,
    likes_number int  NULL,
    text_id int  NOT NULL,
    author_id int  NULL,
    reply_comment_id int  NULL,
    CONSTRAINT Comment_pk PRIMARY KEY (id)
);



-- Table: SocialMediaType
CREATE TABLE SocialMediaType (
    id SERIAL NOT NULL,
    name varchar(200)  NOT NULL UNIQUE,
    CONSTRAINT SocialMediaType_pk PRIMARY KEY (id)
);



-- Table: SocialMediaUser
CREATE TABLE SocialMediaUser (
    id SERIAL NOT NULL,
    name varchar(200)  NOT NULL UNIQUE,
    socialMediaType_id int  NOT NULL,
    CONSTRAINT SocialMediaUser_pk PRIMARY KEY (id)
);



-- Table: Tag
CREATE TABLE Tag (
    id SERIAL NOT NULL,
    name varchar(200)  NOT NULL UNIQUE,
    CONSTRAINT Tag_pk PRIMARY KEY (id)
);



-- Table: Text
CREATE TABLE Text (
    id SERIAL NOT NULL,
    title varchar(500)  NOT NULL,
    content text  NOT NULL,
    post_date varchar(500)  NOT NULL,
    listening_number int  NULL,
    author_id int  NOT NULL,
    CONSTRAINT Text_pk PRIMARY KEY (id)
);

-- Table: Tag_Text
CREATE TABLE Tag_Text (
    id SERIAL NOT NULL,
    text_id int  NOT NULL,
    tag_id int  NOT NULL,
    CONSTRAINT Tag_Text_pk PRIMARY KEY (id)
);



-- Table: User
CREATE TABLE BlogUser (
    id SERIAL NOT NULL,
    name varchar(200)  NOT NULL UNIQUE,
    author boolean  NOT NULL,
    socialMediaUser_id int  NULL,
    CONSTRAINT BlogUser_pk PRIMARY KEY (id)
);



-- Table: record
CREATE TABLE record (
    id SERIAL NOT NULL,
    url varchar(400)  NOT NULL,
    CONSTRAINT record_pk PRIMARY KEY (id)
);



-- foreign keys
-- Reference:  Author_SocialMediaUser (table: User)


ALTER TABLE BlogUser ADD CONSTRAINT Author_SocialMediaUser 
    FOREIGN KEY (socialMediaUser_id)
    REFERENCES SocialMediaUser (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Comment_Author (table: Comment)


ALTER TABLE Comment ADD CONSTRAINT Comment_Author 
    FOREIGN KEY (author_id)
    REFERENCES BlogUser (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Comment_Text (table: Comment)


ALTER TABLE Comment ADD CONSTRAINT Comment_Text 
    FOREIGN KEY (text_id)
    REFERENCES Text (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Comment_Comment (table: Comment)


ALTER TABLE Comment ADD CONSTRAINT Comment_Comment 
    FOREIGN KEY (reply_comment_id)
    REFERENCES Comment (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  SocialMediaUser_SocialMediaType (table: SocialMediaUser)


ALTER TABLE SocialMediaUser ADD CONSTRAINT SocialMediaUser_SocialMediaType 
    FOREIGN KEY (socialMediaType_id)
    REFERENCES SocialMediaType (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Text_Author (table: Text)


ALTER TABLE Text ADD CONSTRAINT Text_Author 
    FOREIGN KEY (author_id)
    REFERENCES BlogUser (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;


-- Reference:  Tag_Text_Tag (table: Tag_Text)


ALTER TABLE Tag_Text ADD CONSTRAINT Tag_Text_Tag 
    FOREIGN KEY (tag_id)
    REFERENCES Tag (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Tag_Text_Text (table: Tag_Text)


ALTER TABLE Tag_Text ADD CONSTRAINT Tag_Text_Text 
    FOREIGN KEY (text_id)
    REFERENCES Text (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;



-- End of file.

