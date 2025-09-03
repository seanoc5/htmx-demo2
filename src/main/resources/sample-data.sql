-- ===== Concept =====
INSERT INTO concept (id, label, description, keywords, antiwords, skipwords, date_created, last_updated)
VALUES
    (10001, 'Artificial Intelligence', 'The simulation of human intelligence in machines', 'AI, machine learning, neural networks', 'artificial plants, artificial flavors', 'the, and, or', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10002, 'Cloud Computing', 'The delivery of computing services over the internet', 'cloud, AWS, Azure, GCP, servers', 'weather cloud, cloud formation', 'the, and, or', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10003, 'Blockchain', 'A distributed ledger technology', 'blockchain, cryptocurrency, ledger, bitcoin', 'chain block, building blocks', 'the, and, or', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10004, 'Data Privacy', 'Protection of personal data and privacy rights', 'GDPR, privacy, data protection', 'private property', 'the, and, or', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10005, 'Cybersecurity', 'Practice of protecting systems and networks', 'security, encryption, firewall, protection', 'physical security', 'the, and, or', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
