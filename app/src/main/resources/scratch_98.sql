-- Створення бази даних
CREATE DATABASE IF NOT EXISTS `form_builder`;

-- Вибір бази даних
USE `form_builder`;

-- Створення таблиці USERS
CREATE TABLE IF NOT EXISTS `USERS` (
  `ID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NOT NULL,
  `SURNAME` VARCHAR(50) NOT NULL,
  `EMAIL` VARCHAR(100) NOT NULL,
  `PASSWORD` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`)
);

-- Додавання даних в таблицю USERS
INSERT INTO `USERS` (`NAME`, `SURNAME`, `EMAIL`, `PASSWORD`)
VALUES ('John', 'Doe', 'johndoe@example.com', 'password123'),
       ('Jane', 'Doe', 'janedoe@example.com', 'password456'),
       ('Bob', 'Smith', 'bobsmith@example.com', 'password789');

-- Створення таблиці FORMS
CREATE TABLE IF NOT EXISTS `FORMS` (
  `ID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(100) NOT NULL,
  `DESCRIPTION` TEXT,
  `USER_ID` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`USER_ID`) REFERENCES `USERS`(`ID`) ON DELETE CASCADE
);

-- Додавання даних в таблицю FORMS
INSERT INTO `FORMS` (`NAME`, `DESCRIPTION`, `USER_ID`)
VALUES ('Contact Form', 'A simple form for contacting us.', 1),
       ('Registration Form', 'A form for registering on our website.', 2),
       ('Feedback Form', 'A form for providing feedback on our services.', 3);

-- Створення таблиці FORM_PARAMETERS
CREATE TABLE IF NOT EXISTS `FORM_PARAMETERS` (
  `ID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FORM_ID` INT(11) UNSIGNED NOT NULL,
  `NAME` VARCHAR(50) NOT NULL,
  `VALUE` TEXT NOT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`FORM_ID`) REFERENCES `FORMS`(`ID`) ON DELETE CASCADE
);

-- Додавання даних в таблицю FORM_PARAMETERS
INSERT INTO `FORM_PARAMETERS` (`FORM_ID`, `NAME`, `VALUE`)
VALUES (1, 'Recipient Email', 'contact@example.com'),
       (1, 'Subject', 'Contact Form Submission'),
       (2, 'Email Verification', 'true'),
       (2, 'Password Length', '8'),
       (3, 'Feedback Type', 'General'),
       (3, 'Rating Scale', '5 Stars');

-- Створення таблиці FIELD_TYPES
CREATE TABLE IF NOT EXISTS `FIELD_TYPES` (
  `ID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NOT NULL,
  `DESCRIPTION` TEXT,
  `EXAMPLE_TEMPLATE` TEXT,
  PRIMARY KEY (`ID`)
);

-- Додавання даних в таблицю FIELD_TYPES
INSERT INTO `FIELD_TYPES` (`NAME`, `DESCRIPTION`, `EXAMPLE_TEMPLATE`)
VALUES ('Text', 'A single line of text input.', '<input type="text">'),
    ('Textarea', 'A multi-line text input.', '<textarea></textarea>'),
    ('Checkbox', 'A checkbox for selecting one or more options.', '<input type="checkbox">'),
    ('Radio', 'A set of radio buttons for selecting one option.', '<input type="radio">'),
    ('Email', 'A single line email input with email validation.', '<input type="email">'),
    ('Password', 'A single line password input with masked text.', '<input type="password">'),
    ('Number', 'A single line numeric input with numeric validation.', '<input type="number">'),
    ('Date', 'A single line date input with date validation.', '<input type="date">');

-- Створення таблиці FORM_FIELDS
CREATE TABLE IF NOT EXISTS FORM_FIELDS (
    ID INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    FORM_ID INT(11) UNSIGNED NOT NULL,
    FIELD_TYPE_ID INT(11) UNSIGNED NOT NULL,
    LABEL VARCHAR(100) NOT NULL,
    REQUIRED TINYINT(1) NOT NULL,
    ORDER INT(11) NOT NULL,
    STATUS ENUM('Active', 'Inactive') DEFAULT 'Active',
    PRIMARY KEY (ID),
    FOREIGN KEY (FORM_ID) REFERENCES FORMS(ID) ON DELETE CASCADE,
    FOREIGN KEY (FIELD_TYPE_ID) REFERENCES FIELD_TYPES(ID) ON DELETE CASCADE
);

