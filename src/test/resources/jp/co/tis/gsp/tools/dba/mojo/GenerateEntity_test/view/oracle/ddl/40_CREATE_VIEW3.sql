CREATE OR REPLACE VIEW VIEW3 AS
SELECT
ORDER_TEST.ORDER_ID AS OID, ORDER_TEST.CUSTOMER AS CUSTOMER_NAME
FROM ORDER_T ORDER_TEST WHERE EXISTS (SELECT *  FROM ORDER_DETAIL OD WHERE ORDER_TEST.ORDER_DETAIL_ID = OD.ORDER_DETAIL_ID);