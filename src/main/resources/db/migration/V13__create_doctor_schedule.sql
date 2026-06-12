CREATE TABLE doctor_schedules (
                                  id BIGSERIAL PRIMARY KEY,

                                  version BIGINT,

                                  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                  updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

                                  created_by VARCHAR(255),
                                  updated_by VARCHAR(255),

                                  doctor_id BIGINT NOT NULL,

                                  day VARCHAR(20) NOT NULL,

                                  start_time TIME NOT NULL,
                                  end_time TIME NOT NULL,

                                  active BOOLEAN NOT NULL DEFAULT TRUE,

                                  CONSTRAINT fk_schedule_doctor
                                      FOREIGN KEY (doctor_id)
                                          REFERENCES doctors(id),

                                  CONSTRAINT uk_doctor_day
                                      UNIQUE (doctor_id, day),

                                  CONSTRAINT chk_schedule_time
                                      CHECK (start_time < end_time)
);

CREATE INDEX idx_schedule_doctor
    ON doctor_schedules(doctor_id);

CREATE INDEX idx_schedule_day
    ON doctor_schedules(day);