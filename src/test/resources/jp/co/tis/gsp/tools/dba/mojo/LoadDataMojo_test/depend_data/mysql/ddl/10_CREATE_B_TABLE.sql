CREATE TABLE B_TABLE (
  B_ID BIGINT NOT NULL  COMMENT 'B_ID',
  C_ID BIGINT NULL DEFAULT NULL  COMMENT 'C_ID',
  D_ID BIGINT NULL DEFAULT NULL  COMMENT 'D_ID',
  TEST_NAME VARCHAR(100) NULL DEFAULT NULL  COMMENT 'TEST_NAME',
  TEST1 VARCHAR(100) NULL DEFAULT NULL  COMMENT 'TEST1',
  TEST2 VARCHAR(500) NULL DEFAULT NULL  COMMENT 'TEST2',
  TEST3 VARCHAR(500) NULL DEFAULT NULL  COMMENT 'TEST3'
)
COMMENT='B_TABLE'