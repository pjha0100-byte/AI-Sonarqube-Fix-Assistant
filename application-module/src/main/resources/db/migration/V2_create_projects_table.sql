CREATE TABLE sonar_issues
(
    id BIGSERIAL PRIMARY KEY,

    issue_key VARCHAR(255) UNIQUE,

    rule_key VARCHAR(255),

    severity VARCHAR(50),

    type VARCHAR(50),

    component VARCHAR(500),

    message TEXT,

    status VARCHAR(50),

    project_key VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);