CREATE TABLE SCHEMA_TEST.INDEX_TEST3 (
  INDEX_TEST3_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  SUB_ID_1 BIGINT NOT NULL,
  SUB_ID_2 BIGINT NOT NULL
);
COMMENT ON table SCHEMA_TEST.INDEX_TEST3 is 'INDEX_TEST3';
COMMENT ON column SCHEMA_TEST.INDEX_TEST3.INDEX_TEST3_ID is 'INDEX_TEST3_ID';
COMMENT ON column SCHEMA_TEST.INDEX_TEST3.SUB_ID_1 is 'SUB_ID_1';
COMMENT ON column SCHEMA_TEST.INDEX_TEST3.SUB_ID_2 is 'SUB_ID_2';
