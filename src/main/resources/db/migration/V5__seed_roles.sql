INSERT INTO roles
(code, name, description, created_at, updated_at)
VALUES
    ('SYSTEM_ADMIN', 'System Administrator', 'Full system access', NOW(), NOW()),
    ('DOCTOR', 'Doctor', 'Doctor role', NOW(), NOW()),
    ('RECEPTIONIST', 'Receptionist', 'Reception role', NOW(), NOW()),
    ('PATIENT', 'Patient', 'Patient role', NOW(), NOW());