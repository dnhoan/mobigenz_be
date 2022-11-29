
CREATE OR REPLACE FUNCTION updateImeiStatus()
    RETURNS trigger AS
$$
BEGIN
    UPDATE imei
    set status = ( case when new.order_detail_id is null then 1 else 0
            end)
    WHERE id = new.ID;
    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';
CREATE TRIGGER UPDATE_IMEI_STATUS
    AFTER UPDATE OF order_detail_id
    ON imei
    FOR EACH ROW
EXECUTE FUNCTION updateImeiStatus();