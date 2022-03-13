-- noinspection SqlNoDataSourceInspectionForFile

-- ********************************************************************************
-- CREATE DATABASE
-- ********************************************************************************

BEGIN TRANSACTION;

DROP TABLE IF EXISTS transfer, account, tenmo_user, transfer_type, transfer_status;
DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;


CREATE TABLE transfer_type (
	transfer_type_id serial NOT NULL,
	transfer_type_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_type PRIMARY KEY (transfer_type_id)
);

CREATE TABLE transfer_status (
	transfer_status_id serial NOT NULL,
	transfer_status_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_status PRIMARY KEY (transfer_status_id)
);

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transfer (
	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
	transfer_type_id int NOT NULL,
	transfer_status_id int NOT NULL,
	account_from int NOT NULL,
	account_to int NOT NULL,
	amount decimal(13, 2) NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT FK_transfer_account_from FOREIGN KEY (account_from) REFERENCES account (account_id),
	CONSTRAINT FK_transfer_account_to FOREIGN KEY (account_to) REFERENCES account (account_id),
	CONSTRAINT FK_transfer_transfer_status FOREIGN KEY (transfer_status_id) REFERENCES transfer_status (transfer_status_id),
	CONSTRAINT FK_transfer_transfer_type FOREIGN KEY (transfer_type_id) REFERENCES transfer_type (transfer_type_id),
	CONSTRAINT CK_transfer_not_same_account CHECK (account_from <> account_to),
	CONSTRAINT CK_transfer_amount_gt_0 CHECK (amount > 0)
);


INSERT INTO transfer_status (transfer_status_desc) VALUES ('Pending');
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Approved');
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Rejected');

INSERT INTO transfer_type (transfer_type_desc) VALUES ('Request');
INSERT INTO transfer_type (transfer_type_desc) VALUES ('Send');

-- ********************************************************************************
-- FILL DATABASE WITH TEST DATA
-- ********************************************************************************


INSERT INTO tenmo_user (user_id, username, password_hash) VALUES(10001, 'cody', '$2a$10$x5q4wXLSz67dsH408YtHeOvWCyZtpNCshVs0be6jsjDxyUWznsG/O');
INSERT INTO tenmo_user (user_id, username, password_hash) VALUES(10002, 'user', '$2a$10$4anLf1ROQqVMkponfYxPN.1lVS1lLAWAvpGFizQcgR4j6T//AcGRm');
INSERT INTO tenmo_user (user_id, username, password_hash) VALUES(10003, 'test', '$2a$10$9S4paYUlI1rZoFCtD6GdR.DeCkg82vqWV52EvPbOGFS9rGozDKeee');
INSERT INTO tenmo_user (user_id, username, password_hash) VALUES(10004, 'testing', '$2a$10$BLFWU8JJ5BaZy1PBD1tSnu6VQ7fr1cg3JIblFvyhhX/pc4ERCRtk2');

INSERT INTO account (account_id, user_id, balance) VALUES (20001, 10001, '790.75');
INSERT INTO account (account_id, user_id, balance) VALUES (20002, 10002, '1109.25');
INSERT INTO account (account_id, user_id, balance) VALUES (20003, 10003, '1100.00');
INSERT INTO account (account_id, user_id, balance) VALUES (20004, 10004, '1000.00');

INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
  VALUES (30001, 2, 2, 20001, 20002, '10.00');
INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
  VALUES (30002, 2, 2, 20001, 20002, '0.25');
INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
  VALUES (30003, 1, 1, 20003, 20001, '10.00');
INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
  VALUES (30004, 1, 2, 20002, 20001, '1.00');
INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
  VALUES (30005, 1, 1, 20002, 20001, '10000.00');
INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
  VALUES (30007, 1, 1, 20003, 20001, '10.00');
INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
  VALUES (30008, 2, 2, 20001, 20003, '100.00');
INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
  VALUES (30006, 1, 2, 20001, 20002, '100.00');

COMMIT;
