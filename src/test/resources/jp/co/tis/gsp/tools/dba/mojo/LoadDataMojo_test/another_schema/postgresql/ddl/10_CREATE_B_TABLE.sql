CREATE TABLE gspanother.B_TABLE (
  B_ID SERIAL NOT NULL,
  C_ID BIGINT,
  D_ID BIGINT,
  TEST_NAME VARCHAR(100),
  TEST1 VARCHAR(100),
  TEST2 VARCHAR(500),
  TEST3 VARCHAR(500),
  PRIMARY KEY (B_ID)
);
COMMENT ON table gspanother.B_TABLE is 'B_TABLE';
COMMENT ON column gspanother.B_TABLE.B_ID is 'B_ID';
COMMENT ON column gspanother.B_TABLE.C_ID is 'C_ID';
COMMENT ON column gspanother.B_TABLE.D_ID is 'D_ID';
COMMENT ON column gspanother.B_TABLE.TEST_NAME is 'TEST_NAME';
COMMENT ON column gspanother.B_TABLE.TEST1 is 'TEST1';
COMMENT ON column gspanother.B_TABLE.TEST2 is 'TEST2';
COMMENT ON column gspanother.B_TABLE.TEST3 is 'TEST3';
