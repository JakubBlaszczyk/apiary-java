
INSERT INTO ACCOUNT (LOGIN, PASSWORD, EMAIL, PRIVILEGE) VALUES ('admin', 'admin', 'admin@admin.com', 'admin');
INSERT INTO ACCOUNT (LOGIN, PASSWORD, EMAIL, PRIVILEGE) VALUES ('user1', 'user1', 'user1@user1.com', 'worker');
INSERT INTO ACCOUNT (LOGIN, PASSWORD, EMAIL, PRIVILEGE) VALUES ('user2', 'user2', 'user2@user2.com', 'beekeeper');
INSERT INTO ACCOUNT (LOGIN, PASSWORD, EMAIL, PRIVILEGE) VALUES ('user3', 'user3', 'user3@user3.com', 'worker');
INSERT INTO APIARY (LOCALIZATION, INFORMATION) VALUES ('Krakow', 'Some interesting apiary');
INSERT INTO APIARY (INFORMATION) VALUES ('This one is only for information');
INSERT INTO APIARY (LOCALIZATION) VALUES ('Lubzina');
INSERT INTO EVENT (ID_apiary, Time_start, note) VALUES (1, CURRENT_TIMESTAMP, 'There was event');
INSERT INTO EVENT (ID_apiary, Time_start, note) VALUES (3, CURRENT_TIMESTAMP, 'Bear attacked!');
INSERT INTO ACCOUNT_APIARY (ID_account, ID_apiary) SELECT ID, 1 FROM ACCOUNT WHERE LOGIN = 'admin';
INSERT INTO ACCOUNT_APIARY (ID_account, ID_apiary) SELECT ID, 2 FROM ACCOUNT WHERE LOGIN = 'admin';
INSERT INTO ACCOUNT_APIARY (ID_account, ID_apiary) SELECT ID, 3 FROM ACCOUNT WHERE LOGIN = 'user1';