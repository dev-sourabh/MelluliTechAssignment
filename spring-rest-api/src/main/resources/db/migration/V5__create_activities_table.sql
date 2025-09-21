CREATE TABLE public.activities (
    id BIGSERIAL PRIMARY KEY,
    "timestamp" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ip VARCHAR(50),
    country VARCHAR(100),
    city VARCHAR(100)
);

INSERT INTO public.activities ("timestamp", city, country, ip) VALUES
('2025-09-20 12:32:52.221721', 'Indore', 'India', '10.11.12.12'),
('2025-09-20 12:34:18.293017', 'Pune', 'India', '172.16.5.20'),
('2025-09-20 12:37:45.988708', 'New York', 'USA', '192.168.0.55'),
('2025-09-20 12:38:21.823154', 'London', 'UK', '203.0.113.77');