-- =========================================================
-- APPOINTMENT SLOTS
-- =========================================================

CREATE TABLE appointment_slots (
                                   id BIGSERIAL PRIMARY KEY,
                                   version BIGINT,

                                   
                                   created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                   updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                   created_by VARCHAR(255),
                                   updated_by VARCHAR(255),

                                   doctor_id BIGINT NOT NULL,

                                   slot_date DATE NOT NULL,
                                   start_time TIME NOT NULL,
                                   end_time TIME NOT NULL,

                                   booked BOOLEAN NOT NULL DEFAULT FALSE,
                                   active BOOLEAN NOT NULL DEFAULT TRUE,

                                   CONSTRAINT fk_slot_doctor
                                       FOREIGN KEY (doctor_id)
                                       REFERENCES doctors(id),

                                   CONSTRAINT chk_slot_time
                                       CHECK (end_time > start_time),

                                   CONSTRAINT uk_doctor_slot
                                       UNIQUE (
                                           doctor_id,
                                           slot_date,
                                           start_time,
                                           end_time
                                       )
                                   

);

CREATE INDEX idx_slot_doctor
    ON appointment_slots(doctor_id);

CREATE INDEX idx_slot_date
    ON appointment_slots(slot_date);

-- =========================================================
-- APPOINTMENTS
-- =========================================================

CREATE TABLE appointments (
                              id BIGSERIAL PRIMARY KEY,
                              version BIGINT,

                              
                              created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                              updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
                              created_by VARCHAR(255),
                              updated_by VARCHAR(255),

                              patient_id BIGINT NOT NULL,
                              doctor_id BIGINT NOT NULL,
                              slot_id BIGINT NOT NULL,

                              appointment_date DATE NOT NULL,

                              status VARCHAR(30) NOT NULL,

                              reason VARCHAR(1000),

                              follow_up BOOLEAN NOT NULL DEFAULT FALSE,

                              parent_appointment_id BIGINT,

                              CONSTRAINT fk_appointment_patient
                                  FOREIGN KEY (patient_id)
                                  REFERENCES patients(id),

                              CONSTRAINT fk_appointment_doctor
                                  FOREIGN KEY (doctor_id)
                                  REFERENCES doctors(id),

                              CONSTRAINT fk_appointment_slot
                                  FOREIGN KEY (slot_id)
                                  REFERENCES appointment_slots(id),

                              CONSTRAINT fk_parent_appointment
                                  FOREIGN KEY (parent_appointment_id)
                                  REFERENCES appointments(id)
                              

);

CREATE INDEX idx_appointment_patient
    ON appointments(patient_id);

CREATE INDEX idx_appointment_doctor
    ON appointments(doctor_id);

CREATE INDEX idx_appointment_slot
    ON appointments(slot_id);

CREATE INDEX idx_appointment_date
    ON appointments(appointment_date);

CREATE INDEX idx_appointment_status
    ON appointments(status);

-- =========================================================
-- PAYMENTS
-- =========================================================

CREATE TABLE payments (
                          id BIGSERIAL PRIMARY KEY,
                          version BIGINT,

                          
                          created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                          updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
                          created_by VARCHAR(255),
                          updated_by VARCHAR(255),

                          appointment_id BIGINT NOT NULL,

                          amount NUMERIC(10,2) NOT NULL,

                          paid_at TIMESTAMP NOT NULL,

                          receipt_number VARCHAR(50) NOT NULL,

                          payment_mode VARCHAR(20) NOT NULL,

                          remarks VARCHAR(500),

                          CONSTRAINT fk_payment_appointment
                              FOREIGN KEY (appointment_id)
                              REFERENCES appointments(id),

                          CONSTRAINT chk_payment_amount
                              CHECK (amount > 0)
                          

);

CREATE INDEX idx_payment_appointment
    ON payments(appointment_id);

CREATE UNIQUE INDEX uk_payment_receipt_number
    ON payments(receipt_number);

-- =========================================================
-- PRESCRIPTIONS
-- =========================================================

CREATE TABLE prescriptions (
                               id BIGSERIAL PRIMARY KEY,
                               version BIGINT,

                               
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                               updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
                               created_by VARCHAR(255),
                               updated_by VARCHAR(255),

                               appointment_id BIGINT NOT NULL UNIQUE,
                               doctor_id BIGINT NOT NULL,
                               patient_id BIGINT NOT NULL,

                               diagnosis VARCHAR(5000),
                               notes VARCHAR(5000),

                               follow_up_date DATE,

                               CONSTRAINT fk_prescription_appointment
                                   FOREIGN KEY (appointment_id)
                                   REFERENCES appointments(id),

                               CONSTRAINT fk_prescription_doctor
                                   FOREIGN KEY (doctor_id)
                                   REFERENCES doctors(id),

                               CONSTRAINT fk_prescription_patient
                                   FOREIGN KEY (patient_id)
                                   REFERENCES patients(id)
                               

);

CREATE INDEX idx_prescription_doctor
    ON prescriptions(doctor_id);

CREATE INDEX idx_prescription_patient
    ON prescriptions(patient_id);

-- =========================================================
-- PRESCRIPTION MEDICINES
-- =========================================================

CREATE TABLE prescription_medicines (
                                        id BIGSERIAL PRIMARY KEY,
                                        version BIGINT,

                                        
                                        created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                        updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                        created_by VARCHAR(255),
                                        updated_by VARCHAR(255),

                                        prescription_id BIGINT NOT NULL,

                                        medicine_name VARCHAR(150) NOT NULL,

                                        dosage VARCHAR(100),

                                        frequency VARCHAR(100),

                                        duration_days INTEGER NOT NULL,

                                        instructions VARCHAR(500),

                                        CONSTRAINT fk_medicine_prescription
                                            FOREIGN KEY (prescription_id)
                                            REFERENCES prescriptions(id),

                                        CONSTRAINT chk_duration_days
                                            CHECK (duration_days > 0)
                                        

);

CREATE INDEX idx_prescription_medicine_prescription
    ON prescription_medicines(prescription_id);
