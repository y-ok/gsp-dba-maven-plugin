CREATE OR REPLACE VIEW VIEW4 AS
SELECT
ORDER_TEST.ORDER_ID AS OID, ORDER_TEST.CUSTOMER AS CUSTOMER_NAME FROM ORDER_T AS ORDER_TEST 
UNION
SELECT
ORDER_TEST.ORDER_ID AS OID, ORDER_TEST.CUSTOMER AS CUSTOMER_NAME FROM ORDER_T AS ORDER_TEST
UNION
SELECT
ORDER_TEST.ORDER_ID AS OID, ORDER_TEST.CUSTOMER AS CUSTOMER_NAME FROM ORDER_T AS ORDER_TEST
UNION
SELECT
ORDER_TEST.ORDER_ID AS OID, ORDER_TEST.CUSTOMER AS CUSTOMER_NAME FROM ORDER_T AS ORDER_TEST
UNION
SELECT
ORDER_TEST.ORDER_ID AS OID, ORDER_TEST.CUSTOMER AS CUSTOMER_NAME FROM ORDER_T AS ORDER_TEST