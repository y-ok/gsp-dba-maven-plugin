CREATE TABLE AUTO1 (
  AUTO1_ID NUMBER(18) NOT NULL ,
  TEST_NAME VARCHAR2(30 CHAR)
);
COMMENT ON table AUTO1 is '自動採番１';
COMMENT ON column AUTO1.AUTO1_ID is 'AUTO1_ID';
COMMENT ON column AUTO1.TEST_NAME is 'TEST_NAME';
CREATE SEQUENCE AUTO1_ID_SEQ increment by 1 start with 1;

