CREATE TABLE SCHEMA_TEST.INDEX_TEST1 (
  INDEX_TEST1_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  SUB_ID_1 BIGINT NOT NULL,
  SUB_ID_2 BIGINT NOT NULL
);
COMMENT ON table SCHEMA_TEST.INDEX_TEST1 is 'INDEX_TEST1';
COMMENT ON column SCHEMA_TEST.INDEX_TEST1.INDEX_TEST1_ID is 'INDEX_TEST1_ID';
COMMENT ON column SCHEMA_TEST.INDEX_TEST1.SUB_ID_1 is 'SUB_ID_1';
COMMENT ON column SCHEMA_TEST.INDEX_TEST1.SUB_ID_2 is 'SUB_ID_2';
