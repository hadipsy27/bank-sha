-- Insert Telkomsel plans
INSERT INTO data_plans (name, price, operator_card_id, created_at, updated_at) VALUES
('10 GB', 100, (SELECT id FROM operator_cards WHERE name = 'Telkomsel'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00'),
('50 GB', 500, (SELECT id FROM operator_cards WHERE name = 'Telkomsel'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00'),
('100 GB', 1000, (SELECT id FROM operator_cards WHERE name = 'Telkomsel'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00');

-- Insert Indosat plans
INSERT INTO data_plans (name, price, operator_card_id, created_at, updated_at) VALUES
('10 GB', 2000, (SELECT id FROM operator_cards WHERE name = 'Indosat'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00'),
('50 GB', 4000, (SELECT id FROM operator_cards WHERE name = 'Indosat'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00'),
('100 GB', 6000, (SELECT id FROM operator_cards WHERE name = 'Indosat'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00');

-- Insert XL plans
INSERT INTO data_plans (name, price, operator_card_id, created_at, updated_at) VALUES
('10 GB', 8000, (SELECT id FROM operator_cards WHERE name = 'XL'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00'),
('50 GB', 16000, (SELECT id FROM operator_cards WHERE name = 'XL'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00'),
('100 GB', 24000, (SELECT id FROM operator_cards WHERE name = 'XL'),  '2025-05-14 10:00:00',  '2025-05-14 10:00:00');