-- Додавання даних в таблицю FORM_FIELDS
INSERT INTO FORM_FIELDS (FORM_ID, FIELD_TYPE_ID, LABEL, REQUIRED, ORDER, STATUS)
VALUES
    (1, 1, 'Name', 1, 1, 'Active'),
    (1, 5, 'Email', 1, 2, 'Active'),
    (1, 3, 'Department', 0, 3, 'Active'),
    (2, 1, 'Name', 1, 1, 'Active'),
    (2, 5, 'Email', 1, 2, 'Active'),
    (2, 7, 'Password', 1, 3, 'Active'),
    (3, 2, 'Feedback', 1, 1, 'Active'),
    (3, 4, 'Satisfied?', 0, 2, 'Active'),
    (3, 6, 'Date', 0, 3, 'Inactive');

-- Створення таблиці FIELD_OPTIONS
CREATE TABLE IF NOT EXISTS FIELD_OPTIONS (
    ID INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    FORM_FIELD_ID INT(11) UNSIGNED NOT NULL,
    LABEL VARCHAR(100) NOT NULL,
    VALUE TEXT,
    PRIMARY KEY (ID),
    FOREIGN KEY (FORM_FIELD_ID) REFERENCES FORM_FIELDS(ID) ON DELETE CASCADE
);

-- Додавання даних в таблицю FIELD_OPTIONS
INSERT INTO FIELD_OPTIONS (FORM_FIELD_ID, LABEL, VALUE)
VALUES
    (3, 'General', NULL),
    (3, 'Technical', NULL),
    (4, 'Yes', 'true'),
    (4, 'No', 'false'),
    (9, 'Option 1', NULL),
    (9, 'Option 2', NULL),
    (9, 'Option 3', NULL);

-- Створення таблиці FORM_RESPONSES
CREATE TABLE IF NOT EXISTS FORM_RESPONSES (
    ID INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    FORM_ID INT(11) UNSIGNED NOT NULL,
    USER_ID INT(11) UNSIGNED NOT NULL,
    CREATED_AT DATETIME NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (FORM_ID) REFERENCES FORMS(ID) ON DELETE CASCADE,
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE
);

-- Створення таблиці FIELD_RESPONSES
CREATE TABLE IF NOT EXISTS FIELD_RESPONSES (
    ID INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    FORM_RESPONSE_ID INT(11) UNSIGNED NOT NULL,
    FORM_FIELD_ID INT(11) UNSIGNED NOT NULL,
    VALUE TEXT,
    PRIMARY KEY (ID),
    FOREIGN KEY (FORM_RESPONSE_ID) REFERENCES FORM_RESPONSES(ID) ON DELETE CASCADE,
    FOREIGN KEY (FORM_FIELD_ID) REFERENCES FORM_FIELDS(ID) ON DELETE CASCADE
);

-- Додавання даних в таблицю FIELD_RESPONSES
INSERT INTO FIELD_RESPONSES (FORM_RESPONSE_ID, FORM_FIELD_ID, VALUE)
VALUES (1, 1, 'John'),
    (1, 2, 'johndoe@example.com'),
    (1, 3, 'Sales'),
    (2, 1, 'Jane'),
    (2, 2, 'janedoe@example.com'),
    (2, 3, 'Marketing'),
    (2, 7, 'password123'),
    (3, 1, 'Bob'),
    (3, 2, 'bobsmith@example.com'),
    (3, 4, 'true'),
    (3, 9, 'Option 1');

