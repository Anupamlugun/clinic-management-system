-- V2__doctor_specialization_fk.sql

-- Remove old text column
ALTER TABLE doctors
DROP COLUMN specialization;

-- Add foreign key column
ALTER TABLE doctors
    ADD COLUMN specialization_id BIGINT;

-- Add FK constraint
ALTER TABLE doctors
    ADD CONSTRAINT fk_doctor_specialization
        FOREIGN KEY (specialization_id)
            REFERENCES doctor_specializations(id);