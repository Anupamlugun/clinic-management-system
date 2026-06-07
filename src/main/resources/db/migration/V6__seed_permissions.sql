INSERT INTO permissions
(code, name, description, created_at, updated_at)
VALUES

    ('CREATE_DOCTOR', 'Create Doctor', 'Create doctor records', NOW(), NOW()),
    ('UPDATE_DOCTOR', 'Update Doctor', 'Update doctor records', NOW(), NOW()),
    ('VIEW_DOCTOR', 'View Doctor', 'View doctor details', NOW(), NOW()),

    ('CREATE_PATIENT', 'Create Patient', 'Create patient records', NOW(), NOW()),
    ('UPDATE_PATIENT', 'Update Patient', 'Update patient records', NOW(), NOW()),
    ('VIEW_PATIENT', 'View Patient', 'View patient details', NOW(), NOW()),

    ('CREATE_APPOINTMENT', 'Create Appointment', 'Create appointment', NOW(), NOW()),
    ('UPDATE_APPOINTMENT', 'Update Appointment', 'Update appointment', NOW(), NOW()),
    ('VIEW_APPOINTMENT', 'View Appointment', 'View appointment', NOW(), NOW()),

    ('CREATE_CONSULTATION', 'Create Consultation', 'Create consultation', NOW(), NOW()),
    ('VIEW_CONSULTATION', 'View Consultation', 'View consultation', NOW(), NOW());