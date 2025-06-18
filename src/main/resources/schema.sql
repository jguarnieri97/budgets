CREATE SCHEMA IF NOT EXISTS USERS;
CREATE SCHEMA IF NOT EXISTS BUDGETS;

CREATE SEQUENCE budget_sequence
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;

-- Creación de Tablas --

CREATE TABLE USERS.SUPPLIER_COMPANY
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    email               VARCHAR(150),
    user_name           VARCHAR(50),
    phone               VARCHAR(15),
    user_address        VARCHAR(150),
    cuit                VARCHAR(15),
    is_active           BOOLEAN,
    lat                 FLOAT,
    ln                  FLOAT,
    is_verified         BOOLEAN,
    company_description VARCHAR(250),
    avg_score           FLOAT,
    avg_price           FLOAT,
    comments_count      INT,
    company_type        INT
);

CREATE TABLE USERS.WORKER
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    email        VARCHAR(150) NOT NULL,
    user_name    VARCHAR(50)  NOT NULL,
    phone        VARCHAR(15),
    user_address VARCHAR(150),
    cuit         VARCHAR(15),
    is_active    BOOLEAN      NOT NULL,
    company_id   BIGINT       NOT NULL,
    FOREIGN KEY (company_id) REFERENCES USERS.SUPPLIER_COMPANY (id)
);

CREATE TABLE USERS.APPLICANT_COMPANY
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    email               VARCHAR(150),
    user_name           VARCHAR(50),
    phone               VARCHAR(15),
    user_address        VARCHAR(150),
    cuit                VARCHAR(15),
    is_active           BOOLEAN,
    lat                 FLOAT,
    ln                  FLOAT,
    is_verified         BOOLEAN,
    company_description VARCHAR(250)
);

CREATE TABLE BUDGETS.BUDGET_REQUEST
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    budget_number VARCHAR(50) NOT NULL,
    is_read       BOOLEAN     NOT NULL,
    applicant_id  BIGINT      NOT NULL,
    created_at    DATE        NOT NULL,
    updated_at    DATE        NOT NULL,
    category      INT         NOT NULL,
    state         INT         NOT NULL,
    work_resume   VARCHAR(250),
    work_detail   VARCHAR(250),
    FOREIGN KEY (applicant_id) REFERENCES USERS.APPLICANT_COMPANY (id)
);

CREATE TABLE BUDGETS.BUDGET
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_id  BIGINT  NOT NULL,
    request_id   BIGINT  NOT NULL,
    price        FLOAT   NOT NULL,
    days_count   INT     NOT NULL,
    worker_count INT     NOT NULL,
    detail       VARCHAR(250),
    state        INT     NOT NULL,
    hired        BOOLEAN NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES USERS.SUPPLIER_COMPANY (id),
    FOREIGN KEY (request_id) REFERENCES BUDGETS.BUDGET_REQUEST (id)
);