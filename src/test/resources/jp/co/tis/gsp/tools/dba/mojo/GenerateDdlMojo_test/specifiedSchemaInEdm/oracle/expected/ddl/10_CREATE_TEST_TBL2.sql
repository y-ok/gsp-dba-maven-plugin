CREATE TABLE SCHEMA_TEST.TEST_TBL2 (
  TEST_TBL2_ID NUMBER NOT NULL ,
  TEST_TBL1_ID NUMBER NOT NULL ,
  TEST_NAME VARCHAR2(20 CHAR)
);
COMMENT ON table SCHEMA_TEST.TEST_TBL2 is 'テストテーブル2';
COMMENT ON column SCHEMA_TEST.TEST_TBL2.TEST_TBL2_ID is 'TEST_TBL2_ID';
COMMENT ON column SCHEMA_TEST.TEST_TBL2.TEST_TBL1_ID is 'TEST_TBL1_ID';
COMMENT ON column SCHEMA_TEST.TEST_TBL2.TEST_NAME is 'TEST_NAME';



