CREATE TABLE doctor_specializations (
                                        id BIGSERIAL PRIMARY KEY,
                                        version BIGINT,

                                        code VARCHAR(50) NOT NULL UNIQUE,
                                        name VARCHAR(100) NOT NULL,

                                        active BOOLEAN NOT NULL DEFAULT TRUE,

                                        created_at TIMESTAMP,
                                        updated_at TIMESTAMP,
                                        created_by VARCHAR(100),
                                        updated_by VARCHAR(100)
);