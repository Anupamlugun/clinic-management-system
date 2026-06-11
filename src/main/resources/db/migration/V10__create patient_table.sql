CREATE TABLE patients (
                          id BIGSERIAL PRIMARY KEY,
                          version BIGINT NOT NULL DEFAULT 0,

                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100),

                          email VARCHAR(255) NOT NULL,
                          phone_number VARCHAR(20),

                          date_of_birth DATE NOT NULL,
                          gender VARCHAR(10) NOT NULL,
                          blood_group VARCHAR(15),

                          emergency_contact_name VARCHAR(100),
                          emergency_contact_number VARCHAR(20),

                          address VARCHAR(500),

                          active BOOLEAN NOT NULL DEFAULT TRUE,

                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP,
                          created_by VARCHAR(255),
                          updated_by VARCHAR(255),

                          CONSTRAINT uk_patient_email UNIQUE(email)
);