ALTER TABLE A_TABLE ADD
PRIMARY KEY PK_A_TABLE
(
  A_ID
);

ALTER TABLE A_TABLE CHANGE A_ID A_ID BIGINT NOT NULL  COMMENT 'A_ID' AUTO_INCREMENT;
