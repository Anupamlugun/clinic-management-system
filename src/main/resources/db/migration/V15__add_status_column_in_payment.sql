ALTER TABLE payments
    ADD COLUMN payment_status VARCHAR(20);

UPDATE payments
SET payment_status = 'PENDING'
WHERE payment_status IS NULL;

ALTER TABLE payments
    ALTER COLUMN payment_status SET NOT NULL;