ALTER TABLE SCHEMA_TEST.INDEX_TEST3 ADD
PRIMARY KEY PK_INDEX_TEST12
(
  INDEX_TEST3_ID
)
  ,MODIFY INDEX_TEST3_ID BIGINT NOT NULL  COMMENT 'INDEX_TEST3_ID' AUTO_INCREMENT
;
