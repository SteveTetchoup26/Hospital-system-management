-- Schéma de la base "hospital", reconstitué à partir des requêtes SQL de l'application.
-- Utilisation : mysql -usteve -p hospital < database/schema.sql

CREATE DATABASE IF NOT EXISTS hospital CHARACTER SET utf8mb4;
USE hospital;

CREATE TABLE IF NOT EXISTS admin (
    admin_id    INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(100) NOT NULL,
    username    VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    image       VARCHAR(500),
    gender      VARCHAR(20),
    date        DATE,
    date_modify DATE
);

CREATE TABLE IF NOT EXISTS doctor (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id     VARCHAR(100) NOT NULL UNIQUE,   -- ex. "DID-1"
    password      VARCHAR(100) NOT NULL,
    fullname      VARCHAR(100) NOT NULL,
    email         VARCHAR(100),
    gender        VARCHAR(20),
    mobile_number VARCHAR(30),
    specialized   VARCHAR(100),
    address       VARCHAR(500),
    image         VARCHAR(500),
    date          DATE,
    date_modify   DATE,
    date_delete   DATE,
    status        VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS patient (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    patient_id    INT NOT NULL UNIQUE,
    password      VARCHAR(100) NOT NULL,
    fullname      VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(30),
    gender        VARCHAR(20),
    address       VARCHAR(500),
    image         VARCHAR(500),
    description   VARCHAR(500),
    diagnosis     VARCHAR(500),
    treatment     VARCHAR(500),
    doctor        VARCHAR(100),                   -- doctor_id du médecin
    specialized   VARCHAR(100),
    date          DATE,
    date_modify   DATE,
    date_delete   DATE,
    status        VARCHAR(20),
    status_pay    VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS appointment (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT NOT NULL,
    patient_id     INT,
    name           VARCHAR(100),
    gender         VARCHAR(20),
    description    VARCHAR(500),
    diagnosis      VARCHAR(500),
    treatment      VARCHAR(500),
    mobile_number  VARCHAR(30),
    address        VARCHAR(500),
    date           DATE,
    date_modify    DATE,
    date_delete    DATE,
    status         VARCHAR(20),
    doctor         VARCHAR(100),                  -- doctor_id du médecin
    specialized    VARCHAR(100),
    schedule       DATE
);

CREATE TABLE IF NOT EXISTS payment (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    patient_id    INT,
    doctor        VARCHAR(100),
    total_days    INT,
    total_price   DOUBLE,
    date          DATE,
    date_checkout DATE,
    date_pay      DATE
);
