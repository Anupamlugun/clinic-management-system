CREATE TABLE doctors (
                         id BIGSERIAL PRIMARY KEY,
                         version BIGINT,

                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100),

                         email VARCHAR(255) UNIQUE NOT NULL,
                         phone_number VARCHAR(20),

                         specialization VARCHAR(100),
                         experience_years INTEGER,

                         active BOOLEAN DEFAULT TRUE,

                         created_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         created_by VARCHAR(100),
                         updated_by VARCHAR(100)
);