insert into shop_user (name_of_user, password_of_user, email_of_user, balance_of_user,selling_right, is_banned,  system_role)
VALUES ('admin','admin','admin@admin.com','1000',true,false,'ROLE_ADMIN'),
       ('user','user','user@user.com','1000',false,false,'ROLE_USER')