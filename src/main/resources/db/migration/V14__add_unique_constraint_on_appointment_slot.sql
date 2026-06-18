ALTER TABLE appointments
    ADD CONSTRAINT uk_appointment_slot
        UNIQUE (slot_id);