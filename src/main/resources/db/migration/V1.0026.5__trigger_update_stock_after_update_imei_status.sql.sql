
CREATE OR REPLACE FUNCTION updateStock()
    RETURNS trigger AS
$$
BEGIN
    UPDATE PRODUCT_DETAILS product
    SET STOCK = (SELECT COUNT(IMEI.ID) FROM IMEI WHERE IMEI.STATUS = 1 AND IMEI.PRODUCT_DETAIL_ID =  OLD.product_detail_id  )
    WHERE product.ID = OLD.product_detail_id;
    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';
CREATE TRIGGER UPDATE_STOCK_AFTER_UPDATE_IMEI_STATUS
    AFTER UPDATE OF status
    ON imei
    FOR EACH ROW
EXECUTE FUNCTION updateStock();