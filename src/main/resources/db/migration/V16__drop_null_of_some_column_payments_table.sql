ALTER TABLE public.payments
    ALTER COLUMN paid_at DROP NOT NULL,
ALTER COLUMN receipt_number DROP NOT NULL,
    ALTER COLUMN payment_mode DROP NOT NULL;