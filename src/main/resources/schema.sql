CREATE TABLE IF NOT EXISTS themes (
    theme_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    theme_name VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS news (
    news_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title VARCHAR(256) NOT NULL,
    theme_id BIGINT REFERENCES themes(theme_id) ON DELETE CASCADE NOT NULL,
    publication_date TIMESTAMP NOT NULL
);