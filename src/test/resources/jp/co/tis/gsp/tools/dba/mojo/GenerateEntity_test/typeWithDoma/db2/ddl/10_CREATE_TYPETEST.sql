CREATE TABLE TYPETEST (
  TYPE1 BIGINT,
  TYPE2 BLOB(100),
  TYPE3 CHARACTER(10),
  TYPE4 CLOB(100),
  TYPE5 DATE,
  TYPE6 DBCLOB(100),
  TYPE7 DECIMAL(10),
  TYPE8 DOUBLE,
  TYPE9 FLOAT,
  TYPE10 GRAPHIC(100),
  TYPE11 INTEGER,
  TYPE12 LONG VARCHAR,
  TYPE13 LONG VARGRAPHIC,
  TYPE14 NUMERIC(10),
  TYPE15 REAL,
  TYPE16 SMALLINT,
  TYPE17 TIME,
  TYPE18 TIMESTAMP,
  TYPE19 VARCHAR(100),
  TYPE20 VARGRAPHIC(100),
  TYPE21 NUMERIC(1,0)
);
COMMENT ON table TYPETEST is 'タイプテスト';
COMMENT ON column TYPETEST.TYPE1 is 'TYPE1';
COMMENT ON column TYPETEST.TYPE2 is 'TYPE2';
COMMENT ON column TYPETEST.TYPE3 is 'TYPE3';
COMMENT ON column TYPETEST.TYPE4 is 'TYPE4';
COMMENT ON column TYPETEST.TYPE5 is 'TYPE5';
COMMENT ON column TYPETEST.TYPE6 is 'TYPE6';
COMMENT ON column TYPETEST.TYPE7 is 'TYPE7';
COMMENT ON column TYPETEST.TYPE8 is 'TYPE8';
COMMENT ON column TYPETEST.TYPE9 is 'TYPE9';
COMMENT ON column TYPETEST.TYPE10 is 'TYPE10';
COMMENT ON column TYPETEST.TYPE11 is 'TYPE11';
COMMENT ON column TYPETEST.TYPE12 is 'TYPE12';
COMMENT ON column TYPETEST.TYPE13 is 'TYPE13';
COMMENT ON column TYPETEST.TYPE14 is 'TYPE14';
COMMENT ON column TYPETEST.TYPE15 is 'TYPE15';
COMMENT ON column TYPETEST.TYPE16 is 'TYPE16';
COMMENT ON column TYPETEST.TYPE17 is 'TYPE17';
COMMENT ON column TYPETEST.TYPE18 is 'TYPE18';
COMMENT ON column TYPETEST.TYPE19 is 'TYPE19';
COMMENT ON column TYPETEST.TYPE20 is 'TYPE20';
COMMENT ON column TYPETEST.TYPE21 is 'TYPE21';