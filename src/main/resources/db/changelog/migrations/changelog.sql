-- liquibase formatted sql

CREATE TABLE categories (
                                     "id" serial NOT NULL,
                                     "name" varchar(255) NOT NULL,
                                     CONSTRAINT "categories_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE products (
                                   "id" serial NOT NULL,
                                   "quantity" bigint NOT NULL,
                                   "attributes" hstore NOT NULL,
                                   "name" varchar(255) NOT NULL UNIQUE,
                                   "price" bigint NOT NULL,
                                   CONSTRAINT "products_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE users (
                                "id" serial NOT NULL,
                                "username" varchar(255) NOT NULL UNIQUE,
                                "password" varchar(255) NOT NULL,
                                "roles" varchar(255) NOT NULL,
                                "status" varchar(255) NOT NULL,
                                "balance" bigint NOT NULL,
                                CONSTRAINT "users_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE transactions (
                                       "id" serial NOT NULL,
                                       "product_name" varchar(255) NOT NULL,
                                       "quantity" int NOT NULL,
                                       "total_price" bigint NOT NULL,
                                       "time" varchar(255) DEFAULT CURRENT_TIMESTAMP,
                                       "customer_id" int NOT NULL,
                                       CONSTRAINT "transactions_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );








CREATE TABLE categories_products (
                                              "category_id" bigint NOT NULL,
                                              "product_id" bigint NOT NULL
) WITH (
      OIDS=FALSE
    );

ALTER TABLE transactions ADD CONSTRAINT transactions_fk1 FOREIGN KEY ("customer_id") REFERENCES "users"("id");
ALTER TABLE transactions ADD CONSTRAINT transactions_fk0 FOREIGN KEY ("product_name") REFERENCES "products"("name");
ALTER TABLE categories_products ADD CONSTRAINT products_categories_fk0 FOREIGN KEY ("category_id") REFERENCES "categories"("id");
ALTER TABLE categories_products ADD CONSTRAINT products_categories_fk1 FOREIGN KEY ("product_id") REFERENCES "products"("id");