-- Створення таблиці FORM_FIELDS_PARAMETERS
CREATE TABLE IF NOT EXISTS FORM_FIELDS_PARAMETERS (
    ID INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    FORM_FIELD_ID INT(11) UNSIGNED NOT NULL,
    NAME VARCHAR(50) NOT NULL,
    VALUE TEXT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (FORM_FIELD_ID) REFERENCES FORM_FIELDS(ID) ON DELETE CASCADE
);

-- Додавання даних в таблицю FORM_FIELDS_PARAMETERS
INSERT INTO FORM_FIELDS_PARAMETERS (FORM_FIELD_ID, NAME, VALUE)
VALUES (1, 'Placeholder', 'Enter your name...'),
    (2, 'Placeholder', 'Enter your email...'),
    (3, 'Options', '[{"label": "Sales", "value": "Sales"}, {"label": "Marketing", "value": "Marketing"}, {"label": "Support", "value": "Support"}]'),
    (7, 'Min Length', '8'),
    (9, 'Options', '[{"label": "Option 1", "value": "Option 1"}, {"label": "Option 2", "value": "Option 2"}, {"label": "Option 3", "value": "Option 3"}]');

-- Створення таблиці FORM_DEFAULT_VALUES
CREATE TABLE IF NOT EXISTS FORM_DEFAULT_VALUES (
    ID INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    FORM_ID INT(11) UNSIGNED NOT NULL,
    FIELD_TYPE_ID INT(11) UNSIGNED NOT NULL,
    VALUE TEXT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (FORM_ID) REFERENCES FORMS(ID) ON DELETE CASCADE,
    FOREIGN KEY (FIELD_TYPE_ID) REFERENCES FIELD_TYPES(ID) ON DELETE CASCADE
);

RENAME TABLE USERS TO SF_USERS;
RENAME TABLE FORMS TO SF_FORMS;
RENAME TABLE FORM_FIELDS TO SF_FORM_FIELDS;
RENAME TABLE FIELD_TYPES TO SF_FIELD_TYPES;
RENAME TABLE FORM_PARAMETERS TO SF_FORM_PARAMETERS;
RENAME TABLE FIELD_PARAMETERS TO SF_FIELD_PARAMETERS;
RENAME TABLE FIELD_OPTIONS TO SF_FIELD_OPTIONS;
RENAME TABLE FORM_RESPONSES TO SF_FORM_RESPONSES;
RENAME TABLE FIELD_RESPONSES TO SF_FIELD_RESPONSES;

INSERT INTO SF_FIELD_PARAMETERS (FORM_FIELD_ID, PARAMETER_NAME, PARAMETER_VALUE)
VALUES (1, 'required', 'true'),
       (1, 'min_length', '2'),
       (1, 'max_length', '50'),
       (2, 'required', 'true'),
       (2, 'min_length', '2'),
       (2, 'max_length', '50'),
       (3, 'required', 'false'),
       (3, 'min_length', '0'),
       (3, 'max_length', '50');


INSERT INTO SF_FIELD_OPTIONS (FIELD_ID, VALUE, LABEL)
VALUES (1, 'option_1', 'Option 1'),
       (1, 'option_2', 'Option 2'),
       (1, 'option_3', 'Option 3'),
       (2, 'option_4', 'Option 4'),
       (2, 'option_5', 'Option 5'),
       (2, 'option_6', 'Option 6'),
       (3, 'option_7', 'Option 7'),
       (3, 'option_8', 'Option 8'),
       (3, 'option_9', 'Option 9');

SELECT fr.*, ff.FIELD_NAME, ff.FIELD_TYPE_ID, ff.LABEL, ff.PLACEHOLDER, ff.OPTIONS, ff.MULTIPLE,
       fp.PARAMETER_NAME, fp.PARAMETER_VALUE
FROM SF_FORM_RESPONSES fr
LEFT JOIN SF_FIELD_RESPONSES ffr ON fr.ID = ffr.FORM_RESPONSE_ID
LEFT JOIN SF_FORM_FIELDS ff ON ffr.FORM_FIELD_ID = ff.ID
LEFT JOIN SF_FIELD_PARAMETERS fp ON ffr.ID = fp.FIELD_RESPONSE_ID
WHERE fr.FORM_ID = 1;